package com.amap.mapapi.busline;

import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.j;
import com.amap.mapapi.core.m;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URLEncoder;
import java.util.ArrayList;

public class a extends m<BusQuery, ArrayList<BusLineItem>>
{
  private int i = 1;
  private int j = 10;
  private int k = 0;

  public a(BusQuery paramBusQuery, Proxy paramProxy, String paramString1, String paramString2)
  {
    super(paramBusQuery, paramProxy, paramString1, paramString2);
  }

  private ArrayList<GeoPoint> a(String paramString)
  {
    String[] arrayOfString = paramString.split(",|;");
    ArrayList localArrayList = new ArrayList();
    for (int m = 0; m < -1 + arrayOfString.length; m += 2)
      localArrayList.add(new GeoPoint((int)(1000000.0D * Double.parseDouble(arrayOfString[(m + 1)])), (int)(1000000.0D * Double.parseDouble(arrayOfString[m]))));
    return localArrayList;
  }

  private boolean b(String paramString)
  {
    return (paramString == null) || (paramString.equals(""));
  }

  public int a()
  {
    return this.j;
  }

  // ERROR //
  protected ArrayList<BusLineItem> a(java.io.InputStream paramInputStream)
    throws com.amap.mapapi.core.AMapException
  {
    // Byte code:
    //   0: new 32	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 35	java/util/ArrayList:<init>	()V
    //   7: astore_2
    //   8: new 26	java/lang/String
    //   11: dup
    //   12: aload_1
    //   13: invokestatic 72	com/amap/mapapi/map/i:a	(Ljava/io/InputStream;)[B
    //   16: invokespecial 75	java/lang/String:<init>	([B)V
    //   19: astore_3
    //   20: aload_3
    //   21: invokestatic 81	com/amap/mapapi/core/e:c	(Ljava/lang/String;)V
    //   24: aload_3
    //   25: invokestatic 81	com/amap/mapapi/core/e:c	(Ljava/lang/String;)V
    //   28: new 83	org/json/JSONObject
    //   31: dup
    //   32: aload_3
    //   33: invokespecial 85	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   36: astore 5
    //   38: aload_0
    //   39: aload 5
    //   41: ldc 87
    //   43: invokevirtual 91	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   46: putfield 20	com/amap/mapapi/busline/a:k	I
    //   49: aload 5
    //   51: ldc 93
    //   53: invokevirtual 97	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   56: astore 7
    //   58: iconst_0
    //   59: istore 8
    //   61: iload 8
    //   63: aload 7
    //   65: invokevirtual 102	org/json/JSONArray:length	()I
    //   68: if_icmpge +578 -> 646
    //   71: aload 7
    //   73: iload 8
    //   75: invokevirtual 106	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   78: astore 9
    //   80: new 108	com/amap/mapapi/busline/BusLineItem
    //   83: dup
    //   84: invokespecial 109	com/amap/mapapi/busline/BusLineItem:<init>	()V
    //   87: astore 10
    //   89: aload 10
    //   91: aload 9
    //   93: ldc 110
    //   95: invokevirtual 113	org/json/JSONObject:getDouble	(Ljava/lang/String;)D
    //   98: d2f
    //   99: invokevirtual 117	com/amap/mapapi/busline/BusLineItem:setmLength	(F)V
    //   102: aload 10
    //   104: aload 9
    //   106: ldc 119
    //   108: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   111: invokevirtual 126	com/amap/mapapi/busline/BusLineItem:setmName	(Ljava/lang/String;)V
    //   114: aload 10
    //   116: aload 9
    //   118: ldc 128
    //   120: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   123: invokevirtual 131	com/amap/mapapi/busline/BusLineItem:setmDescription	(Ljava/lang/String;)V
    //   126: aload 10
    //   128: aload 9
    //   130: ldc 133
    //   132: invokevirtual 91	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   135: invokevirtual 137	com/amap/mapapi/busline/BusLineItem:setmStatus	(I)V
    //   138: aload 9
    //   140: ldc 139
    //   142: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   145: ldc 56
    //   147: invokevirtual 59	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   150: ifne +16 -> 166
    //   153: aload 10
    //   155: aload 9
    //   157: ldc 139
    //   159: invokevirtual 113	org/json/JSONObject:getDouble	(Ljava/lang/String;)D
    //   162: d2f
    //   163: invokevirtual 142	com/amap/mapapi/busline/BusLineItem:setmSpeed	(F)V
    //   166: aload 10
    //   168: aload_0
    //   169: aload 9
    //   171: ldc 144
    //   173: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   176: invokespecial 146	com/amap/mapapi/busline/a:a	(Ljava/lang/String;)Ljava/util/ArrayList;
    //   179: invokevirtual 150	com/amap/mapapi/busline/BusLineItem:setmXys	(Ljava/util/ArrayList;)V
    //   182: aload 10
    //   184: aload 9
    //   186: ldc 152
    //   188: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   191: invokevirtual 155	com/amap/mapapi/busline/BusLineItem:setmLineId	(Ljava/lang/String;)V
    //   194: aload 10
    //   196: aload 9
    //   198: ldc 157
    //   200: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   203: invokevirtual 160	com/amap/mapapi/busline/BusLineItem:setmKeyName	(Ljava/lang/String;)V
    //   206: aload 10
    //   208: aload 9
    //   210: ldc 162
    //   212: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   215: invokevirtual 165	com/amap/mapapi/busline/BusLineItem:setmFrontName	(Ljava/lang/String;)V
    //   218: aload 10
    //   220: aload 9
    //   222: ldc 167
    //   224: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   227: invokevirtual 170	com/amap/mapapi/busline/BusLineItem:setmTerminalName	(Ljava/lang/String;)V
    //   230: aload 10
    //   232: aload 9
    //   234: ldc 172
    //   236: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   239: invokevirtual 175	com/amap/mapapi/busline/BusLineItem:setmStartTime	(Ljava/lang/String;)V
    //   242: aload 10
    //   244: aload 9
    //   246: ldc 177
    //   248: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   251: invokevirtual 180	com/amap/mapapi/busline/BusLineItem:setmEndTime	(Ljava/lang/String;)V
    //   254: aload 10
    //   256: aload 9
    //   258: ldc 182
    //   260: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   263: invokevirtual 185	com/amap/mapapi/busline/BusLineItem:setmCompany	(Ljava/lang/String;)V
    //   266: aload 10
    //   268: aload 9
    //   270: ldc 187
    //   272: invokevirtual 113	org/json/JSONObject:getDouble	(Ljava/lang/String;)D
    //   275: d2f
    //   276: invokevirtual 190	com/amap/mapapi/busline/BusLineItem:setmBasicPrice	(F)V
    //   279: aload 10
    //   281: aload 9
    //   283: ldc 192
    //   285: invokevirtual 113	org/json/JSONObject:getDouble	(Ljava/lang/String;)D
    //   288: d2f
    //   289: invokevirtual 195	com/amap/mapapi/busline/BusLineItem:setmTotalPrice	(F)V
    //   292: aload 10
    //   294: aload 9
    //   296: ldc 197
    //   298: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   301: ldc 199
    //   303: invokevirtual 59	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   306: invokevirtual 203	com/amap/mapapi/busline/BusLineItem:setmCommunicationTicket	(Z)V
    //   309: aload 10
    //   311: aload 9
    //   313: ldc 205
    //   315: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   318: ldc 199
    //   320: invokevirtual 59	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   323: invokevirtual 208	com/amap/mapapi/busline/BusLineItem:setmAuto	(Z)V
    //   326: aload 10
    //   328: aload 9
    //   330: ldc 210
    //   332: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   335: ldc 199
    //   337: invokevirtual 59	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   340: invokevirtual 213	com/amap/mapapi/busline/BusLineItem:setmIcCard	(Z)V
    //   343: aload 10
    //   345: aload 9
    //   347: ldc 215
    //   349: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   352: ldc 199
    //   354: invokevirtual 59	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   357: invokevirtual 218	com/amap/mapapi/busline/BusLineItem:setmLoop	(Z)V
    //   360: aload 10
    //   362: aload 9
    //   364: ldc 220
    //   366: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   369: ldc 199
    //   371: invokevirtual 59	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   374: invokevirtual 223	com/amap/mapapi/busline/BusLineItem:setmDoubleDeck	(Z)V
    //   377: aload 10
    //   379: aload 9
    //   381: ldc 225
    //   383: invokevirtual 91	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   386: invokevirtual 228	com/amap/mapapi/busline/BusLineItem:setmDataSource	(I)V
    //   389: aload 10
    //   391: aload 9
    //   393: ldc 230
    //   395: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   398: ldc 199
    //   400: invokevirtual 59	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   403: invokevirtual 233	com/amap/mapapi/busline/BusLineItem:setmAir	(Z)V
    //   406: aload 10
    //   408: aload 9
    //   410: ldc 235
    //   412: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   415: invokevirtual 238	com/amap/mapapi/busline/BusLineItem:setmFrontSpell	(Ljava/lang/String;)V
    //   418: aload 10
    //   420: aload 9
    //   422: ldc 240
    //   424: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   427: invokevirtual 243	com/amap/mapapi/busline/BusLineItem:setmTerminalSpell	(Ljava/lang/String;)V
    //   430: aload 10
    //   432: aload 9
    //   434: ldc 245
    //   436: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   439: ldc 199
    //   441: invokevirtual 59	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   444: invokevirtual 248	com/amap/mapapi/busline/BusLineItem:setmExpressWay	(Z)V
    //   447: aload 9
    //   449: ldc 250
    //   451: invokevirtual 97	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   454: astore 11
    //   456: aload 11
    //   458: invokevirtual 102	org/json/JSONArray:length	()I
    //   461: istore 12
    //   463: new 32	java/util/ArrayList
    //   466: dup
    //   467: invokespecial 35	java/util/ArrayList:<init>	()V
    //   470: astore 13
    //   472: iconst_0
    //   473: istore 14
    //   475: iload 14
    //   477: iload 12
    //   479: if_icmpge +140 -> 619
    //   482: new 252	com/amap/mapapi/busline/BusStationItem
    //   485: dup
    //   486: invokespecial 253	com/amap/mapapi/busline/BusStationItem:<init>	()V
    //   489: astore 15
    //   491: aload 11
    //   493: iload 14
    //   495: invokevirtual 106	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   498: astore 16
    //   500: aload 15
    //   502: aload 16
    //   504: ldc 255
    //   506: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   509: invokevirtual 258	com/amap/mapapi/busline/BusStationItem:setmCode	(Ljava/lang/String;)V
    //   512: aload 16
    //   514: ldc_w 260
    //   517: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   520: ldc_w 262
    //   523: invokevirtual 30	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   526: astore 17
    //   528: aload 15
    //   530: new 37	com/amap/mapapi/core/GeoPoint
    //   533: dup
    //   534: ldc2_w 38
    //   537: aload 17
    //   539: iconst_1
    //   540: aaload
    //   541: invokestatic 45	java/lang/Double:parseDouble	(Ljava/lang/String;)D
    //   544: dmul
    //   545: d2i
    //   546: ldc2_w 38
    //   549: aload 17
    //   551: iconst_0
    //   552: aaload
    //   553: invokestatic 45	java/lang/Double:parseDouble	(Ljava/lang/String;)D
    //   556: dmul
    //   557: d2i
    //   558: invokespecial 48	com/amap/mapapi/core/GeoPoint:<init>	(II)V
    //   561: invokevirtual 266	com/amap/mapapi/busline/BusStationItem:setmCoord	(Lcom/amap/mapapi/core/GeoPoint;)V
    //   564: aload 15
    //   566: aload 16
    //   568: ldc 119
    //   570: invokevirtual 123	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   573: invokevirtual 267	com/amap/mapapi/busline/BusStationItem:setmName	(Ljava/lang/String;)V
    //   576: aload 15
    //   578: aload 16
    //   580: ldc_w 269
    //   583: invokevirtual 91	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   586: invokevirtual 272	com/amap/mapapi/busline/BusStationItem:setmStationNum	(I)V
    //   589: aload 13
    //   591: aload 15
    //   593: invokevirtual 52	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   596: pop
    //   597: iinc 14 1
    //   600: goto -125 -> 475
    //   603: astore 20
    //   605: aconst_null
    //   606: astore_3
    //   607: aload 20
    //   609: astore 4
    //   611: aload 4
    //   613: invokevirtual 275	java/lang/Exception:printStackTrace	()V
    //   616: goto -592 -> 24
    //   619: aload 10
    //   621: aload 13
    //   623: invokevirtual 278	com/amap/mapapi/busline/BusLineItem:setmStations	(Ljava/util/ArrayList;)V
    //   626: aload_2
    //   627: aload 10
    //   629: invokevirtual 52	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   632: pop
    //   633: iinc 8 1
    //   636: goto -575 -> 61
    //   639: astore 6
    //   641: aload 6
    //   643: invokevirtual 279	org/json/JSONException:printStackTrace	()V
    //   646: aload_2
    //   647: areturn
    //   648: astore 4
    //   650: goto -39 -> 611
    //
    // Exception table:
    //   from	to	target	type
    //   8	20	603	java/lang/Exception
    //   28	58	639	org/json/JSONException
    //   61	166	639	org/json/JSONException
    //   166	472	639	org/json/JSONException
    //   482	597	639	org/json/JSONException
    //   619	633	639	org/json/JSONException
    //   20	24	648	java/lang/Exception
  }

