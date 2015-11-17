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
	// 	function fetchReport() {
	// 		var fromDate = $("[name='fromDate']").val();
	// 		var toDate = $("[name='toDate']").val();
	// 		var customerLocation = $("[name='customerLocation']").val();
	// 		var materialType = $("[name='materialType']").val();
	// 		var vesselName = $("[name='vesselName']").val();
	// 		var vesselDate = $("[name='vesselDate']").val();

	// 		// 		if ("" == fromDate) {
	// 		// 			bootbox.alert("Please select From Date");
	// 		// 			$("[name='fromDate']").focus();
	// 		// 			return false;
	// 		// 		}
	// 		// 		if ("" == toDate) {
	// 		// 			bootbox.alert("Please select To Date");
	// 		// 			$("[name='toDate']").focus();
	// 		// 			return false;
	// 		// 		}
	// 		// 		if ("" == vesselName) {
	// 		// 			bootbox.alert("Please select Vessel Name");
	// 		// 			$("[name='vesselName']").focus();
	// 		// 			return false;
	// 		// 		}
	// 		// 		if ("" == receivedFrom) {
	// 		// 			bootbox.alert("Please select Material Type");
	// 		// 			$("[name='receivedFrom']").focus();
	// 		// 			return false;
	// 		// 		}
	// 		document.forms[0].genericListener = "getReport";
	// 		document.forms[0].submit();
	// 	}
</script>

<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Warehouse Inward Report</h3>
	</div>
