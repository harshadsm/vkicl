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

import vkicl.daoImpl.WarehouseDaoImpl;
import vkicl.form.WarehouseOutwardForm;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
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
		WarehouseOutwardForm warehouseOutwardForm = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request, Constants.Apps.WAREHOUSE_ENTRY);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}
}
