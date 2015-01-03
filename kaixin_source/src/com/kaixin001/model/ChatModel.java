package com.kaixin001.model;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import com.kaixin001.activity.MessageHandlerHolder;
import com.kaixin001.engine.KXEngine;
import com.kaixin001.item.ChatInfoItem;
import com.kaixin001.item.ChatInfoItem.ChatMsg;
import com.kaixin001.item.ChatInfoItem.IChatInfoSubObject;
import com.kaixin001.item.ChatInfoItem.UserStatusChanged;
import com.kaixin001.service.RefreshNewMessageService;
import com.kaixin001.util.BlockedCircleManagerInterface;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONObject;

public class ChatModel extends KXModel
{
  public static final String CHAT_CIRCLEMSG_UNREAD_ALL = "CHAT_CIRCLE_MSG_UNREAD_ALL";
  public static final String CHAT_MSG_TOTAL = "CHAT_MSG_TOTAL";
  public static final String CHAT_MSG_UNREAD_ALL = "CHAT_MSG_UNREAD_ALL";
  private static final String HASH_KEY_PREFIX_GID = "GID=";
  private static final String HASH_KEY_PREFIX_UID = "UID=";
  private static final int MAX_READ_CONVERSITIONS = 50;
  private static final int MAX_READ_SAVED = 20;
  private static final int MAX_UNREAD_CONVERSITIONS = 200;
  private static final int MAX_UNREAD_NUMBER = 999;
  private static final int MAX_UNREAD_SAVED = 500;
  private static final String TAG = "ChatModel";
  public static final boolean USECHATINITLIST;
  private static ChatModel sInstance = null;
  BlockedCircleManagerInterface blockedCircleManager = null;
  private int mBlockedCircleUnreadAll = 0;
  private ArrayList<String> mBlockedCircles = new ArrayList();
  private BuddyList mBuddyList = new BuddyList();
  private String mChatHost = null;
  private String mChatID = null;
  private ChatCircleList mCircleList = new ChatCircleList();
  private int mCircleMsgUnreadAll = 0;
  private ConversitionList mConversitionList = new ConversitionList();
  private String mCookie = null;
  private String mCookieKey = null;
  private String mCookieValue = null;
  private int mImSeq = -1;
  private HashMap<String, ArrayList<ChatInfoItem>> mListMap = new HashMap();
  private int mMsgUnreadAll = 0;
  private HashMap<String, ArrayList<ChatInfoItem>> mNotifyList = new HashMap();
  private HashMap<String, Integer> mUnreadNums = new HashMap();
  private boolean needEnsureCirclesConversition = false;
  private boolean needEnsureFriendsConversition = false;

  private ChatModel()
  {
    this.mUnreadNums.put("CHAT_MSG_UNREAD_ALL", Integer.valueOf(0));
    this.mUnreadNums.put("CHAT_CIRCLE_MSG_UNREAD_ALL", Integer.valueOf(0));
  }

  private void addBlockedCircleUnreadNum(int paramInt)
  {
    this.mBlockedCircleUnreadAll = (paramInt + this.mBlockedCircleUnreadAll);
  }

  private void addChatInfoByType(ChatInfoItem paramChatInfoItem, boolean paramBoolean)
  {
    boolean bool1 = paramChatInfoItem.isCircleChatMsg();
    ChatInfoItem.ChatMsg localChatMsg2;
    String str3;
    String str1;
    String str2;
    label61: ArrayList localArrayList1;
    boolean bool2;
    if (paramChatInfoItem.isChatMsg())
    {
      localChatMsg2 = (ChatInfoItem.ChatMsg)paramChatInfoItem.mSubObject;
      if (paramBoolean)
      {
        str3 = localChatMsg2.mReceiverUID;
        addConversitionByFriendID(str3);
        str1 = "UID=" + str3;
        str2 = str3;
        localArrayList1 = (ArrayList)this.mListMap.get(str1);
        if (localArrayList1 != null)
          break label193;
        ArrayList localArrayList2 = new ArrayList();
        bool2 = addChatInfoItem(paramChatInfoItem, localArrayList2);
        this.mListMap.put(str1, localArrayList2);
      }
    }
    while (true)
    {
      if (paramBoolean)
        break label248;
      if (bool2)
        addUnreadMsgNum(str1, bool1, str2);
      do
      {
        return;
        str3 = localChatMsg2.mSenderUID;
        break;
      }
      while (!bool1);
      ChatInfoItem.ChatMsg localChatMsg1 = (ChatInfoItem.ChatMsg)paramChatInfoItem.mSubObject;
      addConversitionByCircleID(localChatMsg1.mGID);
      str1 = "GID=" + localChatMsg1.mGID;
      str2 = localChatMsg1.mGID;
      break label61;
      label193: bool2 = addChatInfoItem(paramChatInfoItem, localArrayList1);
      if ((paramBoolean) || (localArrayList1.size() <= 500) || ((!bool1) && ((bool1) || (getUnreadNum(str2, bool1) >= 500))))
        continue;
      localArrayList1.remove(0);
    }
    label248: sendSyncMessage(str2, bool1);
  }

