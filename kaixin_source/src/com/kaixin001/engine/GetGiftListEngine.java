package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.GiftItem;
import com.kaixin001.model.GiftListModel;
import com.kaixin001.model.Setting;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetGiftListEngine extends KXEngine
{
  private static final String LOGTAG = "GetGiftListEngine";
  private static GetGiftListEngine instance;

  public static GetGiftListEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new GetGiftListEngine();
      GetGiftListEngine localGetGiftListEngine = instance;
      return localGetGiftListEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int getGiftList(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetGiftListRequest(paramString);
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
        KXLog.e("GetGiftListEngine", "getGiftList error", localException);
        str2 = null;
      }
    }
    return parseGiftListJSON(paramContext, str2);
  }

  public int parseGiftListJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
    if (localJSONObject1 == null)
      return -1001;
    if (Setting.getInstance().isDebug())
      KXLog.d("parseGiftListJSON", "strContent=" + localJSONObject1.toString());
    try
    {
      GiftListModel localGiftListModel = GiftListModel.getInstance();
      this.ret = localJSONObject1.optInt("ret", 0);
      if (this.ret == 1)
      {
        JSONArray localJSONArray = localJSONObject1.getJSONArray("list");
        int i;
        GiftItem[] arrayOfGiftItem;
        if (localJSONArray == null)
        {
          i = 0;
          arrayOfGiftItem = new GiftItem[i];
        }
        for (int j = 0; ; j++)
        {
          if (j >= i)
          {
            if (i > 0)
              localGiftListModel.gifts = arrayOfGiftItem;
            localGiftListModel.ctime = System.currentTimeMillis();
            localGiftListModel.version = "1";
            localGiftListModel.loadSuceess = true;
            localGiftListModel.saveGiftData(paramContext);
            return 1;
            i = localJSONArray.length();
            break;
          }
          JSONObject localJSONObject2 = localJSONArray.getJSONObject(j);
          arrayOfGiftItem[j] = new GiftItem(localJSONObject2.getString("gid"), localJSONObject2.getString("name"), localJSONObject2.getString("icon300"), localJSONObject2.getString("defaultps"));
        }
      }
    }
    catch (Exception localException)
    {
      KXLog.e("parseGiftListJSON", "parseGiftListJSON", localException);
      return -1001;
    }
    return this.ret;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.GetGiftListEngine
 * JD-Core Version:    0.6.0
 */