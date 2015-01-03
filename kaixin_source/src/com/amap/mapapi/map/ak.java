package com.amap.mapapi.map;

import android.content.Context;
import com.amap.mapapi.core.b;

public class ak
{
  private Context a;
  private Runnable b = new al(this);

  public ak(Context paramContext)
  {
    this.a = paramContext;
  }

  private byte[] b()
  {
    b localb = b.a(this.a);
    if (localb != null)
      return localb.b().getBytes();
    return null;
  }

  public void a()
  {
    new Thread(this.b).start();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.ak
 * JD-Core Version:    0.6.0
 */