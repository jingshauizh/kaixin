package com.kaixin001.model;

import com.kaixin001.item.CircleMsgItem;
import com.kaixin001.item.CircleMsgItem.CircleReplyContent;
import com.kaixin001.item.MessageDetailInfo;
import com.kaixin001.item.MessageDetailItem;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

public class CircleMessageModel extends KXModel
{
  public static final int CONTENT_TYPE_AT = 1;
  public static final int CONTENT_TYPE_MUSIC = 4;
  public static final int CONTENT_TYPE_TEXT = 0;
  public static final int CONTENT_TYPE_URL = 2;
  public static final int CONTENT_TYPE_VIDEO = 3;
  private static final String TAG = "CircleMessageModel";
  private static CircleMessageModel instance = null;
  private String AT = "[|s|]@[|m|]10066329[|m|]-101[|e|]";
  protected int mActiveCircleReplyType = 10;
  protected ArrayList<CircleMsgItem> mMeReplyList = null;
  protected int mMeReplyTotal = 0;
  protected ArrayList<MessageDetailItem> mMessageDetailList;
  private int mNewMeReplyMsg = 0;
  private int mNewReplyMeMsg = 0;
  protected ArrayList<CircleMsgItem> mReplyMeList = null;
  protected int mReplyMeTotal = 0;
  protected int mRetDetailTotal = 0;
  protected int mRetMsgCount = 0;
  protected int mRetNum = 0;

