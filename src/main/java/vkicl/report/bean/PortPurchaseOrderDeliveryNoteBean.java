package vkicl.report.bean;

import java.util.ArrayList;

import vkicl.form.PortPurchaseOrderForm;

@SuppressWarnings("serial")
public class PortPurchaseOrderDeliveryNoteBean extends BaseReportBean {

	private Integer itemNo = null;

	public Integer getItemNo() {
		return itemNo;
	}

	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}

	public Double getThickness() {
		return thickness;
	}

	public void setThickness(Double thickness) {
		this.thickness = thickness;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getOrderedQty() {
		return orderedQty;
	}

	public void setOrderedQty(Integer orderedQty) {
		this.orderedQty = orderedQty;
	}

	public Integer getDeliveryQty() {
		return deliveryQty;
	}

	public void setDeliveryQty(Integer deliveryQty) {
		this.deliveryQty = deliveryQty;
	}

	private Double thickness = null;
	private Integer length = null;
	private Integer width = null;
	private Integer orderedQty = null;
	private Integer deliveryQty = null;

	@Override
	public String toString() {
		return "PortPurchaseOrderDeliveryNoteBean [itemNo=" + itemNo + ",thickness=" + thickness + ",length=" + length
				+ ", width=" + width + ", orderedQty=" + orderedQty + ", deliveryQty=" + deliveryQty + "]";
	}

}
