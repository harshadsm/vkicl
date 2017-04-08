package vkicl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vkicl.daoImpl.WarehouseDaoImpl;
import vkicl.form.WarehouseInwardForm;
import vkicl.services.WarehouseInwardService;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class WarehouseInwardAction4 extends BaseAction {
	private static Logger log = Logger.getLogger(WarehouseInwardAction4.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = null;
		WarehouseInwardForm warehouseInwardForm = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request,
					Constants.Apps.WAREHOUSE_ENTRY);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);
			warehouseInwardForm = (WarehouseInwardForm) form;
			genericListener = warehouseInwardForm.getGenericListener();

			if (genericListener.equalsIgnoreCase("add")) {
				log.info("genericListener = " + genericListener);
//				WarehouseDaoImpl impl = new WarehouseDaoImpl();
//				warehouseInwardForm = impl.addWarehouseInwardData(warehouseInwardForm, userInfoVO);
				
				WarehouseInwardService service = new WarehouseInwardService();
				service.processWarehouseInwardEntries_fromLocal(warehouseInwardForm, userInfoVO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}

}
