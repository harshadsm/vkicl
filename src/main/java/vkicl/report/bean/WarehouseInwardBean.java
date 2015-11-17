package vkicl.report.bean;

@SuppressWarnings("serial")
public class WarehouseInwardBean extends BaseReportBean {

	private int id = 0;
	private String receivedFrom = null;
	private String vehicleNumber = null;
	private String vehicleDate = null;
	private String vendorName = null;
	private String vesselName = null;
	private String vesselDate = null;
	private String beNo = null;
	private String materialType = null;
	private String millName = null;
	private String make = null;
	private String grade = null;
	private String heatNo = null;
	private String plateNo = null;
	private Integer length = null;
	private Integer width = null;
	private Double thickness = null;
	private Double secWt = null;
	private String secWtUnit = null;
	private Double actualWt = null;
	private String actualWtUnit = null;
	private Integer qty = null;
	private String wlocation = null;

	private int materialId = 0;
	private String fileName = "";
	private double fileSize = 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReceivedFrom() {
		return receivedFrom;
	}

	public void setReceivedFrom(String receivedFrom) {
		this.receivedFrom = receivedFrom;
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

	public String getBeNo() {
		return beNo;
	}

	public void setBeNo(String beNo) {
		this.beNo = beNo;
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

	public Double getSecWt() {
		return secWt;
	}

	public void setSecWt(Double secWt) {
		this.secWt = secWt;
	}

	public String getSecWtUnit() {
		return secWtUnit;
	}

	public void setSecWtUnit(String secWtUnit) {
		this.secWtUnit = secWtUnit;
	}

	public Double getActualWt() {
		return actualWt;
	}

	public void setActualWt(Double actualWt) {
		this.actualWt = actualWt;
	}

	public String getActualWtUnit() {
		return actualWtUnit;
	}

	public void setActualWtUnit(String actualWtUnit) {
		this.actualWtUnit = actualWtUnit;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getWlocation() {
		return wlocation;
	}

	public void setWlocation(String wlocation) {
		this.wlocation = wlocation;
	}

	public int getMaterialId() {
		return materialId;
	}

	public void setMaterialId(int materialId) {
		this.materialId = materialId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public double getFileSize() {
		return fileSize;
	}

	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return "WarehouseInwardBean [id=" + id + ", receivedFrom="
				+ receivedFrom + ", vehicleNumber=" + vehicleNumber
				+ ", vehicleDate=" + vehicleDate + ", vendorName=" + vendorName
				+ ", vesselName=" + vesselName + ", vesselDate=" + vesselDate
				+ ", beNo=" + beNo + ", materialType=" + materialType
				+ ", millName=" + millName + ", make=" + make + ", grade="
				+ grade + ", heatNo=" + heatNo + ", plateNo=" + plateNo
				+ ", length=" + length + ", width=" + width + ", thickness="
				+ thickness + ", secWt=" + secWt + ", secWtUnit=" + secWtUnit
				+ ", actualWt=" + actualWt + ", actualWtUnit=" + actualWtUnit
				+ ", qty=" + qty + ", wlocation=" + wlocation + ", materialId="
				+ materialId + ", fileName=" + fileName + ", fileSize="
				+ fileSize + "]";
	}

}
