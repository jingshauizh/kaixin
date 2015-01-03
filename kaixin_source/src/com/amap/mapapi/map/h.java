package com.amap.mapapi.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import java.util.List;

class h
{
  protected final a[] a;
  protected final int b;
  protected final int c;
  protected final a[] d;
  Paint e = null;
  Path f = null;
  private boolean g = false;
  private long h = 0L;

  public h(int paramInt1, int paramInt2, boolean paramBoolean, long paramLong)
  {
    this.b = paramInt1;
    this.c = paramInt2;
    this.g = paramBoolean;
    this.h = (1000000L * paramLong);
    if (this.b > 0)
    {
      this.a = new a[this.b];
      this.d = new a[this.c];
      return;
    }
    this.a = null;
    this.d = null;
  }

  private void a(Bitmap paramBitmap, List<ba> paramList)
  {
    1 local1 = new g(paramList)
    {
      public void a(Canvas paramCanvas)
      {
        if (h.this.e == null)
        {
          h.this.e = new Paint();
          h.this.e.setStyle(Paint.Style.STROKE);
          h.this.e.setDither(true);
          h.this.e.setAntiAlias(true);
          h.this.e.setStrokeJoin(Paint.Join.ROUND);
          h.this.e.setStrokeCap(Paint.Cap.ROUND);
        }
        if (h.this.f == null)
          h.this.f = new Path();
        int i = this.a.size();
        for (int j = 0; j < i; j++)
        {
          ba localba = (ba)this.a.get(j);
          h.this.e.setStrokeWidth(3.0F);
          int k = localba.b();
          int n;
          int i1;
          label200: PointF localPointF;
          if (k == 1)
          {
            h.this.e.setColor(-65536);
            List localList = localba.a();
            int m = localList.size();
            n = 0;
            i1 = 1;
            if (n >= m)
              break label329;
            localPointF = (PointF)localList.get(n);
            if (i1 == 0)
              break label302;
            h.this.f.moveTo(localPointF.x, localPointF.y);
          }
          for (int i2 = 0; ; i2 = i1)
          {
            n++;
            i1 = i2;
            break label200;
            if (k == 2)
            {
              h.this.e.setColor(-256);
              break;
            }
            if (k != 3)
              break;
            h.this.e.setColor(-16711936);
            break;
            label302: h.this.f.lineTo(localPointF.x, localPointF.y);
          }
          label329: paramCanvas.drawPath(h.this.f, h.this.e);
          h.this.f.reset();
        }
      }
    };
    f localf = new f(null);
    localf.a(paramBitmap);
    localf.a(local1);
  }

  private long d()
  {
    return System.nanoTime();
  }

  protected int a()
  {
    for (int i = 0; i < this.c; i++)
      this.d[i] = null;
    int j = 0;
    Object localObject1;
    int n;
    Object localObject2;
    while (j < this.b)
    {
      localObject1 = this.a[j];
      n = 0;
      if (n < this.c)
      {
        if (this.d[n] == null)
          this.d[n] = localObject1;
      }
      else
      {
        j++;
        continue;
      }
      if (this.d[n].d <= ((a)localObject1).d)
        break label183;
      localObject2 = this.d[n];
      this.d[n] = localObject1;
    }
    while (true)
    {
      n++;
      localObject1 = localObject2;
      break;
      int k = -1;
      for (int m = 0; m < this.c; m++)
      {
        if (this.d[m] == null)
          continue;
        this.d[m].c = false;
        if (k >= 0)
          continue;
        k = this.d[m].e;
      }
      return k;
      label183: localObject2 = localObject1;
    }
  }

  protected int a(String paramString)
  {
    if (paramString.equals("") == true)
      return -1;
    int i = 0;
    if (i < this.b)
    {
      if (this.a[i] == null);
      do
      {
        i++;
        break;
      }
      while (!this.a[i].b.equals(paramString));
      if (!this.a[i].c)
        return -1;
      if ((this.g == true) && (d() - this.a[i].f > this.h))
      {
        this.a[i].c = false;
        return -1;
      }
      this.a[i].d = d();
      return i;
    }
    return -1;
  }

