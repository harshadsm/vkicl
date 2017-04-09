package vkicl.vo;

import java.awt.Shape;

public class WarehouseInwardRecordVO {
	private Integer portInwardDetailId;
	private Integer portInwardShipmentId;
	private Integer portInwardId;
	private Integer portOutwardId;

	private Integer length;
	private Integer width;
	private Double thickness;
	private Integer availableQuantity;

	private String grade;
	private String materialType;
	private Double actualWt;
	
	private String desc;
	private String vesselDate;
	private String vesselName;
	private String make;
	private String vendorName;

	private String vehicleDate;
	private String vehicleName;
	private String millName;

	private Double secWt;
	private Double balQty;

	private String heatNo;
	private String plateNo;
	private String location;

	private String remark;
	private Double labelWeight;
	
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getLabelWeight() {
		return labelWeight;
	}

	public void setLabelWeight(Double labelWeight) {
		this.labelWeight = labelWeight;
	}

	public Integer getPortOutwardId() {
		return portOutwardId;
	}

	public void setPortOutwardId(Integer portOutwardId) {
		this.portOutwardId = portOutwardId;
	}
	
	public Double getActualWt() {
		return actualWt;
	}

	public void setActualWt(Double actualWt) {
		this.actualWt = actualWt;
	}

	public String getActualWt_unit() {
		return actualWt_unit;
	}

	public void setActualWt_unit(String actualWt_unit) {
		this.actualWt_unit = actualWt_unit;
	}

	private String actualWt_unit;

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}



	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}


	public Double getSecWt() {
		return secWt;
	}

	public void setSecWt(Double secWt) {
		this.secWt = secWt;
	}

	public Double getBalQty() {
		return balQty;
	}

	public void setBalQty(Double qty) {
		this.balQty = qty;
	}

	public String getVehicleDate() {
		return vehicleDate;
	}

	public void setVehicleDate(String vehicleDate) {
		this.vehicleDate = vehicleDate;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getMillName() {
		return millName;
	}

	public void setMillName(String millName) {
		this.millName = millName;
	}

	public Integer getPortInwardDetailId() {
		return portInwardDetailId;
	}

	public void setPortInwardDetailId(Integer portInwardDetailId) {
		this.portInwardDetailId = portInwardDetailId;
	}

	public Integer getPortInwardShipmentId() {
		return portInwardShipmentId;
	}

	public void setPortInwardShipmentId(Integer portInwardShipmentId) {
		this.portInwardShipmentId = portInwardShipmentId;
	}

	public Integer getPortInwardId() {
		return portInwardId;
	}

	public void setPortInwardId(Integer portInwardId) {
		this.portInwardId = portInwardId;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Double getThickness() {
		return thickness;
	}

	public void setThickness(Double thickness) {
		this.thickness = thickness;
	}

	public Integer getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity = availableQuantity;
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

	public String getVesselDate() {
		return vesselDate;
	}

	public void setVesselDate(String vesselDate) {
		this.vesselDate = vesselDate;
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

	public String getHeatNo() {
		return heatNo;
	}

	public void setHeatNo(String heatNo) {
		this.heatNo = heatNo;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "WarehouseInwardRecordVO [portInwardDetailId=" + portInwardDetailId + ", portInwardShipmentId="
				+ portInwardShipmentId + ", portInwardId=" + portInwardId + ", length=" + length + ", width=" + width
				+ ", thickness=" + thickness + ", availableQuantity=" + availableQuantity + ", grade=" + grade
				+ ", materialType=" + materialType + ", desc=" + desc + ", vesselDate=" + vesselDate + ", vesselName="
				+ vesselName + ", vehicleDate=" + vehicleDate + ", vehicleName=" + vehicleName + ", millName="
				+ millName + ", secWt=" + secWt + ", balQty=" + balQty + ", heatNo=" + heatNo + ", plateNo=" + plateNo
				+ ", location=" + location + "]";
	}

}
