package com.sailing.dao;

import java.util.List;
import java.util.Map;

import com.sailing.entity.CarInfo;

public interface CarInfoMapper {
	public List<CarInfo> selectCarInfo();
	public List<CarInfo> selectCarPage(Map<String,Object> map);
	 
}
