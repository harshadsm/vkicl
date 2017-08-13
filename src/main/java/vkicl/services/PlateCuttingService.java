package vkicl.services;

import java.awt.Shape;
import java.awt.geom.Area;
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

import vkicl.daoImpl.StockBalDaoImpl;
import vkicl.exceptions.CutNotPossibleException;
import vkicl.form.StockForm;
import vkicl.services.geometry.GeometryService;
import vkicl.services.geometry.GeometryServiceImpl;
import vkicl.util.JqGridCustomResponse;
import vkicl.util.JqGridParametersHolder;
import vkicl.util.JqGridParametersHolder.JQGRID_PARAM_NAMES;
import vkicl.util.JqGridSearchParameterHolder;
import vkicl.vo.StockBalanceDetailsVO;
import vkicl.vo.SvgPointsStringResponseJsonVO;
import vkicl.vo.UserInfoVO;

public class PlateCuttingService {

	private Logger logger = Logger.getLogger(PlateCuttingService.class);

	// USED FOR SCALING
	private final Double SVG_MAX_WIDTH_AND_LENGTH = 300D;

	public void processForm(StockForm form, UserInfoVO user) throws SQLException, CutNotPossibleException {

		try {
			StockBalDaoImpl impl = new StockBalDaoImpl();
			List<StockBalanceDetailsVO> list = toList(form, user);
			if (null != list && !list.isEmpty()) {

				// Move any existing records to port_inward_details_deleted
				// table.
				// impl.moveToDeleted(form.getStock_Bal_id(), user);
				// Now delete the records from port_inward_details table
				// impl.deletePortInwardDetailsByPortInwardId(form.getStock_Bal_id()());
				// logger.debug("Deleted old records");

				// Now insert the new PortInwardDetailsVO in database
				Shape BigplateShape = impl.fetchplateShape(form.getStock_Bal_id());

				for (StockBalanceDetailsVO vo : list) {
					// Long stockBalId=
					// impl.insertStockBalanceCuttingDetails(vo, user);
					double orginx = form.getOrigin_x();
					double orginy = form.getOrigin_y();
					double smallPlateLength = vo.getLength();
					double smallPlateWidth = vo.getWidth();
					Integer warehouseInwardId = vo.getWarehouseInwardId();

					vkicl.services.geometry.GeometryServiceImpl goemetry = new vkicl.services.geometry.GeometryServiceImpl();

					List<Area> plates = goemetry.cut(orginx, orginy, smallPlateLength, smallPlateWidth, BigplateShape);
					for (int i = 0; i < plates.size(); i++) {
						String heatNo = "WARNING_notKnownWhileCutting";
						String plateNo = "WARNING_notKnownWhileCutting";
						if (i == 0) {
							heatNo = form.getHeat_no();
							plateNo = form.getPlate_no();
						} else if (i >= 1) {
							heatNo = form.getHeat_no_2();
							plateNo = form.getPlate_no_2();
						}

						// Overwrite the heatno and plate no according to what
						// the user has supplied while cutting
						vo.setHeat_no(heatNo);
						vo.setPlate_no(plateNo);

						Area a = plates.get(i);
						String insertSql = goemetry.toInsertSql(a, vo);
						logger.info(insertSql);

						impl.insertCutPlateDetails(insertSql);
						// Just make sure that the generated SQL is correct
						// And then Execute this sql in database to insert the
						// two plates (cut-plate and remaining-plate) in
						// database.
					}

					impl.updateStockBalanceCut(vo.getStockBalId(), vo.getQuantity(), user);

				}
			}

		} catch (SQLException e) {
			logger.error("Some error", e);
		}

	}

	// public List<StockBalanceDetailsVO> toList(StockForm form, UserInfoVO
	// user) throws SQLException {
	// List<StockBalanceDetailsVO> list = new
	// ArrayList<StockBalanceDetailsVO>();
	// Integer stockBalId = form.getStock_Bal_id();
	//
	// StockBalDaoImpl impl = new StockBalDaoImpl();
	// StockBalanceDetailsVO vo = impl.fetchStockBalById(stockBalId);
	// list.add(vo);
	//
	// return list;
	// }

