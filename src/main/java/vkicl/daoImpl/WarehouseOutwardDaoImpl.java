package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import vkicl.util.Constants;
import vkicl.util.Converter;
import vkicl.util.JqGridSearchParameterHolder;
import vkicl.util.JqGridSearchParameterHolder.Rule;
import vkicl.vo.PortInwardRecordVO;
import vkicl.vo.WarehouseOutwardReportVO;

public class WarehouseOutwardDaoImpl extends BaseDaoImpl{
	
	private static Logger log = Logger.getLogger(WarehouseOutwardDaoImpl.class);

	public Integer fetchWarehouseOutwardDetailsRecordCount(String orderByFieldName, String order, JqGridSearchParameterHolder searchParam) throws SQLException {
		List<PortInwardRecordVO> list = new ArrayList<PortInwardRecordVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		int count = 0;
		try {
			conn = getConnection();
//			String count_sql = " SELECT " + " count(*) " + " FROM ("
//					+ "select * from warehouse_outward wo "
//					+ processSearchCriteria(searchParam)
//					+ ") a";
			String count_sql = "select count(*) from ("+composeQueryForWarehouseOutwardDetails(orderByFieldName, order, searchParam)+" ) a";
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
	
	
	
	
	

	private String processSearchRule(Rule r) {
		String data = r.getData();
		String field = r.getField();
		String op = r.getOp();

		String clause = "";
		if (field != null && field.equalsIgnoreCase("buyerName")) {
			clause = "dispo.buyerName like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("vehicleNo")) {
			clause = "wo.vehicle_no like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("vehicleDate")) {
			
			try {
				String vehicleDate = stringToSqlDateString(data, Constants.Apps.DATE_FORMAT, Constants.Apps.DATE_FORMAT_SQL);
				clause = "wo.vehicle_dt = date('" + vehicleDate + "')";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("some error",e);
			}
			
		} else if (field != null && field.equalsIgnoreCase("materialType")) {
			clause = "sb.material_type like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("mill")) {
			clause = "dd.millName like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("make")) {
			clause = "dd.make like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("grade")) {
			clause = "dd.grade like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("heatNo")) {
			clause = "sb.heat_no like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("plateNo")) {
			clause = "sb.plate_no like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("thickness")) {
			clause = "dd.thickness = " + data;
		}  else if (field != null && field.equalsIgnoreCase("length")) {
			clause = "dd.length = " + data;
		}  else if (field != null && field.equalsIgnoreCase("width")) {
			clause = "dd.width = " + data;
		} 

		return clause;
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
	
	
	public List<WarehouseOutwardReportVO> fetchWarehouseOutwardDetails(int pageNo, int pageSize, long total,
			String orderByFieldName, String order, JqGridSearchParameterHolder searchParam) throws SQLException {
		List<WarehouseOutwardReportVO> list = new ArrayList<WarehouseOutwardReportVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";

		try {
			conn = getConnection();

			query = composeQueryForWarehouseOutwardDetails(orderByFieldName, order, searchParam);
			log.info("query = " + query);

			cs = conn.prepareCall(query);

			rs = cs.executeQuery();
			if (null != rs && rs.next()) {

				do {
					WarehouseOutwardReportVO vo = new WarehouseOutwardReportVO();
					vo.setCreateTS(dateToString(rs.getDate(1), "dd-MM-yyyy"));
					vo.setWarehouseOutwardId(rs.getInt(2));
					vo.setDispatchNo(rs.getInt(3));
					vo.setDispatchDetailId(rs.getInt(4));
					vo.setVehicleNo(rs.getString(5));
					vo.setVehicleDate(dateToString(rs.getDate(6), "dd-MM-yyyy"));
					vo.setActualWeight(rs.getDouble(7));
					vo.setMill(rs.getString(8));
					vo.setMake(rs.getString(9));
					vo.setGrade(rs.getString(10));
					vo.setThickness(rs.getInt(11));
					vo.setLength(rs.getInt(12));
					vo.setWidth(rs.getInt(13));
					vo.setOrderedQuantity(rs.getInt(14));
					vo.setDeliveredQuantity(rs.getInt(15));
					vo.setBuyerName(rs.getString(16));
					vo.setMaterialType(rs.getString(17));
					vo.setHeatNo(rs.getString(18));
					vo.setPlateNo(rs.getString(19));
					vo.setMaterialDocId(rs.getInt(20));
					vo.setHandledBy(rs.getString(21));
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

	private String composeQueryForWarehouseOutwardDetails(String orderByFieldName, String order, JqGridSearchParameterHolder searchParam) {
		String query;
		StringBuffer q = new StringBuffer();
		q.append(" select ");
		q.append(" wo.create_ts warehouse_outward_creation_date, ");
		q.append(" wo.warehouse_outward_id, ");
		q.append(" wo.dispatchNo, ");
		q.append(" dd.dispatch_details_id, ");
		q.append(" wo.vehicle_no, ");
		q.append(" wo.vehicle_dt, ");
		q.append(" wo.actual_wt, ");
		q.append("  ");
		q.append(" dd.millName, ");
		q.append(" dd.make, ");
		q.append(" dd.grade, ");
		q.append(" dd.thickness, ");
		q.append(" dd.length, ");
		q.append(" dd.width, ");
		q.append(" dd.qty ordered_quantity, ");
		q.append(" wot.taken_qty delivered_quantity, ");
		q.append(" dispo.buyerName, ");
		q.append(" sb.material_type, ");
		q.append(" sb.heat_no, ");
		q.append(" sb.plate_no, ");
		q.append(" wid.material_id, ");
		q.append(" dispo.handleby ");
		q.append("  from ");
		q.append(" warehouse_outward wo ");
		q.append(" left join warehouse_outward_temp wot on wot.warehouse_outward_id = wo.warehouse_outward_id ");
		q.append("         left join dispatch_details dd on wot.dispatch_details_id = dd.dispatch_details_ID ");
		q.append(" left join dispatch_order dispo on dd.dispatch_order_id = dispo.dispatch_order_id ");
		q.append(" left join stock_balance sb on sb.stock_balance_id = wot.stock_id ");
		q.append("         left join warehouse_inward_details wid on wid.warehouse_in_detail_id = sb.warehouse_inward_id ");
		//q.append(" order by warehouse_outward_id desc;");
		
		//String sql = " select * from warehouse_outward "
		String sql = q.toString()
				+ processSearchCriteria(searchParam) + " " + composeOrderByClause(orderByFieldName, order) ;
		query = sql;
		return query;
	}
	
	private String composeOrderByClause(String orderByFieldName, String order) {
		String orderByClause = "";
//		if (orderByFieldName != null) {
//			orderByClause = " ORDER BY ";
//			if (orderByFieldName.equalsIgnoreCase("vessel_date")) {
//				orderByClause = orderByClause + " pis.vessel_date " + order + " ";
//			} else if (orderByFieldName.equalsIgnoreCase("id")) {
//				orderByClause = orderByClause + " pin.port_inward_id " + order + " ";
//			}
//		}
		return orderByClause;
	}
}
