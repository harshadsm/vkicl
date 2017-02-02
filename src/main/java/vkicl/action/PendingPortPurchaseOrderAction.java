package vkicl.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vkicl.action.BaseAction;
import vkicl.daoImpl.PortPurchaseOrderDaoImpl;
import vkicl.daoImpl.ReportDaoImpl;
import vkicl.form.PortPurchaseOrderDeliveryForm;
import vkicl.form.PortPurchaseOrderForm;
import vkicl.report.form.WarehouseDispatchReportForm;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.PortPurchaseOrderVO;
import vkicl.vo.UserInfoVO;

public class PendingPortPurchaseOrderAction extends BaseAction {
	private static Logger log = Logger.getLogger(PendingPortPurchaseOrderAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionForward actionForward = null;
		PortPurchaseOrderDeliveryForm portpurchaseorderdeliveryForm = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request, Constants.Apps.PORT_ENTRY);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);
			portpurchaseorderdeliveryForm = (PortPurchaseOrderDeliveryForm) form;
			PortPurchaseOrderDaoImpl impl = new PortPurchaseOrderDaoImpl();

			portpurchaseorderdeliveryForm = impl.fetchPendingPPO(portpurchaseorderdeliveryForm, userInfoVO);

			setUserProfile(request, userInfoVO);
			log.info("Loading Port Purchase Order Delivery");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}

}
