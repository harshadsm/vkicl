package vkicl.vo;

public class SelectedStockItemForOutwardVO {

	private Integer stockId;
	private Integer stockQuantityForDelivery;
	private Integer quantity;
	private Integer thickness;
	private Integer length;
	private Integer width;
	private String millName;
	private String make;
	private String grade;
	private String heatNo;
	private String plateNo;
	private String location;
	
	public Integer getStockId() {
		return stockId;
	}
	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}
	public Integer getStockQuantityForDelivery() {
		return stockQuantityForDelivery;
	}
	public void setStockQuantityForDelivery(Integer stockQuantityForDelivery) {
		this.stockQuantityForDelivery = stockQuantityForDelivery;
	}
	public Integer getThickness() {
		return thickness;
	}
	public void setThickness(Integer thickness) {
		this.thickness = thickness;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "SelectedStockItemForOutwardVO [stockId=" + stockId + ", stockQuantityForDelivery="
				+ stockQuantityForDelivery + ", thickness=" + thickness + ", length=" + length + ", widht=" + width
				+ ", millName=" + millName + ", make=" + make + ", grade=" + grade + ", heatNo=" + heatNo + ", plateNo="
				+ plateNo + "]";
	}

	
	
}
