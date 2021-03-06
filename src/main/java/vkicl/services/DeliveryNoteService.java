package vkicl.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import vkicl.daoImpl.DeliveryNoteDaoImpl;
import vkicl.daoImpl.PortInwardDaoImpl;
import vkicl.daoImpl.PortPurchaseOrderDaoImpl;
import vkicl.daoImpl.WarehouseOutwardDaoImpl;
import vkicl.daoImpl.WarehouseOutwardTempDaoImpl;
import vkicl.form.DeliveryNoteUpdateForm;
import vkicl.form.PortPurchaseDeliveryNoteForm;
import vkicl.report.bean.DeliveryNoteBean;
import vkicl.vo.DeliveryNoteLineItemVO;
import vkicl.vo.DeliveryNoteVO;
import vkicl.vo.PortPurchaseOrderLineItemVO;
import vkicl.vo.PortPurchaseOrderVO;
import vkicl.vo.UserInfoVO;

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
				deliveryNote.setVehicleNumber(dnb.getVehicleNo());
				deliveryNote.setVehicleDate(dnb.getVehicleDate());

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

	public void updateDeliveryNote(DeliveryNoteUpdateForm form, UserInfoVO userInfoVO) throws SQLException {

		DeliveryNoteDaoImpl impl = new DeliveryNoteDaoImpl();
		impl.updateDeliveryNote(form, userInfoVO);

	}

	public void updateDeliveryNoteLineItems(DeliveryNoteLineItemVO vo, DeliveryNoteUpdateForm form,
			UserInfoVO userInfoVO) throws SQLException {

		DeliveryNoteDaoImpl impl = new DeliveryNoteDaoImpl();
		impl.updateDeliveryNoteLineItems(vo, form, userInfoVO);

	}

	public List<DeliveryNoteLineItemVO> toList(DeliveryNoteUpdateForm form, UserInfoVO user) {
		List<DeliveryNoteLineItemVO> list = new ArrayList<DeliveryNoteLineItemVO>();

		if (form.getDeliveredQuantity() != null) {
			int recordCount = form.getDeliveredQuantity().length;

			Integer[] deliveredQty = form.getDeliveredQuantity();
			Integer[] deliveryNoteLineItemId = form.getDeliveryNoteLineItemId();

			for (int i = 0; i < recordCount; i++) {
				if (deliveredQty[i] == 0) {
					log.debug("Ignored empty row");
				} else {

					DeliveryNoteLineItemVO vo = new DeliveryNoteLineItemVO();

					vo.setDeliveredQuantity(deliveredQty[i]);
					vo.setId(deliveryNoteLineItemId[i]);

					list.add(vo);
				}

			}
		}

		return list;
	}

	public String getAllDeliveryNotesJson(){
		List<DeliveryNoteVO> list = getAllDeliveryNotes();
		Gson gson = new Gson();
		String json = gson.toJson(list);
		return json;
	}
	public List<DeliveryNoteVO> getAllDeliveryNotes() {
		DeliveryNoteDaoImpl deliveryNoteDaoImpl = new DeliveryNoteDaoImpl();
		List<DeliveryNoteVO> list = deliveryNoteDaoImpl.listAll();
		return list;
	}

	public void updateActualWeightOfDeliveryNote(Integer deliveryNoteId, Double actualWeight) {
		
		log.debug("Going to update delivery_note.id = "+deliveryNoteId);
		log.debug("Going to update actualWeight = "+actualWeight);
		
		DeliveryNoteDaoImpl dao = new DeliveryNoteDaoImpl();
		dao.updateActualWeightOfDeliveryNote(deliveryNoteId, actualWeight);
		
		
	}
	
	
	public void distributeActualWeightPerPlate(Integer deliveryNoteId, Double actualWeight) {
		
		DeliveryNoteDaoImpl dao = new DeliveryNoteDaoImpl();
		dao.distributeActualWeightPerPlate(deliveryNoteId, actualWeight);
		
		
	}

}
