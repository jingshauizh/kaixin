package com.amap.mapapi.route;

import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.j;
import java.net.Proxy;
import java.util.LinkedList;
import java.util.List;

class a extends e
{
  List<GeoPoint> i;

  public a(f paramf, Proxy paramProxy, String paramString1, String paramString2)
  {
    super(paramf, paramProxy, paramString1, paramString2);
  }

  private Segment a(GeoPoint paramGeoPoint1, GeoPoint paramGeoPoint2)
  {
    Segment localSegment = new Segment();
    GeoPoint[] arrayOfGeoPoint = new GeoPoint[2];
    localSegment.setShapes(arrayOfGeoPoint);
    arrayOfGeoPoint[0] = paramGeoPoint1;
    arrayOfGeoPoint[1] = paramGeoPoint2;
    int j = paramGeoPoint2.getLatitudeE6() - paramGeoPoint1.getLatitudeE6();
    int k = paramGeoPoint2.getLongitudeE6() - paramGeoPoint1.getLongitudeE6();
    localSegment.setLength(com.amap.mapapi.core.e.a((int)Math.sqrt(k * k + j * j)));
    if (localSegment.getLength() == 0)
      localSegment.setLength(10);
    return localSegment;
  }

  private List<Segment> a(LinkedList<Segment> paramLinkedList)
  {
    int j = paramLinkedList.size();
    Segment[] arrayOfSegment = new Segment[j - 1];
    for (int k = 0; k <= j - 2; k++)
    {
      Segment localSegment1 = (Segment)paramLinkedList.get(k);
      Segment localSegment2 = (Segment)paramLinkedList.get(k + 1);
      arrayOfSegment[k] = a(localSegment1.getLastPoint(), localSegment2.getFirstPoint());
    }
    for (int m = 0; m <= j - 2; m++)
      paramLinkedList.add(1 + m * 2, arrayOfSegment[m]);
    paramLinkedList.addFirst(a(this.l, ((Segment)paramLinkedList.getFirst()).getFirstPoint()));
    paramLinkedList.addLast(a(((Segment)paramLinkedList.getLast()).getLastPoint(), this.m));
    return paramLinkedList;
  }

