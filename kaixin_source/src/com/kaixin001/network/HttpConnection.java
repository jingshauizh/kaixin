package com.kaixin001.network;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import com.kaixin001.activity.KXApplication;
import com.kaixin001.mime.KxMultipartEntity;
import com.kaixin001.mime.content.ContentBody;
import com.kaixin001.mime.content.FileBody;
import com.kaixin001.mime.content.InputStreamBody;
import com.kaixin001.mime.content.StringBody;
import com.kaixin001.util.KXLog;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

public class HttpConnection
{
  private static final int BUFFER_SIZE = 1024;
  private static final String LOGTAG = "HttpConnection";
  private KXApplication app;
  private HttpClient client;
  private boolean isCancelDownLoad;
  private HttpUriRequest request;

  public HttpConnection(Context paramContext)
  {
    this.app = ((KXApplication)paramContext.getApplicationContext());
    this.client = this.app.getHttpClient();
  }

  public static boolean checkNetworkAndHint(boolean paramBoolean, Activity paramActivity)
  {
    int i = 2131427387;
    int j = 1;
    try
    {
      int k = checkNetworkAvailable(paramActivity);
      if (k < 0)
      {
        i = 2131427387;
        j = 0;
      }
      while (true)
      {
        if ((j == 0) && (paramBoolean))
          Toast.makeText(paramActivity, i, 0).show();
        return j;
        if (k != 0)
          continue;
        i = 2131427388;
        j = 0;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        i = 2131427387;
        j = 0;
      }
    }
  }

  public static int checkNetworkAvailable(Context paramContext)
  {
    if (paramContext == null);
    NetworkInfo localNetworkInfo;
    do
    {
      ConnectivityManager localConnectivityManager;
      do
      {
        return -1;
        localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
      }
      while (localConnectivityManager == null);
      localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
    }
    while ((localNetworkInfo == null) || (!localNetworkInfo.isAvailable()));
    return 1;
  }

  private HttpGet getHttpGet(String paramString, Map<String, Object> paramMap, Header[] paramArrayOfHeader)
    throws HttpException
  {
    StringBuffer localStringBuffer = new StringBuffer(paramString);
    if (localStringBuffer.indexOf("?") == -1)
      localStringBuffer.append("?");
    Iterator localIterator;
    if (paramMap != null)
      localIterator = paramMap.keySet().iterator();
    while (true)
    {
      HttpGet localHttpGet;
      if (!localIterator.hasNext())
      {
        KXLog.d("HttpConnection", "---------------------->GET REQUEST URL:" + localStringBuffer.toString());
        localHttpGet = new HttpGet();
        if (paramArrayOfHeader != null)
          localHttpGet.setHeaders(paramArrayOfHeader);
      }
      try
      {
        localHttpGet.setURI(new URI(localStringBuffer.toString()));
        return localHttpGet;
        String str = (String)localIterator.next();
        if (paramMap.get(str) == null)
          continue;
        if (!localStringBuffer.toString().endsWith("?"))
          localStringBuffer.append("&");
        localStringBuffer.append(str).append("=").append(URLEncoder.encode(String.valueOf(paramMap.get(str))));
      }
      catch (URISyntaxException localURISyntaxException)
      {
    	  throw new HttpException("URISyntaxException", localURISyntaxException);
      }
    }
    
  }

  private HttpPost getHttpPost(String paramString, Map<String, Object> paramMap, Header[] paramArrayOfHeader, HttpProgressListener paramHttpProgressListener)
    throws HttpException
  {
    StringBuffer localStringBuffer = new StringBuffer(paramString);
    KxMultipartEntity localKxMultipartEntity = new KxMultipartEntity(paramHttpProgressListener);
    Iterator localIterator;
    if (paramMap != null)
      localIterator = paramMap.keySet().iterator();
    String str;
    do
    {
      if (!localIterator.hasNext())
      {
        KXLog.d("HttpConnection", "---------------------->POST REQUEST URL:" + localStringBuffer.toString());
        HttpPost localHttpPost = new HttpPost(localStringBuffer.toString());
        if (paramArrayOfHeader != null)
          localHttpPost.setHeaders(paramArrayOfHeader);
        localHttpPost.setEntity(localKxMultipartEntity);
        return localHttpPost;
      }
      str = (String)localIterator.next();
    }
    while (paramMap.get(str) == null);
    Object localObject;
    if ((paramMap.get(str) instanceof InputStreamBody))
      localObject = (InputStreamBody)paramMap.get(str);
    while (true)
    {
      localKxMultipartEntity.addPart(str, (ContentBody)localObject);
      KXLog.d("HttpConnection", "---------------------->key:[" + str + "] value:[" + paramMap.get(str) + "]");
      break;
      if ((paramMap.get(str) instanceof File))
      {
        localObject = new FileBody((File)paramMap.get(str), "image/jpeg");
        continue;
      }
      try
      {
        localObject = new StringBody(String.valueOf(paramMap.get(str)), Charset.forName("UTF-8"));
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
      }
    }
    throw new HttpException("UnsupportedEncodingException", localUnsupportedEncodingException);
  }

