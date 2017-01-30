package vkicl.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import vkicl.daoImpl.PortDaoImpl;
import vkicl.daoImpl.PortInwardOutwardIntersectionDaoImpl;
import vkicl.daoImpl.PortOutwardDaoImpl;
import vkicl.daoImpl.PortOutwardShipmentDaoImpl;
import vkicl.daoImpl.PortPurchaseOrderDaoImpl;
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
}