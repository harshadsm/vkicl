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

function validateForm() {
	
	if ("" == getValByFieldName("body", "customerName")) {
		bootbox.alert("Please enter Customer Name");
		return false;
	} 
	
	if ("" == getValByFieldName("body", "brokerName")) {
		bootbox.alert("Please enter Brokerage Name");
		return false;
	}
	
	if ("" != getValByFieldName("body", "brokerName")) {
		if ("" == getValByFieldName("body", "brokerage")) {
			bootbox.alert("Please enter Brokerage");
			return false;
		}
	} 
	 
	if ("" == getValByFieldName("body", "deliveryAddress")) {
		bootbox.alert("Please enter Delivery Address");
		return false;
	} 
	 
	if ("" == getValByFieldName("body", "rate")) {
		bootbox.alert("Please enter rate");
		return false;
	} 
	if ("" == getValByFieldName("body", "comments")) {
		bootbox.alert("Please enter comments");
		return false;
	} 
	var	str = "Are you sure you want to Submit?";
	bootbox.confirm(str, function(result) {
		if (result) {
			
			submitPortPurchaseOrder();
		}
	});
	
	
	return false;
}


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
<div>
<html:form action="/port-purchase-order" >
<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Port Purchase Order</h3>
	</div>
</div>

<div class="row">
<div class="col-md-12">
<ul class="nav nav-pills">
    <li class="active"><a data-toggle="pill" href="#home">Step 1 : Customer Details</a></li>
    <li><a data-toggle="pill" href="#menu1">Step 2 : Ordered Items</a></li>

  </ul>
  </div>
</div>

<div class="tab-content">

<div id="home" class="tab-pane fade in active">
      <div class="row">
					<div class="col-md-12">
						<h3>Step 1 of 2</h3>
					</div>
					
		</div>
      <div class="row">
			<div class="col-md-4">
			
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
						<td class='excel' colspan="1"><label for="rate">Rate (per metric ton)</label></td>
						<td class='excel' colspan="5"><input type="number"
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
						<td class='excel' colspan="1"><label for="paymentTerms">Payment Terms
								</label></td>
						<td class='excel excel-100' colspan="5">
							<div class='input-group'>
								<div class='input-group-btn weight-group'>
									<input type='hidden' name='paymentTerms' value='Extra' />
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
						<td class='excel' colspan="1"><label for="comments">Comments</label></td>
						<td class='excel' colspan="5"><input type="text"
							name="comments" placeholder="" class="form-control" /></td>
						
					</tr>
				</table>
				
			</div>
			</div>
			</div>
			
			<div id="menu1" class="tab-pane fade">
				<div class="row">
					<div class="col-md-12">
						<h3>Step 2 of 2</h3>
					</div>
					
				</div>
				
				<div class="row">
					<div class="col-md-12">

						<div id="portInwardTable">
							<table id="portInwardGrid"></table>
							<div id="portInwardPager"></div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">

						<div id="portpurchaseorderTable">
							<table id="portInwardPackingList"></table>
							<div id="packingListPager"></div>
						</div>
					</div>
				</div>

		<div class="row">
			<div class="col-xs-12">
				<h3>Review the selected Entries below</h3>
				<table class="table table-responsive table-form" id="portInwardTable">
					<thead>
						<tr>
							<th>Date</th>
							<th>Vendor Name</th>
							<th>Vessel Name</th>
							<th>Mill Name</th>
							<th>Material Type</th>
							<th>Make</th>
							<th>Grade</th>
							<th>Thickness</th>
							<th>Width</th>
							<th>Length</th>
							<th>Quantity</th> </tr>
					</thead>
					<tbody id="details-tbody">
					</tbody>
					<tfoot>
						<tr>
							<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
							<td>Total Ordered Quantity</td>
							<td id="sumOfOrderedQuantity"></td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<input type="button" value="Reset" onclick="resetOutwardForm();"
					class="btn pull-left" />
					
				<html:submit styleClass="btn pull-right"
					onclick="return validateForm()" />
			</div>
		</div>
		<html:hidden property="genericListener" value="add" />

</div>

		
	</html:form>
</div>
		
<script>

