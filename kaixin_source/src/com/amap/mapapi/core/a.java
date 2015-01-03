package com.amap.mapapi.core;

import android.util.Log;
import com.amap.mapapi.map.i;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.util.zip.GZIPInputStream;
import org.json.JSONObject;

public class a
{
  public static int a = -1;
  public static String b = "";
  public static int c = 0;

  protected static InputStream a(HttpURLConnection paramHttpURLConnection)
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

  private static String a()
  {
    return j.a().d() + "/log/init";
  }

  // ERROR //
  public static boolean a(android.content.Context paramContext)
    throws AMapException
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: invokestatic 93	com/amap/mapapi/core/a:b	()[B
    //   6: astore_2
    //   7: invokestatic 95	com/amap/mapapi/core/a:a	()Ljava/lang/String;
    //   10: aload_2
    //   11: aload_0
    //   12: invokestatic 100	com/amap/mapapi/core/e:b	(Landroid/content/Context;)Ljava/net/Proxy;
    //   15: invokestatic 105	com/amap/mapapi/core/g:a	(Ljava/lang/String;[BLjava/net/Proxy;)Ljava/net/HttpURLConnection;
    //   18: astore 10
    //   20: aload 10
    //   22: astore 5
    //   24: aload 5
    //   26: invokestatic 107	com/amap/mapapi/core/a:a	(Ljava/net/HttpURLConnection;)Ljava/io/InputStream;
    //   29: astore 11
    //   31: aload 11
    //   33: astore 4
    //   35: aload 4
    //   37: invokestatic 110	com/amap/mapapi/core/a:a	(Ljava/io/InputStream;)Z
    //   40: istore 12
    //   42: aload 4
    //   44: ifnull +8 -> 52
    //   47: aload 4
    //   49: invokevirtual 115	java/io/InputStream:close	()V
    //   52: iconst_0
    //   53: ifeq +7 -> 60
    //   56: aconst_null
    //   57: invokevirtual 118	java/io/OutputStream:close	()V
    //   60: aload 5
    //   62: ifnull +8 -> 70
    //   65: aload 5
    //   67: invokevirtual 121	java/net/HttpURLConnection:disconnect	()V
    //   70: ldc 2
    //   72: monitorexit
    //   73: iload 12
    //   75: ireturn
    //   76: astore 14
    //   78: new 25	com/amap/mapapi/core/AMapException
    //   81: dup
    //   82: ldc 68
    //   84: invokespecial 62	com/amap/mapapi/core/AMapException:<init>	(Ljava/lang/String;)V
    //   87: athrow
    //   88: astore_1
    //   89: ldc 2
    //   91: monitorexit
    //   92: aload_1
    //   93: athrow
    //   94: astore 13
    //   96: new 25	com/amap/mapapi/core/AMapException
    //   99: dup
    //   100: ldc 68
    //   102: invokespecial 62	com/amap/mapapi/core/AMapException:<init>	(Ljava/lang/String;)V
    //   105: athrow
    //   106: astore 8
    //   108: aconst_null
    //   109: astore 4
    //   111: aconst_null
    //   112: astore 5
    //   114: ldc 123
    //   116: aload 8
    //   118: invokevirtual 126	com/amap/mapapi/core/AMapException:getErrorMessage	()Ljava/lang/String;
    //   121: invokestatic 132	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   124: pop
    //   125: aload 8
    //   127: invokevirtual 135	com/amap/mapapi/core/AMapException:printStackTrace	()V
    //   130: new 25	com/amap/mapapi/core/AMapException
    //   133: dup
    //   134: aload 8
    //   136: invokevirtual 126	com/amap/mapapi/core/AMapException:getErrorMessage	()Ljava/lang/String;
    //   139: invokespecial 62	com/amap/mapapi/core/AMapException:<init>	(Ljava/lang/String;)V
    //   142: athrow
    //   143: astore_3
    //   144: aload 4
    //   146: ifnull +8 -> 154
    //   149: aload 4
    //   151: invokevirtual 115	java/io/InputStream:close	()V
    //   154: iconst_0
    //   155: ifeq +7 -> 162
    //   158: aconst_null
    //   159: invokevirtual 118	java/io/OutputStream:close	()V
    //   162: aload 5
    //   164: ifnull +8 -> 172
    //   167: aload 5
    //   169: invokevirtual 121	java/net/HttpURLConnection:disconnect	()V
    //   172: aload_3
    //   173: athrow
    //   174: astore 7
    //   176: new 25	com/amap/mapapi/core/AMapException
    //   179: dup
    //   180: ldc 68
    //   182: invokespecial 62	com/amap/mapapi/core/AMapException:<init>	(Ljava/lang/String;)V
    //   185: athrow
    //   186: astore 6
    //   188: new 25	com/amap/mapapi/core/AMapException
    //   191: dup
    //   192: ldc 68
    //   194: invokespecial 62	com/amap/mapapi/core/AMapException:<init>	(Ljava/lang/String;)V
    //   197: athrow
    //   198: astore_3
    //   199: aconst_null
    //   200: astore 4
    //   202: aconst_null
    //   203: astore 5
    //   205: goto -61 -> 144
    //   208: astore_3
    //   209: aconst_null
    //   210: astore 4
    //   212: goto -68 -> 144
    //   215: astore 8
    //   217: aconst_null
    //   218: astore 4
    //   220: goto -106 -> 114
    //   223: astore 8
    //   225: goto -111 -> 114
    //
    // Exception table:
    //   from	to	target	type
    //   47	52	76	java/io/IOException
    //   3	7	88	finally
    //   47	52	88	finally
    //   56	60	88	finally
    //   65	70	88	finally
    //   78	88	88	finally
    //   96	106	88	finally
    //   149	154	88	finally
    //   158	162	88	finally
    //   167	172	88	finally
    //   172	174	88	finally
    //   176	186	88	finally
    //   188	198	88	finally
    //   56	60	94	java/io/IOException
    //   7	20	106	com/amap/mapapi/core/AMapException
    //   35	42	143	finally
    //   114	143	143	finally
    //   149	154	174	java/io/IOException
    //   158	162	186	java/io/IOException
    //   7	20	198	finally
    //   24	31	208	finally
    //   24	31	215	com/amap/mapapi/core/AMapException
    //   35	42	223	com/amap/mapapi/core/AMapException
  }

  private static boolean a(InputStream paramInputStream)
    throws AMapException
  {
    do
      try
      {
        JSONObject localJSONObject = new JSONObject(new String(i.a(paramInputStream)));
        if (localJSONObject.has("status"))
        {
          i = localJSONObject.getInt("status");
          if (i == 1)
            a = 1;
        }
        else
        {
          if (localJSONObject.has("info"))
            b = localJSONObject.getString("info");
          if (a != 0)
            continue;
          Log.i("AuthFailure", b);
          throw new AMapException("key鉴权失败");
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          int i;
          localException.printStackTrace();
          if (a != 1)
            break;
          return true;
          if (i != 0)
            continue;
          a = 0;
        }
      }
      finally
      {
        while (a == 1);
        return false;
      }
    while (a == 1);
    return false;
    return false;
  }

  private static byte[] b()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("resType=json&encode=UTF-8");
    return localStringBuffer.toString().getBytes();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.core.a
 * JD-Core Version:    0.6.0
 */