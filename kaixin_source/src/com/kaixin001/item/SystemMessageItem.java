package com.kaixin001.item;

import com.kaixin001.util.ParseNewsInfoUtil;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class SystemMessageItem
{
  JSONObject JSONSubInfo;
  JSONArray JSONVideoInfo;
  int mAction = 0;
  String mAddFriendByWho = "";
  String mAppId;
  String mCnum = "0";
  String mContent;
  Long mCtime = Long.valueOf(0L);
  public SystemMsgExtendItem mExtendItem;
  String mFID = null;
  String mFStar = null;
  String mFlogo;
  String mFpri = "1";
  String mFuid;
  String mId;
  String mLocation = null;
  String mMsgType;
  String mOUID = null;
  String mObjID = null;
  String mOname = null;
  String mPostScript = "";
  String mRealName = "";
  String mRecordLarge = null;
  String mRecordThumbnail = null;
  int mResult = 0;
  String mSmid;
  String mSource = "";
  SystemMessageSource mSourceItem = null;
  String mSubTitle = null;
  String mTitle = null;
  String mTnum = "0";
  String mType = "";
  public ArrayList<String> mVideoSnapshotLIst = null;
  String msFriendCount = null;
  String msFriends = null;
  String strCtime;

  public void appendVImgSnapShot(String paramString)
  {
    if (this.mVideoSnapshotLIst == null)
      this.mVideoSnapshotLIst = new ArrayList();
    if (this.mVideoSnapshotLIst != null)
      this.mVideoSnapshotLIst.add(paramString);
  }

  public int getAction()
  {
    return this.mAction;
  }

  public String getAddFriendByWho()
  {
    return this.mAddFriendByWho;
  }

  public String getAppId()
  {
    return this.mAppId;
  }

  public String getCnum()
  {
    return this.mCnum;
  }

  public String getContent()
  {
    return this.mContent;
  }

  public Long getCtime()
  {
    return this.mCtime;
  }

  public String getFStar()
  {
    return this.mFStar;
  }

  public String getFid()
  {
    return this.mFID;
  }

  public String getFlogo()
  {
    return this.mFlogo;
  }

  public String getFpri()
  {
    return this.mFpri;
  }

  public String getFriendCount()
  {
    return this.msFriendCount;
  }

  public String getFriends()
  {
    return this.msFriends;
  }

  public String getFuid()
  {
    return this.mFuid;
  }

  public String getId()
  {
    return this.mId;
  }

  public JSONObject getJSONSubInfo()
  {
    return this.JSONSubInfo;
  }

  public JSONArray getJSONVideoInfo()
  {
    return this.JSONVideoInfo;
  }

  public String getLocation()
  {
    return this.mLocation;
  }

  public String getMsgType()
  {
    return this.mMsgType;
  }

  public String getOName()
  {
    return this.mOname;
  }

  public String getOUID()
  {
    return this.mOUID;
  }

  public String getObjID()
  {
    return this.mObjID;
  }

  public String getPostScript()
  {
    return this.mPostScript;
  }

  public String getRealName()
  {
    return this.mRealName;
  }

  public String getRecordLarge()
  {
    return this.mRecordLarge;
  }

  public String getRecordThumbnail()
  {
    return this.mRecordThumbnail;
  }

  public int getResult()
  {
    return this.mResult;
  }

  public String getSmid()
  {
    return this.mSmid;
  }

  public String getSource()
  {
    return this.mSource;
  }

  public SystemMessageSource getSourceItem()
  {
    return this.mSourceItem;
  }

  public String getStrCtime()
  {
    return this.strCtime;
  }

  public String getSubTitle()
  {
    return this.mSubTitle;
  }

  public String getTitle()
  {
    return this.mTitle;
  }

  public String getTnum()
  {
    return this.mTnum;
  }

  public String getType()
  {
    return this.mType;
  }

  public String getVImgSnapShot(int paramInt)
  {
    if ((this.mVideoSnapshotLIst != null) && (this.mVideoSnapshotLIst.size() > paramInt))
      return (String)this.mVideoSnapshotLIst.get(paramInt);
    return null;
  }

  public ArrayList<KXLinkInfo> makeTitleList(String paramString)
  {
    return ParseNewsInfoUtil.parseNewsLinkString(paramString);
  }

  public void setAction(int paramInt)
  {
    this.mAction = paramInt;
  }

  public void setAddFriendByWho(String paramString)
  {
    this.mAddFriendByWho = paramString;
  }

  public void setAppId(String paramString)
  {
    this.mAppId = paramString;
  }

  public void setCnum(String paramString)
  {
    this.mCnum = paramString;
  }

  public void setContent(String paramString)
  {
    this.mContent = paramString;
  }

  public void setCtime(Long paramLong)
  {
    this.mCtime = paramLong;
  }

  public void setFStar(String paramString)
  {
    this.mFStar = paramString;
  }

  public void setFid(String paramString)
  {
    this.mFID = paramString;
  }

  public void setFlogo(String paramString)
  {
    this.mFlogo = paramString;
  }

  public void setFpri(String paramString)
  {
    this.mFpri = paramString;
  }

  public void setFriendCount(String paramString)
  {
    this.msFriendCount = paramString;
  }

  public void setFriends(String paramString)
  {
    this.msFriends = paramString;
  }

  public void setFuid(String paramString)
  {
    this.mFuid = paramString;
  }

  public void setId(String paramString)
  {
    this.mId = paramString;
  }

  public void setJSONSubInfo(JSONObject paramJSONObject)
  {
    this.JSONSubInfo = paramJSONObject;
  }

  public void setJSONVideoInfo(JSONArray paramJSONArray)
  {
    this.JSONVideoInfo = paramJSONArray;
  }

  public void setLocation(String paramString)
  {
    this.mLocation = paramString;
  }

  public void setMsgType(String paramString)
  {
    this.mMsgType = paramString;
  }

  public void setOName(String paramString)
  {
    this.mOname = paramString;
  }

  public void setOUID(String paramString)
  {
    this.mOUID = paramString;
  }

  public void setObjID(String paramString)
  {
    this.mObjID = paramString;
  }

  public void setPostScript(String paramString)
  {
    this.mPostScript = paramString;
  }

  public void setRealName(String paramString)
  {
    this.mRealName = paramString;
  }

  public void setRecordLarge(String paramString)
  {
    this.mRecordLarge = paramString;
  }

  public void setRecordThumbnail(String paramString)
  {
    this.mRecordThumbnail = paramString;
  }

  public void setResult(int paramInt)
  {
    this.mResult = paramInt;
  }

  public void setSmid(String paramString)
  {
    this.mSmid = paramString;
  }

  public void setSource(String paramString)
  {
    this.mSource = paramString;
  }

  public void setSourceItem(JSONObject paramJSONObject)
  {
    this.mSourceItem = SystemMessageSource.valueOf(paramJSONObject);
  }

  public void setStrCtime(String paramString)
  {
    this.strCtime = paramString;
  }

  public void setSubTitle(String paramString)
  {
    this.mSubTitle = paramString;
  }

  public void setTitle(String paramString)
  {
    this.mTitle = paramString;
  }

  public void setTnum(String paramString)
  {
    this.mTnum = paramString;
  }

  public void setType(String paramString)
  {
    this.mType = paramString;
  }

  public class SystemMsgExtendItem
  {
    public String mWid;

    public SystemMsgExtendItem()
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.SystemMessageItem
 * JD-Core Version:    0.6.0
 */