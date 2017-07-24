package vkicl.services;

import java.util.List;

import vkicl.daoImpl.PortOutwardDaoImpl;
import vkicl.report.bean.PortOutwardBean2;

public class PortOutwardDetailsService {

	public List<PortOutwardBean2> findPortOutwardDetailsByPortOutwardShipmentId(Integer portOutwardShipmentId) throws Exception{
		PortOutwardDaoImpl portOutwardDaoImpl = new PortOutwardDaoImpl();
		List<PortOutwardBean2> portOutwardDetailRecords = portOutwardDaoImpl.getByPortOutwardShipmentId(portOutwardShipmentId);
		return portOutwardDetailRecords;
	}
}
