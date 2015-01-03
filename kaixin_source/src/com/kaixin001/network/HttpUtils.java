package com.kaixin001.network;

import com.kaixin001.model.RequestVo;
import com.kaixin001.parser.BaseParser;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

public class HttpUtils
{
  public static Object get(RequestVo paramRequestVo)
  {
    String str1 = paramRequestVo.getUrl();
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
    HttpConnectionParams.setConnectionTimeout(new BasicHttpParams(), 10000);
    try
    {
      URL localURL = new URL(str1);
      HttpResponse localHttpResponse = localDefaultHttpClient.execute(new HttpGet(new URI(localURL.getProtocol(), localURL.getHost(), localURL.getPath(), localURL.getQuery(), null)));
      int i = localHttpResponse.getStatusLine().getStatusCode();
      int j = paramRequestVo.getSuccessCode();
      Object localObject1 = null;
      if (i == j)
      {
        int k = paramRequestVo.getSuccessCode();
        localObject1 = null;
        if (k == 200)
        {
          String str2 = EntityUtils.toString(localHttpResponse.getEntity(), "UTF-8");
          BaseParser localBaseParser = paramRequestVo.getParser();
          localObject1 = null;
          if (localBaseParser != null)
          {
            Object localObject2 = paramRequestVo.getParser().parse(str2);
            localObject1 = localObject2;
          }
        }
      }
      return localObject1;
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      localClientProtocolException.printStackTrace();
      return null;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      return null;
    }
    catch (URISyntaxException localURISyntaxException)
    {
      localURISyntaxException.printStackTrace();
    }
    return null;
  }

  public static Object post(RequestVo paramRequestVo)
  {
    String str1 = paramRequestVo.getUrl();
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
    HttpPost localHttpPost = new HttpPost(str1);
    HttpConnectionParams.setConnectionTimeout(new BasicHttpParams(), 10000);
    try
    {
      ArrayList localArrayList;
      Iterator localIterator;
      if (paramRequestVo.getRequestParam() == null)
      {
        HashMap localHashMap = paramRequestVo.getRequestParam();
        localArrayList = new ArrayList();
        localIterator = localHashMap.entrySet().iterator();
      }
      while (true)
      {
        if (!localIterator.hasNext())
        {
          localHttpPost.setEntity(new UrlEncodedFormEntity(localArrayList));
          HttpResponse localHttpResponse = localDefaultHttpClient.execute(localHttpPost);
          if ((localHttpResponse.getStatusLine().getStatusCode() != 200) || (localHttpResponse.getEntity() == null))
            break;
          String str2 = EntityUtils.toString(localHttpResponse.getEntity());
          return paramRequestVo.getParser().parse(str2);
        }
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localArrayList.add(new BasicNameValuePair((String)localEntry.getKey(), (String)localEntry.getValue()));
      }
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
      return null;
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      localClientProtocolException.printStackTrace();
      return null;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.network.HttpUtils
 * JD-Core Version:    0.6.0
 */