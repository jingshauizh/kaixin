package com.tencent.tauth.http;

import android.os.Bundle;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;

public class ClientHttpRequest
{
  private static final String BOUNDRY = "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f";
  private static final String ENDLINE = "\r\n";
  private static final int SET_CONNECTION_TIMEOUT = 5000;
  private static final int SET_SOCKET_TIMEOUT = 10000;
  private static final String TAG = "HttpRequest";

  public static String encodePostBody(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null)
      return "";
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = paramBundle.keySet().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localStringBuilder.toString();
      String str = (String)localIterator.next();
      if (paramBundle.getByteArray(str) != null)
        continue;
      localStringBuilder.append("Content-Disposition: form-data; name=\"" + str + "\"\r\n\r\n" + paramBundle.getString(str));
      localStringBuilder.append("\r\n--" + paramString + "\r\n");
    }
  }

  public static String encodeUrl(Bundle paramBundle)
  {
    if (paramBundle == null)
      return "";
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 1;
    Iterator localIterator = paramBundle.keySet().iterator();
    if (!localIterator.hasNext())
      return localStringBuilder.toString();
    String str = (String)localIterator.next();
    if (i != 0)
      i = 0;
    while (true)
    {
      localStringBuilder.append(URLEncoder.encode(str) + "=" + URLEncoder.encode(paramBundle.getString(str)));
      break;
      localStringBuilder.append("&");
    }
  }

  public static int getFileSizeAtURL(URL paramURL)
  {
    int i = -1;
    try
    {
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)paramURL.openConnection();
      i = localHttpURLConnection.getContentLength();
      localHttpURLConnection.disconnect();
      return i;
    }
    catch (Exception localException)
    {
      TDebug.i("HttpRequest", localException.toString());
    }
    return i;
  }

  // ERROR //
  private static HttpClient getHttpClient()
  {
    // Byte code:
    //   0: invokestatic 139	java/security/KeyStore:getDefaultType	()Ljava/lang/String;
    //   3: invokestatic 143	java/security/KeyStore:getInstance	(Ljava/lang/String;)Ljava/security/KeyStore;
    //   6: astore 16
    //   8: aload 16
    //   10: astore_1
    //   11: aload_1
    //   12: aconst_null
    //   13: aconst_null
    //   14: invokevirtual 147	java/security/KeyStore:load	(Ljava/io/InputStream;[C)V
    //   17: new 149	com/tencent/tauth/http/ClientHttpRequest$TSSLSocketFactory
    //   20: dup
    //   21: aload_1
    //   22: invokespecial 152	com/tencent/tauth/http/ClientHttpRequest$TSSLSocketFactory:<init>	(Ljava/security/KeyStore;)V
    //   25: astore_3
    //   26: aload_3
    //   27: astore 4
    //   29: aload 4
    //   31: getstatic 158	org/apache/http/conn/ssl/SSLSocketFactory:ALLOW_ALL_HOSTNAME_VERIFIER	Lorg/apache/http/conn/ssl/X509HostnameVerifier;
    //   34: invokevirtual 162	org/apache/http/conn/ssl/SSLSocketFactory:setHostnameVerifier	(Lorg/apache/http/conn/ssl/X509HostnameVerifier;)V
    //   37: new 164	org/apache/http/params/BasicHttpParams
    //   40: dup
    //   41: invokespecial 165	org/apache/http/params/BasicHttpParams:<init>	()V
    //   44: astore 5
    //   46: aload 5
    //   48: sipush 10000
    //   51: invokestatic 171	org/apache/http/params/HttpConnectionParams:setConnectionTimeout	(Lorg/apache/http/params/HttpParams;I)V
    //   54: aload 5
    //   56: sipush 10000
    //   59: invokestatic 174	org/apache/http/params/HttpConnectionParams:setSoTimeout	(Lorg/apache/http/params/HttpParams;I)V
    //   62: aload 5
    //   64: getstatic 180	org/apache/http/HttpVersion:HTTP_1_1	Lorg/apache/http/HttpVersion;
    //   67: invokestatic 186	org/apache/http/params/HttpProtocolParams:setVersion	(Lorg/apache/http/params/HttpParams;Lorg/apache/http/ProtocolVersion;)V
    //   70: aload 5
    //   72: ldc 188
    //   74: invokestatic 192	org/apache/http/params/HttpProtocolParams:setContentCharset	(Lorg/apache/http/params/HttpParams;Ljava/lang/String;)V
    //   77: new 194	org/apache/http/conn/scheme/SchemeRegistry
    //   80: dup
    //   81: invokespecial 195	org/apache/http/conn/scheme/SchemeRegistry:<init>	()V
    //   84: astore 6
    //   86: aload 6
    //   88: new 197	org/apache/http/conn/scheme/Scheme
    //   91: dup
    //   92: ldc 199
    //   94: invokestatic 205	org/apache/http/conn/scheme/PlainSocketFactory:getSocketFactory	()Lorg/apache/http/conn/scheme/PlainSocketFactory;
    //   97: bipush 80
    //   99: invokespecial 208	org/apache/http/conn/scheme/Scheme:<init>	(Ljava/lang/String;Lorg/apache/http/conn/scheme/SocketFactory;I)V
    //   102: invokevirtual 212	org/apache/http/conn/scheme/SchemeRegistry:register	(Lorg/apache/http/conn/scheme/Scheme;)Lorg/apache/http/conn/scheme/Scheme;
    //   105: pop
    //   106: aload 6
    //   108: new 197	org/apache/http/conn/scheme/Scheme
    //   111: dup
    //   112: ldc 214
    //   114: aload 4
    //   116: sipush 443
    //   119: invokespecial 208	org/apache/http/conn/scheme/Scheme:<init>	(Ljava/lang/String;Lorg/apache/http/conn/scheme/SocketFactory;I)V
    //   122: invokevirtual 212	org/apache/http/conn/scheme/SchemeRegistry:register	(Lorg/apache/http/conn/scheme/Scheme;)Lorg/apache/http/conn/scheme/Scheme;
    //   125: pop
    //   126: new 216	org/apache/http/impl/conn/tsccm/ThreadSafeClientConnManager
    //   129: dup
    //   130: aload 5
    //   132: aload 6
    //   134: invokespecial 219	org/apache/http/impl/conn/tsccm/ThreadSafeClientConnManager:<init>	(Lorg/apache/http/params/HttpParams;Lorg/apache/http/conn/scheme/SchemeRegistry;)V
    //   137: astore 9
    //   139: aload 5
    //   141: sipush 5000
    //   144: invokestatic 171	org/apache/http/params/HttpConnectionParams:setConnectionTimeout	(Lorg/apache/http/params/HttpParams;I)V
    //   147: aload 5
    //   149: sipush 10000
    //   152: invokestatic 174	org/apache/http/params/HttpConnectionParams:setSoTimeout	(Lorg/apache/http/params/HttpParams;I)V
    //   155: new 221	org/apache/http/impl/client/DefaultHttpClient
    //   158: dup
    //   159: aload 9
    //   161: aload 5
    //   163: invokespecial 224	org/apache/http/impl/client/DefaultHttpClient:<init>	(Lorg/apache/http/conn/ClientConnectionManager;Lorg/apache/http/params/HttpParams;)V
    //   166: areturn
    //   167: astore_0
    //   168: aload_0
    //   169: invokevirtual 227	java/security/KeyStoreException:printStackTrace	()V
    //   172: aconst_null
    //   173: astore_1
    //   174: goto -163 -> 11
    //   177: astore 15
    //   179: aload 15
    //   181: invokevirtual 228	java/security/NoSuchAlgorithmException:printStackTrace	()V
    //   184: goto -167 -> 17
    //   187: astore 14
    //   189: aload 14
    //   191: invokevirtual 229	java/security/cert/CertificateException:printStackTrace	()V
    //   194: goto -177 -> 17
    //   197: astore_2
    //   198: aload_2
    //   199: invokevirtual 230	java/io/IOException:printStackTrace	()V
    //   202: goto -185 -> 17
    //   205: astore 13
    //   207: aload 13
    //   209: invokevirtual 231	java/security/KeyManagementException:printStackTrace	()V
    //   212: aconst_null
    //   213: astore 4
    //   215: goto -186 -> 29
    //   218: astore 12
    //   220: aload 12
    //   222: invokevirtual 228	java/security/NoSuchAlgorithmException:printStackTrace	()V
    //   225: aconst_null
    //   226: astore 4
    //   228: goto -199 -> 29
    //   231: astore 11
    //   233: aload 11
    //   235: invokevirtual 227	java/security/KeyStoreException:printStackTrace	()V
    //   238: aconst_null
    //   239: astore 4
    //   241: goto -212 -> 29
    //   244: astore 10
    //   246: aload 10
    //   248: invokevirtual 232	java/security/UnrecoverableKeyException:printStackTrace	()V
    //   251: aconst_null
    //   252: astore 4
    //   254: goto -225 -> 29
    //
    // Exception table:
    //   from	to	target	type
    //   0	8	167	java/security/KeyStoreException
    //   11	17	177	java/security/NoSuchAlgorithmException
    //   11	17	187	java/security/cert/CertificateException
    //   11	17	197	java/io/IOException
    //   17	26	205	java/security/KeyManagementException
    //   17	26	218	java/security/NoSuchAlgorithmException
    //   17	26	231	java/security/KeyStoreException
    //   17	26	244	java/security/UnrecoverableKeyException
  }

  public static String openUrl(String paramString1, String paramString2, Bundle paramBundle)
    throws IOException
  {
    if (paramString2.equals("GET"))
      paramString1 = paramString1 + encodeUrl(paramBundle);
    TDebug.i("HttpRequest", paramString2 + " URL: " + paramString1);
    Object localObject;
    if (paramString1.startsWith("https"))
    {
      localObject = (HttpsURLConnection)new URL(paramString1).openConnection();
      ((HttpsURLConnection)localObject).setHostnameVerifier(new HostnameVerifier()
      {
        public boolean verify(String paramString, SSLSession paramSSLSession)
        {
          return true;
        }
      });
    }
    try
    {
      2 local2 = new X509TrustManager()
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
      SSLContext localSSLContext = SSLContext.getInstance("TLS");
      localSSLContext.init(null, new TrustManager[] { local2 }, null);
      ((HttpsURLConnection)localObject).setSSLSocketFactory(localSSLContext.getSocketFactory());
      label142: TDebug.i("HttpRequest", "start https");
      while (true)
      {
        ((HttpURLConnection)localObject).setRequestProperty("User-Agent", System.getProperties().getProperty("http.agent") + " ArzenAndroidSDK");
        Bundle localBundle;
        Iterator localIterator1;
        label214: BufferedOutputStream localBufferedOutputStream;
        Iterator localIterator2;
        if (!paramString2.equals("GET"))
        {
          localBundle = new Bundle();
          localIterator1 = paramBundle.keySet().iterator();
          if (localIterator1.hasNext())
            break label460;
          if (!paramBundle.containsKey("method"))
            paramBundle.putString("method", paramString2);
          ((HttpURLConnection)localObject).setRequestMethod("POST");
          ((HttpURLConnection)localObject).setRequestProperty("Content-Type", "multipart/form-data;boundary=" + "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f");
          ((HttpURLConnection)localObject).setDoOutput(true);
          ((HttpURLConnection)localObject).setDoInput(true);
          ((HttpURLConnection)localObject).setRequestProperty("Connection", "Keep-Alive");
          ((HttpURLConnection)localObject).connect();
          localBufferedOutputStream = new BufferedOutputStream(((HttpURLConnection)localObject).getOutputStream());
          localBufferedOutputStream.write(("--" + "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f" + "\r\n").getBytes());
          localBufferedOutputStream.write(encodePostBody(paramBundle, "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f").getBytes());
          localBufferedOutputStream.write(("\r\n" + "--" + "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f" + "\r\n").getBytes());
          if (!localBundle.isEmpty())
          {
            localIterator2 = localBundle.keySet().iterator();
            if (localIterator2.hasNext())
              break label497;
          }
          localBufferedOutputStream.flush();
        }
        try
        {
          String str2 = read(((HttpURLConnection)localObject).getInputStream());
          return str2;
          localObject = (HttpURLConnection)new URL(paramString1).openConnection();
          continue;
          label460: String str1 = (String)localIterator1.next();
          if (paramBundle.getByteArray(str1) == null)
            break label214;
          localBundle.putByteArray(str1, paramBundle.getByteArray(str1));
          break label214;
          label497: String str3 = (String)localIterator2.next();
          localBufferedOutputStream.write(("Content-Disposition: form-data; filename=\"" + str3 + "\"" + "\r\n").getBytes());
          localBufferedOutputStream.write(("Content-Type: content/unknown" + "\r\n" + "\r\n").getBytes());
          localBufferedOutputStream.write(localBundle.getByteArray(str3));
          localBufferedOutputStream.write(("\r\n" + "--" + "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f" + "\r\n").getBytes());
        }
        catch (FileNotFoundException localFileNotFoundException)
        {
          return read(((HttpURLConnection)localObject).getErrorStream());
        }
      }
    }
    catch (Exception localException)
    {
      break label142;
    }
  }

  public static String openUrl(String paramString1, String paramString2, Bundle paramBundle, int paramInt)
    throws MalformedURLException, IOException
  {
    HttpClient localHttpClient = getHttpClient();
    Object localObject1;
    if (paramString2.equals("GET"))
      localObject1 = new HttpGet(paramString1 + encodeUrl(paramBundle));
    int i;
    while (true)
    {
      HttpResponse localHttpResponse = localHttpClient.execute((HttpUriRequest)localObject1);
      i = localHttpResponse.getStatusLine().getStatusCode();
      if (i != 200)
        break;
      return readHttpResponse(localHttpResponse);
      boolean bool = paramString2.equals("POST");
      localObject1 = null;
      if (!bool)
        continue;
      HttpPost localHttpPost = new HttpPost(paramString1);
      Bundle localBundle = new Bundle();
      Iterator localIterator1 = paramBundle.keySet().iterator();
      label130: ByteArrayOutputStream localByteArrayOutputStream;
      Iterator localIterator2;
      if (!localIterator1.hasNext())
      {
        if (paramBundle.containsKey("access_token"))
          paramBundle.putString("access_token", URLDecoder.decode(paramBundle.getString("access_token")));
        localHttpPost.setHeader("Content-Type", "multipart/form-data;boundary=3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f");
        localHttpPost.setHeader("Connection", "Keep-Alive");
        localByteArrayOutputStream = new ByteArrayOutputStream();
        localByteArrayOutputStream.write("--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n".getBytes());
        localByteArrayOutputStream.write(encodePostBody(paramBundle, "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f").getBytes());
        localByteArrayOutputStream.write("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n".getBytes());
        if (!localBundle.isEmpty())
          localIterator2 = localBundle.keySet().iterator();
      }
      while (true)
      {
        if (!localIterator2.hasNext())
        {
          byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
          localByteArrayOutputStream.close();
          localHttpPost.setEntity(new ByteArrayEntity(arrayOfByte));
          localObject1 = localHttpPost;
          break;
          String str1 = (String)localIterator1.next();
          Object localObject2 = paramBundle.get(str1);
          if (!(localObject2 instanceof byte[]))
            break label130;
          localBundle.putByteArray(str1, (byte[])localObject2);
          break label130;
        }
        String str2 = (String)localIterator2.next();
        localByteArrayOutputStream.write(("Content-Disposition: form-data; filename=\"" + str2 + "\"" + "\r\n").getBytes());
        localByteArrayOutputStream.write("Content-Type: content/unknown\r\n\r\n".getBytes());
        localByteArrayOutputStream.write(localBundle.getByteArray(str2));
        localByteArrayOutputStream.write("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n".getBytes());
      }
    }
    throw new IOException("Http statusCode:" + i);
  }

  private static String read(InputStream paramInputStream)
    throws IOException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream), 1000);
    for (String str = localBufferedReader.readLine(); ; str = localBufferedReader.readLine())
    {
      if (str == null)
      {
        paramInputStream.close();
        return localStringBuilder.toString();
      }
      localStringBuilder.append(str);
    }
  }

  private static String readHttpResponse(HttpResponse paramHttpResponse)
    throws IllegalStateException, IOException
  {
    Object localObject = paramHttpResponse.getEntity().getContent();
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

  private static class TSSLSocketFactory extends org.apache.http.conn.ssl.SSLSocketFactory
  {
    SSLContext sslContext = SSLContext.getInstance("TLS");

    public TSSLSocketFactory(KeyStore paramKeyStore)
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
 * Qualified Name:     com.tencent.tauth.http.ClientHttpRequest
 * JD-Core Version:    0.6.0
 */