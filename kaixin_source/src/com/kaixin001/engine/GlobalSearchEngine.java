package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.menu.HomePageMenuItem;
import com.kaixin001.menu.MenuItem;
import com.kaixin001.menu.ThirdAppMenuItem;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class GlobalSearchEngine extends KXEngine
{
  private static final String LOGTAG = "GlobalSearchEngine";
  private static GlobalSearchEngine instance;

  public static GlobalSearchEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new GlobalSearchEngine();
      GlobalSearchEngine localGlobalSearchEngine = instance;
      return localGlobalSearchEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private ArrayList<MenuItem> parseAppJson(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    boolean bool = TextUtils.isEmpty(paramString);
    ArrayList localArrayList = null;
    if (bool);
    while (true)
    {
      return localArrayList;
      JSONObject localJSONObject1 = super.parseJSON(paramContext, true, paramString);
      localArrayList = null;
      if (localJSONObject1 == null)
        continue;
      int i = this.ret;
      localArrayList = null;
      if (i != 1)
        continue;
      JSONArray localJSONArray = localJSONObject1.optJSONArray("data");
      localArrayList = null;
      if (localJSONArray == null)
        continue;
      int j = localJSONArray.length();
      localArrayList = null;
      if (j <= 0)
        continue;
      localArrayList = new ArrayList();
      for (int k = 0; k < localJSONArray.length(); k++)
      {
        JSONObject localJSONObject2 = localJSONArray.optJSONObject(k);
        if (localJSONObject2 == null)
          continue;
        NavigatorApplistEngine.ThirdAppItem localThirdAppItem = new NavigatorApplistEngine.ThirdAppItem();
        localThirdAppItem.name = localJSONObject2.optString("name");
        localThirdAppItem.setDownloadUrl(localJSONObject2.optString("downloadurl"));
        localThirdAppItem.setAppId(localJSONObject2.optString("kxappid"));
        localThirdAppItem.setPakageName(localJSONObject2.optString("packageurl"));
        localThirdAppItem.setVersion(localJSONObject2.optString("version"));
        localThirdAppItem.setUrl(localJSONObject2.optString("wapurl"));
        localThirdAppItem.setAppEntry(localJSONObject2.optString("starturl"));
        ThirdAppMenuItem localThirdAppMenuItem = new ThirdAppMenuItem(localThirdAppItem);
        localThirdAppMenuItem.iconURL = localJSONObject2.optString("logo");
        localArrayList.add(localThirdAppMenuItem);
      }
    }
  }

  private ArrayList<MenuItem> parseStarJson(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    boolean bool = TextUtils.isEmpty(paramString);
    ArrayList localArrayList = null;
    if (bool);
    while (true)
    {
      return localArrayList;
      JSONObject localJSONObject1 = super.parseJSON(paramContext, true, paramString);
      localArrayList = null;
      if (localJSONObject1 == null)
        continue;
      int i = this.ret;
      localArrayList = null;
      if (i != 1)
        continue;
      JSONArray localJSONArray = localJSONObject1.optJSONArray("data");
      localArrayList = null;
      if (localJSONArray == null)
        continue;
      int j = localJSONArray.length();
      localArrayList = null;
      if (j <= 0)
        continue;
      localArrayList = new ArrayList();
      for (int k = 0; k < localJSONArray.length(); k++)
      {
        JSONObject localJSONObject2 = localJSONArray.optJSONObject(k);
        if (localJSONObject2 == null)
          continue;
        localArrayList.add(new HomePageMenuItem(localJSONObject2.optString("name"), localJSONObject2.optString("uid"), localJSONObject2.optString("logo"), true));
      }
    }
  }

  public ArrayList<MenuItem> getGlobalSearchAppList(Context paramContext, String paramString, int paramInt1, int paramInt2)
    throws SecurityErrorException
  {
    String str1 = URLEncoder.encode(paramString);
    String str2 = Protocol.getInstance().makeGetGlobalSearchAppUrl(str1, paramInt1, paramInt2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str4 = localHttpProxy.httpGet(str2, null, null);
      str3 = str4;
      return parseAppJson(paramContext, str3);
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("GlobalSearchEngine", "getGlobalSearchList error", localException);
        String str3 = null;
      }
    }
  }

  public ArrayList<MenuItem> getGlobalSearchStarList(Context paramContext, String paramString, int paramInt1, int paramInt2)
    throws SecurityErrorException
  {
    String str1 = URLEncoder.encode(paramString);
    String str2 = Protocol.getInstance().makeGetGlobalSearchStarUrl(str1, paramInt1, paramInt2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str4 = localHttpProxy.httpGet(str2, null, null);
      str3 = str4;
      return parseStarJson(paramContext, str3);
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("GlobalSearchEngine", "getGlobalSearchList error", localException);
        String str3 = null;
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.GlobalSearchEngine
 * JD-Core Version:    0.6.0
 */