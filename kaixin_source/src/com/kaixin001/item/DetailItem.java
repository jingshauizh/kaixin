package com.kaixin001.item;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import java.io.Serializable;
import java.util.ArrayList;

public class DetailItem
  implements Serializable
{
  public static final int TYPE_CHECKIN = 1;
  public static final int TYPE_RECORD;
  public int mCanForward;
  private String mClientName;
  private String mCommentFlag = "1";
  private int mCommentNum;
  private String mCthreadCid = "";
  private String mCtime;
  private int mDetailType;
  private String mFlogo;
  private String mFname;
  private int mForwordNum;
  private String mFuid;
  private boolean mHasUp;
  private String mId;
  private String mIntro;
  private boolean mIsSameThreadCid = false;
  private String mLocation;
  private String mMapUrl;
  private boolean mNeedRefresh;
  private String mPicLarge;
  private String mPicThumbnail;
  private String mStime;
  private String mSubFname;
  private String mSubFuid;
  private String mSubId;
  private String mSubIntro;
  private String mSubLocation;
  private String mSubPicLarge;
  private String mSubPicThumbnail;
  private NewsInfo mTagNewsInfo;
  private RecordInfo mTagRecordInfo;
  private String mTitle;
  private String mType;
  private String mUserType;
  private String mVideoIntro;
  private ArrayList<String> mVideoSnapshotLIst;

  public static void initWithRecordInfo(DetailItem paramDetailItem, RecordInfo paramRecordInfo)
  {
    paramDetailItem.mDetailType = 0;
    paramDetailItem.mCanForward = paramRecordInfo.fpri;
    paramDetailItem.mFuid = paramRecordInfo.getRecordFeedFuid();
    paramDetailItem.mFname = paramRecordInfo.getRecordFeedFname();
    paramDetailItem.mFlogo = paramRecordInfo.getRecordFeedFlogo();
    paramDetailItem.mUserType = paramRecordInfo.getRecordFeedStar();
    paramDetailItem.mIntro = paramRecordInfo.getRecordFeedTitle();
    paramDetailItem.mStime = paramRecordInfo.getRecordFeedTime();
    paramDetailItem.mPicThumbnail = paramRecordInfo.getRecordThumbnail();
    paramDetailItem.mPicLarge = paramRecordInfo.getRecordLarge();
    paramDetailItem.mSubId = paramRecordInfo.getRecordFeedSubRid();
    paramDetailItem.mSubIntro = paramRecordInfo.getRecordFeedSubTitle();
    paramDetailItem.mSubPicThumbnail = paramRecordInfo.getRecordSubThumbnail();
    paramDetailItem.mSubPicLarge = paramRecordInfo.getRecordSubLarge();
    paramDetailItem.mSubLocation = paramRecordInfo.getRecordFeedSubLocation();
    if (!TextUtils.isEmpty(paramRecordInfo.getRecordFeedCnum()))
      paramDetailItem.mCommentNum = Integer.parseInt(paramRecordInfo.getRecordFeedCnum());
    if (!TextUtils.isEmpty(paramRecordInfo.getRecordFeedTnum()))
      paramDetailItem.mForwordNum = Integer.parseInt(paramRecordInfo.getRecordFeedTnum());
    paramDetailItem.setTagRecordInfo(paramRecordInfo);
  }

  public static DetailItem makeCheckInDetailItem(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, boolean paramBoolean, int paramInt1, int paramInt2)
  {
    DetailItem localDetailItem = new DetailItem();
    localDetailItem.mDetailType = 1;
    localDetailItem.mCommentFlag = paramString2;
    localDetailItem.mType = paramString1;
    localDetailItem.mFuid = paramString3;
    localDetailItem.mFname = paramString4;
    localDetailItem.mFlogo = paramString5;
    localDetailItem.mUserType = paramString6;
    localDetailItem.mId = paramString7;
    localDetailItem.mIntro = paramString8;
    localDetailItem.mStime = paramString9;
    localDetailItem.mPicThumbnail = paramString10;
    localDetailItem.mPicLarge = paramString11;
    localDetailItem.mMapUrl = paramString12;
    localDetailItem.mLocation = paramString13;
    localDetailItem.mClientName = paramString14;
    localDetailItem.mNeedRefresh = paramBoolean;
    localDetailItem.mCommentNum = paramInt1;
    localDetailItem.mForwordNum = paramInt2;
    return localDetailItem;
  }

  public static DetailItem makeWeiboDetailItem(String paramString1, int paramInt1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, int paramInt2, int paramInt3)
  {
    DetailItem localDetailItem = new DetailItem();
    localDetailItem.mDetailType = 0;
    localDetailItem.mCanForward = paramInt1;
    localDetailItem.mType = paramString1;
    localDetailItem.mFuid = paramString2;
    localDetailItem.mFname = paramString3;
    localDetailItem.mFlogo = paramString4;
    localDetailItem.mUserType = paramString5;
    localDetailItem.mId = paramString6;
    localDetailItem.mIntro = paramString7;
    localDetailItem.mStime = paramString8;
    localDetailItem.mPicThumbnail = paramString9;
    localDetailItem.mPicLarge = paramString10;
    localDetailItem.mSubId = paramString11;
    localDetailItem.mSubFname = paramString12;
    localDetailItem.mSubIntro = paramString13;
    localDetailItem.mSubPicThumbnail = paramString14;
    localDetailItem.mSubPicLarge = paramString15;
    localDetailItem.mSubLocation = paramString16;
    if (!TextUtils.isEmpty(paramString17))
      localDetailItem.mCthreadCid = paramString17;
    localDetailItem.mCommentNum = paramInt2;
    localDetailItem.mForwordNum = paramInt3;
    return localDetailItem;
  }

  public static DetailItem makeWeiboDetailItem(String paramString1, String paramString2, String paramString3)
  {
    DetailItem localDetailItem = new DetailItem();
    localDetailItem.mDetailType = 0;
    localDetailItem.mType = paramString1;
    localDetailItem.mFuid = paramString2;
    localDetailItem.mId = paramString3;
    return localDetailItem;
  }

  public int getCanForward()
  {
    return this.mCanForward;
  }

  public String getClientName()
  {
    return this.mClientName;
  }

  public String getCommentFlag()
  {
    return this.mCommentFlag;
  }

  public int getCommentNum()
  {
    return this.mCommentNum;
  }

  public String getCthreadCid()
  {
    return this.mCthreadCid;
  }

  public String getCtime()
  {
    return this.mCtime;
  }

  public int getDetailType()
  {
    return this.mDetailType;
  }

  public String getFLogo()
  {
    return this.mFlogo;
  }

  public String getFname()
  {
    return this.mFname;
  }

  public int getForwordNum()
  {
    return this.mForwordNum;
  }

  public String getFuid()
  {
    return this.mFuid;
  }

  public String getId()
  {
    return this.mId;
  }

  public String getIntro()
  {
    return this.mIntro;
  }

  public String getLocation()
  {
    return this.mLocation;
  }

  public String getMapUrl()
  {
    return this.mMapUrl;
  }

  public String getPicLarge()
  {
    return this.mPicLarge;
  }

  public String getPicThumbnail()
  {
    return this.mPicThumbnail;
  }

  public String getStime()
  {
    return this.mStime;
  }

  public String getSubFname()
  {
    return this.mSubFname;
  }

  public String getSubFuid()
  {
    return this.mSubFuid;
  }

  public String getSubId()
  {
    return this.mSubId;
  }

  public String getSubIntro()
  {
    return this.mSubIntro;
  }

  public String getSubLocation()
  {
    return this.mSubLocation;
  }

  public String getSubPicLarge()
  {
    return this.mSubPicLarge;
  }

  public String getSubPicThumbnail()
  {
    return this.mSubPicThumbnail;
  }

  public NewsInfo getTagNewsInfo()
  {
    return this.mTagNewsInfo;
  }

  public RecordInfo getTagRecordInfo()
  {
    return this.mTagRecordInfo;
  }

  public String getTitle()
  {
    return this.mTitle;
  }

  public String getType()
  {
    return this.mType;
  }

  public String getTypeName(Context paramContext)
  {
    if (this.mDetailType == 0)
      return paramContext.getResources().getString(2131427892);
    return paramContext.getResources().getString(2131428150);
  }

  public String getUserType()
  {
    return this.mUserType;
  }

  public String getVideoIntro()
  {
    return this.mVideoIntro;
  }

  public ArrayList<String> getVideoSnapshotLIst()
  {
    return this.mVideoSnapshotLIst;
  }

  public boolean isHasUp()
  {
    return this.mHasUp;
  }

  public boolean isNeedRefresh()
  {
    return this.mNeedRefresh;
  }

  public boolean isSameThreadCid()
  {
    return this.mIsSameThreadCid;
  }

  public void setCanForward(int paramInt)
  {
    this.mCanForward = paramInt;
  }

  public void setClientName(String paramString)
  {
    this.mClientName = paramString;
  }

  public void setCommentFlag(String paramString)
  {
    this.mCommentFlag = paramString;
  }

  public void setCommentNum(int paramInt)
  {
    this.mCommentNum = paramInt;
  }

  public void setCthreadCid(String paramString)
  {
    this.mCthreadCid = paramString;
  }

  public void setCtime(String paramString)
  {
    this.mCtime = paramString;
  }

  public void setDetailType(int paramInt)
  {
    this.mDetailType = paramInt;
  }

  public void setFLogo(String paramString)
  {
    this.mFlogo = paramString;
  }

  public void setFname(String paramString)
  {
    this.mFname = paramString;
  }

  public void setForwordNum(int paramInt)
  {
    this.mForwordNum = paramInt;
  }

  public void setFuid(String paramString)
  {
    this.mFuid = paramString;
  }

  public void setHasUp(boolean paramBoolean)
  {
    this.mHasUp = paramBoolean;
  }

  public void setId(String paramString)
  {
    this.mId = paramString;
  }

  public void setIntro(String paramString)
  {
    this.mIntro = paramString;
  }

  public void setLocation(String paramString)
  {
    this.mLocation = paramString;
  }

  public void setMapUrl(String paramString)
  {
    this.mMapUrl = paramString;
  }

  public void setNeedRefresh(boolean paramBoolean)
  {
    this.mNeedRefresh = paramBoolean;
  }

  public void setPicLarge(String paramString)
  {
    this.mPicLarge = paramString;
  }

  public void setPicThumbnail(String paramString)
  {
    this.mPicThumbnail = paramString;
  }

  public void setStime(String paramString)
  {
    this.mStime = paramString;
  }

  public void setSubFname(String paramString)
  {
    this.mSubFname = paramString;
  }

  public void setSubFuid(String paramString)
  {
    this.mSubFuid = paramString;
  }

  public void setSubId(String paramString)
  {
    this.mSubId = paramString;
  }

  public void setSubIntro(String paramString)
  {
    this.mSubIntro = paramString;
  }

  public void setSubLocation(String paramString)
  {
    this.mSubLocation = paramString;
  }

  public void setSubPicLarge(String paramString)
  {
    this.mSubPicLarge = paramString;
  }

  public void setSubPicThumbnail(String paramString)
  {
    this.mSubPicThumbnail = paramString;
  }

  public void setTagNewsInfo(NewsInfo paramNewsInfo)
  {
    this.mTagNewsInfo = paramNewsInfo;
    if (paramNewsInfo != null)
    {
      this.mHasUp = paramNewsInfo.mHasUp.booleanValue();
      this.mVideoSnapshotLIst = paramNewsInfo.mVideoSnapshotLIst;
      if (paramNewsInfo.mOrigRecordIntro != null)
        this.mVideoIntro = paramNewsInfo.mOrigRecordIntro;
    }
    else
    {
      return;
    }
    this.mVideoIntro = paramNewsInfo.mIntro;
  }

  public void setTagRecordInfo(RecordInfo paramRecordInfo)
  {
    this.mTagRecordInfo = paramRecordInfo;
    if (paramRecordInfo != null)
    {
      String str = paramRecordInfo.getRecordVideoLogo(0);
      if (!TextUtils.isEmpty(str))
      {
        this.mVideoSnapshotLIst = new ArrayList();
        this.mVideoSnapshotLIst.add(str);
        if (paramRecordInfo.getRecordFeedSubTitle() == null)
          break label58;
        this.mVideoIntro = paramRecordInfo.getRecordFeedSubTitle();
      }
    }
    return;
    label58: this.mVideoIntro = paramRecordInfo.getRecordFeedTitle();
  }

  public void setTitle(String paramString)
  {
    this.mTitle = paramString;
  }

  public void setType(String paramString)
  {
    this.mType = paramString;
  }

  public void setUserType(String paramString)
  {
    this.mUserType = paramString;
  }

  public void setVideoIntro(String paramString)
  {
    this.mVideoIntro = paramString;
  }

  public void setVideoSnapshotLIst(ArrayList<String> paramArrayList)
  {
    this.mVideoSnapshotLIst = paramArrayList;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.DetailItem
 * JD-Core Version:    0.6.0
 */