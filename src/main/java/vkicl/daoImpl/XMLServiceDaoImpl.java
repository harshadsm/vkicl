package vkicl.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import vkicl.util.PropFileReader;

public class XMLServiceDaoImpl extends BaseDaoImpl {
	private static Logger log = Logger.getLogger(XMLServiceDaoImpl.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ArrayList<String> getXML(String query, int count, String params[]) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<String> list = null;
		try {

			conn = getConnection();
			log.info("query = " + query);

			if (null != query) {
				ps = conn.prepareStatement(query);
				if (count > 0 && null != params) {
					for (int i = 0; i < params.length; i++)
						ps.setObject((i + 1), params[i]);
				}

				rs = ps.executeQuery();
				if (null != rs && rs.next()) {
					list = new ArrayList<String>();
					do {
						list.add(formatOutput(rs.getString(1)));
					} while (rs.next());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabaseResources(conn, rs, ps);
		}
		return list;
	}
}
