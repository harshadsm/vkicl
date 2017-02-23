<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.form.PortOutwardForm"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c" %>



<script type="text/javascript">
	var SELECTED_PORT_INVENTORY_ITEMS = [];
	var customer = [], vehicleNumber = [], vesselName = [], grade = [];
	

	
	function selectRadioText(i) {
		var radio = $($("input[name='destination']")[i]);
		$(radio).click();
		updateHiddenField();
	}

	function updateHiddenField() {
		$("input[name=destination]").each(function(i, radio) {
			if (radio.checked) {
				var textbox = $(radio).parent().next().find(".tt-input")[0];
				var value = $(textbox).val();
				if(typeof value === "undefined"){
					var textbox = $(radio).parent().next();
					var value = $(textbox).val();
				}
				$("input[name='destinationName']").val(value);
			}
		});
	}

	function fillDates(id) {
		var param1 = $("#" + id + " input[name='vesselName']").val();
		var select = $("#" + id + " select[name='vesselDate']");
		var query = "query.combo.dates";
		if (null != param1 && "" != param1) {
			showLoader();
			$.ajax({
				url : "./xml?query=" + query + "&count=1&param1=" + param1,
				success : function(xml, textStatus, response) {
					fillXmlSuccess(xml, select);
					hideLoader();
					fillBeNo(id);
				},
				error : function() {
					bootbox.alert("Unable to fill Autocomplete for "
							+ arrayName);
					hideLoader();
				}
			});

		} else {
			$(select).html("<option value=''>--</option");
			fillBeNo(id);
		}
	}

	function fillBeNo(id) {
		var param1 = $("#" + id + " input[name='vesselName']").val();
		var param2 = $("#" + id + " select[name='vesselDate']").val();
		var select = $("#" + id + " select[name='beNo']");
		var query = "query.combo.beNo";
		if (null != param1 && "" != param1) {
			showLoader();
			$.ajax({
				url : "./xml?query=" + query + "&count=2&param1=" + param1 + "&param2=" + param2,
				success : function(xml, textStatus, response) {
					fillXmlSuccess(xml, select);
					hideLoader();
					fillMaterialType(id);
				},
				error : function() {
					bootbox.alert("Unable to fill Autocomplete for "
							+ arrayName);
					hideLoader();
				}
			});

		} else {
			$(select).html("<option value=''>--</option");
			fillMaterialType(id);
		}
	}

	function fillMaterialType(id) {
		var param1 = $("#" + id + " input[name='vesselName']").val();
		var param2 = $("#" + id + " select[name='vesselDate']").val();
		var param3 = $("#" + id + " select[name='beNo']").val();
		var select = $("#" + id + " select[name='materialType']");
		var query = "query.combo.materialType";
		if (null != param1 && "" != param1) {
			showLoader();
			$.ajax({
				url : "./xml?query=" + query + "&count=3&param1=" + param1 + "&param2=" + param2 + "&param3=" + param3,
				success : function(xml, textStatus, response) {
					fillXmlSuccess(xml, select);
					hideLoader();
					fillGrade(id);
				},
				error : function() {
					bootbox.alert("Unable to fill Autocomplete for "
							+ arrayName);
					hideLoader();
				}
			});

		} else {
			$(select).html("<option value=''>--</option");
			fillGrade(id);
		}
	}

	function fillGrade(id) {
		var param1 = $("#" + id + " input[name='vesselName']").val();
		var param2 = $("#" + id + " select[name='vesselDate']").val();
		var param3 = $("#" + id + " select[name='beNo']").val();
		var param4 = $("#" + id + " select[name='materialType']").val();
		var select = $("#" + id + " select[name='grade']");
		var query = "query.combo.grade";
		if (null != param1 && "" != param1) {
			showLoader();
			$.ajax({
				url : "./xml?query=" + query + "&count=4&param1=" + param1 + "&param2=" + param2 + "&param3=" + param3 + "&param4=" + param4,
				success : function(xml, textStatus, response) {
					fillXmlSuccess(xml, select);
					hideLoader();
				},
				error : function() {
					bootbox.alert("Unable to fill Autocomplete for "
							+ arrayName);
					hideLoader();
				}
			});

		} else
			$(select).html("<option value=''>--</option");
	}

	var id = 1;

	function addRow() {
		var str = "<tr id='row-" + id + "'><td class='vessel-container'><input type='text' placeholder='Vessel Name' value='' onblur='fillDates(\"row-"+ id+ "\");' name='vesselName' class='form-control' /></td>"
				+ "<td><select name='vesselDate' class='form-control' onChange='fillBeNo(\"row-"+ id+ "\");'><option value=''>--</option></select></td>"
				+ "<td><select name='beNo' class='form-control' onChange='fillMaterialType(\"row-"+ id+ "\");'><option value=''>--</option></select></td>"
				+ "<td><select name='materialType' class='form-control' onChange='fillGrade(\"row-"+ id+ "\");'><option value=''>--</option></select></td>"
				+ "<td><select name='grade' class='form-control'><option value=''>--</option></select></td>"
				+ "<td><input type='text' placeholder='Description' value='' name='desc' class='form-control' /></td>"
				+ "<td><input type='number' step='0.001' placeholder='Thickness' min='0' value='' name='thickness' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' class='form-control' /></td>"
				+ "<td><input type='number' step='1' placeholder='Width' min='0' value='' name='width' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id	+ "\");' class='form-control' /></td>"
				+ "<td><input type='number' step='1' placeholder='Length' min='0' value='' name='length' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' class='form-control' /></td>"
				+ "<td><input type='number' step='1' placeholder='Quantity' min='0' value='' name='qty' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' class='form-control' /></td>"
				//+ "<td><div class='input-group'><input type='number' step='0.001' placeholder='Section Weight' min='0' readonly value='' name='secWt' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' name='secWtUnit' value='TON' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' disabled aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>TON</a></li><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>KG</a></li></ul></div></div></td>"
				// + "<td><div class='input-group'><input type='number' step='0.001' placeholder='Actual Weight' min='0' value='' name='actualWt' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' name='actualWtUnit' value='TON' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>TON</a></li><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>KG</a></li></ul></div></div></td>"
				+ "<td><input type='button' class='btn-danger delete-row' onclick='deleteRow($(this).parent().parent().attr(\"id\"));' value='-' /></td></tr>";
		$("#details-tbody").append(str);
		id = id + 1;

		fillArray('vesselName', 'query.unique.vesselName');
		fillArray('grade', 'query.unique.grade');
		applyNumericConstraint();
		applyTotalCalc();
	}

	function deleteRow(id) {
		if ($("#details-tbody tr").length > 1)
			$("#" + id).remove();
		else
			bootbox.alert("Cannot Delete Last Row");
		applyTotalCalc();
	}

	function validateForm() {
		updateHiddenField();
		if ("" == getValByFieldName("body", "destinationName")) {
			bootbox.alert("Please enter Destination Name");
			return false;}
		 if ("" == getValByFieldName("body", "vehicleNumber")) {
			bootbox.alert("Please enter Vehicle Number");
			return false;
		} else if ("" == getValByFieldName("body", "vehicleDate")) {
			bootbox.alert("Please enter Vehicle Date");
			return false;
		}
		
		
		
		var	str = "Are you sure you want to Submit?";
		bootbox.confirm(str, function(result) {
			if (result) {
				submitCachedPortOutwardRecords();
			}
		});
		return false;
		
		//return commonSubmit();
	}
	
	function submitCachedPortOutwardRecords(){
		var destination = getValByFieldName("body", "destinationName");
		var vehicle_number = getValByFieldName("body", "vehicleNumber");
		var vehicle_date = getValByFieldName("body", "vehicleDate");
		var selected_port_inventory_items_JSON = JSON.stringify(SELECTED_PORT_INVENTORY_ITEMS);
		var postJsonObject = {
				destinationName : destination,
				vehicleNumber : vehicle_number,
				vehicleDate : vehicle_date,
				selectedPortInventoryItemsJson : selected_port_inventory_items_JSON
		};
		var itemsToSaveJson = "genericListener=add&itemsToSaveJson="+JSON.stringify(postJsonObject);
		console.log(itemsToSaveJson);
		$.ajax({
			url: "port-outward-save.do",
			method: 'POST',
			data: itemsToSaveJson,
			success : function(msg){
				console.log(msg);
				bootbox.alert("Successfully saved records!", function(){
					location.reload();	
				});
				
			},
			error : function(msg){
				console.log(msg);
				bootbox.alert("Some error at server! Please call administrator.");
			}
		});
		
	}

	function resetOutwardForm() {
		resetForm();
		$('input[name=destinationName]').val('');
		$('select').html('<option>No Dates</option');
		$('[name="warehouse"]').val("Taloja");
	}
