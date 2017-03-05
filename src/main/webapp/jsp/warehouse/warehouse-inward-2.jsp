<%@page import="vkicl.vo.LocationDetailsVO"%>
<%@page import="java.util.List"%>
<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.form.PortOutwardForm"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
List<LocationDetailsVO> locationsList = (List<LocationDetailsVO> )request.getAttribute("locationsList");
pageContext.setAttribute("locationsList", locationsList);

Integer locationCount = locationsList.size();
%>

<script type="text/javascript">
	var LOCATIONS_COUNT = <%=locationCount%>;
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

	// addRow() function is not used. You may delete it.
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
				 //+ "<td><div class='input-group'><input type='number' step='0.001' placeholder='Actual Weight' min='0' value='' name='actualWt' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' name='actualWtUnit' value='TON' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>TON</a></li><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>KG</a></li></ul></div></div></td>"
				+ "<td><input type='button' class='btn-danger delete-row' onclick='deleteRow($(this).parent().parent().attr(\"id\"));' value='-' /></td></tr>";
		$("#details-tbody").append(str);
		id = id + 1;

		fillArray('vesselName', 'query.unique.vesselName');
		fillArray('grade', 'query.unique.grade');
		applyNumericConstraint();
		applyTotalCalc();
	}

	function deleteRow(id) {
		console.log(id);
		if ($("#details-tbody tr").length > 1){
			//Adjust the qty
			//Get the qty of the selected row
			var $qtyCell = $("#port_out_item_quantity-"+id);
			
			var $secWtCell = $("#port_out_section_wt-"+id);
			
			var splitRecordQty = Number($qtyCell.val());
			console.log("Qty to be added back = "+$qtyCell.val());
			
			var splitRecordSecWt = Number($secWtCell.val());
			console.log("Qty to be added back = "+$secWtCell.val());
			
			
			var parentPortOutRecordId = $qtyCell.attr("data-attribute-parent-port-out-id");

			var parentPortOutSecWtRecordId = $secWtCell.attr("data-attribute-parent-port-out-sec-id");
			
			console.log("Id of the parent qty cell = "+parentPortOutRecordId);
			
			var $parentPortOutRecordQtyCell = $("#"+parentPortOutRecordId);
			
			var $parentPortOutRecordSecWtCell = $("#"+parentPortOutSecWtRecordId);
			
			console.log($parentPortOutRecordQtyCell);
			
			var parentQty = Number($parentPortOutRecordQtyCell.val());
			var revertedQty = parentQty + splitRecordQty;
			$parentPortOutRecordQtyCell.val(revertedQty); 
			
			var parentSecWt = Number($parentPortOutRecordSecWtCell.val());
			var revertedSecWt = parentSecWt + splitRecordSecWt;
			$parentPortOutRecordSecWtCell.val(revertedSecWt); 
			
			
			
			$("#" + id).remove();
		}else{
			bootbox.alert("Cannot Delete Last Row");
		}
		
		applyTotalCalc();
	}

	function isValidLocations(){
		var isValid = true;
		
		$(".plateLocation").each(function(i,elem){
			var $elem = $(elem);
			var val = $elem.val();
			
			if(val == "-"){
				isValid = false;
				$elem.css({'background-color' : '#d89191'});
			}else{
				$elem.css({'background-color' : '#ffffff'});
			}
		});

		return isValid;
	}

	function isValidPlateCount(){
		var distinctGroupClassNames = new StringSet();
		$(".selected-port-outward-records").each(function(i,elem){
			var $elem = $(elem);
			var groupClassName = $elem.attr("data-attribute-group-class");
			//console.log(groupClassName);
			distinctGroupClassNames.add(groupClassName);
		});	

		
		for(var i=0; i<distinctGroupClassNames.values().length; i++){
			var uniqueGroupClass = distinctGroupClassNames.values()[i];
			console.log(uniqueGroupClass);
			var flag=isGroupPlateCountCorrect(uniqueGroupClass);
		}
		if(flag==true)
			{return true;}
		else
			return false;
		
	}

	function isGroupPlateCountCorrect(uniqueGroupClass){
		var originalQty = 0;
		var sum = 0;
		$("."+uniqueGroupClass).each(function(i,elem){
			var $elem = $(elem);
			originalQty = $elem.attr("data-attribute-original-quantity");
			var qty = $elem.find("[name=availableQuantity]").val();
			sum = sum + Number(qty);
		});

		if(sum != originalQty){
			bootbox.alert("Quantity checksum is wrong for "+uniqueGroupClass);
			var bgCss = $("."+uniqueGroupClass).css("background");
			$("."+uniqueGroupClass).animate({
			    color: "green",
			    backgroundColor: "rgb( 20, 20, 20 )"
			  }).animate({
				    color: "green",
				    backgroundColor: bgCss
			});
		}else{
			console.log("Quantity checksum is correctly matching.");
			return true;
		}
	}
	
	function isValidHeatNo(){
		var isValid = true;
		$(".port_out_heatno").each(function(i,elem){
			var $elem = $(elem);
			console.log($elem.attr("id"));
			var val = $elem.val();
			if(!val){
				isValid = false;
				$elem.css({'background-color' : '#d89191'});
			}else{
				$elem.css({'background-color' : '#ffffff'});
			}
		});
		return isValid;
	}

	function isValidPlateNo(){
		var isValid = true;
		$(".port_out_plateno").each(function(i,elem){
			var $elem = $(elem);
			console.log($elem.attr("id"));
			var val = $elem.val();
			if(!val){
				isValid = false;
				$elem.css({'background-color' : '#d89191'});
			}else{
				$elem.css({'background-color' : '#ffffff'});
			}
		});
		return isValid;
	}

	function isValidActualWeight(){
		var isValid = true;
		$(".warehouse_inward_actual_wt").each(function(i,elem){
			var $elem = $(elem);
			console.log($elem.attr("id"));
			var val = $elem.val();
			var isNumber = !isNaN(val);
			var isGreaterThan0 = false;
			if(isNumber){
				var actWtNumber = Number(val);
				if(actWtNumber > 0){
					isGreaterThan0 = true;
				}
			}
			if(!val || !isNumber || !isGreaterThan0){
				isValid = false;
				$elem.css({'background-color' : '#d89191'});
				$("#actualWeigthTotalInput").css({'background-color' : '#d89191'});
			}else{
				$elem.css({'background-color' : '#ffffff'});
				$("#actualWeigthTotalInput").css({'background-color' : '#ffffff'});
			}
		});
		return isValid;
	}
	
	function validateForm() {
		
		//updateHiddenField();

		$("#details-tbody tr").each(function(i, radio) {
			$(radio).find('td').each(function() {

			});
		});

		var isAllHeatNoValid = isValidHeatNo();
		var isAllPlateNoValid = isValidPlateNo();
		var isAllActualWeightValid = isValidActualWeight();
		var isAllLocValid = isValidLocations();
		var isPlateCountCorrectEvenAfterSplit = isValidPlateCount();
		

		console.log(isAllHeatNoValid);
		console.log(isAllPlateNoValid);
		console.log(isAllActualWeightValid);
		console.log(isAllLocValid);
		console.log(isPlateCountCorrectEvenAfterSplit);

		
		var isAllValid = isAllHeatNoValid && isAllPlateNoValid && isAllActualWeightValid && isAllLocValid && isPlateCountCorrectEvenAfterSplit;
		
		console.log(isAllValid);
		if (isAllValid == true) {
			var str = "Are you sure you want to Submit?";
			bootbox.confirm(str, function(result) {
				if (result) {
					//submitCachedWarehouseInwardRecords();
					submitWarehouseInwards();
				}
			});
		}else{
			bootbox.alert("Please fill in all mandatory fields.");
		}
		return false;
	}

	function addHeatPlateAndLocation() {
		for (var i = 0; i < SELECTED_PORT_INVENTORY_ITEMS.length; i++) {
			var item = SELECTED_PORT_INVENTORY_ITEMS[i];
			var heatNo = getHeatNo(item);
			var plateNo = getPlateNo(item);
			var location = getSelectedLocation(item);

			console.log(heatNo + "-" + plateNo + "-" + location);
			item.heatNo = heatNo;
			item.plateNo = plateNo;
			item.location = location;
		}
	}

	function getHeatNo(item) {
		var elemId = heatId(item);
		var heatNo = $("#" + elemId).val();
		return heatNo;
	}

	function getPlateNo(item) {
		var elemId = plateId(item);
		var heatNo = $("#" + elemId).val();
		return heatNo;
	}

	function getSelectedLocation(item) {
		var elemId = locationId(item);
		var location = $("#" + elemId).val();
		return location;
	}

	function submitWarehouseInwards() {

		var selected_port_inventory_items_JSON = composeSelectedWarehouseInwardsJson();
		console.log(selected_port_inventory_items_JSON);
		var postJsonObject = {
			selectedPortInventoryItemsJson : selected_port_inventory_items_JSON
		};

		var itemsToSaveWarehouseInwardJson = "genericListener=add&itemsToSaveWarehouseInwardJson="
				+ JSON.stringify(postJsonObject);
		
		console.log(itemsToSaveWarehouseInwardJson);
		$.ajax({
					url : "warehouse-inward-save.do",
					method : 'POST',
					data : itemsToSaveWarehouseInwardJson,
					success : function(msg) {
						console.log(msg);
						bootbox.alert("Successfully saved records!",
								function() {
									location.reload();
								});

					},
					error : function(msg) {
						console.log(msg);
						bootbox
								.alert("Some error at server! Please call administrator.");
					}
				});

	}

	function composeSelectedWarehouseInwardsJson() {
		var warehouseInwardsObjectArray = [];
		$(".selected-port-outward-records")
				.each(
						function(i, elem) {
							var $elem = $(elem);
							var selectedWarehouseInwardsObj = composeWarehouseInwardsObject($elem);
							warehouseInwardsObjectArray
									.push(selectedWarehouseInwardsObj);
						});
		var json = JSON.stringify(warehouseInwardsObjectArray);
		return json;
	}

	function composeWarehouseInwardsObject($elem) {
		var vesselDate = getValueByName($elem, "vesselDate");
		var portInwardId = getValueByName($elem, "portInwardId");
		var portInwardDetailId = getValueByName($elem, "portInwardDetailId");
		var portInwardShipmentId = getValueByName($elem, "portInwardShipmentId");
		var portOutwardId = getValueByName($elem, "portOutwardId");

		var length = getValueByName($elem, "length");
		var width = getValueByName($elem, "width");
		var thickness = getValueByName($elem, "thickness");
		var vesselName = getValueByName($elem, "vesselName");
		var millName = getValueByName($elem, "millName");
		var availableQuantity = getValueByName($elem, "availableQuantity");
		var grade = getValueByName($elem, "grade");
		var materialType = getValueByName($elem, "Type");
		var balQty = getValueByName($elem, "balQty");
		var outQty = getValueByName($elem, "outQty");
		var vehicleDate = getValueByName($elem, "vehicleDate");
		var vehicleName = getValueByName($elem, "vehicleName");
		var actualWt = getValueByName($elem, "actualWt");
		var vendorName = getValueByName($elem, "vendorName");
		var make = getValueByName($elem, "make");

		var heatNo = getValueByName($elem, "heatNoInput");
		var plateNo = getValueByName($elem, "plateNoInput");
		var location = getValueByName($elem, "location");

		var warehouseInwardsObject = {
			portInwardId : portInwardId,
			portOutwardId : portOutwardId,
			portInwardDetailId : portInwardDetailId,
			portInwardShipmentId : portInwardShipmentId,
			length : length,
			width : width,
			thickness : thickness,
			vesselDate : vesselDate,
			vesselName : vesselName,
			millName : millName,
			availableQuantity : availableQuantity,
			grade : grade,
			materialType : materialType,
			balQty : balQty,
			outQty : outQty,
			vehicleDate : vehicleDate,
			vehicleName : vehicleName,
			actualWt : actualWt,
			vendorName : vendorName,
			make : make,
			heatNo : heatNo,
			plateNo : plateNo,
			location : location

		};
		console.log(warehouseInwardsObject);
		return warehouseInwardsObject;
	}

	function getValueByName($elem, elementName) {
		return $elem.find("[name=" + elementName + "]").val();
	}

	function submitCachedWarehouseInwardRecords() {

		addHeatPlateAndLocation();

		var selected_port_inventory_items_JSON = JSON
				.stringify(SELECTED_PORT_INVENTORY_ITEMS);
		var postJsonObject = {
			selectedPortInventoryItemsJson : selected_port_inventory_items_JSON
		};

		var itemsToSaveWarehouseInwardJson = "genericListener=add&itemsToSaveWarehouseInwardJson="
				+ JSON.stringify(postJsonObject);
		;
		console.log(itemsToSaveWarehouseInwardJson);
		$
				.ajax({
					url : "warehouse-inward-save.do",
					method : 'POST',
					data : itemsToSaveWarehouseInwardJson,
					success : function(msg) {
						console.log(msg);
						bootbox.alert("Successfully saved records!",
								function() {
									location.reload();
								});

					},
					error : function(msg) {
						console.log(msg);
						bootbox
								.alert("Some error at server! Please call administrator.");
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
	<html:form action="/warehouse-inward-2" >
		<div class="row">
			<div class="col-md-12">
				<h3 class="page-head">Warehouse Inward</h3>
			</div>
		</div>
		<div class="row">
		
			<span id="report-toolbar" class="pull-right"><span
			class="total-field"></span>Export:<img src="./img/excel.png" id="exportToExcel" title="Export to Excel"
			onClick="prepareTableToExcelAndExport()" /><img
			src="./img/pdf.png" id="exportToPDF" title="Export to PDF"
			style="display: none;" /> </span>
				
				<div class="col-md-12" >
					<div>
						<div id="packingListTable">
							<table id="packingListGrid"></table>
							<div id="packingListPager"></div>
						</div>
					</div>
				</div>
			

		</div>
		<div class="row">
			<div class="col-xs-12">
				<h3>Review the selected Warehouse Inward Entries below</h3>
				<table class="table table-responsive table-form" id="portOutwardRecordsTable">
					<thead>
						<tr>
						
						<th>Date</th>
						<th>Vendor Name</th>
							<th>Vessel Name</th>
							<th>Vehicle Date</th>
							<th>Vehicle No.</th>
							<th>Mill Name</th>
							
							<th>Type</th>
							<th>Make</th>
							<!-- <th>B/E No.</th> -->
							<!-- <th>Material Type</th> -->
							<th>Grade</th>
							<!--<th>Description</th>-->
							<th>Thickness</th>
							<th>Width</th>
							<th>Length</th>
							<th>No. of Pcs</th>
							<th>Sec. wt</th>
							<th>Actual Wt.</th>
							<th>Heat No</th>
							<th>Plate No</th>
							<th>Location</th> 
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
					<input type="button" value="Edit" onclick="editText();"
					class="btn pull-right" />
				<button class="btn pull-right" onclick="return validateForm()">Submit</button>
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
			url : './packingListJsonServlet2',
			datatype : 'json',
			
			mtype : 'POST',


			colNames : [ 'portOutwardId','portInwardId','portInwardShipmentId','Date','Vendor Name','Vessel Name','Vehicle Date', 'Vehicle Number', 'Mill Name', 'Type', 'Make','Grade', 'Thickness', 'Width', 'Length', 'No. of Pcs', 'Sec. wt', 'Actual wt.','Actual wt unit' ],

					
			colModel : [  {
				name : 'portOutwardId',
				index : 'portOutwardId',
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
			},{
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
			},{
				name : 'portInwardShipmentId',
				index : 'portInwardShipmentId',
				hidden: true,
				width : 155,
				editable : false,
				editrules : {
					required : true
				},
				editoptions : {
					size : 10
				},
				search:false
				
			}, {
				name : 'vesselDate',
				index : 'vessel_Date',
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
				name : 'vendorName',
				index : 'vendor_name',
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
				align : 'center',
				sortable:true,
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			}, {
				name : 'vehicleDate',
				index : 'vehicle_date',
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
				name : 'vehicleName',
				index : 'vehicle_number',
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
				searchoptions: { sopt:[ 'cn','eq']}
				
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
				name : 'actualWt',
				index : 'actual_wt',
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
				name : 'actualWt_unit',
				index : 'actualWt_unit',
				hidden: true,
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
			sortname : 'port_out_id',
			viewrecords : true,
			sortorder : "desc",
			
			caption : "Inventory arriving at Warehouse",
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
	        		
	        		//Pre-select the plates if user had them selected already
					if(SELECTED_PORT_INVENTORY_ITEMS.length > 0){
						
						var cachedObj = isObjectPresentInCache(composedObj);
						if(cachedObj){
							console.log("cachedObj.orderedQuantity = "+cachedObj.orderedQuantity);
							$grid.jqGrid("setSelection", ids[i]);
							
							/* $("#ordered_qty_"+ids[i]).val(cachedObj.orderedQuantity);
							var val = calculateOutQty(rowId, cachedObj.orderedQuantity);
							$("#packingListGrid").jqGrid("setCell", rowId, "outQty", val); */
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

function areAllPlatesOfGivenPortOutwardRecordPresentInCache(targetObj){
	var areAllPlatesPresentInCache = false;
	
	if(SELECTED_PORT_INVENTORY_ITEMS.length > 0){
		var noOfPlatesInCache = 0;
		for(var i=0;i<SELECTED_PORT_INVENTORY_ITEMS.length;i++){
			var itemObj = SELECTED_PORT_INVENTORY_ITEMS[i];
			var isSame = compareCachedObjects(itemObj, targetObj);
			var isSequenceSame = itemObj.sequence == targetObj.sequence;
			if(isSame && isSequenceSame){
				noOfPlatesInCache = noOfPlatesInCache + itemObj.orderedQuantity;
			}
		}

		if(noOfPlatesInCache >= Number(targetObj.availableQuantity) ){
			areAllPlatesPresentInCache = true;
		}
	}

	console.log("areAllPlatesPresentInCache = "+areAllPlatesPresentInCache);
	return areAllPlatesPresentInCache;
}

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

function handleOnSelectRow(rowId, status){
	
	var row = jQuery("#packingListGrid").jqGrid('getRowData',rowId); 
	console.log("port outward quantity of plates = "+row.quantity);
	var platesQuantity = Number(row.quantity);
	console.log("platesQuantity = "+platesQuantity);
	for(var i=0;i<platesQuantity;i++){
		console.log("----------------- "+i);
		var orderedQty = 1;//$("#ordered_qty_"+rowId).val();
	
		var objectForCaching = composeObjectForCaching(row, orderedQty);

		//Add this sequence number to add to the uniqueness of the HTML elements 
		objectForCaching.sequence = i;
	
		//If status = true, that means item selected. False means item deselected.
		if(status){
			//If already present, update the orderedQuantity in cache
			
			//var isPresentObj = isObjectPresentInCache(objectForCaching);
			var areAllPlatesInCache = areAllPlatesOfGivenPortOutwardRecordPresentInCache(objectForCaching);
			console.log("isPresentObj = "+areAllPlatesInCache);
			if(areAllPlatesInCache){
				console.log("All plates present in cache.");
				//updateOrderedQuantityInCache(objectForCaching);
				//$("#ordered_qty_"+rowId).val(isPresentObj.orderedQuantity);
				//var val = calculateOutQty(rowId, isPresentObj.orderedQuantity);
				//$("#packingListGrid").jqGrid("setCell", rowId, "outQty", val);
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
				
				//Push items into cache as selected.
				SELECTED_PORT_INVENTORY_ITEMS.push(objectForCaching);
				
				if($("#ordered_qty_"+rowId).val()==""){
					$("#ordered_qty_"+rowId).val("1");
					var val = calculateOutQty(rowId, objectForCaching.orderedQuantity);
					$("#packingListGrid").jqGrid("setCell", rowId, "outQty", val);
				}
			}
			
		}else{
			
			removeItemFromCache(objectForCaching);
			$("#ordered_qty_"+rowId).val("");
			$("#packingListGrid").jqGrid("setCell", rowId, "outQty", " ");
		}
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
		var q = Number($(elem).val());
		quantitySum = quantitySum + q;
	});
	
	$(".port_out_section_wt").each(function (index, elem){
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
	var flag4 = one.portOutwardId == two.portOutwardId;
	//var flag4 = one.orderedQuantity == two.orderedQuantity;
	
	var isSame = flag1 && flag2 && flag3 && flag4;// && flag4;
	
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
	if(objectFromCache){
		objectFromCache.orderedQuantity = orderedQty;
	}
	
	
}

function editText() {
	
	$("input[name='vesselDate']").removeAttr("readonly");
	$("input[name='vesselName']").removeAttr("readonly");
	$("input[name='vehicleDate']").removeAttr("readonly");
	$("input[name='vehicleName']").removeAttr("readonly");
	$("input[name='millName']").removeAttr("readonly");
	$("input[name='Type']").removeAttr("readonly");
	$("input[name='grade']").removeAttr("readonly");
	$("input[name='thickness']").removeAttr("readonly");
	$("input[name='width']").removeAttr("readonly");
	$("input[name='length']").removeAttr("readonly");
	$("input[name='availableQuantity']").removeAttr("readonly");
	$("input[name='balQty']").removeAttr("readonly");
	$("input[name='actualWt']").removeAttr("readonly");
	$("input[name='make']").removeAttr("readonly");
	$("input[name='vendorName']").removeAttr("readonly");
}


function composeCombinationId(recordObj){
	//var comboId = ""+ recordObj.portInwardId + "-"+recordObj.portInwardDetailId+"-"+recordObj.portInwardShipmentId;
	
	var comboId = ""+ recordObj.portInwardId + "-"+recordObj.portInwardShipmentId+"-"+recordObj.portOutwardId+"-"+recordObj.sequence+"-"+recordObj.thickness ;
	return comboId;
}

function composeCombinationGroupId(recordObj){
	//var comboId = ""+ recordObj.portInwardId + "-"+recordObj.portInwardDetailId+"-"+recordObj.portInwardShipmentId;
	
	var comboId = ""+ recordObj.portInwardId + "-"+recordObj.portInwardShipmentId+"-"+recordObj.portOutwardId+"-"+recordObj.thickness ;
	return comboId;
}

function composeCombinationClass(id){
	//var comboId = ""+ recordObj.portInwardId + "-"+recordObj.portInwardDetailId+"-"+recordObj.portInwardShipmentId;
	var comboId = "portoutward-group-"+ id;
	return comboId;
}

function awId(recordObj){
	var trElementId = composeCombinationId(recordObj);
	var actualWeightCellId = "actualWeight-"+trElementId;
	return actualWeightCellId;
}

function heatId(recordObj){
	var trElementId = composeCombinationId(recordObj);
	var heatCellId = "heat-"+trElementId;
	return heatCellId;
}

function plateId(recordObj){
	var trElementId = composeCombinationId(recordObj);
	var plateCellId = "plate-"+trElementId;
	return plateCellId;
}

function locationId(recordObj){
	var trElementId = composeCombinationId(recordObj);
	var locationCellId = "location-"+trElementId;
	return locationCellId;
}

function composeJsonCellId(parentTrId){
	var jsonCellId = "jsonstr-"+parentTrId;
	return jsonCellId;
}


function getLocationDropdownHtml(recordObj){
	//var html = "<td><input type='text'          placeholder='Location' name='locationInput' class='form-control port_out_section_wt' /></td>";
	var locationIdStr = locationId(recordObj);
	var html = $("#locationDropdownTemplate").html();
	html = html.replace(/selectLocationId/, locationIdStr);
	html = html.replace(/selectLocationClass/, "plateLocation");
	//console.log(html);
	html = "<td>"+html+"</td>";
	return html;
}

function addRowOfSelectedRecord(recordObj) {
	
	
	var id = composeCombinationId(recordObj);
	var combinationGroupId = composeCombinationGroupId(recordObj);

	var jsonCellId = composeJsonCellId(id);
	var warehouseInwardRecordClass = composeCombinationClass(combinationGroupId);
	var recordObjJson = JSON.stringify(recordObj);		
	var str = "<tr id='" + id + "' class='selected-port-outward-records "+warehouseInwardRecordClass+"' data-attribute-group-class='"+warehouseInwardRecordClass+"' data-attribute-original-quantity='"+recordObj.availableQuantity+"' >"
			+ "<td><input type='text' readonly placeholder='vesselDate' value='"+recordObj.vesselDate+"' name='vesselDate' class='form-control'  /></td>"
			+ "<td><input type='text' readonly placeholder='vendorName' value='"+recordObj.vendorName+"' name='vendorName' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='vesselName' value='"+recordObj.vesselName+"' name='vesselName' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='vehicleDate' value='"+recordObj.vehicleDate+"' name='vehicleDate' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='vehicleName' value='"+recordObj.vehicleName+"' name='vehicleName' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='millName' value='"+recordObj.millName+"' name='millName' class='form-control' /></td>"
			
			+ "<td><input type='text' readonly placeholder='materialType' value='"+recordObj.materialType+"' name='Type' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='make' value='"+recordObj.make+"' name='make' class='form-control' /></td>"
			//+ "<td>be no</td>"
			//+ "<td>Material Type</td>"
			+ "<td><input type='text' readonly placeholder='grade' value='"+recordObj.grade+"' name='grade' class='form-control' /></td>"
			// + "<td><input type='text' placeholder='Description' value='' name='desc' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='thickness' value='"+recordObj.thickness+"' name='thickness' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='width' value='"+recordObj.width+"' name='width' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='length' value='"+recordObj.length+"' name='length' class='form-control' /></td>"
			+ "<td><input type='text' readonly placeholder='Quantity' value='"+recordObj.orderedQuantity+"' name='availableQuantity' class='form-control port_out_item_quantity' id='port_out_item_quantity-" + id + "' data-attribute-parent-port-out-id='port_out_item_quantity-" + id + "' onchange='splitOnChange($(this).parent().parent().attr(\"id\"));'/></td>"
			+ "<td><input type='text' readonly placeholder='balQty' value='"+recordObj.balQty+"' name='balQty' class='form-control port_out_section_wt' id='port_out_section_wt-" + id + "' data-attribute-parent-port-out-sec-id='port_out_section_wt-" + id + "' /></td>"

			+ "<td><input type='text' readonly placeholder='Actual Wt.' value='"+recordObj.actualWt+"' name='actualWt' class='form-control warehouse_inward_actual_wt' id='"+awId(recordObj)+"' data-attribute-parent-port-out-id='actualWt-" + id + "'/></td>"
			+ "<td><input type='text'          placeholder='Heat No' name='heatNoInput' class='form-control port_out_heatno' id='"+heatId(recordObj)+"' data-attribute-parent-port-out-id-actualwt='heatno-" + heatId(recordObj) + "'/></td>"
			+ "<td><input type='text'          placeholder='Plate No' name='plateNoInput' class='form-control port_out_plateno' id='"+plateId(recordObj)+"'/></td>"
			+ getLocationDropdownHtml(recordObj)

			//+ "<td><div class='input-group'><input type='number' step='0.001' placeholder='Section Weight' min='0' readonly value='' name='secWt' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' name='secWtUnit' value='TON' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' disabled aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>TON</a></li><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>KG</a></li></ul></div></div></td>"
			// + "<td><div class='input-group'><input type='number' step='0.001' placeholder='Actual Weight' min='0' value='' name='actualWt' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' name='actualWtUnit' value='TON' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>TON</a></li><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>KG</a></li></ul></div></div></td>"
			+ "<td id='split-button-td-" + id + "' ><input type='button' class='btn btn-warn' onclick='split2($(this).parent().parent().attr(\"id\"));' value='split' /></td>"
			+ "<td><input type='button' class='btn-danger delete-row' onclick='deleteRow($(this).parent().parent().attr(\"id\"));' value='-' /></td>"
			+ "<td><input type='hidden' value='"+recordObj.portInwardId+"' name='portInwardId'/></td>"
			+ "<td><input type='hidden' value='"+recordObj.portOutwardId+"' name='portOutwardId'/></td>"
			+ "<td><input type='hidden' value='"+recordObj.portInwardShipmentId+"' name='portInwardShipmentId'/></td>"
			+ "<td><input type='hidden' value='"+recordObjJson+"' id='"+jsonCellId+"'/></td>"

			+ "</tr>";
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
			+ "<td></td>"
			+ "<td></td>"
			+ "<td></td>"
			+ "<td></td>"
			+ "<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Checksum </td>"
			+ "<td class='port_out_item_quantity' >&nbsp&nbsp"+quantitySum+"</td>"
			+ "<td class='port_out_section_wt'>&nbsp&nbsp"+sectionwtSum.toFixed(3)+"</td>"
			// + "<td><div class='input-group'><input type='number' step='0.001' placeholder='Actual Weight' min='0' value='' name='actualWt' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' name='actualWtUnit' value='TON' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>TON</a></li><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>KG</a></li></ul></div></div></td>"
			//+ "<td><input type='button' class='btn-danger delete-row' onclick='deleteRow($(this).parent().parent().attr(\"id\"));' value='-' /></td></tr>";
			+ "<td><input type='text'  placeholder='Actual Weight Total (TON)' name='actualWeigthTotalInput' id='actualWeigthTotalInput' class='form-control' onchange='distributeActualWeightTotal()'/></td>"
			+ "<td></td>";
	$("#details-tbody").append(str);
	

	//applyNumericConstraint();
	//applyTotalCalc();
}


function split2(idOfRowToSplit){

	var recordsArrayOfGivenPortOutwardGroup = getPortOutwardGroupForId(idOfRowToSplit);
	
	var subRowsCount = recordsArrayOfGivenPortOutwardGroup.length;
	console.log("Id Of the row to be split = "+idOfRowToSplit);
	console.log("Available locations = "+LOCATIONS_COUNT);
	console.log("Sub Rows Count = "+subRowsCount);
	if(subRowsCount < LOCATIONS_COUNT){
		//If splitted already into LOCATIONS_COUNT rows, then dont split further
		console.log("splitting the quantity into more than one locations.-"+idOfRowToSplit);
		var $trToSplit = $("#"+idOfRowToSplit);
		
		var $portOutItemQty = $trToSplit.find("#port_out_item_quantity-"+idOfRowToSplit);
		var qty = Number($portOutItemQty.val());
		
		var $grid = $("#packingListGrid");
		
		var selectedRowId = $grid.jqGrid ('getGridParam', 'selrow');
		var celSecWtValue = $grid.jqGrid('getCell', selectedRowId, 'balQty');
		var celQtyValue = $grid.jqGrid('getCell', selectedRowId, 'quantity');
		
		
		if(qty >1){
			//If quantity is more than 1, then only let it split.
			$portOutItemQty.val(qty - 1);

			var newSecWt= (( (qty-1) * celSecWtValue )/celQtyValue);

			$trToSplit.find("#port_out_section_wt-"+idOfRowToSplit).val(newSecWt);
			
			//Change the ID of the cloned row
			var $klonedTr = $trToSplit.clone();
			$klonedTr.attr("id",idOfRowToSplit+"-"+subRowsCount); 

			//Change the id of the cloned quantity cell.
			var $quantityCell = $klonedTr.find("#port_out_item_quantity-"+idOfRowToSplit)
			$quantityCell.val(1);
			$quantityCell.attr("id","port_out_item_quantity-"+idOfRowToSplit+"-"+subRowsCount);
			$klonedTr.find("#split-button-td-"+idOfRowToSplit).html("");
			
			var $actualWtCell = $klonedTr.find("#actualWeight-"+idOfRowToSplit);
			$actualWtCell.attr("id","actualWeight-"+idOfRowToSplit+"-"+subRowsCount);
			//$klonedTr.after($trToSplit);
			$("#"+idOfRowToSplit).after($klonedTr);
			
		
			var newCloneSecWt= (( (1) * celSecWtValue )/celQtyValue);
			
			var $secWtCell = $klonedTr.find("#port_out_section_wt-"+idOfRowToSplit)
			
			$secWtCell.attr("id","port_out_section_wt-"+idOfRowToSplit+"-"+subRowsCount);
			$secWtCell.val(newCloneSecWt);
			//$trToSplit.find("#port_out_section_wt-"+idOfRowToSplit).val(newCloneSecWt);
			
		}else{
			bootbox.alert("Hey smarty, can't split further. How can you split 1 plate into multiple locations? Go learn some basics.");
		}

	
	}else{
		bootbox.alert("You have already split into all "+LOCATIONS_COUNT+" locations. There are no more locations to split.");
	}
}



function splitOnChange(idOfRowToSplit){

	var recordsArrayOfGivenPortOutwardGroup = getPortOutwardGroupForId(idOfRowToSplit);
	
	var subRowsCount = recordsArrayOfGivenPortOutwardGroup.length;
	console.log("Id Of the row to be split = "+idOfRowToSplit);
	console.log("Available locations = "+LOCATIONS_COUNT);
	console.log("Sub Rows Count = "+subRowsCount);
	//if(subRowsCount < LOCATIONS_COUNT){
		//If splitted already into LOCATIONS_COUNT rows, then dont split further
		console.log("splitting the quantity into more than one locations.-"+idOfRowToSplit);
		var $trToSplit = $("#"+idOfRowToSplit);
		
		var $portOutItemQty = $trToSplit.find("#port_out_item_quantity-"+idOfRowToSplit);
		var qty = Number($portOutItemQty.val());
		
		var $grid = $("#packingListGrid");
		
		var selectedRowId = $grid.jqGrid ('getGridParam', 'selrow');
		var celSecWtValue = $grid.jqGrid('getCell', selectedRowId, 'balQty');
		var celQtyValue = $grid.jqGrid('getCell', selectedRowId, 'quantity');
		
		var newSecWt= (( (qty) * celSecWtValue )/celQtyValue);

		$trToSplit.find("#port_out_section_wt-"+idOfRowToSplit).val(newSecWt);
	
}


function getPortOutwardGroupForId(idOfRowToSplit){
	var portOutwardGroupClassName = composeCombinationClass(idOfRowToSplit);
	var group = $("."+portOutwardGroupClassName);
	console.log(group);
	return group;
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

function distributeActualWeightTotal(){
	var actualWeightTotal = $("#actualWeigthTotalInput").val();
	if(isValidNumber(actualWeightTotal)){
		var actWtTotalNumber = Number(actualWeightTotal);
		console.log(actWtTotalNumber);
		//Actual Wt for row 1 = ((Sec Wt of row 1/Total sec Wt) * (Total Actual Wt – Total Sec Wt.)) + Sec Wt of row 1
		console.log(SELECTED_PORT_INVENTORY_ITEMS.length);
		var secWtTotal = getTotalSectionWeightOfSelectedRecords();


	
		for(var i=0;i<SELECTED_PORT_INVENTORY_ITEMS.length;i++){
		
			var item = SELECTED_PORT_INVENTORY_ITEMS[i];
			var id = composeCombinationId(item);
			
			var recordsArrayOfGivenPortOutwardGroup = getPortOutwardGroupForId(id);
			
			var subRowsCount = recordsArrayOfGivenPortOutwardGroup.length;
			
			
			var $trToSplit = $("#"+id);
			
			var $portSecWt = $trToSplit.find("#port_out_section_wt-"+id);
			var secWt = Number($portSecWt.val());
			
			
			//var secWt = Number(item.balQty);
			var actualWeightElementId = awId(item);

			var actWt = ((secWt / secWtTotal) * (actWtTotalNumber - secWtTotal)) + secWt;
			console.log(actWt);
			actWt = dp2(actWt);
			$("#"+actualWeightElementId).val(actWt);
			
			for(var j=1; j<=(subRowsCount-1);j++)
			{
			 $trToSplit = $("#"+id+"-"+j);
			
			 $portSecWt = $trToSplit.find("#port_out_section_wt-"+id+"-"+j);
			 secWt = Number($portSecWt.val());
			 
			 var actualWeightElementId = awId(item);
				var actWt = ((secWt / secWtTotal) * (actWtTotalNumber - secWtTotal)) + secWt;
				console.log(actWt);
				actWt = dp2(actWt);
				$("#"+actualWeightElementId+"-"+j).val(actWt);
			}
			
			
		}
		
		
	}else{
		bootbox.alert("Please input valid actual weight total.");
	}
}

function dp2(num){
	
	return Math.round(Number(num) * 100) / 100
}

function getTotalSectionWeightOfSelectedRecords(){
	var secWtTotal = 0;
	for(var i=0;i<SELECTED_PORT_INVENTORY_ITEMS.length;i++){
		var item = SELECTED_PORT_INVENTORY_ITEMS[i];
		secWtTotal = Number(item.balQty) + secWtTotal;
	}
	
	return secWtTotal;
}


function isValidNumber(str) {
   // var pattern = /^\d+$/;
   var pattern= /^\d+\.?\d*$/;
    return pattern.test(str);  // returns a boolean
}

</script>
<div style="display: none;">
	<table id="forExportingToExcel">
		<thead>
			<tr>
				<td>Date</td>
				<td>Vendor Name</td>
				<td>Vessel Name</td>
				<td>Vehicle Date</td>
				<td>Vehicle Number</td>
				<td>Mill Name</td>
				<td>Type</td>
				<td>Make</td>
				<td>Grade</td>
				<td>Thickness</td>
				<td>Width</td>
				<td>Length</td>
				<td>Bal Pcs</td>
				<td>Sec wt.</td>
				<td>Actual wt.</td>
				
			</tr>
		</thead>
		<tbody id="forExportingToExcelBody">
		</tbody>
	</table>
</div>

<div id="locationDropdownTemplate" style="visibility: hidden;" >
	<select name="location" class="selectLocationClass" id="selectLocationId">
		<option value="-">Select Location</option>
	<c:forEach items="${locationsList}" var="locationVo">
		<option value='<c:out value="${locationVo.locationName}"></c:out>'><c:out value="${locationVo.locationName}"></c:out></option>
	</c:forEach>
	</select>
</div>

<script>
function prepareTableToExcelAndExport(){
	var template = "<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td><td>{5}</td><td>{6}</td><td>{7}</td><td>{8}</td><td>{9}</td><td>{10}</td><td>{11}</td><td>{12}</td><td>{13}</td><td>{14}</td></tr>";

	$.ajax({ 
                type: 'POST', 
                url: './packingListJsonServlet2', 
                data: { rows: $("#packingListGrid").jqGrid("getGridParam", "records"),page:parseInt(($("#packingListGrid").getGridParam('records') / $("#packingListGrid").getGridParam('rowNum')) + 1), orderBy:'port_inward_detail_id', order:'desc'}, 
                dataType:'json',
                success:  function(response){
    	        	 var rowObject = response.rows;
    	        	for(var i=0;i < rowObject.length;i++){ 
    	        		
    	        		//var composedObj = composeObjectForCaching(rowObject, 0);
    	        		var tr = $.validator.format(template,rowObject[i].vesselDate,rowObject[i].vendorName,rowObject[i].vesselName,rowObject[i].vehicleDate,rowObject[i].vehicleName,rowObject[i].millName
    	        				,rowObject[i].materialType,rowObject[i].make,rowObject[i].grade,rowObject[i].thickness,rowObject[i].width,rowObject[i].length,rowObject[i].quantity,rowObject[i].balQty,rowObject[i].actualWt);
    	        		$("#forExportingToExcelBody").append(tr);
    	        	}

    	        	tableToExcel('forExportingToExcel', 'Excel');
    	        	$("#forExportingToExcelBody").empty();
                }
                
            });
	
}
</script>


