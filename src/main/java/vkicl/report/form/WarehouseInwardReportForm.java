package vkicl.report.form;

import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import vkicl.form.BaseForm;
import vkicl.report.bean.WarehouseInwardBean;

@SuppressWarnings("serial")
public class WarehouseInwardReportForm extends BaseForm {

	private String fromDate = null;
	private String toDate = null;
	private String vesselName = null;
	private String vesselDate = null;
	private String receivedFrom = null;
	private String materialType = null;
	private String grade = null;
	
	private ArrayList<LabelValueBean> vesselNameList = null;
	private ArrayList<LabelValueBean> vesselDateList = null;
	private ArrayList<LabelValueBean> receivedFromList = null;
	private ArrayList<LabelValueBean> materialTypeList = null;
	private ArrayList<LabelValueBean> gradeList = null;

	private ArrayList<WarehouseInwardBean> reportList = null;

	public WarehouseInwardReportForm() {
	}

	public void clear() {
		fromDate = null;
		toDate = null;
		vesselName = null;
		vesselDate = null;
		receivedFrom = null;
		materialType = null;
		reportList = null;
		vesselNameList = null;
		vesselDateList = null;
		receivedFromList = null;
		materialTypeList = null;
		grade = null;
		super.clear();
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

	public String getVesselDate() {
		return vesselDate;
	}

	public void setVesselDate(String vesselDate) {
		this.vesselDate = vesselDate;
	}

	public String getReceivedFrom() {
		return receivedFrom;
	}

	public void setReceivedFrom(String receivedFrom) {
		this.receivedFrom = receivedFrom;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public ArrayList<LabelValueBean> getVesselNameList() {
		return vesselNameList;
	}

	public void setVesselNameList(ArrayList<LabelValueBean> vesselNameList) {
		this.vesselNameList = vesselNameList;
	}

	public ArrayList<LabelValueBean> getVesselDateList() {
		return vesselDateList;
	}

	public void setVesselDateList(ArrayList<LabelValueBean> vesselDateList) {
		this.vesselDateList = vesselDateList;
	}

	public ArrayList<LabelValueBean> getReceivedFromList() {
		return receivedFromList;
	}

	public void setReceivedFromList(ArrayList<LabelValueBean> receivedFromList) {
		this.receivedFromList = receivedFromList;
	}

	public ArrayList<LabelValueBean> getMaterialTypeList() {
		return materialTypeList;
	}

	public void setMaterialTypeList(ArrayList<LabelValueBean> materialTypeList) {
		this.materialTypeList = materialTypeList;
	}

	public ArrayList<WarehouseInwardBean> getReportList() {
		return reportList;
	}

	public void setReportList(ArrayList<WarehouseInwardBean> reportList) {
		this.reportList = reportList;
	}
	
	

	public ArrayList<LabelValueBean> getGradeList() {
		return gradeList;
	}

	public void setGradeList(ArrayList<LabelValueBean> gradeList) {
		this.gradeList = gradeList;
	}
	
	

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "WarehouseInwardReportForm [fromDate=" + fromDate + ", toDate="
				+ toDate + ", vesselName=" + vesselName + ", vesselDate="
				+ vesselDate + ", receivedFrom=" + receivedFrom
				+ ", materialType=" + materialType + ", vesselNameList="
				+ vesselNameList + ", vesselDateList=" + vesselDateList
				+ ", receivedFromList=" + receivedFromList
				+ ", materialTypeList=" + materialTypeList + ", reportList="
				+ reportList + "]";
	}

}