</script>
<div>
	<html:form action="/port-outward" >
		<div class="row">
			<div class="col-md-2">
				<h3 class="page-head">Port Outward</h3>
			</div>
		</div>
		<div class="row">
		
			<div class="col-md-2">
				
			
				<table class="table table-responsive">
					<tr>
						<td>
							<div class="input-group">
								<span class="input-group-addon"> <html:radio
										property="destination" value="warehouse" 
										onclick="updateHiddenField();" /></span> <input type="text"
									name="warehouse" class="form-control" value="Taloja" readonly="readonly"
									placeholder="Warehouse Name" onclick="selectRadioText(0);"
									onblur="updateHiddenField();" onchange="updateHiddenField();" />
							</div>
							<!-- <div class="or-label">Or</div>
							<div class="input-group">
								<span class="input-group-addon"> <html:radio
										property="destination" value="customer"
										onclick="updateHiddenField();" /></span> <input type="text"
									name="customer" class="form-control"
									placeholder="Customer Name" onclick="selectRadioText(1);"
									onblur="updateHiddenField();" onchange="updateHiddenField();" />
							</div> <html:hidden property="destinationName" /> -->
						</td>
					</tr>
					<tr>
						<td><input type="text" name="vehicleNumber"
								class="form-control" placeholder="Vehicle Number"/></td>
					</tr>
					<tr>
						<td>
							<div class="input-group date date-picker-div" id="datetimepicker1">
								<input type="text" name="vehicleDate" 
								id="vehicleDate" class="form-control" placeholder="Vehicle Date"
								data-date-format="DD-MM-YYYY"/>
								<span class="input-group-addon"><span
									class="glyphicon-calendar glyphicon"></span></span>
							</div>
						</td>
					</tr>
				</table>
			</div>
			
			<span id="report-toolbar" class="pull-right"><span
			class="total-field"></span>Export:<img src="./img/excel.png" id="exportToExcel" title="Export to Excel"
			onClick="prepareTableToExcelAndExport()" /><img
			src="./img/pdf.png" id="exportToPDF" title="Export to PDF"
			style="display: none;" /> </span>
				
				<div class="col-md-10">
					<div>
						<div id="packingListTable">
							<table id="packingListGrid"></table>
							<div id="packingListPager"></div>
						</div>
					</div>
				</div>
			

		</div>
		<div class="row">
			<div class="col-md-12">
				<h3>Review the selected Port Outward Entries below</h3>
				<table class="table table-responsive table-form" id="portOutwardRecordsTable">
					<thead>
						<tr>
						
						<th>Date</th>
							<th>Vessel Name</th>
							<th>Mill Name</th>
							<th>Type</th>
							<!-- <th>B/E No.</th> -->
							<!-- <th>Material Type</th> -->
							<th>Grade</th>
							<!--<th>Description</th>-->
							<th>Thickness</th>
							<th>Width</th>
							<th>Length</th>
							<th>Out pcs</th>
							<th>Out qty</th>
							<!-- <th>Actual Weight</th> -->
							<!-- <th><input type="button" class="btn-success add-row" onClick="addRow();" value="+" /></th> -->
						</tr>
					</thead>
					<tbody id="details-tbody">
					</tbody>
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

	</html:form>
