package vkicl.form;

import java.util.Arrays;

@SuppressWarnings("serial")
public class WarehouseInwardForm extends BaseForm {

	private String portVendor = "";
	private double actWt = 0;
	private String actWtUnit = "";

	private String portVehicleNumber = "";
	private String portVehicleDate = "";

	private String vendorName = "";
	private String vendorVehicleNumber = "";
	private String vendorVehicleDate = "";

	private String invoiceNo = "";

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	private String invoiceDate = "";

	private Integer[] row = null;
	private String[] beNo = null;
	private String[] materialType = null;
	private String[] millName = null;
	private String[] make = null;
	private String[] grade = null;
	private Integer[] length = null;
	private Integer[] width = null;
	private Double[] thickness = null;
	private Double[] labelWt = null;
	private String[] labelWtUnit = null;
	private Double[] secWt = null;
	private String[] secWtUnit = null;
	private Integer[] qty = null;
	private Integer[] subRow = null;
	private String[] heatNo = null;
	private String[] plateNo = null;
	private Double[] subSecWt = null;
	private String[] subSecWtUnit = null;
	private Double[] subWt = null;
	private String[] subWtUnit = null;
	private Integer[] subQty = null;
	private String[] wlocation = null;
	private String[] remark = null;

	public WarehouseInwardForm() {
	}

	public WarehouseInwardForm(String portVehicleNumber, String portVehicleDate) {
		super();
		this.portVehicleNumber = portVehicleNumber;
		this.portVehicleDate = portVehicleDate;
	}

	public void clear() {
		portVendor = "";
		actWt = 0;
		actWtUnit = "";

		portVehicleNumber = "";
		portVehicleDate = "";

		vendorName = "";
		vendorVehicleNumber = "";
		vendorVehicleDate = "";

		row = null;
		beNo = null;
		materialType = null;
		millName = null;
		make = null;
		grade = null;
		length = null;
		width = null;
		thickness = null;
		labelWt = null;
		labelWtUnit = null;
		secWt = null;
		secWtUnit = null;
		qty = null;
		subRow = null;
		heatNo = null;
		plateNo = null;
		subSecWt = null;
		subSecWtUnit = null;
		subWt = null;
		subWtUnit = null;
		subQty = null;
		wlocation = null;
		remark = null;

		super.clear();
	}

	public String getPortVendor() {
		return portVendor;
	}

	public void setPortVendor(String portVendor) {
		this.portVendor = portVendor;
	}

	public double getActWt() {
		return actWt;
	}

	public void setActWt(double actWt) {
		this.actWt = actWt;
	}

	public String getActWtUnit() {
		return actWtUnit;
	}

	public void setActWtUnit(String actWtUnit) {
		this.actWtUnit = actWtUnit;
	}

	public String getPortVehicleNumber() {
		return portVehicleNumber;
	}

	public void setPortVehicleNumber(String portVehicleNumber) {
		this.portVehicleNumber = portVehicleNumber;
	}

	public String getPortVehicleDate() {
		return portVehicleDate;
	}

	public void setPortVehicleDate(String portVehicleDate) {
		this.portVehicleDate = portVehicleDate;
	}

	public String getVendorVehicleNumber() {
		return vendorVehicleNumber;
	}

	public void setVendorVehicleNumber(String vendorVehicleNumber) {
		this.vendorVehicleNumber = vendorVehicleNumber;
	}

	public String getVendorVehicleDate() {
		return vendorVehicleDate;
	}

	public void setVendorVehicleDate(String vendorVehicleDate) {
		this.vendorVehicleDate = vendorVehicleDate;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Integer[] getRow() {
		return row;
	}

	public void setRow(Integer[] row) {
		this.row = row;
	}

	public String[] getBeNo() {
		return beNo;
	}

	public void setBeNo(String[] beNo) {
		this.beNo = beNo;
	}

	public String[] getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String[] materialType) {
		this.materialType = materialType;
	}

	public String[] getMillName() {
		return millName;
	}

	public void setMillName(String[] millName) {
		this.millName = millName;
	}

	public String[] getMake() {
		return make;
	}

	public void setMake(String[] make) {
		this.make = make;
	}

	public String[] getGrade() {
		return grade;
	}

	public void setGrade(String[] grade) {
		this.grade = grade;
	}

	public Integer[] getLength() {
		return length;
	}

	public void setLength(Integer[] length) {
		this.length = length;
	}

