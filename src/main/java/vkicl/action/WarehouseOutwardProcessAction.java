package vkicl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vkicl.daoImpl.WarehouseDaoImpl;
import vkicl.form.WarehouseOutwardProcessForm;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class WarehouseOutwardProcessAction extends BaseAction {
	private static Logger log = Logger
			.getLogger(WarehouseOutwardProcessAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = null;
		WarehouseOutwardProcessForm warehouseOutwardProcessForm = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request,
					Constants.Apps.WAREHOUSE_ENTRY);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);
			warehouseOutwardProcessForm = (WarehouseOutwardProcessForm) form;
			WarehouseDaoImpl impl = new WarehouseDaoImpl();
			genericListener = warehouseOutwardProcessForm.getGenericListener();

			int dispatchNo = 0;
			if (null != (request.getParameter("dispatchNo"))) {
				dispatchNo = Integer.parseInt(request
						.getParameter("dispatchNo"));
				warehouseOutwardProcessForm.setDispatchNo(dispatchNo);
				log.info("dispatchNo = " + dispatchNo);
			}

			if (warehouseOutwardProcessForm.getDispatchNo() == 0) {
				log.info("warehouseOutwardProcessForm.getDispatchNo() = "
						+ warehouseOutwardProcessForm.getDispatchNo());
				actionForward = mapping
						.findForward(Constants.Mapping.DISPATCH_REPORT_PAGE);
			}

			if (genericListener.equalsIgnoreCase("add")) {
				log.info("genericListener = " + genericListener);
				userInfoVO = impl.addWarehouseOutwardProcessData(
						warehouseOutwardProcessForm, userInfoVO);
				warehouseOutwardProcessForm.clear();
				actionForward = mapping
						.findForward(Constants.Mapping.DISPATCH_REPORT_PAGE);
			} else {
				warehouseOutwardProcessForm = impl
						.fetchWarehouseOutwardProcessData(
								warehouseOutwardProcessForm, userInfoVO);
			}
			setUserProfile(request, userInfoVO);

		} catch (Exception e) {
			String message = e.getMessage();
			userInfoVO.setMessage(message);
			log.error(e);
		}
		return actionForward;
	}
}
