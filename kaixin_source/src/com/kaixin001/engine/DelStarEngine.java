package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;

public class DelStarEngine extends KXEngine
{
  private static final String LOGTAG = "DelStarEngine";
  private static DelStarEngine instance;

  public static DelStarEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new DelStarEngine();
      DelStarEngine localDelStarEngine = instance;
      return localDelStarEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean delStar(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeDelStarRequest(User.getInstance().getUID(), paramString);
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
        KXLog.e("DelStarEngine", "delStar error", localException);
        str2 = null;
      }
    }
    return parseDelStarJSON(paramContext, str2);
  }

  public boolean parseDelStarJSON(Context paramContext, String paramString)
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
 * Qualified Name:     com.kaixin001.engine.DelStarEngine
 * JD-Core Version:    0.6.0
 */