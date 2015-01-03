package com.kaixin001.pengpeng.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.pengpeng.data.KXDataManager;
import com.kaixin001.util.KXLog;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy.Type;
import java.net.URL;

public class HttpAdapter
{
  private static byte[] BUFFER = new byte[2048];
  private static final String TAG = "HttpAdapter";

  public static String doRequest(Context paramContext, HttpRequest paramHttpRequest)
    throws HttpException
  {
    KXDataManager localKXDataManager = KXDataManager.getInstance();
    String str1 = localKXDataManager.getStringRecord("method");
    String str2 = localKXDataManager.buildRequestUrl();
    paramHttpRequest.setRequestTag(str1);
    paramHttpRequest.setUrl(str2);
    paramHttpRequest.setStartTime(System.currentTimeMillis());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(paramHttpRequest.getUrl(), null, null);
      return str3;
    }
    catch (Exception localException)
    {
      KXLog.e("HttpAdapter", "doRequest error", localException);
    }
    throw new HttpException("An exception occurred when doing http request: " + localException.getCause());
  }

  // ERROR //
  public static String doRequestBak(Context paramContext, HttpRequest paramHttpRequest)
    throws HttpException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aconst_null
    //   3: astore_3
    //   4: invokestatic 29	com/kaixin001/pengpeng/data/KXDataManager:getInstance	()Lcom/kaixin001/pengpeng/data/KXDataManager;
    //   7: astore 7
    //   9: aload 7
    //   11: ldc 31
    //   13: invokevirtual 35	com/kaixin001/pengpeng/data/KXDataManager:getStringRecord	(Ljava/lang/String;)Ljava/lang/String;
    //   16: astore 8
    //   18: aload 7
    //   20: invokevirtual 39	com/kaixin001/pengpeng/data/KXDataManager:buildRequestUrl	()Ljava/lang/String;
    //   23: astore 9
    //   25: aload_1
    //   26: aload 8
    //   28: invokevirtual 45	com/kaixin001/pengpeng/http/HttpRequest:setRequestTag	(Ljava/lang/String;)V
    //   31: aload_1
    //   32: aload 9
    //   34: invokevirtual 48	com/kaixin001/pengpeng/http/HttpRequest:setUrl	(Ljava/lang/String;)V
    //   37: ldc 10
    //   39: new 80	java/lang/StringBuilder
    //   42: dup
    //   43: ldc 101
    //   45: invokespecial 84	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   48: aload_1
    //   49: invokevirtual 66	com/kaixin001/pengpeng/http/HttpRequest:getUrl	()Ljava/lang/String;
    //   52: invokevirtual 104	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   55: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   58: invokestatic 108	com/kaixin001/util/KXLog:w	(Ljava/lang/String;Ljava/lang/String;)V
    //   61: aload_0
    //   62: new 110	java/net/URL
    //   65: dup
    //   66: aload_1
    //   67: invokevirtual 66	com/kaixin001/pengpeng/http/HttpRequest:getUrl	()Ljava/lang/String;
    //   70: invokespecial 111	java/net/URL:<init>	(Ljava/lang/String;)V
    //   73: invokestatic 115	com/kaixin001/pengpeng/http/HttpAdapter:getConnection	(Landroid/content/Context;Ljava/net/URL;)Ljava/net/HttpURLConnection;
    //   76: astore 10
    //   78: aload 10
    //   80: ldc 117
    //   82: ldc 119
    //   84: invokevirtual 124	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   87: aload 10
    //   89: aload_1
    //   90: invokevirtual 128	com/kaixin001/pengpeng/http/HttpRequest:getTimeout	()I
    //   93: invokevirtual 132	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   96: aload 10
    //   98: aload_1
    //   99: invokevirtual 128	com/kaixin001/pengpeng/http/HttpRequest:getTimeout	()I
    //   102: invokevirtual 135	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   105: aload 10
    //   107: aload_1
    //   108: invokevirtual 139	com/kaixin001/pengpeng/http/HttpRequest:isAllowCaching	()Z
    //   111: invokevirtual 143	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   114: aload 10
    //   116: iconst_1
    //   117: invokevirtual 146	java/net/HttpURLConnection:setDefaultUseCaches	(Z)V
    //   120: aload 10
    //   122: iconst_1
    //   123: invokevirtual 149	java/net/HttpURLConnection:setDoInput	(Z)V
    //   126: aload_1
    //   127: invokestatic 54	java/lang/System:currentTimeMillis	()J
    //   130: l2d
    //   131: invokevirtual 58	com/kaixin001/pengpeng/http/HttpRequest:setStartTime	(D)V
    //   134: aload 10
    //   136: invokevirtual 152	java/net/HttpURLConnection:connect	()V
    //   139: aload 10
    //   141: invokevirtual 156	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   144: astore_2
    //   145: new 158	java/io/ByteArrayOutputStream
    //   148: dup
    //   149: invokespecial 159	java/io/ByteArrayOutputStream:<init>	()V
    //   152: astore 11
    //   154: aload_2
    //   155: getstatic 14	com/kaixin001/pengpeng/http/HttpAdapter:BUFFER	[B
    //   158: invokevirtual 165	java/io/InputStream:read	([B)I
    //   161: istore 12
    //   163: iload 12
    //   165: iconst_m1
    //   166: if_icmpne +51 -> 217
    //   169: aload 11
    //   171: invokevirtual 169	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   174: astore 13
    //   176: ldc 171
    //   178: astore 14
    //   180: aload 13
    //   182: ifnull +14 -> 196
    //   185: new 173	java/lang/String
    //   188: dup
    //   189: aload 13
    //   191: invokespecial 176	java/lang/String:<init>	([B)V
    //   194: astore 14
    //   196: aload_2
    //   197: ifnull +7 -> 204
    //   200: aload_2
    //   201: invokevirtual 179	java/io/InputStream:close	()V
    //   204: aload 11
    //   206: ifnull +8 -> 214
    //   209: aload 11
    //   211: invokevirtual 180	java/io/ByteArrayOutputStream:close	()V
    //   214: aload 14
    //   216: areturn
    //   217: aload 11
    //   219: getstatic 14	com/kaixin001/pengpeng/http/HttpAdapter:BUFFER	[B
    //   222: iconst_0
    //   223: iload 12
    //   225: invokevirtual 184	java/io/ByteArrayOutputStream:write	([BII)V
    //   228: goto -74 -> 154
    //   231: astore 4
    //   233: aload 11
    //   235: astore_3
    //   236: aload 4
    //   238: invokevirtual 187	java/lang/Exception:printStackTrace	()V
    //   241: new 21	com/kaixin001/pengpeng/http/HttpException
    //   244: dup
    //   245: new 80	java/lang/StringBuilder
    //   248: dup
    //   249: ldc 82
    //   251: invokespecial 84	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   254: aload 4
    //   256: invokevirtual 88	java/lang/Exception:getCause	()Ljava/lang/Throwable;
    //   259: invokevirtual 92	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   262: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   265: invokespecial 96	com/kaixin001/pengpeng/http/HttpException:<init>	(Ljava/lang/String;)V
    //   268: athrow
    //   269: astore 5
    //   271: aload_2
    //   272: ifnull +7 -> 279
    //   275: aload_2
    //   276: invokevirtual 179	java/io/InputStream:close	()V
    //   279: aload_3
    //   280: ifnull +7 -> 287
    //   283: aload_3
    //   284: invokevirtual 180	java/io/ByteArrayOutputStream:close	()V
    //   287: aload 5
    //   289: athrow
    //   290: astore 15
    //   292: aload 15
    //   294: invokevirtual 188	java/io/IOException:printStackTrace	()V
    //   297: new 21	com/kaixin001/pengpeng/http/HttpException
    //   300: dup
    //   301: new 80	java/lang/StringBuilder
    //   304: dup
    //   305: ldc 82
    //   307: invokespecial 84	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   310: aload 15
    //   312: invokevirtual 189	java/io/IOException:getCause	()Ljava/lang/Throwable;
    //   315: invokevirtual 92	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   318: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   321: invokespecial 96	com/kaixin001/pengpeng/http/HttpException:<init>	(Ljava/lang/String;)V
    //   324: athrow
    //   325: astore 6
    //   327: aload 6
    //   329: invokevirtual 188	java/io/IOException:printStackTrace	()V
    //   332: new 21	com/kaixin001/pengpeng/http/HttpException
    //   335: dup
    //   336: new 80	java/lang/StringBuilder
    //   339: dup
    //   340: ldc 82
    //   342: invokespecial 84	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   345: aload 6
    //   347: invokevirtual 189	java/io/IOException:getCause	()Ljava/lang/Throwable;
    //   350: invokevirtual 92	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   353: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   356: invokespecial 96	com/kaixin001/pengpeng/http/HttpException:<init>	(Ljava/lang/String;)V
    //   359: athrow
    //   360: astore 5
    //   362: aload 11
    //   364: astore_3
    //   365: goto -94 -> 271
    //   368: astore 4
    //   370: aconst_null
    //   371: astore_3
    //   372: goto -136 -> 236
    //
    // Exception table:
    //   from	to	target	type
    //   154	163	231	java/lang/Exception
    //   169	176	231	java/lang/Exception
    //   185	196	231	java/lang/Exception
    //   217	228	231	java/lang/Exception
    //   4	154	269	finally
    //   236	269	269	finally
    //   200	204	290	java/io/IOException
    //   209	214	290	java/io/IOException
    //   275	279	325	java/io/IOException
    //   283	287	325	java/io/IOException
    //   154	163	360	finally
    //   169	176	360	finally
    //   185	196	360	finally
    //   217	228	360	finally
    //   4	154	368	java/lang/Exception
  }

  public static HttpURLConnection getConnection(Context paramContext, URL paramURL)
    throws IOException
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    if ((localNetworkInfo != null) && (1 == localNetworkInfo.getType()))
      return (HttpURLConnection)paramURL.openConnection();
    if (android.net.Proxy.getDefaultHost() != null)
      return (HttpURLConnection)paramURL.openConnection(new java.net.Proxy(Proxy.Type.HTTP, new InetSocketAddress(android.net.Proxy.getDefaultHost(), android.net.Proxy.getDefaultPort())));
    return (HttpURLConnection)paramURL.openConnection();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.pengpeng.http.HttpAdapter
 * JD-Core Version:    0.6.0
 */