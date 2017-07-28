package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import vkicl.report.bean.PortOutwardBean2;
import vkicl.report.bean.WarehouseInwardDetailsBean;
import vkicl.util.Constants;
import vkicl.util.Converter;
import vkicl.vo.PortOutwardRecordVO;
import vkicl.vo.UserInfoVO;
import vkicl.vo.WarehouseInwardRecordVO;

public class PortOutwardDaoImpl extends BaseDaoImpl {

	private Logger logger = Logger.getLogger(PortOutwardDaoImpl.class);

	public Long savePortOutward(PortOutwardRecordVO vo, Long portOutwardShipmentId, UserInfoVO userInfo)
			throws Exception {
		Long savedRecordId = -1L;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		try {
			java.util.Date vesselDate = Converter.stringToDate(vo.getVesselDate(), Constants.Apps.DATE_FORMAT);
			java.sql.Date vesseleDateSql = Converter.dateToSqlDate(vesselDate);
			Double actual_wt = 0d;
			String actual_wt_unit = "TON";
			Double section_wt = -1d;
			String section_wt_unit = "TON";

			String query = "INSERT INTO port_outward " + " (port_out_shipment_id, vessel_name, vessel_date, "
					+ " grade, description, length, width, thickness, actual_wt, actual_wt_Unit, "
					+ " section_wt, section_wt_unit, quantity, create_ui, update_ui, "
					+ " create_ts, update_ts, material_type) "
					+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			logger.info(query);

			conn = getConnection();
			cs = conn.prepareCall(query);

			cs.setLong(1, portOutwardShipmentId);
			cs.setString(2, vo.getVesselName());
			cs.setDate(3, vesseleDateSql);
			cs.setString(4, vo.getGrade());
			cs.setString(5, vo.getDesc());
			cs.setInt(6, vo.getLength());
			cs.setInt(7, vo.getWidth());
			cs.setDouble(8, vo.getThickness());
			cs.setDouble(9, actual_wt);
			cs.setString(10, actual_wt_unit);
			cs.setDouble(11, section_wt);
			cs.setString(12, section_wt_unit);
			cs.setInt(13, vo.getOrderedQuantity());
			cs.setString(14, userInfo.getUserName());
			cs.setString(15, userInfo.getUserName());
			cs.setString(16, getCurentTime());
			cs.setString(17, getCurentTime());
			cs.setString(18, vo.getMaterialType());

			int count = cs.executeUpdate();

			ResultSet result = cs.getGeneratedKeys();
			if (count > 0) {
				result.next();
				savedRecordId = result.getLong(1);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}

		return savedRecordId;
	}

	public List<PortOutwardBean2> getByPortInwardDetailId(Integer portInwardDetailId) throws Exception {
		List<PortOutwardBean2> list = new ArrayList<PortOutwardBean2>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		try {

			String query = " select " + " port_out_id " + ", port_out_shipment_id " + ", vessel_name "
					+ ", vessel_Date " + ", be_no " + ", material_type " + ", grade " + ", description " + ", length "
					+ ", width " + ", thickness " + ", actual_wt " + ", actual_wt_Unit " + ", section_wt "
					+ ", section_wt_unit " + ", quantity " + ", create_ui " + ", update_ui " + ", create_ts "
					+ ", update_ts " + " from port_outward where port_out_id " + " in "
					+ " (select port_outward_id from port_inward_outward_intersection where port_inward_details_id = ?) "
					+ " AND quantity > 0 " + " ";

			logger.info(query);

			conn = getConnection();
			cs = conn.prepareCall(query);

			cs.setInt(1, portInwardDetailId);

			rs = cs.executeQuery();
			PortOutwardBean2 b = null;
			int c = 1;
			if (rs != null) {
				while (rs.next()) {
					c = 1;
					b = new PortOutwardBean2();
					Long port_out_id = rs.getLong(c++);
					Long port_out_shipment_id = rs.getLong(c++);
					String vessel_name = rs.getString(c++);
					Date vessel_Date = null;
					java.sql.Date d = rs.getDate(c++);
					if (d != null) {
						vessel_Date = Converter.sqlDateToDate(d);
					}

					String be_no = rs.getString(c++);
					String material_type = rs.getString(c++);
					String grade = rs.getString(c++);
					String description = rs.getString(c++);
					Integer length = rs.getInt(c++);
					Integer width = rs.getInt(c++);
					Double thickness = rs.getDouble(c++);
					Double actual_wt = rs.getDouble(c++);
					String actual_wt_Unit = rs.getString(c++);
					Double section_wt = rs.getDouble(c++);
					String section_wt_unit = rs.getString(c++);
					Integer quantity = rs.getInt(c++);
					String create_ui = rs.getString(c++);
					String update_ui = rs.getString(c++);
					Date create_ts = Converter.sqlDateToDate(rs.getDate(c++));
					Date update_ts = Converter.sqlDateToDate(rs.getDate(c++));

					b.setPort_out_id(port_out_id);
					b.setPort_out_shipment_id(port_out_shipment_id);
					b.setVessel_name(vessel_name);
					b.setVessel_Date(vessel_Date);
					b.setBe_no(be_no);
					b.setMaterial_type(material_type);
					b.setGrade(grade);
					b.setDescription(description);
					b.setLength(length);
					b.setWidth(width);
					b.setThickness(thickness);
					b.setActual_wt(actual_wt);
					b.setActual_wt_Unit(actual_wt_Unit);
					b.setSection_wt(section_wt);
					b.setSection_wt_unit(section_wt_unit);
					b.setQuantity(quantity);
					b.setCreate_ts(create_ts);
					b.setUpdate_ts(update_ts);
					b.setCreate_ui(create_ui);
					b.setUpdate_ui(update_ui);

					list.add(b);
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}

		return list;
	}

	public void updateActualWeightOfPortOutwardRecord(List<WarehouseInwardRecordVO> warehouseInwardRecordsToBeSaved)
			throws Exception {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		try {

			String query = "UPDATE port_outward set actual_wt = ? where port_out_id = ? ";

			logger.info(query);

			conn = getConnection();
			cs = conn.prepareCall(query);
			for (WarehouseInwardRecordVO vo : warehouseInwardRecordsToBeSaved) {
				Integer portOutwardId = vo.getPortOutwardId();
				Double actualWt = vo.getActualWt();

				cs.setDouble(1, actualWt);
				cs.setInt(2, portOutwardId);
				int count = cs.executeUpdate();
				logger.debug("Updated count = "+count);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}

	}

	public List<PortOutwardBean2> getByPortOutwardShipmentId(Integer portOutwardShipmentId) throws Exception {
		List<PortOutwardBean2> list = new ArrayList<PortOutwardBean2>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		try {

			String query = " select * from port_outward where port_out_shipment_id = ? ";

			logger.info(query);

			conn = getConnection();
			cs = conn.prepareCall(query);

			cs.setInt(1, portOutwardShipmentId);

			rs = cs.executeQuery();
			PortOutwardBean2 b = null;
			int c = 1;
			if (rs != null) {
				while (rs.next()) {
					c = 1;
					b = new PortOutwardBean2();
					Long port_out_id = rs.getLong(c++);
					Long port_out_shipment_id = rs.getLong(c++);
					String vessel_name = rs.getString(c++);
					Date vessel_Date = null;
					java.sql.Date d = rs.getDate(c++);
					if (d != null) {
						vessel_Date = Converter.sqlDateToDate(d);
					}

					String be_no = rs.getString(c++);
					String material_type = rs.getString(c++);
					String grade = rs.getString(c++);
					String description = rs.getString(c++);
					Integer length = rs.getInt(c++);
					Integer width = rs.getInt(c++);
					Double thickness = rs.getDouble(c++);
					Double actual_wt = rs.getDouble(c++);
					String actual_wt_Unit = rs.getString(c++);
					Double section_wt = rs.getDouble(c++);
					String section_wt_unit = rs.getString(c++);
					Integer quantity = rs.getInt(c++);
					String create_ui = rs.getString(c++);
					String update_ui = rs.getString(c++);
					Date create_ts = Converter.sqlDateToDate(rs.getDate(c++));
					Date update_ts = Converter.sqlDateToDate(rs.getDate(c++));

					b.setPort_out_id(port_out_id);
					b.setPort_out_shipment_id(port_out_shipment_id);
					b.setVessel_name(vessel_name);
					b.setVessel_Date(vessel_Date);
					b.setBe_no(be_no);
					b.setMaterial_type(material_type);
					b.setGrade(grade);
					b.setDescription(description);
					b.setLength(length);
					b.setWidth(width);
					b.setThickness(thickness);
					b.setActual_wt(actual_wt);
					b.setActual_wt_Unit(actual_wt_Unit);
					b.setSection_wt(section_wt);
					b.setSection_wt_unit(section_wt_unit);
					b.setQuantity(quantity);
					b.setCreate_ts(create_ts);
					b.setUpdate_ts(update_ts);
					b.setCreate_ui(create_ui);
					b.setUpdate_ui(update_ui);

					list.add(b);
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}

		return list;
	}

	public void distributeActualWeightAmongOutwardedRecords(Integer portOutwardShipmentId, Double actualWeight) {
		// Find the warehouse inward records related to portOutwardShipmentId
				logger.debug("Find the warehouse inward records related to portOutwardShipmentId = "+portOutwardShipmentId);
				List<PortOutwardBean2> inwardedRecords = findInwardedRecordsForPortOutwardShipmentId(portOutwardShipmentId);
				// Distribute the actualWeight among the records in proportion to their
				// section weight.
				
				distributeActualWeightAmongTheOutwardedRecords(inwardedRecords, actualWeight);
		
	}
	
	private void distributeActualWeightAmongTheOutwardedRecords(List<PortOutwardBean2> outwardedRecords, Double actualLorryWeight) {
		//Find the total of section weight
		Double totalSectionWeight = calculateTotalSectionWeight(outwardedRecords);
		Double multiplicationFactor = actualLorryWeight / totalSectionWeight;
		
		for(PortOutwardBean2 widb:outwardedRecords){
			if(widb!=null && widb.getSection_wt()!=null){
				widb.setActual_wt(widb.getSection_wt()*multiplicationFactor);
			}
		}
		
		saveDistributedWeightInDatabase(outwardedRecords);
		
	}
	
	private void saveDistributedWeightInDatabase(List<PortOutwardBean2> inwardedRecords) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "Success";
		int count = 0;
		PreparedStatement statement = null;
		try {
			conn = getConnection();
			String sql = "update port_outward set actual_wt = ?, update_ui = ?, update_ts = NOW()  WHERE port_out_id = ? ";
			
			for(PortOutwardBean2 widb : inwardedRecords){
				statement = conn.prepareStatement(sql);
				statement.setDouble(1, widb.getActual_wt());
				statement.setString(2, "some_admin_to_be_updated_later");
				statement.setLong(3, widb.getPort_out_id());

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
	
	private Double calculateTotalSectionWeight(List<PortOutwardBean2> outwardedRecords) {
		Double totalSectionWeight = 0d;
		for(PortOutwardBean2 pob:outwardedRecords){
			if(pob!=null && pob.getSection_wt()!=null){
				totalSectionWeight = totalSectionWeight + pob.getSection_wt();
			}
		}
		return totalSectionWeight;
	}
	
	
	private List<PortOutwardBean2> findInwardedRecordsForPortOutwardShipmentId(
			Integer portOutwardShipmentId) {

		List<PortOutwardBean2> list = new ArrayList<PortOutwardBean2>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		try {
			conn = getConnection();

			query = composeQueryToFindPortOutwardRecordsByPortOutwardShipmentId(portOutwardShipmentId);

			logger.info("query = " + query);
			cs = conn.prepareCall(query);
			cs.setInt(1, portOutwardShipmentId);
			rs = cs.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					PortOutwardBean2 vo = new PortOutwardBean2();
					
					vo.setPort_out_shipment_id(rs.getLong("port_out_shipment_id"));
					vo.setSection_wt(rs.getDouble("section_wt"));
					vo.setPort_out_id(rs.getLong("port_out_id"));
					
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
	
	
	public String composeQueryToFindPortOutwardRecordsByPortOutwardShipmentId(Integer portOutwardShipmentId) {
		StringBuffer q = new StringBuffer();
		
		q.append(" select * from port_outward where port_out_shipment_id = ? ");
		

		return q.toString();
	}

}