  private boolean addChatInfoItem(ChatInfoItem paramChatInfoItem, ArrayList<ChatInfoItem> paramArrayList)
  {
    KXLog.d("ChatInfoItem", "msg = " + paramChatInfoItem.mCTime + " " + paramChatInfoItem.mSubObject.getID());
    Iterator localIterator = paramArrayList.iterator();
    ChatInfoItem localChatInfoItem;
    do
    {
      if (!localIterator.hasNext())
      {
        KXLog.d("ChatInfoItem", "added");
        paramArrayList.add(paramChatInfoItem);
        return true;
      }
      localChatInfoItem = (ChatInfoItem)localIterator.next();
    }
    while (!paramChatInfoItem.mSubObject.getID().equals(localChatInfoItem.mSubObject.getID()));
    return false;
  }

  private void addConversitionByCircleID(String paramString)
  {
    CircleModel.CircleItem localCircleItem = CircleModel.getInstance().searchCircleItem(paramString);
    if (localCircleItem != null)
    {
      addConversition(localCircleItem);
      return;
    }
    this.needEnsureCirclesConversition = true;
  }

  private void addConversitionByFriendID(String paramString)
  {
    FriendsModel.Friend localFriend = FriendsModel.getInstance().searchAllFriends(paramString);
    if (localFriend != null)
    {
      addConversition(localFriend);
      return;
    }
    this.needEnsureFriendsConversition = true;
  }

  private void addUnreadMsgNum(String paramString1, boolean paramBoolean, String paramString2)
  {
    if (paramBoolean)
    {
      HashMap localHashMap2 = this.mUnreadNums;
      int j = 1 + this.mCircleMsgUnreadAll;
      this.mCircleMsgUnreadAll = j;
      localHashMap2.put("CHAT_CIRCLE_MSG_UNREAD_ALL", Integer.valueOf(j));
    }
    while (true)
    {
      Integer localInteger = (Integer)this.mUnreadNums.get(paramString1);
      if (localInteger == null)
        localInteger = Integer.valueOf(0);
      this.mUnreadNums.put(paramString1, Integer.valueOf(1 + localInteger.intValue()));
      if ((paramBoolean) && (this.mBlockedCircles.contains(paramString2)))
        addBlockedCircleUnreadNum(1);
      sendUnreadsChangedMessage(paramString2, paramBoolean, false);
      return;
      HashMap localHashMap1 = this.mUnreadNums;
      int i = 1 + this.mMsgUnreadAll;
      this.mMsgUnreadAll = i;
      localHashMap1.put("CHAT_MSG_UNREAD_ALL", Integer.valueOf(i));
    }
  }