</div>
<div>
	<html:form action="/warehouse-inward-report.do">
		<div class="row">
			<div class="col-md-4">
				<table class="table table-responsive">
					<tr>
						<td class="form-label"><label for="fromDate">From
								Date</label></td>
						<td>
							<div class="input-group date date-picker-div">
								<input type="text" name="fromDate" class="form-control" /> <span
									class="input-group-addon"><span
									class="glyphicon-calendar glyphicon"></span></span>
							</div>
						</td>
					</tr>
					<tr>
						<td class="form-label"><label for="receivedFrom">Received
								From</label></td>
						<td><html:select property="receivedFrom"
								styleId="receivedFrom" styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:optionsCollection property="receivedFromList" />
							</html:select></td>
					</tr>
					<!-- tr>
						<td class="form-label"><label for="vesselName">Vessel
								Name</label></td>
						<td><html:select property="vesselName" styleId="vesselName"
								styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:optionsCollection property="vesselNameList" />
							</html:select></td>
					</tr-->
				</table>
			</div>
			<div class="col-md-4">
				<table class="table table-responsive">
					<tr>
						<td class="form-label"><label for="toDate">To Date</label></td>
						<td>
							<div class="input-group date date-picker-div">
								<input type="text" name="toDate" class="form-control" /> <span
									class="input-group-addon"><span
									class="glyphicon-calendar glyphicon"></span></span>
							</div>
						</td>
					</tr>
					<tr>
						<td class="form-label"><label for="materialType">Material
								Type</label></td>
						<td><html:select property="materialType"
								styleId="materialType" styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:optionsCollection property="materialTypeList" />
							</html:select></td>
					</tr>
					<!-- tr>
						<td class="form-label"><label for="vesselDate">Vessel
								Date</label></td>
						<td><html:select property="vesselDate" styleId="vesselDate"
								styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:optionsCollection property="vesselDateList" />
							</html:select></td>
					</tr-->
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-8" style="text-align: center;">
				<span class="pull-left"><input type='button' id="reset"
					class="btn btn-default" value="Reset" onclick="resetReport();" /></span>
				<span class="pull-right"><input type='button'
					id="fetch-details" class="btn btn-default" value="Search"
					onclick="fetchReport();" /></span>
			</div>
		</div>
		<html:hidden property="genericListener" value="getReport" />
	</html:form>

	<logic:notEmpty name="WarehouseInwardReportForm" property="vesselName">
		<script type="text/javascript">
			var vesselName = '<c:out value="${WarehouseInwardReportForm.vesselName}" />';
			var vesselDate = '<c:out value="${WarehouseInwardReportForm.vesselDate}" />';
			var receivedFrom = '<c:out value="${WarehouseInwardReportForm.receivedFrom}" />';
			var fromDate = '<c:out value="${WarehouseInwardReportForm.fromDate}" />';
			var toDate = '<c:out value="${WarehouseInwardReportForm.toDate}" />';
			var materialType = '<c:out value="${WarehouseInwardReportForm.materialType}" />';
			$("[name='vesselName']").val(vesselName);
			$("[name='vesselDate']").val(vesselDate);
			$("[name='receivedFrom']").val(receivedFrom);
			$("[name='fromDate']").val(fromDate);
			$("[name='toDate']").val(toDate);
			$("[name='materialType']").val(materialType);
		</script>
	</logic:notEmpty>

	<div class="row details-container">
		<div class="col-md-12">
			<logic:empty name="WarehouseInwardReportForm" property="reportList">
				<br />
				<h3>No Records Found</h3>
			</logic:empty>
			<logic:notEmpty name="WarehouseInwardReportForm"
				property="reportList">
				<jsp:include page="../common/report-export.jsp"></jsp:include>
				<table class="table table-responsive table-report" id="result-table">
					<thead>
						<tr>
							<th class="cell-hide">ID</th>
							<th>Vehicle No.</th>
							<th>Vehicle Date</th>
							<th>Vendor Name</th>
							<!-- <th>Vessel Name</th> -->
							<!-- <th>Vessel Date</th> -->
							<th>B/E No.</th>
							<th>Type</th>
							<th>Mill Name</th>
							<th>Make</th>
							<th>Grade</th>
							<th>Heat No</th>
							<th>Plate No</th>
							<th>Thickness</th>
							<th>Width</th>
							<th>Length</th>
							<th>Section Weight</th>
							<th>Label Weight</th>
							<th>UOM</th>
							<th>Quantity</th>
							<th>Location</th>
							<th class="cell-edit">Edit</th>
						</tr>
					</thead>
					<tbody id="details-tbody">
						<logic:iterate id="report" name="WarehouseInwardReportForm"
							property="reportList">
							<tr data-method="updateWarehouseInwardReport">
								<td data-type="hidden" data-name="id" class="cell-hide"><c:out
										value="${report.id}" /></td>
								<td data-type="text" data-name="vehicleNumber"><c:out
										value="${report.vehicleNumber}" /></td>
								<td data-type="text" data-name="vehicleDate"><c:out
										value="${report.vehicleDate}" /></td>
								<td data-type="text" data-name="vendorName"><c:out
										value="${report.vendorName}" /></td>
								<%-- <td><c:out value="${report.vesselName}" /></td> --%>
								<%-- <td><c:out value="${report.vesselDate}" /></td> --%>
								<td data-type="text" data-name="beNo"><c:out
										value="${report.beNo}" /></td>
								<td data-type="text" data-name="materialType"><c:out
										value="${report.materialType}" /></td>
								<td data-type="text" data-name="millName"><c:out
										value="${report.millName}" /></td>
								<td data-type="text" data-name="make"><c:out
										value="${report.make}" /></td>
								<td data-type="text" data-name="grade"><c:out
										value="${report.grade}" /></td>
								<td data-type="text" data-name="heatNo"><c:out
										value="${report.heatNo}" /></td>
								<td data-type="text" data-name="plateNo"><c:out
										value="${report.plateNo}" /></td>
								<td data-type="number" data-name="thickness" data-step="0.001"><c:out
										value="${report.thickness}" /></td>
								<td data-type="number" data-name="width"><c:out
										value="${report.width}" /></td>
								<td data-type="number" data-name="length"><c:out
										value="${report.length}" /></td>
								<td data-type="number" data-name="secWt" data-step="0.001"><c:out
										value="${report.secWt}" /></td>
								<td data-type="number" data-name="actualWt" data-step="0.001"><c:out
										value="${report.actualWt}" /></td>
								<td data-type="text" data-name="actualWtUnit"><c:out
										value="${report.actualWtUnit}" /></td>
								<td data-type="number" data-name="qty"><c:out
										value="${report.qty}" /></td>
								<td data-type="text" data-name="wlocation"><c:out
										value="${report.wlocation}" /></td>
								<td class="cell-edit"><button name="btnEdit" title="Edit"
										onclick="editReportRow(this);">
										<span class="glyphicon glyphicon glyphicon-pencil"></span>
									</button></td>
							</tr>
						</logic:iterate>
					</tbody>
					<tfoot>
						<tr>
							<th class="cell-hide"></th>
							<th></th>
							<th></th>
							<th></th>
							<!-- <th>Vessel Name</th> -->
							<!-- <th>Vessel Date</th> -->
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th>Total</th>
							<th id="qtyTotal">Quantity</th>
							<th></th>
							<th class="cell-edit"></th>
						</tr>
					</tfoot>
				</table>
			</logic:notEmpty>
		</div>
	</div>
</div>