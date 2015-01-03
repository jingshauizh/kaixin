package com.kaixin001.model;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.UpgradeDownloadFile;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UpgradeModel extends KXModel
{
  private static final String ID_FOR_CACHE = "UPGRADE_0000";
  private static final String UPGRADE_CATCH_FILE = "UPGRADETIME.KX";
  private static UpgradeModel instance = null;
  private static String mCid;
  private static String mClientVersion;
  private static volatile long mLastIgnoreTime;
  private static volatile boolean mLoadCache;
  private static volatile boolean mNoticeDone = false;
  private JSONArray mUpgradeInformation = null;

  static
  {
    mClientVersion = "1.0.1";
    mCid = "1";
    mLoadCache = false;
    mLastIgnoreTime = -1L;
  }

  private UpgradeModel()
  {
    mClientVersion = Protocol.getShortVersion();
  }

  public static void clearLoadedFlag()
  {
    mLoadCache = false;
  }

  public static void enableUpgradeService(boolean paramBoolean)
  {
    if (paramBoolean);
    for (boolean bool = false; ; bool = true)
    {
      mNoticeDone = bool;
      return;
    }
  }

  public static UpgradeModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new UpgradeModel();
      UpgradeModel localUpgradeModel = instance;
      return localUpgradeModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public static boolean isNeedCheckVersion(Context paramContext)
  {
    if ((paramContext == null) || (mNoticeDone));
    while (true)
    {
      return false;
      String str;
      if (!mLoadCache)
      {
        str = FileUtil.getCacheData(FileUtil.getKXCacheDir(paramContext), "UPGRADE_0000", "UPGRADETIME.KX");
        mLoadCache = true;
      }
      try
      {
        mLastIgnoreTime = Long.parseLong(str);
        if (Setting.getInstance().isDebug())
          KXLog.d("UpgradeModel", "load:" + mLastIgnoreTime);
        if ((mLastIgnoreTime != -1L) && (System.currentTimeMillis() - mLastIgnoreTime < 604800000L) && (mLastIgnoreTime <= System.currentTimeMillis()))
          continue;
        return true;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        while (true)
          mLastIgnoreTime = -1L;
      }
    }
  }

  private boolean isNeedUpdate(String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    do
      return false;
    while ((TextUtils.isEmpty(mClientVersion)) || (paramString.toLowerCase().replaceFirst("android-", "").trim().compareTo(mClientVersion) <= 0));
    return true;
  }

  private void saveTime2Cache(Context paramContext)
  {
    if (paramContext == null)
      return;
    mLastIgnoreTime = System.currentTimeMillis();
    FileUtil.setCacheData(FileUtil.getKXCacheDir(paramContext), "UPGRADE_0000", "UPGRADETIME.KX", mLastIgnoreTime);
  }

  private static void setNoticeDone(boolean paramBoolean)
  {
    mNoticeDone = paramBoolean;
  }

  public String checkNeedUpgrade()
  {
    if (this.mUpgradeInformation != null)
    {
      int i = 0;
      try
      {
        while (true)
        {
          if (i >= this.mUpgradeInformation.length())
            return null;
          JSONObject localJSONObject = this.mUpgradeInformation.getJSONObject(i);
          if (localJSONObject.getString("cid").equals(mCid))
          {
            if (localJSONObject.getString("version").equals(mClientVersion))
              break;
            String str = localJSONObject.getString("tip");
            return str;
          }
          i++;
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
    return null;
  }

  public void clear()
  {
    this.mUpgradeInformation = null;
  }

  public boolean getDownloadFile()
  {
    if (this.mUpgradeInformation != null)
    {
      int i = 0;
      try
      {
        while (i < this.mUpgradeInformation.length())
        {
          JSONObject localJSONObject = this.mUpgradeInformation.getJSONObject(i);
          if (localJSONObject.getString("cid").equals(mCid))
          {
            String str = localJSONObject.getString("version");
            if (!isNeedUpdate(str))
              break;
            UpgradeDownloadFile.getInstance().mDescription = localJSONObject.getString("tip");
            UpgradeDownloadFile.getInstance().mUrl = localJSONObject.getString("download");
            UpgradeDownloadFile.getInstance().setVersion(str);
            UpgradeDownloadFile.getInstance().setNewVersionDialogTitle(localJSONObject.optString("title", ""));
            UpgradeDownloadFile.getInstance().setLbtnTitle(localJSONObject.optString("lbtntitle", ""));
            UpgradeDownloadFile.getInstance().setRbtnTitle(localJSONObject.optString("rbtntitle", ""));
            UpgradeDownloadFile.getInstance().setNewVersionContent(localJSONObject.optString("content", ""));
            UpgradeDownloadFile.getInstance().setForcedShow(localJSONObject.optString("allversion", "").equals("1"));
            return true;
          }
          i++;
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
    return false;
  }

  public JSONArray getUpgradeInformation()
  {
    return this.mUpgradeInformation;
  }

  public void ignoreUpgrade(Context paramContext)
  {
    setNoticeDone(false);
    saveTime2Cache(paramContext);
  }

  public void setUpgradeInformation(JSONArray paramJSONArray)
  {
    this.mUpgradeInformation = paramJSONArray;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.UpgradeModel
 * JD-Core Version:    0.6.0
 */