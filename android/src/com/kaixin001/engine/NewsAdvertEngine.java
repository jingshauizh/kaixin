package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.kaixin001.item.AdvertItem;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;

public class NewsAdvertEngine extends KXEngine
{
  private static final String LOGTAG = "NewsAdvertEngine";
  private static NewsAdvertEngine instance;

  public static NewsAdvertEngine getInstance()
  {
    
  
      if (instance == null)
        instance = new NewsAdvertEngine();
      NewsAdvertEngine localNewsAdvertEngine = instance;
      return localNewsAdvertEngine;
  
  }

  public int getAdvert(Context paramContext)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetAdvertRequest();
    Log.e("str1=",str1);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    Log.e("getAdvert =","11111111111111111111111");
    String str2;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      Log.e("str3=",str3);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return -1002;
    }
    catch (Exception localException)
    {
      
        KXLog.e("NewsAdvertEngine", "getGiftList error", localException);
        str2 = null;
     
    }
    return parseAdvertJSON(paramContext, str2);
  }

  // ERROR //
  public int parseAdvertJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
   return -1;
  }

  public void sendAdvertClose(Context paramContext)
  {
    int i = 111;
    String str = Protocol.getInstance().makeAdvertCloseRequest(String.valueOf(i));
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      localHttpProxy.httpGet(str, null, null);
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("NewsAdvertEngine", "getGiftList error", localException);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.NewsAdvertEngine
 * JD-Core Version:    0.6.0
 */