$(function() {
	$("#portInwardGrid").jqGrid(
		{
			url : './portPurchaseOrderJsonServlet',
			datatype : 'json',
			mtype : 'GET',
			
			
			colNames : [ 'id', 'Date', 'Vessel Name', 'Vendor Name', 'Material Type', 'Mill Name', 'Make', 'Grade'],
					
			colModel : [ {
				name : 'portInwardId',
				index : 'port_inward_id',
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
			pager : '#portInwardPager',
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
	        	
	        	}
	        		,
	           		
	           		onSelectRow: function(rowids) {
	           			
	           			var grid = $('#portInwardGrid');
	           			var sel_id = grid.jqGrid('getGridParam', 'selrow');
	           			
	           			
	           			var portInwardId = grid.jqGrid('getCell', sel_id, 'portInwardId');
	           			var url="./portInwardDetailsJsonServlet2?portInwardId="+portInwardId;
	           			console.log(url);
	           			$("#portInwardPackingList").setGridParam({url:url});
	           			$("#portInwardPackingList").trigger('reloadGrid');
	           			
	       	        }
		});
});		

$(function() {
	
	$("#portInwardPackingList").jqGrid(
				{
					//url : './portPurchaseOrderDetailJsonServlet',
					//url: './portInwardDetailsJsonServlet2?portInwardId=6',
					datatype : 'json',
					mtype : 'GET',
					
					
					colNames : ['port_inward_detail_id', 'port_inward_id', 'Thickness', 'Width', 'Length', 'Quantity', 'Actual Weight'],
							
					colModel : [ {
						name : 'portInwardDetailId',
						index : 'portInwardDetailId',
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
						name : 'portInwardId',
						index : 'portInwardId',
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
					},{
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
						width : 300,
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
					multiselect : true,
					footerrow: false,
					pager : '#packingListPager',
					//sortname : 'port_inward_id',
					viewrecords : true,
					sortorder : "desc",
					caption : "Port Inward Packing List",
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
			        	
			        	var $grid = $("#portInwardPackingList");
			        	var ids = $("#portInwardPackingList").jqGrid('getDataIDs');
			        	
			        	
			        	
			        	for(var i=0;i < ids.length;i++){ 
			        		var rowObject = $grid.jqGrid('getRowData',ids[i]); 
			        		
			        		var composedObj = composeObjectForCaching(rowObject, 0);
							
			        		$grid.jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false, searchOperators:true, defaultSearch:"cn"});
			        		
			        		
							
		        		} 
			       },
			           		
			        onSelectRow: handleOnSelectRow,
			       	onSelectAll: function(aRowids, status) {
			       	        	for(var i=0;i<aRowids.length;i++){
			       	            	handleOnSelectRow(aRowids[i],status);
			       	            }
			       	        }
				});
	
});

	

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

function composeObjectForCaching(rowObject,qty){
	//var cachedObject = rowObject.portInwardId+"-"+rowObject.portInwardDetailId+"-"+rowObject.portInwardShipmentId;
	//return cachedObject;
	
	var cachedObj = {
			portInwardId : rowObject.portInwardId,
			portInwardDetailId : rowObject.portInwardDetailId,
			
			length : rowObject.length,
			width : rowObject.width,
			thickness : rowObject.thickness,
			vesselDate : rowObject.vesselDate,
			vesselName : rowObject.vesselName,
			millName : rowObject.millName,
			availableQuantity : rowObject.quantity,
			grade : rowObject.grade,
			materialType : rowObject.materialType,
			
			vehicleDate:rowObject.vehicleDate,
			vehicleName:rowObject.vehicleName,
			actualWt:rowObject.actualWt,
			vendorName : rowObject.vendorName,
			make:rowObject.make,
			actualWt_unit:rowObject.actualWt_unit
	};
	return cachedObj;
}

