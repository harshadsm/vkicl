<%@page import="vkicl.vo.PortPurchaseOrderVO"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c" %>

<% 
PortPurchaseOrderVO vo = (PortPurchaseOrderVO)request.getAttribute("port_purchase_order_details");
System.out.print("hi");
%>

<script type="text/javascript">

function validateForm() {
	
	return false;
	
return commonSubmit();
}


</script> 

<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Port Purchase Order Delivery Note</h3>
	</div>
</div>
<div>
	<html:form enctype="multipart/form-data" action="/delivery-note"
		method="post">
		<div class="row">
			<div class="col-md-10">
				<div class="panel panel-default">
					<div class="panel-heading"></div>
					
						<table class="table">
							
							<tbody id="details-tbody">
								<tr>
									<th>PPO Number</th><td><%=vo.getPpoNo() %>
									<input type="hidden" name='ppoNo' id="ppoNo" value="<%=vo.getPpoNo() %>"/></td>
								
									<th>PPO Date</th><td><%=vo.getPpoDate() %></td>
								</tr>
								<tr>
									<th>Customer Name</th><td><%=vo.getCustName() %></td>
								<th>Delivery address</th><td><%=vo.getDeliveryAddr() %></td>
									
								</tr>
								<tr>
									<th>Payment Terms</th><td><%=vo.getPaymentTerms() %></td>
								
									<th>Excise</th><td><%=vo.getExcise() %></td>
								</tr>
								<tr>
									<th>Tax</th><td><%=vo.getTax() %></td>
								
									<th>Transport</th><td><%=vo.getTransport() %></td>
								</tr>
							</tbody>
						</table>
					
				</div>
			</div>
			<div class="row">
			
			<div class="col-md-10">
				<h3>Port Purchase Order Line Items</h3>
				<table class="table table-responsive table-report" id="delivery-table">
					<thead>
						<tr>
							<th width="5%">No </th>
							<th width="10%">Thickness</th>
							<th width="20%">Width</th>
							<th width="20%">Length</th>
							<th width="20%">Ordered Quantity</th>
							<th width="20%">Delivery Quantity</th>
							
						</tr>
					</thead>
					<tbody id="details-tbody">
					<logic:iterate id="report" name="PortPurchaseOrderDeliveryNoteForm"
							property="reportList">
							<tr 
								id='row-<c:out value="${report.ppoLineitemNo}" />'>
								<td><c:out value="${report.ppoLineitemNo}" /></td>
								<td><c:out value="${report.thickness}" /></td>
								<td><c:out value="${report.length}" /></td>
								<td><c:out value="${report.width}" /></td>
								<td><c:out value="${report.orderedQty}" /></td>
								<td>
							   <input number digits="" type="number"  name="txtDeliveryQty" id="deliveryQty"/>
							   </td>
							</tr>
						</logic:iterate>
					</tbody>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				
				<html:submit styleClass="btn pull-right"
					onclick="return validateForm()" />
			</div>
		</div>
		<html:hidden property="genericListener" value="addDetails" />

	</html:form>
</div>
	
</div>

<style>

.table-excel, .table-excel td, .table-excel th, .table-excel tr {
	background-color: rgba(230, 126, 34, 0.05);
	margin: 10px 0;
	border: 1px solid #e67e22;
}

.table-excel td {
	/*min-width: 165px;*/
	text-align: left;
}

.table-excel td.td-1-input {
	width: 70px;
}

.table-excel td label {
	width: 100%;
	text-align: right;
	padding-top: 5px;
}

.table-excel td input[type='text'] {
	min-width: 100px;
}

.table-excel td input[type='checkbox'], .table-excel td input[type='radio']
	{
	text-align: left;
	margin: 0 10px;
}

.table-excel td, .table-excel th {
	border: 1px solid #e67e22;
	padding: 0 0;
	margin: 0;
}

.table-excel td.row-id{
	text-align: center;
    font-weight: bold;
    min-width: 35px;
}

.table-excel th {
	padding: 2px 0;
}

.table-excel label {
	padding: 0 5px;
}

.table-excel td input, .table-excel td textarea, .table-excel td select {
	border: 0px;
	border-radius: 0;
	padding: 0 0 0 8px;
}

.table-excel td textarea {
	padding: 5px;
}

.table-excel .input-group-addon {
	border-radius: 0;
	border: 1px solid #eee;
	margin: 0;
}

.table-excel td.input-td {
	background-color: #FFFFFF;
}

.table-excel input[type=button].add-row, .table-excel input[type=button].delete-row
	{
	margin: 5px;
	padding: 0;
}



.table-excel td.center-input {
	text-align: center;
}

.table-excel>thead>tr>th, .table-excel>tbody>tr>th, .table-excel>tfoot>tr>th, .table-excel>thead>tr>td, .table-excel>tbody>tr>td, .table-excel>tfoot>tr>td{
padding: 0;
	border: 1px solid #e67e22;
}

.table>thead:first-child>tr:first-child>th.margin-5-sides {
	padding: 0 5px;
}

.table-excel input[name="length"], .table-excel input[name="width"], .table-excel input[name="thickness"], .table-excel input[name="qty"]{
	min-width: 70px;
    max-width: 100px;
}

.table-excel td input[type='text'], .table-excel td input[type='number'] {
	min-width: 70px;
    max-width: 180px;
}

.table-excel .td-excel-pop-btn {
	text-align: center;
}

#main-table {
	width: 100%;
}

#main-table th{
	height: 35px;
}
</style>
<script>



