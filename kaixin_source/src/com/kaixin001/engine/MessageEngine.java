package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.MessageDetailInfo;
import com.kaixin001.model.MessageCenterModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageEngine extends KXEngine
{
  private static final String CACHE_INBOX_FILENAME = "kx_inbox_cache.kx";
  private static final String CACHE_OUTBOX_FILENAME = "kx_outbox_cache.kx";
  public static final int DETAIL_NUM = 10;
  private static final String LOGTAG = "MessageEngine";
  public static final int NUM = 10;
  private static MessageEngine instance = null;
  private String mLastPostMessageMid = null;

  public static MessageEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new MessageEngine();
      MessageEngine localMessageEngine = instance;
      return localMessageEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int doGetInboxMessageForCertainNumber(Context paramContext, boolean paramBoolean, int paramInt)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetMessageInboxRequest("", paramInt, User.getInstance().getUID());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2 = null;
    try
    {
      str2 = localHttpProxy.httpGet(str1, null, null);
      KXLog.d("TEST", "REQUEST DATA:" + str2);
      if (str2 == null)
        return 0;
    }
    catch (Exception localException)
    {
      while (true)
        KXLog.e("MessageEngine", "doGetInboxMessageForCertainNumber error", localException);
    }
    return parseMessageInboxJSON(paramContext, str2);
  }

  public int doGetMessage(Context paramContext, boolean paramBoolean1, boolean paramBoolean2, String paramString)
    throws SecurityErrorException
  {
    String str1;
    if (paramBoolean1)
      str1 = Protocol.getInstance().makeGetMessageInboxRequest(paramString, 10, User.getInstance().getUID());
    while (true)
    {
      HttpProxy localHttpProxy = new HttpProxy(paramContext);
      try
      {
        String str4 = localHttpProxy.httpGet(str1, null, null);
        str2 = str4;
        if (str2 == null)
        {
          return 0;
          str1 = Protocol.getInstance().makeGetMessageSendboxRequest(paramString, 10, User.getInstance().getUID());
        }
      }
      catch (Exception localException)
      {
        String str2;
        while (true)
        {
          KXLog.e("MessageEngine", "doGetMessage error", localException);
          str2 = null;
        }
        JSONObject localJSONObject = super.parseJSON(paramContext, str2);
        if (localJSONObject == null)
        {
          MessageCenterModel.getInstance().setReturnNum(0);
          return 0;
        }
        while (true)
        {
          JSONArray localJSONArray;
          int j;
          MessageCenterModel localMessageCenterModel;
          try
          {
            localJSONArray = localJSONObject.getJSONArray("msgs");
            int i = localJSONObject.getInt("n");
            j = localJSONObject.optInt("total", 0);
            localMessageCenterModel = MessageCenterModel.getInstance();
            localMessageCenterModel.setReturnNum(i);
            if (localJSONArray == null)
              return 0;
            if (paramBoolean1)
            {
              localMessageCenterModel.setMsgInboxTotal(j);
              if (!TextUtils.isEmpty(paramString))
                continue;
              localMessageCenterModel.setMessageInboxList(localJSONArray);
              if (!TextUtils.isEmpty(paramString))
                break;
              String str3 = User.getInstance().getUID();
              if (!paramBoolean1)
                break label288;
              k = 101;
              setCacheMsgList(paramContext, str3, k, str2);
              break;
              localMessageCenterModel.appendMessageInboxList(localJSONArray);
              continue;
            }
          }
          catch (JSONException localJSONException)
          {
            MessageCenterModel.getInstance().setReturnNum(0);
            localJSONException.printStackTrace();
            return 0;
          }
          localMessageCenterModel.setMsgOutboxTotal(j);
          if (TextUtils.isEmpty(paramString))
          {
            localMessageCenterModel.setMessageOutboxList(localJSONArray);
            continue;
          }
          localMessageCenterModel.appendMessageSentList(localJSONArray);
          continue;
          label288: int k = 102;
        }
      }
    }
    return 1;
  }

  public int doGetMessageDetail(Context paramContext, String paramString1, boolean paramBoolean, String paramString2, int paramInt)
    throws SecurityErrorException
  {
    return doGetMessageDetail(paramContext, paramString1, paramBoolean, paramString2, paramInt, null, null);
  }

  public int doGetMessageDetail(Context paramContext, String paramString1, boolean paramBoolean, String paramString2, int paramInt, String paramString3, String paramString4)
    throws SecurityErrorException
  {
    if (paramInt < 0)
      paramInt = 10;
    Protocol localProtocol = Protocol.getInstance();
    String str1 = User.getInstance().getUID();
    String str2 = localProtocol.makeGetMessageDetailRequest(paramInt, paramString1, str1, paramString2, paramString3, paramString4);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str3;
    try
    {
      String str4 = localHttpProxy.httpGet(str2, null, null);
      str3 = str4;
      KXLog.d("TEST", str2);
      KXLog.d("TEST", str3);
      if (str3 == null)
        return 0;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("MessageEngine", "doGetMessageDetail error", localException);
        str3 = null;
      }
    }
    return parseMessageDetailJSON(paramContext, str3, paramBoolean, "0".equalsIgnoreCase(paramString2));
  }

  public int doGetMoreMessageDetail(Context paramContext, String paramString1, boolean paramBoolean, String paramString2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetMessageDetailRequest(10, paramString1, User.getInstance().getUID(), paramString2, null, null);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (str2 == null)
        return 0;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("MessageEngine", "doGetMoreMessageDetail error", localException);
        str2 = null;
      }
    }
    return parseMoreMessageDetailJSON(paramContext, str2, paramBoolean);
  }

  public boolean doMessageLeaveOrJoin(Context paramContext, String paramString, boolean paramBoolean)
  {
    String str1 = Protocol.getInstance().makeMessageLeaveOrJoinRequest(User.getInstance().getUID(), paramString, paramBoolean);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (str2 == null)
        return false;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("MessageEngine", "doMessageLeaveOrJoin error", localException);
        str2 = null;
      }
      try
      {
        int i = new JSONObject(str2).getInt("ret");
        return i == 1;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
    return false;
  }

  public boolean doPostMessage(Context paramContext, ArrayList<String> paramArrayList, String paramString)
  {
    String str1 = Protocol.getInstance().makePostMessageRequest(paramArrayList, paramString, User.getInstance().getUID());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str4 = localHttpProxy.httpGet(str1, null, null);
      str2 = str4;
      if (str2 == null)
        return false;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("MessageEngine", "doPostMessage error", localException);
        str2 = null;
      }
      try
      {
        JSONObject localJSONObject = new JSONObject(str2);
        int i = localJSONObject.getInt("ret");
        String str3 = localJSONObject.getString("mid");
        if (i == 1)
        {
          this.mLastPostMessageMid = str3;
          return true;
        }
        this.mLastPostMessageMid = null;
        return false;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        this.mLastPostMessageMid = null;
      }
    }
    return false;
  }

  public boolean doReplyMessage(Context paramContext, String paramString1, String paramString2, StringBuilder paramStringBuilder)
  {
    String str1 = Protocol.getInstance().makeReplyMessageRequest(paramString1, paramString2, User.getInstance().getUID());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (str2 == null)
        return false;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("MessageEngine", "doReplyMessage error", localException);
        str2 = null;
      }
      try
      {
        JSONObject localJSONObject = new JSONObject(str2);
        if (localJSONObject.getInt("ret") == 1)
        {
          if (paramStringBuilder != null)
            paramStringBuilder.append(localJSONObject.optString("mid"));
          return true;
        }
        return false;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
    return false;
  }

  public String getLastPostMessageMid()
  {
    return this.mLastPostMessageMid;
  }

  public boolean loadCachedMsgList(Context paramContext, String paramString, int paramInt)
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString)))
      return false;
    String str1 = FileUtil.getKXCacheDir(paramContext);
    String str2;
    if (paramInt == 101)
      str2 = "kx_inbox_cache.kx";
    while (true)
    {
      String str3 = FileUtil.getCacheData(str1, paramString, str2);
      if (!TextUtils.isEmpty(str3));
      try
      {
        JSONObject localJSONObject = new JSONObject(str3);
        MessageCenterModel localMessageCenterModel;
        JSONArray localJSONArray;
        int i;
        if (localJSONObject != null)
        {
          localMessageCenterModel = MessageCenterModel.getInstance();
          localJSONArray = localJSONObject.optJSONArray("msgs");
          i = localJSONObject.optInt("total");
          if (paramInt != 101)
            break label115;
          localMessageCenterModel.setMsgInboxTotal(i);
          localMessageCenterModel.setMessageInboxList(localJSONArray);
        }
        while (true)
        {
          return true;
          str2 = "kx_outbox_cache.kx";
          break;
          label115: localMessageCenterModel.setMsgOutboxTotal(i);
          localMessageCenterModel.setMessageOutboxList(localJSONArray);
        }
      }
      catch (Exception localException)
      {
        while (true)
          KXLog.e("MessageEngine", "loadDraft", localException);
      }
    }
  }

  public int parseMessageDetailJSON(Context paramContext, String paramString, boolean paramBoolean1, boolean paramBoolean2)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
    if (localJSONObject == null)
    {
      MessageCenterModel.getInstance().setReturnNum(0);
      return 0;
    }
    try
    {
      int i = localJSONObject.getInt("n");
      String str = localJSONObject.getString("mid");
      JSONArray localJSONArray1 = localJSONObject.getJSONArray("msgs");
      JSONArray localJSONArray2 = localJSONObject.getJSONArray("fuids");
      JSONArray localJSONArray3 = localJSONObject.getJSONArray("fnames");
      int j = localJSONObject.getInt("usernum");
      MessageDetailInfo localMessageDetailInfo = new MessageDetailInfo();
      if (paramBoolean1)
        localMessageDetailInfo.setDetailType(201);
      while (true)
      {
        localMessageDetailInfo.setDetailList(localJSONArray1);
        localMessageDetailInfo.setId(str);
        localMessageDetailInfo.setFuids(localJSONArray2);
        localMessageDetailInfo.setFnames(localJSONArray3);
        localMessageDetailInfo.setUserNum(j);
        MessageCenterModel.getInstance().setActiveMesasgeDetail(localMessageDetailInfo, 101, paramBoolean2);
        MessageCenterModel.getInstance().setReturnNum(i);
        return 1;
        localMessageDetailInfo.setDetailType(202);
      }
    }
    catch (JSONException localJSONException)
    {
      MessageCenterModel.getInstance().setReturnNum(0);
      localJSONException.printStackTrace();
    }
    return 0;
  }

  public int parseMessageInboxJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
    if (localJSONObject == null)
    {
      MessageCenterModel.getInstance().setReturnNum(0);
      return 0;
    }
    try
    {
      MessageCenterModel.getInstance().setMessageInboxList(localJSONObject.getJSONArray("msgs"));
      int i = localJSONObject.getInt("n");
      MessageCenterModel.getInstance().setReturnNum(i);
      return 1;
    }
    catch (JSONException localJSONException)
    {
      MessageCenterModel.getInstance().setReturnNum(0);
      localJSONException.printStackTrace();
    }
    return 0;
  }

  public int parseMessageSentJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
    if (localJSONObject == null)
    {
      MessageCenterModel.getInstance().setReturnNum(0);
      return 0;
    }
    try
    {
      MessageCenterModel.getInstance().setMessageOutboxList(localJSONObject.getJSONArray("msgs"));
      int i = localJSONObject.getInt("n");
      MessageCenterModel.getInstance().setReturnNum(i);
      return 1;
    }
    catch (JSONException localJSONException)
    {
      MessageCenterModel.getInstance().setReturnNum(0);
      localJSONException.printStackTrace();
    }
    return 0;
  }

  public int parseMoreMessageDetailJSON(Context paramContext, String paramString, boolean paramBoolean)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
    if (localJSONObject == null)
    {
      MessageCenterModel.getInstance().setReturnNum(0);
      return 0;
    }
    try
    {
      int i = localJSONObject.getInt("n");
      String str = localJSONObject.getString("mid");
      JSONArray localJSONArray1 = localJSONObject.getJSONArray("msgs");
      JSONArray localJSONArray2 = localJSONObject.getJSONArray("fuids");
      JSONArray localJSONArray3 = localJSONObject.getJSONArray("fnames");
      int j = localJSONObject.getInt("usernum");
      MessageDetailInfo localMessageDetailInfo = new MessageDetailInfo();
      if (paramBoolean)
        localMessageDetailInfo.setDetailType(201);
      while (true)
      {
        localMessageDetailInfo.setDetailList(localJSONArray1);
        localMessageDetailInfo.setId(str);
        localMessageDetailInfo.setFuids(localJSONArray2);
        localMessageDetailInfo.setFnames(localJSONArray3);
        localMessageDetailInfo.setUserNum(j);
        MessageCenterModel.getInstance().setActiveMoreMesasgeDetail(localMessageDetailInfo);
        MessageCenterModel.getInstance().setReturnNum(i);
        return 1;
        localMessageDetailInfo.setDetailType(202);
      }
    }
    catch (JSONException localJSONException)
    {
      MessageCenterModel.getInstance().setReturnNum(0);
      localJSONException.printStackTrace();
    }
    return 0;
  }

  protected boolean setCacheMsgList(Context paramContext, String paramString1, int paramInt, String paramString2)
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString1)))
      return false;
    try
    {
      String str1 = FileUtil.getKXCacheDir(paramContext);
      if (paramInt == 101);
      for (String str2 = "kx_inbox_cache.kx"; ; str2 = "kx_outbox_cache.kx")
      {
        FileUtil.setCacheData(str1, paramString1, str2, paramString2);
        return true;
      }
    }
    catch (Exception localException)
    {
      KXLog.e("MessageEngine", "setCacheMsgList", localException);
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.MessageEngine
 * JD-Core Version:    0.6.0
 */