function handleOnSelectRow(rowId, status){
	console.log("---------->>>> "+rowId);
	console.log("---------->>>> "+status);
	var row = jQuery("#portInwardPackingList").jqGrid('getRowData',rowId); 
	var orderedQty = $("#ordered_qty_"+rowId).val()
	try{
		var x = Number(orderedQty);
	}catch (e){
		orderedQty = 1;
	}
	var objectForCaching = composeObjectForCaching(row, orderedQty);
	
	if(status){
		//If already present, update the orderedQuantity in cache
		
		var isPresentObj = isObjectPresentInCache(objectForCaching);
		console.log("isPresentObj = "+isPresentObj);
		if(isPresentObj){
			//updateOrderedQuantityInCache(objectForCaching);
			$("#ordered_qty_"+rowId).val(isPresentObj.orderedQuantity);
			var val = calculateOutQty(rowId, isPresentObj.orderedQuantity);
			$("#portInwardPackingList").jqGrid("setCell", rowId, "outQty", val);
		}else{
			//Now add the customer code to array.
			
			objectForCaching.orderedQuantity = 1;
			
			
			//Push items into cache as selected.
			console.log("<<<<<<<>>>>>>>>>>>>>>>>>>>");
			console.log(objectForCaching);
			SELECTED_PORT_INVENTORY_ITEMS.push(objectForCaching);
			
		}
		
	}else{
		/* SELECTED_PORT_INVENTORY_ITEMS = $.grep(SELECTED_PORT_INVENTORY_ITEMS, function (value){
			return compareCachedObjects(value, objectForCaching);
			
			//return value != objectForCaching;
		}); */
		removeItemFromCache(objectForCaching);
		
	}
	
	//Refresh the table.
	refreshPortOutwardTable();
}



function refreshPortOutwardTable(){
	//$("#portOutwardRecordsTable").empty();
	$("#details-tbody").empty();
	
	
	for(var i=0;i<SELECTED_PORT_INVENTORY_ITEMS.length;i++){
		addRowOfSelectedRecord(SELECTED_PORT_INVENTORY_ITEMS[i]);
	}
	
	calculateSumOfOrderedQuantity();
		
}

function calculateSumOfOrderedQuantity(){
	//Calculate total item quantity
	var quantitySum = 0;
	var sectionwtSum=0;
	console.log("calculating checksum");
	$(".port_out_item_quantity").each(function (index, elem){
		console.log(elem);
		var q = Number($(elem).val());
		quantitySum = quantitySum + q;
		
		//$("#portInwardPackingList").jqGrid('footerData', 'set', {  'quantity': quantitySum });
	});
	
	$(".port_out_section_wt").each(function (index, elem){
		console.log(elem);
		var secwt = Number($(elem).val());
		sectionwtSum = sectionwtSum + secwt;
	});

	$("#sumOfOrderedQuantity").html(quantitySum);
	//addQuantitySumRow(quantitySum,sectionwtSum);
}

function removeItemFromCache(objectToRemove){
	var newArr = [];
	for(var i=0;i<SELECTED_PORT_INVENTORY_ITEMS.length;i++){
		var cachedObj = SELECTED_PORT_INVENTORY_ITEMS[i];
		var isSame = compareCachedObjects(cachedObj,objectToRemove );
		if(!isSame){
			newArr.push(cachedObj);
		}else{
			console.log("Matched object. So will be removed.");
		}
		
	}
	
	SELECTED_PORT_INVENTORY_ITEMS = newArr;
}

function compareCachedObjects(one, two){
	
	var flag1 = one.portInwardId == two.portInwardId;
	var flag2 = one.portInwardDetailId == two.portInwardDetailId;
	var flag3 = one.portInwardShipmentId == two.portInwardShipmentId;
	var flag4 = one.portOutwardId == two.portOutwardId;
	//var flag4 = one.orderedQuantity == two.orderedQuantity;
	
	var isSame = flag1 && flag2 && flag3 && flag4;// && flag4;
	
	return isSame;
}

function isOrderedQtyLessThanAvailableQtyAtPort(orderedQty, jqGridRowId){
	var $packingListGrid = $("#portInwardPackingList");
	var row = jQuery("#portInwardPackingList").jqGrid('getRowData',jqGridRowId); 
	var availableQty = Number(row.quantity);
	var isMore = false;
	if(orderedQty > availableQty){
		isMore = true;
	}
	return isMore;
}



