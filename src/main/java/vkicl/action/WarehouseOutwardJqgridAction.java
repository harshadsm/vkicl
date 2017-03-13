package vkicl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vkicl.form.WarehouseOutwardForm;
import vkicl.util.Constants;

public class WarehouseOutwardJqgridAction extends BaseAction{

	private Logger log = LoggerFactory.getLogger(WarehouseOutwardJqgridAction.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
		
		
		WarehouseOutwardForm f = (WarehouseOutwardForm)form;
		log.debug("DispatchNo="+f.getDispatchNo());
		request.setAttribute("dispatchNo", f.getDispatchNo());
		return actionForward;
	}
}
