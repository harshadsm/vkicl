<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.form.PortInwardForm"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c" %>

<script type="text/javascript">
	var id = 1, row = {}, row_id = 1;
	
	function validateForm() {
		return commonSubmit();
	}

	function fetchPortInwardDetails() {
		var vendorName = $("input[name='vendorName']").val();
		var vesselName = $("input[name='vesselName']").val();
		var vesselDate = $("input[name='vesselDate']").val();

		if ("" == vesselDate) {
			bootbox.alert("Please select Vessel Date");
			$("input[name='vesselDate']").focus();
			return false;
		}
		if ("" == vendorName && "" == vesselName) {
			bootbox.alert("Please select either Vendor Name or Vessel Name");
			$("input[name='vendorName']").focus();
			return false;
		}
		showLoader();
		$.ajax({
			url : "./json?vendorName=" + vendorName + "&vesselName="
					+ vesselName + "&vesselDate=" + vesselDate
					+ "&method=fetchPortInwardDetails",
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

	function processJSON(json) {
		resetInwardPackingForm();
		json = JSON.parse(json);

		var count = 0;
		if (null != json.beNo)
			count = json.beNo.length;

		$("input[name='vendorName']").val(json.vendorName);
		$("input[name='vesselName']").val(json.vesselName);
		$("input[name='vesselDate']").val(json.vesselDate);

		if (count > 0) {
			$(".details-container").show();
			$("input[name='vendorName']").attr('disabled', 'disabled');
			$("input[name='vesselName']").attr('disabled', 'disabled');
			$("input[name='vesselDate']").attr('disabled', 'disabled');
			$("input[name='vendorName']").css('background', '#eee');
			$("input[name='vesselName']").css('background', '#eee');
			$("input[name='vesselDate']").css('background', '#eee');

			for (var i = 0; i < count; i++) {
				addRow();
				applyNumericConstraint();
				$("#row-"+(row_id-1)+" input[name='pis']").val(json.pis[i]);
				$("#row-"+(row_id-1)+" input[name='beNo']").val(json.beNo[i]);
				$("#row-"+(row_id-1)+" input[name='materialType']").val(json.materialType[i]);
				$("#row-"+(row_id-1)+" input[name='millName']").val(json.millName[i]);
				$("#row-"+(row_id-1)+" input[name='make']").val(json.make[i]);
				$("#row-"+(row_id-1)+" input[name='grade']").val(json.grade[i]);
				$("#row-"+(row_id-1)+" input[name='desc']").val(json.desc[i]);
				$("#row-"+(row_id-1)+" input[name='beWt']").val(json.beWt[i]);
				$("#row-"+(row_id-1)+" input[name='beWtUnit']").val(json.beWtUnit[i]);
				$("#row-"+(row_id-1)+" input[name='beWtUnit']").next().html(json.beWtUnit[i]);
			}
			refreshSubRows();
		} else
			bootbox.alert("No Record Found");
		hideLoader();

	}

	function resetInwardPackingForm() {
		$("#details-tbody").html('');
		$(".details-container").hide();
		$("input[type='text']").removeAttr("readOnly");
		$("input[type='text']").removeAttr("disabled");
		$("input[name='vendorName']").css('background', '#ffffff');
		$("input[name='vesselName']").css('background', '#ffffff');
		$("input[name='vesselDate']").css('background', '#ffffff');
		resetForm();
	}

	function addRow() {
		var str = "<tr id='row-"+row_id+"' class='main-row'><input type='hidden' name='pis' />"
				+ "<td><input readonly type='text' name='beNo' placeholder='B/E No.' class='form-control' /></td>"
				+ "<td><input readonly type='text' name='materialType' placeholder='Material Type' class='form-control' /></td>"
				+ "<td><input readonly type='text' name='millName' placeholder='Mill Name' class='form-control' /></td>"
				+ "<td><input readonly type='text' name='make' placeholder='Make' class='form-control' /></td>"
				+ "<td><input readonly type='text' name='grade' placeholder='Grade' class='form-control' /></td>"
				+ "<td><input readonly type='text' name='desc'	placeholder='Description' class='form-control' /></td>"
				+ "<td><div class='input-group'><input type='number' step='0.001' min='0' readonly name='beWt' placeholder='B/E Weight' class='form-control' aria-label='...'> <div class='input-group-btn weight-group'> <input type='hidden' name='beWtUnit' value='' /> <button type='button' disabled='disabled' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'> UOM <span class='caret'></span></button> <ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);'><a>TON</a></li> <li onclick='btnGroupChange(this);'><a>KG</a></li></ul></div></div></td>"
				+ "</tr><tr id='row-container-"+row_id+"'><td class='expand-collapse-container' colspan='1'><input type='button' class='btn-info' onclick='$(\"#sub-table-"
				+ row_id
				+ "\").slideToggle(200);' value='[+/-]' /></td><td colspan='5'><table class='table table-responsive sub-table table-excel' style='display: none;' id='sub-table-"+row_id+"'>"
				+ "<thead><tr><th>Thickness</th><th>Width</th><th>Length</th><th>Quantity</th><th>Actual Weight</th>"
				+ "<td><input type='button' class='btn-success add-row' onClick='addSubRow(\"row-container-"
				+ row_id
				+ "\");' value='+' /></td></tr></thead><tbody></tbody></table></td></tr>";
		$("#details-tbody").append(str);
		addSubRow("row-container-" + row_id);
		row_id = row_id + 1;
		refreshSubRows();
	}

	function addSubRow(row_container_id) {
		var num = row_container_id
				.substring(row_container_id.lastIndexOf("-") + 1);
		if (null == row[num] || typeof row[num] === "undefined")
			row[num] = 0;
		else
			row[num] = row[num] + 1;

		var str = "<tr id='row-sub-"+num+"-"+row[num]+"' class='sub-row'><input type='hidden' name='subPis' />"
				+ "<td><input type='number' step='0.001' min='0' name='thickness' placeholder='Thickness' class='form-control' /></td>"
				+ "<td><input type='number' step='1' min='0' name='width' placeholder='Width' class='form-control' /></td>"
				+ "<td><input type='number' step='1' min='0' name='length' placeholder='Length' class='form-control' /></td>"
				+ "<td><input type='number' step='1' min='0' name='qty' placeholder='Quantity' class='form-control' /></td>"
				+ "<td><div class='input-group'><input type='number' step='0.001' min='0' name='actualWt' placeholder='Actual Weight' class='form-control' aria-label='...'><div class='input-group-btn weight-group'><input type='hidden' name='actualWtUnit' value='TON' /><button type='button'class='btn btn-default dropdown-toggle' disabled data-toggle='dropdown' aria-expanded='false'>TON<span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'><li onclick='btnGroupChange(this);'><a>TON</a></li><li onclick='btnGroupChange(this);'><a>KG</a></li></ul></div></div></td>"
				+ "<td><input type='button' class='btn-danger delete-row' onclick='deleteRow(\"row-sub-"
				+ num + "-" + row[num] + "\");' value='-' /></td>" + "</tr>";
		$("#" + row_container_id + " tbody").append(str);
		refreshSubRows();
	}
	
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
			$("#" + id).remove();
		else
			bootbox.alert("Cannot Delete Last Row");
		refreshSubRows();
	}
	
	function submit(){
		return commonSubmit();
	}

	$(document).ready(function() {
		$(".details-container").hide();
		fillArray('vendorName', 'query.unique.vendorName');
		fillArray('vesselName', 'query.unique.vesselName');
	});
</script>
<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Port Inward Details</h3>
	</div>
</div>
<div class="row">
<div >
		<div id="jqgrid">
		<table id="grid"></table>
		<div id="pager"></div>

	</div>
	<!-- <a href="javascript:downloadExcelFile()">Export to Excel</a> -->
	<!-- <input style='margin-top: 10px;' type="button" value="Export to Excel"
		id='excelExport' /> -->
		
</div>
</div>

<script>

$(function() {
	$("#grid").jqGrid(
		{
			url : './portInwardDetailsJsonServlet',
			datatype : 'json',
			mtype : 'GET',
			
			
			colNames : [ 'Code',
							'Name','Channel','Channel Name','Division','Division Name','Address','Credit limit', 'Payment Term', 'DL 20b No','DL 21b No'
					],
					
			colModel : [ {
				name : 'customerCode',
				index : 'customerCode',
				width : 185,
				editable : true,
				editrules : {
					required : true
				},
				editoptions : {
					size : 10
				},
				search:true,
				searchoptions: { sopt:['ge']}
			}, {
				name : 'customerName',
				index : 'customerName',
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
				name : 'distChannel',
				index : 'distChannel',
				width : 60,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false
			}, {
				name : 'distChannelName',
				index : 'distChannelName',
				width : 180,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false

			}, {
				name : 'division',
				index : 'division',
				width : 40,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false
			}, {
				name : 'divName',
				index : 'divName',
				width : 150,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false
			}, {
				name : 'address',
				index : 'address',
				width : 600,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				}
			},{
				name : 'creditLimit',
				index : 'creditLimit',
				width : 100,
				editable : true,
				editrules : {
					required : true
				},
				editoptions : {
					size : 10
				},
				search:false
			}, {
				name : 'payTerms',
				index : 'payTerms',
				width : 55,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false
			},  {
				name : 'drugLicense20bNo',
				index : 'drugLicense20bNo',
				width : 55,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false
			}, {
				name : 'drugLicense21bNo',
				index : 'drugLicense21bNo',
				width : 55,
				editable : false,
				editoptions : {
					readonly : true,
					size : 10
				},
				search:false
			}
			
			],
			postData : {
			},
			rowNum : 20,
			rowList : [ 20, 40, 60 ],
			height : 480,
			autowidth : true,
			rownumbers : true,
			pager : '#pager',
			sortname : 'id',
			viewrecords : true,
			sortorder : "asc",
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
	        		var rowObject = jQuery("#grid").jqGrid('getRowData',ids[i]); 
	        		//console.log(rowObject);
	        		var cust_lnk = "<a href=\"viewCustomer.do?id="+rowObject.customerCode+"\">"+rowObject.customerName+"</a>";
	        		
	        		$("#grid").jqGrid('setRowData',ids[i],{customerName:cust_lnk});
	        		
	        		$("#grid").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false, searchOperators:true, defaultSearch:"cn"});
	        		
	        		$("#grid").jqGrid('setColProp', "address", {searchoptions: { sopt:['cn','eq']}});
	        		
	        		
					
	        		
	        		} }
		});
});		
</script>

