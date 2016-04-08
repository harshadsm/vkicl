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
	private Date vesselDate;
	
	
	public Date getVesselDate() {
		return vesselDate;
	}
	public void setVesselDate(Date vesselDate) {
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
	
	
}
