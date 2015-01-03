package com.amap.mapapi.core;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

public class r
{
  public static Drawable a;
  public static Drawable b;
  public static Drawable c;
  public static Drawable d;
  public static Drawable e;
  public static Drawable f;
  public static Drawable g;
  public static Drawable h;
  public static Drawable i;
  public static Drawable j;
  public static Paint k;
  public static Paint l;
  public static Paint m;
  public static boolean n = false;

  public static void a()
  {
    n = false;
    if (a != null)
    {
      Bitmap localBitmap8 = ((BitmapDrawable)a).getBitmap();
      if ((localBitmap8 != null) && (!localBitmap8.isRecycled()))
      {
        localBitmap8.recycle();
        a = null;
      }
    }
    if (b != null)
    {
      Bitmap localBitmap7 = ((BitmapDrawable)b).getBitmap();
      if ((localBitmap7 != null) && (!localBitmap7.isRecycled()))
      {
        localBitmap7.recycle();
        b = null;
      }
    }
    if (c != null)
    {
      Bitmap localBitmap6 = ((BitmapDrawable)c).getBitmap();
      if ((localBitmap6 != null) && (!localBitmap6.isRecycled()))
      {
        localBitmap6.recycle();
        c = null;
      }
    }
    if (d != null)
    {
      Bitmap localBitmap5 = ((BitmapDrawable)d).getBitmap();
      if ((localBitmap5 != null) && (!localBitmap5.isRecycled()))
      {
        localBitmap5.recycle();
        d = null;
      }
    }
    if (e != null)
    {
      Bitmap localBitmap4 = ((BitmapDrawable)e).getBitmap();
      if ((localBitmap4 != null) && (!localBitmap4.isRecycled()))
      {
        localBitmap4.recycle();
        e = null;
      }
    }
    if (f != null)
    {
      Bitmap localBitmap3 = ((BitmapDrawable)f).getBitmap();
      if ((localBitmap3 != null) && (!localBitmap3.isRecycled()))
      {
        localBitmap3.recycle();
        f = null;
      }
    }
    if (g != null)
    {
      Bitmap localBitmap2 = ((BitmapDrawable)g).getBitmap();
      if ((localBitmap2 != null) && (!localBitmap2.isRecycled()))
      {
        localBitmap2.recycle();
        g = null;
      }
    }
    if (h != null)
      h = null;
    if (i != null)
      i = null;
    if (j != null)
    {
      Bitmap localBitmap1 = ((BitmapDrawable)j).getBitmap();
      if ((localBitmap1 != null) && (!localBitmap1.isRecycled()))
      {
        localBitmap1.recycle();
        j = null;
      }
    }
  }

  public static void a(Context paramContext)
  {
    if (n)
      return;
    l = new Paint();
    l.setStyle(Paint.Style.STROKE);
    l.setColor(Color.rgb(54, 114, 227));
    l.setAlpha(180);
    l.setStrokeWidth(5.5F);
    l.setStrokeJoin(Paint.Join.ROUND);
    l.setStrokeCap(Paint.Cap.ROUND);
    l.setAntiAlias(true);
    k = new Paint();
    k.setStyle(Paint.Style.STROKE);
    k.setColor(Color.rgb(54, 114, 227));
    k.setAlpha(150);
    k.setStrokeWidth(5.5F);
    k.setStrokeJoin(Paint.Join.ROUND);
    k.setStrokeCap(Paint.Cap.ROUND);
    k.setAntiAlias(true);
    m = new Paint();
    m.setStyle(Paint.Style.STROKE);
    m.setColor(Color.rgb(54, 114, 227));
    m.setAlpha(180);
    m.setStrokeWidth(5.5F);
    m.setStrokeJoin(Paint.Join.ROUND);
    m.setStrokeCap(Paint.Cap.ROUND);
    m.setAntiAlias(true);
    new DisplayMetrics();
    DisplayMetrics localDisplayMetrics = paramContext.getResources().getDisplayMetrics();
    long l1 = localDisplayMetrics.widthPixels * localDisplayMetrics.heightPixels;
    if (l1 > 153600L)
      a(paramContext, 1);
    while (true)
    {
      Rect localRect1 = new Rect(8, 4, 16, 14);
      byte[] arrayOfByte1 = { 1, 2, 2, 9, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 16, 0, 0, 0, 4, 0, 0, 0, 14, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 18, 0, 0, 0, 4, 0, 0, 0, 17, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 };
      Rect localRect2 = new Rect(17, 5, 8, 12);
      byte[] arrayOfByte2 = { 1, 2, 2, 9, 0, 0, 0, 0, 0, 0, 0, 0, 17, 0, 0, 0, 8, 0, 0, 0, 5, 0, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 14, 0, 0, 0, 26, 0, 0, 0, 5, 0, 0, 0, 19, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 };
      h = c.g.a(paramContext, "left_back.png", arrayOfByte1, localRect1);
      i = c.g.a(paramContext, "right_back.png", arrayOfByte2, localRect2);
      n = true;
      return;
      if (l1 < 153600L)
      {
        a(paramContext, 3);
        continue;
      }
      a(paramContext, 2);
    }
  }

