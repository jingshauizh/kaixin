package com.weibo.sdk.android.net;

import android.text.TextUtils;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.WeiboParameters;
import com.weibo.sdk.android.util.Utility;
import com.weibo.sdk.android.util.Utility.UploadImageUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class HttpManager
{
  private static final String BOUNDARY = getBoundry();
  private static final String END_MP_BOUNDARY;
  public static final String HTTPMETHOD_GET = "GET";
  private static final String HTTPMETHOD_POST = "POST";
  private static final String MP_BOUNDARY = "--" + BOUNDARY;
  private static final String MULTIPART_FORM_DATA = "multipart/form-data";
  private static final int SET_CONNECTION_TIMEOUT = 5000;
  private static final int SET_SOCKET_TIMEOUT = 20000;

  static
  {
    END_MP_BOUNDARY = "--" + BOUNDARY + "--";
  }

  static String getBoundry()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 1;
    if (i >= 12)
      return localStringBuffer.toString();
    long l = System.currentTimeMillis() + i;
    if (l % 3L == 0L)
      localStringBuffer.append((char)(int)l % '\t');
    while (true)
    {
      i++;
      break;
      if (l % 3L == 1L)
      {
        localStringBuffer.append((char)(int)(65L + l % 26L));
        continue;
      }
      localStringBuffer.append((char)(int)(97L + l % 26L));
    }
  }

  private static HttpClient getNewHttpClient()
  {
    try
    {
      KeyStore localKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
      localKeyStore.load(null, null);
      MySSLSocketFactory localMySSLSocketFactory = new MySSLSocketFactory(localKeyStore);
      localMySSLSocketFactory.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
      BasicHttpParams localBasicHttpParams = new BasicHttpParams();
      HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 10000);
      HttpConnectionParams.setSoTimeout(localBasicHttpParams, 10000);
      HttpProtocolParams.setVersion(localBasicHttpParams, HttpVersion.HTTP_1_1);
      HttpProtocolParams.setContentCharset(localBasicHttpParams, "UTF-8");
      SchemeRegistry localSchemeRegistry = new SchemeRegistry();
      localSchemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
      localSchemeRegistry.register(new Scheme("https", localMySSLSocketFactory, 443));
      ThreadSafeClientConnManager localThreadSafeClientConnManager = new ThreadSafeClientConnManager(localBasicHttpParams, localSchemeRegistry);
      HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 5000);
      HttpConnectionParams.setSoTimeout(localBasicHttpParams, 20000);
      DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient(localThreadSafeClientConnManager, localBasicHttpParams);
      return localDefaultHttpClient;
    }
    catch (Exception localException)
    {
    }
    return new DefaultHttpClient();
  }

  // ERROR //
  private static void imageContentToUpload(OutputStream paramOutputStream, String paramString)
    throws WeiboException
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +4 -> 5
    //   4: return
    //   5: new 32	java/lang/StringBuilder
    //   8: dup
    //   9: invokespecial 177	java/lang/StringBuilder:<init>	()V
    //   12: astore_2
    //   13: aload_2
    //   14: getstatic 47	com/weibo/sdk/android/net/HttpManager:MP_BOUNDARY	Ljava/lang/String;
    //   17: invokevirtual 42	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   20: ldc 179
    //   22: invokevirtual 42	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   25: pop
    //   26: aload_2
    //   27: ldc 181
    //   29: invokevirtual 42	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   32: ldc 183
    //   34: invokevirtual 42	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   37: ldc 185
    //   39: invokevirtual 42	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   42: pop
    //   43: aload_2
    //   44: ldc 187
    //   46: invokevirtual 42	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   49: ldc 189
    //   51: invokevirtual 42	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   54: ldc 191
    //   56: invokevirtual 42	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   59: pop
    //   60: aload_2
    //   61: invokevirtual 45	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   64: invokevirtual 197	java/lang/String:getBytes	()[B
    //   67: astore 6
    //   69: aconst_null
    //   70: astore 7
    //   72: aload_0
    //   73: aload 6
    //   75: invokevirtual 203	java/io/OutputStream:write	([B)V
    //   78: new 205	java/io/FileInputStream
    //   81: dup
    //   82: aload_1
    //   83: invokespecial 206	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   86: astore 11
    //   88: ldc 207
    //   90: newarray byte
    //   92: astore 12
    //   94: aload 11
    //   96: aload 12
    //   98: invokevirtual 211	java/io/FileInputStream:read	([B)I
    //   101: istore 13
    //   103: iload 13
    //   105: iconst_m1
    //   106: if_icmpne +60 -> 166
    //   109: aload_0
    //   110: ldc 179
    //   112: invokevirtual 197	java/lang/String:getBytes	()[B
    //   115: invokevirtual 203	java/io/OutputStream:write	([B)V
    //   118: aload_0
    //   119: new 32	java/lang/StringBuilder
    //   122: dup
    //   123: ldc 179
    //   125: invokespecial 38	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   128: getstatic 49	com/weibo/sdk/android/net/HttpManager:END_MP_BOUNDARY	Ljava/lang/String;
    //   131: invokevirtual 42	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   134: invokevirtual 45	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   137: invokevirtual 197	java/lang/String:getBytes	()[B
    //   140: invokevirtual 203	java/io/OutputStream:write	([B)V
    //   143: aload 11
    //   145: ifnull -141 -> 4
    //   148: aload 11
    //   150: invokevirtual 214	java/io/FileInputStream:close	()V
    //   153: return
    //   154: astore 14
    //   156: new 174	com/weibo/sdk/android/WeiboException
    //   159: dup
    //   160: aload 14
    //   162: invokespecial 217	com/weibo/sdk/android/WeiboException:<init>	(Ljava/lang/Exception;)V
    //   165: athrow
    //   166: aload_0
    //   167: aload 12
    //   169: iconst_0
    //   170: iload 13
    //   172: invokevirtual 220	java/io/OutputStream:write	([BII)V
    //   175: goto -81 -> 94
    //   178: astore 8
    //   180: aload 11
    //   182: astore 7
    //   184: new 174	com/weibo/sdk/android/WeiboException
    //   187: dup
    //   188: aload 8
    //   190: invokespecial 217	com/weibo/sdk/android/WeiboException:<init>	(Ljava/lang/Exception;)V
    //   193: athrow
    //   194: astore 9
    //   196: aload 7
    //   198: ifnull +8 -> 206
    //   201: aload 7
    //   203: invokevirtual 214	java/io/FileInputStream:close	()V
    //   206: aload 9
    //   208: athrow
    //   209: astore 10
    //   211: new 174	com/weibo/sdk/android/WeiboException
    //   214: dup
    //   215: aload 10
    //   217: invokespecial 217	com/weibo/sdk/android/WeiboException:<init>	(Ljava/lang/Exception;)V
    //   220: athrow
    //   221: astore 9
    //   223: aload 11
    //   225: astore 7
    //   227: goto -31 -> 196
    //   230: astore 8
    //   232: aconst_null
    //   233: astore 7
    //   235: goto -51 -> 184
    //
    // Exception table:
    //   from	to	target	type
    //   148	153	154	java/io/IOException
    //   88	94	178	java/io/IOException
    //   94	103	178	java/io/IOException
    //   109	143	178	java/io/IOException
    //   166	175	178	java/io/IOException
    //   72	88	194	finally
    //   184	194	194	finally
    //   201	206	209	java/io/IOException
    //   88	94	221	finally
    //   94	103	221	finally
    //   109	143	221	finally
    //   166	175	221	finally
    //   72	88	230	java/io/IOException
  }

  public static String openUrl(String paramString1, String paramString2, WeiboParameters paramWeiboParameters, String paramString3)
    throws WeiboException
  {
    HttpResponse localHttpResponse;
    while (true)
    {
      try
      {
        HttpClient localHttpClient = getNewHttpClient();
        localHttpClient.getParams().setParameter("http.route.default-proxy", NetStateManager.getAPN());
        if (paramString2.equals("GET"))
        {
          localObject = new HttpGet(paramString1 + "?" + Utility.encodeUrl(paramWeiboParameters));
          localHttpResponse = localHttpClient.execute((HttpUriRequest)localObject);
          int i = localHttpResponse.getStatusLine().getStatusCode();
          if (i == 200)
            break;
          throw new WeiboException(readHttpResponse(localHttpResponse), i);
        }
      }
      catch (IOException localIOException)
      {
        throw new WeiboException(localIOException);
      }
      if (paramString2.equals("POST"))
      {
        HttpPost localHttpPost = new HttpPost(paramString1);
        localObject = localHttpPost;
        ((byte[])null);
        String str1 = paramWeiboParameters.getValue("content-type");
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        if (!TextUtils.isEmpty(paramString3))
        {
          paramToUpload(localByteArrayOutputStream, paramWeiboParameters);
          localHttpPost.setHeader("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
          Utility.UploadImageUtils.revitionPostImageSize(paramString3);
          imageContentToUpload(localByteArrayOutputStream, paramString3);
          byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
          localByteArrayOutputStream.close();
          localHttpPost.setEntity(new ByteArrayEntity(arrayOfByte));
          continue;
        }
        if (str1 != null)
        {
          paramWeiboParameters.remove("content-type");
          localHttpPost.setHeader("Content-Type", str1);
        }
        while (true)
        {
          localByteArrayOutputStream.write(Utility.encodeParameters(paramWeiboParameters).getBytes("UTF-8"));
          break;
          localHttpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        }
      }
      boolean bool = paramString2.equals("DELETE");
      Object localObject = null;
      if (!bool)
        continue;
      localObject = new HttpDelete(paramString1);
    }
    String str2 = readHttpResponse(localHttpResponse);
    return (String)str2;
  }

  private static void paramToUpload(OutputStream paramOutputStream, WeiboParameters paramWeiboParameters)
    throws WeiboException
  {
    int i = 0;
    while (true)
    {
      if (i >= paramWeiboParameters.size())
        return;
      String str = paramWeiboParameters.getKey(i);
      StringBuilder localStringBuilder = new StringBuilder(10);
      localStringBuilder.setLength(0);
      localStringBuilder.append(MP_BOUNDARY).append("\r\n");
      localStringBuilder.append("content-disposition: form-data; name=\"").append(str).append("\"\r\n\r\n");
      localStringBuilder.append(paramWeiboParameters.getValue(str)).append("\r\n");
      byte[] arrayOfByte = localStringBuilder.toString().getBytes();
      try
      {
        paramOutputStream.write(arrayOfByte);
        i++;
      }
      catch (IOException localIOException)
      {
      }
    }
    throw new WeiboException(localIOException);
  }

  private static String readHttpResponse(HttpResponse paramHttpResponse)
  {
    HttpEntity localHttpEntity = paramHttpResponse.getEntity();
    try
    {
      Object localObject = localHttpEntity.getContent();
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      Header localHeader = paramHttpResponse.getFirstHeader("Content-Encoding");
      if ((localHeader != null) && (localHeader.getValue().toLowerCase().indexOf("gzip") > -1))
        localObject = new GZIPInputStream((InputStream)localObject);
      byte[] arrayOfByte = new byte[512];
      while (true)
      {
        int i = ((InputStream)localObject).read(arrayOfByte);
        if (i == -1)
          return new String(localByteArrayOutputStream.toByteArray());
        localByteArrayOutputStream.write(arrayOfByte, 0, i);
      }
    }
    catch (IllegalStateException localIllegalStateException)
    {
      return "";
    }
    catch (IOException localIOException)
    {
      label120: break label120;
    }
  }

  private static class MySSLSocketFactory extends org.apache.http.conn.ssl.SSLSocketFactory
  {
    SSLContext sslContext = SSLContext.getInstance("TLS");

    public MySSLSocketFactory(KeyStore paramKeyStore)
      throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
    {
      super();
      1 local1 = new X509TrustManager()
      {
        public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
          throws CertificateException
        {
        }

        public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
          throws CertificateException
        {
        }

        public X509Certificate[] getAcceptedIssuers()
        {
          return null;
        }
      };
      this.sslContext.init(null, new TrustManager[] { local1 }, null);
    }

    public Socket createSocket()
      throws IOException
    {
      return this.sslContext.getSocketFactory().createSocket();
    }

    public Socket createSocket(Socket paramSocket, String paramString, int paramInt, boolean paramBoolean)
      throws IOException, UnknownHostException
    {
      return this.sslContext.getSocketFactory().createSocket(paramSocket, paramString, paramInt, paramBoolean);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.weibo.sdk.android.net.HttpManager
 * JD-Core Version:    0.6.0
 */