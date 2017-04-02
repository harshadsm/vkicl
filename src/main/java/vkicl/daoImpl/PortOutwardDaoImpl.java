package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import vkicl.report.bean.PortOutwardBean2;
import vkicl.util.Constants;
import vkicl.util.Converter;
import vkicl.vo.PortOutwardRecordVO;
import vkicl.vo.UserInfoVO;
import vkicl.vo.WarehouseInwardRecordVO;

public class PortOutwardDaoImpl extends BaseDaoImpl {

	private Logger logger = Logger.getLogger(PortOutwardDaoImpl.class);

	public Long savePortOutward(PortOutwardRecordVO vo, Long portOutwardShipmentId, UserInfoVO userInfo)
			throws Exception {
		Long savedRecordId = -1L;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		try {
			java.util.Date vesselDate = Converter.stringToDate(vo.getVesselDate(), Constants.Apps.DATE_FORMAT);
			java.sql.Date vesseleDateSql = Converter.dateToSqlDate(vesselDate);
			Double actual_wt = 0d;
			String actual_wt_unit = "TON";
			Double section_wt = -1d;
			String section_wt_unit = "TON";

			String query = "INSERT INTO port_outward " + " (port_out_shipment_id, vessel_name, vessel_date, "
					+ " grade, description, length, width, thickness, actual_wt, actual_wt_Unit, "
					+ " section_wt, section_wt_unit, quantity, create_ui, update_ui, "
					+ " create_ts, update_ts, material_type) "
					+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			logger.info(query);

			conn = getConnection();
			cs = conn.prepareCall(query);

			cs.setLong(1, portOutwardShipmentId);
			cs.setString(2, vo.getVesselName());
			cs.setDate(3, vesseleDateSql);
			cs.setString(4, vo.getGrade());
			cs.setString(5, vo.getDesc());
			cs.setInt(6, vo.getLength());
			cs.setInt(7, vo.getWidth());
			cs.setDouble(8, vo.getThickness());
			cs.setDouble(9, actual_wt);
			cs.setString(10, actual_wt_unit);
			cs.setDouble(11, section_wt);
			cs.setString(12, section_wt_unit);
			cs.setInt(13, vo.getOrderedQuantity());
			cs.setString(14, userInfo.getUserName());
			cs.setString(15, userInfo.getUserName());
			cs.setString(16, getCurentTime());
			cs.setString(17, getCurentTime());
			cs.setString(18, vo.getMaterialType());

			int count = cs.executeUpdate();

			ResultSet result = cs.getGeneratedKeys();
			if (count > 0) {
				result.next();
				savedRecordId = result.getLong(1);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}

		return savedRecordId;
	}

	public List<PortOutwardBean2> getByPortInwardDetailId(Integer portInwardDetailId) throws Exception {
		List<PortOutwardBean2> list = new ArrayList<PortOutwardBean2>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		try {

			String query = " select " + " port_out_id " + ", port_out_shipment_id " + ", vessel_name "
					+ ", vessel_Date " + ", be_no " + ", material_type " + ", grade " + ", description " + ", length "
					+ ", width " + ", thickness " + ", actual_wt " + ", actual_wt_Unit " + ", section_wt "
					+ ", section_wt_unit " + ", quantity " + ", create_ui " + ", update_ui " + ", create_ts "
					+ ", update_ts " + " from port_outward where port_out_id " + " in "
					+ " (select port_outward_id from port_inward_outward_intersection where port_inward_details_id = ?) "
					+ " AND quantity > 0 " + " ";

			logger.info(query);

			conn = getConnection();
			cs = conn.prepareCall(query);

			cs.setInt(1, portInwardDetailId);

			rs = cs.executeQuery();
			PortOutwardBean2 b = null;
			int c = 1;
			if (rs != null) {
				while (rs.next()) {
					c = 1;
					b = new PortOutwardBean2();
					Long port_out_id = rs.getLong(c++);
					Long port_out_shipment_id = rs.getLong(c++);
					String vessel_name = rs.getString(c++);
					Date vessel_Date = null;
					java.sql.Date d = rs.getDate(c++);
					if (d != null) {
						vessel_Date = Converter.sqlDateToDate(d);
					}

					String be_no = rs.getString(c++);
					String material_type = rs.getString(c++);
					String grade = rs.getString(c++);
					String description = rs.getString(c++);
					Integer length = rs.getInt(c++);
					Integer width = rs.getInt(c++);
					Double thickness = rs.getDouble(c++);
					Double actual_wt = rs.getDouble(c++);
					String actual_wt_Unit = rs.getString(c++);
					Double section_wt = rs.getDouble(c++);
					String section_wt_unit = rs.getString(c++);
					Integer quantity = rs.getInt(c++);
					String create_ui = rs.getString(c++);
					String update_ui = rs.getString(c++);
					Date create_ts = Converter.sqlDateToDate(rs.getDate(c++));
					Date update_ts = Converter.sqlDateToDate(rs.getDate(c++));

					b.setPort_out_id(port_out_id);
					b.setPort_out_shipment_id(port_out_shipment_id);
					b.setVessel_name(vessel_name);
					b.setVessel_Date(vessel_Date);
					b.setBe_no(be_no);
					b.setMaterial_type(material_type);
					b.setGrade(grade);
					b.setDescription(description);
					b.setLength(length);
					b.setWidth(width);
					b.setThickness(thickness);
					b.setActual_wt(actual_wt);
					b.setActual_wt_Unit(actual_wt_Unit);
					b.setSection_wt(section_wt);
					b.setSection_wt_unit(section_wt_unit);
					b.setQuantity(quantity);
					b.setCreate_ts(create_ts);
					b.setUpdate_ts(update_ts);
					b.setCreate_ui(create_ui);
					b.setUpdate_ui(update_ui);

					list.add(b);
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}

		return list;
	}

	public void updateActualWeightOfPortOutwardRecord(List<WarehouseInwardRecordVO> warehouseInwardRecordsToBeSaved)
			throws Exception {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		try {

			String query = "UPDATE port_outward set actual_wt = ? where port_out_id = ? ";

			logger.info(query);

			conn = getConnection();
			cs = conn.prepareCall(query);
			for (WarehouseInwardRecordVO vo : warehouseInwardRecordsToBeSaved) {
				Integer portOutwardId = vo.getPortOutwardId();
				Double actualWt = vo.getActualWt();

				cs.setDouble(1, actualWt);
				cs.setInt(2, portOutwardId);
				int count = cs.executeUpdate();
				logger.debug("Updated count = "+count);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}

	}

}
