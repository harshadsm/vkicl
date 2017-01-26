package vkicl.vo;

import java.util.Date;

public class PortInwardRecordVOForPPO {
	private Integer portInwardId;
	private String createTs;
	private String vesselName;
	private String vesselDate;
	private String vendorName;
	private String materialType;
	private String millName;
	private String make;
	private String grade;
	private String description;
	private Integer portInwardRecordTotalQuantity;
	private Integer totalPpoQuantityOrderedAgainstThatPortInwardRecord;
	private Integer totalQuantityDeliveredToTaloja;
	private Integer cumulativeBalPcsAtDock;
	public Integer getPortInwardId() {
		return portInwardId;
	}
	public void setPortInwardId(Integer portInwardId) {
		this.portInwardId = portInwardId;
	}
	public String getCreateTs() {
		return createTs;
	}
	public void setCreateTs(String createTs) {
		this.createTs = createTs;
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
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getPortInwardRecordTotalQuantity() {
		return portInwardRecordTotalQuantity;
	}
	public void setPortInwardRecordTotalQuantity(Integer portInwardRecordTotalQuantity) {
		this.portInwardRecordTotalQuantity = portInwardRecordTotalQuantity;
	}
	public Integer getTotalPpoQuantityOrderedAgainstThatPortInwardRecord() {
		return totalPpoQuantityOrderedAgainstThatPortInwardRecord;
	}
	public void setTotalPpoQuantityOrderedAgainstThatPortInwardRecord(
			Integer totalPpoQuantityOrderedAgainstThatPortInwardRecord) {
		this.totalPpoQuantityOrderedAgainstThatPortInwardRecord = totalPpoQuantityOrderedAgainstThatPortInwardRecord;
	}
	public Integer getTotalQuantityDeliveredToTaloja() {
		return totalQuantityDeliveredToTaloja;
	}
	public void setTotalQuantityDeliveredToTaloja(Integer totalQuantityDeliveredToTaloja) {
		this.totalQuantityDeliveredToTaloja = totalQuantityDeliveredToTaloja;
	}
	public Integer getCumulativeBalPcsAtDock() {
		return cumulativeBalPcsAtDock;
	}
	public void setCumulativeBalPcsAtDock(Integer cumulativeBalPcsAtDock) {
		this.cumulativeBalPcsAtDock = cumulativeBalPcsAtDock;
	}

}
