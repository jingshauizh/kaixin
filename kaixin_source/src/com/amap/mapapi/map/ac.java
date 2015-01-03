package com.amap.mapapi.map;

import com.amap.mapapi.core.d;
import com.mapabc.minimap.map.vmap.NativeMapEngine;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

class ac
{
  MapView a;
  public ArrayList<String> b = new ArrayList();
  boolean c = false;
  long d;
  int e;
  byte[] f;
  int g = 0;
  int h = 0;
  boolean i = false;
  boolean j = false;

  public ac(MapView paramMapView)
  {
    this.a = paramMapView;
    this.d = System.currentTimeMillis();
  }

  private void d()
  {
    if (this.h == 0)
      if (this.g >= 8)
      {
        this.h = (8 + d.a(this.f, 0));
        d();
      }
    do
      return;
    while (this.g < this.h);
    int k = d.a(this.f, 0);
    int m = d.a(this.f, 4);
    if (m == 0)
      a(this.f, 8, k);
    while (true)
    {
      d.a(this.f, this.h, this.f, 0, this.g - this.h);
      this.g -= this.h;
      this.h = 0;
      d();
      return;
      ByteArrayOutputStream localByteArrayOutputStream;
      try
      {
        GZIPInputStream localGZIPInputStream = new GZIPInputStream(new ByteArrayInputStream(this.f, 8, k));
        localByteArrayOutputStream = new ByteArrayOutputStream();
        byte[] arrayOfByte = new byte[''];
        while (true)
        {
          int n = localGZIPInputStream.read(arrayOfByte);
          if (n <= -1)
            break;
          localByteArrayOutputStream.write(arrayOfByte, 0, n);
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      continue;
      a(localByteArrayOutputStream.toByteArray(), 0, m);
    }
  }

  public String a()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int k = 0; k < this.b.size(); k++)
    {
      String str = (String)this.b.get(k);
      localStringBuffer.append(str + ";");
    }
    if (localStringBuffer.length() > 0)
    {
      localStringBuffer.deleteCharAt(-1 + localStringBuffer.length());
      return "&cp=1&mesh=" + localStringBuffer.toString();
    }
    return null;
  }

  public void a(ac paramac)
  {
    this.f = new byte[262144];
    this.h = 0;
    this.g = 0;
    this.i = false;
    a("连接打开成功...");
  }

  public void a(ac paramac, int paramInt1, byte[] paramArrayOfByte, int paramInt2)
  {
    System.arraycopy(paramArrayOfByte, 0, this.f, this.g, paramInt2);
    this.g = (paramInt2 + this.g);
    if (!this.i)
    {
      if (this.g > 7)
      {
        if (d.a(this.f, 0) != 0)
          paramac.c = true;
      }
      else
        return;
      d.a(this.f, 4);
      d.a(this.f, 8, this.f, 0, paramInt2 - 8);
      this.g = (-8 + this.g);
      this.h = 0;
      this.i = true;
      d();
    }
    d();
  }

  public void a(String paramString)
  {
  }

