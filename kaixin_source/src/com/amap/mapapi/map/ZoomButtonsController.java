package com.amap.mapapi.map;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

public class ZoomButtonsController
  implements View.OnTouchListener
{
  private MapView.g a;
  private ViewGroup b;
  private OnZoomListener c;

  public ZoomButtonsController(View paramView)
  {
    this.b = ((MapView)paramView);
    this.a = ((MapView)paramView).getZoomMgr();
  }

  public ViewGroup getContainer()
  {
    return this.b;
  }

  public OnZoomListener getOnZoomListener()
  {
    return this.c;
  }

  public View getZoomControls()
  {
    return null;
  }

  public boolean isAutoDismissed()
  {
    return false;
  }

  public boolean isVisible()
  {
    return this.a.b();
  }

  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    return false;
  }

  public void setAutoDismissed(boolean paramBoolean)
  {
  }

  public void setFocusable(boolean paramBoolean)
  {
  }

  public void setOnZoomListener(OnZoomListener paramOnZoomListener)
  {
    this.c = paramOnZoomListener;
  }

  public void setVisible(boolean paramBoolean)
  {
    this.a.a(paramBoolean);
  }

  public void setZoomInEnabled(boolean paramBoolean)
  {
    this.a.f().setEnabled(paramBoolean);
  }

  public void setZoomOutEnabled(boolean paramBoolean)
  {
    this.a.g().setEnabled(paramBoolean);
  }

  public void setZoomSpeed(long paramLong)
  {
  }

  public static abstract interface OnZoomListener
  {
    public abstract void onVisibilityChanged(boolean paramBoolean);

    public abstract void onZoom(boolean paramBoolean);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.ZoomButtonsController
 * JD-Core Version:    0.6.0
 */