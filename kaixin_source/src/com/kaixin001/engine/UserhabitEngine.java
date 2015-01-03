package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class UserhabitEngine extends KXEngine
{
  private static UserhabitEngine instance;

  public static UserhabitEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new UserhabitEngine();
      UserhabitEngine localUserhabitEngine = instance;
      return localUserhabitEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int parseUserhabitResult(String paramString)
  {
    try
    {
      int i = new JSONObject(paramString).optInt("ret", 0);
      return i;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return -1001;
  }

  public int uploadUserHabitMulti(Context paramContext, String paramString)
  {
    String str1 = Protocol.getInstance().makeUserHabitMultiUrl(User.getInstance().getUID());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    HashMap localHashMap = new HashMap();
    localHashMap.put("multi_statflag", paramString);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpPost(str1, localHashMap, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return -1002;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        str2 = null;
      }
    }
    return parseUserhabitResult(str2);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.UserhabitEngine
 * JD-Core Version:    0.6.0
 */