</div>


<script>

$(function() {
	populatePackingList();
});		






function populatePackingList(){
	
	$("#packingListGrid").jqGrid(
		{
			url : './packingListJsonServlet',
			datatype : 'json',
			
			mtype : 'POST',
			
			
			colNames : [ 'portInwardId', 'portInwardDetailId', 'portInwardShipmentId', 'Date', 'Vessel Name', 'Mill Name', 'Type', 'Grade', 'Thickness', 'Width', 'Length', 'Bal Pcs', 'Bal Qty', 'Out Pcs','Out Qty' , 'Actual Wt','Actual Wt Unit'],
					
			colModel : [  {
				name : 'portInwardId',
				index : 'portInwardId',
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
			}, {
				name : 'portInwardDetailId',
				index : 'portInwardDetailId',
				hidden: true,
				width : 185,
				editable :false,
				editrules : {
					required : true
				},
				editoptions : {
					size : 10
				},
				search:false,
				searchoptions: { sopt:['ge']}
			}, {
				name : 'portInwardShipmentId',
				index : 'portInwardShipmentId',
				hidden: true,
				width : 200,
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
				name : 'vesselDate',
				index : 'vessel_date',
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
				name : 'vesselName',
				index : 'vessel_name',
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
				name : 'millName',
				index : 'mill_name',
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
				name : 'materialType',
				index : 'material_type',
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
				name : 'balQty',
				index : 'balQty',
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
			    name: 'txtVAlue',
			    width: 100,
			    search:false,
			    align: 'center',
			    formatter: function (cellValue, option) {
			    	//console.log(option);
			        return '<input number digits="" type="number" size="7" style="color:black;" name="txtBox" id="ordered_qty_' + option.rowId +'" value="" onchange="setTick('+option.rowId+')"/>';
			}
			
			}
			, 
			{
				name : 'outQty',
				index : 'outQty',
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
				name : 'actualWt',
				index : 'actualWt',
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
			}
			,
			{
				name : 'actualWt_unit',
				index : 'actualWt_unit',
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
			},
			
			
			],
			postData : {
			},
			rowNum : 10,
			rowList : [ 10, 20, 30 ,40, 50, 60 ],
			height : 280,
			autowidth : true,
			rownumbers : true,
			multiselect : true,
			pager : '#packingListPager',
			sortname : 'port_inward_detail_id',
			viewrecords : true,
			sortorder : "desc",
			caption : "Inventory available at Port",
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
	        	var $grid = $("#packingListGrid");
	        	var ids = $("#packingListGrid").jqGrid('getDataIDs');
	        	
	        	for(var i=0;i < ids.length;i++){ 
	        		var rowObject = $grid.jqGrid('getRowData',ids[i]); 
	        		var composedObj = composeObjectForCaching(rowObject, 0);
					
	        		$grid.jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false, searchOperators:true, defaultSearch:"cn"});
	        		
	        		//Pre-select the customers if user had them selected already
					if(SELECTED_PORT_INVENTORY_ITEMS.length > 0){
						
						var cachedObj = isObjectPresentInCache(composedObj);
						if(cachedObj){
							console.log("cachedObj.orderedQuantity = "+cachedObj.orderedQuantity);
							$grid.jqGrid("setSelection", ids[i]);
							
							$("#ordered_qty_"+ids[i]).val(cachedObj.orderedQuantity);
							var val = calculateOutQty(rowId, cachedObj.orderedQuantity);
							$("#packingListGrid").jqGrid("setCell", rowId, "outQty", val);
						}else{
							console.log("Not present in cache "+composedObj.thickness);
						}
					}
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
			portInwardShipmentId : rowObject.portInwardShipmentId,
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
			actualWt:rowObject.actualWt,
			actualWt_unit:rowObject.actualWt_unit
	};
	return cachedObj;
}

