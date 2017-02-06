package vkicl.vo;

import java.util.List;

public class DeliveryNoteVO {

	private Integer id;
	private Integer portPurchaseOrderId;
	private PortPurchaseOrderVO portPurchaseOrder;
	private List<DeliveryNoteLineItemVO> deliveryNoteLineItems;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPortPurchaseOrderId() {
		return portPurchaseOrderId;
	}

	public void setPortPurchaseOrderId(Integer portPurchaseOrderId) {
		this.portPurchaseOrderId = portPurchaseOrderId;
	}

	public PortPurchaseOrderVO getPortPurchaseOrder() {
		return portPurchaseOrder;
	}

	public void setPortPurchaseOrder(PortPurchaseOrderVO portPurchaseOrder) {
		this.portPurchaseOrder = portPurchaseOrder;
	}

	public List<DeliveryNoteLineItemVO> getDeliveryNoteLineItems() {
		return deliveryNoteLineItems;
	}

	public void setDeliveryNoteLineItems(List<DeliveryNoteLineItemVO> deliveryNoteLineItems) {
		this.deliveryNoteLineItems = deliveryNoteLineItems;
	}
	
	
}