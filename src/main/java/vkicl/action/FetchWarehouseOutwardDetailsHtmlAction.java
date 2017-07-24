package vkicl.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vkicl.report.bean.PortOutwardBean2;
import vkicl.services.PortOutwardDetailsService;
import vkicl.services.WarehouseOutwardTempService;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.WarehouseOutwardTempVO;

public class FetchWarehouseOutwardDetailsHtmlAction extends BaseAction{
	private static Logger log = Logger.getLogger(DeliveryNoteViewAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		ActionForward actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
		
		String warehouseOutwardIdStr = request.getParameter("warehouseOutwardId");
		Integer warehouseOutwardId = Integer.parseInt(warehouseOutwardIdStr);
		WarehouseOutwardTempService warehouseOutwardTempService = new WarehouseOutwardTempService();
		
		try {
		
			List<WarehouseOutwardTempVO> warehouseOutwardTempRecords = warehouseOutwardTempService.getWarehouseOutwardTempRecordsByWarehouseOutwardId(warehouseOutwardId);
			request.setAttribute("warehouseOutwardTempRecords", warehouseOutwardTempRecords);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return actionForward;
	}
}