function setTick(jqGridRowId){
	
	try{
		jqGridRowId = jqGridRowId+"";
		var $packingListGrid = $("#portInwardPackingList");
		var orderedQty = Number($("#ordered_qty_"+jqGridRowId).val());
		//console.log("Ordered Qty = "+orderedQty);
		var val = calculateOutQty(jqGridRowId, orderedQty);
	    $("#portInwardPackingList").jqGrid("setCell", jqGridRowId, "outQty", val);
				
		
		var isMore = isOrderedQtyLessThanAvailableQtyAtPort(orderedQty, jqGridRowId);
		if(isMore){
			alert("You have entered quantity more than that is available at port.");
		}
		
		if(orderedQty > 0){
			
			var selRowIds = $packingListGrid.jqGrid("getGridParam", "selarrrow");
			if ($.inArray(jqGridRowId, selRowIds) >= 0) {
			    // the row having rowId is selected
			    console.log("Already selected");
			    
			    updateQuantityInCache(jqGridRowId, orderedQty);
			  	//Refresh the table.
				refreshPortOutwardTable();
			    
			}else{
				$packingListGrid.jqGrid("setSelection", jqGridRowId);
				
			}
				
		}else{
			$packingListGrid.jqGrid("setSelection", jqGridRowId);
		}
			
	}catch(e){
		console.log(e);
	}
	
	
	
}

function updateQuantityInCache(jqGridRowId, orderedQty){
	var $packingListGrid = $("#portInwardPackingList");
	var rowObject = $packingListGrid.jqGrid('getRowData',jqGridRowId); 
	var objectForCompare = composeObjectForCaching(rowObject, orderedQty);
	var objectFromCache = isObjectPresentInCache(objectForCompare);
	if(objectFromCache){
		objectFromCache.orderedQuantity = orderedQty;
	}
	
	
}



function composeCombinationId(recordObj){
	//var comboId = ""+ recordObj.portInwardId + "-"+recordObj.portInwardDetailId+"-"+recordObj.portInwardShipmentId;
	var comboId = ""+ recordObj.portInwardId + "-"+recordObj.portInwardDetailId ;
	return comboId;
}

function composeCombinationClass(id){
	//var comboId = ""+ recordObj.portInwardId + "-"+recordObj.portInwardDetailId+"-"+recordObj.portInwardShipmentId;
	var comboId = "portoutward-group-"+ id;
	return comboId;
}



function composeJsonCellId(parentTrId){
	var jsonCellId = "jsonstr-"+parentTrId;
	return jsonCellId;
}

function onOrderedQuantityChanged(portInwardId, portInwardDetailId){
	console.log(portInwardId+" -- "+ portInwardDetailId);
	var comboId = ""+ portInwardId + "-" + portInwardDetailId ;
	var qtyElementId = "port_out_item_quantity-"+comboId;
	var newQuantity = $("#"+qtyElementId).val();
	$.each(SELECTED_PORT_INVENTORY_ITEMS, function(i,elem){
		var cachedPortInwardId = elem.portInwardId;
		var chachedPortInwardDetailId = elem.portInwardDetailId;
		console.log(cachedPortInwardId+" ==== "+chachedPortInwardDetailId);
		if(portInwardId == cachedPortInwardId && portInwardDetailId == chachedPortInwardDetailId){
			console.log(elem);
			elem.orderedQuantity = newQuantity;
			console.log(elem);
		}
	});

	calculateSumOfOrderedQuantity();
}

