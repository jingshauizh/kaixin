package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import org.json.JSONObject;

public class AddVerifyEngine extends KXEngine
{
  private static final String LOGTAG = "AddVerifyEngine";
  private static AddVerifyEngine instance;
  private String strTipMsg;

  public static AddVerifyEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new AddVerifyEngine();
      AddVerifyEngine localAddVerifyEngine = instance;
      return localAddVerifyEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean addVerify(Context paramContext, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeAddVerifyRequest(User.getInstance().getUID(), paramString1, paramString2);
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
        KXLog.e("AddVerifyEngine", "addVerify error", localException);
        str2 = null;
      }
    }
    return parseAddVerifyJSON(paramContext, str2);
  }

  public String getTipMsg()
  {
    return this.strTipMsg;
  }

  public boolean parseAddVerifyJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
    if (localJSONObject == null);
    do
    {
      return false;
      if (!Setting.getInstance().isDebug())
        continue;
      KXLog.d("parseAddVerifyJSON", "strContent=" + localJSONObject.toString());
    }
    while (this.ret <= 0);
    try
    {
      this.strTipMsg = localJSONObject.getString("tipmsg");
      return true;
    }
    catch (Exception localException)
    {
      KXLog.e("AddVerifyEngine", "parseAddVerifyJSON", localException);
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.AddVerifyEngine
 * JD-Core Version:    0.6.0
 */