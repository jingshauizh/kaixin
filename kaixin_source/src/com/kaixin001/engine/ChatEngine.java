package com.kaixin001.engine;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import com.kaixin001.activity.MessageHandlerHolder;
import com.kaixin001.item.ChatInfoItem;
import com.kaixin001.item.ChatInfoItem.ChatMsg;
import com.kaixin001.item.ChatInfoItem.CircleMemberChanged;
import com.kaixin001.item.ChatInfoItem.NewMessage;
import com.kaixin001.item.ChatInfoItem.UserStatusChanged;
import com.kaixin001.item.ChatInfoItem.UserTyping;
import com.kaixin001.item.CommentItem;
import com.kaixin001.item.MessageItem;
import com.kaixin001.item.UserCommentItem;
import com.kaixin001.model.ChatModel;
import com.kaixin001.model.ChatModel.Buddy;
import com.kaixin001.model.ChatModel.ChatCircleItem;
import com.kaixin001.model.MessageCenterModel;
import com.kaixin001.model.MessageNotifyModel;
import com.kaixin001.model.MessageNotifyModel.MessageNotifyItem;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.network.HttpMethod;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.ActivityUtil;
import com.kaixin001.util.KXLog;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatEngine extends KXEngine
{
  private static final int BUDDY_VALUE = 1;
  public static final int ERROR = 2;
  public static final int FAILED = 0;
  private static final int GET_MSG_VALUE = 1;
  private static final int INIT_VALUE = 1;
  public static final int NUM = 20;
  public static final int SUCCEED = 1;
  private static final String TAG = "ChatEngine";
  private static final String TAG_INFORM = "inform";
  private static final String TAG_REFRESH = "refresh";
  private static ChatEngine sInstance = null;
  ReentrantLock threadLock = new ReentrantLock();

  private int chatInit(Context paramContext, String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    if (ChatModel.getInstance().hasInit())
      return 1;
    String str1 = Protocol.getInstance().makeChatInitRequest(paramString, paramInt1, paramInt2, paramInt3);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return 0;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("ChatEngine", "chatInit error", localException);
        str2 = null;
      }
    }
    return parseChatInit(paramContext, str2);
  }

  private void checkBuddyList(JSONObject paramJSONObject)
  {
    JSONArray localJSONArray = paramJSONObject.optJSONArray("buddylist");
    int i;
    if (localJSONArray != null)
      i = localJSONArray.length();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return;
      JSONObject localJSONObject = localJSONArray.optJSONObject(j);
      ChatModel.getInstance().addBuddy(ChatModel.Buddy.valueOf(localJSONObject));
    }
  }

  private void checkCircleOfflineMessageContent(JSONObject paramJSONObject, boolean paramBoolean1, boolean paramBoolean2)
    throws JSONException
  {
    if ((!paramBoolean1) && (!paramBoolean2));
    JSONObject localJSONObject1;
    JSONArray localJSONArray1;
    do
    {
      do
      {
        return;
        localJSONObject1 = paramJSONObject.optJSONObject("groups");
      }
      while (localJSONObject1 == null);
      localJSONArray1 = localJSONObject1.names();
    }
    while (localJSONArray1 == null);
    int i = localJSONArray1.length();
    int j = 0;
    label44: String str2;
    JSONArray localJSONArray2;
    JSONObject localJSONObject4;
    if (j < i)
    {
      String str1 = localJSONArray1.optString(j);
      JSONObject localJSONObject2 = localJSONObject1.optJSONObject(str1);
      str2 = str1.replace("G", "");
      if (localJSONObject2 != null)
      {
        if (paramBoolean2)
        {
          JSONObject localJSONObject7 = localJSONObject2.optJSONObject("ginfo");
          ChatModel.getInstance().addCircle(ChatModel.ChatCircleItem.valueOf(localJSONObject7));
        }
        if (paramBoolean1)
        {
          JSONObject localJSONObject3 = localJSONObject2.optJSONObject("context");
          if (localJSONObject3 != null)
          {
            localJSONArray2 = localJSONObject3.optJSONArray("msgs");
            localJSONObject4 = localJSONObject3.optJSONObject("users");
            if (localJSONArray2 == null);
          }
        }
      }
    }
    for (int k = -1 + localJSONArray2.length(); ; k--)
    {
      if (k < 0)
      {
        j++;
        break label44;
        break;
      }
      JSONObject localJSONObject5 = localJSONArray2.getJSONObject(k);
      if (localJSONObject5 == null)
        continue;
      String str3 = localJSONObject5.optString("uid");
      String str4 = localJSONObject5.optString("cmid");
      String str5 = localJSONObject5.optString("ctime");
      String str6 = getContent(localJSONObject5);
      Boolean localBoolean = Boolean.valueOf(User.getInstance().getUID().equals(str3));
      String str7 = null;
      String str8 = null;
      if (localJSONObject4 != null)
      {
        JSONObject localJSONObject6 = localJSONObject4.optJSONObject(str3);
        str7 = null;
        str8 = null;
        if (localJSONObject6 != null)
        {
          str8 = localJSONObject6.optString("name");
          str7 = localJSONObject6.optString("icon20");
        }
      }
      ChatInfoItem localChatInfoItem = ChatInfoItem.createChatMsg("", str6, str4, str5, "", str3, str2, true, localBoolean.booleanValue());
      ChatInfoItem.ChatMsg localChatMsg = (ChatInfoItem.ChatMsg)localChatInfoItem.mSubObject;
      localChatMsg.mRealName = str8;
      localChatMsg.mLogo = str7;
      ChatModel.getInstance().addCircleOfflineChatInfo(localChatInfoItem);
    }
  }

  private boolean checkCircleOfflineMessageNum(Context paramContext, JSONObject paramJSONObject, boolean paramBoolean)
    throws JSONException
  {
    int i = 0;
    ArrayList localArrayList = new ArrayList();
    JSONObject localJSONObject1 = paramJSONObject.optJSONObject("gunreads");
    JSONArray localJSONArray;
    int j;
    if (localJSONObject1 != null)
    {
      localJSONArray = localJSONObject1.names();
      if (localJSONArray != null)
        j = localJSONArray.length();
    }
    for (int k = 0; ; k++)
    {
      if (k >= j)
      {
        if ((paramBoolean) && (localArrayList.size() > 0))
        {
          Message localMessage = Message.obtain();
          localMessage.what = 90010;
          localMessage.obj = localArrayList;
          MessageHandlerHolder.getInstance().fireMessage(localMessage);
        }
        if (localArrayList.size() <= 0)
          break;
        return true;
      }
      String str1 = localJSONArray.getString(k);
      JSONObject localJSONObject2 = localJSONObject1.optJSONObject(str1);
      if (localJSONObject2 == null)
        continue;
      int m = localJSONObject2.optInt("chat", 0);
      i += localJSONObject2.optInt("talk");
      KXLog.d("TEST", "unread:" + m);
      if (m <= 0)
        continue;
      String str2 = str1.replace("G", "");
      localArrayList.add(str2);
      ChatModel.getInstance().addCircleUnreadMsgNum(str2, m);
    }
    return false;
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
          break label210;
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
    label210: return localJSONObject2.optString("content", "");
  }

  private int getCookie(Context paramContext, String paramString)
  {
    if (ChatModel.getInstance().hasCookie())
    {
      KXLog.d("ChatModel.getInstance().hasCookie() is ", ChatModel.getInstance().hasCookie());
      return 1;
    }
    if (1 != chatInit(paramContext, paramString, 1, 1, 1))
      return 0;
    String str1 = Protocol.getInstance().makeGetChatCookieRequest(paramString);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return 0;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("ChatEngine", "getCookie error", localException);
        str2 = null;
      }
      try
      {
        JSONObject localJSONObject = super.parseJSON(paramContext, str2);
        if ((localJSONObject != null) && (localJSONObject.optInt("ret", 0) == 1))
        {
          ChatModel localChatModel = ChatModel.getInstance();
          localChatModel.setChatHost(localJSONObject.optString("host"));
          localChatModel.setCookieKey(localJSONObject.optString("ckey"));
          localChatModel.setCookieValue(localJSONObject.optString("cookie"));
          if (shouldPushMsg(paramContext))
          {
            boolean bool = ActivityUtil.checkKXActivityExist(paramContext);
            KXLog.d("TEST", "parseNewChatMessages(): isAnyKaixinActivityExist = " + bool);
            if (!bool)
            {
              MessageCenterModel localMessageCenterModel = MessageCenterModel.getInstance();
              new GetNewMessagesThread(paramContext, localMessageCenterModel.getNoticeCnt(1), localMessageCenterModel.getNoticeCnt(3), localMessageCenterModel.getNoticeCnt(6), localMessageCenterModel.getNoticeCnt(2)).start();
            }
          }
          return 1;
        }
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        localSecurityErrorException.printStackTrace();
        return 2;
      }
    }
    return 0;
  }

  public static ChatEngine getInstance()
  {
    monitorenter;
    try
    {
      if (sInstance == null)
        sInstance = new ChatEngine();
      ChatEngine localChatEngine = sInstance;
      return localChatEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private boolean getSeq(Context paramContext, String paramString, int paramInt)
  {
    ChatModel localChatModel = ChatModel.getInstance();
    String str1 = Protocol.getInstance().makeGetChatCtxRequest(localChatModel.getChatHost(), paramString, paramInt);
    KXLog.d("url", "url=" + str1);
    Header[] arrayOfHeader = new Header[1];
    if (localChatModel.getCookie() != null)
      arrayOfHeader[0] = new BasicHeader("Cookie", localChatModel.getCookie());
    while (true)
    {
      HttpConnection localHttpConnection = new HttpConnection(paramContext);
      try
      {
        String str3 = localHttpConnection.httpRequest(str1, HttpMethod.GET, null, arrayOfHeader, null, null);
        str2 = str3;
        if (TextUtils.isEmpty(str2))
        {
          return false;
          arrayOfHeader = null;
        }
      }
      catch (Exception localException1)
      {
        String str2;
        while (true)
        {
          KXLog.e("ChatEngine", "getSeq error", localException1);
          str2 = null;
        }
        KXLog.d("strContent", "strContent=" + str2);
        try
        {
          JSONObject localJSONObject = super.parseJSON(paramContext, str2);
          if ((localJSONObject == null) || (!localJSONObject.getString("t").equals("ctx")))
            break;
          int i = localJSONObject.optInt("seq");
          KXLog.d("wangdan", "seq=" + i);
          localChatModel.setImSeq(i);
          return true;
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
          return false;
        }
      }
    }
    return false;
  }

  private int parseChatInit(Context paramContext, String paramString)
  {
    boolean bool1;
    int i;
    int j;
    label116: int k;
    try
    {
      JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
      if (localJSONObject1 != null)
      {
        ChatModel localChatModel = ChatModel.getInstance();
        String str1 = localJSONObject1.optString("chatid");
        localChatModel.setChatID(str1);
        if (1 == 0)
          break label404;
        bool1 = false;
        boolean bool2 = checkCircleOfflineMessageNum(paramContext, localJSONObject1, bool1);
        checkCircleOfflineMessageContent(localJSONObject1, true, false);
        JSONObject localJSONObject2 = localJSONObject1.optJSONObject("messages");
        JSONObject localJSONObject3;
        JSONArray localJSONArray1;
        if (localJSONObject2 != null)
        {
          localJSONObject3 = localJSONObject2.optJSONObject("fmsgs");
          if (localJSONObject3 != null)
          {
            localJSONArray1 = localJSONObject3.names();
            if (localJSONArray1 != null)
            {
              i = localJSONArray1.length();
              j = 0;
              break label392;
            }
          }
        }
        if (!bool2)
          break label402;
        localChatModel.sendNotifyOnce(paramContext);
        break label402;
        label130: JSONObject localJSONObject4 = localJSONObject3.optJSONObject(localJSONArray1.optString(j));
        if (localJSONObject4 == null)
          break label415;
        String str2 = localJSONObject4.optString("otheruid");
        String str3 = localJSONObject4.optString("real_name");
        String str4 = localJSONObject4.optString("logo20");
        JSONArray localJSONArray2 = localJSONObject4.optJSONArray("msgs");
        if ((localJSONArray2 == null) || (TextUtils.isEmpty(str2)))
          break label415;
        k = -1 + localJSONArray2.length();
        break label410;
        label213: JSONObject localJSONObject5 = localJSONArray2.optJSONObject(k);
        String str5 = localJSONObject5.optString("cmid");
        ChatInfoItem localChatInfoItem = ChatInfoItem.createChatMsg(str1, getContent(localJSONObject5), str5, localJSONObject5.optString("ctime"), User.getInstance().getUID(), str2, "", false, false);
        ChatInfoItem.ChatMsg localChatMsg = (ChatInfoItem.ChatMsg)localChatInfoItem.mSubObject;
        localChatMsg.mRealName = str3;
        localChatMsg.mLogo = str4;
        KXLog.d("wangdan", "otherUID=" + str2);
        KXLog.d("wangdan", "chatID=" + str1);
        if (!localChatInfoItem.isFeedbackChatMsg())
        {
          KXLog.d("wangdan", "aa");
          localChatModel.addChatInfo(localChatInfoItem, localChatInfoItem.isSendChatMsg());
          bool2 = true;
        }
        k--;
      }
    }
    catch (SecurityErrorException localSecurityErrorException)
    {
      localSecurityErrorException.printStackTrace();
      return 2;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
    while (true)
    {
      label392: if (j < i)
        break label130;
      break label116;
      label402: return 1;
      label404: bool1 = true;
      break;
      label410: if (k >= 0)
        break label213;
      label415: j++;
    }
  }

  private boolean shouldPushMsg(Context paramContext)
  {
    return false;
  }

  public int chatSendMsg(Context paramContext, String paramString1, String paramString2, int paramInt)
  {
    String str1 = User.getInstance().getUID();
    String str2;
    if (paramInt == 8)
      str2 = Protocol.getInstance().makeChatSendGroupMsgRequest(str1, paramString2, paramString1);
    while (true)
    {
      HttpProxy localHttpProxy = new HttpProxy(paramContext);
      try
      {
        String str4 = localHttpProxy.httpGet(str2, null, null);
        str3 = str4;
        label52: if (str3 == null);
      }
      catch (Exception localException1)
      {
        try
        {
          String str3;
          super.parseJSON(paramContext, str3);
          while (true)
          {
            return getRet();
            if (paramInt == 9)
            {
              str2 = Protocol.getInstance().makeChatSendTypingGroupMsg(str1, paramString2);
              break;
            }
            if (paramInt == 6)
            {
              str2 = Protocol.getInstance().makeChatSendMsgRequest(str1, paramString2, paramString1);
              break;
            }
            str2 = null;
            if (paramInt != 7)
              break;
            str2 = Protocol.getInstance().makeChatSendTypingRequest(str1, paramString2);
            break;
            localException1 = localException1;
            KXLog.e("ChatEngine", "chatSendMsg error", localException1);
            str3 = null;
            break label52;
            this.ret = -1;
          }
        }
        catch (Exception localException2)
        {
          while (true)
            localException2.printStackTrace();
        }
      }
    }
  }

  // ERROR //
  public int getChatHistory(Context paramContext, String paramString1, String paramString2, int paramInt, String paramString3, com.kaixin001.model.ChatModel.ChatHistoryList paramChatHistoryList)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +21 -> 22
    //   4: aload 6
    //   6: ifnull +16 -> 22
    //   9: aload 5
    //   11: invokestatic 80	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   14: ifne +8 -> 22
    //   17: iload 4
    //   19: ifge +5 -> 24
    //   22: iconst_0
    //   23: ireturn
    //   24: invokestatic 61	com/kaixin001/network/Protocol:getInstance	()Lcom/kaixin001/network/Protocol;
    //   27: aload 5
    //   29: aload_3
    //   30: iload 4
    //   32: invokevirtual 551	com/kaixin001/network/Protocol:makeChatGetHistoryRequest	(Ljava/lang/String;Ljava/lang/String;I)Ljava/lang/String;
    //   35: astore 7
    //   37: new 67	com/kaixin001/network/HttpProxy
    //   40: dup
    //   41: aload_1
    //   42: invokespecial 70	com/kaixin001/network/HttpProxy:<init>	(Landroid/content/Context;)V
    //   45: astore 8
    //   47: aload 8
    //   49: aload 7
    //   51: aconst_null
    //   52: aconst_null
    //   53: invokevirtual 74	com/kaixin001/network/HttpProxy:httpGet	(Ljava/lang/String;Lcom/kaixin001/network/HttpRequestState;Lcom/kaixin001/network/HttpProgressListener;)Ljava/lang/String;
    //   56: astore 27
    //   58: aload 27
    //   60: astore 10
    //   62: aload_0
    //   63: aload_1
    //   64: aload 10
    //   66: invokespecial 380	com/kaixin001/engine/KXEngine:parseJSON	(Landroid/content/Context;Ljava/lang/String;)Lorg/json/JSONObject;
    //   69: astore 13
    //   71: aload 13
    //   73: ifnull +15 -> 88
    //   76: aload_0
    //   77: getfield 546	com/kaixin001/engine/ChatEngine:ret	I
    //   80: istore 14
    //   82: iload 14
    //   84: iconst_1
    //   85: if_icmpeq +23 -> 108
    //   88: iconst_0
    //   89: ireturn
    //   90: astore 9
    //   92: ldc 20
    //   94: ldc_w 553
    //   97: aload 9
    //   99: invokestatic 88	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   102: aconst_null
    //   103: astore 10
    //   105: goto -43 -> 62
    //   108: aload 13
    //   110: ldc_w 555
    //   113: iconst_0
    //   114: invokevirtual 271	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
    //   117: istore 15
    //   119: aload 13
    //   121: ldc_w 557
    //   124: invokevirtual 102	org/json/JSONObject:optJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   127: astore 16
    //   129: aload 16
    //   131: ifnull +11 -> 142
    //   134: aload 16
    //   136: invokevirtual 108	org/json/JSONArray:length	()I
    //   139: ifne +16 -> 155
    //   142: aload 6
    //   144: aload 6
    //   146: invokevirtual 560	com/kaixin001/model/ChatModel$ChatHistoryList:size	()I
    //   149: invokevirtual 563	com/kaixin001/model/ChatModel$ChatHistoryList:setTotal	(I)V
    //   152: goto +176 -> 328
    //   155: aload 6
    //   157: iload 15
    //   159: invokevirtual 563	com/kaixin001/model/ChatModel$ChatHistoryList:setTotal	(I)V
    //   162: aload 16
    //   164: invokevirtual 108	org/json/JSONArray:length	()I
    //   167: istore 17
    //   169: iconst_0
    //   170: istore 18
    //   172: iload 18
    //   174: iload 17
    //   176: if_icmpge +152 -> 328
    //   179: aload 16
    //   181: iload 18
    //   183: invokevirtual 169	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   186: astore 19
    //   188: aload 19
    //   190: ifnonnull +6 -> 196
    //   193: goto +137 -> 330
    //   196: aload_0
    //   197: aload 19
    //   199: invokespecial 182	com/kaixin001/engine/ChatEngine:getContent	(Lorg/json/JSONObject;)Ljava/lang/String;
    //   202: astore 20
    //   204: aload 19
    //   206: ldc_w 565
    //   209: ldc 143
    //   211: invokevirtual 312	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   214: astore 21
    //   216: aload 19
    //   218: ldc 178
    //   220: lconst_0
    //   221: invokevirtual 569	org/json/JSONObject:optLong	(Ljava/lang/String;J)J
    //   224: lstore 22
    //   226: aload 19
    //   228: ldc 176
    //   230: ldc 143
    //   232: invokevirtual 312	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   235: astore 24
    //   237: aload_2
    //   238: aload 21
    //   240: invokevirtual 195	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   243: ifeq +43 -> 286
    //   246: ldc 143
    //   248: aload 20
    //   250: aload 24
    //   252: lload 22
    //   254: aload 5
    //   256: aload_2
    //   257: ldc 143
    //   259: iconst_0
    //   260: iconst_1
    //   261: invokestatic 572	com/kaixin001/item/ChatInfoItem:createChatMsg	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZ)Lcom/kaixin001/item/ChatInfoItem;
    //   264: astore 26
    //   266: aload 6
    //   268: iconst_0
    //   269: aload 26
    //   271: invokevirtual 575	com/kaixin001/model/ChatModel$ChatHistoryList:add	(ILjava/lang/Object;)V
    //   274: goto +56 -> 330
    //   277: astore 12
    //   279: aload 12
    //   281: invokevirtual 426	com/kaixin001/engine/SecurityErrorException:printStackTrace	()V
    //   284: iconst_0
    //   285: ireturn
    //   286: ldc 143
    //   288: aload 20
    //   290: aload 24
    //   292: lload 22
    //   294: aload_2
    //   295: aload 21
    //   297: ldc 143
    //   299: iconst_0
    //   300: iconst_0
    //   301: invokestatic 572	com/kaixin001/item/ChatInfoItem:createChatMsg	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZ)Lcom/kaixin001/item/ChatInfoItem;
    //   304: astore 25
    //   306: aload 25
    //   308: astore 26
    //   310: goto -44 -> 266
    //   313: astore 11
    //   315: ldc 20
    //   317: ldc_w 576
    //   320: aload 11
    //   322: invokestatic 88	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   325: goto -41 -> 284
    //   328: iconst_1
    //   329: ireturn
    //   330: iinc 18 1
    //   333: goto -161 -> 172
    //
    // Exception table:
    //   from	to	target	type
    //   47	58	90	java/lang/Exception
    //   62	71	277	com/kaixin001/engine/SecurityErrorException
    //   76	82	277	com/kaixin001/engine/SecurityErrorException
    //   108	129	277	com/kaixin001/engine/SecurityErrorException
    //   134	142	277	com/kaixin001/engine/SecurityErrorException
    //   142	152	277	com/kaixin001/engine/SecurityErrorException
    //   155	169	277	com/kaixin001/engine/SecurityErrorException
    //   179	188	277	com/kaixin001/engine/SecurityErrorException
    //   196	266	277	com/kaixin001/engine/SecurityErrorException
    //   266	274	277	com/kaixin001/engine/SecurityErrorException
    //   286	306	277	com/kaixin001/engine/SecurityErrorException
    //   62	71	313	java/lang/Exception
    //   76	82	313	java/lang/Exception
    //   108	129	313	java/lang/Exception
    //   134	142	313	java/lang/Exception
    //   142	152	313	java/lang/Exception
    //   155	169	313	java/lang/Exception
    //   179	188	313	java/lang/Exception
    //   196	266	313	java/lang/Exception
    //   266	274	313	java/lang/Exception
    //   286	306	313	java/lang/Exception
  }

  public int getCircleContext(Context paramContext, String paramString1, String paramString2)
  {
    if ((paramContext == null) || (paramString1 == null) || (TextUtils.isEmpty(paramString2)))
      return 0;
    String str1 = Protocol.getInstance().makeGetChatContext(paramString1, paramString2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    int i;
    int j;
    do
      try
      {
        String str9 = localHttpProxy.httpGet(str1, null, null);
        str2 = str9;
        if (TextUtils.isEmpty(str2))
          return 0;
      }
      catch (Exception localException1)
      {
        String str2;
        while (true)
        {
          KXLog.e("ChatEngine", "getCircleContext error", localException1);
          str2 = null;
        }
        try
        {
          JSONObject localJSONObject1 = super.parseJSON(paramContext, str2);
          if ((localJSONObject1 != null) && (this.ret == 1))
          {
            JSONObject localJSONObject2 = localJSONObject1.optJSONObject("context");
            if (localJSONObject2 == null)
              break;
            JSONArray localJSONArray = localJSONObject2.optJSONArray("msgs");
            JSONObject localJSONObject3 = localJSONObject2.optJSONObject("users");
            if (localJSONArray == null)
              break;
            i = localJSONArray.length();
            j = 0;
            continue;
            JSONObject localJSONObject4 = localJSONArray.getJSONObject(j);
            if (localJSONObject4 != null)
            {
              String str3 = localJSONObject4.optString("uid");
              String str4 = localJSONObject4.optString("cmid");
              String str5 = getContent(localJSONObject4);
              String str6 = localJSONObject4.optString("ctime");
              Boolean localBoolean = Boolean.valueOf(User.getInstance().getUID().equals(str3));
              String str7 = null;
              String str8 = null;
              if (localJSONObject3 != null)
              {
                JSONObject localJSONObject5 = localJSONObject3.optJSONObject(str3);
                str7 = null;
                str8 = null;
                if (localJSONObject5 != null)
                {
                  str8 = localJSONObject5.optString("name");
                  str7 = localJSONObject5.optString("icon20");
                }
              }
              ChatInfoItem localChatInfoItem = ChatInfoItem.createChatMsg("", str5, str4, str6, "", str3, paramString2, true, localBoolean.booleanValue());
              ChatInfoItem.ChatMsg localChatMsg = (ChatInfoItem.ChatMsg)localChatInfoItem.mSubObject;
              localChatMsg.mRealName = str8;
              localChatMsg.mLogo = str7;
              KXLog.d("wangdan", "cc");
              ChatModel.getInstance().addChatInfo(localChatInfoItem, localBoolean.booleanValue());
            }
            j++;
          }
        }
        catch (SecurityErrorException localSecurityErrorException)
        {
          localSecurityErrorException.printStackTrace();
          return 0;
        }
        catch (Exception localException2)
        {
          while (true)
            KXLog.e("ChatEngine", "getCircleContext", localException2);
        }
        return 0;
      }
    while (j < i);
    return 1;
  }

  public int getNewChatMessages(Context paramContext)
  {
    String str1 = User.getInstance().getUID();
    if ((TextUtils.isEmpty(str1)) || (getCookie(paramContext, str1) != 1))
      return 0;
    ChatModel localChatModel = ChatModel.getInstance();
    String str2 = localChatModel.getChatHost();
    int i = 12345678 + new Random().nextInt(1000000);
    if ((localChatModel.getImSeq() == -1) || (localChatModel.getCookie() == null) || (TextUtils.isEmpty(localChatModel.getCookie())))
    {
      if (!getSeq(paramContext, str1, i))
        return 0;
      KXLog.d("getNewChatMessages", "model.getImSeq()=" + localChatModel.getImSeq());
    }
    while (true)
    {
      int j = localChatModel.getImSeq();
      int k = 12345678 + new Random().nextInt(1000000);
      String str3 = Protocol.getInstance().makeGetNewChatMessageRequest(str2, str1, k, j);
      KXLog.d("TEST", "imSeq:" + j);
      KXLog.d("TEST", "getNewChatMessages:" + str3);
      Header[] arrayOfHeader = new Header[1];
      HttpConnection localHttpConnection;
      String str4;
      if (localChatModel.getCookie() != null)
      {
        arrayOfHeader[0] = new BasicHeader("Cookie", localChatModel.getCookie());
        localHttpConnection = new HttpConnection(paramContext);
        str4 = null;
      }
      try
      {
        str4 = localHttpConnection.httpRequest(str3, HttpMethod.GET, null, arrayOfHeader, null, null);
        KXLog.d("TEST", "content:" + str4);
        return parseNewChatMessages(paramContext, str4);
        KXLog.d("getNewChatMessages", "model.getImSeq()2=" + localChatModel.getImSeq());
        continue;
        arrayOfHeader = null;
      }
      catch (Exception localException)
      {
        while (true)
        {
          KXLog.e("ChatEngine", "getNewChatMessages error", localException);
          KXLog.d("TEST", "content:" + str4, localException);
        }
      }
    }
  }

  protected int parseNewChatMessages(Context paramContext, String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return 0;
    KXLog.d("HttpConnection", "ChatEngine parseNewChatMessages:" + paramString);
    while (true)
    {
      int k;
      try
      {
        localChatModel = ChatModel.getInstance();
        JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
        if (localJSONObject == null)
          return 0;
        str = localJSONObject.getString("t");
        JSONArray localJSONArray = localJSONObject.optJSONArray("infos");
        KXLog.d("aa", "infos=" + localJSONArray);
        if (!"inform".equals(str))
          continue;
        if ((localJSONArray == null) || (localJSONArray.length() <= 0))
          continue;
        int i = localJSONArray.length();
        localChatModel.setImSeqByAdd(i);
        localArrayList1 = new ArrayList();
        localArrayList2 = new ArrayList();
        localArrayList3 = new ArrayList();
        localArrayList4 = new ArrayList();
        Object localObject = null;
        j = 0;
        k = 0;
        if (k < i)
          continue;
        if (localArrayList3.size() <= 0)
          continue;
        Message localMessage4 = Message.obtain();
        localMessage4.what = 90012;
        localMessage4.obj = localArrayList3;
        MessageHandlerHolder.getInstance().fireMessage(localMessage4);
        if (localArrayList4.size() <= 0)
          continue;
        Message localMessage3 = Message.obtain();
        localMessage3.what = 90011;
        localMessage3.obj = localArrayList4;
        MessageHandlerHolder.getInstance().fireMessage(localMessage3);
        if (localArrayList2.size() <= 0)
          continue;
        Message localMessage2 = Message.obtain();
        localMessage2.what = 90004;
        localMessage2.obj = localArrayList2;
        MessageHandlerHolder.getInstance().fireMessage(localMessage2);
        if (localArrayList1.size() <= 0)
          continue;
        Message localMessage1 = Message.obtain();
        localMessage1.what = 90001;
        localMessage1.obj = localArrayList1;
        MessageHandlerHolder.getInstance().fireMessage(localMessage1);
        if (j == 0)
          continue;
        localChatModel.sendNotifyOnce(paramContext);
        if (localObject == null)
          continue;
        KXLog.d("TEST", "parseNewChatMessages(): have new message!");
        if ((!shouldPushMsg(paramContext)) || (!localObject.isNewMessage()))
          continue;
        boolean bool = ActivityUtil.checkKXActivityExist(paramContext);
        KXLog.d("TEST", "parseNewChatMessages(): isAnyKaixinActivityExist = " + bool);
        if (bool)
          continue;
        ChatInfoItem.NewMessage localNewMessage2 = (ChatInfoItem.NewMessage)localObject.mSubObject;
        new GetNewMessagesThread(paramContext, localNewMessage2.mMsgNum, localNewMessage2.mCommentNum, localNewMessage2.mReplyCommentNum, localNewMessage2.mUserCommentNum).start();
        localNewMessage1 = (ChatInfoItem.NewMessage)localObject.mSubObject;
        if (localNewMessage1.mNotice <= 0)
          continue;
        if (!localNewMessage1.needRequestForSure())
          continue;
        MessageCenterModel.getInstance().setNoticeArray(localNewMessage1);
        MessageCenterEngine.getInstance().checkNewMessage(paramContext, User.getInstance().getUID());
        continue;
        localChatInfoItem = ChatInfoItem.valueOf(localJSONArray.optJSONObject(k));
        if (localChatInfoItem != null)
        {
          if (!localChatInfoItem.isNewMessage())
            continue;
          if (localObject != null)
            continue;
          localObject = localChatInfoItem;
          break label795;
          localObject.copyFrom(localChatInfoItem);
        }
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        ArrayList localArrayList2;
        localSecurityErrorException.printStackTrace();
        return 0;
        if ((!localChatInfoItem.isTyping()) && (!localChatInfoItem.isCircleTyping()))
          continue;
        if (!localChatInfoItem.isSelfTyping())
          localArrayList2.add((ChatInfoItem.UserTyping)localChatInfoItem.mSubObject);
      }
      catch (Exception localException)
      {
        ChatModel localChatModel;
        String str;
        ArrayList localArrayList1;
        ArrayList localArrayList3;
        ArrayList localArrayList4;
        int j;
        ChatInfoItem.NewMessage localNewMessage1;
        ChatInfoItem localChatInfoItem;
        localException.printStackTrace();
        continue;
        if ((!localChatInfoItem.isUserOnline()) && (!localChatInfoItem.isUserOffline()))
          continue;
        localArrayList1.add((ChatInfoItem.UserStatusChanged)localChatInfoItem.mSubObject);
        break label795;
        if ((!localChatInfoItem.isChatMsg()) && (!localChatInfoItem.isCircleChatMsg()))
          continue;
        if (!localChatInfoItem.isFeedbackChatMsg())
        {
          KXLog.d("wangdan", "bb");
          j = 1;
          localChatModel.addChatInfo(localChatInfoItem, localChatInfoItem.isSendChatMsg());
          break label795;
          if (!localChatInfoItem.isCircleKicked())
            continue;
          localArrayList3.add((ChatInfoItem.CircleMemberChanged)localChatInfoItem.mSubObject);
          break label795;
          if (localChatInfoItem.isCircleNewMember())
          {
            localArrayList4.add((ChatInfoItem.CircleMemberChanged)localChatInfoItem.mSubObject);
            break label795;
            MessageCenterModel.getInstance().setNoticeArray(localNewMessage1);
            int m = MessageCenterModel.getInstance().getTotalNoticeCnt();
            if ((m <= 0) || (!ActivityUtil.needNotification(paramContext)))
              continue;
            sendNewMsgNotificationBroadcast(paramContext, m);
            continue;
            if ("refresh".equals(str))
              continue;
            localChatModel.clear();
            continue;
            return 1;
          }
        }
      }
      label795: k++;
    }
  }

  // ERROR //
  public int sendClearUnreadFlag(Context paramContext, String paramString, boolean paramBoolean)
  {
    // Byte code:
    //   0: iload_3
    //   1: ifeq +127 -> 128
    //   4: invokestatic 52	com/kaixin001/model/ChatModel:getInstance	()Lcom/kaixin001/model/ChatModel;
    //   7: invokevirtual 740	com/kaixin001/model/ChatModel:getChatID	()Ljava/lang/String;
    //   10: astore 12
    //   12: invokestatic 61	com/kaixin001/network/Protocol:getInstance	()Lcom/kaixin001/network/Protocol;
    //   15: aload_2
    //   16: aload 12
    //   18: invokevirtual 743	com/kaixin001/network/Protocol:makeChatClearCircleUnread	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   21: astore 5
    //   23: ldc_w 480
    //   26: new 280	java/lang/StringBuilder
    //   29: dup
    //   30: ldc_w 441
    //   33: invokespecial 285	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   36: aload 5
    //   38: invokevirtual 335	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   41: invokevirtual 292	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   44: invokestatic 296	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   47: new 67	com/kaixin001/network/HttpProxy
    //   50: dup
    //   51: aload_1
    //   52: invokespecial 70	com/kaixin001/network/HttpProxy:<init>	(Landroid/content/Context;)V
    //   55: astore 6
    //   57: aload 6
    //   59: aload 5
    //   61: aconst_null
    //   62: aconst_null
    //   63: invokevirtual 74	com/kaixin001/network/HttpProxy:httpGet	(Ljava/lang/String;Lcom/kaixin001/network/HttpRequestState;Lcom/kaixin001/network/HttpProgressListener;)Ljava/lang/String;
    //   66: astore 11
    //   68: aload 11
    //   70: astore 8
    //   72: ldc_w 480
    //   75: new 280	java/lang/StringBuilder
    //   78: dup
    //   79: ldc_w 470
    //   82: invokespecial 285	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   85: aload 8
    //   87: invokevirtual 335	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   90: invokevirtual 292	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   93: invokestatic 296	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   96: invokestatic 52	com/kaixin001/model/ChatModel:getInstance	()Lcom/kaixin001/model/ChatModel;
    //   99: aload_2
    //   100: iload_3
    //   101: invokevirtual 747	com/kaixin001/model/ChatModel:clearUnreadNum	(Ljava/lang/String;Z)V
    //   104: aload_0
    //   105: aload_1
    //   106: aload 8
    //   108: invokespecial 380	com/kaixin001/engine/KXEngine:parseJSON	(Landroid/content/Context;Ljava/lang/String;)Lorg/json/JSONObject;
    //   111: ifnull +15 -> 126
    //   114: aload_0
    //   115: getfield 546	com/kaixin001/engine/ChatEngine:ret	I
    //   118: istore 10
    //   120: iload 10
    //   122: iconst_1
    //   123: if_icmpeq +47 -> 170
    //   126: iconst_0
    //   127: ireturn
    //   128: invokestatic 52	com/kaixin001/model/ChatModel:getInstance	()Lcom/kaixin001/model/ChatModel;
    //   131: aload_2
    //   132: iload_3
    //   133: invokevirtual 751	com/kaixin001/model/ChatModel:getCmidString	(Ljava/lang/String;Z)Ljava/lang/String;
    //   136: astore 4
    //   138: invokestatic 61	com/kaixin001/network/Protocol:getInstance	()Lcom/kaixin001/network/Protocol;
    //   141: aload_2
    //   142: aload 4
    //   144: invokevirtual 754	com/kaixin001/network/Protocol:makeChatClearUnread	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   147: astore 5
    //   149: goto -126 -> 23
    //   152: astore 7
    //   154: ldc 20
    //   156: ldc_w 756
    //   159: aload 7
    //   161: invokestatic 88	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   164: aconst_null
    //   165: astore 8
    //   167: goto -95 -> 72
    //   170: iconst_1
    //   171: ireturn
    //   172: astore 9
    //   174: aload 9
    //   176: invokevirtual 350	java/lang/Exception:printStackTrace	()V
    //   179: iconst_0
    //   180: ireturn
    //
    // Exception table:
    //   from	to	target	type
    //   57	68	152	java/lang/Exception
    //   104	120	172	java/lang/Exception
  }

  private class GetNewMessagesThread extends Thread
  {
    int commnetNum;
    Context context;
    int msgNum;
    int replyCommentNum;
    int unReadnum;
    int userCommentNum;

    public GetNewMessagesThread(Context paramInt1, int paramInt2, int paramInt3, int paramInt4, int arg6)
    {
      this.context = paramInt1;
      this.msgNum = paramInt2;
      this.commnetNum = paramInt3;
      this.replyCommentNum = paramInt4;
      int i;
      this.userCommentNum = i;
    }

    private void addListItem(Object paramObject, int paramInt)
      throws SecurityErrorException
    {
      MessageNotifyModel.MessageNotifyItem localMessageNotifyItem;
      if (paramInt == 201)
        localMessageNotifyItem = MessageNotifyModel.getInstance().getMessageNotifyItem(this.context, (MessageItem)paramObject, paramInt);
      while (true)
      {
        MessageNotifyModel.getInstance().addItem(this.context, localMessageNotifyItem);
        return;
        if ((paramInt == 203) || (paramInt == 204))
        {
          localMessageNotifyItem = MessageNotifyModel.getInstance().getMessageNotifyItem(this.context, (CommentItem)paramObject, paramInt);
          continue;
        }
        if (paramInt != 205)
        {
          localMessageNotifyItem = null;
          if (paramInt != 206)
            continue;
        }
        localMessageNotifyItem = MessageNotifyModel.getInstance().getMessageNotifyItem(this.context, (UserCommentItem)paramObject, paramInt);
      }
    }

    private void getCommentMessage(boolean paramBoolean)
      throws SecurityErrorException
    {
      boolean bool;
      MessageCenterModel localMessageCenterModel;
      ArrayList localArrayList;
      if (paramBoolean)
      {
        bool = CommentEngine.getInstance().doGetCommentForCertainNumber(this.context, this.unReadnum, true);
        KXLog.d("TEST", "getCommentMessage(" + paramBoolean + "):result = " + bool);
        if (bool)
        {
          localMessageCenterModel = MessageCenterModel.getInstance();
          if (!paramBoolean)
            break label110;
          localArrayList = localMessageCenterModel.getCommentList();
        }
      }
      int j;
      int k;
      for (int i = 203; ; i = 204)
      {
        j = 0;
        k = -1 + localArrayList.size();
        if (k >= 0)
          break label124;
        return;
        bool = CommentEngine.getInstance().doGetReplyCommentForCertainNumber(this.context, this.unReadnum, true);
        break;
        label110: localArrayList = localMessageCenterModel.getReplyCommentList();
      }
      label124: CommentItem localCommentItem = (CommentItem)localArrayList.get(k);
      if ((localCommentItem.getUnread() == 0) && ((j != 0) || (k > 0)));
      while (true)
      {
        k--;
        break;
        j = 1;
        addListItem(localCommentItem, i);
      }
    }

    private void getInboxMessage()
      throws SecurityErrorException
    {
      int i = MessageEngine.getInstance().doGetInboxMessageForCertainNumber(this.context, true, this.unReadnum);
      KXLog.d("TEST", "getInboxMessage():result = " + i);
      ArrayList localArrayList;
      int j;
      int k;
      if (i == 1)
      {
        localArrayList = MessageCenterModel.getInstance().getMessageInboxList();
        j = 0;
        k = -1 + localArrayList.size();
        if (k >= 0);
      }
      else
      {
        return;
      }
      MessageItem localMessageItem = (MessageItem)localArrayList.get(k);
      if ((localMessageItem.getUnread() == 0) && ((j != 0) || (k > 0)));
      while (true)
      {
        k--;
        break;
        j = 1;
        addListItem(localMessageItem, 201);
      }
    }

    private void getLeaveMessage(boolean paramBoolean)
      throws SecurityErrorException
    {
      int i;
      MessageCenterModel localMessageCenterModel;
      ArrayList localArrayList;
      if (paramBoolean)
      {
        i = UserCommentEngine.getInstance().doGetUserCommentForCertainNumber(this.context, this.unReadnum, true);
        KXLog.d("TEST", "getLeaveMessage(" + paramBoolean + "):result = " + i);
        if (i == 1)
        {
          localMessageCenterModel = MessageCenterModel.getInstance();
          if (!paramBoolean)
            break label108;
          localArrayList = localMessageCenterModel.getUserCommentList();
        }
      }
      int k;
      for (int j = 205; ; j = 206)
      {
        k = -1 + localArrayList.size();
        if (k >= 0)
          break label122;
        return;
        i = UserCommentEngine.getInstance().doGetSentUserCommentForCertainNumber(this.context, this.unReadnum, true);
        break;
        label108: localArrayList = localMessageCenterModel.getSentUserCommentList();
      }
      label122: UserCommentItem localUserCommentItem = (UserCommentItem)localArrayList.get(k);
      if (localUserCommentItem.getUnread() == 0);
      while (true)
      {
        k--;
        break;
        addListItem(localUserCommentItem, j);
      }
    }

    public void run()
    {
      try
      {
        ChatEngine.this.threadLock.lock();
        KXLog.d("TEST", "GetNewMessagesThread()...");
        if (this.msgNum > 0)
        {
          this.unReadnum = this.msgNum;
          getInboxMessage();
        }
        if (this.commnetNum > 0)
        {
          this.unReadnum = this.commnetNum;
          getCommentMessage(true);
        }
        if (this.replyCommentNum > 0)
        {
          this.unReadnum = this.replyCommentNum;
          getCommentMessage(false);
        }
        if (this.userCommentNum > 0)
        {
          this.unReadnum = this.userCommentNum;
          getLeaveMessage(false);
          getLeaveMessage(true);
        }
        return;
      }
      catch (Exception localException)
      {
        return;
      }
      finally
      {
        ChatEngine.this.threadLock.unlock();
      }
      throw localObject;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.ChatEngine
 * JD-Core Version:    0.6.0
 */