package com.sailing.service;

import java.util.List;
import java.util.Map;

import com.sailing.entity.CarInfo;
import com.sailing.page.PageBean;

public interface CarInfoService {
	public List<CarInfo> selectCarInfo();
	public List<CarInfo> selectCarPage(Map<String,Object> map);
}
