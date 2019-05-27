package com.mobileai.luncert.model.mysql;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.mobileai.luncert.model.mysql.EventMapper;
import com.mobileai.luncert.model.mysql.entity.Event;
import com.mobileai.luncert.utils.Convert;
import com.mobileai.luncert.utils.IPUtil;
import com.mobileai.luncert.utils.MySQLUtil;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestEventMapper {

    private static Logger logger = Logger.getLogger(TestEventMapper.class);

    private SqlSession session = MySQLUtil.open();

    @Test
    public void testFetchLastAlert() {

        Event event = session.getMapper(EventMapper.class).fetchLastAlert();
        if (event != null) logger.info(event.getId() + " : " + event.getSourceString() + " -> " + event.getTargetString());

        MySQLUtil.close(session);
    }

    @Test
    public void testFetchAll() {
        List<Event> result = session.getMapper(EventMapper.class).fetchAll();
        for (Event event : result) {
            logger.info(event.getId() + " : " + event.getSourceString() + " -> " + event.getTargetString());
        }

		MySQLUtil.close(session);
    }

    @Test
    public void testFetch1000() {
        List<Event> result = session.getMapper(EventMapper.class).fetchAll();
        for (Event event : result) {
            logger.info(event.getId() + " : " + event.getSourceString() + " -> " + event.getTargetString());
        }

		MySQLUtil.close(session);
    }

    @Test
    public void testAddEvent() throws IOException {
        String source = "192.168.3.1";
        String target = "192.168.240.103";

        session.getMapper(EventMapper.class).addEvent(
            Convert.dateCastString(new Date()),
            IPUtil.ipToInt(source),
            IPUtil.ipToInt(target),
            IPUtil.queryPos(source).toJSONString(),
            IPUtil.queryPos(target).toJSONString(),
            "xx",
            1,
            "DetailAttackPattern",
            false,
            true,
            "Category");

		MySQLUtil.close(session);
    }

}