package vkicl.report.bean;

@SuppressWarnings("serial")
public class PortInwardBean extends BaseReportBean {

	private int id = 0;
	private String vendorName = null;
	private String vesselName = null;
	private String vesselDate = null;
	private Integer pis = null;
	private Integer subPis = null;
	private String beNo = null;
	private String millName = null;
	private String materialType = null;
	private String make = null;
	private String grade = null;
	private String desc = null;
	private Double beWt = null;
	private String beWtUnit = null;
	private Integer length = null;
	private Integer width = null;
	private Double thickness = null;
	private Double actualWt = null;
	private String actualWtUnit = null;
	private Integer qty = null;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public Integer getPis() {
		return pis;
	}

	public void setPis(Integer pis) {
		this.pis = pis;
	}

	public Integer getSubPis() {
		return subPis;
	}

	public void setSubPis(Integer subPis) {
		this.subPis = subPis;
	}

	public String getBeNo() {
		return beNo;
	}

	public void setBeNo(String beNo) {
		this.beNo = beNo;
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

	public Double getBeWt() {
		return beWt;
	}

	public void setBeWt(Double beWt) {
		this.beWt = beWt;
	}

	public String getBeWtUnit() {
		return beWtUnit;
	}

	public void setBeWtUnit(String beWtUnit) {
		this.beWtUnit = beWtUnit;
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

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	@Override
	public String toString() {
		return "PortInwardBean [id=" + id + ", vendorName=" + vendorName
				+ ", vesselName=" + vesselName + ", vesselDate=" + vesselDate
				+ ", pis=" + pis + ", subPis=" + subPis + ", beNo=" + beNo
				+ ", millName=" + millName + ", materialType=" + materialType
				+ ", make=" + make + ", grade=" + grade + ", desc=" + desc
				+ ", beWt=" + beWt + ", beWtUnit=" + beWtUnit + ", length="
				+ length + ", width=" + width + ", thickness=" + thickness
				+ ", actualWt=" + actualWt + ", actualWtUnit=" + actualWtUnit
				+ ", qty=" + qty + "]";
	}

}
