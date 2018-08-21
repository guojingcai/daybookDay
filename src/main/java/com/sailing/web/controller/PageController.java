package com.sailing.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sailing.entity.CarInfo;
import com.sailing.service.CarInfoService;

@Controller
@RequestMapping("/page")
public class PageController {
	@Autowired
	private CarInfoService carInfoService;

	@RequestMapping("/index")
	public String pageIndex(){
		return "/home1.1";
	}
	
	@RequestMapping("/showCar")
	public String pageShowCar(HttpServletRequest re,CarInfo carInfo){
		List<CarInfo> selectCarInfo = carInfoService.selectCarInfo();
		if(selectCarInfo.size()>0){
			try {
				Map<String,List> map= new HashMap<String,List>();
				List<String> list= new ArrayList<String>();
				List<String> list1= new ArrayList<String>();
				List<String> list2= new ArrayList<String>();
				List<String> list3= new ArrayList<String>();
				Map<String,Object> pb= new HashMap<String,Object>();
				 
				for(int i=0;i<selectCarInfo.size();i++){
					if(!list.contains(selectCarInfo.get(i).getBrand())){
						list.add(selectCarInfo.get(i).getBrand());
					}
					if(!list1.contains(selectCarInfo.get(i).getCarName())){
						list1.add(selectCarInfo.get(i).getCarName());
					}
					if(!list2.contains(selectCarInfo.get(i).getGearbox())){
						list2.add(selectCarInfo.get(i).getGearbox());
					}
					if(!list3.contains(selectCarInfo.get(i).getLevel())){
						list3.add(selectCarInfo.get(i).getLevel());
					}
				}
				pb.put("brand", carInfo.getBrand());
				pb.put("car_name", carInfo.getCarName());
				pb.put("brand", carInfo.getGearbox());
				pb.put("level", carInfo.getLevel());
				List<CarInfo> selectCarPage = carInfoService.selectCarPage(pb);
				map.put("brand", list);
				map.put("carName", list1);
				map.put("gearbox", list2);
				map.put("level", list3);
				
				re.setAttribute("pb", selectCarPage);
				re.setAttribute("map", map);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "/car/buy0";
	}
	
}
