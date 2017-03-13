package vkicl.vo;

import java.util.Date;

public class WarehouseOutwardReportVO {

	private Integer warehouseOutwardId;
	private Double actualWeight;
	private String actialWeightUnit;
	private Integer dispatchNo;
	private String vehicleNo;
	private Date vehicleDate;
	private Date createTS;
	private Integer dispatchDetailId;
	private Integer deliveredQuantity;
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
	public Date getVehicleDate() {
		return vehicleDate;
	}
	public void setVehicleDate(Date vehicleDate) {
		this.vehicleDate = vehicleDate;
	}
	public Date getCreateTS() {
		return createTS;
	}
	public void setCreateTS(Date createTS) {
		this.createTS = createTS;
	}
	public Integer getDispatchDetailId() {
		return dispatchDetailId;
	}
	public void setDispatchDetailId(Integer dispatchDetailId) {
		this.dispatchDetailId = dispatchDetailId;
	}
	public Integer getDeliveredQuantity() {
		return deliveredQuantity;
	}
	public void setDeliveredQuantity(Integer deliveredQuantity) {
		this.deliveredQuantity = deliveredQuantity;
	}
	
	
}
