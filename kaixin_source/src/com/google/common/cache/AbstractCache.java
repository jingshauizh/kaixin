package com.google.common.cache;

import com.google.common.annotations.Beta;
import com.google.common.util.concurrent.UncheckedExecutionException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

@Beta
public abstract class AbstractCache<K, V>
  implements Cache<K, V>
{
  public final V apply(K paramK)
  {
    return getUnchecked(paramK);
  }

  public ConcurrentMap<K, V> asMap()
  {
    throw new UnsupportedOperationException();
  }

  public void cleanUp()
  {
  }

  public V getUnchecked(K paramK)
  {
    try
    {
      Object localObject = get(paramK);
      return localObject;
    }
    catch (ExecutionException localExecutionException)
    {
    }
    throw new UncheckedExecutionException(localExecutionException.getCause());
  }

  public void invalidate(Object paramObject)
  {
    throw new UnsupportedOperationException();
  }

  public void invalidateAll()
  {
    throw new UnsupportedOperationException();
  }

  public long size()
  {
    throw new UnsupportedOperationException();
  }

  public CacheStats stats()
  {
    throw new UnsupportedOperationException();
  }

  @Beta
  public static class SimpleStatsCounter
    implements AbstractCache.StatsCounter
  {
    private final AtomicLong evictionCount = new AtomicLong();
    private final AtomicLong hitCount = new AtomicLong();
    private final AtomicLong loadExceptionCount = new AtomicLong();
    private final AtomicLong loadSuccessCount = new AtomicLong();
    private final AtomicLong missCount = new AtomicLong();
    private final AtomicLong totalLoadTime = new AtomicLong();

    public void incrementBy(AbstractCache.StatsCounter paramStatsCounter)
    {
      CacheStats localCacheStats = paramStatsCounter.snapshot();
      this.hitCount.addAndGet(localCacheStats.hitCount());
      this.missCount.addAndGet(localCacheStats.missCount());
      this.loadSuccessCount.addAndGet(localCacheStats.loadSuccessCount());
      this.loadExceptionCount.addAndGet(localCacheStats.loadExceptionCount());
      this.totalLoadTime.addAndGet(localCacheStats.totalLoadTime());
      this.evictionCount.addAndGet(localCacheStats.evictionCount());
    }

    public void recordConcurrentMiss()
    {
      this.missCount.incrementAndGet();
    }

    public void recordEviction()
    {
      this.evictionCount.incrementAndGet();
    }

    public void recordHit()
    {
      this.hitCount.incrementAndGet();
    }

    public void recordLoadException(long paramLong)
    {
      this.missCount.incrementAndGet();
      this.loadExceptionCount.incrementAndGet();
      this.totalLoadTime.addAndGet(paramLong);
    }

    public void recordLoadSuccess(long paramLong)
    {
      this.missCount.incrementAndGet();
      this.loadSuccessCount.incrementAndGet();
      this.totalLoadTime.addAndGet(paramLong);
    }

    public CacheStats snapshot()
    {
      return new CacheStats(this.hitCount.get(), this.missCount.get(), this.loadSuccessCount.get(), this.loadExceptionCount.get(), this.totalLoadTime.get(), this.evictionCount.get());
    }
  }

  @Beta
  public static abstract interface StatsCounter
  {
    public abstract void recordConcurrentMiss();

    public abstract void recordEviction();

    public abstract void recordHit();

    public abstract void recordLoadException(long paramLong);

    public abstract void recordLoadSuccess(long paramLong);

    public abstract CacheStats snapshot();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.cache.AbstractCache
 * JD-Core Version:    0.6.0
 */