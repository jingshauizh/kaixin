package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.adapter.ExtremityAdapter;
import com.kaixin001.adapter.NewsDetailAdapter;
import com.kaixin001.item.ExtremityItemLv1;
import com.kaixin001.item.ExtremityItemLv1.ItemType;
import com.kaixin001.item.ExtremityItemLv2;
import com.kaixin001.item.ExtremityItemLv2.ItemTypeLv2;
import com.kaixin001.model.ExtremityCommentModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ExtremityCommentEngin extends KXEngine
{
  private static final String LOGTAG = "ExtremityCommentEngin";
  private static ExtremityCommentEngin instance;

  public static ExtremityCommentEngin getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new ExtremityCommentEngin();
      ExtremityCommentEngin localExtremityCommentEngin = instance;
      return localExtremityCommentEngin;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean getMoreComments(Context paramContext, String paramString1, String paramString2, String paramString3, NewsDetailAdapter paramNewsDetailAdapter, ExtremityCommentModel paramExtremityCommentModel, String paramString4, int paramInt)
    throws SecurityErrorException
  {
    this.ret = 0;
    if (paramExtremityCommentModel == null)
      return false;
    String str1 = Protocol.getInstance().makeMoreCommentRequest(User.getInstance().getUID(), paramString1, paramString2, paramString3, paramString4, paramInt);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return false;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("ExtremityCommentEngin", "getObjCommentList error", localException);
        str2 = null;
      }
    }
    return parseCommentJSONOneType(paramContext, str2, paramNewsDetailAdapter, paramExtremityCommentModel);
  }

  public boolean getMoreReposts(Context paramContext, String paramString, NewsDetailAdapter paramNewsDetailAdapter, ExtremityCommentModel paramExtremityCommentModel, int paramInt1, int paramInt2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeRepostListRequest(paramString, paramInt1, paramInt2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return false;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("ExtremityCommentEngin", "getObjCommentList error", localException);
        str2 = null;
      }
    }
    return parseRepostJSON(paramContext, str2, paramNewsDetailAdapter, paramExtremityCommentModel);
  }

  public boolean getObjCommentList(Context paramContext, String paramString1, String paramString2, String paramString3, ExtremityAdapter paramExtremityAdapter, ExtremityCommentModel paramExtremityCommentModel)
    throws SecurityErrorException
  {
    this.ret = 0;
    if (paramExtremityCommentModel == null);
    while (true)
    {
      return false;
      String str1 = Protocol.getInstance().makeObjCommentRequest(User.getInstance().getUID(), paramString1, paramString2, paramString3);
      HttpProxy localHttpProxy = new HttpProxy(paramContext);
      try
      {
        String str3 = localHttpProxy.httpGet(str1, null, null);
        str2 = str3;
        if (TextUtils.isEmpty(str2))
          continue;
        return parseCommentJSON(paramContext, str2, paramExtremityAdapter, paramExtremityCommentModel);
      }
      catch (Exception localException)
      {
        while (true)
        {
          KXLog.e("ExtremityCommentEngin", "getObjCommentList error", localException);
          String str2 = null;
        }
      }
    }
  }

  public boolean parseCommentJSON(Context paramContext, String paramString, ExtremityAdapter paramExtremityAdapter, ExtremityCommentModel paramExtremityCommentModel)
    throws SecurityErrorException
  {
    JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
    if ((localJSONObject1 == null) || (paramExtremityCommentModel == null))
      return false;
    if (this.ret == 1);
    label393: label410: label412: label416: label420: 
    while (true)
    {
      int i;
      Object localObject;
      int j;
      label92: ExtremityItemLv1 localExtremityItemLv1;
      try
      {
        paramExtremityAdapter.commentCount = Integer.valueOf(localJSONObject1.getInt("total"));
        JSONArray localJSONArray = localJSONObject1.getJSONArray("comments");
        i = localJSONArray.length();
        List localList = paramExtremityCommentModel.getAllCommentsAct();
        if (i <= 0)
          break label410;
        localObject = null;
        j = 0;
        break label393;
        if (localObject == null)
          break label410;
        localObject.next();
        break label410;
        JSONObject localJSONObject2 = (JSONObject)localJSONArray.get(j);
        if (localJSONObject2.getInt("mainthread") != 1)
          continue;
        localExtremityItemLv1 = new ExtremityItemLv1();
        localExtremityItemLv1.content = localJSONObject2.getString("abscont");
        localExtremityItemLv1.id = localJSONObject2.getString("thread_cid");
        localExtremityItemLv1.userIconUrl = localJSONObject2.getString("flogo");
        localExtremityItemLv1.userName = localJSONObject2.getString("fname");
        localExtremityItemLv1.uid = localJSONObject2.getString("fuid");
        localExtremityItemLv1.isMainComment = true;
        localExtremityItemLv1.time = localJSONObject2.getString("stime");
        localExtremityItemLv1.mytype = localJSONObject2.getString("mtype");
        localExtremityItemLv1.itemType = ExtremityItemLv1.ItemType.content_comment;
        localList.add(localExtremityItemLv1);
        if (localObject == null)
          break label412;
        localObject.next();
        break label412;
        ExtremityItemLv2 localExtremityItemLv2 = new ExtremityItemLv2();
        localExtremityItemLv2.content = localJSONObject2.getString("abscont");
        localExtremityItemLv2.id = localJSONObject2.getString("thread_cid");
        localExtremityItemLv2.userIconUrl = localJSONObject2.getString("flogo");
        localExtremityItemLv2.userName = localJSONObject2.getString("fname");
        localExtremityItemLv2.uid = localJSONObject2.getString("fuid");
        localExtremityItemLv2.time = localJSONObject2.getString("stime");
        localExtremityItemLv2.mytype = localJSONObject2.getString("mtype");
        localExtremityItemLv2.itemType = ExtremityItemLv2.ItemTypeLv2.content_comment;
        if ((localObject == null) || (localObject.itemLv2s == null))
          break label416;
        localObject.itemLv2s.add(localExtremityItemLv2);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        KXLog.e("ExtremityCommentEngin", "parseObjCommentJSON", localException);
      }
      return false;
      while (true)
      {
        if (j >= i)
          break label420;
        if (j < 100)
          break label92;
        break;
        return true;
        localObject = localExtremityItemLv1;
        j++;
      }
    }
  }

  public boolean parseCommentJSONOneType(Context paramContext, String paramString, NewsDetailAdapter paramNewsDetailAdapter, ExtremityCommentModel paramExtremityCommentModel)
    throws SecurityErrorException
  {
    JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
    if ((localJSONObject1 == null) || (paramExtremityCommentModel == null))
      return false;
    if (this.ret == 1);
    int i;
    int j;
    do
    {
      try
      {
        paramNewsDetailAdapter.commentCount = localJSONObject1.getInt("total");
        JSONArray localJSONArray = localJSONObject1.getJSONArray("comments");
        i = localJSONArray.length();
        List localList = paramExtremityCommentModel.getAllCommentsAct();
        if (i <= 0)
          break;
        j = 0;
        continue;
        JSONObject localJSONObject2 = (JSONObject)localJSONArray.get(j);
        int k = localJSONObject2.getInt("mainthread");
        ExtremityItemLv1 localExtremityItemLv1 = new ExtremityItemLv1();
        localExtremityItemLv1.content = localJSONObject2.getString("abscont");
        localExtremityItemLv1.id = localJSONObject2.getString("thread_cid");
        localExtremityItemLv1.userIconUrl = localJSONObject2.getString("flogo");
        localExtremityItemLv1.userName = localJSONObject2.getString("fname");
        localExtremityItemLv1.uid = localJSONObject2.getString("fuid");
        if (k == 1);
        for (localExtremityItemLv1.isMainComment = true; ; localExtremityItemLv1.isMainComment = false)
        {
          localExtremityItemLv1.time = localJSONObject2.getString("stime");
          localExtremityItemLv1.mytype = localJSONObject2.getString("mtype");
          localExtremityItemLv1.itemType = ExtremityItemLv1.ItemType.content_comment;
          localList.add(localExtremityItemLv1);
          j++;
          break;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        KXLog.e("ExtremityCommentEngin", "parseObjCommentJSON", localException);
      }
      return false;
    }
    while ((j < i) && (j < 100));
    return true;
  }

  public boolean parseRepostJSON(Context paramContext, String paramString, NewsDetailAdapter paramNewsDetailAdapter, ExtremityCommentModel paramExtremityCommentModel)
    throws SecurityErrorException
  {
    int i = 1;
    JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
    if ((localJSONObject1 == null) || (paramExtremityCommentModel == null))
      i = 0;
    while (true)
    {
      return i;
      if (this.ret == i)
        try
        {
          paramNewsDetailAdapter.repostCount = localJSONObject1.getInt("total");
          JSONArray localJSONArray = localJSONObject1.getJSONArray("data");
          int j = localJSONArray.length();
          List localList = paramExtremityCommentModel.getReposts();
          if (j <= 0)
            continue;
          for (int k = 0; k < j; k++)
          {
            JSONObject localJSONObject2 = (JSONObject)localJSONArray.get(k);
            ExtremityItemLv1 localExtremityItemLv1 = new ExtremityItemLv1();
            localExtremityItemLv1.userIconUrl = localJSONObject2.getString("logo");
            localExtremityItemLv1.userName = localJSONObject2.getString("name");
            localExtremityItemLv1.uid = localJSONObject2.getString("uid");
            localExtremityItemLv1.content = localJSONObject2.getString("content");
            localExtremityItemLv1.time = localJSONObject2.getString("time");
            localExtremityItemLv1.itemType = ExtremityItemLv1.ItemType.cotent_repost;
            localList.add(localExtremityItemLv1);
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          KXLog.e("ExtremityCommentEngin", "parseObjCommentJSON", localException);
        }
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.ExtremityCommentEngin
 * JD-Core Version:    0.6.0
 */