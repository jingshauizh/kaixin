package com.amap.mapapi.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.amap.mapapi.core.b;
import com.amap.mapapi.core.c;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public abstract class MapActivity extends Activity
{
  public static int MAP_MODE_BITMAP;
  public static int MAP_MODE_VECTOR;
  private static String c = null;
  Thread a = new ab(this);
  boolean b = false;
  private Timer d = null;
  private Handler e = new z(this);
  private TimerTask f = new aa(this);
  private ArrayList<MapView> g = new ArrayList();
  private int h = MAP_MODE_BITMAP;

  static
  {
    MAP_MODE_VECTOR = 1;
    MAP_MODE_BITMAP = 2;
  }

  public static String getMapApiKey()
  {
    return c;
  }

  void a(MapView paramMapView, Context paramContext, String paramString1, String paramString2)
  {
    c.f = 1 + c.f;
    paramMapView.a(paramContext, paramString1);
    this.g.add(paramMapView);
    if ((c == null) || (c.length() < 15))
      c = paramString2;
    new ak(paramContext).a();
  }

  public int getMapMode()
  {
    return this.h;
  }

  protected boolean isLocationDisplayed()
  {
    return false;
  }

  protected boolean isRouteDisplayed()
  {
    return false;
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    b.a(this);
    if (this.d == null)
    {
      this.d = new Timer();
      this.d.schedule(this.f, 10000L, 1000L);
    }
    this.a.start();
  }

  protected void onDestroy()
  {
    super.onDestroy();
    c = null;
    if (this.d != null)
    {
      this.d.cancel();
      this.d = null;
    }
    int i = this.g.size();
    int j = 0;
    if (j < i)
    {
      MapView localMapView = (MapView)this.g.get(0);
      if (localMapView == null);
      while (true)
      {
        j++;
        break;
        int k = localMapView.getChildCount();
        for (int m = 0; m < k; m++)
        {
          if (localMapView.getChildAt(m) == null)
            continue;
          if (localMapView.getChildAt(m).getBackground() != null)
            localMapView.getChildAt(m).getBackground().setCallback(null);
          localMapView.getChildAt(m).setBackgroundDrawable(null);
        }
        ai localai = localMapView.a();
        if (localai != null)
          localai.c.c();
        localMapView.d();
        if (localMapView.a != null);
        try
        {
          if ((localMapView.b != null) && (!localMapView.b.isRecycled()))
            localMapView.b.recycle();
          localMapView.b = null;
          this.g.remove(0);
          c.f = -1 + c.f;
        }
        catch (Throwable localThrowable)
        {
          while (true)
            localThrowable.printStackTrace();
        }
      }
    }
    System.gc();
  }

  protected void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
  }

  protected void onPause()
  {
    super.onPause();
    int i = this.g.size();
    int j = 0;
    if (j < i)
    {
      MapView localMapView = (MapView)this.g.get(j);
      if (localMapView == null);
      while (true)
      {
        j++;
        break;
        ai localai = localMapView.a();
        if (localai == null)
          continue;
        localai.c.e();
      }
    }
  }

  protected void onRestart()
  {
    super.onRestart();
    int i = this.g.size();
    int j = 0;
    if (j < i)
    {
      MapView localMapView = (MapView)this.g.get(j);
      if (localMapView == null);
      while (true)
      {
        j++;
        break;
        ai localai = localMapView.a();
        if (localai == null)
          continue;
        localai.c.f();
      }
    }
  }

  protected void onResume()
  {
    super.onResume();
    int i = this.g.size();
    int j = 0;
    if (j < i)
    {
      MapView localMapView = (MapView)this.g.get(j);
      if (localMapView == null);
      while (true)
      {
        j++;
        break;
        ai localai = localMapView.a();
        if (localai == null)
          continue;
        localai.c.d();
      }
    }
  }

  protected void onStop()
  {
    super.onStop();
    int i = this.g.size();
    int j = 0;
    if (j < i)
    {
      MapView localMapView = (MapView)this.g.get(j);
      if (localMapView == null);
      while (true)
      {
        j++;
        break;
        ai localai = localMapView.a();
        if (localai == null)
          continue;
        localai.c.a();
      }
    }
  }

  public void setCachInInstalledPackaget(boolean paramBoolean)
  {
    this.b = paramBoolean;
  }

  public void setMapMode(int paramInt)
  {
    this.h = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.MapActivity
 * JD-Core Version:    0.6.0
 */