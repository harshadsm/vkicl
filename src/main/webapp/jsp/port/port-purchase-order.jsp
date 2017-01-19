<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c"%>

<%
	UserInfoVO userInfoVO = (UserInfoVO) session
			.getAttribute(Constants.USER_INFO_SESSION);
%>

<style>
.dispatch-table, .dispatch-table td.excel, .dispatch-table th.excel {
	background-color: rgba(230, 126, 34, 0.05);
	margin: 10px 0;
	border: 1px solid #e67e22;
}

.dispatch-table td.excel {
	/*min-width: 165px;*/
	text-align: left;
}

.dispatch-table td.excel.td-1-input {
	width: 165px;
}

.dispatch-table td.excel label {
	width: 100%;
	text-align: right;
	padding-top: 5px;
}

.dispatch-table td.excel input[type='text'] {
	min-width: 150px;
}

.dispatch-table td.excel input[type='checkbox'], .dispatch-table td.excel input[type='radio']
	{
	text-align: left;
	margin: 0 10px;
}

.dispatch-table td.excel, .dispatch-table th.excel {
	border: 1px solid #e67e22;
	padding: 0 0;
	margin: 0;
}

.dispatch-table th.excel {
	padding: 2px 0;
}

.dispatch-table label {
	padding: 0 5px;
}

.dispatch-table td.excel input, .dispatch-table td.excel textarea {
	border: 0px;
	border-radius: 0;
	padding: 0 0 0 8px;
}

.dispatch-table td.excel textarea {
	padding: 5px;
}

.dispatch-table .input-group-addon {
	border-radius: 0;
	border: 1px solid #eee;
	margin: 0;
}

.dispatch-table td.input-td {
	background-color: #FFFFFF;
}

.dispatch-table input[type=button].add-row, .dispatch-table input[type=button].delete-row
	{
	margin: 5px;
	padding: 0;
}

.dispatch-table div.input-group input[type=number] {
	border-right: 1px solid #e67e22;
}

.dispatch-table div.weight-group .btn, .date-picker-div .input-group-addon {
	background-color: rgba(230, 126, 34, 0.05);
	border-color: #E4DCD4;
	border-radius: 0px;
	color: #5C5B60;
	font-weight: 600;
	border-left: 1px solid #e67e22;
	margin-left: 0;
}

.dispatch-table div.weight-group .btn .caret {
	border-top: 4px solid #000000;
}

.dispatch-table td.excel .input-group {
	width: 100%
}

.dispatch-table td.excel-100 {
	width: 100px;
}

.dispatch-table td.excel-200 {
	width: 200px;
}

.dispatch-table td.excel-100 .weight-group button {
	width: 100%;
	text-align: right;
}

.dispatch-table .input-group-btn:last-child>.btn, .dispatch-table .input-group-btn:last-child>.btn-group
	{
	background: #FFFFFF;
	margin: 0px;
	border: 0px;
}

.dispatch-table .dispatch-table td.center-input {
	text-align: center;
}

.dispatch-table tfoot th.excel {
    padding: 2px 5px;
}

input[name="length"], input[name="width"], input[name="thickness"], input[name="qty"] {
	width: 115px;
}

#input-table .weight-group button{
	max-width: 115px;
    overflow: hidden;
    text-overflow: ellipsis;
}

.dispatch-table tr.main-row, #input-table tr.main-row {
	background-color: #FFFFFF;
}

</style>
<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Port Purchase Order</h3>
	</div>
