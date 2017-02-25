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

function startPurchaseOrderDelivery(id) {
	location.href = './delivery-note.do?purchaseOrderNo=' + id;
}

</script>


<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Port Purchase Order Delivery</h3>
	</div>
</div>

<div>
	<html:form action="/port-purchase-order-delivery.do">
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
					<!--<tr>
						 <td class="form-label"><label for="status">Status</label></td>
						<td><html:select property="status" styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:option value="Pending">Pending</html:option>
								<html:option value="Processing">Processing</html:option>
								<html:option value="Completed">Completed</html:option>
							</html:select></td> 
							
							
					</tr>-->
					<tr>
					<td class="form-label"><label for="customerName">Customer Name</label></td>
						<td><input type="text"  name="customerName" 
							id="customerName" class="form-control"/></td>
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



	<div class="row details-container">
		<div class="col-md-12">
			<logic:empty name="PortPurchaseOrderDeliveryForm" property="reportList">
				<br />
				<h3>No Records Found</h3>
			</logic:empty>
			<logic:notEmpty name="PortPurchaseOrderDeliveryForm"
				property="reportList">
				<jsp:include page="../common/report-export.jsp"></jsp:include>
				<table class="table table-responsive table-report" id="result-table">
					<thead>
						<tr>
							
							<th width="15%">Date of PPO</th>
							<th width="10%">PPO Number</th>
							<th width="20%">Customer Name</th>
							<th width="25%">Delivery Address</th>
							<th width="25%">Vessel Name </th>
							<th width="25%">Grade</th>
							<th width="25%">Total Items</th>
							<th width="10%">Pending to be delivered</th>
							<%
								if (userInfoVO.hasAccess(Constants.Apps.PORT_ENTRY)) {
							%>
							<th class="cell-edit"><span
								class="glyphicon glyphicon-remove"></span></th>
							<% } %>
							<th class="cell-edit"><span
								class="glyphicon glyphicon-chevron-right"></span></th>
						</tr>
					</thead>
					<tbody id="details-tbody">
						<logic:iterate id="report" name="PortPurchaseOrderDeliveryForm"
							property="reportList">
							<tr 
								id='row-<c:out value="${report.id}" />'>
								<td><c:out value="${report.ppoDate}" /></td>
								<td><c:out value="${report.ppoNo}" /></td>
								<td><c:out value="${report.customerName}" /></td>
								<td><c:out value="${report.deliveryAddress}" /></td>
								<td><c:out value="${report.vesselName}" /></td>
								<td><c:out value="${report.grade}" /></td>
								<td><c:out value="${report.totalQuantity}" /></td>
							   <td><c:out value="${report.pendingQuantity}" /></td>
							   <%
									if (userInfoVO.hasAccess(Constants.Apps.DISPATCH_ORDER)) {
								%>

								<c:if
									test="${(userInfoVO.userName == 'admin')}">
									<th class="cell-edit"><button
											title='Delete Dispatch Order'
											onclick='deleteDispatchOrder(<c:out value="${report.id}" />);'>
											<span class="glyphicon glyphicon-remove"></span>
										</button></th>
								</c:if>
								<% } %>
								<th class="cell-edit">
								<button title='Start Order Processing'
											onclick='startPurchaseOrderDelivery(<c:out value="${report.ppoNo}" />);'>
											<span class="glyphicon glyphicon-play"></span>
										</button></th>
							</tr>
						</logic:iterate>
					</tbody>
					
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