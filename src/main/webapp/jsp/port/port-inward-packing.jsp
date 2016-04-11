<%@page import="vkicl.vo.PortInwardRecordVO"%>
<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.form.PortInwardForm"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c" %>

<%
PortInwardRecordVO vo = (PortInwardRecordVO)request.getAttribute("port_inward_record");
%>

<script type="text/javascript">
	var id = 1, row = {}, row_id = 1, SUB_ROW_COUNTER = 1;
	
	function validateForm() {
		return commonSubmit();
	}

	$(function(){
		addSubRow2();
	});
	
	function refreshSubRows(){
		$(".main-row").each(function(){
			var pis = $(this).find("input[name=pis]")[0].value;
			var unit = $(this).find("input[name=beWtUnit]")[0].value;
			$(this).next().find("input[name=subPis]").each(function(){
				$(this).val(pis);
			});
			$(this).next().find("input[name=actualWtUnit]").each(function(){
				$(this).val(unit);
				$(this).next().html(unit);
			});
		});
		applyNumericConstraint();
		applyTotalCalc();
	}

	function deleteRow(id) {
		if ($("#" + id).parent().find("tr").length > 1)
			{
				$("#" + id).remove();
				recalculateAllChecksums();
			}
		else
			{
				bootbox.alert("Cannot Delete Last Row");
			}
		refreshSubRows();
	}
	
	function submit(){
		return commonSubmit();
	}

	
	
	
	
	// Harshad New Functions
	function addSubRow2() {
		SUB_ROW_COUNTER = SUB_ROW_COUNTER + 1;
		var str = "<tr id='row-sub-"+SUB_ROW_COUNTER+"' class='sub-row'><input type='hidden' name='subPis' />"
				+ "<td><input type='number' step='0.001' min='0' name='thickness' placeholder='Thickness' class='form-control' /></td>"
				+ "<td><input type='number' step='1' min='0' name='width' placeholder='Width' class='form-control' /></td>"
				+ "<td><input type='number' step='1' min='0' name='length' placeholder='Length' class='form-control' /></td>"
				+ "<td><input type='number' step='1' min='0' name='qty' placeholder='Quantity' class='form-control' /></td>"
				+ "<td><div class='input-group'><input type='number' step='0.001' min='0' name='actualWt' placeholder='Actual Weight' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' name='actualWtUnit' value='TON' /><button type='button'class='btn btn-default dropdown-toggle' disabled data-toggle='dropdown' aria-expanded='false'>TON</button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);'><a>TON</a></li><li onclick='btnGroupChange(this);'><a>KG</a></li></ul></div></div></td>"
				+ "<td><input type='button' class='btn-danger delete-row' onclick='deleteRow(\"row-sub-"
				+ SUB_ROW_COUNTER+"\");' value='-' /></td>" + "</tr>";
		$("#port_inward_details_table tbody").append(str);
		
		addOnTabNewRowEventHandler("row-sub-"+SUB_ROW_COUNTER);
		addChecksumEventHandlers("row-sub-"+SUB_ROW_COUNTER);
		refreshSubRows();
		console.log("#row-sub-"+SUB_ROW_COUNTER+" input[name='thickness']");
		$("#row-sub-"+SUB_ROW_COUNTER+" input[name='thickness']").focus();
		
	}
	function addChecksumEventHandlers(trId){
		addQuantityChecksumEventHandler(trId);	
		addActualWeightChecksumEventHandler(trId);	
		addLengthChecksumEventHandler(trId);	
		addThicknessChecksumEventHandler(trId);	
		addWidthChecksumEventHandler(trId);	
	}
	
	function addThicknessChecksumEventHandler(trId){
		$("#"+trId+" input[name='thickness']").change(function(){
			calculateChecksumOfThickness();
		});
	}
	
	function calculateChecksumOfThickness(){
		var checkSum = 0;
		$("input[name='thickness']").each(function(){
			console.log($(this).val());
			checkSum = checkSum + Number($(this).val());
			
		});	
		
		console.log("Checksum = "+checkSum);
		$("#checksum-thickness").text(checkSum);
	}
	
	function addWidthChecksumEventHandler(trId){
		$("#"+trId+" input[name='width']").change(function(){
			calculateChecksumOfWidth();
		});
	}
	
	function calculateChecksumOfWidth(){
		var checkSum = 0;
		$("input[name='width']").each(function(){
			console.log($(this).val());
			checkSum = checkSum + Number($(this).val());
			
		});	
		
		console.log("Checksum = "+checkSum);
		$("#checksum-width").text(checkSum);
	}
	
	function addLengthChecksumEventHandler(trId){
		$("#"+trId+" input[name='length']").change(function(){
			calculateChecksumOfLength();
		});
	}

	function calculateChecksumOfLength(){
		var checkSum = 0;
		$("input[name='length']").each(function(){
			console.log($(this).val());
			checkSum = checkSum + Number($(this).val());
			
		});	
		
		console.log("Checksum = "+checkSum);
		$("#checksum-length").text(checkSum);	
	}
	
	function addQuantityChecksumEventHandler(trId){
		$("#"+trId+" input[name='qty']").change(function(){
			calculateChecksumOfQty();
		});
	}
	
	function calculateChecksumOfQty(){
		var checkSumQty = 0;
		$("input[name='qty']").each(function(){
			console.log($(this).val());
			checkSumQty = checkSumQty + Number($(this).val());
			
		});	
		
		console.log("Checksum = "+checkSumQty);
		$("#checksum-quantity").text(checkSumQty);
	}
	
	
	function addActualWeightChecksumEventHandler(trId){
		$("#"+trId+" input[name='actualWt']").change(function(){
			calculateChecksumOfActualWeight();
		});
	}
	
	function calculateChecksumOfActualWeight(){
		var checkSumQty = 0;
		$("input[name='actualWt']").each(function(){
			console.log($(this).val());
			checkSumQty = checkSumQty + Number($(this).val());
			
		});	
		
		console.log("Checksum = "+checkSumQty);
		$("#checksum-actual-weight").text(checkSumQty);
	}
	
	function addOnTabNewRowEventHandler(trId){
		
		$("#"+trId+" input[name='actualWt']").keydown(function (e){
			
			var code = e.keyCode || e.which;
		    if (code == '9') {
		    	addSubRow2();
		    	return false;
		    }
		});
	}
	
	function recalculateAllChecksums(){
		calculateChecksumOfActualWeight();
		calculateChecksumOfQty();
		calculateChecksumOfLength();
		calculateChecksumOfWidth();
		calculateChecksumOfThickness();
	}
