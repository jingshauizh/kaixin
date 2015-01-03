package com.kaixin001.model;

import com.kaixin001.item.RepItem;
import java.util.ArrayList;
import org.json.JSONArray;

public class RepostModel extends KXModel
{
  private static RepostModel instance = null;
  private JSONArray dataList;
  private String lasturpid = "";
  private JSONArray mAnswerList = null;
  private int mCNum = 0;
  private String mContent = "";
  public String mDes;
  private int mErrorNum = 0;
  private String mFName = "";
  private String mFuid = "";
  public String mImageUrl;
  private ArrayList<RepItem> mMoreRepostList = new ArrayList();
  private int mNewflag = 0;
  private JSONArray mResultList = null;
  private int mRpNum = 0;
  private String mRpid = "";
  private String mSuid = "";
  private String mSurpid = "";
  private JSONArray mTagList = null;
  public String mTitle;
  private int mTotalAll = 0;
  private String mUrpid = "";
  private String mVoteuid = "";
  public String mWapUrl;
  private ArrayList<RepItem> moreRepItemsData;
  protected String posterIcon;
  protected boolean star;

  public static RepostModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new RepostModel();
      RepostModel localRepostModel = instance;
      return localRepostModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    this.mContent = "";
    this.mRpid = "";
    this.mFuid = "";
    this.mFName = "";
    this.mRpNum = 0;
    this.mAnswerList = null;
    this.mResultList = null;
    this.mSurpid = "";
    this.mUrpid = "";
    this.mSuid = "";
    this.mVoteuid = "";
    this.mMoreRepostList = null;
    this.lasturpid = "";
    this.mNewflag = 0;
    this.mCNum = 0;
    this.mTagList = null;
    this.mTotalAll = 0;
    this.mTitle = "";
    this.mDes = "";
    this.mImageUrl = "";
    this.mWapUrl = "";
  }

  public void clearDataList()
  {
    if (this.dataList != null)
      this.dataList = null;
  }

  public int getAllTotal()
  {
    return this.mTotalAll;
  }

  public JSONArray getAnswerList()
  {
    return this.mAnswerList;
  }

  public int getCNum()
  {
    return this.mCNum;
  }

  public JSONArray getDataList()
  {
    return this.dataList;
  }

  public int getErrorNum()
  {
    return this.mErrorNum;
  }

  public String getLasturpid()
  {
    return this.lasturpid;
  }

  public ArrayList<RepItem> getMoreRepItemsData()
  {
    return this.moreRepItemsData;
  }

  public ArrayList<RepItem> getMoreRepostList()
  {
    return this.mMoreRepostList;
  }

  public int getNewflag()
  {
    return this.mNewflag;
  }

  public String getPosterIcon()
  {
    return this.posterIcon;
  }

  public String getRepostContent()
  {
    return this.mContent;
  }

  public String getRepostFname()
  {
    return this.mFName;
  }

  public String getRepostFuid()
  {
    return this.mFuid;
  }

  public String getRepostId()
  {
    return this.mRpid;
  }

  public String getRepostSuid()
  {
    return this.mSuid;
  }

  public String getRepostSurpid()
  {
    return this.mSurpid;
  }

  public String getRepostUrpid()
  {
    return this.mUrpid;
  }

  public String getRepostVoteuid()
  {
    return this.mVoteuid;
  }

  public JSONArray getResultList()
  {
    return this.mResultList;
  }

  public int getRpNum()
  {
    return this.mRpNum;
  }

  public JSONArray getTagList()
  {
    return this.mTagList;
  }

  public boolean isStar()
  {
    return this.star;
  }

  public void setAllTotal(int paramInt)
  {
    this.mTotalAll = paramInt;
  }

  public void setAnswerList(JSONArray paramJSONArray)
  {
    this.mAnswerList = paramJSONArray;
  }

  public void setCNum(int paramInt)
  {
    this.mCNum = paramInt;
  }

  public void setDataList(JSONArray paramJSONArray)
  {
    this.dataList = paramJSONArray;
  }

  public void setErrorNum(int paramInt)
  {
    this.mErrorNum = paramInt;
  }

  public void setLasturpid(String paramString)
  {
    this.lasturpid = paramString;
  }

  public void setMoreRepItemsData(ArrayList<RepItem> paramArrayList)
  {
    this.moreRepItemsData = paramArrayList;
  }

  public void setMoreRepostList(ArrayList<RepItem> paramArrayList, String paramString)
  {
    if (this.mMoreRepostList == null)
      this.mMoreRepostList = new ArrayList();
    if ((paramArrayList != null) && (paramArrayList.size() > 0))
      this.mMoreRepostList.addAll(paramArrayList);
    this.lasturpid = paramString;
  }

  public void setNewflag(int paramInt)
  {
    this.mNewflag = paramInt;
  }

  public void setPosterIcon(String paramString)
  {
    this.posterIcon = paramString;
  }

  public void setRepostContent(String paramString)
  {
    if (paramString != null)
      this.mContent = paramString;
  }

  public void setRepostFname(String paramString)
  {
    if (paramString != null)
      this.mFName = paramString;
  }

  public void setRepostFuid(String paramString)
  {
    if (paramString != null)
      this.mFuid = paramString;
  }

  public void setRepostId(String paramString)
  {
    if (paramString != null)
      this.mRpid = paramString;
  }

  public void setRepostSuid(String paramString)
  {
    if (paramString != null)
      this.mSuid = paramString;
  }

  public void setRepostSurpid(String paramString)
  {
    if (paramString != null)
      this.mSurpid = paramString;
  }

  public void setRepostUrpid(String paramString)
  {
    if (paramString != null)
      this.mUrpid = paramString;
  }

  public void setRepostVoteuid(String paramString)
  {
    if (paramString != null)
      this.mVoteuid = paramString;
  }

  public void setResultList(JSONArray paramJSONArray)
  {
    this.mResultList = paramJSONArray;
  }

  public void setRpNum(int paramInt)
  {
    this.mRpNum = paramInt;
  }

  public void setStar(boolean paramBoolean)
  {
    this.star = paramBoolean;
  }

  public void setTagList(JSONArray paramJSONArray)
  {
    this.mTagList = paramJSONArray;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.RepostModel
 * JD-Core Version:    0.6.0
 */