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

<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Warehouse Inward</h3>
	</div>
</div>
<div>
	<html:form enctype="multipart/form-data" action="/warehouse-inward"
		method="post">
		<div class="row">
			<div class="col-md-4">
				<table class="table table-responsive" id="port-table">
					<tr class="head-row">
						<th colspan="2">From Port</th>
					</tr>
					<tr>
						<td class="form-label"><label for="portVehicleNumber">Vehicle
								Number</label></td>
						<td><input type="text" name="portVehicleNumber"
							placeholder="Vehicle Number" onblur="fillPortVehicleDates();"
							class="form-control" /></td>
					</tr>
					<tr>
						<td class="form-label"><label for="portVehicleDate">Vehicle
								Date</label></td>
						<td><select class="form-control" name="portVehicleDate">
								<option value="">--</option>
						</select></td>
					</tr>
					<tr>
						<td colspan="2"><input type='button' id="fetch-details"
							class="btn btn-default pull-right" value="Search"
							onclick="fetchWarehouseInwardDetails();" /></td>
					</tr>
				</table>
			</div>
			<div class="col-md-4">
				<table class="table table-responsive">
					<tr>
						<td class="form-label"><label for="portVendor">From</label></td>
						<td><input type="radio" value="port" name="portVendor"
							onclick="vendorCheck();" checked="checked" /><label class="link"
							onclick="$(this).prev().click();vendorCheck();">Port</label> <input
							type="radio" value="vendor" name="portVendor"
							onclick="vendorCheck();" /><label class="link"
							onclick="$(this).prev().click();vendorCheck();">Vendor</label></td>
					</tr>
					<tr>
						<td class="form-label"><label for="actualWt">Actual
								Weight</label></td>
						<td>
							<div class="input-group">
								<input type="number" step="0.001" placeholder="Actual Weight"
									min="0" value="" name="actWt" class="form-control"
									aria-label="...">
								<div class="input-group-btn weight-group">
									<input type="hidden" name="actWtUnit" value="TON" />
									<button type="button" class="btn btn-default dropdown-toggle"
										data-toggle="dropdown" aria-expanded="false">
										TON <span class="caret"></span>
									</button>
									<ul class="dropdown-menu dropdown-menu-right" role="menu">
										<li onclick="rowBtnGroupChange(this);"><a>TON</a></li>
										<li onclick="rowBtnGroupChange(this);"><a>KG</a></li>
									</ul>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div class="col-md-4">
				<table class="table table-responsive" id="vendor-table">
					<tr class="head-row">
						<th colspan="2">From Vendor</th>
					</tr>
					<tr>
						<td class="form-label"><label for="vendorName">Vendor
								Name</label></td>
						<td><input type="text" name="vendorName"
							class="form-control" placeholder="Vendor Name" /></td>
					</tr>
					<tr>
						<td class="form-label"><label for="vendorVehicleNumber">Vehicle
								Number</label></td>
						<td><input type="text" name="vendorVehicleNumber"
							class="form-control" placeholder="Vehicle Number" /></td>
					</tr>
					<tr>
						<td class="form-label"><label for="vendorVehicleDate">Vehicle
								Date</label></td>
						<td><div class="input-group date date-picker-div">
								<input type="text" name="vendorVehicleDate" class="form-control" />
								<span class="input-group-addon"><span
									class="glyphicon-calendar glyphicon"></span></span>
							</div></td>
					</tr>
				</table>
			</div>
		</div>

		<div class="row">
			<div class="col-md-12">
				<table class="table table-responsive table-form">
					<thead>
						<tr>
							<!--  <th>B/E No.</th> -->
							<th>Material Type</th>
							<th>Mill Name</th>
							<th>Make</th>
							<th>Grade</th>
							<th>Thickness</th>
							<th>Width</th>
							<th>Length</th>
							<th>Quantity</th>
							<th>Section Weight</th>
							<th>Label Weight</th>
							<th><input type="button" class="btn-success add-row"
								onClick="addRow();" value="+" /></th>
						</tr>
					</thead>
					<tbody id="details-tbody">
					</tbody>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<input type="button" value="Reset" onclick="resetForm();"
					class="btn pull-left"> <input type="button"
					class="btn pull-right" onclick="return validateForm();"
					value="Submit">
			</div>
		</div>
		<html:hidden property="genericListener" value="add" />
	</html:form>