  public static ChatModel getInstance()
  {
    monitorenter;
    try
    {
      if (sInstance == null)
        sInstance = new ChatModel();
      ChatModel localChatModel = sInstance;
      return localChatModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void initBlockedCircles()
  {
    this.mBlockedCircles.clear();
    if (this.blockedCircleManager != null)
    {
      ArrayList localArrayList = this.blockedCircleManager.load();
      if ((localArrayList != null) && (localArrayList.size() > 0))
        this.mBlockedCircles.addAll(localArrayList);
    }
  }

  private void makeCookieString()
  {
    if ((TextUtils.isEmpty(this.mCookieKey)) || (TextUtils.isEmpty(this.mCookieValue)))
    {
      this.mCookie = null;
      return;
    }
    this.mCookie = (this.mCookieKey + "=" + this.mCookieValue);
  }

  private void minusBlockedCircleUnread(int paramInt)
  {
    this.mBlockedCircleUnreadAll -= paramInt;
  }

  public void addBlockedCircleItem(String paramString)
  {
    if (!this.mBlockedCircles.contains(paramString))
    {
      this.mBlockedCircles.add(paramString);
      if (this.blockedCircleManager != null)
        this.blockedCircleManager.add(paramString);
      addBlockedCircleUnreadNum(getUnreadNum(paramString, true));
    }
  }

  public void addBuddy(Buddy paramBuddy)
  {
    if (paramBuddy != null)
      this.mBuddyList.add(paramBuddy);
  }

  public void addChatInfo(ChatInfoItem paramChatInfoItem, boolean paramBoolean)
  {
    if ((paramChatInfoItem != null) && (paramChatInfoItem.isValid()))
      addChatInfoByType(paramChatInfoItem, paramBoolean);
  }

  public void addCircle(ChatCircleItem paramChatCircleItem)
  {
    if (paramChatCircleItem != null)
      this.mCircleList.add(paramChatCircleItem);
  }

  public void addCircleOfflineChatInfo(ChatInfoItem paramChatInfoItem)
  {
    String str2;
    ArrayList localArrayList1;
    if ((paramChatInfoItem != null) && (paramChatInfoItem.isValid()))
    {
      ChatInfoItem.ChatMsg localChatMsg = (ChatInfoItem.ChatMsg)paramChatInfoItem.mSubObject;
      String str1 = "GID=" + localChatMsg.mGID;
      str2 = localChatMsg.mGID;
      localArrayList1 = (ArrayList)this.mListMap.get(str1);
      if (localArrayList1 != null)
        break label102;
      ArrayList localArrayList2 = new ArrayList();
      addChatInfoItem(paramChatInfoItem, localArrayList2);
      this.mListMap.put(str1, localArrayList2);
    }
    while (true)
    {
      sendSyncMessage(str2, paramChatInfoItem.isCircleChatMsg());
      return;
      label102: addChatInfoItem(paramChatInfoItem, localArrayList1);
      int i = getUnreadNum(str2, true);
      if (((i != 0) || (localArrayList1.size() <= 20)) && ((i <= 0) || (localArrayList1.size() <= 500)))
        continue;
      localArrayList1.remove(0);
    }
  }

  public void addCircleUnreadMsgNum(String paramString, int paramInt)
  {
    this.mCircleMsgUnreadAll = (paramInt + this.mCircleMsgUnreadAll);
    this.mUnreadNums.put("CHAT_CIRCLE_MSG_UNREAD_ALL", Integer.valueOf(this.mCircleMsgUnreadAll));
    String str = "GID=" + paramString;
    Integer localInteger1 = (Integer)this.mUnreadNums.get(str);
    if (localInteger1 == null)
      localInteger1 = Integer.valueOf(0);
    Integer localInteger2 = Integer.valueOf(paramInt + localInteger1.intValue());
    this.mUnreadNums.put(str, localInteger2);
    addConversitionByCircleID(paramString);
    if (this.mBlockedCircles.contains(paramString))
      addBlockedCircleUnreadNum(paramInt);
    sendUnreadsChangedMessage(paramString, true, false);
  }

  public void addConversition(Conversition paramConversition)
  {
    this.mConversitionList.addOrUpdate(paramConversition);
  }

  public void addConversition(CircleModel.CircleItem paramCircleItem)
  {
    Conversition localConversition = new Conversition();
    localConversition.copyFrom(paramCircleItem);
    this.mConversitionList.addOrUpdate(localConversition);
  }

  public void addConversition(FriendsModel.Friend paramFriend)
  {
    Conversition localConversition = new Conversition();
    localConversition.copyFrom(paramFriend);
    this.mConversitionList.addOrUpdate(localConversition);
  }

  public ChatInfoItem addSendChatInfo(String paramString1, String paramString2, boolean paramBoolean, int paramInt)
  {
    String str1 = getInstance().getChatID();
    long l = System.currentTimeMillis() / 1000L;
    String str2;
    String str3;
    if (paramBoolean)
    {
      str2 = "";
      str3 = User.getInstance().getUID();
      if (!paramBoolean)
        break label80;
    }
    label80: for (String str4 = paramString1; ; str4 = "")
    {
      ChatInfoItem localChatInfoItem = ChatInfoItem.createChatMsg(str1, paramString2, "", l, str2, str3, str4, paramBoolean, true, paramInt);
      addChatInfo(localChatInfoItem, true);
      return localChatInfoItem;
      str2 = paramString1;
      break;
    }
  }

  public void clear()
  {
    KXLog.d("TEST", "clear Cookie");
    this.mChatID = null;
    this.mChatHost = null;
    this.mCookieKey = null;
    this.mCookieValue = null;
    this.mCookie = null;
    this.mImSeq = -1;
    this.mBuddyList.clear();
    this.mCircleList.clear();
  }

  public void clearAllMessage()
  {
    KXLog.d("TEST", "clearAllMessage");
    this.mConversitionList.clear();
    this.mListMap.clear();
    this.mUnreadNums.clear();
    this.mMsgUnreadAll = 0;
    this.mCircleMsgUnreadAll = 0;
    this.mBlockedCircleUnreadAll = 0;
    this.mBuddyList.clear();
    this.mCircleList.clear();
  }

  public void clearMsgInMemory(String paramString, boolean paramBoolean)
  {
    if (getUnreadNum(paramString, paramBoolean) == 0)
      if (!paramBoolean)
        break label78;
    label78: for (String str = "GID=" + paramString; ; str = "UID=" + paramString)
    {
      ArrayList localArrayList = (ArrayList)this.mListMap.get(str);
      if ((localArrayList != null) && (localArrayList.size() > 20))
        localArrayList.subList(0, -20 + localArrayList.size()).clear();
      return;
    }
  }

  public void clearUnreadNum(String paramString, boolean paramBoolean)
  {
    String str;
    Integer localInteger;
    if (paramBoolean)
    {
      str = "GID=" + paramString;
      localInteger = (Integer)this.mUnreadNums.get(str);
      if ((localInteger != null) && (localInteger.intValue() > 0))
      {
        if (!paramBoolean)
          break label143;
        if (this.mBlockedCircles.contains(paramString))
          minusBlockedCircleUnread(localInteger.intValue());
        this.mCircleMsgUnreadAll -= localInteger.intValue();
        this.mUnreadNums.put("CHAT_CIRCLE_MSG_UNREAD_ALL", Integer.valueOf(this.mCircleMsgUnreadAll));
      }
    }
    while (true)
    {
      this.mUnreadNums.put(str, Integer.valueOf(0));
      sendUnreadsChangedMessage(paramString, paramBoolean, true);
      return;
      str = "UID=" + paramString;
      break;
      label143: this.mMsgUnreadAll -= localInteger.intValue();
      this.mUnreadNums.put("CHAT_MSG_UNREAD_ALL", Integer.valueOf(this.mMsgUnreadAll));
    }
  }

  public void delBlockedCircleItem(String paramString)
  {
    if (this.mBlockedCircles.remove(paramString))
    {
      if (this.blockedCircleManager != null)
        this.blockedCircleManager.del(paramString);
      minusBlockedCircleUnread(getUnreadNum(paramString, true));
    }
  }

  public boolean ensureCirclesConversiton()
  {
    boolean bool = this.needEnsureCirclesConversition;
    int i = 0;
    Iterator localIterator;
    if (bool)
    {
      this.needEnsureCirclesConversition = false;
      localIterator = CircleModel.getInstance().getCircles().iterator();
    }
    while (true)
    {
      if (!localIterator.hasNext())
        return i;
      CircleModel.CircleItem localCircleItem = (CircleModel.CircleItem)localIterator.next();
      if (getUnreadNum(localCircleItem.gid, true) <= 0)
        continue;
      addConversition(localCircleItem);
      i = 1;
    }
  }

  public boolean ensureFriendsConversiton()
  {
    boolean bool = this.needEnsureFriendsConversition;
    int i = 0;
    Iterator localIterator;
    if (bool)
    {
      this.needEnsureFriendsConversition = false;
      localIterator = FriendsModel.getInstance().getFriends().iterator();
    }
    while (true)
    {
      if (!localIterator.hasNext())
        return i;
      FriendsModel.Friend localFriend = (FriendsModel.Friend)localIterator.next();
      if (getUnreadNum(localFriend.getFuid(), false) <= 0)
        continue;
      addConversition(localFriend);
      i = 1;
    }
  }

  public String getChatHost()
  {
    return this.mChatHost;
  }

  public String getChatID()
  {
    return this.mChatID;
  }

  public ChatInfoItem getChatInfoItem(String paramString, boolean paramBoolean, long paramLong)
  {
    ArrayList localArrayList = getChatInfoList(paramString, paramBoolean);
    if (localArrayList != null);
    for (int i = 0; ; i++)
    {
      if (i >= localArrayList.size())
        return null;
      ChatInfoItem localChatInfoItem = (ChatInfoItem)localArrayList.get(i);
      if (((ChatInfoItem.ChatMsg)localChatInfoItem.mSubObject).mUniqueID == paramLong)
        return localChatInfoItem;
    }
  }

  public ArrayList<ChatInfoItem> getChatInfoList(String paramString, boolean paramBoolean)
  {
    if (paramBoolean);
    ArrayList localArrayList;
    for (String str = "GID=" + paramString; ; str = "UID=" + paramString)
    {
      localArrayList = (ArrayList)this.mListMap.get(str);
      if ((localArrayList == null) || (localArrayList.size() <= 500))
        break;
      return (ArrayList)localArrayList.subList(-500 + localArrayList.size(), -1 + localArrayList.size());
    }
    return localArrayList;
  }

  public ArrayList<CircleModel.CircleItem> getCircles()
  {
    return CircleModel.getInstance().getCircles();
  }

  public String getCmidString(String paramString, boolean paramBoolean)
  {
    ArrayList localArrayList = getChatInfoList(paramString, paramBoolean);
    int i = getUnreadNum(paramString, paramBoolean);
    StringBuffer localStringBuffer = new StringBuffer();
    int j;
    if (localArrayList != null)
    {
      j = localArrayList.size();
      if (j < i);
    }
    for (int k = j - i; ; k++)
    {
      if (k >= j - 1)
      {
        localStringBuffer.append(((ChatInfoItem.ChatMsg)((ChatInfoItem)localArrayList.get(j - 1)).mSubObject).mMsgID);
        return localStringBuffer.toString();
      }
      localStringBuffer.append(((ChatInfoItem.ChatMsg)((ChatInfoItem)localArrayList.get(k)).mSubObject).mMsgID).append(",");
    }
  }

  public int getConversitionUnreadNum()
  {
    return this.mConversitionList.unreadNumAll();
  }

  public String getCookie()
  {
    return this.mCookie;
  }

  public String getCookieKey()
  {
    return this.mCookieKey;
  }

  public String getCookieValue()
  {
    return this.mCookieValue;
  }

  public int getImSeq()
  {
    return this.mImSeq;
  }

  public int getMapSize()
  {
    if (this.mListMap == null)
      return 0;
    return this.mListMap.size();
  }

  public ArrayList<FriendsModel.Friend> getOnlineFriends()
  {
    return FriendsModel.getInstance().getOnlines();
  }

  public ConversitionList getSortedConversitions()
  {
    this.mConversitionList.sort();
    return this.mConversitionList;
  }

  public int getUnreadNum(String paramString, boolean paramBoolean)
  {
    if ("CHAT_MSG_TOTAL".equals(paramString))
    {
      KXLog.d("getUnreadNum", "mMsgUnreadAll=" + this.mMsgUnreadAll);
      KXLog.d("getUnreadNum", "mCircleMsgUnreadAll=" + this.mCircleMsgUnreadAll);
      i = this.mMsgUnreadAll + this.mCircleMsgUnreadAll;
    }
    while (true)
    {
      if (i > 999)
        i = 999;
      return i;
      if ("CHAT_MSG_UNREAD_ALL".equals(paramString))
      {
        i = this.mMsgUnreadAll;
        continue;
      }
      if (!"CHAT_CIRCLE_MSG_UNREAD_ALL".equals(paramString))
        break;
      i = this.mCircleMsgUnreadAll;
    }
    String str;
    label145: Integer localInteger;
    if (paramBoolean)
    {
      str = "GID=" + paramString;
      localInteger = (Integer)this.mUnreadNums.get(str);
      if (localInteger != null)
        break label189;
    }
    label189: for (int i = 0; ; i = localInteger.intValue())
    {
      break;
      str = "UID=" + paramString;
      break label145;
    }
  }

  public boolean hasCookie()
  {
    return !TextUtils.isEmpty(this.mChatHost);
  }

  public boolean hasInit()
  {
    return !TextUtils.isEmpty(this.mChatID);
  }

  public boolean isCircleBlocked(String paramString)
  {
    return this.mBlockedCircles.contains(paramString);
  }

  public void outputChatMsg(String paramString, boolean paramBoolean)
  {
    Iterator localIterator = getChatInfoList(paramString, paramBoolean).iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      KXLog.d("TEST", ((ChatInfoItem)localIterator.next()).mSubObject.toString());
    }
  }

  public void refreshOnlines(ArrayList<ChatInfoItem.UserStatusChanged> paramArrayList)
  {
    FriendsModel localFriendsModel = FriendsModel.getInstance();
    Iterator localIterator;
    if (paramArrayList != null)
      localIterator = paramArrayList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      ChatInfoItem.UserStatusChanged localUserStatusChanged = (ChatInfoItem.UserStatusChanged)localIterator.next();
      if (localUserStatusChanged.mIsOnline)
      {
        localFriendsModel.addOnline(localUserStatusChanged.mUID);
        continue;
      }
      localFriendsModel.removeOnline(localUserStatusChanged.mUID);
    }
  }

