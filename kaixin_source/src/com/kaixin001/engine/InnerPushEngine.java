package com.kaixin001.engine;

import android.content.Context;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import org.json.JSONObject;

public class InnerPushEngine extends KXEngine
{
  private static final String TAG = "InnerPushEngine";
  private static InnerPushEngine instance;
  public static final String str = "http://ksa2.kaixin002.com/capi/rest.php?rt=xml&method=account.getlastonlinetime&version=iphone-2.1.6&verify=68959080_68959080_1365994614_9a21126631668642c9e531fb59cabc3b_03502iphoneclient_kx";
  private String latestTime = "";

  public static InnerPushEngine getInstance()
  {
    if (instance == null)
      instance = new InnerPushEngine();
    return instance;
  }

  private void parseJson(JSONObject paramJSONObject)
  {
    this.ret = paramJSONObject.optInt("ret", 0);
    this.latestTime = paramJSONObject.optString("lastOnlineTime", "");
  }

  public String getLatestTime()
  {
    return this.latestTime;
  }

  public void getWebLoginTime(Context paramContext, String paramString)
    throws Exception
  {
    String str1 = Protocol.getInstance().getWebLoginTime(paramString);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      parseJson(super.parseJSON(paramContext, str2));
      return;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("InnerPushEngine", "getWebLoginTime error", localException);
        String str2 = null;
      }
    }
  }

  public void runEngine(Context paramContext)
  {
    if ((User.getInstance().getUID() == null) || (User.getInstance().getUID().equals("")))
      return;
    try
    {
      getWebLoginTime(paramContext, User.getInstance().getUID());
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      KXLog.e("InnerPushEngine", "InnerPushEngine error", localException);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.InnerPushEngine
 * JD-Core Version:    0.6.0
 */