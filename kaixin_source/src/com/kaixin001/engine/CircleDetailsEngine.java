package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.CircleDetailsItem;
import com.kaixin001.item.CircleDetailsItem.CircleDetailsContent;
import com.kaixin001.model.CircleDetailsModel;
import com.kaixin001.model.CircleDetailsModel.CircleDetailsHeader;
import com.kaixin001.model.CircleDetailsModel.CircleDetailsHeaderMain;
import com.kaixin001.model.KaixinUser;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CircleDetailsEngine extends KXEngine
{
  private static final String LOGTAG = "CircleDetailsEngine";
  private static CircleDetailsEngine instance;
  private String mError = null;

  public static CircleDetailsEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CircleDetailsEngine();
      CircleDetailsEngine localCircleDetailsEngine = instance;
      return localCircleDetailsEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean getCircleDetailsList(Context paramContext, String paramString1, String paramString2, String paramString3)
    throws SecurityErrorException
  {
    this.ret = 0;
    if (paramString3.equals("0"))
      CircleDetailsModel.getInstance().clear();
    CircleDetailsModel.getInstance().getDetailsHeader().clear();
    String str1 = Protocol.getInstance().makeCircleDetailsRequest(paramString1, paramString2, paramString3);
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
        KXLog.e("CircleDetailsEngine", "getCircleDetailsList error", localException);
        str2 = null;
      }
      this.mError = null;
      if (paramString3.equals("0"))
        CircleDetailsModel.getInstance().clear();
    }
    return parseCircleDetailsJSON(paramContext, str2);
  }

  public String getLastError()
  {
    return this.mError;
  }

  public boolean parseCircleDetailsJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
    if (localJSONObject1 == null)
      return false;
    if (this.ret == 1);
    while (true)
    {
      int i;
      int j;
      int k;
      int m;
      int n;
      int i1;
      try
      {
        CircleDetailsModel.CircleDetailsHeader localCircleDetailsHeader = CircleDetailsModel.getInstance().getDetailsHeader();
        JSONObject localJSONObject2 = localJSONObject1.getJSONObject("result");
        JSONObject localJSONObject3 = localJSONObject2.getJSONObject("detail");
        localCircleDetailsHeader.tid = localJSONObject3.optString("tid", "");
        localCircleDetailsHeader.gid = localJSONObject3.optString("gid", "");
        localCircleDetailsHeader.uid = localJSONObject3.optString("uid", "");
        localCircleDetailsHeader.ctime = localJSONObject3.optLong("ctime", -1L);
        localCircleDetailsHeader.name = localJSONObject3.optString("name", "");
        localCircleDetailsHeader.icon50 = localJSONObject3.optString("icon50", "");
        JSONObject localJSONObject4 = localJSONObject3.optJSONObject("pic");
        if (localJSONObject4 == null)
          continue;
        localCircleDetailsHeader.spic = localJSONObject4.optString("s_pic", "");
        localCircleDetailsHeader.fpic = localJSONObject4.optString("f_pic", "");
        localCircleDetailsHeader.picid = localJSONObject4.optString("pid", "");
        localCircleDetailsHeader.inforesource = localJSONObject3.optString("clientsource", "");
        localCircleDetailsHeader.mInfoType = localJSONObject3.optInt("type", -1);
        JSONArray localJSONArray1 = localJSONObject3.optJSONArray("invite_uinfos");
        if (localJSONArray1 == null)
          continue;
        int i2 = 0;
        if (i2 < localJSONArray1.length())
          continue;
        JSONArray localJSONArray2 = localJSONObject3.getJSONArray("main");
        i = localJSONArray2.length();
        if (i <= 0)
          continue;
        j = 0;
        break label786;
        JSONObject localJSONObject5 = localJSONObject2.getJSONObject("reply");
        JSONArray localJSONArray3 = localJSONObject5.getJSONArray("list");
        CircleDetailsModel.getInstance().setTotalReply(localJSONObject5.optInt("total", -1));
        k = localJSONArray3.length();
        ArrayList localArrayList = CircleDetailsModel.getInstance().getCommentList();
        if (k <= 0)
          break label810;
        m = 0;
        break label796;
        JSONObject localJSONObject10 = (JSONObject)localJSONArray1.get(i2);
        KaixinUser localKaixinUser = new KaixinUser();
        localKaixinUser.uid = localJSONObject10.optString("uid", "");
        localKaixinUser.realname = localJSONObject10.optString("name", "");
        localCircleDetailsHeader.mInviteUsersList.add(localKaixinUser);
        i2++;
        continue;
        JSONObject localJSONObject9 = (JSONObject)localJSONArray2.get(j);
        CircleDetailsModel.CircleDetailsHeaderMain localCircleDetailsHeaderMain = new CircleDetailsModel.CircleDetailsHeaderMain();
        localCircleDetailsHeaderMain.mText = localJSONObject9.optString("txt", "");
        localCircleDetailsHeaderMain.mTitle = localJSONObject9.optString("title", "");
        localCircleDetailsHeaderMain.mImgUrl = localJSONObject9.optString("img_url", "");
        localCircleDetailsHeaderMain.mSwfUrl = localJSONObject9.optString("swf_url", "");
        localCircleDetailsHeaderMain.mType = localJSONObject9.optInt("type", -1);
        localCircleDetailsHeaderMain.mUid = localJSONObject9.optString("uid", "");
        localCircleDetailsHeaderMain.mName = localJSONObject9.optString("uname", "");
        localCircleDetailsHeader.mHeaderList.add(localCircleDetailsHeaderMain);
        j++;
        break label786;
        JSONObject localJSONObject6 = (JSONObject)localJSONArray3.get(m);
        CircleDetailsItem localCircleDetailsItem = new CircleDetailsItem();
        JSONArray localJSONArray4 = localJSONObject6.getJSONArray("content");
        n = localJSONArray4.length();
        if (n <= 0)
          continue;
        i1 = 0;
        break label812;
        localCircleDetailsItem.ctime = localJSONObject6.optString("ctime", "");
        JSONObject localJSONObject7 = localJSONObject6.getJSONObject("userinfo");
        localCircleDetailsItem.name = localJSONObject7.optString("name", "");
        localCircleDetailsItem.uid = localJSONObject7.optString("uid", "");
        localCircleDetailsItem.icon50 = localJSONObject7.optString("icon50", "");
        localArrayList.add(localCircleDetailsItem);
        m++;
        break label796;
        JSONObject localJSONObject8 = (JSONObject)localJSONArray4.get(i1);
        CircleDetailsItem.CircleDetailsContent localCircleDetailsContent = new CircleDetailsItem.CircleDetailsContent();
        localCircleDetailsContent.ntype = localJSONObject8.optInt("type", -1);
        localCircleDetailsContent.content = localJSONObject8.optString("txt", "");
        localCircleDetailsContent.uid = localJSONObject8.optString("uid", "");
        localCircleDetailsContent.uname = localJSONObject8.optString("uname", "");
        localCircleDetailsItem.mContentList.add(localCircleDetailsContent);
        i1++;
      }
      catch (Exception localException)
      {
        KXLog.e("CircleDetailsEngine", "parseCircleDetailsJSON", localException);
      }
      try
      {
        this.mError = localJSONObject1.getString("error");
        return false;
      }
      catch (JSONException localJSONException)
      {
        while (true)
          localJSONException.printStackTrace();
      }
      label786: if (j < i)
        continue;
      continue;
      label796: if ((m >= k) || (m >= 100))
      {
        label810: return true;
        label812: if (i1 < n)
          continue;
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.CircleDetailsEngine
 * JD-Core Version:    0.6.0
 */