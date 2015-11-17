<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>

<%
	UserInfoVO userInfoVO = (UserInfoVO) session
			.getAttribute(Constants.USER_INFO_SESSION);
	pageContext.setAttribute("userInfoVO", userInfoVO);

	if (!userInfoVO.hasAccess(Constants.Apps.PORT_ENTRY))
		out.print("<style>.cell-edit{display: none;}</style>");
%>

<span id="report-toolbar" class="pull-right"><span
	class="total-field"> </span> Record(s) Found | Export: <img
	src="./img/excel.png" id="exportToExcel" title="Export to Excel"
	onClick="tableToExcel('result-table', 'Excel')" /><img
	src="./img/pdf.png" id="exportToPDF" title="Export to PDF"
	style="display: none;" /> </span>

<!-- <div id="pager" class="pager">
	<form>
		<img src="../addons/pager/icons/first.png" class="first" /> <img
			src="../addons/pager/icons/prev.png" class="prev" /> <input
			type="text" class="pagedisplay" /> <img
			src="../addons/pager/icons/next.png" class="next" /> <img
			src="../addons/pager/icons/last.png" class="last" /> <select
			class="pagesize">
			<option selected="selected" value="10">10</option>
			<option value="20">20</option>
			<option value="30">30</option>
			<option value="40">40</option>
		</select>
	</form>
</div>
 -->