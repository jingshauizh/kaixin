package com.amap.mapapi.map;

import android.view.MotionEvent;

public class TrackballGestureDetector
{
  private static TrackballGestureDetector n;
  private boolean a;
  private float b;
  private float c;
  private long d;
  private boolean e;
  private boolean f;
  private boolean g;
  private float h;
  private float i;
  private float j;
  private float k;
  private Runnable l;
  private Thread m;

  private void a()
  {
    if ((this.l == null) || (!this.e));
    do
      return;
    while (this.m != null);
    this.m = new Thread(this.l);
    this.m.start();
  }

  public static TrackballGestureDetector getInstance()
  {
    if (n == null)
      n = new TrackballGestureDetector();
    return n;
  }

  public void analyze(MotionEvent paramMotionEvent)
  {
    int i1 = paramMotionEvent.getAction();
    float f1 = paramMotionEvent.getY();
    float f2 = paramMotionEvent.getX();
    this.f = false;
    this.g = false;
    switch (i1)
    {
    default:
      return;
    case 0:
      this.h = f2;
      this.i = f1;
      this.b = f2;
      this.c = f1;
      this.d = paramMotionEvent.getDownTime();
      this.a = true;
      this.e = false;
    case 2:
    case 1:
    }
    while (true)
    {
      a();
      return;
      if (this.e)
        continue;
      this.j = (this.h - f2);
      this.k = (this.i - f1);
      this.h = f2;
      this.i = f1;
      if (Math.abs(f1 - this.c) + Math.abs(f2 - this.b) <= 0.0F)
        continue;
      this.a = false;
      this.f = true;
      continue;
      if ((!this.a) || (paramMotionEvent.getEventTime() - this.d >= 300L))
        continue;
      this.g = true;
    }
  }

  public float getCurrentDownX()
  {
    if (isTap())
      return this.b;
    return 0.0F;
  }

  public float getCurrentDownY()
  {
    if (isTap())
      return this.c;
    return 0.0F;
  }

  public boolean isScroll()
  {
    return this.f;
  }

  public boolean isTap()
  {
    return this.g;
  }

  public void registerLongPressCallback(Runnable paramRunnable)
  {
    this.l = paramRunnable;
  }

  public float scrollX()
  {
    if (isScroll())
      return this.j;
    return 0.0F;
  }

  public float scrollY()
  {
    if (isScroll())
      return this.k;
    return 0.0F;
  }

  public static abstract interface OnTrackballListener
  {
    public abstract void onTrackballChange(TrackballGestureDetector paramTrackballGestureDetector);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.TrackballGestureDetector
 * JD-Core Version:    0.6.0
 */