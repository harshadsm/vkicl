package vkicl.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import vkicl.services.PortInwardDetailsJsonService;
import vkicl.services.WarehouseOutwardDetailsJsonService;
import vkicl.services.WarehouseOutwardService;
import vkicl.services.WarehouseOutwardTempService;
import vkicl.vo.WarehouseOutwardTempVO;

public class WarehouseOutwardTempJsonServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4896344374052419351L;

	private Logger logger = Logger.getLogger(WarehouseOutwardTempJsonServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			
			String warehouseOutwardIdStr = request.getParameter("warehouseOutwardId");
			Integer warehouseOutwardId = Integer.parseInt(warehouseOutwardIdStr);
			WarehouseOutwardTempService warehouseOutwardTempService = new WarehouseOutwardTempService();
			List<WarehouseOutwardTempVO> list = warehouseOutwardTempService.getWarehouseOutwardTempRecordsByWarehouseOutwardId(warehouseOutwardId);
			Gson gson = new Gson();
			String json = gson.toJson(list);
			
			logger.debug("Going to return WarehouseOutwardTempJsonServlet json ");
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
