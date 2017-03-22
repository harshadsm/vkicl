<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c" %>

<% 
String dispatchNo=request.getParameter("dispatchNo");
if(dispatchNo == null){
	dispatchNo = (String)request.getAttribute("dispatchNo_2");
}
System.out.println("----------------->>>"+dispatchNo);

%>


<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Warehouse Outward (Grid)</h3>
	</div>
</div>

<div>
	<html:form enctype="multipart/form-data" action="/warehouse-outward-process"
		method="post">
		<div class="row">
			<div class="col-md-4">
				<table class="table table-responsive">
					<tr>
						<td class="form-label"><label for="dispatchNo">Dispatch
								Order No.</label></td>
						<td>
							<label for="dispatchNo"><c:out value="<%=dispatchNo %>" /></label>
							<input type="hidden" min="0" name="dispatchNo" id="dispatchOrderId"
								value='<c:out value="<%=dispatchNo %>" />' class="form-control" />
						</td>
					</tr>
					<tr>
						<td class="form-label"><label for="vehicleDate">Vehicle
								Date</label></td>
						<td>
							<div class="input-group date date-picker-div">
								<input type="text" name="vehicleDate" class="form-control" id="vehicleDate" /> <span
									class="input-group-addon"><span
									class="glyphicon-calendar glyphicon"></span></span>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div class="col-md-4">
				<table class="table table-responsive">
					<tr>
						<td class="form-label"><label for="vehicleNumber">Vehicle
								Number</label></td>
						<td><input type="text" name="vehicleNumber" id="vehicleNumber"
							class="form-control" /></td>
					</tr>
					
				</table>
			</div>
		</div>
		
		
		
		
	</html:form>
	
	<div id="dispatchDetailsTableDiv">
		<table id="dispatchDetailsTable"></table>
		<div id="dispatchDetailsTablePager"></div>
	</div>
	
	<div id="stockDiv">
		<table id="stockTable"></table>
		<div id="stockTablePager"></div>
	</div>

	<div>
	
		<table id="selectedStockItemsTable" class="table table-striped" border=1>
			<thead>
				<tr>
					<th colspan="10" class="text-center"><h3>Items selected from Warehouse Stock for Outward</h3></th>
				</tr>
				<tr>
					<th>Dispatch Item Id</th>
					<th>Stock Id</th>
					<th>Mill</th>
					<th>Make</th>
					<th>Grade</th>
					<th>Thickness</th>
					<th>Width</th>
					<th>Length</th>
					<th>Delivery Quantity</th>
					<th>Remove</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>	
</div>
<div>
	<button onclick="submitWarehouseOutward()">Submit</button>
</div>

<script>

$(function(){
	
	populateDispatchDetailsTable();
	populateStockTable();
	
});

function populateDispatchDetailsTable(){
	$("#dispatchDetailsTable").jqGrid(
		{
			url : './getDispatchDetails?dispatchOrderId=<%=dispatchNo%>',
			datatype : 'json',
			
			mtype : 'POST',
			
			
			colNames : [ 'Item DB Id', 'Mill', 'Make', 'grade', 'Thickness', 'Width', 'Length', 'Ordered Qty', 'Delivered Qty', 'Pending Qty', 'Section Weight'],
					
			colModel : [  {
				name : 'dispatchDetailsID',
				index : 'dispatchDetailsID',
				hidden: false,
				width : 185,
				editable : false,
				search:false
				
			}, {
				name : 'millName',
				index : 'millName',
				width : 200,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center', 
				sortable:true,
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			},{
				name : 'make',
				index : 'make',
				width : 200,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center', 
				sortable:true,
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			},{
				name : 'grade',
				index : 'grade',
				width : 200,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center', 
				sortable:true,
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			},{
				name : 'thickness',
				index : 'thickness',
				width : 200,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center',
				search:true,
				sortable:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			},{
				name : 'width',
				index : 'width',
				width : 200,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center',
				search:true,
				sortable:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			},
			{
				name : 'length',
				index : 'length',
				width : 200,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center',
				search:true,
				sortable:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			}
			,{
				name : 'orderedQuantity',
				index : 'qty',
				width : 100,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center',
				search:false,
				sortable:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			},{
				name : 'deliveredQuantity',
				index : 'qty',
				width : 100,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center',
				search:false,
				sortable:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			},{
				name : 'pendingQuantity',
				index : 'qty',
				width : 100,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center',
				search:false,
				sortable:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			}, 
			{
				name : 'actWt',
				index : 'actWt',
				width : 150,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center',
				search:false,
				sortable:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			}
			
			],
			postData : {
				dispatchOrderId:<%=dispatchNo%>
			},
			rowNum : 10,
			rowList : [ 10, 20, 30 ,40, 50, 60 ],
			height : 180,
			width : 1000,
			autowidth : false,
			rownumbers : true,
			multiselect : false,
			pager : '#dispatchDetailsTablePager',
			sortname : 'dispatch_detail_id',
			viewrecords : true,
			sortorder : "desc",
			caption : "Dispatch Order Details",
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
	        	/* var $grid = $("#dispatchDetailsTable");
	        	var ids = $("#dispatchDetailsTable").jqGrid('getDataIDs');
	        	
	        	for(var i=0;i < ids.length;i++){ 
	        		var rowObject = $grid.jqGrid('getRowData',ids[i]); 
	        		console.log(rowObject);
	    		}  */
	        	
	        	},
	   		onSelectRow: handleOnSelectRow,
		    onSelectAll: function(aRowids, status) {
		        	for(var i=0;i<aRowids.length;i++){
		            	handleOnSelectRow(aRowids[i],status);
		            }
		            
		        }
		});
	}

