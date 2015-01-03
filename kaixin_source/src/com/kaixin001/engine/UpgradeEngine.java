package com.kaixin001.engine;

import android.content.Context;
import com.kaixin001.model.UpgradeModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import org.json.JSONException;
import org.json.JSONObject;

public class UpgradeEngine extends KXEngine
{
  private static final String LOGTAG = "UpgradeEngine";
  private static UpgradeEngine instance = null;

  public static UpgradeEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new UpgradeEngine();
      UpgradeEngine localUpgradeEngine = instance;
      return localUpgradeEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean checkUpgradeInformation(Context paramContext)
  {
    String str1 = Protocol.getInstance().makeCheckUpgradeRequest(User.getInstance().getUID());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (str2 == null)
        return false;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("UpgradeEngine", "checkUpgradeInformation error", localException);
        str2 = null;
      }
      try
      {
        JSONObject localJSONObject = new JSONObject(str2);
        if (localJSONObject.getInt("ret") == 1)
        {
          UpgradeModel.getInstance().setUpgradeInformation(localJSONObject.getJSONArray("clients"));
          return true;
        }
        UpgradeModel.getInstance().setUpgradeInformation(null);
        return false;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        UpgradeModel.getInstance().setUpgradeInformation(null);
      }
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.UpgradeEngine
 * JD-Core Version:    0.6.0
 */