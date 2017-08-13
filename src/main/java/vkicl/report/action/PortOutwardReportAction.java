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
import vkicl.report.form.PortOutwardReportForm;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class PortOutwardReportAction extends BaseAction {
	private static Logger log = Logger.getLogger(PortOutwardReportAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = null;
		PortOutwardReportForm portOutwardReportForm = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request,
					Constants.Apps.PORT_REPORT);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);
			portOutwardReportForm = (PortOutwardReportForm) form;
			ReportDaoImpl impl = new ReportDaoImpl();

			ArrayList<LabelValueBean> vesselNameList = impl.getList(userInfoVO,
					"query.unique.vesselName");
			ArrayList<LabelValueBean> dispatchedToList = impl.getList(
					userInfoVO, "query.unique.customerLocation");
			ArrayList<LabelValueBean> gradeList = impl.getList(userInfoVO,
					"query.unique.grade");
			ArrayList<LabelValueBean> millNameList = impl.getList(userInfoVO, "query.unique.millName");
			ArrayList<LabelValueBean> materialTypeList = impl.getList(userInfoVO, "query.unique.materialType");

			portOutwardReportForm.setVesselNameList(vesselNameList);
			portOutwardReportForm.setDispatchedToList(dispatchedToList);
			portOutwardReportForm.setGradeList(gradeList);
			portOutwardReportForm.setMillNamesList(millNameList);
			portOutwardReportForm.setMaterialTypeList(materialTypeList);

			genericListener = portOutwardReportForm.getGenericListener();
			if (genericListener.equalsIgnoreCase("getReport")) {
				portOutwardReportForm = impl.fetchPortOutwardReport(
						portOutwardReportForm, userInfoVO);
				log.info("Fetched Port Outward Report Data");
			}
			log.info("Loaded Port Outward Report");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}

}
