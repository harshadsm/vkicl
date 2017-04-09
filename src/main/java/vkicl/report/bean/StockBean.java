package vkicl.report.bean;

import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class StockBean extends BaseReportBean {

	private int id = 0;

	private String heatNo = "";
	private String plateNo = "";
	private String millName = "";
	private String materialType = "";
	private String make = "";
	private String grade = "";
	private Integer length = 0;
	private Integer width = 0;
	private Double thickness = 0.000;
	private Integer qty = 0;
	private String location = "";

	private String vehicleDate = "";
	private Double secWt = 0.000;
	private String secWtUnit = "";
	private int materialId = 0;
	private String fileName = "";
	private Double fileSize = 0.00;
	private boolean reserved = false;
	private String customer = "";

	private Double actualWt = 0.000;
	
	private String invoiceNoOfLocalVendor;
	private String invoiceDateOfLocalVendor;
	
	
	public String getInvoiceNoOfLocalVendor() {
		return invoiceNoOfLocalVendor;
	}

	public void setInvoiceNoOfLocalVendor(String invoiceNoOfLocalVendor) {
		this.invoiceNoOfLocalVendor = invoiceNoOfLocalVendor;
	}

	public String getInvoiceDateOfLocalVendor() {
		return invoiceDateOfLocalVendor;
	}

	public void setInvoiceDateOfLocalVendor(String invoiceDateOfLocalVendor) {
		this.invoiceDateOfLocalVendor = invoiceDateOfLocalVendor;
	}

	public Double getActualWt() {
		return actualWt;
	}

	public void setActualWt(Double actualWt) {
		this.actualWt = actualWt;
	}

	public String getActualUnit() {
		return actualUnit;
	}

	public void setActualUnit(String actualUnit) {
		this.actualUnit = actualUnit;
	}

	private String actualUnit = "";
	
	public StockBean() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getVehicleDate() {
		return vehicleDate;
	}

	public void setVehicleDate(String vehicleDate) {
		this.vehicleDate = vehicleDate;
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

	public Double getFileSize() {
		return fileSize;
	}

	public void setFileSize(Double fileSize) {
		this.fileSize = fileSize;
	}

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "StockBean [id=" + id + ", heatNo=" + heatNo + ", plateNo="
				+ plateNo + ", millName=" + millName + ", materialType="
				+ materialType + ", make=" + make + ", grade=" + grade
				+ ", length=" + length + ", width=" + width + ", thickness="
				+ thickness + ", qty=" + qty + ", location=" + location
				+ ", vehicleDate=" + vehicleDate + ", secWt=" + secWt
				+ ", secWtUnit=" + secWtUnit + ", materialId=" + materialId
				+ ", fileName=" + fileName + ", fileSize=" + fileSize
				+ ", reserved=" + reserved + ", customer=" + customer + "]";
	}

}
