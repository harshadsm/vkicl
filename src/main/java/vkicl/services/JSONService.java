package vkicl.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import vkicl.daoImpl.PortDaoImpl;
import vkicl.daoImpl.PortPurchaseOrderDaoImpl;
import vkicl.daoImpl.ReportDaoImpl;
import vkicl.daoImpl.WarehouseDaoImpl;
import vkicl.form.BaseForm;
import vkicl.form.PortInwardForm;
import vkicl.form.WarehouseInwardForm;
import vkicl.form.WarehouseLocationForm;
import vkicl.form.WarehouseOutwardFinalForm;
import vkicl.form.WarehouseOutwardForm;
import vkicl.report.form.WarehouseDispatchDetailsReportForm;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(JSONService.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public JSONService() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = null;
		BaseForm form = null;
		PrintWriter out = null;
		try {
			method = request.getParameter("method");
			if (null != method && method.length() > 0) {
				log.info("method = " + method);

				UserInfoVO userInfoVO = (UserInfoVO) request.getSession(true).getAttribute(Constants.USER_INFO_SESSION);

				if (method.equalsIgnoreCase("fetchPortInwardDetails")) {
					String vendorName = request.getParameter("vendorName");
					String vesselName = request.getParameter("vesselName");
					String vesselDate = request.getParameter("vesselDate");
					log.info("vendorName = " + vendorName);
					log.info("vesselName = " + vesselName);
					log.info("vesselDate = " + vesselDate);
					form = new PortInwardForm(vendorName, vesselName, vesselDate);
					PortDaoImpl impl = new PortDaoImpl();
					form = impl.fetchPortInwardDetails((PortInwardForm) form, userInfoVO);
				} else if (method.equalsIgnoreCase("fetchWarehouseInwardDetails")) {
					String portVehicleNumber = request.getParameter("portVehicleNumber");
					String portVehicleDate = request.getParameter("portVehicleDate");
					log.info("portVehicleNumber = " + portVehicleNumber);
					log.info("portVehicleDate = " + portVehicleDate);
					form = new WarehouseInwardForm(portVehicleNumber, portVehicleDate);
					WarehouseDaoImpl impl = new WarehouseDaoImpl();
					form = impl.fetchWarehouseInwardDetails((WarehouseInwardForm) form, userInfoVO);
				} else if (method.equalsIgnoreCase("fetchWarehouseOutwardDetails")) {
					printAllParams(request);
					String dispatchNo = request.getParameter("dispatchNo");
					log.info("dispatchNo = " + dispatchNo);
					if (!StringUtils.isEmpty(dispatchNo) && !"null".equalsIgnoreCase(dispatchNo)) {
						form = new WarehouseDispatchDetailsReportForm(Integer.parseInt(dispatchNo));
						ReportDaoImpl impl = new ReportDaoImpl();
						form = impl.fetchWarehouseDispatchDetailsReport((WarehouseDispatchDetailsReportForm) form,
								userInfoVO);

					} else {
						throw new IllegalArgumentException("Invalid dispatchNo = " + dispatchNo);
					}
				} else if (method.equalsIgnoreCase("fetchWarehouseLocationDetails")) {
					String millName = request.getParameter("millName");
					String make = request.getParameter("make");
					String grade = request.getParameter("grade");
					String length = request.getParameter("length");
					String width = request.getParameter("width");
					String thickness = request.getParameter("thickness");
					String dispatchNo = request.getParameter("dispatchNo");
					String location = request.getParameter("location");
					String dispatchDetailRowId = request.getParameter("dispatchDetailRowId");
					String stockId = request.getParameter("stockId");
					String heatNo = request.getParameter("heatNo");
					String plateNo = request.getParameter("plateNo");

					log.info("millName = " + millName);
					log.info("make = " + make);
					log.info("grade = " + grade);
					log.info("length = " + length);
					log.info("width = " + width);
					log.info("thickness = " + thickness);
					log.info("dispatchNo = " + dispatchNo);
					log.info("dispatchDetailRowId = " + dispatchDetailRowId);

					form = new WarehouseLocationForm(millName, make, grade, length, width, thickness, location, stockId,
							heatNo, plateNo);

					WarehouseDaoImpl impl = new WarehouseDaoImpl();
					// form = impl.fetchWarehouseLocationDetails(
					// (WarehouseLocationForm) form, userInfoVO);
					form = impl.fetchWarehouseLocationData((WarehouseLocationForm) form, userInfoVO);

					populatePreviouslySelectedQuantityIfAny(dispatchNo, dispatchDetailRowId,
							(WarehouseLocationForm) form, userInfoVO, impl);

				} else if (method.equalsIgnoreCase("fetchStockFinalDetails")) {
					String millName = request.getParameter("millName");
					String make = request.getParameter("make");
					String grade = request.getParameter("grade");
					String length = request.getParameter("length");
					String width = request.getParameter("width");
					String thickness = request.getParameter("thickness");
					String location = request.getParameter("location");
					String heatNo = request.getParameter("heatNo");
					String plateNo = request.getParameter("plateNo");
					log.info("millName = " + millName);
					log.info("make = " + make);
					log.info("grade = " + grade);
					log.info("length = " + length);
					log.info("width = " + width);
					log.info("thickness = " + thickness);
					log.info("location = " + location);
					log.info("heatNo = " + heatNo);
					log.info("plateNo = " + plateNo);

					form = new WarehouseOutwardFinalForm(location, heatNo, plateNo, millName, make, grade, length,
							width, thickness);

					WarehouseDaoImpl impl = new WarehouseDaoImpl();
					form = impl.fetchWarehouseOutwardFinalStockDetails((WarehouseOutwardFinalForm) form, userInfoVO);

				}
				GsonBuilder builder = new GsonBuilder();
				Gson gson = builder.create();
				out = response.getWriter();
				String jsonStr = gson.toJson(form);
				log.info(jsonStr);
				out.print(jsonStr + "");
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void populatePreviouslySelectedQuantityIfAny(String dispatchOrderId, String dispatchDetailRowId,
			WarehouseLocationForm form, UserInfoVO userInfo, WarehouseDaoImpl daoImpl) {
		log.info("Populating preselected quantity if any");
		form = daoImpl.findWarehouseOutwardTempRecord(Integer.parseInt(dispatchOrderId),
				Integer.parseInt(dispatchDetailRowId), form, userInfo);
		log.info(form);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void printAllParams(HttpServletRequest request) {
		log.info("---------------------Printing all request params ----------------------");
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			String val = request.getParameter(paramName);
			log.info(paramName + " = " + val);
		}

		log.info("---------------------Printing all request attributes ----------------------");
		Enumeration<String> attrNames = request.getAttributeNames();
		while (attrNames.hasMoreElements()) {
			String attrName = attrNames.nextElement();
			Object val = request.getAttribute(attrName);
			log.info(attrName + " = " + val);
		}

		log.info("---------------------Printing form from session ----------------------");
		printBeanFromSession(request);

	}

	/**
	 * I Was going to manually remove the bean from session. However, after
	 * adding the below code, it
	 * 
	 * @param request
	 */
	private void printBeanFromSession(HttpServletRequest request) {
		log.info(
				"DO NOT REMOVE THIS METHOD. It somehow results into removing the actionForm bean from session. And it is necessary for proper page flow of warehouse outward.");
		HttpSession session = request.getSession();
		WarehouseOutwardForm formInSession = (WarehouseOutwardForm) session.getAttribute("WarehouseOutwardForm");
		if (formInSession != null) {
			log.info(formInSession.toString());
		} else {
			log.info("No form in session.");
		}

	}

}
