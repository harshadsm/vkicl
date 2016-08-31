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
	// 		var vesselName = $("[name='vesselName']").val();
	// 		var dispatchedTo = $("[name='dispatchedTo']").val();

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
	// 		// 		if ("" == dispatchedTo) {
	// 		// 			bootbox.alert("Please select Material Type");
	// 		// 			$("[name='dispatchedTo']").focus();
	// 		// 			return false;
	// 		// 		}
	// 		document.forms[0].genericListener = "getReport";
	// 		document.forms[0].submit();
	// 	}
</script>

<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Port Outward Report</h3>
	</div>
</div>
<div>
	<html:form action="/port-outward-report.do">
		<div class="row">
			<div class="col-md-4">
				<table class="table table-responsive">
					<tr>
						<td class="form-label"><label for="vesselName">Vessel
								Name</label></td>
						<td><html:select property="vesselName" styleId="vesselName"
								styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:optionsCollection property="vesselNameList" />
							</html:select></td>
					</tr>
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
						<td class="form-label"><label for="grade">Material
								Grade</label></td>
						<td><html:select property="grade" styleId="grade"
								styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:optionsCollection property="gradeList" />
							</html:select></td>
					</tr>
					
					
				</table>
			</div>
			<div class="col-md-4">
				<table class="table table-responsive">
					<tr>
						<td class="form-label"><label for="dispatchedTo">Dispatched
								To</label></td>
						<td><html:select property="dispatchedTo"
								styleId="dispatchedTo" styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:optionsCollection property="dispatchedToList" />
							</html:select></td>
					</tr>
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

	<logic:notEmpty name="PortOutwardReportForm" property="vesselName">
		<script type="text/javascript">
			var vesselName = '<c:out value="${PortOutwardReportForm.vesselName}" />';
			var dispatchedTo = '<c:out value="${PortOutwardReportForm.dispatchedTo}" />';
			var fromDate = '<c:out value="${PortOutwardReportForm.fromDate}" />';
			var toDate = '<c:out value="${PortOutwardReportForm.toDate}" />';
			$("[name='vesselName']").val(vesselName);
			$("[name='dispatchedTo']").val(dispatchedTo);
			$("[name='fromDate']").val(fromDate);
			$("[name='toDate']").val(toDate);
		</script>
	</logic:notEmpty>

	<div class="row details-container">
		<div class="col-md-12">
			<logic:empty name="PortOutwardReportForm" property="reportList">
				<br />
				<h3>No Records Found</h3>
			</logic:empty>
			<logic:notEmpty name="PortOutwardReportForm" property="reportList">
				<jsp:include page="../common/report-export.jsp"></jsp:include>
				<table class="table table-responsive table-report" id="result-table">
					<thead>
						<tr>
							<th width="0%" class="cell-hide">ID</th>
							<th width="10%">Delivery Date</th>
							<th width="15%">Dispatch To</th>
							<th width="10%">Vehicle No</th>
							
							<th width="5%">Grade</th>
							<th width="5%">Thickness</th>
							<th width="5%">Width</th>
							<th width="5%">Length</th>
							<th width="5%">No.of Pcs</th>
							<th width="10%">Sec.Wt</th>
							<th width="5%">Actual Wt</th>
							<!-- <th width="5%">Label Weight</th>-->
							<th width="10%">Invoice No. port</th>
							<th width="5%" class="cell-edit"><span
								class="glyphicon glyphicon glyphicon-pencil"></span></th>
							<th width="5%" class="cell-edit"><span
								class="glyphicon glyphicon glyphicon-remove"></span></th>
						</tr>
					</thead>
					<tbody id="details-tbody">
						<logic:iterate id="report" name="PortOutwardReportForm"
							property="reportList">
							<tr data-method="updatePortOutwardReport">
								<td data-type="hidden" data-name="id" class="cell-hide"><c:out
										value="${report.id}" /></td>
										<!--  <td data-type="hidden" data-name="vesselName" class="cell-hide"><c:out
										value="${report.vesselName}" /></td>
										<td data-type="hidden" data-name="vesselDate" class="cell-hide"><c:out
										value="${report.vesselDate}" /></td>-->
								<td data-type="text" data-name="vehicleDate"><c:out
										value="${report.vehicleDate}" /></td>
								<td data-type="text" data-name="dispatchedTo"><c:out
										value="${report.dispatchedTo}" /></td>
										<td data-type="text" data-name="vehicleNumber"><c:out
										value="${report.vehicleNumber}" /></td>
								
								<td data-type="text" data-name="grade"><c:out
										value="${report.grade}" /></td>
								<td data-type="number" data-name="thickness" data-step="0.001"><c:out
										value="${report.thickness}" /></td>
								<td data-type="number" data-name="width"><c:out
										value="${report.width}" /></td>
								<td data-type="number" data-name="length"><c:out
										value="${report.length}" /></td>
								<td data-type="number" data-name="qty"><c:out
										value="${report.qty}" /></td>
								<td data-type="number" data-name="secWt" data-step="0.001"><c:out
										value="${report.secWt}" /></td>
								<td data-type="number" data-name="actualWt"><c:out
										value="${report.actualWt}" /></td>
								<td data-type="text" data-name="invoice" ><c:out
										value="${report.invoice}" /></td>
								<!-- 		
								<td data-type="text" data-name="actualWtUnit"><c:out
										value="${report.actualWtUnit}" /></td>
								-->
								<td class="cell-edit"><button name="btnEdit" title="Edit" onclick="editReportRow(this);">
										<span class="glyphicon glyphicon glyphicon-pencil"></span>
									</button></td>
								<td class="cell-edit"><button name="btnDelete" title="Delete" onclick="deleteReportRow(this);">
										<span class="glyphicon glyphicon glyphicon-remove"></span>
								</button></td>
									
							</tr>
						</logic:iterate>
					</tbody>
					<tfoot>
						<tr>
							<th width="0%" class="cell-hide"></th>
							<th width="10%"></th>
							<th width="10%"></th>
							<th width="5%"></th>
							<th width="10%"></th>
							<th width="5%"></th>
							<th width="10%"></th>
							
							<!-- <th width="5%"></th> -->
							
							<!-- <th>Mill Name</th> -->
							<!-- <th>Make</th> -->
							
							
							<th width="5%">Total</th>
							<th width="5%" id="qtyTotal">Quantity</th>
							<th width="5%" id="secWtTotal"></th>
							<th width="5%" id="actualWtTotal"></th>
							<th width="10%"></th>
							<th class="cell-edit"></th>
						</tr>
					</tfoot>
				</table>
			</logic:notEmpty>
		</div>
	</div>
</div>