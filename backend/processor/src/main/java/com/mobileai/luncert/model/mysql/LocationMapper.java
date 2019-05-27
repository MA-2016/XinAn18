package com.mobileai.luncert.model.mysql;

import java.util.List;

import com.mobileai.luncert.model.mysql.entity.Location;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LocationMapper {

    @Select("select * from Location")
    public List<Location> fetchAll();

}