package vkicl.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import vkicl.util.JqGridParametersHolder;

public class PortInwardDetailsJsonServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4896344374052419351L;
	
	private Logger logger = Logger.getLogger(PortInwardDetailsJsonServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		JqGridParametersHolder params = new JqGridParametersHolder(req);
		
		logger.debug("Going to return PortInwardDetailsJsonServlet json ");
		//TODO: Chck if user is logged in (will implement it later)
		//TODO: check if user has required access (will implement it later)
		OutputStream out = resp.getOutputStream();
		PrintWriter pw = new PrintWriter(out);
		pw.write("{name:harshad}");
		pw.flush();
		pw.close();
		
	}

}
