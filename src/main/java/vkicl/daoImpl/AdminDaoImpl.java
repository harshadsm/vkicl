package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import vkicl.form.UserForm;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class AdminDaoImpl extends BaseDaoImpl {
	private static Logger log = Logger.getLogger(AdminDaoImpl.class);
	private static PropFileReader prop = PropFileReader.getInstance();

	public UserForm updateUser(UserForm form, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		try {
			conn = getConnection();
			query = prop.get("sp.admin.user.update");
			log.info("query = " + query);
			log.info("form = " + form);
			cs = conn.prepareCall(query);
			if (form.getGenericListener().equalsIgnoreCase("Add"))
				cs.setString(1, form.getNewUserName());
			else
				cs.setString(1, form.getCurrentUserName());
			cs.setString(2, form.getPassword());
			cs.setString(3, form.getGenericListener().toLowerCase());
			cs.setString(4, toString(form.getUserAccess()));
			cs.setString(5, userInfoVO.getUserName());
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);

			cs.executeQuery();
			message = cs.getString(6);
			log.info("message = " + message);
			userInfoVO.setMessage(message);

			form.clear();

		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return form;
	}
}
