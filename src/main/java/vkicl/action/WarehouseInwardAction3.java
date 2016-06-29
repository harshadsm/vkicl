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
import vkicl.services.WarehouseInwardService;
import vkicl.util.Constants;
import vkicl.util.Converter;
import vkicl.util.PropFileReader;
import vkicl.vo.PortOutwardPostDataContainerVO;
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
			
			userInfoVO = getUserProfile(request);
			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			Gson gson = new Gson();
			String postDataContainerStr = request.getParameter("itemsToSaveWarehouseInwardJson");
			log.info(postDataContainerStr);
			PortOutwardPostDataContainerVO postDataContainer = gson.fromJson(postDataContainerStr, PortOutwardPostDataContainerVO.class);			
			WarehouseInwardService warehouseInwardService = new WarehouseInwardService();
			warehouseInwardService.processWarehouseInwardEntries(postDataContainer, request, userInfoVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}
}

