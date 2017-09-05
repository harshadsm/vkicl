package vkicl.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Shape;
import java.lang.System;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import vkicl.daoImpl.PortInwardOutwardIntersectionDaoImpl;
import vkicl.daoImpl.PortOutwardDaoImpl;
import vkicl.daoImpl.StockBalDaoImpl;
import vkicl.daoImpl.WarehouseDaoImpl2;
import vkicl.daoImpl.WarehouseShipmentDaoImpl;
import vkicl.form.WarehouseInwardForm;
import vkicl.vo.PortOutwardPostDataContainerVO;
import vkicl.vo.PortOutwardRecordVO;
import vkicl.vo.UserInfoVO;
import vkicl.vo.WarehouseInwardRecordVO;
import vkicl.services.geometry.*;
import vkicl.util.Constants;
import vkicl.util.Converter;
import vkicl.util.Utils;

public class WarehouseInwardService {

	private Logger logger = Logger.getLogger(WarehouseInwardService.class);

	/**
	 * Warehouse inward from port
	 * 
	 * @param postDataContainer
	 * @param request
	 * @param userInfo
	 * @throws Exception
	 */
	public void processWarehouseInwardEntries_fromPort(PortOutwardPostDataContainerVO postDataContainer,
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

			// Step 1: save warehouse_shipment
			warehouseShipmentId = warehouseShipmentDaoImpl.saveWarehouseShipment(plateEntries.get(0), userInfo);

			for (WarehouseInwardRecordVO warehouseInwardRecordVO : plateEntries) {

				// Step 2: Save warehouse_inward records
				warehouseInwardId = warehouseDaoImpl2.addWarehouseInwardData(warehouseInwardRecordVO,
						warehouseShipmentId, userInfo);

				// Step 3: Save warehouse_inward_details records
				warehouseDaoImpl2.addWarehouseInwardDetailData(warehouseInwardRecordVO, warehouseInwardId, userInfo);

				// Step 4: Save port_outward_warehouse_inward_intersection
				// records
				warehouseDaoImpl2.addIntersectionData(warehouseInwardRecordVO, warehouseInwardId, userInfo);

				// Step 5: Save stock_balance and Update stock
				stockBalId = warehouseDaoImpl2.addStockBalData(warehouseInwardRecordVO, userInfo, warehouseInwardId);

				// Step 6: Mark the Port Outward entry as received
				warehouseShipmentDaoImpl.updateWarehouseInwardFlag(warehouseInwardRecordVO, userInfo);

				// Step 7: Update plate shape
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

		// Step 8: Update actual weight of port_outward records
		PortOutwardDaoImpl portOutwardDaoImpl = new PortOutwardDaoImpl();
		portOutwardDaoImpl.updateActualWeightOfPortOutwardRecord(warehouseInwardRecordsToBeSaved);
	}

	public void processWarehouseInwardEntries_fromLocal(WarehouseInwardForm form, UserInfoVO userInfo)
			throws Exception {
		Long warehouseInwardId = -1L;
		Long stockBalId = -1L;

		List<WarehouseInwardRecordVO> plateEntries = toVoList(form);

		if (plateEntries != null && !plateEntries.isEmpty()) {

			java.sql.Date invoiceDate = Converter.stringToSqlDate(form.getInvoiceDate(), Constants.Apps.DATE_FORMAT_2);
			// Step 1: save warehouse_shipment
			WarehouseShipmentDaoImpl warehouseShipmentDaoImpl = new WarehouseShipmentDaoImpl();
			Long warehouseShipmentId = warehouseShipmentDaoImpl.saveWarehouseShipment(form.getVendorVehicleNumber(),
					plateEntries.get(0).getVehicleDate(), form.getVendorName(), 
					form.getInvoiceNo(), invoiceDate, userInfo);

			WarehouseDaoImpl2 warehouseDaoImpl2 = new WarehouseDaoImpl2();

			for (WarehouseInwardRecordVO warehouseInwardRecordVO : plateEntries) {
				// Step 2: Save warehouse_inward records
				warehouseInwardId = warehouseDaoImpl2.addWarehouseInwardData(warehouseInwardRecordVO,
						warehouseShipmentId, userInfo);

				// Step 3: Save warehouse_inward_details records
				warehouseDaoImpl2.addWarehouseInwardDetailData(warehouseInwardRecordVO, warehouseInwardId, userInfo);

				// Step 4: (This Step Not Applicable) Save
				// port_outward_warehouse_inward_intersection records
				// Step 5: Save stock_balance and Update stock
				stockBalId = warehouseDaoImpl2.addStockBalData(warehouseInwardRecordVO, userInfo, warehouseInwardId);

				// Step 6: (This Step Not Applicable) Mark the Port Outward
				// entry as
				// received
				// Step 7: Update plate shape
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

				// Step 8: (This Step Not Applicable) Update actual weight of
				// port_outward records
			}
		}
	}

	private List<WarehouseInwardRecordVO> toVoList(WarehouseInwardForm form) throws ParseException {
		String vendorVehicleDateStr = form.getVendorVehicleDate();
		Date vehicleDate = Converter.stringToDate(vendorVehicleDateStr, Constants.Apps.DATE_FORMAT_2);
		String convertedDate = Converter.dateToString(vehicleDate);
		List<WarehouseInwardRecordVO> voList = new ArrayList<WarehouseInwardRecordVO>();

		Double lorryActualWeight = form.getActWt();

		String[] plateNoArr = form.getPlateNo();
		String[] heatNoArr = form.getHeatNo();
		String[] locationArr = form.getWlocation();
		String[] remarkArr = form.getRemark();
		Double[] sectionWtArr = form.getSecWt();
		Double[] subSectionWeightArr = form.getSubSecWt();
		//Double sectionWeightTotal = calculateTotal(sectionWtArr);
		Double sectionWeightTotal = calculateTotal(subSectionWeightArr);
		
		String[] materialTypeArr = form.getMaterialType();
		String[] millNameArr = form.getMillName();
		String[] makeArr = form.getMake();
		String[] gradeArr = form.getGrade();
		Integer[] lengthArr = form.getLength();
		Integer[] widthArr = form.getWidth();
		Double[] thicknessArr = form.getThickness();
		Integer[] qtyArr = form.getQty();
		Integer[] subQtyArr = form.getSubQty();
		Double[] labelWeightArr = form.getLabelWt();
		
		if (thicknessArr != null && thicknessArr.length > 0) {

			int i = 0;
			for (int j = 0; j < thicknessArr.length; j++) {

				String millName = millNameArr[j];
				String make = makeArr[j];
				String grade = gradeArr[j];
				Integer length = lengthArr[j];
				Integer width = widthArr[j];
				Double thickness = thicknessArr[j];
				Integer qty = qtyArr[j];
				String materialType = materialTypeArr[j];
				Double sectionWt = sectionWtArr[j];
				Double labelWeight = labelWeightArr[j];

				if (plateNoArr != null && plateNoArr.length > 0) {
					int endIndex = i + qty;
					for (; i < endIndex; i++) {

						String plateNo = plateNoArr[i];
						String heatNo = heatNoArr[i];
						String location = locationArr[i];
						String remark = remarkArr[i];

						Integer subQty = subQtyArr[i];

						Double subSectionWeightPerPlate = subSectionWeightArr[i];
						WarehouseInwardRecordVO vo = new WarehouseInwardRecordVO();
						Double dividedPerPlateActualWeight = lorryActualWeight * subSectionWeightPerPlate / sectionWeightTotal;
						
						sectionWt = Utils.calculateSectionWeight(length, width, thickness.intValue(), subQty);
						
						
						vo.setActualWt(dividedPerPlateActualWeight);
						vo.setActualWt_unit("TON");
						vo.setMaterialType(materialType);
						vo.setMillName(millName);
						vo.setMake(make);
						vo.setGrade(grade);
						vo.setLength(length);
						vo.setWidth(width);
						vo.setThickness(thickness);
						vo.setBalQty(sectionWt);
						vo.setAvailableQuantity(subQty);
						vo.setPlateNo(plateNo);
						vo.setHeatNo(heatNo);
						vo.setLocation(location);

						vo.setRemark(remark);
						vo.setLabelWeight(labelWeight);
						
						vo.setVehicleDate(convertedDate);
						vo.setVehicleName(form.getVendorVehicleNumber());
						vo.setSecWt(sectionWt);

						voList.add(vo);

					}
				}
			}
		}
		return voList;
	}

	private Double calculateTotal(Double[] arr) {
		Double total = 0.0d;
		if (arr != null && arr.length > 0) {
			for (Double a : arr) {
				if (a != null) {
					total = total + a;
				}
			}
		}
		return total;
	}

}
