package com.amap.mapapi.map;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

class m extends af
{
  private SensorManager a;
  private Sensor b;
  private SensorEventListener c = null;

  public m(ai paramai, Context paramContext)
  {
    super(paramai, paramContext);
    this.a = ((SensorManager)paramContext.getSystemService("sensor"));
    this.b = this.a.getDefaultSensor(3);
  }

  private void g()
  {
    if (this.c != null);
    try
    {
      this.a.unregisterListener(this.c);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private boolean i()
  {
    SensorEventListener localSensorEventListener = this.c;
    int i = 0;
    if (localSensorEventListener != null);
    try
    {
      boolean bool = this.a.registerListener(this.c, this.b, 1);
      i = bool;
      return i;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public boolean a(SensorEventListener paramSensorEventListener)
  {
    g();
    this.c = paramSensorEventListener;
    return i();
  }

  public void a_()
  {
    i();
  }

  public void c()
  {
    g();
  }

  public void e()
  {
    g();
    this.c = null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.m
 * JD-Core Version:    0.6.0
 */