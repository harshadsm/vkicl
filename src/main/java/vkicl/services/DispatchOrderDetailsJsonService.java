package vkicl.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;

import vkicl.daoImpl.DispatchOrderDetailsDaoImpl;
import vkicl.report.bean.WarehouseDispatchDetailsBean;
import vkicl.util.JqGridCustomResponse;
import vkicl.util.JqGridParametersHolder;
import vkicl.util.JqGridParametersHolder.JQGRID_PARAM_NAMES;
import vkicl.util.JqGridSearchParameterHolder;

public class DispatchOrderDetailsJsonService {
	
	private Logger logger = Logger.getLogger(DispatchOrderDetailsJsonService.class);
	
	private JqGridSearchParameterHolder parseSerachFilters(JqGridParametersHolder params)
			throws JsonParseException, JsonMappingException, IOException {
		JqGridSearchParameterHolder searchParam = null;
		String filters = params.getParam(JQGRID_PARAM_NAMES.filters);

		if (filters != null) {
			ObjectMapper mapper = new ObjectMapper();
			searchParam = mapper.readValue(filters,
					JqGridSearchParameterHolder.class);
			logger.info(searchParam);
		}
		return searchParam;
	}
	
	public String getDispatchOrderDetailsAsJson(HttpServletRequest req)
			throws Exception {
		String dispatchOrderIdStr = req.getParameter("dispatchOrderId");
		Integer dispatchOrderId = Integer.parseInt(dispatchOrderIdStr);
		logger.debug("dispatchOrderId = "+dispatchOrderId);
		
//		String portInwardIdStr = req.getParameter("port_inward_database_id");
//		logger.info("Port Inward Id = " + portInwardIdStr);
//		Integer portInwardId = Integer.parseInt(portInwardIdStr);
//		JqGridSearchParameterHolder.Rule portInwardIdRule = new JqGridSearchParameterHolder.Rule();
//		portInwardIdRule.setField("port_inward_id");
//		portInwardIdRule.setOp("eq");
//		portInwardIdRule.setData(portInwardIdStr);

		JqGridParametersHolder params = new JqGridParametersHolder(req);
		JqGridSearchParameterHolder searchParam = parseSerachFilters(params);
		
		if(searchParam==null){
			searchParam = new JqGridSearchParameterHolder();
		}
		if(searchParam.getRules() == null){
			List<JqGridSearchParameterHolder.Rule> rules = new ArrayList<JqGridSearchParameterHolder.Rule>();
			searchParam.setRules(rules);
		}
//		searchParam.getRules().add(portInwardIdRule);

		String rows = params.getParam(JQGRID_PARAM_NAMES.rows);
		String page = params.getParam(JQGRID_PARAM_NAMES.page);
		String orderBy = params.getParam(JQGRID_PARAM_NAMES.sidx);
		String order = params.getParam(JQGRID_PARAM_NAMES.sord);

		DispatchOrderDetailsDaoImpl dispatchOrderDetailsDao = new DispatchOrderDetailsDaoImpl();
		Integer totalRecordsCount = dispatchOrderDetailsDao.getDispatchOrderDetailsCount(dispatchOrderId,searchParam);//, portInwardId);
		
		List<WarehouseDispatchDetailsBean> outrecords = dispatchOrderDetailsDao.getDispatchOrderDetails( Integer.parseInt(page),
				Integer.parseInt(rows), totalRecordsCount, orderBy, order, searchParam, dispatchOrderId);
		
		
		//updateAlreadyOutQuantity(outrecords);

		JqGridCustomResponse response = new JqGridCustomResponse();
		response.setPage(page);
		response.setRows(outrecords);
		response.setRecords(totalRecordsCount.toString());
		response.setTotal((totalRecordsCount / Long.valueOf(rows)) + 1 + "");
		Gson gson = new Gson();
		String json = gson.toJson(response);
		return json;
	}


	
	

}