</div>

<script type="text/javascript">

	var id = 1, row = {}, row_id = 1, current_row_id= "";

	function fetchWarehouseInwardDetails() {
		
		if($("[name='portVendor']:checked").val() == "port") {
			var portVehicleNumber = $("[name='portVehicleNumber']").val();
			var portVehicleDate = $("[name='portVehicleDate']").val();
	
			if ("" == portVehicleNumber) {
				bootbox.alert("Please select Vehicle Name");
				$("[name='portVehicleNumber']").focus();
				return false;
			}
			if ("" == portVehicleDate) {
				bootbox.alert("Please select Vehicle Date");
				$("[name='portVehicleDate']").focus();
				return false;
			}
			showLoader();
			$.ajax({
				url : "./json?portVehicleNumber=" + portVehicleNumber + "&portVehicleDate="
						+ portVehicleDate + "&method=fetchWarehouseInwardDetails",
				success : function(json, status, response) {
					processJSON(json);
				},
				error : function() {
					$(".details-container").hide();
					hideLoader();
					bootbox.alert("Unable to fetch Required Details");
				}
			});
		} else {
			bootbox.alert("Please select 'From Port' to search");
		}
	}

	function resetWarehouseInwardForm() {
		$("#details-tbody").html('');
		$(".details-container").hide();
		$("input[type='text']").removeAttr("readOnly");
		$("input[type='text']").removeAttr("disabled");
		resetForm();
 		addRow();
	}
	
	function refreshSubRows(){
 		$(".main-row").each(function(){
 			calcSecWtRow(this.id);
 		});
		applyTotalCalc();
		applyNumericConstraint();
	}

	function addRow() {
		var str = "<tr id='row-"+row_id+"' class='main-row'><input type='hidden' name='row' value='"+row_id+"'>"
				//+ "<td><input type='text' placeholder='B/E No.' name='beNo' value='' class='form-control' /></td>"
				+ "<td><input type='text' placeholder='Material Type' name='materialType' value='' class='form-control' /></td>"
				+ "<td><input type='text' placeholder='Mill Name' name='millName' value='' class='form-control' /></td>"
				+ "<td><input type='text' placeholder='Make' name='make' value='' class='form-control' /></td>"
				+ "<td><input type='text' placeholder='Grade' name='grade' value='' class='form-control' /></td>"
				+ "<td><input type='number' step='0.001' placeholder='Thickness' min='0' value='' name='thickness' onchange='calcSecWtRow(\"row-"+ row_id+ "\");' onblur='calcSecWtRow(\"row-"+ row_id+ "\");' class='form-control' /></td>"				
				+ "<td><input type='number' step='1' placeholder='Width' min='0' value='' name='width' onchange='calcSecWtRow(\"row-"+ row_id+ "\");' onblur='calcSecWtRow(\"row-"+ id	+ "\");' class='form-control' /></td>"
				+ "<td><input type='number' step='1' placeholder='Length' min='0' value='' name='length' onchange='calcSecWtRow(\"row-"+ row_id+ "\");' onblur='calcSecWtRow(\"row-"+ row_id+ "\");' class='form-control' /></td>"
				+ "<td><input type='number' step='1' min='0' value='' placeholder='Quantity' name='qty' onchange='calcSecWtRow(\"row-"+ row_id+ "\");' onblur='calcSecWtRow(\"row-"+ row_id+ "\");' class='form-control' /></td>"
				+ "<td><div class='input-group'><input type='number' step='0.001' placeholder='Section Weight' min='0' readonly value='' name='secWt' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' name='secWtUnit' value='TON' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' disabled aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='rowBtnGroupChange(this);calcSecWtRow(\"row-"+ row_id+ "\");'><a>TON</a></li><li onclick='rowBtnGroupChange(this);calcSecWtRow(\"row-"+ row_id+ "\");'><a>KG</a></li></ul></div></div></td>"
				+ "<td><div class='input-group'><input type='number' step='0.001' placeholder='Label Weight' min='0' value='' name='labelWt' onchange='calcSecWtRow(\"row-"+ row_id+ "\");' onblur='calcSecWtRow(\"row-"+ row_id+ "\");' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' onchange='calcSecWtRow(\"row-"+ row_id+ "\");' onblur='calcSecWtRow(\"row-"+ row_id+ "\");' name='labelWtUnit' value='TON' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='rowBtnGroupChange(this);calcSecWtRow(\"row-"+ row_id+ "\");'><a>TON</a></li><li onclick='rowBtnGroupChange(this);calcSecWtRow(\"row-"+ row_id+ "\");'><a>KG</a></li></ul></div></div></td>"
				+ "<td><input type='button' class='btn-danger delete-row' onclick='deleteMainRow(\"row-"+ row_id + "\");' value='-' /></td>"
				+ "</tr><tr id='row-container-"+row_id+"'><td class='expand-collapse-container' colspan='3'><input type='button' class='btn-info' onclick='$(\"#sub-table-"+row_id+"\").slideToggle(200);' value='[+/-]' /></td><td colspan='8'><table class='table table-responsive sub-table table-excel' style='display: none;' id='sub-table-"+row_id+"'>"
				+ "<thead><tr><th>Heat No.</th><th>Plate No.</th><th>Quantity</th><th>Section Weight</th><th class='cell-hide'>Weight</th><th>Location</th><th>Remark</th>"
				+ "<td><input type='button' class='btn-success add-row' onClick='addSubRow(\"row-container-"
				+ row_id
				+ "\");' value='+' /></td></tr></thead><tbody></tbody></table></td></tr>";
		$("#details-tbody").append(str);
		
		current_row_id = "row-" + row_id;
		
		refreshSubRows();
		addSubRow("row-container-" + row_id);
		if(!$("[name='portVendor']:checked").val() == "vendor")
			$("#row-"+row_id+" input[name='qty']").attr('disabled','disabled');
		row_id = row_id + 1;
		
		<% if(userInfoVO.hasAccess(Constants.Apps.WAREHOUSE_APPROVER)) { %>
			$("input[name='qty']").removeAttr('disabled','disabled');
		<%} %>
	}

	function addSubRow(row_container_id) {
		var num = row_container_id
				.substring(row_container_id.lastIndexOf("-") + 1);
		if (null == row[num] || typeof row[num] === "undefined")
			row[num] = 0;
		else
			row[num] = row[num] + 1;

		var str = "<tr id='row-sub-"+num+"-"+row[num]+"' class='sub-row'><input type='hidden' name='subRow' value='"+num+"'>"
				+ "<td><input type='text' name='heatNo' placeholder='Heat No.' class='form-control' /></td>"
				+ "<td><input type='text' name='plateNo' placeholder='Plate No.' class='form-control' /></td>"				
				+ "<td><input type='number' step='1' placeholder='Quantity' onchange='calcSecWtRow(\"row-" + num+ "\");' onblur='calcSecWtRow(\"row-" + num+ "\");' min='0' name='subQty' class='form-control' /></td>"
				+ "<td><div class='input-group'><input type='number' step='0.001' placeholder='Section Weight' min='0' readonly value='' name='subSecWt' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' name='subSecWtUnit' value='TON' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' disabled aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li><a>TON</a></li><li><a>KG</a></li></ul></div></div></td>"
				+ "<td class='cell-hide'><div class='input-group'><input type='number' step='0.001' placeholder='Weight' min='0' readonly value='' name='subWt' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' name='subWtUnit' value='TON' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' disabled aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li><a>TON</a></li><li><a>KG</a></li></ul></div></div></td>"
				+ "<td><input type='text' name='wlocation' placeholder='Location' class='form-control' /></td>"
				+ "<td><input type='text' placeholder='Remark' name='remark' value='' class='form-control' /></td>"
				+ "<td><input type='button' class='btn-danger delete-row' onclick='deleteSubRow(\"row-sub-"
				+ num + "-" + row[num] + "\");' value='-' /></td>" + "</tr>";
		$("#" + row_container_id + " tbody").append(str);
		refreshSubRows();
		
		fillArray('materialType', 'query.unique.materialType');
		fillArray('millName', 'query.unique.millName');
		fillArray('make', 'query.unique.make');
		fillArray('grade', 'query.unique.grade');
		fillArray('wlocation', 'query.unique.wlocation');
	}

	function deleteMainRow(id) {
		if ($("#" + id).parent().find("tr.main-row").length > 1){
			$("#" + id).remove();
			if((id.match(/-/g) || []).length == 1){
				var num = id.substring(id.lastIndexOf("-") + 1);
				$("#row-container-" + num).remove();
			}
		}
		else
			bootbox.alert("Cannot Delete Last Row");
		applyTotalCalc();
	}

	function deleteSubRow(id) {
		if ($("#" + id).parent().find("tr.sub-row").length > 1)
			$("#" + id).remove();
		else
			bootbox.alert("Cannot Delete Last Row");
	}
	
	function vendorCheck(){
		<% if (!userInfoVO.hasAccess(Constants.Apps.WAREHOUSE_APPROVER)) { %>
			if($("[name='portVendor']:checked").val() == "vendor") {
				$(".main-row input[name='qty']").removeAttr("disabled");
			} else {
				$(".main-row input[name='qty']").attr("disabled","disabled");
				$(".main-row input[name='qty']").val("");
			}
		<% } else { %>
			$(".main-row input[name='qty']").removeAttr("disabled");
		<% } %>
		if($("[name='portVendor']:checked").val() == "port"){
			$("#vendor-table input").attr("disabled","disabled");
			$("[name='portVehicleNumber']").removeAttr("disabled");
			$("[name='portVehicleDate']").removeAttr("disabled");
		} else {
			$("#vendor-table input").removeAttr("disabled");
			$("[name='portVehicleNumber']").attr("disabled","disabled");
			$("[name='portVehicleDate']").attr("disabled","disabled");
		}
	}
	
	function fillPortVehicleDates() {
		var select = $("[name='portVehicleDate']");
		var param1 = $("[name='portVehicleNumber']").val();
		var query = "query.combo.vehicleDates";
		if (null != param1 && "" != param1) {
			showLoader();
			$.ajax({
				url : "./xml?query=" + query + "&count=1&param1=" + param1,
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

		} else {
			$(select).html("<option value=''>--</option");
		}
	}

	$(document).ready(function() {
		fillArray('portVehicleNumber', 'query.unique.vehicleNumber');
		fillArray('vendorVehicleNumber', 'query.unique.vehicleNumber');
		fillArray('vendorName', 'query.unique.vendorName');
		
		addRow();
		
		vendorCheck();
	});

	function validateForm() {
		if ($("[name='portVendor']:checked").val() == "vendor") {
			if ("" == getValByFieldName("body", "vendorName")) {
				bootbox.alert("Please enter Vendor Name");
				return false;
			} else if ("" == getValByFieldName("body", "vendorVehicleNumber")) {
				bootbox.alert("Please enter Vehicle Number");
				return false;
			} else if ("" == getValByFieldName("body", "vendorVehicleDate")) {
				bootbox.alert("Please enter Vehicle Date");
				return false;
			} 
		} else if ("" == getValByFieldName("body", "portVehicleNumber")) {
			bootbox.alert("Please enter Vehicle Number");
			return false;
		} else if ("" == getValByFieldName("body", "portVehicleDate")) {
			bootbox.alert("Please enter Vehicle Number");
			return false;
		}
		
		var materialType = $("[name='materialType']");
		for(var i=0; i<materialType.length; i++){
			if($.trim(materialType[i].value) == ""){
				bootbox.alert("Please enter Material Type");
				return false;
			}
		}		
		var millName = $("[name='millName']");
		for(var i=0; i<millName.length; i++){
			if($.trim(millName[i].value) == ""){
				bootbox.alert("Please enter Mill Name");
				return false;
			}
		}	
		var make = $("[name='make']");
		for(var i=0; i<make.length; i++){
			if($.trim(make[i].value) == ""){
				bootbox.alert("Please enter Material Make");
				return false;
			}
		}	
		var grade = $("[name='grade']");
		for(var i=0; i<grade.length; i++){
			if($.trim(grade[i].value) == ""){
				bootbox.alert("Please enter Grade");
				return false;
			}
		}		
		var thickness = $("[name='thickness']");
		for(var i=0; i<thickness.length; i++){
			if($.trim(thickness[i].value) == "" || $.trim(thickness[i].value) <= 0){
				bootbox.alert("Please enter Thickness");
				return false;
			}
		}		
		var width = $("[name='width']");
		for(var i=0; i<width.length; i++){
			if($.trim(width[i].value) == "" || $.trim(width[i].value) <= 0){
				bootbox.alert("Please enter Width");
				return false;
			}
		}		
		var length = $("[name='length']");
		for(var i=0; i<length.length; i++){
			if($.trim(length[i].value) == "" || $.trim(length[i].value) <= 0){
				bootbox.alert("Please enter Length");
				return false;
			}
		}		
		var qty = $("[name='qty']");
		for(var i=0; i<qty.length; i++){
			if($.trim(qty[i].value) == "" || $.trim(qty[i].value) <= 0){
				bootbox.alert("Please enter Quantity");
				return false;
			}
		}		
			
		var secWt = $("[name='secWt']");
		for(var i=0; i<secWt.length; i++){
			if($.trim(secWt[i].value) == "" || $.trim(secWt[i].value) <= 0){
				bootbox.alert("Please enter Section Wt.");
				return false;
			}
		}	
		
		var labelWt = $("[name='labelWt']");
		for(var i=0; i<labelWt.length; i++){
			if($.trim(labelWt[i].value) == "" || $.trim(labelWt[i].value) <= 0){
				bootbox.alert("Please enter Label Wt.");
				return false;
			}
		}
		
		var heatNo = $("[name='heatNo']");
		for(var i=0; i<heatNo.length; i++){
			if($.trim(heatNo[i].value) == "" || $.trim(heatNo[i].value) <= 0){
				bootbox.alert("Please enter Heat No.");
				return false;
			}
		}
		
		var plateNo = $("[name='plateNo']");
		for(var i=0; i<plateNo.length; i++){
			if($.trim(plateNo[i].value) == "" || $.trim(plateNo[i].value) <= 0){
				bootbox.alert("Please enter Plate No");
				return false;
			}
		}
		
		var subQty = $("[name='subQty']");
		for(var i=0; i<subQty.length; i++){
			if($.trim(subQty[i].value) == "" || $.trim(subQty[i].value) <= 0){
				bootbox.alert("Please enter Quantity.");
				return false;
			}
		}
		
		var subSecWt = $("[name='subSecWt']");
		for(var i=0; i<subSecWt.length; i++){
			if($.trim(subSecWt[i].value) == "" || $.trim(subSecWt[i].value) <= 0){
				bootbox.alert("Please enter Section Wt.");
				return false;
			}
		}
		var wlocation = $("[name='wlocation']");
		for(var i=0; i<wlocation.length; i++){
			if($.trim(wlocation[i].value) == "" || $.trim(wlocation[i].value) <= 0){
				bootbox.alert("Please enter location");
				return false;
			}
		}
		
		return commonSubmit();
	}
	
	function processJSON(json) {
		try{
			json = JSON.parse(json);
		}catch(e){
			return false;
		}
		resetWarehouseInwardForm();

		var count = 0;
		if (null != json.beNo)
			count = json.beNo.length;

		$("[name='portVehicleNumber']").val(json.portVehicleNumber);
		$("[name='portVehicleDate']").val(json.portVehicleDate);

		if (count > 0) {
			$("#details-tbody").html("");
			$(".details-container").show();
			$("[name='portVehicleNumber']").attr('readonly', 'readonly');
			$("[name='portVehicleDate']").attr('readonly', 'readonly');
			$("[name='portVehicleNumber']").css('background', '#eee');
			$("[name='portVehicleDate']").css('background', '#eee');

			for (var i = 0; i < count; i++) {
				addRow();
				applyNumericConstraint();
				$("#"+current_row_id + " [name='beNo']").val(json.beNo[i]);
				$("#"+current_row_id + " [name='materialType']").val(json.materialType[i]);
				$("#"+current_row_id + " [name='millName']").val(json.millName[i]);
				$("#"+current_row_id + " [name='make']").val(json.make[i]);
				$("#"+current_row_id + " [name='grade']").val(json.grade[i]);
				$("#"+current_row_id + " [name='length']").val(json.length[i]);
				$("#"+current_row_id + " [name='width']").val(json.width[i]);
				$("#"+current_row_id + " [name='thickness']").val(json.thickness[i]);
				$("#"+current_row_id + " [name='labelWt']").val(json.labelWt[i]);
				$("#"+current_row_id + " [name='labelWtUnit']").val(json.labelWtUnit[i]);
				$("#"+current_row_id + " [name='secWt']").val(json.secWt[i]);
				$("#"+current_row_id + " [name='secWtUnit']").val(json.secWtUnit[i]);
				$("#"+current_row_id + " [name='qty']").val(json.qty[i]);
			}
			refreshSubRows();
		} else
			bootbox.alert("No Record Found");
		hideLoader();
	}
</script>