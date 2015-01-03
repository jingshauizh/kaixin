package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.item.PhotoItem;
import com.kaixin001.model.LocationListModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class LocationListEngine extends KXEngine
{
  public static final String LOGTAG = "LocationListEngine";
  public static final int NUM = 10;
  private static LocationListEngine instance;

  public static LocationListEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new LocationListEngine();
      LocationListEngine localLocationListEngine = instance;
      return localLocationListEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private ArrayList<NewsInfo> parseLocationList(JSONArray paramJSONArray)
  {
    ArrayList localArrayList;
    if (paramJSONArray == null)
      localArrayList = null;
    while (true)
    {
      return localArrayList;
      localArrayList = new ArrayList();
      int i = 0;
      try
      {
        while (i < paramJSONArray.length())
        {
          JSONObject localJSONObject = (JSONObject)paramJSONArray.get(i);
          NewsInfo localNewsInfo = new NewsInfo();
          localNewsInfo.mFuid = localJSONObject.optString("fuid");
          localNewsInfo.mFname = localJSONObject.optString("fname");
          localNewsInfo.mFlogo = localJSONObject.optString("flogo");
          localNewsInfo.mNtype = localJSONObject.optString("ntype");
          localNewsInfo.mNtypename = localJSONObject.optString("ntypename", "");
          localNewsInfo.mTitle = localJSONObject.optString("title", "");
          localNewsInfo.mIntro = localJSONObject.optString("intro", "");
          localNewsInfo.mPrivacy = localJSONObject.optString("privacy", "");
          localNewsInfo.mThumbnail = localJSONObject.optString("thumbnail", "");
          localNewsInfo.mMapUrl = localJSONObject.optString("mapurl", "");
          localNewsInfo.mStar = localJSONObject.optString("star", "");
          localNewsInfo.mCnum = localJSONObject.optString("cnum", "");
          localNewsInfo.mCommentFlag = localJSONObject.optString("commentflag", "");
          localNewsInfo.mUpnum = localJSONObject.optString("upnum", "");
          localNewsInfo.mCtime = localJSONObject.optString("ctime", "");
          localNewsInfo.mStime = localJSONObject.optString("stime", "");
          if (localNewsInfo.mStime.equals(""))
            localNewsInfo.mStime = localNewsInfo.mCtime;
          localNewsInfo.mSource = localJSONObject.optString("source", "");
          localNewsInfo.mSourceId = localJSONObject.optString("source_id", "");
          localNewsInfo.mLocation = localJSONObject.optString("location", "");
          localJSONObject.optString("gender", "");
          localJSONObject.optString("online", "");
          localJSONObject.optString("city", "");
          localJSONObject.optString("imglist", "");
          localJSONObject.optString("forward_num", "");
          if (("1192".equals(localNewsInfo.mNtype)) && (localNewsInfo.mPhotoList != null) && (localNewsInfo.mPhotoList.size() > 0))
          {
            localNewsInfo.mRecordImages = new String[2];
            PhotoItem localPhotoItem = (PhotoItem)localNewsInfo.mPhotoList.get(0);
            localNewsInfo.mRecordImages[0] = localPhotoItem.thumbnail;
            localNewsInfo.mRecordImages[1] = localPhotoItem.large;
          }
          localArrayList.add(localNewsInfo);
          i++;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("LocationListEngine", "ParseNewsArray", localException);
      }
    }
    return localArrayList;
  }

  public boolean getLocationList(Context paramContext, String paramString, int paramInt)
    throws SecurityErrorException
  {
    if (paramInt == 0)
      LocationListModel.getInstance().clear();
    String str1 = Protocol.getInstance().makeLocationListRequest(paramContext, User.getInstance().getUID(), paramString, paramInt, 10);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2 = null;
    try
    {
      str2 = localHttpProxy.httpGet(str1, null, null);
      Thread.sleep(0L);
      if (TextUtils.isEmpty(str2))
        return false;
    }
    catch (InterruptedException localInterruptedException)
    {
      return false;
    }
    catch (Exception localException)
    {
      while (true)
        KXLog.e("LocationListEngine", "getLocationList error", localException);
    }
    return parseLocationListJSON(paramContext, str2);
  }

  public boolean parseLocationListJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
    if (localJSONObject == null)
      return false;
    if (this.ret == 1)
      try
      {
        ArrayList localArrayList = parseLocationList(localJSONObject.getJSONArray("data"));
        LocationListModel.getInstance().setLocationList(localArrayList);
        int i = localJSONObject.getInt("total");
        LocationListModel.getInstance().setTotal(i);
        return true;
      }
      catch (Exception localException)
      {
        KXLog.e("LocationListEngine", "parseLocationListJSON", localException);
        return false;
      }
    KXLog.d("LocationListEngine failed", paramString);
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.LocationListEngine
 * JD-Core Version:    0.6.0
 */