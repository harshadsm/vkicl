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
import vkicl.util.Constants;
import vkicl.vo.DispatchOrderLineItemForProcessingVO;
import vkicl.vo.SelectedStockItemForOutwardVO;
import vkicl.vo.UserInfoVO;
import vkicl.vo.WarehouseOutwardProcessingStatusVO;
import vkicl.vo.WarehouseOutwardProcessingVO;
import vkicl.vo.WarehouseOutwardVO;

public class WarehouseOutwardProcessingServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4896344374052419351L;

	private Logger logger = Logger.getLogger(WarehouseOutwardProcessingServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		WarehouseOutwardProcessingStatusVO status = new WarehouseOutwardProcessingStatusVO();

		response.setContentType("text/text;charset=utf-8");
		response.setHeader("cache-control", "no-cache");

		PrintWriter out = response.getWriter();
		
		try {
			UserInfoVO userInfoVO = getUserProfile(request);
			WarehouseDaoImpl impl = new WarehouseDaoImpl();

			String WAREHOUSE_OUTWARD_JSON = request.getParameter("WAREHOUSE_OUTWARD_JSON");
			logger.debug(WAREHOUSE_OUTWARD_JSON);

			Type objectType = new TypeToken<WarehouseOutwardProcessingVO>() {
			}.getType();
			WarehouseOutwardProcessingVO warehouseProcessingRequest = new Gson().fromJson(WAREHOUSE_OUTWARD_JSON,
					objectType);

			logger.debug(warehouseProcessingRequest.toString());
			// Add data to warehouse_outward_temp
			impl.addWarehouseOutwardTempData(warehouseProcessingRequest, userInfoVO);

			// Add stock outward data
			impl.addStockOutwardData(warehouseProcessingRequest, userInfoVO);

			// Update the stock_balance table records that are selected for
			// outward.
			for (DispatchOrderLineItemForProcessingVO doli : warehouseProcessingRequest.getWarehouseOutwardDetails()) {
				for (SelectedStockItemForOutwardVO st : doli.getSelectedStockLineItems()) {
					Integer newQtyAvailable = st.getQuantity() - st.getStockQuantityForDelivery();
					if (newQtyAvailable == 0) {
						String flag = "1";
						impl.updateStockBalanceData(userInfoVO, newQtyAvailable, st.getStockId(), flag);
					} else {
						String flag = "2";
						impl.updateStockBalanceData(userInfoVO, newQtyAvailable, st.getStockId(), flag);
					}
				}
			}

			// Add the warehouse_outward_process data.
			impl.addWarehouseOutwardProcessData(warehouseProcessingRequest, userInfoVO);
			
			// Check if DO is completely delivered, mark it as COMPLETED
			DispatchOrderDetailsDaoImpl dispatchOrderDetailsDao = new DispatchOrderDetailsDaoImpl();
			Integer totalRecordsCount = dispatchOrderDetailsDao.getDispatchOrderDetailsCount(warehouseProcessingRequest.getDispatchOrderId(),null);//, portInwardId);
			if(totalRecordsCount <=0 ){
				//The DO is completed.
				logger.debug("DO is completely processed. Gonna update its status = completed.");
				impl.updateStatus(warehouseProcessingRequest.getDispatchOrderId(), userInfoVO);
			}
			
			
			logger.debug("Going to return WarehouseOutwardJsonServlet json ");

			status.setStatus("success");
			

		} catch (Exception e) {
			logger.error("Some error", e);
			//throw new ServletException(e);
			status.setStatus("error");
			status.setErrorMessage(e.getMessage());
		} finally{
			Gson gson = new Gson();
			String resultJsonString = gson.toJson(status);
			logger.debug(resultJsonString);
			out.println(resultJsonString);
			out.flush();
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

	private UserInfoVO getUserProfile(HttpServletRequest request) {
		logger.debug("URL = " + request.getRequestURL());

		HttpSession session = request.getSession(true);
		UserInfoVO userInfoVO = null;
		userInfoVO = (UserInfoVO) session.getAttribute(Constants.USER_INFO_SESSION);
		if (null == userInfoVO)
			userInfoVO = new UserInfoVO();
		setUserProfile(request, userInfoVO);
		return userInfoVO;
	}

	private void setUserProfile(HttpServletRequest request, UserInfoVO userInfoVO) {
		HttpSession session = request.getSession(true);
		if (null == userInfoVO.getMessage())
			userInfoVO.setMessage("");
		session.setAttribute(Constants.USER_INFO_SESSION, userInfoVO);
	}

}
