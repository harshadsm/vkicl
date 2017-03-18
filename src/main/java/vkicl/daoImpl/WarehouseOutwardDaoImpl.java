package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	public Integer fetchWarehouseOutwardRecordCount(JqGridSearchParameterHolder searchParam) throws SQLException {
		List<PortInwardRecordVO> list = new ArrayList<PortInwardRecordVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		int count = 0;
		try {
			conn = getConnection();
			String count_sql = " SELECT " + " count(*) " + " FROM warehouse_outward wo "
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
	
	
	public List<WarehouseOutwardReportVO> fetchPortInwardDetails_2(int pageNo, int pageSize, long total,
			String orderByFieldName, String order, JqGridSearchParameterHolder searchParam) throws SQLException {
		List<WarehouseOutwardReportVO> list = new ArrayList<WarehouseOutwardReportVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";

		try {
			conn = getConnection();

			String sql = " select * from warehouse_outward "
					+ processSearchCriteria(searchParam) + " " + composeOrderByClause(orderByFieldName, order) + ";";
			query = sql;
			log.info("query = " + query);

			cs = conn.prepareCall(query);

			rs = cs.executeQuery();
			if (null != rs && rs.next()) {

				do {
					
					
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
}