  public void removeConversition(String paramString, boolean paramBoolean)
  {
    this.mConversitionList.removeIfExist(paramString, paramBoolean);
  }

  public void sendNotifyByGroup()
  {
    Set localSet = this.mNotifyList.keySet();
    Iterator localIterator;
    if (localSet != null)
    {
      KXLog.d("TEST", "keys.size:" + localSet.size());
      localIterator = localSet.iterator();
    }
    while (true)
    {
      if (!localIterator.hasNext())
      {
        this.mNotifyList.clear();
        return;
      }
      String str = (String)localIterator.next();
      Message localMessage = Message.obtain();
      localMessage.what = 90003;
      localMessage.obj = str;
      MessageHandlerHolder.getInstance().fireMessage(localMessage);
    }
  }

  public void sendNotifyOnce(Context paramContext)
  {
    Message localMessage = Message.obtain();
    localMessage.what = 90003;
    MessageHandlerHolder.getInstance().fireMessage(localMessage);
    if ((this.mMsgUnreadAll + this.mCircleMsgUnreadAll - this.mBlockedCircleUnreadAll > 0) && (RefreshNewMessageService.isNormalMode()))
      KXEngine.sendNewChatNotification(paramContext, this.mMsgUnreadAll + this.mCircleMsgUnreadAll);
  }

