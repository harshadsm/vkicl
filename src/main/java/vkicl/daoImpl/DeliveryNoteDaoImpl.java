package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import vkicl.report.bean.DeliveryNoteBean;
import vkicl.util.Converter;
import vkicl.vo.DeliveryNoteLineItemVO;
import vkicl.vo.DeliveryNoteVO;
import vkicl.vo.PortInwardRecordVO;

public class DeliveryNoteDaoImpl  extends BaseDaoImpl{
	
	private Logger log = Logger.getLogger(PortInwardDaoImpl.class);

	public List<DeliveryNoteBean> findByPPOId(Integer ppoId){

		List<DeliveryNoteBean> deliveryNotes = new ArrayList<DeliveryNoteBean>();
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		
		try {
			conn = getConnection();

			query = "select id, create_ts from delivery_notes where port_purchase_order_id = ?";
			log.info("query = " + query);
			cs = conn.prepareCall(query);
			cs.setInt(1, ppoId);
			rs = cs.executeQuery();
			
			
			if ( null != rs && rs.next()) {
				
				do {
					
					DeliveryNoteBean d = new DeliveryNoteBean();
					d.setCreated(rs.getDate("create_ts"));
					d.setId(rs.getInt("id"));
					d.setPpoLineitemNo(ppoId);
					
					deliveryNotes.add(d);
					
				} while (rs.next());
			}

		} catch (Exception e) {
			log.error("Some error",e);
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
			
			
			if ( null != rs && rs.next()) {
				
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
			log.error("Some error",e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return deliveryNoteLineItems;	
		
	}


}