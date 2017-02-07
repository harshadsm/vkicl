package vkicl.vo;

import java.util.Date;

public class PortStockReportVO {

	private int portInwardId = 0;

	private int portInwardDetailId = 0;

	private int portInwardShipmentId = 0;
	private String vendorName = null;
	private String vesselName = null;
	private String vesselDate = null;

	private String millName = null;
	private String materialType = null;
	private String make = null;
	private String grade = null;
	private String desc = null;

	private Integer length = null;
	private Integer width = null;
	private Double thickness = null;
	private Integer qty = null;
	private Integer deliveredTalojaQty = null;
	private Integer ppoQty = null;

	private Integer balanceQty = null;

	public int getPortInwardId() {
		return portInwardId;
	}

	public void setPortInwardId(int portInwardId) {
		this.portInwardId = portInwardId;
	}

	public int getPortInwardDetailId() {
		return portInwardDetailId;
	}

	public void setPortInwardDetailId(int portInwardDetailId) {
		this.portInwardDetailId = portInwardDetailId;
	}

	public int getPortInwardShipmentId() {
		return portInwardShipmentId;
	}

	public void setPortInwardShipmentId(int portInwardShipmentId) {
		this.portInwardShipmentId = portInwardShipmentId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public String getVesselDate() {
		return vesselDate;
	}

	public void setVesselDate(String vesselDate) {
		this.vesselDate = vesselDate;
	}

	public String getMillName() {
		return millName;
	}

	public void setMillName(String millName) {
		this.millName = millName;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
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

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getDeliveredTalojaQty() {
		return deliveredTalojaQty;
	}

	public void setDeliveredTalojaQty(Integer deliveredTalojaQty) {
		this.deliveredTalojaQty = deliveredTalojaQty;
	}

	public Integer getPpoQty() {
		return ppoQty;
	}

	public void setPpoQty(Integer ppoQty) {
		this.ppoQty = ppoQty;
	}

	public Integer getBalanceQty() {
		return balanceQty;
	}

	public void setBalanceQty(Integer balanceQty) {
		this.balanceQty = balanceQty;
	}

	@Override
	public String toString() {
		return "PortStockBean [portInwardId=" + portInwardId + ",portInwardDetailId=" + portInwardDetailId
				+ ",portInwardShipmentId=" + portInwardShipmentId + "," + " vendorName=" + vendorName + ", vesselName="
				+ vesselName + ", vesselDate=" + vesselDate + ", millName=" + millName + ", materialType="
				+ materialType + ", make=" + make + ", grade=" + grade + ", desc=" + desc + ",  length=" + length
				+ ", width=" + width + ", thickness=" + thickness + ", qty=" + qty + ", ppoQty=" + ppoQty
				+ ",deliveredTalojaQty=" + deliveredTalojaQty + ", balanceQty=" + balanceQty + "]";
	}
}
