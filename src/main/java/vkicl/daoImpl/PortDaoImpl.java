package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import vkicl.form.PortInwardForm;
import vkicl.form.PortOutwardForm;
import vkicl.util.JqGridSearchParameterHolder;
import vkicl.util.PropFileReader;
import vkicl.vo.PortInwardRecordVO;
import vkicl.vo.UserInfoVO;

public class PortDaoImpl extends BaseDaoImpl {
	private static Logger log = Logger.getLogger(PortDaoImpl.class);
	static PropFileReader prop = PropFileReader.getInstance();

	public PortInwardForm addPortInwardData(PortInwardForm form,
			UserInfoVO userInfoVO) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		try {
			if ("".equalsIgnoreCase(form.getVendorName())) {
				form.clear();
				return form;
			}
			conn = getConnection();
			query = prop.get("sp.port.inward.insert");
			log.info("query = " + query);
			log.info("form = " + form);
			cs = conn.prepareCall(query);
			cs.setString(1, formatInput(form.getVendorName()));
			cs.setString(2, formatInput(form.getVesselName()));
			cs.setString(3, convertStringToDate(form.getVesselDate()));
			cs.setString(4, toString(form.getBeNo()));
			cs.setString(5, toString(form.getMaterialType()));
			cs.setString(6, toString(form.getMillName()));
			cs.setString(7, toString(form.getMake()));
			cs.setString(8, toString(form.getGrade()));
			cs.setString(9, toString(form.getDesc()));
			cs.setString(10, toString(form.getBeWt()));
			cs.setString(11, toString(form.getBeWtUnit()));
			cs.setInt(12, form.getBeNo().length);
			cs.setString(13, userInfoVO.getUserName());
			cs.registerOutParameter(14, java.sql.Types.VARCHAR);
			cs.executeUpdate();
			message = cs.getString(14);
			log.info("message = " + message);
			form.clear();
			userInfoVO.setMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return form;
	}

