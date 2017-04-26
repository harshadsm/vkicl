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
import vkicl.daoImpl.ReportDaoImpl;
import vkicl.report.form.WarehouseInwardReportForm;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class WarehouseInwardReportAction extends BaseAction {
	private static Logger log = Logger
			.getLogger(WarehouseInwardReportAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = null;
		WarehouseInwardReportForm warehouseInwardReportForm = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request,
					Constants.Apps.PORT_REPORT);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);
			warehouseInwardReportForm = (WarehouseInwardReportForm) form;
			ReportDaoImpl impl = new ReportDaoImpl();

			ArrayList<LabelValueBean> receivedFromList = impl.getList(
					userInfoVO, 
					//"query.unique.customerLocation");
					"query.unique.vendorName");
			ArrayList<LabelValueBean> materialTypeList = impl.getList(
					userInfoVO, "query.unique.materialType");
			ArrayList<LabelValueBean> vesselNameList = impl.getList(userInfoVO,
					"query.unique.vesselName");
			ArrayList<LabelValueBean> vesselDateList = impl.getDateList(
					userInfoVO, "query.unique.vesselDate");
			
			ArrayList<LabelValueBean> gradeList = impl.getList(userInfoVO, "query.unique.grade");
			
			warehouseInwardReportForm.setReceivedFromList(receivedFromList);
			warehouseInwardReportForm.setMaterialTypeList(materialTypeList);
			warehouseInwardReportForm.setVesselNameList(vesselNameList);
			warehouseInwardReportForm.setVesselDateList(vesselDateList);
			warehouseInwardReportForm.setGradeList(gradeList);

			genericListener = warehouseInwardReportForm.getGenericListener();
			if (genericListener.equalsIgnoreCase("getReport")) {
				warehouseInwardReportForm = impl.fetchWarehouseInwardReport(
						warehouseInwardReportForm, userInfoVO);
				log.info("Fetched Warehouse Inward Report Data");
			}
			log.info("Loaded Warehouse Inward Report");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}

}
