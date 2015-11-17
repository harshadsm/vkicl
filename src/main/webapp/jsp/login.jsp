<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c"%>

<%
	UserInfoVO userInfoVO = (UserInfoVO) session
			.getAttribute(Constants.USER_INFO_SESSION);
	pageContext.setAttribute("userInfoVO", userInfoVO);
%>

<div class="container login-box-container">
	<html:form action="/validate-login" styleClass="form-horizontal">
		<div class="row">
			<div class="col-md-12">
				<br /><br />
			</div>
		</div>
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<h3 class="page-head">Login</h3>
				<%
					if (null != userInfoVO && !"".equals(userInfoVO.getMessage())) {
							String message = (null==userInfoVO)?"":userInfoVO.getMessage().trim();
							out.print("<script type='text/javascript'>bootbox.alert('"
									+ StringEscapeUtils.escapeHtml(message)
									+ "');</script>");
							out.print("<br /><h4>" + message + "</h4>");
							userInfoVO.setMessage("");
							session.setAttribute(Constants.USER_INFO_SESSION,
									userInfoVO);
						}
				%>
			</div>
			<div class="col-md-3"></div>
		</div>
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<table class="table table-responsive">
					<tr>
						<td class="form-label"><label for="userName"
							class="col-sm-2 control-label">Username</label></td>
						<td><html:text property="userName" styleId="userName" styleClass="form-control" /></td>
					</tr>
					<tr>
						<td class="form-label"><label for="password"
							class="col-sm-2 control-label">Password</label></td>
						<td><html:password property="password"
								styleClass="form-control" /></td>
					</tr>
					<tr>
						<td></td>
						<td><html:submit styleClass="btn btn-default" /> <html:reset
								styleClass="btn btn-default" /></td>
					</tr>
				</table>
			</div>
			<div class="col-md-3"></div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<br /> <br />
			</div>
		</div>
		<html:hidden property="genericListener" value="" />
	</html:form>
</div>