	public PortInwardForm fetchPortInwardDetails(PortInwardForm form,
			UserInfoVO userInfoVO) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		int count = 0;
		try {
			conn = getConnection();

			query = prop.get("sp.port.inward.detail.select");
			log.info("query = " + query);
			log.info("form = " + form);
			cs = conn.prepareCall(query);
			log.info("form = " + form);
			cs.setString(1, form.getVendorName());
			cs.setString(2, form.getVesselName());
			cs.setString(3, convertStringToDate(form.getVesselDate()));
			cs.registerOutParameter(4, java.sql.Types.INTEGER);
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			rs = cs.executeQuery();
			count = cs.getInt(4);
			message = cs.getString(5);
			log.info("message = " + message);
			if (null != message && message.equalsIgnoreCase("Success")
					&& null != rs && rs.next()) {
				int i = 0;
				Integer pis[] = new Integer[count];
				String beNo[] = new String[count];
				String materialType[] = new String[count];
				String millName[] = new String[count];
				String make[] = new String[count];
				String grade[] = new String[count];
				String desc[] = new String[count];
				Double beWt[] = new Double[count];
				String beWtUnit[] = new String[count];
				do {
					pis[i] = rs.getInt(1);
					beNo[i] = formatOutput(rs.getString(2));
					materialType[i] = formatOutput(rs.getString(3));
					millName[i] = formatOutput(rs.getString(4));
					make[i] = formatOutput(rs.getString(5));
					grade[i] = formatOutput(rs.getString(6));
					desc[i] = formatOutput(rs.getString(7));
					beWt[i] = rs.getDouble(8);
					beWtUnit[i] = formatOutput(rs.getString(9));
					i = i + 1;
				} while (rs.next());

				form.setPis(pis);
				form.setBeNo(beNo);
				form.setMaterialType(materialType);
				form.setMillName(millName);
				form.setMake(make);
				form.setGrade(grade);
				form.setDesc(desc);
				form.setBeWt(beWt);
				form.setBeWtUnit(beWtUnit);
			}

		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return form;
	}
	
	
	public List<PortInwardRecordVO> fetchPortInwardDetails_2(
			int pageNo,
			int pageSize, long total, String orderByFieldName, String order,JqGridSearchParameterHolder searchParam
			) throws SQLException {
		List<PortInwardRecordVO> list = new ArrayList<PortInwardRecordVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		int count = 0;
		try {
			conn = getConnection();
			String count_sql = " SELECT "
					+ " count(*) "
					+" FROM port_inward pin "
					+" INNER JOIN port_inward_shipment pis ON pin.port_inwd_shipment_id = pis.port_inwd_shipment_id "
					+" ORDER BY pis.vessel_date DESC; ";
			String sql = " SELECT "
					+ " pin.port_inward_id"
					+ " ,pin.be_no"
					+ " ,pin.material_type"
					+ " ,pin.mill_name"
					+ " ,pin.material_make"
					+ " ,pin.material_grade"
					+ " ,pin.description"
					+ " ,pin.be_weight"
					+ " ,pin.be_wt_unit"
					+ " ,pis.vessel_date "
					+" FROM port_inward pin "
					+" INNER JOIN port_inward_shipment pis ON pin.port_inwd_shipment_id = pis.port_inwd_shipment_id "
					+" ORDER BY pis.vessel_date DESC; ";
			query = sql;
			log.info("query = " + query);
			
			cs = conn.prepareCall(query);
			
			
			rs = cs.executeQuery();
			if (null != rs && rs.next()) {
				
				
				do {
					PortInwardRecordVO p = new PortInwardRecordVO();
					p.setId( rs.getInt(1));
					p.setBeNo(formatOutput(rs.getString(2)));
					p.setMaterialType(formatOutput(rs.getString(3)));
					p.setMillName(formatOutput(rs.getString(4)));
					p.setMake(formatOutput(rs.getString(5)));
					p.setGrade(formatOutput(rs.getString(6)));
					p.setDesc(formatOutput(rs.getString(7)));
					p.setBeWt(rs.getDouble(8));
					p.setBeWtUnit(formatOutput(rs.getString(9)));
					p.setBeWtUnit(formatOutput(rs.getString(9)));
					p.setVesselDate(new Date(rs.getDate(10).getTime()));
					list.add(p);
				} while (rs.next());

				
			}

		} catch (Exception e) {
			log.error("Some error",e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return list;
	}
	
	public Integer fetchPortInwardDetailsRecordCount() throws SQLException {
		List<PortInwardRecordVO> list = new ArrayList<PortInwardRecordVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		
		
		int count = 0;
		try {
			conn = getConnection();
			String count_sql = " SELECT "
					+ " count(*) "
					+" FROM port_inward pin "
					+" INNER JOIN port_inward_shipment pis ON pin.port_inwd_shipment_id = pis.port_inwd_shipment_id ";
					
			
			log.info("query = " + count_sql);
			
			cs = conn.prepareCall(count_sql);
			
			
			rs = cs.executeQuery();
			if (null != rs && rs.next()) {
				
				
				do {
					count = rs.getInt(1);
					
					log.debug("Row count === "+count);
				} while (rs.next());

				
			}

		} catch (Exception e) {
			log.error("Some error",e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return count;
	}

	public PortInwardForm addPortInwardDetailsData(PortInwardForm form,
			UserInfoVO userInfoVO) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		try {
			conn = getConnection();

			query = prop.get("sp.port.inward.detail.insert");
			log.info("query = " + query);
			log.info("form = " + form);
			cs = conn.prepareCall(query);
			cs.setString(1, toString(form.getSubPis()));
			cs.setString(2, toString(form.getLength()));
			cs.setString(3, toString(form.getWidth()));
			cs.setString(4, toString(form.getThickness()));
			cs.setString(5, toString(form.getActualWt()));
			cs.setString(6, toString(form.getActualWtUnit()));
			cs.setString(7, toString(form.getQty()));
			cs.setString(8, userInfoVO.getUserName());
			cs.registerOutParameter(9, java.sql.Types.VARCHAR);
			cs.executeUpdate();

			message = cs.getString(9);
			log.info("message = " + message);
			form.clear();
			userInfoVO.setMessage(message);
		} catch (Exception e) {

			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return form;
	}

	public PortOutwardForm addPortOutwardData(PortOutwardForm form,
			UserInfoVO userInfoVO) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		String warehouse = "", customer = "";
		try {
			if ("".equalsIgnoreCase(form.getDestinationName())) {
				form.clear();
				return form;
			}
			if (form.getDestination().equals("warehouse"))
				warehouse = form.getDestinationName();
			else
				customer = form.getDestinationName();

			conn = getConnection();
			query = prop.get("sp.port.outward.insert");
			log.info("query = " + query);
			log.info("form = " + form);
			cs = conn.prepareCall(query);
			cs.setString(1, formatInput(warehouse));
			cs.setString(2, formatInput(customer));
			cs.setString(3, formatInput(form.getVehicleNumber()));
			cs.setString(4, convertStringToDate(form.getVehicleDate()));
			cs.setString(5, toString(form.getVesselName()));
			cs.setString(6, toString(form.getVesselDate()));
			cs.setString(7, toString(form.getBeNo()));
			cs.setString(8, toString(form.getMaterialType()));
			cs.setString(9, toString(form.getGrade()));
			cs.setString(10, toString(form.getDesc()));
			cs.setString(11, toString(form.getLength()));
			cs.setString(12, toString(form.getWidth()));
			cs.setString(13, toString(form.getThickness()));
			cs.setString(14, toString(form.getActualWt()));
			cs.setString(15, toString(form.getActualWtUnit()));
			cs.setString(16, toString(form.getSecWt()));
			cs.setString(17, toString(form.getSecWtUnit()));
			cs.setString(18, toString(form.getQty()));
			cs.setInt(19, form.getBeNo().length);
			cs.setString(20, userInfoVO.getUserName());
			cs.registerOutParameter(21, java.sql.Types.VARCHAR);
			cs.execute();

			message = cs.getString(21);
			log.info("message = " + message);
			userInfoVO.setMessage(message);
			log.info("Query executed successfully");

			form.clear();
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return form;
	}
}
