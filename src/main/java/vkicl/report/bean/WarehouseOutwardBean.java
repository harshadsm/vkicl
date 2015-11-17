package vkicl.report.bean;

@SuppressWarnings("serial")
public class WarehouseOutwardBean extends BaseReportBean {

	private int id = 0;
	private String outwardDate = null;
	private String millName = null;
	// private String make = null;
	private String grade = null;
	private String heatNo = null;
	private String plateNo = null;
	private Integer length = null;
	private Integer width = null;
	private int qty = 0;
	private Double thickness = null;
	private Double secWt = null;
	private String secWtUnit = null;
	private Double secWtSum = null;
	private Double actualWt = null;
	private String actualWtUnit = null;
	private Double actualWtSum = null;
	private String vehicleNumber = null;
	private String buyerName = null;
	private String brokerName = null;
	private String handleBy = null;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOutwardDate() {
		return outwardDate;
	}

	public void setOutwardDate(String outwardDate) {
		this.outwardDate = outwardDate;
	}

	public String getMillName() {
		return millName;
	}

	public void setMillName(String millName) {
		this.millName = millName;
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

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
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

	public Double getSecWtSum() {
		return secWtSum;
	}

	public void setSecWtSum(Double secWtSum) {
		this.secWtSum = secWtSum;
	}

	public Double getActualWt() {
		return actualWt;
	}

	public void setActualWt(Double actualWt) {
		this.actualWt = actualWt;
	}

	public String getActualWtUnit() {
		return actualWtUnit;
	}

	public void setActualWtUnit(String actualWtUnit) {
		this.actualWtUnit = actualWtUnit;
	}

	public Double getActualWtSum() {
		return actualWtSum;
	}

	public void setActualWtSum(Double actualWtSum) {
		this.actualWtSum = actualWtSum;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBrokerName() {
		return brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	public String getHandleBy() {
		return handleBy;
	}

	public void setHandleBy(String handleBy) {
		this.handleBy = handleBy;
	}

	@Override
	public String toString() {
		return "WarehouseOutwardBean [id=" + id + ", outwardDate="
				+ outwardDate + ", millName=" + millName + ", grade=" + grade
				+ ", heatNo=" + heatNo + ", plateNo=" + plateNo + ", length="
				+ length + ", width=" + width + ", qty=" + qty + ", thickness="
				+ thickness + ", secWt=" + secWt + ", secWtUnit=" + secWtUnit
				+ ", secWtSum=" + secWtSum + ", actualWt=" + actualWt
				+ ", actualWtUnit=" + actualWtUnit + ", actualWtSum="
				+ actualWtSum + ", vehicleNumber=" + vehicleNumber
				+ ", buyerName=" + buyerName + ", brokerName=" + brokerName
				+ ", handleBy=" + handleBy + "]";
	}

}
