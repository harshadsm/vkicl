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
<div >
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
			
			
			colNames : [ 'Code',
							'Name','Channel','Channel Name','Division','Division Name','Address','Credit limit', 'Payment Term', 'DL 20b No','DL 21b No'
					],
					
			colModel : [ {
				name : 'customerCode',
				index : 'customerCode',
				width : 185,
				editable : true,
				editrules : {
					required : true
				},
				editoptions : {
					size : 10
				},
				search:true,
				searchoptions: { sopt:['ge']}
			}, {
				name : 'customerName',
				index : 'customerName',
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
				name : 'distChannel',
				index : 'distChannel',
				width : 60,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false
			}, {
				name : 'distChannelName',
				index : 'distChannelName',
				width : 180,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false

			}, {
				name : 'division',
				index : 'division',
				width : 40,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false
			}, {
				name : 'divName',
				index : 'divName',
				width : 150,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false
			}, {
				name : 'address',
				index : 'address',
				width : 600,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				}
			},{
				name : 'creditLimit',
				index : 'creditLimit',
				width : 100,
				editable : true,
				editrules : {
					required : true
				},
				editoptions : {
					size : 10
				},
				search:false
			}, {
				name : 'payTerms',
				index : 'payTerms',
				width : 55,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false
			},  {
				name : 'drugLicense20bNo',
				index : 'drugLicense20bNo',
				width : 55,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false
			}, {
				name : 'drugLicense21bNo',
				index : 'drugLicense21bNo',
				width : 55,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false
			}
			
			],
			postData : {
			},
			rowNum : 20,
			rowList : [ 20, 40, 60 ],
			height : 480,
			autowidth : true,
			rownumbers : true,
			pager : '#pager',
			sortname : 'id',
			viewrecords : true,
			sortorder : "asc",
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
	        		var rowObject = jQuery("#grid").jqGrid('getRowData',ids[i]); 
	        		//console.log(rowObject);
	        		var cust_lnk = "<a href=\"viewCustomer.do?id="+rowObject.customerCode+"\">"+rowObject.customerName+"</a>";
	        		
	        		$("#grid").jqGrid('setRowData',ids[i],{customerName:cust_lnk});
	        		
	        		$("#grid").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false, searchOperators:true, defaultSearch:"cn"});
	        		
	        		$("#grid").jqGrid('setColProp', "address", {searchoptions: { sopt:['cn','eq']}});
	        		
	        		
					
	        		
	        		} }
		});
});		
</script>

