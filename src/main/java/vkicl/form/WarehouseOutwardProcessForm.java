package vkicl.form;

import java.util.ArrayList;
import java.util.Arrays;

import vkicl.report.bean.WarehouseLocationBean;

@SuppressWarnings("serial")
public class WarehouseOutwardProcessForm extends BaseForm {

	int dispatchNo = 0;
	private Double actWt = null;
	private String actWtUnit = null;
	private String vehicleNumber = null;
	private String vehicleDate = null;

	private String[] location = null;
	private String[] heatNo = null;
	private String[] plateNo = null;

	private String[] millName = null;
	private String[] make = null;
	private String[] grade = null;
	private Integer[] length = null;
	private Integer[] width = null;
	private Double[] thickness = null;
	private Double[] secWt = null;
	private String[] secWtUnit = null;
	private Integer[] qty = null;

	private ArrayList<WarehouseLocationBean> resultList = null;

	public WarehouseOutwardProcessForm() {
	}

	public WarehouseOutwardProcessForm(int dispatchNo) {
		this.dispatchNo = dispatchNo;
	}

	public void clear() {
		dispatchNo = 0;
		actWt = null;
		actWtUnit = null;
		vehicleNumber = null;
		vehicleDate = null;

		location = null;
		heatNo = null;
		plateNo = null;

		millName = null;
		make = null;
		grade = null;
		length = null;
		width = null;
		thickness = null;
		secWt = null;
		secWtUnit = null;
		qty = null;
		resultList = null;

		super.clear();
	}

	public int getDispatchNo() {
		return dispatchNo;
	}

	public void setDispatchNo(int dispatchNo) {
		this.dispatchNo = dispatchNo;
	}

	public Double getActWt() {
		return actWt;
	}

	public void setActWt(Double actWt) {
		this.actWt = actWt;
	}

	public String getActWtUnit() {
		return actWtUnit;
	}

	public void setActWtUnit(String actWtUnit) {
		this.actWtUnit = actWtUnit;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getVehicleDate() {
		return vehicleDate;
	}

	public void setVehicleDate(String vehicleDate) {
		this.vehicleDate = vehicleDate;
	}

	public String[] getLocation() {
		return location;
	}

	public void setLocation(String[] location) {
		this.location = location;
	}

	public String[] getHeatNo() {
		return heatNo;
	}

	public void setHeatNo(String[] heatNo) {
		this.heatNo = heatNo;
	}

	public String[] getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String[] plateNo) {
		this.plateNo = plateNo;
	}

	public String[] getMillName() {
		return millName;
	}

	public void setMillName(String[] millName) {
		this.millName = millName;
	}

	public String[] getMake() {
		return make;
	}

	public void setMake(String[] make) {
		this.make = make;
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

	public Double[] getSecWt() {
		return secWt;
	}

	public void setSecWt(Double[] secWt) {
		this.secWt = secWt;
	}

	public String[] getSecWtUnit() {
		return secWtUnit;
	}

	public void setSecWtUnit(String[] secWtUnit) {
		this.secWtUnit = secWtUnit;
	}

	public Integer[] getQty() {
		return qty;
	}

	public void setQty(Integer[] qty) {
		this.qty = qty;
	}

	public ArrayList<WarehouseLocationBean> getResultList() {
		return resultList;
	}

	public void setResultList(ArrayList<WarehouseLocationBean> resultList) {
		this.resultList = resultList;
	}

	@Override
	public String toString() {
		return "WarehouseOutwardProcessForm [dispatchNo=" + dispatchNo
				+ ", actWt=" + actWt + ", actWtUnit=" + actWtUnit
				+ ", vehicleNumber=" + vehicleNumber + ", vehicleDate="
				+ vehicleDate + ", location=" + Arrays.toString(location)
				+ ", heatNo=" + Arrays.toString(heatNo) + ", plateNo="
				+ Arrays.toString(plateNo) + ", millName="
				+ Arrays.toString(millName) + ", make=" + Arrays.toString(make)
				+ ", grade=" + Arrays.toString(grade) + ", length="
				+ Arrays.toString(length) + ", width=" + Arrays.toString(width)
				+ ", thickness=" + Arrays.toString(thickness) + ", secWt="
				+ Arrays.toString(secWt) + ", secWtUnit="
				+ Arrays.toString(secWtUnit) + ", qty=" + Arrays.toString(qty)
				+ ", resultList=" + resultList + "]";
	}

}
