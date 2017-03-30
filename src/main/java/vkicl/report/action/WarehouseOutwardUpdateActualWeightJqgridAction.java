package vkicl.report.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import vkicl.action.BaseAction;
import vkicl.daoImpl.ReportDaoImpl;
import vkicl.report.form.WarehouseOutwardReportForm;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class WarehouseOutwardUpdateActualWeightJqgridAction extends BaseAction {
	private static Logger log = Logger
			.getLogger(WarehouseOutwardUpdateActualWeightJqgridAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = null;
		WarehouseOutwardReportForm warehouseOutwardReportForm = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request,
					Constants.Apps.PORT_REPORT);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);
//			warehouseOutwardReportForm = (WarehouseOutwardReportForm) form;
//			ReportDaoImpl impl = new ReportDaoImpl();
//
//			genericListener = warehouseOutwardReportForm.getGenericListener();
//			log.info("Loaded Warehouse Outward Report");
//			if (genericListener.equalsIgnoreCase("getReport")
//					&& warehouseOutwardReportForm.getDispatchNo() != 0) {
//				warehouseOutwardReportForm = impl.fetchWarehouseOutwardReport(
//						warehouseOutwardReportForm, userInfoVO);
//				log.info("Fetched Warehouse Outward Report Data");
//			} else {
//				warehouseOutwardReportForm.clear();
//			}
//
//			ArrayList<LabelValueBean> dispatchNoList = impl.getLVList(
//					userInfoVO, "query.unique.outwardDispatch");
//			dispatchNoList = updateDispatchNoList(dispatchNoList);
//			warehouseOutwardReportForm.setDispatchNoList(dispatchNoList);
//
//			if (null != warehouseOutwardReportForm.getReportList())
//				request.setAttribute("reportListSize",
//						warehouseOutwardReportForm.getReportList().size());
//			else
//				request.setAttribute("reportListSize", 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}

	public ArrayList<LabelValueBean> updateDispatchNoList(
			ArrayList<LabelValueBean> dispatchNoList) {
		for (LabelValueBean bean : dispatchNoList) {
			if (!"--".equalsIgnoreCase(bean.getValue()))
				bean.setLabel(bean.getLabel() + " (ID = " + bean.getValue()
						+ ")");
		}
		return dispatchNoList;
	}
}
