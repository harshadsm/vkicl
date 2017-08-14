package vkicl.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import vkicl.services.PortInwardDetailsJsonService;
import vkicl.services.WarehouseOutwardDetailsJsonService;
import vkicl.services.WarehouseOutwardService;

public class WarehouseOutwardJsonServlet2 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4896344374052419351L;

	private Logger logger = Logger.getLogger(WarehouseOutwardJsonServlet2.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			
			WarehouseOutwardService warehouseOutwardService = new WarehouseOutwardService();
			String json = warehouseOutwardService.getWarehouseOutwardListAsJson(request);
//			WarehouseOutwardDetailsJsonService service = new WarehouseOutwardDetailsJsonService();
//			String json = service.getWarehouseOutwardDetailsListAsJson(request);

			logger.debug("Going to return WarehouseOutwardJsonServlet json ");
			logger.debug(json);

			response.setContentType("text/text;charset=utf-8");
			response.setHeader("cache-control", "no-cache");

			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();

		} catch (Exception e) {
			logger.error("Some error", e);
			throw new ServletException(e);
		}
	}

}
