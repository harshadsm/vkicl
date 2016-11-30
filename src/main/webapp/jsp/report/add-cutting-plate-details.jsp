<%@page import="vkicl.vo.StockBalanceDetailsVO"%>
<%@page import="java.util.List"%>
<%@page import="vkicl.vo.PortInwardRecordVO"%>
<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.form.StockForm"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c" %>

<%
Double maxWidthAndHeightForSvgTag = (Double)request.getAttribute("maxWidthAndHeightForSvgTag");
maxWidthAndHeightForSvgTag = maxWidthAndHeightForSvgTag + 150d;
String plateCoordinatesAsString = (String)request.getAttribute("plateCoordinatesAsString");
List<Double[]> plateCoordinatesScaled = (List<Double[]>)request.getAttribute("plateCoordinatesScaled");
List<Double[]> plateCoordinates = (List<Double[]>)request.getAttribute("plateCoordinates");
StockBalanceDetailsVO vo = (StockBalanceDetailsVO)request.getAttribute("selected_plate_for_cutting");
Double[] firstCoordinate = plateCoordinates.get(0);
%>

<script type="text/javascript">
	var id = 1, row = {}, row_id = 1;
	
	//function validateForm() {
		
		//return commonSubmit();
	//}
	
	function validateForm() {
		
		
		var flag=checkPlateDimension();
		
		
		if(flag==true)
		{
	var	str = "Are you sure you want to Submit?";
	bootbox.confirm(str, function(result) {
		if (result) {
			document.forms[0].submit();
		}
	});
		}
		else
		{
		 bootbox.alert("Cut not possible with given dimensions.");
		 return false;
		}
	return false;
		
		//return commonSubmit();
	}
	
	function checkPlateDimension()
	{
		var sLength=$("#length").val();
		var sWidth=$("#width").val();
		
		var bLength=<%=vo.getLength() %>;
		var bWidth=<%=vo.getWidth() %>;
		
		
		console.log(bLength);
		console.log(bWidth);
		
		if(bLength==sLength && bWidth==sWidth){
			return false;}
		else if(sLength>bLength){
				return false;}
		else if(sWidth > bWidth){
				return false;}
		else if(sLength<=bLength && sWidth<=bWidth){
				return true;}
		else{return false;}
	}
	
	

	function submit() {
		return commonSubmit();
	}

	function coordinateSelected(x,y){
		console.log(x);
		console.log(y);
		$("#origin_x").val(x);
		$("#origin_y").val(y);

		redrawCutPlate();
	}


	function redrawCutPlate(){
		console.log("Going to redraw the cut plate");
		var x = $("#origin_x").val();
		var y = $("#origin_y").val();
		var lengthOfCut = $("#length").val();
		var widthOfCut = $("#width").val();
		var originalPlateLength = $("#originalPlateLength").html();
		var originalPlateWidth = $("#originalPlateWidth").html();
		
		//var coordinateString = "0.0,0.0 150.0,0.0 150.0,150.0 0.0,150.0 0.0,0.0 0.0,0.0";

		$.ajax({
			method:"post",
			url:"./getSvgCoordinatesString",
			data:{
				x:x,
				y:y,
				originalPlateLength:originalPlateLength,
				originalPlateWidth : originalPlateWidth,
				lengthOfCut:lengthOfCut,
				widthOfCut:widthOfCut
			},
			success:function(resp){
				console.log(resp);
				var respObj = JSON.parse(resp);
				var pointsString = respObj.svgPointsString;
				$("#plateToBeCut").attr("points", pointsString);		
			},
			error:function(resp){
				console.log(resp);
			}
		});
		
		
	}
</script>

