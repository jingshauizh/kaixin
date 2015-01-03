package com.google.zxing.common;

import java.util.Vector;

public final class Collections
{
  public static void insertionSort(Vector paramVector, Comparator paramComparator)
  {
    int i = paramVector.size();
    int j = 1;
    if (j >= i)
      return;
    Object localObject1 = paramVector.elementAt(j);
    for (int k = j - 1; ; k--)
    {
      Object localObject2;
      if (k >= 0)
      {
        localObject2 = paramVector.elementAt(k);
        if (paramComparator.compare(localObject2, localObject1) > 0);
      }
      else
      {
        paramVector.setElementAt(localObject1, k + 1);
        j++;
        break;
      }
      paramVector.setElementAt(localObject2, k + 1);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.common.Collections
 * JD-Core Version:    0.6.0
 */