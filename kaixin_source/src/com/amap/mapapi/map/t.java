package com.amap.mapapi.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.c;
import com.amap.mapapi.core.e;
import com.amap.mapapi.core.n;

class t
  implements GestureDetector.OnGestureListener
{
  public static t a = null;
  public static Drawable b = null;
  public static Bitmap c = null;
  protected MapView d;
  protected View e;
  protected GeoPoint f;
  protected long g = -1L;
  protected MapView.LayoutParams h;

  public t(MapView paramMapView, View paramView, GeoPoint paramGeoPoint, Drawable paramDrawable, MapView.LayoutParams paramLayoutParams)
  {
    this.d = paramMapView;
    this.e = paramView;
    this.f = paramGeoPoint;
    this.h = paramLayoutParams;
    a(paramDrawable);
  }

  private static void a(Context paramContext)
  {
    byte[] arrayOfByte = { 1, 2, 2, 9, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 19, 0, 0, 0, 15, 0, 0, 0, 36, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, -117, 0, 0, 0, 15, 0, 0, 0, 29, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, -1, -1, -1, -14, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 };
    Rect localRect = new Rect(20, 15, 19, 36);
    c = c.g.a(paramContext, "popup_bg.9.png");
    b = new NinePatchDrawable(c, arrayOfByte, localRect, null);
  }

  private void a(Drawable paramDrawable)
  {
    if (paramDrawable == null)
    {
      if (b == null)
        a(this.d.getContext());
      paramDrawable = b;
      paramDrawable.setAlpha(255);
    }
    this.e.setBackgroundDrawable(paramDrawable);
  }

  private boolean d()
  {
    return a == this;
  }

  public void a()
  {
    if ((c != null) && (!c.isRecycled()))
    {
      c.recycle();
      c = null;
    }
  }

  public void b()
  {
    if (d())
      return;
    if (a != null)
      a.c();
    a = this;
    this.d.b().a(this);
    if (this.h == null)
      this.h = new MapView.LayoutParams(-2, -2, this.f, 25, 5, 85);
    this.d.addView(this.e, this.h);
    this.g = e.a();
    this.d.b().a(this.g);
  }

  public void c()
  {
    if (!d());
    do
      return;
    while (this.d == null);
    a = null;
    this.d.removeView(this.e);
    this.d.b().b(this);
  }

  public boolean onDown(MotionEvent paramMotionEvent)
  {
    return false;
  }

  public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    return false;
  }

  public void onLongPress(MotionEvent paramMotionEvent)
  {
  }

  public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    return false;
  }

  public void onShowPress(MotionEvent paramMotionEvent)
  {
  }

  public boolean onSingleTapUp(MotionEvent paramMotionEvent)
  {
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.t
 * JD-Core Version:    0.6.0
 */