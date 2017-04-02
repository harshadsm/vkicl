package vkicl.vo;

import java.util.Date;

public class WarehouseOutwardVO3 {

	private Integer warehouseOutwardId;
	private Double actualWeight;
	private String actialWeightUnit;
	private Integer dispatchNo;
	private String vehicleNo;
	private String vehicleDate;
	private String createTS;
	private String buyerName;

	
	public Integer getWarehouseOutwardId() {
		return warehouseOutwardId;
	}
	public void setWarehouseOutwardId(Integer warehouseOutwardId) {
		this.warehouseOutwardId = warehouseOutwardId;
	}
	public Double getActualWeight() {
		return actualWeight;
	}
	public void setActualWeight(Double actualWeight) {
		this.actualWeight = actualWeight;
	}
	public String getActialWeightUnit() {
		return actialWeightUnit;
	}
	public void setActialWeightUnit(String actialWeightUnit) {
		this.actialWeightUnit = actialWeightUnit;
	}
	public Integer getDispatchNo() {
		return dispatchNo;
	}
	public void setDispatchNo(Integer dispatchNo) {
		this.dispatchNo = dispatchNo;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getVehicleDate() {
		return vehicleDate;
	}
	public void setVehicleDate(String vehicleDate) {
		this.vehicleDate = vehicleDate;
	}
	public String getCreateTS() {
		return createTS;
	}
	public void setCreateTS(String createTS) {
		this.createTS = createTS;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	
}
