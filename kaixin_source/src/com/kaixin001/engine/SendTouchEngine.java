package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.network.HttpMethod;
import com.kaixin001.network.Protocol;
import com.kaixin001.network.XAuth;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class SendTouchEngine extends KXEngine
{
  private static final String LOGTAG = "SendTouchEngine";
  public static final int SEND_FAILED = -2001;
  public static final int SEND_FAILL = -6;
  public static final int SEND_NOT_EXIST = -2;
  public static final int SEND_SUC = 1;
  private static SendTouchEngine instance;
  protected String strErrMsg = "";

  public static SendTouchEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new SendTouchEngine();
      SendTouchEngine localSendTouchEngine = instance;
      return localSendTouchEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int parseRecordJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
    if (localJSONObject == null)
      return 0;
    if ((localJSONObject.optString("result") != null) && (localJSONObject.optString("result").equals("1")))
      return 1;
    return Integer.valueOf(localJSONObject.optString("result")).intValue();
  }

  public int sendTouch(Context paramContext, String paramString1, String paramString2, String paramString3, boolean paramBoolean, int paramInt)
    throws SecurityErrorException
  {
    HashMap localHashMap = new HashMap();
    String str1 = Protocol.getInstance().getSendTouchToken();
    localHashMap.put("ctype", Setting.getInstance().getCType());
    localHashMap.put("oauth_token", User.getInstance().getOauthToken());
    localHashMap.put("touid", paramString1);
    String str2;
    if (paramBoolean)
      str2 = "1";
    String str3;
    while (true)
    {
      localHashMap.put("is_privacy", str2);
      localHashMap.put("type", Integer.toString(paramInt));
      localHashMap.put("smid", paramString2);
      localHashMap.put("is_diff_action", "1");
      XAuth.generateURLParams(str1, HttpMethod.GET.name(), localHashMap, XAuth.CONSUMER_KEY, XAuth.CONSUMER_SECRET, User.getInstance().getOauthTokenSecret());
      HttpConnection localHttpConnection = new HttpConnection(paramContext);
      try
      {
        String str4 = localHttpConnection.httpRequest(str1, HttpMethod.GET, localHashMap, null, null, null);
        str3 = str4;
        if (TextUtils.isEmpty(str3))
        {
          return 0;
          str2 = "0";
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          localException.printStackTrace();
          str3 = null;
        }
      }
    }
    return parseRecordJSON(paramContext, str3);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.SendTouchEngine
 * JD-Core Version:    0.6.0
 */