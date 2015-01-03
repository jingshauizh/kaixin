package com.google.common.cache;

import com.google.common.annotations.Beta;
import com.google.common.base.Ascii;
import com.google.common.base.Equivalence;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.base.Ticker;
import com.google.common.collect.ForwardingConcurrentMap;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.UncheckedExecutionException;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@Beta
public final class CacheBuilder<K, V>
{
  static final Supplier<AbstractCache.SimpleStatsCounter> CACHE_STATS_COUNTER;
  private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
  private static final int DEFAULT_EXPIRATION_NANOS = 0;
  private static final int DEFAULT_INITIAL_CAPACITY = 16;
  static final Supplier<? extends AbstractCache.StatsCounter> DEFAULT_STATS_COUNTER = Suppliers.ofInstance(new AbstractCache.StatsCounter()
  {
    public void recordConcurrentMiss()
    {
    }

    public void recordEviction()
    {
    }

    public void recordHit()
    {
    }

    public void recordLoadException(long paramLong)
    {
    }

    public void recordLoadSuccess(long paramLong)
    {
    }

    public CacheStats snapshot()
    {
      return CacheBuilder.EMPTY_STATS;
    }
  });
  static final CacheStats EMPTY_STATS = new CacheStats(0L, 0L, 0L, 0L, 0L, 0L);
  static final int UNSET_INT = -1;
  int concurrencyLevel = -1;
  long expireAfterAccessNanos = -1L;
  long expireAfterWriteNanos = -1L;
  int initialCapacity = -1;
  Equivalence<Object> keyEquivalence;
  CustomConcurrentHashMap.Strength keyStrength;
  int maximumSize = -1;
  RemovalCause nullRemovalCause;
  RemovalListener<? super K, ? super V> removalListener;
  Ticker ticker;
  Equivalence<Object> valueEquivalence;
  CustomConcurrentHashMap.Strength valueStrength;

  static
  {
    CACHE_STATS_COUNTER = new Supplier()
    {
      public AbstractCache.SimpleStatsCounter get()
      {
        return new AbstractCache.SimpleStatsCounter();
      }
    };
  }

  private void checkExpiration(long paramLong, TimeUnit paramTimeUnit)
  {
    boolean bool1;
    boolean bool2;
    if (this.expireAfterWriteNanos == -1L)
    {
      bool1 = true;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Long.valueOf(this.expireAfterWriteNanos);
      Preconditions.checkState(bool1, "expireAfterWrite was already set to %s ns", arrayOfObject1);
      if (this.expireAfterAccessNanos != -1L)
        break label124;
      bool2 = true;
      label54: Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Long.valueOf(this.expireAfterAccessNanos);
      Preconditions.checkState(bool2, "expireAfterAccess was already set to %s ns", arrayOfObject2);
      if (paramLong < 0L)
        break label130;
    }
    label130: for (boolean bool3 = true; ; bool3 = false)
    {
      Object[] arrayOfObject3 = new Object[2];
      arrayOfObject3[0] = Long.valueOf(paramLong);
      arrayOfObject3[1] = paramTimeUnit;
      Preconditions.checkArgument(bool3, "duration cannot be negative: %s %s", arrayOfObject3);
      return;
      bool1 = false;
      break;
      label124: bool2 = false;
      break label54;
    }
  }

  public static CacheBuilder<Object, Object> newBuilder()
  {
    return new CacheBuilder();
  }

  private boolean useNullCache()
  {
    return this.nullRemovalCause == null;
  }

  public <K1 extends K, V1 extends V> Cache<K1, V1> build(CacheLoader<? super K1, V1> paramCacheLoader)
  {
    if (useNullCache())
      return new ComputingCache(this, CACHE_STATS_COUNTER, paramCacheLoader);
    return new NullCache(this, CACHE_STATS_COUNTER, paramCacheLoader);
  }

