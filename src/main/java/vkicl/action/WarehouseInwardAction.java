package vkicl.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vkicl.daoImpl.WarehouseDaoImpl;
import vkicl.form.WarehouseInwardForm;
import vkicl.services.LocationService;
import vkicl.services.WarehouseInwardService;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.LocationDetailsVO;
import vkicl.vo.UserInfoVO;

public class WarehouseInwardAction extends BaseAction {
	private static Logger log = Logger.getLogger(WarehouseInwardAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionForward actionForward = null;
		WarehouseInwardForm warehouseInwardForm = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request, Constants.Apps.WAREHOUSE_ENTRY);
			if (null != actionForward)
				return actionForward;

			LocationService locationService = new LocationService();
			List<LocationDetailsVO> list = locationService.getAllLocationsAsList();
			request.setAttribute("locationsList", list);
			
			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}

}
