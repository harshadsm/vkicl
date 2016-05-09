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
	

	$(document).ready(function() {
		fillArray('customer', 'query.unique.customer');
		fillArray('vehicleNumber', 'query.unique.vehicleNumber');

		if (id == 1)
			addRow();
	});

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
				+ "<td><div class='input-group'><input type='number' step='0.001' placeholder='Section Weight' min='0' readonly value='' name='secWt' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' name='secWtUnit' value='TON' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' disabled aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>TON</a></li><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>KG</a></li></ul></div></div></td>"
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
			return false;
		} else if ("" == getValByFieldName("body", "vehicleNumber")) {
			bootbox.alert("Please enter Vehicle Number");
			return false;
		} else if ("" == getValByFieldName("body", "vehicleDate")) {
			bootbox.alert("Please enter Vehicle Date");
			return false;
		}
		return commonSubmit();
	}

	function resetOutwardForm() {
		resetForm();
		$('input[name=destinationName]').val('');
		$('select').html('<option>No Dates</option');
		$('[name="warehouse"]').val("Taloja");
	}
</script>
<div>
	<html:form action="/port-outward" onsubmit="return validateForm();">
		<div class="row">
		
			<div class="col-md-2">
				<h3 class="page-head">Port Outward</h3>
			
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
							<div class="or-label">Or</div>
							<div class="input-group">
								<span class="input-group-addon"> <html:radio
										property="destination" value="customer"
										onclick="updateHiddenField();" /></span> <input type="text"
									name="customer" class="form-control"
									placeholder="Customer Name" onclick="selectRadioText(1);"
									onblur="updateHiddenField();" onchange="updateHiddenField();" />
							</div> <html:hidden property="destinationName" />
						</td>
					</tr>
					<tr>
						<td><input type="text" name="vehicleNumber"
								class="form-control" placeholder="Vehicle Number"/></td>
					</tr>
					<tr>
						<td>
							<div class="input-group date date-picker-div">
								<input type="text" name="vehicleDate" class="form-control" placeholder="Vehicle Date"/>
								<span class="input-group-addon"><span
									class="glyphicon-calendar glyphicon"></span></span>
							</div>
						</td>
					</tr>
				</table>
			</div>
			
			
				
				
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
				<table class="table table-responsive table-form">
					<thead>
						<tr>
							<th>Vessel Name</th>
							<th>Vessel Date</th>
							<th>B/E No.</th>
							<th>Material Type</th>
							<th>Grade</th>
							<th>Description</th>
							<th>Thickness</th>
							<th>Width</th>
							<th>Length</th>
							<th>Quantity</th>
							<th>Section Weight</th>
							<!-- <th>Actual Weight</th> -->
							<th><input type="button" class="btn-success add-row" onClick="addRow();" value="+" /></th>
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
					onclick="return validateForm();" />
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
			
			
			colNames : [ 'portInwardId', 'portInwardDetailId', 'portInwardShipmentId', 'Out Qty', 'Available Qty', 'Date', 'Vessel Name', 'Grade', 'Length', 'Width','Thickness' ],
					
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
			}, {
			    name: 'txtVAlue',
			    width: 100,
			    search:false,
			    align: 'center',
			    formatter: function (cellValue, option) {
			    	//console.log(option);
			        return '<input number digits="" type="number" size="7" style="color:black;" name="txtBox" id="ordered_qty_' + option.rowId +'" value="" onchange="setTick('+option.rowId+')"/>';
			    }
			},{
				name : 'quantity',
				index : 'quantity',
				width : 150,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false,
				sortable:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			},{
				name : 'vesselDate',
				index : 'vessel_date',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
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
				sortable:true,
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn']}
				
			},{
				name : 'grade',
				index : 'grade',
				width : 200,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				sortable:true,
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			},{
				name : 'length',
				index : 'length',
				width : 200,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
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
				search:true,
				sortable:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			}
			,{
				name : 'thickness',
				index : 'thickness',
				width : 200,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				
				search:true,
				sortable:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			}
			
			
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
	        	console.log(ids);
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
	var isPresent = false;
	if(SELECTED_PORT_INVENTORY_ITEMS.length > 0){
		
		for(var i=0;i<SELECTED_PORT_INVENTORY_ITEMS.length;i++){
			var itemObj = SELECTED_PORT_INVENTORY_ITEMS[i];
			isPresent = compareCachedObjects(itemObj, targetObj);
			
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
			availableQuantity : rowObject.quantity
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
	var cachedObject = composeObjectForCaching(row, orderedQty);
	
	if(status){
		//If already present, remove it. So that we will have only single entry of the customer code.
		SELECTED_PORT_INVENTORY_ITEMS = $.grep(SELECTED_PORT_INVENTORY_ITEMS, function (value){
			console.log("Available in cache = "+SELECTED_PORT_INVENTORY_ITEMS.length);
			var willRemove = compareCachedObjects(value, cachedObject);
			console.log(!willRemove);
			return !willRemove;
			//return value != cachedObject;
		});
		
		//Now add the customer code to array.
		SELECTED_PORT_INVENTORY_ITEMS.push(cachedObject);
		if($("#ordered_qty_"+rowId).val()==""){
			$("#ordered_qty_"+rowId).val("1");
		}
		
		//Add it in the table.
		addRowOfSelectedRecord(cachedObject);
	}else{
		SELECTED_PORT_INVENTORY_ITEMS = $.grep(SELECTED_PORT_INVENTORY_ITEMS, function (value){
			return compareCachedObjects(value, cachedObject);
			
			//return value != cachedObject;
		});
		
		$("#ordered_qty_"+rowId).val("");
	}
	
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
		console.log("Ordered Qty = "+orderedQty);
		
		var isMore = isOrderedQtyLessThanAvailableQtyAtPort(orderedQty, jqGridRowId);
		if(isMore){
			alert("You have entered quantity more than that is available at port.");
		}
		
		if(orderedQty > 0){
			
			var selRowIds = $packingListGrid.jqGrid("getGridParam", "selarrrow");
			if ($.inArray(jqGridRowId, selRowIds) >= 0) {
			    // the row having rowId is selected
			    console.log("Already selected");
			    //handleOnSelectRow(jqGridRowId, true);
			}else{
				$packingListGrid.jqGrid("setSelection", jqGridRowId);
				//handleOnSelectRow(jqGridRowId, true);
			}
				
		}else{
			$packingListGrid.jqGrid("setSelection", jqGridRowId);
		}
			
	}catch(e){
		console.log(e);
	}
	
	
	
}