function handleOnSelectRow(rowId, status){
	
	var row = jQuery("#packingListGrid").jqGrid('getRowData',rowId); 
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
		if(isPresentObj){
			//updateOrderedQuantityInCache(objectForCaching);
			$("#ordered_qty_"+rowId).val(isPresentObj.orderedQuantity);
			var val = calculateOutQty(rowId, isPresentObj.orderedQuantity);
			$("#packingListGrid").jqGrid("setCell", rowId, "outQty", val);
		}else{
			//Now add the customer code to array.
			try{
				var x = Number(objectForCaching.orderedQuantity);
				if(x <= 0){
					objectForCaching.orderedQuantity = 1;
				}
			}catch(e){
				objectForCaching.orderedQuantity = 1;
			}
			
			
			
			if($("#ordered_qty_"+rowId).val()==""){
				$("#ordered_qty_"+rowId).val("1");
				var val = calculateOutQty(rowId, objectForCaching.orderedQuantity);
				$("#packingListGrid").jqGrid("setCell", rowId, "outQty", val);
				objectForCaching.outQty=val;
			}
			
			SELECTED_PORT_INVENTORY_ITEMS.push(objectForCaching);
		}
		
	}else{
		/* SELECTED_PORT_INVENTORY_ITEMS = $.grep(SELECTED_PORT_INVENTORY_ITEMS, function (value){
			return compareCachedObjects(value, objectForCaching);
			
			//return value != objectForCaching;
		}); */
		removeItemFromCache(objectForCaching);
		$("#ordered_qty_"+rowId).val("");
		$("#packingListGrid").jqGrid("setCell", rowId, "outQty", " ");
		$("#balQty").val("122");
	}
	
	//Refresh the table.
	refreshPortOutwardTable();
	
}

