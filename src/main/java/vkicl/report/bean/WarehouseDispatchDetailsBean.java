package vkicl.report.bean;

@SuppressWarnings("serial")
public class WarehouseDispatchDetailsBean extends BaseReportBean {

	private String make = "";
	private String millName = "";
	private String grade = "";
	private Integer length = null;
	private Integer width = null;
	private Double thickness = null;
	private Integer orderedQuantity;
	private Integer deliveredQuantity;
	private Integer pendingQuantity;
	
	private Double actWt = null;
	private String actWtUnit = "";
	private String rate = "";
	private String rateUnit = "";
	private String taxes = "";
	private String excise = "";
	private Integer dispatchDetailsID = null;

	public Integer getDispatchDetailsID() {
		return dispatchDetailsID;
	}

	public void setDispatchDetailsID(Integer dispatchDetailsID) {
		this.dispatchDetailsID = dispatchDetailsID;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
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



	public Integer getOrderedQuantity() {
		return orderedQuantity;
	}

	public void setOrderedQuantity(Integer orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}

	public Integer getDeliveredQuantity() {
		return deliveredQuantity;
	}

	public void setDeliveredQuantity(Integer deliveredQuantity) {
		this.deliveredQuantity = deliveredQuantity;
	}

	public Integer getPendingQuantity() {
		return pendingQuantity;
	}

	public void setPendingQuantity(Integer pendingQuantity) {
		this.pendingQuantity = pendingQuantity;
	}

	public Double getActWt() {
		return actWt;
	}

	public void setActWt(Double actWt) {
		this.actWt = actWt;
	}

	public String getActWtUnit() {
		return actWtUnit;
	}

	public void setActWtUnit(String actWtUnit) {
		this.actWtUnit = actWtUnit;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getRateUnit() {
		return rateUnit;
	}

	public void setRateUnit(String rateUnit) {
		this.rateUnit = rateUnit;
	}

	public String getTaxes() {
		return taxes;
	}

	public void setTaxes(String taxes) {
		this.taxes = taxes;
	}

	public String getExcise() {
		return excise;
	}

	public void setExcise(String excise) {
		this.excise = excise;
	}

	@Override
	public String toString() {
		return "WarehouseDispatchDetailsBean [make=" + make + ", millName=" + millName + ", grade=" + grade
				+ ", length=" + length + ", width=" + width + ", thickness=" + thickness + ", orderedQuantity="
				+ orderedQuantity + ", deliveredQuantity=" + deliveredQuantity + ", pendingQuantity=" + pendingQuantity
				+ ", actWt=" + actWt + ", actWtUnit=" + actWtUnit + ", rate=" + rate + ", rateUnit=" + rateUnit
				+ ", taxes=" + taxes + ", excise=" + excise + ", dispatchDetailsID=" + dispatchDetailsID + "]";
	}



}
