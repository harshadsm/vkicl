package vkicl.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import vkicl.form.BaseForm;
import vkicl.form.WarehouseDispatchForm;
import vkicl.form.WarehouseInwardForm;
import vkicl.form.WarehouseLocationForm;
import vkicl.form.WarehouseOutwardFinalForm;
import vkicl.form.WarehouseOutwardForm;
import vkicl.form.WarehouseOutwardProcessForm;
import vkicl.report.bean.WarehouseLocationBean;
import vkicl.util.JqGridSearchParameterHolder;
import vkicl.util.PropFileReader;
import vkicl.vo.StockBalanceDetailsVO;
import vkicl.vo.UserInfoVO;

public class WarehouseDaoImpl extends BaseDaoImpl {
	static Logger log = Logger.getLogger(WarehouseDaoImpl.class);
	static PropFileReader prop = PropFileReader.getInstance();

	public WarehouseInwardForm fetchWarehouseInwardDetails(WarehouseInwardForm form, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		int count = 0;
		try {
			if ("".equalsIgnoreCase(form.getVendorVehicleDate()) && "".equalsIgnoreCase(form.getPortVehicleDate())) {
				form.clear();
				return form;
			}
			conn = getConnection();

			query = prop.get("sp.warehouse.inward.select");
			log.info("query = " + query);
			log.info("form = " + form);
			cs = conn.prepareCall(query);

			cs.setString(1, formatInput(form.getPortVehicleNumber()));
			cs.setString(2, convertStringToDate(form.getPortVehicleDate()));
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			rs = cs.executeQuery();
			count = cs.getInt(3);
			message = cs.getString(4);
			log.info("message = " + message);
			if (null != message && message.equalsIgnoreCase("Success") && null != rs && rs.next()) {
				int i = 0;
				String beNo[] = new String[count];
				String materialType[] = new String[count];
				String millName[] = new String[count];
				String make[] = new String[count];
				String grade[] = new String[count];
				Integer length[] = new Integer[count];
				Integer width[] = new Integer[count];
				Double thickness[] = new Double[count];
				Double actWt[] = new Double[count];
				String actWtUnit[] = new String[count];
				Double secWt[] = new Double[count];
				String secWtUnit[] = new String[count];
				Integer qty[] = new Integer[count];
				do {
					beNo[i] = formatOutput(rs.getString(1));
					materialType[i] = formatOutput(rs.getString(2));
					millName[i] = formatOutput(rs.getString(3));
					make[i] = formatOutput(rs.getString(4));
					grade[i] = formatOutput(rs.getString(5));
					length[i] = rs.getInt(6);
					width[i] = rs.getInt(7);
					thickness[i] = rs.getDouble(8);
					actWt[i] = rs.getDouble(9);
					actWtUnit[i] = formatOutput(rs.getString(10));
					secWt[i] = rs.getDouble(11);
					secWtUnit[i] = formatOutput(rs.getString(12));
					qty[i] = rs.getInt(13);
					i = i + 1;
				} while (rs.next());
				form.setBeNo(beNo);
				form.setMaterialType(materialType);
				form.setMillName(millName);
				form.setMake(make);
				form.setGrade(grade);
				form.setLength(length);
				form.setWidth(width);
				form.setThickness(thickness);
				form.setLabelWt(actWt);
				form.setLabelWtUnit(actWtUnit);
				form.setSecWt(secWt);
				form.setSecWtUnit(secWtUnit);
				form.setQty(qty);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return form;
	}

	public WarehouseInwardForm addWarehouseInwardData(WarehouseInwardForm form, UserInfoVO userInfoVO)
			throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		try {
			if ("".equalsIgnoreCase(form.getVendorVehicleDate()) && "".equalsIgnoreCase(form.getPortVehicleDate())) {
				form.clear();
				return form;
			}
			conn = getConnection();

			query = prop.get("sp.warehouse.inward.insert");
			log.info("query = " + query);
			log.info("form = " + form);
			cs = conn.prepareCall(query);

			if (form.getPortVendor().equalsIgnoreCase("port")) {
				cs.setString(1, formatInput(form.getPortVehicleNumber()));
				cs.setString(2, formatInput(""));
				cs.setString(3, convertStringToDate(form.getPortVehicleDate()));
			} else {
				cs.setString(1, formatInput(form.getVendorVehicleNumber()));
				cs.setString(2, formatInput(form.getVendorName()));
				cs.setString(3, convertStringToDate(form.getVendorVehicleDate()));
			}

			cs.setString(4, toString(form.getBeNo()));
			cs.setString(5, toString(form.getMaterialType()));
			cs.setString(6, toString(form.getMillName()));
			cs.setString(7, toString(form.getMake()));
			cs.setString(8, toString(form.getGrade()));

			cs.setString(9, toString(form.getLength()));
			cs.setString(10, toString(form.getWidth()));
			cs.setString(11, toString(form.getThickness()));
			cs.setString(12, toString(form.getLabelWt()));
			cs.setString(13, toString(form.getLabelWtUnit()));
			cs.setString(14, toString(form.getSecWt()));
			cs.setString(15, toString(form.getSecWtUnit()));
			cs.setString(16, toString(form.getQty()));
			cs.setString(17, toString(form.getHeatNo()));
			cs.setString(18, toString(form.getPlateNo()));
			cs.setString(19, toString(form.getSubSecWt()));
			cs.setString(20, toString(form.getSubSecWtUnit()));
			cs.setString(21, toString(form.getSubWt()));
			cs.setString(22, toString(form.getSubWtUnit()));
			cs.setString(23, toString(form.getSubQty()));
			cs.setString(24, toString(form.getWlocation()));
			cs.setString(25, toString(form.getRemark()));

			cs.setString(26, toCountString(form.getSubRow()));
			cs.setInt(27, form.getRow().length);
			cs.setString(28, (userInfoVO.getUserName()));
			cs.setString(29, (form.getInvoiceNo()));

			cs.setString(30, convertStringToDate(form.getInvoiceDate()));

			cs.registerOutParameter(31, java.sql.Types.VARCHAR);
			cs.executeUpdate();
			message = cs.getString(31);
			log.info("message = " + message);

			form.clear();
			userInfoVO.setMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return form;
	}

	public WarehouseDispatchForm addWarehouseDispatchData(WarehouseDispatchForm form, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		int id;
		try {
			if ("".equalsIgnoreCase(form.getDate())) {
				form.clear();
				return form;
			}
			conn = getConnection();

			query = prop.get("sp.warehouse.dispatch.insert");
			log.info("query = " + query);
			log.info("form = " + form);
			cs = conn.prepareCall(query);

			cs.setString(1, formatInput(form.getPoNo()));
			cs.setString(2, convertStringToDate(form.getDate()));
			cs.setString(3, formatInput(form.getVehicleNumber()));
			cs.setString(4, userInfoVO.getUserName());
			cs.setString(5, formatInput(form.getTransporterName()));
			cs.setString(6, formatInput(form.getTransport()));
			cs.setString(7, formatInput(form.getTo()));
			cs.setString(8, formatInput(form.getTransportRate()));
			cs.setString(9, "");
			cs.setString(10, formatInput(form.getTransportUnit()));
			cs.setString(11, formatInput(form.getLumsum()));
			cs.setString(12, formatInput(form.getBuyerName()));
			cs.setString(13, formatInput(form.getConsigneeName()));
			cs.setString(14, formatInput(form.getBrokerName()));
			cs.setString(15, formatInput(form.getBrokerage()));
			cs.setString(16, formatInput(form.getBrokerageUnit()));
			cs.setString(17, formatInput(form.getPaymentTerms()));
			cs.setString(18, formatInput(form.getLoadingCharges()));
			cs.setString(19, formatInput(form.getLoadingChargesUnit()));
			cs.setString(20, formatInput(form.getCuttingCharges()));
			cs.setString(21, formatInput(form.getCuttingChargesUnit()));
			cs.setBoolean(22, form.isMtc());
			cs.setBoolean(23, form.isInspection());
			cs.setString(24, formatInput(form.getInspectionCharges()));
			cs.setBoolean(25, form.isUtReport());
			cs.setBoolean(26, form.isLabReport());
			cs.setString(27, formatInput(form.getToAcc()));
			cs.setString(28, formatInput(form.getComments()));
			cs.setInt(29, form.getTotal());
			cs.setString(30, toString(form.getMake()));
			cs.setString(31, toString(form.getMillName()));
			cs.setString(32, toString(form.getGrade()));
			cs.setString(33, toString(form.getLength()));
			cs.setString(34, toString(form.getWidth()));
			cs.setString(35, toString(form.getThickness()));
			cs.setString(36, toString(form.getQty()));
			cs.setString(37, toString(form.getActWt()));
			cs.setString(38, toString(form.getActWtUnit()));
			cs.setString(39, toString(form.getRate()));
			cs.setString(40, toString(form.getRateUnit()));
			cs.setString(41, toString(form.getTaxes()));
			cs.setString(42, toString(form.getExcise()));
			cs.setInt(43, form.getMake().length);
			cs.setString(44, userInfoVO.getUserName());

			cs.registerOutParameter(45, java.sql.Types.INTEGER);
			cs.registerOutParameter(46, java.sql.Types.VARCHAR);
			cs.executeUpdate();
			id = cs.getInt(45);
			message = cs.getString(46);
			log.info("message = " + message);
			form.clear();
			form.setId(id);
			userInfoVO.setMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return form;
	}

	public BaseForm fetchWarehouseLocationDetails(WarehouseLocationForm form, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		ArrayList<WarehouseLocationBean> resultList = null;
		try {
			conn = getConnection();

			query = prop.get("sp.warehouse.outward.location.select");
			log.info("query = " + query);
			log.info("form = " + form);
			cs = conn.prepareCall(query);
			cs.setString(1, formatInput(form.getMake()));
			cs.setString(2, formatInput(form.getGrade()));
			cs.setString(3, formatInput(form.getMillName()));
			cs.setString(4, form.getLength());
			cs.setString(5, form.getWidth());
			cs.setString(6, form.getThickness());
			cs.registerOutParameter(7, java.sql.Types.VARCHAR);
			rs = cs.executeQuery();
			message = cs.getString(7);
			log.info("message = " + message);
			if (null != rs && rs.next()) {
				resultList = new ArrayList<WarehouseLocationBean>();
				do {
					WarehouseLocationBean report = new WarehouseLocationBean();

					report.setMake(formatOutput(rs.getString("material_make")));
					report.setGrade(formatOutput(rs.getString("grade")));
					report.setMillName(formatOutput(rs.getString("mill_name")));
					report.setLength(rs.getInt("length"));
					report.setWidth(rs.getInt("width"));
					report.setThickness(rs.getDouble("thickness"));

					report.setLocation(formatOutput(rs.getString("location")));
					report.setAvailableQty(rs.getInt("quantity"));
					report.setId(rs.getInt("stock_balance_id"));
					resultList.add(report);
					report = null;
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

	public WarehouseOutwardForm addWarehouseOutwardTempData(WarehouseOutwardForm form, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "", result = "";
		Long savedRecordId = -1L;
		try {
			if (0 == form.getMillName().length) {
				form.clear();
				return form;
			}
			conn = getConnection();

			log.info("form = " + form);

			query = prop.get("query.warehouse.outward.dispatch.status");
			cs = conn.prepareCall(query);
			cs.setInt(1, form.getDispatchNo());
			rs = cs.executeQuery();
			if (null != rs && rs.next()) {
				result = rs.getString("is_pending");
				if (result.equalsIgnoreCase("Pending")) {
					query = prop.get("sp.warehouse.outward.temp.insert");
					log.info("query = " + query);
					for (int i = 0; i < form.getMillName().length; i++) {
						if (form.getSubQty()[i] != null && !form.getSubQty()[i].isEmpty()) {
							cs = conn.prepareCall(query);
							log.debug("form.getDispatchNo() = " + form.getDispatchNo());
							cs.setInt(1, form.getDispatchNo());
							log.debug("form.getMillName()[i] = " + form.getMillName()[i]);
							cs.setString(2, form.getMillName()[i]);
							log.debug(" form.getMake()[i] = " + form.getMake()[i]);
							cs.setString(3, form.getMake()[i]);
							log.debug("form.getGrade()[i] = " + form.getGrade()[i]);
							cs.setString(4, form.getGrade()[i]);
							log.debug("form.getThickness()[i] = " + form.getThickness()[i]);
							cs.setDouble(5, form.getThickness()[i]);
							log.debug("form.getWidth()[i] = " + form.getWidth()[i]);
							cs.setInt(6, form.getWidth()[i]);
							log.debug("form.getLength()[i] = " + form.getLength()[i]);
							cs.setInt(7, form.getLength()[i]);
							log.debug("form.getQty()[i] = " + form.getQty()[i]);
							cs.setInt(8, form.getQty()[i]);
							log.debug("form.getSecWt()[i] = " + form.getSecWt()[i]);
							cs.setDouble(9, form.getSecWt()[i]);
							log.debug("form.getSecWtUnit()[i] = " + form.getSecWtUnit()[i]);
							cs.setString(10, form.getSecWtUnit()[i]);
							log.debug("form.getLocation()[i] = " + form.getLocation()[i]);
							cs.setString(11, form.getLocation()[i]);
							// log.debug("form.getAvailableQty()[i] = " +
							// form.getQtyAvailable()[i]);
							cs.setString(12, "");
							log.debug("form.getSubQty()[i] = " + form.getSubQty()[i]);
							cs.setString(13, form.getSubQty()[i]);
							log.debug("userInfoVO.getUserName() = " + userInfoVO.getUserName());
							cs.setString(14, userInfoVO.getUserName());
							log.debug(" = ");
							int dispatch_details_id = i + 1;

							log.info("form.getDispatchDetailsID()[i] = " + form.getDispatchDetailsID()[i]);

							cs.setString(15, form.getDispatchDetailsID()[i]);

							log.debug("form.getStockId()[i] = " + form.getStockId()[i]);
							cs.setString(17, form.getStockId()[i]);

							cs.registerOutParameter(16, java.sql.Types.VARCHAR);

							int id = cs.executeUpdate();
							message = cs.getString(16);
							log.info("i = " + dispatch_details_id + ", message = " + message + " dbId = " + id);

							closeDatabaseResources(null, rs, cs);
						}
					}
				}
			} else {
				log.info("Dispatch No " + form.getDispatchNo() + " is already " + result);
			}

			userInfoVO.setMessage(message);

		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return form;
	}

	public BaseForm fetchWarehouseOutwardFinalStockDetails(WarehouseOutwardFinalForm form, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		try {
			conn = getConnection();

			query = prop.get("sp.warehouse.outward.stock.select");
			log.info("query = " + query);
			log.info("form = " + form);
			cs = conn.prepareCall(query);
			cs.setString(1, form.getMake());
			cs.setString(2, form.getGrade());
			cs.setString(3, form.getMillName());
			cs.setInt(4, form.getLength());
			cs.setInt(5, form.getWidth());
			cs.setDouble(6, form.getThickness());
			cs.setString(7, form.getLocation());
			cs.setString(8, form.getHeatNo());
			cs.setString(9, form.getPlateNo());
			rs = cs.executeQuery();
			if (null != rs && rs.next()) {
				form.setExisLength(rs.getInt("length"));
				form.setExisWidth(rs.getInt("width"));
				form.setExisThickness(rs.getDouble("thickness"));
				form.setStockId(rs.getInt("stock_balance_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return form;
	}

	public UserInfoVO addWarehouseOutwardFinalData(WarehouseOutwardFinalForm form, UserInfoVO userInfoVO) {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		try {
			if (0 == form.getOutwardTempId()) {
				message = "Outward Temp ID is 0";
				log.error(message);
				userInfoVO.setMessage(message);
				return userInfoVO;
			}
			conn = getConnection();

			log.info("form = " + form);

			query = prop.get("sp.warehouse.outward.final.insert");
			log.info("query = " + query);

			cs = conn.prepareCall(query);
			cs.setInt(1, form.getDispatchNo());
			cs.setInt(2, form.getOutwardTempId());
			cs.setInt(3, form.getStockId());
			cs.setString(4, formatInput(form.getLocation()));
			cs.setString(5, formatInput(form.getHeatNo()));
			cs.setString(6, formatInput(form.getPlateNo()));
			cs.setString(7, formatInput(form.getMillName()));
			cs.setString(8, formatInput(form.getMake()));
			cs.setString(9, formatInput(form.getGrade()));
			cs.setInt(10, form.getLength());
			cs.setInt(11, form.getWidth());
			cs.setDouble(12, form.getThickness());
			cs.setDouble(13, form.getSecWt());
			cs.setString(14, formatInput(form.getSecWtUnit()));
			cs.setInt(15, 1);

			cs.setInt(16, form.getExisLength());
			cs.setInt(17, form.getExisWidth());
			cs.setDouble(18, form.getExisThickness());

			cs.setString(19, toString(form.getNewLength()));
			cs.setString(20, toString(form.getNewWidth()));
			cs.setString(21, toString(form.getNewThickness()));
			cs.setString(22, userInfoVO.getUserName());
			cs.registerOutParameter(23, java.sql.Types.VARCHAR);
			cs.executeUpdate();
			message = cs.getString(23);
			log.info("message = " + message);

			userInfoVO.setMessage(message);

		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return userInfoVO;
	}

	public WarehouseOutwardProcessForm fetchWarehouseOutwardProcessData(WarehouseOutwardProcessForm form,
			UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		ArrayList<WarehouseLocationBean> resultList = null;
		try {
			conn = getConnection();

			query = prop.get("query.warehouse.outward.processing");
			log.info("query = " + query);
			log.info("form = " + form);
			cs = conn.prepareCall(query);
			cs.setInt(1, form.getDispatchNo());
			rs = cs.executeQuery();
			if (null != rs && rs.next()) {
				resultList = new ArrayList<WarehouseLocationBean>();
				do {
					WarehouseLocationBean report = new WarehouseLocationBean();

					report.setId(rs.getInt("outward_temp_id"));

					report.setMake(formatOutput(rs.getString("make")));
					report.setGrade(formatOutput(rs.getString("grade")));
					report.setMillName(formatOutput(rs.getString("mill_name")));
					report.setLength(rs.getInt("length"));
					report.setWidth(rs.getInt("width"));
					report.setThickness(rs.getDouble("thickness"));

					report.setLocation(formatOutput(rs.getString("location")));
					report.setQty(rs.getInt("taken_qty"));

					report.setSecWt(rs.getDouble("sect_wt"));
					report.setSecWtUnit(formatOutput(rs.getString("sect_ut")));

					report.setProcessed(rs.getBoolean("Isprocessed"));

					resultList.add(report);
					report = null;
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

	public UserInfoVO addWarehouseOutwardProcessData(WarehouseOutwardProcessForm form, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		// Integer warehouseOutwardId = -1;
		try {

			if (0 == form.getDispatchNo()) {
				message = "Unable to insert Outward entry"; // log.error(message);
				userInfoVO.setMessage("");
				return userInfoVO;
			}

			conn = getConnection();

			query = prop.get("sp.warehouse.outward.insert");
			log.info("query = " + query);
			log.info("form = " + form);
			cs = conn.prepareCall(query);

			cs.setDouble(1, form.getActWt());
			cs.setString(2, formatInput(form.getActWtUnit()));
			cs.setInt(3, form.getDispatchNo());
			cs.setString(4, formatInput(form.getVehicleNumber()));
			cs.setString(5, formatInput(form.getVehicleDate()));
			cs.setString(6, userInfoVO.getUserName());
			cs.registerOutParameter(7, java.sql.Types.VARCHAR);
			cs.executeUpdate();
			message = cs.getString(7);
			log.info("message = " + message);
			form.clear();
			userInfoVO.setMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return userInfoVO;
	}

	public WarehouseLocationForm findWarehouseOutwardTempRecord(int dispatchOrderNo, int dispatchDetailRowId,
			WarehouseLocationForm form, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		List<WarehouseLocationBean> locations = form.getResultList();
		if (locations != null && !locations.isEmpty()) {
			for (WarehouseLocationBean l : locations) {
				try {
					conn = getConnection();

					query = prop.get("sp.warehouse.outward.temp.find");
					log.info("query = " + query);
					log.info("form = " + form);
					cs = conn.prepareCall(query);
					cs.setInt(1, dispatchOrderNo);
					cs.setString(2, form.getMillName());
					cs.setString(3, form.getMake());
					cs.setString(4, form.getGrade());
					cs.setDouble(5, Double.parseDouble(form.getThickness()));
					cs.setInt(6, Integer.parseInt(form.getWidth()));
					cs.setInt(7, Integer.parseInt(form.getLength()));
					cs.setInt(8, dispatchDetailRowId);
					cs.setString(9, l.getLocation());

					rs = cs.executeQuery();
					if (null != rs && rs.next()) {

						do {

							Integer reqd_qty = rs.getInt("reqd_qty");
							Integer locationWiseQty = rs.getInt("location_wise_qty");
							l.setQty(locationWiseQty);
							form.setPreviouslySelectedQtyFromWarehouseOutwardTemp(reqd_qty.toString());

						} while (rs.next());
					}

				} catch (Exception e) {
					e.printStackTrace();
					message = e.getMessage();
					userInfoVO.setMessage(message);
				} finally {
					closeDatabaseResources(conn, rs, cs);
				}
			}
		}
		return form;
	}

	public WarehouseLocationForm fetchWarehouseLocationData(WarehouseLocationForm form, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "";
		String message = "";
		ArrayList<WarehouseLocationBean> resultList = null;

		try {
			conn = getConnection();

			double length = Double.parseDouble(form.getLength());
			double width = Double.parseDouble(form.getWidth());

			double area = (length * width);

			query = "SELECT s.material_make, s.grade, s.mill_name, s.location, s.quantity, s.length, s.width, s.thickness, s.stock_balance_id, "
					+ " s.heat_no, s.plate_no from stock_balance s where is_cut!=1 and s.quantity!=0 and plate_area >= "
					+ area;

			log.info("query = " + query);
			cs = conn.prepareCall(query);
			// cs.setInt(1, id);
			rs = cs.executeQuery();
			if (null != rs && rs.next()) {
				resultList = new ArrayList<WarehouseLocationBean>();
				do {
					WarehouseLocationBean report = new WarehouseLocationBean();

					report.setMake(formatOutput(rs.getString("material_make")));
					report.setGrade(formatOutput(rs.getString("grade")));
					report.setMillName(formatOutput(rs.getString("mill_name")));
					report.setLength(rs.getInt("length"));
					report.setWidth(rs.getInt("width"));
					report.setThickness(rs.getDouble("thickness"));

					report.setLocation(formatOutput(rs.getString("location")));
					report.setAvailableQty(rs.getInt("quantity"));
					report.setStockId(rs.getInt("stock_balance_id"));
					report.setHeatNo(rs.getString("heat_no"));
					report.setPlateNo(rs.getString("plate_no"));

					resultList.add(report);
					report = null;
				} while (rs.next());
			}
			form.setResultList(resultList);

		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}

		return form;
	}

	public void updateStockBalanceData(WarehouseOutwardForm form, UserInfoVO userInfoVO, Integer qty, Integer stockid,
			String flag) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		PreparedStatement statement = null;
		String query = "", message = "";
		// Integer warehouseOutwardId = -1;
		try {

			conn = getConnection();

			if (flag == "1") {
				query = "Update stock_balance set quantity = ?,is_sold=?,sold_date=NOW(),update_ui=?, update_ts=NOW() where stock_balance_id=?";
				statement = conn.prepareStatement(query);
				statement.setInt(1, qty);
				statement.setString(2, "1");
				statement.setString(3, userInfoVO.getUserName());
				statement.setInt(4, stockid);

			} else {
				query = "Update stock_balance set quantity = ?,update_ui=?, update_ts=NOW() where stock_balance_id=?";

				statement = conn.prepareStatement(query);
				statement.setInt(1, qty);
				statement.setString(2, userInfoVO.getUserName());
				statement.setInt(3, stockid);
			}

			statement.executeUpdate();
			log.info("message = " + message);

		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}

	}

	public UserInfoVO addStockOutwardData(WarehouseOutwardForm form, UserInfoVO userInfoVO) {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String query = "", message = "";
		// Integer warehouseOutwardId = -1;
		try {
			if (0 == form.getDispatchNo()) {
				// message = "Unable to insert Outward entry";
				// log.error(message);
				userInfoVO.setMessage("");
				return userInfoVO;
			}
			conn = getConnection();

			query = "insert into stock_outward (mill_name,material_make,heat_no,plate_no,"
					+ " material_type,grade,length,width,thickness,quantity,location,"
					+ " create_ui,update_ui,create_ts,update_ts, plate_area) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			log.info("query = " + query);
			log.info("form = " + form);
			cs = conn.prepareCall(query);

			for (int i = 0; i < form.getMillName().length; i++) {
				cs.setString(1, (form.getMillName())[i]);
				cs.setString(2, (form.getMake())[i]);
				cs.setString(3, "");
				cs.setString(4, "");
				cs.setString(5, "");
				cs.setString(6, (form.getGrade())[i]);
				cs.setInt(7, (form.getLength())[i]);
				cs.setInt(8, (form.getWidth())[i]);
				cs.setDouble(9, (form.getThickness())[i]);
				cs.setDouble(10, (form.getQty())[i]);
				cs.setString(11, (form.getLocation())[i]);
				cs.setString(12, userInfoVO.getUserName());
				cs.setString(13, userInfoVO.getUserName());
				cs.setString(14, getCurentTime());
				cs.setString(15, getCurentTime());

				double plateArea = form.getLength()[i] * form.getWidth()[i];
				cs.setDouble(16, plateArea);

				cs.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			userInfoVO.setMessage(message);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return userInfoVO;
	}

	public Integer fetchStockBalQuantity(WarehouseOutwardForm form) throws SQLException {

		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		int availableQty = 0;
		try {
			conn = getConnection();
			// String count_sql = " SELECT count(*) FROM port_inward_details "

			String sql = " select quantity " + " from  " + " stock_balance where stock_balance_id= "
					+ form.getStockId();

			log.info("query = " + sql);

			cs = conn.prepareCall(sql);

			rs = cs.executeQuery();
			if (null != rs && rs.next()) {

				do {
					availableQty = rs.getInt(1);

					log.debug("Row count === " + availableQty);
				} while (rs.next());

			}

		} catch (Exception e) {
			log.error("Some error", e);
		} finally {
			closeDatabaseResources(conn, rs, cs);
		}
		return availableQty;
	}

}
