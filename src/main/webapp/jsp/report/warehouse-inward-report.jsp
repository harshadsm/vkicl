<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.form.PortOutwardForm"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c"%>

<script type="text/javascript">
var noProcess = true;

function downloadMTC(material_id){
	var url = "./download?material_id="+material_id;
	$('#downloadFrame').remove();
	$('body').append('<iframe id="downloadFrame" style="display:none"></iframe>');
	$('#downloadFrame').attr('src', url);
}

function uploadMTCRow(warehouse_inward_detail_id) {
    $("#uploadForm [name='warehouse_inward_detail_id']").val(warehouse_inward_detail_id);
}

function uploadMTC() {
    if ($("[name='file']").val() != "") {
        var formData = new FormData($("[name='uploadForm']")[0]);
        showLoader();
        if (noProcess) {
            noProcess = false;
            $.ajax({
                url: './upload?warehouse_inward_detail_id='+$("#uploadForm [name='warehouse_inward_detail_id']").val(),
                type: 'POST',
                xhr: function() {
                    var myXhr = $.ajaxSettings.xhr();
                    if (myXhr.upload) {
                        myXhr.upload.addEventListener('progress',
                            progressHandlingFunction, false);
                    }
                    return myXhr;
                },
                success: function() {
                    hideLoader();
                    bootbox.alert("File Uploaded Successfully", function() {
                        noProcess = true;
                        fetchReport();
                    });
                },
                error: function() {
                    hideLoader();
                    bootbox.alert("File Upload Failed", function() {
                        noProcess = true;
                    });
                },
                data: formData,
                cache: false,
                contentType: false,
                processData: false
            });
        }
    } else {
        bootbox.alert("Please select a file for uploading");
    }
}

function progressHandlingFunction(e) {
    if (e.lengthComputable) {
        $("#progressbar").progressbar({
            value: e.loaded,
            max: e.total
        });
    }
}
</script>

<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Warehouse Inward Report</h3>
	</div>
