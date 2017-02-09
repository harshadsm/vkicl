package vkicl.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vkicl.form.DeliveryNoteUpdateForm;
import vkicl.form.PortPurchaseDeliveryNoteForm;
import vkicl.services.DeliveryNoteService;
import vkicl.services.PortPurchaseOrderService;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.DeliveryNoteLineItemVO;
import vkicl.vo.DeliveryNoteVO;
import vkicl.vo.PortPurchaseOrderLineItemVO;
import vkicl.vo.PortPurchaseOrderVO;
import vkicl.vo.UserInfoVO;

public class DeliveryNoteViewAction extends BaseAction {
	private static Logger log = Logger.getLogger(DeliveryNoteViewAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ActionForward actionForward = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;

		try {
			if (request.getMethod() == "GET") {

				actionForward = checkAccess(mapping, request, Constants.Apps.PORT_ENTRY);
				if (null != actionForward)
					return actionForward;

				actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
				userInfoVO = getUserProfile(request);

				String deliveryNoteId = request.getParameter("deliveryNoteId");
				String ppoNo = request.getParameter("ppoNo");
				request.setAttribute("deliveryNoteId", deliveryNoteId);

				if (deliveryNoteId != null) {

					DeliveryNoteService deliveryNoteService = new DeliveryNoteService();
					DeliveryNoteVO deliveryNoteVO = deliveryNoteService
							.getDeliveryNoteDetailsById(Integer.parseInt(deliveryNoteId));
					request.setAttribute("delivery_note_details", deliveryNoteVO);

					List<DeliveryNoteLineItemVO> deliveryNotesLineItems = deliveryNoteService
							.getDeliveryNoteLineItems(Integer.parseInt(deliveryNoteId), Integer.parseInt(ppoNo));
					request.setAttribute("delivery_note_line_items", deliveryNotesLineItems);

				}
			} else {
				actionForward = updateDeliveryNote(mapping, form, request);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}

	private ActionForward updateDeliveryNote(ActionMapping mapping, ActionForm form, HttpServletRequest request) {
		ActionForward actionForward = null;
		DeliveryNoteUpdateForm deliverynoteupdateform = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request, Constants.Apps.PORT_ENTRY);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);
			deliverynoteupdateform = (DeliveryNoteUpdateForm) form;
			genericListener = deliverynoteupdateform.getGenericListener();

			if (genericListener.equalsIgnoreCase("addDetails")) {

				DeliveryNoteService deliveryNoteService = new DeliveryNoteService();

				deliveryNoteService.updateDeliveryNote(deliverynoteupdateform, userInfoVO);

				List<DeliveryNoteLineItemVO> list = deliveryNoteService.toList(deliverynoteupdateform, userInfoVO);

				for (DeliveryNoteLineItemVO vo : list) {
					deliveryNoteService.updateDeliveryNoteLineItems(vo, deliverynoteupdateform, userInfoVO);
				}
				actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
				userInfoVO = getUserProfile(request);
			} else {
				log.info("Loaded Port Purchase Order Line Item Details");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;

	}
}
