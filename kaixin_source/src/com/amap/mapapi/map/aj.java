package com.amap.mapapi.map;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.MotionEvent;
import java.lang.reflect.Method;

abstract class aj
{
  static float j = 1.0F;
  private static Method o;
  private static Method p;
  private static boolean q = false;
  private static boolean r = false;
  b a;
  int b = 0;
  Matrix c = new Matrix();
  Matrix d = new Matrix();
  PointF e = new PointF();
  PointF f = new PointF();
  PointF g = new PointF();
  float h = 1.0F;
  float i = 1.0F;
  boolean k = false;
  boolean l = false;
  boolean m = false;
  public int n = 0;

  public static aj a(Context paramContext, b paramb)
  {
    a locala = new a(null);
    locala.a = paramb;
    return locala;
  }

  private static void c(MotionEvent paramMotionEvent)
  {
    if (r);
    while (true)
    {
      return;
      r = true;
      try
      {
        Class localClass1 = paramMotionEvent.getClass();
        Class[] arrayOfClass1 = new Class[1];
        arrayOfClass1[0] = Integer.TYPE;
        o = localClass1.getMethod("getX", arrayOfClass1);
        Class localClass2 = paramMotionEvent.getClass();
        Class[] arrayOfClass2 = new Class[1];
        arrayOfClass2[0] = Integer.TYPE;
        p = localClass2.getMethod("getY", arrayOfClass2);
        if ((o == null) || (p == null))
          continue;
        q = true;
        return;
      }
      catch (Exception localException)
      {
      }
    }
  }

  public abstract boolean a(MotionEvent paramMotionEvent);

  private static class a extends aj
  {
    float o;
    float p;
    float q;
    float r;

    // ERROR //
    private void a(PointF paramPointF, MotionEvent paramMotionEvent)
    {
      // Byte code:
      //   0: invokestatic 27	com/amap/mapapi/map/aj:b	()Ljava/lang/reflect/Method;
      //   3: astore 17
      //   5: iconst_1
      //   6: anewarray 29	java/lang/Object
      //   9: astore 18
      //   11: aload 18
      //   13: iconst_0
      //   14: iconst_0
      //   15: invokestatic 35	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   18: aastore
      //   19: aload 17
      //   21: aload_2
      //   22: aload 18
      //   24: invokevirtual 41	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   27: checkcast 43	java/lang/Float
      //   30: invokevirtual 47	java/lang/Float:floatValue	()F
      //   33: fstore 19
      //   35: invokestatic 27	com/amap/mapapi/map/aj:b	()Ljava/lang/reflect/Method;
      //   38: astore 20
      //   40: iconst_1
      //   41: anewarray 29	java/lang/Object
      //   44: astore 21
      //   46: aload 21
      //   48: iconst_0
      //   49: iconst_1
      //   50: invokestatic 35	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   53: aastore
      //   54: aload 20
      //   56: aload_2
      //   57: aload 21
      //   59: invokevirtual 41	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   62: checkcast 43	java/lang/Float
      //   65: invokevirtual 47	java/lang/Float:floatValue	()F
      //   68: fstore 22
      //   70: fload 22
      //   72: fload 19
      //   74: fadd
      //   75: fstore 4
      //   77: invokestatic 50	com/amap/mapapi/map/aj:c	()Ljava/lang/reflect/Method;
      //   80: astore 9
      //   82: iconst_1
      //   83: anewarray 29	java/lang/Object
      //   86: astore 10
      //   88: aload 10
      //   90: iconst_0
      //   91: iconst_0
      //   92: invokestatic 35	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   95: aastore
      //   96: aload 9
      //   98: aload_2
      //   99: aload 10
      //   101: invokevirtual 41	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   104: checkcast 43	java/lang/Float
      //   107: invokevirtual 47	java/lang/Float:floatValue	()F
      //   110: fstore 11
      //   112: invokestatic 50	com/amap/mapapi/map/aj:c	()Ljava/lang/reflect/Method;
      //   115: astore 12
      //   117: iconst_1
      //   118: anewarray 29	java/lang/Object
      //   121: astore 13
      //   123: aload 13
      //   125: iconst_0
      //   126: iconst_1
      //   127: invokestatic 35	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   130: aastore
      //   131: aload 12
      //   133: aload_2
      //   134: aload 13
      //   136: invokevirtual 41	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   139: checkcast 43	java/lang/Float
      //   142: invokevirtual 47	java/lang/Float:floatValue	()F
      //   145: fstore 14
      //   147: fload 11
      //   149: fload 14
      //   151: fadd
      //   152: fstore 6
      //   154: aload_1
      //   155: fload 4
      //   157: fconst_2
      //   158: fdiv
      //   159: fload 6
      //   161: fconst_2
      //   162: fdiv
      //   163: invokevirtual 56	android/graphics/PointF:set	(FF)V
      //   166: return
      //   167: astore 16
      //   169: aload 16
      //   171: invokevirtual 59	java/lang/IllegalArgumentException:printStackTrace	()V
      //   174: fconst_0
      //   175: fstore 4
      //   177: goto -100 -> 77
      //   180: astore 15
      //   182: aload 15
      //   184: invokevirtual 60	java/lang/IllegalAccessException:printStackTrace	()V
      //   187: fconst_0
      //   188: fstore 4
      //   190: goto -113 -> 77
      //   193: astore_3
      //   194: aload_3
      //   195: invokevirtual 61	java/lang/reflect/InvocationTargetException:printStackTrace	()V
      //   198: fconst_0
      //   199: fstore 4
      //   201: goto -124 -> 77
      //   204: astore 8
      //   206: aload 8
      //   208: invokevirtual 59	java/lang/IllegalArgumentException:printStackTrace	()V
      //   211: fconst_0
      //   212: fstore 6
      //   214: goto -60 -> 154
      //   217: astore 7
      //   219: aload 7
      //   221: invokevirtual 60	java/lang/IllegalAccessException:printStackTrace	()V
      //   224: fconst_0
      //   225: fstore 6
      //   227: goto -73 -> 154
      //   230: astore 5
      //   232: aload 5
      //   234: invokevirtual 61	java/lang/reflect/InvocationTargetException:printStackTrace	()V
      //   237: fconst_0
      //   238: fstore 6
      //   240: goto -86 -> 154
      //
      // Exception table:
      //   from	to	target	type
      //   0	70	167	java/lang/IllegalArgumentException
      //   0	70	180	java/lang/IllegalAccessException
      //   0	70	193	java/lang/reflect/InvocationTargetException
      //   77	147	204	java/lang/IllegalArgumentException
      //   77	147	217	java/lang/IllegalAccessException
      //   77	147	230	java/lang/reflect/InvocationTargetException
    }

