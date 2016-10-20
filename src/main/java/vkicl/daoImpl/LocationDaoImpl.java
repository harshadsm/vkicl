package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import vkicl.util.Constants;
import vkicl.util.Converter;
import vkicl.vo.LocationDetailsVO;

public class LocationDaoImpl extends BaseDaoImpl{

	private static Logger log = Logger.getLogger(PortDaoImpl.class);
	
	public List<LocationDetailsVO> getAllLocationsAsList(){
		List<LocationDetailsVO> list = new ArrayList<LocationDetailsVO>();
		
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		
		try {
			query = "select * from location order by loc_id";
						
			log.info(query);
			
			conn = getConnection();
			cs = conn.prepareCall(query);
			rs = cs.executeQuery();
			
			while(rs.next()){
				int locationId = rs.getInt("loc_id");
				String locationName = rs.getString("location");
				LocationDetailsVO vo = new LocationDetailsVO();
				vo.setLoc_id(locationId);
				vo.setLocationName(locationName);
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return list;
	}
}
