package com.amap.mapapi.map;

import com.amap.mapapi.core.t;

class u extends t<av.a>
{
  void a(av.a parama)
  {
    monitorenter;
    try
    {
      remove(parama);
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  boolean b(av.a parama)
  {
    boolean bool1 = true;
    monitorenter;
    try
    {
      boolean bool2 = contains(parama);
      if (bool2 == bool1)
        bool1 = false;
      while (true)
      {
        return bool1;
        c(parama);
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.u
 * JD-Core Version:    0.6.0
 */