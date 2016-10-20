package vkicl.services;

import java.util.List;

import vkicl.daoImpl.LocationDaoImpl;
import vkicl.vo.LocationDetailsVO;

public class LocationService {

	
	public List<LocationDetailsVO> getAllLocationsAsList(){
		LocationDaoImpl dao = new LocationDaoImpl();
		return dao.getAllLocationsAsList();
	}
}
