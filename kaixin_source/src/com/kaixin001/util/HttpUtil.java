package com.kaixin001.util;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.util.ByteArrayBuffer;

public class HttpUtil
{
  private static final int TIME_OUT = 20000;

  // ERROR //
  public static byte[] doGetData(java.lang.String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aconst_null
    //   3: astore_2
    //   4: new 21	org/apache/http/params/BasicHttpParams
    //   7: dup
    //   8: invokespecial 22	org/apache/http/params/BasicHttpParams:<init>	()V
    //   11: astore_3
    //   12: aload_3
    //   13: sipush 20000
    //   16: invokestatic 28	org/apache/http/params/HttpConnectionParams:setConnectionTimeout	(Lorg/apache/http/params/HttpParams;I)V
    //   19: aload_3
    //   20: sipush 20000
    //   23: invokestatic 31	org/apache/http/params/HttpConnectionParams:setSoTimeout	(Lorg/apache/http/params/HttpParams;I)V
    //   26: new 33	org/apache/http/impl/client/DefaultHttpClient
    //   29: dup
    //   30: aload_3
    //   31: invokespecial 36	org/apache/http/impl/client/DefaultHttpClient:<init>	(Lorg/apache/http/params/HttpParams;)V
    //   34: astore 8
    //   36: new 38	org/apache/http/client/methods/HttpGet
    //   39: dup
    //   40: aload_0
    //   41: invokespecial 41	org/apache/http/client/methods/HttpGet:<init>	(Ljava/lang/String;)V
    //   44: astore 9
    //   46: aload 9
    //   48: ldc 43
    //   50: ldc 45
    //   52: invokevirtual 49	org/apache/http/client/methods/HttpGet:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
    //   55: aload 8
    //   57: aload 9
    //   59: invokevirtual 53	org/apache/http/impl/client/DefaultHttpClient:execute	(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;
    //   62: astore 10
    //   64: aload 10
    //   66: invokeinterface 59 1 0
    //   71: invokeinterface 65 1 0
    //   76: istore 11
    //   78: iload 11
    //   80: sipush 200
    //   83: if_icmpeq +55 -> 138
    //   86: new 19	java/lang/Exception
    //   89: dup
    //   90: new 67	java/lang/StringBuilder
    //   93: dup
    //   94: ldc 69
    //   96: invokespecial 70	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   99: iload 11
    //   101: invokevirtual 74	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   104: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   107: invokespecial 79	java/lang/Exception:<init>	(Ljava/lang/String;)V
    //   110: athrow
    //   111: astore 4
    //   113: aload 9
    //   115: astore_2
    //   116: aload 8
    //   118: astore_1
    //   119: aload 4
    //   121: invokevirtual 82	org/apache/http/client/ClientProtocolException:printStackTrace	()V
    //   124: aload_2
    //   125: ifnull +7 -> 132
    //   128: aload_2
    //   129: invokevirtual 85	org/apache/http/client/methods/HttpGet:abort	()V
    //   132: aload_1
    //   133: ifnull +3 -> 136
    //   136: aconst_null
    //   137: areturn
    //   138: aload 10
    //   140: invokeinterface 89 1 0
    //   145: invokestatic 93	com/kaixin001/util/HttpUtil:toByteArray	(Lorg/apache/http/HttpEntity;)[B
    //   148: astore 12
    //   150: aload 9
    //   152: ifnull +8 -> 160
    //   155: aload 9
    //   157: invokevirtual 85	org/apache/http/client/methods/HttpGet:abort	()V
    //   160: aload 8
    //   162: ifnull +147 -> 309
    //   165: aload 12
    //   167: areturn
    //   168: astore 7
    //   170: aload 7
    //   172: invokevirtual 94	java/io/IOException:printStackTrace	()V
    //   175: aload_2
    //   176: ifnull +7 -> 183
    //   179: aload_2
    //   180: invokevirtual 85	org/apache/http/client/methods/HttpGet:abort	()V
    //   183: aload_1
    //   184: ifnull -48 -> 136
    //   187: aconst_null
    //   188: areturn
    //   189: astore 6
    //   191: aload 6
    //   193: invokevirtual 95	java/lang/Exception:printStackTrace	()V
    //   196: aload_2
    //   197: ifnull +7 -> 204
    //   200: aload_2
    //   201: invokevirtual 85	org/apache/http/client/methods/HttpGet:abort	()V
    //   204: aload_1
    //   205: ifnull -69 -> 136
    //   208: aconst_null
    //   209: areturn
    //   210: astore 5
    //   212: aload_2
    //   213: ifnull +7 -> 220
    //   216: aload_2
    //   217: invokevirtual 85	org/apache/http/client/methods/HttpGet:abort	()V
    //   220: aload_1
    //   221: ifnull +3 -> 224
    //   224: aload 5
    //   226: athrow
    //   227: astore 5
    //   229: aload 8
    //   231: astore_1
    //   232: aconst_null
    //   233: astore_2
    //   234: goto -22 -> 212
    //   237: astore 5
    //   239: aload 9
    //   241: astore_2
    //   242: aload 8
    //   244: astore_1
    //   245: goto -33 -> 212
    //   248: astore 6
    //   250: aload 8
    //   252: astore_1
    //   253: aconst_null
    //   254: astore_2
    //   255: goto -64 -> 191
    //   258: astore 6
    //   260: aload 9
    //   262: astore_2
    //   263: aload 8
    //   265: astore_1
    //   266: goto -75 -> 191
    //   269: astore 7
    //   271: aload 8
    //   273: astore_1
    //   274: aconst_null
    //   275: astore_2
    //   276: goto -106 -> 170
    //   279: astore 7
    //   281: aload 9
    //   283: astore_2
    //   284: aload 8
    //   286: astore_1
    //   287: goto -117 -> 170
    //   290: astore 4
    //   292: aconst_null
    //   293: astore_1
    //   294: aconst_null
    //   295: astore_2
    //   296: goto -177 -> 119
    //   299: astore 4
    //   301: aload 8
    //   303: astore_1
    //   304: aconst_null
    //   305: astore_2
    //   306: goto -187 -> 119
    //   309: goto -144 -> 165
    //
    // Exception table:
    //   from	to	target	type
    //   46	78	111	org/apache/http/client/ClientProtocolException
    //   86	111	111	org/apache/http/client/ClientProtocolException
    //   138	150	111	org/apache/http/client/ClientProtocolException
    //   4	36	168	java/io/IOException
    //   4	36	189	java/lang/Exception
    //   4	36	210	finally
    //   119	124	210	finally
    //   170	175	210	finally
    //   191	196	210	finally
    //   36	46	227	finally
    //   46	78	237	finally
    //   86	111	237	finally
    //   138	150	237	finally
    //   36	46	248	java/lang/Exception
    //   46	78	258	java/lang/Exception
    //   86	111	258	java/lang/Exception
    //   138	150	258	java/lang/Exception
    //   36	46	269	java/io/IOException
    //   46	78	279	java/io/IOException
    //   86	111	279	java/io/IOException
    //   138	150	279	java/io/IOException
    //   4	36	290	org/apache/http/client/ClientProtocolException
    //   36	46	299	org/apache/http/client/ClientProtocolException
  }

  public static byte[] toByteArray(HttpEntity paramHttpEntity)
    throws IOException
  {
    if (paramHttpEntity == null)
      throw new IllegalArgumentException("HTTP entity may not be null");
    InputStream localInputStream = paramHttpEntity.getContent();
    if (localInputStream == null)
      return new byte[0];
    if (paramHttpEntity.getContentLength() > 2147483647L)
      throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
    int i = (int)paramHttpEntity.getContentLength();
    if (i < 0)
      i = 4096;
    ByteArrayBuffer localByteArrayBuffer = new ByteArrayBuffer(i);
    int j = 4096;
    try
    {
      if (i / j < 6)
        j = i / 6;
      byte[] arrayOfByte = new byte[j];
      int k = 0;
      while (true)
      {
        int m = localInputStream.read(arrayOfByte);
        if (m == -1)
          return localByteArrayBuffer.toByteArray();
        localByteArrayBuffer.append(arrayOfByte, 0, m);
        k += m;
      }
    }
    finally
    {
      localInputStream.close();
    }
    throw localObject;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.HttpUtil
 * JD-Core Version:    0.6.0
 */