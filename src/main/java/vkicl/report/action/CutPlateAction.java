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
import vkicl.exceptions.CutNotPossibleException;
import vkicl.form.StockForm;

import vkicl.services.PlateCuttingService;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class CutPlateAction extends BaseAction {
	private static Logger log = Logger.getLogger(CutPlateAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();
	
	

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = null;
		StockForm stockForm = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request,
					Constants.Apps.STOCK);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);
			stockForm = (StockForm) form;
			ReportDaoImpl impl = new ReportDaoImpl();
			
			//ArrayList<LabelValueBean> locationList = impl.getList(userInfoVO,
			//		"query.unique.location");
			//stockReportForm.setLocationList(locationList);
			
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
				//stockForm = impl.fetchStockBalReport(stockForm,
					//	userInfoVO);
				
				//portInwardForm = (PortInwardForm) form;
				genericListener = stockForm.getGenericListener();
				if (genericListener.equalsIgnoreCase("addDetails")) {
					PlateCuttingService service = new PlateCuttingService();
					
					service.processForm(stockForm, userInfoVO);
				} 
				
				
				//log.info("Fetched Stock Report Data");
			//}
			log.info("Loaded Cutting Plate Details");

		} catch(CutNotPossibleException e){
			actionForward = mapping.findForward(Constants.Mapping.CUT_NOT_POSSIBLE);
		}
		catch (Exception e) {
			e.printStackTrace();
			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
		}
		return actionForward;
	}

}
