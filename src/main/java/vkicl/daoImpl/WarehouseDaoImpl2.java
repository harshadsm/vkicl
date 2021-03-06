package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import vkicl.util.Constants;
import vkicl.util.Converter;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;
import vkicl.vo.WarehouseInwardRecordVO;

public class WarehouseDaoImpl2 extends BaseDaoImpl {
	static Logger log = Logger.getLogger(WarehouseDaoImpl2.class);
	static PropFileReader prop = PropFileReader.getInstance();

	public Long addWarehouseInwardData(WarehouseInwardRecordVO warehouseInwardRecordVO, Long warehouseShipmentId,
			UserInfoVO userInfoVO) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		Long savedRecordId = -1L;
		try {

			String vehicleDateStr = warehouseInwardRecordVO.getVehicleDate();
			java.sql.Date vehicleDate = Converter
					.dateToSqlDate(Converter.stringToDate(vehicleDateStr, Constants.Apps.DATE_FORMAT));
			String vehicleNumber = warehouseInwardRecordVO.getVehicleName();
			String millName = warehouseInwardRecordVO.getMillName();
			String make = warehouseInwardRecordVO.getMake();
			String grade = warehouseInwardRecordVO.getGrade();
			Integer width = warehouseInwardRecordVO.getWidth();
			Double thickness = warehouseInwardRecordVO.getThickness();
			Double secWt = warehouseInwardRecordVO.getBalQty();
			Integer qty = warehouseInwardRecordVO.getAvailableQuantity();
			String materialType = warehouseInwardRecordVO.getMaterialType();
			Integer length = warehouseInwardRecordVO.getLength();
			Double labelWeight = warehouseInwardRecordVO.getLabelWeight();

			query = "INSERT INTO warehouse_inward "
					+ " (warehouse_inship_id, be_no, material_type, mill_name, material_make, grade, "
					+ " length, width, thickness, section_wt, section_wt_unit, weight, "
					+ " weight_unit, quantity, create_ui, update_ui, create_ts, update_ts) "
					+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			log.info(query);

			conn = getConnection();
			cs = conn.prepareCall(query);

			cs.setLong(1, warehouseShipmentId);
			cs.setString(2, "");
			cs.setString(3, materialType);
			cs.setString(4, millName);
			cs.setString(5, make);
			cs.setString(6, grade);
			cs.setInt(7, length);
			cs.setInt(8, width);
			cs.setDouble(9, thickness);
			cs.setDouble(10, secWt);
			cs.setString(11, "");
			cs.setDouble(12, labelWeight==null?0d:labelWeight);
			cs.setString(13, "");
			cs.setInt(14, qty);
			cs.setString(15, userInfoVO.getUserName());
			cs.setString(16, userInfoVO.getUserName());
			cs.setString(17, getCurentTime());
			cs.setString(18, getCurentTime());

			// savedRecordId = cs.executeUpdate();

			int count = cs.executeUpdate();

			ResultSet result = cs.getGeneratedKeys();
			if (count > 0) {
				result.next();
				savedRecordId = result.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return savedRecordId;

	}

	public Integer addWarehouseInwardDetailData(WarehouseInwardRecordVO portOutwardRecordVO, Long warehouseId,
			UserInfoVO userInfoVO) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		Integer savedRecordId = -1;
		try {

			query = "INSERT INTO warehouse_inward_details "
					+ " (warehouse_inward_id,heat_no,plate_no,section_wt,section_wt_unit, "
					+ "  weight, weight_unit, quantity, remark, location, create_ui, "
					+ "  update_ui, create_ts, update_ts) " + " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			log.info(query);

			conn = getConnection();
			cs = conn.prepareCall(query);

			cs.setLong(1, warehouseId);
			cs.setString(2, portOutwardRecordVO.getHeatNo());
			cs.setString(3, portOutwardRecordVO.getPlateNo());
			cs.setDouble(4, portOutwardRecordVO.getBalQty());
			cs.setString(5, "TON");
			cs.setDouble(6, portOutwardRecordVO.getActualWt());
			cs.setString(7, portOutwardRecordVO.getActualWt_unit());
			cs.setInt(8, portOutwardRecordVO.getAvailableQuantity());
			cs.setString(9, portOutwardRecordVO.getRemark()==null?"":portOutwardRecordVO.getRemark());
			cs.setString(10, portOutwardRecordVO.getLocation());
			cs.setString(11, userInfoVO.getUserName());
			cs.setString(12, userInfoVO.getUserName());
			cs.setString(13, getCurentTime());
			cs.setString(14, getCurentTime());

			savedRecordId = cs.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return savedRecordId;

	}

	public long addStockBalData(WarehouseInwardRecordVO portOutwardRecordVO, UserInfoVO userInfoVO, Long warehouseInwardId)
			throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		Long savedRecordId = -1L;

		try {

			query = "INSERT INTO stock_balance " + " (mill_name,material_make,heat_no,plate_no, "
					+ "  material_type, grade, length, width, thickness, quantity, "
					+ "  location,is_delete,is_reserved, is_modified, create_ui,update_ui,create_ts, update_ts,plate_area, warehouse_inward_id ) "
					+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			log.info(query);
			conn = getConnection();
			cs = conn.prepareCall(query);

			cs.setString(1, portOutwardRecordVO.getMillName());
			cs.setString(2, portOutwardRecordVO.getMake());
			cs.setString(3, portOutwardRecordVO.getHeatNo());
			cs.setString(4, portOutwardRecordVO.getPlateNo());
			cs.setString(5, portOutwardRecordVO.getMaterialType());
			cs.setString(6, portOutwardRecordVO.getGrade());
			cs.setInt(7, portOutwardRecordVO.getLength());
			cs.setInt(8, portOutwardRecordVO.getWidth());
			cs.setDouble(9, portOutwardRecordVO.getThickness());
			cs.setInt(10, portOutwardRecordVO.getAvailableQuantity());
			cs.setString(11, portOutwardRecordVO.getLocation());
			cs.setString(12, "");
			cs.setString(13, "");
			cs.setString(14, "");
			cs.setString(15, userInfoVO.getUserName());
			cs.setString(16, userInfoVO.getUserName());
			cs.setString(17, getCurentTime());
			cs.setString(18, getCurentTime());
			cs.setDouble(19, (portOutwardRecordVO.getLength() * portOutwardRecordVO.getWidth()));
			cs.setLong(20, warehouseInwardId);

			int count = cs.executeUpdate();

			ResultSet result = cs.getGeneratedKeys();
			if (count > 0) {
				result.next();
				savedRecordId = result.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return savedRecordId;

	}

	public void addIntersectionData(WarehouseInwardRecordVO portOutwardRecordVO, Long warehouseId,
			UserInfoVO userInfoVO) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		Integer savedRecordId = -1;
		try {

			query = "INSERT INTO port_outward_warehouse_inward_intersection "
					+ " (port_inward_id,port_outward_id,warehouse_inward_id) " + " VALUES ( ?, ?, ?)";

			log.info(query);

			conn = getConnection();
			cs = conn.prepareCall(query);

			cs.setInt(1, portOutwardRecordVO.getPortInwardId());
			cs.setInt(2, portOutwardRecordVO.getPortOutwardId());
			cs.setLong(3, warehouseId);

			cs.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}

	}

}