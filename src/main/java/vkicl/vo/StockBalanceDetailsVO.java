package vkicl.vo;

import java.awt.Shape;

public class StockBalanceDetailsVO {
	private Integer stockBalId; 
	
	private Integer length;
	private Integer width;
	private Double thickness;
    private Integer quantity;
	
	private String materialType;
	private String millName;
	private String make;
	private String grade;
	private Shape plateShape;
	
	private double plateArea;
	
	private Integer isRectangular;
	
	public double getPlateArea() {
		return plateArea;
	}
	public void setPlateArea(double plateArea) {
		this.plateArea = plateArea;
	}
	
	
	
	public Shape getPlateShape() {
		return plateShape;
	}
	public void setPlateShape(Shape plateShape) {
		this.plateShape = plateShape;
	}
	
	
	public Integer getStockBalId() {
		return stockBalId;
	}
	public void setStockBalId(Integer stockBalId) {
		this.stockBalId = stockBalId;
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
	public Integer getIsRectangular() {
		return isRectangular;
	}
	public void setIsRectangular(Integer isRectangular) {
		this.isRectangular = isRectangular;
	}
	
	
}