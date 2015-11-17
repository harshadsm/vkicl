package vkicl.form;

import java.util.Arrays;

@SuppressWarnings("serial")
public class PortOutwardForm extends BaseForm {

	private int dispatchNumber = 0;

	private String destination = "";
	private String destinationName = "";
	private String vehicleNumber = "";
	private String vehicleDate = "";
	private String[] vesselName = null;
	private String[] vesselDate = null;
	private String[] beNo = null;
	private String[] materialType = null;
	private String[] grade = null;
	private String[] desc = null;
	private Integer[] length = null;
	private Integer[] width = null;
	private Double[] thickness = null;
	private Double[] actualWt = null;
	private String[] actualWtUnit = null;
	private Double[] secWt = null;
	private String[] secWtUnit = null;
	private Integer[] qty = null;

	public PortOutwardForm() {
	}

	public void clear() {

		dispatchNumber = 0;

		destination = "";
		destinationName = "";
		vehicleNumber = "";
		vehicleDate = "";
		vesselName = null;
		vesselDate = null;
		beNo = null;
		materialType = null;
		grade = null;
		desc = null;
		length = null;
		width = null;
		thickness = null;
		actualWt = null;
		actualWtUnit = null;
		secWt = null;
		secWtUnit = null;
		qty = null;
		super.clear();
	}

	public int getDispatchNumber() {
		return dispatchNumber;
	}

	public void setDispatchNumber(int dispatchNumber) {
		this.dispatchNumber = dispatchNumber;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
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

	public String[] getVesselName() {
		return vesselName;
	}

	public void setVesselName(String[] vesselName) {
		this.vesselName = vesselName;
	}

	public String[] getVesselDate() {
		return vesselDate;
	}

	public void setVesselDate(String[] vesselDate) {
		this.vesselDate = vesselDate;
	}

	public String[] getBeNo() {
		return beNo;
	}

	public void setBeNo(String[] beNo) {
		this.beNo = beNo;
	}

	public String[] getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String[] materialType) {
		this.materialType = materialType;
	}

	public String[] getGrade() {
		return grade;
	}

	public void setGrade(String[] grade) {
		this.grade = grade;
	}

	public String[] getDesc() {
		return desc;
	}

	public void setDesc(String[] desc) {
		this.desc = desc;
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

	public Double[] getActualWt() {
		return actualWt;
	}

	public void setActualWt(Double[] actualWt) {
		this.actualWt = actualWt;
	}

	public String[] getActualWtUnit() {
		return actualWtUnit;
	}

	public void setActualWtUnit(String[] actualWtUnit) {
		this.actualWtUnit = actualWtUnit;
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

	@Override
	public String toString() {
		return "PortOutwardForm [dispatchNumber=" + dispatchNumber
				+ ", destination=" + destination + ", destinationName="
				+ destinationName + ", vehicleNumber=" + vehicleNumber
				+ ", vehicleDate=" + vehicleDate + ", vesselName="
				+ Arrays.toString(vesselName) + ", vesselDate="
				+ Arrays.toString(vesselDate) + ", beNo="
				+ Arrays.toString(beNo) + ", materialType="
				+ Arrays.toString(materialType) + ", grade="
				+ Arrays.toString(grade) + ", desc=" + Arrays.toString(desc)
				+ ", length=" + Arrays.toString(length) + ", width="
				+ Arrays.toString(width) + ", thickness="
				+ Arrays.toString(thickness) + ", actualWt="
				+ Arrays.toString(actualWt) + ", actualWtUnit="
				+ Arrays.toString(actualWtUnit) + ", secWt="
				+ Arrays.toString(secWt) + ", secWtUnit="
				+ Arrays.toString(secWtUnit) + ", qty=" + Arrays.toString(qty)
				+ "]";
	}

}