  public void sendSyncMessage(String paramString, boolean paramBoolean)
  {
    Message localMessage = Message.obtain();
    localMessage.what = 90009;
    if (paramBoolean);
    for (int i = 1; ; i = 0)
    {
      localMessage.arg1 = i;
      localMessage.obj = paramString;
      MessageHandlerHolder.getInstance().fireMessage(localMessage);
      return;
    }
  }

  public void sendUnreadsChangedMessage(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    int i = 1;
    Message localMessage = Message.obtain();
    localMessage.what = 90005;
    int j;
    if (paramBoolean1)
    {
      j = i;
      localMessage.arg1 = j;
      if (!paramBoolean2)
        break label63;
    }
    while (true)
    {
      localMessage.arg2 = i;
      localMessage.obj = paramString;
      MessageHandlerHolder.getInstance().fireMessage(localMessage);
      return;
      j = 0;
      break;
      label63: i = 0;
    }
  }

  public void setBlockedCircleManager(BlockedCircleManagerInterface paramBlockedCircleManagerInterface)
  {
    this.blockedCircleManager = paramBlockedCircleManagerInterface;
    initBlockedCircles();
  }

  public void setChatHost(String paramString)
  {
    this.mChatHost = paramString;
  }

  public void setChatID(String paramString)
  {
    this.mChatID = paramString;
  }

