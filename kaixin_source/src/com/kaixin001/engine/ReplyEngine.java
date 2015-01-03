package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import org.json.JSONObject;

public class ReplyEngine extends KXEngine
{
  public static final int ERROR_REPLY_COMMENT_NO_PRIVILEGE = -5;
  private static final String LOGTAG = "ReplyEngine";
  private static ReplyEngine instance;

  public static ReplyEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new ReplyEngine();
      ReplyEngine localReplyEngine = instance;
      return localReplyEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int parseRecordJSON(Context paramContext, String paramString, StringBuilder paramStringBuilder)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
    if (localJSONObject == null);
    do
    {
      return 0;
      if (this.ret != 1)
        continue;
      if (paramStringBuilder != null)
        paramStringBuilder.append(localJSONObject.optString("cid"));
      return 1;
    }
    while (this.ret != -5);
    return -5;
  }

  public int postReply(Context paramContext, String paramString1, String paramString2, String paramString3, StringBuilder paramStringBuilder)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makePostReplyRequest(User.getInstance().getUID(), paramString1, paramString2, paramString3);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return 0;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("ReplyEngine", "postReply error", localException);
        str2 = null;
      }
    }
    return parseRecordJSON(paramContext, str2, paramStringBuilder);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.ReplyEngine
 * JD-Core Version:    0.6.0
 */