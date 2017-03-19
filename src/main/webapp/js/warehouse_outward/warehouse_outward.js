var WAREHOUSE_OUTWARD = {
		warehouseOutwardDetails : []
};

function addWarehouseOutwardLineItem(selectedStockId){
	
	console.log("selectedStockId = "+selectedStockId);
	
	//GET SELECTED DISPATCH DETAIL ROW
	var selectedDispatchDetailRowId =$("#dispatchDetailsTable").jqGrid('getGridParam','selrow');  
	var selectedDispatchDetailRow = $("#dispatchDetailsTable").getRowData(selectedDispatchDetailRowId);
	console.log(selectedDispatchDetailRow);
	var dispatchDetailsID = selectedDispatchDetailRow.dispatchDetailsID;
	
	//Get Selected Stock Row
	var selectedStockRow = $("#stockTable").getRowData(selectedStockId);
	var selectedStockDbId = selectedStockRow.stockBalId;
	console.log(selectedStockDbId);
	
	var DISPATCH_LINE_ITEM = getFromCache_WAREHOUSE_OUTWARD(dispatchDetailsID);
	
	var DISPATCH_LINE_ITEM = {
			dispatchDetailId : dispatchDetailsID,
			selectedStockLineItems : []
	};
	
	
	
	
}

function removeWarehouseOutwardLineItem(dispatchDetailId, stockId){
	
}

function refreshWarehouseOutwardTable(){
	
}

function isStockItemAlreadySelected(){
	
}

function getFromCache_WAREHOUSE_OUTWARD(dispatchDetailsID){
	console.log("searching dispatchDetailsID = "+dispatchDetailsID);
	
	if(WAREHOUSE_OUTWARD.warehouseOutwardDetails && WAREHOUSE_OUTWARD.warehouseOutwardDetails.length > 0){
		for(var i=0;i<WAREHOUSE_OUTWARD.warehouseOutwardDetails.length;i++){
			var dispatchDetailLineItem = WAREHOUSE_OUTWARD.warehouseOutwardDetails[i];
			console.log(dispatchDetailLineItem);
		}	
	}else{
		console.log("No selected item in cache");
	}
	
}

