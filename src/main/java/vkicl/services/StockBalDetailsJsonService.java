package vkicl.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;

import vkicl.daoImpl.PortDaoImpl;
import vkicl.daoImpl.PortInwardOutwardIntersectionDaoImpl;
import vkicl.daoImpl.PortInwardPackingListDaoImpl;
import vkicl.daoImpl.PortOutwardDaoImpl;
import vkicl.daoImpl.PortOutwardPackingListDaoImpl;
import vkicl.daoImpl.StockBalDaoImpl;
import vkicl.report.bean.PortOutwardBean2;
import vkicl.util.Constants;
import vkicl.util.JqGridCustomResponse;
import vkicl.util.JqGridParametersHolder;
import vkicl.util.JqGridParametersHolder.JQGRID_PARAM_NAMES;
import vkicl.util.JqGridSearchParameterHolder;
import vkicl.vo.LocationDetailsVO;
import vkicl.vo.PackingListItemVO;
import vkicl.vo.PortInwardRecordVO;
import vkicl.vo.UserInfoVO;

public class StockBalDetailsJsonService {
	
	private Logger logger = Logger.getLogger(StockBalDetailsJsonService.class);
	
	

	
	public String getStockBalListAsJson(HttpServletRequest req)
			throws Exception {
		
//		String portInwardIdStr = req.getParameter("port_inward_database_id");
//		logger.info("Port Inward Id = " + portInwardIdStr);
//		Integer portInwardId = Integer.parseInt(portInwardIdStr);
//		JqGridSearchParameterHolder.Rule portInwardIdRule = new JqGridSearchParameterHolder.Rule();
//		portInwardIdRule.setField("port_inward_id");
//		portInwardIdRule.setOp("eq");
//		portInwardIdRule.setData(portInwardIdStr);

		JqGridParametersHolder params = new JqGridParametersHolder(req);
		//JqGridSearchParameterHolder searchParam = parseSerachFilters(params);
		
		//if(searchParam==null){
		//	searchParam = new JqGridSearchParameterHolder();
		//}
		//if(searchParam.getRules() == null){
		//	List<JqGridSearchParameterHolder.Rule> rules = new ArrayList<JqGridSearchParameterHolder.Rule>();
		//	searchParam.setRules(rules);
	//	}
//		//searchParam.getRules().add(portInwardIdRule);

	   //String id = params.getParam(JQGRID_PARAM_NAMES.id);
		//String name = params.getParam(JQGRID_PARAM_NAMES.name);
		//String orderBy = params.getParam(JQGRID_PARAM_NAMES.sidx);
		//String order = params.getParam(JQGRID_PARAM_NAMES.sord);
	


		
		Map<String, String[]> map = req.getParameterMap();
		UserInfoVO userInfoVO = (UserInfoVO) req.getSession()
				.getAttribute(Constants.USER_INFO_SESSION);

		StockBalDaoImpl portDao = new StockBalDaoImpl();
		//Integer totalRecordsCount = portDao.fetchPortOutwardPackingListRecordCount(searchParam);//, portInwardId);
		
		portDao.updateStockBalance(map, userInfoVO);
		
		
		//updateAlreadyOutQuantity(outrecords);

		JqGridCustomResponse response = new JqGridCustomResponse();
		
		Gson gson = new Gson();
		String json = gson.toJson(response);
		return json;
	}


	
	

}
