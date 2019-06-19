package com.wangxd.example.dao;

import com.wangxd.example.po.entity.BillHeadEntity;
import org.apache.ibatis.annotations.Param;

public interface BillHeadDao{

    BillHeadEntity findByKey(@Param("headKey") String headKey);

}
