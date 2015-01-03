package com.kaixin001.engine;

import android.content.Context;
import com.kaixin001.model.CircleNewsModel;
import com.kaixin001.model.CircleNewsModel.CircleNewsItem;
import com.kaixin001.model.CircleNewsModel.CircleNewsTalkContent;
import com.kaixin001.model.CircleNewsModel.KXPicture;
import com.kaixin001.model.KaixinUser;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class CircleNewsEngine extends KXEngine
{
  private static final String LOGTAG = "CircleNewsEngine";
  public static final int NUM = 10;
  public static final int RESULT_FAILED = 0;
  public static final int RESULT_FAILED_NETWORK = -2;
  public static final int RESULT_FAILED_NO_PERMISSION = -3002;
  public static final int RESULT_FAILED_PARSE = -1;
  public static final int RESULT_OK = 1;
  private static CircleNewsEngine instance = null;
  private String mLastError = "";
  CircleNewsModel model = CircleNewsModel.getInstance();

  private ArrayList<CircleNewsModel.CircleNewsItem> getCircleNewsList(JSONArray paramJSONArray, String paramString)
  {
    if (paramJSONArray == null)
    {
      localArrayList = null;
      return localArrayList;
    }
    int i = paramJSONArray.length();
    if (i == 0)
      return null;
    ArrayList localArrayList = new ArrayList(i);
    int j = 0;
    label34: JSONObject localJSONObject;
    if (j < i)
    {
      localJSONObject = paramJSONArray.optJSONObject(j);
      if (localJSONObject != null)
        break label59;
    }
    while (true)
    {
      j++;
      break label34;
      break;
      label59: CircleNewsModel.CircleNewsItem localCircleNewsItem = new CircleNewsModel.CircleNewsItem();
      localCircleNewsItem.gid = paramString;
      localCircleNewsItem.stid = localJSONObject.optString("stid");
      localCircleNewsItem.tid = localJSONObject.optString("tid");
      localCircleNewsItem.ctime = localJSONObject.optLong("ctime");
      localCircleNewsItem.rnum = localJSONObject.optInt("replynum", 0);
      localCircleNewsItem.talkType = localJSONObject.optInt("talk_type", 0);
      localCircleNewsItem.source = localJSONObject.optString("clientsource", null);
      localCircleNewsItem.pic = getKXPicture(localJSONObject.optJSONObject("pic"));
      localCircleNewsItem.suser = valueOf(localJSONObject);
      localCircleNewsItem.content = valueOf(localJSONObject.optJSONArray("main"));
      localCircleNewsItem.users = getMentionedUsers(localJSONObject.optJSONArray("invite_uinfos"));
      localArrayList.add(localCircleNewsItem);
    }
  }

  public static CircleNewsEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CircleNewsEngine();
      CircleNewsEngine localCircleNewsEngine = instance;
      return localCircleNewsEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private CircleNewsModel.KXPicture getKXPicture(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null)
      return null;
    CircleNewsModel.KXPicture localKXPicture = new CircleNewsModel.KXPicture();
    localKXPicture.pid = paramJSONObject.optString("pid");
    localKXPicture.smallPic = paramJSONObject.optString("s_pic");
    localKXPicture.largePic = paramJSONObject.optString("f_pic");
    localKXPicture.aid = paramJSONObject.optString("aid");
    return localKXPicture;
  }

  private ArrayList<KaixinUser> getMentionedUsers(JSONArray paramJSONArray)
  {
    ArrayList localArrayList = null;
    if (paramJSONArray == null);
    int i;
    do
    {
      return localArrayList;
      i = paramJSONArray.length();
      localArrayList = null;
    }
    while (i == 0);
    localArrayList = new ArrayList(i);
    int j = 0;
    label31: JSONObject localJSONObject;
    if (j < i)
    {
      localJSONObject = paramJSONArray.optJSONObject(j);
      if (localJSONObject != null)
        break label56;
    }
    while (true)
    {
      j++;
      break label31;
      break;
      label56: localArrayList.add(valueOf(localJSONObject));
    }
  }

  private ArrayList<CircleNewsModel.CircleNewsTalkContent> valueOf(JSONArray paramJSONArray)
  {
    if (paramJSONArray == null)
    {
      localArrayList = null;
      return localArrayList;
    }
    int i = paramJSONArray.length();
    if (i == 0)
      return null;
    ArrayList localArrayList = new ArrayList(i);
    int j = 0;
    label31: JSONObject localJSONObject;
    if (j < i)
    {
      localJSONObject = paramJSONArray.optJSONObject(j);
      if (localJSONObject != null)
        break label56;
    }
    while (true)
    {
      j++;
      break label31;
      break;
      label56: CircleNewsModel.CircleNewsTalkContent localCircleNewsTalkContent = new CircleNewsModel.CircleNewsTalkContent();
      localCircleNewsTalkContent.txt = localJSONObject.optString("txt");
      localCircleNewsTalkContent.type = localJSONObject.optInt("type", 0);
      localCircleNewsTalkContent.title = localJSONObject.optString("title");
      localCircleNewsTalkContent.urlImg = localJSONObject.optString("img_url");
      localCircleNewsTalkContent.urlSwf = localJSONObject.optString("swf_url", null);
      localCircleNewsTalkContent.uid = localJSONObject.optString("uid");
      localCircleNewsTalkContent.uname = localJSONObject.optString("uname");
      localArrayList.add(localCircleNewsTalkContent);
    }
  }

  public int doGetCircleNewsItemList(Context paramContext, String paramString, int paramInt1, int paramInt2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeCircleTalkList(paramString, paramInt1, paramInt2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (paramInt1 == 0)
        this.model.clearCircleNews();
      if (str2 == null)
        return 0;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("CircleNewsEngine", "doGetCircleNewsItemList error", localException);
        str2 = null;
      }
      JSONObject localJSONObject1 = super.parseJSON(paramContext, str2);
      if (localJSONObject1 == null)
        return -1;
      if (this.ret == 1)
      {
        JSONObject localJSONObject2 = localJSONObject1.optJSONObject("result");
        int i = localJSONObject2.optInt("total", 0);
        JSONArray localJSONArray = localJSONObject2.optJSONArray("list");
        CircleNewsModel.getInstance().setCircleNewsList(i, getCircleNewsList(localJSONArray, paramString), paramInt1);
        return 1;
      }
      if (localJSONObject1.optInt("code", 0) == 3002)
        return -3002;
    }
    return 0;
  }

  public String getLastError()
  {
    return this.mLastError;
  }

  public KaixinUser valueOf(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null);
    String str;
    do
    {
      return null;
      str = paramJSONObject.optString("uid");
    }
    while (str == null);
    KaixinUser localKaixinUser = new KaixinUser();
    localKaixinUser.uid = str;
    localKaixinUser.realname = paramJSONObject.optString("name");
    localKaixinUser.logo = paramJSONObject.optString("icon50");
    localKaixinUser.relation = paramJSONObject.optInt("relation", -1);
    localKaixinUser.gender = paramJSONObject.optInt("gender", -1);
    return localKaixinUser;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.CircleNewsEngine
 * JD-Core Version:    0.6.0
 */