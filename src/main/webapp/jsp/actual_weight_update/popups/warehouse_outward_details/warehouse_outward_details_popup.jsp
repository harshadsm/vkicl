

<%@page import="vkicl.vo.WarehouseOutwardTempVO"%>
<%@page import="java.util.List"%>
<%
List<WarehouseOutwardTempVO> warehouseOutwardTempRecords = (List<WarehouseOutwardTempVO>) request.getAttribute("warehouseOutwardTempRecords");

%>

<%
if(warehouseOutwardTempRecords!=null && !warehouseOutwardTempRecords.isEmpty()){
	%>
	<table class="table">
	<thead>
		<tr>
			<th>Grade</th>
			<th>Make</th>
			<th>Mill</th>

			<th>Thickness</th>
			<th>Width</th>
			<th>Length</th>
			<th>Quantity</th>
			
		</tr>
	</thead>
	<tbody>
	<%
	
for(WarehouseOutwardTempVO wot : warehouseOutwardTempRecords){ 
%>
<tr>
<td><%=wot.getGrade() %></td>
<td><%=wot.getMake() %></td>
<td><%=wot.getMill_name() %></td>

<td><%=wot.getThickness() %></td>
<td><%=wot.getWidth() %></td>
<td><%=wot.getLength() %></td>
<td><%=wot.getReqd_qty() %></td>


</tr>
<%
}
	%>
	</tbody></table>
	<%
}
%>

