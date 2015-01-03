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

public class CommentEngine extends KXEngine
{
  private static final String CACHE_COMMENT_LIST_FILENAME = "kx_comment_list_cache.kx";
  private static final String CACHE_REPLY_COMMENT_LIST_FILENAME = "kx_reply_comment_list_cache.kx";
  public static final int DETAIL_NUM = 10;
  private static final String LOGTAG = "CommentEngine";
  public static final int NUM = 10;
  private static CommentEngine instance = null;

  public static CommentEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CommentEngine();
      CommentEngine localCommentEngine = instance;
      return localCommentEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean doGetComment(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetCommentRequest(paramString, 10, User.getInstance().getUID(), false);
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
        KXLog.e("CommentEngine", "doGetComment error", localException);
        str2 = null;
      }
      JSONObject localJSONObject = super.parseJSON(paramContext, str2);
      if (localJSONObject == null)
        return false;
      try
      {
        JSONArray localJSONArray = localJSONObject.getJSONArray("comments");
        int i = localJSONObject.getInt("n");
        MessageCenterModel localMessageCenterModel = MessageCenterModel.getInstance();
        localMessageCenterModel.setReturnNum(i);
        localMessageCenterModel.setCommentListTotal(localJSONObject.optInt("total"));
        if (TextUtils.isEmpty(paramString))
        {
          setCacheMsgList(paramContext, User.getInstance().getUID(), 3, str2);
          localMessageCenterModel.setCommentList(localJSONArray);
        }
        else
        {
          localMessageCenterModel.appendCommentList(localJSONArray);
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        return false;
      }
    }
    return true;
  }

  public int doGetCommentDetail(Context paramContext, String paramString1, String paramString2, int paramInt)
    throws SecurityErrorException
  {
    return doGetCommentDetail(paramContext, paramString1, paramString2, paramInt, null, null);
  }

  public int doGetCommentDetail(Context paramContext, String paramString1, String paramString2, int paramInt, String paramString3, String paramString4)
    throws SecurityErrorException
  {
    if (paramInt < 0)
      paramInt = 10;
    Protocol localProtocol = Protocol.getInstance();
    String str1 = User.getInstance().getUID();
    String str2 = localProtocol.makeGetCommentDetailRequest(paramInt, paramString1, str1, paramString2, paramString3, paramString4);
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
        KXLog.e("CommentEngine", "doGetCommentDetail error", localException);
        str3 = null;
      }
    }
    return parseCommentDetailJSON(paramContext, str3, true, "0".equalsIgnoreCase(paramString2));
  }

  public boolean doGetCommentForCertainNumber(Context paramContext, int paramInt, boolean paramBoolean)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetCommentRequest("", paramInt, User.getInstance().getUID(), paramBoolean);
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
        KXLog.e("CommentEngine", "doGetCommentForCertainNumber error", localException);
        str2 = null;
      }
    }
    return parseCommentListJSON(paramContext, str2);
  }

  public boolean doGetReplyComment(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetReplyCommentRequest(paramString, 10, User.getInstance().getUID(), false);
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
        KXLog.e("CommentEngine", "doGetReplyComment error", localException);
        str2 = null;
      }
      JSONObject localJSONObject = super.parseJSON(paramContext, str2);
      if (localJSONObject == null)
        return false;
      try
      {
        JSONArray localJSONArray = localJSONObject.getJSONArray("comments");
        int i = localJSONObject.getInt("n");
        MessageCenterModel localMessageCenterModel = MessageCenterModel.getInstance();
        localMessageCenterModel.setReturnNum(i);
        localMessageCenterModel.setReplyCommentListTotal(localJSONObject.optInt("total"));
        if (TextUtils.isEmpty(paramString))
        {
          setCacheMsgList(paramContext, User.getInstance().getUID(), 6, str2);
          localMessageCenterModel.setReplyCommentList(localJSONArray);
        }
        else
        {
          localMessageCenterModel.appendReplyCommentList(localJSONArray);
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        return false;
      }
    }
    return true;
  }

  public int doGetReplyCommentDetail(Context paramContext, String paramString1, String paramString2, String paramString3, int paramInt)
    throws SecurityErrorException
  {
    return doGetReplyCommentDetail(paramContext, paramString1, paramString2, paramString3, paramInt, null, null);
  }

  public int doGetReplyCommentDetail(Context paramContext, String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5)
    throws SecurityErrorException
  {
    if (paramInt < 0)
      paramInt = 10;
    Protocol localProtocol = Protocol.getInstance();
    String str1 = User.getInstance().getUID();
    String str2 = localProtocol.makeGetReplyCommentDetailRequest(paramInt, paramString1, paramString2, str1, paramString3, paramString4, paramString5);
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
        KXLog.e("CommentEngine", "doGetReplyCommentDetail error", localException);
        str3 = null;
      }
    }
    return parseCommentDetailJSON(paramContext, str3, false, "0".equalsIgnoreCase(paramString3));
  }

  public boolean doGetReplyCommentForCertainNumber(Context paramContext, int paramInt, boolean paramBoolean)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetReplyCommentRequest("", paramInt, User.getInstance().getUID(), paramBoolean);
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
        KXLog.e("CommentEngine", "doGetReplyCommentForCertainNumber error", localException);
        str2 = null;
      }
    }
    return parseReplyCommentListJSON(paramContext, str2);
  }

  public boolean loadCachedMsgList(Context paramContext, String paramString, int paramInt)
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString)))
      return false;
    String str1 = FileUtil.getKXCacheDir(paramContext);
    String str2;
    if (paramInt == 3)
      str2 = "kx_comment_list_cache.kx";
    while (true)
    {
      String str3 = FileUtil.getCacheData(str1, paramString, str2);
      if (!TextUtils.isEmpty(str3))
      {
        if (paramInt == 3);
        try
        {
          boolean bool2 = parseCommentListJSON(paramContext, str3);
          return bool2;
          str2 = "kx_reply_comment_list_cache.kx";
          continue;
          boolean bool1 = parseReplyCommentListJSON(paramContext, str3);
          return bool1;
        }
        catch (Exception localException)
        {
          KXLog.e("CommentEngine", "loadDraft", localException);
        }
      }
    }
    return true;
  }

  public int parseCommentDetailJSON(Context paramContext, String paramString, boolean paramBoolean1, boolean paramBoolean2)
    throws SecurityErrorException
  {
    JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
    if (localJSONObject1 == null)
    {
      MessageCenterModel.getInstance().setReturnNum(0);
      return 0;
    }
    try
    {
      String str1 = localJSONObject1.optString("thread_cid");
      JSONArray localJSONArray = localJSONObject1.getJSONArray("comments");
      int i = localJSONObject1.getInt("mtype");
      String str2 = localJSONObject1.getString("id");
      Object localObject = "";
      JSONObject localJSONObject2 = localJSONObject1.optJSONObject("photoinfo");
      String str3 = localJSONObject1.getString("appid");
      JSONObject localJSONObject3 = localJSONObject1.optJSONObject("recordcontent");
      String str4 = localJSONObject1.optString("privacy");
      JSONObject localJSONObject4 = localJSONObject1.optJSONObject("albumInfo");
      String str5 = localJSONObject1.optString("atstate");
      MessageCenterModel.getInstance().setReturnNum(localJSONObject1.getInt("n"));
      MessageDetailInfo localMessageDetailInfo = new MessageDetailInfo();
      if (paramBoolean1)
        localMessageDetailInfo.setDetailType(203);
      while (true)
      {
        localMessageDetailInfo.setDetailList(localJSONArray);
        localMessageDetailInfo.setType(i);
        localMessageDetailInfo.setId(str1);
        localMessageDetailInfo.setAppName(localJSONObject1.optString("appname"));
        localMessageDetailInfo.setAppHtml(localJSONObject1.optString("apphtml"));
        localMessageDetailInfo.setAbstractContent(localJSONObject1.optString("abscont"));
        if (!TextUtils.isEmpty(str2))
          localMessageDetailInfo.setmOriginalId(str2);
        if (!TextUtils.isEmpty((CharSequence)localObject))
          localMessageDetailInfo.setmCRealName((String)localObject);
        if ((localJSONObject2 != null) && (localJSONObject2.length() > 0))
          localMessageDetailInfo.setmJSONPhotoInfo(localJSONObject2);
        if ((localJSONObject3 != null) && (localJSONObject3.length() > 0))
          localMessageDetailInfo.setmRecordContent(localJSONObject3);
        if (!TextUtils.isEmpty(str4))
          localMessageDetailInfo.setmPrivacy(str4);
        if ((localJSONObject4 != null) && (localJSONObject4.length() > 0))
          localMessageDetailInfo.setmJSONAlbumInfo(localJSONObject4);
        if (!TextUtils.isEmpty(str5))
          localMessageDetailInfo.setmAtstate(str5);
        localMessageDetailInfo.setmAppid(str3);
        MessageCenterModel.getInstance().setActiveMesasgeDetail(localMessageDetailInfo, 3, paramBoolean2);
        return 1;
        localMessageDetailInfo.setDetailType(204);
        String str6 = localJSONObject1.getString("creal_name");
        localObject = str6;
      }
    }
    catch (JSONException localJSONException)
    {
      MessageCenterModel.getInstance().setReturnNum(0);
      localJSONException.printStackTrace();
    }
    return 0;
  }

  public boolean parseCommentListJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
    if (localJSONObject == null)
      return false;
    try
    {
      MessageCenterModel localMessageCenterModel = MessageCenterModel.getInstance();
      localMessageCenterModel.setCommentList(localJSONObject.getJSONArray("comments"));
      localMessageCenterModel.setReturnNum(localJSONObject.getInt("n"));
      localMessageCenterModel.setCommentListTotal(localJSONObject.optInt("total"));
      return true;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return false;
  }

  public boolean parseReplyCommentListJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
    if (localJSONObject == null)
      return false;
    try
    {
      MessageCenterModel localMessageCenterModel = MessageCenterModel.getInstance();
      localMessageCenterModel.setReplyCommentList(localJSONObject.getJSONArray("comments"));
      localMessageCenterModel.setReturnNum(localJSONObject.getInt("n"));
      localMessageCenterModel.setReplyCommentListTotal(localJSONObject.optInt("total"));
      return true;
    }
    catch (JSONException localJSONException)
    {
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
      if (paramInt == 3);
      for (String str2 = "kx_comment_list_cache.kx"; ; str2 = "kx_reply_comment_list_cache.kx")
      {
        FileUtil.setCacheData(str1, paramString1, str2, paramString2);
        return true;
      }
    }
    catch (Exception localException)
    {
      KXLog.e("CommentEngine", "setCacheMsgList", localException);
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.CommentEngine
 * JD-Core Version:    0.6.0
 */