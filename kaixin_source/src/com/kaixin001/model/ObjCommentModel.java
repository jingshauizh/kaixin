package com.kaixin001.model;

import android.text.TextUtils;
import com.kaixin001.item.ObjCommentItem;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class ObjCommentModel extends KXModel
{
  private static ObjCommentModel instance;
  private boolean bSelfUp = false;
  private ArrayList<ObjCommentItem> commentList = new ArrayList();
  private JSONArray jUpList;
  public String likeStr;
  private LbsCommentTitle mLbsCommentTitle = new LbsCommentTitle();
  private int upcount = 0;

  public void addMainComment(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    try
    {
      if (TextUtils.isEmpty(paramString1))
      {
        ObjCommentItem localObjCommentItem1 = new ObjCommentItem();
        localObjCommentItem1.setContent(paramString1);
        localObjCommentItem1.setThread_cid(paramString2);
        localObjCommentItem1.setStime(paramString3);
        localObjCommentItem1.setFuid(paramString4);
        localObjCommentItem1.setMainThread(1);
        localObjCommentItem1.setFlogo(paramString5);
        localObjCommentItem1.setType(paramString7);
        getCommentList().add(paramInt, localObjCommentItem1);
        return;
      }
      ObjCommentItem localObjCommentItem2 = new ObjCommentItem();
      localObjCommentItem2.setContent(paramString1);
      localObjCommentItem2.setThread_cid(paramString2);
      localObjCommentItem2.setStime(paramString3);
      localObjCommentItem2.setFuid(paramString4);
      localObjCommentItem2.setMainThread(1);
      localObjCommentItem2.setFlogo(paramString5);
      localObjCommentItem2.setFname(paramString6);
      localObjCommentItem2.setType(paramString7);
      getCommentList().add(paramInt, localObjCommentItem2);
      ObjCommentItem localObjCommentItem3 = new ObjCommentItem();
      localObjCommentItem3.setMainThread(-1);
      localObjCommentItem3.setThread_cid(paramString2);
      localObjCommentItem3.setFuid(paramString4);
      localObjCommentItem3.setType(paramString7);
      getCommentList().add(paramInt + 1, localObjCommentItem3);
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("ObjCommentModel", "addMainComment", localException);
    }
  }

  public void addMyUp()
  {
    this.bSelfUp = true;
    this.upcount = (1 + this.upcount);
    if (this.jUpList == null)
      this.jUpList = new JSONArray();
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("uuid", User.getInstance().getUID());
      localJSONObject.put("real_name", "");
      this.jUpList.put(localJSONObject);
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("ObjCommentModel", "addMyUp", localException);
    }
  }

  public void addReply(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    ArrayList localArrayList = getCommentList();
    try
    {
      ObjCommentItem localObjCommentItem = new ObjCommentItem();
      localObjCommentItem.setContent(paramString2);
      localObjCommentItem.setStime(paramString3);
      localObjCommentItem.setMainThread(0);
      localObjCommentItem.setFuid(paramString4);
      localObjCommentItem.setFlogo(paramString5);
      localObjCommentItem.setFname(paramString6);
      localArrayList.add(paramInt, localObjCommentItem);
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("ObjCommentModel", "addMainComment", localException);
    }
  }

  public void clear()
  {
    this.jUpList = null;
    this.bSelfUp = false;
    this.upcount = 0;
    this.commentList.clear();
  }

  public ArrayList<ObjCommentItem> getCommentList()
  {
    return this.commentList;
  }

  public String getFirstUpName()
  {
    Object localObject = "";
    if (this.jUpList == null)
      return localObject;
    while (true)
    {
      int i;
      int j;
      try
      {
        i = this.jUpList.length();
        j = 0;
        break label86;
        JSONObject localJSONObject = this.jUpList.getJSONObject(j);
        if (localJSONObject.getString("uuid").compareTo(User.getInstance().getUID()) != 0)
        {
          String str = localJSONObject.getString("real_name");
          localObject = str;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("ObjCommentModel", "getFirstUpName", localException);
      }
      label86: 
      while (j >= i)
      {
        return localObject;
        j++;
      }
    }
  }

  public int getTotalComment()
  {
    int i = 0;
    ArrayList localArrayList = getCommentList();
    int j = localArrayList.size();
    for (int k = 0; ; k++)
    {
      if (k >= j)
        return i;
      try
      {
        ObjCommentItem localObjCommentItem = (ObjCommentItem)localArrayList.get(k);
        if (localObjCommentItem.getMainThread() == -1)
          continue;
        if ((localObjCommentItem.getMainThread() == 1) && (TextUtils.isEmpty(localObjCommentItem.getFuid())) && (TextUtils.isEmpty(localObjCommentItem.getThread_cid())))
        {
          boolean bool = TextUtils.isEmpty(localObjCommentItem.getStime());
          if (bool)
            continue;
        }
        i++;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
  }

  public String[] getTwoUpNames()
  {
    String[] arrayOfString = new String[2];
    if (this.jUpList == null)
      return arrayOfString;
    while (true)
    {
      int k;
      try
      {
        int i = this.jUpList.length();
        int j = 0;
        k = 0;
        if (k >= i)
          break;
        JSONObject localJSONObject = (JSONObject)this.jUpList.get(k);
        if (localJSONObject.getString("uuid").compareTo(User.getInstance().getUID()) != 0)
        {
          arrayOfString[j] = localJSONObject.getString("real_name");
          j++;
          if (j == 2)
            return arrayOfString;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("ObjCommentModel", "getTwoUpNames", localException);
        return arrayOfString;
      }
      k++;
    }
  }

  public int getUpCount()
  {
    return this.upcount;
  }

  public JSONArray getUpList()
  {
    return this.jUpList;
  }

  public int getUpListCount()
  {
    if (this.jUpList == null)
      return 0;
    return this.jUpList.length();
  }

  public LbsCommentTitle getmLbsCommentTitle()
  {
    return this.mLbsCommentTitle;
  }

  public boolean isEmpty()
  {
    if (this.commentList.size() > 1);
    while (true)
    {
      return false;
      ObjCommentItem localObjCommentItem;
      if (this.commentList.size() == 1)
        localObjCommentItem = (ObjCommentItem)this.commentList.get(0);
      try
      {
        boolean bool = TextUtils.isEmpty(localObjCommentItem.getThread_cid());
        if (!bool)
          continue;
        return true;
      }
      catch (Exception localException)
      {
        while (true)
          KXLog.e("ObjCommentModel", "isEmpty", localException);
      }
    }
  }

  public boolean isSelfUp()
  {
    return this.bSelfUp;
  }

  public void setCommentList(ArrayList<ObjCommentItem> paramArrayList)
  {
    this.commentList = paramArrayList;
  }

  public void setSelfUp(boolean paramBoolean)
  {
    this.bSelfUp = paramBoolean;
  }

  public void setUpCount(int paramInt)
  {
    this.upcount = paramInt;
  }

  public void setUpList(JSONArray paramJSONArray)
  {
    this.jUpList = paramJSONArray;
  }

  public void setmLbsCommentTitle(LbsCommentTitle paramLbsCommentTitle)
  {
    this.mLbsCommentTitle = paramLbsCommentTitle;
  }

  public static class LbsCommentTitle
  {
    public String mChid = null;
    public String mContent = null;
    public String mCtime = null;
    public String mFlogo = null;
    public String mFname = null;
    public String mFuid = null;
    public String mLargePhoto = null;
    public String mPoiId = null;
    public String mPoiName = null;
    public String mSource = null;
    public String mThumbnail = null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.ObjCommentModel
 * JD-Core Version:    0.6.0
 */