package vkicl.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import vkicl.form.PortOutwardForm;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.PortOutwardRecordVO;
import vkicl.vo.UserInfoVO;

public class PortOutwardAction extends BaseAction {
	private static Logger log = Logger.getLogger(PortOutwardAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = null;
		PortOutwardForm portOutwardForm = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request,
					Constants.Apps.PORT_ENTRY);
			if (null != actionForward)
				return actionForward;

			//Get the related Port Inward Record
//			String portInwardId = request.getParameter("port_inward_id");
//			PortInwardService portInwardService = new PortInwardService();
//			PortInwardRecordVO portInward = portInwardService.getPortInwardById(portInwardId);
//			request.setAttribute("portInward", portInward);
			
			
			
			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			String itemsToSaveJson = request.getParameter("itemsToSaveJson");
			log.info(itemsToSaveJson);
			
			Gson gson = new Gson();
			List<PortOutwardRecordVO> portOutwardRecordsToBeSaved = gson.fromJson(itemsToSaveJson, new TypeToken<List<PortOutwardRecordVO>>(){}.getType());
			for(PortOutwardRecordVO vo:portOutwardRecordsToBeSaved){
				log.info(vo);
			}
			
			userInfoVO = getUserProfile(request);
			portOutwardForm = (PortOutwardForm) form;
			genericListener = portOutwardForm.getGenericListener();
			if (genericListener.equalsIgnoreCase("add")) {
				String portInwardIdForLinking = request.getParameter("port_inward_id_for_linking_to_port_outward");
				log.info("portInwardIdForLinking = " + portInwardIdForLinking);
				
				
				
//				PortDaoImpl impl = new PortDaoImpl();
//				portOutwardForm = impl.addPortOutwardData(portOutwardForm,
//						userInfoVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}

}
