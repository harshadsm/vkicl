package vkicl.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import vkicl.form.WarehouseOutwardForm;
import vkicl.services.StockLocationUpdateService;
import vkicl.util.Constants;
import vkicl.vo.StockLocationUpdateProecessingStatusVO;
import vkicl.vo.UserInfoVO;

public class StockLocationUpdateServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4896344374052419351L;

	private Logger logger = Logger.getLogger(StockLocationUpdateServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		StockLocationUpdateProecessingStatusVO status = new StockLocationUpdateProecessingStatusVO();

		response.setContentType("text/text;charset=utf-8");
		response.setHeader("cache-control", "no-cache");

		PrintWriter out = response.getWriter();
		
		try {
			logger.debug("Going to return WarehouseOutwardJsonServlet json ");

			StockLocationUpdateService service = new StockLocationUpdateService();
			String statusString = service.updateLocation(request);
			if(statusString!=null && statusString.equalsIgnoreCase("success")){
				status.setStatus("success");	
			}else{
				status.setStatus("error");
			}
			Gson gson = new Gson();
			String json = gson.toJson(status);
			logger.debug(json);
			

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
