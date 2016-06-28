package vkicl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vkicl.daoImpl.WarehouseDaoImpl;
import vkicl.daoImpl.WarehouseDaoImpl2;
import vkicl.form.WarehouseInwardForm;
import vkicl.form.PortOutwardForm;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class WarehouseInwardAction3 extends BaseAction {
	private static Logger log = Logger.getLogger(WarehouseInwardAction3.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = null;
		PortOutwardForm warehouseInwardForm = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request,
					Constants.Apps.WAREHOUSE_ENTRY);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);
			warehouseInwardForm = (PortOutwardForm) form;
			genericListener = warehouseInwardForm.getGenericListener();

			if (genericListener.equalsIgnoreCase("add")) {
				log.info("genericListener = " + genericListener);
				WarehouseDaoImpl2 impl = new WarehouseDaoImpl2();
				warehouseInwardForm = impl.addWarehouseInwardData(warehouseInwardForm, userInfoVO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}

}
