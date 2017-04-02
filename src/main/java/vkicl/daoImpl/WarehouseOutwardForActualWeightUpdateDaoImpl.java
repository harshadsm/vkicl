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
import vkicl.vo.WarehouseOutwardTempVO;
import vkicl.vo.WarehouseOutwardVO3;

public class WarehouseOutwardForActualWeightUpdateDaoImpl extends BaseDaoImpl{
	
	private static Logger log = Logger.getLogger(WarehouseOutwardForActualWeightUpdateDaoImpl.class);

	public Integer fetchWarehouseOutwardRecordCount(String orderByFieldName, String order, JqGridSearchParameterHolder searchParam) throws SQLException {
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
			String count_sql = "select count(*) from ("+composeQueryForWarehouseOutward(orderByFieldName, order, searchParam)+" ) a";
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
	
	
	public List<WarehouseOutwardVO3> fetchWarehouseOutward(int pageNo, int pageSize, long total,
			String orderByFieldName, String order, JqGridSearchParameterHolder searchParam) throws SQLException {
		List<WarehouseOutwardVO3> list = new ArrayList<WarehouseOutwardVO3>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";

		try {
			conn = getConnection();

			query = composeQueryForWarehouseOutward(orderByFieldName, order, searchParam)
			+ " " + composeLimitClause(pageNo, pageSize, total);
			log.info("query = " + query);

			cs = conn.prepareCall(query);

			rs = cs.executeQuery();
			if (null != rs && rs.next()) {

				do {
					WarehouseOutwardVO3 vo = new WarehouseOutwardVO3();
					vo.setCreateTS(dateToString(rs.getDate(1), "dd-MM-yyyy"));
					vo.setWarehouseOutwardId(rs.getInt(2));
					vo.setDispatchNo(rs.getInt(3));
					vo.setVehicleNo(rs.getString(4));
					vo.setVehicleDate(dateToString(rs.getDate(5), "dd-MM-yyyy"));
					vo.setActualWeight(rs.getDouble(6));
					vo.setBuyerName(rs.getString(7));
					
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

	private String composeQueryForWarehouseOutward(String orderByFieldName, String order, JqGridSearchParameterHolder searchParam) {
		String query;
		StringBuffer q = new StringBuffer();
		q.append(" select wo.create_ts,  ");
		q.append(" wo.warehouse_outward_id, ");
		q.append(" wo.dispatchNo, ");
		q.append(" wo.vehicle_no, ");
		q.append(" wo.vehicle_dt, ");
		q.append(" wo.actual_wt, ");
		q.append(" dispo.buyerName ");
		q.append(" from warehouse_outward wo ");
		q.append(" left join dispatch_order dispo on wo.dispatchNo = dispo.dispatch_order_id ");
		
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

	public void updateActualWeightofWarehouseOutward(Integer warehouseOutwardId, Double actualWeight) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		StringBuffer q = new StringBuffer();

		try {
			conn = getConnection();

			q.append(" update warehouse_outward set actual_wt = ? where warehouse_outward_id = ? ");
			String query = q.toString();

			log.info("query = " + query);
			cs = conn.prepareCall(query);
			cs.setDouble(1, actualWeight);
			cs.setInt(2, warehouseOutwardId);
			int recordsUpdated = cs.executeUpdate();
			
			log.debug("Update successful "+recordsUpdated);
		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		
	}
	
	/**
	 * 1 = 0 - 20
	 * 2 = 20 - 20
	 * 3 = 40 - 20
	 * @param pageNo
	 * @param pageSize
	 * @param total
	 * @return
	 */
	private String composeLimitClause(int pageNo, int pageSize, Long total) {
		Integer start = 0;
		Integer noOfRecordsToFetch = pageSize;
		String limitClause = " LIMIT " + start + "," + noOfRecordsToFetch;
		
		Integer totalPages = total.intValue() / pageSize;
		if(total.intValue() % pageSize > 0){
			totalPages++;
		}
		if(pageNo > totalPages){
			pageNo = totalPages;
		}
		
		if (pageSize > total) {
			return limitClause;
		} else {

			start = (pageNo - 1) * pageSize;
			
			limitClause = "LIMIT " + start + "," + noOfRecordsToFetch;
		}

		return limitClause;
	}
}
