package vkicl.services;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vkicl.daoImpl.ReportDaoImpl;
import vkicl.report.bean.FileBean;

/**
 * A servlet that retrieves a file from MySQL database and lets the client
 * downloads the file.
 * 
 * @author www.codejava.net
 */
@SuppressWarnings("serial")
public class FileDownloadService extends HttpServlet {

	// size of byte buffer to send file
	private static final int BUFFER_SIZE = 4096;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {

			int id = Integer.parseInt(request.getParameter("material_id"));
			ReportDaoImpl impl = new ReportDaoImpl();
			FileBean file = impl.getMTC(id);

			ServletContext context = getServletContext();

			// sets MIME type for the file download
			String mimeType = context.getMimeType(file.getFileName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			// set content properties and header attributes for the response
			response.setContentType(mimeType);
			response.setContentLength(file.getFileLength());
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"",
					file.getFileName());
			response.setHeader(headerKey, headerValue);

			// writes the file to the client
			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			while ((bytesRead = file.getInputStream().read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			file.getInputStream().close();
			outStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}