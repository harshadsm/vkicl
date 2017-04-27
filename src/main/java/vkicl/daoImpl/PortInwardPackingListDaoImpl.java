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
import vkicl.util.SectionWeightCalculator;
import vkicl.vo.PackingListItemVO;
import vkicl.vo.PackingListItemVO2;
import vkicl.vo.PortInwardRecordVO;

public class PortInwardPackingListDaoImpl extends BaseDaoImpl {
	private Logger log = Logger.getLogger(PortInwardPackingListDaoImpl.class);

	public List<PackingListItemVO> fetchPortInwardPackingList(int pageNo, int pageSize, long total,
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

			
			String sql = composeQueryForPackingList() + processSearchCriteria1(searchParam) + " "
					+ composeOrderByClause_2(orderByFieldName, order) + " " + composeLimitClause(pageNo, pageSize, total)
					+ ";";
			query = sql;
			log.info("query = " + query);

			cs = conn.prepareCall(query);

			rs = cs.executeQuery();
			if (null != rs) {

				while (rs.next()) {
					PackingListItemVO p = new PackingListItemVO();
					p.setPortInwardId(rs.getInt("port_inward_id"));
					p.setPortInwardShipmentId(rs.getInt("port_inwd_shipment_id"));
					p.setPortInwardDetailId(rs.getInt("port_inward_detail_id"));
					p.setVesselName(rs.getString("vessel_name"));
					p.setVesselDate(dateToString(convertSqlDateToJavaDate(rs.getDate("vessel_date"))));
					p.setGrade(rs.getString("material_grade"));
					p.setMaterialType(rs.getString("material_type"));
					p.setLength(rs.getInt("length"));
					p.setWidth(rs.getInt("width"));
					p.setThickness(rs.getDouble("thickness"));
					p.setQuantity(rs.getInt("balance_pcs"));
					p.setMillName(rs.getString("mill_name"));
					p.setBalQty(rs.getDouble("balanace_quantity"));
					p.setActualWt(rs.getDouble("ActualWt"));
					p.setActualWt_unit(rs.getString("ActualWt_unit"));
					p.setMake(rs.getString("material_make"));
					p.setVendorName(rs.getString("vendor_name"));
					list.add(p);
				} 

			}

		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return list;
	}