  // ERROR //
  protected int a(byte[] paramArrayOfByte, java.io.InputStream paramInputStream, boolean paramBoolean, List<ba> paramList, String paramString)
  {
    // Byte code:
    //   0: iconst_m1
    //   1: istore 6
    //   3: aload_0
    //   4: monitorenter
    //   5: aload_1
    //   6: ifnonnull +17 -> 23
    //   9: aload_2
    //   10: ifnonnull +13 -> 23
    //   13: aload 4
    //   15: ifnonnull +8 -> 23
    //   18: aload_0
    //   19: monitorexit
    //   20: iload 6
    //   22: ireturn
    //   23: aload_0
    //   24: invokevirtual 94	com/amap/mapapi/map/h:b	()I
    //   27: istore 8
    //   29: iload 8
    //   31: ifge +9 -> 40
    //   34: aload_0
    //   35: invokevirtual 96	com/amap/mapapi/map/h:a	()I
    //   38: istore 8
    //   40: iload 8
    //   42: iflt -24 -> 18
    //   45: aload_0
    //   46: getfield 41	com/amap/mapapi/map/h:a	[Lcom/amap/mapapi/map/h$a;
    //   49: ifnull -31 -> 18
    //   52: aload_0
    //   53: getfield 41	com/amap/mapapi/map/h:a	[Lcom/amap/mapapi/map/h$a;
    //   56: iload 8
    //   58: aaload
    //   59: ifnull +56 -> 115
    //   62: aload_0
    //   63: getfield 41	com/amap/mapapi/map/h:a	[Lcom/amap/mapapi/map/h$a;
    //   66: iload 8
    //   68: aaload
    //   69: getfield 99	com/amap/mapapi/map/h$a:a	Landroid/graphics/Bitmap;
    //   72: ifnull +43 -> 115
    //   75: aload_0
    //   76: getfield 41	com/amap/mapapi/map/h:a	[Lcom/amap/mapapi/map/h$a;
    //   79: iload 8
    //   81: aaload
    //   82: getfield 99	com/amap/mapapi/map/h$a:a	Landroid/graphics/Bitmap;
    //   85: invokevirtual 105	android/graphics/Bitmap:isRecycled	()Z
    //   88: ifne +27 -> 115
    //   91: aload_0
    //   92: getfield 41	com/amap/mapapi/map/h:a	[Lcom/amap/mapapi/map/h$a;
    //   95: iload 8
    //   97: aaload
    //   98: getfield 99	com/amap/mapapi/map/h$a:a	Landroid/graphics/Bitmap;
    //   101: invokevirtual 108	android/graphics/Bitmap:recycle	()V
    //   104: aload_0
    //   105: getfield 41	com/amap/mapapi/map/h:a	[Lcom/amap/mapapi/map/h$a;
    //   108: iload 8
    //   110: aaload
    //   111: aconst_null
    //   112: putfield 99	com/amap/mapapi/map/h$a:a	Landroid/graphics/Bitmap;
    //   115: aload_0
    //   116: getfield 41	com/amap/mapapi/map/h:a	[Lcom/amap/mapapi/map/h$a;
    //   119: iload 8
    //   121: aaload
    //   122: getfield 111	com/amap/mapapi/map/h$a:g	Ljava/util/List;
    //   125: ifnull +29 -> 154
    //   128: aload_0
    //   129: getfield 41	com/amap/mapapi/map/h:a	[Lcom/amap/mapapi/map/h$a;
    //   132: iload 8
    //   134: aaload
    //   135: getfield 111	com/amap/mapapi/map/h$a:g	Ljava/util/List;
    //   138: invokeinterface 116 1 0
    //   143: aload_0
    //   144: getfield 41	com/amap/mapapi/map/h:a	[Lcom/amap/mapapi/map/h$a;
    //   147: iload 8
    //   149: aaload
    //   150: aconst_null
    //   151: putfield 111	com/amap/mapapi/map/h$a:g	Ljava/util/List;
    //   154: iload_3
    //   155: iconst_1
    //   156: if_icmpne +176 -> 332
    //   159: aload_1
    //   160: ifnull +172 -> 332
    //   163: aload_0
    //   164: getfield 41	com/amap/mapapi/map/h:a	[Lcom/amap/mapapi/map/h$a;
    //   167: iload 8
    //   169: aaload
    //   170: aload_1
    //   171: iconst_0
    //   172: aload_1
    //   173: arraylength
    //   174: invokestatic 122	android/graphics/BitmapFactory:decodeByteArray	([BII)Landroid/graphics/Bitmap;
    //   177: putfield 99	com/amap/mapapi/map/h$a:a	Landroid/graphics/Bitmap;
    //   180: aload 4
    //   182: ifnull +41 -> 223
    //   185: aload_0
    //   186: getfield 41	com/amap/mapapi/map/h:a	[Lcom/amap/mapapi/map/h$a;
    //   189: iload 8
    //   191: aaload
    //   192: sipush 256
    //   195: sipush 256
    //   198: getstatic 128	android/graphics/Bitmap$Config:ARGB_4444	Landroid/graphics/Bitmap$Config;
    //   201: invokestatic 132	android/graphics/Bitmap:createBitmap	(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;
    //   204: putfield 99	com/amap/mapapi/map/h$a:a	Landroid/graphics/Bitmap;
    //   207: aload_0
    //   208: aload_0
    //   209: getfield 41	com/amap/mapapi/map/h:a	[Lcom/amap/mapapi/map/h$a;
    //   212: iload 8
    //   214: aaload
    //   215: getfield 99	com/amap/mapapi/map/h$a:a	Landroid/graphics/Bitmap;
    //   218: aload 4
    //   220: invokespecial 134	com/amap/mapapi/map/h:a	(Landroid/graphics/Bitmap;Ljava/util/List;)V
    //   223: aload_0
    //   224: getfield 41	com/amap/mapapi/map/h:a	[Lcom/amap/mapapi/map/h$a;
    //   227: ifnull -209 -> 18
    //   230: aload_0
    //   231: getfield 41	com/amap/mapapi/map/h:a	[Lcom/amap/mapapi/map/h$a;
    //   234: iload 8
    //   236: aaload
    //   237: getfield 99	com/amap/mapapi/map/h$a:a	Landroid/graphics/Bitmap;
    //   240: ifnonnull +16 -> 256
    //   243: aload_0
    //   244: getfield 41	com/amap/mapapi/map/h:a	[Lcom/amap/mapapi/map/h$a;
    //   247: iload 8
    //   249: aaload
    //   250: getfield 111	com/amap/mapapi/map/h$a:g	Ljava/util/List;
    //   253: ifnull -235 -> 18
    //   256: aload_0
    //   257: getfield 41	com/amap/mapapi/map/h:a	[Lcom/amap/mapapi/map/h$a;
    //   260: iload 8
    //   262: aaload
    //   263: ifnull +62 -> 325
    //   266: aload_0
    //   267: getfield 41	com/amap/mapapi/map/h:a	[Lcom/amap/mapapi/map/h$a;
    //   270: iload 8
    //   272: aaload
    //   273: iconst_1
    //   274: putfield 71	com/amap/mapapi/map/h$a:c	Z
    //   277: aload_0
    //   278: getfield 41	com/amap/mapapi/map/h:a	[Lcom/amap/mapapi/map/h$a;
    //   281: iload 8
    //   283: aaload
    //   284: aload 5
    //   286: putfield 85	com/amap/mapapi/map/h$a:b	Ljava/lang/String;
    //   289: aload_0
    //   290: getfield 41	com/amap/mapapi/map/h:a	[Lcom/amap/mapapi/map/h$a;
    //   293: iload 8
    //   295: aaload
    //   296: aload_0
    //   297: invokespecial 87	com/amap/mapapi/map/h:d	()J
    //   300: putfield 69	com/amap/mapapi/map/h$a:d	J
    //   303: aload_0
    //   304: getfield 25	com/amap/mapapi/map/h:g	Z
    //   307: iconst_1
    //   308: if_icmpne +17 -> 325
    //   311: aload_0
    //   312: getfield 41	com/amap/mapapi/map/h:a	[Lcom/amap/mapapi/map/h$a;
    //   315: iload 8
    //   317: aaload
    //   318: aload_0
    //   319: invokespecial 87	com/amap/mapapi/map/h:d	()J
    //   322: putfield 89	com/amap/mapapi/map/h$a:f	J
    //   325: iload 8
    //   327: istore 6
    //   329: goto -311 -> 18
    //   332: aload_2
    //   333: ifnull -153 -> 180
    //   336: aload_0
    //   337: getfield 41	com/amap/mapapi/map/h:a	[Lcom/amap/mapapi/map/h$a;
    //   340: iload 8
    //   342: aaload
    //   343: aload_2
    //   344: invokestatic 138	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    //   347: putfield 99	com/amap/mapapi/map/h$a:a	Landroid/graphics/Bitmap;
    //   350: goto -170 -> 180
    //   353: astore 9
    //   355: goto -175 -> 180
    //   358: astore 7
    //   360: aload_0
    //   361: monitorexit
    //   362: aload 7
    //   364: athrow
    //   365: astore 10
    //   367: goto -187 -> 180
    //
    // Exception table:
    //   from	to	target	type
    //   336	350	353	java/lang/OutOfMemoryError
    //   23	29	358	finally
    //   34	40	358	finally
    //   45	115	358	finally
    //   115	154	358	finally
    //   163	180	358	finally
    //   185	223	358	finally
    //   223	256	358	finally
    //   256	325	358	finally
    //   336	350	358	finally
    //   163	180	365	java/lang/OutOfMemoryError
  }

  protected Bitmap a(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.b));
    do
      return null;
    while (this.a[paramInt] == null);
    return this.a[paramInt].a;
  }

  protected int b()
  {
    int i = -1;
    for (int j = 0; j < this.b; j++)
    {
      if (this.a[j] == null)
      {
        this.a[j] = new a();
        this.a[j].e = j;
        return j;
      }
      if ((this.a[j].c) || (i >= 0))
        continue;
      i = j;
    }
    return i;
  }

  protected void c()
  {
    int i = 0;
    if (i < this.b)
    {
      if (this.a[i] == null);
      while (true)
      {
        i++;
        break;
        if ((this.a[i].a != null) && (!this.a[i].a.isRecycled()))
          this.a[i].a.recycle();
        this.a[i].a = null;
      }
    }
  }

  class a
  {
    Bitmap a = null;
    String b = "";
    boolean c = false;
    long d = 0L;
    int e = -1;
    long f = 0L;
    List<ba> g = null;

    a()
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.h
 * JD-Core Version:    0.6.0
 */