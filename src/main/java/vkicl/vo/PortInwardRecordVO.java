package vkicl.vo;

import java.util.Date;

public class PortInwardRecordVO {
	private Integer id;
	private String beNo;
	private String materialType;
	private String millName;
	private String make;
	private String grade;
	private String desc;
	private Double beWt;
	private String beWtUnit;
	private String vesselDate;
	private String vesselName;
	private String vendorName;
	private Integer countOfPortInwardDetailRecords;
	
	
	public String getVesselName() {
		return vesselName;
	}
	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getVesselDate() {
		return vesselDate;
	}
	public void setVesselDate(String vesselDate) {
		this.vesselDate = vesselDate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBeNo() {
		return beNo;
	}
	public void setBeNo(String beNo) {
		this.beNo = beNo;
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Double getBeWt() {
		return beWt;
	}
	public void setBeWt(Double beWt) {
		this.beWt = beWt;
	}
	public String getBeWtUnit() {
		return beWtUnit;
	}
	public void setBeWtUnit(String beWtUnit) {
		this.beWtUnit = beWtUnit;
	}
	public Integer getCountOfPortInwardDetailRecords() {
		return countOfPortInwardDetailRecords;
	}
	public void setCountOfPortInwardDetailRecords(Integer countOfPortInwardDetailRecords) {
		this.countOfPortInwardDetailRecords = countOfPortInwardDetailRecords;
	}
	
	
	
}
