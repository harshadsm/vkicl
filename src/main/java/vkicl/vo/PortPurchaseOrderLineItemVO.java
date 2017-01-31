package vkicl.vo;

public class PortPurchaseOrderLineItemVO {

	private Integer portInwardDetailId;

	private Integer portInwardId;

	private Integer orderedQuantity;

	private Integer length;

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

	private Integer width;
	private Double thickness;

	public Integer getPortInwardDetailId() {
		return portInwardDetailId;
	}

	public void setPortInwardDetailId(Integer portInwardDetailId) {
		this.portInwardDetailId = portInwardDetailId;
	}

	public Integer getPortInwardId() {
		return portInwardId;
	}

	public void setPortInwardId(Integer portInwardId) {
		this.portInwardId = portInwardId;
	}

	public Integer getOrderedQuantity() {
		return orderedQuantity;
	}

	public void setOrderedQuantity(Integer orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}

}
