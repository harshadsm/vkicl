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
import vkicl.daoImpl.StockBalDaoImpl;
import vkicl.report.bean.WarehouseDispatchDetailsBean;
import vkicl.util.JqGridCustomResponse;
import vkicl.util.JqGridParametersHolder;
import vkicl.util.JqGridParametersHolder.JQGRID_PARAM_NAMES;
import vkicl.util.JqGridSearchParameterHolder;
import vkicl.util.JqGridSearchParameterHolder.Rule;
import vkicl.vo.StockBalanceDetailsVO;

public class RelevantStockJsonService {
	
	private Logger logger = Logger.getLogger(RelevantStockJsonService.class);
	
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
	
	public String getRelevantStockAsJson(HttpServletRequest req)
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
		
		putSearchParamsPerOrderedPlateSpecs(searchParam, req);
		
//		searchParam.getRules().add(portInwardIdRule);

		String rows = params.getParam(JQGRID_PARAM_NAMES.rows);
		String page = params.getParam(JQGRID_PARAM_NAMES.page);
		String orderBy = params.getParam(JQGRID_PARAM_NAMES.sidx);
		String order = params.getParam(JQGRID_PARAM_NAMES.sord);

		//DispatchOrderDetailsDaoImpl dispatchOrderDetailsDao = new DispatchOrderDetailsDaoImpl();
		StockBalDaoImpl stockDao = new StockBalDaoImpl();
		Integer totalRecordsCount = stockDao.fetchStockBalRecordCount(searchParam);
		
		List<StockBalanceDetailsVO> outrecords = stockDao.fetchStockBalList( Integer.parseInt(page),
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

	private void putSearchParamsPerOrderedPlateSpecs(JqGridSearchParameterHolder searchParam, HttpServletRequest req) {
		String thickness = req.getParameter("thickness");
		String width = req.getParameter("width");
		String length = req.getParameter("length");
		String mill = req.getParameter("mill");
		String make = req.getParameter("make");
		String grade = req.getParameter("grade");
		
		Rule thicknessRule = new Rule();
		thicknessRule.setData(thickness);
		thicknessRule.setField("thickness");
		thicknessRule.setOp("eq");
		
		Rule widthRule = new Rule();
		widthRule.setData(width);
		widthRule.setField("width");
		widthRule.setOp("ge");
		
		Rule lengthRule = new Rule();
		lengthRule.setData(length);
		lengthRule.setField("length");
		lengthRule.setOp("eq");
		
		Rule millRule = new Rule();
		millRule.setData(mill);
		millRule.setField("mill");
		millRule.setOp("ge");
		
		Rule makeRule = new Rule();
		makeRule.setData(make);
		makeRule.setField("make");
		makeRule.setOp("eq");
		
		
		Rule gradeRule = new Rule();
		gradeRule.setData(grade);
		gradeRule.setField("grade");
		gradeRule.setOp("eq");
		
		
		
		
		if(searchParam == null){
			searchParam = new JqGridSearchParameterHolder();
		}
		if(searchParam.getRules()==null){
			List<Rule> rules = new ArrayList<Rule>();
			searchParam.setRules(rules);
		}
		
		searchParam.getRules().add(thicknessRule);
		if(lengthRule.getData()!=null){
			searchParam.getRules().add(lengthRule);	
		}
		if(widthRule.getData()!=null){
		searchParam.getRules().add(widthRule);
		}
		if(gradeRule.getData()!=null){
			searchParam.getRules().add(gradeRule);	
		}
		if(millRule.getData()!=null){
			searchParam.getRules().add(millRule);
		}
		if(makeRule.getData()!=null){
			searchParam.getRules().add(makeRule);	
		}
		
		
		
	}


	
	

}
