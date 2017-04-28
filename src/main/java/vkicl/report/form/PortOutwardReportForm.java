package vkicl.report.form;

import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import vkicl.form.BaseForm;
import vkicl.report.bean.PortOutwardBean;

@SuppressWarnings("serial")
public class PortOutwardReportForm extends BaseForm {

	private String fromDate = null;
	private String toDate = null;
	private String vesselName = null;
	private String millName = null;
	private String dispatchedTo = null;
	private String grade = null;
 
	private String invoiceNo = null;

	private String vehicleDate=null;
	private String vehicleNumber=null;
	
	private ArrayList<LabelValueBean> vesselNameList = null;
	private ArrayList<LabelValueBean> dispatchedToList = null;
	private ArrayList<LabelValueBean> gradeList = null;
	private ArrayList<PortOutwardBean> reportList = null;
	
	
	public String getMillName() {
		return millName;
	}

	public String getVehicleDate() {
		return vehicleDate;
	}

	public void setVehicleDate(String vehicleDate) {
		this.vehicleDate = vehicleDate;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public void setMillName(String millName) {
		this.millName = millName;
	}

	public PortOutwardReportForm() {
	}

	public void clear() {
		fromDate = null;
		toDate = null;
		vesselName = null;
		dispatchedTo = null;
		
		reportList = null;
		vesselNameList = null;
		dispatchedToList = null;
		gradeList = null;
		invoiceNo = null;
		super.clear();
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public ArrayList<LabelValueBean> getGradeList() {
		return gradeList;
	}

	public void setGradeList(ArrayList<LabelValueBean> gradeList) {
		this.gradeList = gradeList;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public String getDispatchedTo() {
		return dispatchedTo;
	}

	public void setDispatchedTo(String dispatchedTo) {
		this.dispatchedTo = dispatchedTo;
	}

	public ArrayList<LabelValueBean> getVesselNameList() {
		return vesselNameList;
	}

	public void setVesselNameList(ArrayList<LabelValueBean> vesselNameList) {
		this.vesselNameList = vesselNameList;
	}

	public ArrayList<LabelValueBean> getDispatchedToList() {
		return dispatchedToList;
	}

	public void setDispatchedToList(ArrayList<LabelValueBean> dispatchedToList) {
		this.dispatchedToList = dispatchedToList;
	}

	public ArrayList<PortOutwardBean> getReportList() {
		return reportList;
	}

	public void setReportList(ArrayList<PortOutwardBean> reportList) {
		this.reportList = reportList;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	

	@Override
	public String toString() {
		return "PortOutwardReportForm [fromDate=" + fromDate + ", toDate=" + toDate + ", vesselName=" + vesselName+",millName="+millName
				+ ", dispatchedTo=" + dispatchedTo + ", invoiceNo=" + invoiceNo +", grade=" + grade+ ", vesselNameList=" + vesselNameList
				+ ", dispatchedToList=" + dispatchedToList + ", reportList=" + reportList+", gradeList=" + gradeList +"]";
	}

	

}
