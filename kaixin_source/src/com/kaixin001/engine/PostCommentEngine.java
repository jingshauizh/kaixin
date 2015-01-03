package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import org.json.JSONObject;

public class PostCommentEngine extends KXEngine
{
  private static final String LOGTAG = "PostCommentEngine";
  private static PostCommentEngine instance;
  private String msCid = null;

  public static PostCommentEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new PostCommentEngine();
      PostCommentEngine localPostCommentEngine = instance;
      return localPostCommentEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public String getCommentCid()
  {
    return this.msCid;
  }

  public int parseRecordJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
    if (localJSONObject == null);
    while (true)
    {
      return 0;
      if (this.ret != 1)
        break;
      if (!localJSONObject.has("cid"))
        continue;
      this.msCid = localJSONObject.optString("cid");
      return 1;
    }
    return this.ret;
  }

  public int postComment(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makePostCommentRequest(User.getInstance().getUID(), paramString1, paramString2, paramString3, paramString4, paramString5);
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
        KXLog.e("PostCommentEngine", "postComment error", localException);
        str2 = null;
      }
    }
    return parseRecordJSON(paramContext, str2);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.PostCommentEngine
 * JD-Core Version:    0.6.0
 */