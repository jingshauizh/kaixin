package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.FriendInfo;
import com.kaixin001.model.NewsDetailVisitorsModel;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewsDetailVisitorEngine extends KXEngine
{
  public static final int CODE_ERROR_OTHER = 0;
  public static final int CODE_OK = 1;
  private static final String LOGTAG = "NewsDetailVisitorEngine";
  private static NewsDetailVisitorEngine instance;
  private String mId;
  private String mType;
  private String mUid;

  public static NewsDetailVisitorEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new NewsDetailVisitorEngine();
      NewsDetailVisitorEngine localNewsDetailVisitorEngine = instance;
      return localNewsDetailVisitorEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private boolean parseVisitorsJSON(Context paramContext, String paramString, NewsDetailVisitorsModel paramNewsDetailVisitorsModel)
    throws JSONException
  {
    int i = 0;
    if (paramNewsDetailVisitorsModel == null)
      return false;
    int k;
    int m;
    try
    {
      JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
      if (localJSONObject1 == null)
      {
        i = 0;
      }
      else
      {
        int j = this.ret;
        i = 0;
        if (j == 1)
        {
          paramNewsDetailVisitorsModel.clear();
          JSONArray localJSONArray = localJSONObject1.getJSONArray("data");
          k = localJSONArray.length();
          if (k <= 0)
            break label162;
          m = 0;
          break label155;
          JSONObject localJSONObject2 = localJSONArray.getJSONObject(m);
          FriendInfo localFriendInfo = new FriendInfo(localJSONObject2.optString("name", ""), localJSONObject2.optString("uid", ""), localJSONObject2.optString("url", ""));
          localFriendInfo.setType(localJSONObject2.optString("type", "like"));
          paramNewsDetailVisitorsModel.addFriend(localFriendInfo);
          m++;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    while (true)
    {
      return i;
      label155: if (m < k)
        break;
      label162: i = 1;
    }
  }

  public boolean doGetVisitorsRequest(Context paramContext, String paramString1, String paramString2, String paramString3, NewsDetailVisitorsModel paramNewsDetailVisitorsModel)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetVisitorsRequest(paramString1, paramString2, paramString3);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      boolean bool1 = TextUtils.isEmpty(str2);
      i = 0;
      if (bool1);
    }
    catch (Exception localException)
    {
      try
      {
        boolean bool2 = parseVisitorsJSON(paramContext, str2, paramNewsDetailVisitorsModel);
        int i = bool2;
        return i;
        localException = localException;
        KXLog.e("NewsDetailVisitorEngine", "doGetRecordDetailRequest error", localException);
        String str2 = null;
      }
      catch (JSONException localJSONException)
      {
        KXLog.e("NewsDetailVisitorEngine", localJSONException.getMessage());
      }
    }
    return false;
  }

  public String getId()
  {
    return this.mId;
  }

  public String getType()
  {
    return this.mType;
  }

  public String getUid()
  {
    return this.mUid;
  }

  public void setId(String paramString)
  {
    this.mId = paramString;
  }

  public void setType(String paramString)
  {
    this.mType = paramString;
  }

  public void setUid(String paramString)
  {
    this.mUid = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.NewsDetailVisitorEngine
 * JD-Core Version:    0.6.0
 */