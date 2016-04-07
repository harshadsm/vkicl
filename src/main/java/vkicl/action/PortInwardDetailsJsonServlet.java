package vkicl.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class PortInwardDetailsJsonServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4896344374052419351L;
	
	private Logger logger = Logger.getLogger(PortInwardDetailsJsonServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		logger.debug("Going to return PortInwardDetailsJsonServlet json ");
		
	}

}
