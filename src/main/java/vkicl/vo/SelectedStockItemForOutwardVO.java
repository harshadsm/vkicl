package vkicl.vo;

public class SelectedStockItemForOutwardVO {

	private Integer stockId;
	private Integer stockQuantityForDelivery;
	private Integer thickness;
	private Integer length;
	private Integer widht;
	private String millName;
	private String make;
	private String grade;
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
	public Integer getWidht() {
		return widht;
	}
	public void setWidht(Integer widht) {
		this.widht = widht;
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
	@Override
	public String toString() {
		return "SelectedStockItemForOutwardVO [stockId=" + stockId + ", stockQuantityForDelivery="
				+ stockQuantityForDelivery + ", thickness=" + thickness + ", length=" + length + ", widht=" + widht
				+ ", millName=" + millName + ", make=" + make + ", grade=" + grade + "]";
	}
	
	
	
}
