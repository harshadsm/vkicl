package vkicl.report.form;

import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import vkicl.form.BaseForm;
import vkicl.report.bean.PortInwardBean;

@SuppressWarnings("serial")
public class PortInwardReportForm extends BaseForm {

	private String fromDate = null;
	private String toDate = null;
	private String vesselName = null;
	private String materialType = null;
	private String grade = null;

	private ArrayList<LabelValueBean> vesselNameList = null;
	private ArrayList<LabelValueBean> materialTypeList = null;
	private ArrayList<LabelValueBean> gradeList = null;

	private ArrayList<PortInwardBean> reportList = null;

	public PortInwardReportForm() {
	}

	public void clear() {
		fromDate = null;
		toDate = null;
		vesselName = null;
		materialType = null;
		reportList = null;

		vesselNameList = null;
		materialTypeList = null;
		gradeList = null;
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

	public ArrayList<PortInwardBean> getReportList() {
		return reportList;
	}

	public void setReportList(ArrayList<PortInwardBean> reportList) {
		this.reportList = reportList;
	}

	@Override
	public String toString() {
		return "PortInwardReportForm [fromDate=" + fromDate + ", toDate="
				+ toDate + ", vesselName=" + vesselName + ", materialType="
				+ materialType + ", grade=" + grade + ", vesselNameList="
				+ vesselNameList + ", materialTypeList=" + materialTypeList
				+ ", gradeList=" + gradeList + ", reportList=" + reportList
				+ "]";
	}

}
