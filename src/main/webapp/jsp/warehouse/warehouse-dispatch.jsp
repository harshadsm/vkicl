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
		<h3 class="page-head">Dispatch Order</h3>
	</div>
</div>
<div>
	<html:form enctype="multipart/form-data" action="/warehouse-dispatch"
		method="post">
		<div class="row">
			<div class="col-md-12">
				<table class="table table-responsive dispatch-table">
					<tr>
						<td class='excel' colspan="1"><label for="poNo">PO
								No. &amp; Dated</label></td>
						<td class='excel' colspan="5"><input type="text" name="poNo"
							placeholder="" class="form-control" /></td>
						<td class='excel' colspan="1"><label for="date">Date</label></td>
						<td class='excel' colspan="1">
							<div class="input-group date date-picker-div">
								<input type="text" name="date" class="form-control" /> <span
									class="input-group-addon"><span
									class="glyphicon-calendar glyphicon"></span></span>
							</div>
						</td>
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="vehicleNumber">Vehicle
								Number</label></td>
						<td class='excel' colspan="5"><input type="text"
							name="vehicleNumber" placeholder=""
							class="form-control" /></td>
						<td class='excel' colspan="1"><label for="handleBy">Handle
								By</label></td>
						<td class='excel' colspan="1"><input type="text"
							name="handleBy" readonly="readonly"
							value="<%=userInfoVO.getUserName()%>" class="form-control" /></td>
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="transporterName">Transporter
								Name</label></td>
						<td class='excel' colspan="7"><input type="text"
							name="transporterName" placeholder=""
							class="form-control" /></td>
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="transport">Transport
								</label></td>
						<td class='excel excel-100' colspan="1">
							<div class='input-group'>
								<div class='input-group-btn weight-group'>
									<input type='hidden' name='transport' value='Extra' />
									<button type="button" class="btn btn-default dropdown-toggle"
										data-toggle="dropdown" aria-expanded="false"
										style="width: 100%; text-align: right;">
										Extra <span class="caret"></span>
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
									<input type='hidden' name='to' value='To Party' />
									<button type='button' class='btn btn-default dropdown-toggle'
										data-toggle='dropdown' aria-expanded='false'>
										To Party <span class='caret'></span>
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
						<td class='excel excel-200' colspan="1"><input type='number' step='0.001' placeholder='Amount' min='0'
									value='' name=transportRate class='form-control'></td>
						<td class='excel excel-100' colspan='1'>
							<div class='input-group'>
								<div class='input-group-btn weight-group'>
									<input type='hidden' name='transportUnit' value='Per MT' />
									<button type="button" class="btn btn-default dropdown-toggle"
										data-toggle="dropdown" aria-expanded="false"
										style="width: 100%; text-align: right;">
										Per MT <span class="caret"></span>
									</button>
									<ul class='dropdown-menu dropdown-menu-right' role='menu'>
										<li onclick='btnGroupChange(this);'><a>Per MT</a></li>
										<li onclick='btnGroupChange(this);'><a>Fix</a></li>
									</ul>
								</div>
							</div>
						</td>
						<td class='excel' colspan="1"><label for="lumsum">Additional Charges</label></td>
						<td class='excel' colspan="1"><input type="text"
							name="lumsum" placeholder="" class="form-control" /></td>
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="buyerName">Buyer
								Name</label></td>
						<td class='excel' colspan="5"><input type="text"
							name="buyerName" placeholder="" class="form-control" /></td>
						<td class='excel' colspan="1"><label for="paymentTerms">Payment
								Terms</label></td>
						<td class='excel' colspan="1"><input type="text"
							name="paymentTerms" placeholder="" class="form-control" /></td>
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="consigneeName">Consignee
								Name</label></td>
						<td class='excel' colspan="5"><input type="text"
							name="consigneeName" placeholder=""
							class="form-control" /></td>
						<td class='excel' colspan="1"><label for="loadingCharges">Loading
								Charges</label></td>
						<td class='excel' colspan='1'>
							<div class='input-group'>
								<input type='number' step='0.001' placeholder='Loading Charges'
									min='0' value='' name=loadingCharges class='form-control'>
								<div class='input-group-btn weight-group'>
									<input type='hidden' name='loadingChargesUnit' value='Per MT' />
									<button type='button' class='btn btn-default dropdown-toggle'
										data-toggle='dropdown' aria-expanded='false'>
										Per MT <span class='caret'></span>
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
						<td class='excel' colspan="5"><input type="text"
							name="brokerName" placeholder="" class="form-control" /></td>
						<td class='excel td-1-input' colspan="1"><label
							for="cuttingCharges">Cutting Charges</label></td>
						<td class='excel' colspan='1'>
							<div class='input-group'>
								<input type='number' step='0.001' placeholder='Cutting Charges'
									min='0' value='' name=cuttingCharges class='form-control'>
								<div class='input-group-btn weight-group'>
									<input type='hidden' name='cuttingChargesUnit' value='Per MT' />
									<button type='button' class='btn btn-default dropdown-toggle'
										data-toggle='dropdown' aria-expanded='false'>
										Per MT <span class='caret'></span>
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
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<table class="table table-responsive dispatch-table" id="input-table">
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
							<th class='excel' colspan="1">GST</th>
							<!-- <th class='excel' colspan="1">Excise</th> -->
							<th class='excel' colspan="1"><input type="button"
								class="btn-success add-row" onClick="addRow();" value="+" /></th>
						</tr>
					</thead>
					<tbody id="details-tbody">
					</tbody>
					<tfoot>
						<tr>
							<th class='excel' colspan="5"></th>
							<th class='excel' colspan="1" style="text-align: right;">Total</th>
							<th class='excel' colspan="1" id="qtyTotal"></th>
							<th class='excel' colspan="5"></th>
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
								property="mtc" styleId="mtc" /></td>
						<td class='excel' colspan="1"><label for="inspection">Inspection
								Required?</label></td>
						<td class='excel excel-100 input-td center-input' colspan="1"><html:checkbox
								property="inspection" styleId="inspection" /></td>
						<td class='excel' colspan="1"><label for="inspectionCharges">Inspection
								Charges</label></td>
						<td class='excel excel-100 input-td' colspan="1"><input
							type="text" id="inspectionCharges" name="inspectionCharges"
							placeholder="" class="form-control" /></td>
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="utReport">UT
								Report Required?</label></td>
						<td class='excel input-td center-input' colspan="1"><html:checkbox
								property="utReport" styleId="utReport" /></td>
						<td class='excel' colspan="1"><label for="labReport">Lab
								Report Required?</label></td>
						<td class='excel input-td center-input' colspan="1"><html:checkbox
								property="labReport" styleId="labReport" /></td>
						<td class='excel' colspan="1"><label for="toAcc">To
								Our Acc / Party Acc?</label></td>
						<td class='excel input-td excel-100' colspan="1">
							<div class='input-group'>
								<div class='input-group-btn weight-group'>
									<input type='hidden' name='toAcc' value='Our Acc' />
									<button type='button' class='btn btn-default dropdown-toggle'
										data-toggle='dropdown' aria-expanded='false'>
										Our Acc <span class='caret'></span>
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
						<td class='excel' colspan="5"><textarea name="comments"
								class="form-control"></textarea></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<input type="button" value="Reset" onclick="resetForm();"
					class="btn pull-left"> <input type="button"
					class="btn pull-right" onclick="return validateForm();"
					value="Submit">
			</div>
		</div>
		<html:hidden property="genericListener" value="add" />
	</html:form>
