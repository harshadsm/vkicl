package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import vkicl.form.PortInwardForm;
import vkicl.util.Converter;
import vkicl.vo.PortInwardRecordVO;
import vkicl.vo.UserInfoVO;

public class PortInwardDaoImpl extends BaseDaoImpl{

	private Logger log = Logger.getLogger(PortInwardDaoImpl.class);
	
	public PortInwardRecordVO fetchPortInwardById(int id) throws SQLException {
		PortInwardRecordVO vo = null;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		int count = 0;
		try {
			conn = getConnection();

			query = getQuery();
			log.info("query = " + query);
			cs = conn.prepareCall(query);
			cs.setInt(1, id);
			rs = cs.executeQuery();
			
			
			if ( null != rs && rs.next()) {
				
				do {
					int port_inward_id = rs.getInt(1);
					//int port_inwd_shipment_id = rs.getInt(2);
					String material_type = rs.getString(3);
					String mill_name = rs.getString(4);
					String material_make = rs.getString(5);
					String material_grade = rs.getString(6);
					String description = rs.getString(7);
					//java.sql.Date create_ts = rs.getDate(8);
					String vendor_name = rs.getString(9);
					String vessel_name = rs.getString(10);
					java.sql.Date vessel_date = rs.getDate(11);
					
					vo = new PortInwardRecordVO();
					vo.setId(port_inward_id);
					vo.setDesc(description);
					vo.setGrade(material_grade);
					vo.setMake(material_make);
					vo.setMaterialType(material_type);
					vo.setMillName(mill_name);
					vo.setVendorName(vendor_name);
					vo.setVesselDate(Converter.dateToString(Converter.sqlDateToDate(vessel_date)));
					vo.setVesselName(vessel_name);
					
					break; //WE EXPECT/WANT ONLY 1 record.
				} while (rs.next());

				
			}

		} catch (Exception e) {
			log.error("Some error",e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return vo;
	}

	private String getQuery() {
		String q = " select "
				+ " pi.port_inward_id"
				+ " ,pi.port_inwd_shipment_id,pi.material_type,pi.mill_name"
				+ " ,pi.material_make,pi.material_grade,pi.description,pi.create_ts"
				+ " ,pis.vendor_name,pis.vessel_name,pis.vessel_date "
				+ " from port_inward pi inner join port_inward_shipment pis "
				+ " on pi.port_inwd_shipment_id = pis.port_inwd_shipment_id "
				+ " where pi.port_inward_id = ? ";
		return q;
	}
}
