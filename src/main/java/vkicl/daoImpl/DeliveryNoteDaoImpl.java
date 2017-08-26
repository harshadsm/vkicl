package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import vkicl.form.DeliveryNoteUpdateForm;
import vkicl.form.PortPurchaseDeliveryNoteForm;
import vkicl.report.bean.DeliveryNoteBean;
import vkicl.util.Converter;
import vkicl.util.Utils;
import vkicl.vo.DeliveryNoteLineItemVO;
import vkicl.vo.DeliveryNoteVO;
import vkicl.vo.PortInwardRecordVO;
import vkicl.vo.PortPurchaseOrderLineItemVO;
import vkicl.vo.PortPurchaseOrderVO;
import vkicl.vo.UserInfoVO;
import vkicl.vo.WarehouseOutwardTempVO;

public class DeliveryNoteDaoImpl extends BaseDaoImpl {

	private Logger log = Logger.getLogger(PortInwardDaoImpl.class);

	public List<DeliveryNoteBean> findByPPOId(Integer ppoId) {

		List<DeliveryNoteBean> deliveryNotes = new ArrayList<DeliveryNoteBean>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";

		try {
			conn = getConnection();

			query = "select id, create_ts, vehicle_number, vehicle_date from delivery_notes where port_purchase_order_id = ?";
			log.info("query = " + query);
			cs = conn.prepareCall(query);
			cs.setInt(1, ppoId);
			rs = cs.executeQuery();

			if (null != rs && rs.next()) {

				do {

					DeliveryNoteBean d = new DeliveryNoteBean();
					d.setCreated(rs.getDate("create_ts"));
					d.setId(rs.getInt("id"));
					d.setPpoLineitemNo(ppoId);
					d.setVehicleNo(rs.getString("vehicle_number"));
					d.setVehicleDate(dateToString(rs.getDate("vehicle_date")));
					deliveryNotes.add(d);

				} while (rs.next());
			}

		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return deliveryNotes;
	}

	private String getQueryFor(Integer deliveryNoteId, Integer ppoId) {
		StringBuffer q = new StringBuffer();

		q.append(" select * from ( ");
		q.append(" select  ");
		q.append(" dnli.id delivery_note_line_items_id,   ");
		q.append(" dnli.ppo_line_items_id,  ");
		q.append(" dnli.delivered_quantity, ");
		q.append(" dnli.create_ts ");
		q.append(" from delivery_note_line_items dnli ");
		q.append(" where delivery_note_id = ").append(deliveryNoteId).append(" ");
		q.append(" ) dnli ");
		q.append(" left join ");
		q.append(" ( ");
		q.append(" 	select  ");
		q.append("     ppoli.id, ");
		q.append("     pid.length, ");
		q.append("     pid.width, ");
		q.append("     pid.thickness ");
		q.append("     from ppo_line_items ppoli ");
		q.append("     left join port_inward_details pid ");
		q.append("     on pid.port_inward_detail_id = ppoli.port_inward_details_id ");
		q.append("     where port_purchase_order_id = ").append(ppoId).append(" ");
		q.append(" ) ppoli on dnli.ppo_line_items_id = ppoli.id ");
		return q.toString();
	}

	public List<DeliveryNoteLineItemVO> findDeliveryNoteLineItemsByDeliveryNoteId(Integer deliveryNoteId,
			Integer ppoId) {
		List<DeliveryNoteLineItemVO> deliveryNoteLineItems = new ArrayList<DeliveryNoteLineItemVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";

		try {
			conn = getConnection();

			query = getQueryFor(deliveryNoteId, ppoId);

			log.info("query = " + query);
			cs = conn.prepareCall(query);

			rs = cs.executeQuery();

			if (null != rs && rs.next()) {

				do {

					DeliveryNoteLineItemVO l = new DeliveryNoteLineItemVO();
					l.setDate(rs.getDate("create_ts"));
					l.setDeliveredQuantity(rs.getInt("delivered_quantity"));
					l.setId(rs.getInt("delivery_note_line_items_id"));
					l.setLength(rs.getInt("length"));
					l.setWidth(rs.getInt("width"));
					l.setThickness(rs.getInt("thickness"));

					deliveryNoteLineItems.add(l);

				} while (rs.next());
			}

		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return deliveryNoteLineItems;

	}
	
	
	
	private String getQueryForUpdatingActualWeight(Integer deliveryNoteId) {
		StringBuffer q = new StringBuffer();

		q.append(" select * from ( ");
		q.append(" select  ");
		q.append(" dnli.id delivery_note_line_items_id,   ");
		q.append(" dnli.ppo_line_items_id,  ");
		q.append(" dnli.delivered_quantity, ");
		q.append(" dnli.create_ts, ");
		q.append(" dnli.actual_wt ");
		q.append(" from delivery_note_line_items dnli ");
		q.append(" where delivery_note_id = ").append(deliveryNoteId).append(" ");
		q.append(" ) dnli ");
		q.append(" left join ");
		q.append(" ( ");
		q.append(" 	select  ");
		q.append("     ppoli.id, ");
		q.append("     pid.length, ");
		q.append("     pid.width, ");
		q.append("     pid.thickness ");
		q.append("     from ppo_line_items ppoli ");
		q.append("     left join port_inward_details pid ");
		q.append("     on pid.port_inward_detail_id = ppoli.port_inward_details_id ");
		q.append(" ) ppoli on dnli.ppo_line_items_id = ppoli.id ");
		return q.toString();
	}

	public List<DeliveryNoteLineItemVO> findDeliveryNoteLineItemsForActualWeightUpdateByDeliveryNoteId(Integer deliveryNoteId
			) {
		List<DeliveryNoteLineItemVO> deliveryNoteLineItems = new ArrayList<DeliveryNoteLineItemVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";

		try {
			conn = getConnection();

			query = getQueryForUpdatingActualWeight(deliveryNoteId);

			log.info("query = " + query);
			cs = conn.prepareCall(query);

			rs = cs.executeQuery();

			if (null != rs && rs.next()) {

				do {

					DeliveryNoteLineItemVO l = new DeliveryNoteLineItemVO();
					l.setDate(rs.getDate("create_ts"));
					l.setDeliveredQuantity(rs.getInt("delivered_quantity"));
					l.setId(rs.getInt("delivery_note_line_items_id"));
					l.setLength(rs.getInt("length"));
					l.setWidth(rs.getInt("width"));
					l.setThickness(rs.getInt("thickness"));
					l.setActualWeight(rs.getDouble("actual_wt"));

					deliveryNoteLineItems.add(l);

				} while (rs.next());
			}

		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return deliveryNoteLineItems;

	}

	public DeliveryNoteVO getDeliveryNoteDetailsById(int deliveryNoteId) throws SQLException {
		DeliveryNoteVO vo = null;
		PortPurchaseOrderVO ppoVO = null;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";

		try {
			conn = getConnection();

			String sql = "select ppo.port_purchase_order_id, CAST(ppo.create_ts as Date) as ppoDate , ppo.customer_name, ppo.delivery_address, "
					+ " dn.id, CAST(dn.create_ts as Date) as deliveryDate, dn.delivery_address, dn.vehicle_number "
					+ " , dn.actual_wt "
					+ " from port_purchase_order ppo  "
					+ " inner join  delivery_notes dn " + " on ppo.port_purchase_order_id=dn.port_purchase_order_id"
					+ " where dn.id=" + deliveryNoteId;
			query = sql;
			log.info("query = " + query);

			cs = conn.prepareCall(query);

			rs = cs.executeQuery();
			if (null != rs && rs.next()) {

				do {
					vo = new DeliveryNoteVO();
					vo.setPortPurchaseOrderId(rs.getInt(1));

					ppoVO = new PortPurchaseOrderVO();
					ppoVO.setPpoDate(rs.getString(2));
					ppoVO.setCustName(rs.getString(3));
					ppoVO.setDeliveryAddr(rs.getString(4));
					vo.setPortPurchaseOrder(ppoVO);

					vo.setId(rs.getInt(5));
					vo.setDeliveryDate(rs.getString(6));
					vo.setDeliveryNoteAddress(rs.getString(7));
					vo.setVehicleNumber(rs.getString(8));
					vo.setActualWeight(rs.getDouble(9));

				} while (rs.next());

			}

		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return vo;
	}

	public List<DeliveryNoteLineItemVO> getDeliveryNoteLineItemsById(Integer deliveryNoteId) {
		List<DeliveryNoteLineItemVO> deliveryNoteLineItems = new ArrayList<DeliveryNoteLineItemVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		StringBuffer q = new StringBuffer();
		q.append(" select  ");
		//q.append("   -- dnli.*, ");
		q.append("   dnli.id ");
		q.append("   ,dnli.ppo_line_items_id ");
		q.append("   ,dnli.delivered_quantity ");
		q.append("   ,dnli.delivery_note_id ");
		//q.append("   -- , pid.* ");
		q.append("   ,pid.length ");
		q.append("   ,pid.width ");
		q.append("   ,pid.thickness ");
		q.append("   ,pid.be_weight ");
		q.append("   ,dnli.actual_wt ");
		q.append("   ,dnli.create_ts ");
		q.append("   ,pi.mill_name ");
		q.append("   ,pi.material_make ");
		q.append("   ,pi.material_grade ");
		q.append("   ,pi.material_type ");
		q.append("   ,ppoli.ordered_quantity ");
		q.append("   from delivery_note_line_items dnli ");
		q.append("   left join ppo_line_items ppoli on ppoli.id = dnli.ppo_line_items_id ");
		q.append("   left join port_inward_details pid on pid.port_inward_detail_id = ppoli.port_inward_details_id ");
		q.append("   left join port_inward pi on pi.port_inward_id = pid.port_inward_id ");
		q.append("   where dnli.delivery_note_id = ").append(deliveryNoteId);

		try {
			conn = getConnection();

			String query = q.toString();

			log.info("query = " + query);
			cs = conn.prepareCall(query);

			rs = cs.executeQuery();

			if (null != rs && rs.next()) {

				do {

					DeliveryNoteLineItemVO l = new DeliveryNoteLineItemVO();
					l.setDate(rs.getDate("create_ts"));
					l.setDeliveredQuantity(rs.getInt("delivered_quantity"));
					l.setId(rs.getInt("id"));
					l.setLength(rs.getInt("length"));
					l.setWidth(rs.getInt("width"));
					l.setThickness(rs.getInt("thickness"));

					l.setMaterialGrade(rs.getString("material_grade"));
					l.setMaterialMake(rs.getString("material_make"));
					l.setMaterialType(rs.getString("material_type"));
					l.setMillName(rs.getString("mill_name"));
					l.setOrderedQuantity(rs.getInt("ordered_quantity"));
					
					deliveryNoteLineItems.add(l);

				} while (rs.next());
			}

		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return deliveryNoteLineItems;

	}

	public String updateDeliveryNote(DeliveryNoteUpdateForm form, UserInfoVO userInfoVO) throws SQLException {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "Success";
		int count = 0;
		PreparedStatement statement = null;
		try {
			conn = getConnection();

			String sql = "update delivery_notes s set s.actual_wt = ?, s.delivery_address=? ,vehicle_number=?, "
					+ " s.update_ui = ?,s.update_ts = NOW()  WHERE id=? and port_purchase_order_id=?";
			statement = conn.prepareStatement(sql);
			statement.setDouble(1, form.getActualWt());
			statement.setString(2, form.getDeliveryAddress());
			statement.setString(3, form.getVehicleNumber());
			statement.setString(4, userInfoVO.getUserName());
			statement.setInt(5, form.getDeliveryNoteId());
			statement.setInt(6, form.getPpoNo());

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

	public String updateDeliveryNoteLineItems(DeliveryNoteLineItemVO vo, DeliveryNoteUpdateForm form,
			UserInfoVO userInfoVO) throws SQLException {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "Success";
		int count = 0;
		PreparedStatement statement = null;
		try {
			conn = getConnection();

			String sql = "update delivery_note_line_items s set s.delivered_quantity = ?,"
					+ " s.update_ui = ?,s.update_ts = NOW()  WHERE id=? and delivery_note_id=?";
			statement = conn.prepareStatement(sql);
			statement.setDouble(1, vo.getDeliveredQuantity());

			statement.setString(2, userInfoVO.getUserName());
			statement.setInt(3, vo.getId());
			statement.setInt(4, form.getDeliveryNoteId());

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
	
	
	public Long addPortPurchaseDeliveryData(PortPurchaseDeliveryNoteForm portpurchasedeliverynoteform,
			UserInfoVO userInfoVO) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		Long savedRecordId = -1L;
		try {

			query = "INSERT INTO delivery_notes "
					+ " (port_purchase_order_id,create_ui, update_ui, create_ts, update_ts, vehicle_number,delivery_address,vehicle_date) "
					+ " VALUES ( ?, ?, ?, ?, ?,?,?,?)";

			log.info(query);

			conn = getConnection();
			cs = conn.prepareCall(query);

			cs.setLong(1, portpurchasedeliverynoteform.getPpoNo());
			cs.setString(2, userInfoVO.getUserName());
			cs.setString(3, userInfoVO.getUserName());
			cs.setString(4, getCurentTime());
			cs.setString(5, getCurentTime());
			cs.setString(6, portpurchasedeliverynoteform.getVehicleNumber());
			cs.setString(7, portpurchasedeliverynoteform.getDeliveryAddress());
			Date vehicleDate = Converter.dateToSqlDate(Converter.stringToDate(portpurchasedeliverynoteform.getVehicleDate(), "dd-MM-yyyy"));
			cs.setDate(8, vehicleDate);

			int count = cs.executeUpdate();

			ResultSet result = cs.getGeneratedKeys();
			if (count > 0) {
				result.next();
				savedRecordId = result.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return savedRecordId;

	}
	
	public void addPortPurchaseDeliveryLineItemsData(PortPurchaseOrderLineItemVO vo, Long deliveryNoteId,
			UserInfoVO userInfoVO) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";

		try {

			query = "INSERT INTO delivery_note_line_items "
					+ " (ppo_line_items_id,delivered_quantity,delivery_note_id,create_ui, update_ui, create_ts, update_ts) "
					+ " VALUES ( ?,?,?, ?, ?, ?, ?)";

			log.info(query);

			conn = getConnection();
			cs = conn.prepareCall(query);

			cs.setLong(1, vo.getPpoLineItemNo());
			cs.setInt(2, vo.getDeliveryQuantity());
			cs.setLong(3, deliveryNoteId);
			cs.setString(4, userInfoVO.getUserName());
			cs.setString(5, userInfoVO.getUserName());
			cs.setString(6, getCurentTime());
			cs.setString(7, getCurentTime());

			int count = cs.executeUpdate();

			ResultSet result = cs.getGeneratedKeys();

		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}

	}
	
	
	public String updatePortOutward(Map<String, String[]> map, UserInfoVO userInfoVO) {


		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "Success";
		int count = 0;
		PreparedStatement statement = null;
		try {
			conn = getConnection();
			String sql = "update delivery_notes set invoice = ?, actual_wt = ? where id = ?";
			statement = conn.prepareStatement(sql);
			statement.setString(1, fetchFromMap(map, "invoice"));
			statement.setString(2, fetchFromMap(map, "actualWt"));
			statement.setString(3, fetchFromMap(map, "id"));
			

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

	public List<DeliveryNoteVO> listAll() {


		List<DeliveryNoteVO> deliveryNotes = new ArrayList<DeliveryNoteVO>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";

		try {
			conn = getConnection();

			query = "select * from delivery_notes order by vehicle_date";
			log.info("query = " + query);
			cs = conn.prepareCall(query);
			
			rs = cs.executeQuery();

			if (null != rs && rs.next()) {

				do {

					DeliveryNoteVO d = new DeliveryNoteVO();
					d.setCreatedDate(rs.getDate("create_ts"));
					d.setId(rs.getInt("id"));
					d.setActualWeight(rs.getDouble("actual_wt"));
					d.setDeliveryNoteAddress(rs.getString("delivery_address"));
					d.setPortPurchaseOrderId(rs.getInt("port_purchase_order_id"));
					d.setVehicleDate(dateToString(rs.getDate("vehicle_date")));
					d.setVehicleNumber(rs.getString("vehicle_number"));
					d.setInvoice(rs.getString("invoice"));
					
					deliveryNotes.add(d);

				} while (rs.next());
			}

		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return deliveryNotes;
	}
	public void updateActualWeightOfDeliveryNote(Integer delivery_note_id, Double actualWeight) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		StringBuffer q = new StringBuffer();

		try {
			conn = getConnection();

			q.append(" update delivery_notes set actual_wt = ? where id = ? ");
			String query = q.toString();

			log.info("query = " + query);
			cs = conn.prepareCall(query);
			cs.setDouble(1, actualWeight);
			cs.setInt(2, delivery_note_id);
			int recordsUpdated = cs.executeUpdate();
			
			log.debug("Update successful "+recordsUpdated);
		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		
	}

	public void distributeActualWeightPerPlate(Integer deliveryNoteId, Double actualWeight) {
		// Find the delivery note line items for given deliveryNoteId
		log.debug("Find the delivery note line items for given deliveryNoteId = " + deliveryNoteId);
		List<DeliveryNoteLineItemVO> deliveryNoteLineItems = findDeliveryNoteLineItemsForActualWeightUpdateByDeliveryNoteId(deliveryNoteId);
		// Distribute the actualWeight among the records in proportion to their
		// section weight.

		distributeActualWeightAmongTheDeliveryNoteLineItems(deliveryNoteLineItems, actualWeight);

	}
	
	private void distributeActualWeightAmongTheDeliveryNoteLineItems(List<DeliveryNoteLineItemVO> deliveryNoteLineItems,
			Double actualLorryWeight) {
		//Find the total of section weight
		Double totalSectionWeight = calculateTotalSectionWeight(deliveryNoteLineItems);
		Double multiplicationFactor = actualLorryWeight / totalSectionWeight;
		
		for(DeliveryNoteLineItemVO dnli:deliveryNoteLineItems){
			Integer length = dnli.getLength();
			Integer width = dnli.getWidth();
			Integer thickness = dnli.getThickness();
			Integer quantity = dnli.getDeliveredQuantity();
			Double lineItemSectionWeight = Utils.calculateSectionWeight(length, width, thickness, quantity);
			dnli.setActualWeight(lineItemSectionWeight*multiplicationFactor);
			
		}
		
		saveDistributedWeightInDatabase(deliveryNoteLineItems);
		
		
	}
	
	
	private Double calculateTotalSectionWeight(List<DeliveryNoteLineItemVO> inwardedRecords) {
		Double totalSectionWeight = 0d;
		for(DeliveryNoteLineItemVO dnli:inwardedRecords){
			
			Integer l = dnli.getLength();
			Integer w = dnli.getWidth();
			Integer t = dnli.getThickness();
			Integer q = dnli.getDeliveredQuantity();
			
			Double sectionWeight = Utils.calculateSectionWeight(l, w, t, q);
			
			totalSectionWeight = totalSectionWeight + sectionWeight;
			
		}
		return totalSectionWeight;
	}
	
	
	private void saveDistributedWeightInDatabase(List<DeliveryNoteLineItemVO> deliveryNoteLineItems) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "Success";
		int count = 0;
		PreparedStatement statement = null;
		try {
			conn = getConnection();
			String sql = "update delivery_note_line_items set actual_wt = ?, update_ui = ?, update_ts = NOW()  WHERE id = ? ";
			
			for(DeliveryNoteLineItemVO widb : deliveryNoteLineItems){
				statement = conn.prepareStatement(sql);
				statement.setDouble(1, widb.getActualWeight());
				statement.setString(2, "some_admin_to_be_updated_later223143");
				statement.setInt(3, widb.getId());

				statement.executeUpdate();
	
			}
			
			log.info("message = " + message);
			
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			log.error(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}

		
		
	}

}
