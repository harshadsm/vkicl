package vkicl.report.bean;

@SuppressWarnings("serial")
public class WarehouseLocationBean extends BaseReportBean {

	private int id = 0;

	private String millName = null;
	private String make = null;
	private String grade = null;
	private int length = 0;
	private int width = 0;
	private Double thickness = null;

	private String location = "";
	private int availableQty = 0;
	private int qty = 0;

	private String heatNo = null;
	private String plateNo = null;

	private Double secWt = null;
	private String secWtUnit = null;

	private boolean processed = false;

	private int stockId = 0;
	
	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Double getThickness() {
		return thickness;
	}

	public void setThickness(Double thickness) {
		this.thickness = thickness;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(int availableQty) {
		this.availableQty = availableQty;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
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

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	@Override
	public String toString() {
		return "WarehouseLocationBean [id=" + id + ", millName=" + millName
				+ ", make=" + make + ", grade=" + grade + ", length=" + length
				+ ", width=" + width + ", thickness=" + thickness
				+ ", location=" + location + ", availableQty=" + availableQty
				+ ", qty=" + qty + ", heatNo=" + heatNo + ", plateNo="
				+ plateNo + ", secWt=" + secWt + ", secWtUnit=" + secWtUnit
				+ ", processed=" + processed + "]";
	}

}
