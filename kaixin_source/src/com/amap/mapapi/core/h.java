package com.amap.mapapi.core;

import java.util.LinkedList;

public class h<T> extends t<T>
{
  public void a(T paramT)
  {
    if (!this.a.contains(paramT))
      this.a.add(paramT);
  }

  public void b(T paramT)
  {
    for (int i = 0; ; i++)
    {
      if (i < this.a.size())
      {
        if (paramT != this.a.get(i))
          continue;
        this.a.remove(i);
      }
      return;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.core.h
 * JD-Core Version:    0.6.0
 */