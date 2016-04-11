package vkicl.services;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import vkicl.daoImpl.PortInwardDaoImpl;
import vkicl.vo.PortInwardRecordVO;

public class PortInwardService {

	private Logger logger = Logger.getLogger(PortInwardService.class);
	
	public PortInwardRecordVO getPortInwardById(String idString) {
		PortInwardRecordVO vo = null;
		PortInwardDaoImpl dao = new PortInwardDaoImpl();
		Integer id = Integer.parseInt(idString);
		try {
			vo = dao.fetchPortInwardById(id);
		} catch (SQLException e) {
			logger.error("Some error",e);
		}
		return vo;
	}
}
