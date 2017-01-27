package vkicl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.google.gson.Gson;

import vkicl.daoImpl.WarehouseDaoImpl2;
import vkicl.form.PortOutwardForm;
import vkicl.form.PortPurchaseOrderForm;
import vkicl.services.PortPurchaseOrderJsonDetailService;
import vkicl.services.WarehouseInwardService;
import vkicl.util.Constants;
import vkicl.util.Converter;
import vkicl.util.PropFileReader;
import vkicl.vo.PortOutwardPostDataContainerVO;
import vkicl.vo.PortPurchaseOrderPostDataContainerVO;
import vkicl.vo.UserInfoVO;

public class PortPurchaseOrderAction1 extends BaseAction {
	private static Logger log = Logger.getLogger(PortPurchaseOrderAction1.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionForward actionForward = null;
		PortPurchaseOrderForm portpurchaseForm = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request, Constants.Apps.PORT_ENTRY);
			if (null != actionForward)
				return actionForward;

			userInfoVO = getUserProfile(request);
			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			Gson gson = new Gson();
			String postDataContainerStr = request.getParameter("itemsToSaveJson");
			log.info(postDataContainerStr);
			// portpurchaseForm = (PortPurchaseOrderForm) form;

			PortPurchaseOrderPostDataContainerVO postDataContainer = gson.fromJson(postDataContainerStr,
					PortPurchaseOrderPostDataContainerVO.class);
			PortPurchaseOrderJsonDetailService portpurchaseService = new PortPurchaseOrderJsonDetailService();
			portpurchaseService.processPurchaseOrderEntries(postDataContainer, request, userInfoVO);
			request.setAttribute("is_success", "success");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("is_success", "failed");
		}
		return actionForward;
	}
}
