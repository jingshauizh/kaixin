package com.kaixin001.parser;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class GDTLinkParser extends BaseParser
{
  public Object parse(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
      try
      {
        JSONObject localJSONObject1 = new JSONObject(paramString);
        if (localJSONObject1.optInt("ret") == 0)
        {
          JSONObject localJSONObject2 = localJSONObject1.getJSONObject("data");
          return localJSONObject2;
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
 * Qualified Name:     com.kaixin001.parser.GDTLinkParser
 * JD-Core Version:    0.6.0
 */