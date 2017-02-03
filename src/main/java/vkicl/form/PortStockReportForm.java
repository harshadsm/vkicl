package vkicl.form;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.struts.util.LabelValueBean;

import vkicl.report.bean.PortStockBean;

@SuppressWarnings("serial")
public class PortStockReportForm extends BaseForm {

	private String fromDate = null;
	private String toDate = null;
	private String vesselName = null;
	private String materialType = null;
	private String grade = null;

	private ArrayList<LabelValueBean> vesselNameList = null;
	private ArrayList<LabelValueBean> materialTypeList = null;
	private ArrayList<LabelValueBean> gradeList = null;

	private ArrayList<PortStockBean> reportList = null;

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

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public ArrayList<LabelValueBean> getVesselNameList() {
		return vesselNameList;
	}

	public void setVesselNameList(ArrayList<LabelValueBean> vesselNameList) {
		this.vesselNameList = vesselNameList;
	}

	public ArrayList<LabelValueBean> getMaterialTypeList() {
		return materialTypeList;
	}

	public void setMaterialTypeList(ArrayList<LabelValueBean> materialTypeList) {
		this.materialTypeList = materialTypeList;
	}

	public ArrayList<LabelValueBean> getGradeList() {
		return gradeList;
	}

	public void setGradeList(ArrayList<LabelValueBean> gradeList) {
		this.gradeList = gradeList;
	}

	public ArrayList<PortStockBean> getReportList() {
		return reportList;
	}

	public void setReportList(ArrayList<PortStockBean> reportList) {
		this.reportList = reportList;
	}

	@Override
	public String toString() {
		return "PortStockReportForm [fromDate=" + fromDate + ", toDate=" + toDate + ", vesselName=" + vesselName
				+ ", materialType=" + materialType + ", grade=" + grade + ", vesselNameList=" + vesselNameList
				+ ", materialTypeList=" + materialTypeList + ", gradeList=" + gradeList + ", reportList=" + reportList
				+ "]";
	}
}