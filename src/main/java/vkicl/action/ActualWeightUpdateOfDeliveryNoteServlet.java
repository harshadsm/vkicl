package vkicl.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import vkicl.daoImpl.DispatchOrderDetailsDaoImpl;
import vkicl.daoImpl.WarehouseDaoImpl;
import vkicl.form.WarehouseOutwardForm;
import vkicl.services.DeliveryNoteService;
import vkicl.services.PortOutwardShipmentService;
import vkicl.services.WarehouseOutwardForActualWeightUpdateJsonService;
import vkicl.services.WarehouseOutwardService;
import vkicl.util.Constants;
import vkicl.vo.DispatchOrderLineItemForProcessingVO;
import vkicl.vo.SelectedStockItemForOutwardVO;
import vkicl.vo.UserInfoVO;
import vkicl.vo.WarehouseOutwardProcessingStatusVO;
import vkicl.vo.WarehouseOutwardProcessingVO;
import vkicl.vo.WarehouseOutwardVO;

public class ActualWeightUpdateOfDeliveryNoteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4896344374052419351L;

	private Logger logger = Logger.getLogger(ActualWeightUpdateOfDeliveryNoteServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		printAllParams(request);
		
		String actualWeightStr = request.getParameter("actualWeight");
		String idStr = request.getParameter("delivery_note_id");
		String handledBy = request.getParameter("handledBy");
		Integer id = Integer.parseInt(idStr);
		DeliveryNoteService service = new DeliveryNoteService();
		
		
		if(actualWeightStr!=null){
			Double actualWeight = Double.parseDouble(actualWeightStr);
			
			logger.debug("Going to update actual weight of warehouse_outward id = "+id);
			logger.debug("Submitted actual weight = "+actualWeight);
			
			service.updateActualWeightOfDeliveryNote(id, actualWeight);
			
			//As asked by Sourabh, I am distributing the actual weight and updating it in warehouse_outward_temp table.
			service.distributeActualWeightPerPlate(id, actualWeight);
			
		}

		
	}

	private void printAllParams(HttpServletRequest request) {
		logger.info("---------------------Printing all request params ----------------------");
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			String val = request.getParameter(paramName);
			logger.info(paramName + " = " + val);
		}

		logger.info("---------------------Printing all request attributes ----------------------");
		Enumeration<String> attrNames = request.getAttributeNames();
		while (attrNames.hasMoreElements()) {
			String attrName = attrNames.nextElement();
			Object val = request.getAttribute(attrName);
			logger.info(attrName + " = " + val);
		}

		logger.info("---------------------Printing form from session ----------------------");
		printBeanFromSession(request);

	}

	/**
	 * I Was going to manually remove the bean from session. However, after
	 * adding the below code, it
	 * 
	 * @param request
	 */
	private void printBeanFromSession(HttpServletRequest request) {
		logger.info(
				"DO NOT REMOVE THIS METHOD. It somehow results into removing the actionForm bean from session. And it is necessary for proper page flow of warehouse outward.");
		HttpSession session = request.getSession();
		WarehouseOutwardForm formInSession = (WarehouseOutwardForm) session.getAttribute("WarehouseOutwardForm");
		if (formInSession != null) {
			logger.info(formInSession.toString());
		} else {
			logger.info("No form in session.");
		}

	}

	
}
