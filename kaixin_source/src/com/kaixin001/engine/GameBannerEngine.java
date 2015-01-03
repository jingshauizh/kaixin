package com.kaixin001.engine;

import android.content.Context;
import com.kaixin001.item.AdvGameItem;
import com.kaixin001.model.AdvGameModel;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GameBannerEngine extends KXEngine
{
  public static final String LOCATION_APP = "3";
  public static final String LOCATION_GAME = "2";
  private static GameBannerEngine instance;

  public static GameBannerEngine getInstance()
  {
    if (instance == null)
      instance = new GameBannerEngine();
    return instance;
  }

  private boolean parseBannerJson(JSONObject paramJSONObject)
  {
    while (true)
    {
      int i;
      try
      {
        if (!paramJSONObject.optString("ret", "0").equals("1"))
          return false;
        JSONArray localJSONArray = paramJSONObject.optJSONArray("data");
        if (localJSONArray != null)
        {
          i = 0;
          if (i >= localJSONArray.length())
            return true;
          JSONObject localJSONObject = localJSONArray.getJSONObject(i);
          AdvGameItem localAdvGameItem = new AdvGameItem();
          localAdvGameItem.setId(localJSONObject.optString("id", ""));
          localAdvGameItem.setType(localJSONObject.optString("type", ""));
          localAdvGameItem.setAdvertImageUrl(localJSONObject.optString("picurl", ""));
          localAdvGameItem.setAdvertClickUrl(localJSONObject.optString("clickurl", ""));
          localAdvGameItem.setDisplayTime(localJSONObject.optInt("displayTime", 3));
          String str = localJSONObject.optString("location", "");
          if (!str.equals("2"))
            continue;
          AdvGameModel.getInstance().addGameBannerItem(localAdvGameItem);
          break label189;
          if (!str.equals("3"))
            break label189;
          AdvGameModel.getInstance().addAppBannerItem(localAdvGameItem);
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
      return false;
      label189: i++;
    }
  }

  public boolean getBannerData(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    AdvGameModel.getInstance().clear();
    String str1 = Protocol.getInstance().getGameBannerUrl(paramString);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    JSONObject localJSONObject;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (str2 == null)
        return false;
    }
    catch (Exception localException)
    {
      do
      {
        String str2;
        while (true)
        {
          localException.printStackTrace();
          str2 = null;
        }
        localJSONObject = super.parseJSON(paramContext, str2);
      }
      while (localJSONObject == null);
    }
    return parseBannerJson(localJSONObject);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.GameBannerEngine
 * JD-Core Version:    0.6.0
 */