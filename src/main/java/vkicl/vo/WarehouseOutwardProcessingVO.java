package vkicl.vo;

import java.util.List;

public class WarehouseOutwardProcessingVO {

	private Integer dispatchOrderId;
	private String vehicleNumber;
	private String vehicleDate;
	
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

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getVehicleDate() {
		return vehicleDate;
	}

	public void setVehicleDate(String vehicleDate) {
		this.vehicleDate = vehicleDate;
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
