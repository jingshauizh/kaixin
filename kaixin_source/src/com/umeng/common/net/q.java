package com.umeng.common.net;

import com.umeng.common.Log;
import com.umeng.common.b.f;
import com.umeng.common.b.g;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

public class q
{
  private static final String a = q.class.getName();
  private Map<String, String> b;

  private static String a(InputStream paramInputStream)
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
      Log.b(a, "Caught IOException in convertStreamToString()", localIOException2);
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
          Log.b(a, "Caught IOException in convertStreamToString()", localIOException4);
          return null;
        }
      }
      catch (IOException localIOException3)
      {
        Log.b(a, "Caught IOException in convertStreamToString()", localIOException3);
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
        Log.b(a, "Caught IOException in convertStreamToString()", localIOException1);
      }
    }
    return null;
  }

  private JSONObject a(String paramString)
  {
    int i = new Random().nextInt(1000);
    while (true)
    {
      HttpResponse localHttpResponse;
      InputStream localInputStream;
      String str2;
      try
      {
        str1 = System.getProperty("line.separator");
        if (paramString.length() > 1)
          continue;
        Log.b(a, i + ":\tInvalid baseUrl.");
        return null;
        Log.a(a, i + ":\tget: " + paramString);
        localHttpGet = new HttpGet(paramString);
        if ((this.b == null) || (this.b.size() <= 0))
          continue;
        Iterator localIterator = this.b.keySet().iterator();
        if (!localIterator.hasNext())
          continue;
        String str3 = (String)localIterator.next();
        localHttpGet.addHeader(str3, (String)this.b.get(str3));
        continue;
      }
      catch (ClientProtocolException localClientProtocolException)
      {
        String str1;
        HttpGet localHttpGet;
        Log.c(a, i + ":\tClientProtocolException,Failed to send message." + paramString, localClientProtocolException);
        return null;
        BasicHttpParams localBasicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 10000);
        HttpConnectionParams.setSoTimeout(localBasicHttpParams, 20000);
        localHttpResponse = new DefaultHttpClient(localBasicHttpParams).execute(localHttpGet);
        if (localHttpResponse.getStatusLine().getStatusCode() != 200)
          break label513;
        HttpEntity localHttpEntity = localHttpResponse.getEntity();
        if (localHttpEntity == null)
          break label563;
        localInputStream = localHttpEntity.getContent();
        Header localHeader = localHttpResponse.getFirstHeader("Content-Encoding");
        if ((localHeader == null) || (!localHeader.getValue().equalsIgnoreCase("gzip")))
          continue;
        Log.a(a, i + "  Use GZIPInputStream get data....");
        localObject = new GZIPInputStream(localInputStream);
        str2 = a((InputStream)localObject);
        Log.a(a, i + ":\tresponse: " + str1 + str2);
        if (str2 == null)
        {
          return null;
          if ((localHeader == null) || (!localHeader.getValue().equalsIgnoreCase("deflate")))
            break label565;
          Log.a(a, i + "  Use InflaterInputStream get data....");
          localObject = new InflaterInputStream(localInputStream);
          continue;
        }
      }
      catch (Exception localException)
      {
        Log.c(a, i + ":\tIOException,Failed to send message." + paramString, localException);
        return null;
      }
      return new JSONObject(str2);
      label513: Log.c(a, i + ":\tFailed to send message. StatusCode = " + localHttpResponse.getStatusLine().getStatusCode() + g.a + paramString);
      label563: return null;
      label565: Object localObject = localInputStream;
    }
  }

  private JSONObject a(String paramString, JSONObject paramJSONObject)
  {
    String str1 = paramJSONObject.toString();
    int i = new Random().nextInt(1000);
    Log.c(a, i + ":\trequest: " + paramString + g.a + str1);
    HttpPost localHttpPost = new HttpPost(paramString);
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 10000);
    HttpConnectionParams.setSoTimeout(localBasicHttpParams, 20000);
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient(localBasicHttpParams);
    while (true)
    {
      InputStream localInputStream;
      try
      {
        if (!a())
          continue;
        byte[] arrayOfByte = f.a("content=" + str1, Charset.defaultCharset().toString());
        localHttpPost.addHeader("Content-Encoding", "deflate");
        localHttpPost.setEntity(new InputStreamEntity(new ByteArrayInputStream(arrayOfByte), arrayOfByte.length));
        localHttpResponse = localDefaultHttpClient.execute(localHttpPost);
        if (localHttpResponse.getStatusLine().getStatusCode() != 200)
          continue;
        HttpEntity localHttpEntity = localHttpResponse.getEntity();
        if (localHttpEntity == null)
          break;
        localInputStream = localHttpEntity.getContent();
        Header localHeader = localHttpResponse.getFirstHeader("Content-Encoding");
        if ((localHeader != null) && (localHeader.getValue().equalsIgnoreCase("deflate")))
        {
          localObject = new InflaterInputStream(localInputStream);
          str2 = a((InputStream)localObject);
          Log.a(a, i + ":\tresponse: " + g.a + str2);
          if (str2 == null)
          {
            return null;
            ArrayList localArrayList = new ArrayList(1);
            localArrayList.add(new BasicNameValuePair("content", str1));
            localHttpPost.setEntity(new UrlEncodedFormEntity(localArrayList, "UTF-8"));
            continue;
          }
        }
      }
      catch (ClientProtocolException localClientProtocolException)
      {
        HttpResponse localHttpResponse;
        String str2;
        Log.c(a, i + ":\tClientProtocolException,Failed to send message." + paramString, localClientProtocolException);
        return null;
        return new JSONObject(str2);
        Log.c(a, i + ":\tFailed to send message. StatusCode = " + localHttpResponse.getStatusLine().getStatusCode() + g.a + paramString);
        return null;
      }
      catch (IOException localIOException)
      {
        Log.c(a, i + ":\tIOException,Failed to send message." + paramString, localIOException);
        return null;
      }
      catch (JSONException localJSONException)
      {
        Log.c(a, i + ":\tIOException,Failed to send message." + paramString, localJSONException);
        return null;
      }
      Object localObject = localInputStream;
    }
    return (JSONObject)null;
  }

  private void b(String paramString)
  {
    if ((g.c(paramString)) || (!(r.b.equals(paramString.trim()) ^ r.a.equals(paramString.trim()))))
      throw new RuntimeException("验证请求方式失败[" + paramString + "]");
  }

  public q a(Map<String, String> paramMap)
  {
    this.b = paramMap;
    return this;
  }

  public <T extends s> T a(r paramr, Class<T> paramClass)
  {
    String str = paramr.c().trim();
    b(str);
    JSONObject localJSONObject;
    if (r.b.equals(str))
      localJSONObject = a(paramr.b());
    while (true)
    {
      if (localJSONObject == null)
      {
        return null;
        if (r.a.equals(str))
        {
          localJSONObject = a(paramr.c, paramr.a());
          continue;
        }
      }
      else
      {
        try
        {
          s locals = (s)paramClass.getConstructor(new Class[] { JSONObject.class }).newInstance(new Object[] { localJSONObject });
          return locals;
        }
        catch (SecurityException localSecurityException)
        {
          Log.b(a, "SecurityException", localSecurityException);
          return null;
        }
        catch (NoSuchMethodException localNoSuchMethodException)
        {
          while (true)
            Log.b(a, "NoSuchMethodException", localNoSuchMethodException);
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          while (true)
            Log.b(a, "IllegalArgumentException", localIllegalArgumentException);
        }
        catch (InstantiationException localInstantiationException)
        {
          while (true)
            Log.b(a, "InstantiationException", localInstantiationException);
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
          while (true)
            Log.b(a, "IllegalAccessException", localIllegalAccessException);
        }
        catch (InvocationTargetException localInvocationTargetException)
        {
          while (true)
            Log.b(a, "InvocationTargetException", localInvocationTargetException);
        }
      }
      localJSONObject = null;
    }
  }

  public boolean a()
  {
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.common.net.q
 * JD-Core Version:    0.6.0
 */