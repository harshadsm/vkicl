package vkicl.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vkicl.daoImpl.WarehouseDaoImpl;
import vkicl.form.WarehouseOutwardFinalForm;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WarehouseOutwardFinalAction extends BaseAction {
	private static Logger log = Logger
			.getLogger(WarehouseOutwardFinalAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = null;
		WarehouseOutwardFinalForm warehouseOutwardFinalForm = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request,
					Constants.Apps.WAREHOUSE_ENTRY);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);
			warehouseOutwardFinalForm = (WarehouseOutwardFinalForm) form;
			WarehouseDaoImpl impl = new WarehouseDaoImpl();
			log.info("Inserting outward_temp_id = "
					+ warehouseOutwardFinalForm.getOutwardTempId());
			userInfoVO = impl.addWarehouseOutwardFinalData(
					warehouseOutwardFinalForm, userInfoVO);
			warehouseOutwardFinalForm.clear();

			response.setContentType("text/json");
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			PrintWriter out = response.getWriter();
			out.print(gson.toJson(userInfoVO) + "");
			out.close();
			userInfoVO.setMessage("");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}
}
