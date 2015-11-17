package vkicl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vkicl.daoImpl.WarehouseDaoImpl;
import vkicl.form.WarehouseDispatchForm;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class WarehouseDispatchAction extends BaseAction {
	private static Logger log = Logger.getLogger(WarehouseDispatchAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = null;
		WarehouseDispatchForm warehouseDispatchForm = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request,
					Constants.Apps.DISPATCH_ORDER);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);
			warehouseDispatchForm = (WarehouseDispatchForm) form;
			genericListener = warehouseDispatchForm.getGenericListener();

			if (genericListener.equalsIgnoreCase("add")) {
				log.info("genericListener = " + genericListener);
				WarehouseDaoImpl impl = new WarehouseDaoImpl();
				warehouseDispatchForm = impl.addWarehouseDispatchData(warehouseDispatchForm, userInfoVO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}

}
