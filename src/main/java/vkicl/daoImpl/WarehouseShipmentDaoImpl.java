package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import vkicl.util.Constants;
import vkicl.util.Converter;
import vkicl.vo.PortOutwardPostDataContainerVO;
import vkicl.vo.UserInfoVO;
import vkicl.vo.WarehouseInwardRecordVO;

public class WarehouseShipmentDaoImpl extends BaseDaoImpl{

	private Logger logger = Logger.getLogger(WarehouseShipmentDaoImpl.class);

	public Long saveWarehouseShipment(WarehouseInwardRecordVO postDataContainer, UserInfoVO userInfo) throws Exception {
		Long savedRecordId = -1L;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		
		
		try {
			String query = "INSERT INTO warehouse_inward_shipment "
					+ " (vehicle_number, received_date,vendor_name,create_ui,update_ui,create_ts,update_ts) "
					+ " VALUES ( ?, ?, ?, ?, ?, ?, ?)";
						
			logger.info(query);
			
			conn = getConnection();
			cs = conn.prepareCall(query);
			
			cs.setString(1, postDataContainer.getVehicleName());
			
			String vehicleDateStr = postDataContainer.getVehicleDate();
			String vendorName = postDataContainer.getVendorName();
			java.sql.Date vehicleDate = Converter.dateToSqlDate(Converter.stringToDate(vehicleDateStr, Constants.Apps.DATE_FORMAT));
			
			cs.setDate(2, vehicleDate);
			cs.setString(3, vendorName);
			cs.setString(4, userInfo.getUserName());
			cs.setString(5, userInfo.getUserName());
			cs.setString(6, getCurentTime());
			cs.setString(7, getCurentTime());
			
			int count = cs.executeUpdate();
			
			ResultSet result = cs.getGeneratedKeys();
			if(count > 0){
				result.next();
				savedRecordId = result.getLong(1);
			}
			
		} catch (Exception e) {
			throw e;
		}finally {
			closeDatabaseResources(conn, rs, cs);
		}

		return savedRecordId;
	}
	
	public void updateWarehouseInwardFlag(WarehouseInwardRecordVO postDataContainer, UserInfoVO userInfo) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		Integer savedRecordId = -1;
		try {
		
			//java.sql.Date vehicleDate = Converter.dateToSqlDate(Converter.stringToDate(postDataContainer.getVehicleDate(), Constants.Apps.DATE_FORMAT));
			
			query = "UPDATE port_outward SET "
					+ " warehouse_inward_flag =1 WHERE port_out_id="+postDataContainer.getPortInwardId();
						
			logger.info(query);
			
			conn = getConnection();
			cs = conn.prepareCall(query);
			
			
			//cs.setLong(1, warehouseId);
			
			savedRecordId = cs.executeUpdate();
			

		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfo.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		//return savedRecordId;
		
	}

}
