package vkicl.vo;

import java.util.Date;

public class DeliveryNoteLineItemVO {

	private Integer id;
	private Integer width;
	private Integer length;
	private Integer thickness;
	private Integer deliveredQuantity;
	private Integer orderedQuantity;
	private Double actualWeight;
	private String materialMake;
	private String materialGrade;
	private String materialType;
	private String millName;

	private Date date;

	
	public Integer getOrderedQuantity() {
		return orderedQuantity;
	}

	public void setOrderedQuantity(Integer orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getThickness() {
		return thickness;
	}

	public void setThickness(Integer thickness) {
		this.thickness = thickness;
	}

	public Integer getDeliveredQuantity() {
		return deliveredQuantity;
	}

	public void setDeliveredQuantity(Integer deliveredQuantity) {
		this.deliveredQuantity = deliveredQuantity;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getActualWeight() {
		return actualWeight;
	}

	public void setActualWeight(Double actualWeight) {
		this.actualWeight = actualWeight;
	}

	public String getMaterialMake() {
		return materialMake;
	}

	public void setMaterialMake(String materialMake) {
		this.materialMake = materialMake;
	}

	public String getMaterialGrade() {
		return materialGrade;
	}

	public void setMaterialGrade(String materialGrade) {
		this.materialGrade = materialGrade;
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
	
	

}
