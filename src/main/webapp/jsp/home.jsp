<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c"%>
<%
	UserInfoVO userInfoVO = (UserInfoVO) session
			.getAttribute(Constants.USER_INFO_SESSION);
	String url = (String) session.getAttribute(Constants.USER_FORWARD);
	if (null != url && !"".equalsIgnoreCase(url.trim())) {
		session.setAttribute(Constants.USER_FORWARD, null);
		out.print("<script>location.href='" + url + "'</script>");
	}
%>

<div class="row">
	<%
		if (userInfoVO.hasAccess(Constants.Apps.PORT_ENTRY)) {
	%>
	<div class="col-sm-2">
		<div class="portfolio-item">
			<div class="portfolio-image">
				<a href="./port-inward.do">Port - Inward</a>
			</div>
		</div>
	</div>
	<div class="col-sm-2">
		<div class="portfolio-item">
			<div class="portfolio-image">
				<a href="./port-inward-details.do">Port - Inward Packing List</a>
			</div>
		</div>
	</div>
	<div class="col-sm-2">
		<div class="portfolio-item">
			<div class="portfolio-image">
				<a href="./port-outward.do">Port - Outwards</a>
			</div>
		</div>
	</div>
	<div class="col-sm-2">
		<div class="portfolio-item">
			<div class="portfolio-image">
				<a href="./port-purchase-order.do">Port- Purchase-Order</a>
			</div>
		</div>
	</div>
	<div class="col-sm-2">
		<div class="portfolio-item">
			<div class="portfolio-image">
				<a href="./port-purchase-order-delivery.do">Port- Purchase-Order-Delivery</a>
			</div>
		</div>
	</div>
	<%
		}
		if (userInfoVO.hasAccess(Constants.Apps.WAREHOUSE_ENTRY)) {
	%>
	<div class="col-sm-2">
		<div class="portfolio-item">
			<div class="portfolio-image">
				<a href="./warehouse-inward.do">Warehouse - Inwards</a>
			</div>
		</div>
	</div>
	</div>
	<div class="row">
	
	<div class="col-sm-2">
		<div class="portfolio-item">
			<div class="portfolio-image">
				<a href="./warehouse-inward-2.do">Warehouse - Inwards-2</a>
			</div>
		</div>
	</div>
	<!-- <div class="col-sm-2">
		<div class="portfolio-item">
			<div class="portfolio-image">
				<a href="./warehouse-outward.do">Warehouse - Outwards</a>
			</div>
		</div>
	</div> -->
	<%
		}
		if (userInfoVO.hasAccess(Constants.Apps.DISPATCH_ORDER)) {
	%>
	<div class="col-sm-2">
		<div class="portfolio-item">
			<div class="portfolio-image">
				<a href="./warehouse-dispatch.do">Warehouse - Dispatch Order</a>
			</div>
		</div>
	</div>
	<%
		}
		if (userInfoVO.hasAccess(Constants.Apps.PORT_REPORT)) {
	%>
	<div class="col-sm-2">
		<div class="portfolio-item">
			<div class="portfolio-image">
				<a href="./port-inward-report.do">Report - Port Inward</a>
			</div>
		</div>
	</div>
	<div class="col-sm-2">
		<div class="portfolio-item">
			<div class="portfolio-image">
				<a href="./port-stock-report.do">Cumulative Stock at Port</a>
			</div>
		</div>
	</div>
	<div class="col-sm-2">
		<div class="portfolio-item">
			<div class="portfolio-image">
				<a href="./port-outward-report.do">Report - Port Outward</a>
			</div>
		</div>
	</div>
	<%
		}
		if (userInfoVO.hasAccess(Constants.Apps.WAREHOUSE_REPORT)) {
	%>
	<div class="col-sm-2">
		<div class="portfolio-item">
			<div class="portfolio-image">
				<a href="./warehouse-inward-report.do">Report - Warehouse Inward</a>
			</div>
		</div>
	</div>
	</div>
	<div class="row">
	<div class="col-sm-2">
		<div class="portfolio-item">
			<div class="portfolio-image">
				<a href="./warehouse-outward-report.do">Report - Warehouse
					Outward</a>
			</div>
		</div>
	</div>
	
	<%
		}
		if (userInfoVO.hasAccess(Constants.Apps.DISPATCH_ORDER)
				|| userInfoVO.hasAccess(Constants.Apps.DISPATCH_REPORT)) {
	%>
	<div class="col-sm-2">
		<div class="portfolio-item">
			<div class="portfolio-image">
				<a href="./warehouse-dispatch-report.do">Report - Warehouse
					Dispatch Order</a>
			</div>
		</div>
	</div>
	
	<%
		}
		if (userInfoVO.hasAccess(Constants.Apps.WAREHOUSE_APPROVER)) {
	%>
	<div class="col-sm-2">
		<div class="portfolio-item">
			<div class="portfolio-image">
				<a href="./warehouse-inward-approver-report.do">Report -
					Warehouse Inward Approver</a>
			</div>
		</div>
	</div>
	<%
		}
		if (userInfoVO.hasAccess(Constants.Apps.STOCK)) {
	%>
	<div class="col-sm-2">
		<div class="portfolio-item">
			<div class="portfolio-image">
				<a href="./stock-report.do">Report - Stock</a>
			</div>
		</div>
		<br />
	</div>
	<div class="col-sm-2">
		<div class="portfolio-item">
			<div class="portfolio-image">
				<a href="./stock-cutting.do?genericListener=nothing">Cut a Plate</a>
			</div>
		</div>
		<br />
	</div>
	<%
		}
		if (userInfoVO.hasAccess(Constants.Apps.MANAGE_USERS)) {
	%>
	<div class="col-sm-2">
		<div class="portfolio-item">
			<div class="portfolio-image">
				<a href="./manage-user.do">Admin - Manage Users</a>
			</div>
		</div>
	</div>
	<%
		}
	%>
</div>