package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class LoginDaoImpl extends BaseDaoImpl {
	private static Logger log = Logger.getLogger(LoginDaoImpl.class);
	private static PropFileReader prop = PropFileReader.getInstance();

	public UserInfoVO validateLogin(UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		try {
			conn = getConnection();
			query = prop.get("sp.login");
			log.info("Username = " + userInfoVO.getUserName());
			log.info("Password = " + userInfoVO.getPassword());
			log.info("query = " + query);
			cs = conn.prepareCall(query);
			cs.setString(1, userInfoVO.getUserName());
			cs.setString(2, userInfoVO.getPassword());
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);
			// cs.registerOutParameter(4, OracleTypes.CURSOR);

			rs = cs.executeQuery();

			message = cs.getString(3);

			if (message.equalsIgnoreCase("Valid")) {

				ArrayList<String> appAccessList = new ArrayList<String>();

				if (null != rs && rs.next()) {
					do {
						appAccessList.add(rs.getString("access"));
					} while (rs.next());
					userInfoVO.setAppAccessList(appAccessList);
					userInfoVO.setLoggedIn(true);
				} else {
					message = rs.getString(1);
					log.error(message);
					closeDatabaseResources(conn, rs, cs);
					userInfoVO.setMessage(message);
					return userInfoVO;
				}
			} else {
				closeDatabaseResources(conn, rs, cs);
				log.error(message);
				userInfoVO.setMessage(message);
				return userInfoVO;
			}

			// 100 MB
			String querySetLimit = "SET GLOBAL max_allowed_packet=1048576000;";
			Statement stSetLimit = conn.createStatement();
			stSetLimit.execute(querySetLimit);
			log.info("Max Upload limit set to 100 Mb successfully");
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return userInfoVO;
	}
}
