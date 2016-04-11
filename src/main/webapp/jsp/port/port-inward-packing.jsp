<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.form.PortInwardForm"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c" %>

<script type="text/javascript">
	var id = 1, row = {}, row_id = 1, SUB_ROW_COUNTER = 1;
	
	function validateForm() {
		return commonSubmit();
	}

	function fetchPortInwardDetails() {
		var vendorName = $("input[name='vendorName']").val();
		var vesselName = $("input[name='vesselName']").val();
		var vesselDate = $("input[name='vesselDate']").val();

		if ("" == vesselDate) {
			bootbox.alert("Please select Vessel Date");
			$("input[name='vesselDate']").focus();
			return false;
		}
		if ("" == vendorName && "" == vesselName) {
			bootbox.alert("Please select either Vendor Name or Vessel Name");
			$("input[name='vendorName']").focus();
			return false;
		}
		showLoader();
		$.ajax({
			url : "./json?vendorName=" + vendorName + "&vesselName="
					+ vesselName + "&vesselDate=" + vesselDate
					+ "&method=fetchPortInwardDetails",
			success : function(json, status, response) {
				processJSON(json);
			},
			error : function() {
				$(".details-container").hide();
				hideLoader();
				bootbox.alert("Unable to fetch Required Details");
			}
		});

	}

	function processJSON(json) {
		resetInwardPackingForm();
		json = JSON.parse(json);

		var count = 0;
		if (null != json.beNo)
			count = json.beNo.length;

		$("input[name='vendorName']").val(json.vendorName);
		$("input[name='vesselName']").val(json.vesselName);
		$("input[name='vesselDate']").val(json.vesselDate);

		if (count > 0) {
			$(".details-container").show();
			$("input[name='vendorName']").attr('disabled', 'disabled');
			$("input[name='vesselName']").attr('disabled', 'disabled');
			$("input[name='vesselDate']").attr('disabled', 'disabled');
			$("input[name='vendorName']").css('background', '#eee');
			$("input[name='vesselName']").css('background', '#eee');
			$("input[name='vesselDate']").css('background', '#eee');

			for (var i = 0; i < count; i++) {
				addRow();
				applyNumericConstraint();
				$("#row-"+(row_id-1)+" input[name='pis']").val(json.pis[i]);
				$("#row-"+(row_id-1)+" input[name='beNo']").val(json.beNo[i]);
				$("#row-"+(row_id-1)+" input[name='materialType']").val(json.materialType[i]);
				$("#row-"+(row_id-1)+" input[name='millName']").val(json.millName[i]);
				$("#row-"+(row_id-1)+" input[name='make']").val(json.make[i]);
				$("#row-"+(row_id-1)+" input[name='grade']").val(json.grade[i]);
				$("#row-"+(row_id-1)+" input[name='desc']").val(json.desc[i]);
				$("#row-"+(row_id-1)+" input[name='beWt']").val(json.beWt[i]);
				$("#row-"+(row_id-1)+" input[name='beWtUnit']").val(json.beWtUnit[i]);
				$("#row-"+(row_id-1)+" input[name='beWtUnit']").next().html(json.beWtUnit[i]);
			}
			refreshSubRows();
		} else
			bootbox.alert("No Record Found");
		hideLoader();

	}

	function resetInwardPackingForm() {
		$("#details-tbody").html('');
		$(".details-container").hide();
		$("input[type='text']").removeAttr("readOnly");
		$("input[type='text']").removeAttr("disabled");
		$("input[name='vendorName']").css('background', '#ffffff');
		$("input[name='vesselName']").css('background', '#ffffff');
		$("input[name='vesselDate']").css('background', '#ffffff');
		resetForm();
	}

	function addRow() {
		var str = "<tr id='row-"+row_id+"' class='main-row'><input type='hidden' name='pis' />"
				+ "<td><input readonly type='text' name='beNo' placeholder='B/E No.' class='form-control' /></td>"
				+ "<td><input readonly type='text' name='materialType' placeholder='Material Type' class='form-control' /></td>"
				+ "<td><input readonly type='text' name='millName' placeholder='Mill Name' class='form-control' /></td>"
				+ "<td><input readonly type='text' name='make' placeholder='Make' class='form-control' /></td>"
				+ "<td><input readonly type='text' name='grade' placeholder='Grade' class='form-control' /></td>"
				+ "<td><input readonly type='text' name='desc'	placeholder='Description' class='form-control' /></td>"
				+ "<td><div class='input-group'>"
				+ "<input type='number' step='0.001' min='0' readonly name='beWt' placeholder='B/E Weight' class='form-control' aria-label='...'> "
				+ "<div class='input-group-btn weight-group'>"
				+ " <input type='hidden' name='beWtUnit' value='' /> "
				+ " <ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);'><a>TON</a></li> <li onclick='btnGroupChange(this);'><a>KG</a></li></ul></div></div></td>"
				+ "</tr><tr id='row-container-"+row_id+"'><td class='expand-collapse-container' colspan='1'>"
				+ "<input type='button' class='btn-info' onclick='$(\"#sub-table-"
				+ row_id
				+ "\").slideToggle(200);' value='[+/-]' /></td>"
				+ "<td colspan='5'>"
				+ "<table class='table table-responsive sub-table table-excel' style='display: none;' id='sub-table-"+row_id+"'>"
				+ "<thead><tr><th>Thickness</th><th>Width</th><th>Length</th><th>Quantity</th><th>Actual Weight</th>"
				+ "<td><input type='button' class='btn-success add-row' onClick='addSubRow(\"row-container-"
				+ row_id
				+ "\");' value='+' /></td></tr></thead>"
				+ "<tbody></tbody></table></td></tr>";
				
		$("#details-tbody").append(str);
		addSubRow("row-container-" + row_id);
		row_id = row_id + 1;
		refreshSubRows();
	}

	function addSubRow(row_container_id) {
		var num = row_container_id
				.substring(row_container_id.lastIndexOf("-") + 1);
		if (null == row[num] || typeof row[num] === "undefined")
			row[num] = 0;
		else
			row[num] = row[num] + 1;

		var str = "<tr id='row-sub-"+num+"-"+row[num]+"' class='sub-row'><input type='hidden' name='subPis' />"
				+ "<td><input type='number' step='0.001' min='0' name='thickness' placeholder='Thickness' class='form-control' /></td>"
				+ "<td><input type='number' step='1' min='0' name='width' placeholder='Width' class='form-control' /></td>"
				+ "<td><input type='number' step='1' min='0' name='length' placeholder='Length' class='form-control' /></td>"
				+ "<td><input type='number' step='1' min='0' name='qty' placeholder='Quantity' class='form-control' /></td>"
				+ "<td><div class='input-group'><input type='number' step='0.001' min='0' name='actualWt' placeholder='Actual Weight' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' name='actualWtUnit' value='TON' /><button type='button'class='btn btn-default dropdown-toggle' disabled data-toggle='dropdown' aria-expanded='false'>TON<span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);'><a>TON</a></li><li onclick='btnGroupChange(this);'><a>KG</a></li></ul></div></div></td>"
				+ "<td><input type='button' class='btn-danger delete-row' onclick='deleteRow(\"row-sub-"
				+ num + "-" + row[num] + "\");' value='-' /></td>" + "</tr>";
		$("#" + row_container_id + " tbody").append(str);
		refreshSubRows();
	}
	
	function refreshSubRows(){
		$(".main-row").each(function(){
			var pis = $(this).find("input[name=pis]")[0].value;
			var unit = $(this).find("input[name=beWtUnit]")[0].value;
			$(this).next().find("input[name=subPis]").each(function(){
				$(this).val(pis);
			});
			$(this).next().find("input[name=actualWtUnit]").each(function(){
				$(this).val(unit);
				$(this).next().html(unit);
			});
		});
		applyNumericConstraint();
		applyTotalCalc();
	}

	function deleteRow(id) {
		if ($("#" + id).parent().find("tr").length > 1)
			$("#" + id).remove();
		else
			bootbox.alert("Cannot Delete Last Row");
		refreshSubRows();
	}
	
	function submit(){
		return commonSubmit();
	}

	$(document).ready(function() {
		addRow();
		
	});
	
	
	
	
	// Harshad New Functions
	function addSubRow2() {
		SUB_ROW_COUNTER = SUB_ROW_COUNTER + 1;
		var str = "<tr id='row-sub-"+SUB_ROW_COUNTER+"' class='sub-row'><input type='hidden' name='subPis' />"
				+ "<td><input type='number' step='0.001' min='0' name='thickness' placeholder='Thickness' class='form-control' /></td>"
				+ "<td><input type='number' step='1' min='0' name='width' placeholder='Width' class='form-control' /></td>"
				+ "<td><input type='number' step='1' min='0' name='length' placeholder='Length' class='form-control' /></td>"
				+ "<td><input type='number' step='1' min='0' name='qty' placeholder='Quantity' class='form-control' /></td>"
				+ "<td><div class='input-group'><input type='number' step='0.001' min='0' name='actualWt' placeholder='Actual Weight' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' name='actualWtUnit' value='TON' /><button type='button'class='btn btn-default dropdown-toggle' disabled data-toggle='dropdown' aria-expanded='false'>TON</button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);'><a>TON</a></li><li onclick='btnGroupChange(this);'><a>KG</a></li></ul></div></div></td>"
				+ "<td><input type='button' class='btn-danger delete-row' onclick='deleteRow(\"row-sub-"
				+ SUB_ROW_COUNTER+"\");' value='-' /></td>" + "</tr>";
		$("#port_inward_details_table tbody").append(str);
		refreshSubRows();
	}
