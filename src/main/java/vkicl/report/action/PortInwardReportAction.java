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
import vkicl.report.form.PortInwardReportForm;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class PortInwardReportAction extends BaseAction {
	private static Logger log = Logger.getLogger(PortInwardReportAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = null;
		PortInwardReportForm portInwardReportForm = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request,
					Constants.Apps.PORT_REPORT);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);
			portInwardReportForm = (PortInwardReportForm) form;
			ReportDaoImpl impl = new ReportDaoImpl();

			ArrayList<LabelValueBean> vesselNameList = impl.getList(userInfoVO,
					"query.unique.vesselName");
			ArrayList<LabelValueBean> materialTypeList = impl.getList(
					userInfoVO, "query.unique.materialType");
			ArrayList<LabelValueBean> gradeList = impl.getList(userInfoVO,
					"query.unique.grade");
			portInwardReportForm.setVesselNameList(vesselNameList);
			portInwardReportForm.setMaterialTypeList(materialTypeList);
			portInwardReportForm.setGradeList(gradeList);

			genericListener = portInwardReportForm.getGenericListener();
			if (genericListener.equalsIgnoreCase("getReport")) {
				portInwardReportForm = impl.fetchPortInwardReport(
						portInwardReportForm, userInfoVO);
				log.info("Fetched Port Inward Report Data");
			}
			log.info("Loaded Port Inward Report");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}

}
