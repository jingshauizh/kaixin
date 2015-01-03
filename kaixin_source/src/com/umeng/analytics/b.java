package com.umeng.analytics;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.umeng.common.Log;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class b
  implements g
{
  private final String A = "appkey";
  private final String B = "body";
  private final String C = "session_id";
  private final String D = "date";
  private final String E = "time";
  private final String F = "start_millis";
  private final String G = "end_millis";
  private final String H = "duration";
  private final String I = "activities";
  private final String J = "header";
  private final String K = "uptr";
  private final String L = "dntr";
  private final String M = "acc";
  private final String N = "tag";
  private final String O = "label";
  private final String P = "id";
  private final String Q = "ts";
  private final String R = "du";
  private final String S = "context";
  private final String T = "last_config_time";
  private final String U = "report_policy";
  private final String V = "online_params";
  private final String W = "report_interval";
  String a = null;
  String b = null;
  UmengOnlineConfigureListener c = null;
  String d = "";
  String e = "";
  private final a f = new a();
  private final d g = new d();
  private final ReportPolicy h = new ReportPolicy();
  private String i;
  private final Handler j;
  private final int k = 0;
  private final int l = 1;
  private final int m = 2;
  private final int n = 3;
  private final int o = 4;
  private final int p = 5;
  private final int q = 6;
  private final String r = "type";
  private final String s = "error";
  private final String t = "event";
  private final String u = "ekv";
  private final String v = "launch";
  private final String w = "flush";
  private final String x = "terminate";
  private final String y = "online_config";
  private final String z = "cmd_cache_buffer";

  b()
  {
    HandlerThread localHandlerThread = new HandlerThread("MobclickAgent");
    localHandlerThread.start();
    this.j = new Handler(localHandlerThread.getLooper());
  }

  private String a(Context paramContext, String paramString, long paramLong)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramLong).append(paramString).append(com.umeng.common.b.g.b(com.umeng.common.b.f(paramContext)));
    return com.umeng.common.b.g.a(localStringBuilder.toString());
  }

  private String a(Context paramContext, String paramString, SharedPreferences paramSharedPreferences)
  {
    d(paramContext, paramSharedPreferences);
    long l1 = System.currentTimeMillis();
    String str = a(paramContext, paramString, l1);
    SharedPreferences.Editor localEditor = paramSharedPreferences.edit();
    localEditor.putString("appkey", paramString);
    localEditor.putString("session_id", str);
    localEditor.putLong("start_millis", l1);
    localEditor.putLong("end_millis", -1L);
    localEditor.putLong("duration", 0L);
    localEditor.putString("activities", "");
    localEditor.remove("last_terminate_location_time");
    localEditor.commit();
    c(paramContext, paramSharedPreferences);
    return str;
  }

  private String a(Context paramContext, JSONObject paramJSONObject, String paramString1, boolean paramBoolean, String paramString2)
  {
    HttpPost localHttpPost = new HttpPost(paramString1);
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 10000);
    HttpConnectionParams.setSoTimeout(localBasicHttpParams, 30000);
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient(localBasicHttpParams);
    localHttpPost.addHeader("X-Umeng-Sdk", n(paramContext));
    try
    {
      String str1 = f.a(paramContext);
      if (str1 != null)
      {
        HttpHost localHttpHost = new HttpHost(str1, 80);
        localDefaultHttpClient.getParams().setParameter("http.route.default-proxy", localHttpHost);
      }
      String str2 = paramJSONObject.toString();
      Log.a("MobclickAgent", str2);
      if ((e.s) && (!paramBoolean))
      {
        byte[] arrayOfByte = com.umeng.common.b.f.a("content=" + str2, "utf-8");
        localHttpPost.addHeader("Content-Encoding", "deflate");
        localHttpPost.setEntity(new InputStreamEntity(new ByteArrayInputStream(arrayOfByte), com.umeng.common.b.f.a));
      }
      while (true)
      {
        localEditor = h.c(paramContext).edit();
        Date localDate = new Date();
        HttpResponse localHttpResponse = localDefaultHttpClient.execute(localHttpPost);
        long l1 = new Date().getTime() - localDate.getTime();
        if (localHttpResponse.getStatusLine().getStatusCode() != 200)
          break;
        Log.a("MobclickAgent", "Sent message to " + paramString1);
        localEditor.putLong("req_time", l1);
        localEditor.commit();
        HttpEntity localHttpEntity = localHttpResponse.getEntity();
        if (localHttpEntity == null)
          break label421;
        return a(localHttpEntity.getContent());
        ArrayList localArrayList = new ArrayList(1);
        localArrayList.add(new BasicNameValuePair("content", str2));
        localHttpPost.setEntity(new UrlEncodedFormEntity(localArrayList, "UTF-8"));
      }
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      SharedPreferences.Editor localEditor;
      Log.a("MobclickAgent", "ClientProtocolException,Failed to send message.", localClientProtocolException);
      return null;
      localEditor.putLong("req_time", -1L);
      return null;
    }
    catch (IOException localIOException)
    {
      Log.a("MobclickAgent", "IOException,Failed to send message.", localIOException);
    }
    label421: return null;
  }

  private String a(InputStream paramInputStream)
  {
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream), 8192);
    StringBuilder localStringBuilder = new StringBuilder();
    try
    {
      while (true)
      {
        String str = localBufferedReader.readLine();
        if (str == null)
          break;
        localStringBuilder.append(str + "\n");
      }
    }
    catch (IOException localIOException2)
    {
      Log.b("MobclickAgent", "Caught IOException in convertStreamToString()", localIOException2);
      try
      {
        paramInputStream.close();
        return null;
        try
        {
          paramInputStream.close();
          return localStringBuilder.toString();
        }
        catch (IOException localIOException4)
        {
          Log.b("MobclickAgent", "Caught IOException in convertStreamToString()", localIOException4);
          return null;
        }
      }
      catch (IOException localIOException3)
      {
        Log.b("MobclickAgent", "Caught IOException in convertStreamToString()", localIOException3);
        return null;
      }
    }
    finally
    {
      try
      {
        paramInputStream.close();
        throw localObject;
      }
      catch (IOException localIOException1)
      {
        Log.b("MobclickAgent", "Caught IOException in convertStreamToString()", localIOException1);
      }
    }
    return null;
  }

  private JSONArray a(JSONObject paramJSONObject, JSONArray paramJSONArray)
  {
    while (true)
    {
      try
      {
        String str1 = paramJSONObject.getString("tag");
        if (!paramJSONObject.has("label"))
          break label250;
        str2 = paramJSONObject.getString("label");
        String str3 = paramJSONObject.getString("date");
        int i1 = -1 + paramJSONArray.length();
        if (i1 >= 0)
        {
          JSONObject localJSONObject = (JSONObject)paramJSONArray.get(i1);
          if ((str2 != null) || (localJSONObject.has("label")))
            continue;
          if ((!str1.equals(localJSONObject.get("tag"))) || (!str3.equals(localJSONObject.get("date"))))
            continue;
          localJSONObject.put("acc", 1 + localJSONObject.getInt("acc"));
          i2 = 1;
          if (i2 != 0)
            break label248;
          paramJSONArray.put(paramJSONObject);
          return paramJSONArray;
          if ((str2 == null) || (!localJSONObject.has("label")) || (!str1.equals(localJSONObject.get("tag"))) || (!str2.equals(localJSONObject.get("label"))) || (!str3.equals(localJSONObject.get("date"))))
            continue;
          localJSONObject.put("acc", 1 + localJSONObject.getInt("acc"));
          i2 = 1;
          continue;
          i1--;
          continue;
        }
      }
      catch (Exception localException)
      {
        Log.a("MobclickAgent", "custom log merge error in tryToSendMessage", localException);
        paramJSONArray.put(paramJSONObject);
        return paramJSONArray;
      }
      int i2 = 0;
      continue;
      label248: return paramJSONArray;
      label250: String str2 = null;
    }
  }

  private void a(Context paramContext, SharedPreferences paramSharedPreferences)
  {
    long l1 = System.currentTimeMillis();
    if ((e.g) && (l1 - paramSharedPreferences.getLong("last_terminate_location_time", 0L) > 10000L))
    {
      Location localLocation = com.umeng.common.b.l(paramContext);
      if (localLocation != null)
      {
        long l2 = paramSharedPreferences.getLong("gps_time", 0L);
        if (localLocation.getTime() != l2)
          paramSharedPreferences.edit().putFloat("lng", (float)localLocation.getLongitude()).putFloat("lat", (float)localLocation.getLatitude()).putFloat("alt", (float)localLocation.getAltitude()).putLong("gps_time", localLocation.getTime()).putLong("last_terminate_location_time", l1).commit();
      }
    }
  }

  private void a(Context paramContext, SharedPreferences paramSharedPreferences, String paramString1, String paramString2, long paramLong, int paramInt)
  {
    String str1 = paramSharedPreferences.getString("session_id", "");
    String str2 = com.umeng.common.b.g.a();
    String str3 = str2.split(" ")[0];
    String str4 = str2.split(" ")[1];
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("type", "event");
      localJSONObject.put("session_id", str1);
      localJSONObject.put("date", str3);
      localJSONObject.put("time", str4);
      localJSONObject.put("tag", paramString1);
      if (!TextUtils.isEmpty(paramString2))
        localJSONObject.put("label", paramString2);
      if (paramLong > 0L)
        localJSONObject.put("du", paramLong);
      localJSONObject.put("acc", paramInt);
      this.j.post(new b(this, paramContext, localJSONObject));
      return;
    }
    catch (JSONException localJSONException)
    {
      Log.a("MobclickAgent", "json error in emitCustomLogReport", localJSONException);
    }
  }

  private void a(Context paramContext, SharedPreferences paramSharedPreferences, String paramString, JSONObject paramJSONObject)
  {
    String str = paramSharedPreferences.getString("session_id", "");
    JSONObject localJSONObject = new JSONObject();
    JSONArray localJSONArray = new JSONArray();
    try
    {
      paramJSONObject.put("id", paramString);
      paramJSONObject.put("ts", System.currentTimeMillis() / 1000L);
      localJSONArray.put(paramJSONObject);
      localJSONObject.put("type", "ekv");
      localJSONObject.put(str, localJSONArray);
      this.j.post(new b(this, paramContext, localJSONObject));
      return;
    }
    catch (JSONException localJSONException)
    {
      Log.a("MobclickAgent", "json error in emitCustomLogReport", localJSONException);
      localJSONException.printStackTrace();
    }
  }

  private void a(Context paramContext, JSONArray paramJSONArray)
  {
    for (int i1 = 0; ; i1++)
      if (i1 < paramJSONArray.length())
        try
        {
          JSONObject localJSONObject = paramJSONArray.getJSONObject(i1);
          if ((localJSONObject == null) || (!localJSONObject.has("date")) || (!localJSONObject.has("time")) || (!localJSONObject.has("context")))
            continue;
          if (localJSONObject.has("version"))
          {
            if ((localJSONObject.getString("version") == null) || (!localJSONObject.getString("version").equals(com.umeng.common.b.d(paramContext))))
              continue;
            localJSONObject.remove("version");
          }
          this.j.post(new b(this, paramContext, localJSONObject));
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      else
        return;
  }

  private void a(String paramString)
  {
    Log.a("MobclickAgent", paramString);
  }

  private boolean a(SharedPreferences paramSharedPreferences)
  {
    long l1 = paramSharedPreferences.getLong("end_millis", -1L);
    return System.currentTimeMillis() - l1 > e.d;
  }

  private boolean a(JSONObject paramJSONObject1, JSONObject paramJSONObject2)
  {
    try
    {
      String str1 = (String)paramJSONObject1.remove("cache_version");
      String str2 = paramJSONObject2.getString("version_code");
      if (str1 != null)
      {
        boolean bool = str1.equals(str2);
        if (bool)
          return false;
      }
    }
    catch (Exception localException)
    {
      Log.a("MobclickAgent", "Fail to filter message", localException);
    }
    return true;
  }

  private String b(Context paramContext, SharedPreferences paramSharedPreferences)
  {
    Long localLong = Long.valueOf(System.currentTimeMillis());
    SharedPreferences.Editor localEditor = paramSharedPreferences.edit();
    localEditor.putLong("start_millis", localLong.longValue());
    localEditor.putLong("end_millis", -1L);
    localEditor.commit();
    return paramSharedPreferences.getString("session_id", null);
  }

  private JSONArray b(JSONObject paramJSONObject, JSONArray paramJSONArray)
  {
    if ((paramJSONArray == null) || (paramJSONObject == null))
      return paramJSONArray;
    while (true)
    {
      int i1;
      try
      {
        String str = (String)paramJSONObject.keys().next();
        i1 = -1 + paramJSONArray.length();
        if (i1 < 0)
          break;
        JSONObject localJSONObject = (JSONObject)paramJSONArray.get(i1);
        if (localJSONObject.has(str))
        {
          JSONArray localJSONArray = paramJSONObject.getJSONArray(str);
          localJSONObject.getJSONArray(str).put((JSONObject)localJSONArray.get(0));
          return paramJSONArray;
        }
      }
      catch (Exception localException)
      {
        Log.a("MobclickAgent", "custom log merge error in tryToSendMessage", localException);
        return paramJSONArray;
      }
      i1--;
    }
    paramJSONArray.put(paramJSONObject);
    return paramJSONArray;
  }

  private void b(Context paramContext, String paramString1, String paramString2, long paramLong, int paramInt)
  {
    monitorenter;
    try
    {
      SharedPreferences localSharedPreferences = h.e(paramContext);
      if (localSharedPreferences == null);
      while (true)
      {
        return;
        a(paramContext, localSharedPreferences, paramString1, paramString2, paramLong, paramInt);
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void b(Context paramContext, String paramString, Map<String, String> paramMap, long paramLong)
  {
    monitorenter;
    try
    {
      SharedPreferences localSharedPreferences = h.e(paramContext);
      if (localSharedPreferences == null);
      while (true)
      {
        return;
        try
        {
          JSONObject localJSONObject = new JSONObject();
          Iterator localIterator = paramMap.entrySet().iterator();
          int i2;
          for (int i1 = 0; (localIterator.hasNext()) && (i1 < 10); i1 = i2)
          {
            i2 = i1 + 1;
            Map.Entry localEntry = (Map.Entry)localIterator.next();
            localJSONObject.put((String)localEntry.getKey(), (String)localEntry.getValue());
          }
          if (paramLong > 0L)
            localJSONObject.put("du", paramLong);
          a(paramContext, localSharedPreferences, paramString, localJSONObject);
        }
        catch (Exception localException)
        {
          Log.a("MobclickAgent", "exception when convert map to json");
        }
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void b(Context paramContext, String paramString1, Map<String, String> paramMap, String paramString2)
  {
    monitorenter;
    try
    {
      SharedPreferences localSharedPreferences = h.e(paramContext);
      if (localSharedPreferences == null);
      while (true)
      {
        return;
        try
        {
          f(paramContext, "_kvts" + paramString1 + paramString2);
          JSONObject localJSONObject = new JSONObject();
          Iterator localIterator = paramMap.entrySet().iterator();
          int i2;
          for (int i1 = 0; (localIterator.hasNext()) && (i1 < 10); i1 = i2)
          {
            i2 = i1 + 1;
            Map.Entry localEntry = (Map.Entry)localIterator.next();
            localJSONObject.put((String)localEntry.getKey(), (String)localEntry.getValue());
          }
          localSharedPreferences.edit().putString("_kvvl" + paramString1 + paramString2, localJSONObject.toString()).commit();
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void b(Context paramContext, JSONObject paramJSONObject)
  {
    Log.a("MobclickAgent", "start to check onlineConfig info ...");
    String str = a(paramContext, paramJSONObject, "http://oc.umeng.com/check_config_update", true, "online_config");
    if (str == null)
      str = a(paramContext, paramJSONObject, "http://oc.umeng.co/check_config_update", true, "online_config");
    if (str != null)
    {
      Log.a("MobclickAgent", "get onlineConfig info succeed !");
      d(paramContext, str);
      return;
    }
    if (this.c != null)
      this.c.onDataReceived(null);
    Log.a("MobclickAgent", "get onlineConfig info failed !");
  }

  private void c(Context paramContext, SharedPreferences paramSharedPreferences)
  {
    String str1 = paramSharedPreferences.getString("session_id", null);
    if (str1 == null)
    {
      Log.a("MobclickAgent", "Missing session_id, ignore message");
      return;
    }
    String str2 = com.umeng.common.b.g.a();
    String str3 = str2.split(" ")[0];
    String str4 = str2.split(" ")[1];
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("type", "launch");
      localJSONObject.put("session_id", str1);
      localJSONObject.put("date", str3);
      localJSONObject.put("time", str4);
      if (e.g)
      {
        Location localLocation = com.umeng.common.b.l(paramContext);
        if (localLocation != null)
        {
          double d1 = localLocation.getLongitude();
          double d2 = localLocation.getLatitude();
          double d3 = localLocation.getAltitude();
          long l1 = localLocation.getTime();
          if (l1 != paramSharedPreferences.getLong("gps_time", 0L))
          {
            localJSONObject.put("lng", d1);
            localJSONObject.put("lat", d2);
            localJSONObject.put("alt", d3);
            localJSONObject.put("gps_time", l1);
            paramSharedPreferences.edit().putLong("gps_time", l1).commit();
          }
        }
      }
      this.j.post(new b(this, paramContext, localJSONObject));
      return;
    }
    catch (JSONException localJSONException)
    {
      Log.b("MobclickAgent", "json error in emitNewSessionReport", localJSONException);
    }
  }

  private void d(Context paramContext, SharedPreferences paramSharedPreferences)
  {
    String str1 = paramSharedPreferences.getString("session_id", null);
    if (str1 == null)
    {
      a("Missing session_id, ignore message in emitLastEndSessionReport");
      return;
    }
    Long localLong = Long.valueOf(paramSharedPreferences.getLong("duration", -1L));
    if (localLong.longValue() <= 0L)
      localLong = Long.valueOf(0L);
    String str2 = com.umeng.common.b.g.a();
    String str3 = str2.split(" ")[0];
    String str4 = str2.split(" ")[1];
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("type", "terminate");
      localJSONObject.put("session_id", str1);
      localJSONObject.put("date", str3);
      localJSONObject.put("time", str4);
      localJSONObject.put("duration", String.valueOf(localLong.longValue() / 1000L));
      if (e.h)
      {
        String str5 = paramSharedPreferences.getString("activities", "");
        if (!"".equals(str5))
        {
          String[] arrayOfString = str5.split(";");
          JSONArray localJSONArray = new JSONArray();
          for (int i1 = 0; i1 < arrayOfString.length; i1++)
            localJSONArray.put(new JSONArray(arrayOfString[i1]));
          localJSONObject.put("activities", localJSONArray);
        }
      }
      long[] arrayOfLong = e(paramContext, paramSharedPreferences);
      if (arrayOfLong != null)
      {
        localJSONObject.put("uptr", arrayOfLong[1]);
        localJSONObject.put("dntr", arrayOfLong[0]);
      }
      if ((e.g) && (paramSharedPreferences.contains("last_terminate_location_time")))
      {
        localJSONObject.put("lat", paramSharedPreferences.getFloat("lat", 0.0F));
        localJSONObject.put("lng", paramSharedPreferences.getFloat("lng", 0.0F));
        localJSONObject.put("alt", paramSharedPreferences.getFloat("alt", 0.0F));
        localJSONObject.put("gps_time", paramSharedPreferences.getLong("gps_time", 0L));
      }
      this.j.post(new b(this, paramContext, localJSONObject));
      return;
    }
    catch (JSONException localJSONException)
    {
      Log.b("MobclickAgent", "json error in emitLastEndSessionReport", localJSONException);
    }
  }

  // ERROR //
  private void d(Context paramContext, String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 809	com/umeng/analytics/h:b	(Landroid/content/Context;)Landroid/content/SharedPreferences;
    //   4: astore_3
    //   5: new 383	org/json/JSONObject
    //   8: dup
    //   9: aload_2
    //   10: invokespecial 810	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   13: astore 4
    //   15: aload 4
    //   17: ldc 220
    //   19: invokevirtual 531	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   22: ifeq +30 -> 52
    //   25: aload_3
    //   26: invokeinterface 301 1 0
    //   31: ldc_w 812
    //   34: aload 4
    //   36: ldc 220
    //   38: invokevirtual 527	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   41: invokeinterface 307 3 0
    //   46: invokeinterface 323 1 0
    //   51: pop
    //   52: ldc2_w 312
    //   55: lstore 6
    //   57: aload 4
    //   59: ldc 232
    //   61: invokevirtual 531	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   64: ifeq +21 -> 85
    //   67: aload 4
    //   69: ldc 232
    //   71: invokevirtual 552	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   74: istore 19
    //   76: iload 19
    //   78: sipush 1000
    //   81: imul
    //   82: i2l
    //   83: lstore 6
    //   85: aload 4
    //   87: ldc 224
    //   89: invokevirtual 531	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   92: ifeq +20 -> 112
    //   95: aload_0
    //   96: getfield 80	com/umeng/analytics/b:h	Lcom/umeng/analytics/ReportPolicy;
    //   99: aload_1
    //   100: aload 4
    //   102: ldc 224
    //   104: invokevirtual 552	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   107: lload 6
    //   109: invokevirtual 815	com/umeng/analytics/ReportPolicy:a	(Landroid/content/Context;IJ)V
    //   112: aload 4
    //   114: ldc 228
    //   116: invokevirtual 531	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   119: istore 11
    //   121: aconst_null
    //   122: astore 12
    //   124: iload 11
    //   126: ifeq +168 -> 294
    //   129: new 383	org/json/JSONObject
    //   132: dup
    //   133: aload 4
    //   135: ldc 228
    //   137: invokevirtual 527	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   140: invokespecial 810	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   143: astore 13
    //   145: aload 13
    //   147: invokevirtual 706	org/json/JSONObject:keys	()Ljava/util/Iterator;
    //   150: astore 14
    //   152: aload_3
    //   153: invokeinterface 301 1 0
    //   158: astore 15
    //   160: aload 14
    //   162: invokeinterface 734 1 0
    //   167: ifeq +89 -> 256
    //   170: aload 14
    //   172: invokeinterface 712 1 0
    //   177: checkcast 545	java/lang/String
    //   180: astore 17
    //   182: aload 15
    //   184: aload 17
    //   186: aload 13
    //   188: aload 17
    //   190: invokevirtual 527	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   193: invokeinterface 307 3 0
    //   198: pop
    //   199: goto -39 -> 160
    //   202: astore 10
    //   204: ldc 238
    //   206: ldc_w 817
    //   209: aload 10
    //   211: invokestatic 495	com/umeng/common/Log:a	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Exception;)V
    //   214: return
    //   215: astore 21
    //   217: ldc 238
    //   219: ldc_w 819
    //   222: invokestatic 388	com/umeng/common/Log:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   225: return
    //   226: astore 5
    //   228: ldc 238
    //   230: ldc_w 821
    //   233: aload 5
    //   235: invokestatic 495	com/umeng/common/Log:a	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Exception;)V
    //   238: goto -186 -> 52
    //   241: astore 9
    //   243: ldc 238
    //   245: ldc_w 823
    //   248: aload 9
    //   250: invokestatic 495	com/umeng/common/Log:a	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Exception;)V
    //   253: goto -141 -> 112
    //   256: aload 15
    //   258: invokeinterface 323 1 0
    //   263: pop
    //   264: ldc 238
    //   266: new 259	java/lang/StringBuilder
    //   269: dup
    //   270: invokespecial 260	java/lang/StringBuilder:<init>	()V
    //   273: ldc_w 825
    //   276: invokevirtual 267	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   279: aload 13
    //   281: invokevirtual 828	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   284: invokevirtual 281	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   287: invokestatic 388	com/umeng/common/Log:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   290: aload 13
    //   292: astore 12
    //   294: aload_0
    //   295: getfield 100	com/umeng/analytics/b:c	Lcom/umeng/analytics/UmengOnlineConfigureListener;
    //   298: ifnull -84 -> 214
    //   301: aload_0
    //   302: getfield 100	com/umeng/analytics/b:c	Lcom/umeng/analytics/UmengOnlineConfigureListener;
    //   305: aload 12
    //   307: invokeinterface 770 2 0
    //   312: return
    //   313: astore 8
    //   315: goto -230 -> 85
    //
    // Exception table:
    //   from	to	target	type
    //   112	121	202	java/lang/Exception
    //   129	160	202	java/lang/Exception
    //   160	199	202	java/lang/Exception
    //   256	290	202	java/lang/Exception
    //   294	312	202	java/lang/Exception
    //   5	15	215	java/lang/Exception
    //   15	52	226	java/lang/Exception
    //   85	112	241	java/lang/Exception
    //   57	76	313	java/lang/Exception
  }

  private void d(Context paramContext, String paramString1, String paramString2)
  {
    monitorenter;
    while (true)
    {
      SharedPreferences localSharedPreferences;
      int i1;
      try
      {
        localSharedPreferences = h.e(paramContext);
        if (localSharedPreferences == null)
          return;
        try
        {
          i1 = g(paramContext, "_kvts" + paramString1 + paramString2);
          if (i1 >= 0)
            break label81;
          a("event duration less than 0 in ekvEvnetEnd");
        }
        catch (Exception localException)
        {
          a("exception in onLogDurationInternalEnd");
        }
        continue;
      }
      finally
      {
        monitorexit;
      }
      label81: JSONObject localJSONObject = new JSONObject(localSharedPreferences.getString("_kvvl" + paramString1 + paramString2, null));
      localJSONObject.put("du", i1);
      a(paramContext, localSharedPreferences, paramString1, localJSONObject);
    }
  }

  private void e(Context paramContext, String paramString)
  {
    monitorenter;
    try
    {
      JSONObject localJSONObject = new JSONObject();
      try
      {
        localJSONObject.put("type", "online_config");
        localJSONObject.put("appkey", paramString);
        localJSONObject.put("version_code", com.umeng.common.b.d(paramContext));
        localJSONObject.put("package", com.umeng.common.b.u(paramContext));
        localJSONObject.put("sdk_version", "4.5");
        localJSONObject.put("idmd5", com.umeng.common.b.g.b(com.umeng.common.b.f(paramContext)));
        localJSONObject.put("channel", i(paramContext));
        localJSONObject.put("report_policy", this.h.b(paramContext));
        localJSONObject.put("last_config_time", q(paramContext));
        this.j.post(new b(this, paramContext, localJSONObject));
        monitorexit;
        return;
      }
      catch (Exception localException)
      {
        while (true)
          Log.b("MobclickAgent", "exception in onlineConfigInternal");
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private long[] e(Context paramContext, SharedPreferences paramSharedPreferences)
  {
    try
    {
      Class localClass = Class.forName("android.net.TrafficStats");
      Class[] arrayOfClass1 = new Class[1];
      arrayOfClass1[0] = Integer.TYPE;
      Method localMethod1 = localClass.getMethod("getUidRxBytes", arrayOfClass1);
      Class[] arrayOfClass2 = new Class[1];
      arrayOfClass2[0] = Integer.TYPE;
      Method localMethod2 = localClass.getMethod("getUidTxBytes", arrayOfClass2);
      int i1 = paramContext.getApplicationInfo().uid;
      if (i1 == -1)
        return null;
      long[] arrayOfLong = new long[2];
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(i1);
      arrayOfLong[0] = ((Long)localMethod1.invoke(null, arrayOfObject1)).longValue();
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(i1);
      arrayOfLong[1] = ((Long)localMethod2.invoke(null, arrayOfObject2)).longValue();
      if ((arrayOfLong[0] > 0L) && (arrayOfLong[1] > 0L))
      {
        long l1 = paramSharedPreferences.getLong("traffics_up", -1L);
        long l2 = paramSharedPreferences.getLong("traffics_down", -1L);
        paramSharedPreferences.edit().putLong("traffics_up", arrayOfLong[1]).putLong("traffics_down", arrayOfLong[0]).commit();
        if ((l1 <= 0L) || (l2 <= 0L))
          break label309;
        arrayOfLong[0] -= l2;
        arrayOfLong[1] -= l1;
        if (arrayOfLong[0] > 0L)
        {
          long l3 = arrayOfLong[1];
          if (l3 > 0L);
        }
        else
        {
          return null;
        }
        return arrayOfLong;
      }
    }
    catch (Exception localException)
    {
      a("sdk less than 2.2 has get no traffic");
      return null;
    }
    return null;
    label309: return null;
  }

  private void f(Context paramContext, String paramString)
  {
    try
    {
      if (e.k)
      {
        this.g.a(paramString);
        return;
      }
      i locali = i.a(paramContext, paramString);
      locali.a(Long.valueOf(System.currentTimeMillis()));
      locali.a(paramContext);
      return;
    }
    catch (Exception localException)
    {
      Log.a("MobclickAgent", "exception in save event begin info");
    }
  }

  private int g(Context paramContext, String paramString)
  {
    try
    {
      long l2;
      if (e.k)
        l2 = this.g.b(paramString);
      while (l2 > 0L)
      {
        return (int)(System.currentTimeMillis() - l2);
        long l1 = i.a(paramContext, paramString).a().longValue();
        l2 = l1;
      }
    }
    catch (Exception localException)
    {
      Log.a("MobclickAgent", "exception in get event duration", localException);
    }
    return -1;
  }

  private void g(Context paramContext)
  {
    monitorenter;
    while (true)
    {
      SharedPreferences localSharedPreferences;
      try
      {
        this.h.c(paramContext);
        localSharedPreferences = h.e(paramContext);
        if (localSharedPreferences == null)
          return;
        if (a(localSharedPreferences))
        {
          String str2 = a(paramContext, h(paramContext), localSharedPreferences);
          Log.a("MobclickAgent", "Start new session: " + str2);
          continue;
        }
      }
      finally
      {
        monitorexit;
      }
      String str1 = b(paramContext, localSharedPreferences);
      Log.a("MobclickAgent", "Extend current session: " + str1);
    }
  }

  private String h(Context paramContext)
  {
    if (this.b == null)
      return com.umeng.common.b.p(paramContext);
    return this.b;
  }

  private String i(Context paramContext)
  {
    if (this.a == null)
      return com.umeng.common.b.t(paramContext);
    return this.a;
  }

  private void j(Context paramContext)
  {
    monitorenter;
    while (true)
    {
      SharedPreferences localSharedPreferences;
      long l1;
      try
      {
        localSharedPreferences = h.e(paramContext);
        if (localSharedPreferences == null)
          return;
        l1 = localSharedPreferences.getLong("start_millis", -1L);
        if (l1 == -1L)
        {
          Log.b("MobclickAgent", "onEndSession called before onStartSession");
          a(paramContext, localSharedPreferences);
          if (this.g.a() <= 0)
            continue;
          k(paramContext);
          continue;
        }
      }
      finally
      {
        monitorexit;
      }
      long l2 = System.currentTimeMillis();
      long l3 = l2 - l1;
      long l4 = localSharedPreferences.getLong("duration", 0L);
      SharedPreferences.Editor localEditor = localSharedPreferences.edit();
      if (e.h)
      {
        String str1 = localSharedPreferences.getString("activities", "");
        String str2 = this.i;
        if (!"".equals(str1))
          str1 = str1 + ";";
        String str3 = str1 + "[" + str2 + "," + l3 / 1000L + "]";
        localEditor.remove("activities");
        localEditor.putString("activities", str3);
      }
      localEditor.putLong("start_millis", -1L);
      localEditor.putLong("end_millis", l2);
      localEditor.putLong("duration", l3 + l4);
      localEditor.commit();
    }
  }

  private void k(Context paramContext)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("type", "cmd_cache_buffer");
      this.j.post(new b(this, paramContext, localJSONObject));
      return;
    }
    catch (JSONException localJSONException)
    {
      Log.b("MobclickAgent", "json error in emitCache");
    }
  }

  private void l(Context paramContext)
  {
    monitorenter;
    try
    {
      p(paramContext);
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  private void m(Context paramContext)
  {
    monitorenter;
    try
    {
      a locala = this.f;
      if (locala == null);
      while (true)
      {
        return;
        JSONArray localJSONArray = this.f.b(paramContext);
        if ((localJSONArray == null) || (localJSONArray.length() == 0))
          continue;
        a(paramContext, localJSONArray);
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private String n(Context paramContext)
  {
    StringBuffer localStringBuffer1 = new StringBuffer();
    localStringBuffer1.append("Android");
    localStringBuffer1.append("/");
    localStringBuffer1.append("4.5");
    localStringBuffer1.append(" ");
    try
    {
      StringBuffer localStringBuffer2 = new StringBuffer();
      localStringBuffer2.append(paramContext.getPackageManager().getApplicationLabel(paramContext.getApplicationInfo()).toString());
      localStringBuffer2.append("/");
      localStringBuffer2.append(paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionName);
      localStringBuffer2.append(" ");
      localStringBuffer2.append(Build.MODEL);
      localStringBuffer2.append("/");
      localStringBuffer2.append(Build.VERSION.RELEASE);
      localStringBuffer2.append(" ");
      localStringBuffer2.append(com.umeng.common.b.g.b(com.umeng.common.b.f(paramContext)));
      localStringBuffer1.append(URLEncoder.encode(localStringBuffer2.toString()));
      return localStringBuffer1.toString();
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  private JSONObject o(Context paramContext)
  {
    JSONObject localJSONObject1 = new JSONObject();
    String str1;
    String str2;
    try
    {
      str1 = com.umeng.common.b.f(paramContext);
      if ((str1 == null) || (str1.equals("")))
      {
        Log.b("MobclickAgent", "No device id");
        return null;
      }
      str2 = h(paramContext);
      if (str2 == null)
      {
        Log.b("MobclickAgent", "No appkey");
        return null;
      }
    }
    catch (Exception localException)
    {
      Log.b("MobclickAgent", "getMessageHeader error", localException);
      return null;
    }
    localJSONObject1.put("device_id", str1);
    localJSONObject1.put("idmd5", com.umeng.common.b.g.b(str1));
    localJSONObject1.put("mc", com.umeng.common.b.q(paramContext));
    localJSONObject1.put("device_model", Build.MODEL);
    localJSONObject1.put("appkey", str2);
    localJSONObject1.put("channel", i(paramContext));
    localJSONObject1.put("app_version", com.umeng.common.b.e(paramContext));
    localJSONObject1.put("version_code", com.umeng.common.b.d(paramContext));
    localJSONObject1.put("sdk_type", "Android");
    localJSONObject1.put("sdk_version", "4.5");
    localJSONObject1.put("os", "Android");
    localJSONObject1.put("os_version", Build.VERSION.RELEASE);
    localJSONObject1.put("timezone", com.umeng.common.b.n(paramContext));
    String[] arrayOfString1 = com.umeng.common.b.o(paramContext);
    if (arrayOfString1 != null)
    {
      localJSONObject1.put("country", arrayOfString1[0]);
      localJSONObject1.put("language", arrayOfString1[1]);
    }
    localJSONObject1.put("resolution", com.umeng.common.b.r(paramContext));
    String[] arrayOfString2 = com.umeng.common.b.j(paramContext);
    if ((arrayOfString2 != null) && (arrayOfString2[0].equals("2G/3G")))
    {
      localJSONObject1.put("access", arrayOfString2[0]);
      localJSONObject1.put("access_subtype", arrayOfString2[1]);
    }
    while (true)
    {
      localJSONObject1.put("carrier", com.umeng.common.b.s(paramContext));
      localJSONObject1.put("cpu", com.umeng.common.b.a());
      if (!this.d.equals(""))
        localJSONObject1.put("gpu_vender", this.d);
      if (!this.e.equals(""))
        localJSONObject1.put("gpu_renderer", this.e);
      if (e.i)
      {
        JSONObject localJSONObject2 = h.h(paramContext);
        if (localJSONObject2 != null)
          localJSONObject1.put("uinfo", localJSONObject2);
      }
      localJSONObject1.put("package", com.umeng.common.b.u(paramContext));
      return localJSONObject1;
      if (arrayOfString2 != null)
      {
        localJSONObject1.put("access", arrayOfString2[0]);
        continue;
      }
      localJSONObject1.put("access", "Unknown");
    }
  }

  private void p(Context paramContext)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("type", "flush");
      this.j.post(new b(this, paramContext, localJSONObject));
      return;
    }
    catch (JSONException localJSONException)
    {
      Log.b("MobclickAgent", "json error in emitCache");
    }
  }

  private String q(Context paramContext)
  {
    return h.b(paramContext).getString("umeng_last_config_time", "");
  }

  JSONObject a(Context paramContext, JSONObject paramJSONObject1, JSONObject paramJSONObject2, JSONObject paramJSONObject3, String paramString)
  {
    SharedPreferences localSharedPreferences = h.c(paramContext);
    long l1 = localSharedPreferences.getLong("req_time", 0L);
    if (l1 != 0L);
    JSONObject localJSONObject;
    try
    {
      paramJSONObject2.put("req_time", l1);
      localSharedPreferences.edit().putString("header", paramJSONObject2.toString()).commit();
      localJSONObject = new JSONObject();
      if (paramString == null)
        return null;
    }
    catch (JSONException localJSONException2)
    {
      while (true)
        Log.a("MobclickAgent", "json error in tryToSendMessage", localJSONException2);
      try
      {
        if (("flush".equals(paramString)) && (paramJSONObject1 == null))
        {
          Log.e("MobclickAgent", "No cache message to flush in constructMessage");
          return null;
        }
      }
      catch (JSONException localJSONException1)
      {
        Log.b("MobclickAgent", "Fail to construct json message in tryToSendMessage.", localJSONException1);
        h.j(paramContext);
        return null;
      }
      if ((paramJSONObject1 != null) && (a(paramJSONObject1, paramJSONObject2)))
        paramJSONObject1.remove("error");
      if (!"flush".equals(paramString))
      {
        if (paramJSONObject1 == null)
          paramJSONObject1 = new JSONObject();
        if (!paramJSONObject1.isNull(paramString))
          break label231;
        JSONArray localJSONArray1 = new JSONArray();
        localJSONArray1.put(paramJSONObject3);
        paramJSONObject1.put(paramString, localJSONArray1);
      }
    }
    while (true)
    {
      localJSONObject.put("header", paramJSONObject2);
      localJSONObject.put("body", paramJSONObject1);
      return localJSONObject;
      label231: JSONArray localJSONArray2 = paramJSONObject1.getJSONArray(paramString);
      if ("ekv".equals(paramString))
      {
        b(paramJSONObject3, localJSONArray2);
        continue;
      }
      localJSONArray2.put(paramJSONObject3);
    }
  }

  void a(Context paramContext)
  {
    if (paramContext == null);
    try
    {
      Log.b("MobclickAgent", "unexpected null context in onPause");
      return;
      if (!paramContext.getClass().getName().equals(this.i))
      {
        Log.b("MobclickAgent", "onPause() called without context from corresponding onResume()");
        return;
      }
    }
    catch (Exception localException)
    {
      Log.b("MobclickAgent", "Exception occurred in Mobclick.onRause(). ", localException);
      return;
    }
    new a(paramContext, 0).start();
  }

  void a(Context paramContext, String paramString)
  {
    if ((paramString == null) || (paramString == "") || (paramString.length() > 10240));
    do
    {
      return;
      if (paramContext != null)
        continue;
      Log.b("MobclickAgent", "unexpected null context in reportError");
      return;
    }
    while (this.f == null);
    this.f.a(paramContext, paramString);
  }

  void a(Context paramContext, String paramString1, String paramString2)
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)))
    {
      a("invalid params in onEventBegin");
      return;
    }
    f(paramContext, "_tl" + paramString1 + paramString2);
  }

  void a(Context paramContext, String paramString1, String paramString2, long paramLong, int paramInt)
  {
    if (paramContext != null);
    try
    {
      if ((TextUtils.isEmpty(paramString1)) || (paramInt <= 0))
      {
        a("invalid params in onEvent");
        return;
      }
      if ((!this.h.a()) && (e.k))
      {
        if (!this.g.a(paramContext, paramString1, paramString2, paramLong, paramInt))
          return;
        k(paramContext);
        return;
      }
    }
    catch (Exception localException)
    {
      Log.b("MobclickAgent", "Exception occurred in Mobclick.onEvent(). ", localException);
      return;
    }
    new a(paramContext, paramString1, paramString2, paramLong, paramInt, 3).start();
  }

  void a(Context paramContext, String paramString, Map<String, String> paramMap, long paramLong)
  {
    if (paramContext != null);
    try
    {
      if (TextUtils.isEmpty(paramString))
      {
        a("invalid params in onKVEventEnd");
        return;
      }
      if ((paramMap == null) || (paramMap.isEmpty()))
      {
        a("map is null or empty in onEvent");
        return;
      }
    }
    catch (Exception localException)
    {
      Log.b("MobclickAgent", "Exception occurred in Mobclick.onEvent(). ", localException);
      return;
    }
    if ((!this.h.a()) && (e.k))
    {
      if (this.g.a(paramContext, paramString, paramMap, paramLong))
      {
        k(paramContext);
        return;
      }
    }
    else
      new a(paramContext, paramString, paramMap, paramLong, 4).start();
  }

  void a(Context paramContext, String paramString1, Map<String, String> paramMap, String paramString2)
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)))
    {
      a("invalid params in onKVEventBegin");
      return;
    }
    if ((paramMap == null) || (paramMap.isEmpty()))
    {
      a("map is null or empty in onKVEventBegin");
      return;
    }
    try
    {
      if (e.k)
      {
        String str = paramString1 + paramString2;
        this.g.a(str, paramMap);
        this.g.a(str);
        return;
      }
    }
    catch (Exception localException)
    {
      Log.a("MobclickAgent", "exception in save k-v event begin inof", localException);
      return;
    }
    new a(paramContext, paramString1, paramMap, paramString2, 5).start();
  }

  protected void a(Context paramContext, JSONObject paramJSONObject)
  {
    String str = (String)paramJSONObject.remove("type");
    JSONObject localJSONObject = a(paramContext, h.i(paramContext), o(paramContext), paramJSONObject, str);
    if ((localJSONObject == null) || (localJSONObject.isNull("body")))
      return;
    if (!this.h.a(str, paramContext))
    {
      h.b(paramContext, localJSONObject);
      return;
    }
    Object localObject1 = null;
    int i1 = 0;
    Object localObject2;
    if (i1 < e.p.length)
    {
      localObject2 = a(paramContext, localJSONObject, e.p[i1], false, str);
      if (localObject2 == null);
    }
    while (true)
    {
      if (localObject2 != null)
      {
        Log.a("MobclickAgent", "send applog succeed :" + (String)localObject2);
        h.j(paramContext);
        this.h.a(paramContext);
        return;
        i1++;
        localObject1 = localObject2;
        break;
      }
      h.b(paramContext, localJSONObject);
      Log.a("MobclickAgent", "send applog failed");
      return;
      localObject2 = localObject1;
    }
  }

  void b(Context paramContext)
  {
    try
    {
      String str = h(paramContext);
      if ((str == null) || (str.length() == 0))
      {
        Log.b("MobclickAgent", "unexpected empty appkey in onError");
        return;
      }
      if (paramContext == null)
      {
        Log.b("MobclickAgent", "unexpected null context in onError");
        return;
      }
    }
    catch (Exception localException)
    {
      Log.b("MobclickAgent", "Exception occurred in Mobclick.onError()", localException);
      return;
    }
    if (this.f != null)
    {
      this.f.a(paramContext);
      this.f.a(this);
    }
    new a(paramContext, 2).start();
  }

  void b(Context paramContext, String paramString)
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString)))
    {
      a("invalid params in onEventBegin");
      return;
    }
    f(paramContext, "_t" + paramString);
  }

  void b(Context paramContext, String paramString1, String paramString2)
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString2)))
    {
      a("invalid params in onEventEnd");
      return;
    }
    int i1 = g(paramContext, "_tl" + paramString1 + paramString2);
    if (i1 < 0)
    {
      a("event duration less than 0 in onEvnetEnd");
      return;
    }
    a(paramContext, paramString1, paramString2, i1, 1);
  }

  void c(Context paramContext)
  {
    if (paramContext == null);
    try
    {
      Log.b("MobclickAgent", "unexpected null context in onResume");
      return;
      this.i = paramContext.getClass().getName();
      new a(paramContext, 1).start();
      return;
    }
    catch (Exception localException)
    {
      Log.b("MobclickAgent", "Exception occurred in Mobclick.onResume(). ", localException);
    }
  }

  void c(Context paramContext, String paramString)
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString)))
    {
      Log.a("MobclickAgent", "input Context is null or event_id is empty");
      return;
    }
    int i1 = g(paramContext, "_t" + paramString);
    if (i1 < 0)
    {
      Log.a("MobclickAgent", "event duration less than 0 in onEventEnd");
      return;
    }
    a(paramContext, paramString, null, i1, 1);
  }

  void c(Context paramContext, String paramString1, String paramString2)
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)))
    {
      a("invalid params in onKVEventEnd");
      return;
    }
    if (e.k)
    {
      String str = paramString1 + paramString2;
      int i1 = g(paramContext, str);
      if (i1 < 0)
      {
        a("event duration less than 0 in onEvnetEnd");
        return;
      }
      a(paramContext, paramString1, this.g.c(str), i1);
      return;
    }
    new a(paramContext, paramString1, null, paramString2, 6).start();
  }

  void d(Context paramContext)
  {
    if (paramContext == null);
    try
    {
      Log.b("MobclickAgent", "unexpected null context in flush");
      l(paramContext);
      return;
    }
    catch (Exception localException)
    {
      Log.b("MobclickAgent", "Exception occurred in Mobclick.flush(). ", localException);
    }
  }

  public void e(Context paramContext)
  {
    if (paramContext == null);
    String str;
    try
    {
      Log.b("MobclickAgent", "unexpected null context in updateOnlineConfig");
      return;
      str = h(paramContext);
      if (str == null)
      {
        Log.b("MobclickAgent", "unexpected null appkey in updateOnlineConfig");
        return;
      }
    }
    catch (Exception localException)
    {
      Log.b("MobclickAgent", "exception in updateOnlineConfig");
      return;
    }
    new Thread(new c(this, paramContext, str)).start();
  }

  public void f(Context paramContext)
  {
    try
    {
      this.g.a(paramContext);
      j(paramContext);
      return;
    }
    catch (Exception localException)
    {
      Log.a("MobclickAgent", "Exception in onAppCrash", localException);
    }
  }

  private final class a extends Thread
  {
    private final Object b = new Object();
    private Context c;
    private int d;
    private String e;
    private String f;
    private int g;
    private long h;
    private Map<String, String> i;
    private String j;

    a(Context paramInt, int arg3)
    {
      this.c = paramInt.getApplicationContext();
      int k;
      this.d = k;
    }

    a(Context paramString1, String paramString2, String paramLong, long arg5, int paramInt2, int arg8)
    {
      this.c = paramString1.getApplicationContext();
      this.e = paramString2;
      this.f = paramLong;
      this.g = paramInt2;
      int k;
      this.d = k;
      this.h = ???;
    }

    a(String paramMap, Map<String, String> paramLong, long arg4, int arg6)
    {
      this.c = paramMap.getApplicationContext();
      this.e = paramLong;
      this.i = ???;
      int k;
      this.d = k;
      this.h = paramInt;
    }

    a(String paramMap, Map<String, String> paramString1, String paramInt, int arg5)
    {
      this.c = paramMap.getApplicationContext();
      this.e = paramString1;
      this.i = paramInt;
      Object localObject;
      this.j = localObject;
      int k;
      this.d = k;
    }

    // ERROR //
    public void run()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 34	com/umeng/analytics/b$a:b	Ljava/lang/Object;
      //   4: astore_2
      //   5: aload_2
      //   6: monitorenter
      //   7: aload_0
      //   8: getfield 44	com/umeng/analytics/b$a:d	I
      //   11: istore 4
      //   13: iload 4
      //   15: ifne +63 -> 78
      //   18: aload_0
      //   19: getfield 42	com/umeng/analytics/b$a:c	Landroid/content/Context;
      //   22: ifnonnull +13 -> 35
      //   25: ldc 64
      //   27: ldc 66
      //   29: invokestatic 71	com/umeng/common/Log:b	(Ljava/lang/String;Ljava/lang/String;)V
      //   32: aload_2
      //   33: monitorexit
      //   34: return
      //   35: aload_0
      //   36: getfield 26	com/umeng/analytics/b$a:a	Lcom/umeng/analytics/b;
      //   39: aload_0
      //   40: getfield 42	com/umeng/analytics/b$a:c	Landroid/content/Context;
      //   43: invokestatic 76	com/umeng/analytics/b:a	(Lcom/umeng/analytics/b;Landroid/content/Context;)V
      //   46: aload_2
      //   47: monitorexit
      //   48: return
      //   49: astore_3
      //   50: aload_2
      //   51: monitorexit
      //   52: aload_3
      //   53: athrow
      //   54: astore_1
      //   55: ldc 64
      //   57: ldc 78
      //   59: aload_1
      //   60: invokestatic 81	com/umeng/common/Log:b	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Exception;)V
      //   63: return
      //   64: astore 5
      //   66: ldc 64
      //   68: ldc 66
      //   70: aload 5
      //   72: invokestatic 81	com/umeng/common/Log:b	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Exception;)V
      //   75: goto -29 -> 46
      //   78: aload_0
      //   79: getfield 44	com/umeng/analytics/b$a:d	I
      //   82: iconst_1
      //   83: if_icmpne +17 -> 100
      //   86: aload_0
      //   87: getfield 26	com/umeng/analytics/b$a:a	Lcom/umeng/analytics/b;
      //   90: aload_0
      //   91: getfield 42	com/umeng/analytics/b$a:c	Landroid/content/Context;
      //   94: invokestatic 83	com/umeng/analytics/b:b	(Lcom/umeng/analytics/b;Landroid/content/Context;)V
      //   97: goto -51 -> 46
      //   100: aload_0
      //   101: getfield 44	com/umeng/analytics/b$a:d	I
      //   104: iconst_2
      //   105: if_icmpne +17 -> 122
      //   108: aload_0
      //   109: getfield 26	com/umeng/analytics/b$a:a	Lcom/umeng/analytics/b;
      //   112: aload_0
      //   113: getfield 42	com/umeng/analytics/b$a:c	Landroid/content/Context;
      //   116: invokestatic 85	com/umeng/analytics/b:c	(Lcom/umeng/analytics/b;Landroid/content/Context;)V
      //   119: goto -73 -> 46
      //   122: aload_0
      //   123: getfield 44	com/umeng/analytics/b$a:d	I
      //   126: iconst_3
      //   127: if_icmpne +33 -> 160
      //   130: aload_0
      //   131: getfield 26	com/umeng/analytics/b$a:a	Lcom/umeng/analytics/b;
      //   134: aload_0
      //   135: getfield 42	com/umeng/analytics/b$a:c	Landroid/content/Context;
      //   138: aload_0
      //   139: getfield 47	com/umeng/analytics/b$a:e	Ljava/lang/String;
      //   142: aload_0
      //   143: getfield 49	com/umeng/analytics/b$a:f	Ljava/lang/String;
      //   146: aload_0
      //   147: getfield 53	com/umeng/analytics/b$a:h	J
      //   150: aload_0
      //   151: getfield 51	com/umeng/analytics/b$a:g	I
      //   154: invokestatic 88	com/umeng/analytics/b:a	(Lcom/umeng/analytics/b;Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;JI)V
      //   157: goto -111 -> 46
      //   160: aload_0
      //   161: getfield 44	com/umeng/analytics/b$a:d	I
      //   164: iconst_4
      //   165: if_icmpne +29 -> 194
      //   168: aload_0
      //   169: getfield 26	com/umeng/analytics/b$a:a	Lcom/umeng/analytics/b;
      //   172: aload_0
      //   173: getfield 42	com/umeng/analytics/b$a:c	Landroid/content/Context;
      //   176: aload_0
      //   177: getfield 47	com/umeng/analytics/b$a:e	Ljava/lang/String;
      //   180: aload_0
      //   181: getfield 56	com/umeng/analytics/b$a:i	Ljava/util/Map;
      //   184: aload_0
      //   185: getfield 53	com/umeng/analytics/b$a:h	J
      //   188: invokestatic 91	com/umeng/analytics/b:a	(Lcom/umeng/analytics/b;Landroid/content/Context;Ljava/lang/String;Ljava/util/Map;J)V
      //   191: goto -145 -> 46
      //   194: aload_0
      //   195: getfield 44	com/umeng/analytics/b$a:d	I
      //   198: iconst_5
      //   199: if_icmpne +29 -> 228
      //   202: aload_0
      //   203: getfield 26	com/umeng/analytics/b$a:a	Lcom/umeng/analytics/b;
      //   206: aload_0
      //   207: getfield 42	com/umeng/analytics/b$a:c	Landroid/content/Context;
      //   210: aload_0
      //   211: getfield 47	com/umeng/analytics/b$a:e	Ljava/lang/String;
      //   214: aload_0
      //   215: getfield 56	com/umeng/analytics/b$a:i	Ljava/util/Map;
      //   218: aload_0
      //   219: getfield 59	com/umeng/analytics/b$a:j	Ljava/lang/String;
      //   222: invokestatic 94	com/umeng/analytics/b:a	(Lcom/umeng/analytics/b;Landroid/content/Context;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;)V
      //   225: goto -179 -> 46
      //   228: aload_0
      //   229: getfield 44	com/umeng/analytics/b$a:d	I
      //   232: bipush 6
      //   234: if_icmpne -188 -> 46
      //   237: aload_0
      //   238: getfield 26	com/umeng/analytics/b$a:a	Lcom/umeng/analytics/b;
      //   241: aload_0
      //   242: getfield 42	com/umeng/analytics/b$a:c	Landroid/content/Context;
      //   245: aload_0
      //   246: getfield 47	com/umeng/analytics/b$a:e	Ljava/lang/String;
      //   249: aload_0
      //   250: getfield 59	com/umeng/analytics/b$a:j	Ljava/lang/String;
      //   253: invokestatic 97	com/umeng/analytics/b:a	(Lcom/umeng/analytics/b;Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
      //   256: goto -210 -> 46
      //
      // Exception table:
      //   from	to	target	type
      //   7	13	49	finally
      //   18	32	49	finally
      //   32	34	49	finally
      //   35	46	49	finally
      //   46	48	49	finally
      //   50	52	49	finally
      //   66	75	49	finally
      //   78	97	49	finally
      //   100	119	49	finally
      //   122	157	49	finally
      //   160	191	49	finally
      //   194	225	49	finally
      //   228	256	49	finally
      //   0	7	54	java/lang/Exception
      //   52	54	54	java/lang/Exception
      //   18	32	64	java/lang/Exception
      //   35	46	64	java/lang/Exception
    }
  }

  private final class b
    implements Runnable
  {
    private final Object b = new Object();
    private Context c;
    private JSONObject d;

    b(b paramContext, Context paramJSONObject, JSONObject arg4)
    {
      this.c = paramJSONObject.getApplicationContext();
      Object localObject;
      this.d = localObject;
    }

    public void run()
    {
      try
      {
        if (this.d.getString("type").equals("online_config"))
        {
          b.a(b.this, this.c, this.d);
          return;
        }
        if (this.d.getString("type").equals("cmd_cache_buffer"))
          synchronized (this.b)
          {
            b.a(b.this).a(this.c);
            return;
          }
      }
      catch (Exception localException)
      {
        Log.b("MobclickAgent", "Exception occurred in ReportMessageHandler");
        localException.printStackTrace();
        return;
      }
      if (this.d.getString("type").equals("flush"))
        synchronized (this.b)
        {
          b.a(b.this).a(this.c);
          b.this.a(this.c, this.d);
          return;
        }
      synchronized (this.b)
      {
        b.a(b.this).a(this.c);
        b.this.a(this.c, this.d);
        return;
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.analytics.b
 * JD-Core Version:    0.6.0
 */