<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.form.PortOutwardForm"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c" %>



<script type="text/javascript">
	var customer = [], vehicleNumber = [], vesselName = [], grade = [];

	$(document).ready(function() {
		fillArray('customer', 'query.unique.customer');
		fillArray('vehicleNumber', 'query.unique.vehicleNumber');

		if (id == 1)
			addRow();
	});

	function selectRadioText(i) {
		var radio = $($("input[name='destination']")[i]);
		$(radio).click();
		updateHiddenField();
	}

	function updateHiddenField() {
		$("input[name=destination]").each(function(i, radio) {
			if (radio.checked) {
				var textbox = $(radio).parent().next().find(".tt-input")[0];
				var value = $(textbox).val();
				if(typeof value === "undefined"){
					var textbox = $(radio).parent().next();
					var value = $(textbox).val();
				}
				$("input[name='destinationName']").val(value);
			}
		});
	}

	function fillDates(id) {
		var param1 = $("#" + id + " input[name='vesselName']").val();
		var select = $("#" + id + " select[name='vesselDate']");
		var query = "query.combo.dates";
		if (null != param1 && "" != param1) {
			showLoader();
			$.ajax({
				url : "./xml?query=" + query + "&count=1&param1=" + param1,
				success : function(xml, textStatus, response) {
					fillXmlSuccess(xml, select);
					hideLoader();
					fillBeNo(id);
				},
				error : function() {
					bootbox.alert("Unable to fill Autocomplete for "
							+ arrayName);
					hideLoader();
				}
			});

		} else {
			$(select).html("<option value=''>--</option");
			fillBeNo(id);
		}
	}

	function fillBeNo(id) {
		var param1 = $("#" + id + " input[name='vesselName']").val();
		var param2 = $("#" + id + " select[name='vesselDate']").val();
		var select = $("#" + id + " select[name='beNo']");
		var query = "query.combo.beNo";
		if (null != param1 && "" != param1) {
			showLoader();
			$.ajax({
				url : "./xml?query=" + query + "&count=2&param1=" + param1 + "&param2=" + param2,
				success : function(xml, textStatus, response) {
					fillXmlSuccess(xml, select);
					hideLoader();
					fillMaterialType(id);
				},
				error : function() {
					bootbox.alert("Unable to fill Autocomplete for "
							+ arrayName);
					hideLoader();
				}
			});

		} else {
			$(select).html("<option value=''>--</option");
			fillMaterialType(id);
		}
	}

	function fillMaterialType(id) {
		var param1 = $("#" + id + " input[name='vesselName']").val();
		var param2 = $("#" + id + " select[name='vesselDate']").val();
		var param3 = $("#" + id + " select[name='beNo']").val();
		var select = $("#" + id + " select[name='materialType']");
		var query = "query.combo.materialType";
		if (null != param1 && "" != param1) {
			showLoader();
			$.ajax({
				url : "./xml?query=" + query + "&count=3&param1=" + param1 + "&param2=" + param2 + "&param3=" + param3,
				success : function(xml, textStatus, response) {
					fillXmlSuccess(xml, select);
					hideLoader();
					fillGrade(id);
				},
				error : function() {
					bootbox.alert("Unable to fill Autocomplete for "
							+ arrayName);
					hideLoader();
				}
			});

		} else {
			$(select).html("<option value=''>--</option");
			fillGrade(id);
		}
	}

	function fillGrade(id) {
		var param1 = $("#" + id + " input[name='vesselName']").val();
		var param2 = $("#" + id + " select[name='vesselDate']").val();
		var param3 = $("#" + id + " select[name='beNo']").val();
		var param4 = $("#" + id + " select[name='materialType']").val();
		var select = $("#" + id + " select[name='grade']");
		var query = "query.combo.grade";
		if (null != param1 && "" != param1) {
			showLoader();
			$.ajax({
				url : "./xml?query=" + query + "&count=4&param1=" + param1 + "&param2=" + param2 + "&param3=" + param3 + "&param4=" + param4,
				success : function(xml, textStatus, response) {
					fillXmlSuccess(xml, select);
					hideLoader();
				},
				error : function() {
					bootbox.alert("Unable to fill Autocomplete for "
							+ arrayName);
					hideLoader();
				}
			});

		} else
			$(select).html("<option value=''>--</option");
	}

	var id = 1;

	function addRow() {
		var str = "<tr id='row-" + id + "'><td class='vessel-container'><input type='text' placeholder='Vessel Name' value='' onblur='fillDates(\"row-"+ id+ "\");' name='vesselName' class='form-control' /></td>"
				+ "<td><select name='vesselDate' class='form-control' onChange='fillBeNo(\"row-"+ id+ "\");'><option value=''>--</option></select></td>"
				+ "<td><select name='beNo' class='form-control' onChange='fillMaterialType(\"row-"+ id+ "\");'><option value=''>--</option></select></td>"
				+ "<td><select name='materialType' class='form-control' onChange='fillGrade(\"row-"+ id+ "\");'><option value=''>--</option></select></td>"
				+ "<td><select name='grade' class='form-control'><option value=''>--</option></select></td>"
				+ "<td><input type='text' placeholder='Description' value='' name='desc' class='form-control' /></td>"
				+ "<td><input type='number' step='0.001' placeholder='Thickness' min='0' value='' name='thickness' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' class='form-control' /></td>"
				+ "<td><input type='number' step='1' placeholder='Width' min='0' value='' name='width' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id	+ "\");' class='form-control' /></td>"
				+ "<td><input type='number' step='1' placeholder='Length' min='0' value='' name='length' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' class='form-control' /></td>"
				+ "<td><input type='number' step='1' placeholder='Quantity' min='0' value='' name='qty' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' class='form-control' /></td>"
				+ "<td><div class='input-group'><input type='number' step='0.001' placeholder='Section Weight' min='0' readonly value='' name='secWt' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' name='secWtUnit' value='TON' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' disabled aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>TON</a></li><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>KG</a></li></ul></div></div></td>"
				// + "<td><div class='input-group'><input type='number' step='0.001' placeholder='Actual Weight' min='0' value='' name='actualWt' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' onchange='calcSecWtRow(\"row-"+ id+ "\");' onblur='calcSecWtRow(\"row-"+ id+ "\");' name='actualWtUnit' value='TON' /><button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>TON <span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>TON</a></li><li onclick='btnGroupChange(this);calcSecWtRow(\"row-"+ id+ "\");'><a>KG</a></li></ul></div></div></td>"
				+ "<td><input type='button' class='btn-danger delete-row' onclick='deleteRow($(this).parent().parent().attr(\"id\"));' value='-' /></td></tr>";
		$("#details-tbody").append(str);
		id = id + 1;

		fillArray('vesselName', 'query.unique.vesselName');
		fillArray('grade', 'query.unique.grade');
		applyNumericConstraint();
		applyTotalCalc();
	}

	function deleteRow(id) {
		if ($("#details-tbody tr").length > 1)
			$("#" + id).remove();
		else
			bootbox.alert("Cannot Delete Last Row");
		applyTotalCalc();
	}

	function validateForm() {
		updateHiddenField();
		if ("" == getValByFieldName("body", "destinationName")) {
			bootbox.alert("Please enter Destination Name");
			return false;
		} else if ("" == getValByFieldName("body", "vehicleNumber")) {
			bootbox.alert("Please enter Vehicle Number");
			return false;
		} else if ("" == getValByFieldName("body", "vehicleDate")) {
			bootbox.alert("Please enter Vehicle Date");
			return false;
		}
		return commonSubmit();
	}

	function resetOutwardForm() {
		resetForm();
		$('input[name=destinationName]').val('');
		$('select').html('<option>No Dates</option');
		$('[name="warehouse"]').val("Taloja");
	}
