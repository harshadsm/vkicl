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
					
				</table>
			</div>
			<div class="col-md-4">
				<table class="table table-responsive">
					<tr>
						<td class="form-label"><label for="grade">Material
								Grade</label></td>
						
							<td><html:select property="grade"
								styleId="grade" styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:optionsCollection property="gradeList" />
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
							<th>ID</th>
							<th>Date of Inward</th>
							<th>Make </th>
							<th>Mill Name</th>
							<th>Grade</th>
							<th>Thickness</th>
							<th>Width</th>
							<th>Length</th>
							<th>Quantity</th>
							<th>Sec Wt. </th>
							<th>actual Wt </th>
							<th>Heat No. </th>
							<th>Plate no. </th>
							<th>Location</th>
							<th>MTC Download</th>
							<th></th>
						</tr>
					</thead>
					<tbody id="details-tbody">
						<logic:iterate id="report" name="StockReportForm"
							property="reportList">
							<tr data-method="updateStockReport"
								id='row-<c:out value="${report.id}" />'>
								<td data-type="hidden" data-name="id"  id="stockId_${report.id}"><c:out
										value="${report.id}"/></td>
										<td data-type="text" data-name="vehicleDate"><c:out
										value="${report.vehicleDate}" /></td>
										<td data-type="text" data-name="make"><c:out
										value="${report.make}" /></td>
								<td data-type="text" data-name="millName"><c:out
										value="${report.millName}" /></td>
								
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
										<td data-type="number" data-step="0.001" data-name="actualWt"><c:out
										value="${report.actualWt}" /></td>
										<td data-type="text" data-name="heatNo"><c:out
										value="${report.heatNo}" /></td>
								<td data-type="text" data-name="plateNo"><c:out
										value="${report.plateNo}" /></td>
								<td data-type="text" data-name="location" >
									<div id="stock-item-location-${report.id}">
										<c:out value="${report.location}" />
									</div>
									<div id="stock-item-location-shifting-dropdown-${report.id}">
									</div>
									
								</td> 
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
									<td>
										<button onclick="showLocationDropdown(${report.id})" id="showLocationDropdownBtn-${report.id}">
											Shift
										</button>
										<button onclick="saveChangedLocation(${report.id})" style="display: none;" id="changeLocationBtn-${report.id}">
											Save
										</button>
										<button onclick="cancelLocationChange(${report.id})"  style="display: none;" id="cancelLocationChange-${report.id}">
											Cancel
										</button>
										
									</td>
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
							<th>Total</th>
							<th id="qtyTotal"></th>
							<th id="secWtTotal"></th>
							<th id="actualWtTotal"></th>
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
				
			</logic:notEmpty>
		</div>
	</div>
	<div id="locationListDropdownTemplate" style="display:none;" >
		<select id="locationListDropdownSelectTemplate">
		<logic:iterate id="location" name="StockReportForm"
							property="locationList">
			<option value="${location.value}">${location.value}</option>				
		</logic:iterate>
			<tr></tr>
		</select>
	</div>

	<script>
function updateStockBal(recordId){
		console.log(recordId);
		var locationValue = $("#location_"+recordId).val();
		var stockIdValue = $("#stockId_"+recordId).html();
		console.log("locationValue = "+locationValue);
		console.log("stockIdValue = "+stockIdValue);
		
		$.ajax({ 
	        type: 'GET', 
	        url: './stockBalDetailsJsonServlet', 
	         data:{ stock_id:stockIdValue , location:locationValue},
	        dataType:'json',
	        success:  function(response){
	        	bootbox.alert("Record updated successfully!");
	        }
	        
		
	});
	
	
}
	</script>
	<script type="text/javascript">
					
					
					function downloadMTC(material_id){
						var url = "./download?material_id="+material_id;
						$('#downloadFrame').remove();
						$('body').append('<iframe id="downloadFrame" style="display:none"></iframe>');
						$('#downloadFrame').attr('src', url);
					}
					</script>
					