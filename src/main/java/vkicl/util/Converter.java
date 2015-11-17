package vkicl.util;

import vkicl.form.LoginForm;
import vkicl.vo.UserInfoVO;

public class Converter
{
	public static UserInfoVO formToVo(LoginForm loginForm, UserInfoVO userInfoVO)
	{
		if (null == userInfoVO)
		{
			userInfoVO = new UserInfoVO();
		}
		userInfoVO.setUserName(loginForm.getUserName());
		userInfoVO.setPassword(loginForm.getPassword());
		return userInfoVO;
	}

	public static LoginForm voToForm(UserInfoVO userInfoVO, LoginForm loginForm)
	{
		loginForm.setUserName(userInfoVO.getUserName());
		loginForm.setPassword(userInfoVO.getPassword());
		return loginForm;
	}

}
