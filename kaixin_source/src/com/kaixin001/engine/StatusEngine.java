package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;

public class StatusEngine extends KXEngine
{
  private static final String LOGTAG = "StatusEngine";
  private static StatusEngine instance;

  public static StatusEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new StatusEngine();
      StatusEngine localStatusEngine = instance;
      return localStatusEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean parseStatusJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    if (super.parseJSON(paramContext, paramString) == null);
    do
      return false;
    while (this.ret != 1);
    return true;
  }

  public boolean updateStatus(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeStatusRequest(User.getInstance().getUID(), paramString);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
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
        KXLog.e("StatusEngine", "updateStatus error", localException);
        str2 = null;
      }
    }
    return parseStatusJSON(paramContext, str2);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.StatusEngine
 * JD-Core Version:    0.6.0
 */