function calculateOutQty(rowId, orderedQuantity){
	var data = $("#packingListGrid").jqGrid('getRowData');
	var row = data[rowId-1];
	var outqtyval= (row.length * row.width * row.thickness * orderedQuantity * 7.85) / 1000000000;
	return outqtyval.toFixed(3);
}

function refreshPortOutwardTable(){
	//$("#portOutwardRecordsTable").empty();
	$("#details-tbody").empty();
	
	
	for(var i=0;i<SELECTED_PORT_INVENTORY_ITEMS.length;i++){
		addRowOfSelectedRecord(SELECTED_PORT_INVENTORY_ITEMS[i]);
	}
	
	//Calculate total item quantity
	var quantitySum = 0;
	var sectionwtSum=0;
	console.log("calculating checksum");
	$(".port_out_item_quantity").each(function (index, elem){
		console.log(elem);
		var q = Number($(elem).val());
		quantitySum = quantitySum + q;
	});
	
	$(".port_out_section_wt").each(function (index, elem){
		console.log(elem);
		var secwt = Number($(elem).val());
		sectionwtSum = sectionwtSum + secwt;
	});
	addQuantitySumRow(quantitySum,sectionwtSum);
		
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
	//var flag4 = one.orderedQuantity == two.orderedQuantity;
	
	var isSame = flag1 && flag2 && flag3;// && flag4;
	
	return isSame;
}

function isOrderedQtyLessThanAvailableQtyAtPort(orderedQty, jqGridRowId){
	var $packingListGrid = $("#packingListGrid");
	var row = jQuery("#packingListGrid").jqGrid('getRowData',jqGridRowId); 
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
		var $packingListGrid = $("#packingListGrid");
		var orderedQty = Number($("#ordered_qty_"+jqGridRowId).val());
		//console.log("Ordered Qty = "+orderedQty);
		var val = calculateOutQty(jqGridRowId, orderedQty);
	    $("#packingListGrid").jqGrid("setCell", jqGridRowId, "outQty", val);
				
		
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
	var $packingListGrid = $("#packingListGrid");
	var rowObject = $packingListGrid.jqGrid('getRowData',jqGridRowId); 
	var objectForCompare = composeObjectForCaching(rowObject, orderedQty);
	var objectFromCache = isObjectPresentInCache(objectForCompare);
	
	var val = calculateOutQty(jqGridRowId, orderedQty);
    
		
    
	if(objectFromCache){
		objectFromCache.orderedQuantity = orderedQty;
		objectFromCache.outQty=val;
	}
	
	
}

function composeCombinationId(recordObj){
	var comboId = ""+ recordObj.portInwardId + "-"+recordObj.portInwardDetailId+"-"+recordObj.portInwardShipmentId;
	return comboId;
}

