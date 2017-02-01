package vkicl.form;

import java.util.ArrayList;
import java.util.Arrays;
import vkicl.form.*;
import vkicl.report.bean.PortPurchaseOrderDeliveryNoteBean;
import vkicl.report.bean.WarehouseDispatchBean;

@SuppressWarnings("serial")
public class PortPurchaseDeliveryNoteForm extends BaseForm {

	private Integer ppoLineItemsId = null;
	private Integer ppoNo = null;

	public Integer getPpoNo() {
		return ppoNo;
	}

	public void setPpoNo(Integer ppoNo) {
		this.ppoNo = ppoNo;
	}

	ArrayList<PortPurchaseOrderDeliveryNoteBean> reportList = null;

	public ArrayList<PortPurchaseOrderDeliveryNoteBean> getReportList() {
		return reportList;
	}

	public void setReportList(ArrayList<PortPurchaseOrderDeliveryNoteBean> reportList) {
		this.reportList = reportList;
	}

	private Integer deliveredQuantity = null;
	private Integer deliveryNoteId = null;

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

	@Override
	public String toString() {
		return "PortPurchaseDeliveryNoteForm [ppoNo=" + ppoNo + ", ppoLineItemsId=" + ppoLineItemsId
				+ ", deliveredQuantity=" + deliveredQuantity + "" + ",deliveryNoteId=" + deliveryNoteId + ",reportList="
				+ reportList + "]";
	}

}