function addRowOfSelectedRecord(recordObj) {
	console.log(">>>>>>>>>>>>>>>");
	console.log(recordObj);
	var id = composeCombinationId(recordObj);

	var jsonCellId = composeJsonCellId(id);
	var warehouseInwardRecordClass = composeCombinationClass(id);
	var recordObjJson = JSON.stringify(recordObj);	
	
	var $grid = $("#portInwardGrid");
	
	var selectedRowId = $grid.jqGrid ('getGridParam', 'selrow');
	var vesselDate = $grid.jqGrid('getCell', selectedRowId, 'vesselDate');
	var vendorName = $grid.jqGrid('getCell', selectedRowId, 'vendorName');
	var vesselName = $grid.jqGrid('getCell', selectedRowId, 'vesselName');
	var materialType = $grid.jqGrid('getCell', selectedRowId, 'materialType');
	var millName = $grid.jqGrid('getCell', selectedRowId, 'millName');
	var make = $grid.jqGrid('getCell', selectedRowId, 'make');
	var grade = $grid.jqGrid('getCell', selectedRowId, 'grade');
	
	
	var str = "<tr id='" + id + "' class='selected-port-outward-records "+warehouseInwardRecordClass+"' data-attribute-group-class='"+warehouseInwardRecordClass+"' data-attribute-original-quantity='"+recordObj.availableQuantity+"' >"
			+ "<td><input type='text' readonly placeholder='vesselDate' value='"+vesselDate+"' name='vesselDate' class='form-control'  /></td>"
			+ "<td><input type='text' readonly placeholder='vendorName' value='"+vendorName+"' name='vendorName' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='vesselName' value='"+vesselName+"' name='vesselName' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='materialType' value='"+materialType+"' name='Type' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='millName' value='"+millName+"' name='millName' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='make' value='"+make+"' name='make' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='grade' value='"+grade+"' name='grade' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='thickness' value='"+recordObj.thickness+"' name='thickness' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='width' value='"+recordObj.width+"' name='width' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='length' value='"+recordObj.length+"' name='length' class='form-control' /></td>"
			+ "<td ><input type='number'  onChange='onOrderedQuantityChanged("+recordObj.portInwardId + ","+recordObj.portInwardDetailId+")' placeholder='Quantity' max='"+recordObj.availableQuantity+"' value='"+recordObj.orderedQuantity+"' name='availableQuantity' class='form-control port_out_item_quantity' id='port_out_item_quantity-" + id + "' data-attribute-parent-port-out-id='port_out_item_quantity-" + id + "' /></td>"
			+ "<td><input type='hidden'  value='"+recordObj.portInwardId+"' name='portInwardId'/></td>"

			+ "<td><input type='hidden' value='"+recordObj.portInwardDetailId+"' name='portInwardDetailId'/></td>"
			+ "<td><input type='hidden' value='"+recordObj.portInwardShipmentId+"' name='portInwardShipmentId'/></td>"
			+ "<td><input type='hidden' value='"+recordObjJson+"' id='"+jsonCellId+"'/></td>"
			+ "</tr>";
	$("#details-tbody").append(str);

}
function addQuantitySumRow(quantitySum,sectionwtSum) {
	
	
	var str = "<tr id='quantity-sum-row'><td class='vessel-container'></td>"
			+ "<td></td>"
			//+ "<td>be no</td>"
			//+ "<td>Material Type</td>"
			+ "<td></td>"
			+ "<td></td>"
			+ "<td></td>"
			+ "<td></td>"
			+ "<td></td>"
			+ "<td></td>"
			+ "<td></td>"
			+ "<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Checksum </td>"
			+ "<td class='port_out_item_quantity' >&nbsp&nbsp"+quantitySum+"</td>";
	$("#details-tbody").append(str);
	

	//applyNumericConstraint();
	//applyTotalCalc();
}
function calculateOutQty(rowId, orderedQuantity){
	var data = $("#portInwardPackingList").jqGrid('getRowData');
	var row = data[rowId-1];
	var outqtyval= (row.length * row.width * row.thickness * orderedQuantity * 7.85) / 1000000000;
	return outqtyval.toFixed(3);
}

function composeJsonCellId(parentTrId){
	var jsonCellId = "jsonstr-"+parentTrId;
	return jsonCellId;
}
function composeCombinationClass(id){
	//var comboId = ""+ recordObj.portInwardId + "-"+recordObj.portInwardDetailId+"-"+recordObj.portInwardShipmentId;
	var comboId = "portoutward-group-"+ id;
	return comboId;
}

function locationId(recordObj){
	var trElementId = composeCombinationId(recordObj);
	var locationCellId = "location-"+trElementId;
	return locationCellId;
}

