package com.tencent.mm.algorithm;

import java.util.Comparator;
import java.util.Map.Entry;

class LRUMap$1
  implements Comparator<Map.Entry<K, LRUMap<K, O>.TimeVal<O>>>
{
  public int compare(Map.Entry<K, LRUMap<K, O>.TimeVal<O>> paramEntry1, Map.Entry<K, LRUMap<K, O>.TimeVal<O>> paramEntry2)
  {
    return ((LRUMap.TimeVal)paramEntry1.getValue()).t.compareTo(((LRUMap.TimeVal)paramEntry2.getValue()).t);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.algorithm.LRUMap.1
 * JD-Core Version:    0.6.0
 */