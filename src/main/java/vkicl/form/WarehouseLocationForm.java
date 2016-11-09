package vkicl.form;

import java.util.ArrayList;

import vkicl.report.bean.WarehouseLocationBean;

@SuppressWarnings("serial")
public class WarehouseLocationForm extends BaseForm {

	private String millName = null;
	private String make = null;
	private String grade = null;
	private String length = null;
	private String width = null;
	private String thickness = null;
	private String location = null;
	private String previouslySelectedQtyFromWarehouseOutwardTemp;
	private String dispatchDetailRowId;

	private ArrayList<WarehouseLocationBean> resultList = null;

	public WarehouseLocationForm(String millName, String make, String grade,
			String length, String width, String thickness, String location) {
		super();
		this.millName = millName;
		this.make = make;
		this.grade = grade;
		this.length = length;
		this.width = width;
		this.thickness = thickness;
		this.location=location;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMillName() {
		return millName;
	}

	public void setMillName(String millName) {
		this.millName = millName;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getThickness() {
		return thickness;
	}

	public void setThickness(String thickness) {
		this.thickness = thickness;
	}

	public ArrayList<WarehouseLocationBean> getResultList() {
		return resultList;
	}

	public void setResultList(ArrayList<WarehouseLocationBean> resultList) {
		this.resultList = resultList;
	}
	
	

	public String getPreviouslySelectedQtyFromWarehouseOutwardTemp() {
		return previouslySelectedQtyFromWarehouseOutwardTemp;
	}

	public void setPreviouslySelectedQtyFromWarehouseOutwardTemp(String previouslySelectedQtyFromWarehouseOutwardTemp) {
		this.previouslySelectedQtyFromWarehouseOutwardTemp = previouslySelectedQtyFromWarehouseOutwardTemp;
	}

	public String getDispatchDetailRowId() {
		return dispatchDetailRowId;
	}

	public void setDispatchDetailRowId(String dispatchDetailRowId) {
		this.dispatchDetailRowId = dispatchDetailRowId;
	}

	@Override
	public String toString() {
		return "WarehouseLocationForm [millName=" + millName + ", make=" + make + ", grade=" + grade + ", length="
				+ length + ", width=" + width + ", thickness=" + thickness
				+ ", previouslySelectedQtyFromWarehouseOutwardTemp=" + previouslySelectedQtyFromWarehouseOutwardTemp
				+ ", dispatchDetailRowId=" + dispatchDetailRowId + ", resultList=" + resultList + "]";
	}

	

	

}