package vkicl.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import vkicl.daoImpl.PortOutwardShipmentDaoImpl;
import vkicl.services.PortOutwardDetailsJsonService;
import vkicl.services.PortOutwardShipmentService;

public class ActualWeightUpdateAtInwardJsonServlet extends HttpServlet {
	private static final long serialVersionUID = 48963374052419351L;

	private Logger logger = Logger.getLogger(ActualWeightUpdateAtInwardJsonServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			PortOutwardShipmentService service = new PortOutwardShipmentService();
			String json = service.getPortOutwardShipmentsListAsJson(request);
			
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
