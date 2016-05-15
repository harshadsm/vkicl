package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;

import org.apache.log4j.Logger;

import vkicl.util.Constants;
import vkicl.util.Converter;
import vkicl.vo.PortOutwardPostDataContainerVO;
import vkicl.vo.UserInfoVO;

public class PortOutwardShipmentDaoImpl extends BaseDaoImpl{

	private Logger logger = Logger.getLogger(PortOutwardShipmentDaoImpl.class);

	public Long savePortOutwardShipment(PortOutwardPostDataContainerVO postDataContainer, UserInfoVO userInfo) throws Exception {
		Long savedRecordId = -1L;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		
		String custName = "";
		String warehouseName = "";
		
		String destinationName = postDataContainer.getDestinationName();
		if(destinationName!=null && destinationName.equalsIgnoreCase("TALOJA")){
			warehouseName = destinationName;
		}else{
			custName = destinationName;
		}
		String vehicleDateStr = postDataContainer.getVehicleDate();
		java.sql.Date vehicleDate = Converter.dateToSqlDate(Converter.stringToDate(vehicleDateStr, Constants.Apps.DATE_FORMAT));
		String vehicleNumber = postDataContainer.getVehicleNumber();
		
		
		try {
			String query = "INSERT INTO port_outward_shipment "
					+ " (warehouse_name, customer_name, vehicle_number, "
					+ " vehicle_date, create_ui, update_ui, create_ts, update_ts) "
					+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
						
			logger.info(query);
			
			conn = getConnection();
			cs = conn.prepareCall(query);
			cs.setString(1, warehouseName);
			cs.setString(2, custName);
			cs.setString(3, vehicleNumber);
			cs.setDate(4, vehicleDate);
			cs.setString(5, userInfo.getUserName());
			cs.setString(6, userInfo.getUserName());
			cs.setString(7, getCurentTime());
			cs.setString(8, getCurentTime());
			
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
}
