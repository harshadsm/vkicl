package vkicl.report.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import vkicl.action.BaseAction;
import vkicl.daoImpl.PortPurchaseOrderDaoImpl;
import vkicl.daoImpl.ReportDaoImpl;
import vkicl.form.PortStockReportForm;
import vkicl.report.form.PortInwardReportForm;
import vkicl.report.form.StockReportForm;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class PortStockReportAction extends BaseAction {
	private static Logger log = Logger.getLogger(PortStockReportAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionForward actionForward = null;
		PortStockReportForm portStockReportForm = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request, Constants.Apps.PORT_REPORT);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);
			portStockReportForm = (PortStockReportForm) form;
			PortPurchaseOrderDaoImpl impl = new PortPurchaseOrderDaoImpl();

			ArrayList<LabelValueBean> vesselNameList = impl.getList(userInfoVO, "query.unique.vesselName");
			ArrayList<LabelValueBean> materialTypeList = impl.getList(userInfoVO, "query.unique.materialType");
			ArrayList<LabelValueBean> gradeList = impl.getList(userInfoVO, "query.unique.grade");
			ArrayList<LabelValueBean> millNameList = impl.getList(userInfoVO, "query.unique.millName");
			portStockReportForm.setVesselNameList(vesselNameList);
			portStockReportForm.setMaterialTypeList(materialTypeList);
			portStockReportForm.setGradeList(gradeList);
			portStockReportForm.setMillNameList(millNameList);

			genericListener = portStockReportForm.getGenericListener();
			if (genericListener.equalsIgnoreCase("getReport")) {
				portStockReportForm = impl.fetchPortCumulativeStockReport(portStockReportForm, userInfoVO);
				log.info("Fetched Port Stock Report Data");
			}
			log.info("Loaded Port Stock Report");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}

}
