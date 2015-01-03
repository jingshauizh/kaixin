package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.CircleModel;
import com.kaixin001.model.CircleModel.CircleItem;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CirclesEngine extends KXEngine
{
  private static final String CACHE_FILE_CIRCLES = "circles.kx";
  private static final String LOGTAG = "CirclesEngine";
  public static final int NUM = 10;
  public static final int RESULT_FAILED = 0;
  public static final int RESULT_FAILED_NETWORK = -2;
  public static final int RESULT_FAILED_PARSE = -1;
  public static final int RESULT_OK = 1;
  private static CirclesEngine instance = null;
  CircleModel circleModel = CircleModel.getInstance();
  private String mLastError = "";

  private ArrayList<CircleModel.CircleItem> getCircleList(JSONArray paramJSONArray)
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
      label59: CircleModel.CircleItem localCircleItem = new CircleModel.CircleItem();
      localCircleItem.gid = localJSONObject.optString("gid");
      localCircleItem.gname = localJSONObject.optString("name");
      localCircleItem.type = localJSONObject.optInt("logo", 0);
      localCircleItem.mtime = localJSONObject.optString("mtime");
      localCircleItem.hasnews = localJSONObject.optInt("new_talk", 0);
      localArrayList.add(localCircleItem);
    }
  }

  public static CirclesEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CirclesEngine();
      CirclesEngine localCirclesEngine = instance;
      return localCirclesEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void saveCacheFile(Context paramContext, String paramString1, String paramString2)
  {
    FileUtil.setCacheData(FileUtil.getKXCacheDir(paramContext), paramString2, "circles.kx", paramString1);
  }

  public int doGetCircleList(Context paramContext, int paramInt1, int paramInt2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeCircleList(paramInt1, paramInt2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (paramInt1 == 0)
        this.circleModel.clearCircles();
      if (str2 == null)
        return 0;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("CirclesEngine", "doGetCircleList error", localException);
        str2 = null;
      }
      JSONObject localJSONObject1 = super.parseJSON(paramContext, str2);
      if (localJSONObject1 == null)
        return -1;
      if (this.ret == 1)
      {
        JSONObject localJSONObject2 = localJSONObject1.optJSONObject("result");
        int i = localJSONObject2.optInt("total", 0);
        JSONArray localJSONArray = localJSONObject2.optJSONArray("infos");
        this.circleModel.setCircles(i, getCircleList(localJSONArray), paramInt1);
        if (paramInt1 == 0)
          saveCacheFile(paramContext, str2, User.getInstance().getUID());
        return 1;
      }
    }
    return 0;
  }

  public String getLastError()
  {
    return this.mLastError;
  }

  public ArrayList<CircleModel.CircleItem> loadCache(Context paramContext, String paramString)
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString)));
    String str;
    do
    {
      return null;
      str = FileUtil.getCacheData(FileUtil.getKXCacheDir(paramContext), paramString, "circles.kx");
    }
    while (TextUtils.isEmpty(str));
    try
    {
      ArrayList localArrayList = getCircleList(new JSONObject(str).optJSONObject("result").optJSONArray("infos"));
      return localArrayList;
    }
    catch (JSONException localJSONException)
    {
    }
    return null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.CirclesEngine
 * JD-Core Version:    0.6.0
 */