  public void a(int paramInt)
  {
    this.i = paramInt;
  }

  public BusQuery b()
  {
    return (BusQuery)this.b;
  }

  public void b(int paramInt)
  {
    int m = 20;
    if (paramInt > m);
    while (true)
    {
      if (m <= 0)
        m = 10;
      this.j = m;
      return;
      m = paramInt;
    }
  }

  public int c()
  {
    return this.k;
  }

  protected byte[] d()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("encode=utf-8");
    localStringBuilder.append("&resType=json&city=");
    String str1 = ((BusQuery)this.b).getCity();
    if (b(str1))
      localStringBuilder.append("total");
    while (true)
    {
      label85: Object localObject;
      if (((BusQuery)this.b).getCategory() == BusQuery.SearchType.BY_LINE_NAME)
      {
        localStringBuilder.append("&sid=8004");
        localStringBuilder.append("&busName=");
        localObject = ((BusQuery)this.b).getQueryString();
      }
      try
      {
        String str2 = URLEncoder.encode((String)localObject, "utf-8");
        localObject = str2;
        localStringBuilder.append((String)localObject);
        localStringBuilder.append("&number=");
        localStringBuilder.append(this.j);
        localStringBuilder.append("&batch=");
        localStringBuilder.append(this.i);
        return localStringBuilder.toString().getBytes();
        try
        {
          localStringBuilder.append(URLEncoder.encode(str1, "utf-8"));
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException1)
        {
          localUnsupportedEncodingException1.printStackTrace();
        }
        continue;
        if (((BusQuery)this.b).getCategory() == BusQuery.SearchType.BY_ID)
        {
          localStringBuilder.append("&sid=8085");
          localStringBuilder.append("&ids=");
          break label85;
        }
        if (((BusQuery)this.b).getCategory() != BusQuery.SearchType.BY_STATION_NAME)
          break label85;
        localStringBuilder.append("&sid=8086");
        localStringBuilder.append("&stationName=");
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException2)
      {
        while (true)
          localUnsupportedEncodingException2.printStackTrace();
      }
    }
  }

  protected String e()
  {
    return j.a().d() + "/bus/simple";
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.busline.a
 * JD-Core Version:    0.6.0
 */