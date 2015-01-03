package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.RepostHomeInfo;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.RepostListHomeModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class RepostListHomeEngine extends KXEngine
{
  private static final String LOGTAG = "RepostListHomeEngine";
  public static final int NUM = 10;
  private static RepostListHomeEngine instance;

  public static RepostListHomeEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new RepostListHomeEngine();
      RepostListHomeEngine localRepostListHomeEngine = instance;
      return localRepostListHomeEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private ArrayList<RepostHomeInfo> parseRepostHomeList(JSONArray paramJSONArray)
  {
    ArrayList localArrayList;
    if (paramJSONArray == null)
      localArrayList = null;
    while (true)
    {
      return localArrayList;
      localArrayList = new ArrayList();
      try
      {
        int i = paramJSONArray.length();
        for (int j = 0; j < i; j++)
        {
          JSONObject localJSONObject = paramJSONArray.getJSONObject(j);
          RepostHomeInfo localRepostHomeInfo = new RepostHomeInfo();
          localRepostHomeInfo.id = localJSONObject.getString("id");
          localRepostHomeInfo.strctime = localJSONObject.getString("strctime");
          localRepostHomeInfo.title = localJSONObject.getString("title");
          localRepostHomeInfo.type = localJSONObject.getInt("category");
          localArrayList.add(localRepostHomeInfo);
        }
      }
      catch (Exception localException)
      {
        KXLog.e("RepostListHomeEngine", "parseDiaryList", localException);
      }
    }
    return localArrayList;
  }

  public boolean getRepostList(Context paramContext, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
    throws SecurityErrorException
  {
    int i;
    if ((paramString3 != null) && (paramString3.compareTo("1") == 0))
      if (paramBoolean)
        i = NewsModel.getInstance().getRepostNum();
    String str2;
    while (true)
    {
      String str1 = Protocol.getInstance().makeGetRepostListRequest(paramString1, paramString2, paramString3, User.getInstance().getUID(), i);
      HttpProxy localHttpProxy = new HttpProxy(paramContext);
      try
      {
        String str3 = localHttpProxy.httpGet(str1, null, null);
        str2 = str3;
        if (TextUtils.isEmpty(str2))
        {
          return false;
          i = 10;
          continue;
          str1 = Protocol.getInstance().makeGetRepostListRequest(paramString1, paramString2, paramString3, User.getInstance().getUID(), 10);
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          KXLog.e("RepostListHomeEngine", "getRepostList error", localException);
          str2 = null;
        }
      }
    }
    return parseRepostListJSON(paramContext, str2, paramString3);
  }

  public boolean parseRepostListJSON(Context paramContext, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString1);
    if (localJSONObject == null)
      return false;
    if (this.ret == 1)
    {
      try
      {
        ArrayList localArrayList = parseRepostHomeList(localJSONObject.getJSONArray("replists"));
        int i = localJSONObject.optInt("total", 0);
        if ((paramString2 != null) && (paramString2.compareTo("1") == 0))
        {
          RepostListHomeModel.getInstance().setMyRepostList(localArrayList);
          RepostListHomeModel.getInstance().setMyTotal(i);
          break label118;
        }
        RepostListHomeModel.getInstance().setAllRepostList(localArrayList);
        RepostListHomeModel.getInstance().setAllTotal(i);
      }
      catch (Exception localException)
      {
        KXLog.e("RepostListHomeEngine", "parseRepostListJSON", localException);
        return false;
      }
    }
    else
    {
      KXLog.d("RepostListHomeEngine", paramString1);
      return false;
    }
    label118: return true;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.RepostListHomeEngine
 * JD-Core Version:    0.6.0
 */