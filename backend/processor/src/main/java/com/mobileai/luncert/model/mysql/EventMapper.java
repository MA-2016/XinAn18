package com.mobileai.luncert.model.mysql;


import java.util.List;

import com.mobileai.luncert.model.mysql.entity.Event;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EventMapper {

    @Select("select * from Event where beScene = true order by id desc limit 0, 1")
    public Event fetchLastAlert();

    @Select("select * from Event")
    public List<Event> fetchAll();

    @Select("select * from Event where id > #{id}")
    public List<Event> fetchAllAfter(@Param("id") int id);

    @Select("select * from Event limit 0, 1000")
    public List<Event> fetch1000();

    @Insert("insert into Event(time, source, target, srcPos, tarPos, eventId, eventType, beScene, attackSuccess, name, attackType) values(#{time}, #{source}, #{target}, #{srcPos}, #{tarPos}, #{eventId}, #{eventType}, #{beScene}, #{attackSuccess}, #{name}, #{attackType})")
    public void addEvent(@Param("time") String time,
                        @Param("source") int source,
                        @Param("target") int target,
                        @Param("srcPos") String srcPos,
                        @Param("tarPos") String tarPos,
                        @Param("name") String name, // eventName
                        @Param("eventId") int eventId,
                        @Param("eventType") String eventType,
                        @Param("beScene") boolean beScene,
                        @Param("attackSuccess") boolean attackSuccess,
                        @Param("attackType") String attackType);

}