  public static CircleMessageModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CircleMessageModel();
      CircleMessageModel localCircleMessageModel = instance;
      return localCircleMessageModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private String processTextForAt(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap = new HashMap();
    if (paramString.length() == 0)
      return null;
    int i = 0;
    int j = paramString.indexOf("@", i);
    label43: Iterator localIterator;
    if (j < 0)
      localIterator = localArrayList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        return paramString;
        int k = paramString.indexOf("#)", i);
        if ((k < 0) || (j >= k))
          break label43;
        String str1 = paramString.substring(j, k + 2);
        String str2 = str1.substring(1 + str1.indexOf("@"), str1.indexOf("(#")).trim();
        String str3 = str1.substring(1 + str1.indexOf("#"), str1.lastIndexOf("#")).trim();
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append(this.AT).append("[|s|]").append(str3).append("[|m|]").append(str2).append("[|m|]").append("0").append("[|e|]");
        localArrayList.add(str1);
        localHashMap.put(str1, localStringBuffer.toString());
        i = k + 1;
        break;
      }
      String str4 = (String)localIterator.next();
      paramString = paramString.replace(str4, (String)localHashMap.get(str4));
    }
  }

  private void removeListItem(ArrayList<CircleMsgItem> paramArrayList)
  {
    int i = -1 + paramArrayList.size();
    int j = paramArrayList.size() % 10;
    if (j == 0)
      j = 10;
    for (int k = 0; ; k++)
    {
      if (k >= j)
        return;
      paramArrayList.remove(i - k);
    }
  }

  public void clear()
  {
    this.mActiveCircleReplyType = 10;
    this.mReplyMeTotal = 0;
    this.mMeReplyTotal = 0;
    if (this.mReplyMeList != null)
    {
      this.mReplyMeList.clear();
      this.mReplyMeList = null;
    }
    if (this.mMeReplyList != null)
    {
      this.mMeReplyList.clear();
      this.mMeReplyList = null;
    }
    clearCircleMessageDetailList();
    this.mMessageDetailList = null;
    this.mRetDetailTotal = 0;
    this.mRetMsgCount = 0;
    this.mRetNum = 0;
    this.mNewReplyMeMsg = 0;
    this.mNewMeReplyMsg = 0;
  }

  public void clearCircleMessageDetailList()
  {
    if (this.mMessageDetailList != null)
      this.mMessageDetailList.clear();
  }

  public ArrayList<MessageDetailItem> getActiveCircleMesasgeDetail()
  {
    return this.mMessageDetailList;
  }

  public int getActiveCircleReplyType()
  {
    return this.mActiveCircleReplyType;
  }

  public ArrayList<CircleMsgItem> getMeReplyList()
  {
    return this.mMeReplyList;
  }

  public int getMeReplyListTotal()
  {
    return this.mMeReplyTotal;
  }

  public int getMeReplyMsgCnt()
  {
    return this.mNewMeReplyMsg;
  }

  public ArrayList<CircleMsgItem> getReplyMeList()
  {
    return this.mReplyMeList;
  }

  public int getReplyMeListTotal()
  {
    return this.mReplyMeTotal;
  }

  public int getReplyMeMsgCnt()
  {
    return this.mNewReplyMeMsg;
  }

  public int getRetDetailCount()
  {
    return this.mRetMsgCount;
  }

  public int getReturnDetailTotal()
  {
    return this.mRetDetailTotal;
  }

  public int getReturnNum()
  {
    return this.mRetNum;
  }

  public int resetMeReplyMsgCnt()
  {
    this.mNewMeReplyMsg = 0;
    return this.mNewMeReplyMsg;
  }

  public void resetReplyList(int paramInt)
  {
    if ((paramInt == 10) && (this.mReplyMeList.size() > 0))
      removeListItem(this.mReplyMeList);
    do
      return;
    while ((paramInt != 11) || (this.mMeReplyList.size() <= 0));
    removeListItem(this.mMeReplyList);
  }

  public int resetReplyMeMsgCnt()
  {
    this.mNewReplyMeMsg = 0;
    return this.mNewReplyMeMsg;
  }

  public void setActiveCircleMesasgeDetail(MessageDetailInfo paramMessageDetailInfo)
  {
    if (this.mMessageDetailList == null)
      this.mMessageDetailList = new ArrayList();
    JSONArray localJSONArray1 = paramMessageDetailInfo.getDetailList();
    if (localJSONArray1 == null)
      return;
    while (true)
    {
      StringBuilder localStringBuilder;
      int m;
      JSONObject localJSONObject3;
      int n;
      try
      {
        int i = localJSONArray1.length();
        int j = 0;
        if (j >= i)
          break;
        MessageDetailItem localMessageDetailItem = new MessageDetailItem();
        JSONObject localJSONObject1 = (JSONObject)localJSONArray1.get(j);
        JSONArray localJSONArray2 = localJSONObject1.optJSONArray("content");
        int k = localJSONArray2.length();
        localStringBuilder = new StringBuilder();
        m = 0;
        if (m < k)
          continue;
        localMessageDetailItem.setAbscont(localStringBuilder.toString());
        JSONObject localJSONObject2 = localJSONObject1.optJSONObject("userinfo");
        localMessageDetailItem.setCtime(Long.valueOf(localJSONObject1.getLong("ctime")));
        localMessageDetailItem.setFlogo(localJSONObject2.getString("icon120"));
        localMessageDetailItem.setFname(localJSONObject2.getString("name"));
        localMessageDetailItem.setFuid(localJSONObject2.getString("uid"));
        localMessageDetailItem.setMessageAttmList(null);
        this.mMessageDetailList.add(0, localMessageDetailItem);
        j++;
        continue;
        localJSONObject3 = (JSONObject)localJSONArray2.get(m);
        n = localJSONObject3.getInt("type");
        String str = localJSONObject3.getString("txt");
        if (n != 0)
          continue;
        localStringBuilder.append(str);
        break label324;
        if (n == 2)
          localStringBuilder.append(str);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      if (n == 1)
        localStringBuilder.append(processTextForAt("@" + localJSONObject3.getString("uid") + "(#" + localJSONObject3.getString("uname") + "#)"));
      label324: m++;
    }
  }

  public void setActiveCircleReplyType(int paramInt)
  {
    this.mActiveCircleReplyType = paramInt;
  }

  public void setMeReplyListTotal(int paramInt)
  {
    this.mMeReplyTotal = paramInt;
  }

  public void setNewCicleMsgNums()
  {
    this.mNewReplyMeMsg = MessageCenterModel.getInstance().getNoticeCnt(10);
    this.mNewMeReplyMsg = MessageCenterModel.getInstance().getNoticeCnt(11);
  }

  public boolean setReplyList(JSONObject paramJSONObject, int paramInt1, int paramInt2)
  {
    if (paramJSONObject == null)
      return false;
    if (this.mReplyMeList == null)
      this.mReplyMeList = new ArrayList();
    if (this.mMeReplyList == null)
      this.mMeReplyList = new ArrayList();
    if (paramInt2 == 0)
    {
      if (this.mActiveCircleReplyType != 10)
        break label94;
      this.mReplyMeList.clear();
    }
    while (true)
    {
      int k;
      try
      {
        int i = paramJSONObject.getInt("allnum");
        if (10 >= i)
          continue;
        int j = 10;
        k = 0;
        if (k >= j)
        {
          return true;
          label94: this.mMeReplyList.clear();
          continue;
          j = i;
          continue;
        }
        CircleMsgItem localCircleMsgItem = new CircleMsgItem();
        JSONObject localJSONObject1 = paramJSONObject.optJSONObject(String.valueOf(k));
        if (localJSONObject1 == null)
          continue;
        localCircleMsgItem.stid = localJSONObject1.getString("stid");
        localCircleMsgItem.gid = localJSONObject1.getString("gid");
        localCircleMsgItem.circleName = localJSONObject1.getString("gname");
        localCircleMsgItem.quitSession = localJSONObject1.getInt("quitsession");
        localCircleMsgItem.talk_type = localJSONObject1.getInt("talk_type");
        localCircleMsgItem.totalCount = localJSONObject1.getInt("rnum");
        JSONObject localJSONObject2 = localJSONObject1.getJSONObject("suserinfo");
        localCircleMsgItem.sfuid = localJSONObject2.getString("uid");
        localCircleMsgItem.sname = localJSONObject2.getString("name");
        localCircleMsgItem.slogo120 = localJSONObject2.getString("icon120");
        localCircleMsgItem.slogo50 = localJSONObject2.getString("icon50");
        JSONArray localJSONArray1 = localJSONObject1.getJSONArray("main");
        int m = localJSONArray1.length();
        localCircleMsgItem.sContent = new ArrayList();
        int n = 0;
        if (n < m)
          continue;
        localCircleMsgItem.circleUserRole = localJSONObject1.getInt("role");
        JSONObject localJSONObject3 = localJSONObject1.getJSONObject("lastreply");
        localCircleMsgItem.ctime = Long.valueOf(localJSONObject3.getLong("ctime"));
        localCircleMsgItem.tid_last = localJSONObject3.getString("tid");
        JSONArray localJSONArray2 = localJSONObject3.getJSONArray("main");
        int i1 = localJSONArray2.length();
        localCircleMsgItem.content_last = new ArrayList();
        int i2 = 0;
        if (i2 < i1)
          continue;
        JSONObject localJSONObject4 = localJSONObject3.getJSONObject("userinfo");
        localCircleMsgItem.fuid_last = localJSONObject4.getString("uid");
        localCircleMsgItem.fname_last = localJSONObject4.getString("name");
        localCircleMsgItem.logo120_last = localJSONObject4.getString("icon120");
        localCircleMsgItem.logo50_last = localJSONObject4.getString("icon50");
        if (paramInt1 != 10)
          continue;
        this.mReplyMeList.add(localCircleMsgItem);
        break label708;
        JSONObject localJSONObject6 = (JSONObject)localJSONArray1.get(n);
        CircleMsgItem.CircleReplyContent localCircleReplyContent2 = new CircleMsgItem.CircleReplyContent();
        localCircleReplyContent2.txt = localJSONObject6.getString("txt");
        localCircleReplyContent2.type = localJSONObject6.getInt("type");
        if (localCircleReplyContent2.type != 1)
          continue;
        localCircleReplyContent2.atUid = localJSONObject6.getString("uid");
        localCircleReplyContent2.atFname = localJSONObject6.getString("uname");
        localCircleMsgItem.sContent.add(localCircleReplyContent2);
        n++;
        continue;
        JSONObject localJSONObject5 = (JSONObject)localJSONArray2.get(i2);
        CircleMsgItem.CircleReplyContent localCircleReplyContent1 = new CircleMsgItem.CircleReplyContent();
        localCircleReplyContent1.txt = localJSONObject5.getString("txt");
        localCircleReplyContent1.type = localJSONObject5.getInt("type");
        if (localCircleReplyContent1.type != 1)
          continue;
        localCircleReplyContent1.atUid = localJSONObject5.getString("uid");
        localCircleReplyContent1.atFname = localJSONObject5.getString("uname");
        localCircleMsgItem.content_last.add(localCircleReplyContent1);
        i2++;
        continue;
        this.mMeReplyList.add(localCircleMsgItem);
      }
      catch (Exception localException)
      {
        KXLog.e("CircleMessageModel", "setReplyMeList Error", localException);
        return false;
      }
      label708: k++;
    }
  }

  public void setReplyMeListTotal(int paramInt)
  {
    this.mReplyMeTotal = paramInt;
  }

  public void setRetDetailCount(int paramInt)
  {
    this.mRetMsgCount = paramInt;
  }

  public void setReturnDetailTotal(int paramInt)
  {
    this.mRetDetailTotal = paramInt;
  }

  public void setReturnNum(int paramInt)
  {
    this.mRetNum = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.CircleMessageModel
 * JD-Core Version:    0.6.0
 */