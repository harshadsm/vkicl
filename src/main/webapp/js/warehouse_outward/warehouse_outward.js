var WAREHOUSE_OUTWARD = {
		warehouseOutwardDetails : []
};


function addWarehouseOutwardLineItem(selectedStockId){
	
	//console.log("selectedStockId = "+selectedStockId);
	
	//GET SELECTED DISPATCH DETAIL ROW
	var selectedDispatchDetailRow = getSelectedDispatchDetailsRow();
	var dispatchDetailsID = selectedDispatchDetailRow.dispatchDetailsID;
	var orderedQuantity = Number(selectedDispatchDetailRow.orderedQuantity);
	var sectionWeight = Number(selectedDispatchDetailRow.actWt);
	
	//Get Selected Stock Row
	var SELECTED_STOCK_LINE_ITEM = composeStockLineItemObjectForStockJqgridRowId(selectedStockId);
	
	
	var DISPATCH_LINE_ITEM = getFromCache_WAREHOUSE_OUTWARD(dispatchDetailsID);
	
	if(DISPATCH_LINE_ITEM){
		//Update the existing DISPATCH_LINE_ITEM
		console.log("Item already in cache. Simply update the selected stock items and quantityBeingDelivered");
		
		//If stockItem is not in the cache only then add it, else just ignore
		if(!isStockItemAlreadySelected(SELECTED_STOCK_LINE_ITEM.stockId, DISPATCH_LINE_ITEM.selectedStockLineItems)){
			var newQuantityBeingDelivered = DISPATCH_LINE_ITEM.quantityBeingDelivered +  SELECTED_STOCK_LINE_ITEM.stockQuantityForDelivery;
			if(DISPATCH_LINE_ITEM.orderedQuantity){
				if(newQuantityBeingDelivered > DISPATCH_LINE_ITEM.orderedQuantity){
					bootbox.alert(
							"Delivery is more than the ordered quantity. " 
							+ "Ordered Quantity = "+DISPATCH_LINE_ITEM.orderedQuantity
							+ " & Delivery Quantity = " + newQuantityBeingDelivered
							);
				}
			}
			DISPATCH_LINE_ITEM.quantityBeingDelivered = newQuantityBeingDelivered;
			DISPATCH_LINE_ITEM.selectedStockLineItems.push(SELECTED_STOCK_LINE_ITEM);
			
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
				selectedStockLineItems : [],
				sectionWeight : sectionWeight
		};
		
		DISPATCH_LINE_ITEM.selectedStockLineItems.push(SELECTED_STOCK_LINE_ITEM);
		WAREHOUSE_OUTWARD.warehouseOutwardDetails.push(DISPATCH_LINE_ITEM);
	}

	refreshWarehouseOutwardTable();
}

