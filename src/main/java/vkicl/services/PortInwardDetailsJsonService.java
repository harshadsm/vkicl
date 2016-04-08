package vkicl.services;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import vkicl.daoImpl.PortDaoImpl;
import vkicl.util.JqGridParametersHolder;
import vkicl.util.JqGridParametersHolder.JQGRID_PARAM_NAMES;

public class PortInwardDetailsJsonService {
	
	public String getPortInwardListAsJson(HttpServletRequest req) throws SQLException{
		String json = "";
		JqGridParametersHolder params = new JqGridParametersHolder(req);
		
		String rows = params.getParam(JQGRID_PARAM_NAMES.rows);
		String page = params.getParam(JQGRID_PARAM_NAMES.page);
		String orderBy = params.getParam(JQGRID_PARAM_NAMES.sidx);
		String order = params.getParam(JQGRID_PARAM_NAMES.sord);
		
		
		PortDaoImpl portDao = new PortDaoImpl();
		Integer listSize = portDao.fetchPortInwardDetailsRecordCount();
//		portDao.fetchPortInwardDetails_2(Integer.parseInt(page),
//				Integer.parseInt(rows), listSize, orderBy, order, searchParam);
		return json;
	}

}
