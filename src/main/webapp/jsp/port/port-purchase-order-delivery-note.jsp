<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c" %>

<% 
String dispatchNo=request.getParameter("dispatchNo");
if(dispatchNo == null){
	dispatchNo = (String)request.getAttribute("dispatchNo_2");
}

System.out.println("harhads>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+dispatchNo);
%>
<script type="text/javascript">

var MYDISPATCHNO='<%= dispatchNo %>';
	var id = 1, row = {}, row_id = 0, current_row_id = "", globalJson = {};

	function fetchWarehouseOutwardDetails() {
		
		
		//var dispatchNo = $("[name='dispatchNo']").val();
		
		var dispatchNo=MYDISPATCHNO;
		
		console.log(dispatchNo);
		if ("--" == dispatchNo) {
			bootbox.alert("Please select a valid Dispatch Number");
			$("[name='dispatchNo']").focus();
			resetWarehouseOutwardForm();
			return false;
		}
		showLoader();
		$.ajax({
			url : "./json?method=fetchWarehouseOutwardDetails&dispatchNo=" + dispatchNo,
			success : function(json, status, response) {
				processJSON(json);
			},
			error : function() {
				$(".details-container").hide();
				hideLoader();
				bootbox.alert("Unable to fetch Required Details");
			}
		});

	}

	$(document).ready(function() {
		
		
			fetchWarehouseOutwardDetails();
			
	});

	function validateForm() {
	    fillDetails();
		var msg = "";
	    var valid = true;
	    
	    if ("" == getValByFieldName("body", "vehicleNumber")) {
			bootbox.alert("Please enter vehicle Number");
			return false;
		}
	    
	    //if ("" == getValByFieldName("body", "actWt")) {
			//bootbox.alert("Please enter Actual Weight");
			//return false;
		//}
	    
	    if ("" == getValByFieldName("body", "vehicleDate")) {
			bootbox.alert("Please select vehicle Date");
			return false;
		}
	    
	    
	    
    return commonSubmit();
	}
	
	// Harshad Important Trace #2
	function fillDetails(){
		$("#details-tbody tr").each(function(i, tr){
			var id = $(tr).find("[name='row']")[0].value;
			var txtSubQty = $(tr).find("[name='subQty']")[0];
			var txttotal = $(tr).find("[name='total']")[0];
			var txtLocation = $(tr).find("[name='location']")[0];
			
			var txtstockId = $(tr).find("[name='stockId']")[0];
			var txtavailableQty = $(tr).find("[name='availableQty']")[0];
			
			
			
			var str = "";
			$("#hidden-div-"+id + " [name='stockId']").each(function(){
				str = str + $(this).val() + ",";
			});
			if(str.endsWith(","))
				str = str.substr(0, str.length - 1);
			
			
			txtstockId.value = str;
			
			
			
			
			var str = "";
			$("#hidden-div-"+id + " [name='location']").each(function(){
				str = str + $(this).val() + ",";
			});
			if(str.endsWith(","))
				str = str.substr(0, str.length - 1);
			
			
			txtLocation.value = str;
			
			
			var str = "";
			var qtySelected = 0;
			$("#hidden-div-"+id + " [name='subQty']").each(function(){
				if($.trim($(this).val()) == "")
					$(this).val(0);
				str = str + $(this).val() + ",";
				qtySelected = qtySelected + parseInt($(this).val());
				
				txttotal.value=qtySelected;
			});
			if(str.endsWith(","))
				str = str.substr(0, str.length - 1);
			
			//Harshad Important Trace #2
			txtSubQty.value = str;
			
			
			
			var str = "";
			$("#hidden-div-"+id + " [name='availableQty']").each(function(){
				str = str + $(this).val() + ",";
			});
			if(str.endsWith(","))
				str = str.substr(0, str.length - 1);
			
			
			txtavailableQty.value = str;
			
			$("#hidden-div-"+id + " tfoot th#total").html(qtySelected);
		});		
	}
	
	function processJSON(json) {
		try{
			json = JSON.parse(json);
			globalJson = json;
		}catch(e){
			return false;
		}
		resetWarehouseOutwardForm();

		var count = 0;
		if (null != json.resultList)
			count = json.resultList.length;

		$("[name='dispatchNo']").val(json.id);

// 		if(json.pending != "Pending")
// 			bootbox.alert("This order is already " + json.pending, function(){
// 				resetWarehouseOutwardForm();
// 			});
		
		if (count > 0) {

			$("#details-tbody").html('');
			$("#hidden-div").html('');

			for (var i = 0; i < count; i++) {
				addRow();
				applyNumericConstraint();
				
				var result = json.resultList[i];
				var millName = $("#"+current_row_id).find("[name='millName']")[0];
				millName.value = result.millName;
				$("#"+current_row_id).find("[name='make']")[0].value = result.make;
				$("#"+current_row_id).find("[name='dispatchDetailsID']")[0].value = result.dispatchDetailsID;
				$("#"+current_row_id).find("[name='grade']")[0].value = result.grade;
				$("#"+current_row_id).find("[name='length']")[0].value = result.length;
				$("#"+current_row_id).find("[name='width']")[0].value = result.width;
				$("#"+current_row_id).find("[name='thickness']")[0].value = result.thickness;
				$("#"+current_row_id).find("[name='secWtUnit']")[0].value = result.actWtUnit;
				$("#"+current_row_id).find("[name='secWtUnit']").html(result.actWtUnit + " <span class='caret'></span></button>");
				$("#"+current_row_id).find("[name='qty']")[0].value = result.qty;
				//$("#"+current_row_id).find("[name='qtyAvailable']")[0].value = result.qtyAvailable;
			}
			refreshSecWtRow();
		} else
			bootbox.alert("No Record Found");
		$(".after-result-1").show();
		hideLoader();
	}
</script>

<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Warehouse Outward</h3>
	</div>
</div>
<div>
	<html:form enctype="multipart/form-data" action="/warehouse-outward"
		method="post">
		<div class="row">
			<div class="col-md-10">
			
			<table class="table table-responsive dispatch-table">
					<tr>
						<td class='excel' colspan="1"><label for="ppoNo">PPO Number</label></td>
						<td class='excel' colspan="5"><input type="text" name="ppoNo"
							placeholder="" class="form-control" /></td>
						
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="ppoDate">PPO Date</label></td>
						<td class='excel' colspan="5"><input type="text"
							name="brokerName" placeholder=""
							class="form-control" /></td>
						
					</tr>
					</table>
					</div>
					</div>
					<div class="row">
			<div class="col-md-10">
			<tr>
						<td class='excel' colspan="1"><label for="customerName">Customer Name</label></td>
						<td class='excel' colspan="5"><input type="text" name="customerName"
							placeholder="" class="form-control" /></td>
						
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="deliveryAddress">Delivery address</label></td>
						<td class='excel' colspan="5"><input type="text"
							name="brokerName" placeholder=""
							class="form-control" /></td>
						
					</tr>
			</div>
			</div>
			<div class="row">
			<div class="col-md-10">
			<tr>
						<td class='excel' colspan="1"><label for="paymentTerms">Payment Terms</label></td>
						<td class='excel' colspan="5"><input type="text" name="customerName"
							placeholder="" class="form-control" /></td>
						
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="excise">Excise</label></td>
						<td class='excel' colspan="5"><input type="text"
							name="brokerName" placeholder=""
							class="form-control" /></td>
						
					</tr>
			</div>
			</div>
			<div class="row">
			<div class="col-md-10">
			<tr>
						<td class='excel' colspan="1"><label for="tax">Tax</label></td>
						<td class='excel' colspan="5"><input type="text" name="customerName"
							placeholder="" class="form-control" /></td>
						
					</tr>
					<tr>
						<td class='excel' colspan="1"><label for="transport">Transport</label></td>
						<td class='excel' colspan="5"><input type="text"
							name="brokerName" placeholder=""
							class="form-control" /></td>
						
					</tr>
			</div>
			</div>
		<html:hidden property="genericListener" value="add" />
	</html:form>
	<div class="row">
		<div class="col-md-12">
			<div id="hidden-div">
			</div>
		</div>
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

/* div.input-group input[type=number] {
	border-right: 1px solid #e67e22;
}

div.weight-group .btn, .date-picker-div .input-group-addon {
	background-color: rgba(230, 126, 34, 0.05);
	border-color: #E4DCD4;
	border-radius: 0px;
	color: #5C5B60;
	font-weight: 600;
	border-left: 1px solid #e67e22;
	margin-left: 0;
}

div.weight-group .btn .caret {
	border-top: 4px solid #000000;
}

td .input-group {
	width: 100%
}

.td-100 {
	width: 100px;
}

.td-100 .weight-group button {
	width: 100%;
	text-align: right;
}

.input-group-btn:last-child>.btn, .input-group-btn:last-child>.btn-group
	{
	background: #FFFFFF;
	margin: 0px;
	border: 0px;
}

#page-title {
	width: 100%;
	font-size: 1.6em;
	text-align: center;
	font-weight: bold;
	background-color: rgba(230, 126, 34, 0.05);
	border: 1px solid #e67e22;
	padding: 15px;
} */

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