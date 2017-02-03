
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c"%>

<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Port Stock Report</h3>
	</div>
</div>

<div>
	<html:form action="/port-stock-report.do">
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
						<td class="form-label"><label for="vesselName">Vessel
								Name</label></td>
						<td><html:select property="vesselName" styleId="vesselName"
								styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:optionsCollection property="vesselNameList" />
							</html:select></td>
					</tr>
					<tr>
						<td class="form-label"><label for="grade">Material
								Grade</label></td>
						<td><html:select property="grade" styleId="grade"
								styleClass="form-control">
								<html:option value="ALL">ALL</html:option>
								<html:optionsCollection property="gradeList" />
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
			<logic:empty name="PortStockReportForm" property="reportList">
				<br />
				<h3>No Records Found</h3>
			</logic:empty>
			<logic:notEmpty name="PortStockReportForm" property="reportList">
				<jsp:include page="../common/report-export.jsp"></jsp:include>
				<table class="table table-responsive table-report tablesorter"
					id="result-table">
					<thead>
						<tr>
							<th width="0%" class="cell-hide">Port Inward Id</th>
							<th width="0%" class="cell-hide">Port Inward Shipment Id</th>
							<th width="0%" class="cell-hide">Port Inward Detail Id</th>
							<th width="10%">Mill Name</th>
							<th width="10%">Make</th>
							<th width="10%">Type</th>
							<th width="5%">Grade</th>
							<th width="8%">Description</th>
							<th width="7%">Thickness</th>
							<th width="7%">Width</th>
							<th width="7%">Length</th>
							<th width="5%">Quantity</th>
							<th width="5%">PPO Cumulative PCS & Quantity</th>
							<th width="5%">Delivered To Taloja</th>
							<th width="5%">Balance amount to sell</th>
							
						</tr>
					</thead>
					<tbody id="details-tbody">
						<logic:iterate id="report" name="PortStockReportForm"
							property="reportList">
							<tr >
								<td data-type="hidden" data-name="portInwardId" class="cell-hide"><c:out
										value="${report.portInwardId}" /></td>
								<td data-type="hidden" data-name="portInwardShipmentId" class="cell-hide"><c:out
										value="${report.portInwardShipmentId}" /></td>
								<td data-type="hidden" data-name="portInwardDetailId" class="cell-hide"><c:out
										value="${report.portInwardDetailId}" /></td>
								
								<td data-type="text" data-name="millName"><c:out
										value="${report.millName}" /></td>
								<td data-type="text" data-name="make"><c:out
										value="${report.make}" /></td>
								<td data-type="text" data-name="materialType"><c:out
										value="${report.materialType}" /></td>
								<td data-type="text" data-name="grade"><c:out
										value="${report.grade}" /></td>
										<td data-type="text" data-name="desc"><c:out
										value="${report.desc}" /></td>
								<td data-type="number" data-step="0.001" data-name="thickness"><c:out
										value="${report.thickness}" /></td>
								<td data-type="number" data-step="1" data-name="width"><c:out
										value="${report.width}" /></td>
								<td data-type="number" data-step="1" data-name="length"><c:out
										value="${report.length}" /></td>
								<td data-type="number" data-step="1" data-name="qty"><c:out
										value="${report.qty}" /></td>
										<td data-type="number" data-step="1" data-name="ppoQty"><c:out
										value="${report.ppoQty}" /></td>
								<td data-type="number" data-step="1" data-name="deliveredTalojaQty"><c:out
										value="${report.deliveredTalojaQty}" /></td>
										<td data-type="number" data-step="1" data-name="balanceQty"><c:out
										value="${report.balanceQty}" /></td>
							</tr>
						</logic:iterate>
					</tbody>
					<tfoot>
						<tr>
							<th  class="cell-hide"></th>
							<th ></th>
							<th ></th>
							<!--  <th width="10%"></th>-->
							<th ></th>
							<th ></th>
							<th ></th>
							<th ></th>
							<th ></th>
							<th ></th>
							<th ></th>
							<th ></th>
							<th ></th>
							<th width="5%">Total</th>
						
						</tr>
					</tfoot>
				</table>
			</logic:notEmpty>
		</div>
	</div>
</div>