<div>
	<html:form action="/stock-cutting" onsubmit="return validateForm();">

		<div class="row">
			<div class="col-md-2">
				<div class="panel panel-default">
					<div class="panel-heading">Details of original plate to be cut</div>
					<div class="panel-body">
						<table class="table">
							
							<tbody id="details-tbody">
								<tr>
									<th>Stock Balance  #</th><td ><%=vo.getStockBalId() %></td></tr><tr>
									<th>Mill</th><td><%=vo.getMillName() %></td></tr><tr>
									<th>Make</th><td><%=vo.getMake() %></td></tr><tr>
									<th>Material Type</th><td><%=vo.getMaterialType() %></td></tr><tr>
									<th>Grade</th><td><%=vo.getGrade() %></td></tr><tr>
									<th>Thickness</th><td><%=vo.getThickness() %></td></tr><tr>
									<th>Width</th><td id="originalPlateWidth"><%=vo.getWidth() %></td></tr><tr>
									<th>Length</th><td id="originalPlateLength"><%=vo.getLength() %></td></tr><tr>
									<th>Quantity</th><td><%=vo.getQuantity() %></td></tr><tr>
									<th>Location</th><td><%=vo.getLocation() %></td></tr><tr>
									<th>Area</th><td><%=vo.getPlateArea() %></td></tr><tr>
									
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
			<input name="port_inward_id" type="hidden" value="<%=vo.getStockBalId() %>" />
			
			<div class="col-md-4">
				<div class="row">
					<div class="col-md-12 text-center">
						<h3 class="page-head">Cutting Plate Details</h3>
					</div>
				</div>
				<div class="row">
					<table class="table table-responsive sub-table table-excel" id="port_inward_details_table" ">
					<thead>
						<tr>
							<th width="10%">Length</th>
							<th width="10%">Width</th>
							<th width="10%">X</th>
							<th width="10%">Y</th>
							
							
						</tr>
					</thead>
	
					<tbody >
						
							<tr  class='sub-row' >
								<input type='hidden' name='subPis' />
								<td >
									<input onchange="redrawCutPlate()"  
									type='number' step='1' min='0' name='length' id='length' placeholder='length' class='form-control' value="<%=vo.getLength() %>"/>
								</td>
								<td >
									<input  onchange="redrawCutPlate()"  type='number' step='1' min='0' name='width' id='width' placeholder='width' class='form-control' value="<%=vo.getWidth() %>"/>
									<input  type='hidden' name='millName'  value="<%=vo.getMillName() %>"/>
									<input  type='hidden' name='materialType'  value="<%=vo.getMaterialType() %>"/>
									<input  type='hidden' name='grade'  value="<%=vo.getGrade() %>"/>
									<input  type='hidden' name='thickness'  value="<%=vo.getThickness() %>"/>
									<input  type='hidden' name='stock_Bal_id'  value="<%=vo.getStockBalId() %>"/>
									<input  type='hidden' name='quantity'  value="<%=vo.getQuantity() %>"/>
									<input  type='hidden' name='location'  value="<%=vo.getLocation() %>"/>
									<input  type='hidden' name='make'  value="<%=vo.getMake() %>"/>
									<input  type='hidden' name='heat_no'  value="<%=vo.getHeat_no() %>"/>
									<input  type='hidden' name='plate_no'  value="<%=vo.getPlate_no() %>"/>
									
								</td>
								<td >
									<input  onchange="redrawCutPlate()"  type='number' step='1' min='0' id='origin_x' name='origin_x' placeholder='0' class='form-control' value="<%=firstCoordinate[0] %>"/>
								</td>
								<td >
									<input  onchange="redrawCutPlate()"  type='number' step='1' min='0' id='origin_y' name='origin_y' placeholder='0' class='form-control' value="<%=firstCoordinate[1] %>"/>
								</td>
								
									
									
								
							</tr>
	
					</tbody>
					
				</table>
			</div>
			<div class="row">
				<div class="col-md-12">
					<table class="table table-responsive sub-table table-excel">
					<thead>
						<tr>
							<th width="1%">Select</th>
							<th width="10%">Coordinate #</th>
							<th width="10%">X</th>
							<th width="10%">Y</th>
							
							
							
						</tr>
					</thead>
					
						<tbody>
							<%
							int counter = 1;
							for(Double[] coordinate : plateCoordinates){
								Double xx = coordinate[0];
								Double yy = coordinate[1];
							%>
							<tr>
							<td><input type="radio" name="selectedCoordinate" onclick="coordinateSelected(<%=xx%>,<%=yy%>)"></td>
							<td><%=counter %></td>
							<td><%=xx%></td>
							<td><%=yy %></td>
							</tr>	
							<%
							counter++;
							} 
							%>
							
						</tbody>
					</table>
				</div>
			</div>
		
			<div class="row">
				<div class="col-md-4"></div>
				<div class="col-md-4">
					<html:submit styleClass="btn" onclick="return validateForm();" />
				</div>
				<div class="col-md-4"></div>
			</div>
			</div>
			<div class="col-md-5">
				<h3>Plate Shape (Approx. Scaled.)</h3>
				<svg width="<%=maxWidthAndHeightForSvgTag %>" height="<%=maxWidthAndHeightForSvgTag %>">
					 <defs>
						  <radialGradient id="grad1" cx="50%" cy="50%" r="100%" fx="20%" fy="50%">
						      <stop offset="0%" style="stop-color:rgb(104,70,63);
						      stop-opacity:0" />
						      <stop offset="100%" style="stop-color:rgb(0,0,0);stop-opacity:1" />
						    </radialGradient>
						    
						    <radialGradient id="grad2" cx="50%" cy="50%" r="100%" fx="20%" fy="50%">
						      <stop offset="0%" style="stop-color:rgb(50,70,63);
						      stop-opacity:0" />
						      <stop offset="100%" style="stop-color:rgb(0,0,0);stop-opacity:1" />
						    </radialGradient>
					  </defs>
					<polygon id="plateToBeCut" fill="url(#grad2)" points="" />
  					<polygon fill="url(#grad1)" points="<%=plateCoordinatesAsString %>" />
					  <% 
					  int cnt = 1;
					  for(Double[] coordinate : plateCoordinatesScaled){ 
						  Double x = coordinate[0];
						  Double y = coordinate[1] + 20;
					  %>
						  <text fill="red" font-size="15" font-family="Verdana" x="<%=x %>" y="<%=y%>"><%=cnt %></text>
						  
					  <% 
					  cnt++;
					  } 
					  %>
				  
				</svg>
				
				
				<svg width="<%=maxWidthAndHeightForSvgTag %>" height="<%=maxWidthAndHeightForSvgTag %>">
					 <defs>
						  <radialGradient id="grad2" cx="50%" cy="50%" r="100%" fx="20%" fy="50%">
						      <stop offset="0%" style="stop-color:rgb(50,70,63);
						      stop-opacity:0" />
						      <stop offset="100%" style="stop-color:rgb(0,0,0);stop-opacity:1" />
						    </radialGradient>
					  </defs>
  					
					  
				  
				</svg>
				
			</div>
		</div>

		<html:hidden property="genericListener" value="addDetails" />
	</html:form>
</div>





