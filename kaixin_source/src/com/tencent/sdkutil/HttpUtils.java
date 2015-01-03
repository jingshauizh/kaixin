package com.tencent.sdkutil;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.webkit.JavascriptInterface;
import com.tencent.common.OpenConfig;
import com.tencent.jsutil.JsBridge;
import com.tencent.jsutil.JsConfig;
import com.tencent.mta.TencentStat;
import com.tencent.open.HttpStatusException;
import com.tencent.open.NetworkUnavailableException;
import com.tencent.open.cgireport.ReportManager;
import com.tencent.record.debug.WnsClientLog;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.QQToken;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.CharConversionException;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.NotActiveException;
import java.io.NotSerializableException;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.io.SyncFailedException;
import java.io.UTFDataFormatException;
import java.io.UnsupportedEncodingException;
import java.io.WriteAbortedException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileLockInterruptionException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.UnmappableCharacterException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLKeyException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLProtocolException;
import org.apache.http.ConnectionClosedException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.MalformedChunkCodingException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpUtils
{
  private static final String BOUNDRY = "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f";
  private static final String ENDLINE = "\r\n";
  private static final int REQUEST_RETRY_TIME = 3;
  private static final int SET_CONNECTION_TIMEOUT = 15000;
  private static final int SET_SOCKET_TIMEOUT = 30000;
  private static final String TAG = "HttpUtils";
  private static String appId = "222222";
  private static String resultStr = "success";
  private Context context;
  private JsBridge mBridge;
  private QQToken mQQToken;

  public HttpUtils(Context paramContext, QQToken paramQQToken)
  {
    this.context = paramContext;
    this.mBridge = JsBridge.getInstance(this.context, JsConfig.mTencentFileProtocolPath);
    this.mQQToken = paramQQToken;
    appId = this.mQQToken.getAppId();
  }

  public static int compareVersion(String paramString1, String paramString2)
  {
    if ((paramString1 == null) && (paramString2 == null))
      return 0;
    if ((paramString1 != null) && (paramString2 == null))
      return 1;
    if ((paramString1 == null) && (paramString2 != null))
      return -1;
    String[] arrayOfString1 = paramString1.split("\\.");
    String[] arrayOfString2 = paramString2.split("\\.");
    for (int i = 0; ; i++)
    {
      int k;
      int m;
      try
      {
        if ((i < arrayOfString1.length) && (i < arrayOfString2.length))
        {
          k = Integer.parseInt(arrayOfString1[i]);
          m = Integer.parseInt(arrayOfString2[i]);
          if (k < m)
            return -1;
        }
        else
        {
          if (arrayOfString1.length > i)
            return 1;
          int j = arrayOfString2.length;
          if (j <= i)
            break;
          return -1;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        return paramString1.compareTo(paramString2);
      }
      if (k > m)
        return 1;
    }
  }

  public static String encodePostBody(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null)
      return "";
    StringBuilder localStringBuilder = new StringBuilder();
    int i = paramBundle.size();
    Iterator localIterator = paramBundle.keySet().iterator();
    int j = -1;
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      int k = j + 1;
      Object localObject = paramBundle.get(str);
      if (!(localObject instanceof String))
      {
        j = k;
        continue;
      }
      localStringBuilder.append("Content-Disposition: form-data; name=\"" + str + "\"" + "\r\n" + "\r\n" + (String)localObject);
      if (k < i - 1)
        localStringBuilder.append("\r\n--" + paramString + "\r\n");
      j = k;
    }
    return localStringBuilder.toString();
  }

  public static String encodeUrl(Bundle paramBundle)
  {
    if (paramBundle == null)
      return "";
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = paramBundle.keySet().iterator();
    int i = 1;
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Object localObject = paramBundle.get(str);
      if ((!(localObject instanceof String)) && (!(localObject instanceof String[])))
        continue;
      label127: int j;
      if ((localObject instanceof String[]))
      {
        String[] arrayOfString;
        int k;
        if (i != 0)
        {
          i = 0;
          localStringBuilder.append(URLEncoder.encode(str) + "=");
          arrayOfString = (String[])paramBundle.getStringArray(str);
          k = 0;
          if (k >= arrayOfString.length)
            break label203;
          if (k != 0)
            break label169;
          localStringBuilder.append(URLEncoder.encode(arrayOfString[k]));
        }
        while (true)
        {
          k++;
          break label127;
          localStringBuilder.append("&");
          break;
          label169: localStringBuilder.append(URLEncoder.encode("," + arrayOfString[k]));
        }
        label203: j = i;
        i = j;
        continue;
      }
      if (i != 0)
        i = 0;
      while (true)
      {
        localStringBuilder.append(URLEncoder.encode(str) + "=" + URLEncoder.encode(paramBundle.getString(str)));
        j = i;
        break;
        localStringBuilder.append("&");
      }
    }
    return localStringBuilder.toString();
  }

  private static int getErrorCodeFromException(IOException paramIOException)
  {
    if ((paramIOException instanceof CharConversionException))
      return -20;
    if ((paramIOException instanceof MalformedInputException))
      return -21;
    if ((paramIOException instanceof UnmappableCharacterException))
      return -22;
    if ((paramIOException instanceof HttpResponseException))
      return -23;
    if ((paramIOException instanceof ClosedChannelException))
      return -24;
    if ((paramIOException instanceof ConnectionClosedException))
      return -25;
    if ((paramIOException instanceof EOFException))
      return -26;
    if ((paramIOException instanceof FileLockInterruptionException))
      return -27;
    if ((paramIOException instanceof FileNotFoundException))
      return -28;
    if ((paramIOException instanceof HttpRetryException))
      return -29;
    if ((paramIOException instanceof ConnectTimeoutException))
      return -7;
    if ((paramIOException instanceof SocketTimeoutException))
      return -8;
    if ((paramIOException instanceof InvalidPropertiesFormatException))
      return -30;
    if ((paramIOException instanceof MalformedChunkCodingException))
      return -31;
    if ((paramIOException instanceof MalformedURLException))
      return -3;
    if ((paramIOException instanceof NoHttpResponseException))
      return -32;
    if ((paramIOException instanceof InvalidClassException))
      return -33;
    if ((paramIOException instanceof InvalidObjectException))
      return -34;
    if ((paramIOException instanceof NotActiveException))
      return -35;
    if ((paramIOException instanceof NotSerializableException))
      return -36;
    if ((paramIOException instanceof OptionalDataException))
      return -37;
    if ((paramIOException instanceof StreamCorruptedException))
      return -38;
    if ((paramIOException instanceof WriteAbortedException))
      return -39;
    if ((paramIOException instanceof ProtocolException))
      return -40;
    if ((paramIOException instanceof SSLHandshakeException))
      return -41;
    if ((paramIOException instanceof SSLKeyException))
      return -42;
    if ((paramIOException instanceof SSLPeerUnverifiedException))
      return -43;
    if ((paramIOException instanceof SSLProtocolException))
      return -44;
    if ((paramIOException instanceof BindException))
      return -45;
    if ((paramIOException instanceof ConnectException))
      return -46;
    if ((paramIOException instanceof NoRouteToHostException))
      return -47;
    if ((paramIOException instanceof PortUnreachableException))
      return -48;
    if ((paramIOException instanceof SyncFailedException))
      return -49;
    if ((paramIOException instanceof UTFDataFormatException))
      return -50;
    if ((paramIOException instanceof UnknownHostException))
      return -51;
    if ((paramIOException instanceof UnknownServiceException))
      return -52;
    if ((paramIOException instanceof UnsupportedEncodingException))
      return -53;
    if ((paramIOException instanceof ZipException))
      return -54;
    return -2;
  }

  public static String getFromUrl(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
      localHttpURLConnection.setConnectTimeout(5000);
      localHttpURLConnection.setRequestMethod("GET");
      localHttpURLConnection.connect();
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localHttpURLConnection.getInputStream()));
      while (true)
      {
        String str = localBufferedReader.readLine();
        if (str == null)
          break;
        localStringBuffer.append(str);
      }
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localMalformedURLException.printStackTrace();
      return localStringBuffer.toString();
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }

  public static HttpClient getHttpClient(Context paramContext, String paramString1, String paramString2)
  {
    SchemeRegistry localSchemeRegistry = new SchemeRegistry();
    localSchemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
    if (Build.VERSION.SDK_INT < 16);
    while (true)
    {
      try
      {
        KeyStore localKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        localKeyStore.load(null, null);
        HttpUtils.CustomSSLSocketFactory localCustomSSLSocketFactory = new HttpUtils.CustomSSLSocketFactory(localKeyStore);
        localCustomSSLSocketFactory.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
        localSchemeRegistry.register(new Scheme("https", localCustomSSLSocketFactory, 443));
        BasicHttpParams localBasicHttpParams = new BasicHttpParams();
        int i = OpenConfig.a(paramContext, paramString1).b("Common_HttpConnectionTimeout");
        if (i != 0)
          continue;
        i = 15000;
        HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, i);
        int j = OpenConfig.a(paramContext, paramString1).b("Common_SocketConnectionTimeout");
        if (j != 0)
          continue;
        j = 30000;
        HttpConnectionParams.setSoTimeout(localBasicHttpParams, j);
        HttpProtocolParams.setVersion(localBasicHttpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(localBasicHttpParams, "UTF-8");
        HttpProtocolParams.setUserAgent(localBasicHttpParams, "AndroidSDK_" + Build.VERSION.SDK + "_" + Build.DEVICE + "_" + Build.VERSION.RELEASE);
        DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(localBasicHttpParams, localSchemeRegistry), localBasicHttpParams);
        HttpUtils.NetworkProxy localNetworkProxy = getProxy(paramContext);
        if (localNetworkProxy == null)
          continue;
        HttpHost localHttpHost = new HttpHost(localNetworkProxy.host, localNetworkProxy.port);
        localDefaultHttpClient.getParams().setParameter("http.route.default-proxy", localHttpHost);
        return localDefaultHttpClient;
      }
      catch (Exception localException)
      {
        localSchemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        continue;
      }
      localSchemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
    }
  }

  public static HttpUtils.NetworkProxy getProxy(Context paramContext)
  {
    if (paramContext == null)
      return null;
    ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
    if (localConnectivityManager == null)
      return null;
    NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
    if (localNetworkInfo == null)
      return null;
    if (localNetworkInfo.getType() == 0)
    {
      String str = getProxyHost(paramContext);
      int i = getProxyPort(paramContext);
      if ((!TextUtils.isEmpty(str)) && (i >= 0))
        return new HttpUtils.NetworkProxy(str, i, null);
    }
    return null;
  }

  private static String getProxyHost(Context paramContext)
  {
    if (Build.VERSION.SDK_INT < 11)
    {
      if (paramContext != null)
      {
        String str = Proxy.getHost(paramContext);
        if (TextUtils.isEmpty(str))
          str = Proxy.getDefaultHost();
        return str;
      }
      return Proxy.getDefaultHost();
    }
    return System.getProperty("http.proxyHost");
  }

  private static int getProxyPort(Context paramContext)
  {
    int i = -1;
    if (Build.VERSION.SDK_INT < 11)
      if (paramContext != null)
      {
        i = Proxy.getPort(paramContext);
        if (i < 0)
          i = Proxy.getDefaultPort();
      }
    String str;
    do
    {
      return i;
      return Proxy.getDefaultPort();
      str = System.getProperty("http.proxyPort");
    }
    while (TextUtils.isEmpty(str));
    try
    {
      int j = Integer.parseInt(str);
      return j;
    }
    catch (NumberFormatException localNumberFormatException)
    {
    }
    return i;
  }

  private static boolean isQQBrowserAvailable(Context paramContext)
  {
    try
    {
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo("com.tencent.mtt", 64);
      String str1 = localPackageInfo.versionName;
      int i = compareVersion(str1, "4.3");
      int j = 0;
      Signature[] arrayOfSignature;
      if (i >= 0)
      {
        boolean bool1 = str1.startsWith("4.4");
        j = 0;
        if (!bool1)
        {
          arrayOfSignature = localPackageInfo.signatures;
          j = 0;
          if (arrayOfSignature == null);
        }
      }
      try
      {
        MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
        localMessageDigest.update(arrayOfSignature[0].toByteArray());
        String str2 = Util.toHexString(localMessageDigest.digest());
        localMessageDigest.reset();
        boolean bool2 = str2.equals("d8391a394d4a179e6fe7bdb8a301258b");
        j = 0;
        if (bool2)
          j = 1;
        return j;
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
      {
        localNoSuchAlgorithmException.printStackTrace();
        return false;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
    return false;
  }

  public static boolean isWifiDataEnable(Context paramContext)
  {
    return ((ConnectivityManager)paramContext.getSystemService("connectivity")).getNetworkInfo(1).isConnectedOrConnecting();
  }

  private static void loadUrlWithBrowser(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    Intent localIntent = new Intent();
    localIntent.setComponent(new ComponentName(paramString1, paramString2));
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.addFlags(1073741824);
    localIntent.addFlags(268435456);
    localIntent.setData(Uri.parse(paramString3));
    paramContext.startActivity(localIntent);
  }

  public static boolean openBrowser(Context paramContext, String paramString)
  {
    try
    {
      boolean bool2 = isQQBrowserAvailable(paramContext);
      bool1 = bool2;
      if (bool1);
      try
      {
        loadUrlWithBrowser(paramContext, "com.tencent.mtt", "com.tencent.mtt.MainActivity", paramString);
        break label137;
        loadUrlWithBrowser(paramContext, "com.android.browser", "com.android.browser.BrowserActivity", paramString);
      }
      catch (Exception localException7)
      {
      }
      if (bool1)
        try
        {
          loadUrlWithBrowser(paramContext, "com.android.browser", "com.android.browser.BrowserActivity", paramString);
        }
        catch (Exception localException4)
        {
          try
          {
            loadUrlWithBrowser(paramContext, "com.google.android.browser", "com.android.browser.BrowserActivity", paramString);
          }
          catch (Exception localException5)
          {
            try
            {
              loadUrlWithBrowser(paramContext, "com.android.chrome", "com.google.android.apps.chrome.Main", paramString);
            }
            catch (Exception localException6)
            {
              return false;
            }
          }
        }
      else
        try
        {
          loadUrlWithBrowser(paramContext, "com.google.android.browser", "com.android.browser.BrowserActivity", paramString);
        }
        catch (Exception localException2)
        {
          try
          {
            loadUrlWithBrowser(paramContext, "com.android.chrome", "com.google.android.apps.chrome.Main", paramString);
          }
          catch (Exception localException3)
          {
            return false;
          }
        }
    }
    catch (Exception localException1)
    {
      while (true)
        boolean bool1 = false;
    }
    label137: return true;
  }

  public static HttpUtils.Statistic openUrl(Context paramContext, String paramString1, String paramString2, Bundle paramBundle)
  {
    if (paramContext != null)
    {
      ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
      if (localConnectivityManager != null)
      {
        NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
        if ((localNetworkInfo == null) || (!localNetworkInfo.isAvailable()))
        {
          resultStr = "network_unavailabe";
          throw new NetworkUnavailableException("network unavailable");
        }
      }
    }
    Bundle localBundle1;
    HttpClient localHttpClient;
    String str5;
    label158: Object localObject1;
    int i;
    if (paramBundle != null)
    {
      localBundle1 = new Bundle(paramBundle);
      localBundle1.getString("appid_for_getting_config");
      localBundle1.remove("appid_for_getting_config");
      localHttpClient = getHttpClient(paramContext, appId, paramString1);
      if (!paramString2.equals("GET"))
        break label297;
      String str4 = encodeUrl(localBundle1);
      int m = 0 + str4.length();
      if (paramString1.indexOf("?") != -1)
        break label273;
      str5 = paramString1 + "?";
      HttpGet localHttpGet = new HttpGet(str5 + str4);
      localHttpGet.addHeader("Accept-Encoding", "gzip");
      localObject1 = localHttpGet;
      i = m;
    }
    while (true)
    {
      HttpResponse localHttpResponse = localHttpClient.execute((HttpUriRequest)localObject1);
      if (localHttpResponse.getStatusLine().getStatusCode() == 200)
      {
        String str1 = readHttpResponse(localHttpResponse);
        resultStr = "success";
        return new HttpUtils.Statistic(str1, i);
        localBundle1 = new Bundle();
        break;
        label273: str5 = paramString1 + "&";
        break label158;
        label297: if (paramString2.equals("POST"))
        {
          HttpPost localHttpPost = new HttpPost(paramString1);
          localHttpPost.addHeader("Accept-Encoding", "gzip");
          Bundle localBundle2 = new Bundle();
          Iterator localIterator1 = localBundle1.keySet().iterator();
          while (localIterator1.hasNext())
          {
            String str3 = (String)localIterator1.next();
            Object localObject2 = localBundle1.get(str3);
            if (!(localObject2 instanceof byte[]))
              continue;
            localBundle2.putByteArray(str3, (byte[])(byte[])localObject2);
          }
          if (!localBundle1.containsKey("method"))
            localBundle1.putString("method", paramString2);
          localHttpPost.setHeader("Content-Type", "multipart/form-data; boundary=3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f");
          localHttpPost.setHeader("Connection", "Keep-Alive");
          ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
          localByteArrayOutputStream.write("--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n".getBytes());
          localByteArrayOutputStream.write(encodePostBody(localBundle1, "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f").getBytes());
          if (!localBundle2.isEmpty())
          {
            int j = localBundle2.size();
            localByteArrayOutputStream.write("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n".getBytes());
            Iterator localIterator2 = localBundle2.keySet().iterator();
            int k = -1;
            while (localIterator2.hasNext())
            {
              String str2 = (String)localIterator2.next();
              k++;
              localByteArrayOutputStream.write(("Content-Disposition: form-data; name=\"" + str2 + "\"; filename=\"" + str2 + "\"" + "\r\n").getBytes());
              localByteArrayOutputStream.write("Content-Type: content/unknown\r\n\r\n".getBytes());
              byte[] arrayOfByte2 = localBundle2.getByteArray(str2);
              if (arrayOfByte2 != null)
                localByteArrayOutputStream.write(arrayOfByte2);
              if (k >= j - 1)
                continue;
              localByteArrayOutputStream.write("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n".getBytes());
            }
          }
          localByteArrayOutputStream.write("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f--\r\n".getBytes());
          byte[] arrayOfByte1 = localByteArrayOutputStream.toByteArray();
          i = 0 + arrayOfByte1.length;
          localByteArrayOutputStream.close();
          localHttpPost.setEntity(new ByteArrayEntity(arrayOfByte1));
          localObject1 = localHttpPost;
          continue;
        }
      }
      else
      {
        return new HttpUtils.Statistic("http_status", 0);
      }
      i = 0;
      localObject1 = null;
    }
  }

  public static String openUrlForWapOnlineState(Context paramContext, String paramString1, String paramString2)
  {
    new URL(paramString2);
    if (paramContext != null)
    {
      ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
      if (localConnectivityManager != null)
      {
        NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
        if ((localNetworkInfo == null) || (!localNetworkInfo.isAvailable()))
          throw new NetworkUnavailableException("network unavailable");
      }
    }
    HttpResponse localHttpResponse = getHttpClient(paramContext, paramString1, null).execute(new HttpGet(paramString2));
    int i = localHttpResponse.getStatusLine().getStatusCode();
    String str;
    StringBuffer localStringBuffer;
    if (i == 200)
    {
      str = EntityUtils.toString(localHttpResponse.getEntity());
      Log.d("Tencent", str);
      localStringBuffer = new StringBuffer("{online:");
      if (str == null)
        break label216;
    }
    label216: for (int j = Integer.parseInt(str.toString().split("=")[1].substring(0, 1)); ; j = 0)
    {
      localStringBuffer.append(j);
      localStringBuffer.append("}");
      return localStringBuffer.toString();
      throw new HttpStatusException("http status code error:" + i);
    }
  }

  private static String readHttpResponse(HttpResponse paramHttpResponse)
  {
    InputStream localInputStream = paramHttpResponse.getEntity().getContent();
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    Header localHeader = paramHttpResponse.getFirstHeader("Content-Encoding");
    if ((localHeader != null) && (localHeader.getValue().toLowerCase().indexOf("gzip") > -1));
    for (Object localObject = new GZIPInputStream(localInputStream); ; localObject = localInputStream)
    {
      byte[] arrayOfByte = new byte[512];
      while (true)
      {
        int i = ((InputStream)localObject).read(arrayOfByte);
        if (i == -1)
          break;
        localByteArrayOutputStream.write(arrayOfByte, 0, i);
      }
      return new String(localByteArrayOutputStream.toByteArray());
    }
  }

  private static void reportStat(Context paramContext, QQToken paramQQToken, String paramString)
  {
    if ((paramString.indexOf("add_share") > -1) || (paramString.indexOf("upload_pic") > -1) || (paramString.indexOf("add_topic") > -1) || (paramString.indexOf("set_user_face") > -1) || (paramString.indexOf("add_t") > -1) || (paramString.indexOf("add_pic_t") > -1) || (paramString.indexOf("add_pic_url") > -1) || (paramString.indexOf("add_video") > -1))
      TencentStat.a(paramContext, paramQQToken, "requireApi", new String[] { paramString });
  }

  public static void requestAsync(QQToken paramQQToken, Context paramContext, String paramString1, Bundle paramBundle, String paramString2, IRequestListener paramIRequestListener)
  {
    WnsClientLog.getInstance().v("openSDK_LOG", "OpenApi requestAsync");
    new HttpUtils.4(paramQQToken, paramContext, paramString1, paramBundle, paramString2, paramIRequestListener, new Object()).start();
  }

  private static JSONObject requestNative(QQToken paramQQToken, Context paramContext, String paramString1, Bundle paramBundle, String paramString2)
  {
    String str2;
    String str1;
    if (!paramString1.toLowerCase().startsWith("http"))
    {
      str2 = ServerSetting.getInstance().getEnvUrl(paramContext, "https://openmobile.qq.com/") + paramString1;
      str1 = ServerSetting.getInstance().getEnvUrl(paramContext, "https://openmobile.qq.com/") + paramString1;
    }
    while (true)
    {
      reportStat(paramContext, paramQQToken, paramString1);
      Object localObject1 = null;
      long l1 = SystemClock.elapsedRealtime();
      int i = OpenConfig.a(paramContext, paramQQToken.getAppId()).b("Common_HttpRetryCount");
      Log.d("OpenConfig_test", "config 1:Common_HttpRetryCount            config_value:" + i + "   appid:" + paramQQToken.getAppId() + "     url:" + str1);
      if (i == 0)
        i = 3;
      Log.d("OpenConfig_test", "config 1:Common_HttpRetryCount            result_value:" + i + "   appid:" + paramQQToken.getAppId() + "     url:" + str1);
      long l2 = l1;
      int j = 0;
      while (true)
      {
        int k = j + 1;
        try
        {
          HttpUtils.Statistic localStatistic = openUrl(paramContext, str2, paramString2, paramBundle);
          JSONObject localJSONObject = JsonUtil.parseJson(localStatistic.response);
          Object localObject5 = localJSONObject;
          try
          {
            int i3 = ((JSONObject)localObject5).getInt("ret");
            m = i3;
            l6 = localStatistic.reqSize;
            l3 = localStatistic.rspSize;
            ReportManager.getInstance().report(paramContext, str1, l2, l6, l3, m, paramQQToken.getAppId());
            return localObject5;
          }
          catch (JSONException localJSONException2)
          {
            while (true)
              m = -4;
          }
          catch (ConnectTimeoutException localConnectTimeoutException2)
          {
            while (true)
            {
              long l6;
              localObject3 = localObject5;
              localObject2 = localConnectTimeoutException2;
              ((ConnectTimeoutException)localObject2).printStackTrace();
              m = -7;
              l3 = 0L;
              if (k < i)
              {
                l2 = SystemClock.elapsedRealtime();
                localObject4 = localObject3;
                l4 = 0L;
                if (k < i)
                  break;
                long l5 = l4;
                localObject5 = localObject4;
                l6 = l5;
                continue;
              }
              else
              {
                ReportManager.getInstance().report(paramContext, str1, l2, 0L, l3, m, paramQQToken.getAppId());
                throw ((Throwable)localObject2);
              }
            }
          }
          catch (SocketTimeoutException localSocketTimeoutException2)
          {
            int m;
            long l3;
            while (true)
            {
              localObject7 = localObject5;
              localObject6 = localSocketTimeoutException2;
              ((SocketTimeoutException)localObject6).printStackTrace();
              m = -8;
              l3 = 0L;
              if (k >= i)
                break;
              l2 = SystemClock.elapsedRealtime();
              localObject4 = localObject7;
              long l4 = 0L;
            }
            ReportManager.getInstance().report(paramContext, str1, l2, 0L, l3, m, paramQQToken.getAppId());
            throw ((Throwable)localObject6);
          }
        }
        catch (HttpStatusException localHttpStatusException)
        {
          localHttpStatusException.printStackTrace();
          String str3 = localHttpStatusException.getMessage();
          try
          {
            int i2 = Integer.parseInt(str3.replace("http status code error:", ""));
            i1 = i2;
            ReportManager.getInstance().report(paramContext, str1, l2, 0L, 0L, i1, paramQQToken.getAppId());
            throw localHttpStatusException;
          }
          catch (Exception localException)
          {
            while (true)
            {
              localException.printStackTrace();
              int i1 = -9;
            }
          }
        }
        catch (NetworkUnavailableException localNetworkUnavailableException)
        {
          localNetworkUnavailableException.printStackTrace();
          throw localNetworkUnavailableException;
        }
        catch (MalformedURLException localMalformedURLException)
        {
          localMalformedURLException.printStackTrace();
          ReportManager.getInstance().report(paramContext, str1, l2, 0L, 0L, -3, paramQQToken.getAppId());
          throw localMalformedURLException;
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
          int n = getErrorCodeFromException(localIOException);
          ReportManager.getInstance().report(paramContext, str1, l2, 0L, 0L, n, paramQQToken.getAppId());
          throw localIOException;
        }
        catch (JSONException localJSONException1)
        {
          localJSONException1.printStackTrace();
          ReportManager.getInstance().report(paramContext, str1, l2, 0L, 0L, -4, paramQQToken.getAppId());
          throw localJSONException1;
        }
        catch (SocketTimeoutException localSocketTimeoutException1)
        {
          while (true)
          {
            Object localObject6 = localSocketTimeoutException1;
            Object localObject7 = localObject1;
          }
        }
        catch (ConnectTimeoutException localConnectTimeoutException1)
        {
          Object localObject4;
          while (true)
          {
            Object localObject2 = localConnectTimeoutException1;
            Object localObject3 = localObject1;
          }
          localObject1 = localObject4;
          j = k;
        }
      }
      str1 = paramString1;
      str2 = paramString1;
    }
  }

  public static JSONObject upload(Context paramContext, String paramString, Bundle paramBundle)
  {
    if (paramContext != null)
    {
      ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
      if (localConnectivityManager != null)
      {
        NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
        if ((localNetworkInfo == null) || (!localNetworkInfo.isAvailable()))
          throw new NetworkUnavailableException("network unavailable");
      }
    }
    Bundle localBundle1 = new Bundle(paramBundle);
    String str1 = localBundle1.getString("appid_for_getting_config");
    localBundle1.remove("appid_for_getting_config");
    HttpClient localHttpClient = getHttpClient(paramContext, str1, paramString);
    HttpPost localHttpPost = new HttpPost(paramString);
    Bundle localBundle2 = new Bundle();
    Iterator localIterator1 = localBundle1.keySet().iterator();
    while (localIterator1.hasNext())
    {
      String str3 = (String)localIterator1.next();
      Object localObject = localBundle1.get(str3);
      if (!(localObject instanceof byte[]))
        continue;
      localBundle2.putByteArray(str3, (byte[])(byte[])localObject);
    }
    localHttpPost.setHeader("Content-Type", "multipart/form-data; boundary=3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f");
    localHttpPost.setHeader("Connection", "Keep-Alive");
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    localByteArrayOutputStream.write("--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n".getBytes());
    localByteArrayOutputStream.write(encodePostBody(localBundle1, "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f").getBytes());
    if (!localBundle2.isEmpty())
    {
      int k = localBundle2.size();
      localByteArrayOutputStream.write("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n".getBytes());
      Iterator localIterator2 = localBundle2.keySet().iterator();
      int m = -1;
      while (localIterator2.hasNext())
      {
        String str2 = (String)localIterator2.next();
        m++;
        localByteArrayOutputStream.write(("Content-Disposition: form-data; name=\"" + str2 + "\"; filename=\"" + "value.file" + "\"" + "\r\n").getBytes());
        localByteArrayOutputStream.write("Content-Type: application/octet-stream\r\n\r\n".getBytes());
        localByteArrayOutputStream.write(localBundle2.getByteArray(str2));
        if (m >= k - 1)
          continue;
        localByteArrayOutputStream.write("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n".getBytes());
      }
    }
    localByteArrayOutputStream.write("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f--\r\n".getBytes());
    byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
    int i = 0 + arrayOfByte.length;
    localByteArrayOutputStream.close();
    localHttpPost.setEntity(new ByteArrayEntity(arrayOfByte));
    HttpResponse localHttpResponse = localHttpClient.execute(localHttpPost);
    int j = localHttpResponse.getStatusLine().getStatusCode();
    if (j == 200)
      return JsonUtil.parseJson(new HttpUtils.Statistic(readHttpResponse(localHttpResponse), i).response);
    throw new HttpStatusException("http status code error:" + j);
  }

  @JavascriptInterface
  public void AsynLoadImage(String paramString1, String paramString2)
  {
    JSONObject localJSONObject = new JSONObject(paramString1);
    String str = localJSONObject.get("object_imageUrl").toString();
    new AsynLoadImg().save(str, new HttpUtils.1(this, localJSONObject, paramString2));
  }

  @JavascriptInterface
  public void AsynscaleCompressImage(String paramString1, String paramString2)
  {
    JSONObject localJSONObject = new JSONObject(paramString1);
    Log.d("AsynscaleCompressImage", "MessageJson:" + localJSONObject.toString());
    String str = localJSONObject.get("object_imageUrl").toString();
    Log.d("AsynscaleCompressImage", "imageUrl:" + str);
    Object localObject = localJSONObject.get("IsImageUrlArrayList");
    Log.d("AsynscaleCompressImage", "here");
    if ((localObject instanceof Boolean))
    {
      if (((Boolean)localObject).booleanValue())
      {
        Log.d("AsynscaleCompressImage", "多图压缩");
        ArrayList localArrayList = new ArrayList();
        String[] arrayOfString = str.split(",");
        int i = arrayOfString.length;
        for (int j = 0; j < i; j++)
          localArrayList.add(arrayOfString[j]);
        AsynScaleCompressImage.batchScaleCompressImage(this.context, localArrayList, new HttpUtils.2(this, localJSONObject, paramString2));
      }
    }
    else
      return;
    Log.d("AsynscaleCompressImage", "单图压缩");
    AsynScaleCompressImage.scaleCompressImage(this.context, str, new HttpUtils.3(this, localJSONObject, paramString2));
  }

  @JavascriptInterface
  public String Base64encode(String paramString)
  {
    return Base64.encodeToString(paramString.getBytes(), 2);
  }

  public Bundle decodeUrl(String paramString)
  {
    Bundle localBundle = new Bundle();
    if (paramString != null)
      for (String str : paramString.split("&"))
      {
        if (str == null)
          continue;
        String[] arrayOfString2 = str.split("=");
        if (arrayOfString2.length != 2)
          continue;
        localBundle.putString(URLDecoder.decode(arrayOfString2[0]), URLDecoder.decode(arrayOfString2[1]));
      }
    return localBundle;
  }

  public JSONObject decodeUrlToJson(JSONObject paramJSONObject, String paramString)
  {
    if (paramJSONObject == null)
      paramJSONObject = new JSONObject();
    if (paramString != null)
    {
      String[] arrayOfString1 = paramString.split("&");
      int i = arrayOfString1.length;
      int j = 0;
      while (true)
        if (j < i)
        {
          String[] arrayOfString2 = arrayOfString1[j].split("=");
          if (arrayOfString2.length == 2);
          try
          {
            paramJSONObject.put(URLDecoder.decode(arrayOfString2[0]), URLDecoder.decode(arrayOfString2[1]));
            j++;
          }
          catch (JSONException localJSONException)
          {
            while (true)
              localJSONException.printStackTrace();
          }
        }
    }
    return paramJSONObject;
  }

  public String encodeBundleUrlToString(Bundle paramBundle)
  {
    if (paramBundle == null)
      return "";
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = paramBundle.keySet().iterator();
    int i = 1;
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Object localObject = paramBundle.get(str);
      if ((!(localObject instanceof String)) && (!(localObject instanceof String[])))
        continue;
      label130: label172: label206: int j;
      if ((localObject instanceof String[]))
      {
        String[] arrayOfString;
        int k;
        if (i != 0)
        {
          i = 0;
          localStringBuilder.append(URLEncoder.encode(str) + "=");
          arrayOfString = (String[])paramBundle.getStringArray(str);
          k = 0;
          if (k >= arrayOfString.length)
            break label206;
          if (k != 0)
            break label172;
          localStringBuilder.append(URLEncoder.encode(arrayOfString[k]));
        }
        while (true)
        {
          k++;
          break label130;
          localStringBuilder.append("&");
          break;
          localStringBuilder.append(URLEncoder.encode("," + arrayOfString[k]));
        }
        j = i;
        i = j;
        continue;
      }
      if (i != 0)
        i = 0;
      while (true)
      {
        localStringBuilder.append(URLEncoder.encode(str) + "=" + URLEncoder.encode(paramBundle.getString(str)));
        j = i;
        break;
        localStringBuilder.append("&");
      }
    }
    return localStringBuilder.toString();
  }

  public String encodeUrl(String paramString)
  {
    if (paramString == null)
      return "";
    StringBuilder localStringBuilder = new StringBuilder();
    JSONObject localJSONObject = new JSONObject(paramString);
    Iterator localIterator = localJSONObject.keys();
    while (localIterator.hasNext())
    {
      String str = localIterator.next().toString();
      localStringBuilder.append("&");
      localStringBuilder.append(URLEncoder.encode(str) + "=");
      if (!localJSONObject.has(str))
        continue;
      localStringBuilder.append(URLEncoder.encode(localJSONObject.get(str).toString()));
    }
    return localStringBuilder.toString();
  }

  @JavascriptInterface
  public String getUrl(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder(paramString1);
    while (true)
    {
      int j;
      try
      {
        JSONObject localJSONObject = new JSONObject(paramString2);
        Iterator localIterator = localJSONObject.keys();
        i = 1;
        if (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          if (i == 0)
            continue;
          localStringBuilder.append(URLEncoder.encode(str) + "=" + URLEncoder.encode(localJSONObject.getString(str)));
          j = 0;
          break label172;
          localStringBuilder.append("&" + URLEncoder.encode(str) + "=" + URLEncoder.encode(localJSONObject.getString(str)));
          j = i;
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        return "jsonError";
      }
      return localStringBuilder.toString();
      label172: int i = j;
    }
  }

  @JavascriptInterface
  public boolean openBrowser(String paramString)
  {
    try
    {
      boolean bool2 = isQQBrowserAvailable(this.context);
      bool1 = bool2;
      if (bool1);
      try
      {
        loadUrlWithBrowser(this.context, "com.tencent.mtt", "com.tencent.mtt.MainActivity", paramString);
        break label161;
        loadUrlWithBrowser(this.context, "com.android.browser", "com.android.browser.BrowserActivity", paramString);
      }
      catch (Exception localException7)
      {
      }
      if (bool1)
        try
        {
          loadUrlWithBrowser(this.context, "com.android.browser", "com.android.browser.BrowserActivity", paramString);
        }
        catch (Exception localException4)
        {
          try
          {
            loadUrlWithBrowser(this.context, "com.google.android.browser", "com.android.browser.BrowserActivity", paramString);
          }
          catch (Exception localException5)
          {
            try
            {
              loadUrlWithBrowser(this.context, "com.android.chrome", "com.google.android.apps.chrome.Main", paramString);
            }
            catch (Exception localException6)
            {
              return false;
            }
          }
        }
      else
        try
        {
          loadUrlWithBrowser(this.context, "com.google.android.browser", "com.android.browser.BrowserActivity", paramString);
        }
        catch (Exception localException2)
        {
          try
          {
            loadUrlWithBrowser(this.context, "com.android.chrome", "com.google.android.apps.chrome.Main", paramString);
          }
          catch (Exception localException3)
          {
            return false;
          }
        }
    }
    catch (Exception localException1)
    {
      while (true)
        boolean bool1 = false;
    }
    label161: return true;
  }

  @JavascriptInterface
  public void request(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    Log.d("httpUtils", "request : method =" + paramString1 + "url= " + paramString2 + "key =" + paramString3);
    long l = SystemClock.elapsedRealtime();
    try
    {
      Bundle localBundle = (Bundle)TemporaryStorage.get(paramString3);
      String str3;
      if (localBundle.getString("action") == "get_wpastate")
        str3 = localBundle.getString("oauth_consumer_key");
      HttpUtils.Statistic localStatistic;
      for (localObject = new HttpUtils.Statistic(openUrlForWapOnlineState(this.context, str3, paramString2), 0); ; localObject = localStatistic)
      {
        String str1 = TemporaryStorage.getId();
        if (localObject != null)
          TemporaryStorage.set(str1, ((HttpUtils.Statistic)localObject).response);
        JsBridge localJsBridge = this.mBridge;
        String[] arrayOfString = new String[3];
        arrayOfString[0] = str1;
        arrayOfString[1] = paramString5;
        arrayOfString[2] = resultStr;
        localJsBridge.executeMethod(paramString4, arrayOfString);
        return;
        localStatistic = openUrl(this.context, paramString2, paramString1, localBundle);
      }
    }
    catch (SocketTimeoutException localSocketTimeoutException)
    {
      while (true)
      {
        localSocketTimeoutException.printStackTrace();
        ReportManager.getInstance().report(this.context, paramString2, l, 0L, 0L, -8, this.mQQToken.getAppId());
        WnsClientLog.getInstance().e("openSDK_LOG", "OpenApi requestAsync onSocketTimeoutException", localSocketTimeoutException);
        resultStr = "socket_timeout";
        localObject = null;
      }
    }
    catch (HttpStatusException localHttpStatusException)
    {
      while (true)
      {
        localHttpStatusException.printStackTrace();
        String str2 = localHttpStatusException.getMessage();
        try
        {
          int k = Integer.parseInt(str2.replace("http status code error:", ""));
          j = k;
          ReportManager.getInstance().report(this.context, paramString2, l, 0L, 0L, j, this.mQQToken.getAppId());
          WnsClientLog.getInstance().e("openSDK_LOG", "OpenApi requestAsync onHttpStatusException", localHttpStatusException);
          resultStr = "http_status";
          localObject = null;
        }
        catch (Exception localException2)
        {
          while (true)
          {
            localException2.printStackTrace();
            int j = -9;
            resultStr = "http_status";
          }
        }
      }
    }
    catch (NetworkUnavailableException localNetworkUnavailableException)
    {
      while (true)
      {
        localNetworkUnavailableException.printStackTrace();
        WnsClientLog.getInstance().e("openSDK_LOG", "OpenApi requestAsync onNetworkUnavailableException", localNetworkUnavailableException);
        resultStr = "network_unavailabe";
        localObject = null;
      }
    }
    catch (MalformedURLException localMalformedURLException)
    {
      while (true)
      {
        localMalformedURLException.printStackTrace();
        WnsClientLog.getInstance().e("openSDK_LOG", "OpenApi requestAsync MalformedURLException", localMalformedURLException);
        ReportManager.getInstance().report(this.context, paramString2, l, 0L, 0L, -3, this.mQQToken.getAppId());
        resultStr = "malformed_url";
        localObject = null;
      }
    }
    catch (IOException localIOException)
    {
      while (true)
      {
        localIOException.printStackTrace();
        int i = getErrorCodeFromException(localIOException);
        ReportManager.getInstance().report(this.context, paramString2, l, 0L, 0L, i, this.mQQToken.getAppId());
        WnsClientLog.getInstance().e("openSDK_LOG", "OpenApi requestAsync IOException", localIOException);
        resultStr = "io";
        localObject = null;
      }
    }
    catch (Exception localException1)
    {
      while (true)
      {
        WnsClientLog.getInstance().e("openSDK_LOG", "OpenApi requestAsync onUnknowException", localException1);
        resultStr = "unknow";
        Object localObject = null;
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.HttpUtils
 * JD-Core Version:    0.6.0
 */