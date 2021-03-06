package vkicl.daoImpl;

import java.awt.Polygon;
import java.awt.Shape;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import vkicl.form.StockForm;
import vkicl.report.bean.StockBean;
import vkicl.report.bean.WarehouseLocationBean;
import vkicl.report.form.StockReportForm;
import vkicl.services.geometry.GeometryService;
import vkicl.services.geometry.GeometryServiceImpl;
import vkicl.util.Constants;
import vkicl.util.Converter;
import vkicl.util.JqGridSearchParameterHolder;
import vkicl.util.JqGridSearchParameterHolder.Rule;
import vkicl.vo.LocationDetailsVO;
import vkicl.vo.PackingListItemVO;
import vkicl.vo.PortInwardDetailsVO;
import vkicl.vo.PortInwardRecordVO;
import vkicl.vo.StockBalanceDetailsVO;

import vkicl.vo.UserInfoVO;

public class StockBalDaoImpl extends BaseDaoImpl {
	private Logger log = Logger.getLogger(StockBalDaoImpl.class);

	public Integer fetchStockBalRecordCount(JqGridSearchParameterHolder searchParam) throws SQLException {
		List<StockBalanceDetailsVO> list = new ArrayList<StockBalanceDetailsVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		int count = 0;
		try {
			conn = getConnection();
			// String count_sql = " SELECT count(*) FROM port_inward_details "

			String count_sql = " select count(*) " + " from  " + " stock_balance  "

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

	public StockBalanceDetailsVO fetchStockBalById(int id) throws SQLException {
		GeometryService geometryService = new GeometryServiceImpl();
		StockBalanceDetailsVO vo = null;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		int count = 0;
		try {
			conn = getConnection();

			// For whatever weired reasons, ST_AsText does not work on server.
			// AsText works.
			// In fact any function prefixed with ST_ does not work on server.
			// Hence removing ST_ prefix from the queries.
			// query= "SELECT stock_balance_id,mill_name, material_type,
			// material_make, grade,length, thickness, width,
			// ST_AsText(plate_shape) plate_shape_text, ST_Area(plate_shape) as
			// plate_area"
			// + " from stock_balance where stock_balance_id=? and is_cut!=1 ";
			query = "SELECT stock_balance_id,mill_name, material_type, material_make, grade,length, thickness, width, "
					+ " AsText(plate_shape) plate_shape_text, Area(plate_shape) as plate_area, quantity, location, heat_no, plate_no, warehouse_inward_id"
					+ " from stock_balance where stock_balance_id=? ";
			// + " and is_cut!=1 ";

			log.info("query = " + query);
			cs = conn.prepareCall(query);
			cs.setInt(1, id);
			rs = cs.executeQuery();

			if (null != rs && rs.next()) {

				do {

					int stockBalId = rs.getInt(1);
					String mill_name = rs.getString(2);
					String material_type = rs.getString(3);
					String material_make = rs.getString(4);
					String grade = rs.getString(5);
					int length = rs.getInt(6);
					double thickness = rs.getDouble(7);
					int width = rs.getInt(8);
					Polygon plateShape = geometryService.toPolygon(rs.getString("plate_shape_text"));
					double plateArea = rs.getDouble("plate_area");
					int quantity = rs.getInt("quantity");

					vo = new StockBalanceDetailsVO();
					vo.setStockBalId(stockBalId);
					vo.setMillName(mill_name);
					vo.setMaterialType(material_type);
					vo.setMake(material_make);
					vo.setGrade(grade);
					vo.setLength(length);
					vo.setThickness(thickness);
					vo.setWidth(width);
					vo.setPlateArea(plateArea);
					vo.setPlateShape(plateShape);
					vo.setQuantity(quantity);
					vo.setLocation(rs.getString("location"));
					vo.setHeat_no(rs.getString("heat_no"));
					vo.setPlate_no(rs.getString("plate_no"));
					vo.setWarehouseInwardId(rs.getInt("warehouse_inward_id"));
					break; // WE EXPECT/WANT ONLY 1 record.
				} while (rs.next());

			}

		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return vo;
	}

	public List<StockBalanceDetailsVO> fetchCuttingDetailsList(Integer StockBalId) {
		List<StockBalanceDetailsVO> list = new ArrayList<StockBalanceDetailsVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		try {
			conn = getConnection();

			query = "SELECT stock_balance_id,mill_name, material_type, material_make, grade,length, thickness, width, location, heat_no, plate_no"
					+ " FROM stock_balance WHERE stock_balance_id=? and is_cut!=1";

			log.info("query = " + query);
			cs = conn.prepareCall(query);
			cs.setInt(1, StockBalId);
			rs = cs.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					int stockBalId = rs.getInt(1);
					String mill_name = rs.getString(2);
					String material_type = rs.getString(3);
					String material_make = rs.getString(4);
					String grade = rs.getString(5);
					int length = rs.getInt(6);
					double thickness = rs.getDouble(7);
					int width = rs.getInt(8);

					StockBalanceDetailsVO vo = new StockBalanceDetailsVO();
					vo.setStockBalId(stockBalId);
					vo.setMillName(mill_name);
					vo.setMaterialType(material_type);
					vo.setMake(material_make);
					vo.setGrade(grade);
					vo.setLength(length);
					vo.setThickness(thickness);
					vo.setWidth(width);
					vo.setLocation(rs.getString(9));
					vo.setHeat_no(rs.getString(10));
					vo.setPlate_no(rs.getString(11));
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

	public List<StockBalanceDetailsVO> fetchStockBalList(int pageNo, int pageSize, long total, String orderByFieldName,
			String order, JqGridSearchParameterHolder searchParam) throws SQLException {
		List<StockBalanceDetailsVO> list = new ArrayList<StockBalanceDetailsVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		int count = 0;
		try {
			conn = getConnection();

			String sql = "SELECT stock_balance_id,MILL_NAME, MATERIAL_TYPE, MATERIAL_MAKE, GRADE, QUANTITY,LENGTH, "
					+ " THICKNESS, WIDTH, LOCATION, IS_RECTANGULAR, quantity, heat_no, plate_no,location  FROM stock_balance sb "
					+ processSearchCriteria(searchParam) + " " + composeOrderByClause(orderByFieldName, order) + " "
					+ composeLimitClause(pageNo, pageSize, total) + ";";

			query = sql;
			log.info("query = " + query);

			cs = conn.prepareCall(query);

			rs = cs.executeQuery();

			if (null != rs && rs.next()) {
				// reportList = new ArrayList<StockBean>();
				do {
					StockBalanceDetailsVO report = new StockBalanceDetailsVO();

					report.setStockBalId(rs.getInt("stock_balance_id"));
					report.setMake(formatOutput(rs.getString("material_make")));
					report.setGrade(formatOutput(rs.getString("grade")));
					report.setMillName(formatOutput(rs.getString("mill_name")));
					report.setMaterialType(formatOutput(rs.getString("material_type")));
					report.setLength(rs.getInt("length"));
					report.setWidth(rs.getInt("width"));
					report.setThickness(rs.getDouble("thickness"));
					report.setIsRectangular(rs.getInt("is_rectangular"));
					report.setQuantity(rs.getInt("quantity"));
					report.setHeat_no(formatOutput(rs.getString("heat_no")));
					report.setPlate_no(formatOutput(rs.getString("plate_no")));
					report.setLocation(formatOutput(rs.getString("location")));
					list.add(report);
				} while (rs.next());

			}
		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return list;
	}

	public String updateStockBalance(Map<String, String[]> map, UserInfoVO userInfoVO) throws SQLException {
		List<LocationDetailsVO> list = new ArrayList<LocationDetailsVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "Success";
		int count = 0;
		PreparedStatement statement = null;
		try {
			conn = getConnection();

			/*
			 * query = prop.get("sp.report.stock.balance.edit"); log.info(
			 * "query = " + query);
			 * 
			 * cs = conn.prepareCall(query);
			 * 
			 * cs.setString(1, fetchFromMap(map, "location")); cs.setString(2,
			 * fetchFromMap(map, "stock_id")); cs.setString(3,
			 * userInfoVO.getUserName());
			 * 
			 * cs.registerOutParameter(4, java.sql.Types.VARCHAR); rs =
			 * cs.executeQuery(); message = cs.getString(4); log.info(
			 * "message = " + message);
			 */

			String sql = "update stock_balance s set s.location = ?,s.update_ui = ?,s.update_ts = NOW()  WHERE stock_balance_id=?";
			statement = conn.prepareStatement(sql);
			statement.setString(1, fetchFromMap(map, "location"));
			statement.setString(2, userInfoVO.getUserName());
			statement.setString(3, fetchFromMap(map, "stock_id"));

			statement.executeUpdate();
			log.info("message = " + message);

		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			log.error(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}

		return message;
	}

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

	private String processSearchCriteria(JqGridSearchParameterHolder searchParam) {
		String sqlClause = "";
		List<String> clauses = new ArrayList<String>();
		// String notNullClause = "is_cut !=1";
		// clauses.add(notNullClause);
		
		String quantityNot0Clause = " quantity > 0 ";
		clauses.add(quantityNot0Clause);
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

		// added-shweta

		if (field != null && field.equalsIgnoreCase("mill_name")) {
			clause = "mill_name like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("materialType")) {
			clause = "material_type like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("thickness")) {
			clause = "thickness = " + data;
		} else if (field != null && field.equalsIgnoreCase("width")) {
			clause = "width >= " + data;
		} else if (field != null && field.equalsIgnoreCase("length")) {
			clause = "length >=" + data;
		} else if (field != null && field.equalsIgnoreCase("grade")) {
			clause = "grade like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("heat_no")) {
			clause = "heat_no like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("plate_no")) {
			clause = "plate_no like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("quantity")) {
			clause = "quantity > " + data ;
		} else if (field != null && field.equalsIgnoreCase("location")) {
			clause = "location like '%" + data + "%'";
		} else if (field != null && field.equalsIgnoreCase("plateArea")) {
			clause = "plate_area >= " + data;
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
			} else if (orderByFieldName.equalsIgnoreCase("stock_balance_id")) {
				orderByClause = " ORDER BY  stock_balance_id " + order + " ";
			} else if (orderByFieldName.equalsIgnoreCase("material_type")) {
				orderByClause = " ORDER BY  materialType " + order + " ";
			} else if (orderByFieldName.equalsIgnoreCase("mill_name")) {
				orderByClause = " ORDER BY  millName " + order + " ";
			} else if (orderByFieldName.equalsIgnoreCase("grade")) {
				orderByClause = " ORDER BY  grade " + order + " ";
			}

		}
		return orderByClause;
	}

	public Long insertStockBalanceCuttingDetails(StockBalanceDetailsVO vo, UserInfoVO userInfoVO) throws SQLException {
		List<StockBalanceDetailsVO> list = new ArrayList<StockBalanceDetailsVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "Success";
		int count = 0;
		// PreparedStatement statement = null;
		Long savedRecordId = -1L;
		try {
			conn = getConnection();

			String sql = "INSERT INTO  stock_balance (mill_name, material_make, "
					+ " material_type, grade, length, width, thickness, "
					+ "  create_ui, update_ui, create_ts, update_ts) " + " VALUES(?,?,?,?,?,?,?,?,?,?,?)";

			cs = conn.prepareCall(sql);
			cs.setString(1, vo.getMillName());
			cs.setString(2, vo.getMake());
			cs.setString(3, vo.getMaterialType());
			cs.setString(4, vo.getGrade());
			cs.setInt(5, vo.getLength());
			cs.setInt(6, vo.getWidth());
			cs.setDouble(7, vo.getThickness());
			cs.setString(8, userInfoVO.getUserName());
			cs.setString(9, userInfoVO.getUserName());
			cs.setString(10, getCurentTime());
			cs.setString(11, getCurentTime());

			int count1 = cs.executeUpdate();

			ResultSet result = cs.getGeneratedKeys();
			if (count1 > 0) {
				result.next();
				savedRecordId = result.getLong(1);

				log.info("message = " + message);
			}

		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			log.error(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}

		return savedRecordId;
	}

	public String updateStockBalanceCut(Integer StockBalId, Integer quantity, UserInfoVO userInfoVO)
			throws SQLException {
		List<StockBalanceDetailsVO> list = new ArrayList<StockBalanceDetailsVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "Success";
		int count = 0;
		PreparedStatement statement = null;
		try {
			conn = getConnection();
			/*
			 * query = prop.get("sp.report.stock.balance.edit"); log.info(
			 * "query = " + query);
			 * 
			 * cs = conn.prepareCall(query);
			 * 
			 * cs.setString(1, fetchFromMap(map, "location")); cs.setString(2,
			 * fetchFromMap(map, "stock_id")); cs.setString(3,
			 * userInfoVO.getUserName());
			 * 
			 * cs.registerOutParameter(4, java.sql.Types.VARCHAR); rs =
			 * cs.executeQuery(); message = cs.getString(4); log.info(
			 * "message = " + message);
			 */

			Integer balQuantity = (quantity - 1);

			String sql = "update stock_balance s set s.is_cut = ?, quantity=?, s.update_ui = ?,s.update_ts = NOW()  WHERE stock_balance_id=?";
			statement = conn.prepareStatement(sql);
			statement.setString(1, "1");
			statement.setInt(2, balQuantity);
			statement.setString(3, userInfoVO.getUserName());
			statement.setInt(4, StockBalId);

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

	public Long insertStockBalanceCuttingDetails(StockForm stockForm, UserInfoVO userInfoVO) throws SQLException {
		List<StockBalanceDetailsVO> list = new ArrayList<StockBalanceDetailsVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "Success";
		int count = 0;
		PreparedStatement statement = null;
		Long savedRecordId = -1L;
		try {
			conn = getConnection();

			String sql = "INSERT INTO  stock_balance (mill_name, material_make, "
					+ " material_type, grade, length, width, thickness, "
					+ "  create_ui, update_ui, create_ts, update_ts) " + " VALUES(?,?,?,?,?,?,?,?,?,?,?)";

			/*
			 * int recordCount = stockForm.getThickness().length; Double[]
			 * thickness = stockForm.getThickness(); Integer[] width =
			 * stockForm.getWidth(); Integer[] length = stockForm.getLength();
			 * String[] millname = stockForm.getMillName(); String [] make =
			 * stockForm.getMake(); String [] grade = stockForm.getGrade();
			 * String [] materialtype = stockForm.getMaterialType();
			 */
			// Integer[] grade = stockForm.getLength();
			// Integer[] grade = stockForm.getLength();

			// for (int i = 0; i < recordCount; i++) {
			// if (thickness[i] == 0d && width[i] == 0 && length[i] == 0) {
			// logger.debug("Ignored empty row");
			// } else {
			statement = conn.prepareStatement(sql);
			statement.setString(1, stockForm.getMillName());
			statement.setString(2, stockForm.getMake());
			statement.setString(3, stockForm.getMaterialType());
			statement.setString(4, stockForm.getGrade());
			statement.setInt(5, stockForm.getLength());
			statement.setInt(6, stockForm.getWidth());
			statement.setDouble(7, stockForm.getThickness());
			statement.setString(8, userInfoVO.getUserName());
			statement.setString(9, userInfoVO.getUserName());
			statement.setString(10, getCurentTime());
			statement.setString(11, getCurentTime());

			int count1 = statement.executeUpdate();

			ResultSet result = statement.getGeneratedKeys();
			if (count1 > 0) {
				result.next();
				savedRecordId = result.getLong(1);

				log.info("message = " + message);
			}
			// }
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			log.error(message);
		} finally {
			closeDatabaseResources(conn, rs, statement);
		}

		return savedRecordId;
	}

	public String updateStockBalanceShape(String Sql) throws SQLException {
		List<StockBalanceDetailsVO> list = new ArrayList<StockBalanceDetailsVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "Success";
		int count = 0;
		PreparedStatement statement = null;
		try {
			conn = getConnection();

			// String sql = Sql;
			statement = conn.prepareStatement(Sql);
			// statement.setString(1, "1");
			// statement.setString(2, userInfoVO.getUserName());
			// statement.setInt(3, StockBalId);

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

	public String insertCutPlateDetails(String Sql) throws SQLException {
		List<StockBalanceDetailsVO> list = new ArrayList<StockBalanceDetailsVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "Success";
		int count = 0;
		PreparedStatement statement = null;
		try {
			conn = getConnection();

			// String sql = Sql;
			statement = conn.prepareStatement(Sql);
			// statement.setString(1, "1");
			// statement.setString(2, userInfoVO.getUserName());
			// statement.setInt(3, StockBalId);

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

	public Shape fetchplateShape(int id) throws SQLException {
		StockBalanceDetailsVO vo = null;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		int count = 0;
		Shape plateShape = null;

		try {
			conn = getConnection();
			// For whatever weired reasons, ST_AsText does not work on server.
			// AsText works.
			// In fact any function prefixed with ST_ does not work on server.
			// Hence removing ST_ prefix from the queries.
			// query= "SELECT ST_AsText(plate_shape) as plate_shape FROM
			// stock_balance "
			// + " where stock_balance_id=?";
			query = "SELECT AsText(plate_shape) as plate_shape   FROM stock_balance " + " where stock_balance_id=?";

			log.info("query = " + query);
			cs = conn.prepareCall(query);
			cs.setInt(1, id);
			rs = cs.executeQuery();

			if (null != rs && rs.next()) {

				do {

					vkicl.services.geometry.GeometryServiceImpl goemetry = new vkicl.services.geometry.GeometryServiceImpl();

					plateShape = goemetry.toPolygon(rs.getString("plate_shape"));

				} while (rs.next());

			}

		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return plateShape;
	}
	
	
	public String updateStockLocation(Integer stockId, String newLocation, UserInfoVO userInfoVO, String remarks) throws SQLException {
		List<LocationDetailsVO> list = new ArrayList<LocationDetailsVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "Success";
		int count = 0;
		PreparedStatement statement = null;
		try {
			conn = getConnection();

			/*
			 * query = prop.get("sp.report.stock.balance.edit"); log.info(
			 * "query = " + query);
			 * 
			 * cs = conn.prepareCall(query);
			 * 
			 * cs.setString(1, fetchFromMap(map, "location")); cs.setString(2,
			 * fetchFromMap(map, "stock_id")); cs.setString(3,
			 * userInfoVO.getUserName());
			 * 
			 * cs.registerOutParameter(4, java.sql.Types.VARCHAR); rs =
			 * cs.executeQuery(); message = cs.getString(4); log.info(
			 * "message = " + message);
			 */

			String sql = "update stock_balance s set s.location = ?,s.update_ui = ?, s.remark = ?, s.update_ts = NOW()  WHERE stock_balance_id=?";
			statement = conn.prepareStatement(sql);
			statement.setString(1, newLocation);
			statement.setString(2, userInfoVO.getUserName());
			statement.setString(3, remarks);
			
			statement.setInt(4, stockId);

			statement.executeUpdate();
			log.info("message = " + message);

		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			log.error(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}

		return message;
	}
}
