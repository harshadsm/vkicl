package vkicl.form;

import java.util.ArrayList;

import vkicl.form.BaseForm;
import vkicl.report.bean.PortPurchaseOrderDeliveryBean;
import vkicl.report.bean.WarehouseDispatchBean;

@SuppressWarnings("serial")
public class PortPurchaseOrderDeliveryForm extends BaseForm {

	private String fromDate = null;
	private String toDate = null;
	private String status = null;
	ArrayList<PortPurchaseOrderDeliveryBean> reportList = null;

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<PortPurchaseOrderDeliveryBean> getReportList() {
		return reportList;
	}

	public void setReportList(ArrayList<PortPurchaseOrderDeliveryBean> reportList) {
		this.reportList = reportList;
	}

	@Override
	public String toString() {
		return "PortPurchaseOrderDeliveryForm [fromDate=" + fromDate + ", toDate=" + toDate + ", status=" + status
				+ ", reportList=" + reportList + "]";
	}

}