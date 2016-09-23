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
import vkicl.report.form.StockReportForm;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class StockReportAction extends BaseAction {
	private static Logger log = Logger.getLogger(StockReportAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();
	
	

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = null;
		StockReportForm stockReportForm = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request,
					Constants.Apps.STOCK);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);
			stockReportForm = (StockReportForm) form;
			ReportDaoImpl impl = new ReportDaoImpl();
			
			ArrayList<LabelValueBean> locationList = impl.getList(userInfoVO,
					"query.unique.location");
			stockReportForm.setLocationList(locationList);
			
			/*ArrayList<LabelValueBean> materialTypeList = impl.getList(
					userInfoVO, "query.unique.materialType");
			ArrayList<LabelValueBean> millNameList = impl.getList(userInfoVO,
					"query.unique.millName");
			ArrayList<LabelValueBean> gradeList = impl.getList(userInfoVO,
					"query.unique.grade");
			ArrayList<LabelValueBean> makeList = impl.getList(userInfoVO,
					"query.unique.make");
			ArrayList<LabelValueBean> locationList = impl.getList(userInfoVO,
					"query.unique.location");

			stockReportForm.setMaterialTypeList(materialTypeList);
			stockReportForm.setMillNameList(millNameList);
			stockReportForm.setGradeList(gradeList);
			stockReportForm.setMakeList(makeList);
			stockReportForm.setLocationList(locationList);*/

			//genericListener = stockReportForm.getGenericListener();
			//if (genericListener.equalsIgnoreCase("getReport")) {
				stockReportForm = impl.fetchStockBalReport(stockReportForm,
						userInfoVO);
				//log.info("Fetched Stock Report Data");
			//}
			log.info("Loaded Stock Report");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}

}
