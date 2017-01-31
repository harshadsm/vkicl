package vkicl.form;

import java.util.ArrayList;
import java.util.Arrays;
import vkicl.form.*;
import vkicl.report.bean.PortPurchaseOrderDeliveryBean;
import vkicl.report.bean.WarehouseDispatchBean;

@SuppressWarnings("serial")
public class PortPurchaseDeliveryNoteForm extends BaseForm {

	private Integer ppoLineItemsId = null;

	public Integer getPpoLineItemsId() {
		return ppoLineItemsId;
	}

	public void setPpoLineItemsId(Integer ppoLineItemsId) {
		this.ppoLineItemsId = ppoLineItemsId;
	}

	public Integer getDeliveredQuantity() {
		return deliveredQuantity;
	}

	public void setDeliveredQuantity(Integer deliveredQuantity) {
		this.deliveredQuantity = deliveredQuantity;
	}

	public Integer getDeliveryNoteId() {
		return deliveryNoteId;
	}

	public void setDeliveryNoteId(Integer deliveryNoteId) {
		this.deliveryNoteId = deliveryNoteId;
	}

	private Integer deliveredQuantity = null;
	private Integer deliveryNoteId = null;

	@Override
	public String toString() {
		return "PortPurchaseDeliveryNoteForm [ppoLineItemsId=" + ppoLineItemsId + ", deliveredQuantity="
				+ deliveredQuantity + "" + ",deliveryNoteId=" + deliveryNoteId + "]";
	}

}