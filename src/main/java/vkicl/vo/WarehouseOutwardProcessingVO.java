package vkicl.vo;

import java.util.List;

public class WarehouseOutwardProcessingVO {

	private List<DispatchOrderLineItemForProcessingVO> warehouseOutwardDetails;

	public List<DispatchOrderLineItemForProcessingVO> getWarehouseOutwardDetails() {
		return warehouseOutwardDetails;
	}

	public void setWarehouseOutwardDetails(List<DispatchOrderLineItemForProcessingVO> warehouseOutwardDetails) {
		this.warehouseOutwardDetails = warehouseOutwardDetails;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(DispatchOrderLineItemForProcessingVO vo : warehouseOutwardDetails){
			sb.append(vo.toString()).append("\n");
		}
		return sb.toString();
	}
	
	
}
