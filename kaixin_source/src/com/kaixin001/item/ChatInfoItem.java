package com.kaixin001.item;

import android.text.TextUtils;
import com.kaixin001.model.User;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatInfoItem
{
  public static final int INT_TYPE_CHAT_MSG = 1;
  public static final int INT_TYPE_CHAT_MSG_CIRCLE = 2;
  public static final int INT_TYPE_CHAT_TYPING = 32;
  public static final int INT_TYPE_CIRCLE_KICKED = 128;
  public static final int INT_TYPE_CIRCLE_NEW_MEMBERS = 256;
  public static final int INT_TYPE_GCHAT_TYPING = 64;
  public static final int INT_TYPE_NEW_MESSAGE = 16;
  public static final int INT_TYPE_UNKNOWN = 0;
  public static final int INT_TYPE_USER_OFFLINE = 8;
  public static final int INT_TYPE_USER_ONLINE = 4;
  public static final int STATUS_NORMAL = 0;
  public static final int STATUS_RESENDING = 4;
  public static final int STATUS_SENDING = 1;
  public static final int STATUS_SENDING_FAILED = 2;
  public static final int STATUS_SENDING_FAILED_RESENDED = 5;
  public static final int STATUS_SENDING_FAILED_TIPS = 3;
  private static final String TYPE_CHAT_CLEAR = "chat.clear";
  private static final String TYPE_CHAT_CLOST = "chat.close";
  private static final String TYPE_CHAT_MAX = "chat.max";
  private static final String TYPE_CHAT_MIN = "chat.min";
  private static final String TYPE_CHAT_MSG = "chat.msg";
  private static final String TYPE_CHAT_TYPING = "chat.typing";
  private static final String TYPE_CIRCLE_KICKED = "gchat.out";
  private static final String TYPE_CIRCLE_NEW_MEMBERS = "gchat.in";
  private static final String TYPE_GCHAT_CLEAR = "gchat.clear";
  private static final String TYPE_GCHAT_CLOST = "gchat.close";
  private static final String TYPE_GCHAT_MAX = "gchat.max";
  private static final String TYPE_GCHAT_MIN = "gchat.min";
  private static final String TYPE_GCHAT_MSG = "gchat.msg";
  private static final String TYPE_GCHAT_TYPING = "gchat.typing";
  private static final String TYPE_HAIBEI_CLEAR = "kxg.clear";
  private static final String TYPE_HAIBEI_NEW = "kxg.new";
  private static final String TYPE_NEW_MESSAGE = ".ctx";
  private static final String TYPE_USER_OFFLINE = ".user.offline";
  private static final String TYPE_USER_ONLINE = ".user.online";
  private static final String TYPE_WEIBO_CLEAR = "kxt.clear";
  private static final String TYPE_WEIBO_NEW = "kxt.new";
  public long mCTime;
  public int mInfoTypeInt = 0;
  public int mSendStatus = 0;
  public IChatInfoSubObject mSubObject = null;

  public static ChatInfoItem createChatMsg(String paramString1, String paramString2, String paramString3, long paramLong, String paramString4, String paramString5, String paramString6, boolean paramBoolean1, boolean paramBoolean2)
  {
    return createChatMsg(paramString1, paramString2, paramString3, paramLong, paramString4, paramString5, paramString6, paramBoolean1, paramBoolean2, 0);
  }

  public static ChatInfoItem createChatMsg(String paramString1, String paramString2, String paramString3, long paramLong, String paramString4, String paramString5, String paramString6, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
  {
    ChatInfoItem localChatInfoItem = new ChatInfoItem();
    if (paramBoolean1);
    for (String str = "gchat.msg"; ; str = "chat.msg")
    {
      localChatInfoItem.setInfoType(str);
      localChatInfoItem.mSendStatus = paramInt;
      localChatInfoItem.mCTime = paramLong;
      localChatInfoItem.mSubObject = new ChatMsg(paramString1, paramString2, paramString3, paramLong, paramString4, paramString5, paramString6, paramBoolean1, paramBoolean2);
      return localChatInfoItem;
    }
  }

  public static ChatInfoItem createChatMsg(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, boolean paramBoolean1, boolean paramBoolean2)
  {
    ChatInfoItem localChatInfoItem = new ChatInfoItem();
    if (paramBoolean1);
    for (String str = "gchat.msg"; ; str = "chat.msg")
    {
      localChatInfoItem.setInfoType(str);
      localChatInfoItem.mCTime = System.currentTimeMillis();
      localChatInfoItem.mSubObject = new ChatMsg(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramBoolean1, paramBoolean2);
      return localChatInfoItem;
    }
  }

  private void createSubObject(JSONObject paramJSONObject)
  {
    if (isUserOnline())
    {
      this.mSubObject = new UserStatusChanged(true).valueOf(paramJSONObject);
      return;
    }
    if (isUserOffline())
    {
      this.mSubObject = new UserStatusChanged(false).valueOf(paramJSONObject);
      return;
    }
    if (isChatMsg())
    {
      this.mSubObject = new ChatMsg(false).valueOf(paramJSONObject);
      return;
    }
    if (isCircleChatMsg())
    {
      this.mSubObject = new ChatMsg(true).valueOf(paramJSONObject);
      return;
    }
    if (isNewMessage())
    {
      this.mSubObject = new NewMessage().valueOf(paramJSONObject);
      return;
    }
    if (isTyping())
    {
      this.mSubObject = new UserTyping(false).valueOf(paramJSONObject);
      return;
    }
    if (isCircleTyping())
    {
      this.mSubObject = new UserTyping(true).valueOf(paramJSONObject);
      return;
    }
    if (isCircleNewMember())
    {
      this.mSubObject = new CircleMemberChanged(true).valueOf(paramJSONObject);
      return;
    }
    if (isCircleKicked())
    {
      this.mSubObject = new CircleMemberChanged(false).valueOf(paramJSONObject);
      return;
    }
    this.mSubObject = null;
  }

  private void setInfoType(String paramString)
  {
    if ("chat.msg".equalsIgnoreCase(paramString))
      this.mInfoTypeInt = 1;
    do
    {
      return;
      if ("gchat.msg".equalsIgnoreCase(paramString))
      {
        this.mInfoTypeInt = 2;
        return;
      }
      if (".user.online".equalsIgnoreCase(paramString))
      {
        this.mInfoTypeInt = 4;
        return;
      }
      if (".user.offline".equalsIgnoreCase(paramString))
      {
        this.mInfoTypeInt = 8;
        return;
      }
      if (".ctx".equalsIgnoreCase(paramString))
      {
        this.mInfoTypeInt = 16;
        return;
      }
      if ("chat.typing".equalsIgnoreCase(paramString))
      {
        this.mInfoTypeInt = 32;
        return;
      }
      if ("gchat.typing".equalsIgnoreCase(paramString))
      {
        this.mInfoTypeInt = 64;
        return;
      }
      if (!"gchat.out".equalsIgnoreCase(paramString))
        continue;
      this.mInfoTypeInt = 128;
      return;
    }
    while (!"gchat.in".equalsIgnoreCase(paramString));
    this.mInfoTypeInt = 256;
  }

  public static ChatInfoItem valueOf(JSONObject paramJSONObject)
  {
    if (paramJSONObject != null)
    {
      ChatInfoItem localChatInfoItem = new ChatInfoItem();
      localChatInfoItem.setInfoType(paramJSONObject.optString("c"));
      localChatInfoItem.mCTime = paramJSONObject.optLong("t");
      localChatInfoItem.createSubObject(paramJSONObject.optJSONObject("o"));
      return localChatInfoItem;
    }
    return null;
  }

  public void copyFrom(ChatInfoItem paramChatInfoItem)
  {
    this.mCTime = paramChatInfoItem.mCTime;
    this.mInfoTypeInt = paramChatInfoItem.mInfoTypeInt;
    this.mSubObject.copyFrom(paramChatInfoItem.mSubObject);
  }

  public boolean isChatMsg()
  {
    return this.mInfoTypeInt == 1;
  }

  public boolean isCircleChatMsg()
  {
    return this.mInfoTypeInt == 2;
  }

  public boolean isCircleKicked()
  {
    return this.mInfoTypeInt == 128;
  }

  public boolean isCircleNewMember()
  {
    return this.mInfoTypeInt == 256;
  }

  public boolean isCircleTyping()
  {
    return this.mInfoTypeInt == 64;
  }

  public boolean isFeedbackChatMsg()
  {
    int i;
    if (!isChatMsg())
    {
      boolean bool3 = isCircleChatMsg();
      i = 0;
      if (!bool3);
    }
    else
    {
      ChatMsg localChatMsg = (ChatMsg)this.mSubObject;
      boolean bool1 = User.getInstance().getUID().equals(localChatMsg.mSenderUID);
      i = 0;
      if (bool1)
        if (!TextUtils.isEmpty(localChatMsg.mChatID))
        {
          boolean bool2 = "0".equals(localChatMsg.mChatID);
          i = 0;
          if (!bool2);
        }
        else
        {
          i = 1;
        }
    }
    return i;
  }

  public boolean isNewMessage()
  {
    return this.mInfoTypeInt == 16;
  }

  public boolean isSelfTyping()
  {
    if ((isTyping()) || (isCircleTyping()))
    {
      UserTyping localUserTyping = (UserTyping)this.mSubObject;
      return User.getInstance().getUID().equals(localUserTyping.mUID);
    }
    return false;
  }

  public boolean isSendChatMsg()
  {
    if ((isChatMsg()) || (isCircleChatMsg()))
      return ((ChatMsg)this.mSubObject).misSend;
    return false;
  }

  public boolean isTyping()
  {
    return this.mInfoTypeInt == 32;
  }

  public boolean isUserOffline()
  {
    return this.mInfoTypeInt == 8;
  }

  public boolean isUserOnline()
  {
    return this.mInfoTypeInt == 4;
  }

  public boolean isValid()
  {
    return (this.mInfoTypeInt > 0) && (this.mSubObject != null);
  }

  public String toString()
  {
    return "ChatInfoItem type:" + this.mInfoTypeInt;
  }

  public static class ChatMsg
    implements ChatInfoItem.IChatInfoSubObject
  {
    public String mChatID;
    public String mContent;
    public String mGID;
    public boolean mIsCircle;
    public String mLogo;
    public String mMsgID;
    public String mRealName;
    public String mReceiverUID;
    public String mSenderUID;
    public String mTime;
    public long mUniqueID;
    public boolean misSend;

    public ChatMsg(String paramString1, String paramString2, String paramString3, long paramLong, String paramString4, String paramString5, String paramString6, boolean paramBoolean1, boolean paramBoolean2)
    {
      this.mChatID = paramString1;
      this.mMsgID = paramString3;
      this.mContent = handleAttatch(paramString2);
      this.mTime = formatTimestamp(1000L * paramLong);
      this.mReceiverUID = paramString4;
      this.mSenderUID = paramString5;
      this.mGID = paramString6;
      this.mIsCircle = paramBoolean1;
      this.misSend = paramBoolean2;
      createUniqueID();
    }

    public ChatMsg(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, boolean paramBoolean1, boolean paramBoolean2)
    {
      this.mChatID = paramString1;
      this.mMsgID = paramString3;
      this.mContent = handleAttatch(paramString2);
      this.mTime = paramString4;
      this.mReceiverUID = paramString5;
      this.mSenderUID = paramString6;
      this.mGID = paramString7;
      this.mIsCircle = paramBoolean1;
      this.misSend = paramBoolean2;
      createUniqueID();
    }

    public ChatMsg(boolean paramBoolean)
    {
      this.mIsCircle = paramBoolean;
      this.misSend = false;
      createUniqueID();
    }

    public static String formatTimestamp(long paramLong)
    {
      int i = Calendar.getInstance().get(1);
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.setTimeInMillis(paramLong);
      if (localCalendar.get(1) != i);
      for (String str = "yyyy-MM-dd HH:mm"; ; str = "MM-dd HH:mm:ss")
      {
        Date localDate = new Date(paramLong);
        return new SimpleDateFormat(str).format(localDate);
      }
    }

    private String getContent(JSONObject paramJSONObject)
    {
      String str1 = paramJSONObject.optString("msg");
      String str2 = null;
      if (str1 != null);
      JSONObject localJSONObject2;
      JSONObject localJSONObject3;
      long l;
      try
      {
        JSONObject localJSONObject1 = new JSONObject(str1);
        localJSONObject2 = localJSONObject1;
        str2 = null;
        if (localJSONObject2 != null)
        {
          localJSONObject3 = localJSONObject2.optJSONObject("attachment");
          if (localJSONObject3 == null)
            break label198;
          l = Long.valueOf(localJSONObject3.optString("size", "")).longValue();
          if (l >= 1048576L)
          {
            DecimalFormat localDecimalFormat = new DecimalFormat("#0.00");
            str2 = "发送了文件:" + localJSONObject3.optString("filename", "") + "大小:" + localDecimalFormat.format(l / 1024L / 1024.0D) + "M";
          }
        }
        else
        {
          return str2;
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          localException.printStackTrace();
          localJSONObject2 = null;
        }
      }
      return "发送了文件:" + localJSONObject3.optString("filename", "") + "大小:" + l / 1024L + "K";
      label198: return localJSONObject2.optString("content", "");
    }

    private String handleAttatch(String paramString)
    {
      if (!TextUtils.isEmpty(paramString))
      {
        Matcher localMatcher = Pattern.compile("(发送了文件:.*?)<.*?ChatDownloader.*?>(.*?)</a>(.*大小:.*?)\\(", 32).matcher(paramString);
        if (localMatcher.find())
          paramString = localMatcher.group(1) + localMatcher.group(2) + localMatcher.group(3);
      }
      return paramString;
    }

    public void copyFrom(ChatInfoItem.IChatInfoSubObject paramIChatInfoSubObject)
    {
    }

    public void createUniqueID()
    {
      this.mUniqueID = System.currentTimeMillis();
    }

    public String getID()
    {
      return this.mChatID + this.mMsgID + this.mUniqueID;
    }

    public String toString()
    {
      return "ChatMsg mSenderUID:" + this.mSenderUID + " mReceiverUID:" + this.mReceiverUID + " gid:" + this.mGID + " content:" + this.mContent + " mIsCircle:" + this.mIsCircle;
    }

    public ChatInfoItem.IChatInfoSubObject valueOf(JSONObject paramJSONObject)
    {
      if (paramJSONObject != null)
      {
        this.mChatID = paramJSONObject.optString("chatid");
        this.mMsgID = paramJSONObject.optString("cmid");
        this.mContent = handleAttatch(getContent(paramJSONObject));
        this.mTime = paramJSONObject.optString("ctime");
        this.mReceiverUID = paramJSONObject.optString("otheruid");
        this.mSenderUID = paramJSONObject.optString("uid");
        this.mLogo = paramJSONObject.optString("logo20");
        this.mRealName = paramJSONObject.optString("real_name");
        this.mGID = paramJSONObject.optString("gid");
        if (!TextUtils.isEmpty(this.mGID))
        {
          this.mGID = this.mGID.replace("G", "");
          this.mGID = this.mGID.replace("g", "");
        }
        this.misSend = User.getInstance().getUID().equals(this.mSenderUID);
        return this;
      }
      return null;
    }
  }

  public static class CircleMemberChanged
    implements ChatInfoItem.IChatInfoSubObject
  {
    public String mCircleName;
    public int mCircleType;
    public String mGID;
    public int mIsFriend;
    public boolean mIsMemberIn;
    public String mRealName;
    public String mUID;

    public CircleMemberChanged(boolean paramBoolean)
    {
      this.mIsMemberIn = paramBoolean;
    }

    public void copyFrom(ChatInfoItem.IChatInfoSubObject paramIChatInfoSubObject)
    {
    }

    public String getID()
    {
      return this.mGID + this.mUID + this.mIsMemberIn;
    }

    public boolean isSelfJoinin()
    {
      return (this.mIsMemberIn) && (User.getInstance().getUID().equals(this.mUID));
    }

    public boolean isSelfKicked()
    {
      return (!this.mIsMemberIn) && (User.getInstance().getUID().equals(this.mUID));
    }

    public ChatInfoItem.IChatInfoSubObject valueOf(JSONObject paramJSONObject)
    {
      if (paramJSONObject != null)
      {
        this.mGID = paramJSONObject.optString("gid");
        this.mUID = paramJSONObject.optString("uid");
        this.mRealName = paramJSONObject.optString("realname");
        this.mIsFriend = paramJSONObject.optInt("isfriend");
        if (!TextUtils.isEmpty(this.mGID))
        {
          this.mGID = this.mGID.replace("G", "");
          return this;
        }
      }
      return null;
    }
  }

  public static abstract interface IChatInfoSubObject
  {
    public abstract void copyFrom(IChatInfoSubObject paramIChatInfoSubObject);

    public abstract String getID();

    public abstract IChatInfoSubObject valueOf(JSONObject paramJSONObject);
  }

  public static class NewMessage
    implements ChatInfoItem.IChatInfoSubObject
  {
    public int mCircleMsgNum;
    public int mCommentNum;
    public int mHaibeiNum;
    public int mMsgNum;
    public int mNotice;
    public int mReplyCommentNum;
    public int mSystemMsgNum;
    public int mUserCommentNum;
    public int mWeiboNum;

    public void copyFrom(ChatInfoItem.IChatInfoSubObject paramIChatInfoSubObject)
    {
      if ((paramIChatInfoSubObject instanceof NewMessage))
      {
        NewMessage localNewMessage = (NewMessage)paramIChatInfoSubObject;
        this.mNotice = localNewMessage.mNotice;
        this.mMsgNum = localNewMessage.mMsgNum;
        this.mSystemMsgNum = localNewMessage.mSystemMsgNum;
        this.mUserCommentNum = localNewMessage.mUserCommentNum;
        this.mCommentNum = localNewMessage.mCommentNum;
        this.mReplyCommentNum = localNewMessage.mReplyCommentNum;
        this.mCircleMsgNum = localNewMessage.mCircleMsgNum;
        this.mWeiboNum = localNewMessage.mWeiboNum;
        this.mHaibeiNum = localNewMessage.mHaibeiNum;
      }
    }

    public String getID()
    {
      return String.valueOf(this.mNotice);
    }

    public boolean needRequestForSure()
    {
      return (this.mSystemMsgNum > 0) || (this.mUserCommentNum > 0) || (this.mCircleMsgNum > 0);
    }

    public ChatInfoItem.IChatInfoSubObject valueOf(JSONObject paramJSONObject)
    {
      if (paramJSONObject != null)
      {
        String str = paramJSONObject.optString("state");
        if (!TextUtils.isEmpty(str))
          try
          {
            JSONObject localJSONObject1 = new JSONObject(str);
            localJSONObject2 = localJSONObject1;
            if (localJSONObject2 != null)
            {
              this.mNotice = localJSONObject2.optInt("notice", 0);
              this.mMsgNum = localJSONObject2.optInt("msg", 0);
              this.mSystemMsgNum = localJSONObject2.optInt("sysmsg", 0);
              this.mUserCommentNum = localJSONObject2.optInt("bbs", 0);
              this.mCommentNum = localJSONObject2.optInt("comment", 0);
              this.mReplyCommentNum = localJSONObject2.optInt("reply", 0);
              this.mCircleMsgNum = localJSONObject2.optInt("rgroupmsg", 0);
              this.mWeiboNum = localJSONObject2.optInt("kxt", 0);
              this.mHaibeiNum = localJSONObject2.optInt("kxg", 0);
              return this;
            }
          }
          catch (JSONException localJSONException)
          {
            while (true)
            {
              localJSONException.printStackTrace();
              JSONObject localJSONObject2 = null;
            }
          }
      }
      return null;
    }
  }

  public static class UserStatusChanged
    implements ChatInfoItem.IChatInfoSubObject
  {
    public boolean mIsOnline;
    public String mUID;

    public UserStatusChanged(boolean paramBoolean)
    {
      this.mIsOnline = paramBoolean;
    }

    public void copyFrom(ChatInfoItem.IChatInfoSubObject paramIChatInfoSubObject)
    {
    }

    public String getID()
    {
      return this.mUID + this.mIsOnline;
    }

    public ChatInfoItem.IChatInfoSubObject valueOf(JSONObject paramJSONObject)
    {
      if (paramJSONObject != null)
      {
        this.mUID = paramJSONObject.optString("uid");
        return this;
      }
      return null;
    }
  }

  public static class UserTyping
    implements ChatInfoItem.IChatInfoSubObject
  {
    public String mGID;
    public boolean mIsCircle = false;
    public String mRealName = null;
    public String mUID;

    public UserTyping(boolean paramBoolean)
    {
      this.mIsCircle = paramBoolean;
    }

    public void copyFrom(ChatInfoItem.IChatInfoSubObject paramIChatInfoSubObject)
    {
    }

    public String getID()
    {
      return this.mUID + this.mIsCircle + this.mGID;
    }

    public ChatInfoItem.IChatInfoSubObject valueOf(JSONObject paramJSONObject)
    {
      if (paramJSONObject != null)
      {
        this.mUID = paramJSONObject.optString("uid");
        this.mGID = paramJSONObject.optString("gid");
        this.mRealName = paramJSONObject.optString("real_name");
        if (!TextUtils.isEmpty(this.mGID))
        {
          this.mGID = this.mGID.replace("G", "");
          this.mGID = this.mGID.replace("g", "");
        }
        return this;
      }
      return null;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.ChatInfoItem
 * JD-Core Version:    0.6.0
 */