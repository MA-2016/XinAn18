package com.mobileai.luncert.model.mysql;

import java.util.List;

import com.mobileai.luncert.model.mysql.entity.Event;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EventMapper {

    @Select("SELECT * FROM event WHERE alert = true ORDER BY id DESC LIMIT 0, 1")
    public Event fetchLastAlert();

    @Select("SELECT * FROM event")
    public List<Event> fetchAll();

    @Select("SELECT* FROM event WHERE id > #{id}")
    public List<Event> fetchAllAfter(@Param("id") int id);

    @Select("SELECT * FROM event LIMIT 0, 1000")
    public List<Event> fetch1000();

    @Insert("INSERT INTO event(timestamp, source, target, eventId, alert) VALUES (#{timestamp}, #{source}, #{target}, #{eventId}, #{alert})")
    public void addEvent(@Param("timestamp") long timestamp, @Param("source") int source, @Param("target") int target, @Param("eventId") int eventId, @Param("alert") boolean alert);
    
    @Select("SELECT * FROM event WHERE id = #{id}")
    public Event fetchById(@Param("id") int id);
    
    @Select("SELECT * FROM event WHERE target = #{target}")
    public  List<Event> fetchByTarget(@Param("target") int target);
   
    @Select("SELECT * FROM event WHERE timestamp > #{timestamp}")
    public  List<Event> fetchByTime(@Param("timestamp") long timestamp);
    
    @Select("SELECT * FROM event WHERE target = #{target} and timestamp > #{timestamp}")
    public  List<Event> fetchByTimeAndIp(@Param("target") int target, @Param("timestamp") long timestamp);
    
    @Select("SELECT count(*) FROM event")
    public int countAll();
    
    @Select("SELECT count(*) FROM event WHERE target = #{target}")
    public int countAllBy(@Param("target") int target);
    
    @Select("SELECT count(*) FROM event WHERE attackSuccess = #{attackSuccess} ")
    public int  countEqule(@Param("attackSuccess") Boolean attackSuccess);
    
    @Select("SELECT count(*) FROM event WHERE attackSuccess = #{attackSuccess} AND target = #{target}")
    public int  countEquleAnd(@Param("attackSuccess") Boolean attackSuccess, @Param("target") int target);
 
    @Select("SELECT count(*) FROM event WHERE timestamp > #{timestamp}")
    public int countWeekAll( @Param("timestamp") long timestamp);
    
    @Select("SELECT count(*) From event WHERE target = #{target} and timestamp > #{timestamp}")
    public int countWeekAllBy(@Param("target") int target, @Param("timestamp")long timestamp);
    
    @Select("SELECT count(*) FROM event WHERE attackSuccess = #{attackSuccess} AND timestamp > #{timestamp}")
    public int  countWeekEqule(@Param("attackSuccess") Boolean attackSuccess, @Param("timestamp") long timestamp);
    
    @Select("SELECT count(*) FROM event WHERE attackSuccess = #{attackSuccess} AND target = #{target} AND timestamp > #{timestamp}")
    public int  countWeekEquleAnd(@Param("attackSuccess") Boolean attackSuccess, @Param("target") int target, @Param("timestamp") long timestamp);
   
    @Select("SELECT * FROM event WHERE attackSuccess = #{attackSuccess}")
    public List<Event> fetchBySuccess(@Param("attackSuccess") Boolean attackSuccess);
    
    @Select("SELECT * FROM event WHERE attackSuccess = #{attackSuccess} AND target = #{target}")
    public List<Event> fetchBySuccessAnd(@Param("attackSuccess") Boolean attackSuccess, @Param("target") int target);
 
    
    @Select("SELECT * FROM event WHERE attackSuccess = #{attackSuccess} AND timestamp > #{timestamp}")
    public List<Event> fetchWeekBySuccess(@Param("attackSuccess") Boolean attackSuccess, @Param("timestamp") long timestamp);
    
    @Select("SELECT * FROM event WHERE attackSuccess = #{attackSuccess} AND target = #{target} AND timestamp > #{timestamp}")
    public List<Event> fetchWeekBySuccessAnd(@Param("attackSuccess") Boolean attackSuccess,@Param("target") int target, @Param("timestamp") long timestamp);

}