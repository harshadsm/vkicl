package vkicl.vo;

public class PortOutwardPostDataContainerVO {

	
	private String destinationName;
	private String vehicleNumber;
	private String vehicleDate;
	private String selectedPortInventoryItemsJson;
	private String grade;
	private String width;
	private String length;
	private String thickness;
	private Double secWt;
	private String qty;
	private String materialType;
	private String millName;
	
	
	
	public Double getSecWt() {
		return secWt;
	}
	public void setSecWt(Double secWt) {
		this.secWt = secWt;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
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
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getThickness() {
		return thickness;
	}
	public void setThickness(String thickness) {
		this.thickness = thickness;
	}
	
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
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
	public String getSelectedPortInventoryItemsJson() {
		return selectedPortInventoryItemsJson;
	}
	public void setSelectedPortInventoryItemsJson(String selectedPortInventoryItemsJson) {
		this.selectedPortInventoryItemsJson = selectedPortInventoryItemsJson;
	}
	
	
}
