package vkicl.form;

import java.util.Arrays;

@SuppressWarnings("serial")
public class DeliveryNoteUpdateForm extends BaseForm {

	private Integer ppoNo = null;
	private String deliveryDate = null;

	private String vehicleNumber = null;
	private String deliveryAddress = null;
	private Double actualWt = null;

	private Integer deliveryNoteId = null;
	private Integer[] deliveredQuantity = null;

	public Integer getPpoNo() {
		return ppoNo;
	}

	public void setPpoNo(Integer ppoNo) {
		this.ppoNo = ppoNo;
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

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Double getActualWt() {
		return actualWt;
	}

	public void setActualWt(Double actualWt) {
		this.actualWt = actualWt;
	}

	public Integer getDeliveryId() {
		return deliveryNoteId;
	}

	public void setDeliveryId(Integer deliveryNoteId) {
		this.deliveryNoteId = deliveryNoteId;
	}

	public Integer[] getDeliveredQuantity() {
		return deliveredQuantity;
	}

	public void setDeliveredQuantity(Integer[] deliveredQuantity) {
		this.deliveredQuantity = deliveredQuantity;
	}

	@Override
	public String toString() {
		return "DeliveryNoteUpdateForm [ppoNo=" + ppoNo + ",deliveryNoteId=" + (deliveryNoteId) + ", deliveredQuantity="
				+ Arrays.toString(deliveredQuantity) + ", " + "vehicleNumber=" + vehicleNumber + ",deliveryAddress="
				+ deliveryAddress + ",actualWt=" + actualWt + ",deliveryDate=" + deliveryDate + "]";
	}

}