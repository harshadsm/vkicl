<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c"%>


<script type="text/javascript">
	var vendorName = [], vesselName = [], materialType = [], millName = [], make = [], grade = [];

	$(document).ready(function() {
		fillArray('vendorName', 'query.unique.vendorName');
		fillArray('vesselName', 'query.unique.vesselName');
	});

	function validateForm() {
		if ("" == getValByFieldName("body", "vendorName")) {
			bootbox.alert("Please enter Vendor Name");
			return false;
		} else if ("" == getValByFieldName("body", "vesselName")) {
			bootbox.alert("Please enter Vessel Name");
			return false;
		} else if ("" == getValByFieldName("body", "vesselDate")) {
			bootbox.alert("Please enter Vessel Date");
			return false;
		}

		return commonSubmit();
	}
</script>
<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Port Inward</h3>
	</div>
</div>
<div>
	<html:form action="/port-inward">
		<div class="row">
			<div class="col-md-4">
				<table class="table table-responsive">
					<tr>
						<td class="form-label"><label for="vendorName">Vendor
								Name</label></td>
						<td><html:text property="vendorName"
								styleClass="form-control" /></td>
					</tr>
					<tr>
						<td class="form-label"><label for="vesselName">Vessel
								Name</label></td>
						<td><html:text property="vesselName"
								styleClass="form-control" /></td>
					</tr>
					<tr>
						<td class="form-label"><label for="vesselDate">Vessel
								Date</label></td>
						<td>
							<div class="input-group date date-picker-div">
								<html:text property="vesselDate" styleClass="form-control" />
								<span class="input-group-addon"><span
									class="glyphicon-calendar glyphicon"></span></span>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<table class="table table-responsive">
					<thead>
						<tr>
						<!-- <th>B/E No.</th> -->
							<th>Material Type</th>
							<th>Mill Name</th>
							<th>Material Make</th>
							<th>Material Grade</th>
							<th>Description</th>
							<th>B/E Weight</th>
							<th><input type='button' class="btn-success add-row"
								onClick="addRow();" value="+" /></th>
						</tr>
					</thead>
					<tbody id="details-tbody">
					</tbody>
					<tfoot>
						<tr>
							<th colspan="6" style="text-align: right;">Total</th>
							<th id="beWtTotal"></th>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<input type="button" value="Reset" onclick="resetForm();"
					class="btn pull-left">
				<html:submit styleClass="btn pull-right"
					onclick="return validateForm();" />
			</div>
		</div>
		<html:hidden property="genericListener" value="add" />
	</html:form>
</div>

<script type="text/javascript">

	var id = 1;

	function addRow() {
		var str = "<tr id='row-" + id + "'>"
				+ "<td><input type='text' placeholder='Material Type' name='materialType' class='form-control'/ ></td>"
				+ "<td><input type='text' placeholder='Mill Name' name='millName' class='form-control'/ ></td>"
				+ "<td><input type='text' placeholder='Make' name='make' class='form-control'/ ></td>"
				+ "<td><input type='text' placeholder='Grade' name='grade' class='form-control'/ ></td>"
				+ "<td><input type='text' placeholder='Description' name='desc' class='form-control'/ ></td>"
				+ "<td><div class='input-group'><input type='number' step='0.001' placeholder='B/E Weight' min='0' name='beWt' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' name='beWtUnit' value='TON' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);'><a>TON</a></li><li onclick='btnGroupChange(this);'><a>KG</a></li></ul></div></div></td>"
				+ "<td><input type='button' class='btn-danger delete-row text-center' onclick='deleteRow($(this).parent().parent().attr(\"id\"));' value='-' /></td></tr>";
		$("#details-tbody").append(str);
		id = id + 1;

		fillArray('materialType', 'query.unique.materialType');
		fillArray('millName', 'query.unique.millName');
		fillArray('make', 'query.unique.make');
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

	if (id == 1)
		addRow();
</script>