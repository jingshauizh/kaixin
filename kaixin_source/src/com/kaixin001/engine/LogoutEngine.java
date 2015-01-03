package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;

public class LogoutEngine extends KXEngine
{
  private static final String LOGTAG = "LogoutEngine";
  private static LogoutEngine instance;

  public static LogoutEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new LogoutEngine();
      LogoutEngine localLogoutEngine = instance;
      return localLogoutEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean logout(Context paramContext)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeLogoutRequest(User.getInstance().getUID());
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
        KXLog.e("LogoutEngine", "logout error", localException);
        str2 = null;
      }
    }
    return parseLogoutJSON(paramContext, str2);
  }

  public boolean parseLogoutJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    if (super.parseJSON(paramContext, paramString) == null);
    do
      return false;
    while (this.ret != 1);
    return true;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.LogoutEngine
 * JD-Core Version:    0.6.0
 */