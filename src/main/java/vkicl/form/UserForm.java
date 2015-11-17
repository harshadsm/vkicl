package vkicl.form;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.struts.util.LabelValueBean;

@SuppressWarnings("serial")
public class UserForm extends BaseForm {
	private ArrayList<LabelValueBean> userNames = null;
	private ArrayList<LabelValueBean> access = null;

	private String currentUserName = null;
	private String newUserName = null;
	private String password = null;
	private String[] userAccess = null;
	private boolean userDelete = false;

	public ArrayList<LabelValueBean> getUserNames() {
		return userNames;
	}

	public void setUserNames(ArrayList<LabelValueBean> userNames) {
		this.userNames = userNames;
	}

	public ArrayList<LabelValueBean> getAccess() {
		return access;
	}

	public void setAccess(ArrayList<LabelValueBean> access) {
		this.access = access;
	}

	public String getCurrentUserName() {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}

	public String getNewUserName() {
		return newUserName;
	}

	public void setNewUserName(String newUserName) {
		this.newUserName = newUserName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String[] getUserAccess() {
		return userAccess;
	}

	public void setUserAccess(String[] userAccess) {
		this.userAccess = userAccess;
	}

	public boolean isUserDelete() {
		return userDelete;
	}

	public void setUserDelete(boolean userDelete) {
		this.userDelete = userDelete;
	}

	@Override
	public String toString() {
		return "UserForm [userNames=" + userNames + ", access=" + access
				+ ", currentUserName=" + currentUserName + ", newUserName="
				+ newUserName + ", password=" + password + ", userAccess="
				+ Arrays.toString(userAccess) + ", userDelete=" + userDelete
				+ "]";
	}

}