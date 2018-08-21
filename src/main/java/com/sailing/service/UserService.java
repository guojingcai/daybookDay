package com.sailing.service;

import org.apache.ibatis.annotations.Param;

import com.sailing.entity.User;

public interface UserService {
	public User selectLogin(String username,String upassword);
	public void insertUser(User user);
	public User selectUser(String username);
	public User selectUserCode(@Param("uvalidatecode") String uvalidatecode);
	public int updateByPrimaryKeySelective(User record);
}
