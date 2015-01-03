package com.amap.mapapi.map;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PointF;

class av
{
  private static Paint a = null;
  private static Bitmap b = null;
  private static int c = Color.rgb(222, 215, 214);

  public static int a()
  {
    return c;
  }

  public static Paint b()
  {
    if (a == null)
    {
      a = new Paint();
      a.setColor(-7829368);
      a.setAlpha(90);
      DashPathEffect localDashPathEffect = new DashPathEffect(new float[] { 2.0F, 2.5F }, 1.0F);
      a.setPathEffect(localDashPathEffect);
    }
    return a;
  }

  public static Bitmap c()
  {
    if (b == null)
    {
      1 local1 = new g()
      {
        public void a(Canvas paramCanvas)
        {
          Paint localPaint = av.b();
          paramCanvas.drawColor(av.a());
          for (int i = 0; i < 235; i += 21)
          {
            paramCanvas.drawLine(i, 0.0F, i, 256.0F, localPaint);
            paramCanvas.drawLine(0.0F, i, 256.0F, i, localPaint);
          }
        }
      };
      f localf = new f(Bitmap.Config.ARGB_4444);
      localf.a(256, 256);
      localf.a(local1);
      b = localf.b();
    }
    return b;
  }

  static class a
  {
    public int a = 0;
    public final int b;
    public final int c;
    public final int d;
    public final int e;
    public PointF f;
    public int g = -1;

    public a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      this.b = paramInt1;
      this.c = paramInt2;
      this.d = paramInt3;
      this.e = paramInt4;
    }

    public a(a parama)
    {
      this.b = parama.b;
      this.c = parama.c;
      this.d = parama.d;
      this.e = parama.e;
      this.f = parama.f;
      this.a = parama.a;
    }

    public a a()
    {
      return new a(this);
    }

    public boolean equals(Object paramObject)
    {
      if (this == paramObject);
      a locala;
      do
      {
        return true;
        if (!(paramObject instanceof a))
          return false;
        locala = (a)paramObject;
      }
      while ((this.b == locala.b) && (this.c == locala.c) && (this.d == locala.d) && (this.e == locala.e));
      return false;
    }

    public int hashCode()
    {
      return 7 * this.b + 11 * this.c + 13 * this.d + this.e;
    }

    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.b);
      localStringBuilder.append("-");
      localStringBuilder.append(this.c);
      localStringBuilder.append("-");
      localStringBuilder.append(this.d);
      localStringBuilder.append("-");
      localStringBuilder.append(this.e);
      return localStringBuilder.toString();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.av
 * JD-Core Version:    0.6.0
 */