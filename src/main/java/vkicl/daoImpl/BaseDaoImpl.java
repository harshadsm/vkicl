package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class BaseDaoImpl {
	private static MysqlDataSource DS;
	private static Logger log = Logger.getLogger(BaseDaoImpl.class);
	protected static PropFileReader prop = PropFileReader.getInstance();

	public static Connection getConnection() {
		log.debug("Getting Connection");
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			if (null == DS) {
				DS = new MysqlDataSource();
				// DS.setServerName("localhost");
				// DS.setPort(3306);
				String host = null;
				String dbName = null;
				Integer port = null;
				String user = null;
				String pass = null;
				try{
				port = Integer.parseInt(System.getenv("OPENSHIFT_MYSQL_DB_PORT"));
				host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
				dbName = System.getenv("OPENSHIFT_APP_NAME");
				user = prop.getSystem("db.user");
				pass = prop.getSystem("db.pass");
				
				log.debug("Username = "+user);
				log.debug("Password = "+pass);
				
				}catch(NumberFormatException e){
					//NumberFormatException happens only when OPENSHIFT_MYSQL_DB_PORT is not a valid integer.
					//THis is an indication that the environment is not OPENSHIFT.
					//It is local environment.
					//So set the local values
					host = "localhost";
					port = 3306;
					dbName = "vkicl";
					user = prop.getSystem("db.user");
					pass = prop.getSystem("db.pass");
							
				}
				
				
				DS.setServerName(host);
				DS.setDatabaseName(dbName);
				DS.setPort(port);
				//DS.setDatabaseName(prop.getSystem("db.database"));
				DS.setUser(user);
				DS.setPassword(pass);
				DS.setNoAccessToProcedureBodies(true);
				
				DS.setCharacterEncoding("UTF8");
				DS.setUseUnicode(Boolean.TRUE);
				log.info("DataSource Created");
			}
			conn = DS.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null == conn)
			log.error("Unable to get Connection");
		return conn;
	}

	public static String formatInput(Object obj) {
		String value = "";
		if (null != obj && StringUtils.isNotEmpty(obj.toString())) {
			value = obj.toString().toUpperCase();
			value = StringEscapeUtils.escapeHtml(value);
		}
		return value;
	}

	public static String formatOutput(Object obj) {
		String value = "";
		if (null != obj && StringUtils.isNotEmpty(obj.toString())) {
			value = obj.toString().toUpperCase();
			value = StringEscapeUtils.unescapeHtml(value);
		}
		return value;
	}

	public static String toString(Object[] obj) {
		StringBuffer sb = new StringBuffer("");
		if (null != obj)
			for (Object temp : obj)
				sb.append(StringEscapeUtils.escapeHtml(temp.toString()) + ",");
		String out = sb.toString().toUpperCase();
		if (out.endsWith(","))
			out = out.substring(0, out.length() - 1);
		return out;
	}

	public static void closeDatabaseResources(Connection conn, ResultSet rs,
			Statement s) {
		if (null != conn) {
			try {
				conn.close();
				conn = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (null != rs) {
			try {
				rs.close();
				rs = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (null != s) {
			try {
				s.close();
				s = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String getCurentTime() {
		Date dt = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
		return currentTime;
	}

	public String convertStringToDate(String date) throws ParseException {
		if (null == date)
			date = "";
		if (!date.trim().equals("")) {
			date = StringEscapeUtils.escapeHtml(date);
			date = date.replaceAll(" ", "");
			Calendar cal = Calendar.getInstance();
			if (date.contains("-"))
				cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
			else
				cal.setTime(new SimpleDateFormat("dd/MM/yy").parse(date));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = sdf.format(cal.getTime());
		}
		return date;
	}
	
	
	public java.sql.Date convertStringToDate(String date, String format) throws ParseException {
		java.sql.Date d = null;
		if (null == date)
			date = "";
		if (!date.trim().equals("")) {
			date = StringEscapeUtils.escapeHtml(date);
			date = date.replaceAll(" ", "");
			Calendar cal = Calendar.getInstance();
			
			cal.setTime(new SimpleDateFormat(format).parse(date));
			d = new java.sql.Date(cal.getTimeInMillis());
		}
		return d;
	}

	public String convertDateToDisplayString(String date) throws ParseException {
		if (null == date)
			date = "";
		if (!date.trim().equals("")) {
			date = StringEscapeUtils.escapeHtml(date);
			date = date.replaceAll(" ", "");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
			date = sdf.format(cal.getTime());
		}
		return date;
	}
	
	public Date convertSqlDateToJavaDate(java.sql.Date date){
		Date d = new Date(date.getTime());
		return d;
	}
	
	public String dateToString(Date d){
		String format = "dd-MM-yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String dateString = sdf.format(d);
		return dateString;
	}

	public ArrayList<LabelValueBean> getList(UserInfoVO userInfoVO, String key) {
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String message = "";
		ArrayList<LabelValueBean> list = null;
		try {
			conn = getConnection();
			String query = prop.get(key);
			log.info("query = " + query);
			cs = conn.prepareCall(query);
			if (key.equalsIgnoreCase("query.admin.user.all")) {
				cs.setString(1, userInfoVO.getUserName());
			}
			rs = cs.executeQuery();
			list = new ArrayList<LabelValueBean>();
			if (null != rs && rs.next()) {
				do {
					String str = rs.getString(1);
					if (StringUtils.isNotBlank(str)) {
						if (!key.equalsIgnoreCase("query.admin.user.all"))
							str = str.toUpperCase();
						LabelValueBean bean = new LabelValueBean(str, str);
						list.add(bean);
					}
				} while (rs.next());
			}
			closeDatabaseResources(conn, rs, cs);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return list;
	}

	public ArrayList<LabelValueBean> getLVList(UserInfoVO userInfoVO, String key) {
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String message = "";
		ArrayList<LabelValueBean> list = null;
		try {
			conn = getConnection();
			String query = prop.get(key);
			log.info("query = " + query);
			cs = conn.prepareCall(query);
			rs = cs.executeQuery();
			list = new ArrayList<LabelValueBean>();

			LabelValueBean bean = new LabelValueBean("--", "--");
			list.add(bean);
			
			if (null != rs && rs.next()) {
				do {
					String label = rs.getString(1);
					String value = rs.getString(2);
					if (StringUtils.isNotBlank(label)
							&& StringUtils.isNotBlank(value)) {
						bean = new LabelValueBean(label, value);
						list.add(bean);
					}
				} while (rs.next());
			}
			closeDatabaseResources(conn, rs, cs);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return list;
	}

	public ArrayList<LabelValueBean> getDateList(UserInfoVO userInfoVO,
			String key) {
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String message = "";
		ArrayList<LabelValueBean> list = null;
		try {
			conn = getConnection();
			String query = prop.get(key);
			log.info("query = " + query);
			cs = conn.prepareCall(query);
			rs = cs.executeQuery();
			list = new ArrayList<LabelValueBean>();
			if (null != rs && rs.next()) {
				do {
					String str = rs.getString(1);
					if (StringUtils.isNotBlank(str)) {
						str = convertDateToDisplayString(str);
						LabelValueBean bean = new LabelValueBean(str, str);
						list.add(bean);
					}
				} while (rs.next());
			}
			closeDatabaseResources(conn, rs, cs);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return list;
	}

	public String toCountString(Object[] obj) {
		int lth = obj.length;
		Object[] unique = new Object[lth];
		Integer[] times = new Integer[lth];
		int i = 0;
		int j = 0;
		int count;
		while (i < lth) {
			Object w = obj[i];
			count = 1;
			while (++i < lth && obj[i].equals(w))
				++count;
			unique[j] = w;
			times[j++] = count;
		}
		unique = Arrays.copyOf(unique, j);
		times = Arrays.copyOf(times, j);
		return toString(times);
	}

	public String fetchFromMap(Map<String, String[]> map, String key) {
		String value = null;
		if (map.containsKey(key)) {
			value = map.get(key)[0].toUpperCase();
		}
		log.info(key + " = " + value);
		return value;
	}

	public String fetchDateFromMap(Map<String, String[]> map, String key)
			throws ParseException {
		String value = fetchFromMap(map, key);
		if (StringUtils.isNotBlank(value))
			value = convertStringToDate(value);
		return value;
	}
}
