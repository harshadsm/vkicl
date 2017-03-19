var WAREHOUSE_OUTWARD = {
		warehouseOutwardDetails : []
};


function addWarehouseOutwardLineItem(selectedStockId){
	
	//console.log("selectedStockId = "+selectedStockId);
	
	//GET SELECTED DISPATCH DETAIL ROW
	var selectedDispatchDetailRow = getSelectedDispatchDetailsRow();
	var dispatchDetailsID = selectedDispatchDetailRow.dispatchDetailsID;
	var orderedQuantity = Number(selectedDispatchDetailRow.orderedQuantity);
	
	//Get Selected Stock Row
	var SELECTED_STOCK_LINE_ITEM = composeStockLineItemObjectForStockJqgridRowId(selectedStockId);
	
	
	var DISPATCH_LINE_ITEM = getFromCache_WAREHOUSE_OUTWARD(dispatchDetailsID);
	
	if(DISPATCH_LINE_ITEM){
		//Update the existing DISPATCH_LINE_ITEM
		console.log("Item already in cache. Simply update the selected stock items and quantityBeingDelivered");
		
		//If stockItem is not in the cache only then add it, else just ignore
		if(!isStockItemAlreadySelected(SELECTED_STOCK_LINE_ITEM.stockId, DISPATCH_LINE_ITEM.selectedStockLineItems)){
			DISPATCH_LINE_ITEM.selectedStockLineItems.push(SELECTED_STOCK_LINE_ITEM);
			DISPATCH_LINE_ITEM.quantityBeingDelivered = DISPATCH_LINE_ITEM.quantityBeingDelivered +  SELECTED_STOCK_LINE_ITEM.stockQuantityForDelivery;
		}else{
			//Just ignore. Because the stock item is already in the cache.
			console.log("Just ignore. Because the stock item is already in the cache.");
		}
			
	}else{
		//Object definition of DISPATCH_LINE_ITEM 
		DISPATCH_LINE_ITEM = {
				dispatchDetailId : dispatchDetailsID,
				orderedQuantity : orderedQuantity,
				quantityBeingDelivered : SELECTED_STOCK_LINE_ITEM.stockQuantityForDelivery,
				selectedStockLineItems : []
		};
		
		DISPATCH_LINE_ITEM.selectedStockLineItems.push(SELECTED_STOCK_LINE_ITEM);
		WAREHOUSE_OUTWARD.warehouseOutwardDetails.push(DISPATCH_LINE_ITEM);
	}

}

function removeWarehouseOutwardLineItem(stockId){
	stockId = Number(stockId);
	console.log("Going to remove stockId = "+stockId);
	var selectedDispatchDetailRow = getSelectedDispatchDetailsRow();
	var dispatchDetailsID = selectedDispatchDetailRow.dispatchDetailsID;
	console.log("Going to remove this stock item from dispatch details id = "+dispatchDetailsID);
	
	var DISPATCH_LINE_ITEM = getFromCache_WAREHOUSE_OUTWARD(dispatchDetailsID);
	
	if(DISPATCH_LINE_ITEM){
		
		if(DISPATCH_LINE_ITEM.selectedStockLineItems && DISPATCH_LINE_ITEM.selectedStockLineItems.length > 0){
			var stockItemsAfterRemoval = [];
			for(var i=0; i < DISPATCH_LINE_ITEM.selectedStockLineItems.length; i++){
				
				var selectedStockLineItem = DISPATCH_LINE_ITEM.selectedStockLineItems[i];
				console.log(selectedStockLineItem.stockId+" == "+stockId);
				var SELECTED_STOCK_LINE_ITEM = composeStockLineItemObjectForStockJqgridRowId(stockId);
				
				if(selectedStockLineItem && selectedStockLineItem.stockId != SELECTED_STOCK_LINE_ITEM.stockId){
					stockItemsAfterRemoval.push(selectedStockLineItem);
				}else{
					console.log("It will be ignored hence removed. "+stockId);
					console.log(SELECTED_STOCK_LINE_ITEM);
					var stockQuantityToSubtract = Number(SELECTED_STOCK_LINE_ITEM.stockQuantityForDelivery);
					var newQuantityBeingDelivered = DISPATCH_LINE_ITEM.quantityBeingDelivered - stockQuantityToSubtract ;
					console.log(DISPATCH_LINE_ITEM.quantityBeingDelivered);
					console.log(stockQuantityToSubtract);
					console.log(newQuantityBeingDelivered);
					DISPATCH_LINE_ITEM.quantityBeingDelivered = newQuantityBeingDelivered;
				}
			}
			
			console.log(stockItemsAfterRemoval);
			DISPATCH_LINE_ITEM.selectedStockLineItems = stockItemsAfterRemoval;
		}
	}
	
}