</script>
<div>
	<html:form action="/port-outward" onsubmit="return validateForm();">
		<div class="row">
		
			<div class="col-md-2">
				<h3 class="page-head">Port Outward</h3>
			
				<table class="table table-responsive">
					<tr>
						<td>
							<div class="input-group">
								<span class="input-group-addon"> <html:radio
										property="destination" value="warehouse"
										onclick="updateHiddenField();" /></span> <input type="text"
									name="warehouse" class="form-control" value="Taloja" readonly="readonly"
									placeholder="Warehouse Name" onclick="selectRadioText(0);"
									onblur="updateHiddenField();" onchange="updateHiddenField();" />
							</div>
							<div class="or-label">Or</div>
							<div class="input-group">
								<span class="input-group-addon"> <html:radio
										property="destination" value="customer"
										onclick="updateHiddenField();" /></span> <input type="text"
									name="customer" class="form-control"
									placeholder="Customer Name" onclick="selectRadioText(1);"
									onblur="updateHiddenField();" onchange="updateHiddenField();" />
							</div> <html:hidden property="destinationName" />
						</td>
					</tr>
					<tr>
						<td><input type="text" name="vehicleNumber"
								class="form-control" placeholder="Vehicle Number"/></td>
					</tr>
					<tr>
						<td>
							<div class="input-group date date-picker-div">
								<input type="text" name="vehicleDate" class="form-control" placeholder="Vehicle Date"/>
								<span class="input-group-addon"><span
									class="glyphicon-calendar glyphicon"></span></span>
							</div>
						</td>
					</tr>
				</table>
			</div>
			
			<div class="row">
				<div class="col-md-4">
					<div>
						<div id="jqgrid">
							<table id="grid"></table>
							<div id="pager"></div>
						</div>
					</div>
				</div>
				
				<div class="col-md-4">
					<div>
						<div id="packingListTable">
							<table id="packingListGrid"></table>
							<div id="packingListPager"></div>
						</div>
					</div>
				</div>
			</div>

		</div>
		<div class="row">
			<div class="col-md-12">
				<table class="table table-responsive table-form">
					<thead>
						<tr>
							<th>Vessel Name</th>
							<th>Vessel Date</th>
							<th>B/E No.</th>
							<th>Material Type</th>
							<th>Grade</th>
							<th>Description</th>
							<th>Thickness</th>
							<th>Width</th>
							<th>Length</th>
							<th>Quantity</th>
							<th>Section Weight</th>
							<!-- <th>Actual Weight</th> -->
							<th><input type="button" class="btn-success add-row" onClick="addRow();" value="+" /></th>
						</tr>
					</thead>
					<tbody id="details-tbody">
					</tbody>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<input type="button" value="Reset" onclick="resetOutwardForm();"
					class="btn pull-left" />
				<html:submit styleClass="btn pull-right"
					onclick="return validateForm();" />
			</div>
		</div>
		<html:hidden property="genericListener" value="add" />

	</html:form>
