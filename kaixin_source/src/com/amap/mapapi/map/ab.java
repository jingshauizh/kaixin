package com.amap.mapapi.map;

import android.os.Handler;
import android.util.Log;
import com.amap.mapapi.core.AMapException;
import com.amap.mapapi.core.a;
import com.amap.mapapi.core.b;

class ab extends Thread
{
  ab(MapActivity paramMapActivity)
  {
  }

  public void run()
  {
    try
    {
      b.a(this.a);
      a.a(this.a);
      if (a.a == 0)
        MapActivity.b(this.a).sendEmptyMessage(2);
      return;
    }
    catch (AMapException localAMapException)
    {
      Log.i("AuthFailure", localAMapException.getErrorMessage());
      localAMapException.printStackTrace();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.ab
 * JD-Core Version:    0.6.0
 */