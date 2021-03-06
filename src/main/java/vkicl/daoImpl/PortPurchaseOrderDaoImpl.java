package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import vkicl.form.PortPurchaseDeliveryNoteForm;
import vkicl.form.PortPurchaseOrderDeliveryForm;
import vkicl.form.PortStockReportForm;
import vkicl.report.bean.PortPurchaseOrderDeliveryBean;
import vkicl.report.bean.PortStockBean;
import vkicl.util.Constants;
import vkicl.util.Converter;
import vkicl.util.JqGridSearchParameterHolder;
import vkicl.util.JqGridSearchParameterHolder.Rule;
import vkicl.vo.PortInwardDetailsVO;
import vkicl.vo.PortInwardRecordVO;
import vkicl.vo.PortInwardRecordVOForPPO;
import vkicl.vo.PortPurchaseOrderLineItemVO;
import vkicl.vo.PortPurchaseOrderPostDataContainerVO;
import vkicl.vo.PortPurchaseOrderVO;
import vkicl.vo.UserInfoVO;

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

	public List<PortInwardRecordVOForPPO> fetchPortInwardDetails_harshad(int pageNo, int pageSize,
			String orderByFieldName, String order, JqGridSearchParameterHolder searchParam, Integer totalRecordsCount)
			throws SQLException {
		List<PortInwardRecordVOForPPO> list = new ArrayList<PortInwardRecordVOForPPO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";

		try {
			conn = getConnection();

			query = getQueryOfPortInwardRecordsWithCumulativeBalGreaterThan0(pageNo, pageSize, orderByFieldName, order,
					searchParam, totalRecordsCount);
			log.info("query = " + query);

			cs = conn.prepareCall(query);

			rs = cs.executeQuery();
			if (null != rs && rs.next()) {

				do {
					PortInwardRecordVOForPPO p = new PortInwardRecordVOForPPO();
					p.setPortInwardId(rs.getInt("port_inward_id"));
					p.setCreateTs(rs.getString("create_ts"));
					p.setVesselDate(rs.getString("vessel_date"));
					p.setVesselName(rs.getString("vessel_name"));
					p.setVendorName(rs.getString("vendor_name"));
					p.setMaterialType(rs.getString("material_type"));
					p.setMillName(rs.getString("mill_name"));
					p.setMake(rs.getString("material_make"));
					p.setGrade(rs.getString("material_grade"));
					p.setDescription(rs.getString("description"));
					p.setPortInwardRecordTotalQuantity(rs.getInt("port_inward_record_total_quantity"));
					p.setTotalPpoQuantityOrderedAgainstThatPortInwardRecord(
							rs.getInt("total_ppo_quantity_ordered_against_that_port_inward_record"));
					p.setTotalQuantityDeliveredToTaloja(rs.getInt("total_quantity_delivered_to_taloja"));
					p.setCumulativeBalPcsAtDock(rs.getInt("cumulative_bal_pcs_at_dock"));

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

	private String composeOrderByClause_2(String orderByFieldName, String order) {
		StringBuffer orderByClause = new StringBuffer();
		if (orderByFieldName != null) {
			orderByClause.append(" ORDER BY ").append(orderByFieldName).append(" ").append(order);
			// if (orderByFieldName.equalsIgnoreCase("vessel_date")) {
			// orderByClause = orderByClause + " pis.vessel_date " + order + "
			// ";
			// } else if (orderByFieldName.equalsIgnoreCase("port_inward_id")) {
			// orderByClause = orderByClause + " pin.port_inward_id " + order +
			// " ";
			// }

		}
		return orderByClause.toString();
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
			throws SQLException, ParseException {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		Long savedRecordId = -1L;
		try{

			query = "INSERT INTO port_purchase_order "
					+ " (customer_name, broker_name, brokerage,brokerage_unit, rate,delivery_address, "
					+ " excise, tax, transport, payment_terms, total_quantity, comments, "
					+ " create_ui, update_ui, create_ts, update_ts, vehicle_no, vehicle_date, transporter_name, transport_rate, transport_rate_unit ) "
					+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
			cs.setString(17, vo.getVehicleNo());
			cs.setDate(18, convertStringToDate(vo.getVehicleDate(), "dd/MM/yy"));
			cs.setString(19, vo.getTransporterName());
			cs.setDouble(20, vo.getTransportRate());
			cs.setString(21, vo.getTransportRateUnit());
			int count = cs.executeUpdate();

			ResultSet result = cs.getGeneratedKeys();
			if (count > 0) {
				result.next();
				savedRecordId = result.getLong(1);
			}
		
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return savedRecordId;

	}

	public Long addPortPurchaseLineItemData(PortPurchaseOrderLineItemVO vo, Long portPurchaseOrderId,
			UserInfoVO userInfoVO) throws SQLException {
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
			cs.setInt(3, vo.getOrderedQuantity());

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

	



	public Integer getCountOfPortInwardRecordsWithCumulativeBalGreaterThan0(JqGridSearchParameterHolder searchParam) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		Integer count = null;

		try {

			query = getQueryCountOfPortInwardRecordsWithCumulativeBalGreaterThan0(searchParam);

			log.info(query);

			conn = getConnection();
			cs = conn.prepareCall(query);

			ResultSet result = cs.executeQuery();

			while (result.next()) {
				count = result.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();

		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return count;
	}

	public String getQueryCountOfPortInwardRecordsWithCumulativeBalGreaterThan0(
			JqGridSearchParameterHolder searchParam) {
		StringBuffer q = new StringBuffer();
		q.append(" select count(*) from (");
		q.append(" select");
		q.append(" one.port_inward_id,");
		q.append(" one.create_ts,");
		q.append(" one.vessel_name,");
		q.append(" one.vessel_date,");
		q.append(" one.vendor_name,");
		q.append(" one.material_type,");
		q.append(" one.mill_name,");
		q.append(" one.material_make,");
		q.append(" one.material_grade,");
		q.append(" one.description,");
		q.append(" ifnull(one.port_inward_record_total_quantity,0) port_inward_record_total_quantity,");
		q.append(
				" ifnull(two.total_ppo_raised_against_that_port_inward_record,0) total_ppo_quantity_ordered_against_that_port_inward_record,");
		q.append(" ifnull(three.total_quantity_delivered_to_taloja,0) total_quantity_delivered_to_taloja,");
		q.append(" ifnull(port_inward_record_total_quantity,0)");
		q.append("  - ifnull(total_ppo_raised_against_that_port_inward_record,0)");
		q.append("  - ifnull(total_quantity_delivered_to_taloja,0)");
		q.append(" cumulative_bal_pcs_at_dock");
		q.append("  from (");
		q.append(" select pin.port_inward_id");
		q.append(" ,pin.create_ts, pis.vessel_name,  pis.vessel_date, pis.vendor_name");
		q.append(" ,pin.material_type, pin.mill_name, pin.material_make, pin.material_grade, pin.description,");
		q.append(" ifnull(sum(pid.quantity),0)");
		q.append(" port_inward_record_total_quantity");
		q.append(" from port_inward pin");
		q.append(" left join port_inward_shipment pis on pin.port_inwd_shipment_id = pis.port_inwd_shipment_id");
		q.append(" left join port_inward_details pid on pid.port_inward_id = pin.port_inward_id");
		q.append(" group by pin.port_inward_id");
		q.append(" ) one");
		q.append(" left join");
		q.append(" ");
		q.append(" (");
		q.append(" select");
		q.append("  pin.port_inward_id,");
		q.append(" ifnull(sum(ppoli.ordered_quantity),0) total_ppo_raised_against_that_port_inward_record");
		q.append(" from port_inward pin");
		q.append(" left join port_inward_details pid on pid.port_inward_id = pin.port_inward_id");
		q.append(" left join ppo_line_items ppoli on ppoli.port_inward_details_id = pid.port_inward_detail_id");
		q.append(" group by pin.port_inward_id");
		q.append(" ) two");
		q.append(" on one.port_inward_id = two.port_inward_id");
		q.append(" ");
		q.append(" left join");
		q.append(" (");
		q.append(" ");
		q.append(" select pin.port_inward_id");
		q.append(" , ifnull(sum(po.quantity),0) total_quantity_delivered_to_taloja");
		q.append("  from port_inward pin");
		q.append(" left join port_inward_outward_intersection pioi on pioi.port_inward_id = pin.port_inward_id");
		q.append(" left join port_outward po on po.port_out_id = pioi.port_outward_id");
		q.append("     group by pin.port_inward_id");
		q.append(" ) three");
		q.append(" on one.port_inward_id = three.port_inward_id");
		q.append(" ) a");
		q.append(" where a.cumulative_bal_pcs_at_dock > 0");
		// q.append(" order by port_inward_id desc");
		// q.append(" limit 0,2;");

		return q.toString();
	}

	public String getQueryOfPortInwardRecordsWithCumulativeBalGreaterThan0(int pageNo, int pageSize,
			String orderByFieldName, String order, JqGridSearchParameterHolder searchParam, Integer totalRecordsCount) {

		StringBuffer q = new StringBuffer();
		q.append(" select * from (");
		q.append(" select");
		q.append(" one.port_inward_id,");
		q.append(" one.create_ts,");
		q.append(" one.vessel_name,");
		q.append(" one.vessel_date,");
		q.append(" one.vendor_name,");
		q.append(" one.material_type,");
		q.append(" one.mill_name,");
		q.append(" one.material_make,");
		q.append(" one.material_grade,");
		q.append(" one.description,");
		q.append(" ifnull(one.port_inward_record_total_quantity,0) port_inward_record_total_quantity,");
		q.append(
				" ifnull(two.total_ppo_raised_against_that_port_inward_record,0) total_ppo_quantity_ordered_against_that_port_inward_record,");
		q.append(" ifnull(three.total_quantity_delivered_to_taloja,0) total_quantity_delivered_to_taloja,");
		q.append(" ifnull(port_inward_record_total_quantity,0)");
		q.append("  - ifnull(total_ppo_raised_against_that_port_inward_record,0)");
		q.append("  - ifnull(total_quantity_delivered_to_taloja,0)");
		q.append(" cumulative_bal_pcs_at_dock");
		q.append("  from (");
		q.append(" select pin.port_inward_id");
		q.append(" ,pin.create_ts, pis.vessel_name,  pis.vessel_date, pis.vendor_name");
		q.append(" ,pin.material_type, pin.mill_name, pin.material_make, pin.material_grade, pin.description,");
		q.append(" ifnull(sum(pid.quantity),0)");
		q.append(" port_inward_record_total_quantity");
		q.append(" from port_inward pin");
		q.append(" left join port_inward_shipment pis on pin.port_inwd_shipment_id = pis.port_inwd_shipment_id");
		q.append(" left join port_inward_details pid on pid.port_inward_id = pin.port_inward_id");
		q.append(" group by pin.port_inward_id");
		q.append(" ) one");
		q.append(" left join");
		q.append(" ");
		q.append(" (");
		q.append(" select");
		q.append("  pin.port_inward_id,");
		q.append(" ifnull(sum(ppoli.ordered_quantity),0) total_ppo_raised_against_that_port_inward_record");
		q.append(" from port_inward pin");
		q.append(" left join port_inward_details pid on pid.port_inward_id = pin.port_inward_id");
		q.append(" left join ppo_line_items ppoli on ppoli.port_inward_details_id = pid.port_inward_detail_id");
		q.append(" group by pin.port_inward_id");
		q.append(" ) two");
		q.append(" on one.port_inward_id = two.port_inward_id");
		q.append(" ");
		q.append(" left join");
		q.append(" (");
		q.append(" ");
		q.append(" select pin.port_inward_id");
		q.append(" , ifnull(sum(po.quantity),0) total_quantity_delivered_to_taloja");
		q.append("  from port_inward pin");
		q.append(" left join port_inward_outward_intersection pioi on pioi.port_inward_id = pin.port_inward_id");
		q.append(" left join port_outward po on po.port_out_id = pioi.port_outward_id");
		q.append("     group by pin.port_inward_id");
		q.append(" ) three");
		q.append(" on one.port_inward_id = three.port_inward_id");
		q.append(" ) a");
		q.append(" where a.cumulative_bal_pcs_at_dock > 0");
		String searchCriteria = processSearchCriteria(searchParam);
		log.info(searchCriteria);
		String orderByClause = composeOrderByClause_2(orderByFieldName, order);
		log.info(orderByClause);

		q.append("  ").append(searchCriteria);
		q.append("  ").append(orderByClause);

		int pageCount = totalRecordsCount / pageSize;

		if (pageNo > pageCount) {
			pageNo = pageCount;
		}

		int start = pageSize * pageNo;

		q.append(" limit ").append(start).append(", ").append(pageSize);

		return q.toString();
	}

	public PortPurchaseOrderDeliveryForm fetchPendingPPO(PortPurchaseOrderDeliveryForm form, UserInfoVO userInfoVO)
			throws SQLException {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		ArrayList<PortPurchaseOrderDeliveryBean> reportList = null;
		try {
			conn = getConnection();

			StringBuffer q = new StringBuffer();
			q.append(" select pending_ppo.* from (");
			q.append(" select ");
			q.append(" ppo_outer.port_purchase_order_id,");
			q.append(" ppo_outer.create_ts,");
			q.append(" ppo_outer.customer_name,");
			q.append(" ppo_outer.broker_name,");
			q.append(" ppo_outer.delivery_address, ppo_outer.total_quantity, ");
			q.append(" ppo_outer.total_ordered_quantity,");
			q.append(" ifnull(delivered.delivered_quantity,0) delivered_quantity,");
			q.append(" ppo_outer.total_ordered_quantity-");
			q.append(
					" ifnull(delivered.delivered_quantity,0) pending_quantity, ppo_outer.material_grade,ppo_outer.vessel_name, ");
			q.append(" ppo_outer.is_deleted ");
			q.append(" from (");
			q.append(" select ");
			q.append(" ppo.port_purchase_order_id,");
			q.append(" ppo.create_ts,");
			q.append(" ppo.customer_name,");
			q.append(" ppo.broker_name,");
			q.append(" ppo.delivery_address, ppo.total_quantity, ");
			q.append(" ifnull(sum(pli.ordered_quantity),0) total_ordered_quantity, pi.material_grade, pis.vessel_name, ");
			q.append(" ppo.is_deleted ");
			q.append(" from port_purchase_order ppo");
			q.append(" left join ppo_line_items pli on ppo.port_purchase_order_id = pli.port_purchase_order_id");
			q.append(" left join port_inward_details pid on pid.port_inward_detail_id=pli.port_inward_details_id");
			q.append("	     left join port_inward pi on pi.port_inward_id=pid.port_inward_id");
			q.append("	     left join port_inward_shipment pis on pis.port_inwd_shipment_id=pi.port_inwd_shipment_id");
			q.append(" group by pli.port_purchase_order_id");
			q.append(" ) ppo_outer");
			q.append(" left join");
			q.append(" (");
			q.append(" select port_purchase_order_id, ifnull(sum(delivered_quantity),0) delivered_quantity from  (");
			q.append(" select dn.id, dn.port_purchase_order_id, ");
			q.append(" ifnull(sum(delivered_quantity),0) delivered_quantity from delivery_notes dn");
			q.append(" left join delivery_note_line_items dnli on dnli.delivery_note_id = dn.id");
			q.append(" group by dnli.delivery_note_id");
			q.append(" ) d ");
			q.append(" group by d.port_purchase_order_id");
			q.append(" ) delivered on  ppo_outer.port_purchase_order_id = delivered.port_purchase_order_id");
			q.append(" ) pending_ppo");
			q.append(" where ");
			//q.append(" pending_ppo.pending_quantity > 0 ");
			//q.append(" AND ");
			q.append(" pending_ppo.is_deleted !=1 ");

			String filterClause = composeWhereClausePPO(form);
			q.append(filterClause);

			query = q.toString();
			log.info("query = " + query);

			cs = conn.prepareCall(query);

			rs = cs.executeQuery();
			if (null != rs && rs.next()) {
				reportList = new ArrayList<PortPurchaseOrderDeliveryBean>();
				do {
					PortPurchaseOrderDeliveryBean report = new PortPurchaseOrderDeliveryBean();
					report.setPpoNo(rs.getInt("port_purchase_order_id"));
					report.setPpoDate(Converter.dateToString(Converter.sqlDateToDate(rs.getDate("create_ts"))));
					report.setCustomerName(rs.getString("customer_name"));
					report.setDeliveryAddress(rs.getString("delivery_address"));
					report.setTotalQuantity(rs.getInt("total_ordered_quantity"));
					report.setPendingQuantity(rs.getInt("pending_quantity"));
					report.setVesselName(rs.getString("vessel_name"));
					report.setGrade(rs.getString("material_grade"));
					reportList.add(report);
					report = null;
				} while (rs.next());
			}
			form.setReportList(reportList);

		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return form;
	}

	public List<PortPurchaseOrderLineItemVO> fetchPPOLineItems(Integer purchaseOrderNo, UserInfoVO userInfoVO)
			throws SQLException {
		List<PortPurchaseOrderLineItemVO> list = new ArrayList<PortPurchaseOrderLineItemVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";

		try {
			conn = getConnection();

//			String sql = "select a.* from (select ppoli.id,pid.length, pid.width, pid.thickness,ppoli.ordered_quantity,"
//					+ " ppoli.ordered_quantity-ifnull((dq.delivered_quantity),0) pending_quantity,"
//					+ " ifnull((dq.delivered_quantity),0) delivered_quantity"
//					+ " from ppo_line_items ppoli left join port_inward_details pid "
//					+ " on ppoli.port_inward_details_id=pid.port_inward_detail_id "
//					+ " left join (select dnli.ppo_line_items_id,sum(dnli.delivered_quantity) delivered_quantity "
//					+ " from delivery_note_line_items dnli where dnli.delivery_note_id"
//					+ " in (select id from delivery_notes dn where dn.port_purchase_order_id=" + purchaseOrderNo + ") "
//					+ " group by dnli.ppo_line_items_id) dq" + " on dq.ppo_line_items_id=ppoli.id "
//					+ " where ppoli.port_purchase_order_id=" + purchaseOrderNo + ") a " + " where a.pending_quantity>0";
//			query = sql;
			StringBuffer q = new StringBuffer();
			q.append(" select a.* from  ");
			q.append(" ( ");
			q.append(" select  ");
			q.append(" ppoli.id, ");
			q.append(" pid.length,  ");
			q.append(" pid.width,  ");
			q.append(" pid.thickness, ");
			q.append(" ppoli.ordered_quantity,  ");
			q.append(" ppoli.ordered_quantity-ifnull((dq.delivered_quantity),0) pending_quantity,  ");
			q.append(" ifnull((dq.delivered_quantity),0) delivered_quantity , ");
			q.append(" pi.material_type,  ");
			q.append(" pi.mill_name ");
			q.append(" from ppo_line_items ppoli  ");
			q.append(" left join port_inward_details pid  on ppoli.port_inward_details_id=pid.port_inward_detail_id   ");
			q.append(" left join port_inward pi on pi.port_inward_id = pid.port_inward_id ");
			q.append(" left join  ");
			q.append(" ( ");
			q.append(" select dnli.ppo_line_items_id,sum(dnli.delivered_quantity) delivered_quantity   ");
			q.append(" from delivery_note_line_items dnli  ");
			q.append(" where dnli.delivery_note_id in  ");
			q.append(" ( ");
			q.append(" select id from delivery_notes dn where dn.port_purchase_order_id= ").append(purchaseOrderNo);
			q.append(" )   ");
			q.append(" group by dnli.ppo_line_items_id) dq on dq.ppo_line_items_id=ppoli.id   ");
			q.append(" where ppoli.port_purchase_order_id= ").append(purchaseOrderNo);
			q.append(" ) a   ");
			q.append(" where a.pending_quantity>0; ");
			query = q.toString();
			log.info("query = " + query);

			cs = conn.prepareCall(query);

			rs = cs.executeQuery();
			if (null != rs && rs.next()) {

				do {
					PortPurchaseOrderLineItemVO vo = new PortPurchaseOrderLineItemVO();
					vo.setPpoLineItemNo(rs.getInt(1));
					vo.setLength(rs.getInt(2));
					vo.setWidth(rs.getInt(3));
					vo.setThickness(rs.getDouble(4));
					vo.setOrderedQuantity(rs.getInt(5));
					vo.setPendingQuantity(rs.getInt(6));
					vo.setMaterialType(rs.getString(8));
					vo.setMillName(rs.getString(9));
					list.add(vo);

				} while (rs.next());
			}

		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return list;
	}

	public PortPurchaseOrderVO getPPODetailsById(int purchaseOrderNo) throws SQLException {
		PortPurchaseOrderVO vo = null;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";

		try {
			conn = getConnection();

			String sql = "select ppo.port_purchase_order_id, CAST(ppo.create_ts as Date) as ppoDate, ppo.customer_name, ppo.delivery_address, ppo.payment_terms,"
					+ " ppo.excise, ppo.tax, ppo.transport, ppo.comments"
					+ " from port_purchase_order ppo  where ppo.port_purchase_order_id=" + purchaseOrderNo;
			query = sql;
			log.info("query = " + query);

			cs = conn.prepareCall(query);

			rs = cs.executeQuery();
			if (null != rs && rs.next()) {

				do {
					vo = new PortPurchaseOrderVO();
					vo.setPpoNo(rs.getInt(1));
					vo.setPpoDate(rs.getString(2));
					vo.setCustName(rs.getString(3));
					vo.setDeliveryAddr(rs.getString(4));
					vo.setPaymentTerms(rs.getString(5));
					vo.setExcise(rs.getString(6));
					vo.setTax(rs.getString(7));
					vo.setTransport(rs.getString(8));
					vo.setComments(rs.getString(9));

				} while (rs.next());

			}

		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return vo;
	}

	public String updateOrderQty(PortPurchaseDeliveryNoteForm form, PortPurchaseOrderLineItemVO vo,
			UserInfoVO userInfoVO) throws SQLException {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "Success";
		int count = 0;
		PreparedStatement statement = null;
		try {
			conn = getConnection();
			Integer orderQty = vo.getOrderedQuantity() - vo.getDeliveryQuantity();
			String sql = "update ppo_line_items s set s.ordered_quantity = ?, s.update_ui = ?,s.update_ts = NOW()  WHERE port_purchase_order_id=? and id=?";
			statement = conn.prepareStatement(sql);
			statement.setInt(1, orderQty);
			statement.setString(2, userInfoVO.getUserName());
			statement.setInt(3, form.getPpoNo());
			statement.setInt(4, vo.getPpoLineItemNo());

			statement.executeUpdate();
			log.info("message = " + message);

		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			log.error(message);
		} finally {
			closeDatabaseResources(conn, rs, statement);
		}

		return message;
	}

	public PortStockReportForm fetchPortCumulativeStockReport(PortStockReportForm form, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		String sql = "";
		ArrayList<PortStockBean> reportList = null;
		try {
			conn = getConnection();

			sql = composeQueryForCumulativeStockReportAtPort(form);

			query = sql;
			log.info("query = " + query);

			cs = conn.prepareCall(query);

			rs = cs.executeQuery();
			if (null != rs && rs.next()) {
				reportList = new ArrayList<PortStockBean>();
				do {
					PortStockBean report = new PortStockBean();

					report.setPortInwardId(rs.getInt("port_inward_id"));
					report.setPortInwardDetailId(rs.getInt("port_inward_detail_id"));
					report.setPortInwardShipmentId(rs.getInt("port_inwd_shipment_id"));
					report.setVesselDate(formatOutput(rs.getString("vessel_date")));
					report.setVesselName(formatOutput(rs.getString("vessel_name")));
					report.setMillName(formatOutput(rs.getString("mill_name")));
					report.setMake(formatOutput(rs.getString("material_make")));
					report.setMaterialType(formatOutput(rs.getString("material_type")));
					report.setGrade(formatOutput(rs.getString("material_grade")));
					report.setDesc(formatOutput(rs.getString("description")));
					report.setLength(rs.getInt("length"));
					report.setWidth(rs.getInt("width"));
					report.setThickness(rs.getDouble("thickness"));
					report.setInwardQuantity(rs.getInt("inward_quantity"));
					report.setDeliveredTalojaQty(rs.getInt("quantity_delivered_to_taloja"));
					report.setBalanceQtyForSale(rs.getInt("balance_qty_for_sale"));
					report.setPpoOrderedQty(rs.getInt("ppo_ordered_quantity"));
					report.setPpoDeliveredQty(rs.getInt("ppoli_delivered_quantity"));
					report.setBalanceAtDock(rs.getInt("balance_at_dock"));

					reportList.add(report);
					report = null;
				} while (rs.next());
			}
			form.setReportList(reportList);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return form;
	}

	private String composeQueryForCumulativeStockReportAtPort(PortStockReportForm form) {
		StringBuffer q = new StringBuffer();

		q.append(" SELECT one.port_inward_id, ");
		q.append("        one.port_inward_detail_id, ");
		q.append("        one.port_inwd_shipment_id, ");
		q.append("        one.mill_name, ");
		q.append("        one.material_make, ");
		q.append("        one.material_grade, ");
		q.append("        one.material_type, ");
		q.append("        one.description, ");
		q.append("        one.length, ");
		q.append("        one.width, ");
		q.append("        one.thickness, one.vessel_date, one.vessel_name,");
		q.append("        ifnull(one.quantity,0) inward_quantity, ");
		q.append("        ifnull(two.ppo_quantity,0) ppo_ordered_quantity, ");
		q.append("        ifnull(three.Delivered_taloja,0) quantity_delivered_to_taloja,  ");
		q.append("        ( ");
		q.append(" 		ifnull(one.quantity,0)  ");
		q.append("         - ifnull(two.ppo_quantity,0)  ");
		q.append("         - ifnull(three.delivered_taloja,0) ");
		q.append("         ) balance_qty_for_sale, ");
		q.append("         ifnull(four.ppoli_delivered_quantity,0) ppoli_delivered_quantity, ");
		q.append("         ( ");
		q.append(" 			ifnull(one.quantity,0)  ");
		q.append("            - ifnull(four.ppoli_delivered_quantity,0)  ");
		q.append("            - ifnull(three.Delivered_taloja,0) ");
		q.append("         ) balance_at_dock ");
		q.append("          ");
		q.append("          ");
		q.append(" FROM   ((SELECT pi.port_inward_id, ");
		q.append("                pi.mill_name, ");
		q.append("                pi.material_make, ");
		q.append("                pi.material_grade, ");
		q.append("                pi.material_type, ");
		q.append("                pi.description, ");
		q.append("                pid.length, ");
		q.append("                pid.width, ");
		q.append("                pid.thickness, ");
		q.append("                pid.quantity, pis.vessel_date, pis.vessel_name,");
		q.append("                pid.port_inward_detail_id, ");
		q.append("                pi.port_inwd_shipment_id ");
		q.append("         FROM   port_inward pi ");
		q.append("                LEFT JOIN port_inward_details pid ");
		q.append("                       ON pi.port_inward_id = pid.port_inward_id ");
		q.append(" left join port_inward_shipment pis on pi.port_inwd_shipment_id=pis.port_inwd_shipment_id ");
		q.append("         GROUP  BY pid.port_inward_detail_id) one ");
		q.append("         LEFT JOIN (SELECT Ifnull(Sum(ppoli.ordered_quantity), 0) ppo_quantity, ");
		q.append("                           ppoli.port_inward_details_id ");
		q.append("                    FROM   ppo_line_items ppoli ");
		q.append("                    GROUP  BY port_inward_details_id) two ");
		q.append("                ON two.port_inward_details_id = one.port_inward_detail_id ");
		q.append("         LEFT JOIN (SELECT Ifnull(Sum(po.quantity), 0) delivered_taloja, ");
		q.append("                           po.port_out_id, ");
		q.append("                           pioi.port_inward_details_id ");
		q.append("                    FROM   port_outward po ");
		q.append("                           LEFT JOIN port_inward_outward_intersection pioi ");
		q.append("                                  ON po.port_out_id = pioi.port_outward_id ");
		q.append("                    GROUP  BY pioi.port_inward_details_id) three ");
		q.append("                ON three.port_inward_details_id = one.port_inward_detail_id) ");
		q.append(" 		LEFT JOIN (SELECT ifnull(sum(dnli.delivered_quantity),0) ppoli_delivered_quantity, ");
		q.append(" 						ppoli.port_inward_details_id ");
		q.append(" 					FROM delivery_note_line_items dnli ");
		q.append("                     LEFT JOIN ppo_line_items ppoli  ");
		q.append(" 							ON dnli.ppo_line_items_id = ppoli.id  ");
		q.append(" 							AND dnli.delivery_note_id > 0  ");
		q.append(" 					GROUP BY ppoli.port_inward_details_id ");
		q.append(" 					) four ");
		q.append(" 			   ON four.port_inward_details_id = one.port_inward_detail_id ");

		q.append(" where one.port_inward_detail_id is not null   ");

		String filterClause = composeWhereClause(form);
		q.append(filterClause);

		return q.toString();
	}

	private String composeWhereClause(PortStockReportForm form) {
		StringBuffer clause = new StringBuffer();
		List<String> clauses = new ArrayList<String>();

		if (!form.getGrade().equals("ALL")) {
			if (form.getGrade() != null && !form.getGrade().isEmpty()) {
				String materialGradeClause = "one.material_grade like '%" + form.getGrade() + "%'";
				clauses.add(materialGradeClause);
			}
		}

		if (!form.getMaterialType().equals("ALL")) {
			if (form.getMaterialType() != null && !form.getMaterialType().isEmpty()) {

				String anyOtherClause = "one.material_type like '%" + form.getMaterialType() + "%'";
				clauses.add(anyOtherClause);
			}
		}
		
		if(form.getMillName()!=null && !form.getMillName().isEmpty() && !form.getMillName().equalsIgnoreCase("ALL")){
			String millNameClause = "one.mill_name like '%"+form.getMillName()+"%'";
			clauses.add(millNameClause);
		}
		
		if(form.getVesselName()!=null && !form.getVesselName().isEmpty() && !form.getVesselName().equalsIgnoreCase("ALL")){
			String vesselNameClauses = "one.vessel_name like '%"+form.getVesselName()+"%'";
			clauses.add(vesselNameClauses);
		}
		
		if(form.getFromDate()!=null && !form.getFromDate().isEmpty()){
			try{
				Date frmDt = convertStringToDate(form.getFromDate(), "dd/MM/yy");
				String frmDtStr = dateToString(frmDt, "yyyy-MM-dd");
				String vesselDateFrom = "one.vessel_date >= '"+frmDtStr+"'";
				clauses.add(vesselDateFrom);
			}catch(ParseException e){
				log.error("some error",e);
			}
			
		}
		
		if(form.getToDate()!=null && !form.getToDate().isEmpty()){
			try {
				Date toDt = convertStringToDate(form.getToDate(), "dd/MM/yy");
				String toDtStr = dateToString(toDt, "yyyy-MM-dd");
				String toDateClause = "one.vessel_date <= '"+toDtStr+"'";

				clauses.add(toDateClause);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("some error",e);
			}
			
		}

		// Add any more clauses here.

		for (String c : clauses) {

			clause.append(" AND ").append(c).append(" ");

		}

		return clause.toString();
	}

	public String deleteDeliveryNoteLineItems(String id) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement statement = null;
		String message = "Success";
		try {
			conn = getConnection();

			String sql = "delete from delivery_note_line_items where id = ?";
			statement = conn.prepareStatement(sql);
			statement.setInt(1, Integer.parseInt(id));
			statement.executeUpdate();

			log.info("message = " + message);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			log.error(message);
		} finally {
			closeDatabaseResources(conn, rs, statement);
		}
		return message;
	}

	private String toWildCard(String s) {
		String ret = s;
		if (StringUtils.isEmpty(s)) {
			ret = "%";
		} else if ("ALL".equalsIgnoreCase(s)) {
			ret = "%";
		}
		return ret;
	}

	private String composeWhereClausePPO(PortPurchaseOrderDeliveryForm form) {
		StringBuffer clause = new StringBuffer();
		List<String> clauses = new ArrayList<String>();

		if (form.getCustomerName() != null && !form.getCustomerName().isEmpty()) {

			String anyOtherClause = "pending_ppo.customer_name like '%" + form.getCustomerName() + "%'";
			clauses.add(anyOtherClause);

		}

		// Add any more clauses here.
		
		//From date clause
		if(!StringUtils.isEmpty(form.getFromDate()) ){
			try {
				Date fromDate = convertStringToDate(form.getFromDate(), "dd/MM/yy");
				Date fromDate_h0_m0_s0 = setTime(fromDate, 0, 0, 0); 
				String fromDateStr = convertDateToString(fromDate_h0_m0_s0, "yyyy-MM-dd HH:mm:ss");
				String fromDateClause = "create_ts >= '"+fromDateStr+"'";
				clauses.add(fromDateClause);
				
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//To date clause
		if(!StringUtils.isEmpty(form.getToDate())){
			try {
				Date toDate = convertStringToDate(form.getToDate(), "dd/MM/yy");
				Date toDate_h23_m59_s59 = setTime(toDate, 23, 59, 59); 
				String toDateStr = convertDateToString(toDate_h23_m59_s59, "yyyy-MM-dd HH:mm:ss");
				String toDateClause = "create_ts <= '"+toDateStr+"'";
				clauses.add(toDateClause);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (String c : clauses) {

			clause.append(" AND ").append(c).append(" ");

		}

		return clause.toString();
	}

	



}
