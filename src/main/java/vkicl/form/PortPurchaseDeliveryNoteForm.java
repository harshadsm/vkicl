package vkicl.form;

import java.util.ArrayList;
import java.util.Arrays;
import vkicl.form.*;
import vkicl.report.bean.PortPurchaseOrderDeliveryNoteBean;
import vkicl.report.bean.WarehouseDispatchBean;

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

	public Integer[] getDeliveryQuantity() {
		return deliveryQuantity;
	}

	public void setDeliveryQuantity(Integer[] deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}

	@Override
	public String toString() {
		return "PortPurchaseDeliveryNoteForm [ppoNo=" + ppoNo + ",ppoLineitemNo=" + Arrays.toString(ppoLineitemNo)
				+ ", deliveryQuantity=" + Arrays.toString(deliveryQuantity) + "]";
	}

}