  void a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    d.b(this.f, paramInt1);
    int k = paramInt1 + 2;
    d.b(this.f, k);
    int m = k + 2;
    d.a(this.f, m);
    int n = m + 4;
    int i1 = n + 1;
    int i2 = paramArrayOfByte[n];
    String str = new String(paramArrayOfByte, i1, i2);
    (i2 + i1);
    if (this.a.f == null);
    do
    {
      return;
      this.a.f.putGridData(paramArrayOfByte, paramInt1, paramInt2 - paramInt1);
      int i3 = this.a.getGridLevelOff(str.length());
      this.a.f.removeBitmapData(str, i3);
    }
    while (!this.a.isGridInScreen(str));
    this.a.postInvalidate();
  }

  // ERROR //
  public void b()
  {
    // Byte code:
    //   0: aload_0
    //   1: iconst_1
    //   2: putfield 41	com/amap/mapapi/map/ac:j	Z
    //   5: aload_0
    //   6: invokevirtual 180	com/amap/mapapi/map/ac:c	()Z
    //   9: ifne +19 -> 28
    //   12: aload_0
    //   13: getfield 43	com/amap/mapapi/map/ac:a	Lcom/amap/mapapi/map/MapView;
    //   16: getfield 183	com/amap/mapapi/map/MapView:i	Lcom/amap/mapapi/map/n;
    //   19: invokevirtual 187	com/amap/mapapi/map/n:a	()V
    //   22: aload_0
    //   23: aload_0
    //   24: invokevirtual 189	com/amap/mapapi/map/ac:b	(Lcom/amap/mapapi/map/ac;)V
    //   27: return
    //   28: iconst_0
    //   29: istore_1
    //   30: iconst_0
    //   31: istore_2
    //   32: iload_1
    //   33: aload_0
    //   34: getfield 31	com/amap/mapapi/map/ac:b	Ljava/util/ArrayList;
    //   37: invokevirtual 102	java/util/ArrayList:size	()I
    //   40: if_icmpge +103 -> 143
    //   43: aload_0
    //   44: getfield 31	com/amap/mapapi/map/ac:b	Ljava/util/ArrayList;
    //   47: iload_1
    //   48: invokevirtual 106	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   51: checkcast 108	java/lang/String
    //   54: astore 25
    //   56: aload_0
    //   57: getfield 43	com/amap/mapapi/map/ac:a	Lcom/amap/mapapi/map/MapView;
    //   60: getfield 154	com/amap/mapapi/map/MapView:f	Lcom/mapabc/minimap/map/vmap/NativeMapEngine;
    //   63: ifnull +74 -> 137
    //   66: aload_0
    //   67: getfield 43	com/amap/mapapi/map/ac:a	Lcom/amap/mapapi/map/MapView;
    //   70: getfield 154	com/amap/mapapi/map/MapView:f	Lcom/mapabc/minimap/map/vmap/NativeMapEngine;
    //   73: aload 25
    //   75: invokevirtual 192	com/mapabc/minimap/map/vmap/NativeMapEngine:hasGridData	(Ljava/lang/String;)Z
    //   78: ifeq +59 -> 137
    //   81: iconst_1
    //   82: istore 26
    //   84: iload 26
    //   86: ifeq +30 -> 116
    //   89: aload_0
    //   90: getfield 31	com/amap/mapapi/map/ac:b	Ljava/util/ArrayList;
    //   93: iload_1
    //   94: invokevirtual 195	java/util/ArrayList:remove	(I)Ljava/lang/Object;
    //   97: pop
    //   98: iinc 1 255
    //   101: iinc 2 1
    //   104: aload_0
    //   105: getfield 43	com/amap/mapapi/map/ac:a	Lcom/amap/mapapi/map/MapView;
    //   108: getfield 199	com/amap/mapapi/map/MapView:tileDownloadCtrl	Lcom/amap/mapapi/map/ax;
    //   111: aload 25
    //   113: invokevirtual 202	com/amap/mapapi/map/ax:a	(Ljava/lang/String;)V
    //   116: iload_1
    //   117: istore 27
    //   119: iload_2
    //   120: istore 28
    //   122: iload 27
    //   124: iconst_1
    //   125: iadd
    //   126: istore 29
    //   128: iload 28
    //   130: istore_2
    //   131: iload 29
    //   133: istore_1
    //   134: goto -102 -> 32
    //   137: iconst_0
    //   138: istore 26
    //   140: goto -56 -> 84
    //   143: aload_0
    //   144: getfield 31	com/amap/mapapi/map/ac:b	Ljava/util/ArrayList;
    //   147: invokevirtual 102	java/util/ArrayList:size	()I
    //   150: ifne +9 -> 159
    //   153: aload_0
    //   154: aload_0
    //   155: invokevirtual 189	com/amap/mapapi/map/ac:b	(Lcom/amap/mapapi/map/ac;)V
    //   158: return
    //   159: iload_2
    //   160: ifle +10 -> 170
    //   163: aload_0
    //   164: getfield 43	com/amap/mapapi/map/ac:a	Lcom/amap/mapapi/map/MapView;
    //   167: invokevirtual 175	com/amap/mapapi/map/MapView:postInvalidate	()V
    //   170: new 110	java/lang/StringBuilder
    //   173: dup
    //   174: invokespecial 111	java/lang/StringBuilder:<init>	()V
    //   177: ldc 204
    //   179: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   182: aload_0
    //   183: invokevirtual 206	com/amap/mapapi/map/ac:a	()Ljava/lang/String;
    //   186: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   189: invokevirtual 120	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   192: astore 13
    //   194: aload_0
    //   195: getfield 43	com/amap/mapapi/map/ac:a	Lcom/amap/mapapi/map/MapView;
    //   198: aload 13
    //   200: invokevirtual 210	com/amap/mapapi/map/MapView:getConnection	(Ljava/lang/String;)Ljava/net/HttpURLConnection;
    //   203: astore 14
    //   205: aload 14
    //   207: astore 5
    //   209: aload 5
    //   211: ifnonnull +35 -> 246
    //   214: aload_0
    //   215: aload_0
    //   216: invokevirtual 189	com/amap/mapapi/map/ac:b	(Lcom/amap/mapapi/map/ac;)V
    //   219: iconst_0
    //   220: ifeq +7 -> 227
    //   223: aconst_null
    //   224: invokevirtual 215	java/io/OutputStream:close	()V
    //   227: iconst_0
    //   228: ifeq +7 -> 235
    //   231: aconst_null
    //   232: invokevirtual 218	java/io/InputStream:close	()V
    //   235: aload 5
    //   237: ifnull -210 -> 27
    //   240: aload 5
    //   242: invokevirtual 223	java/net/HttpURLConnection:disconnect	()V
    //   245: return
    //   246: aload 5
    //   248: sipush 15000
    //   251: invokevirtual 227	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   254: aload 5
    //   256: ldc 229
    //   258: invokevirtual 232	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   261: aload 5
    //   263: invokevirtual 236	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   266: astore 16
    //   268: aload 16
    //   270: astore 4
    //   272: aload_0
    //   273: aload_0
    //   274: invokevirtual 238	com/amap/mapapi/map/ac:a	(Lcom/amap/mapapi/map/ac;)V
    //   277: sipush 1024
    //   280: newarray byte
    //   282: astore 19
    //   284: aload 4
    //   286: aload 19
    //   288: invokevirtual 239	java/io/InputStream:read	([B)I
    //   291: istore 20
    //   293: iload 20
    //   295: iconst_m1
    //   296: if_icmple +27 -> 323
    //   299: aload_0
    //   300: invokevirtual 180	com/amap/mapapi/map/ac:c	()Z
    //   303: ifeq +10 -> 313
    //   306: aload_0
    //   307: getfield 33	com/amap/mapapi/map/ac:c	Z
    //   310: ifeq +47 -> 357
    //   313: aload_0
    //   314: getfield 43	com/amap/mapapi/map/ac:a	Lcom/amap/mapapi/map/MapView;
    //   317: getfield 183	com/amap/mapapi/map/MapView:i	Lcom/amap/mapapi/map/n;
    //   320: invokevirtual 187	com/amap/mapapi/map/n:a	()V
    //   323: aload_0
    //   324: aload_0
    //   325: invokevirtual 189	com/amap/mapapi/map/ac:b	(Lcom/amap/mapapi/map/ac;)V
    //   328: iconst_0
    //   329: ifeq +7 -> 336
    //   332: aconst_null
    //   333: invokevirtual 215	java/io/OutputStream:close	()V
    //   336: aload 4
    //   338: ifnull +8 -> 346
    //   341: aload 4
    //   343: invokevirtual 218	java/io/InputStream:close	()V
    //   346: aload 5
    //   348: ifnull -321 -> 27
    //   351: aload 5
    //   353: invokevirtual 223	java/net/HttpURLConnection:disconnect	()V
    //   356: return
    //   357: aload_0
    //   358: aload_0
    //   359: iconst_0
    //   360: aload 19
    //   362: iload 20
    //   364: invokevirtual 241	com/amap/mapapi/map/ac:a	(Lcom/amap/mapapi/map/ac;I[BI)V
    //   367: goto -83 -> 284
    //   370: astore 18
    //   372: aload_0
    //   373: aload_0
    //   374: invokevirtual 189	com/amap/mapapi/map/ac:b	(Lcom/amap/mapapi/map/ac;)V
    //   377: iconst_0
    //   378: ifeq +7 -> 385
    //   381: aconst_null
    //   382: invokevirtual 215	java/io/OutputStream:close	()V
    //   385: aload 4
    //   387: ifnull +8 -> 395
    //   390: aload 4
    //   392: invokevirtual 218	java/io/InputStream:close	()V
    //   395: aload 5
    //   397: ifnull -370 -> 27
    //   400: aload 5
    //   402: invokevirtual 223	java/net/HttpURLConnection:disconnect	()V
    //   405: return
    //   406: astore 8
    //   408: aconst_null
    //   409: astore 9
    //   411: aconst_null
    //   412: astore 10
    //   414: aload_0
    //   415: aload_0
    //   416: invokevirtual 189	com/amap/mapapi/map/ac:b	(Lcom/amap/mapapi/map/ac;)V
    //   419: iconst_0
    //   420: ifeq +7 -> 427
    //   423: aconst_null
    //   424: invokevirtual 215	java/io/OutputStream:close	()V
    //   427: aload 9
    //   429: ifnull +8 -> 437
    //   432: aload 9
    //   434: invokevirtual 218	java/io/InputStream:close	()V
    //   437: aload 10
    //   439: ifnull +8 -> 447
    //   442: aload 10
    //   444: invokevirtual 223	java/net/HttpURLConnection:disconnect	()V
    //   447: aload 8
    //   449: athrow
    //   450: astore 24
    //   452: goto -225 -> 227
    //   455: astore 23
    //   457: goto -222 -> 235
    //   460: astore 22
    //   462: goto -126 -> 336
    //   465: astore 21
    //   467: goto -121 -> 346
    //   470: astore 7
    //   472: goto -87 -> 385
    //   475: astore 6
    //   477: goto -82 -> 395
    //   480: astore 12
    //   482: goto -55 -> 427
    //   485: astore 11
    //   487: goto -50 -> 437
    //   490: astore 8
    //   492: aload 5
    //   494: astore 10
    //   496: aconst_null
    //   497: astore 9
    //   499: goto -85 -> 414
    //   502: astore 17
    //   504: aload 5
    //   506: astore 10
    //   508: aload 4
    //   510: astore 9
    //   512: aload 17
    //   514: astore 8
    //   516: goto -102 -> 414
    //   519: astore_3
    //   520: aconst_null
    //   521: astore 4
    //   523: aconst_null
    //   524: astore 5
    //   526: goto -154 -> 372
    //   529: astore 15
    //   531: aconst_null
    //   532: astore 4
    //   534: goto -162 -> 372
    //
    // Exception table:
    //   from	to	target	type
    //   272	284	370	java/io/IOException
    //   284	293	370	java/io/IOException
    //   299	313	370	java/io/IOException
    //   313	323	370	java/io/IOException
    //   357	367	370	java/io/IOException
    //   170	205	406	finally
    //   223	227	450	java/io/IOException
    //   231	235	455	java/io/IOException
    //   332	336	460	java/io/IOException
    //   341	346	465	java/io/IOException
    //   381	385	470	java/io/IOException
    //   390	395	475	java/io/IOException
    //   423	427	480	java/io/IOException
    //   432	437	485	java/io/IOException
    //   246	268	490	finally
    //   272	284	502	finally
    //   284	293	502	finally
    //   299	313	502	finally
    //   313	323	502	finally
    //   357	367	502	finally
    //   170	205	519	java/io/IOException
    //   246	268	529	java/io/IOException
  }

  public void b(ac paramac)
  {
    this.f = null;
    this.h = 0;
    this.g = 0;
    a(null);
    for (int k = 0; k < paramac.b.size(); k++)
      this.a.tileDownloadCtrl.a((String)paramac.b.get(k));
    if (this.a.i.a.b() == this)
      this.a.i.a.a();
    this.a.postInvalidate();
  }

  public void b(String paramString)
  {
    this.b.add(paramString);
  }

  public boolean c()
  {
    if (this.e != this.a.mapLevel)
      return false;
    return this.a.isAGridsInScreen(this.b);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.ac
 * JD-Core Version:    0.6.0
 */