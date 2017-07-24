package vkicl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vkicl.util.Constants;
import vkicl.util.PropFileReader;

public class FetchPortOutwardShipmentDetailsHtmlAction extends BaseAction{
	private static Logger log = Logger.getLogger(DeliveryNoteViewAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		ActionForward actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
		
		return actionForward;
	}
}
