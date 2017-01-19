package vkicl.form;

import java.util.Arrays;
import vkicl.form.*;

@SuppressWarnings("serial")
public class PortPurchaseOrderForm extends BaseForm {

	private int id;

	private String customerName = "";

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

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getExcise() {
		return excise;
	}

	public void setExcise(String excise) {
		this.excise = excise;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	private String brokerName = "";
	private String brokerage = "";
	private String brokerageUnit = "";
	private String deliveryAddress = "";
	private String rate = null;
	private String excise = null;
	private String tax = null;
	private String transport = null;
	private String paymentTerms = null;

	public void clear() {

		id = 0;

		transport = "";

		brokerName = "";
		brokerage = "";
		brokerageUnit = "";
		paymentTerms = "";

		rate = null;

		excise = null;

		// super.clear();
	}

	/*
	 * @Override public String toString() { return "WarehouseDispatchForm [id="
	 * + id + ", poNo=" + poNo + ", date=" + date + ", vehicleNumber=" +
	 * vehicleNumber + ", handleBy=" + handleBy + ", transporterName=" +
	 * transporterName + ", transport=" + transport + ", to=" + to +
	 * ", transportRate=" + transportRate + ", transportUnit=" + transportUnit +
	 * ", lumsum=" + lumsum + ", buyerName=" + buyerName + ", consigneeName=" +
	 * consigneeName + ", brokerName=" + brokerName + ", brokerage=" + brokerage
	 * + ", brokerageUnit=" + brokerageUnit + ", paymentTerms=" + paymentTerms +
	 * ", loadingCharges=" + loadingCharges + ", loadingChargesUnit=" +
	 * loadingChargesUnit + ", cuttingCharges=" + cuttingCharges +
	 * ", cuttingChargesUnit=" + cuttingChargesUnit + ", make=" +
	 * Arrays.toString(make) + ", millName=" + Arrays.toString(millName) +
	 * ", grade=" + Arrays.toString(grade) + ", length=" +
	 * Arrays.toString(length) + ", width=" + Arrays.toString(width) +
	 * ", thickness=" + Arrays.toString(thickness) + ", qty=" +
	 * Arrays.toString(qty) + ", actWt=" + Arrays.toString(actWt) +
	 * ", actWtUnit=" + Arrays.toString(actWtUnit) + ", rate=" +
	 * Arrays.toString(rate) + ", rateUnit=" + Arrays.toString(rateUnit) +
	 * ", taxes=" + Arrays.toString(taxes) + ", excise=" +
	 * Arrays.toString(excise) + ", total=" + total + ", mtc=" + mtc +
	 * ", inspection=" + inspection + ", inspectionCharges=" + inspectionCharges
	 * + ", utReport=" + utReport + ", labReport=" + labReport + ", toAcc=" +
	 * toAcc + ", comments=" + comments + "]"; }
	 */

}