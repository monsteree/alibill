package org.heart.dao;

import org.apache.ibatis.annotations.Mapper;
import org.heart.dto.BillUserinfoDTO;

import java.util.List;

@Mapper
public interface BillUserinfoDao {
    /**
     * 查询所有用户信息
     * @return
     */
    List<BillUserinfoDTO> queryAll();

    /**
     * 根据邮箱查询信息
     * @param billUserinfoDTO
     * @return
     */
    BillUserinfoDTO queryByMailName(BillUserinfoDTO billUserinfoDTO);
}