</div>


<script>

$(function() {
	$("#grid").jqGrid(
		{
			url : './portInwardDetailsJsonServlet',
			datatype : 'json',
			mtype : 'GET',
			
			
			colNames : [ //'id', 
			             'Date', 'Vessel Name', 
			             // 'Vendor Name', 
			             'Grade' 
			             // ,'Mill Name', 'Make', 'Grade', 'Desc', 'Packing List', 'Inward Dettails Record Count', 'Outward'
					],
					
			colModel : [ 
			             /* {
				name : 'id',
				index : 'id',
				width : 185,
				editable : true,
				editrules : {
					required : true
				},
				editoptions : {
					size : 10
				},
				search:false,
				searchoptions: { sopt:['ge']}
			}, */ {
				name : 'vesselDate',
				index : 'vessel_date',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'vesselName',
				index : 'vessel_name',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				sortable:false,
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},
			/* ,{
				name : 'vendorName',
				index : 'vendor_name',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				sortable:false,
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			} 
			,{
				name : 'materialType',
				index : 'materialType',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false,
				sortable:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			} ,{
				name : 'millName',
				index : 'millName',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false,
				sortable:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'make',
				index : 'make',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				sortable:false,
				search:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},
			*/
			{
				name : 'grade',
				index : 'grade',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				sortable:false,
				search:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},/*{
				name : 'desc',
				index : 'desc',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				sortable:false,
				search:false,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'cn','eq']}
				
			},{
				name : 'actionLink',
				index : 'actionLink',
				width : 150,
				editable : false,
				search:false,
				sortable:false
			},{
				name : 'countOfPortInwardDetailRecords',
				index : 'countOfPortInwardDetailRecords',
				width : 10,
				hidden: true,
				editable : false,
				search:false,
				sortable:false
			},{
				name : 'portOutwardLink',
				index : 'portOutwardLink',
				width : 150,
				editable : false,
				search:false,
				sortable:false
			} */
			
			
			],
			postData : {
			},
			rowNum : 20,
			rowList : [ 20, 40, 60 ],
			height : 280,
			autowidth : true,
			rownumbers : true,
			pager : '#pager',
			sortname : 'vessel_date',
			viewrecords : true,
			sortorder : "desc",
			caption : "Port Inward List",
			emptyrecords : "Empty records",
			loadonce : false,
			loadComplete : function() {

			},
			jsonReader : {
				root : "rows",
				page : "page",
				total : "total",
				records : "records",
				repeatitems : false,
				cell : "cell",
				id : "id"
			},
	        gridComplete: function(){ 
	        	var ids = $("#grid").jqGrid('getDataIDs');
	        	console.log(ids);
	        	for(var i=0;i < ids.length;i++){ 
	        		//Create packing list link
	        		var rowObject = jQuery("#grid").jqGrid('getRowData',ids[i]); 
	        		console.log(rowObject);
	        		var countOfPortInwardDetailRecords = Number(rowObject.countOfPortInwardDetailRecords);
	        		if(countOfPortInwardDetailRecords > 0){
	        			var cust_lnk = "<a href=\"add-port-inward-packing-list.do?id="+rowObject.id+"\"> ("+rowObject.countOfPortInwardDetailRecords+") <span class='glyphicon glyphicon-list'></span></a>";
	        		}else{
	        			var cust_lnk = "<a href=\"add-port-inward-packing-list.do?id="+rowObject.id+"\"><span class='glyphicon glyphicon-pencil'></span></a>";
	        		}
	        		
	        		
	        		//Create outward link
	        		var outward_lnk = "<a href=\"port-outward.do?port_inward_id="+rowObject.id+"\">  <span class='glyphicon glyphicon-pencil'></span></a>";
	        		
	        		/* $("#grid").jqGrid('setRowData',ids[i],{actionLink:cust_lnk});
	        		$("#grid").jqGrid('setRowData',ids[i],{portOutwardLink:outward_lnk});
	        		 */
	        		$("#grid").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false, searchOperators:true, defaultSearch:"cn"});
	        		
	        		$("#grid").jqGrid('setColProp', "address", {searchoptions: { sopt:['cn','eq']}});
	        		
	        		
					
	        		
	        		} },
   	        onSelectRow:function(rowId){
   	        	populatePackingList(rowId);
   	        }
		});
});		






