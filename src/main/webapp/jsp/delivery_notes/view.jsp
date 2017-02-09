 <%@page import="vkicl.vo.DeliveryNoteLineItemVO"%>
<%@page import="java.util.List"%>

<%@page import="vkicl.vo.DeliveryNoteVO"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c" %>

<%
DeliveryNoteVO vo = (DeliveryNoteVO)request.getAttribute("delivery_note_details");
List<DeliveryNoteLineItemVO> deliveryNoteLineItems = (List<DeliveryNoteLineItemVO>)request.getAttribute("delivery_note_line_items");
%>

<script type="text/javascript">
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
	
	
function validateForm() {
	
return commonSubmit();
		
}

</script> 

<div>
<div class="row">
	<div class="col-md-12">
		<h3 class="page-head"> Delivery Note</h3>
	</div>
</div>
<div>
	<html:form enctype="multipart/form-data" action="/delivery-note-view"
		method="post">
		<div class="row">
		<div class="col-md-12">
				<div class="panel panel-default">
					<div class="panel-heading"></div>
					
					<table class="table">
							
							<tbody id="details-tbody">
							<tr>
									<th>PPO Number</th><td><%=vo.getPortPurchaseOrderId() %>
									<input type="hidden" name='ppoNo' id="ppoNo" value="<%=vo.getPortPurchaseOrderId() %>"/></td>
								
									<th>PPO Date</th><td><%=vo.getPortPurchaseOrder().getPpoDate() %></td>
								</tr>
								<tr>
									<th>Customer Name</th><td><%=vo.getPortPurchaseOrder().getCustName() %></td>
								<th>Delivery address (Mentioned on PPO)</th><td><%=vo.getPortPurchaseOrder().getDeliveryAddr() %></td>
									
								</tr>
								<tr>
									<th>Delivery Note No.</th><td><%=vo.getId() %></td>
								<th>Delivery Date</th><td>
								<div class="input-group date date-picker-div" id="datetimepicker1">
								<input type="text" name="deliveryDate" id="deliveryDate" class="form-control" value="<%=vo.getDeliveryDate() %>"
								data-date-format="DD-MM-YYYY"/>
								<span class="input-group-addon"><span
									class="glyphicon-calendar glyphicon"></span></span>
									
									
								</div>
							</td>
									
								</tr>
								<tr>
									<th>Delivery address</th><td><input type="text" name="deliveryAddress" id="deliveryAddress"
								class="form-control"  value="<%=vo.getDeliveryNoteAddress() %>"/></td>
								
									<th>Vehicle No.</th><td><input type="text" name="vehicleNumber"
								class="form-control"  value="<%=vo.getVehicleNumber() %>"/></td>
								</tr>
								<tr>
								<th>Actual wt.</th>
								<th>
								
								<div class='input-group'>
								<input type='number' step='0.001' placeholder='B/E Weight' min='0' name='actualWt' id="actualWt" class='form-control' aria-label='...'>
								<div class='input-group-btn weight-group'><input type='hidden' name='beWtUnit' value='TON' />
								<button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-expanded='false'>TON 
								<span class='caret'></span></button><ul class='dropdown-menu dropdown-menu-right' role='menu'>
								<li onclick='btnGroupChange(this);'><a>TON</a></li><li onclick='btnGroupChange(this);'><a>KG</a></li></ul></div></div>
								</th>
								</tr>
							</tbody>
							</table>
							
					</div>
					</div>
		</div>
		
		
		<div class="row">
<div class="col-md-6">
<div>
	
		<h3></h3>
		
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
				<td><%=deliveryNoteLineItem.getId() %>
				<input type="hidden" name="deliveryNoteLineItemId"
								class="form-control"  value="<%=deliveryNoteLineItem.getId() %>"/>
				</td>
				<td><%=deliveryNoteLineItem.getDate() %></td>
				<td><%=deliveryNoteLineItem.getLength() %></td>
				<td><%=deliveryNoteLineItem.getWidth() %></td>
				<td><%=deliveryNoteLineItem.getThickness() %></td>
				<td><input type="Number" name="deliveredQuantity"
								class="form-control"  value="<%=deliveryNoteLineItem.getDeliveredQuantity() %>"/></td>
				<td class="cell-edit"><button name="btnDelete" title="Delete" onclick="deleteDeliveryNoteLineItems(<%=deliveryNoteLineItem.getId() %>);">
										<span class="glyphicon glyphicon glyphicon-remove"></span>
								</button></td>
			</tr>	
			<%	
			} 
			%>
			
		</tbody>
		</table>
</div>
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
</div>
		
		


