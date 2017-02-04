package vkicl.report.bean;

@SuppressWarnings("serial")
public class PortStockBean extends BaseReportBean {

	private int portInwardId = 0;
	private int portInwardDetailId = 0;

	private int portInwardShipmentId = 0;
	private String vendorName;
	private String vesselName;
	private String vesselDate;

	private String millName;
	private String materialType;
	private String make;
	private String grade;
	private String desc;

	private Integer length;
	private Integer width;
	private Double thickness;
	private Integer inwardQuantity;
	private Integer deliveredTalojaQty;
	private Integer ppoOrderedQty;
	private Integer ppoDeliveredQty;

	private Integer balanceQtyForSale;
	private Integer balanceAtDock;

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	public int getPortInwardId() {
		return portInwardId;
	}

	public void setPortInwardId(int portInwardId) {
		this.portInwardId = portInwardId;
	}

	public int getPortInwardDetailId() {
		return portInwardDetailId;
	}

	public void setPortInwardDetailId(int portInwardDetailId) {
		this.portInwardDetailId = portInwardDetailId;
	}

	public int getPortInwardShipmentId() {
		return portInwardShipmentId;
	}

	public void setPortInwardShipmentId(int portInwardShipmentId) {
		this.portInwardShipmentId = portInwardShipmentId;
	}

	public Integer getDeliveredTalojaQty() {
		return deliveredTalojaQty;
	}

	public void setDeliveredTalojaQty(Integer deliveredTalojaQty) {
		this.deliveredTalojaQty = deliveredTalojaQty;
	}

	public Integer getBalanceQtyForSale() {
		return balanceQtyForSale;
	}

	public void setBalanceQtyForSale(Integer balanceQtyForSale) {
		this.balanceQtyForSale = balanceQtyForSale;
	}

	public Integer getPpoOrderedQty() {
		return ppoOrderedQty;
	}

	public void setPpoOrderedQty(Integer ppoOrderedQty) {
		this.ppoOrderedQty = ppoOrderedQty;
	}

	public Integer getPpoDeliveredQty() {
		return ppoDeliveredQty;
	}

	public void setPpoDeliveredQty(Integer ppoDeliveredQty) {
		this.ppoDeliveredQty = ppoDeliveredQty;
	}

	public Integer getBalanceAtDock() {
		return balanceAtDock;
	}

	public void setBalanceAtDock(Integer balanceAtDock) {
		this.balanceAtDock = balanceAtDock;
	}

	public Integer getInwardQuantity() {
		return inwardQuantity;
	}

	public void setInwardQuantity(Integer inwardQuantity) {
		this.inwardQuantity = inwardQuantity;
	}



}
