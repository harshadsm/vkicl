package vkicl.util;

import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	public static String dateToString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.Apps.DATE_FORMAT);
		String dateString = sdf.format(date);
		return dateString;
	}
	
	public static Date sqlDateToDate(java.sql.Date date){
		return new Date(date.getTime());
	}
	
	public static java.sql.Date dateToSqlDate(java.util.Date date){
		return new java.sql.Date(date.getTime());
	}

}
