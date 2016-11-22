package vkicl.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class WarehouseOutwardAction extends BaseAction {
	private static Logger log = Logger.getLogger(WarehouseOutwardAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
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

				// warehouseOutwardForm = impl.addWarehouseOutwardTempData(
				// warehouseOutwardForm, userInfoVO);

				String flag = null;
				Integer qtyAvailable = null;

				Integer strSubQty = null;
				Integer strStockId = null;

				List<String> availableQty = Arrays.<String> asList(warehouseOutwardForm.getAvailableQty());
				List<String> subQty = Arrays.<String> asList(warehouseOutwardForm.getSubQty());
				List<String> stockId = Arrays.<String> asList(warehouseOutwardForm.getStockId());
				List<Integer> qty = Arrays.<Integer> asList(warehouseOutwardForm.getQty());
				log.info(availableQty.size());
				log.info(subQty.size());
				log.info(stockId.size());
				log.info(qty.size());

				List<WarehouseOutwardVO> warehouseVOList = composeVOList(availableQty, subQty, stockId);

				for (String vosubQty : subQty) {
					String[] stringArraySubQty = vosubQty.split(",");
					for (String element : stringArraySubQty) {
						strSubQty = Integer.parseInt(element);

						log.info("vosubqty" + element);
					}

				}

				for (String vostockId : stockId) {
					String[] stringArrayStockId = vostockId.split(",");
					for (String element : stringArrayStockId) {
						strStockId = Integer.parseInt(element);
						log.info("stockId" + element);
					}

				}

				for (String voavailableQty : availableQty) {
					String[] stringArrayAvailableQty = voavailableQty.split(",");
					for (String element : stringArrayAvailableQty) {
						qtyAvailable = Integer.parseInt(element) - strSubQty;
						log.info("availableQty" + element);

						if (qtyAvailable == 0) {
							flag = "1";
							impl.updateStockBalanceData(warehouseOutwardForm, userInfoVO, qtyAvailable, strStockId,
									flag);
						} else {
							flag = "2";
							impl.updateStockBalanceData(warehouseOutwardForm, userInfoVO, qtyAvailable, strStockId,
									flag);
						}
					}

				}
				impl.addStockOutwardData(warehouseOutwardForm, userInfoVO);
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

	private List<WarehouseOutwardVO> composeVOList(List<String> availableQtyList, List<String> orderedQtyList,
			List<String> stockIdList) {

		Integer size = stockIdList.size();
		log.info("stockIdList" + size);
		for (int i = 0; i < size; i++) {
			String stockIdstr = stockIdList.get(i);
			String orderedQtystr = orderedQtyList.get(i);
			String availableQtystr = availableQtyList.get(i);

		}

		// TODO Auto-generated method stub
		return null;
	}
}
