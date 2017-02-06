package vkicl.services;

import java.util.ArrayList;
import java.util.List;

import vkicl.daoImpl.DeliveryNoteDaoImpl;
import vkicl.report.bean.DeliveryNoteBean;
import vkicl.vo.DeliveryNoteLineItemVO;
import vkicl.vo.DeliveryNoteVO;

public class DeliveryNoteService {

	public List<DeliveryNoteVO> findDeliveryNotesByPPOId(Integer ppoId){
		 List<DeliveryNoteVO> deliveryNotes = new ArrayList<DeliveryNoteVO>();
		 
		 DeliveryNoteDaoImpl deliveryNoteDaoImpl = new DeliveryNoteDaoImpl();
		 List<DeliveryNoteBean> deliveryNoteBeans = deliveryNoteDaoImpl.findByPPOId(ppoId);
		 if(deliveryNoteBeans!=null && !deliveryNoteBeans.isEmpty()){
			 for(DeliveryNoteBean dnb:deliveryNoteBeans){
				 Integer deliveryNoteId = dnb.getId();
				//Get the delivery note items
				 List<DeliveryNoteLineItemVO> deliveredItems = deliveryNoteDaoImpl.findDeliveryNoteLineItemsByDeliveryNoteId(deliveryNoteId, ppoId);
				 
				 DeliveryNoteVO deliveryNote = new DeliveryNoteVO();
				 deliveryNote.setId(deliveryNoteId);
				 deliveryNote.setPortPurchaseOrderId(ppoId);
				 deliveryNote.setDeliveryNoteLineItems(deliveredItems);
				 
				 deliveryNotes.add(deliveryNote);
				 
			 }
		 }
		 
		 
		 return deliveryNotes;
	}
}
