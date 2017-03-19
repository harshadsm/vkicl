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
	private String location;
	private String plate_no;
	private String heat_no;
	private Shape plateShape;
	private double plateArea;
	private Integer isRectangular;
	private Integer warehouseInwardId;

	public Integer getWarehouseInwardId() {
		return warehouseInwardId;
	}

	public void setWarehouseInwardId(Integer warehouseInwardId) {
		this.warehouseInwardId = warehouseInwardId;
	}

	public String getPlate_no() {
		return plate_no;
	}

	public void setPlate_no(String plate_no) {
		this.plate_no = plate_no;
	}

	public String getHeat_no() {
		return heat_no;
	}

	public void setHeat_no(String heat_no) {
		this.heat_no = heat_no;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

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
