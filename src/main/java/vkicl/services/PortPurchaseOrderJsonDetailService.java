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
import vkicl.report.bean.PortOutwardBean2;
import vkicl.util.JqGridCustomResponse;
import vkicl.util.JqGridParametersHolder;
import vkicl.util.JqGridParametersHolder.JQGRID_PARAM_NAMES;
import vkicl.util.JqGridSearchParameterHolder;
import vkicl.vo.PackingListItemVO;
import vkicl.vo.PortInwardDetailsVO;
import vkicl.vo.PortInwardRecordVO;

public class PortPurchaseOrderJsonDetailService {

	private Logger logger = Logger.getLogger(PortPurchaseOrderJsonDetailService.class);

	public String getPortPurchaseOrderDetailAsJson(HttpServletRequest req, String portInwardId)
			throws SQLException, JsonParseException, JsonMappingException, IOException {

		JqGridParametersHolder params = new JqGridParametersHolder(req);
		JqGridSearchParameterHolder searchParam = parseSerachFilters(params);

		String rows = params.getParam(JQGRID_PARAM_NAMES.rows);
		String page = params.getParam(JQGRID_PARAM_NAMES.page);
		String orderBy = params.getParam(JQGRID_PARAM_NAMES.sidx);
		String order = params.getParam(JQGRID_PARAM_NAMES.sord);

		PortDaoImpl portDao = new PortDaoImpl();
		// Integer totalRecordsCount =
		// portDao.fetchPortInwardDetailsRecordCount(searchParam);

		List<PortInwardDetailsVO> records = portDao.fetchPortInwardDetailsById(Integer.parseInt(portInwardId));

		JqGridCustomResponse response = new JqGridCustomResponse();
		response.setPage(page);
		response.setRows(records);
		// response.setRecords(totalRecordsCount.toString());
		// response.setTotal((totalRecordsCount / Long.valueOf(rows)) + 1 + "");
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
					int availableQuantity = portInwardQuantity - sum;
					vo.setQuantity(availableQuantity);
					double balQty = (((vo.getLength() * vo.getWidth() * vo.getThickness() * availableQuantity * 7.85)
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

}
