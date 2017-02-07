package vkicl.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import vkicl.daoImpl.DeliveryNoteDaoImpl;
import vkicl.daoImpl.PortInwardDaoImpl;
import vkicl.daoImpl.PortPurchaseOrderDaoImpl;
import vkicl.report.bean.DeliveryNoteBean;
import vkicl.vo.DeliveryNoteLineItemVO;
import vkicl.vo.DeliveryNoteVO;
import vkicl.vo.PortPurchaseOrderVO;

public class DeliveryNoteService {

	private Logger log = Logger.getLogger(DeliveryNoteService.class);

	public List<DeliveryNoteVO> findDeliveryNotesByPPOId(Integer ppoId) {
		List<DeliveryNoteVO> deliveryNotes = new ArrayList<DeliveryNoteVO>();

		DeliveryNoteDaoImpl deliveryNoteDaoImpl = new DeliveryNoteDaoImpl();
		List<DeliveryNoteBean> deliveryNoteBeans = deliveryNoteDaoImpl.findByPPOId(ppoId);
		if (deliveryNoteBeans != null && !deliveryNoteBeans.isEmpty()) {
			for (DeliveryNoteBean dnb : deliveryNoteBeans) {
				Integer deliveryNoteId = dnb.getId();
				// Get the delivery note items
				List<DeliveryNoteLineItemVO> deliveredItems = deliveryNoteDaoImpl
						.findDeliveryNoteLineItemsByDeliveryNoteId(deliveryNoteId, ppoId);

				DeliveryNoteVO deliveryNote = new DeliveryNoteVO();
				deliveryNote.setId(deliveryNoteId);
				deliveryNote.setPortPurchaseOrderId(ppoId);
				deliveryNote.setDeliveryNoteLineItems(deliveredItems);

				deliveryNotes.add(deliveryNote);

			}
		}

		return deliveryNotes;
	}

	public DeliveryNoteVO getDeliveryNoteDetailsById(Integer deliveryNoteId) {
		DeliveryNoteVO vo = null;
		DeliveryNoteDaoImpl dao = new DeliveryNoteDaoImpl();

		try {
			vo = dao.getDeliveryNoteDetailsById(deliveryNoteId);
		} catch (SQLException e) {
			log.error("Some error", e);
		}
		return vo;
	}

	public List<DeliveryNoteLineItemVO> getDeliveryNoteLineItems(Integer deliveryNoteId, Integer ppoNo) {
		List<DeliveryNoteLineItemVO> vo = null;
		DeliveryNoteDaoImpl dao = new DeliveryNoteDaoImpl();

		try {
			vo = dao.findDeliveryNoteLineItemsByDeliveryNoteId(deliveryNoteId, ppoNo);
		} catch (Exception e) {
			log.error("Some error", e);
		}
		return vo;
	}

}
