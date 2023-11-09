package org.heart.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BillTradeDao {

    String BATCH_MERGE = "org.heart.dao.BillTradeDao.merge";

}
