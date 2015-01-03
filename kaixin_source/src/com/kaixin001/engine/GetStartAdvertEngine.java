package com.kaixin001.engine;

import android.content.Context;
import android.content.SharedPreferences;
import com.kaixin001.model.Setting;
import com.kaixin001.model.StartAdvert;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.net.URI;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetStartAdvertEngine extends KXEngine
{
  private static GetStartAdvertEngine instance;
  private final String SP_ADV = "start_advert";
  private final String TAG = "GetStartAdvertEngine";
  private String action_link = "";
  private int duration = 0;
  private long end_time = 0L;
  private String image = "";
  private Context mContext;
  private SharedPreferences sp;
  private long start_time = 0L;

  private GetStartAdvertEngine(Context paramContext)
  {
    this.sp = paramContext.getSharedPreferences("start_advert", 0);
    this.mContext = paramContext;
  }

  public static GetStartAdvertEngine getInstance(Context paramContext)
  {
    if (instance == null)
      instance = new GetStartAdvertEngine(paramContext);
    return instance;
  }

  private String getStrContent(String paramString)
  {
    HttpProxy localHttpProxy = new HttpProxy(this.mContext);
    try
    {
      HttpGet localHttpGet = new HttpGet();
      localHttpGet.setHeader("Connection", "Keep-Alive");
      localHttpGet.setURI(new URI(Setting.getInstance().getNewHost() + paramString));
      String str = localHttpProxy.httpGet(localHttpGet);
      return str;
    }
    catch (Exception localException)
    {
      KXLog.e("GetStartAdvertEngine", "get start advert engine error", localException);
    }
    return null;
  }

  private boolean parseAdvertContent(String paramString)
    throws Exception
  {
    if (paramString == null);
    JSONObject localJSONObject1;
    do
    {
      return false;
      localJSONObject1 = super.parseJSON(this.mContext, paramString);
    }
    while (localJSONObject1 == null);
    JSONArray localJSONArray = localJSONObject1.optJSONArray("data");
    if (localJSONArray.length() > 0)
    {
      JSONObject localJSONObject2 = (JSONObject)localJSONArray.get(0);
      if (localJSONObject2 != null)
      {
        this.start_time = localJSONObject2.optInt("start_time", 0);
        this.end_time = localJSONObject2.optInt("end_time", 0);
        this.image = localJSONObject2.optString("image", "");
        this.duration = (1000 * localJSONObject2.optInt("duration", 0));
        this.action_link = localJSONObject2.optString("action_link", "");
        StartAdvert localStartAdvert2 = StartAdvert.getInstance();
        localStartAdvert2.setActionlink(this.action_link);
        localStartAdvert2.setDuration(this.duration);
        localStartAdvert2.setStartTime(this.start_time);
        localStartAdvert2.setEndTime(this.end_time);
        localStartAdvert2.setImage(this.image);
      }
    }
    while (true)
    {
      return true;
      StartAdvert localStartAdvert1 = StartAdvert.getInstance();
      localStartAdvert1.setActionlink(null);
      localStartAdvert1.setDuration(0);
      localStartAdvert1.setEndTime(0L);
      localStartAdvert1.setImage(null);
      localStartAdvert1.setStartTime(0L);
    }
  }

  public boolean getStartAdvert()
  {
    String str = Protocol.getInstance().getStartAdvertUrl();
    try
    {
      boolean bool = parseAdvertContent(getStrContent(str));
      return bool;
    }
    catch (Exception localException)
    {
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.GetStartAdvertEngine
 * JD-Core Version:    0.6.0
 */