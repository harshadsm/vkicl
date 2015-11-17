package vkicl.vo;

import java.util.ArrayList;

public class UserInfoVO extends BaseVO {
	private String userName = null;
	private String password = null;
	private ArrayList<String> appAccessList = null;
	private boolean isLoggedIn = false;
	private String message = "";

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<String> getAppAccessList() {
		return appAccessList;
	}

	public void setAppAccessList(ArrayList<String> appAccessList) {
		this.appAccessList = appAccessList;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "UserInfoVO [userName=" + userName + ", password=" + password
				+ ", appAccessList=" + appAccessList + ", isLoggedIn="
				+ isLoggedIn + ", message=" + message + "]";
	}

	public boolean hasAccess(String moduleName) {
		boolean acccess = false;
		if (null != this.getAppAccessList())
			for (String app : this.getAppAccessList()) {
				if (null != moduleName && app.equalsIgnoreCase(moduleName)) {
					acccess = true;
					break;
				}
			}
		return acccess;
	}

}
