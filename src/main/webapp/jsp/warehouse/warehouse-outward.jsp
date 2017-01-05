<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c" %>

<script type="text/javascript">

	var id = 1, row = {}, row_id = 0, current_row_id = "", globalJson = {};

	function fetchWarehouseOutwardDetails() {
		var dispatchNo = $("[name='dispatchNo']").val();

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

	function resetWarehouseOutwardForm() {
		$("#details-tbody").html('');
		$("#hidden-div").html('');
		$(".details-container").hide();
		$(".after-result-1").hide();
		row_id = 0; 
		current_row_id = "";
		globalJson = {}
		resetForm();
 		addRow();
	}
	
	function refreshSecWtRow(){
 		$(".main-row").each(function(){
 			calcSecWtRow(this.id);
 		});
		applyTotalCalc();
	}

	function addRow() {
		var str = "<tr id='row-"+row_id+"' class='main-row'><td class='row-id'><input type='hidden' name='row' value='"+row_id+"'>"+row_id+"</td>"
				+ "<td><input type='hidden' name='stockId' value=''><input type='hidden' name='location' value=''><input type='hidden' name='subQty' value=''><input  type='hidden' name='availableQty' value=''  /><input type='hidden' name='dispatchDetailsID' value=''><input readonly type='text' name='millName' value='' class='form-control' /></td>"
				+ "<td><input readonly type='text' name='make' value='' class='form-control' /></td>"
				
				+ "<td><input readonly type='text' name='grade' value='' class='form-control' /></td>"
				+ "<td><input readonly type='number' step='0.001' min='0' value='' name='thickness' onchange='calcSecWtRow(\"row-"+ row_id+ "\");' onblur='calcSecWtRow(\"row-"+ row_id+ "\");' class='form-control' /></td>"				
				+ "<td><input readonly type='number' step='1' min='0' value='' name='width' onchange='calcSecWtRow(\"row-"+ row_id+ "\");' onblur='calcSecWtRow(\"row-"+ id	+ "\");' class='form-control' /></td>"
				+ "<td><input readonly type='number' step='1' min='0' value='' name='length' onchange='calcSecWtRow(\"row-"+ row_id+ "\");' onblur='calcSecWtRow(\"row-"+ row_id+ "\");' class='form-control' /></td>"
				+ "<td><input readonly type='number' step='1' min='0' value=''  name='qty' onchange='calcSecWtRow(\"row-"+ row_id+ "\");' onblur='calcSecWtRow(\"row-"+ row_id+ "\");' class='form-control' /></td>"
				+ "<td><div class='input-group'><input readonly type='number' step='0.001' min='0' readonly value='' name='secWt' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input readonly type='hidden' name='secWtUnit' value='TON' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' disabled aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='rowBtnGroupChange(this);calcSecWtRow(\"row-"+ row_id+ "\");'><a>TON</a></li><li onclick='rowBtnGroupChange(this);calcSecWtRow(\"row-"+ row_id+ "\");'><a>KG</a></li></ul></div></div></td>"
				+ "<td class='td-excel-pop-btn'><button data-toggle='modal' href='#hidden-div-"+row_id+"' class='pop-btn' onclick='loadLocation("+row_id+");return false;'><span class='glyphicon glyphicon-new-window'></span></button></td>"
				+ "<!--td><input readonly type='button' class='btn-danger delete-row' onclick='deleteMainRow(\"row-"+ row_id + "\");' value='-' /></td-->"
				+ "</tr>";
		$("#details-tbody").append(str);
		refreshSecWtRow();
		
		//Important Trace 1
		addHiddenDiv("hidden-div-" + row_id);
		
		
		current_row_id = "row-" + row_id;
		row_id = row_id + 1;
	}

	function addHiddenDiv(hidden_div_container_id) {

		var str = "<div id='"+hidden_div_container_id+"' class='modal fade' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>"
				+ "<div class='modal-dialog'><div class='modal-content'><div class='modal-div'>"
				+ "<button type='button' class='bootbox-close-button close' data-dismiss='modal' aria-hidden='true'>×</button>"
				+ "<table class='table table-responsive'>"
				+ "<thead><tr><th>Stock Id</th><th>Mill Name</th><th>Thickness</th><th>Width</th><th>Length</th>"
				+ "<th>Location</th><th>Heat No.</th><th>Plate No.</th><th>Quantity Available</th><th>Quantity</th></tr></thead><tbody></tbody>"
				+ "<tfoot></tfoot></table></div></div></div></div></div>"
		$("#hidden-div").append(str);
		refreshSecWtRow();
		
		// fillArray('materialType', 'query.unique.materialType');
		// fillArray('millName', 'query.unique.millName');
		// fillArray('make', 'query.unique.make');
		// fillArray('grade', 'query.unique.grade');
		// fillArray('wlocation', 'query.unique.wlocation');
	}
	
	function loadLocation(id){		
		
		console.log($("#hidden-div-"+id).find("[name='subQty']").length);
		
		if($("#hidden-div-"+id).find("[name='subQty']").length <= 0) {
			
			var dispatchNo = $("[name='dispatchNo']").val();
			console.log("DispatchNO = "+dispatchNo);
			
			var millName = $("#row-" + id + " [name=millName]").val();
			var make = $("#row-" + id + " [name=make]").val();
			var grade = $("#row-" + id + " [name=grade]").val();
			var length = $("#row-" + id + " [name=length]").val();
			var width = $("#row-" + id + " [name=width]").val();
			var thickness = $("#row-" + id + " [name=thickness]").val();			
			
			showLoader();			
			var params = "&millName=" + millName + "&make=" + make + "&grade=" + grade + "&length=" + length + "&width=" + width + "&thickness=" + thickness + "&dispatchNo=" + dispatchNo + "&dispatchDetailRowId=" + id;			
			$.ajax({
				url : "./json?method=fetchWarehouseLocationDetails" + params,
				success : function(json, status, response) {
					try{
						json = JSON.parse(json);
					}catch(e){
						 return false;
					}
					$("#hidden-div-" + id + " tbody").html("");
					var count = 0;
					if (null != json.resultList)
						count = json.resultList.length;					
					var str = "", tfoot = "", qtyAvailableTotal = 0;				
					for (var i = 0; i < count; i++) {
						
						var result = json.resultList[i];
						var prevSelectedQty = 0;
						/* if(json.previouslySelectedQtyFromWarehouseOutwardTemp){
							console.log("Previously selected qty");
							console.log(json.previouslySelectedQtyFromWarehouseOutwardTemp);
							prevSelectedQty = json.previouslySelectedQtyFromWarehouseOutwardTemp;
						} */
						
						if(result.qty){
							console.log("Previously selected qty = "+result.qty);
							prevSelectedQty = result.qty;
						}
						
						
						str = str + "<tr><td><input type='text' readonly='readonly' value='" + result.stockId+ "' name='stockId' class='form-control' />"
						    + "<td><input type='text' readonly='readonly' value='" + result.millName+ "' name='millname' class='form-control' />"
						    +"<td><input type='text' readonly='readonly' value='" + result.thickness + "' name='thickness' class='form-control' />"
						    +"<td><input type='text' readonly='readonly' value='" + result.width + "' name='width' class='form-control' />"
						    +"<td><input type='text' readonly='readonly' value='" + result.length + "' name='length' class='form-control' />"
						    + "<td><input type='text' readonly='readonly' value='" + result.location + "' name='location' class='form-control' /></td>"
						    + "<td><input type='text' readonly='readonly' value='" + result.heatNo + "' name='heatNo' class='form-control' /></td>"
						    + "<td><input type='text' readonly='readonly' value='" + result.plateNo + "' name='plateNo' class='form-control' /></td>"
							+ "<td><input type='text' value='" + result.availableQty + "' readonly='readonly' name='availableQty' class='form-control' /></td>"
							+ "<td><input type='number' step='1' placeholder='Quantity' min='0' max='"+result.availableQty+"' name='subQty' onChange='fillDetails();' class='form-control' value='"+prevSelectedQty+"'/></td></tr>"
							qtyAvailableTotal = qtyAvailableTotal + result.availableQty; 
					}
					if(count == 0) {
						str = str + "<tr><th colspan='3'><br/>No Stock Available for this combination<br/><br/></th><tr>"
					}
					else {
						tfoot = tfoot + "<tr><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th style='text-align: right;'>Total</th><th>"+qtyAvailableTotal+"</th><th id='total'></th><tr>";
					}
					$("#hidden-div-" + id + " tbody").html(str);
					$("#hidden-div-" + id + " tfoot").html(tfoot);
					applyNumericConstraint();
					hideLoader();
				},
				error : function() {
					$(".details-container").hide();
					hideLoader();
					bootbox.alert("Unable to fetch Required Details");
				}
			});
		}		
	}

	function deleteMainRow(id) {
		if ($("#" + id).parent().find("tr.main-row").length > 1){
			$("#" + id).remove();
			if((id.match(/-/g) || []).length == 1){
				var num = id.substring(id.lastIndexOf("-") + 1);
				$("#hidden-div-" + num).remove();
			}
		}
		else
			bootbox.alert("Cannot Delete Last Row");
		applyTotalCalc();
	}

	$(document).ready(function() {
		if($("[name='dispatchNo']").val() == "--")
			resetWarehouseOutwardForm();
		else
			fetchWarehouseOutwardDetails();
			
	});

	function validateForm() {
	    fillDetails();
		var msg = "";
	    var valid = true;
	    
// 	    $("tr.main-row [name=subQty]").each(function(row, txt) {
// 	        var result = 0;

// 	        if ($(txt).val() == "") {
// 	        	msg = "Please select Location / Quantity for row " + (row+1);
// 	            valid = false;
// 	            return;
// 	        }
// 	        else 
// 	        {
// 	            res = $(txt).val().split(",");
// 	            for (i = 0; i < res.length; i++) {
// 	                var num = parseInt(res[i]);
// 	                if (!isNaN(num))
// 	                    result = result + num;
// 	            }
// 	            if(result != parseInt($(txt).parent().parent().find("[name=qty]")[0].value)){
// 	            	msg = msg + "<br />Please select Correct Quantity for row " + (row+1);
// 	            	msg = msg + "<br />Quantity Required = " + $(txt).parent().parent().find("[name=qty]")[0].value;
// 	            	msg = msg + "<br />Quantity Selected = " + result + "<br />";	            	
// 		            valid = false;
// 		            return;
// 	            }
// 	        }
// 	    });
// 	    if (!valid) {
// 	        bootbox.alert(msg);
// 	        return false;
// 	    }
	    return commonSubmit();
	}
	
	// Harshad Important Trace #2
	function fillDetails(){
		$("#details-tbody tr").each(function(i, tr){
			var id = $(tr).find("[name='row']")[0].value;
			var txtSubQty = $(tr).find("[name='subQty']")[0];
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

		if(json.pending != "Pending")
			bootbox.alert("This order is already " + json.pending, function(){
				resetWarehouseOutwardForm();
			});
		
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
			<div class="col-md-4">
				<table class="table table-responsive">					
					<tr>
						<td class="form-label"><label for="dispatchNo">Dispatch Order No.</label></td>
						<!-- td><input type="number" min="0" name="dispatchNo" class="form-control" /></td-->
						<td><html:select property="dispatchNo" styleClass="form-control">
							<html:optionsCollection property="dispatchNoList"/>
						</html:select>
						</td>
						<td><input type='button' id="fetch-details" class="btn btn-default" value="Fetch" onclick="fetchWarehouseOutwardDetails();" /></td>
					</tr>
					<!--tr class=" after-result-1">
						<td class="form-label"><label for="actWt">Actual Weight</label></td>					
						<td colspan="2">
							<div class="input-group">
								<input type="number" step="0.001" placeholder="Actual Weight"
									min="0" value="" name="actWt" class="form-control"
									aria-label="...">
								<div class="input-group-btn weight-group">
									<input type="hidden" name="actWtUnit" value="TON" />
									<button type="button" class="btn btn-default dropdown-toggle"
										data-toggle="dropdown" aria-expanded="false">
										TON <span class="caret"></span>
									</button>
									<ul class="dropdown-menu dropdown-menu-right" role="menu">
										<li onclick="rowBtnGroupChange(this);"><a>TON</a></li>
										<li onclick="rowBtnGroupChange(this);"><a>KG</a></li>
									</ul>
								</div>
							</div>
						</td>
					</tr-->
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<table class="table table-responsive table-excel after-result-1" id="main-table">
					<thead>
						<tr>
							<th>No</th>
							<th>Mill Name</th>
							<th>Make</th>
							<th>Grade</th>
							<th>Thickness</th>
							<th>Width</th>
							<th>Length</th>
							<th>Quantity</th>
							<th>Section Weight</th>
							
							<th class="margin-5-sides">Location</th>
						</tr>
					</thead>
					<tbody id="details-tbody">
					</tbody>
				</table>
			</div>
		</div>
		<div class="row after-result-1">
			<div class="col-md-12">
				<input type="button" value="Reset" onclick="resetForm();"
					class="btn pull-left">
				<input type="button" class="btn pull-right"	onclick="return validateForm();" value="Submit">
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