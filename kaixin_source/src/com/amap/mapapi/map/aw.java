package com.amap.mapapi.map;

import java.util.ArrayList;
import java.util.LinkedList;

class aw extends at<av.a>
{
  protected ArrayList<av.a> b(int paramInt, boolean paramBoolean)
  {
    int i = 0;
    monitorenter;
    while (true)
    {
      int j;
      int k;
      try
      {
        LinkedList localLinkedList = this.a;
        if (localLinkedList != null)
          continue;
        Object localObject2 = null;
        return localObject2;
        j = this.a.size();
        if (paramInt <= j)
          continue;
        paramInt = j;
        ArrayList localArrayList = new ArrayList(paramInt);
        k = 0;
        if (i >= j)
          continue;
        if (this.a != null)
          continue;
        localObject2 = null;
        continue;
        av.a locala = (av.a)this.a.get(i);
        if (locala != null)
          continue;
        i3 = i;
        i4 = k;
        i5 = j;
        break label256;
        int m = locala.a;
        if (!paramBoolean)
          continue;
        if (m == 0)
        {
          localArrayList.add(locala);
          this.a.remove(i);
          int i7 = j - 1;
          int i8 = i - 1;
          int i9 = k + 1;
          i3 = i8;
          i4 = i9;
          i5 = i7;
          break label276;
          b();
          localObject2 = localArrayList;
          continue;
          if (m < 0)
          {
            localArrayList.add(locala);
            this.a.remove(i);
            int n = j - 1;
            int i1 = i - 1;
            int i2 = k + 1;
            i3 = i1;
            i4 = i2;
            i5 = n;
          }
        }
      }
      finally
      {
        monitorexit;
      }
      int i3 = i;
      int i4 = k;
      int i5 = j;
      label256: label276: 
      while (i4 < paramInt)
      {
        int i6 = i3 + 1;
        j = i5;
        k = i4;
        i = i6;
        break;
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.aw
 * JD-Core Version:    0.6.0
 */