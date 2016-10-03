package vkicl.services;

import java.awt.Shape;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import vkicl.daoImpl.PortDaoImpl;
import vkicl.daoImpl.StockBalDaoImpl;
import vkicl.form.PortInwardForm;
import vkicl.form.StockForm;
import vkicl.report.form.StockReportForm;
import vkicl.vo.PortInwardDetailsVO;
import vkicl.vo.PortInwardRecordVO;
import vkicl.vo.StockBalanceDetailsVO;

import vkicl.vo.UserInfoVO;

public class StockCuttingDetailsService {

	private static Logger logger = Logger.getLogger(StockCuttingDetailsService.class);
	public void processForm(StockForm form, UserInfoVO user) throws SQLException{

		double orginx=0;
    	double orginy=0;
		StockBalDaoImpl impl = new StockBalDaoImpl();
		List<StockBalanceDetailsVO> list = toList(form, user);
		if(null!=list && !list.isEmpty()){
			
			//Move any existing records to port_inward_details_deleted table.
			//impl.moveToDeleted(form.getStock_Bal_id(), user);
			//Now delete the records from port_inward_details table
			//impl.deletePortInwardDetailsByPortInwardId(form.getStock_Bal_id()());
			//logger.debug("Deleted old records");
			
			//Now insert the new PortInwardDetailsVO in database
			for(StockBalanceDetailsVO vo : list){
				long stockBalId=impl.insertStockBalanceCuttingDetails(vo, user);
				
		    	double length=vo.getLength();
		    	double width=vo.getWidth();
		    	
		    	vkicl.services.geometry.GeometryServiceImpl goemetry=new vkicl.services.geometry.GeometryServiceImpl();
		    	Shape shapeObj= goemetry.toPolygon(orginx, orginy,length, width);
		    	double area=(length * width);
		    	
		    	String Sql=goemetry.toUpdateSql(shapeObj,stockBalId,area);
		    	impl.updateStockBalanceShape(Sql);
		    	impl.updateStockBalanceCut(vo.getStockBalId(), user);
			}
		}
		
	}
	
	public List<StockBalanceDetailsVO> toList(StockForm form, UserInfoVO user) {
		List<StockBalanceDetailsVO> list = new ArrayList<StockBalanceDetailsVO>();
		Integer StockBalId = form.getStock_Bal_id();
		if (form.getThickness() != null) {
			//int recordCount = form.getThickness().length;
			//Double[] thickness = form.getThickness();
			//Integer[] width = form.getWidth();
			//Integer[] length = form.getLength();
		
			//for (int i = 0; i < recordCount; i++) {
			//	if (thickness[i] == 0d && width[i] == 0 && length[i] == 0)  {
			//		logger.debug("Ignored empty row");
			//	} else {

					StockBalanceDetailsVO vo = new StockBalanceDetailsVO();
					vo.setThickness(form.getThickness());
					vo.setWidth(form.getWidth());
					vo.setLength(form.getLength());
					//vo.setQuantity(qty[i]);
					//vo.setBe_weight(actualWt[i]);
//					vo.setBe_wt_unit(actualWtUnit[i]); //As explained by client, it will always be TON
					//vo.setBe_wt_unit("TON");
					//vo.setPort_inward_id(portInwardId);
					//vo.setUpdate_ui(user.getUserName());
					//vo.setCreate_ui(user.getUserName());
					//vo.setCreate_ts(new Date());
					//vo.setUpdate_ts(new Date());
					
					list.add(vo);
				//}

			//}
		}

		return list;
	}
	
	public List<StockBalanceDetailsVO> fetchCuttingDetailsList(Integer stockBalId){
	StockBalDaoImpl portDao = new StockBalDaoImpl();
		List<StockBalanceDetailsVO> records = portDao.fetchCuttingDetailsList(stockBalId);
		return records;
	 
	}

}
