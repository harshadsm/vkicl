<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c"%>

<%
	UserInfoVO userInfoVO = (UserInfoVO) session
			.getAttribute(Constants.USER_INFO_SESSION);
%>
<script type="text/javascript">
var SELECTED_PORT_INVENTORY_ITEMS = [];
</script>

<style>
.dispatch-table, .dispatch-table td.excel, .dispatch-table th.excel {
	background-color: rgba(230, 126, 34, 0.05);
	margin: 10px 0;
	border: 1px solid #e67e22;
}

.dispatch-table td.excel {
	/*min-width: 165px;*/
	text-align: left;
}

.dispatch-table td.excel.td-1-input {
	width: 165px;
}

.dispatch-table td.excel label {
	width: 100%;
	text-align: right;
	padding-top: 5px;
}

.dispatch-table td.excel input[type='text'] {
	min-width: 150px;
}

.dispatch-table td.excel input[type='checkbox'], .dispatch-table td.excel input[type='radio']
	{
	text-align: left;
	margin: 0 10px;
}

.dispatch-table td.excel, .dispatch-table th.excel {
	border: 1px solid #e67e22;
	padding: 0 0;
	margin: 0;
}

.dispatch-table th.excel {
	padding: 2px 0;
}

.dispatch-table label {
	padding: 0 5px;
}

.dispatch-table td.excel input, .dispatch-table td.excel textarea {
	border: 0px;
	border-radius: 0;
	padding: 0 0 0 8px;
}

.dispatch-table td.excel textarea {
	padding: 5px;
}

.dispatch-table .input-group-addon {
	border-radius: 0;
	border: 1px solid #eee;
	margin: 0;
}

.dispatch-table td.input-td {
	background-color: #FFFFFF;
}

.dispatch-table input[type=button].add-row, .dispatch-table input[type=button].delete-row
	{
	margin: 5px;
	padding: 0;
}

.dispatch-table div.input-group input[type=number] {
	border-right: 1px solid #e67e22;
}

.dispatch-table div.weight-group .btn, .date-picker-div .input-group-addon {
	background-color: rgba(230, 126, 34, 0.05);
	border-color: #E4DCD4;
	border-radius: 0px;
	color: #5C5B60;
	font-weight: 600;
	border-left: 1px solid #e67e22;
	margin-left: 0;
}

.dispatch-table div.weight-group .btn .caret {
	border-top: 4px solid #000000;
}

.dispatch-table td.excel .input-group {
	width: 100%
}

.dispatch-table td.excel-100 {
	width: 100px;
}

.dispatch-table td.excel-200 {
	width: 200px;
}

.dispatch-table td.excel-100 .weight-group button {
	width: 100%;
	text-align: right;
}

.dispatch-table .input-group-btn:last-child>.btn, .dispatch-table .input-group-btn:last-child>.btn-group
	{
	background: #FFFFFF;
	margin: 0px;
	border: 0px;
}

.dispatch-table .dispatch-table td.center-input {
	text-align: center;
}

.dispatch-table tfoot th.excel {
    padding: 2px 5px;
}

input[name="length"], input[name="width"], input[name="thickness"], input[name="qty"] {
	width: 115px;
}

#input-table .weight-group button{
	max-width: 115px;
    overflow: hidden;
    text-overflow: ellipsis;
}

.dispatch-table tr.main-row, #input-table tr.main-row {
	background-color: #FFFFFF;
}

</style>
<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Port Purchase Order</h3>
	</div>
</div>
<div class="row">
<ul class="nav nav-pills">
    <li class="active"><a data-toggle="pill" href="#home">Port Purchase Order- Step 1</a></li>
    <li><a data-toggle="pill" href="#menu1">Port Purchase Order- Step 2</a></li>

  </ul>
