package com.amap.mapapi.map;

import com.amap.mapapi.core.AMapException;
import com.amap.mapapi.core.e;
import com.amap.mapapi.core.g;

class al
  implements Runnable
{
  al(ak paramak)
  {
  }

  public void run()
  {
    try
    {
      g.a("http://api.amap.com/webapi/count", ak.a(this.a), e.b(ak.b(this.a)));
      return;
    }
    catch (AMapException localAMapException)
    {
      localAMapException.printStackTrace();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.al
 * JD-Core Version:    0.6.0
 */