    // ERROR //
    private float c(MotionEvent paramMotionEvent)
    {
      // Byte code:
      //   0: invokestatic 27	com/amap/mapapi/map/aj:b	()Ljava/lang/reflect/Method;
      //   3: astore 16
      //   5: iconst_1
      //   6: anewarray 29	java/lang/Object
      //   9: astore 17
      //   11: aload 17
      //   13: iconst_0
      //   14: iconst_0
      //   15: invokestatic 35	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   18: aastore
      //   19: aload 16
      //   21: aload_1
      //   22: aload 17
      //   24: invokevirtual 41	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   27: checkcast 43	java/lang/Float
      //   30: invokevirtual 47	java/lang/Float:floatValue	()F
      //   33: fstore 18
      //   35: invokestatic 27	com/amap/mapapi/map/aj:b	()Ljava/lang/reflect/Method;
      //   38: astore 19
      //   40: iconst_1
      //   41: anewarray 29	java/lang/Object
      //   44: astore 20
      //   46: aload 20
      //   48: iconst_0
      //   49: iconst_1
      //   50: invokestatic 35	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   53: aastore
      //   54: aload 19
      //   56: aload_1
      //   57: aload 20
      //   59: invokevirtual 41	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   62: checkcast 43	java/lang/Float
      //   65: invokevirtual 47	java/lang/Float:floatValue	()F
      //   68: fstore 21
      //   70: fload 18
      //   72: fload 21
      //   74: fsub
      //   75: fstore_3
      //   76: invokestatic 50	com/amap/mapapi/map/aj:c	()Ljava/lang/reflect/Method;
      //   79: astore 8
      //   81: iconst_1
      //   82: anewarray 29	java/lang/Object
      //   85: astore 9
      //   87: aload 9
      //   89: iconst_0
      //   90: iconst_0
      //   91: invokestatic 35	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   94: aastore
      //   95: aload 8
      //   97: aload_1
      //   98: aload 9
      //   100: invokevirtual 41	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   103: checkcast 43	java/lang/Float
      //   106: invokevirtual 47	java/lang/Float:floatValue	()F
      //   109: fstore 10
      //   111: invokestatic 50	com/amap/mapapi/map/aj:c	()Ljava/lang/reflect/Method;
      //   114: astore 11
      //   116: iconst_1
      //   117: anewarray 29	java/lang/Object
      //   120: astore 12
      //   122: aload 12
      //   124: iconst_0
      //   125: iconst_1
      //   126: invokestatic 35	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   129: aastore
      //   130: aload 11
      //   132: aload_1
      //   133: aload 12
      //   135: invokevirtual 41	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   138: checkcast 43	java/lang/Float
      //   141: invokevirtual 47	java/lang/Float:floatValue	()F
      //   144: fstore 13
      //   146: fload 10
      //   148: fload 13
      //   150: fsub
      //   151: fstore 5
      //   153: fload_3
      //   154: fload_3
      //   155: fmul
      //   156: fload 5
      //   158: fload 5
      //   160: fmul
      //   161: fadd
      //   162: invokestatic 68	android/util/FloatMath:sqrt	(F)F
      //   165: freturn
      //   166: astore 15
      //   168: aload 15
      //   170: invokevirtual 59	java/lang/IllegalArgumentException:printStackTrace	()V
      //   173: fconst_0
      //   174: fstore_3
      //   175: goto -99 -> 76
      //   178: astore 14
      //   180: aload 14
      //   182: invokevirtual 60	java/lang/IllegalAccessException:printStackTrace	()V
      //   185: fconst_0
      //   186: fstore_3
      //   187: goto -111 -> 76
      //   190: astore_2
      //   191: aload_2
      //   192: invokevirtual 61	java/lang/reflect/InvocationTargetException:printStackTrace	()V
      //   195: fconst_0
      //   196: fstore_3
      //   197: goto -121 -> 76
      //   200: astore 7
      //   202: aload 7
      //   204: invokevirtual 59	java/lang/IllegalArgumentException:printStackTrace	()V
      //   207: fconst_0
      //   208: fstore 5
      //   210: goto -57 -> 153
      //   213: astore 6
      //   215: aload 6
      //   217: invokevirtual 60	java/lang/IllegalAccessException:printStackTrace	()V
      //   220: fconst_0
      //   221: fstore 5
      //   223: goto -70 -> 153
      //   226: astore 4
      //   228: aload 4
      //   230: invokevirtual 61	java/lang/reflect/InvocationTargetException:printStackTrace	()V
      //   233: fconst_0
      //   234: fstore 5
      //   236: goto -83 -> 153
      //
      // Exception table:
      //   from	to	target	type
      //   0	70	166	java/lang/IllegalArgumentException
      //   0	70	178	java/lang/IllegalAccessException
      //   0	70	190	java/lang/reflect/InvocationTargetException
      //   76	146	200	java/lang/IllegalArgumentException
      //   76	146	213	java/lang/IllegalAccessException
      //   76	146	226	java/lang/reflect/InvocationTargetException
    }

