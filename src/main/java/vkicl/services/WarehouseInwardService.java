package vkicl.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Shape;
import java.lang.System;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import vkicl.daoImpl.PortInwardOutwardIntersectionDaoImpl;
import vkicl.daoImpl.StockBalDaoImpl;
import vkicl.daoImpl.WarehouseDaoImpl2;
import vkicl.daoImpl.WarehouseShipmentDaoImpl;
import vkicl.vo.PortOutwardPostDataContainerVO;
import vkicl.vo.PortOutwardRecordVO;
import vkicl.vo.UserInfoVO;
import vkicl.vo.WarehouseInwardRecordVO;
import vkicl.services.geometry.*;

public class WarehouseInwardService {

	private Logger logger = Logger.getLogger(WarehouseInwardService.class);

	public void processWarehouseInwardEntries(PortOutwardPostDataContainerVO postDataContainer,
			HttpServletRequest request, UserInfoVO userInfo) throws Exception {
		Long warehouseShipmentId = -1L;
		Long warehouseInwardId = -1L;
		Long stockBalId = -1L;
		String itemsToSaveJson = postDataContainer.getSelectedPortInventoryItemsJson();

		Gson gson = new Gson();

		List<WarehouseInwardRecordVO> warehouseInwardRecordsToBeSaved = gson.fromJson(itemsToSaveJson,
				new TypeToken<List<WarehouseInwardRecordVO>>() {
				}.getType());

		WarehouseShipmentDaoImpl warehouseShipmentDaoImpl = new WarehouseShipmentDaoImpl();

		/**
		 * Group the records by vehicle date & vehicle name. This is because, at
		 * a time a vehicle can arrive only once in a day. That was the
		 * assumption made by the previous developer.
		 */
		Map<String, List<WarehouseInwardRecordVO>> map = new HashMap<String, List<WarehouseInwardRecordVO>>();
		for (WarehouseInwardRecordVO warehouseInwardRecordVO : warehouseInwardRecordsToBeSaved) {
			String key = warehouseInwardRecordVO.getVehicleDate() + "_" + warehouseInwardRecordVO.getVehicleName();
			if (map.get(key) == null) {
				map.put(key, new ArrayList<WarehouseInwardRecordVO>());
			}
			map.get(key).add(warehouseInwardRecordVO);
		}

		WarehouseDaoImpl2 warehouseDaoImpl2 = new WarehouseDaoImpl2();

		for (Map.Entry<String, List<WarehouseInwardRecordVO>> entry : map.entrySet()) {
			System.out.println("key=" + entry.getKey() + ", value=" + entry.getValue());
			List<WarehouseInwardRecordVO> plateEntries = entry.getValue();

			/**
			 * Save the shipment detatils in warehouse_inward_shipment table. We
			 * will use the 1st record in the group to get the submitted
			 * shipment details.
			 */
			warehouseShipmentId = warehouseShipmentDaoImpl.saveWarehouseShipment(plateEntries.get(0), userInfo);

			for (WarehouseInwardRecordVO warehouseInwardRecordVO : plateEntries) {

				warehouseInwardId = warehouseDaoImpl2.addWarehouseInwardData(warehouseInwardRecordVO,
						warehouseShipmentId, userInfo);

				warehouseDaoImpl2.addWarehouseInwardDetailData(warehouseInwardRecordVO, warehouseInwardId, userInfo);
				warehouseDaoImpl2.addIntersectionData(warehouseInwardRecordVO, warehouseInwardId, userInfo);

				// Update stock
				stockBalId = warehouseDaoImpl2.addStockBalData(warehouseInwardRecordVO, userInfo, warehouseInwardId);

				// Mark the Port Outward entry as received
				warehouseShipmentDaoImpl.updateWarehouseInwardFlag(warehouseInwardRecordVO, userInfo);

				// Update plate shape
				double orginx = 0;
				double orginy = 0;
				double length = warehouseInwardRecordVO.getLength();
				double width = warehouseInwardRecordVO.getWidth();

				double area = length * width;

				vkicl.services.geometry.GeometryServiceImpl goemetry = new vkicl.services.geometry.GeometryServiceImpl();
				Shape shapeObj = goemetry.toPolygon(orginx, orginy, length, width);

				String Sql = goemetry.toUpdateSql(shapeObj, stockBalId, area);

				StockBalDaoImpl up = new StockBalDaoImpl();
				up.updateStockBalanceShape(Sql);
			}
		}
	}
}