function removeWarehouseOutwardLineItem(stockId){
	stockId = Number(stockId);
	console.log("Going to remove stockId = "+stockId);
	//var selectedDispatchDetailRow = getSelectedDispatchDetailsRow();
	//var dispatchDetailsID = selectedDispatchDetailRow.dispatchDetailsID;
	//console.log("Going to remove this stock item from dispatch details id = "+dispatchDetailsID);
	for(var i=0; i < WAREHOUSE_OUTWARD.warehouseOutwardDetails.length; i++ ){
		var DISPATCH_LINE_ITEM = WAREHOUSE_OUTWARD.warehouseOutwardDetails[i];//getFromCache_WAREHOUSE_OUTWARD(dispatchDetailsID);
		
		if(DISPATCH_LINE_ITEM){
			
			if(DISPATCH_LINE_ITEM.selectedStockLineItems && DISPATCH_LINE_ITEM.selectedStockLineItems.length > 0){
				console.log("------------------------------- "+i);
				var stockItemsAfterRemoval = [];
				for(var j=0; j < DISPATCH_LINE_ITEM.selectedStockLineItems.length; j++){
					
					var selectedStockLineItem = DISPATCH_LINE_ITEM.selectedStockLineItems[j];
					console.log(selectedStockLineItem.stockId+" == "+stockId);
					//var SELECTED_STOCK_LINE_ITEM = composeStockLineItemObjectForStockJqgridRowId(stockId);
					
					if(selectedStockLineItem && selectedStockLineItem.stockId != stockId){
						stockItemsAfterRemoval.push(selectedStockLineItem);
					}else{
						console.log("It will be ignored hence removed. "+stockId);
						var stockQuantityToSubtract = Number(selectedStockLineItem.stockQuantityForDelivery);
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
	refreshWarehouseOutwardTable();
	
}

function refreshWarehouseOutwardTable(){
	$("#selectedStockItemsTable tbody").empty();
	if(WAREHOUSE_OUTWARD.warehouseOutwardDetails.length > 0){
		for(var i=0;i<WAREHOUSE_OUTWARD.warehouseOutwardDetails.length;i++){
			var DISPATCH_ITEM = WAREHOUSE_OUTWARD.warehouseOutwardDetails[i];
			if(DISPATCH_ITEM && DISPATCH_ITEM.selectedStockLineItems){
				for(var j=0;j<DISPATCH_ITEM.selectedStockLineItems.length; j++){
					var SELECTED_STOCK_ITEM = DISPATCH_ITEM.selectedStockLineItems[j];
					//console.log(SELECTED_STOCK_ITEM);
					
					var tr = composeSelectedStockTr(DISPATCH_ITEM, SELECTED_STOCK_ITEM);
					//console.log(tr);
					$("#selectedStockItemsTable tbody").append(tr);
				}
			}
		}
	}
	refreshStockOutwardTotalRow();
}

function composeSelectedStockTr(DISPATCH_ITEM, SELECTED_STOCK_ITEM){
	var ssw = "<td class='text-center section-weight-selected-stock-items'>";
	var sqd = "<td class='text-center quantity-for-delivery-selected-stock-items'>";
	var s = "<td class='text-center'>";
	var e = "</td>";
	var tr = "<tr>" 
		+ s + DISPATCH_ITEM.dispatchDetailId + e
		+ s + SELECTED_STOCK_ITEM.stockId + e
		+ s + SELECTED_STOCK_ITEM.millName + e
		+ s + SELECTED_STOCK_ITEM.make + e
		+ s + SELECTED_STOCK_ITEM.grade + e
		+ s + SELECTED_STOCK_ITEM.thickness + e
		+ s + SELECTED_STOCK_ITEM.width + e
		+ s + SELECTED_STOCK_ITEM.length + e
		+ ssw + SELECTED_STOCK_ITEM.sectionWeight + e
		+ sqd + SELECTED_STOCK_ITEM.stockQuantityForDelivery + e
		+ s + "<button onClick='cancelStockItemFromDispatch("+SELECTED_STOCK_ITEM.stockId+")'>Remove</button>" + e
		+ "</tr>";
	return tr;
}


function cancelStockItemFromDispatch(stockId){
	console.log("cancelled stockId = "+stockId);
	selectUnselectStockItemInJqgrid(stockId, false);
	
	//Since the onSelectRow does not trigger when the row is selected/unselected using setSelection() API
	//We need to call it explicitly.
	//https://github.com/tonytomov/jqGrid/issues/454
	removeWarehouseOutwardLineItem(stockId);		

}

function isStockItemAlreadySelected(stockId, cachedStockItemsForDispatch){
	var isAlreadySelected = false;
	
	for(var i=0; i<WAREHOUSE_OUTWARD.warehouseOutwardDetails.length ; i++){
		var dispatchLineItem = WAREHOUSE_OUTWARD.warehouseOutwardDetails[i];
		var selectedStockLineItems = dispatchLineItem.selectedStockLineItems;
		if(selectedStockLineItems){
			for(var j=0; j<selectedStockLineItems.length; j++){
				var selectedStockLineItem = selectedStockLineItems[j];
				if(stockId){
					if(selectedStockLineItem.stockId == stockId){
						isAlreadySelected = true;
						break;
					}
				}
			}
		}
		
	}
	

	console.log(stockId +" = stock id already Selected = "+isAlreadySelected);
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
	var length = Number(selectedStockRow.length);
	var width = Number(selectedStockRow.width);
	var thickness = Number(selectedStockRow.thickness);
	var millName = selectedStockRow.millName;
	var make = selectedStockRow.make;
	var grade = selectedStockRow.grade;
	var heatNo = selectedStockRow.heat_no;
	var plateNo = selectedStockRow.plate_no;
	var location = selectedStockRow.location;
	var quantity = selectedStockRow.quantity;
	var sectionWeight = selectedStockRow.actWt;
	
	
	//Object definition of selectedStockLineItem
	var SELECTED_STOCK_LINE_ITEM = {
			stockId : selectedStockDbId,
			stockQuantityForDelivery : stockQuantity,
			
			//Below items are for showing in the table.
			thickness : thickness,
			length : length,
			width : width,
			millName : millName,
			make : make,
			grade : grade,
			heateNo : heatNo,
			plateNo : plateNo,
			location : location,
			quantity : quantity,
			sectionWeight : sectionWeight
	};
	return SELECTED_STOCK_LINE_ITEM;
}


function preselectRowsAsPer_WAREHOUSE_OUTWARD_cache(){
	console.log("Going to preselect the stock items");
	var dispatchLineItems = WAREHOUSE_OUTWARD.warehouseOutwardDetails;
	console.log(dispatchLineItems);
	if(dispatchLineItems && dispatchLineItems.length > 0){
		for(var dli=0; dli < dispatchLineItems.length; dli++){
			var dispatchLineItem = dispatchLineItems[dli];
			console.log("--Preselecting for dispatch id = "+dispatchLineItem.dispatchDetailId);
			if(dispatchLineItem && dispatchLineItem.selectedStockLineItems && dispatchLineItem.selectedStockLineItems.length > 0){
				for(var si=0; si < dispatchLineItem.selectedStockLineItems.length; si++){
					var stockItem = dispatchLineItem.selectedStockLineItems[si];
					console.log("Preselecting stock id = "+stockItem.stockId);
					
					selectUnselectStockItemInJqgrid(stockItem.stockId, true);
					
				}
			}
		}
	}
}

function selectUnselectStockItemInJqgrid(stockId, selectUnselect){
	
	console.log("Going to preselect = "+stockId);
	
	var ids = $("#stockTable").jqGrid('getDataIDs');
	var $grid = $("#stockTable");
	for(var i=0;i < ids.length;i++){ 
		var rowObject = $grid.jqGrid('getRowData',ids[i]); 
		var jqgridRowStockBalId = Number(rowObject.stockBalId);
		//console.log(jqgridRowStockBalId);
		if(jqgridRowStockBalId == stockId){
			
			var selectionRow = i + 1;
			//console.log("ROW ID >>>> "+selectionRow);
			//console.log("SELECTED/UNSELECTED >>>> "+selectUnselect);
			
			var isAlreadySelected =  isRowSelected("#stockTable",selectionRow);
			if(selectUnselect && !isAlreadySelected){
				$grid.setSelection(selectionRow, selectUnselect);
			}
			
			if(!selectUnselect){
				$grid.setSelection(selectionRow, selectUnselect);
			}
			
			
		}
		
		
	} 
	
	
}

function isRowSelected(gridId, rowId){
	var isSelected = false;
	var selRowIds = $(gridId).jqGrid("getGridParam", "selarrrow");
	if ($.inArray(rowId, selRowIds) >= 0) {
	    isSelected = true;
	}
	return isSelected;
}

function submitWarehouseOutward(){
	var dispatchOrderId = $("#dispatchOrderId").val();
	var vehicleNumber = $("#vehicleNumber").val();
	var vehicleDate = $("#vehicleDate").val();
	WAREHOUSE_OUTWARD.dispatchOrderId = dispatchOrderId;
	WAREHOUSE_OUTWARD.vehicleNumber = vehicleNumber;
	WAREHOUSE_OUTWARD.vehicleDate = vehicleDate;
	
	
	
	if(vehicleNumber!="" && vehicleDate!="" && WAREHOUSE_OUTWARD.warehouseOutwardDetails.length > 0){
		var WAREHOUSE_OUTWARD_JSON = JSON.stringify(WAREHOUSE_OUTWARD);
		
		$.ajax({
			url : "./submitWarehouseOutward",
			type : "POST",
			data : {WAREHOUSE_OUTWARD_JSON:WAREHOUSE_OUTWARD_JSON},
			success : function(resp){
				console.log(resp);
				var result = JSON.parse(resp);
				if(result.status == 'success'){
					window.location.assign("./warehouse-dispatch-report.do");
				}else{
					bootbox.alert("Error 3243. Failed to process warehouse outward. ServerFault="+result.errorMessage);
				}
			},
			error : function(resp){
				console.log(resp);
				bootbox.alert("Error 3244. Faiiled to process. ");
			}
		});
	}else{
		bootbox.alert("Please fill in Vehicle Number, Vehicle Date and select the items to deliver.");
	}
	

}

function calculateSectionWeightTotal(){
	var sectionWeightTotal = 0;
	$(".section-weight-selected-stock-items").each(function(i,elem){
		var $elem = $(elem);
		var val = Number($elem.html());
		sectionWeightTotal = sectionWeightTotal + val;
		
	});
	console.log("------------------- secwttotal");
	console.log(sectionWeightTotal);
	
	sectionWeightTotal = $.number(sectionWeightTotal,3,'.','');
	console.log(sectionWeightTotal);
	return sectionWeightTotal;
}


function calculateQuantityForDeliveryTotal(){
	var quantityForDeliveryTotal = 0;
	
	console.log();
	$(".quantity-for-delivery-selected-stock-items").each(function(i,elem){
		var $elem = $(elem);
		var val = Number($elem.html());
		quantityForDeliveryTotal = quantityForDeliveryTotal + val;
	});
	return quantityForDeliveryTotal;
}
function refreshStockOutwardTotalRow(){
	var quantityForDeliveryTotal = calculateQuantityForDeliveryTotal();
	var sectionWeightTotal = calculateSectionWeightTotal();
	
	var foot = $("#selectedStockItemsTable").find('tfoot');
	 foot.empty();
	 var tr = "<tr>";
//	 tr = tr + "<td>" + "</td>";
//	 tr = tr + "<td>" + "</td>";
//	 tr = tr + "<td>" + "</td>";
//	 tr = tr + "<td>" + "</td>";
//	 tr = tr + "<td>" + "</td>";
//	 tr = tr + "<td>" + "</td>";
//	 tr = tr + "<td>" + "</td>";
	 tr = tr + "<td class='text-right' colspan='8'>" +" TOTAL " +"</td>";
	 tr = tr + "<td class='text-center'>" + sectionWeightTotal + "</td>";
	 tr = tr + "<td class='text-center'>" + quantityForDeliveryTotal + "</td>";
	 tr = tr + "<td>" + "</td>";
	 tr = tr + "</tr>";
	 foot.append(tr);
}