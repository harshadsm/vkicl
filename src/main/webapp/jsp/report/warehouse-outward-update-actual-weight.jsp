<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.form.PortInwardForm"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c" %>


<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Warehouse Outward (Update Actual Weight)</h3>
	</div>
</div>
<div class="row">
<div>
		<div id="jqgrid">
		<table id="grid"></table>
		<div id="pager"></div>

	</div>
	<!-- <a href="javascript:downloadExcelFile()">Export to Excel</a> -->
	<!-- <input style='margin-top: 10px;' type="button" value="Export to Excel"
		id='excelExport' /> -->
		
</div>
</div>

<script>

$(function() {
	$("#grid").jqGrid(
		{
			url : './warehouseOutwardForActualWeightUpdateJsonServlet',
			editurl : './warehouseOutwardUpdateActualWeightServlet',
			datatype : 'json',
			mtype : 'GET',
			
			
			colNames : [ 'id', 'Vehicle Date', 'Vehicle No', 'Customer Name', 'Section Wt',  'Actual Wt', 'Handled By', 'Packing List', 'Inward Dettails Record Count'
					],
					
			colModel : [ {
				name : 'warehouseOutwardId',
				index : 'warehouseOutwardId',
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
			}, {
				name : 'vehicleDate',
				index : 'vehicleDate',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'vehicleNo',
				index : 'vehicleNo',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				sortable:false,
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'buyerName',
				index : 'buyerName',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				sortable:false,
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'sectionWt',
				index : 'sectionWt',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				sortable:false,
				search : false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
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
				
				search : false,
				searchoptions: { sopt:[ 'ge']}
				
			},{
				name : 'handledBy',
				index : 'handledBy',
				width : 300,
				editable : true,
				editrules : {
					required : true
				},
				editoptions : {
					
					size : 10
				},
				
				search : false,
				searchoptions: { sopt:[ 'ge']}
				
			},{
				name : 'actionLink',
				index : 'actionLink',
				width : 150,
				editable : false,
				search:false,
				sortable:false,
				hidden : true
			},{
				name : 'countOfPortInwardDetailRecords',
				index : 'countOfPortInwardDetailRecords',
				width : 10,
				hidden: true,
				editable : false,
				search:false,
				sortable:false
			}
			
			],
			postData : {
			},
			cellEdit: true,
			cellsubmit : 'remote',
			cellurl : './warehouseOutwardUpdateActualWeightServlet',
			rowNum : 10,
			rowList : [ 10, 20, 30 ],
			height : 280,
			autowidth : true,
			rownumbers : true,
			pager : '#pager',
			sortname : 'warehouse_outward_creation_date',
			viewrecords : true,
			sortorder : "desc",
			caption : "Warehouse Outward List",
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
	        	var ids = $("#grid").jqGrid('getDataIDs');
	        	console.log(ids);
	        	for(var i=0;i < ids.length;i++){ 
	        		//Create packing list link
	        		var rowObject = jQuery("#grid").jqGrid('getRowData',ids[i]); 
	        		console.log(rowObject);
	        		var countOfPortInwardDetailRecords = Number(rowObject.countOfPortInwardDetailRecords);
	        		if(countOfPortInwardDetailRecords > 0){
	        			var cust_lnk = "<a href=\"add-port-inward-packing-list.do?id="+rowObject.id+"\"> ("+rowObject.countOfPortInwardDetailRecords+") <span class='glyphicon glyphicon-list'></span></a>";
	        		}else{
	        			var cust_lnk = "<a href=\"add-port-inward-packing-list.do?id="+rowObject.id+"\"><span class='glyphicon glyphicon-pencil'></span></a>";
	        		}


	        		$("#grid").jqGrid('setRowData',ids[i],{actionLink:cust_lnk});
	        		
	        		$("#grid").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false, searchOperators:true, defaultSearch:"cn"});
	        		
	        		$("#grid").jqGrid('setColProp', "address", {searchoptions: { sopt:['cn','eq']}});
	        		
	        		
					
	        		
	        		} },
	        beforeSubmitCell: function(rowid, cellname, value, iRow, iCol){
		        
				console.log("rowid = "+rowid);
				console.log("cellname = "+cellname);
				console.log("value = "+value);
				console.log("iRow = "+iRow);
				console.log("iCol = "+iCol);
				var rowObject = jQuery("#grid").jqGrid('getRowData',rowid); 
        		console.log(rowObject);
				var warehouseOutwardId = rowObject.warehouseOutwardId;
				var actualWeight = value;
				var parametersToSubmit = {warehouseOutwardId:warehouseOutwardId};
        		console.log(parametersToSubmit);
				
		        return parametersToSubmit;
		    },
		    afterSaveCell: function(rowid, cellname, value, iRow, iCol){
			    bootbox.alert("Actual Weight Updated successfully to "+value+"!");
			} 
		});
});		
</script>

