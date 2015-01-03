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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserCommentEngine extends KXEngine
{
  public static final int DETAIL_NUM = 10;
  private static final String LEAVE_MESSAGE_FILENAME = "kx_LeaveMsglistcache.kx";
  private static final String LOGTAG = "UserCommentEngine";
  public static final int NUM = 10;
  private static final String REPLY_LEAVE_MESSAGE_FILENAME = "kx_ReplyLeaveMsglistcache.kx";
  public static final int RESULT_ERROR_NOT_FOUND = 2;
  public static final int RESULT_ERROR_OTHER = 0;
  public static final int RESULT_OK = 1;
  private static UserCommentEngine instance = null;

  public static UserCommentEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new UserCommentEngine();
      UserCommentEngine localUserCommentEngine = instance;
      return localUserCommentEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void saveCacheFile(Context paramContext, String paramString1, int paramInt, String paramString2)
  {
    if (paramContext == null)
      return;
    if (paramInt == 2);
    for (String str = "kx_LeaveMsglistcache.kx"; ; str = "kx_ReplyLeaveMsglistcache.kx")
    {
      FileUtil.setCacheData(FileUtil.getKXCacheDir(paramContext), paramString1, str, paramString2);
      return;
    }
  }

  public boolean doGetSentUserComment(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    String str1 = User.getInstance().getUID();
    String str2 = Protocol.getInstance().makeGetSentUserCommentRequest(paramString, 10, str1, false);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str4 = localHttpProxy.httpGet(str2, null, null);
      str3 = str4;
      if (str3 == null)
        return false;
    }
    catch (Exception localException)
    {
      String str3;
      while (true)
      {
        KXLog.e("UserCommentEngine", "doGetSentUserComment error", localException);
        str3 = null;
      }
      KXLog.d("doGetSentUserComment", "strContent=" + str3);
      if (TextUtils.isEmpty(paramString))
        saveCacheFile(paramContext, str1, 5, str3);
      JSONObject localJSONObject = super.parseJSON(paramContext, str3);
      if (localJSONObject == null)
        return false;
      try
      {
        int i = localJSONObject.optInt("n", 0);
        int j = localJSONObject.optInt("total", 0);
        JSONArray localJSONArray = localJSONObject.getJSONArray("comments");
        MessageCenterModel localMessageCenterModel = MessageCenterModel.getInstance();
        localMessageCenterModel.setReturnNum(i);
        localMessageCenterModel.setSentUserCommentTotal(Math.max(i, j));
        if (TextUtils.isEmpty(paramString))
          localMessageCenterModel.setSentUserCommentList(localJSONArray);
        else
          localMessageCenterModel.appendSentUserCommentList(localJSONArray);
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        return false;
      }
    }
    return true;
  }

  public int doGetSentUserCommentDetail(Context paramContext, String paramString1, String paramString2, int paramInt)
    throws SecurityErrorException
  {
    return doGetSentUserCommentDetail(paramContext, paramString1, paramString2, paramInt, null, null);
  }

  public int doGetSentUserCommentDetail(Context paramContext, String paramString1, String paramString2, int paramInt, String paramString3, String paramString4)
    throws SecurityErrorException
  {
    if (paramInt < 0)
      paramInt = 10;
    Protocol localProtocol = Protocol.getInstance();
    String str1 = User.getInstance().getUID();
    String str2 = localProtocol.makeGetSentUserCommentDetailRequest(paramInt, paramString1, str1, paramString2, paramString3, paramString4);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str3;
    try
    {
      String str4 = localHttpProxy.httpGet(str2, null, null);
      str3 = str4;
      if (str3 == null)
      {
        MessageCenterModel.getInstance().setReturnNum(0);
        return 0;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("UserCommentEngine", "doGetSentUserCommentDetail error", localException);
        str3 = null;
      }
    }
    return parseUserCommentDetailJSON(paramContext, str3, false, "0".equalsIgnoreCase(paramString2));
  }

  public int doGetSentUserCommentForCertainNumber(Context paramContext, int paramInt, boolean paramBoolean)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetSentUserCommentRequest("", paramInt, User.getInstance().getUID(), paramBoolean);
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
        KXLog.e("UserCommentEngine", "doGetSentUserCommentForCertainNumber error", localException);
        str2 = null;
      }
    }
    return parseSentUserCommentListJSON(paramContext, str2);
  }

  public boolean doGetUserComment(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    String str1 = User.getInstance().getUID();
    String str2 = Protocol.getInstance().makeGetUserCommentRequest(paramString, 10, str1, false);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str4 = localHttpProxy.httpGet(str2, null, null);
      str3 = str4;
      if (str3 == null)
        return false;
    }
    catch (Exception localException)
    {
      JSONObject localJSONObject;
      do
      {
        String str3;
        while (true)
        {
          KXLog.e("UserCommentEngine", "doGetUserComment error", localException);
          str3 = null;
        }
        if (TextUtils.isEmpty(paramString))
          saveCacheFile(paramContext, str1, 2, str3);
        localJSONObject = super.parseJSON(paramContext, str3);
      }
      while (localJSONObject == null);
      try
      {
        JSONArray localJSONArray = localJSONObject.getJSONArray("comments");
        int i = localJSONObject.optInt("n", 0);
        int j = localJSONObject.optInt("total", 0);
        MessageCenterModel localMessageCenterModel = MessageCenterModel.getInstance();
        localMessageCenterModel.setReturnNum(i);
        localMessageCenterModel.setUserCommentTotal(Math.max(i, j));
        if (TextUtils.isEmpty(paramString))
          localMessageCenterModel.setUserCommentList(localJSONArray);
        else
          localMessageCenterModel.appendUserCommentList(localJSONArray);
      }
      catch (JSONException localJSONException)
      {
        KXLog.e("UserCommentEngine", "doGetUserComment", localJSONException);
        return false;
      }
    }
    return true;
  }

  public int doGetUserCommentDetail(Context paramContext, String paramString1, String paramString2, int paramInt)
    throws SecurityErrorException
  {
    return doGetUserCommentDetail(paramContext, paramString1, paramString2, paramInt, null, null);
  }

  public int doGetUserCommentDetail(Context paramContext, String paramString1, String paramString2, int paramInt, String paramString3, String paramString4)
    throws SecurityErrorException
  {
    if (paramInt < 0)
      paramInt = 10;
    Protocol localProtocol = Protocol.getInstance();
    String str1 = User.getInstance().getUID();
    String str2 = localProtocol.makeGetUserCommentDetailRequest(paramInt, paramString1, str1, paramString2, paramString3, paramString4);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str3;
    try
    {
      String str4 = localHttpProxy.httpGet(str2, null, null);
      str3 = str4;
      if (str3 == null)
      {
        MessageCenterModel.getInstance().setReturnNum(0);
        return 0;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("UserCommentEngine", "doGetUserCommentDetail error", localException);
        str3 = null;
      }
    }
    return parseUserCommentDetailJSON(paramContext, str3, true, "0".equalsIgnoreCase(paramString2));
  }

  public int doGetUserCommentForCertainNumber(Context paramContext, int paramInt, boolean paramBoolean)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetUserCommentRequest("", paramInt, User.getInstance().getUID(), paramBoolean);
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
        KXLog.e("UserCommentEngine", "doGetUserCommentForCertainNumber error", localException);
        str2 = null;
      }
    }
    return parseUserCommentListJSON(paramContext, str2);
  }

  public void loadCacheFile(Context paramContext, String paramString, int paramInt)
  {
    if (paramContext == null)
      return;
    if (paramInt == 2);
    JSONArray localJSONArray;
    int i;
    int j;
    MessageCenterModel localMessageCenterModel;
    for (String str1 = "kx_LeaveMsglistcache.kx"; ; str1 = "kx_ReplyLeaveMsglistcache.kx")
    {
      String str2 = FileUtil.getCacheData(FileUtil.getKXCacheDir(paramContext), paramString, str1);
      if (TextUtils.isEmpty(str2))
        break;
      try
      {
        JSONObject localJSONObject = new JSONObject(str2);
        localJSONArray = localJSONObject.getJSONArray("comments");
        i = localJSONObject.optInt("n", 0);
        j = localJSONObject.optInt("total", 0);
        localMessageCenterModel = MessageCenterModel.getInstance();
        if (paramInt != 2)
          break label125;
        localMessageCenterModel.setUserCommentList(localJSONArray);
        localMessageCenterModel.setReturnNum(i);
        localMessageCenterModel.setUserCommentTotal(j);
        return;
      }
      catch (Exception localException)
      {
        KXLog.e("UserCommentEngine", "loadDraft", localException);
        return;
      }
    }
    label125: localMessageCenterModel.setSentUserCommentList(localJSONArray);
    localMessageCenterModel.setReturnNum(i);
    localMessageCenterModel.setSentUserCommentTotal(j);
  }

  public int parseSentUserCommentListJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
    if (localJSONObject == null)
      return 0;
    try
    {
      int i = localJSONObject.optInt("n", 0);
      int j = localJSONObject.optInt("total", 0);
      MessageCenterModel.getInstance().setSentUserCommentList(localJSONObject.getJSONArray("comments"));
      MessageCenterModel.getInstance().setReturnNum(i);
      MessageCenterModel.getInstance().setSentUserCommentTotal(Math.max(i, j));
      return 1;
    }
    catch (JSONException localJSONException)
    {
    }
    return 0;
  }

  public int parseUserCommentDetailJSON(Context paramContext, String paramString, boolean paramBoolean1, boolean paramBoolean2)
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
      String str = localJSONObject.optString("thread_cid", null);
      if (str == null)
      {
        MessageCenterModel.getInstance().setReturnNum(0);
        return 2;
      }
      JSONArray localJSONArray = localJSONObject.getJSONArray("comments");
      int i = localJSONObject.getInt("mtype");
      MessageCenterModel.getInstance().setReturnNum(localJSONObject.getInt("n"));
      MessageDetailInfo localMessageDetailInfo = new MessageDetailInfo();
      if (paramBoolean1)
        localMessageDetailInfo.setDetailType(205);
      while (true)
      {
        localMessageDetailInfo.setDetailList(localJSONArray);
        localMessageDetailInfo.setType(i);
        localMessageDetailInfo.setId(str);
        MessageCenterModel.getInstance().setActiveMesasgeDetail(localMessageDetailInfo, 3, paramBoolean2);
        return 1;
        localMessageDetailInfo.setDetailType(206);
      }
    }
    catch (JSONException localJSONException)
    {
      MessageCenterModel.getInstance().setReturnNum(0);
    }
    return 0;
  }

  public int parseUserCommentListJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
    if (localJSONObject == null)
      return 0;
    try
    {
      MessageCenterModel.getInstance().setUserCommentList(localJSONObject.getJSONArray("comments"));
      int i = localJSONObject.optInt("n", 0);
      int j = localJSONObject.optInt("total", 0);
      MessageCenterModel.getInstance().setReturnNum(i);
      MessageCenterModel.getInstance().setUserCommentTotal(Math.max(i, j));
      return 1;
    }
    catch (JSONException localJSONException)
    {
    }
    return 0;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.UserCommentEngine
 * JD-Core Version:    0.6.0
 */