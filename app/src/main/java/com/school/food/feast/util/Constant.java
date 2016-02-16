package com.school.food.feast.util;

public class Constant {
	public static final String APPID = "7ff3b5ddaf309007e16c13b8fafc1fd1";

	public static final String Preferences_user= "user";
	public static final String Preferences_username = "username";
	public static final String Preferences_password = "password";

	public static String userId;
	public static final String IP = "192.168.1.100";
	//public static final String IP = "192.168.1.101";
	public static final String PORT = "8080";
	
	public static final String SERVER = "http://" + IP + ":" + PORT + "/food_server"; 
	public static final String WEB_APP_URL = SERVER + "/" ;
	public static final String SERVER_LOGIN = "/Login?method=login";
	public static final String SERVER_REGISTER = "/RegisterAccount?method=RegisterAccount";

	//Btn的标识
	public static final int BTN_FLAG_HOME = 0x01;
	public static final int BTN_FLAG_ORDER = 0x01 << 1;
	public static final int BTN_FLAG_MINE = 0x01 << 2;

	//Fragment的标识
	public static final String FRAGMENT_FLAG_HOME = "首页";
	public static final String FRAGMENT_FLAG_ORDER = "订单";
	public static final String FRAGMENT_FLAG_MINE = "我的";


	public static class REQUESTCODE {
		public static int LOGINACTIVITY = 10;
		public static int DMFRESULT = 11;
	}

	public static class URL{
		public static String shareURL="https://www.baidu.com";
	}
}
