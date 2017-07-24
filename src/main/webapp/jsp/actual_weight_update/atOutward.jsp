

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
			url : './actualWeightUpdateAtOutwardJsonServlet',
			editurl : './actualWeightUpdateAtOutwardServlet',
			datatype : 'json',
			
			mtype : 'POST',


			colNames : [ 
				'warehouse_outward_id',
				'actual_ut',
				
				'create_ts',
				'create_ui',
				'delivered_quantity',
				'dispatch_detail_id',
				'dispatchNo',
				'handled_by',
				'vehicle_dt',
				'vehicle_no',
				'actual_wt'
				 ],

					
			colModel : [  {
				name : 'warehouse_outward_id',
				index : 'warehouse_outward_id',
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
				name : 'actual_ut',
				index : 'actual_ut',
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
				name : 'create_ts',
				index : 'create_ts',
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
				name : 'create_ui',
				index : 'create_ui',
				width : 300,
				hidden:true,
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
				name : 'delivered_quantity',
				index : 'delivered_quantity',
				width : 300,
				hidden : true,
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
				name : 'dispatch_detail_id',
				index : 'dispatch_detail_id',
				width : 300,
				hidden : true,
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
				name : 'dispatchNo',
				index : 'dispatchNo',
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
				name : 'handled_by',
				index : 'handled_by',
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
				name : 'vehicle_dt',
				index : 'vehicle_dt',
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
				name : 'vehicle_no',
				index : 'vehicle_no',
				width : 200,
				editable : false,
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
				
			},{
				name : 'actual_wt',
				index : 'actual_wt',
				hidden: false,
				width : 185,
				editable : true,
				editrules : {
					required : true
				},
				editoptions : {
					
					size : 10
				},
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
			} 
		});
});
</script>
