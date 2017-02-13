package vkicl.form;

import java.util.Arrays;

@SuppressWarnings("serial")
public class PortPurchaseDeliveryNoteForm extends BaseForm {

	private Integer ppoNo = null;
	private String vehicleNumber = null;
	private String vehicleDate = null;

	public String getVehicleDate() {
		return vehicleDate;
	}

	public void setVehicleDate(String vehicleDate) {
		this.vehicleDate = vehicleDate;
	}

	private String deliveryAddress = null;
	private Double actualWt = null;

	public Double getActualWt() {
		return actualWt;
	}

	public void setActualWt(Double actualWt) {
		this.actualWt = actualWt;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public Integer getPpoNo() {
		return ppoNo;
	}

	public void setPpoNo(Integer ppoNo) {
		this.ppoNo = ppoNo;
	}

	private Integer[] ppoLineitemNo = null;

	public Integer[] getPpoLineitemNo() {
		return ppoLineitemNo;
	}

	public void setPpoLineitemNo(Integer[] ppoLineitemNo) {
		this.ppoLineitemNo = ppoLineitemNo;
	}

	private Integer[] deliveryQuantity = null;
	private Integer[] orderedQuantity = null;

	public Integer[] getOrderedQuantity() {
		return orderedQuantity;
	}

	public void setOrderedQuantity(Integer[] orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}

	public Integer[] getDeliveryQuantity() {
		return deliveryQuantity;
	}

	public void setDeliveryQuantity(Integer[] deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}

	@Override
	public String toString() {
		return "PortPurchaseDeliveryNoteForm [ppoNo=" + ppoNo + ",ppoLineitemNo=" + Arrays.toString(ppoLineitemNo)
				+ ", deliveryQuantity=" + Arrays.toString(deliveryQuantity) + ", orderedQuantity="
				+ Arrays.toString(orderedQuantity) + ",vehicleNumber=" + vehicleNumber + ",deliveryAddress="
				+ deliveryAddress + ",actualWt=" + actualWt + ",vehicleDate=" + vehicleDate + "]";
	}

}