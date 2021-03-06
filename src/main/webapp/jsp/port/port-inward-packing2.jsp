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
		<h3 class="page-head">Port Inward Details</h3>
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
			url : './portInwardDetailsJsonServlet',
			datatype : 'json',
			mtype : 'GET',
			
			
			colNames : [ 'id', 'Inward Date', 'Vessel Date', 'Vessel Name', 'Vendor Name', 'Material Type', 'Mill Name', 'Make', 'Grade', 'Desc', 'Packing List', 'Inward Dettails Record Count'
					],
					
			colModel : [ {
				name : 'id',
				index : 'id',
				width : 50,
				editable : true,
				editrules : {
					required : true
				},
				editoptions : {
					size : 10
				},
				search:false,
				searchoptions: { sopt:['ge']}
			}, {
				name : 'createTs',
				index : 'create_ts',
				width : 180,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'vesselDate',
				index : 'vessel_date',
				width : 180,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'vesselName',
				index : 'vessel_name',
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
				name : 'vendorName',
				index : 'vendor_name',
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
				name : 'materialType',
				index : 'materialType',
				width : 200,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search: true,
				sortable:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'millName',
				index : 'millName',
				width : 200,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search: true,
				sortable:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'make',
				index : 'make',
				width : 200,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				sortable:false,
				search: true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'grade',
				index : 'grade',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				sortable:false,
				search: true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'desc',
				index : 'desc',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				sortable:false,
				search:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'actionLink',
				index : 'actionLink',
				width : 150,
				editable : false,
				search:false,
				sortable:false
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
			rowNum : 20,
			rowList : [ 20, 40, 60 ],
			height : 280,
			autowidth : true,
			rownumbers : true,
			pager : '#pager',
			sortname : 'vessel_date',
			viewrecords : true,
			sortorder : "desc",
			caption : "Port Inward List",
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
	        		
	        		
					
	        		
	        		} }
		});
});		
</script>

