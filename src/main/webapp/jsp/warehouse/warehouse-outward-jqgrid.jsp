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
							<input type="hidden" min="0" name="dispatchNo"
								value='<c:out value="<%=dispatchNo %>" />' class="form-control" />
						</td>
					</tr>
					<tr>
						<td class="form-label"><label for="vehicleDate">Vehicle
								Date</label></td>
						<td>
							<div class="input-group date date-picker-div">
								<input type="text" name="vehicleDate" class="form-control" /> <span
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
						<td><input type="text" name="vehicleNumber"
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
	
</div>


<script>

$(function(){
	
	populateDispatchDetailsTable();
	
});

function populateDispatchDetailsTable(){
	$("#dispatchDetailsTable").jqGrid(
		{
			url : './getDispatchDetails?dispatchOrderId=<%=dispatchNo%>',
			datatype : 'json',
			
			mtype : 'POST',
			
			
			colNames : [ 'dispatchDetailsId', 'Mill', 'Make', 'grade', 'Thickness', 'Width', 'Length', 'Ordered Qty', 'Section Weight'],
					
			colModel : [  {
				name : 'dispatchDetailsID',
				index : 'dispatchDetailsID',
				hidden: true,
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
				name : 'qty',
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
	        	var $grid = $("#dispatchDetailsTable");
	        	var ids = $("#dispatchDetailsTable").jqGrid('getDataIDs');
	        	
	        	for(var i=0;i < ids.length;i++){ 
	        		var rowObject = $grid.jqGrid('getRowData',ids[i]); 
	        		console.log(rowObject);
	    		} 
	        	
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
	console.log(row);
	var url = './relevantStockJsonServlet?';
	var mill = 'millName='+row.millName;
	var make = '&make='+row.make;
	var grade = '&grade='+row.grade;
	var length = '&length='+row.length;
	var width = '&width='+row.width;
	var thickness = '&thickness=1'+row.thickness;
	var dispatchDetailsRowId = '&dispatchDetailRowId='+row.dispatchDetailsID;
	var dispatchNo = '&dispatchNo=<%=dispatchNo%>';

	url = url + mill+make+grade+length+width+thickness+dispatchDetailsRowId+dispatchNo;
	

	populateStockTable(url);
	
}

function populateStockTable(url){
	console.log(url);

	$("#stockTable").jqGrid(
		{
			url : './relevantStockJsonServlet',
			mtype:'POST',
			datatype : 'json',
			colNames : [ 'stockId', 'Mill', 'Make', 'grade', 'Thickness', 'Width', 'Length', 'Ordered Qty', 'Section Weight', 'Location'],
					
			colModel : [  {
				name : 'stockId',
				index : 'stockId',
				hidden: true,
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
				name : 'availableQty',
				index : 'availableQty',
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
				thickness : 10
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
	        	var $grid = $("#dispatchDetailsTable");
	        	var ids = $("#dispatchDetailsTable").jqGrid('getDataIDs');
	        	
	        	for(var i=0;i < ids.length;i++){ 
	        		var rowObject = $grid.jqGrid('getRowData',ids[i]); 
	        		console.log(rowObject);
	    		} 
	        	
	        	},
	   		onSelectRow: handleOnSelectRow,
		    onSelectAll: function(aRowids, status) {
		        	for(var i=0;i<aRowids.length;i++){
		            	handleOnSelectRow(aRowids[i],status);
		            }
		            
		        }
		});
	}
</script>