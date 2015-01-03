package com.kaixin001.network;

import android.content.Context;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHeader;

public class HttpProxy
{
  private HttpConnection httpConnection;
  private Context mContext;

  public HttpProxy(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public static String getWapProxyURL(String paramString)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("rurl", paramString);
    localHashMap.put("ctype", Setting.getInstance().getCType());
    localHashMap.put("oauth_token", User.getInstance().getOauthToken());
    String str1 = Setting.getInstance().getHost() + "/mobile/wapagent.json";
    XAuth.generateURLParams(str1, HttpMethod.GET.name(), localHashMap, XAuth.CONSUMER_KEY, XAuth.CONSUMER_SECRET, User.getInstance().getOauthTokenSecret());
    StringBuffer localStringBuffer = new StringBuffer(str1);
    if (localStringBuffer.indexOf("?") == -1)
      localStringBuffer.append("?");
    Iterator localIterator = localHashMap.keySet().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localStringBuffer.toString();
      String str2 = (String)localIterator.next();
      if (!localStringBuffer.toString().endsWith("?"))
        localStringBuffer.append("&");
      localStringBuffer.append(str2).append("=").append(URLEncoder.encode(String.valueOf(localHashMap.get(str2))));
    }
  }

  private Header[] putKeepAlive(Header[] paramArrayOfHeader)
  {
    if (paramArrayOfHeader == null)
    {
      Header[] arrayOfHeader2 = new Header[1];
      arrayOfHeader2[0] = new BasicHeader("Connection", "Keep-Alive");
      return arrayOfHeader2;
    }
    Header[] arrayOfHeader1 = (Header[])Arrays.copyOf(paramArrayOfHeader, 1 + paramArrayOfHeader.length);
    arrayOfHeader1[paramArrayOfHeader.length] = new BasicHeader("Connection", "Keep-Alive");
    return arrayOfHeader1;
  }

  public void cancel()
  {
    if (this.httpConnection != null)
      this.httpConnection.abortRequest();
  }

  public void cancelDownload()
  {
    if (this.httpConnection != null)
      this.httpConnection.cancelDownLoad();
  }

  public boolean httpDownload(String paramString1, String paramString2, boolean paramBoolean, HttpRequestState paramHttpRequestState, HttpProgressListener paramHttpProgressListener)
    throws Exception
  {
    this.httpConnection = new HttpConnection(this.mContext);
    return this.httpConnection.httpDownload(paramString1, paramString2, paramBoolean, paramHttpRequestState, paramHttpProgressListener);
  }

  public String httpGet(String paramString, HttpRequestState paramHttpRequestState, HttpProgressListener paramHttpProgressListener)
    throws Exception
  {
    return httpGet(paramString, null, paramHttpRequestState, paramHttpProgressListener);
  }

  public String httpGet(String paramString, Header[] paramArrayOfHeader, HttpRequestState paramHttpRequestState, HttpProgressListener paramHttpProgressListener)
    throws Exception
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("rurl", paramString);
    localHashMap.put("ctype", Setting.getInstance().getCType());
    localHashMap.put("oauth_token", User.getInstance().getOauthToken());
    localHashMap.put("device_name", Setting.getInstance().getManufacturerName() + "$!" + Setting.getInstance().getDeviceName());
    Header[] arrayOfHeader = putKeepAlive(paramArrayOfHeader);
    String str1 = Setting.getInstance().getHost() + "/mobile/agent.json";
    try
    {
      String str2 = httpRequest(str1, HttpMethod.GET, localHashMap, arrayOfHeader, paramHttpRequestState, paramHttpProgressListener);
      return str2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    throw localException;
  }

  public String httpGet(HttpGet paramHttpGet)
  {
    this.httpConnection = new HttpConnection(this.mContext);
    return this.httpConnection.httpRequest(paramHttpGet);
  }

  public String httpPost(String paramString, Map<String, Object> paramMap, HttpRequestState paramHttpRequestState, HttpProgressListener paramHttpProgressListener)
    throws Exception
  {
    return httpPost(paramString, paramMap, null, paramHttpRequestState, paramHttpProgressListener);
  }

  public String httpPost(String paramString, Map<String, Object> paramMap, Header[] paramArrayOfHeader, HttpRequestState paramHttpRequestState, HttpProgressListener paramHttpProgressListener)
    throws Exception
  {
    paramMap.put("rurl", paramString);
    paramMap.put("ctype", Setting.getInstance().getCType());
    paramMap.put("oauth_token", User.getInstance().getOauthToken());
    paramMap.put("device_name", Setting.getInstance().getManufacturerName() + "$!" + Setting.getInstance().getDeviceName());
    Header[] arrayOfHeader = putKeepAlive(paramArrayOfHeader);
    return httpRequest(Setting.getInstance().getHost() + "/mobile/agent.json", HttpMethod.POST, paramMap, arrayOfHeader, paramHttpRequestState, paramHttpProgressListener);
  }

  public String httpRequest(String paramString, HttpMethod paramHttpMethod, Map<String, Object> paramMap, Header[] paramArrayOfHeader, HttpRequestState paramHttpRequestState, HttpProgressListener paramHttpProgressListener)
    throws Exception
  {
    XAuth.generateURLParams(paramString, paramHttpMethod.name(), paramMap, XAuth.CONSUMER_KEY, XAuth.CONSUMER_SECRET, User.getInstance().getOauthTokenSecret());
    this.httpConnection = new HttpConnection(this.mContext);
    return this.httpConnection.httpRequest(paramString, paramHttpMethod, paramMap, paramArrayOfHeader, paramHttpRequestState, paramHttpProgressListener);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.network.HttpProxy
 * JD-Core Version:    0.6.0
 */