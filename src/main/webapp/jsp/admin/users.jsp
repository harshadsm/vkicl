<%@page import="java.util.ArrayList"%>
<%@page import="vkicl.form.UserForm"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="org.apache.struts.util.LabelValueBean"%>
<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page import="vkicl.form.UserForm"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c"%>

<%
	ArrayList<LabelValueBean> access = (ArrayList<LabelValueBean>) request.getAttribute("access");
%>

<div class="row">
	<div class="col-md-12">
		<h3 class="page-head">Manage Users</h3>
	</div>
</div>
<html:form action="manage-user" onsubmit="return validateForm();">
	<div class="row">
		<div class="col-sm-4">
			<table class="table table-responsive">
				<tr>
					<td><label for="genericListener">Action</label></td>
					<td><select class="form-control" name="genericListener">
							<option value="Add" label="Add">Add</option>
							<option value="Update" label="Update">Update</option>
							<option value="Delete" label="Delete">Delete</option>
					</select></td>
				<tr>
				<tr>
					<td><label for="newUserName">Enter new Username</label></td>
					<td><input type="text" name="newUserName" id="newUserName"
						class="form-control" /></td>
				</tr>
				<tr>
					<td><label for="newUserName">Select Existing Username</label></td>
					<td><html:select property="currentUserName"
							styleId="currentUserName" styleClass="form-control">
							<html:optionsCollection property="userNames" />
						</html:select></td>
				</tr>
				<tr>
					<td><label for="userName">Password</label></td>
					<td><input type="text" name="password" class="form-control" /></td>
				</tr>
			</table>
		</div>
		<div class="col-sm-8">
			<table class="table table-responsive">
				<tr class="head-row">
					<td><label>Access</label></td>
					<td><input type='checkbox' id="selAll" name="selAll"
						value='Select All' /> <label for="selAll" class="link">
							Select / Deselect All Access </label></td>
				</tr>
				<tr>
					<%
						for (int i = 0; i < access.size(); i++) {
							out.print("<td><input type='checkbox' id='userAccess' name='userAccess' value='"
									+ access.get(i).getValue()
									+ "' /> <label class='link' onclick='$(this).prev().click();'>"
									+ access.get(i).getLabel() + "</label></td>");
							if ((i + 1) % 2 == 0)
								out.print("</tr><tr>");
						}
					%>
				</tr>
			</table>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<input type="button" value="Fetch Details" onclick="fetchUserData();"
				class="btn pull-left"> <input type="button"
				class="btn pull-right" onclick="return validateForm();"
				value="Submit">
		</div>
	</div>
</html:form>

<script type="text/javascript">
	$("#selAll").click(function() {
		var flag = this.checked;
		$("[name=userAccess]").each(function(i, cbox) {
			$(cbox).prop("checked", flag);
		});
	});

	function validateForm() {
		var action = document.forms[0].genericListener.value;
		if (action == "Add" && $.trim($("#newUserName").val()) == "") {
			bootbox.alert("Please Enter a new Username");
			return false;
		}
		if (action == "Add")
			action = "Create new User";
		else if (action == "Update")
			action = "Update Existing User";
		else
			action = "Delete Existing User";
		var str = "Are you sure you want to " + action + "?";

		return commonSubmit(str);
	}

	function fetchUserData() {
		var currentUserName = $("select[name='currentUserName']").val();
		showLoader();
		$.ajax({
			url : "./xml?query=query.admin.user.access&count=1&param1="
					+ currentUserName,
			success : function(xml, textStatus, response) {
				xmlDoc = $.parseXML(xml);
				$("input[type=checkbox]").removeAttr('checked');
				$(xmlDoc).find("value").each(function(i, data) {
					$("[value='" + data.innerHTML + "']").click();
				});
				hideLoader();
			},
			error : function() {
				bootbox.alert("Unable to fetch Details for for " + username);
				hideLoader();
			}
		});
		$.ajax({
			url : "./xml?query=query.admin.user.password&count=1&param1="
					+ currentUserName,
			success : function(xml, textStatus, response) {
				xmlDoc = $.parseXML(xml);
				$(xmlDoc).find("value").each(function(i, data) {
					// access.push(data.innerHTML);
					$("[name='password']").val(data.innerHTML);
				});
				hideLoader();
			},
			error : function() {
				bootbox.alert("Unable to fetch Details for for " + username);
				hideLoader();
			}
		});
	}
</script>