</div>
	
  <div class="tab-content">
    <div id="home" class="tab-pane fade in active">
      <h3>Step 1</h3>
      <div class="row">
			<div class="col-md-10">
				<table class="table table-responsive dispatch-table">
					<tr>
						<td class='excel' colspan="1"><label for="customerName">Customer Name</label></td>
						<td class='excel' colspan="5"><input type="text" name="customerName"
							placeholder="" class="form-control" /></td>
						
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="brokerName">Broker Name</label></td>
						<td class='excel' colspan="5"><input type="text"
							name="brokerName" placeholder=""
							class="form-control" /></td>
						
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="brokerage">Brokerage</label></td>
						<td class='excel' colspan='7'>
							<div class='input-group'>
								<input type='number' step='0.001' placeholder='Brokerage' min='0'
									value='' name=brokerage class='form-control'>
								<div class='input-group-btn weight-group'>
									<input type='hidden' name='brokerageUnit' value='Per MT' />
									<button type='button' class='btn btn-default dropdown-toggle'
										data-toggle='dropdown' aria-expanded='false'>
										Per MT <span class='caret'></span>
									</button>
									<ul class='dropdown-menu dropdown-menu-right' role='menu'>
										<li onclick='btnGroupChange(this);'><a>Per MT</a></li>
										<li onclick='btnGroupChange(this);'><a>Percentage</a></li>
									</ul>
								</div>
							</div>
						</td>
					</tr>
					
					<tr>
						<td class='excel' colspan="1"><label for="deliveryAddress">Delivery Address</label></td>
						<td class='excel' colspan="5"><input type="text"
							name="deliveryAddress" placeholder="" class="form-control" /></td>
						
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="consigneeName">Rate (per metric ton)</label></td>
						<td class='excel' colspan="5"><input type="text"
							name="rate" placeholder=""
							class="form-control" /></td>
						
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="excise">Excise
								</label></td>
						<td class='excel excel-100' colspan="5">
							<div class='input-group'>
								<div class='input-group-btn weight-group'>
									<input type='hidden' name='excise' value='Extra' />
									<button type="button" class="btn btn-default dropdown-toggle"
										data-toggle="dropdown" aria-expanded="false"
										style="width: 100%; text-align: right;">
										Extra <span class="caret"></span>
									</button>
									<ul class='dropdown-menu dropdown-menu-right' role='menu'>
										<li onclick='btnGroupChange(this);'><a>Extra 12.5%</a></li>
										<li onclick='btnGroupChange(this);'><a>Extra 12.5% and 4%</a></li>
										<li onclick='btnGroupChange(this);'><a>include 12.5%</a></li>
										<li onclick='btnGroupChange(this);'><a>include 12.5% and 4%</a></li>
									</ul>
								</div>
							</div>
						</td>
						
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="tax">Tax
								</label></td>
						<td class='excel excel-100' colspan="5">
							<div class='input-group'>
								<div class='input-group-btn weight-group'>
									<input type='hidden' name='tax' value='Extra' />
									<button type="button" class="btn btn-default dropdown-toggle"
										data-toggle="dropdown" aria-expanded="false"
										style="width: 100%; text-align: right;">
										Extra <span class="caret"></span>
									</button>
									<ul class='dropdown-menu dropdown-menu-right' role='menu'>
										<li onclick='btnGroupChange(this);'><a>Extra 12.5%</a></li>
										<li onclick='btnGroupChange(this);'><a>Extra 12.5% and 4%</a></li>
										<li onclick='btnGroupChange(this);'><a>include 12.5%</a></li>
										<li onclick='btnGroupChange(this);'><a>include 12.5% and 4%</a></li>
									</ul>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="transport">Transport
								</label></td>
						<td class='excel excel-100' colspan="5">
							<div class='input-group'>
								<div class='input-group-btn weight-group'>
									<input type='hidden' name='transport' value='Extra' />
									<button type="button" class="btn btn-default dropdown-toggle"
										data-toggle="dropdown" aria-expanded="false"
										style="width: 100%; text-align: right;">
										Extra <span class="caret"></span>
									</button>
									<ul class='dropdown-menu dropdown-menu-right' role='menu'>
										<li onclick='btnGroupChange(this);'><a>Extra 12.5%</a></li>
										<li onclick='btnGroupChange(this);'><a>Extra 12.5% and 4%</a></li>
										<li onclick='btnGroupChange(this);'><a>include 12.5%</a></li>
										<li onclick='btnGroupChange(this);'><a>include 12.5% and 4%</a></li>
									</ul>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="paymentterms">Payment Terms
								</label></td>
						<td class='excel excel-100' colspan="5">
							<div class='input-group'>
								<div class='input-group-btn weight-group'>
									<input type='hidden' name='paymentterms' value='Extra' />
									<button type="button" class="btn btn-default dropdown-toggle"
										data-toggle="dropdown" aria-expanded="false"
										style="width: 100%; text-align: right;">
										Extra <span class="caret"></span>
									</button>
									<ul class='dropdown-menu dropdown-menu-right' role='menu'>
									<li onclick='btnGroupChange(this);'><a>Extra 12.5%</a></li>
										<li onclick='btnGroupChange(this);'><a>Extra 12.5% and 4%</a></li>
										<li onclick='btnGroupChange(this);'><a>include 12.5%</a></li>
										<li onclick='btnGroupChange(this);'><a>include 12.5% and 4%</a></li>
									</ul>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
    </div>
    <div id="menu1" class="tab-pane fade">
      <h3>Step 2</h3>
      <div class="row">
      <div class="col-md-12">
					
						<div id="portInwardTable">
							<table id="portpurchaseorderinwardGrid"></table>
							<div id="portInwardPager"></div>
						</div>
					
	 </div>
	 </div>
	    <div class="row">
	<div class="col-md-12">
				
				<div id="portpurchaseorderTable">
							<table id="portpurchaseorderdetailGrid"></table>
							<div id="portInwarddetailPager"></div>
						</div>
				</div>
  </div>
  
	 <div class="row">
	 <div class="col-md-12">
					
						<div id="portfinalTable">
							<table id="portpurchaseorderfinalGrid"></table>
							<div id="portInwardfinalPager"></div>
						</div>
					
	 </div>
	 </div>
