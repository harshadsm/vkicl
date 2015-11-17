<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.form.PortOutwardForm"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c"%>

<%
	Object reportListSize = request.getAttribute("reportListSize");
	pageContext.setAttribute("reportListSize", reportListSize);
%>

<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Warehouse Outward Report</h3>
	</div>
</div>
<div>
	<html:form action="/warehouse-outward-report.do">
		<div class="row">
			<div class="col-md-4">
				<table class="table table-responsive">
					<tr>
						<td class="form-label"><label for="dispatchNo">Dispatch
								Date</label></td>
						<td><html:select property="dispatchNo" styleId="dispatchNo"
								styleClass="form-control">
								<html:optionsCollection property="dispatchNoList" />
							</html:select></td>
						<td><input type='button' id="fetch-details"
							class="btn btn-default" value="Fetch" onclick="fetchReport();" /></td>
					</tr>
				</table>
			</div>
		</div>
		<html:hidden property="genericListener" value="getReport" />
	</html:form>

	<logic:notEmpty name="WarehouseOutwardReportForm" property="dispatchNo">
		<script type="text/javascript">
			var dispatchNo = '<c:out value="${WarehouseOutwardReportForm.dispatchNo}" />';
			$("[name='dispatchNo']").val(dispatchNo);
		</script>
	</logic:notEmpty>

	<div class="row details-container">
		<div class="col-md-12">
			<logic:empty name="WarehouseOutwardReportForm" property="reportList">
				<br />
				<h3>No Records Found</h3>
			</logic:empty>
			<logic:notEmpty name="WarehouseOutwardReportForm"
				property="reportList">
				<jsp:include page="../common/report-export.jsp"></jsp:include>
				<table class="table table-responsive table-report" id="result-table">
					<thead>
						<tr>
							<th>DO ID</th>
							<th>Outward Date</th>
							<th>Mill Name</th>
							<th>Grade</th>
							<th>Thickness</th>
							<th>Width</th>
							<th>Length</th>
							<th>Quantity</th>
							<th>Heat No</th>
							<th>Plate No</th>
							<th>Section Weight</th>
							<th>Section Weight (SUM)</th>
							<th>Actual Weight</th>
							<th>Actual Weight (SUM)</th>
							<th>UOM</th>
							<th>Vehicle Number</th>
							<th>Party Name</th>
							<th>Broker Name</th>
							<th>Handled By</th>
						</tr>
					</thead>
					<tbody id="details-tbody">
						<logic:iterate id="report" name="WarehouseOutwardReportForm"
							property="reportList" indexId="id">
							<tr data-method="updateWarehouseOutwardReport"
								id='row-<c:out value="${id}" />'>
								<c:if test="${id == 0}">
									<td data-type="text" data-name="id"
										rowspan='<c:out value="${reportListSize}" />'><c:out
											value="${report.id}" /></td>
									<td data-type="text" data-name="outwardDate"
										rowspan='<c:out value="${reportListSize}" />'><c:out
											value="${report.outwardDate}" /></td>
									<td data-type="text" data-name="millName"><c:out
											value="${report.millName}" /></td>
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
									<td data-type="text" data-name="heatNo"><c:out
											value="${report.heatNo}" /></td>
									<td data-type="text" data-name="plateNo"><c:out
											value="${report.plateNo}" /></td>
									<td data-type="number" data-name="secWt" data-step="0.001"><c:out
											value="${report.secWt}" /></td>
									<td data-type="number" data-name="secWtSum" data-step="0.001"
										rowspan='<c:out value="${reportListSize}" />'><c:out
											value="${report.secWtSum}" /></td>
									<td data-type="number" data-name="actualWt" data-step="0.001"><c:out
											value="${report.actualWt}" /></td>
									<td data-type="number" data-name="actualWtSum"
										data-step="0.001"
										rowspan='<c:out value="${reportListSize}" />'><c:out
											value="${report.actualWtSum}" /></td>
									<td data-type="text" data-name="actualWtUnit"
										rowspan='<c:out value="${reportListSize}" />'><c:out
											value="${report.actualWtUnit}" /></td>
									<td data-type="text" data-name="vehicleNumber"
										rowspan='<c:out value="${reportListSize}" />'><c:out
											value="${report.vehicleNumber}" /></td>
									<td data-type="text" data-name="buyerName"
										rowspan='<c:out value="${reportListSize}" />'><c:out
											value="${report.buyerName}" /></td>
									<td data-type="text" data-name="brokerName"
										rowspan='<c:out value="${reportListSize}" />'><c:out
											value="${report.brokerName}" /></td>
									<td data-type="text" data-name="handleBy"
										rowspan='<c:out value="${reportListSize}" />'><c:out
											value="${report.handleBy}" /></td>
								</c:if>
								<c:if test="${id != 0}">
									<td data-type="text" data-name="millName"><c:out
											value="${report.millName}" /></td>
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
									<td data-type="text" data-name="heatNo"><c:out
											value="${report.heatNo}" /></td>
									<td data-type="text" data-name="plateNo"><c:out
											value="${report.plateNo}" /></td>
									<td data-type="number" data-name="secWt" data-step="0.001"><c:out
											value="${report.secWt}" /></td>
									<td data-type="number" data-name="actualWt" data-step="0.001"><c:out
											value="${report.actualWt}" /></td>
								</c:if>
							</tr>
						</logic:iterate>
					</tbody>
					<tfoot>
						<tr>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th>Total</th>
							<th id="qtyTotal">Quantity</th>
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
						</tr>
					</tfoot>
				</table>
			</logic:notEmpty>
		</div>
	</div>
</div>