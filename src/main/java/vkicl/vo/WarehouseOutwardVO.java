package vkicl.vo;

public class WarehouseOutwardVO {
	private Integer stockId;
	private Integer availableQty;
	private Integer orderedQty;
	private Integer dispatchDetailId;
	private Integer dispatchedQty;

	
	public Integer getDispatchDetailId() {
		return dispatchDetailId;
	}

	public void setDispatchDetailId(Integer dispatchDetailId) {
		this.dispatchDetailId = dispatchDetailId;
	}

	public Integer getDispatchedQty() {
		return dispatchedQty;
	}

	public void setDispatchedQty(Integer dispatchedQty) {
		this.dispatchedQty = dispatchedQty;
	}

	public Integer getStockId() {
		return stockId;
	}

	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	public Integer getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(Integer availableQty) {
		this.availableQty = availableQty;
	}

	public Integer getOrderedQty() {
		return orderedQty;
	}

	public void setOrderedQty(Integer orderedQty) {
		this.orderedQty = orderedQty;
	}

}
