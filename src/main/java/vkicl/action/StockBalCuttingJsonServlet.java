package vkicl.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import vkicl.services.PlateCuttingService;
import vkicl.services.StockBalDetailsJsonService;

public class StockBalCuttingJsonServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4896344374052419351L;

	private Logger logger = Logger.getLogger(StockBalCuttingJsonServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			PlateCuttingService service = new PlateCuttingService();
			String json = service.getStockBalCuttingListAsJson(request);

			logger.debug("Going to return StockBalCuttingJsonService json ");
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
