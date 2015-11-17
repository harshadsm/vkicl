package vkicl.services;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import vkicl.daoImpl.ReportDaoImpl;
import vkicl.util.Constants;
import vkicl.vo.UserInfoVO;

@SuppressWarnings("serial")
public class FileUploadService extends HttpServlet {

	private static Logger log = Logger.getLogger(FileUploadService.class);

	@SuppressWarnings("rawtypes")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			UserInfoVO userInfoVO = (UserInfoVO) request.getSession()
					.getAttribute(Constants.USER_INFO_SESSION);
			int id = Integer.parseInt(request.getParameter("warehouse_inward_detail_id"));
			log.info("warehouse_inward_detail_id = " + id);
			List fileItems = upload.parseRequest(request);
			Iterator i = fileItems.iterator();
			if(fileItems.size() == 0){
				throw new Exception("Unable to upload file");
			}
			while (i.hasNext()) {
				FileItem file = (FileItem) i.next();
				if (!file.isFormField()) {
					ReportDaoImpl impl = new ReportDaoImpl();
					impl.insertMTC(id, file, userInfoVO);
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		doPost(request, response);
	}
}