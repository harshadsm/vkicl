package vkicl.vo;

import java.util.List;

public class WarehouseOutwardProcessingVO {

	private Integer dispatchOrderId;
	
	private List<DispatchOrderLineItemForProcessingVO> warehouseOutwardDetails;

	public List<DispatchOrderLineItemForProcessingVO> getWarehouseOutwardDetails() {
		return warehouseOutwardDetails;
	}

	public void setWarehouseOutwardDetails(List<DispatchOrderLineItemForProcessingVO> warehouseOutwardDetails) {
		this.warehouseOutwardDetails = warehouseOutwardDetails;
	}

	public Integer getDispatchOrderId() {
		return dispatchOrderId;
	}

	public void setDispatchOrderId(Integer dispatchOrderId) {
		this.dispatchOrderId = dispatchOrderId;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(DispatchOrderLineItemForProcessingVO vo : warehouseOutwardDetails){
			sb.append(vo.toString()).append("\n");
		}
		return "dispatchOrderId = "+dispatchOrderId+" = "+sb.toString();
	}
	
	
}