function composeCombinationId(recordObj){
	var comboId = ""+ recordObj.portInwardId + "-"+recordObj.portInwardDetailId+"-"+recordObj.portInwardShipmentId;
}

function addRowOfSelectedRecord(recordObj) {
	var id = composeCombinationId(recordObj);
	var str = "<tr id='" + id + "'><td class='vessel-container'><input type='text' placeholder='Vessel Name' value='' onblur='fillDates(\"row-"+ id+ "\");' name='vesselName' class='form-control' /></td>"
			+ "<td>"+recordObj.vesselDate+"</td>"
			+ "<td>be no</td>"
			+ "<td>material type</td>"
			+ "<td>"+recordObj.grade+"</td>"
			+ "<td><input type='text' placeholder='Description' value='' name='desc' class='form-control' /></td>"
			+ "<td>"+recordObj.thickness+"</td>"
			+ "<td>"+recordObj.width+"</td>"
			+ "<td>"+recordObj.length+"</td>"
			+ "<td>"+recordObj.orderedQuantity+"</td>"
			+ "<td><div class='input-group'><input type='number' step='0.001' placeholder='Section Weight' min='0' readonly value='' name='secWt' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' name='secWtUnit' value='TON' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' disabled aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>TON</a></li><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>KG</a></li></ul></div></div></td>"
			// + "<td><div class='input-group'><input type='number' step='0.001' placeholder='Actual Weight' min='0' value='' name='actualWt' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' name='actualWtUnit' value='TON' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>TON</a></li><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>KG</a></li></ul></div></div></td>"
			+ "<td><input type='button' class='btn-danger delete-row' onclick='deleteRow($(this).parent().parent().attr(\"id\"));' value='-' /></td></tr>";
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



</script>