function addRowOfSelectedRecord(recordObj) {
	console.log(recordObj);
	var id = composeCombinationId(recordObj);
	

	var str = "<tr id='" + id + "'>"
			
	+ "<td><input type='text' readonly placeholder='vesselDate' value='"+recordObj.vesselDate+"' name='vesselDate' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='vesselName' value='"+recordObj.vesselName+"' name='vesselName' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='millName' value='"+recordObj.millName+"' name='millName' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='materialType' value='"+recordObj.materialType+"' name='Type' class='form-control' /></td>"
			//+ "<td>be no</td>"
			//+ "<td>Material Type</td>"
			+ "<td><input type='text' readonly placeholder='grade' value='"+recordObj.grade+"' name='grade' class='form-control' /></td>"
			// + "<td><input type='text' placeholder='Description' value='' name='desc' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='thickness' value='"+recordObj.thickness+"' name='thickness' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='width' value='"+recordObj.width+"' name='width' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='length' value='"+recordObj.length+"' name='length' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='orderedQuantity' value='"+recordObj.orderedQuantity+"' name='availableQuantity' class='form-control port_out_item_quantity' /></td>"
			+ "<td><input type='text' readonly placeholder='balQty' value='"+recordObj.outQty+"' name='balQty' class='form-control port_out_section_wt' /></td>"
			+ "<td><input type='hidden' readonly placeholder='actualWt' value='"+recordObj.actualWt+"' name='actualWt' class='form-control ' /></td>"
			//+ "<td><div class='input-group'><input type='number' step='0.001' placeholder='Section Weight' min='0' readonly value='' name='secWt' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' name='secWtUnit' value='TON' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' disabled aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>TON</a></li><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>KG</a></li></ul></div></div></td>"
			 //+ "<td><div class='input-group'><input type='number' step='0.001' placeholder='Actual Weight' min='0' value='' name='actualWt' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' name='actualWtUnit' value='TON' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>TON</a></li><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>KG</a></li></ul></div></div></td>"
			//+ "<td><input type='button' class='btn-danger delete-row' onclick='deleteRow($(this).parent().parent().attr(\"id\"));' value='-' /></td></tr>";
			+ "<td></td>"
			+ "<td></td>";
	$("#details-tbody").append(str);
	

	//applyNumericConstraint();
	//applyTotalCalc();
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
			+ "<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Checksum </td>"
			+ "<td class='port_out_item_quantity' >&nbsp&nbsp"+quantitySum+"</td>"
			+ "<td class='port_out_section_wt'>&nbsp&nbsp"+sectionwtSum.toFixed(3)+"</td>"
			// + "<td><div class='input-group'><input type='number' step='0.001' placeholder='Actual Weight' min='0' value='' name='actualWt' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' name='actualWtUnit' value='TON' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>TON</a></li><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>KG</a></li></ul></div></div></td>"
			//+ "<td><input type='button' class='btn-danger delete-row' onclick='deleteRow($(this).parent().parent().attr(\"id\"));' value='-' /></td></tr>";
			+ "<td></td>"
			+ "<td></td>";
	$("#details-tbody").append(str);
	

	//applyNumericConstraint();
	//applyTotalCalc();
}



//Below function will force the numeric input if type="number" for input tag.
//Source: http://stackoverflow.com/questions/469357/html-text-input-allow-only-numeric-input
function forceNumeric(){
    var $input = $(this);
    $input.val($input.val().replace(/[^\d]+/g,''));
}

$(function(){
	$('body').on('propertychange input', 'input[type="number"]', forceNumeric);
});

function p(){
	for(var i=0;i<SELECTED_PORT_INVENTORY_ITEMS.length;i++){
		var item = SELECTED_PORT_INVENTORY_ITEMS[i];
		console.log(i+" - Ordered Qty = "+item.orderedQuantity);
	}
}



</script>


<div style="display: none;">
	<table id="forExportingToExcel">
		<thead>
			<tr>
				<td>Date</td>
				<td>vessel Name</td>
				<td>Mill Name</td>
				<td>Type</td>
				<td>Grade</td>
				<td>Thickness</td>
				<td>Width</td>
				<td>Length</td>
				<td>Bal Pcs</td>
				<td>Bal Qty</td>
				
			</tr>
		</thead>
		<tbody id="forExportingToExcelBody">
		</tbody>
	</table>
</div>

<script>
function prepareTableToExcelAndExport(){
	var template = "<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td><td>{5}</td><td>{6}</td><td>{7}</td><td>{8}</td><td>{9}</td></tr>";

	$.ajax({ 
                type: 'POST', 
                url: './packingListJsonServlet', 
                data: { rows: $("#packingListGrid").jqGrid("getGridParam", "records"),page:parseInt(($("#packingListGrid").getGridParam('records') / $("#packingListGrid").getGridParam('rowNum')) + 1), orderBy:'port_inward_detail_id', order:'desc'}, 
                dataType:'json',
                success:  function(response){
    	        	 var rowObject = response.rows;
    	        	for(var i=0;i < rowObject.length;i++){ 
    	        		
    	        		//var composedObj = composeObjectForCaching(rowObject, 0);
    	        		var tr = $.validator.format(template,rowObject[i].vesselDate,rowObject[i].vesselName,rowObject[i].millName
    	        				,rowObject[i].materialType,rowObject[i].grade,rowObject[i].thickness,rowObject[i].width,rowObject[i].length,rowObject[i].quantity,rowObject[i].balQty);
    	        		$("#forExportingToExcelBody").append(tr);
    	        	}

    	        	tableToExcel('forExportingToExcel', 'Excel');
    	        	$("#forExportingToExcelBody").empty();
                }
                
            });
	
}
</script>
