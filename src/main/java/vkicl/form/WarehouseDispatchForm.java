package vkicl.form;

import java.util.Arrays;

@SuppressWarnings("serial")
public class WarehouseDispatchForm extends BaseForm {

	private int id;

	private String poNo = "";
	private String date = "";
	private String vehicleNumber = "";
	private String handleBy = "";
	private String transporterName = "";

	private String transport = "";
	private String to = "";
	private String transportRate = "";
	private String transportUnit = "";

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

	private String[] make = null;
	private String[] millName = null;
	private String[] grade = null;
	private Integer[] length = null;
	private Integer[] width = null;
	private Double[] thickness = null;
	private Integer[] qty = null;
	private Double[] actWt = null;
	private String[] actWtUnit = null;
	private String[] rate = null;
	private String[] rateUnit = null;
	private String[] taxes = null;
	private String[] excise = null;

	private int total;
	private boolean mtc = false;
	private boolean inspection = false;
	private String inspectionCharges = "";
	private boolean utReport = false;
	private boolean labReport = false;
	private String toAcc = "";
	private String comments = "";

	public void clear() {

		id = 0;
		poNo = "";
		date = "";
		vehicleNumber = "";
		handleBy = "";
		transporterName = "";

		transport = "";
		to = "";
		transportRate = "";
		transportUnit = "";

		lumsum = "";
		buyerName = "";
		consigneeName = "";
		brokerName = "";
		brokerage = "";
		brokerageUnit = "";
		paymentTerms = "";
		loadingCharges = "";
		loadingChargesUnit = "";
		cuttingCharges = "";
		cuttingChargesUnit = "";

		make = null;
		millName = null;
		grade = null;
		length = null;
		width = null;
		thickness = null;
		qty = null;
		actWt = null;
		actWtUnit = null;
		rate = null;
		rateUnit = null;
		taxes = null;
		excise = null;

		total = 0;
		mtc = false;
		inspection = false;
		inspectionCharges = "";
		utReport = false;
		labReport = false;
		toAcc = "";
		comments = "";

		super.clear();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getTransportRate() {
		return transportRate;
	}

	public void setTransportRate(String transportRate) {
		this.transportRate = transportRate;
	}

	public String getTransportUnit() {
		return transportUnit;
	}

	public void setTransportUnit(String transportUnit) {
		this.transportUnit = transportUnit;
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

	public String[] getMake() {
		return make;
	}

	public void setMake(String[] make) {
		this.make = make;
	}

	public String[] getMillName() {
		return millName;
	}

	public void setMillName(String[] millName) {
		this.millName = millName;
	}

	public String[] getGrade() {
		return grade;
	}

	public void setGrade(String[] grade) {
		this.grade = grade;
	}

	public Integer[] getLength() {
		return length;
	}

	public void setLength(Integer[] length) {
		this.length = length;
	}

	public Integer[] getWidth() {
		return width;
	}

	public void setWidth(Integer[] width) {
		this.width = width;
	}

	public Double[] getThickness() {
		return thickness;
	}

	public void setThickness(Double[] thickness) {
		this.thickness = thickness;
	}

	public Integer[] getQty() {
		return qty;
	}

	public void setQty(Integer[] qty) {
		this.qty = qty;
	}

	public Double[] getActWt() {
		return actWt;
	}

	public void setActWt(Double[] actWt) {
		this.actWt = actWt;
	}

	public String[] getActWtUnit() {
		return actWtUnit;
	}

	public void setActWtUnit(String[] actWtUnit) {
		this.actWtUnit = actWtUnit;
	}

	public String[] getRate() {
		return rate;
	}

	public void setRate(String[] rate) {
		this.rate = rate;
	}

	public String[] getRateUnit() {
		return rateUnit;
	}

	public void setRateUnit(String[] rateUnit) {
		this.rateUnit = rateUnit;
	}

	public String[] getTaxes() {
		return taxes;
	}

	public void setTaxes(String[] taxes) {
		this.taxes = taxes;
	}

	public String[] getExcise() {
		return excise;
	}

	public void setExcise(String[] excise) {
		this.excise = excise;
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
		return "WarehouseDispatchForm [id=" + id + ", poNo=" + poNo + ", date="
				+ date + ", vehicleNumber=" + vehicleNumber + ", handleBy="
				+ handleBy + ", transporterName=" + transporterName
				+ ", transport=" + transport + ", to=" + to
				+ ", transportRate=" + transportRate + ", transportUnit="
				+ transportUnit + ", lumsum=" + lumsum + ", buyerName="
				+ buyerName + ", consigneeName=" + consigneeName
				+ ", brokerName=" + brokerName + ", brokerage=" + brokerage
				+ ", brokerageUnit=" + brokerageUnit + ", paymentTerms="
				+ paymentTerms + ", loadingCharges=" + loadingCharges
				+ ", loadingChargesUnit=" + loadingChargesUnit
				+ ", cuttingCharges=" + cuttingCharges
				+ ", cuttingChargesUnit=" + cuttingChargesUnit + ", make="
				+ Arrays.toString(make) + ", millName="
				+ Arrays.toString(millName) + ", grade="
				+ Arrays.toString(grade) + ", length="
				+ Arrays.toString(length) + ", width=" + Arrays.toString(width)
				+ ", thickness=" + Arrays.toString(thickness) + ", qty="
				+ Arrays.toString(qty) + ", actWt=" + Arrays.toString(actWt)
				+ ", actWtUnit=" + Arrays.toString(actWtUnit) + ", rate="
				+ Arrays.toString(rate) + ", rateUnit="
				+ Arrays.toString(rateUnit) + ", taxes="
				+ Arrays.toString(taxes) + ", excise="
				+ Arrays.toString(excise) + ", total=" + total + ", mtc=" + mtc
				+ ", inspection=" + inspection + ", inspectionCharges="
				+ inspectionCharges + ", utReport=" + utReport + ", labReport="
				+ labReport + ", toAcc=" + toAcc + ", comments=" + comments
				+ "]";
	}

}