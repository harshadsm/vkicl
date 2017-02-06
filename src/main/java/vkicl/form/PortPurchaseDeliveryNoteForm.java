package vkicl.form;

import java.util.Arrays;

@SuppressWarnings("serial")
public class PortPurchaseDeliveryNoteForm extends BaseForm {

	private Integer ppoNo = null;

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
				+ Arrays.toString(orderedQuantity) + "]";
	}

}