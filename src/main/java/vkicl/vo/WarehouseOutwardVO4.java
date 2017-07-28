package vkicl.vo;

import java.util.Date;

public class WarehouseOutwardVO4 {
	private Integer warehouse_outward_id;
	private Double actual_wt;
	private String actual_ut;
	private Integer dispatchNo;
	private String vehicle_no;
	private Date vehicle_dt;
	private String create_ui;
	private String update_ui;
	private Date create_ts;
	private Date update_ts;
	private Integer dispatch_detail_id;
	private Integer delivered_quantity;
	private String handled_by;
	public Integer getWarehouse_outward_id() {
		return warehouse_outward_id;
	}
	public void setWarehouse_outward_id(Integer warehouse_outward_id) {
		this.warehouse_outward_id = warehouse_outward_id;
	}
	public Double getActual_wt() {
		return actual_wt;
	}
	public void setActual_wt(Double actual_wt) {
		this.actual_wt = actual_wt;
	}
	public String getActual_ut() {
		return actual_ut;
	}
	public void setActual_ut(String actual_ut) {
		this.actual_ut = actual_ut;
	}
	public Integer getDispatchNo() {
		return dispatchNo;
	}
	public void setDispatchNo(Integer dispatchNo) {
		this.dispatchNo = dispatchNo;
	}
	public String getVehicle_no() {
		return vehicle_no;
	}
	public void setVehicle_no(String vehicle_no) {
		this.vehicle_no = vehicle_no;
	}
	public Date getVehicle_dt() {
		return vehicle_dt;
	}
	public void setVehicle_dt(Date vehicle_dt) {
		this.vehicle_dt = vehicle_dt;
	}
	public String getCreate_ui() {
		return create_ui;
	}
	public void setCreate_ui(String create_ui) {
		this.create_ui = create_ui;
	}
	public String getUpdate_ui() {
		return update_ui;
	}
	public void setUpdate_ui(String update_ui) {
		this.update_ui = update_ui;
	}
	public Date getCreate_ts() {
		return create_ts;
	}
	public void setCreate_ts(Date create_ts) {
		this.create_ts = create_ts;
	}
	public Date getUpdate_ts() {
		return update_ts;
	}
	public void setUpdate_ts(Date update_ts) {
		this.update_ts = update_ts;
	}
	public Integer getDispatch_detail_id() {
		return dispatch_detail_id;
	}
	public void setDispatch_detail_id(Integer dispatch_detail_id) {
		this.dispatch_detail_id = dispatch_detail_id;
	}
	public Integer getDelivered_quantity() {
		return delivered_quantity;
	}
	public void setDelivered_quantity(Integer delivered_quantity) {
		this.delivered_quantity = delivered_quantity;
	}
	public String getHandled_by() {
		return handled_by;
	}
	public void setHandled_by(String handled_by) {
		this.handled_by = handled_by;
	}
	
	
}