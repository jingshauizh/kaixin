package com.kaixin001.model;

import android.text.TextUtils;
import com.kaixin001.item.CircleDetailsItem;
import com.kaixin001.item.CircleDetailsItem.CircleDetailsContent;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;

public class CircleDetailsModel extends KXModel
{
  public static final int CHATGROUPS_DETAILS_TYPE_AT = 1;
  public static final int CHATGROUPS_DETAILS_TYPE_MUSIC = 4;
  public static final int CHATGROUPS_DETAILS_TYPE_TEXT = 0;
  public static final int CHATGROUPS_DETAILS_TYPE_URL = 2;
  public static final int CHATGROUPS_DETAILS_TYPE_VIDEO = 3;
  private static CircleDetailsModel instance;
  private ArrayList<CircleDetailsItem> commentList = new ArrayList();
  private CircleDetailsHeader mHeader = new CircleDetailsHeader();
  private int mTotalRply = -1;

  public static CircleDetailsModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CircleDetailsModel();
      CircleDetailsModel localCircleDetailsModel = instance;
      return localCircleDetailsModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void addMainComment(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    try
    {
      if (TextUtils.isEmpty(paramString1))
      {
        CircleDetailsItem localCircleDetailsItem = new CircleDetailsItem();
        localCircleDetailsItem.mContentList.add(new CircleDetailsItem.CircleDetailsContent(paramString1, 0));
        localCircleDetailsItem.name = paramString6;
        localCircleDetailsItem.uid = paramString4;
        localCircleDetailsItem.icon50 = null;
        getCommentList().add(paramInt, localCircleDetailsItem);
      }
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("CircleDetailsModel", "addMainComment", localException);
    }
  }

  public void clear()
  {
    this.commentList.clear();
    this.mHeader.clear();
  }

  public ArrayList<CircleDetailsItem> getCommentList()
  {
    return this.commentList;
  }

  public CircleDetailsHeader getDetailsHeader()
  {
    return this.mHeader;
  }

  public int getTotalReply()
  {
    return this.mTotalRply;
  }

  public void setCommentList(ArrayList<CircleDetailsItem> paramArrayList)
  {
    this.commentList = paramArrayList;
  }

  public void setDetailsHeader(CircleDetailsHeader paramCircleDetailsHeader)
  {
    this.mHeader = paramCircleDetailsHeader;
  }

  public void setTotalReply(int paramInt)
  {
    this.mTotalRply = paramInt;
  }

  public static class CircleDetailsHeader
  {
    public long ctime = -1L;
    public String fpic = null;
    public String gender = null;
    public String gid = null;
    public String icon120 = null;
    public String icon50 = null;
    public String inforesource = null;
    public ArrayList<CircleDetailsModel.CircleDetailsHeaderMain> mHeaderList = new ArrayList();
    public int mInfoType = -1;
    public ArrayList<KaixinUser> mInviteUsersList = new ArrayList();
    public String name = null;
    public String online = null;
    public String picid = null;
    public String relation = null;
    public String spic = null;
    public String tid = null;
    public String uid = null;

    public void clear()
    {
      this.tid = null;
      this.gid = null;
      this.uid = null;
      this.ctime = -1L;
      this.relation = null;
      this.name = null;
      this.gender = null;
      this.online = null;
      this.icon120 = null;
      this.icon50 = null;
      this.spic = null;
      this.fpic = null;
      this.picid = null;
      this.inforesource = null;
      this.mHeaderList.clear();
      this.mInviteUsersList.clear();
    }
  }

  public static class CircleDetailsHeaderMain
  {
    public String mImgUrl = null;
    public String mName = null;
    public String mSwfUrl = null;
    public String mText = null;
    public String mTitle = null;
    public int mType = -1;
    public String mUid = null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.CircleDetailsModel
 * JD-Core Version:    0.6.0
 */