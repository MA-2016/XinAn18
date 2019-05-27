package com.mobileai.luncert.model.mysql;

import com.mobileai.luncert.model.mysql.entity.PHost;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PHostMapper {

    @Select("select * from ProtectedHost where ip = #{ip}")
    public PHost findByIp(@Param("ip") int ip);
    
}