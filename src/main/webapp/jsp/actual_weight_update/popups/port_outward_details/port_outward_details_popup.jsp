
<%@page import="vkicl.report.bean.PortOutwardBean2"%>
<%@page import="java.util.List"%>
<%
List<PortOutwardBean2> portOutwardDetailsRecords = (List<PortOutwardBean2>) request.getAttribute("portOutwardDetailsRecords");

%>

<%
if(portOutwardDetailsRecords!=null && !portOutwardDetailsRecords.isEmpty()){
	%>
	<table class="table">
	<thead>
		<tr>
			<th>Grade</th>
			<th>Type</th>
			<th>Thickness</th>
			<th>Length</th>
			<th>Width</th>
			<th>Quantity</th>
			<th>Vessel Date</th>
			<th>Vessel Name</th>
			
		</tr>
	</thead>
	<tbody>
	<%
	
for(PortOutwardBean2 pob : portOutwardDetailsRecords){ 
%>
<tr>
<td><%=pob.getGrade() %></td>
<td><%=pob.getMaterial_type() %></td>
<td><%=pob.getThickness() %></td>
<td><%=pob.getLength() %></td>
<td><%=pob.getWidth() %></td>
<td><%=pob.getQuantity() %></td>
<td><%=pob.getVessel_Date() %></td>
<td><%=pob.getVessel_name() %></td>




</tr>
<%
}
	%>
	</tbody></table>
	<%
}
%>