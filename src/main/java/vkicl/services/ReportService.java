package vkicl.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import vkicl.daoImpl.PortPurchaseOrderDaoImpl;
import vkicl.daoImpl.ReportDaoImpl;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class ReportService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ReportService.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ReportService() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = null;
		String message = "Error";
		PrintWriter out = null;
		try {

			UserInfoVO userInfoVO = (UserInfoVO) request.getSession().getAttribute(Constants.USER_INFO_SESSION);
			method = request.getParameter("method");
			Map<String, String[]> map = request.getParameterMap();
			for (Object key : map.keySet()) {
				log.info(key + " = " + map.get(key)[0]);
			}
			ReportDaoImpl impl = new ReportDaoImpl();
			if (StringUtils.isNotBlank(method)) {
				if (method.equals("updatePortInwardReport") && userInfoVO.hasAccess(Constants.Apps.PORT_ENTRY)) {
					message = impl.updatePortInwardReport(map, userInfoVO);
				} else if (method.equals("updatePortOutwardReport")
						&& userInfoVO.hasAccess(Constants.Apps.PORT_ENTRY)) {
					message = impl.updatePortOutwardReport(map, userInfoVO);
				} else if (method.equals("deletePortOutward") && userInfoVO.hasAccess(Constants.Apps.PORT_ENTRY)) {
					message = impl.deletePortOutward(map);
				} else if (method.equals("updateWarehouseInwardReport")
						&& userInfoVO.hasAccess(Constants.Apps.WAREHOUSE_ENTRY)) {
					message = impl.updateWarehouseInwardReport(map, userInfoVO);
				} else if (method.equals("changeStockLocation")
						&& userInfoVO.hasAccess(Constants.Apps.LOCATION_TRANSFER)) {
					message = impl.changeStockLocation(map, userInfoVO);
				} else if (method.equals("deleteStock") && userInfoVO.hasAccess(Constants.Apps.STOCK_DELETE)) {
					message = impl.deleteStock(map, userInfoVO);
				} else if (method.equals("reserveStock") && userInfoVO.hasAccess(Constants.Apps.STOCK_RESERVATION)) {
					message = impl.reserveStock(map, userInfoVO);
				} else if (method.equals("deleteDispatchOrder")
						&& userInfoVO.hasAccess(Constants.Apps.DISPATCH_ORDER)) {
					message = impl.deleteDispatchOrder(map, userInfoVO);

				} else if (method.equalsIgnoreCase("deleteDeliveryNoteLineItems")) {
					PortPurchaseOrderDaoImpl dao = new PortPurchaseOrderDaoImpl();
					message = dao.deleteDeliveryNoteLineItems(request.getParameter("id"));
				}
			}
			out = response.getWriter();
			out.print("<root><message>" + message + "</message></root>");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
