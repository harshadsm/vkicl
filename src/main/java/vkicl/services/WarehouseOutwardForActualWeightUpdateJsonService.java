package vkicl.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import vkicl.daoImpl.WarehouseOutwardDaoImpl;
import vkicl.daoImpl.WarehouseOutwardForActualWeightUpdateDaoImpl;
import vkicl.report.bean.PortOutwardBean2;
import vkicl.util.JqGridCustomResponse;
import vkicl.util.JqGridParametersHolder;
import vkicl.util.JqGridParametersHolder.JQGRID_PARAM_NAMES;
import vkicl.util.JqGridSearchParameterHolder;
import vkicl.util.JqGridSearchParameterHolder.Rule;
import vkicl.vo.PackingListItemVO;
import vkicl.vo.PackingListItemVO2;
import vkicl.vo.PortInwardRecordVO;
import vkicl.vo.WarehouseOutwardReportVO;
import vkicl.vo.WarehouseOutwardTempVO;
import vkicl.vo.WarehouseOutwardVO3;

public class WarehouseOutwardForActualWeightUpdateJsonService {

	private Logger logger = Logger.getLogger(WarehouseOutwardForActualWeightUpdateJsonService.class);

	public String getWarehouseOutwardListAsJson(HttpServletRequest req)
			throws SQLException, JsonParseException, JsonMappingException, IOException {

		// String dd = req.getParameter("getPortInwardDetails");

		JqGridParametersHolder params = new JqGridParametersHolder(req);
		JqGridSearchParameterHolder searchParam = parseSerachFilters(params);

		String rows = params.getParam(JQGRID_PARAM_NAMES.rows);
		String page = params.getParam(JQGRID_PARAM_NAMES.page);
		String orderBy = params.getParam(JQGRID_PARAM_NAMES.sidx);
		String order = params.getParam(JQGRID_PARAM_NAMES.sord);

		WarehouseOutwardForActualWeightUpdateDaoImpl warehouseOutwardDao = new WarehouseOutwardForActualWeightUpdateDaoImpl();

		Integer totalRecordsCount = warehouseOutwardDao.fetchWarehouseOutwardRecordCount(orderBy, order, searchParam);
		List<WarehouseOutwardVO3> records = warehouseOutwardDao.fetchWarehouseOutward(Integer.parseInt(page),
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

	private JqGridSearchParameterHolder parseSerachFilters2(JqGridParametersHolder params, HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException {
		JqGridSearchParameterHolder searchParam = null;
		String filters = params.getParam(JQGRID_PARAM_NAMES.filters);

		if (filters != null) {
			ObjectMapper mapper = new ObjectMapper();
			searchParam = mapper.readValue(filters, JqGridSearchParameterHolder.class);
			logger.info(searchParam);
		}

		String portInwardIdStr = request.getParameter("portInwardId");
		Rule portInwardIdRule = new Rule();
		portInwardIdRule.setField("port_inward_id");
		portInwardIdRule.setData(portInwardIdStr);
		portInwardIdRule.setOp("eq");
		if (searchParam == null) {
			searchParam = new JqGridSearchParameterHolder();
		}

		if (searchParam.getRules() == null) {
			searchParam.setRules(new ArrayList<Rule>());
		}
		searchParam.getRules().add(portInwardIdRule);
		return searchParam;
	}

	public String getPackingListAsJson(HttpServletRequest req) throws Exception {

		// String portInwardIdStr = req.getParameter("port_inward_database_id");
		// logger.info("Port Inward Id = " + portInwardIdStr);
		// Integer portInwardId = Integer.parseInt(portInwardIdStr);
		// JqGridSearchParameterHolder.Rule portInwardIdRule = new
		// JqGridSearchParameterHolder.Rule();
		// portInwardIdRule.setField("port_inward_id");
		// portInwardIdRule.setOp("eq");
		// portInwardIdRule.setData(portInwardIdStr);

		JqGridParametersHolder params = new JqGridParametersHolder(req);
		JqGridSearchParameterHolder searchParam = parseSerachFilters(params);

		if (searchParam == null) {
			searchParam = new JqGridSearchParameterHolder();
		}
		if (searchParam.getRules() == null) {
			List<JqGridSearchParameterHolder.Rule> rules = new ArrayList<JqGridSearchParameterHolder.Rule>();
			searchParam.setRules(rules);
		}
		// searchParam.getRules().add(portInwardIdRule);

		String rows = params.getParam(JQGRID_PARAM_NAMES.rows);
		String page = params.getParam(JQGRID_PARAM_NAMES.page);
		String orderBy = params.getParam(JQGRID_PARAM_NAMES.sidx);
		String order = params.getParam(JQGRID_PARAM_NAMES.sord);

		PortInwardPackingListDaoImpl portDao = new PortInwardPackingListDaoImpl();
		Integer totalRecordsCount = portDao.fetchPortInwardPackingListRecordCount(searchParam);// ,
																								// portInwardId);
		List<PackingListItemVO> records = portDao.fetchPortInwardPackingList(Integer.parseInt(page),
				Integer.parseInt(rows), totalRecordsCount, orderBy, order, searchParam);

		records = updateAlreadyOutQuantity(records);

		JqGridCustomResponse response = new JqGridCustomResponse();
		response.setPage(page);
		response.setRows(records);
		response.setRecords(totalRecordsCount.toString());
		response.setTotal((totalRecordsCount / Long.valueOf(rows)) + 1 + "");
		Gson gson = new Gson();
		String json = gson.toJson(response);
		return json;
	}

	private List<PackingListItemVO> updateAlreadyOutQuantity(List<PackingListItemVO> records) throws Exception {
		List<PackingListItemVO> recordsWithQtyMoreThanZero = new ArrayList<PackingListItemVO>();
		PortOutwardDaoImpl portOutDaoImpl = new PortOutwardDaoImpl();

		if (records != null && !records.isEmpty()) {
			for (PackingListItemVO vo : records) {
				// Find All Port Outward records for the given
				// PortInwardDetailsId
				Integer portInwardDetailId = vo.getPortInwardDetailId();
				List<PortOutwardBean2> list = portOutDaoImpl.getByPortInwardDetailId(portInwardDetailId);

				if (list != null && !list.isEmpty()) {
					int sum = 0;
					// sum the already out quantity
					for (PortOutwardBean2 b : list) {
						sum = sum + b.getQuantity();
					}
					// set it in the vo.
					vo.setQuantityAlreadyOut(sum);
					int portInwardQuantity = vo.getQuantity();
					// int availableQuantity = portInwardQuantity - sum;
					// vo.setQuantity(availableQuantity);
					double balQty = (((vo.getLength() * vo.getWidth() * vo.getThickness() * portInwardQuantity * 7.85)
							/ 1000000000));

					double output = ((double) ((int) (balQty * 1000.0))) / 1000.0;

					vo.setBalQty(output);

				}

				if (vo.getQuantity() != null && vo.getQuantity() > 0) {
					recordsWithQtyMoreThanZero.add(vo);
				}

			}

		}
		return recordsWithQtyMoreThanZero;

	}

	public String getPackingListAsJson2(HttpServletRequest req) throws Exception {

		// String portInwardIdStr = req.getParameter("port_inward_database_id");
		// logger.info("Port Inward Id = " + portInwardIdStr);
		// Integer portInwardId = Integer.parseInt(portInwardIdStr);
		// JqGridSearchParameterHolder.Rule portInwardIdRule = new
		// JqGridSearchParameterHolder.Rule();
		// portInwardIdRule.setField("port_inward_id");
		// portInwardIdRule.setOp("eq");
		// portInwardIdRule.setData(portInwardIdStr);

		JqGridParametersHolder params = new JqGridParametersHolder(req);
		JqGridSearchParameterHolder searchParam = parseSerachFilters2(params, req);

		if (searchParam == null) {
			searchParam = new JqGridSearchParameterHolder();
		}
		if (searchParam.getRules() == null) {
			List<JqGridSearchParameterHolder.Rule> rules = new ArrayList<JqGridSearchParameterHolder.Rule>();
			searchParam.setRules(rules);
		}
		// searchParam.getRules().add(portInwardIdRule);

		String rows = params.getParam(JQGRID_PARAM_NAMES.rows);
		String page = params.getParam(JQGRID_PARAM_NAMES.page);
		String orderBy = params.getParam(JQGRID_PARAM_NAMES.sidx);
		String order = params.getParam(JQGRID_PARAM_NAMES.sord);

		String portInwardIdStr = req.getParameter("portInwardId");
		PortInwardPackingListDaoImpl portDao = new PortInwardPackingListDaoImpl();
		Integer totalRecordsCount = portDao.fetchPortInwardPackingListRecordCount_2(searchParam, portInwardIdStr);// ,
		// portInwardId);
		List<PackingListItemVO2> records = portDao.fetchPortInwardPackingList_2(Integer.parseInt(page),
				Integer.parseInt(rows), totalRecordsCount, orderBy, order, searchParam, portInwardIdStr);

		// records = updateAlreadyOutQuantity(records);

		JqGridCustomResponse response = new JqGridCustomResponse();
		response.setPage(page);
		response.setRows(records);
		response.setRecords(totalRecordsCount.toString());
		response.setTotal((totalRecordsCount / Long.valueOf(rows)) + 1 + "");
		Gson gson = new Gson();
		String json = gson.toJson(response);
		return json;
	}

	public void updateActualWeightOfWarehouseOutward(Integer warehouseOutwardId, Double actualWeight) {

		WarehouseOutwardTempService warehouseOutwardTempService = new WarehouseOutwardTempService();

		// Get all the delivered items in this warehouseOutwardId
		List<WarehouseOutwardTempVO> deliveredItems = warehouseOutwardTempService
				.getWarehouseOutwardTempRecordsByWarehouseOutwardId(warehouseOutwardId);

		// actualWeight Of item = (secionWeight of the item * actialWeight) /
		// sumOfSectionWeight
		// Update the item in warehouse_outward_temp
		updateActualWeightOfEachItem(deliveredItems, actualWeight);
		
		// Update the actualWeight in warehouse_outward
		WarehouseOutwardForActualWeightUpdateDaoImpl dao = new WarehouseOutwardForActualWeightUpdateDaoImpl();
		dao.updateActualWeightofWarehouseOutward(warehouseOutwardId, actualWeight);
	}

	private void updateActualWeightOfEachItem(List<WarehouseOutwardTempVO> deliveredItems, Double actualWeight) {
		// sumOfSectionWeight = SUM of section weights of all the items
		// delivered in this warehouse_outward_temp
		Double sumOfSectionWeight = calculateSumOfSectionWeight(deliveredItems);

		if (sumOfSectionWeight != null && sumOfSectionWeight > 0d) {
			if (deliveredItems != null && !deliveredItems.isEmpty()) {
				for (WarehouseOutwardTempVO t : deliveredItems) {
					Double sectionWeightOfItem = t.getSect_wt();
					Double actualWeightOfItem = 0d;
					if (sectionWeightOfItem != null) {
						actualWeightOfItem = (sectionWeightOfItem * actualWeight) / sumOfSectionWeight;
					}

					t.setActual_wt(actualWeightOfItem);

				}
			}
		}

		WarehouseOutwardTempService warehouseOutwardTempService = new WarehouseOutwardTempService();

		// Update it in database.
		warehouseOutwardTempService.updateActualWeight(deliveredItems);

		

	}

	private Double calculateSumOfSectionWeight(List<WarehouseOutwardTempVO> deliveredItems) {
		Double sumOfSectionWeight = 0d;
		if (deliveredItems != null && !deliveredItems.isEmpty()) {
			for (WarehouseOutwardTempVO t : deliveredItems) {
				Double secWt = t.getSect_wt();
				if (secWt != null) {
					sumOfSectionWeight = sumOfSectionWeight + secWt;
				}
			}
		}
		return sumOfSectionWeight;
	}

	public void updateHandledBy(Integer id, String handledBy) {
		WarehouseOutwardForActualWeightUpdateDaoImpl dao = new WarehouseOutwardForActualWeightUpdateDaoImpl();
		dao.updateHandledBy(id, handledBy);
		
	}

}
