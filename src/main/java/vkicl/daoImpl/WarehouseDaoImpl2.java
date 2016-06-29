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


	public Integer addWarehouseInwardData(WarehouseInwardRecordVO portOutwardRecordVO,Long warehouseShipmentId,
			UserInfoVO userInfoVO) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		Integer savedRecordId = -1;
		try {
			
			String vehicleDateStr = portOutwardRecordVO.getVehicleDate();
			java.sql.Date vehicleDate = Converter.dateToSqlDate(Converter.stringToDate(vehicleDateStr, Constants.Apps.DATE_FORMAT));
			String vehicleNumber = portOutwardRecordVO.getVehicleName();
			String millName = portOutwardRecordVO.getMillName();
		    String grade = portOutwardRecordVO.getGrade();
			Integer width = portOutwardRecordVO.getWidth();
			Double thickness = portOutwardRecordVO.getThickness();
			Double secWt = portOutwardRecordVO.getSecWt();
			Double qty = portOutwardRecordVO.getBalQty();
			String materialType = portOutwardRecordVO.getMaterialType();
			Integer length = portOutwardRecordVO.getLength();
			
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
			cs.setString(5, "");
			cs.setString(6, grade);
			cs.setInt(7, length);
			cs.setInt(8, width);
			cs.setDouble(9, thickness);
			cs.setDouble(10, 0d);
			cs.setString(11, "");
			cs.setDouble(12, 0d);
			cs.setString(13, "");
			cs.setDouble(14, qty);
			cs.setString(15, userInfoVO.getUserName());
			cs.setString(16, userInfoVO.getUserName());
			cs.setString(17, getCurentTime());
			cs.setString(18, getCurentTime());
			
			
			 savedRecordId = cs.executeUpdate();
			
			/*ResultSet result = cs.getGeneratedKeys();
			if(count > 0){
				result.next();
				savedRecordId = result.getLong(1);
				
			message = cs.getString(29);
			log.info("message = " + message);

			userInfoVO.setMessage(message);
			*/
			//}
		} catch (Exception e) {
			e.printStackTrace();
			//message = e.getMessage();
			//userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return savedRecordId;
		
	}
	
	
}