  private void a(BusSegment paramBusSegment, String paramString)
  {
    paramBusSegment.setLineName(paramString);
    paramBusSegment.setFirstStationName("");
    paramBusSegment.setLastStationName("");
    int j = paramString.indexOf("(");
    int k = paramString.lastIndexOf(")");
    if ((j <= 0) || (k <= 0) || (k <= j))
      return;
    paramBusSegment.setLineName(paramString.substring(0, j));
    int m = j + 1;
    try
    {
      String[] arrayOfString = paramString.substring(m, k).split("--");
      paramBusSegment.setFirstStationName(arrayOfString[0]);
      paramBusSegment.setLastStationName(arrayOfString[1]);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void a(GeoPoint[] paramArrayOfGeoPoint, String[] paramArrayOfString)
  {
    int j = 0;
    for (int k = 1; j < -1 + paramArrayOfString.length; k++)
    {
      int m = (int)(1000000.0D * Double.parseDouble(paramArrayOfString[j]));
      paramArrayOfGeoPoint[k] = new GeoPoint((int)(1000000.0D * Double.parseDouble(paramArrayOfString[(j + 1)])), m);
      j += 2;
    }
  }

  private void a(String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    for (int j = 0; j < paramArrayOfString2.length; j++)
      paramArrayOfString1[(j + 1)] = paramArrayOfString2[j];
  }

  // ERROR //
  protected java.util.ArrayList<Route> a(java.io.InputStream paramInputStream)
    throws com.amap.mapapi.core.AMapException
  {
    // Byte code:
    //   0: new 153	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 154	java/util/ArrayList:<init>	()V
    //   7: astore_2
    //   8: new 114	java/lang/String
    //   11: dup
    //   12: aload_1
    //   13: invokestatic 159	com/amap/mapapi/map/i:a	(Ljava/io/InputStream;)[B
    //   16: invokespecial 162	java/lang/String:<init>	([B)V
    //   19: astore_3
    //   20: aload_3
    //   21: invokestatic 165	com/amap/mapapi/core/e:c	(Ljava/lang/String;)V
    //   24: new 167	org/json/JSONObject
    //   27: dup
    //   28: aload_3
    //   29: invokespecial 169	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   32: ldc 171
    //   34: invokevirtual 175	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   37: astore 5
    //   39: iconst_0
    //   40: istore 6
    //   42: iload 6
    //   44: aload 5
    //   46: invokevirtual 180	org/json/JSONArray:length	()I
    //   49: if_icmpge +314 -> 363
    //   52: aload 5
    //   54: iload 6
    //   56: invokevirtual 184	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   59: ldc 186
    //   61: invokevirtual 175	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   64: astore 7
    //   66: new 188	com/amap/mapapi/route/Route
    //   69: dup
    //   70: aload_0
    //   71: getfield 192	com/amap/mapapi/route/a:b	Ljava/lang/Object;
    //   74: checkcast 194	com/amap/mapapi/route/f
    //   77: getfield 197	com/amap/mapapi/route/f:b	I
    //   80: invokespecial 199	com/amap/mapapi/route/Route:<init>	(I)V
    //   83: astore 8
    //   85: new 52	java/util/LinkedList
    //   88: dup
    //   89: invokespecial 200	java/util/LinkedList:<init>	()V
    //   92: astore 9
    //   94: iconst_0
    //   95: istore 10
    //   97: iload 10
    //   99: aload 7
    //   101: invokevirtual 180	org/json/JSONArray:length	()I
    //   104: if_icmpge +271 -> 375
    //   107: aload 7
    //   109: iload 10
    //   111: invokevirtual 184	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   114: astore 13
    //   116: new 98	com/amap/mapapi/route/BusSegment
    //   119: dup
    //   120: invokespecial 201	com/amap/mapapi/route/BusSegment:<init>	()V
    //   123: astore 14
    //   125: aload 13
    //   127: ldc 203
    //   129: invokevirtual 207	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   132: astore 15
    //   134: aload 13
    //   136: ldc 209
    //   138: invokevirtual 207	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   141: astore 16
    //   143: aload_0
    //   144: aload 14
    //   146: aload 13
    //   148: ldc 211
    //   150: invokevirtual 207	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   153: invokespecial 213	com/amap/mapapi/route/a:a	(Lcom/amap/mapapi/route/BusSegment;Ljava/lang/String;)V
    //   156: aload 14
    //   158: aload 13
    //   160: ldc 215
    //   162: invokevirtual 218	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   165: invokevirtual 219	com/amap/mapapi/route/BusSegment:setLength	(I)V
    //   168: aload 13
    //   170: ldc 221
    //   172: invokevirtual 218	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   175: istore 17
    //   177: iload 17
    //   179: iconst_2
    //   180: iadd
    //   181: anewarray 114	java/lang/String
    //   184: astore 18
    //   186: iload 17
    //   188: iconst_2
    //   189: iadd
    //   190: anewarray 20	com/amap/mapapi/core/GeoPoint
    //   193: astore 19
    //   195: aload 13
    //   197: ldc 223
    //   199: invokevirtual 207	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   202: ldc 225
    //   204: invokevirtual 133	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   207: astore 20
    //   209: aload 13
    //   211: ldc 227
    //   213: invokevirtual 207	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   216: ldc 229
    //   218: invokevirtual 133	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   221: astore 21
    //   223: aload 13
    //   225: ldc 231
    //   227: invokevirtual 207	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   230: ldc 229
    //   232: invokevirtual 133	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   235: astore 22
    //   237: aload_0
    //   238: aload 18
    //   240: aload 20
    //   242: invokespecial 233	com/amap/mapapi/route/a:a	([Ljava/lang/String;[Ljava/lang/String;)V
    //   245: aload_0
    //   246: aload 19
    //   248: aload 21
    //   250: invokespecial 235	com/amap/mapapi/route/a:a	([Lcom/amap/mapapi/core/GeoPoint;[Ljava/lang/String;)V
    //   253: aload 14
    //   255: aload_0
    //   256: aload 22
    //   258: invokevirtual 238	com/amap/mapapi/route/a:a	([Ljava/lang/String;)[Lcom/amap/mapapi/core/GeoPoint;
    //   261: invokevirtual 239	com/amap/mapapi/route/BusSegment:setShapes	([Lcom/amap/mapapi/core/GeoPoint;)V
    //   264: aload 19
    //   266: iconst_0
    //   267: aload 14
    //   269: invokevirtual 240	com/amap/mapapi/route/BusSegment:getFirstPoint	()Lcom/amap/mapapi/core/GeoPoint;
    //   272: invokevirtual 243	com/amap/mapapi/core/GeoPoint:e	()Lcom/amap/mapapi/core/GeoPoint;
    //   275: aastore
    //   276: aload 19
    //   278: iload 17
    //   280: iconst_1
    //   281: iadd
    //   282: aload 14
    //   284: invokevirtual 244	com/amap/mapapi/route/BusSegment:getLastPoint	()Lcom/amap/mapapi/core/GeoPoint;
    //   287: invokevirtual 243	com/amap/mapapi/core/GeoPoint:e	()Lcom/amap/mapapi/core/GeoPoint;
    //   290: aastore
    //   291: aload 18
    //   293: iconst_0
    //   294: aload 15
    //   296: aastore
    //   297: aload 18
    //   299: iload 17
    //   301: iconst_1
    //   302: iadd
    //   303: aload 16
    //   305: aastore
    //   306: aload 14
    //   308: aload 19
    //   310: invokevirtual 247	com/amap/mapapi/route/BusSegment:setPassStopPos	([Lcom/amap/mapapi/core/GeoPoint;)V
    //   313: aload 14
    //   315: aload 18
    //   317: invokevirtual 251	com/amap/mapapi/route/BusSegment:setPassStopName	([Ljava/lang/String;)V
    //   320: aload 9
    //   322: aload 14
    //   324: invokevirtual 254	java/util/LinkedList:add	(Ljava/lang/Object;)Z
    //   327: pop
    //   328: iinc 10 1
    //   331: goto -234 -> 97
    //   334: astore 26
    //   336: aload 26
    //   338: invokevirtual 257	java/lang/Exception:printStackTrace	()V
    //   341: aconst_null
    //   342: astore_3
    //   343: goto -323 -> 20
    //   346: astore 23
    //   348: aload 23
    //   350: invokevirtual 257	java/lang/Exception:printStackTrace	()V
    //   353: goto -100 -> 253
    //   356: astore 4
    //   358: aload 4
    //   360: invokevirtual 258	org/json/JSONException:printStackTrace	()V
    //   363: aload_2
    //   364: areturn
    //   365: astore 24
    //   367: aload 24
    //   369: invokevirtual 257	java/lang/Exception:printStackTrace	()V
    //   372: goto -108 -> 264
    //   375: aload 8
    //   377: aload 9
    //   379: invokevirtual 261	com/amap/mapapi/route/Route:a	(Ljava/util/List;)V
    //   382: aload_0
    //   383: aload 8
    //   385: invokevirtual 264	com/amap/mapapi/route/a:a	(Lcom/amap/mapapi/route/Route;)V
    //   388: aload 8
    //   390: invokevirtual 267	com/amap/mapapi/route/Route:a	()Ljava/util/List;
    //   393: invokeinterface 273 1 0
    //   398: astore 11
    //   400: aload 11
    //   402: invokeinterface 279 1 0
    //   407: ifeq +21 -> 428
    //   410: aload 11
    //   412: invokeinterface 282 1 0
    //   417: checkcast 15	com/amap/mapapi/route/Segment
    //   420: aload 8
    //   422: invokevirtual 285	com/amap/mapapi/route/Segment:setRoute	(Lcom/amap/mapapi/route/Route;)V
    //   425: goto -25 -> 400
    //   428: aload 8
    //   430: ifnull +28 -> 458
    //   433: aload 8
    //   435: aload_0
    //   436: getfield 289	com/amap/mapapi/route/a:j	Ljava/lang/String;
    //   439: invokevirtual 292	com/amap/mapapi/route/Route:setStartPlace	(Ljava/lang/String;)V
    //   442: aload 8
    //   444: aload_0
    //   445: getfield 295	com/amap/mapapi/route/a:k	Ljava/lang/String;
    //   448: invokevirtual 298	com/amap/mapapi/route/Route:setTargetPlace	(Ljava/lang/String;)V
    //   451: aload_2
    //   452: aload 8
    //   454: invokevirtual 299	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   457: pop
    //   458: iinc 6 1
    //   461: goto -419 -> 42
    //
    // Exception table:
    //   from	to	target	type
    //   8	20	334	java/lang/Exception
    //   245	253	346	java/lang/Exception
    //   24	39	356	org/json/JSONException
    //   42	94	356	org/json/JSONException
    //   97	245	356	org/json/JSONException
    //   245	253	356	org/json/JSONException
    //   253	264	356	org/json/JSONException
    //   264	328	356	org/json/JSONException
    //   348	353	356	org/json/JSONException
    //   367	372	356	org/json/JSONException
    //   375	400	356	org/json/JSONException
    //   400	425	356	org/json/JSONException
    //   433	458	356	org/json/JSONException
    //   253	264	365	java/lang/Exception
  }

  protected void a(Route paramRoute)
  {
    paramRoute.a(a((LinkedList)paramRoute.a()));
  }

  public void a(List<GeoPoint> paramList)
  {
    this.i = paramList;
  }

  protected byte[] d()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("sid=8001");
    localStringBuilder.append("&encode=utf-8");
    localStringBuilder.append("&xys=" + ((f)this.b).a() + "," + ((f)this.b).c() + ";");
    if ((this.i != null) && (this.i.size() > 0))
    {
      int j = this.i.size();
      String str = "";
      for (int k = 0; k < j; k++)
      {
        GeoPoint localGeoPoint = (GeoPoint)this.i.get(k);
        str = str + localGeoPoint.getLongitudeE6() / 1000000.0D + "," + localGeoPoint.getLatitudeE6() / 1000000.0D + ";";
      }
      localStringBuilder.append(str);
    }
    localStringBuilder.append(((f)this.b).b() + "," + ((f)this.b).d());
    localStringBuilder.append("&resType=json");
    localStringBuilder.append("&RouteType=" + ((f)this.b).b);
    localStringBuilder.append("&per=");
    localStringBuilder.append(50);
    return localStringBuilder.toString().getBytes();
  }

  protected String e()
  {
    return j.a().d() + "/bus/simple";
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.route.a
 * JD-Core Version:    0.6.0
 */