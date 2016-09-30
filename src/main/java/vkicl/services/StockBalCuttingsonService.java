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
import vkicl.daoImpl.PortInwardDaoImpl;
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
import vkicl.vo.StockBalanceDetailsVO;
import vkicl.vo.StockBalanceDetailsVO;
import vkicl.vo.UserInfoVO;

public class StockBalCuttingsonService {
	
	private Logger logger = Logger.getLogger(StockBalCuttingsonService.class);
	
	

	
	public String getStockBalCuttingListAsJson(HttpServletRequest req)
			throws Exception {
		

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

		StockBalDaoImpl portDao = new StockBalDaoImpl();
		Integer totalRecordsCount = portDao.fetchStockBalRecordCount(searchParam);//, portInwardId);
		
		List<StockBalanceDetailsVO> outrecords = portDao.fetchStockBalList( Integer.parseInt(page),
				Integer.parseInt(rows), totalRecordsCount, orderBy, order, searchParam);
		
		
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
	
	public StockBalanceDetailsVO getCuttingDetailsById(String idString) {
		StockBalanceDetailsVO vo = null;
		StockBalDaoImpl dao = new StockBalDaoImpl();
		Integer id = Integer.parseInt(idString);
		try {
			vo = dao.fetchStockBalById(id);
		} catch (SQLException e) {
			logger.error("Some error",e);
		}
		return vo;
	}
}
