package vkicl.services;

import java.awt.Shape;
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
import vkicl.form.PortInwardForm;
import vkicl.form.StockForm;
import vkicl.report.bean.PortOutwardBean2;
import vkicl.report.form.StockReportForm;
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
	
	
	public void processForm(StockForm form, UserInfoVO user) throws SQLException{


		StockBalDaoImpl impl = new StockBalDaoImpl();
		List<StockBalanceDetailsVO> list = toList(form, user);
		if(null!=list && !list.isEmpty()){
			
			//Move any existing records to port_inward_details_deleted table.
			//impl.moveToDeleted(form.getStock_Bal_id(), user);
			//Now delete the records from port_inward_details table
			//impl.deletePortInwardDetailsByPortInwardId(form.getStock_Bal_id()());
			//logger.debug("Deleted old records");
			
			//Now insert the new PortInwardDetailsVO in database
			for(StockBalanceDetailsVO vo : list){
				Long stockBalId= impl.insertStockBalanceCuttingDetails(vo, user);
				double orginx=0;
				double orginy=0;
				double length=vo.getLength();
		    	double width=vo.getWidth();
		    	
		    	vkicl.services.geometry.GeometryServiceImpl goemetry=new vkicl.services.geometry.GeometryServiceImpl();
		    	Shape shapeObj= goemetry.toPolygon(orginx, orginy,length, width);
		    	
		    	double area= length * width;
		    	
		    	String Sql=goemetry.toUpdateSql(shapeObj,stockBalId, area);
		    	impl.updateStockBalanceShape(Sql);
		    	
		    	impl.updateStockBalanceCut(vo.getStockBalId(), user);

			}
		}
		
		
	}
		
		public List<StockBalanceDetailsVO> toList(StockForm form, UserInfoVO user) {
			List<StockBalanceDetailsVO> list = new ArrayList<StockBalanceDetailsVO>();
			Integer StockBalId = form.getStock_Bal_id();
			//if (form.getThickness() != null) {
				//int recordCount = form.getThickness().length;
				//Double[] thickness = form.getThickness();
				//Integer[] width = form.getWidth();
				//Integer[] length = form.getLength();
			
				//for (int i = 0; i < recordCount; i++) {
				//	if (thickness[i] == 0d && width[i] == 0 && length[i] == 0)  {
				//		logger.debug("Ignored empty row");
				//	} else {

						StockBalanceDetailsVO vo = new StockBalanceDetailsVO();
						vo.setThickness(form.getThickness());
						vo.setWidth(form.getWidth());
						vo.setLength(form.getLength());
						vo.setGrade(form.getGrade());
						vo.setMaterialType(form.getMaterialType());
						vo.setMake(form.getMake());
						vo.setMillName(form.getMillName());
						vo.setStockBalId(form.getStock_Bal_id());
						//vo.setQuantity(qty[i]);
						//vo.setBe_weight(actualWt[i]);
//						vo.setBe_wt_unit(actualWtUnit[i]); //As explained by client, it will always be TON
						//vo.setBe_wt_unit("TON");
						//vo.setPort_inward_id(portInwardId);
						//vo.setUpdate_ui(user.getUserName());
						//vo.setCreate_ui(user.getUserName());
						//vo.setCreate_ts(new Date());
						//vo.setUpdate_ts(new Date());
						
						list.add(vo);
					//}

				//}
			//}

			return list;
		}
	
	
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
