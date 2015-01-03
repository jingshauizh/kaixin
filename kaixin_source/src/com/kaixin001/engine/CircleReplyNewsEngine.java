package com.kaixin001.engine;

import android.content.Context;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import org.json.JSONObject;

public class CircleReplyNewsEngine extends KXEngine
{
  private static final String LOGTAG = "CircleReplyNewsEngine";
  public static final int RESULT_FAILED = 0;
  public static final int RESULT_FAILED_NETWORK = -2;
  public static final int RESULT_FAILED_NO_PERMISSION = -3002;
  public static final int RESULT_FAILED_PARSE = -1;
  public static final int RESULT_OK = 1;
  private static CircleReplyNewsEngine instance = null;
  public String content;
  public long ctime;
  private String mLastError = "";
  public String rtid;
  public String stid;

  public static CircleReplyNewsEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CircleReplyNewsEngine();
      CircleReplyNewsEngine localCircleReplyNewsEngine = instance;
      return localCircleReplyNewsEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int doPostCircleNewsReply(Context paramContext, String paramString1, String paramString2, String paramString3)
    throws SecurityErrorException
  {
    this.rtid = null;
    this.content = null;
    String str1 = Protocol.getInstance().makeCircleTalkReply(paramString1, paramString2, paramString3);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (str2 == null)
        return 0;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("CircleReplyNewsEngine", "doPostCircleNewsReply error", localException);
        str2 = null;
      }
      KXLog.d("doPostCircleNewsReply", "strContent=" + str2);
      JSONObject localJSONObject1 = super.parseJSON(paramContext, str2);
      if (localJSONObject1 == null)
        return -1;
      if (this.ret == 1)
      {
        JSONObject localJSONObject2 = localJSONObject1.optJSONObject("result");
        this.stid = paramString2;
        this.rtid = localJSONObject2.optString("rtid", null);
        this.ctime = localJSONObject1.optLong("ctime", 0L);
        this.content = paramString3;
        return 1;
      }
      if (localJSONObject1.optInt("code", 0) == 3002)
        return -3002;
    }
    return 0;
  }

  public String getLastError()
  {
    return this.mLastError;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.CircleReplyNewsEngine
 * JD-Core Version:    0.6.0
 */