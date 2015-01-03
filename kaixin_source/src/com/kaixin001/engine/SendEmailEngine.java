package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.network.HttpMethod;
import com.kaixin001.network.Protocol;
import com.kaixin001.network.XAuth;
import com.kaixin001.util.KXLog;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class SendEmailEngine extends KXEngine
{
  private static final String LOGTAG = "SendEmailEngine";
  private static SendEmailEngine instance;

  public static SendEmailEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new SendEmailEngine();
      SendEmailEngine localSendEmailEngine = instance;
      return localSendEmailEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int parseRecordJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
    if (localJSONObject == null)
      return 0;
    boolean bool = paramString.contains("result");
    int i = 0;
    if (bool)
      i = localJSONObject.optInt("result", 0);
    return i;
  }

  public int sendEmail(Context paramContext, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    HashMap localHashMap = new HashMap();
    String str1 = Protocol.getInstance().getForgetPwd();
    String str2 = Protocol.getInstance().makeGetPwdUrl(XAuth.encode(paramString1), paramString2);
    localHashMap.put("appid", paramString2);
    localHashMap.put("username", paramString1);
    localHashMap.put("sig", XAuth.getParamsForEmail(str2));
    HttpConnection localHttpConnection = new HttpConnection(paramContext);
    String str3;
    try
    {
      String str4 = localHttpConnection.httpRequest(str1, HttpMethod.POST, localHashMap, null, null, null);
      str3 = str4;
      if (TextUtils.isEmpty(str3))
        return 0;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("SendEmailEngine", "sendEmail error", localException);
        str3 = null;
      }
    }
    return parseRecordJSON(paramContext, str3);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.SendEmailEngine
 * JD-Core Version:    0.6.0
 */