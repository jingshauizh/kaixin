package com.amap.mapapi.map;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.amap.mapapi.core.a;
import java.util.ArrayList;

class z extends Handler
{
  z(MapActivity paramMapActivity)
  {
  }

  public void handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
    case 1:
      int i;
      int j;
      do
      {
        return;
        i = MapActivity.a(this.a).size();
        j = 0;
      }
      while (j >= i);
      MapView localMapView = (MapView)MapActivity.a(this.a).get(j);
      if (localMapView == null);
      while (true)
      {
        j++;
        break;
        ai localai = localMapView.a();
        if (localai == null)
          continue;
        localai.c.b();
      }
    case 2:
    }
    Toast.makeText(this.a, a.b, 0).show();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.z
 * JD-Core Version:    0.6.0
 */