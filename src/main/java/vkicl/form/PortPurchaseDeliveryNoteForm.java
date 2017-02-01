package vkicl.form;

import java.util.ArrayList;
import java.util.Arrays;
import vkicl.form.*;
import vkicl.report.bean.PortPurchaseOrderDeliveryNoteBean;
import vkicl.report.bean.WarehouseDispatchBean;

@SuppressWarnings("serial")
public class PortPurchaseDeliveryNoteForm extends BaseForm {

	private Integer ppoNo = null;

	public Integer getPpoNo() {
		return ppoNo;
	}

	public void setPpoNo(Integer ppoNo) {
		this.ppoNo = ppoNo;
	}

	ArrayList<PortPurchaseOrderDeliveryNoteBean> reportList = null;

	public ArrayList<PortPurchaseOrderDeliveryNoteBean> getReportList() {
		return reportList;
	}

	public void setReportList(ArrayList<PortPurchaseOrderDeliveryNoteBean> reportList) {
		this.reportList = reportList;
	}

	@Override
	public String toString() {
		return "PortPurchaseDeliveryNoteForm [ppoNo=" + ppoNo + ",reportList=" + reportList + "]";
	}

}