package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vkicl.report.bean.WarehouseDispatchDetailsBean;
import vkicl.util.Constants;
import vkicl.util.JqGridSearchParameterHolder;
import vkicl.util.JqGridSearchParameterHolder.Rule;

public class DispatchOrderDetailsDaoImpl extends BaseDaoImpl {

	private Logger log = LoggerFactory.getLogger(DispatchOrderDetailsDaoImpl.class);

	public List<WarehouseDispatchDetailsBean> getDispatchOrderDetails( int pageNo, int pageSize, long total,
			String orderByFieldName, String order, JqGridSearchParameterHolder searchParam, int dispatchOrderId) {
		
		List<WarehouseDispatchDetailsBean> rows = new ArrayList<WarehouseDispatchDetailsBean>();
		StringBuffer q = new StringBuffer();

		q.append(" select  ");
		q.append(" ddt.millName, ddt.make, ddt.grade, ddt.length, ddt.width, ddt.thickness, ");
		q.append(
				" (ddt.qty - COALESCE(wott.taken_qty,0)) pending_qty, ddt.rate, ddt.rateUnit, ddt.taxes, ddt.excise, ");
		q.append(" ddt.actWt, ddt.actWtUnit, ddt.dispatch_details_ID, ");
		q.append(" ddt.qty orderedQuantity,  wott.taken_qty deliveredQuantity ");
		q.append(" from  ");
		q.append(" ( ");
		q.append(" select * from dispatch_details dd   ");
		q.append(" where dd.dispatch_order_id = ").append(dispatchOrderId);
		q.append(" ) ddt  ");
		q.append(" left join ( ");
		q.append(" select wot.dispatch_details_id, sum(taken_qty) taken_qty from warehouse_outward_temp wot ");
		q.append(" where wot.dispatch_order_id =  ").append(dispatchOrderId);
		q.append(" group by wot.dispatch_details_id ");
		q.append(" ) wott  ");
		q.append(" on ddt.dispatch_details_id = wott.dispatch_details_id ");
		q.append(" where (ddt.qty - COALESCE(wott.taken_qty,0)) > 0 ");
		q.append(processSearchCriteria1(searchParam));
		q.append(" "+composeOrderByClause(orderByFieldName, order));
		q.append(" " + composeLimitClause(pageNo, pageSize, total) + ";");
		
		log.debug(q.toString());

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		int count = 0;
		try {
			conn = getConnection();

			String sql = q.toString();// + processSearchCriteria1(searchParam) + ";";

			log.info("query = " + sql);

			cs = conn.prepareCall(sql);

			rs = cs.executeQuery();
			if (null != rs) {

				while (rs.next()) {
					WarehouseDispatchDetailsBean d = new WarehouseDispatchDetailsBean();
					d.setMillName(rs.getString(1));
					d.setMake(rs.getString(2));
					d.setGrade(rs.getString(3));
					d.setLength(rs.getInt(4));
					d.setWidth(rs.getInt(5));
					d.setThickness(rs.getDouble(6));
					d.setPendingQuantity(rs.getInt(7));
					d.setRate(rs.getString(8));
					d.setRateUnit(rs.getString(9));
					d.setTaxes(rs.getString(10));
					d.setExcise(rs.getString(11));
					d.setActWt(rs.getDouble(12));
					d.setActWtUnit(rs.getString(13));
					d.setDispatchDetailsID(rs.getInt(14));
					d.setOrderedQuantity(rs.getInt(15));
					d.setDeliveredQuantity(rs.getInt(16));
					rows.add(d);

				}
				;

				log.debug("Row count === " + rows.size());

			}

		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return rows;

	}

	public Integer getDispatchOrderDetailsCount(int dispatchOrderId, JqGridSearchParameterHolder searchParam) {
		StringBuffer q = new StringBuffer();

		// q.append(" select ");
		// q.append(" ddt.millName, ddt.make, ddt.grade, ddt.length, ddt.width,
		// ddt.thickness, ");
		// q.append(
		// " (ddt.qty - COALESCE(wott.taken_qty,0)) pending_qty, ddt.rate,
		// ddt.rateUnit, ddt.taxes, ddt.excise, ");
		// q.append(" ddt.actWt, ddt.actWtUnit, ddt.dispatch_details_ID ");

		q.append(" select count(*) ");
		q.append(" from  ");
		q.append(" ( ");
		q.append(" select * from dispatch_details dd   ");
		q.append(" where dd.dispatch_order_id = ").append(dispatchOrderId);
		q.append(" ) ddt  ");
		q.append(" left join ( ");
		q.append(" select wot.dispatch_details_id, sum(taken_qty) taken_qty from warehouse_outward_temp wot ");
		q.append(" where wot.dispatch_order_id =  ").append(dispatchOrderId);
		q.append(" group by wot.dispatch_details_id ");
		q.append(" ) wott  ");
		q.append(" on ddt.dispatch_details_id = wott.dispatch_details_id ");
		q.append(" where (ddt.qty - COALESCE(wott.taken_qty,0)) > 0 ");

		log.debug(q.toString());

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		int count = 0;
		try {
			conn = getConnection();

			String count_sql = q.toString() + processSearchCriteria1(searchParam) + ";";

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

	private String processSearchCriteria1(JqGridSearchParameterHolder searchParam) {
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
			clause = "po.vessel_name like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("grade")) {
			clause = "pi.material_grade like '%" + data + "%'";
		}

		// added-shweta

		else if (field != null && field.equalsIgnoreCase("vessel_Date")) {
			clause = "po.vessel_Date like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("vehicle_date")) {
			clause = "pis.vehicle_date like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("vehicle_number")) {
			clause = "pis.vehicle_number like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("mill_name")) {
			clause = "pi.mill_name like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("material_type")) {
			clause = "po.material_type like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("thickness")) {
			clause = "po.thickness like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("width")) {
			clause = "po.width like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("length")) {
			clause = "po.length like '%" + data + "%'";
		}

		else if (field != null && field.equalsIgnoreCase("vessel_date")) {
			clause = processDateClause(data);
		} else if (field != null && field.equalsIgnoreCase("port_inward_id")) {
			clause = "port_out_id = " + data;
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
			c = " WHERE " + c;
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

	private String composeOrderByClause(String orderByFieldName, String order) {
		String orderByClause = "";
		if (orderByFieldName != null) {

			if (orderByFieldName.equalsIgnoreCase("length")) {
				orderByClause = " ORDER BY  length " + order + " ";
			} else if (orderByFieldName.equalsIgnoreCase("width")) {
				orderByClause = " ORDER BY  width " + order + " ";
			} else if (orderByFieldName.equalsIgnoreCase("thickness")) {
				orderByClause = " ORDER BY  thickness " + order + " ";
			} else if (orderByFieldName.equalsIgnoreCase("port_inward_detail_id")) {
				orderByClause = " ORDER BY  port_inward_detail_id " + order + " ";
			} else if (orderByFieldName.equalsIgnoreCase("vessel_date")) {
				orderByClause = " ORDER BY  pis.vessel_date " + order + " ";
			} else if (orderByFieldName.equalsIgnoreCase("vessel_name")) {
				orderByClause = " ORDER BY  pis.vessel_name " + order + " ";
			} else if (orderByFieldName.equalsIgnoreCase("grade")) {
				orderByClause = " ORDER BY  pi.material_grade " + order + " ";
			}

		}
		return orderByClause;
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
