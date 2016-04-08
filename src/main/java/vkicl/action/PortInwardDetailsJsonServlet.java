package vkicl.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import vkicl.services.PortInwardDetailsJsonService;
import vkicl.util.JqGridParametersHolder;

public class PortInwardDetailsJsonServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4896344374052419351L;

	private Logger logger = Logger.getLogger(PortInwardDetailsJsonServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			super.doGet(req, resp);
			PortInwardDetailsJsonService service = new PortInwardDetailsJsonService();
			String json = service.getPortInwardListAsJson(req);

			logger.debug("Going to return PortInwardDetailsJsonServlet json ");
			logger.debug(json);
			// TODO: Chck if user is logged in (will implement it later)
			// TODO: check if user has required access (will implement it later)
			OutputStream out = resp.getOutputStream();
			PrintWriter pw = new PrintWriter(out);
			pw.write("{name:harshad}");
			pw.flush();
			pw.close();
		} catch (SQLException e) {
			logger.error("Some eorror", e);
			throw new ServletException(e);
		}
	}

}
