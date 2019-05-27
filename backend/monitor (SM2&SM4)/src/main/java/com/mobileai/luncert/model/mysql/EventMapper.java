package com.mobileai.luncert.model.mysql;

import java.util.Date;
import java.util.List;

import com.mobileai.luncert.model.mysql.entity.Event;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EventMapper {

    @Select("select * from Event")
    public List<Event> fetchAll();

    @Select("select * from Event limit 0, #{size}")
    public List<Event> fetchN(@Param("size") int size);
    
    @Select("select * from Event where alert = true order by id desc limit 0, 1")
    public Event fetchLastAlert();

    @Select("select * from Event where id > #{id}")
    public List<Event> fetchAllAfter(@Param("id") int id);

    @Select("select * from Event limit 0, 1000")
    public List<Event> fetch1000();

    @Insert("insert into Event(time, source, target, eventId, alert) values(#{time}, #{source}, #{target}, #{eventId}, #{alert})")
    public void addEvent(@Param("time") Date time, @Param("source") int source, @Param("target") int target, @Param("eventId") int eventId, @Param("alert") boolean alert);
    
    @Select("select * from Event where id = #{id}")
    public Event fetchById(@Param("id") int id);
    
    @Select("select * from Event where target = #{target}")
    public  List<Event> fetchByTarget(@Param("target") int target);
   
    @Select("select * from Event where time > #{time}")
    public  List<Event> fetchByTime(@Param("time") Date time);
    
    @Select("select * from Event where target = #{target} and time > #{time}")
    public  List<Event> fetchByTimeAndIp(@Param("target") int target, @Param("time")Date time);
    
    @Select("select count(*) from Event")
    public int countAll();
    
    @Select("select count(*) from Event where target = #{target}")
    public int countAllBy(@Param("target") int target);
    
    @Select("select count(*) from Event where attackSuccess = #{attackSuccess} ")
    public int  countEqule(@Param("attackSuccess") Boolean attackSuccess);
    
    @Select("select count(*) from Event where attackSuccess = #{attackSuccess} and target = #{target}")
    public int  countEquleAnd(@Param("attackSuccess") Boolean attackSuccess,@Param("target") int target);
 
    @Select("select count(*) from Event where time > #{time}")
    public int countWeekAll( @Param("time")Date time);
    
    @Select("select count(*) from Event where target = #{target} and time > #{time}")
    public int countWeekAllBy(@Param("target") int target, @Param("time")Date time);
    
    @Select("select count(*) from Event where attackSuccess = #{attackSuccess} and time > #{time}")
    public int  countWeekEqule(@Param("attackSuccess") Boolean attackSuccess, @Param("time")Date time);
    
    @Select("select count(*) from Event where attackSuccess = #{attackSuccess} and target = #{target} and time > #{time}")
    public int  countWeekEquleAnd(@Param("attackSuccess") Boolean attackSuccess,@Param("target") int target, @Param("time")Date time);

   
    @Select("select * from Event where attackSuccess = #{attackSuccess}")
    public List<Event> fetchBySuccess(@Param("attackSuccess") Boolean attackSuccess);
    
    @Select("select * from Event where attackSuccess = #{attackSuccess} and target = #{target}")
    public List<Event> fetchBySuccessAnd(@Param("attackSuccess") Boolean attackSuccess,@Param("target") int target);
 
    
    @Select("select * from Event where attackSuccess = #{attackSuccess} and time > #{time}")
    public List<Event> fetchWeekBySuccess(@Param("attackSuccess") Boolean attackSuccess, @Param("time")Date time);
    
    @Select("select * from Event where attackSuccess = #{attackSuccess} and target = #{target} and time > #{time}")
    public List<Event> fetchWeekBySuccessAnd(@Param("attackSuccess") Boolean attackSuccess,@Param("target") int target, @Param("time")Date time);

}