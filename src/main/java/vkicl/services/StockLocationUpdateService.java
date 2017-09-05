package vkicl.services;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vkicl.daoImpl.StockBalDaoImpl;
import vkicl.util.Constants;
import vkicl.vo.UserInfoVO;

public class StockLocationUpdateService {

	private Logger logger = LoggerFactory.getLogger(StockLocationUpdateService.class);
	
	public String updateLocation(HttpServletRequest req) throws SQLException{
		UserInfoVO userInfoVO = (UserInfoVO) req.getSession()
				.getAttribute(Constants.USER_INFO_SESSION);
		
		String stockIdStr = req.getParameter("stockBalanceDbId");
		String newLocation = req.getParameter("newStockLocation");
		String remarksIfAny = req.getParameter("remark");
		Integer stockId = Integer.parseInt(stockIdStr);
		
		logger.debug("stockIdStr = "+stockIdStr);
		logger.debug("newLocation = "+newLocation);
		
		StockBalDaoImpl dao = new StockBalDaoImpl();
		String isSuccess = dao.updateStockLocation(stockId, newLocation, userInfoVO, remarksIfAny);
		return isSuccess;
	}
}
