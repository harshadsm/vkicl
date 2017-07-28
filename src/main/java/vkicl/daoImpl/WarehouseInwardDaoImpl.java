package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import vkicl.report.bean.WarehouseInwardDetailsBean;
import vkicl.vo.StockBalanceDetailsVO;

public class WarehouseInwardDaoImpl extends BaseDaoImpl {

	private Logger logger = Logger.getLogger(WarehouseInwardDaoImpl.class);
	
	public void distributeActualWeightAmongInwardedRecords(Integer portOutwardShipmentId, Double actualWeight) {
		// Find the warehouse inward records related to portOutwardShipmentId
		logger.debug("Find the warehouse inward records related to portOutwardShipmentId = "+portOutwardShipmentId);
		List<WarehouseInwardDetailsBean> inwardedRecords = findInwardedRecordsForPortOutwardShipmentId(portOutwardShipmentId);
		// Distribute the actualWeight among the records in proportion to their
		// section weight.
		
		distributeActualWeightAmongTheInwardedRecords(inwardedRecords, actualWeight);

	}

	private void distributeActualWeightAmongTheInwardedRecords(List<WarehouseInwardDetailsBean> inwardedRecords, Double actualLorryWeight) {
		//Find the total of section weight
		Double totalSectionWeight = calculateTotalSectionWeight(inwardedRecords);
		Double multiplicationFactor = actualLorryWeight / totalSectionWeight;
		
		for(WarehouseInwardDetailsBean widb:inwardedRecords){
			if(widb!=null && widb.getSection_wt()!=null){
				widb.setWeight(widb.getSection_wt()*multiplicationFactor);
			}
		}
		
		saveDistributedWeightInDatabase(inwardedRecords);
		
	}

	private void saveDistributedWeightInDatabase(List<WarehouseInwardDetailsBean> inwardedRecords) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "Success";
		int count = 0;
		PreparedStatement statement = null;
		try {
			conn = getConnection();
			String sql = "update warehouse_inward_details set weight = ?, update_ui = ?, update_ts = NOW()  WHERE warehouse_in_detail_id = ? ";
			
			for(WarehouseInwardDetailsBean widb : inwardedRecords){
				statement = conn.prepareStatement(sql);
				statement.setDouble(1, widb.getWeight());
				statement.setString(2, "some_admin_to_be_updated_later");
				statement.setInt(3, widb.getWarehouse_in_detail_id());

				statement.executeUpdate();
	
			}
			logger.info("message = " + message);
			
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			logger.error(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}

	}

	private Double calculateTotalSectionWeight(List<WarehouseInwardDetailsBean> inwardedRecords) {
		Double totalSectionWeight = 0d;
		for(WarehouseInwardDetailsBean widb:inwardedRecords){
			if(widb!=null && widb.getSection_wt()!=null){
				totalSectionWeight = totalSectionWeight + widb.getSection_wt();
			}
		}
		return totalSectionWeight;
	}

	private List<WarehouseInwardDetailsBean> findInwardedRecordsForPortOutwardShipmentId(
			Integer portOutwardShipmentId) {

		List<WarehouseInwardDetailsBean> list = new ArrayList<WarehouseInwardDetailsBean>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		try {
			conn = getConnection();

			query = composeQueryToFindWarehouseInwardRecordsByPortOutwardShipmentId(portOutwardShipmentId);

			logger.info("query = " + query);
			cs = conn.prepareCall(query);
			cs.setInt(1, portOutwardShipmentId);
			rs = cs.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					WarehouseInwardDetailsBean vo = new WarehouseInwardDetailsBean();
					
					vo.setWarehouse_in_detail_id(rs.getInt("warehouse_in_detail_id"));
					vo.setSection_wt(rs.getDouble("section_wt"));
					
					list.add(vo);
				}
			}
			logger.info("message = " + message);

		} catch (Exception e) {

			e.printStackTrace();
			message = e.getMessage();

		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return list;
	}

	public String composeQueryToFindWarehouseInwardRecordsByPortOutwardShipmentId(Integer portOutwardShipmentId) {
		StringBuffer q = new StringBuffer();
		q.append(" select ");
		q.append(" *  ");
		q.append(" from warehouse_inward_details where warehouse_inward_id in ");
		q.append(" ( ");
		q.append(" select warehouse_inward_id from port_outward_warehouse_inward_intersection where port_outward_id in ");
		q.append(" ( ");
		q.append(" select port_out_id from port_outward where port_out_shipment_id = ? ");
		q.append(" ) ");
		q.append(" ) ");

		return q.toString();
	}
}