  public CacheBuilder<K, V> concurrencyLevel(int paramInt)
  {
    boolean bool1 = true;
    boolean bool2;
    if (this.concurrencyLevel == -1)
    {
      bool2 = bool1;
      Object[] arrayOfObject = new Object[bool1];
      arrayOfObject[0] = Integer.valueOf(this.concurrencyLevel);
      Preconditions.checkState(bool2, "concurrency level was already set to %s", arrayOfObject);
      if (paramInt <= 0)
        break label57;
    }
    while (true)
    {
      Preconditions.checkArgument(bool1);
      this.concurrencyLevel = paramInt;
      return this;
      bool2 = false;
      break;
      label57: bool1 = false;
    }
  }

  public CacheBuilder<K, V> expireAfterAccess(long paramLong, TimeUnit paramTimeUnit)
  {
    checkExpiration(paramLong, paramTimeUnit);
    this.expireAfterAccessNanos = paramTimeUnit.toNanos(paramLong);
    if ((paramLong == 0L) && (this.nullRemovalCause == null))
      this.nullRemovalCause = RemovalCause.EXPIRED;
    return this;
  }

  public CacheBuilder<K, V> expireAfterWrite(long paramLong, TimeUnit paramTimeUnit)
  {
    checkExpiration(paramLong, paramTimeUnit);
    this.expireAfterWriteNanos = paramTimeUnit.toNanos(paramLong);
    if ((paramLong == 0L) && (this.nullRemovalCause == null))
      this.nullRemovalCause = RemovalCause.EXPIRED;
    return this;
  }

  int getConcurrencyLevel()
  {
    if (this.concurrencyLevel == -1)
      return 4;
    return this.concurrencyLevel;
  }

  long getExpireAfterAccessNanos()
  {
    if (this.expireAfterAccessNanos == -1L)
      return 0L;
    return this.expireAfterAccessNanos;
  }

  long getExpireAfterWriteNanos()
  {
    if (this.expireAfterWriteNanos == -1L)
      return 0L;
    return this.expireAfterWriteNanos;
  }

  int getInitialCapacity()
  {
    if (this.initialCapacity == -1)
      return 16;
    return this.initialCapacity;
  }

  Equivalence<Object> getKeyEquivalence()
  {
    return (Equivalence)Objects.firstNonNull(this.keyEquivalence, getKeyStrength().defaultEquivalence());
  }

  CustomConcurrentHashMap.Strength getKeyStrength()
  {
    return (CustomConcurrentHashMap.Strength)Objects.firstNonNull(this.keyStrength, CustomConcurrentHashMap.Strength.STRONG);
  }

  <K1 extends K, V1 extends V> RemovalListener<K1, V1> getRemovalListener()
  {
    return (RemovalListener)Objects.firstNonNull(this.removalListener, NullListener.INSTANCE);
  }

  Ticker getTicker()
  {
    return (Ticker)Objects.firstNonNull(this.ticker, Ticker.systemTicker());
  }

  Equivalence<Object> getValueEquivalence()
  {
    return (Equivalence)Objects.firstNonNull(this.valueEquivalence, getValueStrength().defaultEquivalence());
  }

  CustomConcurrentHashMap.Strength getValueStrength()
  {
    return (CustomConcurrentHashMap.Strength)Objects.firstNonNull(this.valueStrength, CustomConcurrentHashMap.Strength.STRONG);
  }

  public CacheBuilder<K, V> initialCapacity(int paramInt)
  {
    boolean bool1 = true;
    boolean bool2;
    if (this.initialCapacity == -1)
    {
      bool2 = bool1;
      Object[] arrayOfObject = new Object[bool1];
      arrayOfObject[0] = Integer.valueOf(this.initialCapacity);
      Preconditions.checkState(bool2, "initial capacity was already set to %s", arrayOfObject);
      if (paramInt < 0)
        break label57;
    }
    while (true)
    {
      Preconditions.checkArgument(bool1);
      this.initialCapacity = paramInt;
      return this;
      bool2 = false;
      break;
      label57: bool1 = false;
    }
  }

