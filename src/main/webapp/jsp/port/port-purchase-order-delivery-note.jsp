<%@page import="vkicl.vo.DeliveryNoteLineItemVO"%>
<%@page import="vkicl.vo.DeliveryNoteVO"%>
<%@page import="java.util.List"%>
<%@page import="vkicl.vo.PortPurchaseOrderLineItemVO"%>
<%@page import="vkicl.vo.PortPurchaseOrderVO"%>

<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c" %>

<% 
PortPurchaseOrderVO vo = (PortPurchaseOrderVO)request.getAttribute("port_purchase_order_details");
List<PortPurchaseOrderLineItemVO> portPurchaseOrderLineItemVO =(List<PortPurchaseOrderLineItemVO>)request.getAttribute("port_purchase_order_line_items");
List<DeliveryNoteVO> deliveryNotes = (List<DeliveryNoteVO>)request.getAttribute("deliveryNotes");
System.out.print(request.getAttribute("port_purchase_order_line_items"));
%>

<script type="text/javascript">

function validateForm() {
	
	
	if ("" == getValByFieldName("body", "vehicleNumber")) {
		bootbox.alert("Please enter vehicleNumber");
		return false;
	} 

	if(!isValidDeliveryQuantity()){
		return false;
	}
	
		
	return commonSubmit();
		
}

function isValidDeliveryQuantity(){
	var x = true;
	
	var orderedQtyArr = $("[name='orderedQuantity']");
	var deliveryQuantityArr = $("[name='deliveryQuantity']");
	
	$.each(orderedQtyArr, function(i,elem){
		var $elem = $(elem);
		var orderedQty = Number($elem.val());
		var cnt = $elem.attr("data-cnt");
		var deliveryQtyElementId = "deliveryQuantity-"+cnt;
		var deliveryQty = 0;
		try{
			deliveryQty = Number($("#"+deliveryQtyElementId).val());
		}catch(e){
			console.log("There is some problem");
			console.log(e);
		}
		

		if(deliveryQty > orderedQty){
			bootbox.alert("ERROR 2019: Delivery quantity cannot be greater than ordered quantity. Delivery quantity ["+deliveryQty+"] is greater than ordered quantity ["+orderedQty+"]");
			x = false;
			return false;//This is to skip to next iteration
		}
		console.log(orderedQty + " - "+ deliveryQty);
		
	});
	
	return x;
	
}




function deleteDeliveryNoteLineItems(id) {
	bootbox.confirm("Are you sure you want to delete?", function(flag){
		if(flag){
		
			var url = "./report?method=deleteDeliveryNoteLineItems&id="+id;
			showLoader();
			$.ajax({
				url : url,
				success : function(xml, textStatus, response) {
					if (null != xml && "" != xml) {
						xmlDoc = $.parseXML(xml);
						var message = $(xmlDoc).find("message")[0].innerHTML;
					
						if (message == "Success") {
							hideLoader();
							bootbox.alert("Record deleted successfully");
							var tr = $("#row-"+id);
							$(tr).remove();
						} else {
							bootbox.alert(message);
							hideLoader();
						}
					}
				},
				error : function() {
					bootbox.alert("Unable to delete");
					hideLoader();
				}
			});
		}
	});
	}
	
</script> 

<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Port Purchase Order Delivery Note</h3>
	</div>
</div>
<div>

	<html:form action="/delivery-note" method="post">
		<div class="row">
		<div class="col-md-10">
				<div class="panel panel-default">
					<div class="panel-body">
					<table class="table">
							
							<tbody>
							<tr>
									<th>PPO Number</th><td><%=vo.getPpoNo() %>
									<input type="hidden" name='ppoNo' id="ppoNo" value="<%=vo.getPpoNo() %>"/></td>
								
									<th>PPO Date</th><td><%=vo.getPpoDate() %></td>
								</tr>
								<tr>
									<th>Customer Name</th><td><%=vo.getCustName() %></td>
									<th>Delivery address</th>
									<td><input type="text" name="deliveryAddress"
										class="form-control"  value="<%=vo.getDeliveryAddr() %>"/>
									</td>
									
								</tr>
								<tr>
									<th>Payment Terms</th><td><%=vo.getPaymentTerms() %></td>
								
									<th>Excise</th><td><%=vo.getExcise() %></td>
								</tr>
								<tr>
									<th>Tax</th><td><%=vo.getTax() %></td>
								
									<th>Transport</th><td><%=vo.getTransport() %></td>
								</tr>
								<tr>
									<th>Vehicle No.</th>
									<td>
										<input type="text" name="vehicleNumber"
											class="form-control"  placeholder="Vehicle Number"/>
									</td>
								
									
									<th>Vehicle Date</th>
									<td>
										<div class="input-group date date-picker-div" id="datetimepicker1">
											<input type="text" name="vehicleDate" id="vehicleDate" class="form-control"
												data-date-format="DD-MM-YYYY"/>
											
											<span class="input-group-addon"><span
												class="glyphicon-calendar glyphicon"></span></span>
										</div>
									</td>
								</tr>
								<tr>
									<th>Comments</th>
									<td><%=vo.getComments() %></td>
								</tr>
								
							</tbody>
							</table>
						</div>
					</div>
					
		</div>
		</div>