	public Integer[] getWidth() {
		return width;
	}

	public void setWidth(Integer[] width) {
		this.width = width;
	}

	public Double[] getThickness() {
		return thickness;
	}

	public void setThickness(Double[] thickness) {
		this.thickness = thickness;
	}

	public Double[] getLabelWt() {
		return labelWt;
	}

	public void setLabelWt(Double[] labelWt) {
		this.labelWt = labelWt;
	}

	public String[] getLabelWtUnit() {
		return labelWtUnit;
	}

	public void setLabelWtUnit(String[] labelWtUnit) {
		this.labelWtUnit = labelWtUnit;
	}

	public Double[] getSecWt() {
		return secWt;
	}

	public void setSecWt(Double[] secWt) {
		this.secWt = secWt;
	}

	public String[] getSecWtUnit() {
		return secWtUnit;
	}

	public void setSecWtUnit(String[] secWtUnit) {
		this.secWtUnit = secWtUnit;
	}

	public Integer[] getQty() {
		return qty;
	}

	public void setQty(Integer[] qty) {
		this.qty = qty;
	}

	public Integer[] getSubRow() {
		return subRow;
	}

	public void setSubRow(Integer[] subRow) {
		this.subRow = subRow;
	}

	public String[] getHeatNo() {
		return heatNo;
	}

	public void setHeatNo(String[] heatNo) {
		this.heatNo = heatNo;
	}

	public String[] getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String[] plateNo) {
		this.plateNo = plateNo;
	}

	public Double[] getSubSecWt() {
		return subSecWt;
	}

	public void setSubSecWt(Double[] subSecWt) {
		this.subSecWt = subSecWt;
	}

	public String[] getSubSecWtUnit() {
		return subSecWtUnit;
	}

	public void setSubSecWtUnit(String[] subSecWtUnit) {
		this.subSecWtUnit = subSecWtUnit;
	}

	public Double[] getSubWt() {
		return subWt;
	}

	public void setSubWt(Double[] subWt) {
		this.subWt = subWt;
	}

	public String[] getSubWtUnit() {
		return subWtUnit;
	}

	public void setSubWtUnit(String[] subWtUnit) {
		this.subWtUnit = subWtUnit;
	}

	public Integer[] getSubQty() {
		return subQty;
	}

	public void setSubQty(Integer[] subQty) {
		this.subQty = subQty;
	}

	public String[] getWlocation() {
		return wlocation;
	}

	public void setWlocation(String[] wlocation) {
		this.wlocation = wlocation;
	}

	public String[] getRemark() {
		return remark;
	}

	public void setRemark(String[] remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "WarehouseInwardForm [portVendor=" + portVendor + ", actWt=" + actWt + ", actWtUnit=" + actWtUnit
				+ ", portVehicleNumber=" + portVehicleNumber + ", portVehicleDate=" + portVehicleDate + ", vendorName="
				+ vendorName + ", vendorVehicleNumber=" + vendorVehicleNumber + ", vendorVehicleDate="
				+ vendorVehicleDate + ", row=" + Arrays.toString(row) + ", beNo=" + Arrays.toString(beNo)
				+ ", materialType=" + Arrays.toString(materialType) + ", millName=" + Arrays.toString(millName)
				+ ", make=" + Arrays.toString(make) + ", grade=" + Arrays.toString(grade) + ", length="
				+ Arrays.toString(length) + ", width=" + Arrays.toString(width) + ", thickness="
				+ Arrays.toString(thickness) + ", labelWt=" + Arrays.toString(labelWt) + ", labelWtUnit="
				+ Arrays.toString(labelWtUnit) + ", secWt=" + Arrays.toString(secWt) + ", secWtUnit="
				+ Arrays.toString(secWtUnit) + ", qty=" + Arrays.toString(qty) + ", subRow=" + Arrays.toString(subRow)
				+ ", heatNo=" + Arrays.toString(heatNo) + ", plateNo=" + Arrays.toString(plateNo) + ", subSecWt="
				+ Arrays.toString(subSecWt) + ", subSecWtUnit=" + Arrays.toString(subSecWtUnit) + ", subWt="
				+ Arrays.toString(subWt) + ", subWtUnit=" + Arrays.toString(subWtUnit) + ", subQty="
				+ Arrays.toString(subQty) + ", wlocation=" + Arrays.toString(wlocation) + ", remark="
				+ Arrays.toString(remark) + "]";
	}

}
