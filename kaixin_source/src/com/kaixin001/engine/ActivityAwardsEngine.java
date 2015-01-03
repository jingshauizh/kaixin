package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.ActivityAwardItem;
import com.kaixin001.model.ActivityAwardModel;
import com.kaixin001.model.KaixinModelTemplate;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class ActivityAwardsEngine extends KXEngine
{
  private static final String ITEM_CACHE_FILE = "activity_award";
  public static final int NUM = 10;
  public static final int RESULT_FAILED = 0;
  public static final int RESULT_FAILED_NETWORK = -2;
  public static final int RESULT_FAILED_PARSE = -1;
  public static final int RESULT_OK = 1;
  private static final String TAG = "ActivityAwardsEngine";
  private static ActivityAwardsEngine instance = null;
  private String mLastError = "";

  private ArrayList<ActivityAwardItem> getAwardList(JSONArray paramJSONArray)
  {
    if (paramJSONArray == null);
    for (int i = 0; i == 0; i = paramJSONArray.length())
    {
      localArrayList = null;
      return localArrayList;
    }
    ArrayList localArrayList = new ArrayList(i);
    int j = 0;
    label34: JSONObject localJSONObject;
    if (j < i)
    {
      localJSONObject = paramJSONArray.optJSONObject(j);
      if (localJSONObject != null)
        break label59;
    }
    while (true)
    {
      j++;
      break label34;
      break;
      label59: ActivityAwardItem localActivityAwardItem = new ActivityAwardItem();
      localActivityAwardItem.id = localJSONObject.optString("rid");
      localActivityAwardItem.uid = localJSONObject.optString("uid");
      localActivityAwardItem.aid = localJSONObject.optString("aid");
      localActivityAwardItem.title = localJSONObject.optString("title");
      localActivityAwardItem.title = localJSONObject.optString("title");
      localActivityAwardItem.content = localJSONObject.optString("detail");
      localActivityAwardItem.type = localJSONObject.optInt("type", -1);
      localActivityAwardItem.used = localJSONObject.optInt("status", -1);
      localArrayList.add(localActivityAwardItem);
    }
  }

  public static ActivityAwardsEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new ActivityAwardsEngine();
      ActivityAwardsEngine localActivityAwardsEngine = instance;
      return localActivityAwardsEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int doDeleteAwardRequest(Context paramContext, ActivityAwardModel paramActivityAwardModel, String paramString)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeLBSDeleteAwardRequest(User.getInstance().getUID(), paramString);
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
      do
      {
        String str2;
        while (true)
        {
          KXLog.e("ActivityAwardsEngine", "GetAwardsRequest error", localException);
          str2 = null;
        }
        KXLog.d("GetAwardsRequest", "strContent=" + str2);
        if (super.parseJSON(paramContext, str2) == null)
          return -1;
      }
      while (this.ret != 1);
    }
    return 1;
  }

  public int doGetAwardsRequest(Context paramContext, ActivityAwardModel paramActivityAwardModel, int paramInt1, int paramInt2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeLBSGetAwardsRequest(User.getInstance().getUID(), paramInt1, paramInt2);
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
        KXLog.e("ActivityAwardsEngine", "GetAwardsRequest error", localException);
        str2 = null;
      }
      KXLog.d("GetAwardsRequest", "strContent=" + str2);
      JSONObject localJSONObject = super.parseJSON(paramContext, str2);
      if (localJSONObject == null)
        return -1;
      if (this.ret == 1)
      {
        int i = localJSONObject.optInt("total", 0);
        JSONArray localJSONArray = localJSONObject.optJSONArray("awards");
        paramActivityAwardModel.awards.setItemList(i, getAwardList(localJSONArray), paramInt1);
        return 1;
      }
    }
    return 0;
  }

  public int doUseAwardRequest(Context paramContext, ActivityAwardModel paramActivityAwardModel, String paramString)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeLBSUseAwardRequest(User.getInstance().getUID(), paramString);
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
      do
      {
        String str2;
        while (true)
        {
          KXLog.e("ActivityAwardsEngine", "GetAwardsRequest error", localException);
          str2 = null;
        }
        KXLog.d("GetAwardsRequest", "strContent=" + str2);
        if (super.parseJSON(paramContext, str2) == null)
          return -1;
      }
      while (this.ret != 1);
    }
    return 1;
  }

  public String getLastError()
  {
    return this.mLastError;
  }

  public ArrayList<ActivityAwardItem> loadCheckInItemCache(Context paramContext, String paramString, int paramInt)
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString)));
    do
      return null;
    while (!TextUtils.isEmpty(FileUtil.getCacheData(FileUtil.getKXCacheDir(paramContext), paramString, "activity_award" + paramInt)));
    return null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.ActivityAwardsEngine
 * JD-Core Version:    0.6.0
 */