  public void setCookieKey(String paramString)
  {
    this.mCookieKey = paramString;
    makeCookieString();
  }

  public void setCookieValue(String paramString)
  {
    this.mCookieValue = paramString;
    makeCookieString();
  }

  public void setImSeq(int paramInt)
  {
    this.mImSeq = paramInt;
  }

  public void setImSeqByAdd(int paramInt)
  {
    this.mImSeq = (paramInt + this.mImSeq);
  }

  public static class Buddy extends FriendsModel.Friend
  {
    public Buddy()
    {
      super();
    }

    public Buddy(FriendsModel.Friend paramFriend)
    {
      super(paramFriend);
    }

    public static Buddy valueOf(JSONObject paramJSONObject)
    {
      if (paramJSONObject != null)
      {
        Buddy localBuddy = new Buddy();
        localBuddy.setFuid(paramJSONObject.optString("uid"));
        localBuddy.setFname(paramJSONObject.optString("real_name"));
        localBuddy.setFlogo(paramJSONObject.optString("logo20"));
        localBuddy.setOnline(paramJSONObject.optString("online"));
        localBuddy.setState(paramJSONObject.optString("state"));
        if (!TextUtils.isEmpty(localBuddy.getFuid()))
          return localBuddy;
      }
      return null;
    }

    public boolean equals(Object paramObject)
    {
      if (this == paramObject);
      FriendsModel.Friend localFriend;
      do
        while (true)
        {
          return true;
          if (paramObject == null)
            return false;
          if (!(paramObject instanceof FriendsModel.Friend))
            return false;
          localFriend = (FriendsModel.Friend)paramObject;
          if (this.fuid != null)
            break;
          if (localFriend.fuid != null)
            return false;
        }
      while (this.fuid.equals(localFriend.fuid));
      return false;
    }

    public int hashCode()
    {
      if (this.fuid == null);
      for (int i = 0; ; i = this.fuid.hashCode())
        return i + 31;
    }
  }

  public static class BuddyList extends ArrayList<ChatModel.Buddy>
  {
    private static final long serialVersionUID = 8904153678504123778L;

    public static ArrayList<FriendsModel.Friend> getSortedList(BuddyList paramBuddyList, ArrayList<FriendsModel.Friend> paramArrayList)
    {
      ArrayList localArrayList = new ArrayList(paramArrayList);
      int i = -1 + paramBuddyList.size();
      if (i < 0)
        return localArrayList;
      for (int j = 0; ; j++)
      {
        if (j >= localArrayList.size())
        {
          i--;
          break;
        }
        ChatModel.Buddy localBuddy = (ChatModel.Buddy)paramBuddyList.get(i);
        if (!localBuddy.fuid.equals(((FriendsModel.Friend)localArrayList.get(j)).fuid))
          continue;
        localArrayList.remove(j);
        localArrayList.add(0, localBuddy);
      }
    }

    private void outputTest()
    {
      Iterator localIterator = iterator();
      while (true)
      {
        if (!localIterator.hasNext())
          return;
        ChatModel.Buddy localBuddy = (ChatModel.Buddy)localIterator.next();
        KXLog.d("TEST", "buddy:" + localBuddy.fname);
      }
    }

    public void addOnline(String paramString)
    {
      FriendsModel.Friend localFriend;
      if (!isExist(paramString))
      {
        localFriend = FriendsModel.getInstance().searchAllFriends(paramString);
        if (localFriend != null)
        {
          ChatModel.Buddy localBuddy = search(paramString);
          if (localBuddy == null)
            break label37;
          localBuddy.setOnline("1");
        }
      }
      return;
      label37: add((ChatModel.Buddy)localFriend);
    }

    public boolean isExist(FriendsModel.Friend paramFriend)
    {
      return super.indexOf(paramFriend) > -1;
    }

