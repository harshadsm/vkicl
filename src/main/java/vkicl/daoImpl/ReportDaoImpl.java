package vkicl.daoImpl;

import java.awt.geom.Area;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import vkicl.form.PortInwardForm;
import vkicl.report.bean.FileBean;
import vkicl.report.bean.PortInwardBean;
import vkicl.report.bean.PortOutwardBean;
import vkicl.report.bean.StockBean;
import vkicl.report.bean.WarehouseDispatchBean;
import vkicl.report.bean.WarehouseDispatchDetailsBean;
import vkicl.report.bean.WarehouseInwardBean;
import vkicl.report.bean.WarehouseOutwardBean;
import vkicl.report.form.PortInwardReportForm;
import vkicl.report.form.PortOutwardReportForm;
import vkicl.report.form.StockReportForm;
import vkicl.report.form.WarehouseDispatchDetailsReportForm;
import vkicl.report.form.WarehouseDispatchReportForm;
import vkicl.report.form.WarehouseInwardReportForm;
import vkicl.report.form.WarehouseOutwardReportForm;
import vkicl.util.Converter;
import vkicl.util.PropFileReader;
import vkicl.util.SectionWeightCalculator;
import vkicl.vo.PortInwardDetailsVO;
import vkicl.vo.UserInfoVO;

public class ReportDaoImpl extends BaseDaoImpl {
	static Logger log = Logger.getLogger(ReportDaoImpl.class);
	static PropFileReader prop = PropFileReader.getInstance();

