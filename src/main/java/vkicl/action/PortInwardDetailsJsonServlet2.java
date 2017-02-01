package vkicl.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import vkicl.services.PortInwardDetailsJsonService;

import vkicl.services.PortPurchaseOrderJsonService;

public class PortInwardDetailsJsonServlet2 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4896344374052419351L;

	private Logger logger = Logger.getLogger(PortInwardDetailsJsonServlet2.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String json = "some error at server. Need to debug";
			if (request.getMethod() == "GET") {
				String portInwardId = request.getParameter("portInwardId");

				if (portInwardId != null) {
					// PortPurchaseOrderJsonDetailService service = new
					// PortPurchaseOrderJsonDetailService();
					// String json =
					// service.getPortPurchaseOrderDetailAsJson(request,
					// portInwardId);

					PortInwardDetailsJsonService portInwardDetailsJsonService = new PortInwardDetailsJsonService();
					json = portInwardDetailsJsonService.getPackingListAsJson2(request);
					logger.debug("Going to return PortPurchaseOrderJsonServlet json ");
					logger.debug(json);

				} else {
					logger.debug("no parameter foiund");

				}
				response.setContentType("text/text;charset=utf-8");
				response.setHeader("cache-control", "no-cache");

				PrintWriter out = response.getWriter();
				out.println(json);
				out.flush();

			}

		} catch (Exception e) {
			logger.error("Some error", e);
			throw new ServletException(e);
		}
	}

}
