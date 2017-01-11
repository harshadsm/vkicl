package vkicl.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import vkicl.daoImpl.WarehouseDaoImpl;
import vkicl.form.WarehouseOutwardForm;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.StockBalanceDetailsVO;
import vkicl.vo.UserInfoVO;
import vkicl.vo.WarehouseOutwardVO;
import vkicl.vo.WarehouseOutwardVO2;

public class WarehouseOutwardAction extends BaseAction {
	private static Logger log = Logger.getLogger(WarehouseOutwardAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		printAllParams(request);
		ActionForward actionForward = null;
		WarehouseOutwardForm warehouseOutwardForm = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request, Constants.Apps.WAREHOUSE_ENTRY);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);
			warehouseOutwardForm = (WarehouseOutwardForm) form;
			WarehouseDaoImpl impl = new WarehouseDaoImpl();
			genericListener = warehouseOutwardForm.getGenericListener();

			if (genericListener.equalsIgnoreCase("add") && warehouseOutwardForm.getDispatchNo() != 0) {
				log.info("genericListener = " + genericListener);
				String dispatchIdStr = Integer.toString(warehouseOutwardForm.getDispatchNo());
				request.setAttribute("dispatchNo_2", dispatchIdStr);
				impl.addWarehouseOutwardTempData(warehouseOutwardForm, userInfoVO);

				String flag = null;
				Integer qtyAvailable = null;
				List<String> availableQty = Arrays.<String> asList(warehouseOutwardForm.getAvailableQty());
				List<String> subQty = Arrays.<String> asList(warehouseOutwardForm.getSubQty());
				List<String> stockId = Arrays.<String> asList(warehouseOutwardForm.getStockId());
				List<String> qty = Arrays.<String> asList(warehouseOutwardForm.getQty());

				List<WarehouseOutwardVO> warehouseVOList = composeVOList(availableQty, subQty, stockId);

				for (WarehouseOutwardVO warehouseoutwardvo : warehouseVOList) {

					qtyAvailable = warehouseoutwardvo.getAvailableQty() - warehouseoutwardvo.getOrderedQty();
					if (qtyAvailable == 0) {
						flag = "1";
						impl.updateStockBalanceData(warehouseOutwardForm, userInfoVO, qtyAvailable,
								warehouseoutwardvo.getStockId(), flag);
					} else {
						flag = "2";
						impl.updateStockBalanceData(warehouseOutwardForm, userInfoVO, qtyAvailable,
								warehouseoutwardvo.getStockId(), flag);
					}

				}

				impl.addStockOutwardData(warehouseOutwardForm, userInfoVO);

				impl.addWarehouseOutwardProcessData(warehouseOutwardForm, userInfoVO);

				List<WarehouseOutwardVO2> warehouseOutwardVo2List = composeVOOutwardList(subQty, qty);
				for (WarehouseOutwardVO2 warehouseoutwardvo2 : warehouseOutwardVo2List) {

					Integer remainingqty = warehouseoutwardvo2.getQty() - warehouseoutwardvo2.getSubQty();
					if (remainingqty == 0) {
						impl.updateStatus(warehouseOutwardForm, userInfoVO);
					}

				}
				// composeVOOutwardList(subQty, qty);

				setUserProfile(request, userInfoVO);
				actionForward = mapping.findForward(Constants.Mapping.DISPATCH_REPORT_PAGE);
			}

			ArrayList<LabelValueBean> dispatchNoList = impl.getList(userInfoVO, "query.warehouse.outward.pending");
			warehouseOutwardForm.setDispatchNoList(dispatchNoList);
			setUserProfile(request, userInfoVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
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

	private List<WarehouseOutwardVO> composeVOList(List<String> availableQtyList, List<String> orderedQtyList,
			List<String> stockIdList) {
		List<WarehouseOutwardVO> list = new ArrayList<WarehouseOutwardVO>();
		Integer size = stockIdList.size();
		log.info("stockIdList" + size);
		for (int i = 0; i < size; i++) {
			String stockIdstr = stockIdList.get(i);
			String orderedQtystr = orderedQtyList.get(i);
			String availableQtystr = availableQtyList.get(i);

			String[] stockIdArr = stockIdstr.split(",");
			String[] orderedQtyArr = orderedQtystr.split(",");
			String[] availableQtyArr = availableQtystr.split(",");

			if (stockIdArr != null) {
				Integer noOfStockRecords = stockIdArr.length;

				for (int j = 0; j < noOfStockRecords; j++) {
					WarehouseOutwardVO vo = new WarehouseOutwardVO();
					log.info(availableQtyArr[j]);
					log.info(orderedQtyArr[j]);
					log.info(stockIdArr[j]);

					if (NumberUtils.isNumber(availableQtyArr[j]) && NumberUtils.isNumber(availableQtyArr[j])
							&& NumberUtils.isNumber(stockIdArr[j])) {

						vo.setAvailableQty(Integer.parseInt(availableQtyArr[j]));
						vo.setOrderedQty(Integer.parseInt(orderedQtyArr[j]));
						vo.setStockId(Integer.parseInt(stockIdArr[j]));
						list.add(vo);

					} else {
						log.info("Some thing is not a number.");
					}

				}
			}
		}

		// TODO Auto-generated method stub
		return list;
	}

	private List<WarehouseOutwardVO2> composeVOOutwardList(List<String> subQty, List<String> qty) {
		List<WarehouseOutwardVO2> list = new ArrayList<WarehouseOutwardVO2>();
		Integer size = subQty.size();
		log.info("subQty" + size);
		for (int i = 0; i < size; i++) {
			String subQtystr = subQty.get(i);
			String qtystr = qty.get(i);

			String[] subQtyArr = subQtystr.split(",");
			String[] qtyArr = qtystr.split(",");

			if (subQtyArr != null) {
				Integer noOfRecords = subQtyArr.length;

				for (int j = 0; j < noOfRecords; j++) {
					WarehouseOutwardVO2 vo = new WarehouseOutwardVO2();

					if (NumberUtils.isNumber(subQtyArr[j])) {

						vo.setSubQty(Integer.parseInt(subQtyArr[j]));
						vo.setQty(Integer.parseInt(qtyArr[j]));

						list.add(vo);

					} else {
						log.info("Some thing is not a number.");
					}

				}
			}
		}

		// TODO Auto-generated method stub
		return list;
	}
}
