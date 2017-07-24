package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vkicl.report.bean.WarehouseInwardDetailsBean;
import vkicl.vo.WarehouseOutwardTempVO;

public class WarehouseOutwardTempDaoImpl extends BaseDaoImpl {

	private Logger log = LoggerFactory.getLogger(WarehouseOutwardTempDaoImpl.class);

	public List<WarehouseOutwardTempVO> getWarehouseOutwardTempRecordsByWarehouseOutwardId(Integer warehouseOutwardId) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		StringBuffer q = new StringBuffer();
		String message = "";
		List<WarehouseOutwardTempVO> resultList = new ArrayList<WarehouseOutwardTempVO>();

		try {
			conn = getConnection();

			q.append(" SELECT * FROM warehouse_outward_temp where warehouse_outward_id = ? ");
			String query = q.toString();

			log.info("query = " + query);
			log.info("warehouseOutwardId = " + warehouseOutwardId);
			cs = conn.prepareCall(query);
			cs.setInt(1, warehouseOutwardId);

			rs = cs.executeQuery();
			if (null != rs) {

				while (rs.next()) {
					WarehouseOutwardTempVO w = new WarehouseOutwardTempVO();
					w.setOutward_temp_id(rs.getInt("outward_temp_id"));
					w.setDispatch_order_id(rs.getInt("dispatch_order_id"));
					w.setMill_name(rs.getString("mill_name"));
					w.setMake(rs.getString("make"));
					w.setGrade(rs.getString("grade"));
					w.setThickness(rs.getDouble("thickness"));
					w.setHeat_no(rs.getString("heat_no"));
					w.setPlate_no(rs.getString("plate_no"));
					w.setWidth(rs.getInt("width"));
					w.setLength(rs.getInt("length"));
					w.setReqd_qty(rs.getInt("reqd_qty"));
					w.setSect_wt(rs.getDouble("sect_wt"));
					w.setSect_ut(rs.getString("sect_ut"));
					w.setLocation(rs.getString("location"));
					w.setAvail_qty(rs.getInt("avail_qty"));
					w.setTaken_qty(rs.getInt("taken_qty"));
					w.setIsprocessed(rs.getBoolean("Isprocessed"));
					w.setCreate_ui(rs.getString("create_ui"));
					w.setUpdate_ui(rs.getString("update_ui"));
					w.setCreate_ts(rs.getString("create_ts"));
					w.setUpdate_ts(rs.getString("update_ts"));
					w.setDispatch_details_id(rs.getInt("dispatch_details_id"));
					w.setStock_Id(rs.getInt("stock_Id"));
					w.setWarehouse_outward_id(rs.getInt("warehouse_outward_id"));
					w.setActual_wt(rs.getDouble("actual_wt"));
					resultList.add(w);
				}
			}

		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}

		return resultList;
	}

	public void updateActualWeight(List<WarehouseOutwardTempVO> deliveredItems) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		StringBuffer q = new StringBuffer();

		try {
			conn = getConnection();

			q.append(" update warehouse_outward_temp set actual_wt = ? where outward_temp_id = ? ");
			String query = q.toString();

			log.info("query = " + query);
			cs = conn.prepareCall(query);

			for (WarehouseOutwardTempVO t : deliveredItems) {
				cs.setDouble(1, t.getActual_wt());
				cs.setInt(2, t.getOutward_temp_id());
				cs.executeUpdate();
			}

		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}

	}

	public void distributeActualWeightPerPlate(Integer warehouseOutwardId, Double actualWeight) {
		// Find the warehouse outward temp records related to warehouseOutwardId
		log.debug("Find the warehouse outward temp records related to warehouseOutwardId = " + warehouseOutwardId);
		List<WarehouseOutwardTempVO> outwardTempRecords = getWarehouseOutwardTempRecordsByWarehouseOutwardId(
				warehouseOutwardId);
		// Distribute the actualWeight among the records in proportion to their
		// section weight.

		distributeActualWeightAmongTheOutwardTempRecords(outwardTempRecords, actualWeight);

	}

	private void distributeActualWeightAmongTheOutwardTempRecords(List<WarehouseOutwardTempVO> outwardTempRecords,
			Double actualLorryWeight) {
		//Find the total of section weight
		Double totalSectionWeight = calculateTotalSectionWeight(outwardTempRecords);
		Double multiplicationFactor = actualLorryWeight / totalSectionWeight;
		
		for(WarehouseOutwardTempVO widb:outwardTempRecords){
			if(widb!=null && widb.getSect_wt()!=null){
				widb.setActual_wt(widb.getSect_wt()*multiplicationFactor);
			}
		}
		
		saveDistributedWeightInDatabase(outwardTempRecords);
		
		
	}

	private Double calculateTotalSectionWeight(List<WarehouseOutwardTempVO> inwardedRecords) {
		Double totalSectionWeight = 0d;
		for(WarehouseOutwardTempVO widb:inwardedRecords){
			if(widb!=null && widb.getSect_wt()!=null){
				totalSectionWeight = totalSectionWeight + widb.getSect_wt();
			}
		}
		return totalSectionWeight;
	}

	private void saveDistributedWeightInDatabase(List<WarehouseOutwardTempVO> outwardedRecords) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "Success";
		int count = 0;
		PreparedStatement statement = null;
		try {
			conn = getConnection();
			String sql = "update warehouse_outward_temp set actual_wt = ?, update_ui = ?, update_ts = NOW()  WHERE outward_temp_id = ? ";
			
			for(WarehouseOutwardTempVO widb : outwardedRecords){
				statement = conn.prepareStatement(sql);
				statement.setDouble(1, widb.getActual_wt());
				statement.setString(2, "some_admin_to_be_updated_later223143");
				statement.setInt(3, widb.getOutward_temp_id());

				statement.executeUpdate();
	
			}
			
			log.info("message = " + message);
			
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			log.error(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}

		
		
	}
}
