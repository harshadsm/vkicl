package vkicl.vo;

import java.util.List;

public class DispatchOrderLineItemForProcessingVO {

	private Integer dispatchDetailId;
	private Integer orderedQuantity;
	private Integer quantityBeingDelivered;
	private List<SelectedStockItemForOutwardVO> selectedStockLineItems;
	public Integer getDispatchDetailId() {
		return dispatchDetailId;
	}
	public void setDispatchDetailId(Integer dispatchDetailId) {
		this.dispatchDetailId = dispatchDetailId;
	}
	public Integer getOrderedQuantity() {
		return orderedQuantity;
	}
	public void setOrderedQuantity(Integer orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}
	public Integer getQuantityBeingDelivered() {
		return quantityBeingDelivered;
	}
	public void setQuantityBeingDelivered(Integer quantityBeingDelivered) {
		this.quantityBeingDelivered = quantityBeingDelivered;
	}
	public List<SelectedStockItemForOutwardVO> getSelectedStockLineItems() {
		return selectedStockLineItems;
	}
	public void setSelectedStockLineItems(List<SelectedStockItemForOutwardVO> selectedStockLineItems) {
		this.selectedStockLineItems = selectedStockLineItems;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(SelectedStockItemForOutwardVO s : selectedStockLineItems){
			sb.append("\t-->> ").append(s.toString());
		}
		return "DispatchOrderLineItemForProcessingVO [dispatchDetailId=" + dispatchDetailId + ", orderedQuantity="
				+ orderedQuantity 
				+ ", quantityBeingDelivered=" + quantityBeingDelivered
				+ ", stocksItems = ["+sb.toString()+"]"
				+ "]";
	}
	
	
}
