package vkicl.form;

import java.util.Arrays;

@SuppressWarnings("serial")
public class WarehouseOutwardFinalForm extends BaseForm {

	private int dispatchNo = 0;
	private int outwardTempId = 0;
	private int stockId = 0;

	private String location = null;
	private String heatNo = null;
	private String plateNo = null;

	private String millName = null;
	private String make = null;
	private String grade = null;
	private Integer length = null;
	private Integer width = null;
	private Double thickness = null;
	private Double secWt = null;
	private String secWtUnit = null;
	private Integer qty = null;

	private Integer exisLength = null;
	private Integer exisWidth = null;
	private Double exisThickness = null;

	private Integer[] newLength = null;
	private Integer[] newWidth = null;
	private Double[] newThickness = null;

	public WarehouseOutwardFinalForm() {
	}

	public WarehouseOutwardFinalForm(String location, String heatNo,
			String plateNo, String millName, String make, String grade,
			String length, String width, String thickness) {
		super();
		this.location = location;
		this.heatNo = heatNo;
		this.plateNo = plateNo;
		this.millName = millName;
		this.make = make;
		this.grade = grade;
		this.length = Integer.valueOf(length);
		this.width = Integer.valueOf(width);
		this.thickness = Double.valueOf(thickness);
	}

	public int getDispatchNo() {
		return dispatchNo;
	}

	public void setDispatchNo(int dispatchNo) {
		this.dispatchNo = dispatchNo;
	}

	public int getOutwardTempId() {
		return outwardTempId;
	}

	public void setOutwardTempId(int outwardTempId) {
		this.outwardTempId = outwardTempId;
	}

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getExisLength() {
		return exisLength;
	}

	public void setExisLength(Integer exisLength) {
		this.exisLength = exisLength;
	}

	public Integer getExisWidth() {
		return exisWidth;
	}

	public void setExisWidth(Integer exisWidth) {
		this.exisWidth = exisWidth;
	}

	public Double getExisThickness() {
		return exisThickness;
	}

	public void setExisThickness(Double exisThickness) {
		this.exisThickness = exisThickness;
	}

	public Integer[] getNewLength() {
		return newLength;
	}

	public void setNewLength(Integer[] newLength) {
		this.newLength = newLength;
	}

	public Integer[] getNewWidth() {
		return newWidth;
	}

	public void setNewWidth(Integer[] newWidth) {
		this.newWidth = newWidth;
	}

	public Double[] getNewThickness() {
		return newThickness;
	}

	public void setNewThickness(Double[] newThickness) {
		this.newThickness = newThickness;
	}

	@Override
	public String toString() {
		return "WarehouseOutwardFinalForm [dispatchNo=" + dispatchNo
				+ ", outwardTempId=" + outwardTempId + ", stockId=" + stockId
				+ ", location=" + location + ", heatNo=" + heatNo
				+ ", plateNo=" + plateNo + ", millName=" + millName + ", make="
				+ make + ", grade=" + grade + ", length=" + length + ", width="
				+ width + ", thickness=" + thickness + ", secWt=" + secWt
				+ ", secWtUnit=" + secWtUnit + ", qty=" + qty + ", exisLength="
				+ exisLength + ", exisWidth=" + exisWidth + ", exisThickness="
				+ exisThickness + ", newLength=" + Arrays.toString(newLength)
				+ ", newWidth=" + Arrays.toString(newWidth) + ", newThickness="
				+ Arrays.toString(newThickness) + "]";
	}

}
