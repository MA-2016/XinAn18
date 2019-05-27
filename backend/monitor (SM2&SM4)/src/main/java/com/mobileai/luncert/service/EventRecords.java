package com.mobileai.luncert.service;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mobileai.luncert.model.mysql.EventMapper;
import com.mobileai.luncert.model.mysql.entity.Event;
import com.mobileai.luncert.utils.IPUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventRecords {

    @Autowired
    EventMapper eventMapper;

    public Event getLatest() {
        List<Event> ret = eventMapper.fetchN(1);
        if (ret != null && ret.size() == 1) return ret.get(0);
        else return null;
    }
    public Event getOneEvent(int id) {
		return eventMapper.fetchById(id);
	}

	public PageInfo<Event> getAll(int pageSize, int pageNum,String target) {
		PageHelper.startPage(pageNum, pageSize);
		List<Event> list=new ArrayList<>();
		if(target!=null) {
			int ip=IPUtil.ipToInt(target);
			System.out.println(ip);
			list= eventMapper.fetchByTarget(ip);
			System.out.println(list);
		}else {
	      list=eventMapper.fetchAll();
		}
		PageInfo<Event> info=new PageInfo<>(list);
		
		return info;
	}



	//time处需要纠正
	public PageInfo<Event> getWeekEvents(int pageSize, int pageNum, String target) {
		Calendar ca=Calendar.getInstance();
		ca.set(Calendar.DAY_OF_YEAR, ca.get(Calendar.DAY_OF_YEAR)-7);
		
		Date date=ca.getTime();
		
		PageHelper.startPage(pageNum, pageSize);
		List<Event> list=new ArrayList<>();
		
		if(target!=null) {
			int ip=IPUtil.ipToInt(target);
			list=eventMapper.fetchByTimeAndIp(ip, date);
		}else {
			list=eventMapper.fetchByTime(date);
		}
		PageInfo<Event> info =new PageInfo<>(list);
		return info;
	}
    //历史攻击统计
	public Map<String, Integer> getCensus(String target) {
		Map<String,Integer> map=new HashMap<>();
		Integer totalNum=0;
		Integer successNum=0;
		Integer faildNum=0;
		if(target!=null) {
			int ip=IPUtil.ipToInt(target);
			 totalNum=eventMapper.countAllBy(ip);
			 successNum=eventMapper.countEquleAnd(true,ip);
			 faildNum=eventMapper.countEquleAnd(false,ip);
		}else {
	    totalNum=eventMapper.countAll();
		 successNum=eventMapper.countEqule(true);
		 faildNum=eventMapper.countEqule(false);
		}
		map.put("totalNum", totalNum);
		map.put("successNum", successNum);
		map.put("faildNum", faildNum);
		return map;
	}
	//一周内统计
	public Map<String, Integer> getCensusWeek(String target) {
		Calendar ca=Calendar.getInstance();
		ca.set(Calendar.DAY_OF_YEAR, ca.get(Calendar.DAY_OF_YEAR)-7);
		
		Date date=ca.getTime();
		Map<String,Integer> map=new HashMap<>();
		Integer totalNum=0;
		Integer successNum=0;
		Integer faildNum=0;
		if(target!=null) {
			int ip=IPUtil.ipToInt(target);
			 totalNum=eventMapper.countWeekAllBy(ip,date);
			 successNum=eventMapper.countWeekEquleAnd(true,ip,date);
			 faildNum=eventMapper.countWeekEquleAnd(false,ip,date);
		}else {
	    totalNum=eventMapper.countWeekAll(date);
		 successNum=eventMapper.countWeekEqule(true,date);
		 faildNum=eventMapper.countWeekEqule(false,date);
		}
		map.put("totalNum", totalNum);
		map.put("successNum", successNum);
		map.put("faildNum", faildNum);
		return map;
	}

	public PageInfo<Event> getFailed(int pageSize, int pageNum, String target) {
		PageHelper.startPage(pageNum, pageSize);
		List<Event> list=new ArrayList<>();
		
		if(target!=null) {
			int ip=IPUtil.ipToInt(target);
			list=eventMapper.fetchBySuccessAnd(false, ip);
		}else {
			list=eventMapper.fetchBySuccess(false);
		}
		PageInfo<Event> info =new PageInfo<>(list);
		return info;
	}

	public PageInfo<Event> getSuccess(int pageSize, int pageNum, String target) {
		PageHelper.startPage(pageNum, pageSize);
		List<Event> list=new ArrayList<>();
		
		if(target!=null) {
			int ip=IPUtil.ipToInt(target);
			list=eventMapper.fetchBySuccessAnd(true, ip);
		}else {
			list=eventMapper.fetchBySuccess(true);
		}
		PageInfo<Event> info =new PageInfo<>(list);
		return info;
	}

	public PageInfo<Event> getWeekSuccess(int pageSize, int pageNum, String target) {
		Calendar ca=Calendar.getInstance();
		ca.set(Calendar.DAY_OF_YEAR, ca.get(Calendar.DAY_OF_YEAR)-7);
		
		Date date=ca.getTime();
		
		PageHelper.startPage(pageNum, pageSize);
		List<Event> list=new ArrayList<>();
		
		if(target!=null) {
			int ip=IPUtil.ipToInt(target);
			list=eventMapper.fetchWeekBySuccessAnd(true, ip, date);
		}else {
			list=eventMapper.fetchWeekBySuccess(true, date);
		}
		PageInfo<Event> info =new PageInfo<>(list);
		return info;
	}

	public PageInfo<Event> getWeekFailed(int pageSize, int pageNum, String target) {
		Calendar ca=Calendar.getInstance();
		ca.set(Calendar.DAY_OF_YEAR, ca.get(Calendar.DAY_OF_YEAR)-7);
		
		Date date=ca.getTime();
		
		PageHelper.startPage(pageNum, pageSize);
		List<Event> list=new ArrayList<>();
		
		if(target!=null) {
			int ip=IPUtil.ipToInt(target);
			list=eventMapper.fetchWeekBySuccessAnd(false, ip, date);
		}else {
			list=eventMapper.fetchWeekBySuccess(false, date);
		}
		PageInfo<Event> info =new PageInfo<>(list);
		return info;
    }
    
}