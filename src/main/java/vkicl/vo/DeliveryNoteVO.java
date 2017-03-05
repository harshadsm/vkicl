package vkicl.vo;

import java.util.List;

public class DeliveryNoteVO {

	private Integer id;
	private Integer portPurchaseOrderId;
	private String deliveryDate;
	private String deliveryNoteAddress;

	private String vehicleNumber;
	private PortPurchaseOrderVO portPurchaseOrder;
	private List<DeliveryNoteLineItemVO> deliveryNoteLineItems;
	private Double actualWeight;

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

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getDeliveryNoteAddress() {
		return deliveryNoteAddress;
	}

	public void setDeliveryNoteAddress(String deliveryNoteAddress) {
		this.deliveryNoteAddress = deliveryNoteAddress;
	}

	public Double getActualWeight() {
		return actualWeight;
	}

	public void setActualWeight(Double actualWeight) {
		this.actualWeight = actualWeight;
	}
	
	
}
