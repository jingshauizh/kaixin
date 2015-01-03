package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class GetMP4VideoEngine
{
  public static final int ERROR_GET_VIDEO_MP4 = -1;
  private static final String LOGTAG = "GetMP4VideoEngine";
  private static GetMP4VideoEngine minstance;
  public String msVideo = null;

  public static GetMP4VideoEngine getInstance()
  {
    monitorenter;
    try
    {
      if (minstance == null)
        minstance = new GetMP4VideoEngine();
      GetMP4VideoEngine localGetMP4VideoEngine = minstance;
      return localGetMP4VideoEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public String getVideoUrl()
  {
    return this.msVideo;
  }

  public int parseTudouVideoJSON(Context paramContext, String paramString)
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject(paramString);
    if (localJSONObject1 == null);
    String str;
    do
    {
      JSONObject localJSONObject2;
      do
      {
        return -1;
        localJSONObject2 = localJSONObject1.optJSONObject("playurl");
      }
      while (localJSONObject2 == null);
      str = localJSONObject2.optString("fluency");
    }
    while (str == null);
    this.msVideo = str;
    return 0;
  }

  public int postVideoMP4Request(Context paramContext, String paramString)
    throws JSONException
  {
    String str1 = Protocol.getInstance().makeMP4VideoRequest();
    this.msVideo = null;
    HashMap localHashMap = new HashMap();
    localHashMap.put("uid", User.getInstance().getUID());
    localHashMap.put("videourl", URLEncoder.encode(paramString));
    localHashMap.put("ip", "123.125.4.16");
    localHashMap.put("pf", "04");
    localHashMap.put("chid", "2270");
    localHashMap.put("subpf", "000");
    localHashMap.put("subchid", "0000");
    localHashMap.put("key", "GEYDEMZSGI3TAMBUGAYDAMJQGI2DEOJUGE");
    localHashMap.put("format", "json");
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpPost(str1, localHashMap, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return 0;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("GetMP4VideoEngine", "postVideoMP4Request error", localException);
        str2 = null;
      }
    }
    return parseTudouVideoJSON(paramContext, str2);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.GetMP4VideoEngine
 * JD-Core Version:    0.6.0
 */