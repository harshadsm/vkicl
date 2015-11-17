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
import vkicl.report.form.WarehouseDispatchDetailsReportForm;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class WarehouseDispatchDetailsReportAction extends BaseAction {
	private static Logger log = Logger
			.getLogger(WarehouseDispatchDetailsReportAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = null;
		WarehouseDispatchDetailsReportForm warehouseDispatchDetailsReportForm = null;
		@SuppressWarnings("unused")
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request,
					Constants.Apps.DISPATCH_ORDER);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);
			warehouseDispatchDetailsReportForm = (WarehouseDispatchDetailsReportForm) form;
			ReportDaoImpl impl = new ReportDaoImpl();

			String id = request.getParameter("id");
			if (StringUtils.isNotBlank(id)) {
				try {
					log.info("ID = " + id);
					warehouseDispatchDetailsReportForm.setId(Integer
							.parseInt(id));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			id = String.valueOf(warehouseDispatchDetailsReportForm.getId());
			// ArrayList<LabelValueBean> receivedFromList = impl.getList(
			// userInfoVO, "query.unique.customerLocation");
			// ArrayList<LabelValueBean> materialTypeList = impl.getList(
			// userInfoVO, "query.unique.materialType");
			// ArrayList<LabelValueBean> vesselNameList =
			// impl.getList(userInfoVO,
			// "query.unique.vesselName");
			// ArrayList<LabelValueBean> vesselDateList = impl.getDateList(
			// userInfoVO, "query.unique.vesselDate");
			//
			// warehouseDispatchReportForm.setReceivedFromList(receivedFromList);
			// warehouseDispatchReportForm.setMaterialTypeList(materialTypeList);
			// warehouseDispatchReportForm.setVesselDateList(vesselDateList);

			genericListener = warehouseDispatchDetailsReportForm
					.getGenericListener();

			if (StringUtils.isNotBlank(id)) {
				warehouseDispatchDetailsReportForm = impl
						.fetchWarehouseDispatchDetailsReport(
								warehouseDispatchDetailsReportForm, userInfoVO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}

}
