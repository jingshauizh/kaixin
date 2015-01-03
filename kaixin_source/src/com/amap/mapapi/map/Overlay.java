package com.amap.mapapi.map;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.amap.mapapi.core.GeoPoint;

public class Overlay
{
  protected static final float SHADOW_X_SKEW = -0.9F;
  protected static final float SHADOW_Y_SCALE = 0.5F;

  static void a(Canvas paramCanvas, Drawable paramDrawable, int paramInt1, int paramInt2)
  {
    Rect localRect = paramDrawable.getBounds();
    paramDrawable.setBounds(paramInt1 + localRect.left, paramInt2 + localRect.top, paramInt1 + localRect.right, paramInt2 + localRect.bottom);
    paramDrawable.draw(paramCanvas);
    paramDrawable.setBounds(localRect.left - paramInt1, localRect.top - paramInt2, localRect.right - paramInt1, localRect.bottom - paramInt2);
  }

  protected static void drawAt(Canvas paramCanvas, Drawable paramDrawable, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      Drawable localDrawable = new as().a(paramDrawable);
      as.a(localDrawable, paramDrawable);
      paramDrawable = localDrawable;
    }
    a(paramCanvas, paramDrawable, paramInt1, paramInt2);
  }

  public void draw(Canvas paramCanvas, MapView paramMapView, boolean paramBoolean)
  {
  }

  public boolean draw(Canvas paramCanvas, MapView paramMapView, boolean paramBoolean, long paramLong)
  {
    draw(paramCanvas, paramMapView, paramBoolean);
    return false;
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent, MapView paramMapView)
  {
    return false;
  }

  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent, MapView paramMapView)
  {
    return false;
  }

  public boolean onTap(GeoPoint paramGeoPoint, MapView paramMapView)
  {
    return false;
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent, MapView paramMapView)
  {
    return false;
  }

  public boolean onTrackballEvent(MotionEvent paramMotionEvent, MapView paramMapView)
  {
    return false;
  }

  public static abstract interface Snappable
  {
    public abstract boolean onSnapToItem(int paramInt1, int paramInt2, Point paramPoint, MapView paramMapView);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.Overlay
 * JD-Core Version:    0.6.0
 */