package com.kaixin001.engine;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.kaixin001.activity.MessageHandlerHolder;
import com.kaixin001.fragment.NewsFragment;
import com.kaixin001.model.KXCityModel;
import com.kaixin001.model.KaixinConst;
import com.kaixin001.model.MessageCenterModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.StringUtil;
import java.util.Calendar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    ((NotificationManager)paramContext.getSystemService("notification")).cancel(KaixinConst.ID_NEW_CHAT_NOTIFICATION);
  }

  public static void clearNoticeFlag(Context paramContext)
  {
    String str = Protocol.getInstance().makeClearNoticeFlagRequest(User.getInstance().getUID());
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
    if (paramJSONObject == null);
    while (true)
    {
      return;
      try
      {
        int i = MessageCenterModel.getInstance().getTotalNoticeCnt();
        JSONArray localJSONArray = paramJSONObject.optJSONArray("notices");
        if (localJSONArray == null)
          continue;
        MessageCenterModel.getInstance().setNoticeArray(localJSONArray);
        int j = paramJSONObject.getInt("notice");
        int k = MessageCenterModel.getInstance().getTotalNoticeCnt();
        if ((k <= 0) || (j == 0) || (i == k))
          continue;
        sendNewMsgNotificationBroadcast(paramContext, k);
        return;
      }
      catch (JSONException localJSONException)
      {
      }
    }
  }

  private void parseRet(JSONObject paramJSONObject)
    throws JSONException
  {
    if (paramJSONObject == null)
      return;
    this.ret = paramJSONObject.optInt("ret", 0);
  }

  public static void sendKXCityNotification(Context paramContext, KXCityModel paramKXCityModel)
  {
    boolean bool1;
    boolean bool2;
    NotificationManager localNotificationManager;
    Notification localNotification;
    Bundle localBundle;
    Intent localIntent;
    if (paramContext != null)
    {
      SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
      localSharedPreferences.getBoolean("notification_vibrate_preference", true);
      bool1 = localSharedPreferences.getBoolean("notification_led_preference", true);
      bool2 = localSharedPreferences.getBoolean("notification_ringtone_enabled_preference", true);
      localNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
      localNotification = new Notification();
      if (TextUtils.isEmpty(paramKXCityModel.getTitle()))
        break label765;
      localBundle = new Bundle();
      localBundle.putString("fname", paramKXCityModel.getFname());
      localBundle.putString("fuid", paramKXCityModel.getFuid());
      localBundle.putString("rpid", paramKXCityModel.getRid());
      localBundle.putString("isShowMoreRep", "1");
      localBundle.putString("commentflag", paramKXCityModel.getCommentFlag());
      localBundle.putString("usertype", paramKXCityModel.getUserType());
      localBundle.putString("statKey", paramKXCityModel.getStatKey());
      if ((TextUtils.isEmpty(paramKXCityModel.getType())) || (!paramKXCityModel.getType().equals("1088")))
        break label372;
      localIntent = new Intent("com.kaixin001.VIEW_REPOST_DETAIL");
      localIntent.putExtras(localBundle);
    }
    while (true)
    {
      localIntent.putExtra("prefragment", NewsFragment.class.getName());
      localIntent.addFlags(67108864);
      PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, 0, localIntent, 0);
      localNotification.icon = 2130838989;
      String str1 = paramContext.getResources().getString(2131427329);
      String str2 = paramKXCityModel.getTitle();
      localNotification.tickerText = (str1 + ":" + str2);
      localNotification.setLatestEventInfo(paramContext, str1, str2, localPendingIntent);
      if (0 != 0)
        localNotification.defaults = (0x2 | localNotification.defaults);
      if (bool1)
        localNotification.defaults = (0x4 | localNotification.defaults);
      if (bool2)
        localNotification.sound = Uri.parse("android.resource://com.kaixin001.activity/2131099652");
      localNotification.flags = (0x10 | localNotification.flags);
      localNotificationManager.notify(KaixinConst.ID_NEW_PUSH_NOTIFICATION, localNotification);
      return;
      label372: if ((!TextUtils.isEmpty(paramKXCityModel.getType())) && (paramKXCityModel.getType().equals("1090")))
      {
        localIntent = new Intent("com.kaixin001.VIEW_HOROSCOPE");
        break;
      }
      if ((!TextUtils.isEmpty(paramKXCityModel.getType())) && (paramKXCityModel.getType().equals("100001")))
      {
        localIntent = new Intent("com.kaixin001.VIEW_REPOST");
        break;
      }
      if ((!TextUtils.isEmpty(paramKXCityModel.getType())) && (paramKXCityModel.getType().equals("100002")))
      {
        localIntent = new Intent("com.kaixin001.VIEW_PHOTO_ALBUM");
        break;
      }
      if ((!TextUtils.isEmpty(paramKXCityModel.getType())) && (paramKXCityModel.getType().equals("100004")))
      {
        localIntent = new Intent("com.kaixin001.VIEW_NEWS");
        break;
      }
      if ((!TextUtils.isEmpty(paramKXCityModel.getType())) && (paramKXCityModel.getType().equals("100005")))
      {
        localIntent = new Intent("com.kaixin001.VIEW_HOME_DETAIL");
        break;
      }
      if ((!TextUtils.isEmpty(paramKXCityModel.getType())) && (paramKXCityModel.getType().equals("100006")))
      {
        localIntent = new Intent("com.kaixin001.VIEW_GAMES");
        break;
      }
      if ((!TextUtils.isEmpty(paramKXCityModel.getType())) && (paramKXCityModel.getType().equals("100007")))
      {
        localIntent = new Intent("com.kaixin001.VIEW_GIFT");
        break;
      }
      if ((!TextUtils.isEmpty(paramKXCityModel.getType())) && (paramKXCityModel.getType().equals("100008")))
      {
        localIntent = new Intent("com.kaixin001.VIEW_POSITION");
        break;
      }
      if ((!TextUtils.isEmpty(paramKXCityModel.getType())) && (paramKXCityModel.getType().equals("100009")))
      {
        localIntent = new Intent("com.kaixin001.VIEW_TOPIC");
        localIntent.putExtra("search", KXCityModel.mTopic);
        break;
      }
      localIntent = new Intent("com.kaixin001.VIEW_PUSH_DETAIL");
      localBundle.putString("link", paramKXCityModel.getUrl());
      localBundle.putString("label", paramKXCityModel.getLabel());
      break;
      label765: localIntent = new Intent("com.kaixin001.VIEW_NEWS");
    }
  }

  public static void sendNewChatNotification(Context paramContext, int paramInt)
  {
    if (paramContext != null)
    {
      SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
      localSharedPreferences.getBoolean("notification_vibrate_preference", true);
      boolean bool1 = localSharedPreferences.getBoolean("notification_led_preference", true);
      boolean bool2 = localSharedPreferences.getBoolean("notification_ringtone_enabled_preference", true);
      NotificationManager localNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
      Notification localNotification = new Notification();
      Intent localIntent = new Intent("com.kaixin001.VIEW_CHAT_LIST");
      localIntent.addFlags(67108864);
      PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, 0, localIntent, 0);
      localNotification.icon = 2130838988;
      String str1 = paramContext.getResources().getString(2131427329);
      String str2 = StringUtil.replaceTokenWith(paramContext.getResources().getString(2131428129), "*", String.valueOf(paramInt));
      localNotification.tickerText = (str1 + ":" + str2);
      localNotification.flags = (0x10 | localNotification.flags);
      localNotification.setLatestEventInfo(paramContext, str1, str2, localPendingIntent);
      if (0 != 0)
        localNotification.defaults = (0x2 | localNotification.defaults);
      if (bool1)
        localNotification.defaults = (0x4 | localNotification.defaults);
      if (bool2)
        localNotification.sound = Uri.parse("android.resource://com.kaixin001.activity/2131099652");
      localNotificationManager.notify(KaixinConst.ID_NEW_CHAT_NOTIFICATION, localNotification);
    }
  }

  public static void sendNewMsgNotification(Context paramContext, int paramInt)
  {
    if (paramContext != null)
    {
      SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
      localSharedPreferences.getBoolean("notification_vibrate_preference", true);
      boolean bool1 = localSharedPreferences.getBoolean("notification_led_preference", true);
      boolean bool2 = localSharedPreferences.getBoolean("notification_ringtone_enabled_preference", true);
      NotificationManager localNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
      Notification localNotification = new Notification();
      Intent localIntent = new Intent("com.kaixin001.VIEW_MESSAGECENTER_DETAIL");
      localIntent.addFlags(67108864);
      PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, 0, localIntent, 0);
      localNotification.icon = 2130838989;
      String str1 = paramContext.getResources().getString(2131427329);
      String str2 = StringUtil.replaceTokenWith(paramContext.getResources().getString(2131427661), "*", String.valueOf(paramInt));
      localNotification.tickerText = (str1 + ":" + str2);
      localNotification.setLatestEventInfo(paramContext, str1, str2, localPendingIntent);
      if (0 != 0)
        localNotification.defaults = (0x2 | localNotification.defaults);
      if (bool1)
        localNotification.defaults = (0x4 | localNotification.defaults);
      if (bool2)
        localNotification.sound = Uri.parse("android.resource://com.kaixin001.activity/2131099652");
      localNotificationManager.notify(KaixinConst.ID_NEW_MESSAGE_NOTIFICATION, localNotification);
    }
  }

  public void cancel()
  {
    this.bIsStop = true;
  }

  public void checkBusinessProcError(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    if ((paramString != null) && ((paramString.indexOf("error_code") != -1) || (paramString.indexOf("errno") != -1)));
    while (true)
    {
      JSONObject localJSONObject;
      int i;
      try
      {
        localJSONObject = new JSONObject(paramString);
        i = localJSONObject.optInt("error_code", 0);
        if (i != 0)
          break label241;
        i = localJSONObject.optInt("errno", 0);
        break label241;
        SecurityErrorException localSecurityErrorException1 = new SecurityErrorException();
        localSecurityErrorException1.errorNumber = i;
        localSecurityErrorException1.errMessage = localJSONObject.optString("error", paramString);
        localSecurityErrorException1.ret = localJSONObject.optInt("ret", 0);
        localSecurityErrorException1.uid = localJSONObject.optString("uid", null);
        throw localSecurityErrorException1;
      }
      catch (JSONException localJSONException)
      {
      }
      label116: lastHttpRequestCode = -999;
      return;
      label241: 
      do
      {
        if (i == 401)
        {
          if (lastHttpRequestCode == i)
            break label116;
          lastHttpRequestCode = i;
          if (!localJSONObject.optString("error").contains(String.valueOf(40101)))
            break label116;
          SecurityErrorException localSecurityErrorException3 = new SecurityErrorException();
          localSecurityErrorException3.errorNumber = 40101;
          throw localSecurityErrorException3;
        }
        if ((i != 400) || (lastHttpRequestCode == i))
          break label116;
        lastHttpRequestCode = i;
        if (!localJSONObject.optString("error").contains(String.valueOf(40051)))
          break label116;
        SecurityErrorException localSecurityErrorException2 = new SecurityErrorException();
        localSecurityErrorException2.errorNumber = 40051;
        throw localSecurityErrorException2;
        if (i == -22)
          break;
      }
      while (i != 8100);
    }
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
  public JSONObject parseJSON(Context paramContext, boolean paramBoolean, String paramString)
    throws SecurityErrorException
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: aload_3
    //   3: invokevirtual 462	com/kaixin001/engine/KXEngine:checkBusinessProcError	(Landroid/content/Context;Ljava/lang/String;)V
    //   6: aload_3
    //   7: invokestatic 197	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   10: istore 10
    //   12: iload 10
    //   14: ifeq +204 -> 218
    //   17: aconst_null
    //   18: astore 12
    //   20: aload 12
    //   22: areturn
    //   23: astore 4
    //   25: aload 4
    //   27: getfield 435	com/kaixin001/engine/SecurityErrorException:errorNumber	I
    //   30: bipush 234
    //   32: if_icmpne +36 -> 68
    //   35: aload 4
    //   37: getfield 443	com/kaixin001/engine/SecurityErrorException:ret	I
    //   40: bipush 234
    //   42: if_icmpne +24 -> 66
    //   45: invokestatic 468	android/os/Message:obtain	()Landroid/os/Message;
    //   48: astore 8
    //   50: aload 8
    //   52: sipush 10000
    //   55: putfield 471	android/os/Message:what	I
    //   58: invokestatic 476	com/kaixin001/activity/MessageHandlerHolder:getInstance	()Lcom/kaixin001/activity/MessageHandlerHolder;
    //   61: aload 8
    //   63: invokevirtual 480	com/kaixin001/activity/MessageHandlerHolder:fireMessage	(Landroid/os/Message;)V
    //   66: aconst_null
    //   67: areturn
    //   68: aload 4
    //   70: getfield 435	com/kaixin001/engine/SecurityErrorException:errorNumber	I
    //   73: sipush 8100
    //   76: if_icmpne +48 -> 124
    //   79: aload 4
    //   81: getfield 443	com/kaixin001/engine/SecurityErrorException:ret	I
    //   84: sipush 8100
    //   87: if_icmpne -21 -> 66
    //   90: invokestatic 468	android/os/Message:obtain	()Landroid/os/Message;
    //   93: astore 7
    //   95: aload 7
    //   97: sipush 8100
    //   100: putfield 471	android/os/Message:what	I
    //   103: aload 7
    //   105: aload 4
    //   107: getfield 442	com/kaixin001/engine/SecurityErrorException:errMessage	Ljava/lang/String;
    //   110: putfield 484	android/os/Message:obj	Ljava/lang/Object;
    //   113: invokestatic 476	com/kaixin001/activity/MessageHandlerHolder:getInstance	()Lcom/kaixin001/activity/MessageHandlerHolder;
    //   116: aload 7
    //   118: invokevirtual 480	com/kaixin001/activity/MessageHandlerHolder:fireMessage	(Landroid/os/Message;)V
    //   121: goto -55 -> 66
    //   124: aload 4
    //   126: getfield 435	com/kaixin001/engine/SecurityErrorException:errorNumber	I
    //   129: ldc_w 448
    //   132: if_icmpne +39 -> 171
    //   135: invokestatic 468	android/os/Message:obtain	()Landroid/os/Message;
    //   138: astore 6
    //   140: aload 6
    //   142: ldc_w 448
    //   145: putfield 471	android/os/Message:what	I
    //   148: aload 6
    //   150: aload_1
    //   151: ldc_w 485
    //   154: invokevirtual 486	android/content/Context:getString	(I)Ljava/lang/String;
    //   157: putfield 484	android/os/Message:obj	Ljava/lang/Object;
    //   160: invokestatic 476	com/kaixin001/activity/MessageHandlerHolder:getInstance	()Lcom/kaixin001/activity/MessageHandlerHolder;
    //   163: aload 6
    //   165: invokevirtual 480	com/kaixin001/activity/MessageHandlerHolder:fireMessage	(Landroid/os/Message;)V
    //   168: goto -102 -> 66
    //   171: aload 4
    //   173: getfield 435	com/kaixin001/engine/SecurityErrorException:errorNumber	I
    //   176: ldc_w 452
    //   179: if_icmpne -113 -> 66
    //   182: invokestatic 468	android/os/Message:obtain	()Landroid/os/Message;
    //   185: astore 5
    //   187: aload 5
    //   189: ldc_w 452
    //   192: putfield 471	android/os/Message:what	I
    //   195: aload 5
    //   197: aload_1
    //   198: ldc_w 487
    //   201: invokevirtual 486	android/content/Context:getString	(I)Ljava/lang/String;
    //   204: putfield 484	android/os/Message:obj	Ljava/lang/Object;
    //   207: invokestatic 476	com/kaixin001/activity/MessageHandlerHolder:getInstance	()Lcom/kaixin001/activity/MessageHandlerHolder;
    //   210: aload 5
    //   212: invokevirtual 480	com/kaixin001/activity/MessageHandlerHolder:fireMessage	(Landroid/os/Message;)V
    //   215: goto -149 -> 66
    //   218: new 139	org/json/JSONObject
    //   221: dup
    //   222: aload_3
    //   223: invokespecial 431	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   226: astore 11
    //   228: aload 11
    //   230: astore 12
    //   232: aload 12
    //   234: ifnull -214 -> 20
    //   237: aload_0
    //   238: aload 12
    //   240: invokespecial 489	com/kaixin001/engine/KXEngine:parseRet	(Lorg/json/JSONObject;)V
    //   243: iload_2
    //   244: ifeq -224 -> 20
    //   247: aload_0
    //   248: aload_1
    //   249: aload 12
    //   251: invokespecial 491	com/kaixin001/engine/KXEngine:parseMsgNotice	(Landroid/content/Context;Lorg/json/JSONObject;)V
    //   254: aload 12
    //   256: areturn
    //   257: astore 9
    //   259: ldc 21
    //   261: ldc_w 493
    //   264: aload 9
    //   266: invokestatic 122	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   269: aconst_null
    //   270: areturn
    //   271: astore 13
    //   273: ldc_w 495
    //   276: aload 13
    //   278: invokevirtual 496	java/lang/Exception:toString	()Ljava/lang/String;
    //   281: invokestatic 498	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   284: aconst_null
    //   285: astore 12
    //   287: goto -55 -> 232
    //
    // Exception table:
    //   from	to	target	type
    //   0	6	23	com/kaixin001/engine/SecurityErrorException
    //   6	12	257	org/json/JSONException
    //   218	228	257	org/json/JSONException
    //   237	243	257	org/json/JSONException
    //   247	254	257	org/json/JSONException
    //   273	284	257	org/json/JSONException
    //   218	228	271	java/lang/Exception
  }

  public void sendNewMsgNotificationBroadcast(Context paramContext, int paramInt)
  {
    clearNoticeFlag(paramContext);
    int i = Calendar.getInstance().get(11);
    KXLog.d("HttpConnection", "sendNewMsgNotificationBroadcast curHour:" + i + ", isSystemNoticeCntChange:" + MessageCenterModel.getInstance().isSystemNoticeCntChange());
    if (MessageCenterModel.getInstance().isSystemNoticeCntChange());
    do
    {
      return;
      KXLog.d("HttpConnection", "sendNewMsgNotificationBroadcast mEnableNewMessageNotification:" + this.mEnableNewMessageNotification);
      if (paramContext != null)
      {
        Intent localIntent = new Intent("com.kaixin001.NEW_MSG_NOTIFICATION");
        Bundle localBundle = new Bundle();
        localBundle.putString("notices", String.valueOf(paramInt));
        localIntent.putExtras(localBundle);
        paramContext.sendBroadcast(localIntent);
      }
      Message localMessage = Message.obtain();
      localMessage.what = 5999;
      localMessage.arg1 = paramInt;
      MessageHandlerHolder.getInstance().fireMessage(localMessage);
    }
    while (!this.mEnableNewMessageNotification);
    sendNewMsgNotification(paramContext, paramInt);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.KXEngine
 * JD-Core Version:    0.6.0
 */