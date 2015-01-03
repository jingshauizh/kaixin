package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.MessageDetailInfo;
import com.kaixin001.model.CircleMessageModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CircleMsgEngine extends KXEngine
{
  private static final String CACHE_CIRCLE_ME_REPLY_LIST_FILENAME = "kx_circle_me_reply_list_cache.kx";
  private static final String CACHE_CIRCLE_REPLY_ME_LIST_FILENAME = "kx_circle_reply_me_list_cache.kx";
  public static final int DETAIL_NUM = 20;
  private static final String LOGTAG = "CircleMsgEngine";
  public static final int NUM = 10;
  private static CircleMsgEngine instance = null;

  public static CircleMsgEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CircleMsgEngine();
      CircleMsgEngine localCircleMsgEngine = instance;
      return localCircleMsgEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private boolean parseReplayList(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
    if (localJSONObject1 == null)
      return false;
    try
    {
      JSONObject localJSONObject2 = localJSONObject1.getJSONObject("result");
      int i = localJSONObject1.getInt("ret");
      CircleMessageModel localCircleMessageModel = CircleMessageModel.getInstance();
      localCircleMessageModel.setReturnNum(i);
      if (localCircleMessageModel.getActiveCircleReplyType() == 10)
        localCircleMessageModel.setReplyMeListTotal(localJSONObject2.getInt("allnum"));
      while (true)
      {
        localCircleMessageModel.setReplyList(localJSONObject2, localCircleMessageModel.getActiveCircleReplyType(), 0);
        return true;
        localCircleMessageModel.setMeReplyListTotal(localJSONObject2.getInt("allnum"));
      }
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return false;
  }

  public int doCircleDetailMessageLeaveOrJoin(Context paramContext, String paramString1, String paramString2, int paramInt)
  {
    int i = 1;
    if ((paramContext == null) || (TextUtils.isEmpty(paramString2)) || (TextUtils.isEmpty(paramString1)))
      i = -1;
    while (true)
    {
      return i;
      String str1 = Protocol.getInstance().makeCircleDetailLeaveOrJoinRequest(paramString1, paramString2, paramInt);
      HttpProxy localHttpProxy = new HttpProxy(paramContext);
      try
      {
        String str3 = localHttpProxy.httpGet(str1, null, null);
        str2 = str3;
        if (str2 == null)
          return -2;
      }
      catch (Exception localException)
      {
        String str2;
        while (true)
        {
          KXLog.e("CircleMsgEngine", "doCircleDetailMessageLeaveOrJoin error", localException);
          str2 = null;
        }
        try
        {
          int j = new JSONObject(str2).getInt("ret");
          if (j != i)
            return 0;
        }
        catch (JSONException localJSONException)
        {
          localJSONException.printStackTrace();
        }
      }
    }
    return -1;
  }

  public boolean doGetCircleMsgDetail(Context paramContext, String paramString1, String paramString2, String paramString3)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeCircleDetailsRequest(paramString2, paramString1, paramString3);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (str2 == null)
        return false;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("CircleMsgEngine", "doGetCircleMsgDetail error", localException);
        str2 = null;
      }
    }
    return parseCircleMsgDetailJSON(paramContext, str2);
  }

  public boolean doGetCircleMsgDetail(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeCircleDetailsRequest(paramString2, paramString1, paramString3, paramString4);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (str2 == null)
        return false;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("CircleMsgEngine", "doGetCircleMsgDetail error", localException);
        str2 = null;
      }
    }
    return parseCircleMsgDetailJSON(paramContext, str2);
  }

  public ArrayList<Integer> doGetNewMsgCnt(Context paramContext)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetCircleReplyListRequest(0, String.valueOf(0), 10);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (str2 == null)
        return null;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("CircleMsgEngine", "doGetNewMsgCnt error", localException);
        str2 = null;
      }
      JSONObject localJSONObject1 = super.parseJSON(paramContext, str2);
      if (localJSONObject1 == null)
        return null;
      try
      {
        JSONObject localJSONObject2 = localJSONObject1.getJSONObject("result");
        ArrayList localArrayList = new ArrayList();
        JSONObject localJSONObject3 = localJSONObject2.getJSONObject("rgroup_msg");
        localArrayList.add(0, Integer.valueOf(localJSONObject3.getInt("rgroupmsg")));
        localArrayList.add(1, Integer.valueOf(localJSONObject3.getInt("rgroupreply")));
        return localArrayList;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
    return null;
  }

  public boolean doGetReplyList(Context paramContext, int paramInt1, int paramInt2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetCircleReplyListRequest(paramInt1 - 10, String.valueOf(paramInt2), 10);
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
        KXLog.e("CircleMsgEngine", "doGetReplyList error", localException);
        str2 = null;
      }
      KXLog.d("doGetReplyList", "strContent=" + str2);
      JSONObject localJSONObject1 = super.parseJSON(paramContext, str2);
      if (localJSONObject1 == null)
        return false;
      try
      {
        JSONObject localJSONObject2 = localJSONObject1.getJSONObject("result");
        int i = localJSONObject1.getInt("ret");
        CircleMessageModel localCircleMessageModel = CircleMessageModel.getInstance();
        localCircleMessageModel.setReturnNum(i);
        if (paramInt1 == 10)
          localCircleMessageModel.setReplyMeListTotal(localJSONObject2.getInt("allnum"));
        while (true)
        {
          if (paramInt2 == 0)
            setCacheMsgList(paramContext, User.getInstance().getUID(), paramInt1, str2);
          return localCircleMessageModel.setReplyList(localJSONObject2, paramInt1, paramInt2);
          localCircleMessageModel.setMeReplyListTotal(localJSONObject2.getInt("allnum"));
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
    return false;
  }

  public boolean loadCachedMsgList(Context paramContext, String paramString, int paramInt)
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString)))
      return false;
    String str1 = FileUtil.getKXCacheDir(paramContext);
    String str2;
    if (paramInt == 10)
      str2 = "kx_circle_reply_me_list_cache.kx";
    while (true)
    {
      String str3 = FileUtil.getCacheData(str1, paramString, str2);
      if (!TextUtils.isEmpty(str3))
      {
        if (paramInt == 10);
        try
        {
          boolean bool2 = parseReplayList(paramContext, str3);
          return bool2;
          str2 = "kx_circle_me_reply_list_cache.kx";
          continue;
          boolean bool1 = parseReplayList(paramContext, str3);
          return bool1;
        }
        catch (Exception localException)
        {
          KXLog.e("CircleMsgEngine", "loadDraft", localException);
        }
      }
    }
    return true;
  }

  public boolean parseCircleMsgDetailJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
    if (localJSONObject1 == null)
    {
      CircleMessageModel.getInstance().setReturnNum(0);
      return false;
    }
    try
    {
      CircleMessageModel.getInstance().setReturnNum(localJSONObject1.getInt("ret"));
      MessageDetailInfo localMessageDetailInfo = new MessageDetailInfo();
      JSONObject localJSONObject2 = localJSONObject1.optJSONObject("result");
      JSONObject localJSONObject3 = localJSONObject2.optJSONObject("detail");
      String str1 = localJSONObject3.getString("tid");
      String str2 = localJSONObject3.getString("gid");
      localMessageDetailInfo.setCircleDetailTid(str1);
      localMessageDetailInfo.setCircleDetailGid(str2);
      JSONObject localJSONObject4 = localJSONObject2.optJSONObject("reply");
      CircleMessageModel.getInstance().setReturnDetailTotal(localJSONObject4.getInt("total"));
      JSONArray localJSONArray = localJSONObject4.optJSONArray("list");
      if (localJSONArray != null)
      {
        localMessageDetailInfo.setDetailList(localJSONArray);
        CircleMessageModel.getInstance().setRetDetailCount(localJSONArray.length());
      }
      CircleMessageModel.getInstance().setActiveCircleMesasgeDetail(localMessageDetailInfo);
      return true;
    }
    catch (JSONException localJSONException)
    {
      CircleMessageModel.getInstance().setReturnNum(0);
      localJSONException.printStackTrace();
    }
    return false;
  }

  protected boolean setCacheMsgList(Context paramContext, String paramString1, int paramInt, String paramString2)
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString1)))
      return false;
    try
    {
      String str1 = FileUtil.getKXCacheDir(paramContext);
      if (paramInt == 10);
      for (String str2 = "kx_circle_reply_me_list_cache.kx"; ; str2 = "kx_circle_me_reply_list_cache.kx")
      {
        FileUtil.setCacheData(str1, paramString1, str2, paramString2);
        return true;
      }
    }
    catch (Exception localException)
    {
      KXLog.e("CircleMsgEngine", "setCacheMsgList", localException);
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.CircleMsgEngine
 * JD-Core Version:    0.6.0
 */