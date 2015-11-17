package vkicl.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import vkicl.daoImpl.XMLServiceDaoImpl;
import vkicl.util.PropFileReader;

public class XMLService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(XMLService.class);
	private static PropFileReader prop = PropFileReader.getInstance();

	public XMLService() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> list = null;
		PrintWriter out = null;
		String query = null;
		String count = null;
		String params[] = null;
		int num = 0;
		try {
			query = request.getParameter("query");
			count = request.getParameter("count");

			if (null != count && !"".equals(count)) {
				try {
					num = Integer.parseInt(count);
					params = new String[num];

					for (int i = 1; i <= num; i++) {
						params[i - 1] = request.getParameter("param" + i);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (null != query && !"".equals(query) && !"null".equals(query)) {
				query = prop.get(query);
				XMLServiceDaoImpl impl = new XMLServiceDaoImpl();

				list = impl.getXML(query, num, params);

				if (null != list) {
					out = response.getWriter();
					out.print("<result>");
					for (String value : list) {
						out.print("<value>" + value + "</value>");
					}
					out.print("</result>");
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
