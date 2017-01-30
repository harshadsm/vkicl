package vkicl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.google.gson.Gson;

import vkicl.daoImpl.PortDaoImpl;
import vkicl.daoImpl.PortPurchaseOrderDaoImpl;
import vkicl.daoImpl.WarehouseDaoImpl;
import vkicl.form.PortPurchaseOrderForm;
import vkicl.form.WarehouseDispatchForm;
import vkicl.services.PortOutwardService;
import vkicl.services.PortPurchaseOrderService;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.PortPurchaseOrderPostDataContainerVO;
import vkicl.vo.UserInfoVO;

public class PortPurchaseOrderAction1 extends BaseAction {
	private static Logger log = Logger.getLogger(PortPurchaseOrderAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionForward actionForward = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request, Constants.Apps.PORT_ENTRY);
			if (null != actionForward)
				return actionForward;

			userInfoVO = getUserProfile(request);
			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			// Gson gson = new Gson();
			// String postDataContainerStr =
			// request.getParameter("itemsToSaveJson");

			// log.info(postDataContainerStr);
			// PortPurchaseOrderPostDataContainerVO postDataContainer =
			// gson.fromJson(postDataContainerStr,
			// PortPurchaseOrderPostDataContainerVO.class);

			// PortPurchaseOrderService portPurchaseOrderService = new
			// PortPurchaseOrderService();
			// portPurchaseOrderService.processPortPurchaseOrderEntries(postDataContainer,
			// request, userInfoVO);
			// request.setAttribute("is_success", "success");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("is_success", "failed");
		}
		return actionForward;
	}

}
