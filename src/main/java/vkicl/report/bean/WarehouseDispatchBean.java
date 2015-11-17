package vkicl.report.bean;

@SuppressWarnings("serial")
public class WarehouseDispatchBean extends BaseReportBean {
	private int id;
	private String pending = "";

	private String poNo = "";
	private String date = "";
	private String vehicleNumber = "";
	private String handleBy = "";
	private String transporterName = "";
	private String toUs = "";
	private String toParty = "";
	private String toPay = "";
	private String prepaid = "";
	private String perMTFix = "";
	private String lumsum = "";
	private String buyerName = "";
	private String consigneeName = "";
	private String brokerName = "";
	private String brokerage = "";
	private String brokerageUnit = "";
	private String paymentTerms = "";
	private String loadingCharges = "";
	private String loadingChargesUnit = "";
	private String cuttingCharges = "";
	private String cuttingChargesUnit = "";

	private int total;
	private boolean mtc = false;
	private boolean inspection = false;
	private String inspectionCharges = "";
	private boolean utReport = false;
	private boolean labReport = false;
	private String toAcc = "";
	private String comments = "";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPending() {
		return pending;
	}

	public void setPending(String pending) {
		this.pending = pending;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getHandleBy() {
		return handleBy;
	}

	public void setHandleBy(String handleBy) {
		this.handleBy = handleBy;
	}

	public String getTransporterName() {
		return transporterName;
	}

	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}

	public String getToUs() {
		return toUs;
	}

	public void setToUs(String toUs) {
		this.toUs = toUs;
	}

	public String getToParty() {
		return toParty;
	}

	public void setToParty(String toParty) {
		this.toParty = toParty;
	}

	public String getToPay() {
		return toPay;
	}

	public void setToPay(String toPay) {
		this.toPay = toPay;
	}

	public String getPrepaid() {
		return prepaid;
	}

	public void setPrepaid(String prepaid) {
		this.prepaid = prepaid;
	}

	public String getPerMTFix() {
		return perMTFix;
	}

	public void setPerMTFix(String perMTFix) {
		this.perMTFix = perMTFix;
	}

	public String getLumsum() {
		return lumsum;
	}

	public void setLumsum(String lumsum) {
		this.lumsum = lumsum;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
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

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getLoadingCharges() {
		return loadingCharges;
	}

	public void setLoadingCharges(String loadingCharges) {
		this.loadingCharges = loadingCharges;
	}

	public String getLoadingChargesUnit() {
		return loadingChargesUnit;
	}

	public void setLoadingChargesUnit(String loadingChargesUnit) {
		this.loadingChargesUnit = loadingChargesUnit;
	}

	public String getCuttingCharges() {
		return cuttingCharges;
	}

	public void setCuttingCharges(String cuttingCharges) {
		this.cuttingCharges = cuttingCharges;
	}

	public String getCuttingChargesUnit() {
		return cuttingChargesUnit;
	}

	public void setCuttingChargesUnit(String cuttingChargesUnit) {
		this.cuttingChargesUnit = cuttingChargesUnit;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public boolean isMtc() {
		return mtc;
	}

	public void setMtc(boolean mtc) {
		this.mtc = mtc;
	}

	public boolean isInspection() {
		return inspection;
	}

	public void setInspection(boolean inspection) {
		this.inspection = inspection;
	}

	public String getInspectionCharges() {
		return inspectionCharges;
	}

	public void setInspectionCharges(String inspectionCharges) {
		this.inspectionCharges = inspectionCharges;
	}

	public boolean isUtReport() {
		return utReport;
	}

	public void setUtReport(boolean utReport) {
		this.utReport = utReport;
	}

	public boolean isLabReport() {
		return labReport;
	}

	public void setLabReport(boolean labReport) {
		this.labReport = labReport;
	}

	public String getToAcc() {
		return toAcc;
	}

	public void setToAcc(String toAcc) {
		this.toAcc = toAcc;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "WarehouseDispatchBean [id=" + id + ", pending=" + pending
				+ ", poNo=" + poNo + ", date=" + date + ", vehicleNumber="
				+ vehicleNumber + ", handleBy=" + handleBy
				+ ", transporterName=" + transporterName + ", toUs=" + toUs
				+ ", toParty=" + toParty + ", toPay=" + toPay + ", prepaid="
				+ prepaid + ", perMTFix=" + perMTFix + ", lumsum=" + lumsum
				+ ", buyerName=" + buyerName + ", consigneeName="
				+ consigneeName + ", brokerName=" + brokerName + ", brokerage="
				+ brokerage + ", brokerageUnit=" + brokerageUnit
				+ ", paymentTerms=" + paymentTerms + ", loadingCharges="
				+ loadingCharges + ", loadingChargesUnit=" + loadingChargesUnit
				+ ", cuttingCharges=" + cuttingCharges
				+ ", cuttingChargesUnit=" + cuttingChargesUnit + ", total="
				+ total + ", mtc=" + mtc + ", inspection=" + inspection
				+ ", inspectionCharges=" + inspectionCharges + ", utReport="
				+ utReport + ", labReport=" + labReport + ", toAcc=" + toAcc
				+ ", comments=" + comments + "]";
	}
}
