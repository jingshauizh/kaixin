package com.kaixin001.model;

import android.text.TextUtils;
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

  static
  {
    if (!MessageCenterModel.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      instance = null;
      return;
    }
  }

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
    while (true)
    {
      Date localDate = new Date(paramLong);
      return new SimpleDateFormat(str).format(localDate);
      if (m != j)
      {
        str = "MM-dd HH:mm";
        continue;
      }
      str = "HH:mm";
    }
  }

  public static MessageCenterModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new MessageCenterModel();
      MessageCenterModel localMessageCenterModel = instance;
      return localMessageCenterModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private SystemMessageItem parseSystemMessage(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null)
      return null;
    while (true)
    {
      int i;
      SystemMessageItem localSystemMessageItem2;
      try
      {
        localSystemMessageItem1 = new SystemMessageItem();
        String str4;
        String str5;
        String str8;
        JSONArray localJSONArray2;
        try
        {
          String str1 = paramJSONObject.optString("content");
          String str2 = paramJSONObject.optString("intro");
          if (TextUtils.isEmpty(str1))
            continue;
          localSystemMessageItem1.setContent(str1);
          String str3 = paramJSONObject.optString("msgtype");
          str4 = paramJSONObject.optString("ntype");
          if (TextUtils.isEmpty(str3))
            continue;
          localSystemMessageItem1.setMsgType(str3);
          str5 = paramJSONObject.optString("real_name");
          String str6 = paramJSONObject.optString("fname");
          if ((TextUtils.isEmpty(str6)) || (str6.equals("null")))
            continue;
          localSystemMessageItem1.setRealName(str6);
          String str7 = paramJSONObject.optString("id");
          str8 = paramJSONObject.optString("rid");
          if (TextUtils.isEmpty(str7))
            continue;
          localSystemMessageItem1.setId(str7);
          JSONObject localJSONObject1 = paramJSONObject.optJSONObject("source");
          if (localJSONObject1 == null)
            continue;
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
          if ((localJSONArray1 == null) || (localJSONArray1.length() <= 0))
            continue;
          JSONObject localJSONObject4 = localJSONArray1.getJSONObject(0);
          if (localJSONObject4 == null)
            continue;
          localSystemMessageItem1.setRecordThumbnail(localJSONObject4.optString("thumbnail"));
          localSystemMessageItem1.setRecordLarge(localJSONObject4.optString("large"));
          localSystemMessageItem1.setFriendCount(paramJSONObject.optString("friends_num"));
          localSystemMessageItem1.setFriends(paramJSONObject.optString("friends"));
          localSystemMessageItem1.setResult(paramJSONObject.optInt("result"));
          localSystemMessageItem1.setSmid(paramJSONObject.optString("smid"));
          localSystemMessageItem1.setAppId(paramJSONObject.optString("appid"));
          localSystemMessageItem1.setAction(paramJSONObject.optInt("action", 0));
          String str9 = paramJSONObject.optString("askinfo");
          if ((TextUtils.isEmpty(str9)) || (str9.equals("null")))
            continue;
          localSystemMessageItem1.setPostScript(str9);
          String str10 = paramJSONObject.optString("ucd");
          if ((TextUtils.isEmpty(str10)) || (str10.equals("null")))
            continue;
          localSystemMessageItem1.setAddFriendByWho(str10);
          localSystemMessageItem1.setFlogo(paramJSONObject.optString("flogo"));
          localSystemMessageItem1.setFuid(paramJSONObject.optString("fuid"));
          localSystemMessageItem1.setCtime(Long.valueOf(paramJSONObject.optLong("ctime", 0L)));
          localSystemMessageItem1.setStrCtime(paramJSONObject.optString("stime"));
          JSONObject localJSONObject2 = paramJSONObject.optJSONObject("sub_info");
          localSystemMessageItem1.setJSONSubInfo(localJSONObject2);
          localSystemMessageItem1.setJSONVideoInfo(paramJSONObject.optJSONArray("video"));
          localJSONArray2 = paramJSONObject.optJSONArray("video");
          if ((localJSONArray2 != null) || (localJSONObject2 == null))
            continue;
          localJSONArray2 = localJSONObject2.optJSONArray("video");
          if ((localJSONArray2 == null) || (localJSONArray2.length() <= 0))
            continue;
          i = 0;
          if (i < localJSONArray2.length())
            continue;
          localSystemMessageItem1.setSource(paramJSONObject.optString("source"));
          localSystemMessageItem1.setType(paramJSONObject.optString("ntype"));
          String str11 = paramJSONObject.optString("cnum");
          localSystemMessageItem1.setFuid(paramJSONObject.optString("fuid"));
          if (str11 == null)
            continue;
          localSystemMessageItem1.setCnum(str11);
          String str12 = paramJSONObject.optString("tnum");
          if (str12 == null)
            continue;
          localSystemMessageItem1.setTnum(str12);
          if (paramJSONObject.isNull("fpri"))
            continue;
          localSystemMessageItem1.setFpri(paramJSONObject.optString("fpri"));
          if (!TextUtils.isEmpty(localSystemMessageItem1.getAppId()))
            continue;
          localObject = localSystemMessageItem1.getType();
          if (!((String)localObject).equals("1192"))
            continue;
          localSystemMessageItem1.getClass();
          SystemMessageItem.SystemMsgExtendItem localSystemMsgExtendItem = new SystemMessageItem.SystemMsgExtendItem(localSystemMessageItem1);
          localSystemMessageItem1.mExtendItem = localSystemMsgExtendItem;
          localSystemMessageItem1.mExtendItem.mWid = paramJSONObject.optString("wid");
          localSystemMessageItem2 = localSystemMessageItem1;
          break label899;
          if (TextUtils.isEmpty(str2))
            continue;
          localSystemMessageItem1.setContent(str2);
          continue;
        }
        catch (Exception localException1)
        {
          localSystemMessageItem2 = localSystemMessageItem1;
          localException1.printStackTrace();
        }
        if (TextUtils.isEmpty(str4))
          continue;
        localSystemMessageItem1.setMsgType(str4);
        continue;
        if ((TextUtils.isEmpty(str5)) || (str5.equals("null")))
          continue;
        localSystemMessageItem1.setRealName(str5);
        continue;
        localSystemMessageItem1.setRealName("");
        continue;
        if (TextUtils.isEmpty(str8))
          continue;
        localSystemMessageItem1.setId(str8);
        continue;
        JSONObject localJSONObject3 = (JSONObject)localJSONArray2.get(i);
        if (localJSONObject3 == null)
          break label902;
        localSystemMessageItem1.appendVImgSnapShot(localJSONObject3.optString("thumb"));
        break label902;
        String str13 = localSystemMessageItem1.getAppId();
        Object localObject = str13;
        continue;
      }
      catch (Exception localException2)
      {
        SystemMessageItem localSystemMessageItem1;
        localSystemMessageItem2 = null;
        continue;
        localSystemMessageItem2 = localSystemMessageItem1;
      }
      label899: return localSystemMessageItem2;
      label902: i++;
    }
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
    while (true)
    {
      try
      {
        int i;
        if (this.mCommentArray == null)
        {
          this.mCommentArray = paramJSONArray;
          return;
          if (i < paramJSONArray.length())
          {
            this.mCommentArray.put(paramJSONArray.getJSONObject(i));
            i++;
            continue;
          }
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
      do
        return;
      while (paramJSONArray == null);
      i = 0;
    }
  }

  public void appendCommentList(JSONArray paramJSONArray)
  {
    appendCommentJSONArray(paramJSONArray);
    if ((this.mCommentList == null) || (this.mCommentList.size() == 0))
      setCommentList(paramJSONArray);
    while (true)
    {
      return;
      if (paramJSONArray == null)
        break;
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
    }
    setReplyCommentListTotal(this.mCommentList.size());
  }

  public void appendMentionMeMessageJSONArray(JSONArray paramJSONArray)
  {
    while (true)
    {
      try
      {
        int i;
        if (this.mSystemMentionMeArray == null)
        {
          this.mSystemMentionMeArray = paramJSONArray;
          return;
          if (i < paramJSONArray.length())
          {
            this.mSystemMentionMeArray.put(paramJSONArray.getJSONObject(i));
            i++;
            continue;
          }
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
      do
        return;
      while (paramJSONArray == null);
      i = 0;
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
    while (true)
    {
      try
      {
        int i;
        if (this.mMessageInboxArray == null)
        {
          this.mMessageInboxArray = paramJSONArray;
          return;
          if (i < paramJSONArray.length())
          {
            this.mMessageInboxArray.put(paramJSONArray.getJSONObject(i));
            i++;
            continue;
          }
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
      do
        return;
      while (paramJSONArray == null);
      i = 0;
    }
  }

  public void appendMessageInboxList(JSONArray paramJSONArray)
  {
    appendMessageInboxJSONArray(paramJSONArray);
    if ((this.mMessageInboxList == null) || (this.mMessageInboxList.size() == 0))
      setMessageInboxList(paramJSONArray);
    while (true)
    {
      return;
      if ((paramJSONArray == null) || (paramJSONArray.length() <= 0))
        break;
      int i = 0;
      try
      {
        while (i < paramJSONArray.length())
        {
          MessageItem localMessageItem = valueOf((JSONObject)paramJSONArray.get(i));
          this.mMessageInboxList.add(localMessageItem);
          i++;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessageCenterModel", "appendMessageInboxList error", localException);
        return;
      }
    }
    setMsgInboxTotal(this.mMessageInboxList.size());
  }

  public void appendMessageSentJSONArray(JSONArray paramJSONArray)
  {
    while (true)
    {
      try
      {
        int i;
        if (this.mMessageSentArray == null)
        {
          this.mMessageSentArray = paramJSONArray;
          return;
          if (i < paramJSONArray.length())
          {
            this.mMessageSentArray.put(paramJSONArray.getJSONObject(i));
            i++;
            continue;
          }
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
      do
        return;
      while (paramJSONArray == null);
      i = 0;
    }
  }

  public void appendMessageSentList(JSONArray paramJSONArray)
  {
    appendMessageSentJSONArray(paramJSONArray);
    if ((this.mMessageSentList == null) || (this.mMessageSentList.size() == 0))
      setMessageOutboxList(paramJSONArray);
    while (true)
    {
      return;
      if ((paramJSONArray == null) || (paramJSONArray.length() <= 0))
        break;
      int i = 0;
      try
      {
        while (i < paramJSONArray.length())
        {
          MessageItem localMessageItem = valueOf((JSONObject)paramJSONArray.get(i));
          this.mMessageSentList.add(localMessageItem);
          i++;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessageCenterModel", "appendMessageSentList error", localException);
        return;
      }
    }
    setMsgOutboxTotal(this.mMessageSentList.size());
  }

  public void appendReplyCommentJSONArray(JSONArray paramJSONArray)
  {
    while (true)
    {
      try
      {
        int i;
        if (this.mReplyCommentArray == null)
        {
          this.mReplyCommentArray = paramJSONArray;
          return;
          if (i < paramJSONArray.length())
          {
            this.mReplyCommentArray.put(paramJSONArray.getJSONObject(i));
            i++;
            continue;
          }
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
      do
        return;
      while (paramJSONArray == null);
      i = 0;
    }
  }

  public void appendReplyCommentList(JSONArray paramJSONArray)
  {
    appendReplyCommentJSONArray(paramJSONArray);
    if ((this.mReplyCommentList == null) || (this.mReplyCommentList.size() == 0))
      setReplyCommentList(paramJSONArray);
    while (true)
    {
      return;
      if (paramJSONArray == null)
        break;
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
          this.mReplyCommentList.add(localCommentItem);
          i++;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessageCenterModel", "appendReplyCommentList error", localException);
        return;
      }
    }
    setReplyCommentListTotal(this.mReplyCommentList.size());
  }

  public void appendRepostMessageJSONArray(JSONArray paramJSONArray)
  {
    while (true)
    {
      try
      {
        int i;
        if (this.mSystemRepostArray == null)
        {
          this.mSystemRepostArray = paramJSONArray;
          return;
          if (i < paramJSONArray.length())
          {
            this.mSystemRepostArray.put(paramJSONArray.getJSONObject(i));
            i++;
            continue;
          }
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
      do
        return;
      while (paramJSONArray == null);
      i = 0;
    }
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
    while (true)
    {
      try
      {
        int i;
        if (this.mSentUserCommentArray == null)
        {
          this.mSentUserCommentArray = paramJSONArray;
          return;
          if (i < paramJSONArray.length())
          {
            this.mSentUserCommentArray.put(paramJSONArray.getJSONObject(i));
            i++;
            continue;
          }
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
      do
        return;
      while (paramJSONArray == null);
      i = 0;
    }
  }

  public void appendSentUserCommentList(JSONArray paramJSONArray)
  {
    appendSentUserCommentJSONArray(paramJSONArray);
    if ((this.mSentUserCommentList == null) || (this.mSentUserCommentList.size() == 0))
      setSentUserCommentList(paramJSONArray);
    while (true)
    {
      return;
      if (paramJSONArray == null)
        continue;
      int i = 0;
      try
      {
        while (i < paramJSONArray.length())
        {
          UserCommentItem localUserCommentItem = new UserCommentItem();
          JSONObject localJSONObject = (JSONObject)paramJSONArray.get(i);
          localUserCommentItem.setAbscont(localJSONObject.getString("abscont"));
          localUserCommentItem.setAbscont_last(localJSONObject.getString("abscont_last"));
          localUserCommentItem.setCnum(localJSONObject.getInt("cnum"));
          localUserCommentItem.setCtime(Long.valueOf(localJSONObject.getLong("ctime")));
          localUserCommentItem.setCtime_last(localJSONObject.getString("ctime_last"));
          localUserCommentItem.setFlogo(localJSONObject.getString("flogo"));
          localUserCommentItem.setFname(localJSONObject.getString("fname"));
          localUserCommentItem.setFname_last(localJSONObject.getString("fname_last"));
          localUserCommentItem.setFuid(localJSONObject.getString("fuid"));
          localUserCommentItem.setFuid_last(localJSONObject.getString("fuid_last"));
          localUserCommentItem.setMtype(localJSONObject.getInt("mtype"));
          localUserCommentItem.setThread_cid(localJSONObject.getString("thread_cid"));
          localUserCommentItem.setUnread(localJSONObject.getInt("unread"));
          this.mSentUserCommentList.add(localUserCommentItem);
          i++;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessageCenterModel", "appendSentUserCommentList error", localException);
      }
    }
  }

  public void appendSystemMessageJSONArray(JSONArray paramJSONArray)
  {
    while (true)
    {
      try
      {
        int i;
        if (this.mSystemMessageArray == null)
        {
          this.mSystemMessageArray = paramJSONArray;
          return;
          if (i < paramJSONArray.length())
          {
            this.mSystemMessageArray.put(paramJSONArray.getJSONObject(i));
            i++;
            continue;
          }
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
      do
        return;
      while (paramJSONArray == null);
      i = 0;
    }
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
    while (true)
    {
      try
      {
        int i;
        if (this.mUserCommentArray == null)
        {
          this.mUserCommentArray = paramJSONArray;
          return;
          if (i < paramJSONArray.length())
          {
            this.mUserCommentArray.put(paramJSONArray.getJSONObject(i));
            i++;
            continue;
          }
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
      do
        return;
      while (paramJSONArray == null);
      i = 0;
    }
  }

  public void appendUserCommentList(JSONArray paramJSONArray)
  {
    appendUserCommentJSONArray(paramJSONArray);
    if ((this.mUserCommentList == null) || (this.mUserCommentList.size() == 0))
      setUserCommentList(paramJSONArray);
    while (true)
    {
      return;
      if (paramJSONArray == null)
        continue;
      int i = 0;
      try
      {
        while (i < paramJSONArray.length())
        {
          UserCommentItem localUserCommentItem = new UserCommentItem();
          JSONObject localJSONObject = (JSONObject)paramJSONArray.get(i);
          localUserCommentItem.setAbscont(localJSONObject.getString("abscont"));
          localUserCommentItem.setAbscont_last(localJSONObject.getString("abscont_last"));
          localUserCommentItem.setCnum(localJSONObject.getInt("cnum"));
          localUserCommentItem.setCtime(Long.valueOf(localJSONObject.getLong("ctime")));
          localUserCommentItem.setCtime_last(localJSONObject.getString("ctime_last"));
          localUserCommentItem.setFlogo(localJSONObject.getString("flogo"));
          localUserCommentItem.setFname(localJSONObject.getString("fname"));
          localUserCommentItem.setFname_last(localJSONObject.getString("fname_last"));
          localUserCommentItem.setFuid(localJSONObject.getString("fuid"));
          localUserCommentItem.setFuid_last(localJSONObject.getString("fuid_last"));
          localUserCommentItem.setMtype(localJSONObject.getInt("mtype"));
          localUserCommentItem.setThread_cid(localJSONObject.getString("thread_cid"));
          localUserCommentItem.setUnread(localJSONObject.getInt("unread"));
          localUserCommentItem.setPosTime(localJSONObject.optString("ptime"));
          this.mUserCommentList.add(localUserCommentItem);
          i++;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessageCenterModel", "appendUserCommentList error", localException);
      }
    }
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
    this.mActiveMesageDetail = paramMessageDetailInfo;
    if (this.mMessageDetailList == null)
      this.mMessageDetailList = new ArrayList();
    JSONArray localJSONArray1;
    while (true)
    {
      localJSONArray1 = paramMessageDetailInfo.getDetailList();
      if (localJSONArray1 != null)
        break;
      return;
      if (!paramBoolean)
        continue;
      this.mMessageDetailList.clear();
    }
    int i = 0;
    while (true)
    {
      MessageDetailItem localMessageDetailItem;
      JSONArray localJSONArray2;
      ArrayList localArrayList;
      int k;
      try
      {
        if (i >= localJSONArray1.length())
          break;
        localMessageDetailItem = new MessageDetailItem();
        JSONObject localJSONObject1 = (JSONObject)localJSONArray1.get(i);
        localMessageDetailItem.setAbscont(localJSONObject1.getString("abscont"));
        localMessageDetailItem.setCtime(Long.valueOf(localJSONObject1.getLong("ctime")));
        localMessageDetailItem.setFlogo(localJSONObject1.getString("flogo"));
        localMessageDetailItem.setFname(localJSONObject1.getString("fname"));
        localMessageDetailItem.setFuid(localJSONObject1.getString("fuid"));
        if (paramInt != 3)
          continue;
        localMessageDetailItem.setMessageId(localJSONObject1.getString("cid"));
        localJSONArray2 = localJSONObject1.optJSONArray("attm");
        if (localJSONArray2 == null)
          continue;
        int j = localJSONArray2.length();
        if (j == 0)
          break label334;
        localArrayList = new ArrayList();
        k = 0;
        if (k >= j)
        {
          localMessageDetailItem.setMessageAttmList(localArrayList);
          this.mMessageDetailList.add(i, localMessageDetailItem);
          i++;
          continue;
          localMessageDetailItem.setMessageId(localJSONObject1.getString("mid"));
          continue;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessageCenterModel", "setActiveMesasgeDetail error", localException);
        return;
      }
      JSONObject localJSONObject2 = localJSONArray2.getJSONObject(k);
      MessageAttachmentItem localMessageAttachmentItem = new MessageAttachmentItem();
      String str1 = localJSONObject2.getString("name");
      String str2 = localJSONObject2.getString("size");
      localMessageAttachmentItem.setmAttmName(str1);
      localMessageAttachmentItem.setmAttmSize(str2);
      localArrayList.add(localMessageAttachmentItem);
      k++;
      continue;
      label334: localMessageDetailItem.setMessageAttmList(null);
    }
  }

  public void setActiveMessageType(int paramInt)
  {
    this.mActiveMessageType = paramInt;
  }

  public void setActiveMoreMesasgeDetail(MessageDetailInfo paramMessageDetailInfo)
  {
    if ((this.mMessageDetailList == null) && (!$assertionsDisabled))
      throw new AssertionError();
    JSONArray localJSONArray1 = paramMessageDetailInfo.getDetailList();
    if (localJSONArray1 == null);
    while (true)
    {
      return;
      int i = 0;
      try
      {
        if (i >= localJSONArray1.length())
          continue;
        MessageDetailItem localMessageDetailItem = new MessageDetailItem();
        JSONObject localJSONObject1 = (JSONObject)localJSONArray1.get(i);
        localMessageDetailItem.setAbscont(localJSONObject1.getString("abscont"));
        localMessageDetailItem.setCtime(Long.valueOf(localJSONObject1.getLong("ctime")));
        localMessageDetailItem.setFlogo(localJSONObject1.getString("flogo"));
        localMessageDetailItem.setFname(localJSONObject1.getString("fname"));
        localMessageDetailItem.setFuid(localJSONObject1.getString("fuid"));
        localMessageDetailItem.setMessageId(localJSONObject1.getString("mid"));
        JSONArray localJSONArray2 = localJSONObject1.optJSONArray("attm");
        ArrayList localArrayList;
        int k;
        if (localJSONArray2 != null)
        {
          int j = localJSONArray2.length();
          if (j == 0)
            break label276;
          localArrayList = new ArrayList();
          k = 0;
          label180: if (k < j)
            break label210;
          localMessageDetailItem.setMessageAttmList(localArrayList);
        }
        while (true)
        {
          this.mMessageDetailList.add(i, localMessageDetailItem);
          i++;
          break;
          label210: JSONObject localJSONObject2 = localJSONArray2.getJSONObject(k);
          MessageAttachmentItem localMessageAttachmentItem = new MessageAttachmentItem();
          String str1 = localJSONObject2.getString("name");
          String str2 = localJSONObject2.getString("size");
          localMessageAttachmentItem.setmAttmName(str1);
          localMessageAttachmentItem.setmAttmSize(str2);
          localArrayList.add(localMessageAttachmentItem);
          k++;
          break label180;
          label276: localMessageDetailItem.setMessageAttmList(null);
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessageCenterModel", "setActiveMesasgeDetail error", localException);
      }
    }
  }

  public void setActiveUserCommentType(int paramInt)
  {
    this.mActiveUserCommentType = paramInt;
  }

  public void setCommentDraftList(JSONArray paramJSONArray, ArrayList<CommentItem> paramArrayList)
  {
    setCommentJSONArray(paramJSONArray);
    if (paramJSONArray == null);
    while (true)
    {
      return;
      if (paramArrayList == null)
        paramArrayList = new ArrayList();
      paramArrayList.clear();
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
          localCommentItem.setAppId(localJSONObject.optString("appid"));
          paramArrayList.add(localCommentItem);
          i++;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessageCenterModel", "setCommentList error", localException);
      }
    }
  }

  public void setCommentJSONArray(JSONArray paramJSONArray)
  {
    this.mCommentArray = paramJSONArray;
  }

  public void setCommentList(JSONArray paramJSONArray)
  {
    setCommentJSONArray(paramJSONArray);
    if (paramJSONArray == null);
    while (true)
    {
      return;
      if (this.mCommentList == null)
        this.mCommentList = new ArrayList();
      this.mCommentList.clear();
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
        KXLog.e("MessageCenterModel", "setCommentList error", localException);
      }
    }
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
    setMessageInboxJSONArray(paramJSONArray);
    if (paramJSONArray == null);
    while (true)
    {
      return;
      if (this.mMessageInboxList == null)
        this.mMessageInboxList = new ArrayList();
      this.mMessageInboxList.clear();
      int i = 0;
      try
      {
        while (i < paramJSONArray.length())
        {
          MessageItem localMessageItem = new MessageItem();
          JSONObject localJSONObject = (JSONObject)paramJSONArray.get(i);
          localMessageItem.setAbscont(localJSONObject.getString("abscont"));
          localMessageItem.setCtime(Long.valueOf(localJSONObject.getLong("ctime")));
          localMessageItem.setFlogo(localJSONObject.getString("flogo"));
          localMessageItem.setFname(localJSONObject.getString("fname"));
          localMessageItem.setFuid(localJSONObject.getString("fuid"));
          localMessageItem.setMid(localJSONObject.getString("mid"));
          localMessageItem.setMsgnum(localJSONObject.getInt("msgnum"));
          localMessageItem.setMtype(localJSONObject.getInt("mtype"));
          localMessageItem.setUnread(localJSONObject.getInt("unread"));
          this.mMessageInboxList.add(localMessageItem);
          i++;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessageCenterModel", "setMessageInboxList error", localException);
      }
    }
  }

  public void setMessageOutboxJSONArray(JSONArray paramJSONArray)
  {
    this.mMessageSentArray = paramJSONArray;
  }

  public void setMessageOutboxList(JSONArray paramJSONArray)
  {
    setMessageOutboxJSONArray(paramJSONArray);
    if (paramJSONArray == null);
    while (true)
    {
      return;
      if (this.mMessageSentList == null)
        this.mMessageSentList = new ArrayList();
      this.mMessageSentList.clear();
      int i = 0;
      try
      {
        while (i < paramJSONArray.length())
        {
          MessageItem localMessageItem = new MessageItem();
          JSONObject localJSONObject = (JSONObject)paramJSONArray.get(i);
          localMessageItem.setAbscont(localJSONObject.getString("abscont"));
          localMessageItem.setCtime(Long.valueOf(localJSONObject.getLong("ctime")));
          localMessageItem.setFlogo(localJSONObject.getString("flogo"));
          localMessageItem.setFname(localJSONObject.getString("fname"));
          localMessageItem.setFuid(localJSONObject.getString("fuid"));
          localMessageItem.setMid(localJSONObject.getString("mid"));
          localMessageItem.setMsgnum(localJSONObject.getInt("msgnum"));
          localMessageItem.setMtype(localJSONObject.getInt("mtype"));
          localMessageItem.setUnread(localJSONObject.getInt("unread"));
          this.mMessageSentList.add(localMessageItem);
          i++;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessageCenterModel", "setMessageSentList error", localException);
      }
    }
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
    this.mOldSystemNoticeCnt = this.mSystemNoticeCnt;
    this.mSystemNoticeCnt = 0;
    resetNotice();
    int i;
    if (paramJSONArray != null)
      i = 0;
    try
    {
      while (true)
      {
        int j = paramJSONArray.length();
        if (i >= j)
        {
          KXLog.d("HttpConnection", "mOldSystemNoticeCnt:" + this.mOldSystemNoticeCnt + ", mSystemNoticeCnt:" + this.mSystemNoticeCnt);
          return;
        }
        k = paramJSONArray.getJSONObject(i).getInt("mtype");
        int m = paramJSONArray.getJSONObject(i).getInt("n");
        this.mNotices.set(k, Integer.valueOf(m));
        if (k < 7)
          this.mTotalNoticeCnt = (m + this.mTotalNoticeCnt);
        if (k != 4)
          break;
        this.mSystemNoticeCnt = m;
        break;
        this.mTotalNoticeCnt = (m + this.mTotalNoticeCnt);
        int n = m + ((Integer)this.mNotices.get(12)).intValue();
        this.mNotices.set(12, Integer.valueOf(n));
        i++;
      }
    }
    catch (JSONException localJSONException)
    {
      while (true)
      {
        int k;
        localJSONException.printStackTrace();
        continue;
        if (k == 10)
          continue;
        if (k != 11)
          continue;
      }
    }
  }

  public void setReplyCommentJSONArray(JSONArray paramJSONArray)
  {
    this.mReplyCommentArray = paramJSONArray;
  }

  public void setReplyCommentList(JSONArray paramJSONArray)
  {
    setReplyCommentJSONArray(paramJSONArray);
    if (paramJSONArray == null);
    while (true)
    {
      return;
      if (this.mReplyCommentList == null)
        this.mReplyCommentList = new ArrayList();
      this.mReplyCommentList.clear();
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
          this.mReplyCommentList.add(localCommentItem);
          i++;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessageCenterModel", "setReplyCommentList error", localException);
      }
    }
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
    setSentUserCommentJSONArray(paramJSONArray);
    if (paramJSONArray == null);
    while (true)
    {
      return;
      if (this.mSentUserCommentList == null)
        this.mSentUserCommentList = new ArrayList();
      this.mSentUserCommentList.clear();
      int i = 0;
      try
      {
        while (i < paramJSONArray.length())
        {
          UserCommentItem localUserCommentItem = new UserCommentItem();
          JSONObject localJSONObject = (JSONObject)paramJSONArray.get(i);
          localUserCommentItem.setAbscont(localJSONObject.getString("abscont"));
          localUserCommentItem.setAbscont_last(localJSONObject.getString("abscont_last"));
          localUserCommentItem.setCnum(localJSONObject.getInt("cnum"));
          localUserCommentItem.setCtime(Long.valueOf(localJSONObject.getLong("ctime")));
          localUserCommentItem.setCtime_last(localJSONObject.getString("ctime_last"));
          localUserCommentItem.setFlogo(localJSONObject.getString("flogo"));
          localUserCommentItem.setFname(localJSONObject.getString("fname"));
          localUserCommentItem.setFname_last(localJSONObject.getString("fname_last"));
          localUserCommentItem.setFuid(localJSONObject.getString("fuid"));
          localUserCommentItem.setFuid_last(localJSONObject.getString("fuid_last"));
          localUserCommentItem.setMtype(localJSONObject.getInt("mtype"));
          localUserCommentItem.setThread_cid(localJSONObject.getString("thread_cid"));
          localUserCommentItem.setUnread(localJSONObject.getInt("unread"));
          this.mSentUserCommentList.add(localUserCommentItem);
          i++;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessageCenterModel", "setSentUserCommentList error", localException);
      }
    }
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
    setUserCommentJSONArray(paramJSONArray);
    if (paramJSONArray == null);
    while (true)
    {
      return;
      if (this.mUserCommentList == null)
        this.mUserCommentList = new ArrayList();
      this.mUserCommentList.clear();
      int i = 0;
      try
      {
        while (i < paramJSONArray.length())
        {
          UserCommentItem localUserCommentItem = new UserCommentItem();
          JSONObject localJSONObject = (JSONObject)paramJSONArray.get(i);
          localUserCommentItem.setAbscont(localJSONObject.getString("abscont"));
          localUserCommentItem.setAbscont_last(localJSONObject.getString("abscont_last"));
          localUserCommentItem.setCnum(localJSONObject.getInt("cnum"));
          localUserCommentItem.setCtime(Long.valueOf(localJSONObject.getLong("ctime")));
          localUserCommentItem.setCtime_last(localJSONObject.getString("ctime_last"));
          localUserCommentItem.setFlogo(localJSONObject.getString("flogo"));
          localUserCommentItem.setFname(localJSONObject.getString("fname"));
          localUserCommentItem.setFname_last(localJSONObject.getString("fname_last"));
          localUserCommentItem.setFuid(localJSONObject.getString("fuid"));
          localUserCommentItem.setFuid_last(localJSONObject.getString("fuid_last"));
          localUserCommentItem.setMtype(localJSONObject.getInt("mtype"));
          localUserCommentItem.setThread_cid(localJSONObject.getString("thread_cid"));
          localUserCommentItem.setUnread(localJSONObject.getInt("unread"));
          localUserCommentItem.setPosTime(localJSONObject.optString("ptime"));
          this.mUserCommentList.add(localUserCommentItem);
          i++;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessageCenterModel", "setUserCommentList error", localException);
      }
    }
  }

  public void setUserCommentList(JSONArray paramJSONArray, ArrayList<UserCommentItem> paramArrayList)
  {
    if (paramJSONArray == null);
    while (true)
    {
      return;
      if (paramArrayList == null)
        paramArrayList = new ArrayList();
      paramArrayList.clear();
      int i = 0;
      try
      {
        while (i < paramJSONArray.length())
        {
          UserCommentItem localUserCommentItem = new UserCommentItem();
          JSONObject localJSONObject = (JSONObject)paramJSONArray.get(i);
          localUserCommentItem.setAbscont(localJSONObject.getString("abscont"));
          localUserCommentItem.setAbscont_last(localJSONObject.getString("abscont_last"));
          localUserCommentItem.setCnum(localJSONObject.getInt("cnum"));
          localUserCommentItem.setCtime(Long.valueOf(localJSONObject.getLong("ctime")));
          localUserCommentItem.setCtime_last(localJSONObject.getString("ctime_last"));
          localUserCommentItem.setFlogo(localJSONObject.getString("flogo"));
          localUserCommentItem.setFname(localJSONObject.getString("fname"));
          localUserCommentItem.setFname_last(localJSONObject.getString("fname_last"));
          localUserCommentItem.setFuid(localJSONObject.getString("fuid"));
          localUserCommentItem.setFuid_last(localJSONObject.getString("fuid_last"));
          localUserCommentItem.setMtype(localJSONObject.getInt("mtype"));
          localUserCommentItem.setThread_cid(localJSONObject.getString("thread_cid"));
          localUserCommentItem.setUnread(localJSONObject.getInt("unread"));
          paramArrayList.add(localUserCommentItem);
          i++;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessageCenterModel", "setUserCommentList error", localException);
      }
    }
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