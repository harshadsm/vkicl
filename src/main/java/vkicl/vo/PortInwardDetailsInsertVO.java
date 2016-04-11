package vkicl.vo;

import java.util.Date;

public class PortInwardDetailsInsertVO {

	private Integer port_inward_detail_id;
	private Integer port_inward_id;
	private Integer length;
	private Integer width;
	private Double thickness;
	private Double be_weight;
	private String be_wt_unit;
	private Integer quantity;
	private String create_ui;
	private String update_ui;
	private Date create_ts;
	private Date update_ts;
	public Integer getPort_inward_detail_id() {
		return port_inward_detail_id;
	}
	public void setPort_inward_detail_id(Integer port_inward_detail_id) {
		this.port_inward_detail_id = port_inward_detail_id;
	}
	public Integer getPort_inward_id() {
		return port_inward_id;
	}
	public void setPort_inward_id(Integer port_inward_id) {
		this.port_inward_id = port_inward_id;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Double getThickness() {
		return thickness;
	}
	public void setThickness(Double thickness) {
		this.thickness = thickness;
	}
	public Double getBe_weight() {
		return be_weight;
	}
	public void setBe_weight(Double be_weight) {
		this.be_weight = be_weight;
	}
	public String getBe_wt_unit() {
		return be_wt_unit;
	}
	public void setBe_wt_unit(String be_wt_unit) {
		this.be_wt_unit = be_wt_unit;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
	@Override
	public String toString() {
		return "PortInwardDetailsInsertVO [port_inward_detail_id=" + port_inward_detail_id + ", port_inward_id="
				+ port_inward_id + ", length=" + length + ", width=" + width + ", thickness=" + thickness
				+ ", be_weight=" + be_weight + ", be_wt_unit=" + be_wt_unit + ", quantity=" + quantity + ", create_ui="
				+ create_ui + ", update_ui=" + update_ui + ", create_ts=" + create_ts + ", update_ts=" + update_ts
				+ "]";
	}
	
	
}
