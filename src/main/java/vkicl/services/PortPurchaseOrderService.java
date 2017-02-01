package vkicl.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import vkicl.daoImpl.PortDaoImpl;
import vkicl.daoImpl.PortInwardDaoImpl;
import vkicl.daoImpl.PortInwardOutwardIntersectionDaoImpl;
import vkicl.daoImpl.PortOutwardDaoImpl;
import vkicl.daoImpl.PortOutwardShipmentDaoImpl;
import vkicl.daoImpl.PortPurchaseOrderDaoImpl;
import vkicl.form.PortInwardForm;
import vkicl.form.PortPurchaseDeliveryNoteForm;
import vkicl.vo.PortInwardDetailsVO;
import vkicl.vo.PortInwardRecordVO;
import vkicl.vo.PortOutwardPostDataContainerVO;
import vkicl.vo.PortOutwardRecordVO;
import vkicl.vo.PortPurchaseOrderLineItemVO;
import vkicl.vo.PortPurchaseOrderPostDataContainerVO;
import vkicl.vo.PortPurchaseOrderSavingStatusVO;
import vkicl.vo.PortPurchaseOrderVO;
import vkicl.vo.UserInfoVO;

public class PortPurchaseOrderService {

	private Logger logger = Logger.getLogger(PortPurchaseOrderService.class);

	public void processPortPurchaseOrderEntries(PortPurchaseOrderPostDataContainerVO portPurchaseOrder,
			HttpServletRequest request, UserInfoVO userInfo) throws Exception {

		PortPurchaseOrderSavingStatusVO status = new PortPurchaseOrderSavingStatusVO();
		Long portPurchaseOrderId = -1L;
		Long warehouseInwardId = -1L;
		Long stockBalId = -1L;
		String ppoLineItemsString = portPurchaseOrder.getSelectedPortInventoryItemsJson();

		Gson gson = new Gson();

		List<PortPurchaseOrderLineItemVO> portPurchaseOrderToBeSaved = gson.fromJson(ppoLineItemsString,
				new TypeToken<List<PortPurchaseOrderLineItemVO>>() {
				}.getType());

		PortPurchaseOrderDaoImpl portPurchaseOrderDaoImpl = new PortPurchaseOrderDaoImpl();
		portPurchaseOrderId = portPurchaseOrderDaoImpl.addPortPurchaseOrderData(portPurchaseOrder, userInfo);

		for (PortPurchaseOrderLineItemVO lineItem : portPurchaseOrderToBeSaved) {
			logger.info(lineItem);
			// Save Port Outward Records with Port outward shipment id
			// Get the port outward id
			portPurchaseOrderDaoImpl.addPortPurchaseLineItemData(lineItem, portPurchaseOrderId, userInfo);
		}

		status.setPortPurchaseOrderId(portPurchaseOrderId);
		status.setStatus("success");
		// return status;
	}

	public PortPurchaseOrderVO getPPODetailsById(Integer purchaseOrderNo) {
		PortPurchaseOrderVO vo = null;
		PortPurchaseOrderDaoImpl dao = new PortPurchaseOrderDaoImpl();

		try {
			vo = dao.getPPODetailsById(purchaseOrderNo);
		} catch (SQLException e) {
			logger.error("Some error", e);
		}
		return vo;
	}

	public List<PortPurchaseOrderLineItemVO> fetchPPOLineItems(Integer purchaseOrderNo, UserInfoVO userInfo)
			throws SQLException {
		PortPurchaseOrderDaoImpl dao = new PortPurchaseOrderDaoImpl();
		List<PortPurchaseOrderLineItemVO> records = dao.fetchPPOLineItems(purchaseOrderNo, userInfo);
		return records;
	}

	public void processDeliveryNote(PortPurchaseDeliveryNoteForm form, UserInfoVO userInfoVO) throws SQLException {

		PortPurchaseOrderDaoImpl impl = new PortPurchaseOrderDaoImpl();
		Long deliveryNoteId = impl.addPortPurchaseDeliveryData(form, userInfoVO);

		List<PortPurchaseOrderLineItemVO> list = toList(form, userInfoVO);

		for (PortPurchaseOrderLineItemVO vo : list) {
			impl.addPortPurchaseDeliveryLineItemsData(vo, deliveryNoteId, userInfoVO);

			// impl.updateOrderQty(form.getPpoNo(), vo, userInfoVO);
		}
	}

	public List<PortPurchaseOrderLineItemVO> toList(PortPurchaseDeliveryNoteForm form, UserInfoVO user) {
		List<PortPurchaseOrderLineItemVO> list = new ArrayList<PortPurchaseOrderLineItemVO>();

		if (form.getDeliveryQuantity() != null) {
			int recordCount = form.getDeliveryQuantity().length;

			Integer[] ppoLineItems = form.getPpoLineitemNo();
			Integer[] deliveryQty = form.getDeliveryQuantity();
			Integer[] orderedQty = form.getOrderedQuantity();

			for (int i = 0; i < recordCount; i++) {
				if (deliveryQty[i] == 0) {
					logger.debug("Ignored empty row");
				} else {

					PortPurchaseOrderLineItemVO vo = new PortPurchaseOrderLineItemVO();

					vo.setPpoLineItemNo(ppoLineItems[i]);
					vo.setDeliveryQuantity(deliveryQty[i]);
					vo.setOrderedQuantity(orderedQty[i]);

					list.add(vo);
				}

			}
		}

		return list;
	}
}
