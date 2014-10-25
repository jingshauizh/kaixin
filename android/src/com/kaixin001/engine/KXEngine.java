package com.kaixin001.engine;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.kaixin001.model.KXCityModel;
import com.kaixin001.model.KaixinConst;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.StringUtil;

public abstract class KXEngine
{
	public static final int CAPTCHA_ERR = -1004;
	public static final int HTTP_ERR = -1002;
	public static final int PARSEJSON_ERR = -1001;
	public static final int RESULT_ERR = 0;
	public static final int RESULT_OK = 1;
	public static final int SECURITY_ERR = -1003;
	private static final String TAG = "KXEngine";
	private static int lastHttpRequestCode = -999;
	protected final String ERROR = "error";
	protected final String ERROR_NO = "error_code";
	protected final String ERR_NO = "errno";
	protected final String RET_NO = "ret";
	protected final String UID = "uid";
	protected boolean bIsStop = false;
	protected boolean mEnableNewMessageNotification = true;
	protected int ret = 0;

	public static void cancelNewChatNotification(Context paramContext)
	{
		((NotificationManager) paramContext.getSystemService("notification"))
				.cancel(KaixinConst.ID_NEW_CHAT_NOTIFICATION);
	}

	public static void clearNoticeFlag(Context paramContext)
	{
		String str = Protocol.getInstance().makeClearNoticeFlagRequest(
				User.getInstance().getUID());
		HttpProxy localHttpProxy = new HttpProxy(paramContext);
		try
		{
			localHttpProxy.httpGet(str, null, null);
			return;
		}
		catch (Exception localException)
		{
			KXLog.e("KXEngine", "failed to clear notice flag", localException);
		}
	}

	private void parseMsgNotice(Context paramContext, JSONObject paramJSONObject)
	{

	}

	private void parseRet(JSONObject paramJSONObject) throws JSONException
	{
		if (paramJSONObject == null)
			return;
		this.ret = paramJSONObject.optInt("ret", 0);
	}

	public static void sendKXCityNotification(Context paramContext,
			KXCityModel paramKXCityModel)
	{

	}

	public static void sendNewChatNotification(Context paramContext,
			int paramInt)
	{

	}

	public static void sendNewMsgNotification(Context paramContext, int paramInt)
	{
		if (paramContext != null)
		{
			SharedPreferences localSharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(paramContext);
			localSharedPreferences.getBoolean(
					"notification_vibrate_preference", true);
			boolean bool1 = localSharedPreferences.getBoolean(
					"notification_led_preference", true);
			boolean bool2 = localSharedPreferences.getBoolean(
					"notification_ringtone_enabled_preference", true);
			NotificationManager localNotificationManager = (NotificationManager) paramContext
					.getSystemService("notification");
			Notification localNotification = new Notification();
			Intent localIntent = new Intent(
					"com.kaixin001.VIEW_MESSAGECENTER_DETAIL");
			localIntent.addFlags(67108864);
			PendingIntent localPendingIntent = PendingIntent.getActivity(
					paramContext, 0, localIntent, 0);
			localNotification.icon = 2130838989;
			String str1 = paramContext.getResources().getString(2131427329);
			String str2 = StringUtil.replaceTokenWith(paramContext
					.getResources()
					.getString(2131427661), "*", String.valueOf(paramInt));
			localNotification.tickerText = (str1 + ":" + str2);
			localNotification.setLatestEventInfo(paramContext, str1, str2,
					localPendingIntent);
			if (0 != 0)
				localNotification.defaults = (0x2 | localNotification.defaults);
			if (bool1)
				localNotification.defaults = (0x4 | localNotification.defaults);
			if (bool2)
				localNotification.sound = Uri
						.parse("android.resource://com.kaixin001.activity/2131099652");
			localNotificationManager.notify(
					KaixinConst.ID_NEW_MESSAGE_NOTIFICATION, localNotification);
		}
	}

	public void cancel()
	{
		this.bIsStop = true;
	}

	public void checkBusinessProcError(Context paramContext, String paramString)
			throws SecurityErrorException
	{

	}

	public void enableNewMessageNotification(boolean paramBoolean)
	{
		this.mEnableNewMessageNotification = paramBoolean;
	}

	public int getRet()
	{
		return this.ret;
	}

	public JSONObject parseJSON(Context paramContext, String paramString)
			throws SecurityErrorException
	{
		return parseJSON(paramContext, true, paramString);
	}

	// ERROR //
	public JSONObject parseJSON(Context paramContext, boolean paramBoolean,
			String paramString) throws SecurityErrorException
	{
		return null;
	}

	public void sendNewMsgNotificationBroadcast(Context paramContext,
			int paramInt)
	{

	}
}

/*
 * Location:
 * C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name: com.kaixin001.engine.KXEngine JD-Core Version: 0.6.0
 */