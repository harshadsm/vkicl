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
import vkicl.form.PortPurchaseOrderForm;
import vkicl.util.Constants;
import vkicl.util.Converter;
import vkicl.util.JqGridSearchParameterHolder;
import vkicl.util.JqGridSearchParameterHolder.Rule;
import vkicl.util.PropFileReader;
import vkicl.vo.PackingListItemVO;
import vkicl.vo.PortInwardDetailsVO;
import vkicl.vo.PortInwardRecordVO;
import vkicl.vo.PortOutwardPostDataContainerVO;
import vkicl.vo.PortPurchaseOrderPostDataContainerVO;
import vkicl.vo.PortPurchaseOrderVO;
import vkicl.vo.UserInfoVO;
import vkicl.vo.WarehouseInwardRecordVO;

public class PortPurchaseOrderDaoImpl extends BaseDaoImpl {
	private static Logger log = Logger.getLogger(PortPurchaseOrderDaoImpl.class);

	public List<PortInwardRecordVO> fetchPortInwardDetails(int pageNo, int pageSize, String orderByFieldName,
			String order, JqGridSearchParameterHolder searchParam) throws SQLException {
		List<PortInwardRecordVO> list = new ArrayList<PortInwardRecordVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";

		try {
			conn = getConnection();

			String sql = "  SELECT pin.port_inward_id ,pin.be_no ,pin.material_type ,pin.mill_name ,pin.material_make ,pin.material_grade ,pin.description ,"
					+ " pin.be_weight ,pin.be_wt_unit ,pis.vessel_date ,pis.vessel_name ,pis.vendor_name , count(pid.port_inward_id) as port_inward_detail_records_count , "
					+ " (SUM(pid.quantity)-SUM(ppli.ordered_quantity)-SUM(po.quantity) ) as cumulative_Bal_Pcs_At_Dock FROM port_inward pin "
					+ " INNER JOIN port_inward_shipment pis ON pin.port_inwd_shipment_id = pis.port_inwd_shipment_id "
					+ " inner JOIN port_inward_details pid ON pid.port_inward_id = pin.port_inward_id "
					+ " inner join ppo_line_items ppli on ppli.port_inward_details_id=pid.port_inward_detail_id "
					+ " inner join port_inward_outward_intersection pioi on pioi.port_inward_details_id=pid.port_inward_detail_id "
					+ " inner join port_outward po on po.port_out_id=pioi.port_outward_id "
					+ " group by pin.port_inward_id having cumulative_Bal_Pcs_At_Dock > 0 "
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

	public List<PortInwardDetailsVO> fetchPortInwardDetailsById(Integer portInwardId) {
		List<PortInwardDetailsVO> list = new ArrayList<PortInwardDetailsVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		try {
			conn = getConnection();

			String sql = "SELECT pin.port_inward_id,pin.be_no,pin.material_type,pin.mill_name,pin.material_make,pin.material_grade,"
					+ " pin.description,pin.be_weight,pin.be_wt_unit " + " FROM port_inward pin "
					+ " INNER JOIN port_inward_shipment pis ON pin.port_inwd_shipment_id = pis.port_inwd_shipment_id";
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

	public List<PackingListItemVO> fetchPPOLineItems(int portInwardId, int pageNo, int pageSize,
			String orderByFieldName, String order, JqGridSearchParameterHolder searchParam) throws SQLException {
		List<PackingListItemVO> list = new ArrayList<PackingListItemVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";

		try {
			conn = getConnection();

			String sql = "SELECT pis.vessel_date, pis.vessel_name, pis.vendor_name ,pid.port_inward_detail_id, pid.thickness,pid.width,pid.length,"
					+ " pid.quantity, pid.be_weight, (SUM(ppli.ordered_quantity)-SUM(po.quantity) ) as Bal_Pcs_At_Dock "
					+ " from port_inward_details pid left join port_inward pin on pid.port_inward_id=pin.port_inward_id "
					+ " left join port_inward_shipment pis on pin.port_inwd_shipment_id=pis.port_inwd_shipment_id"
					+ " inner join ppo_line_items ppli on ppli.port_inward_details_id=pid.port_inward_detail_id"
					+ " inner join port_inward_outward_intersection pioi on pioi.port_inward_details_id=pid.port_inward_detail_id "
					+ " inner join port_outward po on po.port_out_id=pioi.port_outward_id where pid.port_inward_id="
					+ portInwardId + " group by pid.port_inward_detail_id HAVING Bal_Pcs_At_Dock>0 "
					+ processSearchCriteria(searchParam) + " " + composeOrderByClause(orderByFieldName, order) + ";";
			query = sql;
			log.info("query = " + query);

			cs = conn.prepareCall(query);

			rs = cs.executeQuery();
			if (null != rs && rs.next()) {

				do {
					PackingListItemVO p = new PackingListItemVO();
					p.setPortInwardId(rs.getInt(1));
					// p.setBeNo(formatOutput(rs.getString(2)));
					p.setMaterialType(formatOutput(rs.getString(2)));
					p.setMillName(formatOutput(rs.getString(3)));
					p.setMake(formatOutput(rs.getString(4)));
					p.setGrade(formatOutput(rs.getString(5)));
					p.setDesc(formatOutput(rs.getString(6)));
					p.setVesselDate(Converter.dateToString(Converter.sqlDateToDate(rs.getDate(7))));
					p.setVesselName(rs.getString(8));
					p.setVendorName(rs.getString(9));
					p.setPortInwardDetailId(rs.getInt(10));
					p.setThickness(rs.getDouble(11));
					p.setWidth(rs.getInt(12));
					p.setLength(rs.getInt(13));
					p.setQuantity(rs.getInt(14));
					p.setActualWt(rs.getDouble(15));
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
			} else if (orderByFieldName.equalsIgnoreCase("port_inward_id")) {
				orderByClause = orderByClause + " pin.port_inward_id " + order + " ";
			}
		}
		return orderByClause;
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
			clause = "pis.vessel_date like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("vessel_date")) {
			clause = processDateClause(data);
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

	public Long addPortPurchaseOrderData(PortPurchaseOrderPostDataContainerVO vo, UserInfoVO userInfoVO)
			throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		Long savedRecordId = -1L;
		try {

			query = "INSERT INTO port_purchase_order "
					+ " (customer_name, broker_name, brokerage,brokerage_unit, rate,delivery_address, "
					+ " excise, tax, transport, payment_terms, total_quantity, comments, "
					+ " create_ui, update_ui, create_ts, update_ts) "
					+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

			log.info(query);

			conn = getConnection();
			cs = conn.prepareCall(query);

			cs.setString(1, vo.getCustName());
			cs.setString(2, vo.getBrokerName());
			cs.setDouble(3, vo.getBrokerage());
			cs.setString(4, vo.getBrokerageUnit());
			cs.setDouble(5, vo.getRate());
			cs.setString(6, vo.getDeliveryAddr());
			cs.setString(7, vo.getExcise());
			cs.setString(8, vo.getTax());
			cs.setString(9, vo.getTransport());
			cs.setString(10, vo.getPaymentTerms());
			cs.setInt(11, vo.getTotalQty());
			cs.setString(12, vo.getComments());
			cs.setString(13, userInfoVO.getUserName());
			cs.setString(14, userInfoVO.getUserName());
			cs.setString(15, getCurentTime());
			cs.setString(16, getCurentTime());

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

	public Long addPortPurchaseLineItemData(PortPurchaseOrderVO vo, Long portPurchaseOrderId, UserInfoVO userInfoVO)
			throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		Long savedRecordId = -1L;
		try {

			query = "INSERT INTO ppo_line_items "
					+ " (port_purchase_order_id, port_inward_details_id, ordered_quantity, "
					+ " create_ui, update_ui, create_ts, update_ts) " + " VALUES ( ?, ?, ?, ?, ?, ?, ?)";

			log.info(query);

			conn = getConnection();
			cs = conn.prepareCall(query);

			cs.setLong(1, portPurchaseOrderId);
			cs.setInt(2, vo.getPortInwardDetailId());
			cs.setInt(3, vo.getAvailableQuantity());

			cs.setString(4, userInfoVO.getUserName());
			cs.setString(5, userInfoVO.getUserName());
			cs.setString(6, getCurentTime());
			cs.setString(7, getCurentTime());

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

	public Long addPortPurchaseDeliveryData(PortPurchaseOrderForm portpurchaseform, Long portPurchaseOrderId,
			UserInfoVO userInfoVO) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		Long savedRecordId = -1L;
		try {

			query = "INSERT INTO delivery_notes "
					+ " (port_purchase_order_id,create_ui, update_ui, create_ts, update_ts) "
					+ " VALUES ( ?, ?, ?, ?, ?)";

			log.info(query);

			conn = getConnection();
			cs = conn.prepareCall(query);

			cs.setLong(1, portPurchaseOrderId);
			cs.setString(2, userInfoVO.getUserName());
			cs.setString(3, userInfoVO.getUserName());
			cs.setString(4, getCurentTime());
			cs.setString(5, getCurentTime());

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

	public Long addPortPurchaseDeliveryLineItemsData(PortPurchaseOrderForm portpurchaseform, Long portPurchaseOrderId,
			UserInfoVO userInfoVO) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		Long savedRecordId = -1L;
		try {

			query = "INSERT INTO delivery_note_line_items "
					+ " (ppo_line_items_id,delivered_quantity,delivery_note_id,create_ui, update_ui, create_ts, update_ts) "
					+ " VALUES ( ?,?,?, ?, ?, ?, ?)";

			log.info(query);

			conn = getConnection();
			cs = conn.prepareCall(query);

			cs.setLong(1, portPurchaseOrderId);
			cs.setString(2, userInfoVO.getUserName());
			cs.setString(3, userInfoVO.getUserName());
			cs.setString(4, getCurentTime());
			cs.setString(5, getCurentTime());

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

}
