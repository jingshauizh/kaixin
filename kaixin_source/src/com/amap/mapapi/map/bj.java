package com.amap.mapapi.map;

import android.graphics.Matrix;
import android.graphics.Point;
import android.view.animation.Animation.AnimationListener;
import com.amap.mapapi.core.GeoPoint;

class bj extends a
{
  static float h = 1.0F;
  public MapView e;
  public float f;
  public float g;
  public int i = -1;
  public boolean j = false;
  private Animation.AnimationListener k;
  private float l;
  private float m;
  private float n;
  private boolean o;
  private boolean p = false;

  public bj(MapView paramMapView, Animation.AnimationListener paramAnimationListener)
  {
    super(160, 40);
    this.e = paramMapView;
    this.k = paramAnimationListener;
  }

  protected void a()
  {
    if (this.o)
      h += this.n;
    while (true)
    {
      Matrix localMatrix = new Matrix();
      localMatrix.setScale(h, h, this.f, this.g);
      this.e.b().b(h);
      this.e.b().b(localMatrix);
      return;
      h -= this.n;
    }
  }

  public void a(float paramFloat1, int paramInt, boolean paramBoolean, float paramFloat2, float paramFloat3)
  {
    this.o = paramBoolean;
    this.f = paramFloat2;
    this.g = paramFloat3;
    this.l = paramFloat1;
    h = this.l;
    if (this.o)
    {
      this.n = (this.l * this.d / this.c);
      this.m = (2.0F * this.l);
      return;
    }
    this.n = (0.5F * this.l * this.d / this.c);
    this.m = (0.5F * this.l);
  }

  public void a(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
  {
    this.e.c[0] = this.e.c[1];
    this.e.c[1] = paramInt;
    if (this.e.c[0] == this.e.c[1])
      return;
    this.e.getZoomMgr().d();
    this.e.a().a(this.e.getOverlayDrawing());
    if (!f())
    {
      this.c = 160;
      a(this.e.b().b(), paramInt, paramBoolean, paramFloat1, paramFloat2);
      this.e.a().d.a(true);
      this.e.a().d.e = true;
      this.k.onAnimationStart(null);
      super.c();
      return;
    }
    this.p = true;
    d();
    a(this.m, paramInt, paramBoolean, paramFloat1, paramFloat2);
    this.e.a().d.a(true);
    this.e.a().d.e = true;
    this.k.onAnimationStart(null);
    super.c();
    this.p = false;
  }

  protected void b()
  {
    if (this.p)
      return;
    this.e.a().d.e = false;
    if (this.j == true)
    {
      Point localPoint1 = new Point((int)this.f, (int)this.g);
      GeoPoint localGeoPoint1 = this.e.getProjection().fromPixels((int)this.f, (int)this.g);
      this.e.a().g.j = this.e.a().g.a(localGeoPoint1);
      this.e.a().g.a(localPoint1);
      this.e.a().b.a(false, false);
    }
    this.e.getController().setZoom(this.i);
    this.k.onAnimationEnd(null);
    if (this.j == true)
    {
      Point localPoint2 = new Point(this.e.a().b.c() / 2, this.e.a().b.d() / 2);
      GeoPoint localGeoPoint2 = this.e.getProjection().fromPixels(this.e.a().b.c() / 2, this.e.a().b.d() / 2);
      this.e.a().g.j = this.e.a().g.a(localGeoPoint2);
      this.e.a().g.a(localPoint2);
      this.e.a().b.a(false, false);
    }
    this.e.a(0);
    h = 1.0F;
    aj.j = 1.0F;
    this.e.a().a(true);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.bj
 * JD-Core Version:    0.6.0
 */