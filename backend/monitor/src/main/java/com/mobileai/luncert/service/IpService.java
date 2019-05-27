// TODO:
// package com.mobileai.luncert.service;

// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.github.pagehelper.PageHelper;
// import com.github.pagehelper.PageInfo;
// import com.mobileai.luncert.model.mysql.IpMapper;
// import com.mobileai.luncert.utils.IPUtil;

// @Service
// public class IpService {
	
// 	@Autowired
// 	private IpMapper ipMapper;

// 	public int delProtected(String ip) {
// 		int ip2=IPUtil.ipToInt(ip);
// 		return ipMapper.deleteProtectedip(ip2);
// 	}

// 	public int delBlack(String ip) {
// 		int ip2=IPUtil.ipToInt(ip);
// 		return ipMapper.deleteBlackip(ip2);
// 	}

// 	public int setBlack(String ip) {
// 		int ip2=IPUtil.ipToInt(ip);
// 		return ipMapper.insertBlackip(ip2);
// 	}

// 	public int setProtected(String ip) {
// 		int ip2=IPUtil.ipToInt(ip);
// 		return ipMapper.insertProtectedip(ip2);
// 	}

// 	public PageInfo<String> getProtected(int pageSize,int pageNum) {
// 		int total=ipMapper.countProtecetd();
// 		PageHelper.startPage(pageNum, pageSize);
// 		List<Integer> list=ipMapper.getProtectedIP();
// 		List<String> ips=new ArrayList<>();
// 		for(int ip: list) {
// 			String i=IPUtil.ipToString(ip);
// 			ips.add(i);
// 		}
// 		PageInfo<String> info=new PageInfo<>(ips);
// 		info.setTotal(total);
// 		return info;
// 	}

// 	public PageInfo<String> getBlack( int pageSize,int pageNum) {
// 		int total=ipMapper.countBlack();
// 		PageHelper.startPage(pageNum, pageSize);
// 		List<Integer> list=ipMapper.getBlackIp();
// 		List<String> ips=new ArrayList<>();
// 		for(int ip: list) {
// 			//String i=IPUtil.ipToString(ip.getIp());
// 			String i=IPUtil.ipToString(ip);
// 			ips.add(i);
// 		}
// 		PageInfo<String> info=new PageInfo<>(ips);
// 		info.setTotal(total);
		
// 		return info;
// 	}
   
// }
