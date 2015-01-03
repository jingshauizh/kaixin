package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.KaixinModelTemplate;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

public class NavigatorApplistEngine extends KXEngine
{
  public static String KX_APPLIST_FILE;
  public static String KX_GAMELIST_FILE;
  private static final String LOGTAG = "NavigatorApplistEngine";
  public static final String TAG = "NavigatorApplistEngine";
  public static String THIRD_APPLIST_FILE = "recommend_apps_list.kx";
  private static NavigatorApplistEngine instance;
  public KaixinModelTemplate<ThirdAppItem> recommend3dAppsList = new KaixinModelTemplate();
  public KaixinModelTemplate<ThirdAppItem> recommend3dGamesList = new KaixinModelTemplate();
  public KaixinModelTemplate<ThirdAppItem> result3dAppList = new KaixinModelTemplate();
  public ArrayList<KXAppItem> resultKXAppsList;
  public ArrayList<KXAppItem> resultKXGamesList;

  static
  {
    KX_APPLIST_FILE = "recommend_kxapp.kx";
    KX_GAMELIST_FILE = "recommend_kxgame.kx";
    instance = null;
  }

  public static NavigatorApplistEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new NavigatorApplistEngine();
      NavigatorApplistEngine localNavigatorApplistEngine = instance;
      return localNavigatorApplistEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private String loadCacheFile(Context paramContext, int paramInt)
  {
    return FileUtil.getCacheData(FileUtil.getKXCacheDir(paramContext), User.getInstance().getUID(), THIRD_APPLIST_FILE + paramInt);
  }

  private String loadKXCacheFile(Context paramContext, boolean paramBoolean)
  {
    if (paramBoolean);
    for (String str = KX_GAMELIST_FILE; ; str = KX_APPLIST_FILE)
      return FileUtil.getCacheData(FileUtil.getKXCacheDir(paramContext), User.getInstance().getUID(), str);
  }

  private boolean saveCacheFile(Context paramContext, String paramString, int paramInt)
  {
    return FileUtil.setCacheData(FileUtil.getKXCacheDir(paramContext), User.getInstance().getUID(), THIRD_APPLIST_FILE + paramInt, paramString);
  }

  private boolean saveKXCacheFile(Context paramContext, String paramString, boolean paramBoolean)
  {
    if (paramBoolean);
    for (String str = KX_GAMELIST_FILE; ; str = KX_APPLIST_FILE)
      return FileUtil.setCacheData(FileUtil.getKXCacheDir(paramContext), User.getInstance().getUID(), str, paramString);
  }

