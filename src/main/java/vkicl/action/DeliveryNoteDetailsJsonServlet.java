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

import vkicl.daoImpl.DeliveryNoteDaoImpl;
import vkicl.services.PortInwardDetailsJsonService;
import vkicl.services.WarehouseOutwardDetailsJsonService;
import vkicl.services.WarehouseOutwardService;
import vkicl.services.WarehouseOutwardTempService;
import vkicl.vo.DeliveryNoteLineItemVO;
import vkicl.vo.WarehouseOutwardTempVO;

public class DeliveryNoteDetailsJsonServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4896344374052419351L;

	private Logger logger = Logger.getLogger(DeliveryNoteDetailsJsonServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			
			String deliveryNoteIdStr = request.getParameter("deliveryNoteId");
			Integer deliveryNoteId = Integer.parseInt(deliveryNoteIdStr);
			
//			WarehouseOutwardTempService warehouseOutwardTempService = new WarehouseOutwardTempService();
//			List<WarehouseOutwardTempVO> list = warehouseOutwardTempService.getWarehouseOutwardTempRecordsByWarehouseOutwardId(deliveryNoteId);
			
			DeliveryNoteDaoImpl d = new DeliveryNoteDaoImpl();
			List<DeliveryNoteLineItemVO> list = d.getDeliveryNoteLineItemsById(deliveryNoteId);
			
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