  static void a(Context paramContext, int paramInt)
  {
    switch (paramInt)
    {
    default:
    case 1:
    case 2:
    case 3:
    }
    try
    {
      a = c.g.b(paramContext, "start.png");
      b = c.g.b(paramContext, "end.png");
      c = c.g.b(paramContext, "foot.png");
      e = c.g.b(paramContext, "bus_w.png");
      d = c.g.b(paramContext, "car.png");
      f = c.g.b(paramContext, "starticon.png");
      g = c.g.b(paramContext, "endicon.png");
      j = c.g.b(paramContext, "route_coner.png");
      return;
      try
      {
        a = c.g.b(paramContext, "start_w.png");
        b = c.g.b(paramContext, "end_w.png");
        c = c.g.b(paramContext, "foot_w.png");
        e = c.g.b(paramContext, "bus_w.png");
        d = c.g.b(paramContext, "car_w.png");
        f = c.g.b(paramContext, "starticon_w.png");
        g = c.g.b(paramContext, "endicon_w.png");
        j = c.g.b(paramContext, "route_coner_w.png");
        return;
      }
      catch (Exception localException3)
      {
        return;
      }
      try
      {
        a = c.g.b(paramContext, "start.png");
        b = c.g.b(paramContext, "end.png");
        c = c.g.b(paramContext, "foot.png");
        e = c.g.b(paramContext, "bus.png");
        d = c.g.b(paramContext, "car.png");
        f = c.g.b(paramContext, "starticon.png");
        g = c.g.b(paramContext, "endicon.png");
        j = c.g.b(paramContext, "route_coner.png");
        return;
      }
      catch (Exception localException2)
      {
        return;
      }
      try
      {
        a = c.g.b(paramContext, "start.png");
        b = c.g.b(paramContext, "end.png");
        c = c.g.b(paramContext, "foot.png");
        e = c.g.b(paramContext, "bus.png");
        d = c.g.b(paramContext, "car.png");
        f = c.g.b(paramContext, "starticon.png");
        g = c.g.b(paramContext, "endicon.png");
        j = c.g.b(paramContext, "route_coner_q.png");
        return;
      }
      catch (Exception localException1)
      {
        return;
      }
    }
    catch (Exception localException4)
    {
    }
  }

  public static Drawable b(Context paramContext)
  {
    Rect localRect = new Rect(8, 4, 16, 14);
    byte[] arrayOfByte = { 1, 2, 2, 9, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 16, 0, 0, 0, 4, 0, 0, 0, 14, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 18, 0, 0, 0, 4, 0, 0, 0, 17, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 };
    return c.g.a(paramContext, "left_back.png", arrayOfByte, localRect);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.core.r
 * JD-Core Version:    0.6.0
 */