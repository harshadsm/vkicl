package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.text.DateFormatter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import vkicl.form.PortInwardForm;
import vkicl.form.PortOutwardForm;
import vkicl.util.Constants;
import vkicl.util.Converter;
import vkicl.util.JqGridSearchParameterHolder;
import vkicl.util.JqGridSearchParameterHolder.Rule;
import vkicl.util.PropFileReader;
import vkicl.vo.PortInwardDetailsVO;
import vkicl.vo.PortInwardRecordVO;
import vkicl.vo.UserInfoVO;

public class PortDaoImpl extends BaseDaoImpl {
	private static Logger log = Logger.getLogger(PortDaoImpl.class);

	public Integer fetchCumulativeBalPcsAtDockCount() throws SQLException {
		List<PortInwardRecordVO> list = new ArrayList<PortInwardRecordVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		int count = 0;
		try {
			conn = getConnection();
			String count_sql = " select (pi.quantity-ppol.ordered_quantity-(po.quantity) as count from port_inward_details pi "
					+ " inner join ppo_line_items ppol on pi.port_inward_detail_id = ppol.port_inward_details_id "
					+ " inner join port_inward_outward_intersection pioi on pi.port_inward_id=pioi.port_inward_id "
					+ " inner join port_outward po on po.port_out_id=pioi.port_outward_id ";
			log.info("query = " + count_sql);

			cs = conn.prepareCall(count_sql);

			rs = cs.executeQuery();
			if (null != rs && rs.next()) {

				do {
					count = rs.getInt(1);

					log.debug("Row count === " + count);
				} while (rs.next());

			}

		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return count;
	}

	public PortInwardForm addPortInwardData(PortInwardForm form, UserInfoVO userInfoVO) throws SQLException {
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
			/**
			 * TODO: Ideally we need to remove BE No altogether. Here I am
			 * saving time by adding a dummy value 99999. Its a kludge.
			 */
			cs.setString(4, "DUMMY");
			cs.setString(5, toString(form.getMaterialType()));
			cs.setString(6, toString(form.getMillName()));
			cs.setString(7, toString(form.getMake()));
			cs.setString(8, toString(form.getGrade()));
			cs.setString(9, toString(form.getDesc()));
			cs.setString(10, toString(form.getBeWt()));
			cs.setString(11, toString(form.getBeWtUnit()));

			cs.setInt(12, form.getMaterialType().length);
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

	public PortInwardForm fetchPortInwardDetails(PortInwardForm form, UserInfoVO userInfoVO) throws SQLException {
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
			if (null != message && message.equalsIgnoreCase("Success") && null != rs && rs.next()) {
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

	public List<PortInwardRecordVO> fetchPortInwardDetails_2(int pageNo, int pageSize, long total,
			String orderByFieldName, String order, JqGridSearchParameterHolder searchParam) throws SQLException {
		List<PortInwardRecordVO> list = new ArrayList<PortInwardRecordVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";

		try {
			conn = getConnection();

			String sql = " SELECT " + " pin.port_inward_id" + " ,pin.be_no" + " ,pin.material_type" + " ,pin.mill_name"
					+ " ,pin.material_make" + " ,pin.material_grade" + " ,pin.description" + " ,pin.be_weight"
					+ " ,pin.be_wt_unit" + " ,pis.vessel_date " + " ,pis.vessel_name " + " ,pis.vendor_name "
					+ ", count(pid.port_inward_id) as port_inward_detail_records_count " + " FROM port_inward pin "
					+ " INNER JOIN port_inward_shipment pis ON pin.port_inwd_shipment_id = pis.port_inwd_shipment_id "
					+ " LEFT JOIN port_inward_details pid ON pid.port_inward_id = pin.port_inward_id group by pin.port_inward_id"
					+ processSearchCriteria(searchParam) + " " + composeOrderByClause(orderByFieldName, order) + ";";
			query = sql;
			log.info("query = " + query);

			cs = conn.prepareCall(query);

			rs = cs.executeQuery();
			if (null != rs && rs.next()) {

				do {
					PortInwardRecordVO p = new PortInwardRecordVO();
					p.setId(rs.getInt(1));
					p.setBeNo(formatOutput(rs.getString(2)));
					p.setMaterialType(formatOutput(rs.getString(3)));
					p.setMillName(formatOutput(rs.getString(4)));
					p.setMake(formatOutput(rs.getString(5)));
					p.setGrade(formatOutput(rs.getString(6)));
					p.setDesc(formatOutput(rs.getString(7)));
					p.setBeWt(rs.getDouble(8));
					p.setBeWtUnit(formatOutput(rs.getString(9)));
					p.setBeWtUnit(formatOutput(rs.getString(9)));
					p.setVesselDate(Converter.dateToString(Converter.sqlDateToDate(rs.getDate(10))));
					p.setVesselName(rs.getString(11));
					p.setVendorName(rs.getString(12));
					p.setCountOfPortInwardDetailRecords(rs.getInt(13));
					list.add(p);
				} while (rs.next());

			}

		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return list;
	}

	private String composeOrderByClause(String orderByFieldName, String order) {
		String orderByClause = "";
		if (orderByFieldName != null) {
			orderByClause = " ORDER BY ";
			if (orderByFieldName.equalsIgnoreCase("vessel_date")) {
				orderByClause = orderByClause + " pis.vessel_date " + order + " ";
			} else if (orderByFieldName.equalsIgnoreCase("id")) {
				orderByClause = orderByClause + " pin.port_inward_id " + order + " ";
			}
		}
		return orderByClause;
	}

	public Integer fetchPortInwardDetailsRecordCount(JqGridSearchParameterHolder searchParam) throws SQLException {
		List<PortInwardRecordVO> list = new ArrayList<PortInwardRecordVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		int count = 0;
		try {
			conn = getConnection();
			String count_sql = " SELECT " + " count(*) " + " FROM port_inward pin "
					+ " INNER JOIN port_inward_shipment pis ON pin.port_inwd_shipment_id = pis.port_inwd_shipment_id "
					+ processSearchCriteria(searchParam);

			log.info("query = " + count_sql);

			cs = conn.prepareCall(count_sql);

			rs = cs.executeQuery();
			if (null != rs && rs.next()) {

				do {
					count = rs.getInt(1);

					log.debug("Row count === " + count);
				} while (rs.next());

			}

		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return count;
	}

	private String processSearchCriteria(JqGridSearchParameterHolder searchParam) {
		String sqlClause = "";
		List<String> clauses = new ArrayList<String>();
		if (null != searchParam && null != searchParam.getRules() && !searchParam.getRules().isEmpty()) {
			for (JqGridSearchParameterHolder.Rule r : searchParam.getRules()) {
				String clause = processSearchRule(r);
				if (!StringUtils.isEmpty(clause)) {
					clauses.add(clause);
				}
			}
		}

		// Prepare the sqlClause
		sqlClause = prepareSqlClause(clauses);

		return sqlClause;
	}

	private String prepareSqlClause(List<String> clauses) {
		String c = "";
		int clausesCount = clauses.size();
		for (int i = 0; i < clausesCount; i++) {
			String s = clauses.get(i);
			if (i > 0) {
				s = " AND " + s;
			}
			c = c + " " + s;

		}
		if (!StringUtils.isEmpty(c)) {
			c = " HAVING " + c;
		}
		return c;
	}

	private String processSearchRule(Rule r) {
		String data = r.getData();
		String field = r.getField();
		String op = r.getOp();

		String clause = "";
		if (field != null && field.equalsIgnoreCase("vendor_name")) {
			clause = "pis.vendor_name like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("vessel_name")) {
			clause = "pis.vessel_name like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("vessel_date")) {
			clause = processDateClause(data);
		} else if(field != null && field.equalsIgnoreCase("materialType")){
			clause = "material_type like '%" + data + "%'";
		} else if(field != null && field.equalsIgnoreCase("grade")){
			clause = "material_grade like '%" + data + "%'";
		} else if(field != null && field.equalsIgnoreCase("millName")){
			clause = "mill_name like '%" + data + "%'";
		} else if(field != null && field.equalsIgnoreCase("make")){
			clause = "material_make like '%" + data + "%'";
		}

		return clause;
	}

	private String processDateClause(String data) {
		String clause = "";
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.Apps.DATE_FORMAT);
		SimpleDateFormat sdfSql = new SimpleDateFormat(Constants.Apps.DATE_FORMAT_SQL);
		try {
			Date date = sdf.parse(data);
			clause = "vessel_date = '" + sdfSql.format(date) + "'";

		} catch (Exception e) {
			log.error("some error", e);
		}
		return clause;
	}

	public PortInwardForm addPortInwardDetailsData(PortInwardForm form, UserInfoVO userInfoVO) throws SQLException {
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

	public PortOutwardForm addPortOutwardData(PortOutwardForm form, UserInfoVO userInfoVO) throws SQLException {
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

	/**
	 * //port_inward_id,length,width,thickness,be_weight,be_wt_unit,quantity,
	 * create_ui,update_ui,create_ts,update_ts) VALUES
	 * (?,?,?,?,?,?,?,?,?,NOW(),NOW());
	 * 
	 * @param vo
	 */
	public String addPortInwardDetailsData(PortInwardDetailsVO vo, UserInfoVO userVo) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		try {
			conn = getConnection();
			moveToDeleted(vo.getPort_inward_id(), userVo);
			query = prop.get("port.inward.detail.insert");
			log.info("query = " + query);
			log.info("form = " + vo);
			cs = conn.prepareCall(query);
			cs.setInt(1, vo.getPort_inward_id());
			cs.setInt(2, vo.getLength());
			cs.setInt(3, vo.getWidth());
			cs.setDouble(4, vo.getThickness());
			cs.setDouble(5, vo.getBe_weight());
			cs.setString(6, vo.getBe_wt_unit());
			cs.setInt(7, vo.getQuantity());
			cs.setString(8, vo.getCreate_ui());
			cs.setString(9, vo.getUpdate_ui());
			cs.setDate(10, Converter.dateToSqlDate(vo.getCreate_ts()));
			cs.setDate(11, Converter.dateToSqlDate(vo.getUpdate_ts()));

			cs.executeUpdate();

			log.info("message = " + message);

		} catch (Exception e) {

			e.printStackTrace();
			message = e.getMessage();

		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return message;

	}

	public void moveToDeleted(Integer portInwardId, UserInfoVO userVo) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		try {
			List<PortInwardDetailsVO> list = fetchPortInwardDetailsById(portInwardId);

			conn = getConnection();
			query = prop.get("port.inward.detail.deleted.insert");
			log.info("query = " + query);
			cs = conn.prepareCall(query);

			for (PortInwardDetailsVO vo : list) {
				cs.setInt(1, vo.getPort_inward_detail_id());
				cs.setInt(2, vo.getPort_inward_id());
				cs.setInt(3, vo.getLength());
				cs.setInt(4, vo.getWidth());
				cs.setDouble(5, vo.getThickness());
				cs.setDouble(6, vo.getBe_weight());
				cs.setString(7, vo.getBe_wt_unit());
				cs.setInt(8, vo.getQuantity());
				cs.setString(9, vo.getCreate_ui());
				cs.setString(10, vo.getUpdate_ui());
				cs.setDate(11, Converter.dateToSqlDate(vo.getCreate_ts()));
				cs.setDate(12, Converter.dateToSqlDate(vo.getUpdate_ts()));
				cs.setString(13, userVo.getUserName());
				cs.setDate(14, Converter.dateToSqlDate(new Date()));
				cs.addBatch();
			}

			cs.executeBatch();

		} catch (Exception e) {
			log.error("some error", e);

		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
	}

	public void deletePortInwardDetailsByPortInwardId(Integer portInwardId) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		try {
			conn = getConnection();
			query = prop.get("port.inward.detail.delete");
			log.info("query = " + query);
			cs = conn.prepareCall(query);

			cs.setInt(1, portInwardId);

			cs.executeUpdate();

		} catch (Exception e) {
			log.error("some error", e);

		} finally {
			closeDatabaseResources(conn, rs, cs);
		}

	}

	/**
	 * port.inward.detail.select
	 * 
	 * @param portInwardId
	 */
	public List<PortInwardDetailsVO> fetchPortInwardDetailsById(Integer portInwardId) {
		List<PortInwardDetailsVO> list = new ArrayList<PortInwardDetailsVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		try {
			conn = getConnection();

			query = prop.get("port.inward.detail.select");
			log.info("query = " + query);
			log.info("portInwardId = " + portInwardId);
			cs = conn.prepareCall(query);
			cs.setInt(1, portInwardId);

			rs = cs.executeQuery();
			if (rs != null) {
				while (rs.next()) {

					Integer port_inward_detail_id = rs.getInt("port_inward_detail_id");
					Integer port_inward_id = rs.getInt("port_inward_id");
					Integer length = rs.getInt("length");
					Integer width = rs.getInt("width");
					Double thickness = rs.getDouble("thickness");
					Double be_weight = rs.getDouble("be_weight");
					String be_wt_unit = rs.getString("be_wt_unit");
					Integer quantity = rs.getInt("quantity");
					String create_ui = rs.getString("create_ui");
					String update_ui = rs.getString("update_ui");
					Date create_ts = rs.getDate("create_ts");
					Date update_ts = rs.getDate("update_ts");
					Double section_weight = rs.getDouble("section_wt");

					PortInwardDetailsVO vo = new PortInwardDetailsVO();
					vo.setBe_weight(be_weight);
					vo.setBe_wt_unit(be_wt_unit);
					vo.setCreate_ts(create_ts);
					vo.setCreate_ui(create_ui);
					vo.setLength(length);
					vo.setPort_inward_detail_id(port_inward_detail_id);
					vo.setPort_inward_id(port_inward_id);
					vo.setQuantity(quantity);
					vo.setThickness(thickness);
					vo.setUpdate_ts(update_ts);
					vo.setUpdate_ui(update_ui);
					vo.setWidth(width);
					vo.setSection_wt(section_weight);

					list.add(vo);
				}
			}
			log.info("message = " + message);

		} catch (Exception e) {

			e.printStackTrace();
			message = e.getMessage();

		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return list;
	}
}
