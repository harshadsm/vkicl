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
							
							<th>Mill Name</th>
							<th>Make</th>
							<th>Material Type</th>
							<th>Grade</th>
							<th>Thickness</th>
							<th>Width</th>
							<th>Length</th>
							<th>Quantity</th>
							
							<th>Location</th>
							
						</tr>
					</thead>
					<tbody id="details-tbody">
						<logic:iterate id="report" name="StockReportForm"
							property="reportList">
							<tr data-method="updateStockReport"
								id='row-<c:out value="${report.id}" />'>
								<td data-type="hidden" data-name="id" class="cell-hide" id="stockId_${report.id}"><c:out
										value="${report.id}"/></td>
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
								
								<td class='excel excel-100' colspan="1">
							 <select class="form-control" id="location_${report.id}" onChange="updateStockBal(${report.id})">
                 <option>Select</option>
                 <logic:iterate id="location" name="StockReportForm"
							property="locationList">
							 <option value="${location.value}" <c:if test="${report.location eq location.value}"> selected </c:if>>${location.label}</option>
							</logic:iterate>
                </select>
						</td>
								
						</logic:iterate>
					</tbody>
					
				</table>
				
			</logic:notEmpty>
		</div>
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