  public Integer getKXAppslistData(Context paramContext)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetCommonApplistRequest(0, 100);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    Object localObject = null;
    try
    {
      localObject = localHttpProxy.httpGet(str1, null, null);
      if (!TextUtils.isEmpty((CharSequence)localObject))
        saveKXCacheFile(paramContext, (String)localObject, false);
      while (localObject == null)
      {
        return Integer.valueOf(0);
        String str2 = loadKXCacheFile(paramContext, false);
        localObject = str2;
      }
    }
    catch (Exception localException)
    {
      while (true)
        KXLog.e("NavigatorApplistEngine", "getApplist error", localException);
      JSONObject localJSONObject = super.parseJSON(paramContext, (String)localObject);
      if (localJSONObject == null)
        return Integer.valueOf(0);
      if (this.ret == 1)
      {
        this.resultKXAppsList = parseKXApplistJSON(localJSONObject);
        return Integer.valueOf(1);
      }
    }
    return (Integer)Integer.valueOf(0);
  }

  public Integer getKXGameslistData(Context paramContext)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetKXApplistRequest(0, 100);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    Object localObject = null;
    try
    {
      localObject = localHttpProxy.httpGet(str1, null, null);
      if (!TextUtils.isEmpty((CharSequence)localObject))
        saveKXCacheFile(paramContext, (String)localObject, true);
      while (localObject == null)
      {
        return Integer.valueOf(0);
        String str2 = loadKXCacheFile(paramContext, true);
        localObject = str2;
      }
    }
    catch (Exception localException)
    {
      while (true)
        KXLog.e("NavigatorApplistEngine", "getApplist error", localException);
      JSONObject localJSONObject = super.parseJSON(paramContext, (String)localObject);
      if (localJSONObject == null)
        return Integer.valueOf(0);
      if (this.ret == 1)
      {
        this.resultKXGamesList = parseKXApplistJSON(localJSONObject);
        return Integer.valueOf(1);
      }
    }
    return (Integer)Integer.valueOf(0);
  }

  public Integer getThirdApplistData(Context paramContext, int paramInt1, int paramInt2, boolean paramBoolean)
    throws SecurityErrorException
  {
    KXLog.d("NavigatorApplistEngine", "load third apps: " + paramInt1 + "," + paramInt2);
    Object localObject = null;
    if (paramBoolean)
    {
      KXLog.d("NavigatorApplistEngine", "load cache of third apps");
      localObject = loadCacheFile(paramContext, paramInt1);
      if (localObject == null)
      {
        paramBoolean = false;
        KXLog.d("NavigatorApplistEngine", "but cache file not exist, so load third apps from network");
      }
    }
    String str1;
    HttpProxy localHttpProxy;
    if (!paramBoolean)
    {
      KXLog.d("NavigatorApplistEngine", "load third apps from network");
      str1 = Protocol.getInstance().makeGetThirdApplistRequest(paramInt1, paramInt2);
      localHttpProxy = new HttpProxy(paramContext);
    }
    try
    {
      String str2 = localHttpProxy.httpGet(str1, null, null);
      localObject = str2;
      if (localObject == null)
        return Integer.valueOf(0);
    }
    catch (Exception localException)
    {
      while (true)
        KXLog.e("NavigatorApplistEngine", "getApplist error", localException);
      boolean bool;
      JSONObject localJSONObject;
      ArrayList localArrayList1;
      ArrayList localArrayList2;
      ArrayList localArrayList3;
      Iterator localIterator;
      if (paramBoolean)
      {
        bool = false;
        localJSONObject = super.parseJSON(paramContext, bool, (String)localObject);
        localArrayList1 = parseThirdApplistJSON(localJSONObject);
        localArrayList2 = new ArrayList();
        localArrayList3 = new ArrayList();
        localIterator = localArrayList1.iterator();
      }
      while (true)
      {
        if (!localIterator.hasNext())
        {
          if (localJSONObject != null)
            break label269;
          return Integer.valueOf(0);
          bool = true;
          break;
        }
        ThirdAppItem localThirdAppItem = (ThirdAppItem)localIterator.next();
        if (localThirdAppItem.getType() == 1)
        {
          localArrayList2.add(localThirdAppItem);
          continue;
        }
        if (localThirdAppItem.getType() != 2)
          continue;
        localArrayList3.add(localThirdAppItem);
      }
      label269: if (this.ret == 1)
      {
        int i = localJSONObject.optInt("total", 0);
        this.result3dAppList.setItemList(i, localArrayList1, paramInt1);
        this.recommend3dGamesList.setItemList(i, localArrayList2, paramInt1);
        this.recommend3dAppsList.setItemList(i, localArrayList3, paramInt1);
        if (!paramBoolean)
          saveCacheFile(paramContext, (String)localObject, paramInt1);
        return Integer.valueOf(1);
      }
    }
    return (Integer)Integer.valueOf(0);
  }

  public ArrayList<KXAppItem> parseKXApplistJSON(JSONObject paramJSONObject)
    throws SecurityErrorException
  {
    JSONArray localJSONArray = paramJSONObject.optJSONArray("data");
    if (localJSONArray == null);
    ArrayList localArrayList;
    int j;
    for (int i = 0; ; i = localJSONArray.length())
    {
      localArrayList = new ArrayList();
      j = 0;
      if (j < i)
        break;
      return localArrayList;
    }
    JSONObject localJSONObject = (JSONObject)localJSONArray.opt(j);
    KXAppItem localKXAppItem = new KXAppItem();
    localKXAppItem.setName(localJSONObject.optString("name"));
    localKXAppItem.setUrl(localJSONObject.optString("wapurl"));
    localKXAppItem.setLogo(localJSONObject.optString("logo"));
    localKXAppItem.setAppId(localJSONObject.optString("appid"));
    localKXAppItem.setStatus(localJSONObject.optString("status"));
    localKXAppItem.setOrderid(localJSONObject.optString("orderid"));
    localKXAppItem.setIntro(localJSONObject.optString("intro"));
    localKXAppItem.setTime(localJSONObject.optString("time"));
    if (localJSONObject.optInt("isnew") == 0);
    for (boolean bool = false; ; bool = true)
    {
      localKXAppItem.setIsNew(bool);
      localKXAppItem.setShareWX(localJSONObject.optInt("weixin"));
      localArrayList.add(localKXAppItem);
      j++;
      break;
    }
  }

  public ArrayList<ThirdAppItem> parseThirdApplistJSON(JSONObject paramJSONObject)
    throws SecurityErrorException
  {
    JSONArray localJSONArray = paramJSONObject.optJSONArray("data");
    if (localJSONArray == null);
    ArrayList localArrayList;
    int j;
    for (int i = 0; ; i = localJSONArray.length())
    {
      localArrayList = new ArrayList();
      j = 0;
      if (j < i)
        break;
      return localArrayList;
    }
    JSONObject localJSONObject1 = (JSONObject)localJSONArray.opt(j);
    ThirdAppItem localThirdAppItem = new ThirdAppItem();
    localThirdAppItem.setName(localJSONObject1.optString("name"));
    JSONObject localJSONObject2 = localJSONObject1.optJSONObject("detail");
    if (localJSONObject2 != null)
    {
      localThirdAppItem.setDev(localJSONObject2.optString("dev", ""));
      localThirdAppItem.setPakageName(localJSONObject2.optString("package_name"));
    }
    localThirdAppItem.setDescription(localJSONObject1.optString("des"));
    localThirdAppItem.setLogo(localJSONObject1.optString("largelogo"));
    localThirdAppItem.setUrl(localJSONObject1.optString("detailurl"));
    localThirdAppItem.setType(localJSONObject1.optInt("type"));
    localThirdAppItem.setDownloadUrl(localJSONObject1.optString("downloadurl"));
    localThirdAppItem.setAppEntry(localJSONObject1.optString("open_url"));
    localThirdAppItem.setAppId(localJSONObject1.optString("appid"));
    localThirdAppItem.setIntro(localJSONObject1.optString("intro"));
    localThirdAppItem.setTime(localJSONObject1.optString("time"));
    if (localJSONObject1.optInt("isnew") == 0);
    for (boolean bool = false; ; bool = true)
    {
      localThirdAppItem.setIsNew(bool);
      localArrayList.add(localThirdAppItem);
      j++;
      break;
    }
  }

  public static class AppItem
  {
    private String intro = "";
    private boolean isAdvBanner = false;
    private boolean isNativeGame = false;
    private boolean isNew = false;
    private String logo = "";
    private String mDescription = "";
    public String name = "";
    private String time = "";
    public String url = "";

    public String getDescription()
    {
      return this.mDescription;
    }

    public String getIntro()
    {
      return this.intro;
    }

    public String getLogo()
    {
      return this.logo;
    }

    public String getName()
    {
      return this.name;
    }

    public String getTime()
    {
      return this.time;
    }

    public String getUrl()
    {
      return this.url;
    }

    public boolean isAdvBanner()
    {
      return this.isAdvBanner;
    }

    public boolean isNativeGame()
    {
      return this.isNativeGame;
    }

    public boolean isNew()
    {
      return this.isNew;
    }

    public void setAdvBanner(boolean paramBoolean)
    {
      this.isAdvBanner = paramBoolean;
    }

    public void setDescription(String paramString)
    {
      this.mDescription = paramString;
    }

    public void setIntro(String paramString)
    {
      this.intro = paramString;
    }

    public void setIsNew(boolean paramBoolean)
    {
      this.isNew = paramBoolean;
    }

    public void setLogo(String paramString)
    {
      this.logo = paramString;
    }

    public void setName(String paramString)
    {
      this.name = paramString;
    }

    public void setNativeGame(boolean paramBoolean)
    {
      this.isNativeGame = paramBoolean;
    }

    public void setTime(String paramString)
    {
      this.time = paramString;
    }

    public void setUrl(String paramString)
    {
      this.url = paramString;
    }
  }

  public static class KXAppItem extends NavigatorApplistEngine.AppItem
  {
    private String appId = "";
    private String orderid = "";
    private int shareWX = 0;
    private String status = "";

    public String getAppId()
    {
      return this.appId;
    }

    public String getOrderid()
    {
      return this.orderid;
    }

    public int getShareWX()
    {
      return this.shareWX;
    }

    public String getStatus()
    {
      return this.status;
    }

    public void setAppId(String paramString)
    {
      this.appId = paramString;
    }

    public void setOrderid(String paramString)
    {
      this.orderid = paramString;
    }

    public void setShareWX(int paramInt)
    {
      this.shareWX = paramInt;
    }

    public void setStatus(String paramString)
    {
      this.status = paramString;
    }
  }

  public static class ThirdAppItem extends NavigatorApplistEngine.AppItem
  {
    public static final int TYPE_ITEM_APP = 2;
    public static final int TYPE_ITEM_GAME = 1;
    private String appEntry = "";
    private String appId = "";
    private String dev = "";
    private String downloadUrl = "";
    private String launchActivtiy = "";
    private String pakageName = "";
    private int type = 0;
    private String version = "";

    public String getAppEntry()
    {
      return this.appEntry;
    }

    public String getAppId()
    {
      return this.appId;
    }

    public String getDev()
    {
      return this.dev;
    }

    public String getDownloadUrl()
    {
      return this.downloadUrl;
    }

    public String getLaunchClass()
    {
      return this.launchActivtiy;
    }

    public String getPakageName()
    {
      return this.pakageName;
    }

    public int getType()
    {
      return this.type;
    }

    public String getVersion()
    {
      return this.version;
    }

    public void setAppEntry(String paramString)
    {
      this.appEntry = paramString;
    }

    public void setAppId(String paramString)
    {
      this.appId = paramString;
    }

    public void setDev(String paramString)
    {
      this.dev = paramString;
    }

    public void setDownloadUrl(String paramString)
    {
      this.downloadUrl = paramString;
    }

    public void setLaunchActivity(String paramString)
    {
      this.launchActivtiy = paramString;
    }

    public void setPakageName(String paramString)
    {
      this.pakageName = paramString;
    }

    public void setType(int paramInt)
    {
      this.type = paramInt;
    }

    public void setVersion(String paramString)
    {
      this.version = paramString;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.NavigatorApplistEngine
 * JD-Core Version:    0.6.0
 */