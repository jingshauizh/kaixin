package com.autonavi.aps.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Build;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class NetManagerApache
{
  private static NetManagerApache b = null;
  private Context a;

  public static NetManagerApache getInstance(Context paramContext)
  {
    if (b == null)
    {
      NetManagerApache localNetManagerApache = new NetManagerApache();
      b = localNetManagerApache;
      localNetManagerApache.a = paramContext.getApplicationContext();
    }
    return b;
  }

  public static String intToIpAddr(int paramInt)
  {
    return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "." + (0xFF & paramInt >> 16) + "." + (paramInt >>> 24);
  }

  // ERROR //
  public String doPostXmlAsString(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: new 57	java/lang/StringBuffer
    //   3: dup
    //   4: invokespecial 58	java/lang/StringBuffer:<init>	()V
    //   7: astore_3
    //   8: aconst_null
    //   9: astore 4
    //   11: aload_0
    //   12: invokevirtual 62	com/autonavi/aps/api/NetManagerApache:getHttpClient	()Lorg/apache/http/client/HttpClient;
    //   15: astore 10
    //   17: aload 10
    //   19: astore 9
    //   21: new 64	org/apache/http/client/methods/HttpPost
    //   24: dup
    //   25: aload_1
    //   26: invokespecial 65	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
    //   29: astore 11
    //   31: new 67	org/apache/http/entity/StringEntity
    //   34: dup
    //   35: aload_2
    //   36: ldc 69
    //   38: invokespecial 72	org/apache/http/entity/StringEntity:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   41: astore 12
    //   43: aload 12
    //   45: ldc 74
    //   47: invokevirtual 77	org/apache/http/entity/StringEntity:setContentType	(Ljava/lang/String;)V
    //   50: aload 11
    //   52: ldc 79
    //   54: ldc 81
    //   56: invokevirtual 84	org/apache/http/client/methods/HttpPost:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
    //   59: aload 11
    //   61: aload 12
    //   63: invokevirtual 88	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
    //   66: aload 9
    //   68: aload 11
    //   70: invokeinterface 94 2 0
    //   75: astore 13
    //   77: ldc 96
    //   79: astore 14
    //   81: aload 13
    //   83: ldc 79
    //   85: invokeinterface 102 2 0
    //   90: astore 15
    //   92: aconst_null
    //   93: astore 4
    //   95: aload 15
    //   97: ifnull +14 -> 111
    //   100: aload 15
    //   102: arraylength
    //   103: istore 16
    //   105: iconst_0
    //   106: istore 17
    //   108: goto +547 -> 655
    //   111: aload 14
    //   113: astore 18
    //   115: aload 13
    //   117: ldc 104
    //   119: invokeinterface 102 2 0
    //   124: astore 19
    //   126: aconst_null
    //   127: astore 20
    //   129: aconst_null
    //   130: astore 4
    //   132: aload 19
    //   134: ifnull +17 -> 151
    //   137: aload 19
    //   139: arraylength
    //   140: istore 21
    //   142: aconst_null
    //   143: astore 20
    //   145: iconst_0
    //   146: istore 22
    //   148: goto +517 -> 665
    //   151: aload 13
    //   153: invokeinterface 108 1 0
    //   158: invokeinterface 114 1 0
    //   163: istore 23
    //   165: aconst_null
    //   166: astore 4
    //   168: iload 23
    //   170: sipush 200
    //   173: if_icmpne +473 -> 646
    //   176: aload 13
    //   178: invokeinterface 118 1 0
    //   183: invokeinterface 124 1 0
    //   188: astore 4
    //   190: aload 20
    //   192: ifnull +447 -> 639
    //   195: aload 20
    //   197: ldc 126
    //   199: invokevirtual 130	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   202: ifeq +437 -> 639
    //   205: new 132	java/util/zip/GZIPInputStream
    //   208: dup
    //   209: aload 4
    //   211: invokespecial 135	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   214: astore 24
    //   216: new 137	java/io/BufferedReader
    //   219: dup
    //   220: new 139	java/io/InputStreamReader
    //   223: dup
    //   224: aload 24
    //   226: aload 18
    //   228: invokespecial 142	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
    //   231: invokespecial 145	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   234: astore 25
    //   236: aload 25
    //   238: invokevirtual 148	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   241: astore 26
    //   243: aload 26
    //   245: ifnonnull +227 -> 472
    //   248: aload 25
    //   250: ifnull +8 -> 258
    //   253: aload 25
    //   255: invokevirtual 151	java/io/BufferedReader:close	()V
    //   258: aload 24
    //   260: ifnull +8 -> 268
    //   263: aload 24
    //   265: invokevirtual 154	java/io/InputStream:close	()V
    //   268: aload 9
    //   270: ifnull +15 -> 285
    //   273: aload 9
    //   275: invokeinterface 158 1 0
    //   280: invokeinterface 163 1 0
    //   285: aload_3
    //   286: invokevirtual 164	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   289: areturn
    //   290: aload 15
    //   292: iload 17
    //   294: aaload
    //   295: invokeinterface 169 1 0
    //   300: astore 32
    //   302: aconst_null
    //   303: astore 4
    //   305: aload 32
    //   307: ifnull +375 -> 682
    //   310: aload 32
    //   312: invokevirtual 172	java/lang/String:length	()I
    //   315: ifle +367 -> 682
    //   318: aload 32
    //   320: ldc 174
    //   322: invokevirtual 178	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   325: astore 33
    //   327: aconst_null
    //   328: astore 4
    //   330: aload 33
    //   332: ifnull +350 -> 682
    //   335: aload 33
    //   337: arraylength
    //   338: istore 34
    //   340: aconst_null
    //   341: astore 4
    //   343: iload 34
    //   345: ifle +337 -> 682
    //   348: aload 33
    //   350: arraylength
    //   351: istore 35
    //   353: iconst_0
    //   354: istore 36
    //   356: goto +319 -> 675
    //   359: aload 33
    //   361: iload 36
    //   363: aaload
    //   364: astore 37
    //   366: aload 37
    //   368: invokevirtual 181	java/lang/String:trim	()Ljava/lang/String;
    //   371: invokevirtual 184	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   374: ldc 186
    //   376: invokevirtual 190	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   379: istore 38
    //   381: aconst_null
    //   382: astore 4
    //   384: iload 38
    //   386: ifeq +302 -> 688
    //   389: aload 37
    //   391: invokevirtual 181	java/lang/String:trim	()Ljava/lang/String;
    //   394: invokevirtual 184	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   397: ldc 186
    //   399: ldc 192
    //   401: invokevirtual 196	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   404: astore 14
    //   406: goto +276 -> 682
    //   409: aload 19
    //   411: iload 22
    //   413: aaload
    //   414: astore 28
    //   416: aload 28
    //   418: invokeinterface 169 1 0
    //   423: astore 29
    //   425: aconst_null
    //   426: astore 4
    //   428: aload 29
    //   430: ifnull +36 -> 466
    //   433: aload 28
    //   435: invokeinterface 169 1 0
    //   440: invokevirtual 172	java/lang/String:length	()I
    //   443: istore 30
    //   445: aconst_null
    //   446: astore 4
    //   448: iload 30
    //   450: ifle +16 -> 466
    //   453: aload 28
    //   455: invokeinterface 169 1 0
    //   460: astore 31
    //   462: aload 31
    //   464: astore 20
    //   466: iinc 22 1
    //   469: goto +196 -> 665
    //   472: aload_3
    //   473: aload 26
    //   475: invokevirtual 199	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   478: pop
    //   479: goto -243 -> 236
    //   482: astore 5
    //   484: aload 25
    //   486: astore 6
    //   488: aload 24
    //   490: astore 4
    //   492: aload 9
    //   494: astore 7
    //   496: aload 5
    //   498: invokestatic 205	com/autonavi/aps/api/Utils:printException	(Ljava/lang/Exception;)V
    //   501: aload 5
    //   503: athrow
    //   504: astore 8
    //   506: aload 7
    //   508: astore 9
    //   510: aload 6
    //   512: ifnull +8 -> 520
    //   515: aload 6
    //   517: invokevirtual 151	java/io/BufferedReader:close	()V
    //   520: aload 4
    //   522: ifnull +8 -> 530
    //   525: aload 4
    //   527: invokevirtual 154	java/io/InputStream:close	()V
    //   530: aload 9
    //   532: ifnull +15 -> 547
    //   535: aload 9
    //   537: invokeinterface 158 1 0
    //   542: invokeinterface 163 1 0
    //   547: aload 8
    //   549: athrow
    //   550: astore 8
    //   552: aconst_null
    //   553: astore 6
    //   555: aconst_null
    //   556: astore 4
    //   558: aconst_null
    //   559: astore 9
    //   561: goto -51 -> 510
    //   564: astore 8
    //   566: aconst_null
    //   567: astore 6
    //   569: goto -59 -> 510
    //   572: astore 8
    //   574: aload 24
    //   576: astore 4
    //   578: aconst_null
    //   579: astore 6
    //   581: goto -71 -> 510
    //   584: astore 8
    //   586: aload 25
    //   588: astore 6
    //   590: aload 24
    //   592: astore 4
    //   594: goto -84 -> 510
    //   597: astore 5
    //   599: aconst_null
    //   600: astore 6
    //   602: aconst_null
    //   603: astore 4
    //   605: aconst_null
    //   606: astore 7
    //   608: goto -112 -> 496
    //   611: astore 5
    //   613: aload 9
    //   615: astore 7
    //   617: aconst_null
    //   618: astore 6
    //   620: goto -124 -> 496
    //   623: astore 5
    //   625: aload 24
    //   627: astore 4
    //   629: aload 9
    //   631: astore 7
    //   633: aconst_null
    //   634: astore 6
    //   636: goto -140 -> 496
    //   639: aload 4
    //   641: astore 24
    //   643: goto -427 -> 216
    //   646: aconst_null
    //   647: astore 25
    //   649: aconst_null
    //   650: astore 24
    //   652: goto -404 -> 248
    //   655: iload 17
    //   657: iload 16
    //   659: if_icmplt -369 -> 290
    //   662: goto -551 -> 111
    //   665: iload 22
    //   667: iload 21
    //   669: if_icmplt -260 -> 409
    //   672: goto -521 -> 151
    //   675: iload 36
    //   677: iload 35
    //   679: if_icmplt -320 -> 359
    //   682: iinc 17 1
    //   685: goto -30 -> 655
    //   688: iinc 36 1
    //   691: goto -16 -> 675
    //
    // Exception table:
    //   from	to	target	type
    //   236	243	482	java/lang/Exception
    //   472	479	482	java/lang/Exception
    //   496	504	504	finally
    //   11	17	550	finally
    //   21	77	564	finally
    //   81	92	564	finally
    //   100	105	564	finally
    //   115	126	564	finally
    //   137	142	564	finally
    //   151	165	564	finally
    //   176	190	564	finally
    //   195	216	564	finally
    //   290	302	564	finally
    //   310	327	564	finally
    //   335	340	564	finally
    //   348	353	564	finally
    //   359	381	564	finally
    //   389	406	564	finally
    //   409	425	564	finally
    //   433	445	564	finally
    //   453	462	564	finally
    //   216	236	572	finally
    //   236	243	584	finally
    //   472	479	584	finally
    //   11	17	597	java/lang/Exception
    //   21	77	611	java/lang/Exception
    //   81	92	611	java/lang/Exception
    //   100	105	611	java/lang/Exception
    //   115	126	611	java/lang/Exception
    //   137	142	611	java/lang/Exception
    //   151	165	611	java/lang/Exception
    //   176	190	611	java/lang/Exception
    //   195	216	611	java/lang/Exception
    //   290	302	611	java/lang/Exception
    //   310	327	611	java/lang/Exception
    //   335	340	611	java/lang/Exception
    //   348	353	611	java/lang/Exception
    //   359	381	611	java/lang/Exception
    //   389	406	611	java/lang/Exception
    //   409	425	611	java/lang/Exception
    //   433	445	611	java/lang/Exception
    //   453	462	611	java/lang/Exception
    //   216	236	623	java/lang/Exception
  }

  public HttpClient getHttpClient()
  {
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
    ConnectivityManager localConnectivityManager = (ConnectivityManager)this.a.getSystemService("connectivity");
    int i;
    String str1;
    int j;
    if (localConnectivityManager != null)
    {
      NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
      if ((localNetworkInfo != null) && (localNetworkInfo.isAvailable()) && (localNetworkInfo.isConnected()) && (localNetworkInfo.getTypeName().equalsIgnoreCase("wifi")))
      {
        i = 1;
        if (i == 0)
        {
          str1 = Proxy.getHost(this.a);
          if ((str1 != null) && (str1.length() > 0))
          {
            String str2 = Build.MODEL;
            if ((str2 == null) || ((str2.indexOf("OMAP_SS") < 0) && (str2.indexOf("omap_ss") < 0) && (str2.indexOf("MT810") < 0) && (str2.indexOf("MT720") < 0) && (str2.indexOf("GT-I9008") < 0)))
              break label202;
            j = 1;
            label160: if (j == 0)
              break label208;
            Utils.writeLogCat("oms found, ignore net proxy");
          }
        }
      }
    }
    while (true)
    {
      HttpConnectionParams.setConnectionTimeout(localDefaultHttpClient.getParams(), 20000);
      HttpConnectionParams.setSoTimeout(localDefaultHttpClient.getParams(), 20000);
      return localDefaultHttpClient;
      i = 0;
      break;
      label202: j = 0;
      break label160;
      label208: HttpHost localHttpHost = new HttpHost(str1, Proxy.getPort(this.a));
      localDefaultHttpClient.getParams().setParameter("http.route.default-proxy", localHttpHost);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.autonavi.aps.api.NetManagerApache
 * JD-Core Version:    0.6.0
 */