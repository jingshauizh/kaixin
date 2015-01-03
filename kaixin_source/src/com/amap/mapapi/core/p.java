package com.amap.mapapi.core;

import android.location.Address;
import com.amap.mapapi.map.i;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class p extends m<q, ArrayList<Address>>
{
  private String i = null;
  private String j = null;
  private String k = null;
  private int l = 0;
  private ArrayList<Address> m;
  private ArrayList<Address> n;
  private ArrayList<Address> o;
  private boolean p;
  private boolean q;
  private boolean r;

  public p(q paramq, Proxy paramProxy, String paramString1, String paramString2)
  {
    super(paramq, paramProxy, paramString1, paramString2);
    this.l = paramq.c;
    this.m = new ArrayList();
    this.n = new ArrayList();
    this.o = new ArrayList();
  }

  private String a(JSONObject paramJSONObject)
    throws JSONException
  {
    return paramJSONObject.getString("name");
  }

  private void a(ArrayList<Address> paramArrayList1, ArrayList<Address> paramArrayList2)
  {
    int i1 = paramArrayList2.size();
    int i2 = this.l - paramArrayList1.size();
    for (int i3 = 0; i3 < i2; i3++)
    {
      if (i1 <= i3)
        continue;
      paramArrayList1.add(paramArrayList2.get(i3));
    }
  }

  // ERROR //
  private void a(JSONObject paramJSONObject, String paramString)
    throws Exception
  {
    // Byte code:
    //   0: aload_1
    //   1: aload_2
    //   2: invokevirtual 81	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   5: astore_3
    //   6: aload_2
    //   7: ldc 83
    //   9: invokevirtual 88	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   12: ifeq +455 -> 467
    //   15: iconst_0
    //   16: istore 23
    //   18: iload 23
    //   20: aload_3
    //   21: invokevirtual 93	org/json/JSONArray:length	()I
    //   24: if_icmpge +794 -> 818
    //   27: invokestatic 99	com/amap/mapapi/core/e:b	()Landroid/location/Address;
    //   30: astore 24
    //   32: aload_3
    //   33: iload 23
    //   35: invokevirtual 103	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   38: astore 25
    //   40: aload 24
    //   42: aload 25
    //   44: ldc 55
    //   46: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   49: invokevirtual 109	android/location/Address:setFeatureName	(Ljava/lang/String;)V
    //   52: aload 24
    //   54: aload 25
    //   56: ldc 111
    //   58: invokevirtual 115	org/json/JSONObject:getDouble	(Ljava/lang/String;)D
    //   61: invokevirtual 119	android/location/Address:setLatitude	(D)V
    //   64: aload 24
    //   66: aload 25
    //   68: ldc 121
    //   70: invokevirtual 115	org/json/JSONObject:getDouble	(Ljava/lang/String;)D
    //   73: invokevirtual 124	android/location/Address:setLongitude	(D)V
    //   76: aload 25
    //   78: ldc 126
    //   80: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   83: astore 26
    //   85: aload 26
    //   87: ifnull +20 -> 107
    //   90: aload 26
    //   92: ldc 128
    //   94: invokevirtual 88	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   97: ifne +10 -> 107
    //   100: aload 24
    //   102: aload 26
    //   104: invokevirtual 131	android/location/Address:setPhone	(Ljava/lang/String;)V
    //   107: aload 25
    //   109: ldc 133
    //   111: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   114: astore 27
    //   116: aload 27
    //   118: ifnull +164 -> 282
    //   121: aload 27
    //   123: ldc 128
    //   125: invokevirtual 88	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   128: ifne +154 -> 282
    //   131: aload 27
    //   133: iconst_m1
    //   134: aload 27
    //   136: invokevirtual 134	java/lang/String:length	()I
    //   139: iadd
    //   140: invokevirtual 138	java/lang/String:charAt	(I)C
    //   143: invokestatic 144	java/lang/Character:isDigit	(C)Z
    //   146: ifeq +25 -> 171
    //   149: new 146	java/lang/StringBuilder
    //   152: dup
    //   153: invokespecial 147	java/lang/StringBuilder:<init>	()V
    //   156: aload 27
    //   158: invokevirtual 151	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   161: ldc 153
    //   163: invokevirtual 151	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   166: invokevirtual 157	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   169: astore 27
    //   171: aload 24
    //   173: iconst_2
    //   174: aload 27
    //   176: invokevirtual 161	android/location/Address:setAddressLine	(ILjava/lang/String;)V
    //   179: aload 27
    //   181: ldc 162
    //   183: invokevirtual 166	java/lang/String:indexOf	(I)I
    //   186: istore 32
    //   188: iload 32
    //   190: iconst_m1
    //   191: if_icmpeq +160 -> 351
    //   194: aload 27
    //   196: iconst_0
    //   197: iload 32
    //   199: iconst_1
    //   200: iadd
    //   201: invokevirtual 170	java/lang/String:substring	(II)Ljava/lang/String;
    //   204: astore 38
    //   206: aload 27
    //   208: iload 32
    //   210: iconst_1
    //   211: iadd
    //   212: invokevirtual 173	java/lang/String:substring	(I)Ljava/lang/String;
    //   215: astore 37
    //   217: aload 24
    //   219: aload 38
    //   221: invokevirtual 176	android/location/Address:setThoroughfare	(Ljava/lang/String;)V
    //   224: aload 24
    //   226: invokevirtual 182	java/lang/Object:getClass	()Ljava/lang/Class;
    //   229: ldc 184
    //   231: iconst_1
    //   232: anewarray 186	java/lang/Class
    //   235: dup
    //   236: iconst_0
    //   237: ldc 85
    //   239: aastore
    //   240: invokevirtual 190	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   243: astore 40
    //   245: aload 40
    //   247: ifnull +35 -> 282
    //   250: aload 37
    //   252: ifnull +30 -> 282
    //   255: aload 37
    //   257: ldc 128
    //   259: invokevirtual 88	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   262: ifne +20 -> 282
    //   265: aload 40
    //   267: aload 24
    //   269: iconst_1
    //   270: anewarray 178	java/lang/Object
    //   273: dup
    //   274: iconst_0
    //   275: aload 37
    //   277: aastore
    //   278: invokevirtual 196	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   281: pop
    //   282: aload 24
    //   284: invokevirtual 182	java/lang/Object:getClass	()Ljava/lang/Class;
    //   287: ldc 198
    //   289: iconst_1
    //   290: anewarray 186	java/lang/Class
    //   293: dup
    //   294: iconst_0
    //   295: ldc 85
    //   297: aastore
    //   298: invokevirtual 190	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   301: astore 30
    //   303: aload 30
    //   305: ifnull +20 -> 325
    //   308: aload 30
    //   310: aload 24
    //   312: iconst_1
    //   313: anewarray 178	java/lang/Object
    //   316: dup
    //   317: iconst_0
    //   318: ldc 200
    //   320: aastore
    //   321: invokevirtual 196	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   324: pop
    //   325: aload 24
    //   327: ifnull +18 -> 345
    //   330: aload_0
    //   331: getfield 47	com/amap/mapapi/core/p:n	Ljava/util/ArrayList;
    //   334: aload 24
    //   336: invokevirtual 74	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   339: pop
    //   340: aload_0
    //   341: iconst_1
    //   342: putfield 202	com/amap/mapapi/core/p:q	Z
    //   345: iinc 23 1
    //   348: goto -330 -> 18
    //   351: aload 27
    //   353: ldc 203
    //   355: invokevirtual 166	java/lang/String:indexOf	(I)I
    //   358: istore 33
    //   360: iload 33
    //   362: iconst_m1
    //   363: if_icmpeq +29 -> 392
    //   366: aload 27
    //   368: iconst_0
    //   369: iload 33
    //   371: iconst_1
    //   372: iadd
    //   373: invokevirtual 170	java/lang/String:substring	(II)Ljava/lang/String;
    //   376: astore 38
    //   378: aload 27
    //   380: iload 33
    //   382: iconst_1
    //   383: iadd
    //   384: invokevirtual 173	java/lang/String:substring	(I)Ljava/lang/String;
    //   387: astore 37
    //   389: goto -172 -> 217
    //   392: aload 27
    //   394: invokevirtual 134	java/lang/String:length	()I
    //   397: istore 34
    //   399: iconst_0
    //   400: istore 35
    //   402: iload 35
    //   404: aload 27
    //   406: invokevirtual 134	java/lang/String:length	()I
    //   409: if_icmpge +425 -> 834
    //   412: aload 27
    //   414: iload 35
    //   416: invokevirtual 138	java/lang/String:charAt	(I)C
    //   419: invokestatic 144	java/lang/Character:isDigit	(C)Z
    //   422: ifeq +29 -> 451
    //   425: aload 27
    //   427: iconst_0
    //   428: iload 35
    //   430: invokevirtual 170	java/lang/String:substring	(II)Ljava/lang/String;
    //   433: astore 36
    //   435: aload 27
    //   437: iload 35
    //   439: invokevirtual 173	java/lang/String:substring	(I)Ljava/lang/String;
    //   442: astore 37
    //   444: aload 36
    //   446: astore 38
    //   448: goto -231 -> 217
    //   451: iinc 35 1
    //   454: goto -52 -> 402
    //   457: astore 39
    //   459: aload 39
    //   461: invokevirtual 206	java/lang/Exception:printStackTrace	()V
    //   464: goto -182 -> 282
    //   467: aload_2
    //   468: ldc 208
    //   470: invokevirtual 88	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   473: istore 4
    //   475: iconst_0
    //   476: istore 5
    //   478: iload 4
    //   480: ifeq +176 -> 656
    //   483: iload 5
    //   485: aload_3
    //   486: invokevirtual 93	org/json/JSONArray:length	()I
    //   489: if_icmpge +329 -> 818
    //   492: invokestatic 99	com/amap/mapapi/core/e:b	()Landroid/location/Address;
    //   495: astore 15
    //   497: aload_3
    //   498: iload 5
    //   500: invokevirtual 103	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   503: astore 16
    //   505: aload 16
    //   507: ldc 210
    //   509: invokevirtual 213	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   512: astore 17
    //   514: aload 16
    //   516: ldc 215
    //   518: invokevirtual 213	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   521: astore 18
    //   523: aload 15
    //   525: new 146	java/lang/StringBuilder
    //   528: dup
    //   529: invokespecial 147	java/lang/StringBuilder:<init>	()V
    //   532: aload 17
    //   534: ldc 55
    //   536: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   539: invokevirtual 151	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   542: ldc 217
    //   544: invokevirtual 151	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   547: aload 18
    //   549: ldc 55
    //   551: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   554: invokevirtual 151	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   557: invokevirtual 157	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   560: invokevirtual 109	android/location/Address:setFeatureName	(Ljava/lang/String;)V
    //   563: aload 15
    //   565: aload 16
    //   567: ldc 111
    //   569: invokevirtual 115	org/json/JSONObject:getDouble	(Ljava/lang/String;)D
    //   572: invokevirtual 119	android/location/Address:setLatitude	(D)V
    //   575: aload 15
    //   577: aload 16
    //   579: ldc 121
    //   581: invokevirtual 115	org/json/JSONObject:getDouble	(Ljava/lang/String;)D
    //   584: invokevirtual 124	android/location/Address:setLongitude	(D)V
    //   587: aload 15
    //   589: invokevirtual 182	java/lang/Object:getClass	()Ljava/lang/Class;
    //   592: ldc 198
    //   594: iconst_1
    //   595: anewarray 186	java/lang/Class
    //   598: dup
    //   599: iconst_0
    //   600: ldc 85
    //   602: aastore
    //   603: invokevirtual 190	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   606: astore 21
    //   608: aload 21
    //   610: ifnull +20 -> 630
    //   613: aload 21
    //   615: aload 15
    //   617: iconst_1
    //   618: anewarray 178	java/lang/Object
    //   621: dup
    //   622: iconst_0
    //   623: ldc 219
    //   625: aastore
    //   626: invokevirtual 196	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   629: pop
    //   630: aload 15
    //   632: ifnull +18 -> 650
    //   635: aload_0
    //   636: getfield 49	com/amap/mapapi/core/p:o	Ljava/util/ArrayList;
    //   639: aload 15
    //   641: invokevirtual 74	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   644: pop
    //   645: aload_0
    //   646: iconst_1
    //   647: putfield 221	com/amap/mapapi/core/p:r	Z
    //   650: iinc 5 1
    //   653: goto -170 -> 483
    //   656: aload_2
    //   657: ldc 223
    //   659: invokevirtual 88	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   662: istore 6
    //   664: iconst_0
    //   665: istore 7
    //   667: iload 6
    //   669: ifeq +149 -> 818
    //   672: iload 7
    //   674: aload_3
    //   675: invokevirtual 93	org/json/JSONArray:length	()I
    //   678: if_icmpge +140 -> 818
    //   681: invokestatic 99	com/amap/mapapi/core/e:b	()Landroid/location/Address;
    //   684: astore 8
    //   686: aload_3
    //   687: iload 7
    //   689: invokevirtual 103	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   692: astore 9
    //   694: aload 9
    //   696: ldc 55
    //   698: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   701: astore 10
    //   703: aload 8
    //   705: aload 10
    //   707: invokevirtual 109	android/location/Address:setFeatureName	(Ljava/lang/String;)V
    //   710: aload 8
    //   712: aload 9
    //   714: ldc 111
    //   716: invokevirtual 115	org/json/JSONObject:getDouble	(Ljava/lang/String;)D
    //   719: invokevirtual 119	android/location/Address:setLatitude	(D)V
    //   722: aload 8
    //   724: aload 9
    //   726: ldc 121
    //   728: invokevirtual 115	org/json/JSONObject:getDouble	(Ljava/lang/String;)D
    //   731: invokevirtual 124	android/location/Address:setLongitude	(D)V
    //   734: aload 8
    //   736: iconst_2
    //   737: aload 10
    //   739: invokevirtual 161	android/location/Address:setAddressLine	(ILjava/lang/String;)V
    //   742: aload 8
    //   744: aload 10
    //   746: invokevirtual 176	android/location/Address:setThoroughfare	(Ljava/lang/String;)V
    //   749: aload 8
    //   751: invokevirtual 182	java/lang/Object:getClass	()Ljava/lang/Class;
    //   754: ldc 198
    //   756: iconst_1
    //   757: anewarray 186	java/lang/Class
    //   760: dup
    //   761: iconst_0
    //   762: ldc 85
    //   764: aastore
    //   765: invokevirtual 190	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   768: astore 13
    //   770: aload 13
    //   772: ifnull +20 -> 792
    //   775: aload 13
    //   777: aload 8
    //   779: iconst_1
    //   780: anewarray 178	java/lang/Object
    //   783: dup
    //   784: iconst_0
    //   785: ldc 225
    //   787: aastore
    //   788: invokevirtual 196	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   791: pop
    //   792: aload 8
    //   794: ifnull +18 -> 812
    //   797: aload_0
    //   798: getfield 45	com/amap/mapapi/core/p:m	Ljava/util/ArrayList;
    //   801: aload 8
    //   803: invokevirtual 74	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   806: pop
    //   807: aload_0
    //   808: iconst_1
    //   809: putfield 227	com/amap/mapapi/core/p:p	Z
    //   812: iinc 7 1
    //   815: goto -143 -> 672
    //   818: return
    //   819: astore 11
    //   821: goto -29 -> 792
    //   824: astore 19
    //   826: goto -196 -> 630
    //   829: astore 28
    //   831: goto -506 -> 325
    //   834: iload 34
    //   836: istore 35
    //   838: goto -413 -> 425
    //
    // Exception table:
    //   from	to	target	type
    //   224	245	457	java/lang/Exception
    //   255	282	457	java/lang/Exception
    //   749	770	819	java/lang/Exception
    //   775	792	819	java/lang/Exception
    //   587	608	824	java/lang/Exception
    //   613	630	824	java/lang/Exception
    //   282	303	829	java/lang/Exception
    //   308	325	829	java/lang/Exception
  }

  private ArrayList<Address> b(ArrayList<Address> paramArrayList)
  {
    if (this.l <= 0)
      return paramArrayList;
    if (this.p)
    {
      paramArrayList.add(this.m.get(0));
      this.m.remove(0);
    }
    a(paramArrayList, this.n);
    if ((this.l - paramArrayList.size() > 0) && (this.r))
      paramArrayList.add(this.o.get(0));
    a(paramArrayList, this.m);
    return paramArrayList;
  }

  protected ArrayList<Address> a(InputStream paramInputStream)
    throws AMapException
  {
    ArrayList localArrayList = new ArrayList();
    Address localAddress;
    try
    {
      str = new String(i.a(paramInputStream));
      e.c(str);
    }
    catch (Exception localException3)
    {
      try
      {
        String str;
        JSONArray localJSONArray = new JSONObject(str).getJSONArray("list");
        int i1 = 0;
        while (i1 < localJSONArray.length())
        {
          JSONObject localJSONObject = localJSONArray.getJSONObject(i1);
          if (localJSONObject.has("province"))
            this.j = a(localJSONObject.getJSONObject("province"));
          if (localJSONObject.has("district"))
            this.k = a(localJSONObject.getJSONObject("district"));
          if (localJSONObject.has("city"))
          {
            this.i = a(localJSONObject.getJSONObject("city"));
            if ((this.i == null) || (this.i.equals("")))
              this.i = this.j;
          }
          if (localJSONObject.has("roadlist"))
          {
            a(localJSONObject, "roadlist");
            if (this.m.size() > 0)
              a(this.m);
          }
          if (localJSONObject.has("crosslist"))
          {
            a(localJSONObject, "crosslist");
            if (this.o.size() > 0)
              a(this.o);
          }
          if (localJSONObject.has("poilist"))
          {
            a(localJSONObject, "poilist");
            if (this.n.size() > 0)
              a(this.n);
          }
          i1++;
          continue;
          localException3 = localException3;
          localException3.printStackTrace();
          str = null;
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        if ((this.r) || (this.q) || (this.p))
          return b(localArrayList);
      }
      catch (IOException localIOException)
      {
        while (true)
          localIOException.printStackTrace();
      }
      catch (Exception localException1)
      {
        while (true)
          localException1.printStackTrace();
        localAddress = e.b();
        localAddress.setAdminArea(this.j);
        localAddress.setLocality(this.i);
        localAddress.setFeatureName(this.k);
        localAddress.setLatitude(((q)this.b).b);
        localAddress.setLongitude(((q)this.b).a);
      }
    }
    try
    {
      Method localMethod = localAddress.getClass().getMethod("setSubLocality", new Class[] { String.class });
      if (localMethod != null)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = this.k;
        localMethod.invoke(localAddress, arrayOfObject);
      }
      label460: localAddress.setAddressLine(0, "中国");
      if (!this.j.equals(this.i))
        localAddress.setAddressLine(1, this.j + this.i + this.k);
      while (true)
      {
        localArrayList.add(localAddress);
        return localArrayList;
        localAddress.setAddressLine(1, this.i + this.k);
      }
    }
    catch (Exception localException2)
    {
      break label460;
    }
  }

  protected void a(ArrayList<Address> paramArrayList)
  {
    if ((this.j != null) && (paramArrayList.size() == 0))
      paramArrayList.add(e.b());
    Iterator localIterator = paramArrayList.iterator();
    while (true)
    {
      Address localAddress;
      if (localIterator.hasNext())
      {
        localAddress = (Address)localIterator.next();
        localAddress.setAdminArea(this.j);
        localAddress.setLocality(this.i);
      }
      try
      {
        Method localMethod = localAddress.getClass().getMethod("setSubLocality", new Class[] { String.class });
        if (localMethod != null)
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = this.k;
          localMethod.invoke(localAddress, arrayOfObject);
        }
        label111: localAddress.setAddressLine(0, "中国");
        if (!this.j.equals(this.i))
        {
          localAddress.setAddressLine(1, this.j + this.i + this.k);
          continue;
        }
        localAddress.setAddressLine(1, this.i + this.k);
        continue;
        return;
      }
      catch (Exception localException)
      {
        break label111;
      }
    }
  }

  protected byte[] d()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("sid=7001&resType=json&encode=utf-8");
    localStringBuilder.append("&region=" + ((q)this.b).a + "," + ((q)this.b).b);
    localStringBuilder.append("&range=500&roadnum=10&crossnum=1&poinum=" + ((q)this.b).c);
    return localStringBuilder.toString().getBytes();
  }

  protected String e()
  {
    return j.a().d() + "/rgeocode/simple?";
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.core.p
 * JD-Core Version:    0.6.0
 */