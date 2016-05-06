<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%
	UserInfoVO userInfoVO = (UserInfoVO) session
			.getAttribute(Constants.USER_INFO_SESSION);
%>

<div class="mainmenu-wrapper">
	<div class="menu-wrapper">
		<div class="brand-name">
			<img alt="VKICL" src="./img/logo.png" height="50px"> Inventory
			Management System
		</div>
		<%
			if (null != userInfoVO && userInfoVO.isLoggedIn()) {
		%>
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="./home.do">Home</a>
				</div>

				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<%
							if (userInfoVO.hasAccess(Constants.Apps.PORT_ENTRY)) {
						%>
						<li class="dropdown"><a href="./" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-expanded="false">Port
								<span class="caret"></span>
						</a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="./port-inward.do">Create New Inward Record</a></li>
								<li><a href="./port-inward-details.do">Inward Records List (Create packing list for Inward)</a></li>
								<li><a href="./port-outward.do">Create Outward Record</a></li>
								<li><a href="./port-outward-2.do">Create Outward Record 2</a></li>
							</ul></li>
						<%
							}
								if (userInfoVO.hasAccess(Constants.Apps.WAREHOUSE_ENTRY)
										|| userInfoVO.hasAccess(Constants.Apps.DISPATCH_ORDER)) {
						%>
						<li class="dropdown"><a href="./" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-expanded="false">Warehouse
								<span class="caret"></span>
						</a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="./warehouse-inward.do">Inwards</a></li>
								<li><a href="./warehouse-outward.do">Outwards</a></li>
								<%
									if (userInfoVO.hasAccess(Constants.Apps.DISPATCH_ORDER)) {
								%>
								<li><a href="./warehouse-dispatch.do">Dispatch Order</a></li>
								<%
									}
								%>
							</ul></li>
						<%
							}
								if (userInfoVO.hasAccess(Constants.Apps.PORT_REPORT)
										|| userInfoVO
												.hasAccess(Constants.Apps.WAREHOUSE_REPORT)
										|| userInfoVO.hasAccess(Constants.Apps.DISPATCH_ORDER)) {
						%>
						<li class="dropdown"><a href="./" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-expanded="false">Reports
								<span class="caret"></span>
						</a>
							<ul class="dropdown-menu" role="menu">
								<%
									if (userInfoVO.hasAccess(Constants.Apps.PORT_REPORT)) {
								%>
								<li><a href="./port-inward-report.do">Port Inward </a></li>
								<li><a href="./port-outward-report.do">Port Outward </a></li>
								<%
									}
											if (userInfoVO.hasAccess(Constants.Apps.WAREHOUSE_REPORT)) {
								%>
								<li><a href="./warehouse-inward-report.do">Warehouse
										Inward</a></li>
								<li><a href="./warehouse-outward-report.do">Warehouse
										Outward</a></li>
								<%
									}
											if (userInfoVO.hasAccess(Constants.Apps.WAREHOUSE_APPROVER)) {
								%>
								<li><a href="./warehouse-inward-approver-report.do">Warehouse
										Inward Approver</a></li>
								<%
									}
											if (userInfoVO.hasAccess(Constants.Apps.DISPATCH_ORDER)
													|| userInfoVO
															.hasAccess(Constants.Apps.DISPATCH_REPORT)) {
								%>
								<li><a href="./warehouse-dispatch-report.do">Warehouse
										Dispatch Order</a></li>
								<%
									}
											if (userInfoVO.hasAccess(Constants.Apps.STOCK)) {
								%>
								<li><a href="./stock-report.do">Stock</a></li>
								<%
									}
								%>
							</ul></li>

						<%
							}
								if (userInfoVO.hasAccess(Constants.Apps.MANAGE_USERS)) {
						%>
						<li class="dropdown"><a href="./" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-expanded="false">Admin
								<span class="caret"></span>
						</a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="./manage-user.do">Manage Users</a></li>
							</ul></li>

						<%
							}
						%>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li id="mnulogin"><a href="./logout.do">Logout - <span
								class="glyphicon glyphicon-user"></span> <%=userInfoVO.getUserName()%>
						</a></li>
					</ul>
				</div>
			</div>
		</nav>
		<%
			} else {
		%>
		<div></div>
		<%
			}
		%>
	</div>
</div>