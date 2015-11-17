<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c"%>

<%
	UserInfoVO userInfoVO = (UserInfoVO) session
			.getAttribute(Constants.USER_INFO_SESSION);
%>

<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Stock Report</h3>
	</div>
</div>

<div>
	<html:form action="/stock-report.do">
		<div class="row">
			<div class="col-md-4">
				<table class="table table-responsive">
					<tr>
						<td class="form-label"><label for="millName">Mill
								Name</label></td>
						<td><html:select property="millName" styleId="millName"
								styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:optionsCollection property="millNameList" />
							</html:select></td>
					</tr>
					<tr>
						<td class="form-label"><label for="make">Make</label></td>
						<td><html:select property="make" styleId="make"
								styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:optionsCollection property="makeList" />
							</html:select></td>
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
						<td class="form-label"><label for="location">Location</label></td>
						<td><html:select property="location" styleId="location"
								styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:optionsCollection property="locationList" />
							</html:select></td>
					</tr>
					<tr>
						<td class="form-label"><label for="thickness">Thickness</label></td>
						<td><input type="number" min="0" name="thickness"
							id="thickness" class="form-control"
							value="<c:out value="${StockReportForm.thickness}" />"></td>
					</tr>
				</table>
			</div>
			<div class="col-md-4">
				<table class="table table-responsive">
					<tr>
						<td class="form-label"><label for="grade">Material
								Grade</label></td>
						<td><html:select multiple="true" property="grade" size="10"
								styleId="grade" styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:optionsCollection property="gradeList" />
							</html:select></td>
					</tr>
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

	<logic:notEmpty name="StockReportForm" property="millName">
		<script type="text/javascript">
			// 			var millName = '<c:out value="${StockReportForm.millName}" />';
			// 			var materialType = '<c:out value="${StockReportForm.materialType}" />';
			// 			var location = '<c:out value="${StockReportForm.location}" />';
			// 			var make = '<c:out value="${StockReportForm.make}" />';
			// 			$("[name='millName']").val(millName);
			// 			$("[name='materialType']").val(materialType);
			// 			$("[name='location']").val(location);
			// 			$("[name='make']").val(make);
		</script>
	</logic:notEmpty>

	<div class="row details-container">
		<div class="col-md-12">
			<logic:empty name="StockReportForm" property="reportList">
				<br />
				<h3>No Records Found</h3>
			</logic:empty>
			<logic:notEmpty name="StockReportForm" property="reportList">
				<jsp:include page="../common/report-export.jsp"></jsp:include>
				<table class="table table-responsive table-report tablesorter"
					id="result-table">
					<thead>
						<tr>
							<th class="cell-hide">ID</th>
							<th>Date</th>
							<th>Mill Name</th>
							<th>Make</th>
							<th>Material Type</th>
							<th>Grade</th>
							<th>Thickness</th>
							<th>Width</th>
							<th>Length</th>
							<th>Quantity</th>
							<th>Section Weight</th>
							<th>UOM</th>
							<th>Heat No.</th>
							<th>Plate No.</th>
							<th>Reserved</th>
							<th>Location</th>
							<th>Certificate</th>
							<%
								if (userInfoVO.hasAccess(Constants.Apps.LOCATION_TRANSFER)) {
							%>
							<th class="cell-edit"><span
								class="glyphicon glyphicon-random"></span></th>
							<%
								}
									if (userInfoVO.hasAccess(Constants.Apps.STOCK_RESERVATION)) {
							%>
							<th class="cell-edit"><span class="glyphicon glyphicon-user"></span>
							</th>
							<%
								}
									if (userInfoVO.hasAccess(Constants.Apps.STOCK_DELETE)) {
							%>
							<th class="cell-edit"><span
								class="glyphicon glyphicon-remove"></span></th>
							<%
								}
							%>
						</tr>
					</thead>
					<tbody id="details-tbody">
						<logic:iterate id="report" name="StockReportForm"
							property="reportList">
							<tr data-method="updateStockReport"
								id='row-<c:out value="${report.id}" />'>
								<td data-type="hidden" data-name="id" class="cell-hide"><c:out
										value="${report.id}" /></td>
								<td data-type="text" data-name="vehicleDate"><c:out
										value="${report.vehicleDate}" /></td>
								<td data-type="text" data-name="millName"><c:out
										value="${report.millName}" /></td>
								<td data-type="text" data-name="make"><c:out
										value="${report.make}" /></td>
								<td data-type="text" data-name="materialType"><c:out
										value="${report.materialType}" /></td>
								<td data-type="text" data-name="grade"><c:out
										value="${report.grade}" /></td>
								<td data-type="number" data-step="0.001" data-name="thickness"><c:out
										value="${report.thickness}" /></td>
								<td data-type="number" data-step="1" data-name="width"><c:out
										value="${report.width}" /></td>
								<td data-type="number" data-step="1" data-name="length"><c:out
										value="${report.length}" /></td>
								<td data-type="number" data-step="1" data-name="qty"><c:out
										value="${report.qty}" /></td>
								<td data-type="number" data-step="0.001" data-name="secWt"><c:out
										value="${report.secWt}" /></td>
								<td data-type="text" data-step="1" data-name="secWtUnit"><c:out
										value="${report.secWtUnit}" /></td>
								<td data-type="text" data-name="heatNo"><c:out
										value="${report.heatNo}" /></td>
								<td data-type="text" data-name="plateNo"><c:out
										value="${report.plateNo}" /></td>
								<td data-type="text" data-step="1" data-name="reserved"><c:if
										test="${report.reserved == false}"><!--Not Reserved--></c:if> <c:if
										test="${report.reserved == true}"><!--Reserved for --><c:out
											value="${report.customer}" />
									</c:if></td>
								<td data-type="text" data-name="location"><c:out
										value="${report.location}" /></td>
								<td><c:if test="${report.fileSize > 0}">
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
								<%
									if (userInfoVO.hasAccess(Constants.Apps.LOCATION_TRANSFER)) {
								%>
								<td class="cell-edit"><button name="btnLocation"
										title="Change Location"
										onclick="loadChangeLocation(<c:out value="${report.id}" />);"
										data-toggle="modal" href="#hidden-loc-div" class="pop-btn">
										<span class="glyphicon glyphicon-random"></span>
									</button></td>
								<%
									}
											if (userInfoVO.hasAccess(Constants.Apps.STOCK_RESERVATION)) {
								%>
								<td class="cell-edit"><button name="btnReserveStock"
										title="Reserve Stock" data-toggle="modal"
										href="#hidden-stock-res-div" class="pop-btn"
										onclick="loadReserveStock(<c:out value="${report.id}" />);">
										<span class="glyphicon glyphicon-user"></span>
									</button></td>
								<%
									}
											if (userInfoVO.hasAccess(Constants.Apps.STOCK_DELETE)) {
								%>
								<td class="cell-edit"><button name="btnRemoveStock"
										title="Remove Stock" class="pop-btn"
										onclick="removeStock(<c:out value="${report.id}" />);">
										<span class="glyphicon glyphicon-remove"></span>
									</button></td>
								<%
									}
								%>
							</tr>
						</logic:iterate>
					</tbody>
					<tfoot>
						<tr>
							<th class="cell-hide"></th>
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
							<th id="qtyTotal"></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<%
								if (userInfoVO.hasAccess(Constants.Apps.LOCATION_TRANSFER)) {
							%><th></th>
							<%
								}
									if (userInfoVO.hasAccess(Constants.Apps.STOCK_RESERVATION)) {
							%>
							<th></th>
							<%
								}
									if (userInfoVO.hasAccess(Constants.Apps.STOCK_DELETE)) {
							%><th></th>
							<%
								}
							%>
						</tr>
					</tfoot>
				</table>
				<script type="text/javascript">
					$(".file-size").each(function(){
						$(this).html(formatFileSize($(this).html()));
					});
					
					function downloadMTC(material_id){
						var url = "./download?material_id="+material_id;
						$('#downloadFrame').remove();
						$('body').append('<iframe id="downloadFrame" style="display:none"></iframe>');
						$('#downloadFrame').attr('src', url);
					}
					
					function loadChangeLocation(id){
						var tr = $("#row-"+id);
						var location = $(tr).find("[data-name='location']")[0].innerHTML;
						$("#hidden-loc-div #id").val(id);
						$("#hidden-loc-div #old-loc").val(location);
					}
					
					function loadReserveStock(id){
						$("#hidden-stock-res-div #id").val(id);
					}

					fillArray('txtNewLoc', 'query.unique.location');
					fillArray('customer', 'query.unique.customer');			

					
					function removeStock(id){
						bootbox.confirm("Are you sure you want to delete this stock?", function(flag){
							if(flag){
								var url = "./report?method=deleteStock&id="+id;
								showLoader();
								$.ajax({
									url : url,
									success : function(xml, textStatus, response) {
										if (null != xml && "" != xml) {
											xmlDoc = $.parseXML(xml);
											var message = $(xmlDoc).find("message")[0].innerHTML;
											var status = "";
											if(message.length > 8)
											    status = message.substring(0, 7);
											if (status == "Success") {
												hideLoader();
												bootbox.alert(message.substring(9));
												var tr = $("#row-"+id);
												$(tr).remove();
											} else {
												bootbox.alert(message);
												$("#hidden-loc-div .bootbox-close-button").trigger('click');
												hideLoader();
											}
										}
									},
									error : function() {
										bootbox.alert("Unable to delete stock");
										$("#hidden-loc-div .bootbox-close-button").trigger('click');
										hideLoader();
									}
								});
							}
						});
					}
					
					function submitChangeLocation(){
						var id = $("#hidden-loc-div #id").val();
						var loc = $("#hidden-loc-div #txtNewLoc").val();
						loc = $.trim(loc).toUpperCase();
						if ("" == loc) {
							bootbox.alert("Please enter new Location");
							return false;
						}
						var url = "./report?method=changeStockLocation&id="+id+"&location="+loc;
						showLoader();
						$.ajax({
							url : url,
							success : function(xml, textStatus, response) {
								if (null != xml && "" != xml) {
									xmlDoc = $.parseXML(xml);
									var message = $(xmlDoc).find("message")[0].innerHTML;
									var status = "";
									if(message.length > 8)
									    status = message.substring(0, 7);
									if (status == "Success") {
										hideLoader();
										bootbox.alert(message.substring(9));
										var tr = $("#row-"+id);
										$(tr).find("[data-name='location']")[0].innerHTML = loc;
										$("#hidden-loc-div .bootbox-close-button").trigger('click');
										$("#hidden-loc-div #txtNewLoc").val("");	
									} else {
										bootbox.alert(message);
										$("#hidden-loc-div .bootbox-close-button").trigger('click');
										hideLoader();
									}
								}
							},
							error : function() {
								bootbox.alert("Unable to change stock location");
								$("#hidden-loc-div .bootbox-close-button").trigger('click');
								hideLoader();
							}
						});
						
					}
					
					function submitReserveStock(){
						var id = $("#hidden-stock-res-div #id").val();
						var customer = $("#hidden-stock-res-div #customer").val();
						customer = $.trim(customer).toUpperCase();
						if ("" == customer) {
							bootbox.alert("Please enter customer name");
							return false;
						}
						var url = "./report?method=reserveStock&id="+id+"&customer="+customer;
						showLoader();
						$.ajax({
							url : url,
							success : function(xml, textStatus, response) {
								if (null != xml && "" != xml) {
									xmlDoc = $.parseXML(xml);
									var message = $(xmlDoc).find("message")[0].innerHTML;
									var status = "";
									if(message.length > 8)
									    status = message.substring(0, 7);
									if (status == "Success") {
										hideLoader();
										bootbox.alert(message.substring(9));
										var tr = $("#row-"+id);
										$(tr).find("[data-name='reserved']")[0].innerHTML = "<!--Reserved for -->" + customer;
										$("#hidden-stock-res-div .bootbox-close-button").trigger('click');
										$("#hidden-stock-res-div #customer").val("");
									} else {
										bootbox.alert(message);
										$("#hidden-loc-div .bootbox-close-button").trigger('click');
										hideLoader();
									}
								}
							},
							error : function() {
								bootbox.alert("Unable to reserve stock");
								$("#hidden-loc-div .bootbox-close-button").trigger('click');
								hideLoader();
							}
						});
					}
					
					function submitUnreserveStock(){
						var id = $("#hidden-stock-res-div #id").val();
						
						var url = "./report?method=reserveStock&id="+id+"&customer=";
						showLoader();
						$.ajax({
							url : url,
							success : function(xml, textStatus, response) {
								if (null != xml && "" != xml) {
									xmlDoc = $.parseXML(xml);
									var message = $(xmlDoc).find("message")[0].innerHTML;
									var status = "";
									if(message.length > 8)
									    status = message.substring(0, 7);
									if (status == "Success") {
										hideLoader();
										bootbox.alert(message.substring(9));
										var tr = $("#row-"+id);
										$(tr).find("[data-name='reserved']")[0].innerHTML = "<!--Not Reserved-->";
										$("#hidden-stock-res-div .bootbox-close-button").trigger('click');
									} else {
										bootbox.alert(message);
										$("#hidden-loc-div .bootbox-close-button").trigger('click');
										hideLoader();
									}
								}
							},
							error : function() {
								bootbox.alert("Unable to remove stock reservation");
								$("#hidden-loc-div .bootbox-close-button").trigger('click');
								hideLoader();
							}
						});
					}
				</script>
			</logic:notEmpty>
		</div>
	</div>

	<div id='hidden-loc-div' class='modal fade hidden-modal-div'
		tabindex='-1' role='dialog' aria-labelledby='myModalLabel'
		aria-hidden='true'>
		<div class='modal-dialog'>
			<div class='modal-content'>
				<div class='modal-div'>
					<button type='button' class='bootbox-close-button close'
						data-dismiss='modal' aria-hidden='true'>×</button>
					<input type="hidden" id="id" />
					<table class="table table-responsive">
						<tbody>
							<tr>
								<th>Old Stock Location:</th>
								<th colspan="2"><input type="text" id="old-loc"
									class="form-control" readonly="readonly" /></th>
							</tr>
							<tr>
								<th>New Stock Location:</th>
								<th><input type="text" class="form-control" id="txtNewLoc"
									placeholder="Location" name="txtNewLoc" /></th>
								<th><button class="btn btn-default"
										onclick="submitChangeLocation()">Submit</button></th>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<div id='hidden-stock-res-div' class='modal fade hidden-modal-div'
		tabindex='-1' role='dialog' aria-labelledby='myModalLabel'
		aria-hidden='true'>
		<div class='modal-dialog'>
			<div class='modal-content'>
				<div class='modal-div'>
					<button type='button' class='bootbox-close-button close'
						data-dismiss='modal' aria-hidden='true'>×</button>
					<input type="hidden" id="id" />
					<table class="table table-responsive">
						<tbody>
							<tr>
								<th>Reserved For:</th>
								<th><input type="text" id="customer" name="customer"
									class="form-control" /></th>
								<th><button class="btn btn-default"
										onclick="submitReserveStock()">Reserve</button>
									<button class="btn btn-default"
										onclick="submitUnreserveStock()">Remove Reservation</button></th>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>

<style type="text/css">
.hidden-modal-div th {
	text-align: left;
}

.hidden-modal-div input {
	text-transform: uppercase;
}

.hidden-modal-div #txtNewLoc {
	width: 300px;
}

.ui-front {
	z-index: 9999;
}
</style>