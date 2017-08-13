

<div class="col-md-12">
	<div>
		<div id="packingListTable">
			<table id="packingListGrid"></table>
			<div id="packingListPager"></div>
		</div>
	</div>
</div>

<script>
$(function(){
	

console.log("Going to jqgrid");
$("#packingListGrid").jqGrid(
		{
			url : './listAllPortDeliveryNotesJsonServlet',
			editurl : './actualWeightUpdateAtOutwardServlet',
			datatype : 'json',
			
			mtype : 'POST',


			colNames : [ 
				'id',
				'delivery Note Address',
				'Port Purchase Order Id',
				'vehicle Number',
				'vehicle Date',
				'invoice',
				'actual Weight'
				 ],

					
			colModel : [  {
				name : 'id',
				index : 'id',
				hidden: false,
				width : 185,
				editable : false,
				editrules : {
					required : true
				},
				editoptions : {
					size : 10
				},
				search:false,
				searchoptions: { sopt:['ge']}
			},{
				name : 'deliveryNoteAddress',
				index : 'deliveryNoteAddress',
				hidden: false,
				width : 185,
				editable : false,
				editrules : {
					required : true
				},
				editoptions : {
					size : 10
				},
				search:false,
				searchoptions: { sopt:['ge']}
			},{
				name : 'portPurchaseOrderId',
				index : 'portPurchaseOrderId',
				hidden: false,
				width : 155,
				editable : false,
				editrules : {
					required : true
				},
				editoptions : {
					size : 10
				},
				search:false
				
			}, {
				name : 'vehicleNumber',
				index : 'vehicleNumber',
				width : 300,
				hidden:false,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center',
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			},{
				name : 'vehicleDate',
				index : 'vehicleDate',
				width : 300,
				hidden : false,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center',
				sortable:true,
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'invoice',
				index : 'invoice',
				width : 300,
				hidden : false,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center',
				sortable:true,
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			}, {
				name : 'actualWeight',
				index : 'actualWeight',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center',
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			}
			],
			postData : {
			},
			cellEdit: true,
			cellsubmit : 'remote',
			cellurl : './actualWeightUpdateAtOutwardServlet',
			rowNum : 10,
			rowList : [ 10, 20, 30 ,40, 50, 60 ],
			height : 280,
			autowidth : true,
			rownumbers : true,
			multiselect : false,
			pager : '#packingListPager',
			sortname : 'port_out_shipment_id',
			viewrecords : true,
			sortorder : "desc",
			
			caption : "Actual Weight of the shipments sent out from Warehouse ddd",
			emptyrecords : "Empty records",
			loadonce : false,
			loadComplete : function() {
			},
			jsonReader : {
				root : "rows",
				page : "page",
				total : "total",
				records : "records",
				repeatitems : false,
				cell : "cell",
				id : "id"
			},
	        gridComplete: function(){
	        	
	        	console.log("adsf");
	        }, 
	        beforeSubmitCell: function(rowid, cellname, value, iRow, iCol){
		        
				console.log("rowid = "+rowid);
				console.log("cellname = "+cellname);
				console.log("value = "+value);
				console.log("iRow = "+iRow);
				console.log("iCol = "+iCol);
				var rowObject = jQuery("#packingListGrid").jqGrid('getRowData',rowid); 
        		console.log(rowObject);
				var warehouse_outward_id = rowObject.warehouse_outward_id;
				var actualWeight = value;
				var parametersToSubmit = {warehouse_outward_id:warehouse_outward_id};
        		console.log(parametersToSubmit);
				
		        return parametersToSubmit;
		    },
		    afterSaveCell: function(rowid, cellname, value, iRow, iCol){
			    bootbox.alert("Actual Weight Updated successfully to "+value+"!");
			},
			ondblClickRow: function (rowid, iRow, iCol, e){
				var rowData = $(this).getRowData(rowid); 
				console.log(rowData);
				var warehouse_outward_id = rowData.warehouse_outward_id;

				fetchWarehouseOutwardDetails(warehouse_outward_id);
			} 
		});
});

function fetchWarehouseOutwardDetails(warehouse_outward_id){
	$.ajax({
		url:'fetchWarehouseOutwardDetailsHtml.do?warehouseOutwardId='+warehouse_outward_id,
		success:function(resp){
			bootbox.alert(resp);
		},
		error:function(resp){
			bootbox.alert(resp);
		}
	});
}
</script>
