<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c"%>

<%
	UserInfoVO userInfoVO = (UserInfoVO) session
			.getAttribute(Constants.USER_INFO_SESSION);
	pageContext.setAttribute("userInfoVO", userInfoVO);
%>

<script type="text/javascript">
function openWarehouseDetails(id) {
	location.href = './warehouse-dispatch-details-report.do?id=' + id;
}
function startWarehouseOutward(id) {
	location.href = './warehouse-outward.do?dispatchNo=' + id;
}
function processOutward(id) {
	location.href = './warehouse-outward-process.do?dispatchNo=' + id;
}
</script>


<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Warehouse Dispatch Report</h3>
	</div>
</div>

<div>
	<html:form action="/warehouse-dispatch-report.do">
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
						<td class="form-label"><label for="status">Status</label></td>
						<td><html:select property="status" styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:option value="Pending">Pending</html:option>
								<html:option value="Processing">Processing</html:option>
								<html:option value="Completed">Completed</html:option>
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

	<logic:notEmpty name="WarehouseDispatchReportForm" property="fromDate">
		<script type="text/javascript">
			var fromDate = '<c:out value="${WarehouseDispatchReportForm.fromDate}" />';
			var toDate = '<c:out value="${WarehouseDispatchReportForm.toDate}" />';
			$("[name='fromDate']").val(fromDate);
			$("[name='toDate']").val(toDate);
		</script>
	</logic:notEmpty>

	<div class="row details-container">
		<div class="col-md-12">
			<logic:empty name="WarehouseDispatchReportForm" property="reportList">
				<br />
				<h3>No Records Found</h3>
			</logic:empty>
			<logic:notEmpty name="WarehouseDispatchReportForm"
				property="reportList">
				<jsp:include page="../common/report-export.jsp"></jsp:include>
				<table class="table table-responsive table-report" id="result-table">
					<thead>
						<tr>
							<th width="">ID</th>
							<th width="5%">Date</th>
							<th width="10%">PO No. &amp; Dated</th>
							<th width="15%">Consignee Name</th>
							<th width="15%">Buyer Name</th>
							<th width="15%">Transporter Name</th>
							<th width="10%">Handle By</th>
							<th width="20%">Comments</th>
							<th width="10%">Status</th>
							<%
								if (userInfoVO.hasAccess(Constants.Apps.DISPATCH_ORDER)) {
							%>
							<th class="cell-edit"><span
								class="glyphicon glyphicon-remove"></span></th>
							<th class="cell-edit"><span
								class="glyphicon glyphicon-zoom-in"></span></th>
							<%
								}
							%>
							<th class="cell-edit"><span
								class="glyphicon glyphicon-chevron-right"></span></th>
						</tr>
					</thead>
					<tbody id="details-tbody">
						<logic:iterate id="report" name="WarehouseDispatchReportForm"
							property="reportList">
							<tr data-method="updatePortInwardReport"
								id='row-<c:out value="${report.id}" />'>
								<td><c:out value="${report.id}" /></td>
								<td><c:out value="${report.date}" /></td>
								<td><c:out value="${report.poNo}" /></td>
								<td><c:out value="${report.consigneeName}" /></td>
								<td><c:out value="${report.buyerName}" /></td>
								<td><c:out value="${report.transporterName}" /></td>
								<td><c:out value="${report.handleBy}" /></td>
								<td><c:out value="${report.comments}" /></td>
								<td><c:out value="${report.pending}" /></td>
								<%
									if (userInfoVO.hasAccess(Constants.Apps.DISPATCH_ORDER)) {
								%>

								<c:if
									test="${(userInfoVO.userName == report.handleBy || userInfoVO.userName == 'admin') && report.pending == 'Pending'}">
									<th class="cell-edit"><button
											title='Delete Dispatch Order'
											onclick='deleteDispatchOrder(<c:out value="${report.id}" />);'>
											<span class="glyphicon glyphicon-remove"></span>
										</button></th>
								</c:if>

								<c:if
									test="${(userInfoVO.userName != report.handleBy && userInfoVO.userName != 'admin') || report.pending != 'Pending'}">
									<th class="cell-edit"><span
										class="glyphicon glyphicon-remove"></span></th>
								</c:if>

								<th class="cell-edit"><button title='View Dispatch Details'
										onclick='openWarehouseDetails(<c:out value="${report.id}" />);'>
										<span class="glyphicon glyphicon-zoom-in"></span>
									</button></th>
								<%
									}
											if (userInfoVO.hasAccess(Constants.Apps.WAREHOUSE_ENTRY)) {
								%>
								<th class="cell-edit"><c:if
										test="${report.pending == 'Processing'}">
										<button title='Complete Order Processing'
											onclick='startWarehouseOutward(<c:out value="${report.id}" />);'>
											<span class="glyphicon glyphicon-forward"></span>
										</button> 
										
									</c:if> <c:if test="${report.pending == 'Pending'}">
										<button title='Start Order Processing'
											onclick='startWarehouseOutward(<c:out value="${report.id}" />);'>
											<span class="glyphicon glyphicon-play"></span>
										</button>
									</c:if> <c:if test="${report.pending == 'Completed'}">
										<span class="glyphicon glyphicon-ok"></span>
									</c:if></th>
									<!-- @TODO Harshad Remove this later -->
									<th class="cell-edit">
										<button title='Start Order Processing'
											onclick='processOutward(<c:out value="${report.id}" />);'>
											<span class="glyphicon glyphicon-play"></span>
										</button>
									</th>
								<%
									}
								%>
							</tr>
						</logic:iterate>
					</tbody>
					<!-- <tfoot>
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
					</tfoot> -->
				</table>

				<script type="text/javascript">
					function deleteDispatchOrder(id){
						bootbox.confirm("Are you sure you want to delete this dispatch order?", function(flag){
							if(flag){
								var url = "./report?method=deleteDispatchOrder&id="+id;
								showLoader();
								$.ajax({
									url : url,
									success : function(xml, textStatus, response) {
										if (null != xml && "" != xml) {
											xmlDoc = $.parseXML(xml);
											var message = $(xmlDoc).find("message")[0].innerHTML;
											var status = "";
											if(message.length > 8)
											    status = message.substring(0, 7);
											if (status == "Success") {
												hideLoader();
												bootbox.alert(message.substring(9));
												var tr = $("#row-"+id);
												$(tr).remove();
											} else {
												bootbox.alert(message);
												hideLoader();
											}
										}
									},
									error : function() {
										bootbox.alert("Unable to delete dispatch order");
										hideLoader();
									}
								});
							}
						});
					}
				</script>

			</logic:notEmpty>
		</div>
	</div>
</div>