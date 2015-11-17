<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.form.PortOutwardForm"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c"%>

<script type="text/javascript">

	$(document).ready(function() {
		fillArray('vehicleNumber', 'query.unique.vehicleNumber');
		refreshHeatNos();
	});
	
	function refreshHeatNos() {
		$("tr.main-row").each(function(index, row) {
			var id = $(row).attr('id');
			var processed = $("#" + id + " input[name='processed']").val();
			if(processed != true && processed != "true")
				fillHeatNo(id);
		});
	}

	function fillHeatNo(id) {
		var num = id.substring(id.lastIndexOf("-") + 1);
		var param1 = $("#" + id + " input[name='make']").val();
		var param2 = $("#" + id + " input[name='grade']").val();
		var param3 = $("#" + id + " input[name='millName']").val();
		var param4 = $("#" + id + " input[name='length']").val();
		var param5 = $("#" + id + " input[name='width']").val();
		var param6 = $("#" + id + " input[name='thickness']").val();
		var param7 = $("#" + id + " input[name='location']").val();
		var param8 = "";
		var select = $("#" + id + " select[name='heatNo']");
		var query = "sp.warehouse.outward.select";
		if (null != param1 && "" != param1) {
			showLoader();
			$.ajax({
				url : "./xml?query=" + query + "&count=8&param1=" + param1
						+ "&param2=" + param2 + "&param3=" + param3
						+ "&param4=" + param4 + "&param5=" + param5
						+ "&param6=" + param6 + "&param7=" + param7
						+ "&param8=" + param8,
				success : function(xml, textStatus, response) {
					fillXmlSuccess(xml, select);
					$("#hidden-div-" + num + " [name='heatNo']").val($("#" + id + " select[name='heatNo']").val());
					hideLoader();
					fillPlateNo(id);
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

	function fillPlateNo(id) {
		var num = id.substring(id.lastIndexOf("-") + 1);
		var param1 = $("#" + id + " input[name='make']").val();
		var param2 = $("#" + id + " input[name='grade']").val();
		var param3 = $("#" + id + " input[name='millName']").val();
		var param4 = $("#" + id + " input[name='length']").val();
		var param5 = $("#" + id + " input[name='width']").val();
		var param6 = $("#" + id + " input[name='thickness']").val();
		var param7 = $("#" + id + " input[name='location']").val();
		var param8 = $("#" + id + " select[name='heatNo']").val();
		var select = $("#" + id + " select[name='plateNo']");
		var query = "sp.warehouse.outward.select";
		if (null != param1 && "" != param1) {
			showLoader();
			$.ajax({
				url : "./xml?query=" + query + "&count=8&param1=" + param1
						+ "&param2=" + param2 + "&param3=" + param3
						+ "&param4=" + param4 + "&param5=" + param5
						+ "&param6=" + param6 + "&param7=" + param7
						+ "&param8=" + param8,
				success : function(xml, textStatus, response) {
					fillXmlSuccess(xml, select);
					$("#hidden-div-" + num + " [name='plateNo']").val($("#" + id + " select[name='plateNo']").val());
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

	function validateForm() {
		var flag = true;
		var i = 0;
		$("tr.main-row").each(function(index, row) {
			var id = $(row).attr('id');
			var processed = $("#" + id + " input[name='processed']").val();
			if(processed != true && processed != "true")
				flag  = false;
				i = index + 1;
		});

		if(!flag) {
			bootbox.alert("Please insert details for Row No. " + i);
			return false;
		}
		
		if ("" == getValByFieldName("body", "actWt")) {
			bootbox.alert("Please enter Actual Weight");
			return false;
		} 
		if ("" == getValByFieldName("body", "vehicleNumber")) {
			bootbox.alert("Please enter Vehicle Number");
			return false;
		} 
		if ("" == getValByFieldName("body", "vehicleDate")) {
			bootbox.alert("Please enter vehicle Date");
			return false;
		}
		
		return commonSubmit();
	}

	function resetWarehouseOutwardProcessForm() {
		// resetForm();
		$("input[name='actWt']").val("");
		$("input[name='vehicleNumber']").val("");
		$("input[name='vehicleDate']").val("");
	}
</script>
<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Warehouse Outward</h3>
	</div>
</div>
<div>
	<html:form action="/warehouse-outward-process"
		onsubmit="return validateForm();">
		<div class="row">
			<div class="col-md-4">
				<table class="table table-responsive">
					<tr>
						<td class="form-label"><label for="dispatchNo">Dispatch
								Order No.</label></td>
						<td><input type="number" min="0" name="dispatchNo"
							disabled="disabled"
							value='<c:out value="${WarehouseOutwardProcessForm.dispatchNo}" />'
							class="form-control" /></td>
					</tr>
					<tr class=" after-result-1">
						<td class="form-label"><label for="actWt">Actual
								Weight</label></td>
						<td colspan="2">
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
				<table class="table table-responsive">
					<tr>
						<td class="form-label"><label for="vehicleNumber">Vehicle
								Number</label></td>
						<td><input type="text" name="vehicleNumber"
							class="form-control" /></td>
					</tr>
					<tr>
						<td class="form-label"><label for="vehicleDate">Vehicle
								Date</label></td>
						<td>
							<div class="input-group date date-picker-div">
								<input type="text" name="vehicleDate" class="form-control" /> <span
									class="input-group-addon"><span
									class="glyphicon-calendar glyphicon"></span></span>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<logic:notEmpty name="WarehouseOutwardProcessForm"
			property="resultList">
			<div class="row">
				<div class="col-md-12">
					<table class="table table-responsive table-form table-excel">
						<thead>
							<tr>
								<th style="min-width: 30px;">No.</th>
								<th>Make</th>
								<th>Grade</th>
								<th>Mill Name</th>
								<th>Thickness</th>
								<th>Width</th>
								<th>Length</th>
								<th>Quantity</th>
								<th>Section Weight</th>
								<th>Location</th>
								<th>Heat No</th>
								<th>Plate No</th>
								<th class="cell-edit"><span
									class="glyphicon glyphicon-tags"></span></th>
							</tr>
						</thead>
						<tbody id="details-tbody">
							<logic:iterate id="result" indexId="row"
								name="WarehouseOutwardProcessForm" property="resultList">
								<tr class="main-row" id='row-<c:out value="${row}" />'>
									<th><c:out value="${row + 1}" /></th>
									<td><input name="processed" type="hidden"
										value='<c:out value="${result.processed}" />'><input
										type="text" name="make"
										value='<c:out value="${result.make}" />' class="form-control"
										disabled="disabled" /></td>
									<td><input type="text" name="grade"
										value='<c:out value="${result.grade}" />' class="form-control"
										disabled="disabled" /></td>
									<td><input type="text" name="millName"
										value='<c:out value="${result.millName}" />'
										class="form-control" disabled="disabled" /></td>
									<td><input type="number" name="thickness"
										value='<c:out value="${result.thickness}" />'
										class="form-control" disabled="disabled" /></td>
									<td><input type="number" name="width"
										value='<c:out value="${result.width}" />' class="form-control"
										disabled="disabled" /></td>
									<td><input type="number" name="length"
										value='<c:out value="${result.length}" />'
										class="form-control" disabled="disabled" /></td>
									<td><input type="number" name="qty"
										value='<c:out value="${result.qty}" />' class="form-control"
										disabled="disabled" /></td>
									<td>
										<div class="input-group">
											<input type="number" name="secWt"
												value='<c:out value="${result.secWt}" />'
												class="form-control" disabled="disabled" />
											<div class="input-group-btn weight-group">
												<input type="hidden" name="secWtUnit"
													value='<c:out value="${result.secWtUnit}" />' />
												<button type="button" disabled="disabled"
													class="btn btn-default dropdown-toggle"
													data-toggle="dropdown" aria-expanded="false">
													<c:out value="${result.secWtUnit}" />
												</button>
											</div>
										</div>
									</td>
									<td><input type="text" name="location"
										value='<c:out value="${result.location}" />'
										class="form-control" disabled="disabled" /></td>
									<td><c:if test="${result.processed == true}">
											<select name="heatNo" class="form-control"
												disabled="disabled"
												onchange="fillPlateNo('row-<c:out value="${row}" />');"><option
													value="--" label="--">--</option></select>
										</c:if> <c:if test="${result.processed != true}">
											<select name="heatNo" class="form-control"
												onchange="fillPlateNo('row-<c:out value="${row}" />');"><option
													value="--" label="--">--</option></select>
										</c:if></td>
									<td><c:if test="${result.processed == true}">
											<select name="plateNo" class="form-control"
												disabled="disabled"><option value="--" label="--">--</option></select>
										</c:if> <c:if test="${result.processed != true}">
											<select name="plateNo" class="form-control"><option
													value="--" label="--">--</option></select>
										</c:if></td>
									<td class="cell-edit"><c:if
											test="${result.processed == true}">
											<span class="glyphicon glyphicon-tags"></span>
										</c:if> <c:if test="${result.processed != true}">
											<button data-toggle="modal"
												href='#hidden-div-<c:out value="${row}" />' class="pop-btn"
												onclick='loadStock(<c:out value="${row}" />);return false;'>
												<span class="glyphicon glyphicon-tags"></span>
											</button>
										</c:if></td>
								</tr>
							</logic:iterate>
						</tbody>
					</table>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<input type="button" value="Reset"
						onclick="resetWarehouseOutwardProcessForm();"
						class="btn pull-left" />
					<html:submit styleClass="btn pull-right"
						onclick="return validateForm();" />
				</div>
			</div>
		</logic:notEmpty>
		<html:hidden property="genericListener" value="add" />
	</html:form>

	<logic:empty name="WarehouseOutwardProcessForm" property="resultList">
		<div class="row">
			<div class="col-md-12">
				<h3>No data Found for this dispatch ID</h3>
			</div>
		</div>
	</logic:empty>

	<logic:notEmpty name="WarehouseOutwardProcessForm"
		property="resultList">
		<logic:iterate id="result" indexId="row"
			name="WarehouseOutwardProcessForm" property="resultList">
			<div class="row">
				<div class="col-md-12" id="hidden-div">
					<div id='hidden-div-<c:out value="${row}" />' class='modal fade'
						tabindex='-1' role='dialog' aria-labelledby='myModalLabel'
						aria-hidden='true'>
						<div class='modal-dialog'>
							<div class='modal-content'>
								<div class='modal-div'>
									<html:form action="/warehouse-outward-final">
										<button type='button' class='bootbox-close-button close'
											data-dismiss='modal' aria-hidden='true'>×</button>
										<input type="hidden" name="dispatchNo" />
										<input type="hidden" name="outwardTempId"
											value='<c:out value="${result.id}" />' />
										<input type="hidden" name="stockId" />
										<table class='table table-responsive table-excel'>
											<tr>
												<th colspan="6">Required Plate Details</th>
											</tr>
											<tr>
												<th>Mill Name</th>
												<td><input type="text" name="millName"
													value='<c:out value="${result.millName}" />'
													class="form-control" readonly="readonly" /></td>
												<th>Make</th>
												<td><input type="text" name="make"
													value='<c:out value="${result.make}" />'
													class="form-control" readonly="readonly" /></td>
												<th>Grade</th>
												<td><input type="text" name="grade"
													value='<c:out value="${result.grade}" />'
													class="form-control" readonly="readonly" /></td>
											</tr>
											<tr>
												<th>Thickness</th>
												<td><input type="number" name="thickness"
													value='<c:out value="${result.thickness}" />'
													class="form-control" readonly="readonly" /></td>
												<th>Width</th>
												<td><input type="number" name="width"
													value='<c:out value="${result.width}" />'
													class="form-control" readonly="readonly" /></td>
												<th>Length</th>
												<td><input type="number" name="length"
													value='<c:out value="${result.length}" />'
													class="form-control" readonly="readonly" /></td>
											</tr>
											<tr>
												<th>Section Weight</th>
												<td style="max-width: 210px;" colspan="5">
													<div class="input-group">
														<input type="number" name="secWt"
															value='<c:out value="${result.secWt}" />'
															class="form-control" readonly="readonly" />
														<div class="input-group-btn weight-group">
															<input type="hidden" name="secWtUnit"
																value='<c:out value="${result.secWtUnit}" />' />
															<button type="button" disabled="disabled"
																class="btn btn-default dropdown-toggle"
																data-toggle="dropdown" aria-expanded="false">
																<c:out value="${result.secWtUnit}" />
															</button>
														</div>
													</div>
												</td>
											</tr>
										</table>

										<table class='table table-responsive table-excel'>
											<tr>
												<th colspan="6">Selected Plate Details</th>
											</tr>
											<tr>
												<th>Thickness</th>
												<td><input type="number" name="exisThickness" value=''
													class="form-control" readonly="readonly" /></td>
												<th>Width</th>
												<td><input type="number" name="exisWidth" value=''
													class="form-control" readonly="readonly" /></td>
												<th>Length</th>
												<td><input type="number" name="exisLength" value=''
													class="form-control" readonly="readonly" /></td>
											</tr>
											<tr>
												<th>Location</th>
												<td><input type="text" name="location"
													value='<c:out value="${result.location}" />'
													class="form-control" readonly="readonly" /></td>
												<th>Heat No</th>
												<td><input type="text" name="heatNo" value=''
													class="form-control" readonly="readonly" /></td>
												<th>Plate No</th>
												<td><input type="text" name="plateNo" value=''
													class="form-control" readonly="readonly" /></td>
											</tr>
										</table>

										<table class='table table-responsive table-excel'
											id='new-plate-table'>
											<thead>
												<tr>
													<th colspan="6">New Plate(s) Details</th>
													<th style="width: 30px; text-align: center;"><input
														type='button' class="btn-success add-row"
														onClick="addRow(<c:out value="${row}" />);" value="+" /></th>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>

										<div class="row">
											<input type="button" value="Submit" class="btn pull-right"
												onclick="submitFinalForm(<c:out value="${row}" />);" /><br />
											<br /> <br />
										</div>
									</html:form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</logic:iterate>
	</logic:notEmpty>
</div>

<script type="text/javascript">

function submitFinalForm(id){
	var processed = $("#row-" + id + " [name='processed']").val();
	if(processed == true || processed == "true"){
    	bootbox.alert("This plate is already processed");
    	$("#row-" + id + " .cell-edit").html('<span	class="glyphicon glyphicon-tags"></span>');
    	$("#row-" + id + " [name='processed']").val("true");
    	$("#row-" + id + " select").attr("disabled", "disabled");
	} else {	
		var msg = "";	
		var newLength = $("#hidden-div-" + id + " [name=newLength]");
		var newWidth = $("#hidden-div-" + id + " [name=newWidth]");
		var newThickness = $("#hidden-div-" + id + " [name=newThickness]");
		for(var i=0; i<newLength.length; i++){
			var l = $(newLength[i]).val();
			var w = $(newWidth[i]).val();
			var t = $(newThickness[i]).val();
			if(l=="" || w=="" || t==""){
				msg += "Plate " + (i+1) + " details are incomplete. This plate will not be saved.<br />"
			}
		}
		if(msg != "")
			msg + msg + "<br />";
		msg = msg + "Are you Sure you want to Submit?";		
		bootbox.confirm(msg, function(result){
			if(result){
				var form = $("#hidden-div-" + id + " form");			
				showLoader();			
				$.ajax({
			        url: form.attr('action'),
			        type: 'POST',
			        data: form.serialize(),
			        success: function(result) {
			            if(result && result.message && result.message != "") {
			            	$("#row-" + id + " .cell-edit").html('<span	class="glyphicon glyphicon-tags"></span>');
			            	$("#row-" + id + " [name='processed']").val("true");
			            	$("#row-" + id + " select").attr("disabled", "disabled");
			            	bootbox.alert(result.message);
			            	refreshHeatNos();
				            hideLoader();
			            } else {
			            	bootbox.alert("Unable to process request");	            
			            }
		            	$("#hidden-div-" + id + " .bootbox-close-button").trigger('click');
			            hideLoader();
			        },
			        error: function(error){
		            	bootbox.alert("Unable to process request");
			        	hideLoader();
		            	$("#hidden-div-" + id + " .bootbox-close-button").trigger('click');
			        }
			    });
			}
		});
	}
}

function loadStock(id){
	
	$("#hidden-div-" + id + " input[name='dispatchNo']").val(document.forms[0].dispatchNo.value);
	
	var make = $("#row-" + id + " input[name='make']").val();
	var grade = $("#row-" + id + " input[name='grade']").val();
	var millName = $("#row-" + id + " input[name='millName']").val();
	var length = $("#row-" + id + " input[name='length']").val();
	var width = $("#row-" + id + " input[name='width']").val();
	var thickness = $("#row-" + id + " input[name='thickness']").val();
	var location = $("#row-" + id + " input[name='location']").val();
	var heatNo = $("#row-" + id + " select[name='heatNo']").val();
	var plateNo = $("#row-" + id + " select[name='plateNo']").val();
	var method = "fetchStockFinalDetails";
	
	var exisLength = $("#hidden-div-" + id + " [name='exisLength']").val();
	if (null == exisLength || "" == exisLength) {
		showLoader();
		$.ajax({
			url : "./json?method=" + method + "&make=" + make
					+ "&grade=" + grade + "&millName=" + millName
					+ "&length=" + length + "&width=" + width
					+ "&thickness=" + thickness + "&location=" + location
					+ "&heatNo=" + heatNo + "&plateNo=" + plateNo,
			success : function(json, status, response) {
				processJSON(json, id);
				hideLoader();
			},
			error : function() {
				bootbox.alert("Unable to fill Autocomplete for "
						+ arrayName);
				hideLoader();
			}
		});

	}	
}

function processJSON(json, id) {
	json = JSON.parse(json);
	$("#hidden-div-"+id+" input[name='exisThickness']").val(json.exisThickness);
	$("#hidden-div-"+id+" input[name='exisWidth']").val(json.exisWidth);
	$("#hidden-div-"+id+" input[name='exisLength']").val(json.exisLength);
	$("#hidden-div-"+id+" input[name='stockId']").val(json.stockId);
	addRow(id);
	hideLoader();
}

function addRow(id) {
	var str = "<tr><th>Thickness</th><td><input type='number' step='0.001' name='newThickness' value='' class='form-control' /></td>"
		+ "<th>Width</th><td><input type='number' name='newWidth' value='' class='form-control' /></td>"
		+ "<th>Length</th><td><input type='number' name='newLength' value='' class='form-control' /></td>"
		+ "<td class='cell-edit'><input type='button' class='btn-danger delete-row' onclick='deleteRow(this, "+id+")' value='-' /></td></tr>";
	$("#hidden-div-"+id+" #new-plate-table tbody").append(str);
	applyNumericConstraint();
}

function deleteRow(ele, id){
	if($("#hidden-div-"+id+" #new-plate-table tbody tr").length >= 2)
		$(ele).parent().parent().remove();
	else
		bootbox.alert("Cannot delete last row");
}

</script>

<style type="text/css">
.input-group .form-control:first-child {
	min-width: 85px;
}

#details-tbody input[type='number'] {
	max-width: 85px;
}

#details-tbody select {
	min-width: 100px;
}

table.table-form {
	max-width: 100%;
}

.table-excel .cell-edit {
	padding: 4px 5px;
	border: 1px solid #e67e22;
	min-width: 40px;
	text-align: center;
}

#hidden-div .modal-dialog {
	width: 900px;
}

.modal-div .table>thead>tr>th, .modal-div .table>tbody>tr>th {
	text-align: left;
	padding: 5px;
}

.table-excel input[type=button].add-row, .table-excel input[type=button].delete-row
	{
	margin: 0;
	padding: 0;
}

.table-excel td input, .table-excel td textarea, .table-excel td select
	{
	border: 0px;
	border-radius: 0;
	padding: 0 5px;
}
</style>