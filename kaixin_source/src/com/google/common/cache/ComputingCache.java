package com.google.common.cache;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.io.Serializable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

class ComputingCache<K, V> extends AbstractCache<K, V>
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  final CustomConcurrentHashMap<K, V> map;

  ComputingCache(CacheBuilder<? super K, ? super V> paramCacheBuilder, Supplier<? extends AbstractCache.StatsCounter> paramSupplier, CacheLoader<? super K, V> paramCacheLoader)
  {
    this.map = new CustomConcurrentHashMap(paramCacheBuilder, paramSupplier, paramCacheLoader);
  }

  public ConcurrentMap<K, V> asMap()
  {
    return this.map;
  }

  public void cleanUp()
  {
    this.map.cleanUp();
  }

  public V get(K paramK)
    throws ExecutionException
  {
    return this.map.getOrCompute(paramK);
  }

  public void invalidate(Object paramObject)
  {
    Preconditions.checkNotNull(paramObject);
    this.map.remove(paramObject);
  }

  public void invalidateAll()
  {
    this.map.clear();
  }

  public long size()
  {
    return this.map.longSize();
  }

  public CacheStats stats()
  {
    AbstractCache.SimpleStatsCounter localSimpleStatsCounter = new AbstractCache.SimpleStatsCounter();
    CustomConcurrentHashMap.Segment[] arrayOfSegment = this.map.segments;
    int i = arrayOfSegment.length;
    for (int j = 0; j < i; j++)
      localSimpleStatsCounter.incrementBy(arrayOfSegment[j].statsCounter);
    return localSimpleStatsCounter.snapshot();
  }

  Object writeReplace()
  {
    return this.map.cacheSerializationProxy();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.cache.ComputingCache
 * JD-Core Version:    0.6.0
 */