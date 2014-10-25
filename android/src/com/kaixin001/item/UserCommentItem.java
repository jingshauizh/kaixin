package com.kaixin001.item;

import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.util.ParseNewsInfoUtil;
import java.util.ArrayList;

public class UserCommentItem
{
  String mAbscont;
  String mAbscont_last;
  int mCnum;
  Long mCtime;
  String mCtime_last;
  String mFlogo;
  String mFname;
  String mFname_last;
  String mFuid;
  String mFuid_last;
  int mMtype;
  String mPosTime = null;
  String mThread_cid;
  int mUnread;

  public String getAbscont()
  {
    return this.mAbscont;
  }

  public String getAbscont_last()
  {
    return this.mAbscont_last;
  }

  public int getCnum()
  {
    return this.mCnum;
  }

  public Long getCtime()
  {
    return this.mCtime;
  }

  public String getCtime_last()
  {
    return this.mCtime_last;
  }

  public String getFlogo()
  {
    return this.mFlogo;
  }

  public String getFname()
  {
    return this.mFname;
  }

  public String getFname_last()
  {
    return this.mFname_last;
  }

  public String getFuid()
  {
    return this.mFuid;
  }

  public String getFuid_last()
  {
    return this.mFuid_last;
  }

  public int getMtype()
  {
    return this.mMtype;
  }

  public String getPosTime()
  {
    return this.mPosTime;
  }

  public String getThread_cid()
  {
    return this.mThread_cid;
  }

  public int getUnread()
  {
    return this.mUnread;
  }

  public ArrayList<KXLinkInfo> makeTitleList(String paramString)
  {
    return ParseNewsInfoUtil.parseCommentAndReplyLinkString(paramString);
  }

  public void setAbscont(String paramString)
  {
    this.mAbscont = paramString;
  }

  public void setAbscont_last(String paramString)
  {
    this.mAbscont_last = paramString;
  }

  public void setCnum(int paramInt)
  {
    this.mCnum = paramInt;
  }

  public void setCtime(Long paramLong)
  {
    this.mCtime = paramLong;
  }

  public void setCtime_last(String paramString)
  {
    this.mCtime_last = paramString;
  }

  public void setFlogo(String paramString)
  {
    this.mFlogo = paramString;
  }

  public void setFname(String paramString)
  {
    this.mFname = paramString;
  }

  public void setFname_last(String paramString)
  {
    this.mFname_last = paramString;
  }

  public void setFuid(String paramString)
  {
    this.mFuid = paramString;
  }

  public void setFuid_last(String paramString)
  {
    this.mFuid_last = paramString;
  }

  public void setMtype(int paramInt)
  {
    this.mMtype = paramInt;
  }

  public void setNewnum(int paramInt)
  {
    this.mCnum = paramInt;
  }

  public void setPosTime(String paramString)
  {
    this.mPosTime = paramString;
  }

  public void setThread_cid(String paramString)
  {
    this.mThread_cid = paramString;
  }

  public void setUnread(int paramInt)
  {
    this.mUnread = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.UserCommentItem
 * JD-Core Version:    0.6.0
 */