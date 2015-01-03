package com.amap.mapapi.map;

import java.util.Vector;

class an extends Vector
{
  protected int a = -1;

  public Object a()
  {
    monitorenter;
    try
    {
      boolean bool = c();
      Object localObject2;
      if (bool)
        localObject2 = null;
      while (true)
      {
        return localObject2;
        localObject2 = super.elementAt(0);
        super.removeElementAt(0);
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject1;
  }

  public Object b()
  {
    monitorenter;
    try
    {
      boolean bool = c();
      if (bool);
      Object localObject2;
      for (Object localObject3 = null; ; localObject3 = localObject2)
      {
        return localObject3;
        localObject2 = super.elementAt(0);
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject1;
  }

  public boolean c()
  {
    return super.isEmpty();
  }

  public void clear()
  {
    monitorenter;
    try
    {
      super.removeAllElements();
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
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.an
 * JD-Core Version:    0.6.0
 */