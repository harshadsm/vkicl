package vkicl.report.bean;

@SuppressWarnings("serial")
public class PortOutwardBean extends BaseReportBean {

	private int id = 0;
	private String dispatchedTo = null;
	private String vehicleNumber = null;
	private String vehicleDate = null;
	private String vesselName = null;
	private String vesselDate = null;
	private String beNo = null;
	private String materialType = null;
	private String millName = null;
	private String make = null;
	private String grade = null;
	private Integer length = null;
	private Integer width = null;
	private Double thickness = null;
	private Integer qty = null;
	private Double secWt = null;
	private String secWtUnit = null;
	private Double actualWt = null;
	private String actualWtUnit = null;
	private String invoice = null;
	private String destination = null;
	private String toWarehouseOrCustomerHiddenField = null;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDispatchedTo() {
		return dispatchedTo;
	}

	public void setDispatchedTo(String dispatchedTo) {
		this.dispatchedTo = dispatchedTo;
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

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	

	public String getToWarehouseOrCustomerHiddenField() {
		return toWarehouseOrCustomerHiddenField;
	}

	public void setToWarehouseOrCustomerHiddenField(String toWarehouseOrCustomerHiddenField) {
		this.toWarehouseOrCustomerHiddenField = toWarehouseOrCustomerHiddenField;
	}

	@Override
	public String toString() {
		return "PortOutwardBean [id=" + id + ", dispatchedTo=" + dispatchedTo + ", vehicleNumber=" + vehicleNumber
				+ ", vehicleDate=" + vehicleDate + ", vesselName=" + vesselName + ", vesselDate=" + vesselDate
				+ ", beNo=" + beNo + ", materialType=" + materialType + ", millName=" + millName + ", make=" + make
				+ ", grade=" + grade + ", length=" + length + ", width=" + width + ", thickness=" + thickness + ", qty="
				+ qty + ", secWt=" + secWt + ", secWtUnit=" + secWtUnit + ", actualWt=" + actualWt + ", actualWtUnit="
				+ actualWtUnit + ", invoice=" + invoice + ", destination=" + destination + "]";
	}

}