    public boolean isExist(String paramString)
    {
      Iterator localIterator = iterator();
      do
        if (!localIterator.hasNext())
          return false;
      while (!((ChatModel.Buddy)localIterator.next()).getFuid().equals(paramString));
      return true;
    }

    public void removeOnline(String paramString)
    {
      Iterator localIterator = iterator();
      ChatModel.Buddy localBuddy;
      do
      {
        if (!localIterator.hasNext())
          return;
        localBuddy = (ChatModel.Buddy)localIterator.next();
      }
      while (!localBuddy.getFuid().equals(paramString));
      localBuddy.setOnline("0");
    }

    public ChatModel.Buddy search(String paramString)
    {
      Iterator localIterator = iterator();
      ChatModel.Buddy localBuddy;
      do
      {
        if (!localIterator.hasNext())
          return null;
        localBuddy = (ChatModel.Buddy)localIterator.next();
      }
      while (!localBuddy.getFuid().equals(paramString));
      return localBuddy;
    }
  }

  public static class ChatCircleItem extends CircleModel.CircleItem
  {
    public static ChatCircleItem valueOf(JSONObject paramJSONObject)
    {
      if (paramJSONObject != null)
      {
        ChatCircleItem localChatCircleItem = new ChatCircleItem();
        localChatCircleItem.gid = paramJSONObject.optString("gid");
        localChatCircleItem.gname = paramJSONObject.optString("name");
        localChatCircleItem.type = paramJSONObject.optInt("logo");
        localChatCircleItem.mtime = paramJSONObject.optString("mtime");
        if (!TextUtils.isEmpty(localChatCircleItem.gid))
          return localChatCircleItem;
      }
      return null;
    }
  }

  public static class ChatCircleList extends ArrayList<ChatModel.ChatCircleItem>
  {
    private static final long serialVersionUID = -2445409838833806925L;

    public static ArrayList<CircleModel.CircleItem> getSortedList(ChatCircleList paramChatCircleList, ArrayList<CircleModel.CircleItem> paramArrayList)
    {
      ArrayList localArrayList = new ArrayList(paramArrayList);
      int i = -1 + paramChatCircleList.size();
      if (i < 0)
        return localArrayList;
      for (int j = 0; ; j++)
      {
        if (j >= localArrayList.size())
        {
          i--;
          break;
        }
        ChatModel.ChatCircleItem localChatCircleItem = (ChatModel.ChatCircleItem)paramChatCircleList.get(i);
        if (!localChatCircleItem.gid.equals(((CircleModel.CircleItem)localArrayList.get(j)).gid))
          continue;
        localArrayList.remove(j);
        localArrayList.add(0, localChatCircleItem);
      }
    }

    public boolean isExist(CircleModel.CircleItem paramCircleItem)
    {
      return super.indexOf(paramCircleItem) > -1;
    }

    public ChatModel.ChatCircleItem search(String paramString)
    {
      Iterator localIterator = iterator();
      ChatModel.ChatCircleItem localChatCircleItem;
      do
      {
        if (!localIterator.hasNext())
          return null;
        localChatCircleItem = (ChatModel.ChatCircleItem)localIterator.next();
      }
      while (!localChatCircleItem.gid.equals(paramString));
      return localChatCircleItem;
    }
  }

  public static class ChatHistoryList extends ArrayList<ChatInfoItem>
  {
    private int mTotal = 0;

    public void clear()
    {
      super.clear();
      this.mTotal = 0;
    }

    public int getTotal()
    {
      return Math.max(this.mTotal, super.size());
    }

    public void setTotal(int paramInt)
    {
      if (paramInt >= 0)
        this.mTotal = paramInt;
    }
  }

