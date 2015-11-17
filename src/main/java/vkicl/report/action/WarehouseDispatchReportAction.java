package vkicl.report.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vkicl.action.BaseAction;
import vkicl.daoImpl.ReportDaoImpl;
import vkicl.report.form.WarehouseDispatchReportForm;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class WarehouseDispatchReportAction extends BaseAction {
	private static Logger log = Logger
			.getLogger(WarehouseDispatchReportAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = null;
		WarehouseDispatchReportForm warehouseDispatchReportForm = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request,
					Constants.Apps.DISPATCH_REPORT);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);
			warehouseDispatchReportForm = (WarehouseDispatchReportForm) form;
			ReportDaoImpl impl = new ReportDaoImpl();

			if (StringUtils.isBlank(warehouseDispatchReportForm
					.getGenericListener())) {
				warehouseDispatchReportForm.setFromDate("");
				warehouseDispatchReportForm.setToDate("");
				warehouseDispatchReportForm.setStatus("ALL");
			}

			warehouseDispatchReportForm = impl.fetchWarehouseDispatchReport(
					warehouseDispatchReportForm, userInfoVO);
			setUserProfile(request, userInfoVO);
			log.info("Loading Warehouse Dispatch Report");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}

}
