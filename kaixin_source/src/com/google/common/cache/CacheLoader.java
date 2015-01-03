package com.google.common.cache;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.io.Serializable;

@Beta
public abstract class CacheLoader<K, V>
{
  public static <K, V> CacheLoader<K, V> from(Function<K, V> paramFunction)
  {
    return new FunctionToCacheLoader(paramFunction);
  }

  public static <V> CacheLoader<Object, V> from(Supplier<V> paramSupplier)
  {
    return new SupplierToCacheLoader(paramSupplier);
  }

  public abstract V load(K paramK)
    throws Exception;

  private static final class FunctionToCacheLoader<K, V> extends CacheLoader<K, V>
    implements Serializable
  {
    private static final long serialVersionUID;
    private final Function<K, V> computingFunction;

    public FunctionToCacheLoader(Function<K, V> paramFunction)
    {
      this.computingFunction = ((Function)Preconditions.checkNotNull(paramFunction));
    }

    public V load(K paramK)
    {
      return this.computingFunction.apply(paramK);
    }
  }

  private static final class SupplierToCacheLoader<V> extends CacheLoader<Object, V>
    implements Serializable
  {
    private static final long serialVersionUID;
    private final Supplier<V> computingSupplier;

    public SupplierToCacheLoader(Supplier<V> paramSupplier)
    {
      this.computingSupplier = ((Supplier)Preconditions.checkNotNull(paramSupplier));
    }

    public V load(Object paramObject)
    {
      return this.computingSupplier.get();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.cache.CacheLoader
 * JD-Core Version:    0.6.0
 */