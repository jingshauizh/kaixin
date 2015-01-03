package com.kaixin001.parser;

import android.text.TextUtils;
import com.kaixin001.item.NewsInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GDTDataParser extends BaseParser
{
  public Object parse(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
      try
      {
        JSONObject localJSONObject1 = new JSONObject(paramString);
        int i = localJSONObject1.optInt("ret");
        int j = localJSONObject1.optInt("rpt");
        if ((i == 0) && (j == 0))
        {
          JSONObject localJSONObject2 = (JSONObject)localJSONObject1.getJSONObject("data").getJSONObject("9079537217180471308").getJSONArray("list").get(0);
          NewsInfo localNewsInfo = new NewsInfo();
          localNewsInfo.mFuid = "-100";
          localNewsInfo.rl = localJSONObject2.optString("rl");
          localNewsInfo.viewid = localJSONObject2.optString("viewid");
          localNewsInfo.acttype = localJSONObject2.optString("acttype");
          localNewsInfo.targetid = localJSONObject2.optString("targetid");
          localNewsInfo.html_snippet = localJSONObject2.optString("html_snippet");
          localNewsInfo.img = localJSONObject2.optString("img");
          localNewsInfo.txt = localJSONObject2.optString("txt");
          localNewsInfo.ext = localJSONObject2.getJSONObject("ext");
          localNewsInfo.btn_render = localJSONObject2.getInt("btn_render");
          return localNewsInfo;
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    return null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.parser.GDTDataParser
 * JD-Core Version:    0.6.0
 */