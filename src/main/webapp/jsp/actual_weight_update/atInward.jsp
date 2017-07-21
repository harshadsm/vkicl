

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
			url : './actualWeightUpdateAtInwardJsonServlet',
			editurl : './actualWeightUpdateAtInwardServlet',
			datatype : 'json',
			
			mtype : 'POST',


			colNames : [ 
				'port_out_shipment_id', 
				'warehouse_name', 
				'customer_name', 
				'vehicle_number', 
				'vehicle_date', 
				'create_ui', 
				'create_ts', 
				'invoice', 
				'warehouse_inward_flag', 
				'actual_weight' ],

					
			colModel : [  {
				name : 'port_out_shipment_id',
				index : 'port_out_shipment_id',
				hidden: true,
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
				name : 'warehouse_name',
				index : 'warehouse_name',
				hidden: true,
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
				name : 'customer_name',
				index : 'customer_name',
				hidden: true,
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
				name : 'vehicle_number',
				index : 'vehicle_number',
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
				
			},{
				name : 'vehicle_date',
				index : 'vehicle_date',
				width : 300,
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
				name : 'create_ui',
				index : 'create_ui',
				width : 300,
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
				name : 'create_ts',
				index : 'create_ts',
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
				
			},{
				name : 'invoice',
				index : 'invoice',
				width : 300,
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
				name : 'warehouse_inward_flag',
				index : 'warehouse_inward_flag',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center',
				sortable:true,
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn']}
				
			},{
				name : 'actual_weight',
				index : 'actual_weight',
				width : 200,
				editable : true,
				editrules : {
					required : true
				},
				editoptions : {
					
					size : 10
				},
				
				align : 'center', 
				sortable:true,
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			}
			],
			postData : {
			},
			cellEdit: true,
			cellsubmit : 'remote',
			cellurl : './actualWeightUpdateAtInwardServlet',
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
			
			caption : "Actual Weight of the shipments arriving at Warehouse",
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
				var port_out_shipment_id = rowObject.port_out_shipment_id;
				var actualWeight = value;
				var parametersToSubmit = {port_out_shipment_id:port_out_shipment_id};
        		console.log(parametersToSubmit);
				
		        return parametersToSubmit;
		    },
		    afterSaveCell: function(rowid, cellname, value, iRow, iCol){
			    bootbox.alert("Actual Weight Updated successfully to "+value+"!");
			} 
		});
});
</script>
