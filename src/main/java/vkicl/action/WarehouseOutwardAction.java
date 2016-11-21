package vkicl.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import vkicl.daoImpl.WarehouseDaoImpl;
import vkicl.form.WarehouseOutwardForm;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class WarehouseOutwardAction extends BaseAction {
	private static Logger log = Logger.getLogger(WarehouseOutwardAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = null;
		WarehouseOutwardForm warehouseOutwardForm = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request,
					Constants.Apps.WAREHOUSE_ENTRY);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);
			warehouseOutwardForm = (WarehouseOutwardForm) form;
			WarehouseDaoImpl impl = new WarehouseDaoImpl();
			genericListener = warehouseOutwardForm.getGenericListener();

			if (genericListener.equalsIgnoreCase("add")
					&& warehouseOutwardForm.getDispatchNo() != 0) {
				log.info("genericListener = " + genericListener);
				
				Integer availableQty=impl.fetchStockBalQuantity(warehouseOutwardForm);
				
				warehouseOutwardForm = impl.addWarehouseOutwardTempData(
						warehouseOutwardForm, userInfoVO);
				
				impl.updateStockBalanceData(warehouseOutwardForm, userInfoVO,availableQty);
				impl.addStockOutwardData(warehouseOutwardForm, userInfoVO);
					
				setUserProfile(request, userInfoVO);
				actionForward = mapping
						.findForward(Constants.Mapping.DISPATCH_REPORT_PAGE);
			}

			ArrayList<LabelValueBean> dispatchNoList = impl.getList(userInfoVO,
					"query.warehouse.outward.pending");
			warehouseOutwardForm.setDispatchNoList(dispatchNoList);
			setUserProfile(request, userInfoVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}
}
