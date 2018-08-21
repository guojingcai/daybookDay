package com.sailing.dao;

import org.apache.ibatis.annotations.Param;

import com.sailing.entity.User;

public interface UserMapper {
	public User selectLogin(@Param("username") String username,@Param("upassword") String upassword);
	public User selectUser(@Param("username") String username);
	public void insertUser(User user);
	public User selectUserCode(@Param("uvalidatecode") String uvalidatecode);
	public int updateByPrimaryKeySelective(User record);
}