function refreshWarehouseOutwardTable(){
	
}

function isStockItemAlreadySelected(stockId, cachedStockItemsForDispatch){
	var isAlreadySelected = false;
	if(stockId && cachedStockItemsForDispatch){
		if(cachedStockItemsForDispatch.length > 0){
			for(var i=0;i < cachedStockItemsForDispatch.length; i++ ){
				var cachedItem = cachedStockItemsForDispatch[i];
				if(cachedItem.stockId == stockId){
					isAlreadySelected = true;
					break;
				}
			}
		}
	}
	console.log();
	return isAlreadySelected;
}

function getFromCache_WAREHOUSE_OUTWARD(dispatchDetailsID){
	var DISPATCH_ITEM_FROM_CACHE = null;
	console.log("searching dispatchDetailsID = "+dispatchDetailsID);
	
	if(WAREHOUSE_OUTWARD.warehouseOutwardDetails && WAREHOUSE_OUTWARD.warehouseOutwardDetails.length > 0){
		for(var i=0;i<WAREHOUSE_OUTWARD.warehouseOutwardDetails.length;i++){
			var dispatchDetailLineItem = WAREHOUSE_OUTWARD.warehouseOutwardDetails[i];
			console.log(dispatchDetailLineItem);
			if(dispatchDetailLineItem.dispatchDetailId == dispatchDetailsID){
				DISPATCH_ITEM_FROM_CACHE = dispatchDetailLineItem;
				break;
			}else{
				//Nothing
			}
		}	
	}else{
		console.log("No selected item in cache");
	}
	return DISPATCH_ITEM_FROM_CACHE;
}

function getSelectedDispatchDetailsRow(){
	var selectedDispatchDetailRowId =$("#dispatchDetailsTable").jqGrid('getGridParam','selrow');  
	var selectedDispatchDetailRow = $("#dispatchDetailsTable").getRowData(selectedDispatchDetailRowId);
	return selectedDispatchDetailRow;
}


function composeStockLineItemObjectForStockJqgridRowId(selectedStockTableRowId){
	var selectedStockRow = $("#stockTable").getRowData(selectedStockTableRowId);
	var selectedStockDbId = Number(selectedStockRow.stockBalId);
	var stockQuantity = Number(selectedStockRow.quantity);
	
	//Object definition of selectedStockLineItem
	var SELECTED_STOCK_LINE_ITEM = {
			stockId : selectedStockDbId,
			stockQuantityForDelivery : stockQuantity
	};
	return SELECTED_STOCK_LINE_ITEM;
}


function preselectRowsAsPer_WAREHOUSE_OUTWARD_cache(){
	console.log("Going to preselect the stock items");
	var dispatchLineItems = WAREHOUSE_OUTWARD.warehouseOutwardDetails;
	if(dispatchLineItems && dispatchLineItems.length > 0){
		for(var dli=0; dli < dispatchLineItems.length; dli++){
			var dispatchLineItem = dispatchLineItems[dli];
			if(dispatchLineItem && dispatchLineItem.selectedStockLineItems && dispatchLineItem.selectedStockLineItems.length > 0){
				for(var si=0; si < dispatchLineItem.selectedStockLineItems.length; si++){
					var stockItem = dispatchLineItem.selectedStockLineItems[si];
					console.log(stockItem);
					
					selectStockItemInJqgrid(stockItem);
					
				}
			}
		}
	}
}

function selectStockItemInJqgrid(stockItem){
	
	console.log("Going to preselect = "+stockItem.stockId);
	
	var ids = $("#stockTable").jqGrid('getDataIDs');
	var $grid = $("#stockTable");
	for(var i=0;i < ids.length;i++){ 
		var rowObject = $grid.jqGrid('getRowData',ids[i]); 
		var jqgridRowStockBalId = Number(rowObject.stockBalId);
		//console.log(jqgridRowStockBalId);
		if(jqgridRowStockBalId == stockItem.stockId){
			
			var selectionRow = i + 1;
			console.log("SELECTED >>>> "+selectionRow);
			$grid.setSelection(selectionRow, true);
		}
		
		
	} 
}