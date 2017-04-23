package vkicl.vo;

public class PortPurchaseOrderPostDataContainerVO {

	private String selectedPortInventoryItemsJson;

	public String getSelectedPortInventoryItemsJson() {
		return selectedPortInventoryItemsJson;
	}

	public void setSelectedPortInventoryItemsJson(String selectedPortInventoryItemsJson) {
		this.selectedPortInventoryItemsJson = selectedPortInventoryItemsJson;
	}

	private Integer portInwardDetailId;

	private Integer portInwardId;

	private String custName;
	private String brokerName;
	private Double brokerage;
	private String brokerageUnit;
	private String deliveryAddr;

	private Double rate;

	private String excise;
	private String tax;
	private String transport;

	private String paymentTerms;
	private String comments;
	private Integer totalQty;
	private Integer availableQuantity;
	
	private String vehicleNo;
	private String vehicleDate;
	private String transporterName;
	private Double transportRate;
	private String transportRateUnit;
	

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

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	
	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getVehicleDate() {
		return vehicleDate;
	}

	public void setVehicleDate(String vehicleDate) {
		this.vehicleDate = vehicleDate;
	}

	public String getTransporterName() {
		return transporterName;
	}

	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}
	
	

	public Double getTransportRate() {
		return transportRate;
	}

	public void setTransportRate(Double transportRate) {
		this.transportRate = transportRate;
	}

	public String getTransportRateUnit() {
		return transportRateUnit;
	}

	public void setTransportRateUnit(String transportRateUnit) {
		this.transportRateUnit = transportRateUnit;
	}

	@Override
	public String toString() {
		return "PortPurchaseOrderPostDataContainerVO [selectedPortInventoryItemsJson=" + selectedPortInventoryItemsJson
				+ ", portInwardDetailId=" + portInwardDetailId + ", portInwardId=" + portInwardId + ", custName="
				+ custName + ", brokerName=" + brokerName + ", brokerage=" + brokerage + ", brokerageUnit="
				+ brokerageUnit + ", deliveryAddr=" + deliveryAddr + ", rate=" + rate + ", excise=" + excise + ", tax="
				+ tax + ", transport=" + transport + ", paymentTerms=" + paymentTerms + ", comments=" + comments
				+ ", totalQty=" + totalQty + ", availableQuantity=" + availableQuantity + ", vehicleNo=" + vehicleNo
				+ ", vehicleDate=" + vehicleDate + ", transporterName=" + transporterName + ", transportRate="
				+ transportRate + ", transportRateUnit=" + transportRateUnit + "]";
	}

	
}
