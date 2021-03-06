package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import java.util.SortedMap;

@Beta
@GwtCompatible
public abstract interface SortedMapDifference<K, V> extends MapDifference<K, V>
{
  public abstract SortedMap<K, MapDifference.ValueDifference<V>> entriesDiffering();

  public abstract SortedMap<K, V> entriesInCommon();

  public abstract SortedMap<K, V> entriesOnlyOnLeft();

  public abstract SortedMap<K, V> entriesOnlyOnRight();
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.SortedMapDifference
 * JD-Core Version:    0.6.0
 */