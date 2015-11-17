package vkicl.form;


@SuppressWarnings("serial")
public class LoginForm extends BaseForm
{
	private String userName = null;
	private String password = null;
	private String error = null;

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Override
	public String toString()
	{
		return "LoginForm [userName=" + userName + ", password=" + password
				+ ", error=" + error + "]";
	}

}
