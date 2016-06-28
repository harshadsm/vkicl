package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import vkicl.form.PortOutwardForm;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class WarehouseDaoImpl2 extends BaseDaoImpl {
	static Logger log = Logger.getLogger(WarehouseDaoImpl2.class);
	static PropFileReader prop = PropFileReader.getInstance();


	public PortOutwardForm addWarehouseInwardData(PortOutwardForm form,
			UserInfoVO userInfoVO) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		try {
			
			conn = getConnection();

			query = prop.get("sp.warehouse.inward.insert");
			log.info("query = " + query);
			log.info("form = " + form);
			cs = conn.prepareCall(query);

			
			cs.setString(1, formatInput(form.getVehicleNumber()));
			cs.setString(2, formatInput(""));
			
				cs.setString(3, convertStringToDate(form.getVehicleDate()));
			
			cs.setString(4, formatInput(""));
			
			cs.setString(5, toString(form.getMaterialType()));
			cs.setString(6, (form.getMillName()));
			cs.setString(7, formatInput(""));
			cs.setString(8, toString(form.getGrade()));

			cs.setString(9, toString(form.getLength()));
			cs.setString(10, toString(form.getWidth()));
			cs.setString(11, toString(form.getThickness()));
			cs.setString(12, formatInput(""));
			cs.setString(13, formatInput(""));
			cs.setString(14, toString(form.getSecWt()));
			cs.setString(15, formatInput(""));
			cs.setString(16, toString(form.getQty()));
			cs.setString(17, formatInput(""));
			cs.setString(18, formatInput(""));
			cs.setString(19, formatInput(""));
			cs.setString(20, formatInput(""));
			cs.setString(21, formatInput(""));
			cs.setString(22, formatInput(""));
			cs.setString(23, formatInput(""));
			cs.setString(24, formatInput(""));
			cs.setString(25, formatInput(""));

			cs.setString(26, formatInput(""));
			cs.setInt(27, 0);
			cs.setString(28, userInfoVO.getUserName());
			cs.registerOutParameter(29, java.sql.Types.VARCHAR);
			cs.executeUpdate();
			message = cs.getString(29);
			log.info("message = " + message);

			form.clear();
			userInfoVO.setMessage(message);
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