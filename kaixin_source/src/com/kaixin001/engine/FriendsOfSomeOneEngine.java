package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.HomeVisitorItem;
import com.kaixin001.model.HomeVisitorModel;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FriendsOfSomeOneEngine extends KXEngine
{
  private static final String LOGTAG = "FriendsOfSomeOneEngine";
  private static FriendsOfSomeOneEngine instance;

  public static FriendsOfSomeOneEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new FriendsOfSomeOneEngine();
      FriendsOfSomeOneEngine localFriendsOfSomeOneEngine = instance;
      return localFriendsOfSomeOneEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private boolean parseFriendsOfSomeoneJSON(Context paramContext, String paramString1, String paramString2, HomeVisitorModel paramHomeVisitorModel)
    throws JSONException
  {
    int i = 0;
    if (paramHomeVisitorModel == null)
      return false;
    int k;
    int m;
    try
    {
      JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString2);
      if (localJSONObject1 == null)
      {
        i = 0;
      }
      else
      {
        int j = this.ret;
        i = 0;
        if (j == 1)
        {
          if (paramString1.equals("0"))
            paramHomeVisitorModel.clear();
          paramHomeVisitorModel.setTotal(Integer.valueOf(localJSONObject1.getString("total")).intValue());
          JSONArray localJSONArray = localJSONObject1.getJSONArray("data");
          k = localJSONArray.length();
          if (k <= 0)
            break label190;
          m = 0;
          break label183;
          JSONObject localJSONObject2 = localJSONArray.getJSONObject(m);
          paramHomeVisitorModel.addVisitor(new HomeVisitorItem(localJSONObject2.optString("uid"), localJSONObject2.optString("real_name"), localJSONObject2.optString("icon50"), localJSONObject2.optString("gender"), localJSONObject2.optString("type"), null, localJSONObject2.optString("online")));
          m++;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    while (true)
    {
      return i;
      label183: if (m < k)
        break;
      label190: i = 1;
    }
  }

  public boolean doGetFriendsOfSomeoneRequest(Context paramContext, String paramString1, String paramString2, String paramString3, HomeVisitorModel paramHomeVisitorModel)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetFriendsOfSomeone(paramString1, paramString2, paramString3);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      boolean bool1 = TextUtils.isEmpty(str2);
      i = 0;
      if (bool1);
    }
    catch (Exception localException)
    {
      try
      {
        boolean bool2 = parseFriendsOfSomeoneJSON(paramContext, paramString2, str2, paramHomeVisitorModel);
        int i = bool2;
        return i;
        localException = localException;
        KXLog.e("FriendsOfSomeOneEngine", "doGetHomeVisitorsRequest error", localException);
        String str2 = null;
      }
      catch (JSONException localJSONException)
      {
        KXLog.e("FriendsOfSomeOneEngine", localJSONException.getMessage());
      }
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.FriendsOfSomeOneEngine
 * JD-Core Version:    0.6.0
 */