  private HttpUriRequest getRequest(String paramString, HttpMethod paramHttpMethod, Map<String, Object> paramMap, Header[] paramArrayOfHeader, HttpProgressListener paramHttpProgressListener)
    throws HttpException
  {
    HttpPost localHttpPost = null;
    if (paramString != null)
    {
      if (paramHttpMethod == HttpMethod.POST)
        localHttpPost = getHttpPost(paramString, paramMap, paramArrayOfHeader, paramHttpProgressListener);
    }
    else
      return localHttpPost;
    return getHttpGet(paramString, paramMap, paramArrayOfHeader);
  }

  public static String inputStreamToString(InputStream paramInputStream)
    throws Exception
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    byte[] arrayOfByte = new byte[1024];
    while (true)
    {
      int i = paramInputStream.read(arrayOfByte, 0, 1024);
      if (i == -1)
        return new String(localByteArrayOutputStream.toByteArray());
      localByteArrayOutputStream.write(arrayOfByte, 0, i);
    }
  }

  public void abortRequest()
  {
    if (this.request != null)
      this.request.abort();
  }

  public void cancelDownLoad()
  {
    this.isCancelDownLoad = true;
  }

  // ERROR //
  public long getRemoteFileSize(String paramString)
    throws HttpException
  {
    // Byte code:
    //   0: new 136	org/apache/http/client/methods/HttpGet
    //   3: dup
    //   4: invokespecial 137	org/apache/http/client/methods/HttpGet:<init>	()V
    //   7: astore_2
    //   8: aload_2
    //   9: new 143	java/net/URI
    //   12: dup
    //   13: aload_1
    //   14: invokespecial 144	java/net/URI:<init>	(Ljava/lang/String;)V
    //   17: invokevirtual 148	org/apache/http/client/methods/HttpGet:setURI	(Ljava/net/URI;)V
    //   20: aload_0
    //   21: getfield 40	com/kaixin001/network/HttpConnection:client	Lorg/apache/http/client/HttpClient;
    //   24: aload_2
    //   25: invokeinterface 296 2 0
    //   30: astore 6
    //   32: aload 6
    //   34: invokeinterface 302 1 0
    //   39: invokeinterface 308 1 0
    //   44: lreturn
    //   45: astore_3
    //   46: new 81	com/kaixin001/network/HttpException
    //   49: dup
    //   50: ldc 178
    //   52: aload_3
    //   53: invokespecial 181	com/kaixin001/network/HttpException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   56: athrow
    //   57: astore 5
    //   59: new 81	com/kaixin001/network/HttpException
    //   62: dup
    //   63: ldc_w 310
    //   66: aload 5
    //   68: invokespecial 181	com/kaixin001/network/HttpException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   71: athrow
    //   72: astore 4
    //   74: new 81	com/kaixin001/network/HttpException
    //   77: dup
    //   78: ldc_w 312
    //   81: aload 4
    //   83: invokespecial 181	com/kaixin001/network/HttpException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   86: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   8	20	45	java/net/URISyntaxException
    //   20	32	57	org/apache/http/client/ClientProtocolException
    //   20	32	72	java/io/IOException
  }

  // ERROR //
  public boolean httpDownload(String paramString1, String paramString2, boolean paramBoolean, HttpRequestState paramHttpRequestState, HttpProgressListener paramHttpProgressListener)
    throws HttpException
  {
    // Byte code:
    //   0: new 136	org/apache/http/client/methods/HttpGet
    //   3: dup
    //   4: invokespecial 137	org/apache/http/client/methods/HttpGet:<init>	()V
    //   7: astore 6
    //   9: new 143	java/net/URI
    //   12: dup
    //   13: aload_1
    //   14: invokespecial 144	java/net/URI:<init>	(Ljava/lang/String;)V
    //   17: astore 7
    //   19: aload 6
    //   21: aload 7
    //   23: invokevirtual 148	org/apache/http/client/methods/HttpGet:setURI	(Ljava/net/URI;)V
    //   26: lconst_0
    //   27: lstore 10
    //   29: iload_3
    //   30: ifeq +71 -> 101
    //   33: aload 6
    //   35: ldc_w 322
    //   38: invokevirtual 326	org/apache/http/client/methods/HttpGet:getHeaders	(Ljava/lang/String;)[Lorg/apache/http/Header;
    //   41: ifnull +60 -> 101
    //   44: new 217	java/io/File
    //   47: dup
    //   48: aload_2
    //   49: invokespecial 327	java/io/File:<init>	(Ljava/lang/String;)V
    //   52: astore 81
    //   54: aload 81
    //   56: invokevirtual 330	java/io/File:exists	()Z
    //   59: ifeq +42 -> 101
    //   62: aload 81
    //   64: invokevirtual 333	java/io/File:length	()J
    //   67: lstore 10
    //   69: aload 6
    //   71: ldc_w 322
    //   74: new 117	java/lang/StringBuilder
    //   77: dup
    //   78: ldc_w 335
    //   81: invokespecial 120	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   84: lload 10
    //   86: invokevirtual 338	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   89: ldc_w 340
    //   92: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: invokevirtual 128	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   98: invokevirtual 343	org/apache/http/client/methods/HttpGet:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
    //   101: aload_0
    //   102: getfield 40	com/kaixin001/network/HttpConnection:client	Lorg/apache/http/client/HttpClient;
    //   105: aload 6
    //   107: invokeinterface 296 2 0
    //   112: astore 16
    //   114: aload 16
    //   116: invokeinterface 347 1 0
    //   121: invokeinterface 353 1 0
    //   126: istore 17
    //   128: aload 4
    //   130: ifnull +10 -> 140
    //   133: aload 4
    //   135: iload 17
    //   137: invokevirtual 359	com/kaixin001/network/HttpRequestState:setStatusCode	(I)V
    //   140: iload 17
    //   142: sipush 200
    //   145: if_icmpeq +11 -> 156
    //   148: iload 17
    //   150: sipush 206
    //   153: if_icmpne +684 -> 837
    //   156: aload 16
    //   158: invokeinterface 302 1 0
    //   163: invokeinterface 308 1 0
    //   168: lstore 18
    //   170: aload 16
    //   172: invokeinterface 302 1 0
    //   177: invokeinterface 363 1 0
    //   182: astore 24
    //   184: aconst_null
    //   185: astore 25
    //   187: aconst_null
    //   188: astore 26
    //   190: new 365	java/io/BufferedInputStream
    //   193: dup
    //   194: aload 24
    //   196: invokespecial 368	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   199: astore 27
    //   201: new 370	java/io/FileOutputStream
    //   204: dup
    //   205: aload_2
    //   206: invokespecial 371	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   209: astore 28
    //   211: new 373	java/io/BufferedOutputStream
    //   214: dup
    //   215: aload 28
    //   217: invokespecial 376	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   220: astore 29
    //   222: sipush 1024
    //   225: newarray byte
    //   227: astore 50
    //   229: lconst_0
    //   230: lstore 51
    //   232: lload 10
    //   234: lstore 53
    //   236: aload 27
    //   238: aload 50
    //   240: invokevirtual 379	java/io/BufferedInputStream:read	([B)I
    //   243: istore 55
    //   245: iload 55
    //   247: iconst_m1
    //   248: if_icmpne +139 -> 387
    //   251: aload 29
    //   253: invokevirtual 384	java/io/OutputStream:flush	()V
    //   256: iconst_1
    //   257: istore 57
    //   259: aload 29
    //   261: ifnull +8 -> 269
    //   264: aload 29
    //   266: invokevirtual 387	java/io/OutputStream:close	()V
    //   269: aload 27
    //   271: ifnull +8 -> 279
    //   274: aload 27
    //   276: invokevirtual 388	java/io/BufferedInputStream:close	()V
    //   279: aload 24
    //   281: ifnull +8 -> 289
    //   284: aload 24
    //   286: invokevirtual 389	java/io/InputStream:close	()V
    //   289: iload 57
    //   291: ireturn
    //   292: astore 8
    //   294: new 81	com/kaixin001/network/HttpException
    //   297: dup
    //   298: ldc_w 391
    //   301: aload 8
    //   303: invokespecial 181	com/kaixin001/network/HttpException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   306: astore 9
    //   308: aload 9
    //   310: athrow
    //   311: astore 14
    //   313: new 81	com/kaixin001/network/HttpException
    //   316: dup
    //   317: ldc_w 310
    //   320: aload 14
    //   322: invokespecial 181	com/kaixin001/network/HttpException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   325: astore 15
    //   327: aload 15
    //   329: athrow
    //   330: astore 12
    //   332: new 81	com/kaixin001/network/HttpException
    //   335: dup
    //   336: ldc_w 393
    //   339: aload 12
    //   341: invokespecial 181	com/kaixin001/network/HttpException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   344: astore 13
    //   346: aload 13
    //   348: athrow
    //   349: astore 22
    //   351: new 81	com/kaixin001/network/HttpException
    //   354: dup
    //   355: ldc_w 395
    //   358: aload 22
    //   360: invokespecial 181	com/kaixin001/network/HttpException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   363: astore 23
    //   365: aload 23
    //   367: athrow
    //   368: astore 20
    //   370: new 81	com/kaixin001/network/HttpException
    //   373: dup
    //   374: ldc_w 312
    //   377: aload 20
    //   379: invokespecial 181	com/kaixin001/network/HttpException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   382: astore 21
    //   384: aload 21
    //   386: athrow
    //   387: aload 29
    //   389: aload 50
    //   391: iconst_0
    //   392: iload 55
    //   394: invokevirtual 396	java/io/OutputStream:write	([BII)V
    //   397: lload 53
    //   399: iload 55
    //   401: i2l
    //   402: ladd
    //   403: lstore 53
    //   405: aload 5
    //   407: ifnull +42 -> 449
    //   410: ldc2_w 397
    //   413: lload 53
    //   415: lmul
    //   416: lload 18
    //   418: ldiv
    //   419: lload 51
    //   421: lsub
    //   422: lconst_1
    //   423: lcmp
    //   424: iflt +25 -> 449
    //   427: ldc2_w 397
    //   430: lload 53
    //   432: lmul
    //   433: lload 18
    //   435: ldiv
    //   436: lstore 51
    //   438: aload 5
    //   440: lload 53
    //   442: lload 18
    //   444: invokeinterface 404 5 0
    //   449: aload_0
    //   450: getfield 284	com/kaixin001/network/HttpConnection:isCancelDownLoad	Z
    //   453: istore 56
    //   455: iload 56
    //   457: ifeq -221 -> 236
    //   460: goto -209 -> 251
    //   463: astore 30
    //   465: new 81	com/kaixin001/network/HttpException
    //   468: dup
    //   469: ldc_w 312
    //   472: aload 30
    //   474: invokespecial 181	com/kaixin001/network/HttpException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   477: astore 31
    //   479: aload 31
    //   481: athrow
    //   482: astore 32
    //   484: aload 26
    //   486: ifnull +8 -> 494
    //   489: aload 26
    //   491: invokevirtual 387	java/io/OutputStream:close	()V
    //   494: aload 25
    //   496: ifnull +8 -> 504
    //   499: aload 25
    //   501: invokevirtual 388	java/io/BufferedInputStream:close	()V
    //   504: aload 24
    //   506: ifnull +8 -> 514
    //   509: aload 24
    //   511: invokevirtual 389	java/io/InputStream:close	()V
    //   514: aload 32
    //   516: athrow
    //   517: astore 44
    //   519: aload 25
    //   521: ifnull +8 -> 529
    //   524: aload 25
    //   526: invokevirtual 388	java/io/BufferedInputStream:close	()V
    //   529: aload 24
    //   531: ifnull -17 -> 514
    //   534: aload 24
    //   536: invokevirtual 389	java/io/InputStream:close	()V
    //   539: goto -25 -> 514
    //   542: astore 45
    //   544: goto -30 -> 514
    //   547: astore 48
    //   549: aload 24
    //   551: ifnull -37 -> 514
    //   554: aload 24
    //   556: invokevirtual 389	java/io/InputStream:close	()V
    //   559: goto -45 -> 514
    //   562: astore 49
    //   564: goto -50 -> 514
    //   567: astore 46
    //   569: aload 24
    //   571: ifnull +8 -> 579
    //   574: aload 24
    //   576: invokevirtual 389	java/io/InputStream:close	()V
    //   579: aload 46
    //   581: athrow
    //   582: astore 38
    //   584: aload 25
    //   586: ifnull +8 -> 594
    //   589: aload 25
    //   591: invokevirtual 388	java/io/BufferedInputStream:close	()V
    //   594: aload 24
    //   596: ifnull +8 -> 604
    //   599: aload 24
    //   601: invokevirtual 389	java/io/InputStream:close	()V
    //   604: aload 38
    //   606: athrow
    //   607: astore 42
    //   609: aload 24
    //   611: ifnull -7 -> 604
    //   614: aload 24
    //   616: invokevirtual 389	java/io/InputStream:close	()V
    //   619: goto -15 -> 604
    //   622: astore 43
    //   624: goto -20 -> 604
    //   627: astore 40
    //   629: aload 24
    //   631: ifnull +8 -> 639
    //   634: aload 24
    //   636: invokevirtual 389	java/io/InputStream:close	()V
    //   639: aload 40
    //   641: athrow
    //   642: astore 36
    //   644: aload 24
    //   646: ifnull -132 -> 514
    //   649: aload 24
    //   651: invokevirtual 389	java/io/InputStream:close	()V
    //   654: goto -140 -> 514
    //   657: astore 37
    //   659: goto -145 -> 514
    //   662: astore 34
    //   664: aload 24
    //   666: ifnull +8 -> 674
    //   669: aload 24
    //   671: invokevirtual 389	java/io/InputStream:close	()V
    //   674: aload 34
    //   676: athrow
    //   677: astore 69
    //   679: aload 27
    //   681: ifnull +8 -> 689
    //   684: aload 27
    //   686: invokevirtual 388	java/io/BufferedInputStream:close	()V
    //   689: aload 24
    //   691: ifnull -402 -> 289
    //   694: aload 24
    //   696: invokevirtual 389	java/io/InputStream:close	()V
    //   699: iload 57
    //   701: ireturn
    //   702: astore 70
    //   704: iload 57
    //   706: ireturn
    //   707: astore 73
    //   709: aload 24
    //   711: ifnull -422 -> 289
    //   714: aload 24
    //   716: invokevirtual 389	java/io/InputStream:close	()V
    //   719: iload 57
    //   721: ireturn
    //   722: astore 74
    //   724: iload 57
    //   726: ireturn
    //   727: astore 71
    //   729: aload 24
    //   731: ifnull +8 -> 739
    //   734: aload 24
    //   736: invokevirtual 389	java/io/InputStream:close	()V
    //   739: aload 71
    //   741: athrow
    //   742: astore 63
    //   744: aload 27
    //   746: ifnull +8 -> 754
    //   749: aload 27
    //   751: invokevirtual 388	java/io/BufferedInputStream:close	()V
    //   754: aload 24
    //   756: ifnull +8 -> 764
    //   759: aload 24
    //   761: invokevirtual 389	java/io/InputStream:close	()V
    //   764: aload 63
    //   766: athrow
    //   767: astore 67
    //   769: aload 24
    //   771: ifnull -7 -> 764
    //   774: aload 24
    //   776: invokevirtual 389	java/io/InputStream:close	()V
    //   779: goto -15 -> 764
    //   782: astore 68
    //   784: goto -20 -> 764
    //   787: astore 65
    //   789: aload 24
    //   791: ifnull +8 -> 799
    //   794: aload 24
    //   796: invokevirtual 389	java/io/InputStream:close	()V
    //   799: aload 65
    //   801: athrow
    //   802: astore 61
    //   804: aload 24
    //   806: ifnull -517 -> 289
    //   809: aload 24
    //   811: invokevirtual 389	java/io/InputStream:close	()V
    //   814: iload 57
    //   816: ireturn
    //   817: astore 62
    //   819: iload 57
    //   821: ireturn
    //   822: astore 59
    //   824: aload 24
    //   826: ifnull +8 -> 834
    //   829: aload 24
    //   831: invokevirtual 389	java/io/InputStream:close	()V
    //   834: aload 59
    //   836: athrow
    //   837: iconst_0
    //   838: istore 57
    //   840: aload 4
    //   842: ifnull -553 -> 289
    //   845: aload 4
    //   847: aload 16
    //   849: invokeinterface 302 1 0
    //   854: invokestatic 409	org/apache/http/util/EntityUtils:toString	(Lorg/apache/http/HttpEntity;)Ljava/lang/String;
    //   857: invokevirtual 412	com/kaixin001/network/HttpRequestState:parseData	(Ljava/lang/String;)V
    //   860: iconst_0
    //   861: ireturn
    //   862: astore 79
    //   864: new 81	com/kaixin001/network/HttpException
    //   867: dup
    //   868: ldc_w 391
    //   871: aload 79
    //   873: invokespecial 181	com/kaixin001/network/HttpException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   876: astore 80
    //   878: aload 80
    //   880: athrow
    //   881: astore 77
    //   883: new 81	com/kaixin001/network/HttpException
    //   886: dup
    //   887: ldc_w 414
    //   890: aload 77
    //   892: invokespecial 181	com/kaixin001/network/HttpException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   895: astore 78
    //   897: aload 78
    //   899: athrow
    //   900: astore 75
    //   902: new 81	com/kaixin001/network/HttpException
    //   905: dup
    //   906: ldc_w 312
    //   909: aload 75
    //   911: invokespecial 181	com/kaixin001/network/HttpException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   914: astore 76
    //   916: aload 76
    //   918: athrow
    //   919: astore 47
    //   921: goto -342 -> 579
    //   924: astore 41
    //   926: goto -287 -> 639
    //   929: astore 39
    //   931: goto -327 -> 604
    //   934: astore 35
    //   936: goto -262 -> 674
    //   939: astore 33
    //   941: goto -427 -> 514
    //   944: astore 72
    //   946: goto -207 -> 739
    //   949: astore 66
    //   951: goto -152 -> 799
    //   954: astore 64
    //   956: goto -192 -> 764
    //   959: astore 60
    //   961: goto -127 -> 834
    //   964: astore 58
    //   966: iload 57
    //   968: ireturn
    //   969: astore 32
    //   971: aload 27
    //   973: astore 25
    //   975: aconst_null
    //   976: astore 26
    //   978: goto -494 -> 484
    //   981: astore 32
    //   983: aload 29
    //   985: astore 26
    //   987: aload 27
    //   989: astore 25
    //   991: goto -507 -> 484
    //   994: astore 30
    //   996: aload 27
    //   998: astore 25
    //   1000: aconst_null
    //   1001: astore 26
    //   1003: goto -538 -> 465
    //   1006: astore 30
    //   1008: aload 29
    //   1010: astore 26
    //   1012: aload 27
    //   1014: astore 25
    //   1016: goto -551 -> 465
    //
    // Exception table:
    //   from	to	target	type
    //   9	26	292	java/net/URISyntaxException
    //   101	114	311	org/apache/http/client/ClientProtocolException
    //   101	114	330	java/lang/Exception
    //   170	184	349	java/lang/IllegalStateException
    //   170	184	368	java/io/IOException
    //   190	201	463	java/io/IOException
    //   190	201	482	finally
    //   465	482	482	finally
    //   489	494	517	java/io/IOException
    //   534	539	542	java/io/IOException
    //   524	529	547	java/io/IOException
    //   554	559	562	java/io/IOException
    //   524	529	567	finally
    //   489	494	582	finally
    //   589	594	607	java/io/IOException
    //   614	619	622	java/io/IOException
    //   589	594	627	finally
    //   499	504	642	java/io/IOException
    //   649	654	657	java/io/IOException
    //   499	504	662	finally
    //   264	269	677	java/io/IOException
    //   694	699	702	java/io/IOException
    //   684	689	707	java/io/IOException
    //   714	719	722	java/io/IOException
    //   684	689	727	finally
    //   264	269	742	finally
    //   749	754	767	java/io/IOException
    //   774	779	782	java/io/IOException
    //   749	754	787	finally
    //   274	279	802	java/io/IOException
    //   809	814	817	java/io/IOException
    //   274	279	822	finally
    //   845	860	862	org/apache/http/ParseException
    //   845	860	881	org/json/JSONException
    //   845	860	900	java/io/IOException
    //   574	579	919	java/io/IOException
    //   634	639	924	java/io/IOException
    //   599	604	929	java/io/IOException
    //   669	674	934	java/io/IOException
    //   509	514	939	java/io/IOException
    //   734	739	944	java/io/IOException
    //   794	799	949	java/io/IOException
    //   759	764	954	java/io/IOException
    //   829	834	959	java/io/IOException
    //   284	289	964	java/io/IOException
    //   201	222	969	finally
    //   222	229	981	finally
    //   236	245	981	finally
    //   251	256	981	finally
    //   387	397	981	finally
    //   410	449	981	finally
    //   449	455	981	finally
    //   201	222	994	java/io/IOException
    //   222	229	1006	java/io/IOException
    //   236	245	1006	java/io/IOException
    //   251	256	1006	java/io/IOException
    //   387	397	1006	java/io/IOException
    //   410	449	1006	java/io/IOException
    //   449	455	1006	java/io/IOException
  }

  // ERROR //
  public String httpRequest(String paramString, HttpMethod paramHttpMethod, Map<String, Object> paramMap, Header[] paramArrayOfHeader, HttpRequestState paramHttpRequestState, HttpProgressListener paramHttpProgressListener)
    throws HttpException
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_0
    //   2: aload_1
    //   3: aload_2
    //   4: aload_3
    //   5: aload 4
    //   7: aload 6
    //   9: invokespecial 420	com/kaixin001/network/HttpConnection:getRequest	(Ljava/lang/String;Lcom/kaixin001/network/HttpMethod;Ljava/util/Map;[Lorg/apache/http/Header;Lcom/kaixin001/network/HttpProgressListener;)Lorg/apache/http/client/methods/HttpUriRequest;
    //   12: putfield 276	com/kaixin001/network/HttpConnection:request	Lorg/apache/http/client/methods/HttpUriRequest;
    //   15: aload_0
    //   16: getfield 40	com/kaixin001/network/HttpConnection:client	Lorg/apache/http/client/HttpClient;
    //   19: aload_0
    //   20: getfield 276	com/kaixin001/network/HttpConnection:request	Lorg/apache/http/client/methods/HttpUriRequest;
    //   23: invokeinterface 296 2 0
    //   28: astore 10
    //   30: ldc 11
    //   32: new 117	java/lang/StringBuilder
    //   35: dup
    //   36: ldc_w 422
    //   39: invokespecial 120	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   42: aload 10
    //   44: invokeinterface 347 1 0
    //   49: invokevirtual 213	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   52: invokevirtual 128	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   55: invokestatic 134	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   58: aload 10
    //   60: invokeinterface 347 1 0
    //   65: invokeinterface 353 1 0
    //   70: istore 11
    //   72: aload 5
    //   74: ifnull +10 -> 84
    //   77: aload 5
    //   79: iload 11
    //   81: invokevirtual 359	com/kaixin001/network/HttpRequestState:setStatusCode	(I)V
    //   84: ldc 11
    //   86: new 117	java/lang/StringBuilder
    //   89: dup
    //   90: ldc_w 424
    //   93: invokespecial 120	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   96: iload 11
    //   98: invokevirtual 427	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   101: invokevirtual 128	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   104: invokestatic 134	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   107: aload 10
    //   109: invokeinterface 302 1 0
    //   114: invokeinterface 363 1 0
    //   119: invokestatic 429	com/kaixin001/network/HttpConnection:inputStreamToString	(Ljava/io/InputStream;)Ljava/lang/String;
    //   122: astore 16
    //   124: aload 16
    //   126: astore 13
    //   128: ldc 11
    //   130: new 117	java/lang/StringBuilder
    //   133: dup
    //   134: ldc_w 431
    //   137: invokespecial 120	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   140: aload 13
    //   142: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: invokevirtual 128	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   148: invokestatic 134	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   151: aload 13
    //   153: areturn
    //   154: astore 9
    //   156: aload_0
    //   157: getfield 34	com/kaixin001/network/HttpConnection:app	Lcom/kaixin001/activity/KXApplication;
    //   160: invokevirtual 434	com/kaixin001/activity/KXApplication:shutdownHttpClient	()V
    //   163: new 81	com/kaixin001/network/HttpException
    //   166: dup
    //   167: ldc_w 310
    //   170: aload 9
    //   172: invokespecial 181	com/kaixin001/network/HttpException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   175: athrow
    //   176: astore 8
    //   178: aload_0
    //   179: getfield 34	com/kaixin001/network/HttpConnection:app	Lcom/kaixin001/activity/KXApplication;
    //   182: invokevirtual 434	com/kaixin001/activity/KXApplication:shutdownHttpClient	()V
    //   185: new 81	com/kaixin001/network/HttpException
    //   188: dup
    //   189: ldc_w 312
    //   192: aload 8
    //   194: invokespecial 181	com/kaixin001/network/HttpException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   197: athrow
    //   198: astore 7
    //   200: aload_0
    //   201: getfield 34	com/kaixin001/network/HttpConnection:app	Lcom/kaixin001/activity/KXApplication;
    //   204: invokevirtual 434	com/kaixin001/activity/KXApplication:shutdownHttpClient	()V
    //   207: new 81	com/kaixin001/network/HttpException
    //   210: dup
    //   211: ldc_w 393
    //   214: aload 7
    //   216: invokespecial 181	com/kaixin001/network/HttpException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   219: athrow
    //   220: astore 15
    //   222: new 81	com/kaixin001/network/HttpException
    //   225: dup
    //   226: ldc_w 391
    //   229: aload 15
    //   231: invokespecial 181	com/kaixin001/network/HttpException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   234: athrow
    //   235: astore 14
    //   237: new 81	com/kaixin001/network/HttpException
    //   240: dup
    //   241: ldc_w 312
    //   244: aload 14
    //   246: invokespecial 181	com/kaixin001/network/HttpException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   249: athrow
    //   250: astore 12
    //   252: aload 12
    //   254: invokevirtual 437	java/lang/Throwable:printStackTrace	()V
    //   257: aconst_null
    //   258: astore 13
    //   260: goto -132 -> 128
    //
    // Exception table:
    //   from	to	target	type
    //   15	30	154	org/apache/http/client/ClientProtocolException
    //   15	30	176	java/io/IOException
    //   15	30	198	java/lang/Exception
    //   107	124	220	org/apache/http/ParseException
    //   107	124	235	java/io/IOException
    //   107	124	250	java/lang/Throwable
  }

  // ERROR //
  public String httpRequest(HttpGet paramHttpGet)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 40	com/kaixin001/network/HttpConnection:client	Lorg/apache/http/client/HttpClient;
    //   4: aload_1
    //   5: invokeinterface 296 2 0
    //   10: astore 5
    //   12: ldc 11
    //   14: new 117	java/lang/StringBuilder
    //   17: dup
    //   18: ldc_w 422
    //   21: invokespecial 120	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   24: aload 5
    //   26: invokeinterface 347 1 0
    //   31: invokevirtual 213	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   34: invokevirtual 128	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   37: invokestatic 134	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   40: aload 5
    //   42: invokeinterface 347 1 0
    //   47: invokeinterface 353 1 0
    //   52: istore 6
    //   54: ldc 11
    //   56: new 117	java/lang/StringBuilder
    //   59: dup
    //   60: ldc_w 424
    //   63: invokespecial 120	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   66: iload 6
    //   68: invokevirtual 427	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   71: invokevirtual 128	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   74: invokestatic 134	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   77: aload 5
    //   79: invokeinterface 302 1 0
    //   84: invokeinterface 363 1 0
    //   89: invokestatic 429	com/kaixin001/network/HttpConnection:inputStreamToString	(Ljava/io/InputStream;)Ljava/lang/String;
    //   92: astore 11
    //   94: aload 11
    //   96: astore 8
    //   98: ldc 11
    //   100: new 117	java/lang/StringBuilder
    //   103: dup
    //   104: ldc_w 431
    //   107: invokespecial 120	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   110: aload 8
    //   112: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   115: invokevirtual 128	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   118: invokestatic 134	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   121: aload 8
    //   123: areturn
    //   124: astore 10
    //   126: new 81	com/kaixin001/network/HttpException
    //   129: dup
    //   130: ldc_w 391
    //   133: aload 10
    //   135: invokespecial 181	com/kaixin001/network/HttpException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   138: athrow
    //   139: astore 4
    //   141: aload_0
    //   142: getfield 34	com/kaixin001/network/HttpConnection:app	Lcom/kaixin001/activity/KXApplication;
    //   145: invokevirtual 434	com/kaixin001/activity/KXApplication:shutdownHttpClient	()V
    //   148: aconst_null
    //   149: areturn
    //   150: astore 9
    //   152: new 81	com/kaixin001/network/HttpException
    //   155: dup
    //   156: ldc_w 312
    //   159: aload 9
    //   161: invokespecial 181	com/kaixin001/network/HttpException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   164: athrow
    //   165: astore_3
    //   166: aload_0
    //   167: getfield 34	com/kaixin001/network/HttpConnection:app	Lcom/kaixin001/activity/KXApplication;
    //   170: invokevirtual 434	com/kaixin001/activity/KXApplication:shutdownHttpClient	()V
    //   173: goto -25 -> 148
    //   176: astore 7
    //   178: aload 7
    //   180: invokevirtual 437	java/lang/Throwable:printStackTrace	()V
    //   183: aconst_null
    //   184: astore 8
    //   186: goto -88 -> 98
    //   189: astore_2
    //   190: aload_0
    //   191: getfield 34	com/kaixin001/network/HttpConnection:app	Lcom/kaixin001/activity/KXApplication;
    //   194: invokevirtual 434	com/kaixin001/activity/KXApplication:shutdownHttpClient	()V
    //   197: goto -49 -> 148
    //
    // Exception table:
    //   from	to	target	type
    //   77	94	124	org/apache/http/ParseException
    //   0	77	139	org/apache/http/client/ClientProtocolException
    //   77	94	139	org/apache/http/client/ClientProtocolException
    //   98	121	139	org/apache/http/client/ClientProtocolException
    //   126	139	139	org/apache/http/client/ClientProtocolException
    //   152	165	139	org/apache/http/client/ClientProtocolException
    //   178	183	139	org/apache/http/client/ClientProtocolException
    //   77	94	150	java/io/IOException
    //   0	77	165	java/io/IOException
    //   98	121	165	java/io/IOException
    //   126	139	165	java/io/IOException
    //   152	165	165	java/io/IOException
    //   178	183	165	java/io/IOException
    //   77	94	176	java/lang/Throwable
    //   0	77	189	java/lang/Exception
    //   77	94	189	java/lang/Exception
    //   98	121	189	java/lang/Exception
    //   126	139	189	java/lang/Exception
    //   152	165	189	java/lang/Exception
    //   178	183	189	java/lang/Exception
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.network.HttpConnection
 * JD-Core Version:    0.6.0
 */