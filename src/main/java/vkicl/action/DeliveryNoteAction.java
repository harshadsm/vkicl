package vkicl.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import vkicl.daoImpl.PortDaoImpl;
import vkicl.daoImpl.PortPurchaseOrderDaoImpl;
import vkicl.daoImpl.WarehouseDaoImpl;
import vkicl.form.PortInwardForm;
import vkicl.form.PortPurchaseDeliveryNoteForm;
import vkicl.form.PortPurchaseOrderDeliveryForm;
import vkicl.form.WarehouseOutwardForm;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.PortInwardDetailsVO;
import vkicl.vo.PortPurchaseOrderLineItemVO;
import vkicl.vo.PortPurchaseOrderVO;
import vkicl.vo.StockBalanceDetailsVO;
import vkicl.vo.UserInfoVO;
import vkicl.vo.WarehouseOutwardVO;
import vkicl.vo.WarehouseOutwardVO2;

public class DeliveryNoteAction extends BaseAction {
	private static Logger log = Logger.getLogger(DeliveryNoteAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ActionForward actionForward = null;
		PortPurchaseDeliveryNoteForm portpurchasedeliverynoteForm = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		if (request.getMethod() == "GET") {
			try {
				actionForward = checkAccess(mapping, request, Constants.Apps.PORT_ENTRY);
				if (null != actionForward)
					return actionForward;

				actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
				userInfoVO = getUserProfile(request);
				portpurchasedeliverynoteForm = (PortPurchaseDeliveryNoteForm) form;
				Integer purchaseOrderNo = Integer.parseInt(request.getParameter("purchaseOrderNo"));

				if (purchaseOrderNo != null) {
					PortPurchaseOrderDaoImpl impl = new PortPurchaseOrderDaoImpl();
					PortPurchaseOrderVO portPurchaseOrderVO = impl.fetchPPODetails(purchaseOrderNo);
					request.setAttribute("port_purchase_order_details", portPurchaseOrderVO);

					portpurchasedeliverynoteForm = impl.fetchPPOLineItems(portpurchasedeliverynoteForm, purchaseOrderNo,
							userInfoVO);
				}
				actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
				userInfoVO = getUserProfile(request);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			actionForward = processPPOList(mapping, form, request);
		}
		return actionForward;
	}

	private ActionForward processPPOList(ActionMapping mapping, ActionForm form, HttpServletRequest request) {
		ActionForward actionForward = null;
		PortPurchaseDeliveryNoteForm portPurchaseDeliveryForm = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request, Constants.Apps.PORT_ENTRY);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);
			portPurchaseDeliveryForm = (PortPurchaseDeliveryNoteForm) form;
			genericListener = portPurchaseDeliveryForm.getGenericListener();
			if (genericListener.equalsIgnoreCase("addDetails")) {
				PortPurchaseOrderDaoImpl impl = new PortPurchaseOrderDaoImpl();
				Long deliveryNoteId = impl.addPortPurchaseDeliveryData(portPurchaseDeliveryForm, userInfoVO);
			} else {
				log.info("Loaded Port Purchase Order Line Item Details");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}
}
