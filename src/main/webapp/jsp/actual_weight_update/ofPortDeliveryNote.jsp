

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
			editurl : './actualWeightUpdateOfDeliveryNoteServlet',
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
				editable : true,
				editrules : {
					required : true
				},
				editoptions : {
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
			cellurl : './actualWeightUpdateOfDeliveryNoteServlet',
			rowNum : 10,
			rowList : [ 10, 20, 30 ,40, 50, 60 ],
			height : 280,
			autowidth : true,
			rownumbers : true,
			multiselect : false,
			pager : '#packingListPager',
			sortname : 'id',
			viewrecords : true,
			sortorder : "desc",
			
			caption : "Actual Weight of Port Delivery Notes",
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
				var delivery_note_id = rowObject.id;
				var actualWeight = value;
				var parametersToSubmit = {delivery_note_id:delivery_note_id};
        		console.log(parametersToSubmit);
				
		        return parametersToSubmit;
		    },
		    afterSaveCell: function(rowid, cellname, value, iRow, iCol){
			    bootbox.alert("Actual Weight Updated successfully to "+value+"!");
			},
			
			// set the subGrid property to true to show expand buttons for each row
			subGrid : true,
			// javascript function that will take care of showing the child grid
			subGridRowExpanded : showChildGrid,
			subGridOptions : {
				// expand all rows on load
				expandOnLoad : false
			}
			 
		});
});




//the event handler on expanding parent row receives two parameters
// the ID of the grid tow  and the primary key of the row
function showChildGrid(parentRowID, parentRowKey) {

	console.log("parentRowID = "+parentRowID);
	console.log("parentRowKey = "+parentRowKey);
	// create unique table and pager
	var childGridID = parentRowID + "_table";
	var childGridPagerID = parentRowID + "_pager";

	// send the parent row primary key to the server so that we know which grid to show
	var childGridURL = "./deliveryNoteDetailsJsonServlet?deliveryNoteId="+parentRowKey;

	// add a table and pager HTML elements to the parent grid row - we will render the child grid here
	$('#' + parentRowID)
			.append(
					'<table id=' + childGridID + '></table><div id=' + childGridPagerID + '></div>');

	$("#" + childGridID).jqGrid({
		url : childGridURL,
		mtype : "GET",
		datatype : "json",
		page : 1,
		colNames : [ 
'Id',
'Mill',
'Make',
'Grade',
'Type',
'Thickness',
'Length',
'Width',
'Actual Weight',
'Ordered Qty',
'Delivered Qty'
		    		],
		colModel : [ {
			label : 'id',
			name : 'id',
			key : true,
			width : 30
		}, {
			label : 'millName',
			name : 'millName',
			width : 70
		}, {
			label : 'materialMake',
			name : 'materialMake',
			width : 70
		}, {
			label : 'materialGrade',
			name : 'materialGrade',
			width : 70
		}, {
			label : 'materialType',
			name : 'materialType',
			width : 70
		}, {
			label : 'thickness',
			name : 'thickness',
			width : 30
		},{
			label : 'length',
			name : 'length',
			width : 30
		},{
			label : 'width',
			name : 'width',
			width : 30
		},{
			label : 'actualWeight',
			name : 'actualWeight',
			width : 100
		},{
			label : 'orderedQuantity',
			name : 'orderedQuantity',
			width : 120
		}, {
			label : 'deliveredQuantity',
			name : 'deliveredQuantity',
			width : 120
		}  ],
		loadonce : true,
		footerrow: true,
		loadComplete : function() {
			var $grid =  $("#" + childGridID);
			var actualWeightSum = $grid.jqGrid('getCol', 'actual_wt', false, 'sum');
			console.log("actualWeightSum = "+actualWeightSum);

			$grid.jqGrid('footerData','set', {width: 'Total:', actual_wt: actualWeightSum});
		},
		width : 1000,
		height : '100%',
		pager : "#" + childGridPagerID
	});
}
</script>
