package com.kaixin001.item;

import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.util.ParseNewsInfoUtil;
import java.util.ArrayList;

public class MessageDetailItem
{
  String mAbscont;
  boolean mAttm;
  long mCtime = 0L;
  String mFlogo;
  String mFname;
  String mFuid;
  private ArrayList<MessageAttachmentItem> mMessageAttmList;
  String mMid;
  int mStatus = -1;
  String mStrctime;
  int mUploadTaskID = -1;

  public String getAbscont()
  {
    return this.mAbscont;
  }

  public Long getCtime()
  {
    return Long.valueOf(this.mCtime);
  }

  public String getFlogo()
  {
    return this.mFlogo;
  }

  public String getFname()
  {
    return this.mFname;
  }

  public String getFuid()
  {
    return this.mFuid;
  }

  public ArrayList<MessageAttachmentItem> getMessageAttmList()
  {
    return this.mMessageAttmList;
  }

  public String getMessageId()
  {
    return this.mMid;
  }

  public int getStatues()
  {
    return this.mStatus;
  }

  public String getStrctime()
  {
    return this.mStrctime;
  }

  public int getTaskID()
  {
    return this.mUploadTaskID;
  }

  public boolean isAttm()
  {
    return this.mAttm;
  }

  public ArrayList<KXLinkInfo> makeTitleList(String paramString)
  {
    return ParseNewsInfoUtil.parseCommentAndReplyLinkString(paramString);
  }

  public void setAbscont(String paramString)
  {
    this.mAbscont = paramString;
  }

  public void setAttm(boolean paramBoolean)
  {
    this.mAttm = paramBoolean;
  }

  public void setCtime(Long paramLong)
  {
    this.mCtime = paramLong.longValue();
  }

  public void setFlogo(String paramString)
  {
    this.mFlogo = paramString;
  }

  public void setFname(String paramString)
  {
    this.mFname = paramString;
  }

  public void setFuid(String paramString)
  {
    this.mFuid = paramString;
  }

  public void setMessageAttmList(ArrayList<MessageAttachmentItem> paramArrayList)
  {
    this.mMessageAttmList = paramArrayList;
  }

  public void setMessageId(String paramString)
  {
    this.mMid = paramString;
  }

  public void setStatus(int paramInt)
  {
    this.mStatus = paramInt;
  }

  public void setStrctime(String paramString)
  {
    this.mStrctime = paramString;
  }

  public void setTaskID(int paramInt)
  {
    this.mUploadTaskID = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.MessageDetailItem
 * JD-Core Version:    0.6.0
 */