	public PortInwardReportForm fetchPortInwardReport(PortInwardReportForm form, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		ArrayList<PortInwardBean> reportList = null;
		try {
			conn = getConnection();

			query = prop.get("sp.report.port.inward");
			log.info("query = " + query);
			log.info("form = " + form);
			cs = conn.prepareCall(query);
			cs.setString(1, convertStringToDate(form.getFromDate()));
			cs.setString(2, convertStringToDate(form.getToDate()));
			cs.setString(3, form.getVesselName());
			cs.setString(4, form.getMaterialType());
			cs.setString(5, form.getGrade());
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			rs = cs.executeQuery();
			message = cs.getString(6);
			log.info("message = " + message);
			if (null != rs && rs.next()) {
				reportList = new ArrayList<PortInwardBean>();
				do {
					PortInwardBean report = new PortInwardBean();

					report.setId(rs.getInt("port_inward_id"));
					report.setVendorName(formatOutput(rs.getString("vendor_name")));
					report.setVesselName(formatOutput(rs.getString("vessel_name")));
					report.setVesselDate(convertDateToDisplayString(rs.getString("vessel_date")));
					report.setBeNo(formatOutput(rs.getString("be_no")));
					report.setMillName(formatOutput(rs.getString("mill_name")));
					report.setMake(formatOutput(rs.getString("material_make")));
					report.setMaterialType(formatOutput(rs.getString("material_type")));
					report.setGrade(formatOutput(rs.getString("material_grade")));
					report.setLength(rs.getInt("length"));
					report.setWidth(rs.getInt("width"));
					report.setThickness(rs.getDouble("thickness"));
					report.setQty(rs.getInt("quantity"));
					report.setBeWt(rs.getDouble("be_weight"));
					report.setBeWtUnit(formatOutput(rs.getString("be_wt_unit")));

					reportList.add(report);
					report = null;
				} while (rs.next());
			}
			form.setReportList(reportList);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return form;
	}

	public PortOutwardReportForm fetchPortOutwardReport(PortOutwardReportForm form, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		
		ArrayList<PortOutwardBean> reportList = new ArrayList<PortOutwardBean>();
		try {
			conn = getConnection();

			query = prop.get("sp.report.port.outward");
			log.info("query = " + query);
			log.info("form = " + form);
			cs = conn.prepareCall(query);
			cs.setString(1, form.getVesselName());
			cs.setString(2, form.getDispatchedTo());
			cs.setString(3, convertStringToDate(form.getFromDate()));
			cs.setString(4, convertStringToDate(form.getToDate()));
			cs.setString(5, form.getGrade());
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			rs = cs.executeQuery();
			message = cs.getString(6);
			log.info("message = " + message);
			if (null != rs) {
				
				while (rs.next()) {
					PortOutwardBean report = new PortOutwardBean();

					report.setId(rs.getInt("port_out_id"));
					report.setDispatchedTo(formatOutput(rs.getString("Dispatched_To")));
					report.setVehicleNumber(formatOutput(rs.getString("vehicle_number")));
					report.setVehicleDate(convertDateToDisplayString(rs.getString("vehicle_date")));
					report.setVesselName(formatOutput(rs.getString("vessel_name")));
					report.setVesselDate(convertDateToDisplayString(rs.getString("vessel_Date")));
					report.setBeNo(formatOutput(rs.getString("be_no")));
					report.setMaterialType(formatOutput(rs.getString("material_type")));
					report.setMillName(rs.getString("mill_name"));
					// report.setMake(rs.getString("material_make"));
					report.setGrade(formatOutput(rs.getString("grade")));
					report.setLength(rs.getInt("length"));
					report.setWidth(rs.getInt("width"));
					report.setThickness(rs.getDouble("thickness"));
					report.setQty(rs.getInt("quantity"));
					report.setSecWt(rs.getDouble("section_wt"));
					report.setSecWtUnit(formatOutput(rs.getString("actual_wt_Unit")));
					report.setActualWt(rs.getDouble("actual_wt"));
					report.setActualWtUnit(formatOutput(rs.getString("actual_wt_Unit")));
					report.setInvoice(rs.getString("invoice"));
					report.setToWarehouseOrCustomerHiddenField(rs.getString("warehouse_or_customer"));
					
					reportList.add(report);
					
				} 
			}
			log.debug("Total records found = "+reportList.size());
			form.setReportList(reportList);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return form;
	}

	public WarehouseInwardReportForm fetchWarehouseInwardReport(WarehouseInwardReportForm form, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		ArrayList<WarehouseInwardBean> reportList = null;
		try {
			conn = getConnection();

			query = prop.get("sp.report.warehouse.inward");
			log.info("query = " + query);
			log.info("form = " + form);
			cs = conn.prepareCall(query);
			cs.setString(1, convertStringToDate(form.getFromDate()));
			cs.setString(2, convertStringToDate(form.getToDate()));
			cs.setString(3, form.getReceivedFrom());
			cs.setString(4, form.getMaterialType());
			cs.setString(6, form.getVesselName());
			cs.setString(7, form.getGrade());
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			rs = cs.executeQuery();
			message = cs.getString(5);
			log.info("message = " + message);
			if (null != rs && rs.next()) {
				reportList = new ArrayList<WarehouseInwardBean>();
				do {
					WarehouseInwardBean report = new WarehouseInwardBean();

					report.setId(rs.getInt("warehouse_in_detail_id"));
					report.setReceivedFrom(formatOutput(rs.getString("Received_From")));
					report.setVehicleNumber(formatOutput(rs.getString("vehicle_number")));
					
					//report.setVehicleDate(convertDateToDisplayString(rs.getString("received_date")));
					Date receivedDate = rs.getDate("received_date");
					String receivedDateStr = dateToString(receivedDate, "dd-MM-yyyy");
					report.setVehicleDate(receivedDateStr);
					
					report.setVendorName(formatOutput(rs.getString("vendor_name")));
					report.setVesselName(rs.getString("vessel_name"));
					// report.setVesselDate(convertDateToDisplayString(rs.getString("vessel_Date")));
					report.setBeNo(formatOutput(rs.getString("be_no")));
					report.setMaterialType(formatOutput(rs.getString("material_type")));
					report.setMillName(formatOutput(rs.getString("mill_name")));
					report.setMake(formatOutput(rs.getString("material_make")));
					report.setGrade(formatOutput(rs.getString("grade")));
					report.setHeatNo(formatOutput(rs.getString("heat_no")));
					report.setPlateNo(formatOutput(rs.getString("plate_no")));
					report.setLength(rs.getInt("length"));
					report.setWidth(rs.getInt("width"));
					report.setThickness(rs.getDouble("thickness"));
					report.setSecWt(rs.getDouble("section_wt"));
					report.setSecWtUnit(formatOutput(rs.getString("weight_unit")));
					report.setActualWt(rs.getDouble("weight"));
					report.setActualWtUnit(formatOutput(rs.getString("weight_unit")));
					report.setQty(rs.getInt("quantity"));
					report.setWlocation(formatOutput(rs.getString("location")));
					report.setFileName(formatOutput(rs.getString("file_name")));
					report.setFileSize(rs.getDouble("file_size"));
					report.setMaterialId(rs.getInt("material_id"));

					Date warehouseInwardEntryDate = rs.getDate("create_ts");
					String warehouseInwardEntryDateStr = dateToString(warehouseInwardEntryDate, "dd-MM-yyyy");
					report.setCreateDt(warehouseInwardEntryDateStr);
					
					reportList.add(report);
					report = null;
				} while (rs.next());
			}
			form.setReportList(reportList);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return form;
	}

	public String updatePortInwardReport(Map<String, String[]> map, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		try {
			conn = getConnection();
			query = prop.get("sp.report.port.inward.edit");
			log.info("query = " + query);
			cs = conn.prepareCall(query);
			cs.setString(1, fetchFromMap(map, "id"));
			cs.setString(2, fetchFromMap(map, "vendorName"));
			cs.setString(3, fetchFromMap(map, "vesselName"));
			cs.setString(4, fetchDateFromMap(map, "vesselDate"));
			cs.setString(5, fetchFromMap(map, "beNo"));
			cs.setString(6, fetchFromMap(map, "millName"));
			cs.setString(7, fetchFromMap(map, "make"));
			cs.setString(8, fetchFromMap(map, "materialType"));
			cs.setString(9, fetchFromMap(map, "grade"));
			cs.setString(10, fetchFromMap(map, "length"));
			cs.setString(11, fetchFromMap(map, "width"));
			cs.setString(12, fetchFromMap(map, "thickness"));
			cs.setString(13, fetchFromMap(map, "qty"));
			cs.setString(14, fetchFromMap(map, "beWt"));
			cs.setString(15, fetchFromMap(map, "beWtUnit"));
			cs.setString(16, userInfoVO.getUserName());
			cs.registerOutParameter(17, java.sql.Types.VARCHAR);
			rs = cs.executeQuery();
			message = cs.getString(17);
			log.info("message = " + message);

		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			log.error(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return message;
	}

	public String updatePortOutwardReport(Map<String, String[]> map, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		try {
			conn = getConnection();
			query = prop.get("sp.report.port.outward.edit");
			log.info("query = " + query);
			cs = conn.prepareCall(query);

			cs.setString(1, fetchFromMap(map, "id"));
			cs.setString(2, fetchFromMap(map, "dispatchedTo"));
			cs.setString(3, fetchFromMap(map, "vehicleNumber"));
			cs.setString(4, fetchDateFromMap(map, "vehicleDate"));
			// cs.setString(5, fetchFromMap(map, "vesselName"));
			// cs.setString(6, fetchFromMap(map, "vesselDate"));
			cs.setString(5, fetchFromMap(map, "beNo"));
			// cs.setString(8, fetchFromMap(map, "materialType"));
			cs.setString(6, fetchFromMap(map, "grade"));
			cs.setString(7, fetchFromMap(map, "length"));
			cs.setString(8, fetchFromMap(map, "width"));
			cs.setString(9, fetchFromMap(map, "thickness"));
			cs.setString(10, fetchFromMap(map, "qty"));
			cs.setString(11, fetchFromMap(map, "secWt"));
			cs.setString(12, fetchFromMap(map, "actualWt"));
			cs.setString(13, fetchFromMap(map, "actualWtUnit"));
			cs.setString(14, fetchFromMap(map, "invoice"));
			cs.setString(15, userInfoVO.getUserName());
			//cs.setString(17, fetchFromMap(map, "destination"));
			cs.registerOutParameter(16, java.sql.Types.VARCHAR);

			rs = cs.executeQuery();
			message = cs.getString(16);
			log.info("message = " + message);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			log.error(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return message;
	}

	public String deletePortOutward(Map<String, String[]> map) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement statement = null;
		String message = "Success";
		try {
			conn = getConnection();

			String sql = "delete from port_outward where port_out_id = ?";
			statement = conn.prepareStatement(sql);
			statement.setInt(1, Integer.parseInt(fetchFromMap(map, "id")));
			statement.executeUpdate();

			log.info("message = " + message);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			log.error(message);
		} finally {
			closeDatabaseResources(conn, rs, statement);
		}
		return message;
	}

	public String updateWarehouseInwardReport(Map<String, String[]> map, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		try {
			conn = getConnection();
			query = prop.get("sp.report.warehouse.inward.edit");
			log.info("query = " + query);
			cs = conn.prepareCall(query);

			cs.setString(1, fetchFromMap(map, "id"));
			cs.setString(2, fetchFromMap(map, "vehicleNumber"));
			cs.setString(3, fetchDateFromMap(map, "vehicleDate"));
			cs.setString(4, fetchFromMap(map, "vendorName"));
			cs.setString(5, fetchFromMap(map, "beNo"));
			cs.setString(6, fetchFromMap(map, "materialType"));
			cs.setString(7, fetchFromMap(map, "millName"));
			cs.setString(8, fetchFromMap(map, "make"));
			cs.setString(9, fetchFromMap(map, "grade"));
			cs.setString(10, fetchFromMap(map, "heatNo"));
			cs.setString(11, fetchFromMap(map, "plateNo"));
			cs.setString(12, fetchFromMap(map, "length"));
			cs.setString(13, fetchFromMap(map, "width"));
			cs.setString(14, fetchFromMap(map, "thickness"));
			cs.setString(15, fetchFromMap(map, "secWt"));
			cs.setString(16, fetchFromMap(map, "actualWt"));
			cs.setString(17, fetchFromMap(map, "secWtUnit"));
			cs.setString(18, fetchFromMap(map, "qty"));
			cs.setString(19, userInfoVO.getUserName());
			cs.registerOutParameter(20, java.sql.Types.VARCHAR);

			rs = cs.executeQuery();
			message = cs.getString(20);
			log.info("message = " + message);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			log.error(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return message;
	}

	public String insertMTC(int id, FileItem file, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		try {
			log.info("File Name = " + file.getName());
			log.info("File Size  = " + file.getSize());
			conn = getConnection();
			query = prop.get("sp.report.warehouse.material.insert");
			log.info("query = " + query);
			cs = conn.prepareCall(query);
			cs.setInt(1, id);
			cs.setBlob(2, file.getInputStream());
			cs.setString(3, file.getName());
			cs.setLong(4, file.getSize());
			cs.setString(5, userInfoVO.getUserName());
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			rs = cs.executeQuery();
			message = cs.getString(6);
			log.info("message = " + message);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			log.error(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return message;
	}

	public FileBean getMTC(int id) {
		int fileLength = 0;
		String fileName = null;
		InputStream inputStream = null;
		FileBean file = null;
		try {
			Connection conn = getConnection();
			String sql = "SELECT * FROM material_doc WHERE material_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				fileName = rs.getString("file_name");
				Blob blob = rs.getBlob("file");
				inputStream = blob.getBinaryStream();
				fileLength = inputStream.available();
				file = new FileBean();
				file.setFileName(fileName);
				file.setFileLength(fileLength);
				file.setInputStream(inputStream);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	public WarehouseDispatchDetailsReportForm fetchWarehouseDispatchDetailsReport(
			WarehouseDispatchDetailsReportForm form, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		ArrayList<WarehouseDispatchDetailsBean> resultList = null;
		try {

			conn = getConnection();

			query = prop.get("sp.report.warehouse.dispatch.details");
			log.info("query = " + query);
			log.info("form = " + form);
			cs = conn.prepareCall(query);
			cs.setInt(1, form.getId());
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);
			rs = cs.executeQuery();
			message = cs.getString(2);
			log.info("message = " + message);

			boolean isResultSet = cs.execute();
			if (!isResultSet) {
				message = "The first result is not a ResultSet.";
				userInfoVO.setMessage(message);
				return form;
			}

			// First ReulstSet object
			log.info("First Resultset");
			rs = cs.getResultSet();
			while (rs.next()) {
				form.setId(rs.getInt("dispatch_order_id"));
				form.setPending(rs.getString("is_pending"));

				form.setPoNo(formatOutput(rs.getString("poNo")));
				form.setDate(convertDateToDisplayString(rs.getString("date")));
				form.setVehicleNumber(formatOutput(rs.getString("vehicle")));
				form.setHandleBy(formatOutput(rs.getString("handleBy")));
				form.setTransporterName(formatOutput(rs.getString("transporter_name")));
				form.setTransport(formatOutput(rs.getString("toUs")));
				form.setTo(formatOutput(rs.getString("toParty")));
				form.setTransportRate(formatOutput(rs.getString("toPay")));
				form.setTransportUnit(formatOutput(rs.getString("perMTFix")));
				form.setLumsum(formatOutput(rs.getString("lumsum")));
				form.setBuyerName(formatOutput(rs.getString("buyerName")));
				form.setConsigneeName(formatOutput(rs.getString("consigneeName")));
				form.setBrokerName(formatOutput(rs.getString("brokerName")));
				form.setBrokerage(formatOutput(rs.getString("brokerage")));
				form.setBrokerageUnit(rs.getString("brokerageUnit"));
				form.setPaymentTerms(formatOutput(rs.getString("paymentTerms")));
				form.setLoadingCharges(formatOutput(rs.getString("loadingCharges")));
				form.setLoadingChargesUnit(rs.getString("loadingChargesUnit"));
				form.setCuttingCharges(formatOutput(rs.getString("cuttingCharges")));
				form.setCuttingChargesUnit(rs.getString("cuttingChargesUnit"));

				form.setTotal(rs.getInt("total"));
				form.setMtc(rs.getBoolean("mtc"));
				form.setInspection(rs.getBoolean("inspection"));
				form.setInspectionCharges(formatOutput(rs.getString("inspectionCharges")));
				form.setUtReport(rs.getBoolean("utReport"));
				form.setLabReport(rs.getBoolean("labReport"));
				form.setToAcc(formatOutput(rs.getString("toAcc")));
				form.setComments(formatOutput(rs.getString("comments")));
			}
			rs.close();

			isResultSet = cs.getMoreResults();
			if (!isResultSet) {
				message = "The second result is not a ResultSet.";
				userInfoVO.setMessage(message);
				return form;
			}

			log.info("Second Resultset");
			rs = cs.getResultSet();
			if (null != rs && rs.next()) {
				resultList = new ArrayList<WarehouseDispatchDetailsBean>();
				do {
					WarehouseDispatchDetailsBean bean = new WarehouseDispatchDetailsBean();

					bean.setMillName(formatOutput(rs.getString("millName")));
					bean.setMake(formatOutput(rs.getString("make")));
					bean.setGrade(formatOutput(rs.getString("grade")));
					bean.setLength(rs.getInt("length"));
					bean.setWidth(rs.getInt("width"));
					bean.setThickness(rs.getDouble("thickness"));
					bean.setPendingQuantity(rs.getInt("pending_qty"));
					bean.setRate(rs.getString("rate"));
					bean.setRateUnit(rs.getString("rateUnit"));
					bean.setTaxes(rs.getString("taxes"));
					bean.setExcise(rs.getString("excise"));
					bean.setOrderedQuantity(rs.getInt("qty"));
					bean.setActWt(rs.getDouble("actWt"));
					bean.setActWtUnit(rs.getString("actWtUnit"));
					bean.setDispatchDetailsID(rs.getInt("dispatch_details_ID"));

					resultList.add(bean);
					bean = null;
				} while (rs.next());
			}
			form.setResultList(resultList);

		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return form;
	}

	public WarehouseDispatchReportForm fetchWarehouseDispatchReport(WarehouseDispatchReportForm form,
			UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		ArrayList<WarehouseDispatchBean> reportList = null;
		try {
			conn = getConnection();

			query = prop.get("sp.report.warehouse.dispatch");
			log.info("query = " + query);
			log.info("form = " + form);
			cs = conn.prepareCall(query);
			cs.setString(1, convertStringToDate(form.getFromDate()));
			cs.setString(2, convertStringToDate(form.getToDate()));
			cs.setString(3, formatInput(form.getStatus()));
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			rs = cs.executeQuery();
			message = cs.getString(4);
			log.info("message = " + message);
			if (null != rs && rs.next()) {
				reportList = new ArrayList<WarehouseDispatchBean>();
				do {
					WarehouseDispatchBean report = new WarehouseDispatchBean();

					report.setId(rs.getInt("dispatch_order_id"));
					report.setPoNo(formatOutput(rs.getString("poNo")));
					report.setDate(convertDateToDisplayString(rs.getString("date")));
					report.setPending(rs.getString("is_pending"));
					report.setConsigneeName(formatOutput(rs.getString("consigneeName")));
					report.setBuyerName(formatOutput(rs.getString("buyerName")));
					report.setTransporterName(formatOutput(rs.getString("transporter_name")));
					report.setComments(formatOutput(rs.getString("comments")));
					report.setHandleBy(rs.getString("handleBy"));
					reportList.add(report);
					report = null;
				} while (rs.next());
			}
			form.setReportList(reportList);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return form;
	}

	public StockReportForm fetchStockReport(StockReportForm form, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		String sql = "";
		ArrayList<StockBean> reportList = null;
		try {
			conn = getConnection();

			query = prop.get("sp.report.stock.balance");

			log.info("query = " + query);
			log.info("form = " + form);

			// log.info("Grade = " + toString(form.getGrade()));
			cs = conn.prepareCall(query);
			cs.setString(1, toWildCard(formatInput(form.getGrade())));
			cs.setString(2, toWildCard(formatInput(form.getMake())));
			cs.setString(3, toWildCard(formatInput(form.getMaterialType())));
			cs.setString(4, toWildCard(formatInput(form.getLocation())));
			cs.setString(5, toWildCard(formatInput(form.getMillName())));
			cs.setDouble(6, form.getThickness());
			cs.registerOutParameter(7, java.sql.Types.VARCHAR);
			rs = cs.executeQuery();
			message = cs.getString(7);
			log.info("message = " + message);
			if (null != rs) {
				reportList = new ArrayList<StockBean>();
				while (rs.next()){
					StockBean report = new StockBean();

					report.setId(rs.getInt("stock_balance_id"));
					report.setMake(formatOutput(rs.getString("material_make")));
					report.setGrade(formatOutput(rs.getString("grade")));
					report.setMillName(formatOutput(rs.getString("mill_name")));
					report.setMaterialType(formatOutput(rs.getString("material_type")));
					
					
					Double t = rs.getDouble("thickness");
					Integer l = rs.getInt("length");
					Integer w = rs.getInt("width");
					Integer q = rs.getInt("quantity");
					
					report.setLength(l);
					report.setWidth(w);
					report.setThickness(t);

					report.setHeatNo(formatOutput(rs.getString("heat_no")));
					report.setPlateNo(formatOutput(rs.getString("plate_no")));
					report.setLocation(formatOutput(rs.getString("location")));
					report.setQty(q);

					report.setVehicleDate(Converter.dateToString(Converter.sqlDateToDate(rs.getDate("Date_Inward"))));
					//report.setSecWt(rs.getDouble("section_wt"));
					Double secWt = SectionWeightCalculator.calculateSectionWeight(t, l, w, q);
					report.setSecWt(secWt);
					
					report.setSecWtUnit(formatOutput(rs.getString("section_wt_unit")));
					report.setMaterialId(rs.getInt("material_id"));
					report.setFileName(rs.getString("file_name"));
					report.setFileSize(rs.getDouble("file_size"));
					report.setReserved(rs.getBoolean("is_reserved"));
					report.setCustomer(formatOutput(rs.getString("customer")));
					report.setActualWt((rs.getDouble("weight")));
					report.setActualUnit(formatOutput(rs.getString("weight_unit")));
					String invoiceNo = rs.getString("invoice_no");
					report.setInvoiceNoOfLocalVendor(invoiceNo==null?"":invoiceNo);
					java.sql.Date invoiceDate = rs.getDate("invoice_date");
					String invoiceDateStr = "";
					if(invoiceDate!=null){
						invoiceDateStr = Converter.dateToString(Converter.sqlDateToDate(invoiceDate));
					}
					report.setInvoiceDateOfLocalVendor(invoiceDateStr);
					reportList.add(report);
					
				} ;
			}
			form.setReportList(reportList);
		} catch (Exception e) {
			e.printStackTrace();
			// message = e.getMessage();
			// userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return form;
	}

	private String toWildCard(String s) {
		String ret = s;
		if (StringUtils.isEmpty(s)) {
			ret = "%";
		} else if ("ALL".equalsIgnoreCase(s)) {
			ret = "%";
		}
		return ret;
	}

	public String changeStockLocation(Map<String, String[]> map, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		try {
			conn = getConnection();
			query = prop.get("sp.report.stock.location.update");
			log.info("query = " + query);
			cs = conn.prepareCall(query);

			cs.setString(1, fetchFromMap(map, "id"));
			cs.setString(2, fetchFromMap(map, "location"));
			cs.setString(3, userInfoVO.getUserName());
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			rs = cs.executeQuery();
			message = cs.getString(4);
			log.info("message = " + message);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			log.error(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return message;
	}

	public String deleteStock(Map<String, String[]> map, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		try {
			conn = getConnection();
			query = prop.get("sp.report.stock.delete");
			log.info("query = " + query);
			cs = conn.prepareCall(query);

			cs.setString(1, fetchFromMap(map, "id"));
			cs.setString(2, userInfoVO.getUserName());
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);
			rs = cs.executeQuery();
			message = cs.getString(3);
			log.info("message = " + message);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			log.error(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return message;
	}

	public String reserveStock(Map<String, String[]> map, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		try {
			conn = getConnection();
			query = prop.get("sp.report.stock.reserve");
			log.info("query = " + query);
			cs = conn.prepareCall(query);

			cs.setString(1, fetchFromMap(map, "id"));
			cs.setString(2, fetchFromMap(map, "customer"));
			cs.setString(3, userInfoVO.getUserName());
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			rs = cs.executeQuery();
			message = cs.getString(4);
			log.info("message = " + message);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			log.error(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return message;
	}

	public String deleteDispatchOrder(Map<String, String[]> map, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		try {
			conn = getConnection();
			query = prop.get("sp.report.warehouse.dispatch.delete");
			log.info("query = " + query);
			cs = conn.prepareCall(query);

			cs.setString(1, fetchFromMap(map, "id"));
			cs.setString(2, userInfoVO.getUserName());
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);
			rs = cs.executeQuery();
			message = cs.getString(3);
			log.info("message = " + message);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			log.error(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return message;
	}

	public WarehouseOutwardReportForm fetchWarehouseOutwardReport(

			WarehouseOutwardReportForm form, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		ArrayList<WarehouseOutwardBean> reportList = null;
		try {
			conn = getConnection();

			query = prop.get("sp.report.warehouse.outward");

			log.info("query = " + query);
			log.info("form = " + form);
			cs = conn.prepareCall(query);
			cs.setInt(1, form.getDispatchNo());
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);
			rs = cs.executeQuery();
			message = cs.getString(2);
			log.info("message = " + message);
			if (null != rs && rs.next()) {
				reportList = new ArrayList<WarehouseOutwardBean>();
				do {
					WarehouseOutwardBean report = new WarehouseOutwardBean();

					report.setId(rs.getInt("dispatch_order_id"));
					report.setOutwardDate(formatOutput(rs.getString("Outward_Date")));

					report.setMillName(formatOutput(rs.getString("millName")));
					// report.setMake(formatOutput(rs.getString("material_make")));
					report.setGrade(formatOutput(rs.getString("grade")));
					report.setLength(rs.getInt("length"));
					report.setWidth(rs.getInt("width"));
					report.setThickness(rs.getDouble("thickness"));
					report.setHeatNo(formatOutput(rs.getString("heat_no")));
					report.setPlateNo(formatOutput(rs.getString("plate_no")));
					report.setQty(rs.getInt("Quantity"));
					report.setSecWt(rs.getDouble("section_wt"));
					report.setSecWtSum(rs.getDouble("SEC.WT_SUM"));
					report.setSecWtUnit(formatOutput(rs.getString("actual_ut")));
					report.setActualWt(rs.getDouble("ACT.WT"));
					report.setActualWtSum(rs.getDouble("ACT.WT_SUM"));
					report.setActualWtUnit(formatOutput(rs.getString("actual_ut")));

					report.setVehicleNumber(formatOutput(rs.getString("vehicle")));

					report.setBuyerName(formatOutput(rs.getString("buyerName")));
					report.setBrokerName(formatOutput(rs.getString("brokerName")));
					report.setHandleBy(rs.getString("handleBy"));

					reportList.add(report);
					report = null;
				} while (rs.next());
			}
			form.setReportList(reportList);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return form;
	}

	public StockReportForm fetchStockBalReport(StockReportForm form, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		ArrayList<StockBean> reportList = null;
		try {
			conn = getConnection();

			String sql = "SELECT stock_balance_id,MILL_NAME, MATERIAL_TYPE, MATERIAL_MAKE, "
					+ " GRADE, QUANTITY,LENGTH, THICKNESS, WIDTH, LOCATION , heat_no, plate_no "
					+ " FROM stock_balance";
			query = sql;
			log.info("query = " + query);

			cs = conn.prepareCall(query);
			rs = cs.executeQuery();

			if (null != rs && rs.next()) {
				reportList = new ArrayList<StockBean>();
				do {
					StockBean report = new StockBean();

					report.setId(rs.getInt("stock_balance_id"));
					report.setMake(formatOutput(rs.getString("material_make")));
					report.setGrade(formatOutput(rs.getString("grade")));
					report.setMillName(formatOutput(rs.getString("mill_name")));
					report.setMaterialType(formatOutput(rs.getString("material_type")));
					report.setLength(rs.getInt("length"));
					report.setWidth(rs.getInt("width"));
					report.setThickness(rs.getDouble("thickness"));
					report.setLocation(formatOutput(rs.getString("location")));
					report.setQty(rs.getInt("quantity"));
					report.setHeatNo(rs.getString("heat_no"));
					report.setPlateNo(rs.getString("plate_no"));

					reportList.add(report);
					report = null;
				} while (rs.next());
			}
			form.setReportList(reportList);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return form;
	}

	public String deletePortPurchaseOrder(Map<String, String[]> map, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		try {
			conn = getConnection();
			query = "update port_purchase_order set is_deleted = 1, deleted_by = ?, deleted_date = ? where port_purchase_order_id = ? ";
			log.info("query = " + query);
			cs = conn.prepareCall(query);

			String ppoId = fetchFromMap(map, "id");
			cs.setString(1, userInfoVO.getUserName());
			cs.setDate(2, new java.sql.Date(new Date().getTime()));
			cs.setInt(3, Integer.parseInt(ppoId));
			
			int i = cs.executeUpdate();
			message = "Successfully marked the PPO-"+ppoId+" as deleted.";
			log.info("message = " + message);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			log.error(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return message;
	}

}