function submitPortPurchaseOrder(){
	
	var custName = getValByFieldName("body", "customerName");
	var brokerName = getValByFieldName("body", "brokerName");
	var brokerage = getValByFieldName("body", "brokerage");
	var brokerageUnit = getValByFieldName("body", "brokerageUnit");
	
	var deliveryAddrs = getValByFieldName("body", "deliveryAddress");
	var rate = getValByFieldName("body", "rate");
	var excise = getValByFieldName("body", "excise");
	var tax = getValByFieldName("body", "tax");
	var transport = getValByFieldName("body", "transport");
	var paymanetTerms = getValByFieldName("body", "paymentTerms");
	var comments = getValByFieldName("body", "comments");
	
	
	var parseTotal=  $(portInwardPackingList).jqGrid('getCol', 'quantity', false, 'sum');
	
	
	
	var selected_port_inventory_items_JSON = composeSelectedPortPurchaseOrderJson();
	console.log(selected_port_inventory_items_JSON);
	var postJsonObject = {
			
			custName : custName,
			 brokerName  : brokerName,
			 brokerage: brokerage,
			 deliveryAddr: deliveryAddrs,
			 rate:rate,
			 excise:excise,
			 tax:tax,
			 transport:transport,
			 paymentTerms:paymanetTerms,
			 comments : comments,
			 totalQty:parseTotal,
			 brokerageUnit:brokerageUnit,
			 
			selectedPortInventoryItemsJson : selected_port_inventory_items_JSON
	};
	
	var itemsToSaveJson  = "itemsToSaveJson="+encodeURIComponent(JSON.stringify(postJsonObject));//+"&genericListener=add";
	console.log(itemsToSaveJson);
	//itemsToSaveJson = encodeURIComponent(itemsToSaveJson);
	//console.log(itemsToSaveJson);
	$.ajax({
		url: "port-purchase-order-save",
		method: 'POST',
		data: itemsToSaveJson,
		dataType: "json",
		success : function(result){
			console.log(result);
			
			if(result && result.status == 'success'){
				var portPurchaseOrderId = result.portPurchaseOrderId;
				bootbox.alert("Successfully saved order : "+portPurchaseOrderId, function(){
					location.reload();	
				});
			
			}else{
				bootbox.alert("Failed to save record. Reason: "+result.errorMessage);
			}
			
			
		},
		error : function(msg){
			console.log(msg);
			bootbox.alert("Some error at server! Please call administrator.");
		}
	});
}
function composeSelectedPortPurchaseOrderJson(){
	var portPurchaseOrderObjectArray = [];
	$(".selected-port-outward-records").each(function(i, elem){
		var $elem = $(elem);
		var selectedPortPurchaseOrderObj = composePortPurchaseOrderObject($elem);
		portPurchaseOrderObjectArray.push(selectedPortPurchaseOrderObj);
	});
	var json = JSON.stringify(portPurchaseOrderObjectArray);
	return json;
}

function composePortPurchaseOrderObject($elem){
	//var vesselDate = getValueByName($elem, "vesselDate");
	var portInwardId = getValueByName($elem, "portInwardId");
	var portInwardDetailId = getValueByName($elem, "portInwardDetailId");
	
	//var length = getValueByName($elem, "length");
	//var width = getValueByName($elem, "width");
	//var thickness = getValueByName($elem, "thickness");
	//var vesselName = getValueByName($elem, "vesselName");
	//var millName = getValueByName($elem, "millName");
	var orderedQuantity = getValueByName($elem, "availableQuantity");
	//var grade = getValueByName($elem, "grade");
	//var materialType = getValueByName($elem, "Type");

	//var vehicleDate = getValueByName($elem, "vehicleDate");
	//var vehicleName = getValueByName($elem, "vehicleName");
	//var actualWt = getValueByName($elem, "actualWt");
	//var vendorName = getValueByName($elem, "vendorName");
	//var make = getValueByName($elem, "make");

	var portPurchaseOrdersObject = {
			portInwardId : portInwardId,
			portInwardDetailId : portInwardDetailId,
			orderedQuantity : orderedQuantity
			//length : length,
			//width : width,
			//thickness : thickness,
			//vesselDate : vesselDate,
			//vesselName : vesselName,
			//millName : millName,
			//availableQuantity : availableQuantity,
			//grade : grade,
			//materialType : materialType,
			//vehicleDate : vehicleDate,
			//vehicleName : vehicleName,
			//actualWt : actualWt,
			//vendorName : vendorName,
			//make : make
	
			
	};
	console.log(portPurchaseOrdersObject);
	return portPurchaseOrdersObject;
}
function getValueByName($elem, elementName){
	return $elem.find("[name="+elementName+"]").val();
}
</script>

