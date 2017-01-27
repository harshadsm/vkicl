package vkicl.report.bean;

import java.util.ArrayList;

import vkicl.form.PortPurchaseOrderForm;

@SuppressWarnings("serial")
public class PortPurchaseOrderDeliveryBean extends BaseReportBean {

	private String customerName = "";
	private String brokerName = "";
	private String brokerage = "";
	private String brokerageUnit = "";
	private String deliveryAddress = "";
	private Double rate = null;
	private Double excise = null;
	private Double tax = null;
	private Double transport = null;
	private String paymentTerms = null;
	private Integer totalQuantity = null;
	private Integer pendingQuantity = null;
	private String ppoDate = "";
	private Integer ppoNo = null;

	public Integer getPpoNo() {
		return ppoNo;
	}

	public void setPpoNo(Integer ppoNo) {
		this.ppoNo = ppoNo;
	}

	private Integer id = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBrokerName() {
		return brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	public String getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(String brokerage) {
		this.brokerage = brokerage;
	}

	public String getBrokerageUnit() {
		return brokerageUnit;
	}

	public void setBrokerageUnit(String brokerageUnit) {
		this.brokerageUnit = brokerageUnit;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
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

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Integer getPendingQuantity() {
		return pendingQuantity;
	}

	public void setPendingQuantity(Integer pendingQuantity) {
		this.pendingQuantity = pendingQuantity;
	}

	public String getPpoDate() {
		return ppoDate;
	}

	public void setPpoDate(String ppoDate) {
		this.ppoDate = ppoDate;
	}

	@Override
	public String toString() {
		return "PortPurchaseOrderDeliveryBean [id=" + id + ",ppoNo=" + ppoNo + ",customerName=" + customerName
				+ ", brokerName=" + brokerName + ", brokerage=" + brokerage + ", brokerageUnit=" + brokerageUnit
				+ ", deliveryAddress=" + deliveryAddress + ", rate=" + rate + ", excise=" + excise + ", tax=" + tax
				+ ", transport=" + transport + ", paymentTerms=" + paymentTerms + ", totalQuantity=" + totalQuantity
				+ ", pendingQuantity=" + pendingQuantity + "]";
	}

}
