<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c"%>

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

.dispatch-table div.weight-group .btn, .date-picker-div .input-group-addon
	{
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

#input-table .weight-group button {
	max-width: 115px;
	overflow: hidden;
	text-overflow: ellipsis;
}
</style>
<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Dispatch Order Report Details</h3>
	</div>
</div>
<div>
	<html:form enctype="multipart/form-data"
		action="/warehouse-dispatch-details-report" method="post">
		<div class="row">
			<div class="col-md-12">
				<table class="table table-responsive dispatch-table">
					<tr>
						<td class='excel' colspan="1"><label for="poNo">PO
								No. &amp; Dated</label></td>
						<td class='excel'colspan="5"><html:text disabled="true"
								property="poNo" styleClass="form-control" /></td>
						<td class='excel' colspan="1"><label for="date">Date</label></td>
						<td class='excel' colspan="1">
							<div class="input-group date date-picker-div">
								<html:text disabled="true" property="date"
									styleClass="form-control" />
								<span class="input-group-addon"><span
									class="glyphicon-calendar glyphicon"></span></span>
							</div>
						</td>
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="vehicleNumber">Vehicle
								Number</label></td>
						<td class='excel'colspan="5"><html:text disabled="true"
								property="vehicleNumber" styleClass="form-control" /></td>
						<td class='excel' colspan="1"><label for="handleBy">Handle
								By</label></td>
						<td class='excel' colspan="1"><html:text disabled="true"
								property="handleBy" styleClass="form-control" /></td>
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="transporterName">Transporter
								Name</label></td>
						<td class='excel' colspan="7"><html:text disabled="true"
								property="transporterName" styleClass="form-control" /></td>
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="transport">Transport
								</label></td>
						<td class='excel excel-100' colspan="1">
							<div class='input-group'>
								<div class='input-group-btn weight-group'>
									<html:hidden property='transport' value='Extra' />
									<button type="button" class="btn btn-default dropdown-toggle" disabled="disabled"
										data-toggle="dropdown" aria-expanded="false"
										style="width: 100%; text-align: right;">
										<c:out value="${WarehouseDispatchDetailsReportForm.transport}" /> 
										<span class="caret"></span>
									</button>
									<ul class='dropdown-menu dropdown-menu-right' role='menu'>
										<li onclick='btnGroupChange(this);'><a>Extra</a></li>
										<li onclick='btnGroupChange(this);'><a>Included</a></li>
										<li onclick='btnGroupChange(this);'><a>Nil</a></li>
									</ul>
								</div>
							</div>
						</td>
						<td class='excel excel-100' colspan="1">
							<div class='input-group'>
								<div class='input-group-btn weight-group'>
									<html:hidden property='to' value='To Party' />
									<button type='button' class='btn btn-default dropdown-toggle' disabled="disabled"
										data-toggle='dropdown' aria-expanded='false'>
										<c:out value="${WarehouseDispatchDetailsReportForm.to}" />
											<span class='caret'></span>
									</button>
									<ul class='dropdown-menu dropdown-menu-right' role='menu'>
										<li onclick='btnGroupChange(this);'><a>To Party</a></li>
										<li onclick='btnGroupChange(this);'><a>To Us</a></li>
									</ul>
								</div>
							</div>
						</td>
						<td class='excel excel-200' colspan="1"><label for="transportRate">Transport
								Rate</label></td>
						<td class='excel excel-200' colspan="1"><input type='number' step='0.001' placeholder='Amount' min='0' disabled="disabled"
									value='<c:out value="${WarehouseDispatchDetailsReportForm.transportRate}"/>' name=transportRate class='form-control'></td>
						<td class='excel excel-100' colspan='1'>
							<div class='input-group'>
								<div class='input-group-btn weight-group'>
									<input type='hidden' name='transportUnit' value='Per MT' />
									<button type="button" class="btn btn-default dropdown-toggle" disabled="disabled"
										data-toggle="dropdown" aria-expanded="false"
										style="width: 100%; text-align: right;">
										<c:out value="${WarehouseDispatchDetailsReportForm.transportUnit}" />
										<span class="caret"></span>
									</button>
									<ul class='dropdown-menu dropdown-menu-right' role='menu'>
										<li onclick='btnGroupChange(this);'><a>Per MT</a></li>
										<li onclick='btnGroupChange(this);'><a>Fix</a></li>
									</ul>
								</div>
							</div>
						</td>
						<td class='excel' colspan="1"><label for="lumsum">Additional Charges</label></td>
						<td class='excel' colspan="1"><html:text disabled="true"
							property="lumsum" styleClass="form-control" /></td>
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="buyerName">Buyer
								Name</label></td>
						<td class='excel'colspan="5"><html:text disabled="true"
								property="buyerName" styleClass="form-control" /></td>
						<td class='excel' colspan="1"><label for="paymentTerms">Payment
								Terms</label></td>
						<td class='excel' colspan="1"><html:text disabled="true"
								property="paymentTerms" styleClass="form-control" /></td>
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="consigneeName">Consignee
								Name</label></td>
						<td class='excel'colspan="5"><html:text disabled="true"
								property="consigneeName" styleClass="form-control" /></td>
						<td class='excel' colspan="1"><label for="loadingCharges">Loading
								Charges</label></td>
						<td class='excel' colspan='1'>
							<div class='input-group'>
								<input disabled type='number' step='0.001'
									placeholder='Loading Charges' min='0'
									value='<c:out value="${WarehouseDispatchDetailsReportForm.loadingCharges}"/>'
									name=loadingCharges class='form-control'>
								<div class='input-group-btn weight-group'>
									<html:hidden property='loadingChargesUnit' value='Per MT' />
									<button disabled type='button'
										class='btn btn-default dropdown-toggle' data-toggle='dropdown'
										aria-expanded='false'>
										<c:out
											value="${WarehouseDispatchDetailsReportForm.loadingChargesUnit}" />
										<span class='caret'></span>
									</button>
									<ul class='dropdown-menu dropdown-menu-right' role='menu'>
										<li onclick='btnGroupChange(this);'><a>Per MT</a></li>
										<li onclick='btnGroupChange(this);'><a>Fix</a></li>
									</ul>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td class='excel td-1-input' colspan="1"><label
							for="brokerName">Broker Name</label></td>
						<td class='excel'colspan="5"><html:text disabled="true"
								property="brokerName" styleClass="form-control" /></td>
						<td class='excel td-1-input' colspan="1"><label
							for="cuttingCharges">Cutting Charges</label></td>
						<td class='excel' colspan='1'>
							<div class='input-group'>
								<input disabled type='number' step='0.001'
									placeholder='Cutting Charges' min='0'
									value='<c:out value="${WarehouseDispatchDetailsReportForm.cuttingCharges}"/>'
									name=cuttingCharges class='form-control'>
								<div class='input-group-btn weight-group'>
									<html:hidden property='cuttingChargesUnit' value='Per MT' />
									<button disabled type='button'
										class='btn btn-default dropdown-toggle' data-toggle='dropdown'
										aria-expanded='false'>
										<c:out
											value="${WarehouseDispatchDetailsReportForm.cuttingChargesUnit}" />
										<span class='caret'></span>
									</button>
									<ul class='dropdown-menu dropdown-menu-right' role='menu'>
										<li onclick='btnGroupChange(this);'><a>Per MT</a></li>
										<li onclick='btnGroupChange(this);'><a>Fix</a></li>
									</ul>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="brokerage">Brokerage</label></td>
						<td class='excel' colspan='7'>
							<div class='input-group'>
								<input disabled type='number' step='0.001'
									placeholder='Brokerage' min='0'
									value='<c:out value="${WarehouseDispatchDetailsReportForm.brokerage}"/>'
									name=brokerage class='form-control'>
								<div class='input-group-btn weight-group'>
									<html:hidden property='brokerageUnit' value='Per MT' />
									<button disabled type='button'
										class='btn btn-default dropdown-toggle' data-toggle='dropdown'
										aria-expanded='false'>
										<c:out
											value="${WarehouseDispatchDetailsReportForm.brokerageUnit}" />
										<span class='caret'></span>
									</button>
									<ul class='dropdown-menu dropdown-menu-right' role='menu'>
										<li onclick='btnGroupChange(this);'><a>Per MT</a></li>
										<li onclick='btnGroupChange(this);'><a>Percentage</a></li>
									</ul>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<table class="table table-responsive dispatch-table"
					id="input-table">
					<thead>
						<tr>
							<th class='excel' colspan="1">Material Make</th>
							<th class='excel' colspan="1">Mill Name</th>
							<th class='excel' colspan="1">Grade</th>
							<th class='excel' colspan="1">Thickness</th>
							<th class='excel' colspan="1">Width</th>
							<th class='excel' colspan="1">Length</th>
							<th class='excel' colspan="1">Quantity</th>
							<th style='display: none;' class='excel' colspan="1">Actual
								Weight</th>
							<th class='excel' colspan="1">Rate</th>
							<th class='excel' colspan="1">Taxes</th>
							<th class='excel' colspan="1">Excise</th>
							<!-- <th class='excel' colspan="1"><input disabled type="button" -->
							<!-- class="btn-success add-row" onClick="addRow();" value="+" /></th> -->
						</tr>
					</thead>
					<tbody id="details-tbody">
						<logic:present name="WarehouseDispatchDetailsReportForm"
							property="resultList">
							<logic:iterate name="WarehouseDispatchDetailsReportForm"
								property="resultList" id="bean">

								<tr class="main-row">
									<%-- <html:hidden property="row" /> --%>
									<td class="excel" colspan="1"><input disabled type="text"
										placeholder="Make" name="make"
										value="<c:out value="${bean.make}" />" class="form-control" /></td>
									<td class="excel" colspan="1"><input disabled type="text"
										placeholder="Mill Name" name="millName"
										value="<c:out value="${bean.millName}" />"
										class="form-control" /></td>
									<td class="excel" colspan="1"><input disabled type="text"
										placeholder="Grade" name="grade"
										value="<c:out value="${bean.grade}" />" class="form-control" /></td>
									<td class="excel" colspan="1"><input disabled
										type="number" step="0.001" placeholder="Thickness" min="0"
										value="<c:out value="${bean.thickness}" />" name="thickness"
										class="form-control" /></td>
									<td class="excel" colspan="1"><input disabled
										type="number" step="1" placeholder="Width" min="0"
										value="<c:out value="${bean.width}" />" name="width"
										class="form-control" /></td>
									<td class="excel" colspan="1"><input disabled
										type="number" step="1" placeholder="Length" min="0"
										value="<c:out value="${bean.length}" />" name="length"
										class="form-control" /></td>
									<td class="excel" colspan="1"><input disabled
										type="number" step="1" placeholder="Quantity" min="0"
										value="<c:out value="${bean.orderedQuantity}" />" name="orderedQuantity"
										class="form-control" /></td>
									<td style="display: none;" class="excel" colspan="1"><div
											class="input-group">
											<input disabled type="number" step="0.001"
												placeholder="Actual Weight" min="0" value="" name="actWt"
												class="form-control">
											<div class="input-group-btn weight-group">
												<html:hidden property="actWtUnit" value="TON" />
												<button disabled type="button"
													class="btn btn-default dropdown-toggle"
													data-toggle="dropdown" aria-expanded="false">
													<c:out value="${bean.actWtUnit}" />
													<span class="caret"></span>
												</button>
												<ul class="dropdown-menu dropdown-menu-right" role="menu">
													<li onclick="btnGroupChange(this);"><a>TON</a></li>
													<li onclick="btnGroupChange(this);"><a>KG</a></li>
												</ul>
											</div>
										</div></td>
									<td class='excel' colspan='1'><div class='input-group'>
											<input disabled type='number' step='0.001' min='0'
												value='<c:out value="${bean.rate}" />' placeholder='Rate'
												name='rate' class='form-control' />
											<div class='input-group-btn weight-group'>
												<input disabled type='hidden' name='rateUnit'
													value='<c:out value="${bean.rateUnit}" />' />
												<button disabled type='button'
													class='btn btn-default dropdown-toggle'
													data-toggle='dropdown' aria-expanded='false'>
													<c:out value="${bean.rateUnit}" />
													<span class='caret'></span>
												</button>
												<ul class='dropdown-menu dropdown-menu-right' role='menu'>
													<li onclick='btnGroupChange(this);'><a>Per MT</a></li>
													<li onclick='btnGroupChange(this);'><a>Fix</a></li>
												</ul>
											</div>
										</div></td>

									<td class="excel" colspan="1"><div class="input-group">
											<div class="input-group-btn weight-group">
												<html:hidden property="taxes" value="VAT 5%" />
												<button disabled type="button"
													class="btn btn-default dropdown-toggle"
													data-toggle="dropdown" aria-expanded="false">
													<c:out value="${bean.taxes}" />
													<span class="caret"></span>
												</button>
												<ul class="dropdown-menu dropdown-menu-right" role="menu">
													<li onclick="btnGroupChange(this);"><a>VAT 5%</a></li>
													<li onclick="btnGroupChange(this);"><a>CST 2%</a></li>
													<li onclick="btnGroupChange(this);"><a>CST 5%</a></li>
													<li onclick="btnGroupChange(this);"><a>F-Form</a></li>
												</ul>
											</div>
										</div></td>
									<td class="excel" colspan="1"><div class="input-group">
											<div class="input-group-btn weight-group">
												<html:hidden property="excise" value="Extra 12.5%" />
												<button disabled type="button"
													class="btn btn-default dropdown-toggle"
													data-toggle="dropdown" aria-expanded="false">
													<c:out value="${bean.excise}" />
													<span class="caret"></span>
												</button>
												<ul class="dropdown-menu dropdown-menu-right" role="menu">
													<li onclick="btnGroupChange(this);"><a>Extra 12.5%</a></li>
													<li onclick="btnGroupChange(this);"><a>Extra 12.5%
															and 4%</a></li>
													<li onclick="btnGroupChange(this);"><a>Included
															12.5%</a></li>
													<li onclick="btnGroupChange(this);"><a>Included
															12.5% and 4%</a></li>
												</ul>
											</div>
										</div></td>
								</tr>
							</logic:iterate>
						</logic:present>
					</tbody>
					<tfoot>
						<tr>
							<th class='excel' colspan="5"></th>
							<th class='excel' colspan="1" style="text-align: right;">Total</th>
							<th class='excel' colspan="1" id="qtyTotal"></th>
							<th class='excel'colspan="5"></th>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<table class="table table-responsive dispatch-table">
					<tr>
						<td class='excel' colspan="1"><label for="mtc">MTC
								Required?</label></td>
						<td class='excel excel-100 input-td center-input' colspan="1"><html:checkbox
								disabled="true" property="mtc" styleId="mtc" /></td>
						<td class='excel' colspan="1"><label for="inspection">Inspection
								Required?</label></td>
						<td class='excel excel-100 input-td center-input' colspan="1"><html:checkbox
								disabled="true" property="inspection" styleId="inspection" /></td>
						<td class='excel' colspan="1"><label for="inspectionCharges">Inspection
								Charges</label></td>
						<td class='excel excel-100 input-td' colspan="1"><html:text disabled="true"
								property="inspectionCharges" styleClass="form-control" /></td>
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="utReport">UT
								Report Required?</label></td>
						<td class='excel input-td center-input' colspan="1"><html:checkbox
								disabled="true" property="utReport" styleId="utReport" /></td>
						<td class='excel' colspan="1"><label for="labReport">Lab
								Report Required?</label></td>
						<td class='excel input-td center-input' colspan="1"><html:checkbox
								disabled="true" property="labReport" styleId="labReport" /></td>
						<td class='excel' colspan="1"><label for="toAcc">To
								Our Acc / Party Acc?</label></td>
						<td class='excel input-td excel-100' colspan="1">
							<div class='input-group'>
								<div class='input-group-btn weight-group'>
									<html:hidden property='toAcc' value='Our Acc' />
									<button disabled type='button'
										class='btn btn-default dropdown-toggle' data-toggle='dropdown'
										aria-expanded='false'>
										<c:out value="${WarehouseDispatchDetailsReportForm.toAcc}" />
										<span class='caret'></span>
									</button>
									<ul class='dropdown-menu dropdown-menu-right' role='menu'>
										<li onclick='btnGroupChange(this);'><a>Our Acc</a></li>
										<li onclick='btnGroupChange(this);'><a>Party Acc</a></li>
									</ul>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="comments">Comments</label></td>
						<td class='excel' colspan="5"><html:textarea disabled="true"
								property="comments" styleClass="form-control"></html:textarea></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<!-- <button value="Reset" onclick="resetForm();" class="btn pull-left"></button> -->
				<!-- <button class="btn pull-right" onclick="return validateForm();"
					value="Submit"></button> -->
			</div>
		</div>
		<html:hidden property="genericListener" value="add" />
	</html:form>
