package com.google.common.cache;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingObject;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nullable;

@Beta
public abstract class ForwardingCache<K, V> extends ForwardingObject
  implements Cache<K, V>
{
  @Deprecated
  @Nullable
  public V apply(@Nullable K paramK)
  {
    return delegate().apply(paramK);
  }

  public ConcurrentMap<K, V> asMap()
  {
    return delegate().asMap();
  }

  public void cleanUp()
  {
    delegate().cleanUp();
  }

  protected abstract Cache<K, V> delegate();

  @Nullable
  public V get(@Nullable K paramK)
    throws ExecutionException
  {
    return delegate().get(paramK);
  }

  @Nullable
  public V getUnchecked(@Nullable K paramK)
  {
    return delegate().getUnchecked(paramK);
  }

  public void invalidate(@Nullable Object paramObject)
  {
    delegate().invalidate(paramObject);
  }

  public void invalidateAll()
  {
    delegate().invalidateAll();
  }

  public long size()
  {
    return delegate().size();
  }

  public CacheStats stats()
  {
    return delegate().stats();
  }

  @Beta
  public static abstract class SimpleForwardingCache<K, V> extends ForwardingCache<K, V>
  {
    private final Cache<K, V> delegate;

    protected SimpleForwardingCache(Cache<K, V> paramCache)
    {
      this.delegate = ((Cache)Preconditions.checkNotNull(paramCache));
    }

    protected final Cache<K, V> delegate()
    {
      return this.delegate;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.cache.ForwardingCache
 * JD-Core Version:    0.6.0
 */