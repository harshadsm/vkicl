package vkicl.vo;

public class PortPurchaseOrderLineItemVO {

	private Integer portInwardDetailId;

	private Integer portInwardId;

	private Integer ppoNo;

	private Integer ppoLineItemNo;

	private Integer orderedQuantity;

	private Integer length;

	private Integer width;
	private Double thickness;

	public Integer getPpoLineItemNo() {
		return ppoLineItemNo;
	}

	public void setPpoLineItemNo(Integer ppoLineItemNo) {
		this.ppoLineItemNo = ppoLineItemNo;
	}

	public Integer getPpoNo() {
		return ppoNo;
	}

	public void setPpoNo(Integer ppoNo) {
		this.ppoNo = ppoNo;
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