</div>
<div>
	<html:form action="/warehouse-inward-report.do">
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
					<tr>
						<td class="form-label"><label for="receivedFrom">Received
								From</label></td>
						<td><html:select property="receivedFrom"
								styleId="receivedFrom" styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:optionsCollection property="receivedFromList" />
							</html:select></td>
					</tr>
					<tr>
						<td class="form-label"><label for="vesselName">Vessel
								Name</label></td>
						<td><html:select property="vesselName" styleId="vesselName"
								styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:optionsCollection property="vesselNameList" />
							</html:select></td>
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
					<tr>
						<td class="form-label"><label for="materialType">Material
								Type</label></td>
						<td><html:select property="materialType"
								styleId="materialType" styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:optionsCollection property="materialTypeList" />
							</html:select></td>
					</tr>
					<tr>
						<td class="form-label"><label for="grade">
								Grade</label></td>
						<td><html:select property="grade"
								styleId="grade" styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:optionsCollection property="gradeList" />
							</html:select></td>
					</tr>
					<!-- tr>
						<td class="form-label"><label for="vesselDate">Vessel
								Date</label></td>
						<td><html:select property="vesselDate" styleId="vesselDate"
								styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:optionsCollection property="vesselDateList" />
							</html:select></td>
					</tr-->
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

	<logic:notEmpty name="WarehouseInwardReportForm" property="vesselName">
		<script type="text/javascript">
			var vesselName = '<c:out value="${WarehouseInwardReportForm.vesselName}" />';
			var vesselDate = '<c:out value="${WarehouseInwardReportForm.vesselDate}" />';
			var receivedFrom = '<c:out value="${WarehouseInwardReportForm.receivedFrom}" />';
			var fromDate = '<c:out value="${WarehouseInwardReportForm.fromDate}" />';
			var toDate = '<c:out value="${WarehouseInwardReportForm.toDate}" />';
			var materialType = '<c:out value="${WarehouseInwardReportForm.materialType}" />';
			$("[name='vesselName']").val(vesselName);
			$("[name='vesselDate']").val(vesselDate);
			$("[name='receivedFrom']").val(receivedFrom);
			$("[name='fromDate']").val(fromDate);
			$("[name='toDate']").val(toDate);
			$("[name='materialType']").val(materialType);
		</script>
	</logic:notEmpty>

	<div class="row details-container">
		<div class="col-md-12">
			<logic:empty name="WarehouseInwardReportForm" property="reportList">
				<br />
				<h3>No Records Found</h3>
			</logic:empty>
			<logic:notEmpty name="WarehouseInwardReportForm"
				property="reportList">
				<jsp:include page="../common/report-export.jsp"></jsp:include>
				<table class="table table-responsive table-report" id="result-table">
					<thead>
						<tr>
							<th >ID</th>
							<th>Date of Inward </th>
							<th>Vehicle No.</th>
							<th>Vehicle Date</th>
							<th>Vendor Name</th>
							<th>Vessel Name</th> 
							<!-- <th>Vessel Date</th> -->
							<!--<th>B/E No.</th>-->
							<th>Type</th>
							<th>Mill Name</th>
							<th>Make</th>
							<th>Grade</th>
							<th>Thickness</th>
							<th>Width</th>
							<th>Length</th>
							<th>Quantity</th>
							<th>Section Weight</th>
							<th>Actual Weight</th>
							<th>Heat No</th>
							<th>Plate No</th>
							<th>Location</th>
							<!-- 
							<th colspan="2" style="text-align: center;">MTC upload</th>
						 	-->
							<!-- <th class="cell-edit">Edit</th> -->
						</tr>
					</thead>
					<tbody id="details-tbody">
						<logic:iterate id="report" name="WarehouseInwardReportForm"
							property="reportList">
							<tr data-method="updateWarehouseInwardReport">
								<td data-name="id" ><c:out
										value="${report.id}" /></td>
										<td data-type="text" data-name="VehicleDate"><c:out
										value="${report.createDt}" /></td>
								<td data-type="text" data-name="vehicleNumber"><c:out
										value="${report.vehicleNumber}" /></td>
								<td data-type="text" data-name="vehicleDate"><c:out
										value="${report.vehicleDate}" /></td>
								<td data-type="text" data-name="vendorName"><c:out
										value="${report.vendorName}" /></td>
								<td data-type="text" data-name="vesselName"><c:out value="${report.vesselName}" /></td> 
								<%-- <td><c:out value="${report.vesselDate}" /></td> --%>
								<%--<td data-type="text" data-name="beNo"><c:out
										value="${report.beNo}" /></td>--%>
								<td data-type="text" data-name="materialType"><c:out
										value="${report.materialType}" /></td>
								<td data-type="text" data-name="millName"><c:out
										value="${report.millName}" /></td>
								<td data-type="text" data-name="make"><c:out
										value="${report.make}" /></td>
								<td data-type="text" data-name="grade"><c:out
										value="${report.grade}" /></td>
								<td data-type="number" data-name="thickness" data-step="0.001"><c:out
										value="${report.thickness}" /></td>
								<td data-type="number" data-name="width"><c:out
										value="${report.width}" /></td>
								<td data-type="number" data-name="length"><c:out
										value="${report.length}" /></td>
										<td data-type="number" data-name="qty"><c:out
										value="${report.qty}" /></td>
								<td data-type="number" data-name="secWt" data-step="0.001"><c:out
										value="${report.secWt}" /></td>
										<td data-type="number" data-name="actualWt" data-step="0.001"><c:out
										value="${report.actualWt}" /></td>
								<td data-type="text" data-name="heatNo"><c:out
										value="${report.heatNo}" /></td>
								<td data-type="text" data-name="plateNo"><c:out
										value="${report.plateNo}" /></td>
								<td data-type="text" data-name="wlocation" ><c:out
										value="${report.wlocation}" /></td>
								<!-- 
								<td class="cell-edit" style="text-align: right;"><c:if
										test="${report.fileSize > 0}">
										<button title='<c:out value="${report.fileName}" />'
											onclick='downloadMTC(<c:out value="${report.materialId}" />)'>
											<span class="file-size"><c:out
													value="${report.fileSize}" /></span> <span
												class="glyphicon glyphicon-save"></span>
										</button>
									</c:if> <c:if test="${report.fileSize == 0}">
										<center>
											<b>----</b>
										</center>
									</c:if></td>
								 
								<td class="cell-edit">
									<button name="btnUpload" title="Upload" data-toggle='modal'
										href='#hidden-div-form'
										onclick='uploadMTCRow(<c:out
										value="${report.id}" />);'>
										<span class="glyphicon glyphicon-open"></span>
									</button>
								</td>
								-->
								<!-- <td class="cell-edit"><button name="btnEdit" title="Edit"
										onclick="editReportRow(this);">
										<span class="glyphicon glyphicon glyphicon-pencil"></span>
									</button></td> -->
							</tr>
						</logic:iterate>
					</tbody>
					<tfoot>
						<tr>
							<th class="cell-hide"></th>
							<th></th>
							<th></th>
							<th></th>
							<!-- <th>Vessel Name</th> -->
							<!-- <th>Vessel Date</th> -->
							<!--  <th></th>-->
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th>Total</th>
							<th id="qtyTotal">Quantity</th>
							<th id="secWtTotal"></th>
							<th id="actualWtTotal"></th>
							<th></th>
							<th></th>
							<th></th>
						</tr>
					</tfoot>
				</table>
			</logic:notEmpty>
		</div>
	</div>
</div>
<div id="hidden-div">
	<div id="hidden-div-form" class='modal fade' tabindex='-1'
		role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>
		<div class='modal-dialog'>
			<div class='modal-content'>
				<div class='modal-div'>
					<button type="button" class="bootbox-close-button close"
						data-dismiss="modal" aria-hidden="true">×</button>
					<form enctype="multipart/form-data" name="uploadForm"
						id="uploadForm">
						<table class="table table-responsive" id="upload-table">
							<tr>
								<th colspan="2">Upload Certificate</th>
							</tr>
							<tr>
								<td><input name="file" type="file" /><input
									name="warehouse_inward_detail_id" type="hidden" /></td>
								<td><input type="button" class="btn btn-default pull-right"
									value="Upload" name="upload" onclick="uploadMTC();" /></td>
							</tr>
							<tr>
								<td colspan="2" class="progress-bar-td">
									<div id="progressbar"></div>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>