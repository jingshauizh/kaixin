package com.amap.mapapi.map;

import android.os.Handler;
import java.util.TimerTask;

class aa extends TimerTask
{
  aa(MapActivity paramMapActivity)
  {
  }

  public void run()
  {
    MapActivity.b(this.a).sendEmptyMessage(1);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.aa
 * JD-Core Version:    0.6.0
 */