	public List<StockBalanceDetailsVO> toList(StockForm form, UserInfoVO user) throws SQLException {
		List<StockBalanceDetailsVO> list = new ArrayList<StockBalanceDetailsVO>();
		Integer stockBalId = form.getStock_Bal_id();
		StockBalDaoImpl impl = new StockBalDaoImpl();
		StockBalanceDetailsVO voForWarehouseInwardId = impl.fetchStockBalById(stockBalId);
		// if (form.getThickness() != null) {
		// int recordCount = form.getThickness().length;
		// Double[] thickness = form.getThickness();
		// Integer[] width = form.getWidth();
		// Integer[] length = form.getLength();

		// for (int i = 0; i < recordCount; i++) {
		// if (thickness[i] == 0d && width[i] == 0 && length[i] == 0) {
		// logger.debug("Ignored empty row");
		// } else {

		StockBalanceDetailsVO vo = new StockBalanceDetailsVO();
		vo.setWarehouseInwardId(voForWarehouseInwardId.getWarehouseInwardId());

		vo.setThickness(form.getThickness());
		vo.setWidth(form.getWidth());
		vo.setLength(form.getLength());
		vo.setGrade(form.getGrade());
		vo.setMaterialType(form.getMaterialType());
		vo.setMake(form.getMake());
		vo.setMillName(form.getMillName());
		vo.setStockBalId(form.getStock_Bal_id());

		double plateArea = (form.getLength() * form.getWidth());

		vo.setPlateArea(plateArea);
		vo.setQuantity(form.getQuantity());
		vo.setLocation(form.getLocation());
		vo.setHeat_no(form.getHeat_no());
		vo.setPlate_no(form.getPlate_no());
		// vo.setBe_weight(actualWt[i]);
		// vo.setBe_wt_unit(actualWtUnit[i]); //As explained by client, it will
		// always be TON
		// vo.setBe_wt_unit("TON");
		// vo.setPort_inward_id(portInwardId);
		// vo.setUpdate_ui(user.getUserName());
		// vo.setCreate_ui(user.getUserName());
		// vo.setCreate_ts(new Date());
		// vo.setUpdate_ts(new Date());

		list.add(vo);
		// }

		// }
		// }

		return list;
	}