</script>
<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Port Inward Details</h3>
	</div>
</div>
<div>
	<html:form action="/port-inward-details" onsubmit="return validateForm();">
		<input name="port_inward_id" type="hidden" value=<%=request.getAttribute("port_inward_id") %> />
		
		
		<div class="row details-container">
			<div class="col-md-12">
				<table class="table table-responsive">
					<thead>
						<tr>
							<th>B/E No.hh</th>
							<th>Material Type</th>
							<th>Mill Name</th>
							<th>Make</th>
							<th>Grade</th>
							<th>Description</th>
							<th>B/E Weight</th>
						</tr>
					</thead>
					<tbody id="details-tbody">

					</tbody>
				</table>
			</div>
		</div>
		<div class="row details-container">
			<div class="col-md-12">
				<input type="button" value="Reset"
					onclick="resetInwardPackingForm();" class="btn pull-left">
				<html:submit styleClass="btn pull-right" onclick="return validateForm();" />
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-1"></div>
			<div class="col-md-10">
			<table class="table table-responsive sub-table table-excel" id="port_inward_details_table" >
				<thead>
					<tr>
						<th>Thickness</th>
						<th>Width</th>
						<th>Length</th>
						<th>Quantity</th>
						<th>Actual Weight</th>
						<td><input type='button' class='btn-success add-row' onClick='addSubRow2()' value='+' /></td>
					</tr>
				</thead>
				<tbody>
					<tr class="sub-row" id="row-sub-1">
						<input type="hidden" name="subPis">
						<td><input type="number" class="form-control"
							placeholder="Thickness" name="thickness" min="0" step="0.001"></td>
						<td><input type="number" class="form-control"
							placeholder="Width" name="width" min="0" step="1"></td>
						<td><input type="number" class="form-control"
							placeholder="Length" name="length" min="0" step="1"></td>
						<td><input type="number" class="form-control"
							placeholder="Quantity" name="qty" min="0" step="1"></td>
						<td><div class="input-group">
								<input type="number" aria-label="..." class="form-control"
									placeholder="Actual Weight" name="actualWt" min="0"
									step="0.001">
								<div class="input-group-btn weight-group">
									<input type="hidden" value="TON" name="actualWtUnit">
									<button aria-expanded="false" data-toggle="dropdown"
										disabled="" class="btn btn-default dropdown-toggle"
										type="button">
										TON</span>
									</button>
									
								</div>
							</div></td>
						<td><input type="button" value="-"
							onclick="deleteRow('row-sub-1')"
							class="btn-danger delete-row"></td>
					</tr>
				</tbody>
			</table>
			</div>
			<div class="col-md-1"></div>
		</div>
		<html:hidden property="genericListener" value="addDetails" />
	</html:form>
</div>