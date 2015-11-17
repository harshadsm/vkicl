package vkicl.report.form;

import java.util.ArrayList;

import vkicl.form.BaseForm;
import vkicl.report.bean.WarehouseDispatchDetailsBean;

@SuppressWarnings("serial")
public class WarehouseDispatchDetailsReportForm extends BaseForm {

	private int id;
	private String pending = "Pending";

	private ArrayList<WarehouseDispatchDetailsBean> resultList = null;

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

	private int total;
	private boolean mtc = false;
	private boolean inspection = false;
	private String inspectionCharges = "";
	private boolean utReport = false;
	private boolean labReport = false;
	private String toAcc = "";
	private String comments = "";

	public WarehouseDispatchDetailsReportForm() {
		super();
	}

	public WarehouseDispatchDetailsReportForm(int id) {
		super();
		this.id = id;
	}

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

	public ArrayList<WarehouseDispatchDetailsBean> getResultList() {
		return resultList;
	}

	public void setResultList(ArrayList<WarehouseDispatchDetailsBean> resultList) {
		this.resultList = resultList;
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
		return "WarehouseDispatchDetailsReportForm [id=" + id + ", pending="
				+ pending + ", resultList=" + resultList + ", poNo=" + poNo
				+ ", date=" + date + ", vehicleNumber=" + vehicleNumber
				+ ", handleBy=" + handleBy + ", transporterName="
				+ transporterName + ", transport=" + transport + ", to=" + to
				+ ", transportRate=" + transportRate + ", transportUnit="
				+ transportUnit + ", lumsum=" + lumsum + ", buyerName="
				+ buyerName + ", consigneeName=" + consigneeName
				+ ", brokerName=" + brokerName + ", brokerage=" + brokerage
				+ ", brokerageUnit=" + brokerageUnit + ", paymentTerms="
				+ paymentTerms + ", loadingCharges=" + loadingCharges
				+ ", loadingChargesUnit=" + loadingChargesUnit
				+ ", cuttingCharges=" + cuttingCharges
				+ ", cuttingChargesUnit=" + cuttingChargesUnit + ", total="
				+ total + ", mtc=" + mtc + ", inspection=" + inspection
				+ ", inspectionCharges=" + inspectionCharges + ", utReport="
				+ utReport + ", labReport=" + labReport + ", toAcc=" + toAcc
				+ ", comments=" + comments + "]";
	}

}