  public class Conversition
    implements Comparable<Conversition>
  {
    public boolean isCircle = false;
    public String isOnline;
    public long mActiveTime;
    public int mCircleType = 0;
    public String mID = "";
    public String mLogo;
    public String mName;

    public Conversition()
    {
    }

    private ChatModel getOuterType()
    {
      return ChatModel.this;
    }

    public int compareTo(Conversition paramConversition)
    {
      if (paramConversition == null)
        return 0;
      return (int)(paramConversition.mActiveTime - this.mActiveTime);
    }

    public void copyFrom(CircleModel.CircleItem paramCircleItem)
    {
      this.mActiveTime = System.currentTimeMillis();
      this.mID = paramCircleItem.gid;
      this.mName = paramCircleItem.gname;
      this.isOnline = "1";
      this.isCircle = true;
      this.mCircleType = paramCircleItem.type;
    }

    public void copyFrom(FriendsModel.Friend paramFriend)
    {
      this.mActiveTime = System.currentTimeMillis();
      this.mID = paramFriend.getFuid();
      this.mName = paramFriend.getFname();
      this.mLogo = paramFriend.getFlogo();
      this.isOnline = paramFriend.getOnline();
      this.isCircle = false;
    }

    public boolean equals(Object paramObject)
    {
      if (this == paramObject);
      Conversition localConversition;
      do
        while (true)
        {
          return true;
          if (paramObject == null)
            return false;
          if (getClass() != paramObject.getClass())
            return false;
          localConversition = (Conversition)paramObject;
          if (!getOuterType().equals(localConversition.getOuterType()))
            return false;
          if (this.isCircle != localConversition.isCircle)
            return false;
          if (this.mID != null)
            break;
          if (localConversition.mID != null)
            return false;
        }
      while (this.mID.equals(localConversition.mID));
      return false;
    }

    public int hashCode()
    {
      int i = 31 * (31 + getOuterType().hashCode());
      int j;
      int k;
      if (this.isCircle)
      {
        j = 1231;
        k = 31 * (i + j);
        if (this.mID != null)
          break label54;
      }
      label54: for (int m = 0; ; m = this.mID.hashCode())
      {
        return k + m;
        j = 1237;
        break;
      }
    }

    public String toString()
    {
      return this.mName + ":" + this.mActiveTime;
    }

    public Integer unreadNum()
    {
      return Integer.valueOf(ChatModel.this.getUnreadNum(this.mID, this.isCircle));
    }

    public void updateActiveTime(Conversition paramConversition)
    {
      this.mActiveTime = paramConversition.mActiveTime;
    }
  }

  public static class ConversitionList extends ArrayList<ChatModel.Conversition>
  {
    private static final long serialVersionUID = 4225227031943936668L;

    private void outputConversition()
    {
      Iterator localIterator = iterator();
      while (true)
      {
        if (!localIterator.hasNext())
          return;
        ChatModel.Conversition localConversition = (ChatModel.Conversition)localIterator.next();
        if (localConversition == null)
          continue;
        KXLog.d("TEST", "outputConversition:" + localConversition.toString());
      }
    }

    public void addOrUpdate(ChatModel.Conversition paramConversition)
    {
      Iterator localIterator = iterator();
      boolean bool = localIterator.hasNext();
      int i = 0;
      if (!bool);
      while (true)
      {
        if ((i == 0) && (((paramConversition.unreadNum().intValue() == 0) && (getReadConversitionNum() < 50)) || ((paramConversition.unreadNum().intValue() > 0) && (getUnreadConversitionNum() < 200))))
          add(paramConversition);
        return;
        ChatModel.Conversition localConversition = (ChatModel.Conversition)localIterator.next();
        if ((localConversition == null) || (!localConversition.equals(paramConversition)))
          break;
        localConversition.updateActiveTime(paramConversition);
        i = 1;
      }
    }

    public int getReadConversitionNum()
    {
      int i = 0;
      Iterator localIterator = iterator();
      while (true)
      {
        if (!localIterator.hasNext())
          return i;
        ChatModel.Conversition localConversition = (ChatModel.Conversition)localIterator.next();
        if ((localConversition == null) || (localConversition.unreadNum().intValue() != 0))
          continue;
        i++;
      }
    }

    public int getUnreadConversitionNum()
    {
      int i = 0;
      Iterator localIterator = iterator();
      while (true)
      {
        if (!localIterator.hasNext())
          return i;
        ChatModel.Conversition localConversition = (ChatModel.Conversition)localIterator.next();
        if ((localConversition == null) || (localConversition.unreadNum().intValue() <= 0))
          continue;
        i++;
      }
    }

    public void removeIfExist(ChatModel.Conversition paramConversition)
    {
      Iterator localIterator = iterator();
      ChatModel.Conversition localConversition;
      do
      {
        if (!localIterator.hasNext())
          return;
        localConversition = (ChatModel.Conversition)localIterator.next();
      }
      while ((localConversition == null) || (!localConversition.equals(paramConversition)));
      remove(localConversition);
    }

    public void removeIfExist(String paramString, boolean paramBoolean)
    {
      Iterator localIterator = iterator();
      ChatModel.Conversition localConversition;
      do
      {
        if (!localIterator.hasNext())
          return;
        localConversition = (ChatModel.Conversition)localIterator.next();
      }
      while ((localConversition == null) || (!localConversition.mID.equals(paramString)) || (localConversition.isCircle != paramBoolean));
      remove(localConversition);
    }

    public void sort()
    {
      Collections.sort(this);
    }

    public int unreadNumAll()
    {
      int i = 0;
      Iterator localIterator = iterator();
      while (true)
      {
        if (!localIterator.hasNext())
        {
          if (i > 999)
            i = 999;
          return i;
        }
        ChatModel.Conversition localConversition = (ChatModel.Conversition)localIterator.next();
        if (localConversition == null)
          continue;
        i += localConversition.unreadNum().intValue();
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.ChatModel
 * JD-Core Version:    0.6.0
 */