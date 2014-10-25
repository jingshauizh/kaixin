package com.kaixin001.engine;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.kaixin001.model.User;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.network.HttpMethod;
import com.kaixin001.network.Protocol;
import com.kaixin001.network.XAuth;
import com.kaixin001.util.KXLog;

//import com.kaixin001.businesslogic.LogoutAndExitProxy;

public class LoginEngine extends KXEngine
{
	public static final int LOGIN_FAILED = -2001;
	public static final int LOGIN_INCORRECT_DELETE = 4001106;
	public static final int LOGIN_INCORRECT_FAKEUSER = 4001107;
	public static final int LOGIN_INCORRECT_PASSWORD = 4001105;
	public static final int LOGIN_INCORRECT_USERNAME = 4001104;
	public static final int LOGIN_SUC = 0;;
	private static LoginEngine instance;
	protected String strErrMsg = "";

	public static LoginEngine getInstance()
	{

		if (instance == null)
			instance = new LoginEngine();
		LoginEngine localLoginEngine = instance;
		return localLoginEngine;

	}

	public int doLogin(Activity paramActivity, Context paramContext,
			String paramString1, String paramString2, String paramString3,
			String paramString4, String paramString5, String paramString6,
			String paramString7) throws SecurityErrorException
	{
		if (paramContext == null)
			return -2001;
		this.ret = 0;
		HttpConnection localHttpConnection = new HttpConnection(paramContext);
		String str10 = null;
		String str13;
		Map localMap = null;
		Object localObject1;
		if (paramString7.equals("login"))
		{
			str10 = Protocol.getInstance().getAccessToken();
			if ((paramString6 != null) && (paramString6.equalsIgnoreCase("QQ")))
			{
				if (TextUtils.isEmpty(paramString3))
				{
					str13 = "";
					localMap = XAuth.getQQParams(str10, paramString1,
							paramString2, str13, null, paramString4,
							paramString5);
				}
			}
			String str12 = paramString3;
			// localMap = XAuth.getWEIBOParams(str10, paramString1,
			// paramString2, str12, null, paramString4, paramString5);
			localMap = XAuth.getParams(str10, paramString1, paramString2, null,
					paramString4, paramString5);
			try
			{
				String str11 = localHttpConnection.httpRequest(str10,
						HttpMethod.POST, localMap, null, null, null);
				localObject1 = str11;

			}
			catch (Exception localException3)
			{
				localException3.printStackTrace();
				localObject1 = null;

			}
		}
		// return 100;
		else
		{
			HashMap localHashMap = new HashMap();
			String str1 = Protocol.getInstance().getDefaultToken();
			String str2 = Protocol.getInstance().makeGetDefaultToken(
					"100015822");
			localHashMap.put("appid", "100015822");
			localHashMap.put("scope", "basic");
			localHashMap.put("sig", XAuth.getParamsForLook(str2));
			try
			{
				String str9 = localHttpConnection.httpRequest(str1,
						HttpMethod.GET, localMap, null, null, null);
				localObject1 = str9;
			}
			catch (Exception localException1)
			{
				localException1.printStackTrace();
				localObject1 = null;
			}
		}

		int i;
		int k;
		try
		{
			if (((String) localObject1).contains("need_bind"))
			{
				this.strErrMsg = super.parseJSON(paramContext,
						(String) localObject1).optString("bind_url");
				KXLog.d("doLogin", "strErrMsg=" + this.strErrMsg);
				return -1001;
			}
			if (((String) localObject1).contains("captcha"))
			{
				this.strErrMsg = ((String) localObject1);
				return -1004;
			}
			if (((String) localObject1).contains("error_code"))
			{
				this.strErrMsg = super.parseJSON(paramContext,
						(String) localObject1).optString("error");
				return -1001;
			}
			String[] arrayOfString = ((String) localObject1).split("&");
			String localObject2 = null;
			String localObject3 = null;
			String localObject4 = null;
			String str3 = null;
			String str4 = null;
			i = -1;
			// new LogoutAndExitProxy(paramActivity);
			int j = arrayOfString.length;
			k = 0;
			while (k < j)
			{
				String str8 = arrayOfString[k];
				if (str8.startsWith("oauth_token_secret"))
				{
					localObject3 = str8.split("=")[1];

				}
				if (str8.startsWith("oauth_token"))
				{
					localObject2 = str8.split("=")[1];

				}
				if (str8.startsWith("user_id"))
				{
					localObject4 = str8.split("=")[1];

				}
				if (str8.startsWith("wapverify"))
				{
					str4 = str8.split("=")[1];

				}
				if (str8.startsWith("verify"))
				{
					str3 = str8.split("=")[1];

				}
				if (!str8.startsWith("is_semi_account"))
				{
					if (!str8.split("=")[1].equals("0"))
					{
						i = 0;
					}
				}
				k++;
			}
			User localUser = User.getInstance();
			if (paramString7.equals("look"))
			{
				localUser.setLookAround(Boolean.valueOf(true));
			}
			localUser.logoutClear();
			localUser.setUID((String) localObject4);
			localUser.setAccount(paramString1);
			localUser.setOauthToken((String) localObject2);
			localUser.setOauthTokenSecret((String) localObject3);
			localUser.setWapVerify(str4);
			localUser.setImcomplete(i);
			String str5;
			String str6;
			String localObject5 = null;
			if (!TextUtils.isEmpty(str3))
			{
				str5 = localUser.getReserved();
				str6 = "verify=" + str3 + ";";
				if (!TextUtils.isEmpty(str5))
					// break;
					localObject5 = str6;
				localUser.setReserved((String) localObject5);
			}
			localUser.saveUserLoginInfo(paramContext);
			localUser.loadUserData(paramContext);

			localUser.setLookAround(Boolean.valueOf(false));

		}
		catch (Exception localException2)
		{
			String str5 = null;
			String str6 = null;
			String localObject5;
			this.strErrMsg = paramContext.getResources().getString(2131427379);
			localException2.printStackTrace();

			int m = str5.indexOf("verify=");
			if (m < 0)
			{
				localObject5 = str5 + str6;

			}
			int n = str5.indexOf(";", m);
			String str7 = str5.substring(0, m) + str6 + str5.substring(n);
			localObject5 = str7;
		}
		return -2001;

	}

	public String getErrMsg()
	{
		return this.strErrMsg;
	}
}

/*
 * Location:
 * C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name: com.kaixin001.engine.LoginEngine JD-Core Version: 0.6.0
 */