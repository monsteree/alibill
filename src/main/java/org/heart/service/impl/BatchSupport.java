package org.heart.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@Scope("singleton")
@Service
public class BatchSupport extends SqlSessionDaoSupport {

	@Autowired
	private SqlSessionFactory extendSqlSessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(BatchSupport.class);

	private static final int DEF_BATCH_SIZE = 10000;

	/**
	 *  批量插入对象
	 *
	 * 	使用ExecutorType.BATCH需要新建SqlSession，插入的数据无法和AOP事务一起回滚
	 * @param statementId
	 * @param list
	 * @param <T>
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public <T> int batchExecute(String statementId, Collection<T> list) {
		return batchExecuteTransaction(statementId, list);
	}

	/**
	 *  批量插入对象
	 *
	 * 	使用ExecutorType.BATCH需要新建SqlSession，插入的数据无法和AOP事务一起回滚
	 * @param statementId
	 * @param list
	 * @param <T>
	 * @return
	 */
	public <T> int batchExecuteTransaction(String statementId, Collection<T> list) {
		if (StringUtils.isBlank(statementId) || list == null) {
			logger.error("StatementId {} or Collection {} is invalid. ", new Object[] { statementId, list });
			return 0;
		}
		if (list.isEmpty()) {
			return 0;
		}
		if (extendSqlSessionFactory != null) {
			SqlSession session = this.extendSqlSessionFactory.openSession(ExecutorType.BATCH, false);
			logger.info("batch mode: [{}] size:[{}] start...", statementId, list.size());
			try {
				int er = 0;
				int count = 0;
				int listsize= list.size()-1;
				for (T obj : list) {
					int effect = session.update(statementId, obj);
					er += effect;
					if (count % DEF_BATCH_SIZE == 0 || count == listsize) {
						session.flushStatements();
						session.commit();
					}
					count++;
				}
				logger.info("batch mode: [{}] size:[{}] end...", statementId, list.size());
				return er;
			}catch (Exception e){
				session.rollback();
				logger.info("QueryAssist.batchExecuteTransaction ERROR ", e);
				throw e;
			} finally {
				session.close();
			}

		} else {
			logger.error("未配置extendSqlSessionFactory");
			return 0;
		}
	}

	public <T, V> boolean batchcheck(String statementId, Collection<T> list, Class<V> clazz) {
		int num = list.size();
		int total = 0;
		if (StringUtils.isBlank(statementId) || list == null) {
			logger.error("StatementId {} or Collection {} is invalid. ", new Object[] { statementId, list });
			return false;
		}
		if (list.isEmpty()) {
			return false;
		}
		if (extendSqlSessionFactory != null) {
			SqlSession session = this.extendSqlSessionFactory.openSession();
			logger.info("batch mode: [{}] size:[{}] start...", statementId, list.size());
			int count = 0;
			List<T> checklist = new ArrayList<T>();
			for (T obj : list) {
				checklist.add(obj);
				count++;	
				if (count % DEF_BATCH_SIZE == 0 || count == list.size()) {
					List<V> effect = session.selectList(statementId, checklist);
					total += effect.size();
					checklist.clear();
				}	
			}
			logger.info("batch mode: [{}] size:[{}] end...", statementId, list.size());
			session.close();
			if (num == total) {
				return true;
			} else {
				return false;
			}
		} else {
			logger.error("未配置extendSqlSessionFactory");
			return false;
		}
	}

	
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

}