</div>

<script type="text/javascript">
 	var id = 1, row = {}, row_id = 1, current_row_id = "";

	function resetWarehouseDispatchForm() {
		$("#details-tbody").html('');
		$(".details-container").hide();
		$("input[type='text']").removeAttr("readOnly");
		$("input[type='text']").removeAttr("disabled");
		resetForm();
		addRow();
	}

	function addRow() {
		var str = "<tr id='row-"+row_id+"' class='main-row'><input type='hidden' name='row' value='"+row_id+"'>"
				+ "<td class='excel' colspan='1'><input type='text' placeholder='Make' name='make' value='' class='form-control' /></td>"
				+ "<td class='excel' colspan='1'><input type='text' placeholder='Mill Name' name='millName' value='' class='form-control' /></td>"
				+ "<td class='excel' colspan='1'><input type='text' placeholder='Grade' name='grade' value='' class='form-control' /></td>"
				+ "<td class='excel' colspan='1'><input type='number' step='0.001' placeholder='Thickness' min='0' value='' name='thickness' class='form-control' /></td>"
				+ "<td class='excel' colspan='1'><input type='number' step='1' placeholder='Width' min='0' value='' name='width' class='form-control' /></td>"
				+ "<td class='excel' colspan='1'><input type='number' step='1' placeholder='Length' min='0' value='' name='length' class='form-control' /></td>"
				+ "<td class='excel' colspan='1'><input type='number' step='1' placeholder='Quantity' min='0' value='' name='qty' class='form-control' /></td>"
				+ "<td style='display: none;' class='excel' colspan='1'><div class='input-group'><input type='number' step='0.001' placeholder='Actual Weight' min='0' value='' name='actWt' class='form-control'><div class='input-group-btn weight-group'><input type='hidden' name='actWtUnit' value='TON' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);'><a>TON</a></li><li onclick='btnGroupChange(this);'><a>KG</a></li></ul></div></div></td>"
				+ "<td class='excel' colspan='1'><div class='input-group'><input type='number' step='0.001' min='0' value='' placeholder='Rate' name='rate' class='form-control' /><div class='input-group-btn weight-group'><input type='hidden' name='rateUnit' value='Per MT' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>Per MT <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);'><a>Per MT</a></li><li onclick='btnGroupChange(this);'><a>Fix</a></li></ul></div></div></td>"

				//+ "<td class='excel' colspan='1'><div class='input-group'><div class='input-group-btn weight-group'><input type='hidden' name='taxes' value='GST 18%' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>GST 18% <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);'><a>GST 18%</a></li></ul></div></div></td>"
				+ "<td class='excel excel-100' ><div class='input-group'><div class='input-group-btn weight-group'><input type='hidden' name='excise' value='Extra 18%' /><button type='button' class='btn btn-default dropdown-toggle'data-toggle='dropdown' aria-expanded='falsestyle='width: 100%; text-align: right;'>Extra 18%<span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);'><a>Extra 18%</a></li><li onclick='btnGroupChange(this);'><a>Include 18%</a></li></ul></div></div></td>"
				
				//+ "<td class='excel' colspan='1'><div class='input-group'><div class='input-group-btn weight-group'><input type='hidden' name='excise' value='Extra 12.5%' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>Extra 12.5% <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);'><a>Extra 12.5%</a></li><li onclick='btnGroupChange(this);'><a>Extra 12.5% and 4%</a></li><li onclick='btnGroupChange(this);'><a>Included 12.5%</a></li><li onclick='btnGroupChange(this);'><a>Included 12.5% and 4%</a></li></ul></div></div></td>"
				+ "<td class='excel' colspan='1'><input type='button' class='btn-danger delete-row' onclick='deleteMainRow(\"row-"
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
		addRow();
	});

	function validateForm() {
		
		if ("" == getValByFieldName("body", "poNo")) {
			bootbox.alert("Please enter PO Number");
			return false;
		} 
		if ("" == getValByFieldName("body", "date")) {
			bootbox.alert("Please enter Date");
			return false;
		} 
		if ("" == getValByFieldName("body", "buyerName")) {
			bootbox.alert("Please enter Buyer Name");
			return false;
		} 
		if ("" == getValByFieldName("body", "paymentTerms")) {
			bootbox.alert("Please enter Payment Terms");
			return false;
		} 
		if ("" != getValByFieldName("body", "brokerName")) {
			if ("" == getValByFieldName("body", "brokerage")) {
				bootbox.alert("Please enter Brokerage");
				return false;
			}
		} 
		if ("" == getValByFieldName("body", "loadingCharges")) {
			bootbox.alert("Please enter Loading Charges");
			return false;
		} 
		if ("" == getValByFieldName("body", "cuttingCharges")) {
			bootbox.alert("Please enter Cutting Charges");
			return false;
		} 
		if ("Nil" != getValByFieldName("body", "transport")) {
			if ("" == getValByFieldName("body", "transportRate")) {
				bootbox.alert("Please enter Transport Rate");
				return false;
			}
		}		
		var make = $("[name='make']");
		for(var i=0; i<make.length; i++){
			if($.trim(make[i].value) == ""){
				bootbox.alert("Please enter Material Make");
				return false;
			}
		}		
		var millName = $("[name='millName']");
		for(var i=0; i<millName.length; i++){
			if($.trim(millName[i].value) == ""){
				bootbox.alert("Please enter Mill Name");
				return false;
			}
		}		
		var grade = $("[name='grade']");
		for(var i=0; i<grade.length; i++){
			if($.trim(grade[i].value) == ""){
				bootbox.alert("Please enter Grade");
				return false;
			}
		}		
		var thickness = $("[name='thickness']");
		for(var i=0; i<thickness.length; i++){
			if($.trim(thickness[i].value) == "" || $.trim(thickness[i].value) <= 0){
				bootbox.alert("Please enter proper Thickness");
				return false;
			}
		}		
		var width = $("[name='width']");
		for(var i=0; i<width.length; i++){
			if($.trim(width[i].value) == "" || $.trim(width[i].value) <= 0){
				bootbox.alert("Please enter proper Width");
				return false;
			}
		}		
		var length = $("[name='length']");
		for(var i=0; i<length.length; i++){
			if($.trim(length[i].value) == "" || $.trim(length[i].value) <= 0){
				bootbox.alert("Please enter proper Length");
				return false;
			}
		}		
		var qty = $("[name='qty']");
		for(var i=0; i<qty.qty; i++){
			if($.trim(qty[i].value) == "" || $.trim(qty[i].value) <= 0){
				bootbox.alert("Please enter proper Quantity");
				return false;
			}
		}		
		var rate = $("[name='rate']");
		for(var i=0; i<rate.rate; i++){
			if($.trim(rate[i].value) == "" || $.trim(rate[i].value) <= 0){
				bootbox.alert("Please enter proper Rate");
				return false;
			}
		} 
		if ("" == getValByFieldName("body", "inspectionCharges")) {
			bootbox.alert("Please enter Inspection Charges");
			return false;
		}
		
		return commonSubmit();
	}
</script>
