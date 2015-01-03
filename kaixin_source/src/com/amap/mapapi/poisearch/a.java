package com.amap.mapapi.poisearch;

import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.PoiItem;
import com.amap.mapapi.core.e;
import com.amap.mapapi.core.j;
import com.amap.mapapi.core.m;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class a extends m<b, ArrayList<PoiItem>>
{
  private int i = 1;
  private int j = 20;
  private int k = 0;
  private ArrayList<String> l = new ArrayList();

  public a(b paramb, Proxy paramProxy, String paramString1, String paramString2)
  {
    super(paramb, paramProxy, paramString1, paramString2);
  }

  private boolean a(String paramString)
  {
    return (paramString == null) || (paramString.equals(""));
  }

  public int a()
  {
    return this.j;
  }

  // ERROR //
  public ArrayList<PoiItem> a(java.io.InputStream paramInputStream)
    throws com.amap.mapapi.core.AMapException
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_2
    //   2: new 25	java/util/ArrayList
    //   5: dup
    //   6: invokespecial 28	java/util/ArrayList:<init>	()V
    //   9: astore_3
    //   10: new 36	java/lang/String
    //   13: dup
    //   14: aload_1
    //   15: invokestatic 53	com/amap/mapapi/map/i:a	(Ljava/io/InputStream;)[B
    //   18: invokespecial 56	java/lang/String:<init>	([B)V
    //   21: astore 4
    //   23: aload 4
    //   25: invokestatic 62	com/amap/mapapi/core/e:c	(Ljava/lang/String;)V
    //   28: new 64	org/json/JSONObject
    //   31: dup
    //   32: aload 4
    //   34: invokespecial 66	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   37: astore 5
    //   39: aload 5
    //   41: ldc 68
    //   43: invokevirtual 71	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   46: ifeq +318 -> 364
    //   49: aload 5
    //   51: ldc 68
    //   53: invokevirtual 75	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   56: astore 8
    //   58: aload_0
    //   59: aload 5
    //   61: ldc 77
    //   63: invokevirtual 81	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   66: putfield 23	com/amap/mapapi/poisearch/a:k	I
    //   69: iload_2
    //   70: aload 8
    //   72: invokevirtual 86	org/json/JSONArray:length	()I
    //   75: if_icmpge +308 -> 383
    //   78: aload 8
    //   80: iload_2
    //   81: invokevirtual 90	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   84: astore 9
    //   86: aload 9
    //   88: ldc 92
    //   90: invokevirtual 96	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   93: astore 10
    //   95: aload 9
    //   97: ldc 98
    //   99: invokevirtual 102	org/json/JSONObject:getDouble	(Ljava/lang/String;)D
    //   102: dstore 11
    //   104: aload 9
    //   106: ldc 104
    //   108: invokevirtual 102	org/json/JSONObject:getDouble	(Ljava/lang/String;)D
    //   111: dstore 13
    //   113: aload 9
    //   115: ldc 106
    //   117: invokevirtual 96	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   120: astore 15
    //   122: aload 9
    //   124: ldc 108
    //   126: invokevirtual 96	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   129: astore 16
    //   131: new 110	com/amap/mapapi/core/PoiItem
    //   134: dup
    //   135: aload 10
    //   137: new 112	com/amap/mapapi/core/GeoPoint
    //   140: dup
    //   141: dload 13
    //   143: invokestatic 115	com/amap/mapapi/core/e:a	(D)J
    //   146: dload 11
    //   148: invokestatic 115	com/amap/mapapi/core/e:a	(D)J
    //   151: invokespecial 118	com/amap/mapapi/core/GeoPoint:<init>	(JJ)V
    //   154: aload 15
    //   156: aload 16
    //   158: invokespecial 121	com/amap/mapapi/core/PoiItem:<init>	(Ljava/lang/String;Lcom/amap/mapapi/core/GeoPoint;Ljava/lang/String;Ljava/lang/String;)V
    //   161: astore 17
    //   163: aload 9
    //   165: ldc 123
    //   167: invokevirtual 71	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   170: ifeq +15 -> 185
    //   173: aload 17
    //   175: aload 9
    //   177: ldc 123
    //   179: invokevirtual 96	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   182: invokevirtual 126	com/amap/mapapi/core/PoiItem:setAdCode	(Ljava/lang/String;)V
    //   185: aload 9
    //   187: ldc 128
    //   189: invokevirtual 71	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   192: ifeq +32 -> 224
    //   195: aload 17
    //   197: aload 9
    //   199: ldc 128
    //   201: invokevirtual 96	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   204: invokestatic 133	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   207: invokevirtual 137	com/amap/mapapi/core/PoiItem:setDistance	(I)V
    //   210: aload 17
    //   212: invokevirtual 140	com/amap/mapapi/core/PoiItem:getDistance	()I
    //   215: ifne +9 -> 224
    //   218: aload 17
    //   220: iconst_m1
    //   221: invokevirtual 137	com/amap/mapapi/core/PoiItem:setDistance	(I)V
    //   224: aload 17
    //   226: aload 9
    //   228: ldc 142
    //   230: invokevirtual 96	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   233: invokevirtual 145	com/amap/mapapi/core/PoiItem:setTel	(Ljava/lang/String;)V
    //   236: ldc 34
    //   238: astore 18
    //   240: aload 9
    //   242: ldc 147
    //   244: invokevirtual 71	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   247: ifeq +12 -> 259
    //   250: aload 9
    //   252: ldc 147
    //   254: invokevirtual 96	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   257: astore 18
    //   259: aload 18
    //   261: invokevirtual 148	java/lang/String:length	()I
    //   264: iconst_4
    //   265: if_icmple +101 -> 366
    //   268: aload 17
    //   270: aload 18
    //   272: iconst_0
    //   273: iconst_4
    //   274: invokevirtual 152	java/lang/String:substring	(II)Ljava/lang/String;
    //   277: invokevirtual 155	com/amap/mapapi/core/PoiItem:setTypeCode	(Ljava/lang/String;)V
    //   280: aload 9
    //   282: ldc 157
    //   284: invokevirtual 96	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   287: ldc 159
    //   289: invokevirtual 163	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   292: astore 19
    //   294: aload 19
    //   296: iconst_0
    //   297: aaload
    //   298: astore 20
    //   300: iconst_1
    //   301: istore 21
    //   303: iload 21
    //   305: aload 19
    //   307: arraylength
    //   308: if_icmpge +77 -> 385
    //   311: new 165	java/lang/StringBuilder
    //   314: dup
    //   315: invokespecial 166	java/lang/StringBuilder:<init>	()V
    //   318: aload 20
    //   320: invokevirtual 170	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   323: ldc 172
    //   325: invokevirtual 170	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   328: aload 19
    //   330: iload 21
    //   332: aaload
    //   333: invokevirtual 170	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   336: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   339: astore 23
    //   341: aload 23
    //   343: astore 20
    //   345: iinc 21 1
    //   348: goto -45 -> 303
    //   351: astore 24
    //   353: aload 24
    //   355: invokevirtual 179	java/lang/Exception:printStackTrace	()V
    //   358: aconst_null
    //   359: astore 4
    //   361: goto -338 -> 23
    //   364: aload_3
    //   365: areturn
    //   366: aload 17
    //   368: ldc 34
    //   370: invokevirtual 155	com/amap/mapapi/core/PoiItem:setTypeCode	(Ljava/lang/String;)V
    //   373: goto -93 -> 280
    //   376: astore 7
    //   378: aload 7
    //   380: invokevirtual 180	org/json/JSONException:printStackTrace	()V
    //   383: aload_3
    //   384: areturn
    //   385: aload 17
    //   387: aload 20
    //   389: invokevirtual 183	com/amap/mapapi/core/PoiItem:setTypeDes	(Ljava/lang/String;)V
    //   392: aload 17
    //   394: aload 9
    //   396: ldc 185
    //   398: invokevirtual 96	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   401: invokevirtual 188	com/amap/mapapi/core/PoiItem:setXmlNode	(Ljava/lang/String;)V
    //   404: aload_3
    //   405: aload 17
    //   407: invokevirtual 191	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   410: pop
    //   411: iinc 2 1
    //   414: goto -345 -> 69
    //   417: astore 6
    //   419: aload 6
    //   421: invokevirtual 179	java/lang/Exception:printStackTrace	()V
    //   424: goto -41 -> 383
    //
    // Exception table:
    //   from	to	target	type
    //   10	23	351	java/lang/Exception
    //   28	69	376	org/json/JSONException
    //   69	185	376	org/json/JSONException
    //   185	224	376	org/json/JSONException
    //   224	236	376	org/json/JSONException
    //   240	259	376	org/json/JSONException
    //   259	280	376	org/json/JSONException
    //   280	300	376	org/json/JSONException
    //   303	341	376	org/json/JSONException
    //   366	373	376	org/json/JSONException
    //   385	411	376	org/json/JSONException
    //   28	69	417	java/lang/Exception
    //   69	185	417	java/lang/Exception
    //   185	224	417	java/lang/Exception
    //   224	236	417	java/lang/Exception
    //   240	259	417	java/lang/Exception
    //   259	280	417	java/lang/Exception
    //   280	300	417	java/lang/Exception
    //   303	341	417	java/lang/Exception
    //   366	373	417	java/lang/Exception
    //   385	411	417	java/lang/Exception
  }

  public void a(int paramInt)
  {
    this.i = paramInt;
  }

  public int b()
  {
    return this.k;
  }

  public void b(int paramInt)
  {
    int m = 20;
    if (paramInt > m);
    for (int n = m; ; n = paramInt)
    {
      if (n <= 0);
      while (true)
      {
        this.j = m;
        return;
        m = n;
      }
    }
  }

  public PoiSearch.Query c()
  {
    return ((b)this.b).a;
  }

  protected byte[] d()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Object localObject3;
    if (((b)this.b).b == null)
    {
      localStringBuilder.append("sid=1000&city=");
      localObject3 = ((b)this.b).a.getCity();
      if (a((String)localObject3))
        localStringBuilder.append("total");
    }
    while (true)
    {
      Object localObject1 = ((b)this.b).a.getQueryString();
      try
      {
        String str2 = URLEncoder.encode((String)localObject1, "utf-8");
        localObject1 = str2;
        localStringBuilder.append("&keyword=" + (String)localObject1);
        localStringBuilder.append("&number=" + this.j);
        localStringBuilder.append("&batch=" + this.i);
        localObject2 = ((b)this.b).a.getCategory();
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException1)
      {
        try
        {
          String str1 = URLEncoder.encode((String)localObject2, "utf-8");
          Object localObject2 = str1;
          localStringBuilder.append("&type=" + (String)localObject2);
          localStringBuilder.append("&resType=json&encode=utf-8");
          return localStringBuilder.toString().getBytes();
          try
          {
            String str3 = URLEncoder.encode((String)localObject3, "utf-8");
            localObject3 = str3;
            localStringBuilder.append((String)localObject3);
          }
          catch (UnsupportedEncodingException localUnsupportedEncodingException3)
          {
            while (true)
              localUnsupportedEncodingException3.printStackTrace();
          }
          if (((b)this.b).b.getShape().equals("bound"))
          {
            localStringBuilder.append("sid=1002&city=total");
            localStringBuilder.append("&cenX=");
            localStringBuilder.append(1.0F * (float)((b)this.b).b.getCenter().a() / 1000000.0F);
            localStringBuilder.append("&cenY=");
            localStringBuilder.append(1.0F * (float)((b)this.b).b.getCenter().b() / 1000000.0F);
            localStringBuilder.append("&range=");
            localStringBuilder.append(((b)this.b).b.getRange());
            continue;
          }
          if (!((b)this.b).b.getShape().equals("Rectangle"))
            continue;
          localStringBuilder.append("sid=1005");
          GeoPoint localGeoPoint1 = ((b)this.b).b.getLowerLeft();
          GeoPoint localGeoPoint2 = ((b)this.b).b.getUpperRight();
          double d1 = e.a(localGeoPoint1.b());
          double d2 = e.a(localGeoPoint1.a());
          double d3 = e.a(localGeoPoint2.b());
          double d4 = e.a(localGeoPoint2.a());
          localStringBuilder.append("&regionType=rectangle");
          localStringBuilder.append("&region=" + d2 + "," + d1 + ";" + d4 + "," + d3);
          continue;
          localUnsupportedEncodingException1 = localUnsupportedEncodingException1;
          localUnsupportedEncodingException1.printStackTrace();
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException2)
        {
          while (true)
            localUnsupportedEncodingException2.printStackTrace();
        }
      }
    }
  }

  protected String e()
  {
    return j.a().d() + "/gss/simple?";
  }

  public PoiSearch.SearchBound i()
  {
    return ((b)this.b).b;
  }

  public List<String> j()
  {
    return this.l;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.poisearch.a
 * JD-Core Version:    0.6.0
 */