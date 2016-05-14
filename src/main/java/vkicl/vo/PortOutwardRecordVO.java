package vkicl.vo;

public class PortOutwardRecordVO {
	private Integer portInwardDetailId;
	private Integer portInwardShipmentId;
	private Integer portInwardId;
	
	private Integer length;
	private Integer width;
	private Double thickness;
	private Integer availableQuantity;
	private Integer orderedQuantity;
	
	private String grade;
	private String desc;
	private String vesselDate;
	private String vesselName;
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
	@Override
	public String toString() {
		return "PortOutwardRecordVO [portInwardDetailId=" + portInwardDetailId + ", portInwardShipmentId="
				+ portInwardShipmentId + ", portInwardId=" + portInwardId + ", length=" + length + ", width=" + width
				+ ", thickness=" + thickness + ", availableQuantity=" + availableQuantity + ", orderedQuantity="
				+ orderedQuantity + ", grade=" + grade + ", desc=" + desc + ", vesselDate=" + vesselDate
				+ ", vesselName=" + vesselName + "]";
	}
	
	
}
