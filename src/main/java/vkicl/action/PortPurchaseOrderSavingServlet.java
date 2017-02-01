package vkicl.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import vkicl.services.PortPurchaseOrderJsonDetailService;
import vkicl.util.PropFileReader;
import vkicl.vo.PortPurchaseOrderPostDataContainerVO;
import vkicl.vo.PortPurchaseOrderSavingStatusVO;
import vkicl.vo.UserInfoVO;

public class PortPurchaseOrderSavingServlet extends HttpServlet {
	private static Logger log = Logger.getLogger(PortPurchaseOrderSavingServlet.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		printParameterMap(request);
		PortPurchaseOrderSavingStatusVO status = new PortPurchaseOrderSavingStatusVO();
		UserInfoVO userInfoVO = composeDummy();

		try {

			Gson gson = new Gson();
			String postDataContainerStr = request.getParameter("itemsToSaveJson");
			log.info(postDataContainerStr);

			// portpurchaseForm = (PortPurchaseOrderForm) form;

			PortPurchaseOrderPostDataContainerVO portPurchaseOrder = gson.fromJson(postDataContainerStr,
					PortPurchaseOrderPostDataContainerVO.class);
			if (portPurchaseOrder != null) {
				PortPurchaseOrderJsonDetailService portpurchaseService = new PortPurchaseOrderJsonDetailService();
				status = portpurchaseService.processPurchaseOrderEntries(portPurchaseOrder, request, userInfoVO);
			} else {
				throw new JsonParseException("Could not parse json " + postDataContainerStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			status.setErrorMessage(e.getMessage());
			status.setStatus("error");
		}

		Gson gson = new Gson();
		String json = gson.toJson(status);
		response.setContentType("text/text;charset=utf-8");
		response.setHeader("cache-control", "no-cache");

		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();

	}

	private UserInfoVO composeDummy() {
		UserInfoVO userInfoVo = new UserInfoVO();
		userInfoVo.setUserName("harshadmahajandummy");
		return userInfoVo;
	}

	public void printParameterMap(HttpServletRequest request) {
		if (request != null) {
			Map<String, String[]> paramMap = request.getParameterMap();
			Set<String> keys = paramMap.keySet();

			for (String key : keys) {
				String[] values = paramMap.get(key);
				log.info("====> Key = " + key);
				for (String v : values) {
					log.info(v);
				}
				log.info("-----------------------");
			}
		}
	}
}
