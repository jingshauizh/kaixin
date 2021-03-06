package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Comparator;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ForwardingSortedSetMultimap<K, V> extends ForwardingSetMultimap<K, V>
  implements SortedSetMultimap<K, V>
{
  protected abstract SortedSetMultimap<K, V> delegate();

  public SortedSet<V> get(@Nullable K paramK)
  {
    return delegate().get(paramK);
  }

  public SortedSet<V> removeAll(@Nullable Object paramObject)
  {
    return delegate().removeAll(paramObject);
  }

  public SortedSet<V> replaceValues(K paramK, Iterable<? extends V> paramIterable)
  {
    return delegate().replaceValues(paramK, paramIterable);
  }

  public Comparator<? super V> valueComparator()
  {
    return delegate().valueComparator();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.ForwardingSortedSetMultimap
 * JD-Core Version:    0.6.0
 */