	public String getStockBalCuttingListAsJson(HttpServletRequest req) throws Exception {

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

		StockBalDaoImpl stockBalDao = new StockBalDaoImpl();
		Integer totalRecordsCount = stockBalDao.fetchStockBalRecordCount(searchParam);// ,
																						// portInwardId);

		List<StockBalanceDetailsVO> outrecords = stockBalDao.fetchStockBalList(Integer.parseInt(page),
				Integer.parseInt(rows), totalRecordsCount, orderBy, order, searchParam);

		// updateAlreadyOutQuantity(outrecords);

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
			searchParam = mapper.readValue(filters, JqGridSearchParameterHolder.class);
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
			logger.error("Some error", e);
		}
		return vo;
	}

	public String getPlateCoordinatesAsString(Shape plateShape) {
		GeometryService geometryService = new GeometryServiceImpl();
		List<Double[]> coordinates = geometryService.getCoordinatesList(plateShape);
		Double scalingFactor = getScalingFactor(plateShape);
		List<Double[]> scaledCoordinates = scale(coordinates, scalingFactor);

		StringBuilder sb = new StringBuilder();

		for (Double[] coordinate : scaledCoordinates) {
			Double x = coordinate[0];
			Double y = coordinate[1];
			sb.append(x).append(",").append(y).append(" ");
		}

		sb.trimToSize();
		logger.info(sb.toString());
		return sb.toString();
	}

	public String getCustomScaledPlateCoordinatesAsString(Shape plateShape, Double scalingFactor) {
		GeometryService geometryService = new GeometryServiceImpl();
		List<Double[]> coordinates = geometryService.getCoordinatesList(plateShape);

		if (coordinates != null && coordinates.size() > 0) {
			int lastItemIndex = coordinates.size() - 1;
			coordinates.remove(lastItemIndex);
		}
		List<Double[]> scaledCoordinates = scale(coordinates, scalingFactor);

		StringBuilder sb = new StringBuilder();

		for (Double[] coordinate : scaledCoordinates) {
			Double x = coordinate[0];
			Double y = coordinate[1];
			sb.append(x).append(",").append(y).append(" ");
		}

		sb.trimToSize();
		logger.info(sb.toString());
		return sb.toString();
	}

	public List<Double[]> getPlateCoordinates(Shape plateShape) {
		GeometryService geometryService = new GeometryServiceImpl();
		List<Double[]> coordinates = geometryService.getCoordinatesList(plateShape);

		removeLastCoordinate(coordinates);
		removeLastCoordinate(coordinates);

		return coordinates;

	}

	public List<Double[]> getPlateCoordinatesScalled(Shape plateShape) {
		GeometryService geometryService = new GeometryServiceImpl();
		List<Double[]> coordinates = geometryService.getCoordinatesList(plateShape);
		Double scalingFactor = getScalingFactor(plateShape);
		List<Double[]> scaledCoordinates = scale(coordinates, scalingFactor);

		removeLastCoordinate(scaledCoordinates);
		removeLastCoordinate(scaledCoordinates);

		return scaledCoordinates;

	}

	private void removeLastCoordinate(List<Double[]> coordinates) {
		if (coordinates != null && (coordinates.size() - 1) > 2) {
			coordinates.remove(coordinates.size() - 1);
		}
	}

	private List<Double[]> scale(List<Double[]> coordinates, Double scalingFactor) {
		List<Double[]> scaledCoordinates = new ArrayList<Double[]>();
		for (Double[] coordinate : coordinates) {
			Double x = coordinate[0];
			Double y = coordinate[1];

			Double scaledX = x * scalingFactor;
			Double scaledY = y * scalingFactor;

			Double[] scaledCoordinate = { scaledX, scaledY };
			scaledCoordinates.add(scaledCoordinate);
		}
		return scaledCoordinates;
	}

	private Double getScalingFactor(Shape plateShape) {
		Double width = plateShape.getBounds2D().getWidth();
		Double length = plateShape.getBounds2D().getHeight();
		Double scalingFactor = SVG_MAX_WIDTH_AND_LENGTH / width;
		if (length > width) {
			scalingFactor = SVG_MAX_WIDTH_AND_LENGTH / length;
		}
		logger.info("Scaling Factor  = " + scalingFactor);
		return scalingFactor;
	}

	public Double getMaxWidthAndHeightForSvgTag() {
		return SVG_MAX_WIDTH_AND_LENGTH;
	}

	public String getSvgCoordinatesStringJson(HttpServletRequest request) {
		// originalPlateLength : originalPlateLength,
		// originalPlateWidth : originalPlateWidth,
		// lengthOfCut : lengthOfCut,
		// widthOfCut : widthOfCut
		//
		String x = request.getParameter("x");
		String y = request.getParameter("y");
		String lengthOfCutStr = request.getParameter("lengthOfCut");
		String widthOfCutStr = request.getParameter("widthOfCut");
		String originalPlateLengthStr = request.getParameter("originalPlateLength");
		String originalPlateWidthStr = request.getParameter("originalPlateWidth");
		logger.info("x = " + x);
		logger.info("y = " + y);
		logger.info("l = " + lengthOfCutStr);
		logger.info("w = " + widthOfCutStr);

		Double originX = Double.parseDouble(x);
		Double originY = Double.parseDouble(y);
		Double lengthOfCut = Double.parseDouble(lengthOfCutStr);
		Double widthOfCut = Double.parseDouble(widthOfCutStr);
		Double originalPlateLength = Double.parseDouble(originalPlateLengthStr);
		Double originalPlateWidth = Double.parseDouble(originalPlateWidthStr);

		GeometryService geometryService = new GeometryServiceImpl();
		Shape originalPlate = geometryService.toPolygon(0d, 0d, originalPlateLength, originalPlateWidth);
		Double scalingFactor = getScalingFactor(originalPlate);
		// Shape plateToBeCut = geometryService.toPolygon(originX, originY,
		// lengthOfCut * scalingFactor, widthOfCut * scalingFactor);
		Shape plateToBeCut = geometryService.toPolygon(originX, originY, lengthOfCut, widthOfCut);
		String svgCoordinatesAsString = getCustomScaledPlateCoordinatesAsString(plateToBeCut, scalingFactor);
		logger.info(svgCoordinatesAsString);

		SvgPointsStringResponseJsonVO svgPointsJson = new SvgPointsStringResponseJsonVO();
		svgPointsJson.setSvgPointsString(svgCoordinatesAsString);
		svgPointsJson.setStatus("success");
		Gson gson = new Gson();
		String json = gson.toJson(svgPointsJson);
		return json;
	}
}