</div>

<script>

$(function() {
	$("#portpurchaseorderinwardGrid").jqGrid(
		{
			url : './portPurchaseOrderJsonServlet',
			datatype : 'json',
			mtype : 'GET',
			
			
			colNames : [ 'id', 'Date', 'Vessel Name', 'Vendor Name', 'Material Type', 'Mill Name', 'Make', 'Grade'],
					
			colModel : [ {
				name : 'id',
				index : 'id',
				hidden: true,
				width : 30,
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
				name : 'vesselDate',
				index : 'vessel_date',
				width : 100,
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
				width : 150,
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
				width : 150,
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
				width : 120,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false,
				sortable:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'millName',
				index : 'millName',
				width : 150,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false,
				sortable:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'make',
				index : 'make',
				width : 100,
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
				name : 'grade',
				index : 'grade',
				width : 100,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				sortable:false,
				search:false,
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
	        		
	        		$("#grid").jqGrid('setRowData',ids[i],{actionLink:cust_lnk});
	        		
	        		$("#grid").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false, searchOperators:true, defaultSearch:"cn"});
	        		
	        		$("#grid").jqGrid('setColProp', "address", {searchoptions: { sopt:['cn','eq']}});
	        		} }
	        		,
	           		
	           		onSelectRow: function(rowId) {
	           			var row = jQuery("#portpurchaseorderinwardGrid").jqGrid('getRowData',rowId); 
	           			
	           			
	           			
	       	        }
		});
});		

$(function() {
	
	$("#portpurchaseorderdetailGrid").jqGrid(
				{
					url : './portPurchaseOrderDetailJsonServlet',
					datatype : 'json',
					mtype : 'GET',
					
					
					colNames : [ 'port_inward_detail_id', 'Thickness', 'Width', 'Length', 'Quantity', 'Actual Weight'],
							
					colModel : [ {
						name : 'port_inward_detail_id',
						index : 'port_inward_detail_id',
						width : 20,
						hidden: true,
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
						name : 'thickness',
						index : 'thickness',
						width : 150,
						editable : false,
						editoptions : {
							readonly : true,
							size : 10
						},
						search:true,
						//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
						searchoptions: { sopt:[ 'cn','eq']}
						
					},{
						name : 'width',
						index : 'width',
						width : 150,
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
						name : 'length',
						index : 'length',
						width : 150,
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
						name : 'quantity',
						index : 'quantity',
						width : 100,
						editable : false,
						editoptions : {
							readonly : true,
							size : 10
						},
						search:false,
						sortable:false,
						//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
						searchoptions: { sopt:[ 'cn','eq']}
						
					},{
						name : 'actualWt',
						index : 'actualWt',
						width : 100,
						editable : false,
						editoptions : {
							readonly : true,
							size : 10
						},
						search:false,
						sortable:false,
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
					sortname : 'port_inward_detail_id',
					viewrecords : true,
					sortorder : "desc",
					caption : "Port Inward Details",
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
			        		
			        		$("#grid").jqGrid('setRowData',ids[i],{actionLink:cust_lnk});
			        		
			        		$("#grid").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false, searchOperators:true, defaultSearch:"cn"});
			        		
			        		$("#grid").jqGrid('setColProp', "address", {searchoptions: { sopt:['cn','eq']}});
			        		} }
			        		,
			           		
			           		onSelectRow: function(rowId) {
			           			 
			           			
			       	            
			       	        }
				});
	
});

