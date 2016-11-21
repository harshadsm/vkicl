package vkicl.form;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.struts.util.LabelValueBean;

@SuppressWarnings("serial")
public class WarehouseOutwardForm extends BaseForm {

	int dispatchNo = 0;
	ArrayList<LabelValueBean> dispatchNoList = null;
	private Double actWt = null;

	private String[] location = null;
	private String[] qtyAvailable = null;
	

	private String[] subQty = null;

	private Integer[] row = null;
	private String[] millName = null;
	private String[] make = null;
	private String[] grade = null;
	private Integer[] length = null;
	private Integer[] width = null;
	private Double[] thickness = null;
	private Double[] secWt = null;
	private String[] secWtUnit = null;
	private Integer[] qty = null;
	private Integer[] stockId = null;

	
	public WarehouseOutwardForm() {
	}

	public WarehouseOutwardForm(int dispatchNo) {
		this.dispatchNo = dispatchNo;
	}

	public void clear() {
		dispatchNo = 0;
		dispatchNoList = null;
		actWt = null;

		location = null;
		qtyAvailable = null;
		subQty = null;

		row = null;
		millName = null;
		make = null;
		grade = null;
		length = null;
		width = null;
		thickness = null;
		secWt = null;
		secWtUnit = null;
		qty = null;
		subQty = null;
		stockId=null;
		
		super.clear();
	}

	public int getDispatchNo() {
		return dispatchNo;
	}

	public void setDispatchNo(int dispatchNo) {
		this.dispatchNo = dispatchNo;
	}

	public ArrayList<LabelValueBean> getDispatchNoList() {
		return dispatchNoList;
	}

	public void setDispatchNoList(ArrayList<LabelValueBean> dispatchNoList) {
		this.dispatchNoList = dispatchNoList;
	}

	public Double getActWt() {
		return actWt;
	}

	public void setActWt(Double actWt) {
		this.actWt = actWt;
	}
	public String[] getQtyAvailable() {
		return qtyAvailable;
	}

	public void setQtyAvailable(String[] qtyAvailable) {
		this.qtyAvailable = qtyAvailable;
	}

	public String[] getLocation() {
		return location;
	}

	public void setLocation(String[] location) {
		this.location = location;
	}

	

	public String[] getSubQty() {
		return subQty;
	}

	public void setSubQty(String[] subQty) {
		this.subQty = subQty;
	}

	public Integer[] getRow() {
		return row;
	}

	public void setRow(Integer[] row) {
		this.row = row;
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

	public Integer[] getStockId() {
		return stockId;
	}

	public void setStockId(Integer[] stockId) {
		this.stockId = stockId;
	}

	
	@Override
	public String toString() {
		return "WarehouseOutwardForm [dispatchNo=" + dispatchNo
				+ ", dispatchNoList=" + dispatchNoList + ", actWt=" + actWt
				+ ", location=" + Arrays.toString(location) + ", qtyAvailable="
				+ Arrays.toString(qtyAvailable) + ", subQty="
				+ Arrays.toString(subQty) + ", row=" + Arrays.toString(row)
				+ ", millName=" + Arrays.toString(millName) + ", make="
				+ Arrays.toString(make) + ", grade=" + Arrays.toString(grade)
				+ ", length=" + Arrays.toString(length) + ", width="
				+ Arrays.toString(width) + ", thickness="
				+ Arrays.toString(thickness) + ", secWt="
				+ Arrays.toString(secWt) + ", secWtUnit="
				+ Arrays.toString(secWtUnit) + ", qty=" + Arrays.toString(qty)+",stockId="+Arrays.toString(stockId)
				+ "]";
	}

}
