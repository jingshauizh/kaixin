package com.kaixin001.model;

import android.text.TextUtils;

import com.kaixin001.item.ChatInfoItem;
import com.kaixin001.item.ChatInfoItem.NewMessage;
import com.kaixin001.item.CommentItem;
import com.kaixin001.item.MessageAttachmentItem;
import com.kaixin001.item.MessageDetailInfo;
import com.kaixin001.item.MessageDetailItem;
import com.kaixin001.item.MessageItem;
import com.kaixin001.item.SystemMessageItem;
import com.kaixin001.item.SystemMessageItem.SystemMsgExtendItem;
import com.kaixin001.item.UserCommentItem;
import com.kaixin001.util.KXLog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageCenterModel extends KXModel
{
  public static final int CIRCLE_MESSAGE_MY_REPLY = 11;
  public static final int CIRCLE_MESSAGE_REPLY_ME = 10;
  public static final int CIRCLE_MESSAGE_TYPE = 12;
  public static final int COMMENT_TYPE = 3;
  public static final int RECEIVED_USER_COMMNET_TYPE = 2;
  public static final int REPLY_COMMENT_TYPE = 6;
  public static final int SENT_USER_COMMNET_TYPE = 5;
  public static final int SHORT_MESSAGE_INBOX = 101;
  public static final int SHORT_MESSAGE_OUTBOX = 102;
  public static final int SHORT_MESSAGE_TYPE = 1;
  public static final int SYSTEM_MENTION_ME_TYPE = 8;
  public static final int SYSTEM_MESSAGE_TYPE = 4;
  public static final int SYSTEM_NOTICE_TYPE = 7;
  public static final int SYSTEM_REPOST_TYPE = 9;
  private static final String TAG = "MessageCenterModel";
  private static MessageCenterModel instance;
  protected int mActiveCommentType = 3;
  protected MessageDetailInfo mActiveMesageDetail = null;
  protected int mActiveMessageType = 101;
  protected int mActiveUserCommentType = 2;
  protected JSONArray mCommentArray = null;
  protected ArrayList<CommentItem> mCommentList = null;
  protected int mCommentTotal = 0;
  protected ArrayList<Integer> mLast3Notices = new ArrayList();
  protected int mMentionMeReturnNum = 0;
  protected int mMentionMeTotal = 0;
  protected ArrayList<MessageDetailItem> mMessageDetailList;
  protected JSONArray mMessageInboxArray = null;
  protected ArrayList<MessageItem> mMessageInboxList = null;
  protected JSONArray mMessageSentArray = null;
  protected ArrayList<MessageItem> mMessageSentList = null;
  protected int mMsgInboxTotal = 0;
  protected int mMsgOutboxTotal = 0;
  protected ArrayList<Integer> mNotices = new ArrayList();
  protected int mOldSystemNoticeCnt = 0;
  protected JSONArray mReplyCommentArray = null;
  protected ArrayList<CommentItem> mReplyCommentList = null;
  protected int mReplyCommentTotal = 0;
  protected int mRepostReturnNum = 0;
  protected int mRepostTotal = 0;
  protected int mReturnNum = 0;
  protected JSONArray mSentUserCommentArray = null;
  protected ArrayList<UserCommentItem> mSentUserCommentList = null;
  protected int mSentUserCommentTotal = 0;
  protected JSONArray mSystemMentionMeArray = null;
  protected ArrayList<SystemMessageItem> mSystemMentionMeList = null;
  protected JSONArray mSystemMessageArray = null;
  protected ArrayList<SystemMessageItem> mSystemMessageList = null;
  protected int mSystemMessageTotal = 0;
  protected int mSystemNoticeCnt = 0;
  protected JSONArray mSystemRepostArray = null;
  protected ArrayList<SystemMessageItem> mSystemRepostList = null;
  protected int mTotalNoticeCnt = 0;
  protected JSONArray mUserCommentArray = null;
  protected ArrayList<UserCommentItem> mUserCommentList = null;
  protected int mUserCommentTotal = 0;

 

  private MessageCenterModel()
  {
    for (int i = 0; ; i++)
    {
      if (i >= 13)
        return;
      this.mNotices.add(Integer.valueOf(0));
    }
  }

  public static String formatTimestamp(long paramLong)
  {
    Calendar localCalendar1 = Calendar.getInstance();
    int i = localCalendar1.get(1);
    int j = localCalendar1.get(6);
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar2.setTimeInMillis(paramLong);
    int k = localCalendar2.get(1);
    int m = localCalendar2.get(6);
    String str;
    if (k != i)
      str = "yyyy-MM-dd HH:mm";
   
      Date localDate = new Date(paramLong);
      
      if (m != j)
      {
        str = "MM-dd HH:mm";
        
      }
      str = "HH:mm";
      return new SimpleDateFormat(str).format(localDate);
    
  }

  public static MessageCenterModel getInstance()
  {
   
   
      if (instance == null)
        instance = new MessageCenterModel();
      MessageCenterModel localMessageCenterModel = instance;
      return localMessageCenterModel;
   
  }

  private SystemMessageItem parseSystemMessage(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null)
      return null;
    
      int i = 0;
      SystemMessageItem localSystemMessageItem2;
      SystemMessageItem localSystemMessageItem1 = null;
      try
      {
        localSystemMessageItem1 = new SystemMessageItem();
        String str4 = null;
        String str5 = null;
        String str8 = null;
        JSONArray localJSONArray2 = null;
        try
        {
          String str1 = paramJSONObject.optString("content");
          String str2 = paramJSONObject.optString("intro");
         
          localSystemMessageItem1.setContent(str1);
          String str3 = paramJSONObject.optString("msgtype");
          str4 = paramJSONObject.optString("ntype");
         
          localSystemMessageItem1.setMsgType(str3);
          str5 = paramJSONObject.optString("real_name");
          String str6 = paramJSONObject.optString("fname");
         
          localSystemMessageItem1.setRealName(str6);
          String str7 = paramJSONObject.optString("id");
          str8 = paramJSONObject.optString("rid");
          
          localSystemMessageItem1.setId(str7);
          JSONObject localJSONObject1 = paramJSONObject.optJSONObject("source");
         
          localSystemMessageItem1.setSourceItem(localJSONObject1);
          localSystemMessageItem1.setTitle(paramJSONObject.optString("title"));
          localSystemMessageItem1.setFid(paramJSONObject.optString("fid"));
          localSystemMessageItem1.setFStar(paramJSONObject.optString("fstar"));
          localSystemMessageItem1.setObjID(paramJSONObject.optString("objid"));
          localSystemMessageItem1.setOUID(paramJSONObject.optString("ouid"));
          localSystemMessageItem1.setOName(paramJSONObject.optString("oname"));
          localSystemMessageItem1.setSubTitle(paramJSONObject.optString("sub_title"));
          localSystemMessageItem1.setLocation(paramJSONObject.optString("location"));
          JSONArray localJSONArray1 = paramJSONObject.optJSONArray("images");
        
          JSONObject localJSONObject4 = localJSONArray1.getJSONObject(0);
         
          localSystemMessageItem1.setRecordThumbnail(localJSONObject4.optString("thumbnail"));
          localSystemMessageItem1.setRecordLarge(localJSONObject4.optString("large"));
          localSystemMessageItem1.setFriendCount(paramJSONObject.optString("friends_num"));
          localSystemMessageItem1.setFriends(paramJSONObject.optString("friends"));
          localSystemMessageItem1.setResult(paramJSONObject.optInt("result"));
          localSystemMessageItem1.setSmid(paramJSONObject.optString("smid"));
          localSystemMessageItem1.setAppId(paramJSONObject.optString("appid"));
          localSystemMessageItem1.setAction(paramJSONObject.optInt("action", 0));
          String str9 = paramJSONObject.optString("askinfo");
        
          localSystemMessageItem1.setPostScript(str9);
          String str10 = paramJSONObject.optString("ucd");
          
          localSystemMessageItem1.setAddFriendByWho(str10);
          localSystemMessageItem1.setFlogo(paramJSONObject.optString("flogo"));
          localSystemMessageItem1.setFuid(paramJSONObject.optString("fuid"));
          localSystemMessageItem1.setCtime(Long.valueOf(paramJSONObject.optLong("ctime", 0L)));
          localSystemMessageItem1.setStrCtime(paramJSONObject.optString("stime"));
          JSONObject localJSONObject2 = paramJSONObject.optJSONObject("sub_info");
          localSystemMessageItem1.setJSONSubInfo(localJSONObject2);
          localSystemMessageItem1.setJSONVideoInfo(paramJSONObject.optJSONArray("video"));
          localJSONArray2 = paramJSONObject.optJSONArray("video");
          
          localJSONArray2 = localJSONObject2.optJSONArray("video");
          
          i = 0;
          
          localSystemMessageItem1.setSource(paramJSONObject.optString("source"));
          localSystemMessageItem1.setType(paramJSONObject.optString("ntype"));
          String str11 = paramJSONObject.optString("cnum");
          localSystemMessageItem1.setFuid(paramJSONObject.optString("fuid"));
          
          localSystemMessageItem1.setCnum(str11);
          String str12 = paramJSONObject.optString("tnum");
         
          localSystemMessageItem1.setTnum(str12);
          
          localSystemMessageItem1.setFpri(paramJSONObject.optString("fpri"));
          
          String localObject = localSystemMessageItem1.getType();
         
          localSystemMessageItem1.getClass();
          //SystemMessageItem.SystemMsgExtendItem localSystemMsgExtendItem = new SystemMessageItem.SystemMsgExtendItem();
          localSystemMessageItem1.mExtendItem = null;
          localSystemMessageItem1.mExtendItem.mWid = paramJSONObject.optString("wid");
          localSystemMessageItem2 = localSystemMessageItem1;
          
         
          localSystemMessageItem1.setContent(str2);
          
        }
        catch (Exception localException1)
        {
          localSystemMessageItem2 = localSystemMessageItem1;
          localException1.printStackTrace();
        }
      
        localSystemMessageItem1.setMsgType(str4);
       
       
        localSystemMessageItem1.setRealName(str5);
        
        localSystemMessageItem1.setRealName("");
       
        if (TextUtils.isEmpty(str8))
         
        localSystemMessageItem1.setId(str8);
       
        JSONObject localJSONObject3 = (JSONObject)localJSONArray2.get(i);
       
        localSystemMessageItem1.appendVImgSnapShot(localJSONObject3.optString("thumb"));
       
        String str13 = localSystemMessageItem1.getAppId();
        Object localObject = str13;
        
      }
      catch (Exception localException2)
      {
    
        return localSystemMessageItem1;
      }
	return localSystemMessageItem1;
     
      
    
  }

  private MessageItem valueOf(JSONObject paramJSONObject)
    throws JSONException
  {
    MessageItem localMessageItem = new MessageItem();
    localMessageItem.setAbscont(paramJSONObject.getString("abscont"));
    localMessageItem.setCtime(Long.valueOf(paramJSONObject.getLong("ctime")));
    localMessageItem.setFlogo(paramJSONObject.getString("flogo"));
    localMessageItem.setFname(paramJSONObject.getString("fname"));
    localMessageItem.setFuid(paramJSONObject.getString("fuid"));
    localMessageItem.setMid(paramJSONObject.getString("mid"));
    localMessageItem.setMsgnum(paramJSONObject.getInt("msgnum"));
    localMessageItem.setMtype(paramJSONObject.getInt("mtype"));
    localMessageItem.setUnread(paramJSONObject.getInt("unread"));
    return localMessageItem;
  }

  public void appendCommentJSONArray(JSONArray paramJSONArray)
  {
    
      try
      {
        int i = 0;
        if (this.mCommentArray == null)
        {
          this.mCommentArray = paramJSONArray;
         
          if (i < paramJSONArray.length())
          {
            this.mCommentArray.put(paramJSONArray.getJSONObject(i));
            i++;
           
          }
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
     
    
  }

  public void appendCommentList(JSONArray paramJSONArray)
  {
    appendCommentJSONArray(paramJSONArray);
    if ((this.mCommentList == null) || (this.mCommentList.size() == 0)){
    	 setCommentList(paramJSONArray);
    	return;
    }
     
  
      
      
      int i = 0;
      try
      {
        while (i < paramJSONArray.length())
        {
          CommentItem localCommentItem = new CommentItem();
          JSONObject localJSONObject = (JSONObject)paramJSONArray.get(i);
          localCommentItem.setAbscont(localJSONObject.getString("abscont"));
          localCommentItem.setAbscont_last(localJSONObject.getString("abscont_last"));
          localCommentItem.setApphtml(localJSONObject.getString("apphtml"));
          localCommentItem.setAppname(localJSONObject.getString("appname"));
          localCommentItem.setAppId(localJSONObject.optString("appid"));
          localCommentItem.setCnum(localJSONObject.getInt("cnum"));
          localCommentItem.setCtime(Long.valueOf(localJSONObject.getLong("ctime")));
          localCommentItem.setCtime_last(localJSONObject.getString("ctime_last"));
          localCommentItem.setFlogo(localJSONObject.getString("flogo"));
          localCommentItem.setFname(localJSONObject.getString("fname"));
          localCommentItem.setFname_last(localJSONObject.getString("fname_last"));
          localCommentItem.setFuid(localJSONObject.getString("fuid"));
          localCommentItem.setFuid_last(localJSONObject.getString("fuid_last"));
          localCommentItem.setMtype(localJSONObject.getInt("mtype"));
          localCommentItem.setThread_cid(localJSONObject.getString("thread_cid"));
          localCommentItem.setUnread(localJSONObject.getInt("unread"));
          localCommentItem.setPosTime(localJSONObject.optString("ptime"));
          this.mCommentList.add(localCommentItem);
          i++;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessageCenterModel", "appendCommentList error", localException);
        return;
      }
    
    setReplyCommentListTotal(this.mCommentList.size());
  }

  public void appendMentionMeMessageJSONArray(JSONArray paramJSONArray)
  {
   
      try
      {
        int i=0;
        if (this.mSystemMentionMeArray == null)
        {
          this.mSystemMentionMeArray = paramJSONArray;
         
          if (i < paramJSONArray.length())
          {
            this.mSystemMentionMeArray.put(paramJSONArray.getJSONObject(i));
            i++;
          
          }
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
     
    
  }

  public void appendMentionMeMessageList(JSONArray paramJSONArray, int paramInt)
  {
    if (paramJSONArray == null)
      return;
    appendMentionMeMessageJSONArray(paramJSONArray);
    if ((this.mSystemMentionMeList == null) || (this.mSystemMentionMeList.size() == 0))
    {
      setSystemMentionMeList(paramJSONArray, paramInt);
      return;
    }
    int i = 0;
    for (int j = 0; ; j++)
    {
      try
      {
        if (j >= paramJSONArray.length())
        {
          if (i != 0)
            break;
          this.mMentionMeTotal = this.mSystemMentionMeList.size();
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessageCenterModel", "appendMentionMeMessageList error", localException);
        return;
      }
      SystemMessageItem localSystemMessageItem = parseSystemMessage((JSONObject)paramJSONArray.opt(j));
      if (localSystemMessageItem == null)
        continue;
      this.mSystemMentionMeList.add(localSystemMessageItem);
      i++;
    }
  }

  public void appendMessageInboxJSONArray(JSONArray paramJSONArray)
  {
    
     
    
  }

  public void appendMessageInboxList(JSONArray paramJSONArray)
  {
    
   
  }

  public void appendMessageSentJSONArray(JSONArray paramJSONArray)
  {
   
  }

  public void appendMessageSentList(JSONArray paramJSONArray)
  {
   
  
  }

  public void appendReplyCommentJSONArray(JSONArray paramJSONArray)
  {
  
  }

  public void appendReplyCommentList(JSONArray paramJSONArray)
  {
    
  }

  public void appendRepostMessageJSONArray(JSONArray paramJSONArray)
  {

  }

  public void appendRepostMessageList(JSONArray paramJSONArray, int paramInt)
  {
    if (paramJSONArray == null)
      return;
    appendRepostMessageJSONArray(paramJSONArray);
    if ((this.mSystemRepostList == null) || (this.mSystemRepostList.size() == 0))
    {
      setSystemRepostList(paramJSONArray, paramInt);
      return;
    }
    int i = 0;
    for (int j = 0; ; j++)
    {
      try
      {
        if (j >= paramJSONArray.length())
        {
          if (i != 0)
            break;
          this.mRepostTotal = this.mSystemRepostList.size();
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessageCenterModel", "appendRepostMessageList error", localException);
        return;
      }
      SystemMessageItem localSystemMessageItem = parseSystemMessage((JSONObject)paramJSONArray.opt(j));
      if (localSystemMessageItem == null)
        continue;
      this.mSystemRepostList.add(localSystemMessageItem);
      i++;
    }
  }

  public void appendSentUserCommentJSONArray(JSONArray paramJSONArray)
  {
 
  }

  public void appendSentUserCommentList(JSONArray paramJSONArray)
  {
  
  }

  public void appendSystemMessageJSONArray(JSONArray paramJSONArray)
  {
   
  }

  public void appendSystemMessageList(JSONArray paramJSONArray, int paramInt)
  {
    if (paramJSONArray == null)
      return;
    appendSystemMessageJSONArray(paramJSONArray);
    if ((this.mSystemMessageList == null) || (this.mSystemMessageList.size() == 0))
    {
      setSystemMessageList(paramJSONArray, paramInt);
      return;
    }
    int i = 0;
    for (int j = 0; ; j++)
      try
      {
        int k = paramJSONArray.length();
        if (j >= k)
        {
          if (i != 0)
            break;
          this.mSystemMessageTotal = this.mSystemMessageList.size();
          return;
        }
        SystemMessageItem localSystemMessageItem = parseSystemMessage((JSONObject)paramJSONArray.opt(j));
        if (localSystemMessageItem == null)
          continue;
        this.mSystemMessageList.add(localSystemMessageItem);
        i++;
      }
      catch (Exception localException)
      {
        while (true)
          KXLog.e("MessageCenterModel", "appendSystemMessageList error", localException);
      }
  }

  public void appendUserCommentJSONArray(JSONArray paramJSONArray)
  {
 
  }

  public void appendUserCommentList(JSONArray paramJSONArray)
  {
   
  }

  public void clear()
  {
    this.mMsgInboxTotal = 0;
    this.mMsgOutboxTotal = 0;
    this.mSystemMessageTotal = 0;
    this.mMentionMeTotal = 0;
    this.mRepostTotal = 0;
    this.mCommentTotal = 0;
    this.mReplyCommentTotal = 0;
    this.mUserCommentTotal = 0;
    this.mSentUserCommentTotal = 0;
    this.mTotalNoticeCnt = 0;
    this.mOldSystemNoticeCnt = 0;
    this.mSystemNoticeCnt = 0;
    this.mReturnNum = 0;
    resetNotice();
    if (this.mMessageInboxList != null)
    {
      this.mMessageInboxList.clear();
      this.mMessageInboxList = null;
    }
    this.mMessageInboxArray = null;
    if (this.mMessageSentList != null)
    {
      this.mMessageSentList.clear();
      this.mMessageSentList = null;
    }
    this.mMessageSentArray = null;
    if (this.mSystemMessageList != null)
    {
      this.mSystemMessageList.clear();
      this.mSystemMessageList = null;
    }
    this.mSystemMessageArray = null;
    if (this.mSystemMentionMeList != null)
    {
      this.mSystemMentionMeList.clear();
      this.mSystemMentionMeList = null;
    }
    this.mSystemMentionMeArray = null;
    if (this.mSystemRepostList != null)
    {
      this.mSystemRepostList.clear();
      this.mSystemRepostList = null;
    }
    this.mSystemRepostArray = null;
    if (this.mCommentList != null)
    {
      this.mCommentList.clear();
      this.mCommentList = null;
    }
    this.mCommentArray = null;
    if (this.mReplyCommentList != null)
    {
      this.mReplyCommentList.clear();
      this.mReplyCommentList = null;
    }
    this.mReplyCommentArray = null;
    if (this.mUserCommentList != null)
    {
      this.mUserCommentList.clear();
      this.mUserCommentList = null;
    }
    this.mUserCommentArray = null;
    if (this.mSentUserCommentList != null)
    {
      this.mSentUserCommentList.clear();
      this.mSentUserCommentList = null;
    }
    this.mSentUserCommentArray = null;
    this.mActiveMesageDetail = null;
    this.mActiveMessageType = 101;
  }

  public void clearMessageDetailList()
  {
    if (this.mMessageDetailList != null)
      this.mMessageDetailList.clear();
  }

  public int getActiveCommentType()
  {
    return this.mActiveCommentType;
  }

  public MessageDetailInfo getActiveMesasgeDetail()
  {
    return this.mActiveMesageDetail;
  }

  public int getActiveMessageType()
  {
    return this.mActiveMessageType;
  }

  public int getActiveUserCommentType()
  {
    return this.mActiveUserCommentType;
  }

  public JSONArray getCommentArray()
  {
    return this.mCommentArray;
  }

  public ArrayList<CommentItem> getCommentList()
  {
    return this.mCommentList;
  }

  public int getCommentListTotal()
  {
    return this.mCommentTotal;
  }

  public int getMentionMeReturnNum()
  {
    return this.mMentionMeReturnNum;
  }

  public int getMentionMeTotal()
  {
    return this.mMentionMeTotal;
  }

  public ArrayList<MessageDetailItem> getMessageDetailList()
  {
    return this.mMessageDetailList;
  }

  public JSONArray getMessageInboxArray()
  {
    return this.mMessageInboxArray;
  }

  public ArrayList<MessageItem> getMessageInboxList()
  {
    return this.mMessageInboxList;
  }

  public JSONArray getMessageSentArray()
  {
    return this.mMessageSentArray;
  }

  public ArrayList<MessageItem> getMessageSentList()
  {
    return this.mMessageSentList;
  }

  public int getMsgInboxTotal()
  {
    return this.mMsgInboxTotal;
  }

  public int getMsgOutboxTotal()
  {
    return this.mMsgOutboxTotal;
  }

  public int getNoticeCnt(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < this.mNotices.size()))
      return ((Integer)this.mNotices.get(paramInt)).intValue();
    return 0;
  }

  public JSONArray getReplyCommentArray()
  {
    return this.mReplyCommentArray;
  }

  public ArrayList<CommentItem> getReplyCommentList()
  {
    return this.mReplyCommentList;
  }

  public int getReplyCommentListTotal()
  {
    return this.mReplyCommentTotal;
  }

  public int getRepostReturnNum()
  {
    return this.mRepostReturnNum;
  }

  public int getRepostTotal()
  {
    return this.mRepostTotal;
  }

  public int getReturnNum()
  {
    return this.mReturnNum;
  }

  public JSONArray getSentUserCommentArray()
  {
    return this.mSentUserCommentArray;
  }

  public ArrayList<UserCommentItem> getSentUserCommentList()
  {
    return this.mSentUserCommentList;
  }

  public int getSentUserCommentTotal()
  {
    return this.mSentUserCommentTotal;
  }

  public JSONArray getSystemMentionMeArray()
  {
    return this.mSystemMentionMeArray;
  }

  public ArrayList<SystemMessageItem> getSystemMentionMeList()
  {
    return this.mSystemMentionMeList;
  }

  public JSONArray getSystemMessageArray()
  {
    return this.mSystemMessageArray;
  }

  public ArrayList<SystemMessageItem> getSystemMessageList()
  {
    return this.mSystemMessageList;
  }

  public int getSystemMessageTotal()
  {
    return this.mSystemMessageTotal;
  }

  public ArrayList<SystemMessageItem> getSystemRepostList()
  {
    return this.mSystemRepostList;
  }

  public int getTotalNoticeCnt()
  {
    return this.mTotalNoticeCnt;
  }

  public JSONArray getUserCommentArray()
  {
    return this.mUserCommentArray;
  }

  public ArrayList<UserCommentItem> getUserCommentList()
  {
    return this.mUserCommentList;
  }

  public int getUserCommentTotal()
  {
    return this.mUserCommentTotal;
  }

  public JSONArray getmSystemRepostArray()
  {
    return this.mSystemRepostArray;
  }

  public boolean isSystemNoticeCntChange()
  {
    KXLog.d("HttpConnection", "mSystemNoticeCnt:" + this.mSystemNoticeCnt + ", mOldSystemNoticeCnt:" + this.mOldSystemNoticeCnt);
    return (this.mSystemNoticeCnt > 0) && (this.mOldSystemNoticeCnt != this.mSystemNoticeCnt);
  }

  public void resetNotice()
  {
    this.mTotalNoticeCnt = 0;
    for (int i = 0; ; i++)
    {
      if (i >= this.mNotices.size())
        return;
      this.mNotices.set(i, Integer.valueOf(0));
    }
  }

  public void setActiveCommentType(int paramInt)
  {
    this.mActiveCommentType = paramInt;
  }

  public void setActiveMesasgeDetail(MessageDetailInfo paramMessageDetailInfo, int paramInt, boolean paramBoolean)
  {
    
  }

  public void setActiveMessageType(int paramInt)
  {
    this.mActiveMessageType = paramInt;
  }

  public void setActiveMoreMesasgeDetail(MessageDetailInfo paramMessageDetailInfo)
  {
   
  }

  public void setActiveUserCommentType(int paramInt)
  {
    this.mActiveUserCommentType = paramInt;
  }

  public void setCommentDraftList(JSONArray paramJSONArray, ArrayList<CommentItem> paramArrayList)
  {
   
  }

  public void setCommentJSONArray(JSONArray paramJSONArray)
  {
    this.mCommentArray = paramJSONArray;
  }

  public void setCommentList(JSONArray paramJSONArray)
  {
    
  }

  public void setCommentListTotal(int paramInt)
  {
    this.mCommentTotal = paramInt;
  }

  public void setMentionMeReturnNum(int paramInt)
  {
    this.mMentionMeReturnNum = paramInt;
  }

  public void setMentionMeTotal(int paramInt)
  {
    this.mMentionMeTotal = paramInt;
  }

  public void setMessageInboxJSONArray(JSONArray paramJSONArray)
  {
    this.mMessageInboxArray = paramJSONArray;
  }

  public void setMessageInboxList(JSONArray paramJSONArray)
  {
   
  }

  public void setMessageOutboxJSONArray(JSONArray paramJSONArray)
  {
    this.mMessageSentArray = paramJSONArray;
  }

  public void setMessageOutboxList(JSONArray paramJSONArray)
  {
  
  }

  public void setMsgInboxTotal(int paramInt)
  {
    this.mMsgInboxTotal = paramInt;
  }

  public void setMsgOutboxTotal(int paramInt)
  {
    this.mMsgOutboxTotal = paramInt;
  }

  public void setNoticeArray(ChatInfoItem.NewMessage paramNewMessage)
  {
    this.mOldSystemNoticeCnt = this.mSystemNoticeCnt;
    this.mSystemNoticeCnt = 0;
    resetNotice();
    int i = paramNewMessage.mMsgNum;
    this.mNotices.set(1, Integer.valueOf(i));
    this.mTotalNoticeCnt = (i + this.mTotalNoticeCnt);
    int j = paramNewMessage.mCommentNum;
    this.mNotices.set(3, Integer.valueOf(j));
    this.mTotalNoticeCnt = (j + this.mTotalNoticeCnt);
    int k = paramNewMessage.mReplyCommentNum;
    this.mNotices.set(6, Integer.valueOf(k));
    this.mTotalNoticeCnt = (k + this.mTotalNoticeCnt);
    int m = paramNewMessage.mCircleMsgNum;
    this.mNotices.set(12, Integer.valueOf(m));
    this.mTotalNoticeCnt = (m + this.mTotalNoticeCnt);
    this.mSystemNoticeCnt = paramNewMessage.mSystemMsgNum;
    KXLog.d("HttpConnection", "mOldSystemNoticeCnt:" + this.mOldSystemNoticeCnt + ", mSystemNoticeCnt:" + this.mSystemNoticeCnt);
  }

  public void setNoticeArray(JSONArray paramJSONArray)
  {
   
  }

  public void setReplyCommentJSONArray(JSONArray paramJSONArray)
  {
    this.mReplyCommentArray = paramJSONArray;
  }

  public void setReplyCommentList(JSONArray paramJSONArray)
  {
   
  }

  public void setReplyCommentListTotal(int paramInt)
  {
    this.mReplyCommentTotal = paramInt;
  }

  public void setRepostReturnNum(int paramInt)
  {
    this.mRepostReturnNum = paramInt;
  }

  public void setRepostTotal(int paramInt)
  {
    this.mRepostTotal = paramInt;
  }

  public void setReturnNum(int paramInt)
  {
    this.mReturnNum = paramInt;
  }

  public void setSentUserCommentJSONArray(JSONArray paramJSONArray)
  {
    this.mSentUserCommentArray = paramJSONArray;
  }

  public void setSentUserCommentList(JSONArray paramJSONArray)
  {
   
  }

  public void setSentUserCommentTotal(int paramInt)
  {
    this.mSentUserCommentTotal = paramInt;
  }

  public void setSystemMentionMeArray(JSONArray paramJSONArray)
  {
    this.mSystemMentionMeArray = paramJSONArray;
  }

  public void setSystemMentionMeList(JSONArray paramJSONArray, int paramInt)
  {
    setSystemMentionMeArray(paramJSONArray);
    if (paramJSONArray == null)
      return;
    if (this.mSystemMentionMeList == null)
      this.mSystemMentionMeList = new ArrayList();
    this.mSystemMentionMeList.clear();
    int i = 0;
    for (int j = 0; ; j++)
    {
      try
      {
        if (j >= paramJSONArray.length())
        {
          if (i != 0)
            break;
          this.mMentionMeTotal = this.mSystemMentionMeList.size();
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessageCenterModel", "setSystemMentionMeList ERROR", localException);
        return;
      }
      SystemMessageItem localSystemMessageItem = parseSystemMessage((JSONObject)paramJSONArray.opt(j));
      if (localSystemMessageItem == null)
        continue;
      this.mSystemMentionMeList.add(localSystemMessageItem);
      i++;
    }
  }

  public void setSystemMessageJSONArray(JSONArray paramJSONArray)
  {
    this.mSystemMessageArray = paramJSONArray;
  }

  public void setSystemMessageList(JSONArray paramJSONArray, int paramInt)
  {
    setSystemMessageJSONArray(paramJSONArray);
    if (paramJSONArray == null)
      return;
    if (this.mSystemMessageList == null)
      this.mSystemMessageList = new ArrayList();
    this.mSystemMessageList.clear();
    int i = 0;
    for (int j = 0; ; j++)
    {
      try
      {
        if (j >= paramJSONArray.length())
        {
          if (i != 0)
            break;
          this.mSystemMessageTotal = this.mSystemMessageList.size();
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessageCenterModel", "setSystemMessageList error", localException);
        return;
      }
      SystemMessageItem localSystemMessageItem = parseSystemMessage((JSONObject)paramJSONArray.opt(j));
      if (localSystemMessageItem == null)
        continue;
      this.mSystemMessageList.add(localSystemMessageItem);
      i++;
    }
  }

  public void setSystemMessageTotal(int paramInt)
  {
    this.mSystemMessageTotal = paramInt;
  }

  public void setSystemRepostArray(JSONArray paramJSONArray)
  {
    this.mSystemRepostArray = paramJSONArray;
  }

  public void setSystemRepostList(JSONArray paramJSONArray, int paramInt)
  {
    setSystemRepostArray(paramJSONArray);
    if (paramJSONArray == null)
      return;
    if (this.mSystemRepostList == null)
      this.mSystemRepostList = new ArrayList();
    this.mSystemRepostList.clear();
    int i = 0;
    for (int j = 0; ; j++)
    {
      try
      {
        if (j >= paramJSONArray.length())
        {
          if (i != 0)
            break;
          this.mRepostTotal = this.mSystemRepostList.size();
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessageCenterModel", "setSystemRepostList error", localException);
        return;
      }
      SystemMessageItem localSystemMessageItem = parseSystemMessage((JSONObject)paramJSONArray.opt(j));
      if (localSystemMessageItem == null)
        continue;
      this.mSystemRepostList.add(localSystemMessageItem);
      i++;
    }
  }

  public void setUserCommentJSONArray(JSONArray paramJSONArray)
  {
    this.mUserCommentArray = paramJSONArray;
  }

  public void setUserCommentList(JSONArray paramJSONArray)
  {
   
  }

  public void setUserCommentList(JSONArray paramJSONArray, ArrayList<UserCommentItem> paramArrayList)
  {
    
  }

  public void setUserCommentTotal(int paramInt)
  {
    this.mUserCommentTotal = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.MessageCenterModel
 * JD-Core Version:    0.6.0
 */