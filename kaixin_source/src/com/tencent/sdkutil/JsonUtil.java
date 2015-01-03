package com.tencent.sdkutil;

import android.os.Bundle;
import android.webkit.JavascriptInterface;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil
{
  JSONObject mJsonObject;

  public JsonUtil()
  {
  }

  public JsonUtil(String paramString)
  {
    this.mJsonObject = parseJson(paramString);
  }

  public static String parseBundleToJsonString(Bundle paramBundle)
  {
    StringBuffer localStringBuffer = new StringBuffer("{");
    Iterator localIterator = paramBundle.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Object localObject = paramBundle.get(str);
      localStringBuffer.append(str + ":" + localObject.toString());
      localStringBuffer.append(",");
    }
    localStringBuffer.append("}");
    return localStringBuffer.toString();
  }

  public static JSONObject parseJson(String paramString)
  {
    if (paramString.equals("false"))
      paramString = "{value : false}";
    if (paramString.equals("true"))
      paramString = "{value : true}";
    if (paramString.contains("allback("))
      paramString = paramString.replaceFirst("[\\s\\S]*allback\\(([\\s\\S]*)\\);[^\\)]*\\z", "$1").trim();
    if (paramString.contains("online"))
      paramString = "{online:" + paramString.charAt(-2 + paramString.length()) + "}";
    return new JSONObject(paramString);
  }

  public static Bundle parseJsonToBundleWithFile(JSONObject paramJSONObject)
  {
    Iterator localIterator1 = paramJSONObject.keys();
    Bundle localBundle = new Bundle();
    while (localIterator1.hasNext())
    {
      String str1 = (String)localIterator1.next();
      if (str1.equals("file"))
      {
        Iterator localIterator2 = paramJSONObject.getJSONObject(str1).keys();
        while (localIterator2.hasNext())
        {
          String str2 = (String)localIterator2.next();
          localBundle.putByteArray(str2, (byte[])(byte[])TemporaryStorage.get(str2));
        }
        continue;
      }
      localBundle.putString(str1, paramJSONObject.getString(str1));
    }
    return localBundle;
  }

  @JavascriptInterface
  public boolean getBoolean(String paramString, boolean paramBoolean)
  {
    try
    {
      boolean bool = this.mJsonObject.getBoolean(paramString);
      return bool;
    }
    catch (JSONException localJSONException)
    {
    }
    return paramBoolean;
  }

  @JavascriptInterface
  public double getFloat(String paramString, double paramDouble)
  {
    try
    {
      double d = this.mJsonObject.getDouble(paramString);
      return d;
    }
    catch (JSONException localJSONException)
    {
    }
    return paramDouble;
  }

  @JavascriptInterface
  public int getInt(String paramString, int paramInt)
  {
    try
    {
      int i = this.mJsonObject.getInt(paramString);
      return i;
    }
    catch (JSONException localJSONException)
    {
    }
    return paramInt;
  }

  @JavascriptInterface
  public String getString(String paramString)
  {
    try
    {
      String str = this.mJsonObject.getString(paramString);
      return str;
    }
    catch (JSONException localJSONException)
    {
    }
    return null;
  }

  public JSONObject getmJsonObject()
  {
    return this.mJsonObject;
  }

  @JavascriptInterface
  public void setJson(String paramString)
  {
    this.mJsonObject = parseJson(paramString);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.JsonUtil
 * JD-Core Version:    0.6.0
 */