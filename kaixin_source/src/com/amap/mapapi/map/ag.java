package com.amap.mapapi.map;

import android.view.View;
import android.view.View.OnClickListener;

class ag
  implements View.OnClickListener
{
  ag(MapView.e parame)
  {
  }

  public void onClick(View paramView)
  {
    int i = 0;
    if (i < 4)
      if (MapView.e.a(this.a)[i].equals(paramView))
        if (i > 1)
          this.a.a = true;
    while (true)
    {
      if (MapView.e.b(this.a) != null)
        MapView.e.b(this.a).a(i);
      return;
      this.a.a = false;
      continue;
      i++;
      break;
      i = -1;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.ag
 * JD-Core Version:    0.6.0
 */