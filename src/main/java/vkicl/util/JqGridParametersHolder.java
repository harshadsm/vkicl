package vkicl.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.gson.JsonParseException;

public class JqGridParametersHolder {

	private Logger logger = Logger.getLogger(JqGridParametersHolder.class);

	/**
	 * sidx:id
page:1
rows:10
_search:false
sord:desc
nd:1375633550130
	 */
	
	private HttpServletRequest req;
	
	public JqGridParametersHolder(HttpServletRequest request){
		this.req = request;
	}
	
	/**
	 * 
	 * @param paramName
	 * @return
	 */
	public String getParam(JQGRID_PARAM_NAMES paramName){
		return req.getParameter(paramName.toString());
	}

	public String getParam(String paramName){
		return req.getParameter(paramName.toString());
	}
	
	public enum JQGRID_PARAM_NAMES{
		
		sidx("sidx"),//get index row - i.e. user click to sort
		page("page"),//get the requested page
		rows("rows"),//get how many rows we want to have into the grid
		search("_search"),
		sord("sord"),//get the direction : DESC or ASC
		nd("nd"),
		filters("filters");
		
		private String PARAM_NAME;
		
		JQGRID_PARAM_NAMES(String param_name){
			this.PARAM_NAME = param_name;
		}
		
		public String toString(){
			return PARAM_NAME;
		}
	}
	
//	public JqGridSearchParameterHolder parseSerachFilters(JqGridParametersHolder params)
//			throws JsonParseException, JsonMappingException, IOException {
//		JqGridSearchParameterHolder searchParam = null;
//		String filters = params.getParam(JQGRID_PARAM_NAMES.filters);
//
//		if (filters != null) {
//			ObjectMapper mapper = new ObjectMapper();
//			searchParam = mapper.readValue(filters,
//					JqGridSearchParameterHolder.class);
//			logger.info(searchParam);
//		}
//		return searchParam;
//	}
	
}
