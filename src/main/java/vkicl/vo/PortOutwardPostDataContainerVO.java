package vkicl.vo;

public class PortOutwardPostDataContainerVO {

	
	private String destinationName;
	private String vehicleNumber;
	private String vehicleDate;
	private String selectedPortInventoryItemsJson;
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
