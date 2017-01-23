package vkicl.vo;

public class PortPurchaseOrderPostDataContainerVO {

	private String selectedPortInventoryItemsJson;

	private String grade;
	private String width;
	private String length;
	private String thickness;
	private String qty;
	private String materialType;
	private String millName;
	private String vesselName;
	private String vesselDate;
	private String vendorName;
	private String make;
	private Double actualWt;
	private String actualWt_unit;
	private Integer portInwardDetailId;

	public Integer getPortInwardDetailId() {
		return portInwardDetailId;
	}

	public void setPortInwardDetailId(Integer portInwardDetailId) {
		this.portInwardDetailId = portInwardDetailId;
	}

	public String getSelectedPortInventoryItemsJson() {
		return selectedPortInventoryItemsJson;
	}

	public void setSelectedPortInventoryItemsJson(String selectedPortInventoryItemsJson) {
		this.selectedPortInventoryItemsJson = selectedPortInventoryItemsJson;
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

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
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

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
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

}
