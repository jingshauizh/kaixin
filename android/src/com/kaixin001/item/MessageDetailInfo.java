package com.kaixin001.item;

import org.json.JSONArray;
import org.json.JSONObject;

public class MessageDetailInfo
{
  public static final int CIRCLE_REPLY_ME_DETAIL_TYPE = 207;
  public static final int CIRLCE_ME_REPLY_DETAIL_TYPE = 208;
  public static final int COMMENT_DETAIL_TYPE = 203;
  public static final int RECEIVED_USER_COMMNET_DETAIL_TYPE = 205;
  public static final int REPLY_COMMENT_DETAIL_TYPE = 204;
  public static final int SENT_USER_COMMNET_DETAIL_TYPE = 206;
  public static final int SHORT_MESSAGE_DETAIL_INBOX_TYPE = 201;
  public static final int SHORT_MESSAGE_DETAIL_SENT_TYPE = 202;
  private String gid = null;
  private String mAbstractContent = null;
  private int mActiveMessageDetailType = 201;
  private String mAppHtml = null;
  private String mAppName = null;
  private String mAppid = null;
  private String mAtstate = "";
  private String mCRealName = null;
  private String mId = "";
  private JSONObject mJSONAlbumInfo = null;
  private JSONArray mJSONMessageDetailFnames = null;
  private JSONArray mJSONMessageDetailFuids = null;
  private JSONArray mJSONMessageDetailList = null;
  private JSONObject mJSONPhotoInfo = null;
  private String mOriginalId;
  private String mPrivacy = null;
  private JSONObject mRecordContent = null;
  private int mType;
  private int mUserNum;
  private String tid = null;

  public String getAbstractContent()
  {
    return this.mAbstractContent;
  }

  public String getAppHtml()
  {
    return this.mAppHtml;
  }

  public String getAppName()
  {
    return this.mAppName;
  }

  public String getCircleDetailGid()
  {
    return this.gid;
  }

  public String getCircleDetailTid()
  {
    return this.tid;
  }

  public JSONArray getDetailList()
  {
    return this.mJSONMessageDetailList;
  }

  public int getDetailType()
  {
    return this.mActiveMessageDetailType;
  }

  public JSONArray getFnames()
  {
    return this.mJSONMessageDetailFnames;
  }

  public JSONArray getFuids()
  {
    return this.mJSONMessageDetailFuids;
  }

  public String getId()
  {
    return this.mId;
  }

  public int getType()
  {
    return this.mType;
  }

  public int getUserNum()
  {
    return this.mUserNum;
  }

  public String getmAppid()
  {
    return this.mAppid;
  }

  public String getmAtstate()
  {
    return this.mAtstate;
  }

  public String getmCRealName()
  {
    return this.mCRealName;
  }

  public JSONObject getmJSONAlbumInfo()
  {
    return this.mJSONAlbumInfo;
  }

  public JSONObject getmJSONPhotoInfo()
  {
    return this.mJSONPhotoInfo;
  }

  public String getmOriginalId()
  {
    return this.mOriginalId;
  }

  public String getmPrivacy()
  {
    return this.mPrivacy;
  }

  public JSONObject getmRecordContent()
  {
    return this.mRecordContent;
  }

  public void setAbstractContent(String paramString)
  {
    this.mAbstractContent = paramString;
  }

  public void setAppHtml(String paramString)
  {
    this.mAppHtml = paramString;
  }

  public void setAppName(String paramString)
  {
    this.mAppName = paramString;
  }

  public void setCircleDetailGid(String paramString)
  {
    this.gid = paramString;
  }

  public void setCircleDetailTid(String paramString)
  {
    this.tid = paramString;
  }

  public void setDetailList(JSONArray paramJSONArray)
  {
    this.mJSONMessageDetailList = paramJSONArray;
  }

  public void setDetailType(int paramInt)
  {
    this.mActiveMessageDetailType = paramInt;
  }

  public void setFnames(JSONArray paramJSONArray)
  {
    this.mJSONMessageDetailFnames = paramJSONArray;
  }

  public void setFuids(JSONArray paramJSONArray)
  {
    this.mJSONMessageDetailFuids = paramJSONArray;
  }

  public void setId(String paramString)
  {
    this.mId = paramString;
  }

  public void setType(int paramInt)
  {
    this.mType = paramInt;
  }

  public void setUserNum(int paramInt)
  {
    this.mUserNum = paramInt;
  }

  public void setmAppid(String paramString)
  {
    this.mAppid = paramString;
  }

  public void setmAtstate(String paramString)
  {
    this.mAtstate = paramString;
  }

  public void setmCRealName(String paramString)
  {
    this.mCRealName = paramString;
  }

  public void setmJSONAlbumInfo(JSONObject paramJSONObject)
  {
    this.mJSONAlbumInfo = paramJSONObject;
  }

  public void setmJSONPhotoInfo(JSONObject paramJSONObject)
  {
    this.mJSONPhotoInfo = paramJSONObject;
  }

  public void setmOriginalId(String paramString)
  {
    this.mOriginalId = paramString;
  }

  public void setmPrivacy(String paramString)
  {
    this.mPrivacy = paramString;
  }

  public void setmRecordContent(JSONObject paramJSONObject)
  {
    this.mRecordContent = paramJSONObject;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.MessageDetailInfo
 * JD-Core Version:    0.6.0
 */