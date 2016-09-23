package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import vkicl.report.bean.WarehouseLocationBean;
import vkicl.util.Constants;
import vkicl.util.Converter;
import vkicl.util.JqGridSearchParameterHolder;
import vkicl.util.JqGridSearchParameterHolder.Rule;
import vkicl.vo.LocationDetailsVO;
import vkicl.vo.PackingListItemVO;
import vkicl.vo.PortInwardRecordVO;
import vkicl.vo.UserInfoVO;

public class StockBalDaoImpl extends BaseDaoImpl {
	private Logger log = Logger.getLogger(PortOutwardPackingListDaoImpl.class);

	
	
	
	public String updateStockBalance(Map<String, String[]> map,
			UserInfoVO userInfoVO) throws SQLException {
		List<LocationDetailsVO> list = new ArrayList<LocationDetailsVO>();
		Connection conn = null;
		ResultSet rs = null; 
		CallableStatement cs = null;
		String query = ""; 
		String message = "";  
		int count = 0;
		try {
			conn = getConnection();

			query = prop.get("sp.report.stock.balance.edit");
			log.info("query = " + query);
			
			cs = conn.prepareCall(query);
			
			cs.setString(1, fetchFromMap(map, "location"));
			cs.setString(2, fetchFromMap(map, "stock_id"));
			cs.setString(3, userInfoVO.getUserName());
			
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			rs = cs.executeQuery();
			message = cs.getString(4);
			log.info("message = " + message);
			
			
		} 
		catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			log.error(message);
		} finally
		{
			closeDatabaseResources(conn, rs, cs);
		}
	
		return message;
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

	public Integer fetchPortOutwardPackingListRecordCount(JqGridSearchParameterHolder searchParam) throws SQLException {
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
			+" port_outward po "
			+" left join port_outward_shipment pis on pis.port_out_shipment_id = po.port_out_shipment_id "
			
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

	private String processSearchCriteria1(JqGridSearchParameterHolder searchParam) {
		String sqlClause = "";
		List<String> clauses = new ArrayList<String>();
		String notNullClause = "po.port_out_id is not null and pis.warehouse_name !='' and po.warehouse_inward_flag!=1";
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

	
	private String processSearchCriteria(JqGridSearchParameterHolder searchParam) {
		String sqlClause = "";
		List<String> clauses = new ArrayList<String>();
		String notNullClause = "po.port_out_id is not null and pis.warehouse_name !='' ";
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
			clause = "po.vessel_name like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("grade")) {
			clause = "pi.material_grade like '%" + data + "%'";}
			
			//added-shweta
			
			else if (field != null && field.equalsIgnoreCase("vessel_Date")) {
				clause = "po.vessel_Date like '%" + data + "%'";
			}
			else if (field != null && field.equalsIgnoreCase("vehicle_date")) {
				clause = "pis.vehicle_date like '%" + data + "%'";
			}
			else if (field != null && field.equalsIgnoreCase("vehicle_number")) {
				clause = "pis.vehicle_number like '%" + data + "%'";
			}
			else if (field != null && field.equalsIgnoreCase("mill_name")) {
				clause = "pi.mill_name like '%" + data + "%'";
			}
			else if (field != null && field.equalsIgnoreCase("material_type")) {
				clause = "po.material_type like '%" + data + "%'";
			}
			else if (field != null && field.equalsIgnoreCase("thickness")) {
				clause = "po.thickness like '%" + data + "%'";
			}
			else if (field != null && field.equalsIgnoreCase("width")) {
				clause = "po.width like '%" + data + "%'";
			}
			else if (field != null && field.equalsIgnoreCase("length")) {
				clause = "po.length like '%" + data + "%'";
			}
		
			
		else if (field != null && field.equalsIgnoreCase("vessel_date")) {
			clause = processDateClause(data);
		} else if (field != null && field.equalsIgnoreCase("port_inward_id")) {
			clause = "port_out_id = " + data ;
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
			
			if(orderByFieldName.equalsIgnoreCase("length")){
				orderByClause = " ORDER BY  length "+order+" ";
			}else if(orderByFieldName.equalsIgnoreCase("width")){
				orderByClause = " ORDER BY  width "+order+" ";
			}else if(orderByFieldName.equalsIgnoreCase("thickness")){
				orderByClause = " ORDER BY  thickness "+order+" ";
			}else if(orderByFieldName.equalsIgnoreCase("port_inward_detail_id")){
				orderByClause = " ORDER BY  port_inward_detail_id "+order+" ";
			}else if(orderByFieldName.equalsIgnoreCase("vessel_date")){
				orderByClause = " ORDER BY  pis.vessel_date "+order+" ";
			}else if(orderByFieldName.equalsIgnoreCase("vessel_name")){
				orderByClause = " ORDER BY  pis.vessel_name "+order+" ";
			}else if(orderByFieldName.equalsIgnoreCase("grade")){
				orderByClause = " ORDER BY  pi.material_grade "+order+" ";
			}
			
			 
		}
		return orderByClause;
	}
}
