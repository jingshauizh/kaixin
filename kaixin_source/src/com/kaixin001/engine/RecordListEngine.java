package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.item.PhotoItem;
import com.kaixin001.model.RecordListModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class RecordListEngine extends KXEngine
{
  public static final String LOGTAG = "RecordListEngine";
  public static final int NUM = 10;
  private static RecordListEngine instance;

  public static RecordListEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new RecordListEngine();
      RecordListEngine localRecordListEngine = instance;
      return localRecordListEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private ArrayList<NewsInfo> parseRecordList(JSONArray paramJSONArray)
  {
    if (paramJSONArray == null)
    {
      localArrayList = null;
      return localArrayList;
    }
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    while (true)
    {
      JSONObject localJSONObject1;
      NewsInfo localNewsInfo;
      JSONArray localJSONArray3;
      int m;
      try
      {
        if (i >= paramJSONArray.length())
          break;
        localJSONObject1 = (JSONObject)paramJSONArray.get(i);
        localNewsInfo = new NewsInfo();
        localNewsInfo.mFuid = localJSONObject1.optString("fuid");
        localNewsInfo.mFname = localJSONObject1.optString("fname");
        localNewsInfo.mFlogo = localJSONObject1.optString("flogo");
        localNewsInfo.mNtype = localJSONObject1.optString("ntype");
        localNewsInfo.mRid = localJSONObject1.optString("rid");
        localNewsInfo.mNtypename = localJSONObject1.optString("ntypename", "");
        localNewsInfo.mTitle = localJSONObject1.optString("title", "");
        localNewsInfo.mIntro = localJSONObject1.optString("intro", "");
        localNewsInfo.mSubTitle = localJSONObject1.optString("sub_title", "");
        JSONObject localJSONObject2 = localJSONObject1.optJSONObject("sub_info");
        JSONArray localJSONArray1 = localJSONObject1.optJSONArray("video");
        if ((localJSONArray1 != null) || (localJSONObject2 == null))
          continue;
        localJSONArray1 = localJSONObject2.optJSONArray("video");
        if (localJSONArray1 == null)
          continue;
        if (localNewsInfo.mVideoSnapshotLIst != null)
          break label1057;
        localNewsInfo.mVideoSnapshotLIst = new ArrayList();
        break label1057;
        int k = localJSONArray1.length();
        if (j < k)
          continue;
        if (localJSONObject2 == null)
          continue;
        localNewsInfo.mOrigRecordId = localJSONObject2.optString("rid");
        localNewsInfo.mOrigRecordIntro = localJSONObject2.optString("intro");
        localNewsInfo.mOrigRecordLocation = localJSONObject2.optString("location");
        localNewsInfo.mOrigRecordTitle = localJSONObject2.optString("sub_title", "");
        JSONArray localJSONArray2 = localJSONObject2.optJSONArray("images");
        if ((localJSONArray2 == null) || (localJSONArray2.length() <= 0))
          continue;
        localNewsInfo.mRecordImages = new String[2];
        JSONObject localJSONObject6 = localJSONArray2.getJSONObject(0);
        localNewsInfo.mRecordImages[0] = localJSONObject6.optString("thumbnail", "");
        localNewsInfo.mRecordImages[1] = localJSONObject6.optString("large", "");
        localNewsInfo.mCtime = localJSONObject1.optString("ctime", "");
        localNewsInfo.mStime = localJSONObject1.optString("stime", "");
        localNewsInfo.mThumbnail = localJSONObject1.optString("thumbnail", "");
        if (localJSONObject1.isNull("id"))
          continue;
        localNewsInfo.mNewsId = localJSONObject1.optString("id");
        if (localJSONObject1.opt("star") != null)
        {
          str1 = localJSONObject1.optString("star", "0");
          localNewsInfo.mStar = str1;
          localNewsInfo.mCnum = localJSONObject1.optString("cnum", "0");
          localNewsInfo.mTnum = localJSONObject1.optString("tnum", "0");
          localNewsInfo.mUpnum = localJSONObject1.optString("upnum", "0");
          localNewsInfo.mPrivacy = localJSONObject1.optString("privacy", "");
          localNewsInfo.mCommentFlag = localJSONObject1.optString("commentflag", "");
          if (localJSONObject1.isNull("fpri"))
            continue;
          localNewsInfo.mFpri = localJSONObject1.optString("fpri");
          localNewsInfo.mLocation = localJSONObject1.optString("location");
          localNewsInfo.mSource = localJSONObject1.optString("source", "");
          localNewsInfo.mSourceId = localJSONObject1.optString("source_id", "");
          localJSONArray3 = localJSONObject1.optJSONArray("imglist");
          if ((localJSONArray3 == null) || (localJSONArray3.length() <= 0) || ((!localNewsInfo.mNtype.equals("1")) && (!localNewsInfo.mNtype.equals("1242")) && (!localNewsInfo.mNtype.equals("1192"))))
            continue;
          localNewsInfo.mPhotoList = new ArrayList();
          m = 0;
          if (m < localJSONArray3.length())
            break label837;
          JSONArray localJSONArray4 = localJSONObject1.optJSONArray("images");
          if ((localJSONArray4 == null) || (localJSONArray4.length() <= 0))
            continue;
          localNewsInfo.mRecordImages = new String[2];
          JSONObject localJSONObject4 = localJSONArray4.getJSONObject(0);
          localNewsInfo.mRecordImages[0] = localJSONObject4.optString("thumbnail", "");
          localNewsInfo.mRecordImages[1] = localJSONObject4.optString("large", "");
          localArrayList.add(localNewsInfo);
          i++;
          continue;
          JSONObject localJSONObject3 = localJSONArray1.optJSONObject(j);
          if (localJSONObject3 == null)
            break label1063;
          localNewsInfo.mVideoSnapshotLIst.add(localJSONObject3.optString("thumb"));
          break label1063;
          localNewsInfo.mNewsId = localJSONObject1.optString("rid", "");
          continue;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("RecordListEngine", "ParseNewsArray", localException);
        return localArrayList;
      }
      String str1 = localJSONObject1.optString("fstar", "0");
      continue;
      label837: JSONObject localJSONObject5 = (JSONObject)localJSONArray3.get(m);
      PhotoItem localPhotoItem = new PhotoItem();
      localPhotoItem.privacy = localJSONObject5.optString("privacy");
      localPhotoItem.index = localJSONObject5.optString("pos");
      localPhotoItem.albumTitle = localJSONObject5.optString("albumtitle");
      localPhotoItem.albumId = localJSONObject5.optString("albumid");
      localPhotoItem.albumType = 2;
      localPhotoItem.thumbnail = localJSONObject5.optString("thumbnail");
      localPhotoItem.pid = localJSONObject5.optString("pid");
      localPhotoItem.title = localJSONObject5.optString("title");
      localPhotoItem.picnum = localJSONObject5.optString("picnum");
      localPhotoItem.visible = localJSONObject5.optString("visible", "1");
      localPhotoItem.fuid = localJSONObject5.optString("uid", null);
      if (localPhotoItem.fuid == null);
      for (String str2 = localNewsInfo.mFuid; ; str2 = localPhotoItem.fuid)
      {
        localPhotoItem.fuid = str2;
        localPhotoItem.large = localJSONObject5.optString("large", "");
        localNewsInfo.mPhotoList.add(localPhotoItem);
        m++;
        break;
      }
      label1057: int j = 0;
      continue;
      label1063: j++;
    }
  }

  public boolean getRecordList(Context paramContext, String paramString, int paramInt)
    throws SecurityErrorException
  {
    if (paramInt == 0)
      RecordListModel.getInstance().clear();
    String str1 = Protocol.getInstance().makeRecordListRequest(paramContext, User.getInstance().getUID(), paramString, paramInt, 10);
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
        KXLog.e("RecordListEngine", "getRecordList error", localException);
    }
    return parseRecordListJSON(paramContext, str2);
  }

  public boolean parseRecordListJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
    if (localJSONObject == null)
      return false;
    if (this.ret == 1)
      try
      {
        ArrayList localArrayList = parseRecordList(localJSONObject.getJSONArray("data"));
        RecordListModel.getInstance().setRecordList(localArrayList);
        int i = localJSONObject.getInt("total");
        RecordListModel.getInstance().setTotal(i);
        return true;
      }
      catch (Exception localException)
      {
        KXLog.e("RecordListEngine", "parseRecordListJSON", localException);
        return false;
      }
    KXLog.d("RecordListEngine failed", paramString);
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.RecordListEngine
 * JD-Core Version:    0.6.0
 */