  CacheBuilder<K, V> keyEquivalence(Equivalence<Object> paramEquivalence)
  {
    if (this.keyEquivalence == null);
    for (boolean bool = true; ; bool = false)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.keyEquivalence;
      Preconditions.checkState(bool, "key equivalence was already set to %s", arrayOfObject);
      this.keyEquivalence = ((Equivalence)Preconditions.checkNotNull(paramEquivalence));
      return this;
    }
  }

  public CacheBuilder<K, V> maximumSize(int paramInt)
  {
    boolean bool1 = true;
    boolean bool2;
    if (this.maximumSize == -1)
    {
      bool2 = bool1;
      Object[] arrayOfObject = new Object[bool1];
      arrayOfObject[0] = Integer.valueOf(this.maximumSize);
      Preconditions.checkState(bool2, "maximum size was already set to %s", arrayOfObject);
      if (paramInt < 0)
        break label73;
    }
    while (true)
    {
      Preconditions.checkArgument(bool1, "maximum size must not be negative");
      this.maximumSize = paramInt;
      if (this.maximumSize == 0)
        this.nullRemovalCause = RemovalCause.SIZE;
      return this;
      bool2 = false;
      break;
      label73: bool1 = false;
    }
  }

  @CheckReturnValue
  public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> removalListener(RemovalListener<? super K1, ? super V1> paramRemovalListener)
  {
    if (this.removalListener == null);
    for (boolean bool = true; ; bool = false)
    {
      Preconditions.checkState(bool);
      this.removalListener = ((RemovalListener)Preconditions.checkNotNull(paramRemovalListener));
      return this;
    }
  }

  CacheBuilder<K, V> setKeyStrength(CustomConcurrentHashMap.Strength paramStrength)
  {
    if (this.keyStrength == null);
    for (boolean bool = true; ; bool = false)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.keyStrength;
      Preconditions.checkState(bool, "Key strength was already set to %s", arrayOfObject);
      this.keyStrength = ((CustomConcurrentHashMap.Strength)Preconditions.checkNotNull(paramStrength));
      return this;
    }
  }

  CacheBuilder<K, V> setValueStrength(CustomConcurrentHashMap.Strength paramStrength)
  {
    if (this.valueStrength == null);
    for (boolean bool = true; ; bool = false)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.valueStrength;
      Preconditions.checkState(bool, "Value strength was already set to %s", arrayOfObject);
      this.valueStrength = ((CustomConcurrentHashMap.Strength)Preconditions.checkNotNull(paramStrength));
      return this;
    }
  }

  public CacheBuilder<K, V> softValues()
  {
    return setValueStrength(CustomConcurrentHashMap.Strength.SOFT);
  }

  CacheBuilder<K, V> strongKeys()
  {
    return setKeyStrength(CustomConcurrentHashMap.Strength.STRONG);
  }

  CacheBuilder<K, V> strongValues()
  {
    return setValueStrength(CustomConcurrentHashMap.Strength.STRONG);
  }

  public CacheBuilder<K, V> ticker(Ticker paramTicker)
  {
    if (this.ticker == null);
    for (boolean bool = true; ; bool = false)
    {
      Preconditions.checkState(bool);
      this.ticker = ((Ticker)Preconditions.checkNotNull(paramTicker));
      return this;
    }
  }

  public String toString()
  {
    Objects.ToStringHelper localToStringHelper = Objects.toStringHelper(this);
    if (this.initialCapacity != -1)
      localToStringHelper.add("initialCapacity", Integer.valueOf(this.initialCapacity));
    if (this.concurrencyLevel != -1)
      localToStringHelper.add("concurrencyLevel", Integer.valueOf(this.concurrencyLevel));
    if (this.maximumSize != -1)
      localToStringHelper.add("maximumSize", Integer.valueOf(this.maximumSize));
    if (this.expireAfterWriteNanos != -1L)
      localToStringHelper.add("expireAfterWrite", this.expireAfterWriteNanos + "ns");
    if (this.expireAfterAccessNanos != -1L)
      localToStringHelper.add("expireAfterAccess", this.expireAfterAccessNanos + "ns");
    if (this.keyStrength != null)
      localToStringHelper.add("keyStrength", Ascii.toLowerCase(this.keyStrength.toString()));
    if (this.valueStrength != null)
      localToStringHelper.add("valueStrength", Ascii.toLowerCase(this.valueStrength.toString()));
    if (this.keyEquivalence != null)
      localToStringHelper.addValue("keyEquivalence");
    if (this.valueEquivalence != null)
      localToStringHelper.addValue("valueEquivalence");
    if (this.removalListener != null)
      localToStringHelper.addValue("removalListener");
    return localToStringHelper.toString();
  }

  CacheBuilder<K, V> valueEquivalence(Equivalence<Object> paramEquivalence)
  {
    if (this.valueEquivalence == null);
    for (boolean bool = true; ; bool = false)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.valueEquivalence;
      Preconditions.checkState(bool, "value equivalence was already set to %s", arrayOfObject);
      this.valueEquivalence = ((Equivalence)Preconditions.checkNotNull(paramEquivalence));
      return this;
    }
  }

  public CacheBuilder<K, V> weakKeys()
  {
    return setKeyStrength(CustomConcurrentHashMap.Strength.WEAK);
  }

  public CacheBuilder<K, V> weakValues()
  {
    return setValueStrength(CustomConcurrentHashMap.Strength.WEAK);
  }

  static final class CacheAsMap<K, V> extends ForwardingConcurrentMap<K, V>
  {
    private final ConcurrentMap<K, V> delegate;

    CacheAsMap(ConcurrentMap<K, V> paramConcurrentMap)
    {
      this.delegate = paramConcurrentMap;
    }

    protected ConcurrentMap<K, V> delegate()
    {
      return this.delegate;
    }

    public V put(K paramK, V paramV)
    {
      throw new UnsupportedOperationException();
    }

    public void putAll(Map<? extends K, ? extends V> paramMap)
    {
      throw new UnsupportedOperationException();
    }

    public V putIfAbsent(K paramK, V paramV)
    {
      throw new UnsupportedOperationException();
    }

    public V replace(K paramK, V paramV)
    {
      throw new UnsupportedOperationException();
    }

    public boolean replace(K paramK, V paramV1, V paramV2)
    {
      throw new UnsupportedOperationException();
    }
  }

  static final class NullCache<K, V> extends AbstractCache<K, V>
  {
    ConcurrentMap<K, V> asMap;
    final CacheLoader<? super K, V> loader;
    final CacheBuilder.NullConcurrentMap<K, V> map;
    final AbstractCache.StatsCounter statsCounter;

    NullCache(CacheBuilder<? super K, ? super V> paramCacheBuilder, Supplier<? extends AbstractCache.StatsCounter> paramSupplier, CacheLoader<? super K, V> paramCacheLoader)
    {
      this.map = new CacheBuilder.NullConcurrentMap(paramCacheBuilder);
      this.statsCounter = ((AbstractCache.StatsCounter)paramSupplier.get());
      this.loader = ((CacheLoader)Preconditions.checkNotNull(paramCacheLoader));
    }

    // ERROR //
    private V compute(K paramK)
      throws ExecutionException
    {
      // Byte code:
      //   0: aload_1
      //   1: invokestatic 44	com/google/common/base/Preconditions:checkNotNull	(Ljava/lang/Object;)Ljava/lang/Object;
      //   4: pop
      //   5: invokestatic 63	java/lang/System:nanoTime	()J
      //   8: lstore_3
      //   9: aload_0
      //   10: getfield 48	com/google/common/cache/CacheBuilder$NullCache:loader	Lcom/google/common/cache/CacheLoader;
      //   13: aload_1
      //   14: invokevirtual 66	com/google/common/cache/CacheLoader:load	(Ljava/lang/Object;)Ljava/lang/Object;
      //   17: astore 11
      //   19: invokestatic 63	java/lang/System:nanoTime	()J
      //   22: lload_3
      //   23: lsub
      //   24: lstore 12
      //   26: aload 11
      //   28: ifnonnull +36 -> 64
      //   31: aload_0
      //   32: getfield 38	com/google/common/cache/CacheBuilder$NullCache:statsCounter	Lcom/google/common/cache/AbstractCache$StatsCounter;
      //   35: lload 12
      //   37: invokeinterface 70 3 0
      //   42: aload_0
      //   43: getfield 38	com/google/common/cache/CacheBuilder$NullCache:statsCounter	Lcom/google/common/cache/AbstractCache$StatsCounter;
      //   46: invokeinterface 73 1 0
      //   51: aload 11
      //   53: ifnonnull +111 -> 164
      //   56: new 75	java/lang/NullPointerException
      //   59: dup
      //   60: invokespecial 76	java/lang/NullPointerException:<init>	()V
      //   63: athrow
      //   64: aload_0
      //   65: getfield 38	com/google/common/cache/CacheBuilder$NullCache:statsCounter	Lcom/google/common/cache/AbstractCache$StatsCounter;
      //   68: lload 12
      //   70: invokeinterface 79 3 0
      //   75: goto -33 -> 42
      //   78: astore 10
      //   80: new 81	com/google/common/util/concurrent/UncheckedExecutionException
      //   83: dup
      //   84: aload 10
      //   86: invokespecial 84	com/google/common/util/concurrent/UncheckedExecutionException:<init>	(Ljava/lang/Throwable;)V
      //   89: athrow
      //   90: astore 6
      //   92: invokestatic 63	java/lang/System:nanoTime	()J
      //   95: lload_3
      //   96: lsub
      //   97: lstore 7
      //   99: iconst_0
      //   100: ifne +50 -> 150
      //   103: aload_0
      //   104: getfield 38	com/google/common/cache/CacheBuilder$NullCache:statsCounter	Lcom/google/common/cache/AbstractCache$StatsCounter;
      //   107: lload 7
      //   109: invokeinterface 70 3 0
      //   114: aload_0
      //   115: getfield 38	com/google/common/cache/CacheBuilder$NullCache:statsCounter	Lcom/google/common/cache/AbstractCache$StatsCounter;
      //   118: invokeinterface 73 1 0
      //   123: aload 6
      //   125: athrow
      //   126: astore 9
      //   128: new 51	java/util/concurrent/ExecutionException
      //   131: dup
      //   132: aload 9
      //   134: invokespecial 85	java/util/concurrent/ExecutionException:<init>	(Ljava/lang/Throwable;)V
      //   137: athrow
      //   138: astore 5
      //   140: new 87	com/google/common/util/concurrent/ExecutionError
      //   143: dup
      //   144: aload 5
      //   146: invokespecial 90	com/google/common/util/concurrent/ExecutionError:<init>	(Ljava/lang/Error;)V
      //   149: athrow
      //   150: aload_0
      //   151: getfield 38	com/google/common/cache/CacheBuilder$NullCache:statsCounter	Lcom/google/common/cache/AbstractCache$StatsCounter;
      //   154: lload 7
      //   156: invokeinterface 79 3 0
      //   161: goto -47 -> 114
      //   164: aload 11
      //   166: areturn
      //
      // Exception table:
      //   from	to	target	type
      //   9	19	78	java/lang/RuntimeException
      //   9	19	90	finally
      //   80	90	90	finally
      //   128	138	90	finally
      //   140	150	90	finally
      //   9	19	126	java/lang/Exception
      //   9	19	138	java/lang/Error
    }

    public ConcurrentMap<K, V> asMap()
    {
      ConcurrentMap localConcurrentMap = this.asMap;
      if (localConcurrentMap != null)
        return localConcurrentMap;
      CacheBuilder.CacheAsMap localCacheAsMap = new CacheBuilder.CacheAsMap(this.map);
      this.asMap = localCacheAsMap;
      return localCacheAsMap;
    }

    public V get(K paramK)
      throws ExecutionException
    {
      Object localObject = compute(paramK);
      this.map.notifyRemoval(paramK, localObject);
      return localObject;
    }

    public void invalidate(Object paramObject)
    {
    }

    public void invalidateAll()
    {
    }

    public long size()
    {
      return 0L;
    }

    public CacheStats stats()
    {
      return this.statsCounter.snapshot();
    }
  }

  static final class NullComputingConcurrentMap<K, V> extends CacheBuilder.NullConcurrentMap<K, V>
  {
    private static final long serialVersionUID;
    final CacheLoader<? super K, ? extends V> loader;

    NullComputingConcurrentMap(CacheBuilder<? super K, ? super V> paramCacheBuilder, CacheLoader<? super K, ? extends V> paramCacheLoader)
    {
      super();
      this.loader = ((CacheLoader)Preconditions.checkNotNull(paramCacheLoader));
    }

    private V compute(K paramK)
    {
      Preconditions.checkNotNull(paramK);
      try
      {
        Object localObject = this.loader.load(paramK);
        return localObject;
      }
      catch (Exception localException)
      {
        throw new UncheckedExecutionException(localException);
      }
      catch (Error localError)
      {
      }
      throw new ExecutionError(localError);
    }

    public V get(Object paramObject)
    {
      Object localObject = compute(paramObject);
      Preconditions.checkNotNull(localObject, this.loader + " returned null for key " + paramObject + ".");
      notifyRemoval(paramObject, localObject);
      return localObject;
    }
  }

  static class NullConcurrentMap<K, V> extends AbstractMap<K, V>
    implements ConcurrentMap<K, V>, Serializable
  {
    private static final long serialVersionUID;
    private final RemovalCause removalCause;
    private final RemovalListener<K, V> removalListener;

    NullConcurrentMap(CacheBuilder<? super K, ? super V> paramCacheBuilder)
    {
      this.removalListener = paramCacheBuilder.getRemovalListener();
      this.removalCause = paramCacheBuilder.nullRemovalCause;
    }

    public boolean containsKey(@Nullable Object paramObject)
    {
      return false;
    }

    public boolean containsValue(@Nullable Object paramObject)
    {
      return false;
    }

    public Set<Map.Entry<K, V>> entrySet()
    {
      return Collections.emptySet();
    }

    public V get(@Nullable Object paramObject)
    {
      return null;
    }

    void notifyRemoval(K paramK, V paramV)
    {
      RemovalNotification localRemovalNotification = new RemovalNotification(paramK, paramV, this.removalCause);
      this.removalListener.onRemoval(localRemovalNotification);
    }

    public V put(K paramK, V paramV)
    {
      Preconditions.checkNotNull(paramK);
      Preconditions.checkNotNull(paramV);
      notifyRemoval(paramK, paramV);
      return null;
    }

    public V putIfAbsent(K paramK, V paramV)
    {
      return put(paramK, paramV);
    }

    public V remove(@Nullable Object paramObject)
    {
      return null;
    }

    public boolean remove(@Nullable Object paramObject1, @Nullable Object paramObject2)
    {
      return false;
    }

    public V replace(K paramK, V paramV)
    {
      Preconditions.checkNotNull(paramK);
      Preconditions.checkNotNull(paramV);
      return null;
    }

    public boolean replace(K paramK, @Nullable V paramV1, V paramV2)
    {
      Preconditions.checkNotNull(paramK);
      Preconditions.checkNotNull(paramV2);
      return false;
    }
  }

  static enum NullListener
    implements RemovalListener<Object, Object>
  {
    static
    {
      NullListener[] arrayOfNullListener = new NullListener[1];
      arrayOfNullListener[0] = INSTANCE;
      $VALUES = arrayOfNullListener;
    }

    public void onRemoval(RemovalNotification<Object, Object> paramRemovalNotification)
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.cache.CacheBuilder
 * JD-Core Version:    0.6.0
 */