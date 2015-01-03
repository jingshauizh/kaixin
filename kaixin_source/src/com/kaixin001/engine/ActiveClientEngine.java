package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.db.ActiveDBAdapter;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.network.HttpMethod;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;

public class ActiveClientEngine extends KXEngine
{
  private static final String LOGTAG = "ActiveClientEngine";
  private static ActiveClientEngine instance;

  public static ActiveClientEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new ActiveClientEngine();
      ActiveClientEngine localActiveClientEngine = instance;
      return localActiveClientEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean activeClient(Context paramContext, String paramString1, String paramString2, String paramString3)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeActiveClientRequest(paramString1, paramString2, paramString3);
    HttpConnection localHttpConnection = new HttpConnection(paramContext);
    String str2;
    try
    {
      String str3 = localHttpConnection.httpRequest(str1, HttpMethod.GET, null, null, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return false;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("ActiveClientEngine", "activeClient error", localException);
        str2 = null;
      }
    }
    return parseActiveClientJSON(paramContext, str2);
  }

  public boolean loadActiveFlag(Context paramContext)
  {
    ActiveDBAdapter localActiveDBAdapter = new ActiveDBAdapter(paramContext);
    try
    {
      String str = User.getInstance().getUID();
      if (TextUtils.isEmpty(str))
        str = "client_uid" + Protocol.getShortVersion();
      boolean bool = localActiveDBAdapter.getActiveFlagById(str);
      return bool;
    }
    catch (Exception localException)
    {
      KXLog.e("ActiveClientEngine", "loadActiveFlag error", localException);
      return false;
    }
    finally
    {
      localActiveDBAdapter.close();
    }
    throw localObject;
  }

  public boolean parseActiveClientJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    if (super.parseJSON(paramContext, false, paramString) == null);
    do
      return false;
    while (this.ret <= 0);
    return true;
  }

  public boolean saveActiveFlag(Context paramContext, String paramString1, String paramString2)
  {
    ActiveDBAdapter localActiveDBAdapter = new ActiveDBAdapter(paramContext);
    while (true)
    {
      try
      {
        String str = User.getInstance().getUID();
        if (!TextUtils.isEmpty(str))
          continue;
        str = "client_uid" + Protocol.getShortVersion();
        long l = localActiveDBAdapter.insertOrUpdateActive(str, paramString1, paramString2);
        if (l != -1L)
          return true;
      }
      catch (Exception localException)
      {
        KXLog.e("ActiveClientEngine", "saveActiveFlag", localException);
        return false;
      }
      finally
      {
        localActiveDBAdapter.close();
      }
      localActiveDBAdapter.close();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.ActiveClientEngine
 * JD-Core Version:    0.6.0
 */