package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;

public class UpEngine extends KXEngine
{
  private static final String LOGTAG = "UpEngine";
  private static UpEngine instance;

  public static UpEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new UpEngine();
      UpEngine localUpEngine = instance;
      return localUpEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int parseUpJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    if (super.parseJSON(paramContext, paramString) == null)
      return -1;
    return this.ret;
  }

  public int postUp(Context paramContext, String paramString1, String paramString2, String paramString3)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makePostUpRequest(User.getInstance().getUID(), paramString1, paramString2, paramString3);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return 0;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("UpEngine", "postUp error", localException);
        str2 = null;
      }
    }
    return parseUpJSON(paramContext, str2);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.UpEngine
 * JD-Core Version:    0.6.0
 */