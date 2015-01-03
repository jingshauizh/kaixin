package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class FeedbackEngine extends KXEngine
{
  private static final String LOGTAG = "FeedbackEngine";
  private static FeedbackEngine instance;
  private int INSERT_OK = 1;

  public static FeedbackEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new FeedbackEngine();
      FeedbackEngine localFeedbackEngine = instance;
      return localFeedbackEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean parseFeedbackJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
    if (localJSONObject == null)
      return false;
    if (this.ret == 1)
      try
      {
        int j = localJSONObject.getInt("insert_ok");
        int k = this.INSERT_OK;
        i = 0;
        if (j == k)
          i = 1;
        return i;
      }
      catch (JSONException localJSONException)
      {
        while (true)
        {
          localJSONException.printStackTrace();
          int i = 0;
        }
      }
    return false;
  }

  public Boolean postFeedbackRequest(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    User localUser = User.getInstance();
    String str1 = Protocol.getInstance().makeFeedbackRequest(localUser.getUID());
    HashMap localHashMap = new HashMap();
    localHashMap.put("content", paramString);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpPost(str1, localHashMap, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return Boolean.valueOf(false);
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("FeedbackEngine", "postFeedbackRequest error", localException);
        str2 = null;
      }
    }
    return Boolean.valueOf(parseFeedbackJSON(paramContext, str2));
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.FeedbackEngine
 * JD-Core Version:    0.6.0
 */