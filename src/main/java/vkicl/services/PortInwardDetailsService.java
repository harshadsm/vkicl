package vkicl.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vkicl.daoImpl.PortDaoImpl;
import vkicl.form.PortInwardForm;
import vkicl.vo.PortInwardDetailsInsertVO;
import vkicl.vo.UserInfoVO;

public class PortInwardDetailsService {

	public void processForm(PortInwardForm form, UserInfoVO user){

		PortDaoImpl impl = new PortDaoImpl();
		List<PortInwardDetailsInsertVO> list = toList(form, user);
		if(null!=list && !list.isEmpty()){
			for(PortInwardDetailsInsertVO vo : list){
				impl.addPortInwardDetailsData(vo);
			}
		}
		
		
	}
	
	public List<PortInwardDetailsInsertVO> toList(PortInwardForm form, UserInfoVO user) {
		List<PortInwardDetailsInsertVO> list = new ArrayList<PortInwardDetailsInsertVO>();
		Integer portInwardId = form.getPort_inward_id();
		if (form.getThickness() != null) {
			int recordCount = form.getThickness().length;
			Double[] thickness = form.getThickness();
			Integer[] width = form.getWidth();
			Integer[] length = form.getLength();
			Integer[] qty = form.getQty();
			Double [] actualWt = form.getActualWt();
			String [] actualWtUnit = form.getActualWtUnit();
			for (int i = 0; i < recordCount; i++) {
				PortInwardDetailsInsertVO vo = new PortInwardDetailsInsertVO();
				vo.setThickness(thickness[i]);
				vo.setWidth(width[i]);
				vo.setLength(length[i]);
				vo.setQuantity(qty[i]);
				vo.setBe_weight(actualWt[i]);
				vo.setBe_wt_unit(actualWtUnit[i]);
				vo.setPort_inward_id(portInwardId);
				vo.setUpdate_ui(user.getUserName());
				vo.setCreate_ui(user.getUserName());
				vo.setCreate_ts(new Date());
				vo.setUpdate_ts(new Date());
				
				list.add(vo);
			}
		}

		return list;
	}

}
