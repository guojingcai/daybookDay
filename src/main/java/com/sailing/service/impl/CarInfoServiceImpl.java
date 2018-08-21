package com.sailing.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sailing.dao.CarInfoMapper;
import com.sailing.entity.CarInfo;
import com.sailing.service.CarInfoService;
@Service
public class CarInfoServiceImpl implements CarInfoService {
	@Autowired
	private CarInfoMapper carInfoMapper;
	@Override
	public List<CarInfo> selectCarInfo() {
		return carInfoMapper.selectCarInfo();
	}
	@Override
	public List<CarInfo> selectCarPage(Map<String, Object> map) {
		 
		return carInfoMapper.selectCarPage(map);
	}

	 
}