<div class="row">
<div class="col-md-10">
				<h3>Enter quantity to be delivered in this delivery note</h3>
				<table class="table table-responsive table-report" id="delivery-table">
					<thead>
					<tr>
							<th width="5%" 	>No </th>
							<th width="8%">Thickness</th>
							<th width="15%">Width</th>
							<th width="15%">Length</th>
							
							<th width="15%">Material Type</th>
							<th width="15%">Mill Name</th>
							<th width="15%">Ordered Quantity</th>
							<th width="15%">Pending Quantity</th>
							<th width="20%">Delivery Quantity</th>
							
						</tr>
					</thead>
					<tbody id="details-tbody">
					<%
						int cnt = 0;
						for(PortPurchaseOrderLineItemVO record:portPurchaseOrderLineItemVO){
							cnt++;
					%>
					
					<tr id="row-sub-<%=cnt %>" class='sub-row'>
								<td><input disabled id="ppoLineitemNo" name="ppoLineitemNo"  type='label' value="<%=record.getPpoLineItemNo() %>"/>
								<input   type='hidden' name="ppoLineitemNo" id="ppoLineitemNo" value="<%=record.getPpoLineItemNo() %>" />
								</td>
								<td><input  disabled type='label' value="<%=record.getThickness() %>"/></td>
								<td><input  disabled type='label' value="<%=record.getWidth() %>"/></td>
								<td><input disabled  type='label' value="<%=record.getLength() %>"/></td>
								<td><input disabled  type='label' value="<%=record.getMaterialType() %>"/></td>
								<td><input disabled  type='label' value="<%=record.getMillName() %>"/></td>
								<td><input disabled  type='label' value="<%=record.getOrderedQuantity() %>"/>
								<input   type='hidden' name="orderedQuantity" id="orderedQuantity-<%=cnt %>" data-cnt="<%=cnt %>" value="<%=record.getOrderedQuantity() %>" />
								</td>
								<td><input disabled  type='label' value="<%=record.getPendingQuantity() %>"/></td>
								<td><input number digits="" type="number"  name="deliveryQuantity" id="deliveryQuantity-<%=cnt %>" value="<%=record.getDeliveryQuantity() %>"/></td>
							</tr>
					<% } %>
						
					</tbody>
					</table>
					</div>
</div>
<div class="row">
			<div class="col-md-10">
				
				<html:submit styleClass="btn pull-right"
					onclick="return validateForm()" />
			</div>
		</div>
		<html:hidden property="genericListener" value="addDetails" />

	</html:form>
	
	
</div>



<div class="row">
<div class="col-md-6">
<div>
	<%
	
	for(DeliveryNoteVO deliveryNote : deliveryNotes){
		List<DeliveryNoteLineItemVO> deliveryNoteLineItems = deliveryNote.getDeliveryNoteLineItems();
		
		%>
		<h3>
			<a href="delivery-note-view.do?deliveryNoteId=<%=deliveryNote.getId() %>&ppoNo=<%=deliveryNote.getPortPurchaseOrderId()%>">
			Delivery Note - <%=deliveryNote.getId() %>
			&nbsp;&nbsp;[Vehicle No = <%=deliveryNote.getVehicleNumber() %>]
			</a>
		</h3>
		<table class="table table-striped">
		<thead>
			<tr>
				<th>Id</th>
				<th>Date</th>
				<th>Length</th>
				<th>Width</th>
				<th>Thickness</th>
				<th>Delivered Quantity</th>
				<th width="5%" class="cell-edit"><span
								class="glyphicon glyphicon glyphicon-remove"></span></th>
			</tr>
		
		</thead>
		<tbody>
			<%
			for(DeliveryNoteLineItemVO deliveryNoteLineItem : deliveryNoteLineItems){
			%>
			<tr id='row-<%=deliveryNoteLineItem.getId() %>'>
				<td><%=deliveryNoteLineItem.getId() %></td>
				<td><%=deliveryNote.getVehicleDate() %></td>
				<td><%=deliveryNoteLineItem.getLength() %></td>
				<td><%=deliveryNoteLineItem.getWidth() %></td>
				<td><%=deliveryNoteLineItem.getThickness() %></td>
				<td><%=deliveryNoteLineItem.getDeliveredQuantity() %></td>
				<td class="cell-edit"><button name="btnDelete" title="Delete" onclick="deleteDeliveryNoteLineItems(<%=deliveryNoteLineItem.getId() %>);">
										<span class="glyphicon glyphicon glyphicon-remove"></span>
								</button></td>
			</tr>	
			<%	
			} 
			%>
			
		</tbody>
		</table>
		<% 
		
	} 
	%>
</div>
</div>
</div>
	