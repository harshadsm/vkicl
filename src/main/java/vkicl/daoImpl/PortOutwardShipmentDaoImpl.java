package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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
import vkicl.vo.PortOutwardPostDataContainerVO;
import vkicl.vo.PortOutwardShipmentVO;
import vkicl.vo.UserInfoVO;
import vkicl.vo.WarehouseOutwardVO3;

public class PortOutwardShipmentDaoImpl extends BaseDaoImpl{

	private Logger logger = Logger.getLogger(PortOutwardShipmentDaoImpl.class);

	public Long savePortOutwardShipment(PortOutwardPostDataContainerVO postDataContainer, UserInfoVO userInfo) throws Exception {
		Long savedRecordId = -1L;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		
		String custName = "";
		String warehouseName = "";
		
		String destinationName = postDataContainer.getDestinationName();
		if(destinationName!=null && destinationName.equalsIgnoreCase("TALOJA")){
			warehouseName = destinationName;
		}else{
			custName = destinationName;
		}
		String vehicleDateStr = postDataContainer.getVehicleDate();
		java.sql.Date vehicleDate = Converter.dateToSqlDate(Converter.stringToDate(vehicleDateStr, Constants.Apps.DATE_FORMAT));
		String vehicleNumber = postDataContainer.getVehicleNumber();
		
		
		try {
			String query = "INSERT INTO port_outward_shipment "
					+ " (warehouse_name, customer_name, vehicle_number, "
					+ " vehicle_date, create_ui, update_ui, create_ts, update_ts) "
					+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
						
			logger.info(query);
			
			conn = getConnection();
			cs = conn.prepareCall(query);
			cs.setString(1, warehouseName);
			cs.setString(2, custName);
			cs.setString(3, vehicleNumber);
			cs.setDate(4, vehicleDate);
			cs.setString(5, userInfo.getUserName());
			cs.setString(6, userInfo.getUserName());
			cs.setString(7, getCurentTime());
			cs.setString(8, getCurentTime());
			
			int count = cs.executeUpdate();
			
			ResultSet result = cs.getGeneratedKeys();
			if(count > 0){
				result.next();
				savedRecordId = result.getLong(1);
			}
			
		} catch (Exception e) {
			throw e;
		}finally {
			closeDatabaseResources(conn, rs, cs);
		}

		return savedRecordId;
	}
	
	public Integer fetchPortOutwardShipmentRecordCount(String orderByFieldName, String order, JqGridSearchParameterHolder searchParam) throws SQLException {
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
			String count_sql = "select count(*) from ("+composeQueryForPortOutwardShipmentThatAreWarehouseInwarded(orderByFieldName, order, searchParam)+" ) a";
			logger.info("query = " + count_sql);

			cs = conn.prepareCall(count_sql);

			rs = cs.executeQuery();
			if (null != rs && rs.next()) {

				do {
					count = rs.getInt(1);

					logger.debug("Row count === " + count);
				} while (rs.next());

			}

		} catch (Exception e) {
			logger.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return count;
	}
	
	private String composeQueryForPortOutwardShipmentThatAreWarehouseInwarded(String orderByFieldName, String order, JqGridSearchParameterHolder searchParam) {
		String query;
		StringBuffer q = new StringBuffer();
		q.append(" select   ");
		q.append( " port_out_shipment_id, " );
		q.append( " warehouse_name, " );
		q.append( " customer_name, " );
		q.append( " vehicle_number, " );
		q.append( " vehicle_date, " );
		q.append( " create_ui, " );
		q.append( " update_ui, " );
		q.append( " create_ts, " );
		q.append( " update_ts, " );
		q.append( " invoice, " );
		q.append( " warehouse_inward_flag, " );
		q.append( " actual_weight " );
		q.append(" from port_outward_shipment ");
//		q.append(" where port_out_shipment_id in ( ");
//		q.append(" select port_out_shipment_id from port_outward where port_out_id in( ");
//		q.append(" select distinct port_outward_id from port_outward_warehouse_inward_intersection ");
//		q.append(" ) ");
//		q.append(" ) ");
		
		//q.append(" order by warehouse_outward_id desc;");
		
		//String sql = " select * from warehouse_outward "
		String sql = q.toString()
				+ processSearchCriteria(searchParam) + " " + composeOrderByClause(orderByFieldName, order) ;
		query = sql;
		return query;
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
				logger.error("some error",e);
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
	
	
	public List<PortOutwardShipmentVO> fetchPortOutwardShipmentList(int pageNo, int pageSize, long total,
			String orderByFieldName, String order, JqGridSearchParameterHolder searchParam) throws SQLException {
		List<PortOutwardShipmentVO> list = new ArrayList<PortOutwardShipmentVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";

		try {
			conn = getConnection();

			query = composeQueryForPortOutwardShipmentThatAreWarehouseInwarded(orderByFieldName, order, searchParam)
			+ " " + composeLimitClause(pageNo, pageSize, total);
			logger.info("query = " + query);

			cs = conn.prepareCall(query);

			rs = cs.executeQuery();
			if (null != rs && rs.next()) {

				do {
					PortOutwardShipmentVO vo = new PortOutwardShipmentVO();
					vo.setActual_weight(rs.getDouble("actual_weight"));
					vo.setCreate_ts(dateToString(rs.getDate("create_ts")));
					vo.setCreate_ui(rs.getString("create_ui"));
					vo.setCustomer_name(rs.getString("customer_name"));
					vo.setInvoice(rs.getString("invoice"));
					vo.setPort_out_shipment_id(rs.getInt("port_out_shipment_id"));
					vo.setUpdate_ts(dateToString(rs.getDate("update_ts")));
					vo.setUpdate_ui(rs.getString("update_ui"));
					vo.setVehicle_date(dateToString(rs.getDate("vehicle_date")));
					vo.setVehicle_number(rs.getString("vehicle_number"));
					vo.setWarehouse_inward_flag(rs.getInt("warehouse_inward_flag"));
					vo.setWarehouse_name(rs.getString("warehouse_name"));
					
					list.add(vo);
					
				} while (rs.next());

			}

		} catch (Exception e) {
			logger.error("Some error", e);
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

	public void updateActualWeightOfPortOutwardShipment(Integer portOutwardShipmentId, Double actualWeight) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		StringBuffer q = new StringBuffer();

		try {
			conn = getConnection();

			q.append(" update port_outward_shipment set actual_weight = ? where port_out_shipment_id = ? ");
			String query = q.toString();

			logger.info("query = " + query);
			cs = conn.prepareCall(query);
			cs.setDouble(1, actualWeight);
			cs.setInt(2, portOutwardShipmentId);
			int recordsUpdated = cs.executeUpdate();
			
			logger.debug("Update successful "+recordsUpdated);
		} catch (Exception e) {
			logger.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		
	}
}
