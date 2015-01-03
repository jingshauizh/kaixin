package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.GameUserInfo;
import com.kaixin001.model.ShareInfo;
import com.kaixin001.model.User;
import com.kaixin001.model.UserInfo;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetUrlEngine extends KXEngine
{
  private static final String LOGTAG = "GetUrlEngine";
  private static GetUrlEngine instance = null;
  private String msThirdPartLogo = null;
  private String msThirdPartName = null;
  private String msUrl = null;
  private String msUrlVersion = null;

  private void parseUploadScoreJson(JSONObject paramJSONObject)
    throws JSONException
  {
    GameUserInfo localGameUserInfo = GameUserInfo.getInstance();
    JSONObject localJSONObject = paramJSONObject.getJSONObject("userInfo");
    if (localJSONObject != null)
    {
      String str1 = localJSONObject.optString("topNum");
      String str2 = localJSONObject.optString("name");
      String str3 = localJSONObject.optString("score");
      localGameUserInfo.setName(str2);
      localGameUserInfo.setScore(str3);
      localGameUserInfo.setTopNum(str1);
    }
  }

  public Integer getGameConfigs(Context paramContext, String paramString)
  {
    String str1 = Protocol.getInstance().makeGameConfigUrl(paramString);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str2 = localHttpProxy.httpGet(str1, null, null);
      boolean bool = TextUtils.isEmpty(str2);
      Object localObject = null;
      if (!bool)
      {
        JSONObject localJSONObject1 = super.parseJSON(paramContext, str2);
        this.ret = localJSONObject1.optInt("ret");
        int i = this.ret;
        localObject = null;
        if (i == 1)
        {
          JSONObject localJSONObject2 = localJSONObject1.getJSONArray("data").getJSONObject(0);
          ShareInfo localShareInfo = ShareInfo.getInstance();
          localShareInfo.setEndTime(localJSONObject2.optString("end_time"));
          localShareInfo.setStartTime(localJSONObject2.optString("start_time"));
          localShareInfo.setShareMessage(localJSONObject2.optString("share_message"));
          localShareInfo.setShareUrl(localJSONObject2.optString("share_url"));
          localShareInfo.setShareImg(localJSONObject2.optString("share_img"));
          Integer localInteger = Integer.valueOf(this.ret);
          localObject = localInteger;
        }
      }
      return localObject;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public String getThirdPartName()
  {
    return this.msThirdPartName;
  }

  public boolean getThirdPartyInfo(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    String str1 = User.getInstance().getUID();
    String str2 = Protocol.getInstance().makeGetThirdPartyUrl(str1, paramString);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str4 = localHttpProxy.httpGet(str2, null, null);
      str3 = str4;
      if (str3 == null)
        return false;
      if (parseApplistJSON(paramContext, str3));
      for (int i = 1; ; i = 0)
        return i;
    }
    catch (Exception localException)
    {
      while (true)
        String str3 = null;
    }
  }

  public String getThirdPartyLogo()
  {
    return this.msThirdPartLogo;
  }

  public String getUrl()
  {
    return this.msUrl;
  }

  public String getUrlVersion()
  {
    return this.msUrlVersion;
  }

  public boolean parseApplistJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
    if (localJSONObject1 == null)
      return false;
    int i = this.ret;
    int j = 0;
    if (i == 1);
    try
    {
      JSONArray localJSONArray = localJSONObject1.optJSONArray("data");
      if ((localJSONArray != null) && (localJSONArray.length() > 0))
      {
        JSONObject localJSONObject2 = localJSONArray.getJSONObject(0);
        if (localJSONObject2 != null)
        {
          this.msUrl = localJSONObject2.optString("url");
          this.msUrlVersion = localJSONObject2.optString("urlversion");
          this.msThirdPartName = localJSONObject2.optString("thirdpartyname");
          this.msThirdPartLogo = localJSONObject2.optString("thirdpartlogo");
          j = 1;
        }
      }
      while (true)
      {
        return j;
        j = 0;
        continue;
        j = 0;
      }
    }
    catch (Exception localException)
    {
      while (true)
        j = 0;
    }
  }

  public Integer processWapRanking(Context paramContext, String paramString)
  {
    String str = Protocol.getInstance().makeWapRanking(paramString);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      JSONObject localJSONObject = super.parseJSON(paramContext, localHttpProxy.httpGet(str, null, null));
      this.ret = localJSONObject.optInt("ret");
      if (this.ret == 1)
      {
        UserInfo localUserInfo = UserInfo.getInstance();
        localUserInfo.setCurUserTopNum(localJSONObject.optJSONObject("user").optString("topNum"));
        JSONArray localJSONArray = localJSONObject.optJSONArray("users");
        ArrayList localArrayList = new ArrayList();
        if (localJSONArray != null);
        for (int i = 0; ; i++)
        {
          if (i >= localJSONArray.length())
          {
            localUserInfo.setNames(localArrayList);
            return Integer.valueOf(this.ret);
          }
          localArrayList.add(localJSONArray.getJSONObject(i).optString("name"));
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return Integer.valueOf(0);
  }

  public Integer uploadGameScore(Context paramContext, String paramString1, String paramString2)
  {
    String str = Protocol.getInstance().makeUploadScore(paramString1, paramString2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      JSONObject localJSONObject = super.parseJSON(paramContext, localHttpProxy.httpGet(str, null, null));
      if (localJSONObject != null)
      {
        this.ret = localJSONObject.optInt("ret");
        if (this.ret == 1)
        {
          parseUploadScoreJson(localJSONObject);
          Integer localInteger = Integer.valueOf(this.ret);
          return localInteger;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return Integer.valueOf(0);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.GetUrlEngine
 * JD-Core Version:    0.6.0
 */