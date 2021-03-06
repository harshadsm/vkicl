package vkicl.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;

import vkicl.daoImpl.PortOutwardShipmentDaoImpl;
import vkicl.daoImpl.WarehouseOutwardDaoImpl;
import vkicl.daoImpl.WarehouseOutwardTempDaoImpl;
import vkicl.util.JqGridCustomResponse;
import vkicl.util.JqGridParametersHolder;
import vkicl.util.JqGridSearchParameterHolder;
import vkicl.util.JqGridParametersHolder.JQGRID_PARAM_NAMES;
import vkicl.vo.PortOutwardShipmentVO;
import vkicl.vo.WarehouseOutwardVO4;

public class WarehouseOutwardService {
	private Logger logger = Logger.getLogger(WarehouseOutwardService.class);
	
	public String getWarehouseOutwardListAsJson(HttpServletRequest req)
			throws SQLException, JsonParseException, JsonMappingException, IOException {

		// String dd = req.getParameter("getPortInwardDetails");

		JqGridParametersHolder params = new JqGridParametersHolder(req);
		JqGridSearchParameterHolder searchParam = parseSerachFilters(params);

		String rows = params.getParam(JQGRID_PARAM_NAMES.rows);
		String page = params.getParam(JQGRID_PARAM_NAMES.page);
		String orderBy = params.getParam(JQGRID_PARAM_NAMES.sidx);
		String order = params.getParam(JQGRID_PARAM_NAMES.sord);
		
		WarehouseOutwardDaoImpl warehouseOutwardDaoImpl = new WarehouseOutwardDaoImpl();
		
		Integer totalRecordsCount = warehouseOutwardDaoImpl.fetchWarehouseOutwardRecordCount(orderBy, order, searchParam);
		List<WarehouseOutwardVO4> records = warehouseOutwardDaoImpl.fetchWarehouseOutwardList(Integer.parseInt(page),
				Integer.parseInt(rows), totalRecordsCount, orderBy, order, searchParam);
		

		JqGridCustomResponse response = new JqGridCustomResponse();
		response.setPage(page);
		response.setRows(records);
		response.setRecords(totalRecordsCount.toString());
		
		Long noOfPages = totalRecordsCount / Long.valueOf(rows);
		Long remainder = totalRecordsCount % Long.valueOf(rows);
		if(remainder > 0){
			noOfPages = noOfPages + 1L;
		}
		
//		response.setTotal((totalRecordsCount / Long.valueOf(rows)) + 1 + "");
		response.setTotal(noOfPages + "");
		
		Gson gson = new Gson();
		String json = gson.toJson(response);
		return json;
	}
	
	private JqGridSearchParameterHolder parseSerachFilters(JqGridParametersHolder params)
			throws JsonParseException, JsonMappingException, IOException {
		JqGridSearchParameterHolder searchParam = null;
		String filters = params.getParam(JQGRID_PARAM_NAMES.filters);

		if (filters != null) {
			ObjectMapper mapper = new ObjectMapper();
			searchParam = mapper.readValue(filters, JqGridSearchParameterHolder.class);
			logger.info(searchParam);
		}
		return searchParam;
	}

	public void updateActualWeightOfPortOutwardShipment(Integer id, Double actualWeight) {
		logger.debug("Going to update warehouse_outward.id = "+id);
		logger.debug("Going to update actualWeight = "+actualWeight);
		
		WarehouseOutwardDaoImpl warehouseOutwardDaoImpl = new WarehouseOutwardDaoImpl();
		warehouseOutwardDaoImpl.updateActualWeightOfWarehouseOutward(id, actualWeight);
	}

	public void distributeActualWeightPerPlate(Integer warehouseOutwardId, Double actualWeight) {
		WarehouseOutwardTempDaoImpl warehouseOutwardTempDaoImpl = new WarehouseOutwardTempDaoImpl();
		warehouseOutwardTempDaoImpl.distributeActualWeightPerPlate(warehouseOutwardId,  actualWeight);
		
	}
}
