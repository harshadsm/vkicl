package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import vkicl.util.Constants;
import vkicl.util.Converter;
import vkicl.vo.PortOutwardRecordVO;
import vkicl.vo.UserInfoVO;

public class PortOutwardDaoImpl extends BaseDaoImpl {

	private Logger logger = Logger.getLogger(PortOutwardDaoImpl.class);


	public Long savePortOutward(PortOutwardRecordVO vo, Long portOutwardShipmentId, UserInfoVO userInfo)  throws Exception {
		Long savedRecordId = -1L;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		
		try {
			java.util.Date vesselDate = Converter.stringToDate(vo.getVesselDate(), Constants.Apps.DATE_FORMAT);
			java.sql.Date vesseleDateSql = Converter.dateToSqlDate(vesselDate);
			Double actual_wt = -1d;
			String actual_wt_unit = "TON";
			Double section_wt = -1d;
			String section_wt_unit = "TON";
			
			String query = "INSERT INTO port_outward "
					+ " (port_out_shipment_id, vessel_name, vessel_date, "
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
			cs.setInt(6,vo.getLength());
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
			cs.setString(18,  vo.getMaterialType());
			
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