function handleOnSelectRow(rowId, status){
	var row = jQuery("#dispatchDetailsTable").jqGrid('getRowData',rowId); 
	var plate_area = row.length * row.width;
	console.log(plate_area);
	
	var searchStockCriteria = {
			thickness:row.thickness,
			length : row.length,
			width : row.width,
			millName : row.millName,
			make : row.make,
			grade : row.grade,
			plateArea : plate_area
	};
	
	$("#stockTable").setGridParam({postData:searchStockCriteria});
	$("#stockTable").trigger('reloadGrid');
	
	
}

function handleOnSelectStockRow(selectedStockJqgridRowId, status){
	
	if(status){
		addWarehouseOutwardLineItem(selectedStockJqgridRowId);
	}else{
		var SELECTED_STOCK_LINE_ITEM = composeStockLineItemObjectForStockJqgridRowId(selectedStockJqgridRowId);
		console.log("calling REMOVE for ===> "+SELECTED_STOCK_LINE_ITEM.stockId);
		removeWarehouseOutwardLineItem(SELECTED_STOCK_LINE_ITEM.stockId);
	}
	
}

function populateStockTable(thickness, length, width, mill, make, grade){
	console.log(thickness);
	
	$("#stockTable").jqGrid(
		{
			url : './relevantStockJsonServlet',
			mtype:'POST',
			datatype : 'json',
			colNames : [ 'Stock Id', 'Mill', 'Make', 'grade', 'Thickness', 'Width', 'Length', 'Quantity Available', 'Section Weight', 'Heat No', 'Plate No', 'Location'],
					
			colModel : [  {
				name : 'stockBalId',
				index : 'stock_balance_id',
				hidden: false,
				width : 185,
				editable : false,
				search:false
				
			}, {
				name : 'millName',
				index : 'millName',
				width : 200,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center', 
				sortable:true,
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			},{
				name : 'make',
				index : 'make',
				width : 200,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center', 
				sortable:true,
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			},{
				name : 'grade',
				index : 'grade',
				width : 200,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center', 
				sortable:true,
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			},{
				name : 'thickness',
				index : 'thickness',
				width : 100,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center',
				search:true,
				sortable:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			},{
				name : 'width',
				index : 'width',
				width : 100,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center',
				search:true,
				sortable:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			},
			{
				name : 'length',
				index : 'length',
				width : 100,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center',
				search:true,
				sortable:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			}
			,{
				name : 'quantity',
				index : 'quantity',
				width : 100,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center',
				search:false,
				sortable:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			}, 
			{
				name : 'actWt',
				index : 'actWt',
				width : 150,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center',
				search:false,
				sortable:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			}, 
			{
				name : 'heat_no',
				index : 'heat_no',
				width : 150,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center',
				search:false,
				sortable:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			}, 
			{
				name : 'plate_no',
				index : 'plate_no',
				width : 150,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center',
				search:false,
				sortable:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			}, 
			{
				name : 'location',
				index : 'location',
				width : 150,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				align : 'center',
				search:false,
				sortable:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			}
			
			],
			postData : {
				
				length : length,
				thickness : thickness,
				width : width,
				millName : mill,
				make : make,
				grade : grade
				
			},
			rowNum : 10,
			rowList : [ 10, 20, 30 ,40, 50, 60 ],
			height : 180,
			width : 1000,
			autowidth : false,
			rownumbers : true,
			multiselect : true,
			pager : '#stockTablePager',
			sortname : 'stock_balance_id',
			viewrecords : true,
			sortorder : "desc",
			caption : "Relevant Stock for selected record",
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
	        	/* var $grid = $("#dispatchDetailsTable");
	        	var ids = $("#dispatchDetailsTable").jqGrid('getDataIDs');
	        	
	        	for(var i=0;i < ids.length;i++){ 
	        		var rowObject = $grid.jqGrid('getRowData',ids[i]); 
	        		console.log(rowObject);
	    		}  */

	        	preselectRowsAsPer_WAREHOUSE_OUTWARD_cache();
	        	
	        	},
	   		onSelectRow: handleOnSelectStockRow,
		    onSelectAll: function(aRowids, status) {
		        	for(var i=0;i<aRowids.length;i++){
		            	handleOnSelectStockRow(aRowids[i],status);
		            }
		            
		        }
		});
	}
</script>

<script src="./js/warehouse_outward/warehouse_outward.js"></script>