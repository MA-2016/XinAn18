// TODO:
// package com.mobileai.luncert.controller;

// import java.io.IOException;
// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.github.pagehelper.PageInfo;
// import com.mobileai.luncert.service.IpService;

// @CrossOrigin
// @RestController
// @RequestMapping("/ip")
// public class IpController {
	
// 	@Autowired
// 	private IpService ipService;
	
// 	@PostMapping(value="/setBlackIp")
// 	public Map<String,Integer> setBlack(@RequestParam String ip) throws IOException{
// 		int i=ipService.setBlack(ip);
// 		Map<String,Integer> map=new HashMap<>();
// 		if(i==0) {
			
// 			map.put("code", 100);
// 		}else {
// 			map.put("code", 200);
// 		}
// 		return map;
		
// 	}
// 	@PostMapping(value="/setProtectedIp")
// 	public Map<String,Integer> setProtected(@RequestParam String ip) {
// 		int i=ipService.setProtected(ip);
// 		Map<String,Integer> map=new HashMap<>();
// 		if(i==0) {
			
// 			map.put("code", 100);
// 		}else {
// 			map.put("code", 200);
// 		}
// 		return map;
// 	}
	
// 	@DeleteMapping("/delBlackIp")
// 	public Map<String,Integer> delBlack(@RequestParam String ip) {
// 		int i=ipService.delBlack(ip);
// 		Map<String,Integer> map=new HashMap<>();
// 		if(i==0) {
			
// 			map.put("code", 100);
// 		}else {
// 			map.put("code", 200);
// 		}
// 		return map;
// 	}
// 	@DeleteMapping("/delProtectedIp")
// 	public Map<String,Integer> delProtected(@RequestParam String ip) {
// 		int i=ipService.delProtected(ip);
// 		Map<String,Integer> map=new HashMap<>();
// 		if(i==0) {
			
// 			map.put("code", 100);
// 		}else {
// 			map.put("code", 200);
// 		}
// 		return map;
// 	}
	
// 	@GetMapping("/getBlackIp")
// 	public PageInfo<String> getBlack(@RequestParam int pageSize,@RequestParam int pageNum){
// 		PageInfo<String> info =ipService.getBlack(pageSize,pageNum);
// 		return info;
// 	}
	
// 	@GetMapping("/getProtectedIp")
// 	public PageInfo<String> getProtected(@RequestParam int pageSize,@RequestParam int pageNum){
// 		PageInfo<String> info =ipService.getProtected(pageSize,pageNum);
		
// 		return info;
		
// 	}

// }
