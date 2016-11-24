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

				warehouseOutwardForm = impl.addWarehouseOutwardTempData(warehouseOutwardForm, userInfoVO);

				String flag = null;
				Integer qtyAvailable = null;
				List<String> availableQty = Arrays.<String> asList(warehouseOutwardForm.getAvailableQty());
				List<String> subQty = Arrays.<String> asList(warehouseOutwardForm.getSubQty());
				List<String> stockId = Arrays.<String> asList(warehouseOutwardForm.getStockId());
				List<Integer> qty = Arrays.<Integer> asList(warehouseOutwardForm.getQty());

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
					vo.setAvailableQty(Integer.parseInt(availableQtyArr[j]));
					vo.setOrderedQty(Integer.parseInt(orderedQtyArr[j]));
					vo.setStockId(Integer.parseInt(stockIdArr[j]));
					list.add(vo);
				}
			}
		}

		// TODO Auto-generated method stub
		return list;
	}
}
