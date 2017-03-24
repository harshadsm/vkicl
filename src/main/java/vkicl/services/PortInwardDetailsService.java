package vkicl.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import vkicl.daoImpl.PortDaoImpl;
import vkicl.form.PortInwardForm;
import vkicl.vo.PortInwardDetailsVO;
import vkicl.vo.PortInwardRecordVO;
import vkicl.vo.UserInfoVO;

public class PortInwardDetailsService {

	private static Logger logger = Logger.getLogger(PortInwardDetailsService.class);

	public void processForm(PortInwardForm form, UserInfoVO user) {

		PortDaoImpl impl = new PortDaoImpl();
		List<PortInwardDetailsVO> list = toList(form, user);
		if (null != list && !list.isEmpty()) {

			// Move any existing records to port_inward_details_deleted table.
			impl.moveToDeleted(form.getPort_inward_id(), user);
			// Now delete the records from port_inward_details table
			impl.deletePortInwardDetailsByPortInwardId(form.getPort_inward_id());
			// logger.debug("Deleted old records");

			// Now insert the new PortInwardDetailsVO in database
			for (PortInwardDetailsVO vo : list) {
				impl.addPortInwardDetailsData(vo, user);
			}
		}

	}

	public List<PortInwardDetailsVO> toList(PortInwardForm form, UserInfoVO user) {
		List<PortInwardDetailsVO> list = new ArrayList<PortInwardDetailsVO>();
		Integer portInwardId = form.getPort_inward_id();
		if (form.getThickness() != null) {
			int recordCount = form.getThickness().length;
			Double[] thickness = form.getThickness();
			Integer[] width = form.getWidth();
			Integer[] length = form.getLength();
			Integer[] qty = form.getQty();
			Double[] actualWt = form.getActualWt();
			Double[] sectionWt = form.getSectionWt();
			// String [] actualWtUnit = form.getActualWtUnit();
			for (int i = 0; i < recordCount; i++) {
				if (thickness[i] == 0d && width[i] == 0 && length[i] == 0 && qty[i] == 0 && sectionWt[i] == 0d) {
					logger.debug("Ignored empty row");
				} else {

					PortInwardDetailsVO vo = new PortInwardDetailsVO();
					vo.setThickness(thickness[i]);
					vo.setWidth(width[i]);
					vo.setLength(length[i]);
					vo.setQuantity(qty[i]);
					vo.setBe_weight(sectionWt[i]);
					//vo.setBe_weight(actualWt[i]);
					// vo.setBe_wt_unit(actualWtUnit[i]); //As explained by
					// client, it will always be TON
					vo.setBe_wt_unit("TON");
					vo.setPort_inward_id(portInwardId);
					vo.setUpdate_ui(user.getUserName());
					vo.setCreate_ui(user.getUserName());
					vo.setCreate_ts(new Date());
					vo.setUpdate_ts(new Date());

					list.add(vo);
				}

			}
		}

		return list;
	}

	public List<PortInwardDetailsVO> fetchPortInwardDetailsList(Integer portInwardId) {
		PortDaoImpl portDao = new PortDaoImpl();
		List<PortInwardDetailsVO> records = portDao.fetchPortInwardDetailsById(portInwardId);
		return records;
	}

}