</div>
<div>
	<html:form enctype="multipart/form-data" action="/port-purchase-order"
		method="post">
		<div class="container">
  
  
  <ul class="nav nav-pills">
    <li class="active"><a data-toggle="pill" href="#home">Home</a></li>
    <li><a data-toggle="pill" href="#menu1">Menu 1</a></li>

  </ul>
  
  <div class="tab-content">
    <div id="home" class="tab-pane fade in active">
      <h3>HOME</h3>
      <div class="row">
			<div class="col-md-6">
				<table class="table table-responsive dispatch-table">
					<tr>
						<td class='excel' colspan="1"><label for="customerName">Customer Name</label></td>
						<td class='excel' colspan="5"><input type="text" name="customerName"
							placeholder="" class="form-control" /></td>
						
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="brokerName">Broker Name</label></td>
						<td class='excel' colspan="5"><input type="text"
							name="brokerName" placeholder=""
							class="form-control" /></td>
						
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="brokerage">Brokerage</label></td>
						<td class='excel' colspan='7'>
							<div class='input-group'>
								<input type='number' step='0.001' placeholder='Brokerage' min='0'
									value='' name=brokerage class='form-control'>
								<div class='input-group-btn weight-group'>
									<input type='hidden' name='brokerageUnit' value='Per MT' />
									<button type='button' class='btn btn-default dropdown-toggle'
										data-toggle='dropdown' aria-expanded='false'>
										Per MT <span class='caret'></span>
									</button>
									<ul class='dropdown-menu dropdown-menu-right' role='menu'>
										<li onclick='btnGroupChange(this);'><a>Per MT</a></li>
										<li onclick='btnGroupChange(this);'><a>Percentage</a></li>
									</ul>
								</div>
							</div>
						</td>
					</tr>
					
					<tr>
						<td class='excel' colspan="1"><label for="deliveryAddress">Delivery Address</label></td>
						<td class='excel' colspan="5"><input type="text"
							name="deliveryAddress" placeholder="" class="form-control" /></td>
						
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="consigneeName">Rate (per metric ton)</label></td>
						<td class='excel' colspan="5"><input type="text"
							name="rate" placeholder=""
							class="form-control" /></td>
						
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="excise">Excise
								</label></td>
						<td class='excel excel-100' colspan="5">
							<div class='input-group'>
								<div class='input-group-btn weight-group'>
									<input type='hidden' name='excise' value='Extra' />
									<button type="button" class="btn btn-default dropdown-toggle"
										data-toggle="dropdown" aria-expanded="false"
										style="width: 100%; text-align: right;">
										Extra <span class="caret"></span>
									</button>
									<ul class='dropdown-menu dropdown-menu-right' role='menu'>
										<li onclick='btnGroupChange(this);'><a>Extra 12.5%</a></li>
										<li onclick='btnGroupChange(this);'><a>Extra 12.5% and 4%</a></li>
										<li onclick='btnGroupChange(this);'><a>include 12.5%</a></li>
										<li onclick='btnGroupChange(this);'><a>include 12.5% and 4%</a></li>
									</ul>
								</div>
							</div>
						</td>
						
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="tax">Tax
								</label></td>
						<td class='excel excel-100' colspan="5">
							<div class='input-group'>
								<div class='input-group-btn weight-group'>
									<input type='hidden' name='tax' value='Extra' />
									<button type="button" class="btn btn-default dropdown-toggle"
										data-toggle="dropdown" aria-expanded="false"
										style="width: 100%; text-align: right;">
										Extra <span class="caret"></span>
									</button>
									<ul class='dropdown-menu dropdown-menu-right' role='menu'>
										<li onclick='btnGroupChange(this);'><a>Extra 12.5%</a></li>
										<li onclick='btnGroupChange(this);'><a>Extra 12.5% and 4%</a></li>
										<li onclick='btnGroupChange(this);'><a>include 12.5%</a></li>
										<li onclick='btnGroupChange(this);'><a>include 12.5% and 4%</a></li>
									</ul>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="transport">Transport
								</label></td>
						<td class='excel excel-100' colspan="5">
							<div class='input-group'>
								<div class='input-group-btn weight-group'>
									<input type='hidden' name='transport' value='Extra' />
									<button type="button" class="btn btn-default dropdown-toggle"
										data-toggle="dropdown" aria-expanded="false"
										style="width: 100%; text-align: right;">
										Extra <span class="caret"></span>
									</button>
									<ul class='dropdown-menu dropdown-menu-right' role='menu'>
										<li onclick='btnGroupChange(this);'><a>Extra 12.5%</a></li>
										<li onclick='btnGroupChange(this);'><a>Extra 12.5% and 4%</a></li>
										<li onclick='btnGroupChange(this);'><a>include 12.5%</a></li>
										<li onclick='btnGroupChange(this);'><a>include 12.5% and 4%</a></li>
									</ul>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="paymentterms">Payment Terms
								</label></td>
						<td class='excel excel-100' colspan="5">
							<div class='input-group'>
								<div class='input-group-btn weight-group'>
									<input type='hidden' name='paymentterms' value='Extra' />
									<button type="button" class="btn btn-default dropdown-toggle"
										data-toggle="dropdown" aria-expanded="false"
										style="width: 100%; text-align: right;">
										Extra <span class="caret"></span>
									</button>
									<ul class='dropdown-menu dropdown-menu-right' role='menu'>
									<li onclick='btnGroupChange(this);'><a>Extra 12.5%</a></li>
										<li onclick='btnGroupChange(this);'><a>Extra 12.5% and 4%</a></li>
										<li onclick='btnGroupChange(this);'><a>include 12.5%</a></li>
										<li onclick='btnGroupChange(this);'><a>include 12.5% and 4%</a></li>
									</ul>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
    </div>
    <div id="menu1" class="tab-pane fade">
      <h3>Menu 1</h3>
      <div class="row">
			<div class="col-md-6">
			fgdhg
			</div>
			<div class="col-md-6">
			xfbx
			</div>
			</div>
    </div>
    <div class="row">
			<div class="col-md-12">
			hh
			</div>
			</div>
    </div>
  </div>
</div>
	</html:form>
</div>

