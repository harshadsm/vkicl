package vkicl.vo;

import java.util.Date;

public class PortOutwardShipmentVO {
	
	private Integer port_out_shipment_id;
	private String warehouse_name;
	private String customer_name;
	private String vehicle_number;
	private String vehicle_date;
	private String create_ui;
	private String update_ui;
	private String create_ts;
	private String update_ts;
	private String invoice;
	private Integer warehouse_inward_flag;
	private Double actual_weight;
	public Integer getPort_out_shipment_id() {
		return port_out_shipment_id;
	}
	public void setPort_out_shipment_id(Integer port_out_shipment_id) {
		this.port_out_shipment_id = port_out_shipment_id;
	}
	public String getWarehouse_name() {
		return warehouse_name;
	}
	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getVehicle_number() {
		return vehicle_number;
	}
	public void setVehicle_number(String vehicle_number) {
		this.vehicle_number = vehicle_number;
	}
	public String getVehicle_date() {
		return vehicle_date;
	}
	public void setVehicle_date(String vehicle_date) {
		this.vehicle_date = vehicle_date;
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
	public String getCreate_ts() {
		return create_ts;
	}
	public void setCreate_ts(String create_ts) {
		this.create_ts = create_ts;
	}
	public String getUpdate_ts() {
		return update_ts;
	}
	public void setUpdate_ts(String update_ts) {
		this.update_ts = update_ts;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public Integer getWarehouse_inward_flag() {
		return warehouse_inward_flag;
	}
	public void setWarehouse_inward_flag(Integer warehouse_inward_flag) {
		this.warehouse_inward_flag = warehouse_inward_flag;
	}
	public Double getActual_weight() {
		return actual_weight;
	}
	public void setActual_weight(Double actual_weight) {
		this.actual_weight = actual_weight;
	}
	
	
}
