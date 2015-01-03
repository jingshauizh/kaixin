package com.kaixin001.engine;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class RecommendAppNotifyEngine extends KXEngine
{
  private static final long APP_SHOW_NEW_FLAG_DURATION = 259200000L;
  private static final int TYPE_APP = 1;
  private static final int TYPE_GAME = 2;
  private static RecommendAppNotifyEngine instance;
  private CopyOnWriteArrayList<AppNotifyItem> mExistApps = new CopyOnWriteArrayList();

  private boolean appExist(String paramString)
  {
    Iterator localIterator = this.mExistApps.iterator();
    do
      if (!localIterator.hasNext())
        return false;
    while (!((AppNotifyItem)localIterator.next()).mAppName.equals(paramString));
    return true;
  }

  private String conversionAppsToString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = this.mExistApps.iterator();
    if (!localIterator.hasNext())
      return localStringBuffer.toString();
    AppNotifyItem localAppNotifyItem = (AppNotifyItem)localIterator.next();
    localStringBuffer.append(localAppNotifyItem.mType).append(";");
    localStringBuffer.append(localAppNotifyItem.mAppName).append(";");
    localStringBuffer.append(localAppNotifyItem.mAppStartTime).append(";");
    if (localAppNotifyItem.mNotify);
    for (String str = "1"; ; str = "0")
    {
      localStringBuffer.append(str).append(";");
      break;
    }
  }

  private void conversionStringToApps(String paramString)
  {
    this.mExistApps.clear();
    try
    {
      if (!TextUtils.isEmpty(paramString))
      {
        String[] arrayOfString = paramString.split(";");
        if (arrayOfString != null)
        {
          int i = 0;
          if (i >= arrayOfString.length)
            return;
          AppNotifyItem localAppNotifyItem = new AppNotifyItem();
          localAppNotifyItem.mType = Integer.parseInt(arrayOfString[i]);
          localAppNotifyItem.mAppName = arrayOfString[(i + 1)];
          localAppNotifyItem.mAppStartTime = Long.parseLong(arrayOfString[(i + 2)]);
          if (arrayOfString[(i + 3)].equals("1"));
          for (boolean bool = true; ; bool = false)
          {
            localAppNotifyItem.mNotify = bool;
            this.mExistApps.add(localAppNotifyItem);
            i += 4;
            break;
          }
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static RecommendAppNotifyEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new RecommendAppNotifyEngine();
      RecommendAppNotifyEngine localRecommendAppNotifyEngine = instance;
      return localRecommendAppNotifyEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void loadData(Context paramContext)
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    try
    {
      String str2 = localSharedPreferences.getString("recommend_app_notify_" + User.getInstance().getUID(), "");
      str1 = str2;
      conversionStringToApps(str1);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        String str1 = null;
    }
  }

  private int parseRecommendAppNotify(Context paramContext, String paramString)
  {
    while (true)
    {
      int i;
      try
      {
        if (!TextUtils.isEmpty(paramString))
        {
          JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
          if (localJSONObject == null)
            return -1;
          if (this.ret == 1)
          {
            JSONArray localJSONArray1 = localJSONObject.optJSONArray("apps");
            if ((localJSONArray1 == null) || (localJSONArray1.length() <= 0))
              continue;
            i = 0;
            if (i < localJSONArray1.length())
              continue;
            JSONArray localJSONArray2 = localJSONObject.optJSONArray("games");
            if ((localJSONArray2 == null) || (localJSONArray2.length() <= 0))
              break label249;
            int j = 0;
            if (j < localJSONArray2.length())
              continue;
            break label249;
            String str2 = localJSONArray1.getString(i);
            if (appExist(str2))
              break label251;
            AppNotifyItem localAppNotifyItem2 = new AppNotifyItem();
            localAppNotifyItem2.mType = 1;
            localAppNotifyItem2.mAppName = str2;
            localAppNotifyItem2.mAppStartTime = System.currentTimeMillis();
            localAppNotifyItem2.mNotify = true;
            this.mExistApps.add(localAppNotifyItem2);
            break label251;
            String str1 = localJSONArray2.getString(j);
            if (appExist(str1))
              continue;
            AppNotifyItem localAppNotifyItem1 = new AppNotifyItem();
            localAppNotifyItem1.mType = 2;
            localAppNotifyItem1.mAppName = str1;
            localAppNotifyItem1.mAppStartTime = System.currentTimeMillis();
            localAppNotifyItem1.mNotify = true;
            this.mExistApps.add(localAppNotifyItem1);
            j++;
            continue;
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return -1;
      label249: return 1;
      label251: i++;
    }
  }

  private void saveData(Context paramContext)
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    try
    {
      String str = conversionAppsToString();
      SharedPreferences.Editor localEditor = localSharedPreferences.edit();
      localEditor.putString("recommend_app_notify_" + User.getInstance().getUID(), str);
      localEditor.commit();
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public boolean appIsNew(String paramString)
  {
    Iterator localIterator = this.mExistApps.iterator();
    AppNotifyItem localAppNotifyItem;
    do
    {
      if (!localIterator.hasNext())
        return true;
      localAppNotifyItem = (AppNotifyItem)localIterator.next();
    }
    while ((!localAppNotifyItem.mAppName.equals(paramString)) || (System.currentTimeMillis() - localAppNotifyItem.mAppStartTime <= 259200000L));
    return false;
  }

  public void clearNewCount(Context paramContext)
  {
    Iterator localIterator = this.mExistApps.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        saveData(paramContext);
        return;
      }
      ((AppNotifyItem)localIterator.next()).mNotify = false;
    }
  }

  public int getNewAppCount()
  {
    int i = 0;
    Iterator localIterator = this.mExistApps.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return i;
      AppNotifyItem localAppNotifyItem = (AppNotifyItem)localIterator.next();
      if ((localAppNotifyItem.mType != 1) || (!localAppNotifyItem.mNotify))
        continue;
      i++;
    }
  }

  public int getNewGameCount()
  {
    int i = 0;
    Iterator localIterator = this.mExistApps.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return i;
      AppNotifyItem localAppNotifyItem = (AppNotifyItem)localIterator.next();
      if ((localAppNotifyItem.mType != 2) || (!localAppNotifyItem.mNotify))
        continue;
      i++;
    }
  }

  public int getRecommendAppNotify(Context paramContext)
  {
    loadData(paramContext);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    int i = -1;
    try
    {
      int j = parseRecommendAppNotify(paramContext, localHttpProxy.httpGet(Protocol.getInstance().makeRecommendAppNotify(), null, null));
      i = j;
      saveData(paramContext);
      return i;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  class AppNotifyItem
  {
    String mAppName;
    long mAppStartTime;
    boolean mNotify;
    int mType;

    AppNotifyItem()
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.RecommendAppNotifyEngine
 * JD-Core Version:    0.6.0
 */