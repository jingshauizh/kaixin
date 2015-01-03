package com.umeng.analytics;

import android.content.Context;
import android.content.SharedPreferences;
import com.umeng.common.Log;
import com.umeng.common.b;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import org.json.JSONException;
import org.json.JSONObject;

class h
{
  static SharedPreferences a(Context paramContext)
  {
    return paramContext.getSharedPreferences("mobclick_agent_user_" + paramContext.getPackageName(), 0);
  }

  static void a(Context paramContext, JSONObject paramJSONObject)
  {
    if (paramJSONObject == null)
      return;
    String str = g(paramContext);
    try
    {
      paramJSONObject.put("cache_version", b.d(paramContext));
      FileOutputStream localFileOutputStream = paramContext.openFileOutput(str, 0);
      localFileOutputStream.write(paramJSONObject.toString().getBytes());
      localFileOutputStream.flush();
      localFileOutputStream.close();
      return;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      Log.b("MobclickAgent", "cache message error", localFileNotFoundException);
      return;
    }
    catch (IOException localIOException)
    {
      Log.b("MobclickAgent", "cache message error", localIOException);
      return;
    }
    catch (Exception localException)
    {
      Log.b("MobclickAgent", "message is null", localException);
    }
  }

  static SharedPreferences b(Context paramContext)
  {
    return paramContext.getSharedPreferences("mobclick_agent_online_setting_" + paramContext.getPackageName(), 0);
  }

  static void b(Context paramContext, JSONObject paramJSONObject)
  {
    if (paramJSONObject == null)
      return;
    try
    {
      JSONObject localJSONObject2 = paramJSONObject.optJSONObject("body");
      localJSONObject1 = localJSONObject2;
      a(paramContext, localJSONObject1);
      return;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        JSONObject localJSONObject1 = null;
      }
    }
  }

  static SharedPreferences c(Context paramContext)
  {
    return paramContext.getSharedPreferences("mobclick_agent_header_" + paramContext.getPackageName(), 0);
  }

  static SharedPreferences d(Context paramContext)
  {
    return paramContext.getSharedPreferences("mobclick_agent_update_" + paramContext.getPackageName(), 0);
  }

  static SharedPreferences e(Context paramContext)
  {
    return paramContext.getSharedPreferences("mobclick_agent_state_" + paramContext.getPackageName(), 0);
  }

  static String f(Context paramContext)
  {
    return "mobclick_agent_header_" + paramContext.getPackageName();
  }

  static String g(Context paramContext)
  {
    return "mobclick_agent_cached_" + paramContext.getPackageName();
  }

  static JSONObject h(Context paramContext)
  {
    JSONObject localJSONObject = new JSONObject();
    SharedPreferences localSharedPreferences = a(paramContext);
    try
    {
      if (localSharedPreferences.getInt("gender", -1) != -1)
        localJSONObject.put("sex", localSharedPreferences.getInt("gender", -1));
      if (localSharedPreferences.getInt("age", -1) != -1)
        localJSONObject.put("age", localSharedPreferences.getInt("age", -1));
      if (!"".equals(localSharedPreferences.getString("user_id", "")))
        localJSONObject.put("id", localSharedPreferences.getString("user_id", ""));
      if (!"".equals(localSharedPreferences.getString("id_source", "")))
        localJSONObject.put("url", URLEncoder.encode(localSharedPreferences.getString("id_source", "")));
      int i = localJSONObject.length();
      if (i > 0)
        return localJSONObject;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  static JSONObject i(Context paramContext)
  {
    String str1 = g(paramContext);
    try
    {
      FileInputStream localFileInputStream = paramContext.openFileInput(str1);
      byte[] arrayOfByte = new byte[1024];
      int i;
      for (String str2 = ""; ; str2 = str2 + new String(arrayOfByte, 0, i))
      {
        i = localFileInputStream.read(arrayOfByte);
        if (i == -1)
          break;
      }
      localFileInputStream.close();
      int j = str2.length();
      if (j == 0)
        return null;
      try
      {
        JSONObject localJSONObject = new JSONObject(str2);
        return localJSONObject;
      }
      catch (JSONException localJSONException)
      {
        j(paramContext);
        localJSONException.printStackTrace();
        return null;
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      return null;
    }
    catch (IOException localIOException)
    {
    }
    return null;
  }

  static void j(Context paramContext)
  {
    paramContext.deleteFile(f(paramContext));
    paramContext.deleteFile(g(paramContext));
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.analytics.h
 * JD-Core Version:    0.6.0
 */