</div>

<script type="text/javascript">
	var id = 1, row = {}, row_id = 1, current_row_id = "";

	function resetWarehouseDispatchDetailsReportForm() {
		$("#details-tbody").html('');
		$(".details-container").hide();
		$("input[type='text']").removeAttr("readOnly");
		$("input[type='text']").removeAttr("disabled");
		resetForm();
		addRow();
	}

	function addRow() {
		var str = "<tr id='row-"+row_id+"' class='main-row'><input type='hidden' property='row' value='"+row_id+"' />"
				+ "<td class='excel' colspan='1'><input disabled type='text' placeholder='Make' name='make' value='' class='form-control' /></td>"
				+ "<td class='excel' colspan='1'><input disabled type='text' placeholder='Mill Name' name='millName' value='' class='form-control' /></td>"
				+ "<td class='excel' colspan='1'><input disabled type='text' placeholder='Grade' name='grade' value='' class='form-control' /></td>"
				+ "<td class='excel' colspan='1'><input disabled type='number' step='0.001' placeholder='Thickness' min='0' value='' name='thickness' class='form-control' /></td>"
				+ "<td class='excel' colspan='1'><input disabled type='number' step='1' placeholder='Width' min='0' value='' name='width' class='form-control' /></td>"
				+ "<td class='excel' colspan='1'><input disabled type='number' step='1' placeholder='Length' min='0' value='' name='length' class='form-control' /></td>"
				+ "<td class='excel' colspan='1'><input disabled type='number' step='1' placeholder='Quantity' min='0' value='' name='qty' class='form-control' /></td>"
				+ "<td style='display: none;' class='excel' colspan='1'><div class='input-group'><input disabled type='number' step='0.001' placeholder='Actual Weight' min='0' value='' name='actWt' class='form-control'><div class='input-group-btn weight-group'><input type='hidden' property='actWtUnit' value='TON' /><button disabled type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);'><a>TON</a></li><li onclick='btnGroupChange(this);'><a>KG</a></li></ul></div></div></td>"
				+ "<td class='excel' colspan='1'><div class='input-group'><input type='number' step='0.001' min='0' value='' placeholder='Rate' name='rate' class='form-control' /><div class='input-group-btn weight-group'><input type='hidden' name='rateUnit' value='Per MT' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>Per MT <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);'><a>Per MT</a></li><li onclick='btnGroupChange(this);'><a>Fix</a></li></ul></div></div></td>"
				+ "<td class='excel' colspan='1'><div class='input-group'><div class='input-group-btn weight-group'><input type='hidden' property='taxes' value='VAT 5%' /><button disabled type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>VAT 5% <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);'><a>VAT 5%</a></li><li onclick='btnGroupChange(this);'><a>CST 2%</a></li><li onclick='btnGroupChange(this);'><a>CST 5%</a></li><li onclick='btnGroupChange(this);'><a>F-Form</a></li></ul></div></div></td>"
				+ "<td class='excel' colspan='1'><div class='input-group'><div class='input-group-btn weight-group'><input type='hidden' property='excise' value='Extra 12.5%' /><button disabled type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>Extra 12.5% <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);'><a>Extra 12.5%</a></li><li onclick='btnGroupChange(this);'><a>Extra 12.5% and 4%</a></li><li onclick='btnGroupChange(this);'><a>Included 12.5%</a></li><li onclick='btnGroupChange(this);'><a>Included 12.5% and 4%</a></li></ul></div></div></td>"
				+ "<td class='excel' colspan='1'><input disabled type='button' class='btn-danger delete-row' onclick='deleteMainRow(\"row-"
				+ row_id + "\");' value='-' /></td></tr>";
		$("#details-tbody").append(str);
		current_row_id = "row-" + row_id;
		row_id = row_id + 1;
		applyTotalCalc();

		fillArray('make', 'query.unique.make');
		fillArray('grade', 'query.unique.grade');
		fillArray('millName', 'query.unique.millName');
	}

	$(document).ready(function() {
		fillArray('transporterName', 'query.unique.transporterName');
		fillArray('vehicleNumber', 'query.unique.vehicleNumber');
		fillArray('buyerName', 'query.unique.buyerName');
		fillArray('consigneeName', 'query.unique.consigneeName');
		fillArray('brokerName', 'query.unique.brokerName');
		// addRow();
	});

	function validateForm() {
		if ("" == getValByFieldName("body", "poNo")) {
			bootbox.alert("Please enter PO Number");
			return false;
		} else if ("" == getValByFieldName("body", "date")) {
			bootbox.alert("Please enter Date");
			return false;
		} else if ("" == getValByFieldName("body", "buyerName")) {
			bootbox.alert("Please enter Buyer Name");
			return false;
		} else if ("" == getValByFieldName("body", "paymentTerms")) {
			bootbox.alert("Please enter Payment Terms");
			return false;
		} else if ("" == getValByFieldName("body", "paymentTerms")) {
			bootbox.alert("Please enter Payment Terms");
			return false;
		} else if ("" != getValByFieldName("body", "brokerName")) {
			if ("" == getValByFieldName("body", "brokerage")) {
				bootbox.alert("Please enter Brokerage");
				return false;
			}
		}

		return commonSubmit();
	}
</script>
