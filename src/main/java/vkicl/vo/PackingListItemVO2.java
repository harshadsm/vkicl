package vkicl.vo;

public class PackingListItemVO2 {
	private Integer portInwardDetailId; 
	private Integer portInwardShipmentId;
	private Integer portInwardId;
	private Integer portOutwardId;
	
	private Integer length;
	private Integer width;
	private Double thickness;
	private Integer quantity;
	private Integer quantityAlreadyOut = 0;
	
	private String materialType;
	private String millName;
	private String make;
	private String grade;
	private String desc;
	private Double beWt;
	private String beWtUnit;
	private String vesselDate;
	private String vesselName;
	private String vendorName;
	private Double balQty;
	private String vehicleDate;
	private String vehicleName;
	private Double actualWt;
	private String actualWt_unit;
	private Double sectionWt;
	private String sectionWt_unit;
	
	public String getActualWt_unit() {
		return actualWt_unit;
	}
	public void setActualWt_unit(String actualWt_unit) {
		this.actualWt_unit = actualWt_unit;
	}
	
	
		public Double getActualWt() {
		return actualWt;
	}
	public void setActualWt(Double actualWt) {
		this.actualWt = actualWt;
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
		
		
	public Double getBalQty() {
		return balQty;
	}
	public void setBalQty(Double balQty) {
		this.balQty = balQty;
	}
	public Integer getPortInwardDetailId() {
		return portInwardDetailId;
	}
	public void setPortInwardDetailId(Integer portInwardDetailId) {
		this.portInwardDetailId = portInwardDetailId;
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
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Double getBeWt() {
		return beWt;
	}
	public void setBeWt(Double beWt) {
		this.beWt = beWt;
	}
	public String getBeWtUnit() {
		return beWtUnit;
	}
	public void setBeWtUnit(String beWtUnit) {
		this.beWtUnit = beWtUnit;
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
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public Integer getPortInwardShipmentId() {
		return portInwardShipmentId;
	}
	public void setPortInwardShipmentId(Integer portInwardShipmentId) {
		this.portInwardShipmentId = portInwardShipmentId;
	}
	public Integer getPortOutwardId() {
		return portOutwardId;
	}
	public void setPortOutwardId(Integer portOutwardId) {
		this.portOutwardId = portOutwardId;
	}
	public Integer getQuantityAlreadyOut() {
		return quantityAlreadyOut;
	}
	public void setQuantityAlreadyOut(Integer quantityAlreadyOut) {
		this.quantityAlreadyOut = quantityAlreadyOut;
	}
	public Double getSectionWt() {
		return sectionWt;
	}
	public void setSectionWt(Double sectionWt) {
		this.sectionWt = sectionWt;
	}
	public String getSectionWt_unit() {
		return sectionWt_unit;
	}
	public void setSectionWt_unit(String sectionWt_unit) {
		this.sectionWt_unit = sectionWt_unit;
	}
	

}
