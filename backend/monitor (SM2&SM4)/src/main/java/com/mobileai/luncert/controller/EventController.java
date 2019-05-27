package com.mobileai.luncert.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.mobileai.luncert.annotation.AdminAuthCheck;
import com.mobileai.luncert.annotation.AuthCheck;
import com.mobileai.luncert.model.mysql.entity.Event;
import com.mobileai.luncert.service.EventRecords;

@RestController
@CrossOrigin
@RequestMapping("event")
public class EventController {
	
	@Autowired
	private EventRecords eventRecords;
	
	// @AuthCheck
	@GetMapping(value = "/queryEventById")
	public Event queryEventById(int id) {
		return eventRecords.getOneEvent(id);
	}
	
	@AdminAuthCheck
	@GetMapping(value = "/fetchAllEventsAdmin")
	public PageInfo<Event> fetchAllEventsAdmin(int pageSize, int pageNum, @RequestParam(required = false) String target) {
		return eventRecords.getAll(pageSize,pageNum,target);
	}

	@AuthCheck
	@GetMapping(value="/fetchAllEvents")
	public PageInfo<Event> fetchAllEvents(
		@RequestParam int pageSize,
		@RequestParam int pageNum,
		@RequestParam String target) {
		return eventRecords.getAll(pageSize,pageNum,target);
	}
	
	@AdminAuthCheck	
	@GetMapping(value="/getEventLastWeekAdmin")
	public PageInfo<Event> getEventLastWeekAdmin(
		@RequestParam int pageSize,
		@RequestParam int pageNum,
		@RequestParam(required=false) String target) {
		return eventRecords.getWeekEvents(pageSize,pageNum,target);
	}

	@AuthCheck	
	@GetMapping(value="/getEventLastWeek")
	public PageInfo<Event> getEventLastWeek(
		@RequestParam int pageSize,
		@RequestParam int pageNum,
		@RequestParam String target) {
		return eventRecords.getWeekEvents(pageSize,pageNum,target);
	}
	
	@AdminAuthCheck
	@GetMapping(value="/getCensusAllAdmin")
	public Map<String,Integer> getCensusAllAdmin(
		@RequestParam(required=false) String target){
		return eventRecords.getCensus(target);
	}
	
	@AuthCheck
	@GetMapping(value="/getCensusAll")
	public Map<String,Integer> getCensusAll(
		@RequestParam String target){
		return eventRecords.getCensus(target);
	}
	
	@AdminAuthCheck
	@GetMapping(value="/getCensusLastWeekAdmin")
	public Map<String, Integer> getCensusLastWeekAdmin(
		@RequestParam(required=false) String target){
		return eventRecords.getCensusWeek(target);
	}
	  
	@AuthCheck
	@GetMapping(value="/getCensusLastWeek")
	public Map<String, Integer> getCensusLastWeek(
		@RequestParam String target){
		return eventRecords.getCensusWeek(target);
	}
	
	@AdminAuthCheck
	@GetMapping(value="getAttackSuccessAdmin")
	public PageInfo<Event> getSuccessAdmin(
		@RequestParam int pageSize,
		@RequestParam int pageNum,
		@RequestParam(required=false) String target){
		return eventRecords.getSuccess(pageSize,pageNum,target);
	}
	
	@AuthCheck
	@GetMapping(value="getAttackSuccess")
	public PageInfo<Event> getSuccess(
		@RequestParam int pageSize,
		@RequestParam int pageNum,
		@RequestParam String target){
		return eventRecords.getSuccess(pageSize,pageNum,target);
	}

	@AdminAuthCheck
	@GetMapping(value="getAttackFailedAdmin")
	 public PageInfo<Event> getFailedAdmin(
		 @RequestParam int pageSize,
		 @RequestParam int pageNum,
		 @RequestParam(required=false) String target){
		PageInfo<Event> info =eventRecords.getFailed(pageSize,pageNum,target);
		return info;
	}
	
	@AuthCheck
	@GetMapping(value="getAttackFailed")
	 public PageInfo<Event> getFailed(
		 @RequestParam int pageSize,
		 @RequestParam int pageNum,
		 @RequestParam String target){
		PageInfo<Event> info =eventRecords.getFailed(pageSize,pageNum,target);
		return info;
	}

	@AdminAuthCheck
	@GetMapping(value="getAttackSuccessLastWeekAdmin")
	 public PageInfo<Event> getAttackSuccessLastWeekAdmin(
		 @RequestParam int pageSize,
		 @RequestParam int pageNum,
		 @RequestParam(required=false) String target){
		PageInfo<Event> info =eventRecords.getWeekSuccess(pageSize,pageNum,target);
		return info;
	}

	@AuthCheck
	@GetMapping(value="getAttackSuccessLastWeek")
	 public PageInfo<Event> getAttackSuccessLastWeek(
		 @RequestParam int pageSize,
		 @RequestParam int pageNum,
		 @RequestParam String target){
		PageInfo<Event> info =eventRecords.getWeekSuccess(pageSize,pageNum,target);
		return info;
	}

	@AdminAuthCheck
	@GetMapping(value="getAttackFailedLastWeekAdmin")
	 public PageInfo<Event> getAttackFailedLastWeekAdmin(
		 @RequestParam int pageSize,
		 @RequestParam int pageNum,
		 @RequestParam(required=false) String target){
		PageInfo<Event> info =eventRecords.getWeekFailed(pageSize,pageNum,target);
		return info;
	}

	@AuthCheck
	@GetMapping(value="getAttackFailedLastWeek")
	 public PageInfo<Event> getAttackFailedLastWeek(
		 @RequestParam int pageSize,
		 @RequestParam int pageNum,
		 @RequestParam String target){
		PageInfo<Event> info =eventRecords.getWeekFailed(pageSize,pageNum,target);
		return info;
	}
	
}