$(function() {
	$("#portpurchaseorderfinalGrid").jqGrid(
		{
			url : './',
			datatype : 'json',
			mtype : 'GET',
			
			
			colNames : [ 'id', 'Date', 'Vessel Name', 'Vendor Name', 'Material Type', 'Mill Name', 'Make', 'Grade', 'Thickness', 'Width', 'Length', 'Quantity'],
					
			colModel : [ {
				name : 'id',
				index : 'id',
				hidden: true,
				width : 30,
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
				name : 'vesselDate',
				index : 'vessel_date',
				width : 100,
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
				width : 150,
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
				width : 150,
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
				width : 100,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false,
				sortable:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'millName',
				index : 'millName',
				width : 120,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false,
				sortable:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'make',
				index : 'make',
				width : 100,
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
				name : 'grade',
				index : 'grade',
				width : 100,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				sortable:false,
				search:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			}, {
				name : 'thickness',
				index : 'thickness',
				width : 100,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'width',
				index : 'width',
				width : 100,
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
				name : 'length',
				index : 'length',
				width : 100,
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
				name : 'quantity',
				index : 'quantity',
				width : 100,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false,
				sortable:false,
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
			sortname : 'vessel_date',
			viewrecords : true,
			sortorder : "desc",
			caption : "Port Purchase Order Items",
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
	        		
	        		$("#grid").jqGrid('setRowData',ids[i],{actionLink:cust_lnk});
	        		
	        		$("#grid").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false, searchOperators:true, defaultSearch:"cn"});
	        		
	        		$("#grid").jqGrid('setColProp', "address", {searchoptions: { sopt:['cn','eq']}});
	        		} }
	        		,
	           		
	           		onSelectRow: function(rowId) {
	           			var row = jQuery("#portpurchaseorderinwardGrid").jqGrid('getRowData',rowId); 
	           			
	           			
	           			
	       	        }
		});
});		


function composeObjectForCaching(rowObject,qty){
	//var cachedObject = rowObject.portInwardId+"-"+rowObject.portInwardDetailId+"-"+rowObject.portInwardShipmentId;
	//return cachedObject;
	
	var cachedObj = {
			portInwardId : rowObject.portInwardId,
			portInwardDetailId : rowObject.portInwardDetailId,
			portInwardShipmentId : rowObject.portInwardShipmentId,
			portOutwardId : rowObject.portOutwardId,
			orderedQuantity : qty,
			length : rowObject.length,
			width : rowObject.width,
			thickness : rowObject.thickness,
			vesselDate : rowObject.vesselDate,
			vesselName : rowObject.vesselName,
			millName : rowObject.millName,
			availableQuantity : rowObject.quantity,
			grade : rowObject.grade,
			materialType : rowObject.materialType,
			balQty : rowObject.balQty,
			outQty : rowObject.outQty,
			vehicleDate:rowObject.vehicleDate,
			vehicleName:rowObject.vehicleName,
			actualWt:rowObject.actualWt,
			vendorName : rowObject.vendorName,
			make:rowObject.make,
			actualWt_unit:rowObject.actualWt_unit
	};
	return cachedObj;
}

function isObjectPresentInCache(targetObj){
	
	if(SELECTED_PORT_INVENTORY_ITEMS.length > 0){
		
		for(var i=0;i<SELECTED_PORT_INVENTORY_ITEMS.length;i++){
			var itemObj = SELECTED_PORT_INVENTORY_ITEMS[i];
			var isPresent = compareCachedObjects(itemObj, targetObj);
			
			if(isPresent){
				return itemObj;
			}
		}
		
	}
	return false;
}

</script>

