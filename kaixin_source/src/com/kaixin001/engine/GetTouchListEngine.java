package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.TouchItem;
import com.kaixin001.model.Setting;
import com.kaixin001.model.TouchListModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.network.HttpMethod;
import com.kaixin001.network.Protocol;
import com.kaixin001.network.XAuth;
import com.kaixin001.util.KXLog;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetTouchListEngine extends KXEngine
{
  private static final String LOGTAG = "GetTouchListEngine";
  private static GetTouchListEngine instance;

  public static GetTouchListEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new GetTouchListEngine();
      GetTouchListEngine localGetTouchListEngine = instance;
      return localGetTouchListEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int getTouchList(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    HashMap localHashMap = new HashMap();
    String str1 = Protocol.getInstance().getTouchToken();
    localHashMap.put("ctype", Setting.getInstance().getCType());
    localHashMap.put("oauth_token", User.getInstance().getOauthToken());
    XAuth.generateURLParams(str1, HttpMethod.GET.name(), localHashMap, XAuth.CONSUMER_KEY, XAuth.CONSUMER_SECRET, User.getInstance().getOauthTokenSecret());
    HttpConnection localHttpConnection = new HttpConnection(paramContext);
    String str2;
    try
    {
      String str3 = localHttpConnection.httpRequest(str1, HttpMethod.GET, localHashMap, null, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return -1002;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        str2 = null;
      }
    }
    return parseTouchListJSON(paramContext, str2);
  }

  public int parseTouchListJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
    if (localJSONObject1 == null)
      return -1001;
    if (Setting.getInstance().isDebug())
      KXLog.d("parseTouchListJSON", "strContent=" + localJSONObject1.toString());
    while (true)
    {
      JSONArray localJSONArray;
      TouchItem[] arrayOfTouchItem;
      int j;
      try
      {
        TouchListModel localTouchListModel = TouchListModel.getInstance();
        if (localJSONObject1.optString("num", "0").equals("0"))
          continue;
        this.ret = 1;
        if (this.ret != 1)
          break;
        localJSONArray = localJSONObject1.getJSONArray("list");
        if (localJSONArray == null)
        {
          i = 0;
          arrayOfTouchItem = new TouchItem[i];
          j = 0;
          if (j < i)
            break label183;
          if (i <= 0)
            continue;
          localTouchListModel.touchs = arrayOfTouchItem;
          localTouchListModel.ctime = System.currentTimeMillis();
          localTouchListModel.loadSuceess = true;
          localTouchListModel.saveTouchData(paramContext);
          return 1;
          this.ret = 0;
          continue;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("parseTouchListJSON", "parseTouchListJSON", localException);
        return -1001;
      }
      int i = localJSONArray.length();
      continue;
      label183: JSONObject localJSONObject2 = localJSONArray.getJSONObject(j);
      arrayOfTouchItem[j] = new TouchItem(localJSONObject2.getString("type"), localJSONObject2.getString("type_name"), localJSONObject2.getString("type_icon"));
      j++;
    }
    return this.ret;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.GetTouchListEngine
 * JD-Core Version:    0.6.0
 */