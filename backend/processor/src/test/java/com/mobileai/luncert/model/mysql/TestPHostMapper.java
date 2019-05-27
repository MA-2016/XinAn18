package com.mobileai.luncert.model.mysql;

import com.mobileai.luncert.model.mysql.PHostMapper;
import com.mobileai.luncert.model.mysql.entity.PHost;
import com.mobileai.luncert.utils.IPUtil;
import com.mobileai.luncert.utils.MySQLUtil;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestPHostMapper {

    private static Logger logger = Logger.getLogger(TestPHostMapper.class);

    private SqlSession session = MySQLUtil.open();

    @Test
    public void testFindByIp() {
        PHost phost = session.getMapper(PHostMapper.class).findByIp(1090093);
        logger.info(IPUtil.ipToString(phost.getIp()));

        MySQLUtil.close(session);
    }

}