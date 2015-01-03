package com.amap.mapapi.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.util.zip.GZIPInputStream;

public abstract class m<T, V>
{
  protected Proxy a;
  protected T b;
  protected int c = 1;
  protected int d = 20;
  protected int e = 0;
  protected int f = 0;
  protected String g;
  protected String h = "";

  public m(T paramT, Proxy paramProxy, String paramString1, String paramString2)
  {
    this.a = paramProxy;
    this.b = paramT;
    this.c = 1;
    this.d = 5;
    this.e = 2;
    this.g = paramString2;
  }

  // ERROR //
  private V a()
    throws AMapException
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_1
    //   2: aconst_null
    //   3: astore_2
    //   4: aconst_null
    //   5: astore_3
    //   6: aconst_null
    //   7: astore 4
    //   9: iload_1
    //   10: aload_0
    //   11: getfield 25	com/amap/mapapi/core/m:c	I
    //   14: if_icmpge +327 -> 341
    //   17: aload_0
    //   18: aload_0
    //   19: invokevirtual 51	com/amap/mapapi/core/m:e	()Ljava/lang/String;
    //   22: putfield 35	com/amap/mapapi/core/m:h	Ljava/lang/String;
    //   25: aload_0
    //   26: invokevirtual 54	com/amap/mapapi/core/m:f	()[B
    //   29: astore 16
    //   31: aload 16
    //   33: ifnonnull +76 -> 109
    //   36: aload_0
    //   37: getfield 35	com/amap/mapapi/core/m:h	Ljava/lang/String;
    //   40: aload_0
    //   41: getfield 37	com/amap/mapapi/core/m:a	Ljava/net/Proxy;
    //   44: invokestatic 59	com/amap/mapapi/core/g:a	(Ljava/lang/String;Ljava/net/Proxy;)Ljava/net/HttpURLConnection;
    //   47: astore 4
    //   49: aload_0
    //   50: aload 4
    //   52: invokevirtual 62	com/amap/mapapi/core/m:a	(Ljava/net/HttpURLConnection;)Ljava/io/InputStream;
    //   55: astore 17
    //   57: aload 17
    //   59: astore 6
    //   61: aload_0
    //   62: aload 6
    //   64: invokespecial 65	com/amap/mapapi/core/m:a	(Ljava/io/InputStream;)Ljava/lang/Object;
    //   67: astore_2
    //   68: aload_0
    //   69: getfield 25	com/amap/mapapi/core/m:c	I
    //   72: istore_1
    //   73: aload 6
    //   75: ifnull +287 -> 362
    //   78: aload 6
    //   80: invokevirtual 70	java/io/InputStream:close	()V
    //   83: aconst_null
    //   84: astore_3
    //   85: iconst_0
    //   86: ifeq +7 -> 93
    //   89: aconst_null
    //   90: invokevirtual 73	java/io/OutputStream:close	()V
    //   93: aload 4
    //   95: ifnull +11 -> 106
    //   98: aload 4
    //   100: invokevirtual 78	java/net/HttpURLConnection:disconnect	()V
    //   103: aconst_null
    //   104: astore 4
    //   106: goto -97 -> 9
    //   109: aload_0
    //   110: getfield 35	com/amap/mapapi/core/m:h	Ljava/lang/String;
    //   113: aload 16
    //   115: aload_0
    //   116: getfield 37	com/amap/mapapi/core/m:a	Ljava/net/Proxy;
    //   119: invokestatic 81	com/amap/mapapi/core/g:a	(Ljava/lang/String;[BLjava/net/Proxy;)Ljava/net/HttpURLConnection;
    //   122: astore 20
    //   124: aload 20
    //   126: astore 4
    //   128: goto -79 -> 49
    //   131: astore 19
    //   133: new 44	com/amap/mapapi/core/AMapException
    //   136: dup
    //   137: ldc 83
    //   139: invokespecial 86	com/amap/mapapi/core/AMapException:<init>	(Ljava/lang/String;)V
    //   142: athrow
    //   143: astore 18
    //   145: new 44	com/amap/mapapi/core/AMapException
    //   148: dup
    //   149: ldc 83
    //   151: invokespecial 86	com/amap/mapapi/core/AMapException:<init>	(Ljava/lang/String;)V
    //   154: athrow
    //   155: astore 9
    //   157: aload_3
    //   158: astore 6
    //   160: aload 9
    //   162: astore 10
    //   164: iinc 1 1
    //   167: aload_0
    //   168: getfield 25	com/amap/mapapi/core/m:c	I
    //   171: istore 11
    //   173: iload_1
    //   174: iload 11
    //   176: if_icmpge +99 -> 275
    //   179: sipush 1000
    //   182: aload_0
    //   183: getfield 29	com/amap/mapapi/core/m:e	I
    //   186: imul
    //   187: i2l
    //   188: invokestatic 92	java/lang/Thread:sleep	(J)V
    //   191: aload 6
    //   193: ifnull +163 -> 356
    //   196: aload 6
    //   198: invokevirtual 70	java/io/InputStream:close	()V
    //   201: aconst_null
    //   202: astore_3
    //   203: iconst_0
    //   204: ifeq +7 -> 211
    //   207: aconst_null
    //   208: invokevirtual 73	java/io/OutputStream:close	()V
    //   211: aload 4
    //   213: ifnull -107 -> 106
    //   216: aload 4
    //   218: invokevirtual 78	java/net/HttpURLConnection:disconnect	()V
    //   221: aconst_null
    //   222: astore 4
    //   224: goto -118 -> 106
    //   227: astore 13
    //   229: new 44	com/amap/mapapi/core/AMapException
    //   232: dup
    //   233: aload 13
    //   235: invokevirtual 95	java/lang/InterruptedException:getMessage	()Ljava/lang/String;
    //   238: invokespecial 86	com/amap/mapapi/core/AMapException:<init>	(Ljava/lang/String;)V
    //   241: athrow
    //   242: astore 5
    //   244: aload 6
    //   246: ifnull +8 -> 254
    //   249: aload 6
    //   251: invokevirtual 70	java/io/InputStream:close	()V
    //   254: iconst_0
    //   255: ifeq +7 -> 262
    //   258: aconst_null
    //   259: invokevirtual 73	java/io/OutputStream:close	()V
    //   262: aload 4
    //   264: ifnull +8 -> 272
    //   267: aload 4
    //   269: invokevirtual 78	java/net/HttpURLConnection:disconnect	()V
    //   272: aload 5
    //   274: athrow
    //   275: aload_0
    //   276: invokevirtual 97	com/amap/mapapi/core/m:h	()Ljava/lang/Object;
    //   279: pop
    //   280: new 44	com/amap/mapapi/core/AMapException
    //   283: dup
    //   284: aload 10
    //   286: invokevirtual 100	com/amap/mapapi/core/AMapException:getErrorMessage	()Ljava/lang/String;
    //   289: invokespecial 86	com/amap/mapapi/core/AMapException:<init>	(Ljava/lang/String;)V
    //   292: athrow
    //   293: astore 15
    //   295: new 44	com/amap/mapapi/core/AMapException
    //   298: dup
    //   299: ldc 83
    //   301: invokespecial 86	com/amap/mapapi/core/AMapException:<init>	(Ljava/lang/String;)V
    //   304: athrow
    //   305: astore 14
    //   307: new 44	com/amap/mapapi/core/AMapException
    //   310: dup
    //   311: ldc 83
    //   313: invokespecial 86	com/amap/mapapi/core/AMapException:<init>	(Ljava/lang/String;)V
    //   316: athrow
    //   317: astore 8
    //   319: new 44	com/amap/mapapi/core/AMapException
    //   322: dup
    //   323: ldc 83
    //   325: invokespecial 86	com/amap/mapapi/core/AMapException:<init>	(Ljava/lang/String;)V
    //   328: athrow
    //   329: astore 7
    //   331: new 44	com/amap/mapapi/core/AMapException
    //   334: dup
    //   335: ldc 83
    //   337: invokespecial 86	com/amap/mapapi/core/AMapException:<init>	(Ljava/lang/String;)V
    //   340: athrow
    //   341: aload_2
    //   342: areturn
    //   343: astore 5
    //   345: aload_3
    //   346: astore 6
    //   348: goto -104 -> 244
    //   351: astore 10
    //   353: goto -189 -> 164
    //   356: aload 6
    //   358: astore_3
    //   359: goto -156 -> 203
    //   362: aload 6
    //   364: astore_3
    //   365: goto -280 -> 85
    //
    // Exception table:
    //   from	to	target	type
    //   78	83	131	java/io/IOException
    //   89	93	143	java/io/IOException
    //   17	31	155	com/amap/mapapi/core/AMapException
    //   36	49	155	com/amap/mapapi/core/AMapException
    //   49	57	155	com/amap/mapapi/core/AMapException
    //   109	124	155	com/amap/mapapi/core/AMapException
    //   179	191	227	java/lang/InterruptedException
    //   61	73	242	finally
    //   167	173	242	finally
    //   179	191	242	finally
    //   229	242	242	finally
    //   275	293	242	finally
    //   196	201	293	java/io/IOException
    //   207	211	305	java/io/IOException
    //   249	254	317	java/io/IOException
    //   258	262	329	java/io/IOException
    //   17	31	343	finally
    //   36	49	343	finally
    //   49	57	343	finally
    //   109	124	343	finally
    //   61	73	351	com/amap/mapapi/core/AMapException
  }

  private V a(InputStream paramInputStream)
    throws AMapException
  {
    return b(paramInputStream);
  }

  protected InputStream a(HttpURLConnection paramHttpURLConnection)
    throws AMapException
  {
    try
    {
      PushbackInputStream localPushbackInputStream = new PushbackInputStream(paramHttpURLConnection.getInputStream(), 2);
      byte[] arrayOfByte = new byte[2];
      localPushbackInputStream.read(arrayOfByte);
      localPushbackInputStream.unread(arrayOfByte);
      if ((arrayOfByte[0] == 31) && (arrayOfByte[1] == -117))
      {
        GZIPInputStream localGZIPInputStream = new GZIPInputStream(localPushbackInputStream);
        return localGZIPInputStream;
      }
      return localPushbackInputStream;
    }
    catch (ProtocolException localProtocolException)
    {
      throw new AMapException("协议解析错误 - ProtocolException");
    }
    catch (UnknownHostException localUnknownHostException)
    {
      throw new AMapException("未知主机 - UnKnowHostException");
    }
    catch (UnknownServiceException localUnknownServiceException)
    {
      throw new AMapException("服务器连接失败 - UnknownServiceException");
    }
    catch (IOException localIOException)
    {
    }
    throw new AMapException("IO 操作异常 - IOException");
  }

  protected abstract V b(InputStream paramInputStream)
    throws AMapException;

  protected abstract byte[] d();

  protected abstract String e();

  protected byte[] f()
  {
    return d();
  }

  public V g()
    throws AMapException
  {
    Object localObject1 = this.b;
    Object localObject2 = null;
    if (localObject1 != null)
      localObject2 = a();
    return localObject2;
  }

  protected V h()
  {
    return null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.core.m
 * JD-Core Version:    0.6.0
 */