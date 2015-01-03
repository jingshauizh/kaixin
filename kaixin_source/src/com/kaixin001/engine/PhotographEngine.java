package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.PhotoGraphItem;
import com.kaixin001.model.PhotographModel;
import com.kaixin001.model.Setting;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import org.json.JSONArray;
import org.json.JSONObject;

public class PhotographEngine extends KXEngine
{
  private static final String LOGTAG = "NewsAdvertEngine";
  private static PhotographEngine instance;

  public static PhotographEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new PhotographEngine();
      PhotographEngine localPhotographEngine = instance;
      return localPhotographEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int getPhoto(Context paramContext)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetPhotoRequest();
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return -1002;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("NewsAdvertEngine", "getGiftList error", localException);
        str2 = null;
      }
    }
    return parseAdvertJSON(paramContext, str2);
  }

  public int parseAdvertJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
    if (localJSONObject1 == null)
      return -1001;
    if (Setting.getInstance().isDebug())
      KXLog.d("parseAdvertJSON", "strContent=" + localJSONObject1.toString());
    try
    {
      PhotographModel localPhotographModel = PhotographModel.getInstance();
      this.ret = localJSONObject1.optInt("ret", 0);
      if (this.ret == 1)
      {
        localPhotographModel.uid = localJSONObject1.optInt("uid", 0);
        JSONArray localJSONArray = localJSONObject1.getJSONArray("data");
        int i;
        PhotoGraphItem[] arrayOfPhotoGraphItem;
        if (localJSONArray == null)
        {
          i = 0;
          if (i > 0)
            arrayOfPhotoGraphItem = new PhotoGraphItem[i];
        }
        else
        {
          for (int j = 0; ; j++)
          {
            if (j >= i)
            {
              localPhotographModel.item = arrayOfPhotoGraphItem;
              break label237;
              i = localJSONArray.length();
              break;
            }
            JSONObject localJSONObject2 = localJSONArray.getJSONObject(j).getJSONObject("activity");
            arrayOfPhotoGraphItem[j] = new PhotoGraphItem(localJSONObject2.optString("id"), localJSONObject2.optString("bannerUrl"), localJSONObject2.optString("url"), localJSONObject2.optString("title"), localJSONObject2.optString("actionUpload"));
          }
        }
        localPhotographModel.clear();
      }
    }
    catch (Exception localException)
    {
      KXLog.e("parseGiftListJSON", "parseGiftListJSON", localException);
      return -1001;
    }
    return this.ret;
    label237: return 1;
  }

  public void sendgetPhotoClose(Context paramContext)
  {
    String str1 = PhotographModel.getInstance().item[0].id;
    String str2 = Protocol.getInstance().makeGetPhotoCloseRequest(str1);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      localHttpProxy.httpGet(str2, null, null);
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("NewsAdvertEngine", "getGiftList error", localException);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.PhotographEngine
 * JD-Core Version:    0.6.0
 */