package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.FriendPhotoInfo;
import com.kaixin001.model.FriendPhotoModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FriendPhotoEngine extends KXEngine
{
  public static final int FIREST_DATA_NUM = 24;
  private static final String FRIENDS_PHOTO_FILE = "friends_photo.kx";
  public static final int MORE_DATA_NUM = 24;
  private static final String PLAZA_PHOTO_FILE = "plaza_photo.kx";
  public static final int REQUEST_TYPE_FRIEND = 0;
  public static final int REQUEST_TYPE_PLAZA = 1;
  public static final int STATE_MORE_FAIL = 1;
  public static final int STATE_MORE_LOADING = 2;
  public static final int STATE_MORE_SUCC = 0;
  public static final int STATE_MORE_VIRGIN = -1;
  public static final String TAG = "FriendPhotoEngine";
  private static FriendPhotoEngine instance;
  private volatile int mLoadMoreDataState = -1;

  public static FriendPhotoEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new FriendPhotoEngine();
      FriendPhotoEngine localFriendPhotoEngine = instance;
      return localFriendPhotoEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private ArrayList<FriendPhotoInfo> parsePhotoArray(JSONArray paramJSONArray, int paramInt)
    throws JSONException
  {
    if (paramJSONArray == null)
    {
      localArrayList = null;
      return localArrayList;
    }
    int i = paramJSONArray.length();
    ArrayList localArrayList = new ArrayList();
    int j = 0;
    label27: JSONObject localJSONObject;
    FriendPhotoInfo localFriendPhotoInfo;
    boolean bool;
    if (j < i)
    {
      localJSONObject = paramJSONArray.getJSONObject(j);
      localFriendPhotoInfo = new FriendPhotoInfo();
      localFriendPhotoInfo.ownerId = localJSONObject.getString("ownerid");
      localFriendPhotoInfo.ownerName = localJSONObject.getString("ownername");
      localFriendPhotoInfo.img = localJSONObject.getString("img");
      localFriendPhotoInfo.albumId = localJSONObject.getString("albumid");
      localFriendPhotoInfo.pid = localJSONObject.getString("pid");
      localFriendPhotoInfo.ctime = localJSONObject.getString("ctime");
      localFriendPhotoInfo.time = localJSONObject.getString("stime");
      localFriendPhotoInfo.pos = localJSONObject.optInt("pos", 1);
      localFriendPhotoInfo.picTotal = localJSONObject.optInt("total", localFriendPhotoInfo.pos);
      if (localJSONObject.optInt("ispwd", 0) != 1)
        break label247;
      bool = true;
      label179: localFriendPhotoInfo.bNeedPwd = bool;
      if (paramInt != 0)
        break label253;
      localFriendPhotoInfo.imageName = localJSONObject.getString("imgname");
    }
    for (localFriendPhotoInfo.vnum = null; ; localFriendPhotoInfo.vnum = localJSONObject.getString("vnum"))
    {
      localArrayList.add(localFriendPhotoInfo);
      KXLog.d("FriendPhotoEngine", "parsePhotoArray friend:" + localFriendPhotoInfo.ownerName);
      j++;
      break label27;
      break;
      label247: bool = false;
      break label179;
      label253: localFriendPhotoInfo.imageName = null;
    }
  }

  public boolean getPhotoList(Context paramContext, int paramInt1, int paramInt2, int paramInt3, long paramLong)
    throws SecurityErrorException
  {
    String str1 = FileUtil.getKXCacheDir(paramContext);
    String str2 = User.getInstance().getUID();
    String str3 = null;
    if (paramInt1 == 0)
      str3 = Protocol.getInstance().makeFriendsPhotoRequest(User.getInstance().getUID(), paramInt2, paramInt3, paramLong);
    if (paramInt1 == 1)
      str3 = Protocol.getInstance().makePlazaPhotoRequest(User.getInstance().getUID(), paramInt2, paramInt3, paramLong);
    if (paramInt2 > 0)
      this.mLoadMoreDataState = 2;
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    boolean bool;
    try
    {
      String str6 = localHttpProxy.httpGet(str3, null, null);
      str4 = str6;
      if (TextUtils.isEmpty(str4))
      {
        this.mLoadMoreDataState = 1;
        return false;
      }
    }
    catch (Exception localException)
    {
      String str4;
      while (true)
      {
        KXLog.e("FriendPhotoEngine", "getPhotoList error", localException);
        str4 = null;
      }
      bool = parsePhotoJSON(paramContext, paramInt1, paramInt2, paramLong, true, str4);
      String str5;
      if ((bool) && (paramInt2 == 0))
      {
        KXLog.d("FriendPhotoEngine", "--------- do cache data [type]=" + paramInt1);
        str5 = "";
        if (paramInt1 != 0)
          break label211;
        str5 = "friends_photo.kx";
      }
      while (true)
      {
        FileUtil.setCacheData(str1, str2, str5, str4);
        if (!bool)
          break;
        this.mLoadMoreDataState = 0;
        return bool;
        label211: if (paramInt1 != 1)
          continue;
        str5 = "plaza_photo.kx";
      }
      this.mLoadMoreDataState = 1;
    }
    return bool;
  }

  public int getmLoadMoreDataState()
  {
    monitorenter;
    try
    {
      int i = this.mLoadMoreDataState;
      monitorexit;
      return i;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public boolean loadDataFromCache(Context paramContext, int paramInt)
  {
    String str1 = FileUtil.getKXCacheDir(paramContext);
    String str2 = User.getInstance().getUID();
    String str3 = "";
    if (paramInt == 0)
      str3 = "friends_photo.kx";
    String str4;
    while (true)
    {
      str4 = FileUtil.getCacheData(str1, str2, str3);
      if (!TextUtils.isEmpty(str4))
        break;
      return false;
      if (paramInt != 1)
        continue;
      str3 = "plaza_photo.kx";
    }
    try
    {
      boolean bool = parsePhotoJSON(paramContext, paramInt, 0, -2L, false, str4);
      return bool;
    }
    catch (SecurityErrorException localSecurityErrorException)
    {
      localSecurityErrorException.printStackTrace();
    }
    return false;
  }

  public boolean parsePhotoJSON(Context paramContext, int paramInt1, int paramInt2, long paramLong, boolean paramBoolean, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramBoolean, paramString);
    if (localJSONObject == null)
      return false;
    FriendPhotoModel localFriendPhotoModel = FriendPhotoModel.getInstance();
    if (this.ret == 1);
    while (true)
    {
      ArrayList localArrayList;
      int j;
      try
      {
        JSONArray localJSONArray = localJSONObject.getJSONArray("data");
        localArrayList = parsePhotoArray(localJSONArray, paramInt1);
        if (paramInt1 != 0)
          continue;
        int i = localJSONObject.getInt("total");
        if ((i == 0) || (localJSONArray.length() != 0))
          break label170;
        return true;
        Object localObject;
        localFriendPhotoModel.resetNewFriendPhoto(localArrayList, i, localObject);
        if (paramInt1 != 1)
          break;
        j = localJSONObject.getInt("total");
        if (paramInt2 != 0)
          break label158;
        localFriendPhotoModel.resetNewPlazaPhoto(localArrayList, j, paramLong);
        break;
        l = localJSONObject.getLong("ctime");
        continue;
        localFriendPhotoModel.addMoreFriendPhoto(localArrayList, i);
        continue;
      }
      catch (Exception localException)
      {
        KXLog.e("FriendPhotoEngine", "parsePhotoJSON", localException);
      }
      return false;
      label158: localFriendPhotoModel.addMorePlazaPhoto(localArrayList, j);
      break;
      label170: if (paramInt2 != 0)
        continue;
      if (paramLong != -2L)
        continue;
      long l = paramLong;
    }
    return true;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.FriendPhotoEngine
 * JD-Core Version:    0.6.0
 */