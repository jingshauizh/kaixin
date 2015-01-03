package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.AlbumInfo;
import com.kaixin001.model.AlbumListModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class AlbumListEngine extends KXEngine
{
  public static final String ALBUM_ALBUMID = "albumid";
  public static final String ALBUM_ALBUMTITLE = "title";
  public static final String ALBUM_COVERPIC = "coverpic";
  public static final String ALBUM_COVERPIC_130 = "coverpic130";
  public static final String ALBUM_COVERPID = "coverpid";
  public static final String ALBUM_CTIME = "ctime";
  public static final String ALBUM_MTIME = "mtime";
  public static final String ALBUM_PICNUM = "picnum";
  public static final String ALBUM_PRIVACY = "privacy";
  private static final String MY_ALBUMLIST_FILE = "my_albumlist.kx";
  public static final int NUM = 1000;
  private static final String TAG = "AlbumListEngine";
  private static AlbumListEngine instance;

  public static AlbumListEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new AlbumListEngine();
      AlbumListEngine localAlbumListEngine = instance;
      return localAlbumListEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private boolean parseNewsArray(JSONArray paramJSONArray, AlbumListModel paramAlbumListModel, boolean paramBoolean)
  {
    if (paramJSONArray == null)
      return false;
    int i;
    int j;
    do
      try
      {
        paramAlbumListModel.getAlbumslist().clear();
        i = paramJSONArray.length();
        j = 0;
        continue;
        JSONObject localJSONObject = (JSONObject)paramJSONArray.get(j);
        AlbumInfo localAlbumInfo = new AlbumInfo();
        localAlbumInfo.albumsFeedAlbumid = localJSONObject.getString("albumid");
        localAlbumInfo.albumsFeedAlbumtitle = localJSONObject.getString("title");
        localAlbumInfo.albumsFeedCoverpic = localJSONObject.getString("coverpic130");
        localAlbumInfo.albumsFeedCoverpic130 = localJSONObject.getString("coverpic130");
        localAlbumInfo.albumsFeedCoverpid = localJSONObject.getString("coverpid");
        localAlbumInfo.albumsFeedCtime = localJSONObject.getString("ctime");
        localAlbumInfo.albumsFeedMtime = localJSONObject.getString("mtime");
        localAlbumInfo.albumsFeedPicnum = localJSONObject.getString("picnum");
        localAlbumInfo.albumsFeedPrivacy = localJSONObject.getString("privacy");
        localAlbumInfo.albumsFeedIsFriend = paramBoolean;
        paramAlbumListModel.getAlbumslist().add(localAlbumInfo);
        j++;
      }
      catch (Exception localException)
      {
        KXLog.e("AlbumListEngine", "parseAlbumsArray", localException);
        return false;
      }
    while (j < i);
    return true;
  }

  public boolean getAlbumPhotoList(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeAlbumListRequest(true, User.getInstance().getUID(), paramString, 1000);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    boolean bool;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
      {
        bool = false;
        return bool;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("AlbumListEngine", "getAlbumPhotoList error", localException);
        str2 = null;
      }
    }
    if (User.getInstance().getUID().compareTo(paramString) == 0);
    for (AlbumListModel localAlbumListModel = AlbumListModel.getMyAlbumList(); ; localAlbumListModel = AlbumListModel.getInstance())
    {
      bool = parseAlbumListJSON(paramContext, localAlbumListModel, true, str2);
      if ((!bool) || (User.getInstance().getUID().compareTo(paramString) != 0))
        break;
      FileUtil.setCacheData(FileUtil.getKXCacheDir(paramContext), User.getInstance().getUID(), "my_albumlist.kx", str2);
      return bool;
    }
  }

  public boolean loadDataFromCache(Context paramContext)
  {
    String str = FileUtil.getCacheData(FileUtil.getKXCacheDir(paramContext), User.getInstance().getUID(), "my_albumlist.kx");
    if (TextUtils.isEmpty(str))
      return false;
    try
    {
      boolean bool = parseAlbumListJSON(paramContext, AlbumListModel.getMyAlbumList(), true, str);
      return bool;
    }
    catch (SecurityErrorException localSecurityErrorException)
    {
      localSecurityErrorException.printStackTrace();
    }
    return false;
  }

  public boolean parseAlbumListJSON(Context paramContext, AlbumListModel paramAlbumListModel, boolean paramBoolean, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramBoolean, paramString);
    if (localJSONObject == null);
    while (true)
    {
      return false;
      if (this.ret != 1)
        break;
      try
      {
        JSONArray localJSONArray = localJSONObject.getJSONArray("albums");
        if (localJSONObject.optInt("isfriend", 1) == 0);
        for (boolean bool = false; parseNewsArray(localJSONArray, paramAlbumListModel, bool); bool = true)
        {
          paramAlbumListModel.setPicTotal();
          return true;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("AlbumListEngine", "parseAlbumListJSON", localException);
        return false;
      }
    }
    KXLog.e("AlbumListEngine failed", paramString);
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.AlbumListEngine
 * JD-Core Version:    0.6.0
 */