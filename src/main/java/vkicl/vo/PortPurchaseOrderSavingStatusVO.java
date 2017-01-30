package vkicl.vo;

public class PortPurchaseOrderSavingStatusVO {

	private String status;
	private Long portPurchaseOrderId;
	private String errorMessage;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getPortPurchaseOrderId() {
		return portPurchaseOrderId;
	}
	public void setPortPurchaseOrderId(Long portPurchaseOrderId) {
		this.portPurchaseOrderId = portPurchaseOrderId;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	
}
