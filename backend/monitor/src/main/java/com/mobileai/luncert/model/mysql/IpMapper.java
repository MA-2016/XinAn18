// TODO:
// package com.mobileai.luncert.model.mysql;

// import java.util.List;

// import org.apache.ibatis.annotations.Delete;
// import org.apache.ibatis.annotations.Insert;
// import org.apache.ibatis.annotations.Param;
// import org.apache.ibatis.annotations.Select;

// public interface IpMapper {
    
// 	@Select("select * from BlacklistHost")
// 	List<Integer> getBlackIp();

// 	@Select("select * from ProtectedHost")
// 	List<Integer> getProtectedIP();

// 	@Insert("insert into BlacklistHost(ip) values (#{ip})")
// 	int insertBlackip(@Param("ip")int ip);
	
// 	@Insert("insert into ProtectedHost(ip) values (#{ip})")
// 	int insertProtectedip(@Param("ip")int ip);
	
// 	@Delete("delete from BlacklistHost where ip = #{ip} ")
// 	int deleteBlackip(@Param("ip")int ip);
// 	@Delete("delete from ProtectedHost where ip = #{ip} ")
// 	int deleteProtectedip(@Param("ip")int ip);
// 	@Select("select count(*) from BlacklistHost ")
// 	int countBlack();
	
// 	@Select("select count(*) from ProtectedHost ")
// 	int countProtecetd();
	
// }
