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
import vkicl.vo.PortOutwardPostDataContainerVO;
import vkicl.vo.PortOutwardRecordVO;
import vkicl.vo.UserInfoVO;

public class PortOutwardService {

	private Logger logger = Logger.getLogger(PortOutwardService.class);

	public void processPortOutwardEntries(PortOutwardPostDataContainerVO postDataContainer,
			HttpServletRequest request, UserInfoVO userInfo) throws Exception {

		String genericListener = null;

		String itemsToSaveJson = postDataContainer.getSelectedPortInventoryItemsJson();

		Gson gson = new Gson();

		List<PortOutwardRecordVO> portOutwardRecordsToBeSaved = gson.fromJson(itemsToSaveJson,
				new TypeToken<List<PortOutwardRecordVO>>() {
				}.getType());
		

		
		PortOutwardShipmentDaoImpl portOutwardShipmentDaoImpl = new PortOutwardShipmentDaoImpl();
		
		// portOutwardForm = impl.addPortOutwardData(portOutwardForm,
		// userInfoVO);
		
		//Save Port Outward Shipment and get the Port outward shipment id
		Integer portOutwardShipmentId = portOutwardShipmentDaoImpl.savePortOutwardShipment(postDataContainer, userInfo);
		
		
		PortOutwardDaoImpl portOutwardDaoImpl = new PortOutwardDaoImpl();
		PortInwardOutwardIntersectionDaoImpl portInOutIntersectionDaoImpl = new PortInwardOutwardIntersectionDaoImpl();
		for (PortOutwardRecordVO vo : portOutwardRecordsToBeSaved) {
			logger.info(vo);
			//Save Port Outward Records with Port outward shipment id
			//Get the port outward id
			
			Integer portOutwardId = portOutwardDaoImpl.savePortOutward(vo,portOutwardShipmentId, userInfo);
			
			//Save a record in intersection table
			portInOutIntersectionDaoImpl.save(vo.getPortInwardId(), vo.getPortInwardDetailId(), portOutwardId);
			
		}
		
		

	}


}
