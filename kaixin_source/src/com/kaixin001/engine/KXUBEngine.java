package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.HashMap;

public class KXUBEngine extends KXEngine
{
  private static final String LOGTAG = "KXUBEngine";
  private static KXUBEngine sInstance = null;

  public static KXUBEngine getInstance()
  {
    monitorenter;
    try
    {
      if (sInstance == null)
        sInstance = new KXUBEngine();
      KXUBEngine localKXUBEngine = sInstance;
      return localKXUBEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean uploadUBLog(Context paramContext, String paramString)
  {
    int i = 1;
    if ((paramContext == null) || (TextUtils.isEmpty(paramString)))
      i = 0;
    while (true)
    {
      return i;
      String str1 = Protocol.getInstance().makeUploadUserBehaviorRequest();
      HashMap localHashMap = new HashMap();
      localHashMap.put("content", paramString);
      HttpProxy localHttpProxy = new HttpProxy(paramContext);
      try
      {
        String str3 = localHttpProxy.httpPost(str1, localHashMap, null, null);
        str2 = str3;
      }
      catch (Exception localException1)
      {
        try
        {
          while (true)
          {
            if (super.parseJSON(paramContext, str2) != null)
            {
              int j = this.ret;
              if (j == i)
                break;
            }
            return false;
            localException1 = localException1;
            KXLog.e("KXUBEngine", "doPostDiary error", localException1);
            String str2 = null;
          }
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
      }
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.KXUBEngine
 * JD-Core Version:    0.6.0
 */