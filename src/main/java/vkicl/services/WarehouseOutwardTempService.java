package vkicl.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vkicl.daoImpl.WarehouseOutwardTempDaoImpl;
import vkicl.vo.WarehouseOutwardTempVO;

public class WarehouseOutwardTempService {

	private Logger log = LoggerFactory.getLogger(WarehouseOutwardTempService.class);
	
	public List<WarehouseOutwardTempVO> getWarehouseOutwardTempRecordsByWarehouseOutwardId(Integer warehouseOutwardId) {
		WarehouseOutwardTempDaoImpl warehouseOutwardTempDaoImpl = new WarehouseOutwardTempDaoImpl();
		List<WarehouseOutwardTempVO> resultList = warehouseOutwardTempDaoImpl
				.getWarehouseOutwardTempRecordsByWarehouseOutwardId(warehouseOutwardId);

		
		log.debug("resultList = "+resultList.size());
		
		return resultList;
	}

	public void updateActualWeight(List<WarehouseOutwardTempVO> deliveredItems) {
		WarehouseOutwardTempDaoImpl warehouseOutwardTempDaoImpl = new WarehouseOutwardTempDaoImpl();
		
		warehouseOutwardTempDaoImpl.updateActualWeight(deliveredItems);
	}
}
