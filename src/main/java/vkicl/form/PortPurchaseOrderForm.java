package vkicl.form;

import java.util.Arrays;
import vkicl.form.*;

@SuppressWarnings("serial")
public class PortPurchaseOrderForm extends BaseForm {

	private int id;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	@Override
	public String toString() {
		return "PortPurchaseOrderForm [id=" + id + ", customerName=" + customerName + ", brokerName=" + brokerName
				+ ", brokerage=" + brokerage + ", brokerageUnit=" + brokerageUnit + ", deliveryAddress="
				+ deliveryAddress + ", rate=" + rate + ", excise=" + excise + ", tax=" + tax + ", transport="
				+ transport + ", paymentTerms=" + paymentTerms + ", totalQuantity=" + totalQuantity + "]";
	}

}