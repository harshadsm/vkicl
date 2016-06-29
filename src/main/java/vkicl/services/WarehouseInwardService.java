package vkicl.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import vkicl.daoImpl.PortInwardOutwardIntersectionDaoImpl;
import vkicl.daoImpl.PortOutwardDaoImpl;
import vkicl.daoImpl.WarehouseDaoImpl2;
import vkicl.daoImpl.WarehouseShipmentDaoImpl;
import vkicl.vo.PortOutwardPostDataContainerVO;
import vkicl.vo.PortOutwardRecordVO;
import vkicl.vo.UserInfoVO;
import vkicl.vo.WarehouseInwardRecordVO;

public class WarehouseInwardService {

	private Logger logger = Logger.getLogger(WarehouseInwardService.class);

	public void processWarehouseInwardEntries(PortOutwardPostDataContainerVO postDataContainer,
			HttpServletRequest request, UserInfoVO userInfo) throws Exception {
		Long warehouseShipmentId=-1L;
		String itemsToSaveJson = postDataContainer.getSelectedPortInventoryItemsJson();

		Gson gson = new Gson();

		List<WarehouseInwardRecordVO> warehouseInwardRecordsToBeSaved = gson.fromJson(itemsToSaveJson,
				new TypeToken<List<WarehouseInwardRecordVO>>() {
				}.getType());
		
		
		WarehouseShipmentDaoImpl warehouseShipmentDaoImpl = new WarehouseShipmentDaoImpl();
		
		Map<String, List<WarehouseInwardRecordVO>> map = new HashMap<String, List<WarehouseInwardRecordVO>>();
		for( WarehouseInwardRecordVO warehouseInwardRecordVO : warehouseInwardRecordsToBeSaved){
			String key = warehouseInwardRecordVO.getVehicleDate() + "_" + warehouseInwardRecordVO.getVehicleName();
			if(map.get(key) == null){
				map.put(key, new ArrayList<WarehouseInwardRecordVO>());
			}			
			map.get(key).add(warehouseInwardRecordVO);
		}
		
		WarehouseDaoImpl2 impl = new WarehouseDaoImpl2();
		
		for (Map.Entry<String, List<WarehouseInwardRecordVO>> entry : map.entrySet()) {
		    System.out.println("key=" + entry.getKey() + ", value=" + entry.getValue());
		    List<WarehouseInwardRecordVO> vehicleEntry = entry.getValue();
		    warehouseShipmentId = warehouseShipmentDaoImpl.saveWarehouseShipment(vehicleEntry.get(0), userInfo);
		    
		    for(WarehouseInwardRecordVO warehouseInwardRecordVO :vehicleEntry){
		    	impl.addWarehouseInwardData(warehouseInwardRecordVO, warehouseShipmentId, userInfo);
		    }
		}
	}
}