	public List<PackingListItemVO> fetchPortOnwardPackingList(int pageNo, int pageSize, long total,
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

			// String sql = " SELECT * FROM port_inward_details "
			String sql = " select po.port_out_id,,po.vessel_Date, po.vessel_name, pi.mill_name, po.material_type, po.grade,po.thickness,po.width,po.length,po.quantity "
					+ " from port_outward_shipment pos "
					+ "inner join port_outward po on pos.port_out_shipment_id = po.port_out_shipment_id "
					+ " inner join port_inward_outward_intersection pios on pios.port_outward_id=po.port_out_id "
					+ " inner join port_inward pi on pi.port_inward_id=pios.port_inward_id "

					+ processSearchCriteria(searchParam) + " " + composeOrderByClause(orderByFieldName, order) + " "
					+ composeLimitClause(pageNo, pageSize, total) + ";";
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
					p.setMaterialType(rs.getString(7));
					p.setLength(rs.getInt(8));
					p.setWidth(rs.getInt(9));
					p.setThickness(rs.getDouble(10));
					p.setQuantity(rs.getInt(11));
					p.setMillName(rs.getString(12));
					p.setBalQty(rs.getDouble(13));
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
	 * 1 = 0 - 20 2 = 20 - 20 3 = 40 - 20
	 * 
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
		if (total.intValue() % pageSize > 0) {
			totalPages++;
		}
		if (pageNo > totalPages) {
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
			// String count_sql = " SELECT count(*) FROM port_inward_details "

//			String count_sql = " select count(*) " + " from  " + " port_inward pi "
//					+ " left join port_inward_shipment pis on pis.port_inwd_shipment_id = pi.port_inwd_shipment_id "
//					+ " left join port_inward_details pid on pid.port_inward_id = pi.port_inward_id "
//					+ processSearchCriteria(searchParam);
			
			String count_sql = "select count(*) from ("+composeQueryForPackingList() + processSearchCriteria1(searchParam) + " ) a";

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

	private String processSearchCriteria2(JqGridSearchParameterHolder searchParam) {
		String sqlClause = "";
		List<String> clauses = new ArrayList<String>();
		// String notNullClause = "pid.port_inward_detail_id is not null";
		// clauses.add(notNullClause);
		if (null != searchParam && null != searchParam.getRules() && !searchParam.getRules().isEmpty()) {
			for (JqGridSearchParameterHolder.Rule r : searchParam.getRules()) {
				String clause = processSearchRule2(r);
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
			clause = "pis.vessel_date like '%" + data + "%'";
		}
		// Changes-Shweta
		else if (field != null && field.equalsIgnoreCase("mill_name")) {
			clause = "pi.mill_name like '%" + data + "%'";
		}

		else if (field != null && field.equalsIgnoreCase("material_type")) {
			clause = "pi.material_type like '%" + data + "%'";
		}

		else if (field != null && field.equalsIgnoreCase("material_grade")) {
			clause = "pi.material_grade like '%" + data + "%'";
		}

		else if (field != null && field.equalsIgnoreCase("thickness")) {
			clause = "pid.thickness like '%" + data + "%'";
		}

		else if (field != null && field.equalsIgnoreCase("width")) {
			clause = "pid.width like '%" + data + "%'";
		}

		else if (field != null && field.equalsIgnoreCase("length")) {
			clause = "pid.length like '%" + data + "%'";
		}

		else if (field != null && field.equalsIgnoreCase("grade")) {
			clause = "pi.material_grade like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("vessel_date")) {
			clause = processDateClause(data);
		} else if (field != null && field.equalsIgnoreCase("port_inward_id")) {
			clause = "pi.port_inward_id = " + data;
		}

		return clause;
	}

	private String processSearchRule2(Rule r) {
		String data = r.getData();
		String field = r.getField();
		String op = r.getOp();

		String clause = "";
		if (field != null && field.equalsIgnoreCase("thickness")) {
			clause = "thickness = " + data;
		} else if (field != null && field.equalsIgnoreCase("width")) {
			clause = "width = " + data;
		} else if (field != null && field.equalsIgnoreCase("length")) {
			clause = "pid.length = " + data;
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
	
	private String composeOrderByClause_2(String orderByFieldName, String order) {
		String orderByClause = "";
		if (orderByFieldName != null) {

			if (orderByFieldName.equalsIgnoreCase("length")) {
				orderByClause = " ORDER BY  one.length " + order + " ";
			} else if (orderByFieldName.equalsIgnoreCase("width")) {
				orderByClause = " ORDER BY  one.width " + order + " ";
			} else if (orderByFieldName.equalsIgnoreCase("thickness")) {
				orderByClause = " ORDER BY  one.thickness " + order + " ";
			} else if (orderByFieldName.equalsIgnoreCase("port_inward_detail_id")) {
				orderByClause = " ORDER BY  one.port_inward_detail_id " + order + " ";
			} else if (orderByFieldName.equalsIgnoreCase("vessel_date")) {
				orderByClause = " ORDER BY  one.vessel_date " + order + " ";
			} else if (orderByFieldName.equalsIgnoreCase("vessel_name")) {
				orderByClause = " ORDER BY  one.vessel_name " + order + " ";
			} else if (orderByFieldName.equalsIgnoreCase("grade")) {
				orderByClause = " ORDER BY  one.material_grade " + order + " ";
			}

		}
		return orderByClause;
	}

	public Integer fetchPortInwardPackingListRecordCount_2(JqGridSearchParameterHolder searchParam,
			String portInwardIdStr) throws SQLException {
		List<PortInwardRecordVO> list = new ArrayList<PortInwardRecordVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		int count = 0;
		try {
			conn = getConnection();
			// String count_sql = " SELECT count(*) FROM port_inward_details "

			// String count_sql = " select count(*) "
			// +" from "
			// +" port_inward pi "
			// +" left join port_inward_shipment pis on
			// pis.port_inwd_shipment_id = pi.port_inwd_shipment_id "
			// +" left join port_inward_details pid on pid.port_inward_id =
			// pi.port_inward_id "
			// + processSearchCriteria(searchParam);

			String count_sql = getCountQueryFor2ndTable(searchParam, portInwardIdStr);

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

	public String getCountQueryFor2ndTable(JqGridSearchParameterHolder searchParam, String port_inward_id) {
		StringBuffer q = new StringBuffer();
		q.append(" select count(*) from ( ");
		q.append("  SELECT port_inward_id, ");
		q.append("  port_inward_detail_id, ");
		q.append("  thickness, ");
		q.append("  width, ");
		q.append("  length, ");
		q.append("  ifnull(quantity,0) quantity, ");
		q.append("  ifnull(total_ppo_ordered_quantity,0) total_ppo_ordered_quantity, ");
		q.append("  ifnull(total_to_taloja,0) total_to_taloja, ");
		q.append("  ifnull(quantity,0) ");
		q.append("  - ifnull(total_ppo_ordered_quantity,0)  ");
		q.append("  - ifnull(total_to_taloja,0) bal_pcs_at_dock ");
		q.append("  ");
		q.append(" FROM   (SELECT pid.port_inward_id, ");
		q.append("                pid.port_inward_detail_id, ");
		q.append("                pid.thickness, ");
		q.append("                pid.width, ");
		q.append("                pid.length, ");
		q.append("                pid.quantity ");
		q.append("         FROM   port_inward_details pid ");
		q.append("         WHERE  pid.port_inward_id = ").append(port_inward_id).append(" ) one ");
		q.append("        LEFT JOIN (SELECT pli.port_inward_details_id, ");
		q.append("                          Sum(ordered_quantity) total_ppo_ordered_quantity ");
		q.append("                   FROM   ppo_line_items pli ");
		q.append("                   WHERE  pli.port_inward_details_id IN ");
		q.append("                          (SELECT pid.port_inward_detail_id ");
		q.append("                           FROM   port_inward_details pid ");
		q.append("                           WHERE  pid.port_inward_id = ").append(port_inward_id).append(" ) ");
		q.append("                   GROUP  BY pli.port_inward_details_id) two ");
		q.append("               ON one.port_inward_detail_id = two.port_inward_details_id ");
		q.append("        LEFT JOIN (SELECT port_inward_details_id, ");
		q.append("                          Sum(quantity) total_to_taloja ");
		q.append("                   FROM   (SELECT port_inward_details_id, ");
		q.append("                                  port_inward_id, ");
		q.append("                                  port_outward_id ");
		q.append("                           FROM   port_inward_outward_intersection pioi ");
		q.append("                           WHERE  port_inward_id = ").append(port_inward_id).append(" ) a ");
		q.append("                          LEFT JOIN (SELECT po.port_out_id, ");
		q.append("                                            po.port_out_shipment_id, ");
		q.append("                                            po.quantity, ");
		q.append("                                            pos.warehouse_name ");
		q.append("                                     FROM   port_outward po ");
		q.append("                                            LEFT JOIN port_outward_shipment pos ");
		q.append("                                                   ON pos.port_out_shipment_id = ");
		q.append("                                                      po.port_out_shipment_id ");
		q.append("                                     WHERE  warehouse_name = 'Taloja') b ");
		q.append("                                 ON a.port_outward_id = b.port_out_id ");
		q.append("                   WHERE  warehouse_name = 'Taloja' ");
		q.append("                   GROUP  BY port_inward_details_id) three ");
		q.append("               ON three.port_inward_details_id = one.port_inward_detail_id ");
		q.append(" ) r where bal_pcs_at_dock > 0 ");
		q.append(processSearchCriteria2(searchParam));

		return q.toString();
	}

	public String getQueryFor2ndTable(String port_inward_id) {
		StringBuffer q = new StringBuffer();
		q.append(" select * from ( ");
		q.append("  SELECT port_inward_id, ");
		q.append("  port_inward_detail_id, ");
		q.append("  thickness, ");
		q.append("  width, ");
		q.append("  length, ");
		q.append("  ifnull(quantity,0) quantity, ");
		q.append("  ifnull(total_ppo_ordered_quantity,0) total_ppo_ordered_quantity, ");
		q.append("  ifnull(total_to_taloja,0) total_to_taloja, ");
		q.append("  ifnull(quantity,0) ");
		q.append("  - ifnull(total_ppo_ordered_quantity,0)  ");
		q.append("  - ifnull(total_to_taloja,0) bal_pcs_at_dock, ");
		q.append("  be_weight section_wt");
		q.append("  ");
		q.append(" FROM   (SELECT pid.port_inward_id, ");
		q.append("                pid.port_inward_detail_id, ");
		q.append("                pid.thickness, ");
		q.append("                pid.width, ");
		q.append("                pid.length, ");
		q.append("                pid.quantity, ");
		q.append("                pid.be_weight ");
		q.append("         FROM   port_inward_details pid ");
		q.append("         WHERE  pid.port_inward_id = ").append(port_inward_id).append(" ) one ");
		q.append("        LEFT JOIN (SELECT pli.port_inward_details_id, ");
		q.append("                          Sum(ordered_quantity) total_ppo_ordered_quantity ");
		q.append("                   FROM   ppo_line_items pli ");
		q.append("                   WHERE  pli.port_inward_details_id IN ");
		q.append("                          (SELECT pid.port_inward_detail_id ");
		q.append("                           FROM   port_inward_details pid ");
		q.append("                           WHERE  pid.port_inward_id = ").append(port_inward_id).append(" ) ");
		q.append("                   GROUP  BY pli.port_inward_details_id) two ");
		q.append("               ON one.port_inward_detail_id = two.port_inward_details_id ");
		q.append("        LEFT JOIN (SELECT port_inward_details_id, ");
		q.append("                          Sum(quantity) total_to_taloja ");
		q.append("                   FROM   (SELECT port_inward_details_id, ");
		q.append("                                  port_inward_id, ");
		q.append("                                  port_outward_id ");
		q.append("                           FROM   port_inward_outward_intersection pioi ");
		q.append("                           WHERE  port_inward_id = ").append(port_inward_id).append(" ) a ");
		q.append("                          LEFT JOIN (SELECT po.port_out_id, ");
		q.append("                                            po.port_out_shipment_id, ");
		q.append("                                            po.quantity, ");
		q.append("                                            pos.warehouse_name ");
		q.append("                                     FROM   port_outward po ");
		q.append("                                            LEFT JOIN port_outward_shipment pos ");
		q.append("                                                   ON pos.port_out_shipment_id = ");
		q.append("                                                      po.port_out_shipment_id ");
		q.append("                                     WHERE  warehouse_name = 'Taloja') b ");
		q.append("                                 ON a.port_outward_id = b.port_out_id ");
		q.append("                   WHERE  warehouse_name = 'Taloja' ");
		q.append("                   GROUP  BY port_inward_details_id) three ");
		q.append("               ON three.port_inward_details_id = one.port_inward_detail_id ");
		q.append(" ) r where bal_pcs_at_dock > 0 ");
		// q.append(processSearchCriteria(searchParam));

		return q.toString();
	}

	public List<PackingListItemVO2> fetchPortInwardPackingList_2(int pageNo, int pageSize, long total,
			String orderByFieldName, String order, JqGridSearchParameterHolder searchParam, String portInwardIdStr)
			throws SQLException {
		List<PackingListItemVO2> list = new ArrayList<PackingListItemVO2>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		int count = 0;
		try {
			conn = getConnection();

			// String sql = " SELECT * FROM port_inward_details "
			// String sql = " select pi.port_inward_id,
			// pi.port_inwd_shipment_id,pid.port_inward_detail_id, "
			// +" pis.vessel_name, pis.vessel_date, pi.material_grade,
			// pi.material_type, "
			// +" pid.length, pid.width, pid.thickness, pid.quantity ,
			// pi.mill_name, "
			// +" round(((pid.length * pid.width * pid.thickness * pid.quantity
			// * 7.85)/1000000000),3) as BalQty, pid.be_weight as ActualWt, "
			// + " pid.be_wt_unit as ActualWt_unit"
			// +" from "
			// +" port_inward pi "
			// +" left join port_inward_shipment pis on
			// pis.port_inwd_shipment_id = pi.port_inwd_shipment_id "
			// +" left join port_inward_details pid on pid.port_inward_id =
			// pi.port_inward_id "
			// +" where pid.port_inward_detail_id is not null; "
			String sql = getQueryFor2ndTable(portInwardIdStr) + processSearchCriteria2(searchParam) + " "
					+ composeOrderByClause(orderByFieldName, order) + " " + composeLimitClause(pageNo, pageSize, total)
					+ ";";
			query = sql;
			log.info("query = " + query);

			cs = conn.prepareCall(query);

			rs = cs.executeQuery();
			if (null != rs && rs.next()) {

				do {
					PackingListItemVO2 p = new PackingListItemVO2();
					Double t = rs.getDouble("thickness");
					Integer l = rs.getInt("length");
					Integer w = rs.getInt("width");
					Integer q = rs.getInt("bal_pcs_at_dock");
					
					p.setPortInwardId(rs.getInt("port_inward_id"));
					// p.setPortInwardShipmentId(rs.getInt(2));
					p.setPortInwardDetailId(rs.getInt("port_inward_detail_id"));
					// p.setVesselName(rs.getString(4));
					// p.setVesselDate(dateToString(convertSqlDateToJavaDate(rs.getDate(5))));
					// p.setGrade(rs.getString(6));
					// p.setMaterialType(rs.getString(7));
					p.setLength(l);
					p.setWidth(w);
					p.setThickness(t);
					p.setQuantity(q);
					// p.setQuantity(rs.getInt("quantity"));
					p.setBalQty(rs.getDouble("bal_pcs_at_dock"));
					//p.setActualWt(9999d);
					Double recalculatedSectionWeight = SectionWeightCalculator.calculateSectionWeight(t, l, w, q);
					//p.setSectionWt(rs.getDouble("section_wt"));
					p.setSectionWt(recalculatedSectionWeight);
					
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

	private String composeQueryForPackingList() {
		StringBuffer q = new StringBuffer();

		q.append(" SELECT one.port_inward_id,");
		q.append(" one.port_inward_detail_id,");
		q.append(" one.port_inwd_shipment_id,");
		q.append(" one.vessel_name,");
		q.append(" one.vessel_date, ");
		q.append(" one.material_grade,");
		q.append(" one.material_type,");
		q.append(" one.length,");
		q.append(" one.width,");
		q.append(" one.thickness, ");
		q.append(" ifnull(one.quantity,0) inward_quantity,");
		q.append(" one.mill_name,");
		q.append(" round(((one.length * one.width * one.thickness * one.quantity * 7.85)/1000000000),3) as balanace_quantity,");
		q.append(" one.ActualWt,");
		q.append(" one.ActualWt_unit,");
		q.append(" one.material_make,");
		q.append(" one.vendor_name,");

		q.append("  (");
		q.append("	ifnull(one.quantity,0) ");
		q.append("    - ifnull(four.ppoli_delivered_quantity,0) ");
		q.append("    - ifnull(three.Delivered_taloja,0)");
		q.append(" ) balance_pcs");

		q.append(" FROM   ((SELECT pi.port_inward_id,");
		q.append("            pi.mill_name,");
		q.append("          pi.material_make,");
		q.append("         pi.material_grade,");
		q.append("        pi.material_type,");
		q.append("        pi.description,");
		q.append("       pid.length,");
		q.append("      pid.width,");
		q.append("   pid.thickness,");
		q.append("   pid.quantity, pis.vessel_date, pis.vessel_name,pis.vendor_name,");
		q.append("   pid.port_inward_detail_id,");
		q.append("   pi.port_inwd_shipment_id,");
		q.append("	pid.be_weight as ActualWt, ");
		q.append("	pid.be_wt_unit as ActualWt_unit");
		q.append(" FROM   port_inward pi");
		q.append("       LEFT JOIN port_inward_details pid");
		q.append("              ON pi.port_inward_id = pid.port_inward_id");
		q.append(" left join port_inward_shipment pis on pi.port_inwd_shipment_id=pis.port_inwd_shipment_id");
		q.append("       GROUP  BY pid.port_inward_detail_id) one");
		q.append("      LEFT JOIN (SELECT Ifnull(Sum(ppoli.ordered_quantity), 0) ppo_quantity,");
		q.append("                       ppoli.port_inward_details_id");
		q.append("               FROM   ppo_line_items ppoli");
		q.append("               GROUP  BY port_inward_details_id) two");
		q.append("          ON two.port_inward_details_id = one.port_inward_detail_id");
		q.append("   LEFT JOIN (SELECT Ifnull(Sum(po.quantity), 0) delivered_taloja,");
		q.append("                    po.port_out_id,");
		q.append("                    pioi.port_inward_details_id");
		q.append("           FROM   port_outward po");
		q.append("                 LEFT JOIN port_inward_outward_intersection pioi");
		q.append("                        ON po.port_out_id = pioi.port_outward_id");
		q.append("         GROUP  BY pioi.port_inward_details_id) three");
		q.append("     ON three.port_inward_details_id = one.port_inward_detail_id)");
		q.append("	LEFT JOIN (SELECT ifnull(sum(dnli.delivered_quantity),0) ppoli_delivered_quantity,");
		q.append("				ppoli.port_inward_details_id");
		q.append("			FROM delivery_note_line_items dnli");
		q.append("           LEFT JOIN ppo_line_items ppoli ");
		q.append("				ON dnli.ppo_line_items_id = ppoli.id ");
		q.append("		GROUP BY ppoli.port_inward_details_id");
		q.append("		) four");
		q.append("   ON four.port_inward_details_id = one.port_inward_detail_id");

		return q.toString();
	}

	private String processSearchCriteria1(JqGridSearchParameterHolder searchParam) {
		String sqlClause = "";
		List<String> clauses = new ArrayList<String>();
		String notNullClause = "one.port_inward_detail_id is not null";
		clauses.add(notNullClause);
		if (null != searchParam && null != searchParam.getRules() && !searchParam.getRules().isEmpty()) {
			for (JqGridSearchParameterHolder.Rule r : searchParam.getRules()) {
				String clause = processSearchRule1(r);
				if (!StringUtils.isEmpty(clause)) {
					clauses.add(clause);
				}
			}
		}
		// Prepare the sqlClause
		sqlClause = prepareSqlClause(clauses);

		return sqlClause;
	}

	private String processSearchRule1(Rule r) {
		String data = r.getData();
		String field = r.getField();
		String op = r.getOp();

		String clause = "";
		if (field != null && field.equalsIgnoreCase("vendor_name")) {
			clause = "one.vendor_name like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("vessel_name")) {
			clause = "one.vessel_name like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("vessel_date")) {
			clause = "one.vessel_date like '%" + data + "%'";
		}
		// Changes-Shweta
		else if (field != null && field.equalsIgnoreCase("mill_name")) {
			clause = "one.mill_name like '%" + data + "%'";
		}

		else if (field != null && field.equalsIgnoreCase("material_type")) {
			clause = "one.material_type like '%" + data + "%'";
		}

		else if (field != null && field.equalsIgnoreCase("material_grade")) {
			clause = "one.material_grade like '%" + data + "%'";
		}

		else if (field != null && field.equalsIgnoreCase("thickness")) {
			clause = "one.thickness like '%" + data + "%'";
		}

		else if (field != null && field.equalsIgnoreCase("width")) {
			clause = "one.width like '%" + data + "%'";
		}

		else if (field != null && field.equalsIgnoreCase("length")) {
			clause = "one.length like '%" + data + "%'";
		}

		else if (field != null && field.equalsIgnoreCase("grade")) {
			clause = "one.material_grade like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("vessel_date")) {
			clause = processDateClause(data);
		} else if (field != null && field.equalsIgnoreCase("port_inward_id")) {
			clause = "one.port_inward_id = " + data;
		}

		return clause;
	}
	
	
	
	
	public Integer fetchPortInwardPackingListRecordCountForPortOutward(JqGridSearchParameterHolder searchParam) throws SQLException {
		List<PortInwardRecordVO> list = new ArrayList<PortInwardRecordVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		int count = 0;
		try {
			conn = getConnection();
			// String count_sql = " SELECT count(*) FROM port_inward_details "

//			String count_sql = " select count(*) " + " from  " + " port_inward pi "
//					+ " left join port_inward_shipment pis on pis.port_inwd_shipment_id = pi.port_inwd_shipment_id "
//					+ " left join port_inward_details pid on pid.port_inward_id = pi.port_inward_id "
//					+ processSearchCriteria(searchParam);
			
			String count_sql = "select count(*) from ("+composeQueryForPackingListToBeSentFromPort() + processSearchCriteria1(searchParam) + " ) a";

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
	
	
	public List<PackingListItemVO> fetchPortInwardPackingListForPortOutward(int pageNo, int pageSize, long total,
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

			
			String sql = composeQueryForPackingListToBeSentFromPort() + processSearchCriteria1(searchParam) + " "
					+ composeOrderByClause_2(orderByFieldName, order) + " " + composeLimitClause(pageNo, pageSize, total)
					+ ";";
			query = sql;
			log.info("query = " + query);

			cs = conn.prepareCall(query);

			rs = cs.executeQuery();
			if (null != rs) {

				while (rs.next()) {
					PackingListItemVO p = new PackingListItemVO();
					p.setPortInwardId(rs.getInt("port_inward_id"));
					p.setPortInwardShipmentId(rs.getInt("port_inwd_shipment_id"));
					p.setPortInwardDetailId(rs.getInt("port_inward_detail_id"));
					p.setVesselName(rs.getString("vessel_name"));
					p.setVesselDate(dateToString(convertSqlDateToJavaDate(rs.getDate("vessel_date"))));
					p.setGrade(rs.getString("material_grade"));
					p.setMaterialType(rs.getString("material_type"));
					p.setLength(rs.getInt("length"));
					p.setWidth(rs.getInt("width"));
					p.setThickness(rs.getDouble("thickness"));
					p.setQuantity(rs.getInt("balance_pcs"));
					p.setMillName(rs.getString("mill_name"));
					p.setBalQty(rs.getDouble("balanace_quantity"));
					p.setActualWt(rs.getDouble("ActualWt"));
					p.setActualWt_unit(rs.getString("ActualWt_unit"));
					p.setMake(rs.getString("material_make"));
					p.setVendorName(rs.getString("vendor_name"));
					list.add(p);
				} 

			}

		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return list;
	}

	private String composeQueryForPackingListToBeSentFromPort() {
		StringBuffer q = new StringBuffer();

		q.append(" select * from (");
		q.append(" SELECT one.port_inward_id,");
		q.append(" one.port_inward_detail_id,");
		q.append(" one.port_inwd_shipment_id,");
		q.append(" one.vessel_name,");
		q.append(" one.vessel_date, ");
		q.append(" one.material_grade,");
		q.append(" one.material_type,");
		q.append(" one.length,");
		q.append(" one.width,");
		q.append(" one.thickness, ");
		q.append(" ifnull(one.quantity,0) inward_quantity,");
		q.append(" one.mill_name,");
		q.append(" round(((one.length * one.width * one.thickness * one.quantity * 7.85)/1000000000),3) as balanace_quantity,");
		q.append(" one.ActualWt,");
		q.append(" one.ActualWt_unit,");
		q.append(" one.material_make,");
		q.append(" one.vendor_name,");

		q.append("  (");
		q.append("	ifnull(one.quantity,0) ");
		q.append("    - ifnull(four.ppoli_delivered_quantity,0) ");
		q.append("    - ifnull(three.Delivered_taloja,0)");
		q.append(" ) balance_pcs");

		q.append(" FROM   ((SELECT pi.port_inward_id,");
		q.append("            pi.mill_name,");
		q.append("          pi.material_make,");
		q.append("         pi.material_grade,");
		q.append("        pi.material_type,");
		q.append("        pi.description,");
		q.append("       pid.length,");
		q.append("      pid.width,");
		q.append("   pid.thickness,");
		q.append("   pid.quantity, pis.vessel_date, pis.vessel_name,pis.vendor_name,");
		q.append("   pid.port_inward_detail_id,");
		q.append("   pi.port_inwd_shipment_id,");
		q.append("	pid.be_weight as ActualWt, ");
		q.append("	pid.be_wt_unit as ActualWt_unit");
		q.append(" FROM   port_inward pi");
		q.append("       LEFT JOIN port_inward_details pid");
		q.append("              ON pi.port_inward_id = pid.port_inward_id");
		q.append(" left join port_inward_shipment pis on pi.port_inwd_shipment_id=pis.port_inwd_shipment_id");
		q.append("       GROUP  BY pid.port_inward_detail_id) one");
		q.append("      LEFT JOIN (SELECT Ifnull(Sum(ppoli.ordered_quantity), 0) ppo_quantity,");
		q.append("                       ppoli.port_inward_details_id");
		q.append("               FROM   ppo_line_items ppoli");
		q.append("               GROUP  BY port_inward_details_id) two");
		q.append("          ON two.port_inward_details_id = one.port_inward_detail_id");
		q.append("   LEFT JOIN (SELECT Ifnull(Sum(po.quantity), 0) delivered_taloja,");
		q.append("                    po.port_out_id,");
		q.append("                    pioi.port_inward_details_id");
		q.append("           FROM   port_outward po");
		q.append("                 LEFT JOIN port_inward_outward_intersection pioi");
		q.append("                        ON po.port_out_id = pioi.port_outward_id");
		q.append("         GROUP  BY pioi.port_inward_details_id) three");
		q.append("     ON three.port_inward_details_id = one.port_inward_detail_id)");
		q.append("	LEFT JOIN (SELECT ifnull(sum(dnli.delivered_quantity),0) ppoli_delivered_quantity,");
		q.append("				ppoli.port_inward_details_id");
		q.append("			FROM delivery_note_line_items dnli");
		q.append("           LEFT JOIN ppo_line_items ppoli ");
		q.append("				ON dnli.ppo_line_items_id = ppoli.id ");
		q.append("		GROUP BY ppoli.port_inward_details_id");
		q.append("		) four");
		q.append("   ON four.port_inward_details_id = one.port_inward_detail_id");
		
		q.append(" having balance_pcs > 0 ");
		q.append(" ) one ");

		return q.toString();
	}
}