function populatePackingList(rowId){
	console.log(rowId);
	$("#packingListGrid").jqGrid("GridUnload");
	$("#packingListGrid").jqGrid(
		{
			url : './packingListJsonServlet?port_inward_database_id='+rowId,
			datatype : 'json',
			
			mtype : 'POST',
			
			
			colNames : [ 'ID', 'Length', 'Width','Thickness' ],
					
			colModel : [  {
				name : 'portInwardDetailId',
				index : 'portInwardDetailId',
				hidden: true,
				width : 185,
				editable : true,
				editrules : {
					required : true
				},
				editoptions : {
					size : 10
				},
				search:false,
				searchoptions: { sopt:['ge']}
			}, {
				name : 'length',
				index : 'length',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			},{
				name : 'width',
				index : 'width',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			}
			,{
				name : 'thickness',
				index : 'thickness',
				width : 300,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false,
				search:true,
				//searchoptions: { sopt:['eq', 'ne', 'bw', 'bn', 'ew', 'en', 'cn', 'nc', 'nu', 'nn', 'in', 'ni']}
				searchoptions: { sopt:[ 'eq']}
				
			}
			
			
			],
			postData : {
			},
			rowNum : 20,
			rowList : [ 20, 40, 60 ],
			height : 280,
			autowidth : true,
			rownumbers : true,
			multiselect : true,
			pager : '#packingListPager',
			sortname : 'port_inward_detail_id',
			viewrecords : true,
			sortorder : "desc",
			caption : "Packing List for Selected Port Inward Record",
			emptyrecords : "Empty records",
			loadonce : false,
			loadComplete : function() {

			},
			jsonReader : {
				root : "rows",
				page : "page",
				total : "total",
				records : "records",
				repeatitems : false,
				cell : "cell",
				id : "id"
			},
	        gridComplete: function(){ 
	        	var ids = $("#packingListGrid").jqGrid('getDataIDs');
	        	console.log(ids);
	        	for(var i=0;i < ids.length;i++){ 
	        		//Create packing list link
	        		var rowObject = jQuery("#packingListGrid").jqGrid('getRowData',ids[i]); 
	        		console.log(rowObject);
	        		var countOfPortInwardDetailRecords = Number(rowObject.countOfPortInwardDetailRecords);
	        		if(countOfPortInwardDetailRecords > 0){
	        			var cust_lnk = "<a href=\"add-port-inward-packing-list.do?id="+rowObject.id+"\"> ("+rowObject.countOfPortInwardDetailRecords+") <span class='glyphicon glyphicon-list'></span></a>";
	        		}else{
	        			var cust_lnk = "<a href=\"add-port-inward-packing-list.do?id="+rowObject.id+"\"><span class='glyphicon glyphicon-pencil'></span></a>";
	        		}
	        		
	        		
	        		//Create outward link
	        		var outward_lnk = "<a href=\"port-outward.do?port_inward_id="+rowObject.id+"\">  <span class='glyphicon glyphicon-pencil'></span></a>";
	        		
	        		$("#packingListGrid").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false, searchOperators:true, defaultSearch:"cn"});
	        		
	        		$("#packingListGrid").jqGrid('setColProp', "address", {searchoptions: { sopt:['cn','eq']}});
	        		
	        		
					
	        		
	        		} }
		});
}
</script>
