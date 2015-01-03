package com.kaixin001.engine;

import android.content.Context;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProgressListener;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.HashMap;
import org.json.JSONObject;

public class WeiboEngine extends KXEngine
{
  public static final int CODE_ERROR_OTHER = 0;
  public static final int CODE_ERROR_PRIVACY_LIMITED = 226;
  public static final int CODE_ERROR_TOO_FREQUENT = 218;
  public static final int CODE_OK = 1;
  private static final String LOGTAG = "WeiboEngine";
  private static WeiboEngine instance;
  private String msRetRecordId = null;

  public static WeiboEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new WeiboEngine();
      WeiboEngine localWeiboEngine = instance;
      return localWeiboEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public String getRetRecordId()
  {
    return this.msRetRecordId;
  }

  public int parseWeiboJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
    int i;
    if (localJSONObject1 == null)
      i = 0;
    do
    {
      return i;
      try
      {
        JSONObject localJSONObject2 = localJSONObject1.optJSONObject("upgradeInfo");
        if (localJSONObject2 != null)
          BaseFragment.getBaseFragment().setUpgradeInfoData(localJSONObject2);
        while (this.ret == 1)
        {
          this.msRetRecordId = localJSONObject1.optString("rid");
          return 1;
          BaseFragment.getBaseFragment().isShowLevelToast(false);
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          BaseFragment.getBaseFragment().isShowLevelToast(false);
          localException.printStackTrace();
        }
        i = localJSONObject1.optInt("code", -1);
      }
    }
    while ((i == 218) || (i == 226));
    return 0;
  }

  public int postWeibo(Context paramContext, String paramString1, String paramString2, String paramString3, HttpProgressListener paramHttpProgressListener)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeRepostWeiboRequest(User.getInstance().getUID(), paramString1);
    HashMap localHashMap = new HashMap();
    localHashMap.put("content", paramString2);
    localHashMap.put("forward", paramString3);
    localHashMap.put("uid", User.getInstance().getUID());
    localHashMap.put("rid", paramString1);
    localHashMap.put("api_version", "3.9.9");
    localHashMap.put("version", "android-3.9.9");
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpPost(str1, localHashMap, null, paramHttpProgressListener);
      str2 = str3;
      return parseWeiboJSON(paramContext, str2);
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("WeiboEngine", "postWeibo error", localException);
        String str2 = null;
      }
    }
  }

  public void setRetRecordId(String paramString)
  {
    this.msRetRecordId = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.WeiboEngine
 * JD-Core Version:    0.6.0
 */