    public boolean a(MotionEvent paramMotionEvent)
    {
      aj.b(paramMotionEvent);
      if (!aj.a())
        return false;
      switch (0xFF & paramMotionEvent.getAction())
      {
      case 3:
      case 4:
      default:
      case 0:
      case 5:
      case 1:
      case 6:
      case 2:
      }
      boolean bool2;
      label489: float f1;
      do
      {
        do
        {
          bool2 = false;
          while (true)
          {
            return bool2;
            this.o = paramMotionEvent.getX();
            this.p = paramMotionEvent.getY();
            this.d.set(this.c);
            this.e.set(this.o, this.p);
            this.b = 1;
            bool2 = false;
            continue;
            this.n = (1 + this.n);
            if (this.n != 1)
              break;
            this.m = true;
            j = 1.0F;
            this.h = c(paramMotionEvent);
            if (this.h <= 10.0F)
              break;
            this.c.reset();
            this.d.reset();
            this.d.set(this.c);
            a(this.f, paramMotionEvent);
            this.b = 2;
            this.k = true;
            bool2 = false | this.a.a(this.e);
            this.q = this.f.x;
            this.r = this.f.y;
            continue;
            this.k = false;
            this.b = 0;
            bool2 = false;
            continue;
            this.n = (-1 + this.n);
            if (this.n == 1)
            {
              this.m = true;
              this.b = 2;
            }
            if (this.n != 0)
              break;
            a(this.f, paramMotionEvent);
            this.l = false;
            this.m = false;
            if (!this.k)
              break;
            this.k = false;
            bool2 = false | this.a.a(this.i, this.f);
            this.b = 0;
            continue;
            if (this.b != 1)
              break label489;
            float f3 = paramMotionEvent.getX();
            float f4 = paramMotionEvent.getY();
            this.c.set(this.d);
            this.c.postTranslate(paramMotionEvent.getX() - this.e.x, paramMotionEvent.getY() - this.e.y);
            boolean bool3 = false | this.a.a(f3 - this.o, f4 - this.p);
            this.o = f3;
            this.p = f4;
            bool2 = bool3 | this.a.a(this.c);
          }
        }
        while (this.b != 2);
        f1 = c(paramMotionEvent);
        this.i = 0.0F;
      }
      while ((f1 <= 10.0F) || (Math.abs(f1 - this.h) <= 5.0F));
      this.c.set(this.d);
      float f2;
      if (f1 > this.h)
        f2 = f1 / this.h;
      while (true)
      {
        this.i = f2;
        j = f1 / this.h;
        if (f1 < this.h)
          this.i = (-this.i);
        a(this.g, paramMotionEvent);
        boolean bool1 = false | this.a.a(this.g.x - this.q, this.g.y - this.r);
        this.q = this.g.x;
        this.r = this.g.y;
        this.c.postScale(f1 / this.h, f1 / this.h, this.f.x, this.f.y);
        bool2 = bool1 | this.a.b(this.i) | this.a.b(this.c);
        this.l = true;
        break;
        f2 = this.h / f1;
      }
    }
  }

  public static abstract interface b
  {
    public abstract boolean a(float paramFloat1, float paramFloat2);

    public abstract boolean a(float paramFloat, PointF paramPointF);

    public abstract boolean a(Matrix paramMatrix);

    public abstract boolean a(PointF paramPointF);

    public abstract boolean b(float paramFloat);

    public abstract boolean b(Matrix paramMatrix);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.aj
 * JD-Core Version:    0.6.0
 */