package vkicl.vo;

import java.awt.Shape;

public class PortPurchaseOrderVO {
	private Integer portInwardDetailId;

	private Integer portInwardId;

	private String custName;
	private String brokerName;
	private Double brokerage;
	private String brokerageUnit;
	private String deliveryAddr;

	private Integer rate;

	private Double excise;
	private Double tax;
	private Double transport;

	private String paymentTerms;
	private String comments;
	private Integer totalQty;
	private Integer availableQuantity;

	private Integer pendingItems;
	private String ppoDate;
	private Integer ppoNo;

	public Integer getPpoNo() {
		return ppoNo;
	}

	public void setPpoNo(Integer ppoNo) {
		this.ppoNo = ppoNo;
	}

	public String getPpoDate() {
		return ppoDate;
	}

	public void setPpoDate(String ppoDate) {
		this.ppoDate = ppoDate;
	}

	public Integer getPendingItems() {
		return pendingItems;
	}

	public void setPendingItems(Integer pendingItems) {
		this.pendingItems = pendingItems;
	}

	public Integer getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public void setDeliveryAddr(String deliveryAddr) {
		this.deliveryAddr = deliveryAddr;
	}

	public String getDeliveryAddr() {
		return deliveryAddr;
	}

	public void setDeliveryAddrs(String deliveryAddr) {
		this.deliveryAddr = deliveryAddr;
	}

	public String getBrokerageUnit() {
		return brokerageUnit;
	}

	public void setBrokerageUnit(String brokerageUnit) {
		this.brokerageUnit = brokerageUnit;
	}

	public Integer getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Integer totalQty) {
		this.totalQty = totalQty;
	}

	public Integer getPortInwardDetailId() {
		return portInwardDetailId;
	}

	public void setPortInwardDetailId(Integer portInwardDetailId) {
		this.portInwardDetailId = portInwardDetailId;
	}

	public Integer getPortInwardId() {
		return portInwardId;
	}

	public void setPortInwardId(Integer portInwardId) {
		this.portInwardId = portInwardId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getBrokerName() {
		return brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	public Double getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(Double brokerage) {
		this.brokerage = brokerage;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public Double getExcise() {
		return excise;
	}

	public void setExcise(Double excise) {
		this.excise = excise;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getTransport() {
		return transport;
	}

	public void setTransport(Double transport) {
		this.transport = transport;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "PortPurchaseOrderVO [portInwardDetailId=" + portInwardDetailId + "," + " portInwardId=" + portInwardId
				+ ", custName=" + custName + ", brokerName=" + brokerName + ", brokerage=" + brokerage
				+ ",brokerageUnit=" + brokerageUnit + ",rate=" + rate + ", excise=" + excise + ", deliveryAddr="
				+ deliveryAddr + ",tax=" + tax + ", totalQty=" + totalQty + ",transport=" + transport
				+ ", paymentTerms=" + paymentTerms + ", comments=" + comments + "]";
	}

}
