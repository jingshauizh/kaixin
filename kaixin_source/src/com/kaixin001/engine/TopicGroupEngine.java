package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.GiftItem;
import com.kaixin001.item.LogoItem;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.item.PhotoItem;
import com.kaixin001.item.RepItem;
import com.kaixin001.item.VoteItem;
import com.kaixin001.model.TopicModel;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.media.KXMediaInfo;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class TopicGroupEngine extends KXEngine
{
  private static final String LOGTAG = "TopicGroupEngine";
  public static final int NUM = 20;
  public static final int RESULT_FAILED;
  private static TopicGroupEngine instance;
  public String msg;

  public static TopicGroupEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new TopicGroupEngine();
      TopicGroupEngine localTopicGroupEngine = instance;
      return localTopicGroupEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private boolean parseNewsArray(JSONArray paramJSONArray, TopicModel paramTopicModel)
  {
    ArrayList localArrayList = paramTopicModel.getTopicList();
    if (localArrayList == null)
      return false;
    int i = 0;
    while (true)
    {
      JSONObject localJSONObject1;
      NewsInfo localNewsInfo;
      JSONArray localJSONArray3;
      int i11;
      JSONArray localJSONArray4;
      int i7;
      JSONArray localJSONArray5;
      int i5;
      JSONArray localJSONArray8;
      int i3;
      JSONArray localJSONArray9;
      int i1;
      JSONArray localJSONArray10;
      int m;
      try
      {
        if (i >= paramJSONArray.length())
          return true;
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
        localNewsInfo.mIntroShort = localJSONObject1.optString("intro_short", "");
        localNewsInfo.mSubTitle = localJSONObject1.optString("sub_title", "");
        localNewsInfo.mMapUrl = localJSONObject1.optString("mapurl", "");
        localNewsInfo.mGiftType = localJSONObject1.optString("gifttype");
        JSONObject localJSONObject2 = localJSONObject1.optJSONObject("sub_info");
        JSONArray localJSONArray1 = localJSONObject1.optJSONArray("video");
        if ((localJSONArray1 != null) || (localJSONObject2 == null))
          continue;
        localJSONArray1 = localJSONObject2.optJSONArray("video");
        if (localJSONArray1 == null)
          continue;
        if (localNewsInfo.mVideoSnapshotLIst != null)
          break label2260;
        localNewsInfo.mVideoSnapshotLIst = new ArrayList();
        break label2260;
        int k = localJSONArray1.length();
        if (j < k)
          continue;
        localNewsInfo.mMediaInfo = null;
        String str1 = localJSONObject1.optString("audiourl");
        if (TextUtils.isEmpty(str1))
          continue;
        localNewsInfo.mMediaInfo = new KXMediaInfo();
        localNewsInfo.mMediaInfo.setId(localNewsInfo.mRid);
        localNewsInfo.mMediaInfo.setUrl(str1);
        localNewsInfo.mMediaInfo.setDuration(localJSONObject1.optString("aulen", "1"));
        if (localJSONObject2 == null)
          continue;
        localNewsInfo.mSubMediaInfo = null;
        String str2 = localJSONObject2.optString("audiourl");
        if (TextUtils.isEmpty(str2))
          continue;
        localNewsInfo.mSubMediaInfo = new KXMediaInfo();
        localNewsInfo.mSubMediaInfo.setId(localNewsInfo.mRid);
        localNewsInfo.mSubMediaInfo.setUrl(str2);
        localNewsInfo.mSubMediaInfo.setDuration(localJSONObject2.optString("aulen", "1"));
        localNewsInfo.mOrigRecordId = localJSONObject2.optString("rid");
        localNewsInfo.mOrigRecordIntro = localJSONObject2.optString("intro");
        localNewsInfo.mOrigRecordLocation = localJSONObject2.optString("location");
        localNewsInfo.mOrigRecordTitle = localJSONObject2.optString("sub_title", "");
        JSONArray localJSONArray2 = localJSONObject2.optJSONArray("images");
        if ((localJSONArray2 == null) || (localJSONArray2.length() <= 0))
          continue;
        localNewsInfo.mRecordImages = new String[2];
        JSONObject localJSONObject12 = localJSONArray2.getJSONObject(0);
        localNewsInfo.mRecordImages[0] = localJSONObject12.optString("thumbnail", "");
        localNewsInfo.mRecordImages[1] = localJSONObject12.optString("large", "");
        localNewsInfo.mCtime = localJSONObject1.optString("ctime", "");
        localNewsInfo.mStime = localJSONObject1.optString("stime", "");
        localNewsInfo.mThumbnail = localJSONObject1.optString("thumbnail", "");
        if (localJSONObject1.isNull("id"))
          continue;
        localNewsInfo.mNewsId = localJSONObject1.optString("id");
        if (localJSONObject1.opt("star") != null)
        {
          str3 = localJSONObject1.optString("star", "0");
          localNewsInfo.mStar = str3;
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
          i11 = 0;
          if (i11 < localJSONArray3.length())
            break label1478;
          localJSONArray4 = localJSONObject1.optJSONArray("replist");
          if ((localJSONArray4 == null) || (localJSONArray4.length() <= 0))
            continue;
          localNewsInfo.mRepostList = new ArrayList();
          i7 = 0;
          int i8 = localJSONArray4.length();
          if (i7 < i8)
            break label1698;
          localJSONArray5 = localJSONObject1.optJSONArray("vote_list");
          if ((localJSONArray5 == null) || (localJSONArray5.length() <= 0))
            continue;
          localNewsInfo.mVoteList = new ArrayList();
          i5 = 0;
          int i6 = localJSONArray5.length();
          if (i5 < i6)
            break label1934;
          JSONArray localJSONArray6 = localJSONObject1.optJSONArray("images");
          if ((localJSONArray6 == null) || (localJSONArray6.length() <= 0))
            continue;
          localNewsInfo.mRecordImages = new String[2];
          JSONObject localJSONObject8 = localJSONArray6.getJSONObject(0);
          localNewsInfo.mRecordImages[0] = localJSONObject8.optString("thumbnail", "");
          localNewsInfo.mRecordImages[1] = localJSONObject8.optString("large", "");
          JSONArray localJSONArray7 = localJSONObject1.optJSONArray("imglist");
          if (!"2".equals(localNewsInfo.mNtype))
            continue;
          localNewsInfo.mContent = localJSONObject1.optString("content", "");
          if ((localJSONArray7 == null) || (localJSONArray7.length() <= 0))
            continue;
          localNewsInfo.mRecordImages = new String[2];
          JSONObject localJSONObject7 = localJSONArray7.getJSONObject(0);
          localNewsInfo.mRecordImages[0] = localJSONObject7.optString("thumbnail", "");
          localNewsInfo.mRecordImages[1] = localJSONObject7.optString("large", "");
          if ((!"1192".equals(localNewsInfo.mNtype)) || (localNewsInfo.mPhotoList == null) || (localNewsInfo.mPhotoList.size() <= 0))
            continue;
          localNewsInfo.mRecordImages = new String[2];
          PhotoItem localPhotoItem1 = (PhotoItem)localNewsInfo.mPhotoList.get(0);
          localNewsInfo.mRecordImages[0] = localPhotoItem1.thumbnail;
          localNewsInfo.mRecordImages[1] = localPhotoItem1.large;
          localJSONArray8 = localJSONObject1.optJSONArray("sub_userlist");
          if ((localJSONArray8 == null) || (localJSONArray8.length() <= 0))
            continue;
          localNewsInfo.mlogoList = new ArrayList();
          i3 = 0;
          int i4 = localJSONArray8.length();
          if (i3 < i4)
            break label2047;
          localJSONArray9 = localJSONObject1.optJSONArray("friendlist");
          if ((localJSONArray9 == null) || (localJSONArray9.length() <= 0))
            continue;
          localNewsInfo.mlogoList = new ArrayList();
          i1 = 0;
          int i2 = localJSONArray9.length();
          if (i1 < i2)
            break label2124;
          localJSONArray10 = localJSONObject1.optJSONArray("sub_giftlist");
          if ((localJSONArray10 == null) || (localJSONArray10.length() <= 0))
            continue;
          localNewsInfo.mgiftList = new ArrayList();
          m = 0;
          int n = localJSONArray10.length();
          if (m < n)
            break label2198;
          localArrayList.add(localNewsInfo);
          i++;
          continue;
          JSONObject localJSONObject3 = localJSONArray1.optJSONObject(j);
          if (localJSONObject3 == null)
            break label2266;
          localNewsInfo.mVideoSnapshotLIst.add(localJSONObject3.optString("thumb"));
          break label2266;
          localNewsInfo.mNewsId = localJSONObject1.optString("rid", "");
          continue;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("TopicGroupEngine", "ParseNewsArray", localException);
        return false;
      }
      String str3 = localJSONObject1.optString("fstar", "0");
      continue;
      label1478: JSONObject localJSONObject11 = (JSONObject)localJSONArray3.get(i11);
      PhotoItem localPhotoItem2 = new PhotoItem();
      localPhotoItem2.privacy = localJSONObject11.optString("privacy");
      localPhotoItem2.index = localJSONObject11.optString("pos");
      localPhotoItem2.albumTitle = localJSONObject11.optString("albumtitle");
      localPhotoItem2.albumId = localJSONObject11.optString("albumid");
      localPhotoItem2.albumType = 2;
      localPhotoItem2.thumbnail = localJSONObject11.optString("thumbnail");
      localPhotoItem2.pid = localJSONObject11.optString("pid");
      localPhotoItem2.title = localJSONObject11.optString("title");
      localPhotoItem2.picnum = localJSONObject11.optString("picnum");
      localPhotoItem2.visible = localJSONObject11.optString("visible", "1");
      localPhotoItem2.fuid = localJSONObject11.optString("uid", null);
      if (localPhotoItem2.fuid == null);
      for (String str5 = localNewsInfo.mFuid; ; str5 = localPhotoItem2.fuid)
      {
        localPhotoItem2.fuid = str5;
        localPhotoItem2.large = localJSONObject11.optString("large", "");
        localNewsInfo.mPhotoList.add(localPhotoItem2);
        i11++;
        break;
      }
      label1698: JSONObject localJSONObject10 = (JSONObject)localJSONArray4.get(i7);
      RepItem localRepItem = new RepItem();
      localRepItem.id = localJSONObject10.optString("id");
      localRepItem.category = localJSONObject10.optInt("category");
      localRepItem.title = localJSONObject10.optString("title");
      localRepItem.myview = localJSONObject10.optString("myview", "");
      localRepItem.ftitle = localJSONObject10.optString("ftitle", "");
      localRepItem.vthumb = localJSONObject10.optString("vthumb", "");
      JSONArray localJSONArray11 = localJSONObject10.optJSONArray("imglist");
      localRepItem.mThumbImg = null;
      if ((localJSONArray11 != null) && (localJSONArray11.length() > 0))
      {
        localRepItem.mThumbImg = ((String)localJSONArray11.get(0));
        localRepItem.mRepostImgList = new ArrayList();
      }
      for (int i9 = 0; ; i9++)
      {
        int i10 = localJSONArray11.length();
        if (i9 >= i10)
        {
          localRepItem.mContent = localJSONObject10.optString("abstract", "");
          localNewsInfo.mRepostList.add(localRepItem);
          i7++;
          break;
        }
        String str4 = (String)localJSONArray11.get(i9);
        localRepItem.mRepostImgList.add(str4);
      }
      label1934: JSONObject localJSONObject9 = (JSONObject)localJSONArray5.get(i5);
      VoteItem localVoteItem = new VoteItem();
      localVoteItem.mType = localJSONObject9.optString("type");
      localVoteItem.mUid = localJSONObject9.optString("uid");
      localVoteItem.mToUid = localJSONObject9.optString("touid");
      localVoteItem.mCtime = localJSONObject9.optString("ctime");
      localVoteItem.mId = localJSONObject9.optString("id");
      localVoteItem.mTitle = localJSONObject9.optString("title");
      localNewsInfo.mVoteList.add(localVoteItem);
      i5++;
      continue;
      label2047: JSONObject localJSONObject6 = (JSONObject)localJSONArray8.get(i3);
      LogoItem localLogoItem2 = new LogoItem();
      localLogoItem2.slogo = localJSONObject6.optString("slogo");
      localLogoItem2.sname = localJSONObject6.optString("sname");
      localLogoItem2.suid = localJSONObject6.optString("suid");
      localNewsInfo.mlogoList.add(localLogoItem2);
      i3++;
      continue;
      label2124: JSONObject localJSONObject5 = (JSONObject)localJSONArray9.get(i1);
      LogoItem localLogoItem1 = new LogoItem();
      localLogoItem1.slogo = localJSONObject5.optString("flogo");
      localLogoItem1.sname = localJSONObject5.optString("fname");
      localLogoItem1.suid = localJSONObject5.optString("fuid");
      localNewsInfo.mlogoList.add(localLogoItem1);
      i1++;
      continue;
      label2198: JSONObject localJSONObject4 = (JSONObject)localJSONArray10.get(m);
      GiftItem localGiftItem = new GiftItem(localJSONObject4.optString("gid"), localJSONObject4.optString("gname"), localJSONObject4.optString("gicon"));
      localNewsInfo.mgiftList.add(localGiftItem);
      m++;
      continue;
      label2260: int j = 0;
      continue;
      label2266: j++;
    }
  }

  public boolean getTopicGroupData(Context paramContext, TopicModel paramTopicModel, String paramString1, String paramString2, String paramString3, String paramString4)
    throws SecurityErrorException
  {
    this.ret = 0;
    int i = Integer.parseInt(paramString2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str1 = Protocol.getInstance().makeGetTopicGroupRequest(paramString4, paramString1, paramString2, paramString3);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (!parseTopicGroupJSON(paramContext, true, str2, paramTopicModel, i))
        return false;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("TopicGroupEngine", "getNewsData error", localException);
        String str2 = null;
      }
      this.ret = 1;
      if (this.ret == 1)
        return true;
    }
    return false;
  }

  public boolean parseTopicGroupJSON(Context paramContext, boolean paramBoolean, String paramString, TopicModel paramTopicModel, int paramInt)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramBoolean, paramString);
    if (localJSONObject == null);
    while (true)
    {
      return false;
      try
      {
        KXLog.d("TopicGroupEngine", "getNewsData response=" + localJSONObject.toString());
        paramTopicModel.total = localJSONObject.optInt("total", 0);
        paramTopicModel.detail = localJSONObject.getString("detail");
        paramTopicModel.title = localJSONObject.getString("title");
        if (paramInt == 0)
          paramTopicModel.clear();
        JSONArray localJSONArray = localJSONObject.optJSONArray("records");
        if (localJSONArray == null)
          continue;
        boolean bool = parseNewsArray(localJSONArray, paramTopicModel);
        if (bool)
          return true;
      }
      catch (Exception localException)
      {
        KXLog.e("TopicGroupEngine", "parseTopicGroupJSON", localException);
      }
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.TopicGroupEngine
 * JD-Core Version:    0.6.0
 */