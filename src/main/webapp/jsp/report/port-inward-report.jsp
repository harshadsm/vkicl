
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c"%>

<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Port Inward Report</h3>
	</div>
</div>

<div>
	<html:form action="/port-inward-report.do">
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
						<td class="form-label"><label for="vesselName">Vessel
								Name</label></td>
						<td><html:select property="vesselName" styleId="vesselName"
								styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:optionsCollection property="vesselNameList" />
							</html:select></td>
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

	<logic:notEmpty name="PortInwardReportForm" property="vesselName">
		<script type="text/javascript">
			var vesselName = '<c:out value="${PortInwardReportForm.vesselName}" />';
			var materialType = '<c:out value="${PortInwardReportForm.materialType}" />';
			var fromDate = '<c:out value="${PortInwardReportForm.fromDate}" />';
			var toDate = '<c:out value="${PortInwardReportForm.toDate}" />';
			$("[name='vesselName']").val(vesselName);
			$("[name='materialType']").val(materialType);
			$("[name='fromDate']").val(fromDate);
			$("[name='toDate']").val(toDate);
		</script>
	</logic:notEmpty>

	<div class="row details-container">
		<div class="col-md-12">
			<logic:empty name="PortInwardReportForm" property="reportList">
				<br />
				<h3>No Records Found</h3>
			</logic:empty>
			<logic:notEmpty name="PortInwardReportForm" property="reportList">
				<jsp:include page="../common/report-export.jsp"></jsp:include>
				<table class="table table-responsive table-report tablesorter"
					id="result-table">
					<thead>
						<tr>
							<th width="0%" class="cell-hide">ID</th>
							<th width="15%">Vendor Name</th>
							<th width="10%">Vessel Name</th>
							<th width="10%">Vessel Date</th>
							<th width="5%">B/E No.</th>
							<th width="15%">Mill Name</th>
							<th width="5%">Make</th>
							<th width="5%">Type</th>
							<th width="5%">Grade</th>
							<th width="5%">Thickness</th>
							<th width="5%">Width</th>
							<th width="5%">Length</th>
							<th width="5%">Quantity</th>
							<th width="5%">B/E Wt</th>
							<th width="5%">UOM</th>
							
						</tr>
					</thead>
					<tbody id="details-tbody">
						<logic:iterate id="report" name="PortInwardReportForm"
							property="reportList">
							<tr data-method="updatePortInwardReport">
								<td data-type="hidden" data-name="id" class="cell-hide"><c:out
										value="${report.id}" /></td>
								<td data-type="text" data-name="vendorName"><c:out
										value="${report.vendorName}" /></td>
								<td data-type="text" data-name="vesselName"><c:out
										value="${report.vesselName}" /></td>
								<td data-type="text" data-name="vesselDate"><c:out
										value="${report.vesselDate}" /></td>
								<td data-type="text" data-name="beNo"><c:out
										value="${report.beNo}" /></td>
								<td data-type="text" data-name="millName"><c:out
										value="${report.millName}" /></td>
								<td data-type="text" data-name="make"><c:out
										value="${report.make}" /></td>
								<td data-type="text" data-name="materialType"><c:out
										value="${report.materialType}" /></td>
								<td data-type="text" data-name="grade"><c:out
										value="${report.grade}" /></td>
								<td data-type="number" data-step="0.001" data-name="thickness"><c:out
										value="${report.thickness}" /></td>
								<td data-type="number" data-step="1" data-name="width"><c:out
										value="${report.width}" /></td>
								<td data-type="number" data-step="1" data-name="length"><c:out
										value="${report.length}" /></td>
								<td data-type="number" data-step="1" data-name="qty"><c:out
										value="${report.qty}" /></td>
								<td data-type="number" data-step="0.001" data-name="beWt"><c:out
										value="${report.beWt}" /></td>
								<td data-type="text" data-name="beWtUnit"><c:out
										value="${report.beWtUnit}" /></td>
								<td data-type="text" data-name="action">
									<a href="viewPortInwardRecord.do?port_inward_id=${report.id}">View</a>
								</td>
								
							</tr>
						</logic:iterate>
					</tbody>
					<tfoot>
						<tr>
							<th width="0%" class="cell-hide"></th>
							<th width="15%"></th>
							<th width="10%"></th>
							<th width="10%"></th>
							<th width="5%"></th>
							<th width="15%"></th>
							<th width="5%"></th>
							<th width="5%"></th>
							<th width="5%"></th>
							<th width="5%"></th>
							<th width="5%"></th>
							<th width="5%">Total</th>
							<th width="5%" id="qtyTotal"></th>
							<th width="5%"></th>
							<th width="5%"></th>
							<th class="cell-edit"></th>
						</tr>
					</tfoot>
				</table>
			</logic:notEmpty>
		</div>
	</div>
</div>
