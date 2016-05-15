package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class PortInwardOutwardIntersectionDaoImpl extends BaseDaoImpl {

	private Logger logger = Logger.getLogger(PortInwardOutwardIntersectionDaoImpl.class);

	public Integer save(Integer portInwardId, Integer portInwardDetailId, Long portOutwardId) throws Exception {
		int savedRecordId = -1;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;


		try {
			
			String query = "INSERT INTO port_inward_outward_intersection " + " ("
					+ " port_inward_details_id, port_outward_id, port_inward_id "
					+ " ) "
					+ " VALUES ( ?, ?, ?)";

			logger.info(query);

			conn = getConnection();
			cs = conn.prepareCall(query);

			cs.setInt(1, portInwardDetailId);
			cs.setLong(2, portOutwardId);
			cs.setInt(3, portInwardId);
			savedRecordId = cs.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}

		return savedRecordId;
	}
}
