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
		<h3 class="page-head">Warehouse Outward Details Report</h3>
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
			url : './warehouseOutwardJsonServlet',
			datatype : 'json',
			mtype : 'GET',
			
			
			colNames : [ 'id', 'Vehicle Date', 'Vehicle No', 'Vendor Name', 'Material Type', 'Mill Name', 'Make', 'Grade', 'Thickness','Length','Width', 
			 			'HeatNo', 'PlateNo', 'Section Wt', 'Packing List', 'Inward Dettails Record Count', 
			 			'Delivered Quantity', 'Material Doc Id', 'Handled By'
					],
					
			colModel : [ {
				name : 'warehouseOutwardId',
				index : 'warehouseOutwardId',
				width : 185,
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
				name : 'materialType',
				index : 'materialType',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search : true,
				sortable:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'mill',
				index : 'mill',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search : true,
				sortable:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'make',
				index : 'make',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				sortable:false,
				search : true,
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
				search : true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'thickness',
				index : 'thickness',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				sortable:false,
				search : true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'length',
				index : 'length',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				sortable:false,
				search : true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'width',
				index : 'width',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				sortable:false,
				search : true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'heatNo',
				index : 'heatNo',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				sortable:false,
				search : true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'plateNo',
				index : 'plateNo',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				sortable:false,
				search : true,
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
				search : true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
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
			},{
				name : 'deliveredQuantity',
				index : 'deliveredQuantity',
				width : 200,
				hidden: false,
				editable : false,
				search:false,
				sortable:false
			},{
				name : 'materialDocId',
				index : 'materialDocId',
				width : 200,
				hidden: false,
				editable : false,
				search:false,
				sortable:false
			},{
				name : 'handledBy',
				index : 'handledBy',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				sortable:false,
				search : true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
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
	        	var $grid = $("#grid");
	        	var ids = $grid.jqGrid('getDataIDs');
	        	console.log(ids);
	        	for(var i=0;i < ids.length;i++){ 
	        		//Create packing list link
	        		var rowObject = $grid.jqGrid('getRowData',ids[i]); 
	        		//console.log(rowObject);
	        		var countOfPortInwardDetailRecords = Number(rowObject.countOfPortInwardDetailRecords);
	        		if(countOfPortInwardDetailRecords > 0){
	        			var cust_lnk = "<a href=\"add-port-inward-packing-list.do?id="+rowObject.id+"\"> ("+rowObject.countOfPortInwardDetailRecords+") <span class='glyphicon glyphicon-list'></span></a>";
	        		}else{
	        			var cust_lnk = "<a href=\"add-port-inward-packing-list.do?id="+rowObject.id+"\"><span class='glyphicon glyphicon-pencil'></span></a>";
	        		}


	        		var t = Number(rowObject.thickness);
	        		var w = Number(rowObject.width);
	        		var l = Number(rowObject.length);
	        		var q = Number(rowObject.deliveredQuantity);
	        		
	        		var sectionWeight = (t * w * l * q * 7.85 / 1000000000);
	        		console.log(sectionWeight);
	        		sectionWeight = $.number(sectionWeight, 3, '.', '' );	
	        		$grid.jqGrid('setRowData',ids[i],{sectionWt:sectionWeight});
	        		
	        		$grid.jqGrid('setRowData',ids[i],{actionLink:cust_lnk});
	        		
	        		$grid.jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false, searchOperators:true, defaultSearch:"cn"});
	        		
	        		$grid.jqGrid('setColProp', "address", {searchoptions: { sopt:['cn','eq']}});

	        		var certificateFileId = rowObject.materialDocId;
	        		
					if(certificateFileId!='0'){
						var btn = "<button title='Certificate' onclick='downloadMTC("+certificateFileId+")'> <span class='glyphicon glyphicon-save'></span> </button>";
						$grid.jqGrid('setRowData',ids[i],{materialDocId:btn});
					}else{
						$grid.jqGrid('setRowData',ids[i],{materialDocId:"---"});
					}
	        		
	        		} 
        		}
		});
});		


function downloadMTC(material_id){
	var url = "./download?material_id="+material_id;
	$('#downloadFrame').remove();
	$('body').append('<iframe id="downloadFrame" style="display:none"></iframe>');
	$('#downloadFrame').attr('src', url);
}
</script>

