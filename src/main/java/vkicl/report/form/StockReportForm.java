package vkicl.report.form;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.struts.util.LabelValueBean;

import vkicl.form.BaseForm;
import vkicl.report.bean.StockBean;

@SuppressWarnings("serial")
public class StockReportForm extends BaseForm {

	private String materialType = null;
	private String millName = null;
	private String grade = null;
	private String make = null;
	public double getSectionWt() {
		return sectionWt;
	}

	public void setSectionWt(double sectionWt) {
		this.sectionWt = sectionWt;
	}

	private String location = null;
	private double sectionWt = -1d;
	private double actualWt = -1d;
	public double getActualWt() {
		return actualWt;
	}

	public void setActualWt(double actualWt) {
		this.actualWt = actualWt;
	}

	private double thickness = 0;
	
	private Integer stock_Bal_id = null;

	private ArrayList<LabelValueBean> materialTypeList = null;
	private ArrayList<LabelValueBean> millNameList = null;
	private ArrayList<LabelValueBean> gradeList = null;
	private ArrayList<LabelValueBean> makeList = null;
	private ArrayList<LabelValueBean> locationList = null;

	private ArrayList<StockBean> reportList = null;

	public StockReportForm() {
	}

	public void clear() {
		materialType = null;
		millName = null;
		grade = null;
		make = null;
		location = null;
		thickness = 0;

		materialTypeList = null;
		millNameList = null;
		gradeList = null;
		makeList = null;
		locationList = null;
		super.clear();
	}

	public Integer getStock_Bal_id() {
		return stock_Bal_id;
	}

	public void setStock_Bal_id(Integer stock_Bal_id) {
		this.stock_Bal_id = stock_Bal_id;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public String getMillName() {
		return millName;
	}

	public void setMillName(String millName) {
		this.millName = millName;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getThickness() {
		return thickness;
	}

	public void setThickness(double thickness) {
		this.thickness = thickness;
	}

	public ArrayList<LabelValueBean> getMaterialTypeList() {
		return materialTypeList;
	}

	public void setMaterialTypeList(ArrayList<LabelValueBean> materialTypeList) {
		this.materialTypeList = materialTypeList;
	}

	public ArrayList<LabelValueBean> getMillNameList() {
		return millNameList;
	}

	public void setMillNameList(ArrayList<LabelValueBean> millNameList) {
		this.millNameList = millNameList;
	}

	public ArrayList<LabelValueBean> getGradeList() {
		return gradeList;
	}

	public void setGradeList(ArrayList<LabelValueBean> gradeList) {
		this.gradeList = gradeList;
	}

	public ArrayList<LabelValueBean> getMakeList() {
		return makeList;
	}

	public void setMakeList(ArrayList<LabelValueBean> makeList) {
		this.makeList = makeList;
	}

	public ArrayList<LabelValueBean> getLocationList() {
		return locationList;
	}

	public void setLocationList(ArrayList<LabelValueBean> locationList) {
		this.locationList = locationList;
	}

	public ArrayList<StockBean> getReportList() {
		return reportList;
	}

	public void setReportList(ArrayList<StockBean> reportList) {
		this.reportList = reportList;
	}

	@Override
	public String toString() {
		return "StockReportForm [materialType=" + materialType + ", millName="
				+ millName + ", grade=" + grade + ", make="
				+ make + ", location=" + location + ", thickness=" + thickness
				+ ", materialTypeList=" + materialTypeList + ", millNameList="
				+ millNameList + ", gradeList=" + gradeList + ", makeList="
				+ makeList + ", locationList=" + locationList + ", reportList="
				+ reportList + "]";
	}

}
