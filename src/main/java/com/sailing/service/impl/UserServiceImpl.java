package com.sailing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sailing.dao.UserMapper;
import com.sailing.entity.User;
import com.sailing.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	@Override
	public User selectLogin(String username, String upassword) {
		return userMapper.selectLogin(username, upassword);
	}
	@Override
	public void insertUser(User user) {
		userMapper.insertUser(user);
		
	}
	@Override
	public User selectUser(String username) {
		 return userMapper.selectUser(username);
	}
	@Override
	public User selectUserCode(String uvalidatecode) {
		return userMapper.selectUserCode(uvalidatecode);
	}
	@Override
	public int updateByPrimaryKeySelective(User record) {
		return userMapper.updateByPrimaryKeySelective(record);
	}

}
