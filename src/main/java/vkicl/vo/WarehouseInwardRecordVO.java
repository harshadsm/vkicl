package vkicl.vo;

import java.awt.Shape;

public class WarehouseInwardRecordVO {
	private Integer portInwardDetailId;
	private Integer portInwardShipmentId;
	private Integer portInwardId;
	
	private Integer length;
	private Integer width;
	private Double thickness;
	private Integer availableQuantity;
	private Integer orderedQuantity;
	
	private String grade;
	private String materialType;
	private String desc;
	private String vesselDate;
	private String vesselName;
	
	private String vehicleDate;
	private String vehicleName;
	private String millName;
	
	
	private Double secWt;
	private Double balQty;
	
	private String heatNo;
	private String plateNo;
	private String location;
	
	
	
	
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
	public Integer getOrderedQuantity() {
		return orderedQuantity;
	}
	public void setOrderedQuantity(Integer orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
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
				+ ", thickness=" + thickness + ", availableQuantity=" + availableQuantity + ", orderedQuantity="
				+ orderedQuantity + ", grade=" + grade + ", materialType=" + materialType + ", desc=" + desc
				+ ", vesselDate=" + vesselDate + ", vesselName=" + vesselName + ", vehicleDate=" + vehicleDate
				+ ", vehicleName=" + vehicleName + ", millName=" + millName + ", secWt=" + secWt + ", balQty=" + balQty
				+ ", heatNo=" + heatNo + ", plateNo=" + plateNo + ", location=" + location + "]";
	}
	
	
}