</script>
<div class="row">
	<div class="col-md-12 text-center">
		<h3 class="page-head">Port Inward Details</h3>
	</div>
</div>
<div>
	<html:form action="/port-inward-details" onsubmit="return validateForm();">

		<div class="row details-container">
			
		</div>
		
		<div class="row">
			<div class="col-md-3">
				<div class="panel panel-default">
					<div class="panel-heading">Selected Port Inward Entry</div>
					<div class="panel-body">
						<table class="table">
							
							<tbody id="details-tbody">
								<tr>
									<th>Port Inward #</th><td><%=vo.getId() %></td></tr><tr>
									<th>Vessel Date</th><td><%=vo.getVesselDate() %></td></tr><tr>
									<th>Vessel</th><td><%=vo.getVesselName() %></td></tr><tr>
									<th>Vendor</th><td><%=vo.getVendorName() %></td></tr><tr>
									<th>Material Type</th><td><%=vo.getMaterialType() %></td></tr><tr>
									<th>Mill</th><td><%=vo.getMillName() %></td></tr><tr>
									<th>Make</th><td><%=vo.getMake() %></td></tr><tr>
									<th>Grade</th><td><%=vo.getGrade() %></td></tr><tr>
									<th>Description</th><td><%=vo.getDesc() %></td></tr><tr>
									
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
			<input name="port_inward_id" type="hidden" value="<%=vo.getId() %>" />
			
			<div class="col-md-9">
			<table class="table table-responsive sub-table table-excel" id="port_inward_details_table" >
				<thead>
					<tr>
						<th>Thickness</th>
						<th>Width</th>
						<th>Length</th>
						<th>Quantity</th>
						<th>Actual Weight</th>
						<td><input type='button' class='btn-success add-row' onClick='addSubRow2()' value='+' /></td>
					</tr>
				</thead>
				<tbody>
					
				</tbody>
				<tfoot>
					<tr>
						<td><div id="checksum-thickness"></div></td>
						<td><div id="checksum-width"></div></td>
						<td><div id="checksum-length"></div></td>
						<td><div id="checksum-quantity"></div></td>
						<td>
							
							<div class='input-group'>
							<div id="checksum-actual-weight"></div>
								<div class='input-group-btn weight-group'>
									<button type='button'class='btn btn-default dropdown-toggle pull-right' disabled data-toggle='dropdown' aria-expanded='false'>TON</button>
								</div>
							</div>
						</td>
					</tr>
				</tfoot>
			</table>
			
			</div>
			
		</div>
		
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4 text-center">
				<input type="button" value="Reset"
					onclick="resetInwardPackingForm();" class="btn"> 
				<html:submit styleClass="btn" onclick="return validateForm();" />
			</div>
			<div class="col-md-4"></div>
		</div>
		<html:hidden property="genericListener" value="addDetails" />
	</html:form>
</div>