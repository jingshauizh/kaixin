package com.amap.mapapi.geocoder;

import android.location.Address;
import com.amap.mapapi.core.j;
import com.amap.mapapi.core.m;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URLEncoder;
import java.util.ArrayList;

class a extends m<b, ArrayList<Address>>
{
  public int i = 0;

  public a(b paramb, Proxy paramProxy, String paramString1, String paramString2)
  {
    super(paramb, paramProxy, paramString1, paramString2);
    this.i = paramb.b;
  }

  // ERROR //
  protected ArrayList<Address> a(java.io.InputStream paramInputStream)
    throws com.amap.mapapi.core.AMapException
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_2
    //   2: new 29	java/util/ArrayList
    //   5: dup
    //   6: invokespecial 32	java/util/ArrayList:<init>	()V
    //   9: astore_3
    //   10: new 34	java/lang/String
    //   13: dup
    //   14: aload_1
    //   15: invokestatic 39	com/amap/mapapi/map/i:a	(Ljava/io/InputStream;)[B
    //   18: invokespecial 42	java/lang/String:<init>	([B)V
    //   21: astore 4
    //   23: aload 4
    //   25: invokestatic 48	com/amap/mapapi/core/e:c	(Ljava/lang/String;)V
    //   28: new 50	org/json/JSONObject
    //   31: dup
    //   32: aload 4
    //   34: invokespecial 52	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   37: astore 5
    //   39: aload 5
    //   41: ldc 54
    //   43: invokevirtual 58	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   46: ifle +364 -> 410
    //   49: aload 5
    //   51: ldc 60
    //   53: invokevirtual 64	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   56: astore 8
    //   58: iload_2
    //   59: aload 8
    //   61: invokevirtual 70	org/json/JSONArray:length	()I
    //   64: if_icmpge +346 -> 410
    //   67: aload 8
    //   69: iload_2
    //   70: invokevirtual 74	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   73: astore 9
    //   75: invokestatic 77	com/amap/mapapi/core/e:b	()Landroid/location/Address;
    //   78: astore 10
    //   80: aload 9
    //   82: ldc 79
    //   84: invokevirtual 83	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   87: astore 11
    //   89: aload 11
    //   91: invokestatic 89	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   94: ifne +10 -> 104
    //   97: aload 10
    //   99: aload 11
    //   101: invokevirtual 94	android/location/Address:setFeatureName	(Ljava/lang/String;)V
    //   104: aload 9
    //   106: ldc 96
    //   108: invokevirtual 83	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   111: astore 12
    //   113: aload 12
    //   115: invokestatic 89	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   118: ifne +11 -> 129
    //   121: aload 10
    //   123: iconst_2
    //   124: aload 12
    //   126: invokevirtual 100	android/location/Address:setAddressLine	(ILjava/lang/String;)V
    //   129: aload 9
    //   131: ldc 102
    //   133: invokevirtual 83	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   136: astore 13
    //   138: aload 13
    //   140: invokestatic 89	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   143: ifne +10 -> 153
    //   146: aload 10
    //   148: aload 13
    //   150: invokevirtual 105	android/location/Address:setAdminArea	(Ljava/lang/String;)V
    //   153: aload 9
    //   155: ldc 107
    //   157: invokevirtual 83	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   160: astore 14
    //   162: aload 14
    //   164: invokestatic 89	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   167: ifeq +7 -> 174
    //   170: aload 13
    //   172: astore 14
    //   174: aload 10
    //   176: aload 14
    //   178: invokevirtual 110	android/location/Address:setLocality	(Ljava/lang/String;)V
    //   181: aload 9
    //   183: ldc 112
    //   185: invokevirtual 83	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   188: astore 15
    //   190: aload 15
    //   192: invokestatic 89	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   195: istore 16
    //   197: iload 16
    //   199: ifne +46 -> 245
    //   202: aload 10
    //   204: invokevirtual 118	java/lang/Object:getClass	()Ljava/lang/Class;
    //   207: ldc 120
    //   209: iconst_1
    //   210: anewarray 122	java/lang/Class
    //   213: dup
    //   214: iconst_0
    //   215: ldc 34
    //   217: aastore
    //   218: invokevirtual 126	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   221: astore 21
    //   223: aload 21
    //   225: ifnull +20 -> 245
    //   228: aload 21
    //   230: aload 10
    //   232: iconst_1
    //   233: anewarray 114	java/lang/Object
    //   236: dup
    //   237: iconst_0
    //   238: aload 15
    //   240: aastore
    //   241: invokevirtual 132	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   244: pop
    //   245: aload 10
    //   247: iconst_0
    //   248: ldc 134
    //   250: invokevirtual 100	android/location/Address:setAddressLine	(ILjava/lang/String;)V
    //   253: aload 13
    //   255: aload 14
    //   257: invokevirtual 138	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   260: ifne +114 -> 374
    //   263: aload 10
    //   265: iconst_1
    //   266: new 140	java/lang/StringBuilder
    //   269: dup
    //   270: invokespecial 141	java/lang/StringBuilder:<init>	()V
    //   273: aload 13
    //   275: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   278: aload 14
    //   280: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   283: aload 15
    //   285: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   288: invokevirtual 149	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   291: invokevirtual 100	android/location/Address:setAddressLine	(ILjava/lang/String;)V
    //   294: aload 9
    //   296: ldc 151
    //   298: invokevirtual 83	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   301: astore 17
    //   303: aload 17
    //   305: invokestatic 89	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   308: ifne +13 -> 321
    //   311: aload 10
    //   313: aload 17
    //   315: invokestatic 157	java/lang/Double:parseDouble	(Ljava/lang/String;)D
    //   318: invokevirtual 161	android/location/Address:setLongitude	(D)V
    //   321: aload 9
    //   323: ldc 163
    //   325: invokevirtual 83	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   328: astore 18
    //   330: aload 18
    //   332: invokestatic 89	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   335: ifne +13 -> 348
    //   338: aload 10
    //   340: aload 18
    //   342: invokestatic 157	java/lang/Double:parseDouble	(Ljava/lang/String;)D
    //   345: invokevirtual 166	android/location/Address:setLatitude	(D)V
    //   348: aload_3
    //   349: aload 10
    //   351: invokevirtual 169	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   354: pop
    //   355: iinc 2 1
    //   358: goto -300 -> 58
    //   361: astore 23
    //   363: aload 23
    //   365: invokevirtual 172	java/lang/Exception:printStackTrace	()V
    //   368: aconst_null
    //   369: astore 4
    //   371: goto -348 -> 23
    //   374: aload 10
    //   376: iconst_1
    //   377: new 140	java/lang/StringBuilder
    //   380: dup
    //   381: invokespecial 141	java/lang/StringBuilder:<init>	()V
    //   384: aload 14
    //   386: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   389: aload 15
    //   391: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   394: invokevirtual 149	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   397: invokevirtual 100	android/location/Address:setAddressLine	(ILjava/lang/String;)V
    //   400: goto -106 -> 294
    //   403: astore 7
    //   405: aload 7
    //   407: invokevirtual 173	org/json/JSONException:printStackTrace	()V
    //   410: aload_3
    //   411: areturn
    //   412: astore 6
    //   414: aload 6
    //   416: invokevirtual 172	java/lang/Exception:printStackTrace	()V
    //   419: aload_3
    //   420: areturn
    //   421: astore 20
    //   423: goto -178 -> 245
    //
    // Exception table:
    //   from	to	target	type
    //   10	23	361	java/lang/Exception
    //   28	58	403	org/json/JSONException
    //   58	104	403	org/json/JSONException
    //   104	129	403	org/json/JSONException
    //   129	153	403	org/json/JSONException
    //   153	170	403	org/json/JSONException
    //   174	197	403	org/json/JSONException
    //   202	223	403	org/json/JSONException
    //   228	245	403	org/json/JSONException
    //   245	294	403	org/json/JSONException
    //   294	321	403	org/json/JSONException
    //   321	348	403	org/json/JSONException
    //   348	355	403	org/json/JSONException
    //   374	400	403	org/json/JSONException
    //   28	58	412	java/lang/Exception
    //   58	104	412	java/lang/Exception
    //   104	129	412	java/lang/Exception
    //   129	153	412	java/lang/Exception
    //   153	170	412	java/lang/Exception
    //   174	197	412	java/lang/Exception
    //   245	294	412	java/lang/Exception
    //   294	321	412	java/lang/Exception
    //   321	348	412	java/lang/Exception
    //   348	355	412	java/lang/Exception
    //   374	400	412	java/lang/Exception
    //   202	223	421	java/lang/Exception
    //   228	245	421	java/lang/Exception
  }

  protected byte[] d()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("?sid=7000&resType=json&encode=utf-8&address=");
    Object localObject = ((b)this.b).a;
    try
    {
      String str = URLEncoder.encode((String)localObject, "utf-8");
      localObject = str;
      localStringBuilder.append((String)localObject);
      localStringBuilder.append("&count=");
      localStringBuilder.append(((b)this.b).b);
      return localStringBuilder.toString().getBytes();
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      while (true)
        localUnsupportedEncodingException.printStackTrace();
    }
  }

  protected String e()
  {
    return j.a().d() + "/geocode/simple?";
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.geocoder.a
 * JD-Core Version:    0.6.0
 */