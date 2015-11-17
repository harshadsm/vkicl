package vkicl.report.form;

import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import vkicl.form.BaseForm;
import vkicl.report.bean.WarehouseOutwardBean;

@SuppressWarnings("serial")
public class WarehouseOutwardReportForm extends BaseForm {

	private int dispatchNo = 0;

	private ArrayList<LabelValueBean> dispatchNoList = null;
	private ArrayList<WarehouseOutwardBean> reportList = null;

	public WarehouseOutwardReportForm() {
	}

	public void clear() {
		dispatchNo = 0;
		dispatchNoList = null;
		reportList = null;
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

	public ArrayList<WarehouseOutwardBean> getReportList() {
		return reportList;
	}

	public void setReportList(ArrayList<WarehouseOutwardBean> reportList) {
		this.reportList = reportList;
	}

	@Override
	public String toString() {
		return "WarehouseOutwardReportForm [dispatchNo=" + dispatchNo
				+ ", dispatchNoList=" + dispatchNoList + ", reportList="
				+ reportList + "]";
	}

}
