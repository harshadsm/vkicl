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
import vkicl.vo.PackingListItemVO;
import vkicl.vo.PortInwardRecordVO;

public class PortInwardPackingListDaoImpl extends BaseDaoImpl {
	private Logger log = Logger.getLogger(PortInwardPackingListDaoImpl.class);

	
	public List<PackingListItemVO> fetchPortInwardPackingList( int pageNo, int pageSize, long total,
			String orderByFieldName, String order, JqGridSearchParameterHolder searchParam) throws SQLException {
		List<PackingListItemVO> list = new ArrayList<PackingListItemVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		int count = 0;
		try {
			conn = getConnection();

			//String sql = " SELECT * FROM port_inward_details "
			String sql = " select pi.port_inward_id, pi.port_inwd_shipment_id,pid.port_inward_detail_id, "
			+" pis.vessel_name, pis.vessel_date, pi.material_grade, "
			+" pid.length, pid.width, pid.thickness, pid.quantity "
			+" from  "
			+" port_inward pi "
			+" left join port_inward_shipment pis on pis.port_inwd_shipment_id = pi.port_inwd_shipment_id "
			+" left join port_inward_details pid on pid.port_inward_id = pi.port_inward_id "
			//+" where pid.port_inward_detail_id is not null; "
			
			+ processSearchCriteria(searchParam) + " "+composeOrderByClause(orderByFieldName, order) + " " + composeLimitClause(pageNo, pageSize, total) + ";";
			query = sql;
			log.info("query = " + query);

			cs = conn.prepareCall(query);

			rs = cs.executeQuery();
			if (null != rs && rs.next()) {

				do {
					PackingListItemVO p = new PackingListItemVO();
					p.setPortInwardId(rs.getInt(1));
					p.setPortInwardShipmentId(rs.getInt(2));
					p.setPortInwardDetailId(rs.getInt(3));
					p.setVesselName(rs.getString(4));
					p.setVesselDate(dateToString(convertSqlDateToJavaDate(rs.getDate(5))));
					p.setGrade(rs.getString(6));
					p.setLength(rs.getInt(7));
					p.setWidth(rs.getInt(8));
					p.setThickness(rs.getDouble(9));
					p.setQuantity(rs.getInt(10));
					
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
		String limitClause = "LIMIT " + start + "," + noOfRecordsToFetch;
		
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

	public Integer fetchPortInwardPackingListRecordCount(JqGridSearchParameterHolder searchParam) throws SQLException {
		List<PortInwardRecordVO> list = new ArrayList<PortInwardRecordVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		int count = 0;
		try {
			conn = getConnection();
			//String count_sql = " SELECT count(*) FROM port_inward_details "
			
			String count_sql = " select count(*) "
			+" from  "
			+" port_inward pi "
			+" left join port_inward_shipment pis on pis.port_inwd_shipment_id = pi.port_inwd_shipment_id "
			+" left join port_inward_details pid on pid.port_inward_id = pi.port_inward_id "
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
		String notNullClause = "pid.port_inward_detail_id is not null";
		clauses.add(notNullClause);
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
			c = " WHERE " + c;
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
		} else if (field != null && field.equalsIgnoreCase("port_inward_id")) {
			clause = "port_inward_id = " + data ;
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
	private String composeOrderByClause(String orderByFieldName, String order) {
		String orderByClause = "";
		if(orderByFieldName!=null){
			orderByClause = " ORDER BY ";
			if(orderByFieldName.equalsIgnoreCase("length")){
				orderByClause = orderByClause + " length "+order+" ";
			}else if(orderByFieldName.equalsIgnoreCase("width")){
				orderByClause = orderByClause + " width "+order+" ";
			}else if(orderByFieldName.equalsIgnoreCase("thickness")){
				orderByClause = orderByClause + " thickness "+order+" ";
			}else if(orderByFieldName.equalsIgnoreCase("port_inward_detail_id")){
				orderByClause = orderByClause + " port_inward_detail_id "+order+" ";
			}
		}
		return orderByClause;
	}
}
