package com.google.common.cache;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

@Beta
public abstract interface Cache<K, V> extends Function<K, V>
{
  public abstract V apply(K paramK);

  public abstract ConcurrentMap<K, V> asMap();

  public abstract void cleanUp();

  public abstract V get(K paramK)
    throws ExecutionException;

  public abstract V getUnchecked(K paramK);

  public abstract void invalidate(Object paramObject);

  public abstract void invalidateAll();

  public abstract long size();

  public abstract CacheStats stats();
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.cache.Cache
 * JD-Core Version:    0.6.0
 */