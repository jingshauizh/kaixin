package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.HashMap;

public class SocialShareEngine extends KXEngine
{
  private static final String TAG = "SocialShareEngine";
  private static SocialShareEngine instance;

  public static SocialShareEngine getInstance()
  {
    if (instance == null)
      instance = new SocialShareEngine();
    return instance;
  }

  public boolean shareToQzone(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    String str1 = Protocol.getInstance().shareQZone();
    HashMap localHashMap = new HashMap();
    localHashMap.put("title", paramString3);
    localHashMap.put("url", str1);
    localHashMap.put("comment", paramString5);
    localHashMap.put("summary", paramString4);
    localHashMap.put("accesstoken", paramString2);
    localHashMap.put("openid", paramString1);
    localHashMap.put("uid", User.getInstance().getUID());
    localHashMap.put("api_version", "3.9.9");
    localHashMap.put("version", "android-3.9.9");
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpPost(str1, localHashMap, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return false;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("SocialShareEngine", "share qzone error", localException);
        String str2 = null;
      }
    }
    return true;
  }

  public boolean shareToWeibo(Context paramContext, String paramString1, String paramString2)
  {
    String str1 = Protocol.getInstance().shareWeibo(paramString1, paramString2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return false;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("SocialShareEngine", "share weibo error", localException);
        String str2 = null;
      }
    }
    return true;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.SocialShareEngine
 * JD-Core Version:    0.6.0
 */