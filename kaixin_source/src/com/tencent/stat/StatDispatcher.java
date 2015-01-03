package com.tencent.stat;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.tencent.stat.common.RC4;
import com.tencent.stat.common.StatCommonHelper;
import com.tencent.stat.common.StatLogger;
import com.tencent.stat.event.Event;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

class StatDispatcher
{
  private static Context applicationContext;
  private static long dispatcherThreadId;
  private static StatDispatcher instance;
  private static StatLogger logger = StatCommonHelper.getLogger();
  DefaultHttpClient client = null;
  Handler handler = null;

  static
  {
    dispatcherThreadId = -1L;
    instance = null;
    applicationContext = null;
  }

  private StatDispatcher()
  {
    try
    {
      HandlerThread localHandlerThread = new HandlerThread("StatDispatcher");
      localHandlerThread.start();
      dispatcherThreadId = localHandlerThread.getId();
      this.handler = new Handler(localHandlerThread.getLooper());
      BasicHttpParams localBasicHttpParams = new BasicHttpParams();
      HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 10000);
      HttpConnectionParams.setSoTimeout(localBasicHttpParams, 10000);
      this.client = new DefaultHttpClient(localBasicHttpParams);
      this.client.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy()
      {
        public long getKeepAliveDuration(HttpResponse paramHttpResponse, HttpContext paramHttpContext)
        {
          long l = super.getKeepAliveDuration(paramHttpResponse, paramHttpContext);
          if (l == -1L)
            l = 20000L;
          return l;
        }
      });
      if (StatConfig.getStatHttpProxy() != null)
        this.client.getParams().setParameter("http.route.default-proxy", StatConfig.getStatHttpProxy());
      return;
    }
    catch (Throwable localThrowable)
    {
      logger.e(localThrowable);
    }
  }

  static Context getApplicationContext()
  {
    return applicationContext;
  }

  static StatDispatcher getInstance()
  {
    if (instance == null)
      instance = new StatDispatcher();
    return instance;
  }

  static void setApplicationContext(Context paramContext)
  {
    applicationContext = paramContext.getApplicationContext();
  }

  void send(Event paramEvent, StatDispatchCallback paramStatDispatchCallback)
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = paramEvent.toJsonString();
    send(Arrays.asList(arrayOfString), paramStatDispatchCallback);
  }

  void send(List<String> paramList, StatDispatchCallback paramStatDispatchCallback)
  {
    if ((paramList.isEmpty()) || (this.handler == null))
      return;
    this.handler.post(new Runnable(paramList, paramStatDispatchCallback)
    {
      public void run()
      {
        StatDispatcher.this.sendHttpPost(this.val$evs, this.val$cb);
      }
    });
  }

  void sendHttpPost(List<String> paramList, StatDispatchCallback paramStatDispatchCallback)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[");
    for (int i = 0; i < paramList.size(); i++)
    {
      localStringBuilder.append((String)paramList.get(i));
      if (i == -1 + paramList.size())
        continue;
      localStringBuilder.append(",");
    }
    localStringBuilder.append("]");
    String str = StatConfig.getStatReportUrl();
    logger.i("[" + str + "]Send request(" + localStringBuilder.toString().length() + "bytes):" + localStringBuilder.toString());
    HttpPost localHttpPost = new HttpPost(str);
    HttpHost localHttpHost;
    ByteArrayOutputStream localByteArrayOutputStream;
    byte[] arrayOfByte1;
    int k;
    HttpResponse localHttpResponse;
    HttpEntity localHttpEntity;
    int m;
    long l;
    try
    {
      localHttpPost.addHeader("Accept-Encoding", "gzip");
      localHttpPost.setHeader("Connection", "Keep-Alive");
      localHttpPost.removeHeaders("Cache-Control");
      localHttpHost = StatCommonHelper.getHttpProxy(applicationContext);
      int j = 0;
      if (localHttpHost != null)
      {
        this.client.getParams().setParameter("http.route.default-proxy", StatCommonHelper.getHttpProxy(applicationContext));
        localHttpPost.addHeader("X-Online-Host", "pingma.qq.com:80");
        localHttpPost.addHeader("Accept", "*/*");
        localHttpPost.addHeader("Content-Type", "json");
        j = 1;
      }
      localByteArrayOutputStream = new ByteArrayOutputStream();
      arrayOfByte1 = localStringBuilder.toString().getBytes("UTF-8");
      k = arrayOfByte1.length;
      if (localStringBuilder.length() < 256)
      {
        if (localHttpHost == null)
          localHttpPost.addHeader("Content-Encoding", "rc4");
        while (true)
        {
          localHttpPost.setEntity(new ByteArrayEntity(RC4.encrypt(arrayOfByte1)));
          localHttpResponse = this.client.execute(localHttpPost);
          if (j != 0)
            this.client.getParams().removeParameter("http.route.default-proxy");
          localHttpEntity = localHttpResponse.getEntity();
          m = localHttpResponse.getStatusLine().getStatusCode();
          l = localHttpEntity.getContentLength();
          logger.i("recv response status code:" + m + ", content length:" + l);
          if (l != 0L)
            break label635;
          EntityUtils.toString(localHttpEntity);
          if (m != 200)
            break;
          if (paramStatDispatchCallback == null)
            return;
          paramStatDispatchCallback.onDispatchSuccess();
          return;
          localHttpPost.addHeader("X-Content-Encoding", "rc4");
        }
      }
    }
    catch (Throwable localThrowable)
    {
      logger.e(localThrowable);
      if (paramStatDispatchCallback == null)
        return;
      paramStatDispatchCallback.onDispatchFailure();
      return;
    }
    finally
    {
    }
    if (localHttpHost == null)
      localHttpPost.addHeader("Content-Encoding", "rc4,gzip");
    while (true)
    {
      localByteArrayOutputStream.write(new byte[4]);
      GZIPOutputStream localGZIPOutputStream = new GZIPOutputStream(localByteArrayOutputStream);
      localGZIPOutputStream.write(arrayOfByte1);
      localGZIPOutputStream.close();
      arrayOfByte1 = localByteArrayOutputStream.toByteArray();
      ByteBuffer.wrap(arrayOfByte1, 0, 4).putInt(k);
      logger.d("before Gzip:" + k + " bytes, after Gzip:" + arrayOfByte1.length + " bytes");
      break;
      localHttpPost.addHeader("X-Content-Encoding", "rc4,gzip");
    }
    logger.error("Server response error code:" + m);
    return;
    label635: if (l > 0L)
    {
      InputStream localInputStream = localHttpEntity.getContent();
      DataInputStream localDataInputStream = new DataInputStream(localInputStream);
      Object localObject2 = new byte[(int)localHttpEntity.getContentLength()];
      localDataInputStream.readFully(localObject2);
      Header localHeader = localHttpResponse.getFirstHeader("Content-Encoding");
      if (localHeader != null)
      {
        if (localHeader.getValue().equalsIgnoreCase("gzip,rc4"))
        {
          byte[] arrayOfByte2 = RC4.decrypt(StatCommonHelper.deocdeGZipContent(localObject2));
          localObject2 = arrayOfByte2;
        }
      }
      else
        if (m != 200)
          break label900;
      while (true)
      {
        try
        {
          logger.d(new String(localObject2, "UTF-8"));
          JSONObject localJSONObject = new JSONObject(new String(localObject2, "UTF-8")).getJSONObject("cfg");
          if (localJSONObject == null)
            continue;
          StatConfig.updateOnlineConfig(localJSONObject);
          if (paramStatDispatchCallback == null)
            continue;
          paramStatDispatchCallback.onDispatchSuccess();
          localInputStream.close();
          return;
          if (!localHeader.getValue().equalsIgnoreCase("rc4,gzip"))
            continue;
          localObject2 = StatCommonHelper.deocdeGZipContent(RC4.decrypt(localObject2));
          break;
          if (!localHeader.getValue().equalsIgnoreCase("gzip"))
            continue;
          localObject2 = StatCommonHelper.deocdeGZipContent(localObject2);
          break;
          if (!localHeader.getValue().equalsIgnoreCase("rc4"))
            break;
          localObject2 = RC4.decrypt(localObject2);
        }
        catch (JSONException localJSONException)
        {
          logger.i(localJSONException.toString());
          continue;
        }
        logger.error("Server response error code:" + m + ", error:" + new String(localObject2, "UTF-8"));
      }
    }
    label900: EntityUtils.toString(localHttpEntity);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.stat.StatDispatcher
 * JD-Core Version:    0.6.0
 */