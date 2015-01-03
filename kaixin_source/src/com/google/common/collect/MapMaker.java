package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Ticker;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public final class MapMaker extends GenericMapMaker<Object, Object>
{
  private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
  private static final int DEFAULT_EXPIRATION_NANOS = 0;
  private static final int DEFAULT_INITIAL_CAPACITY = 16;
  static final int UNSET_INT = -1;
  int concurrencyLevel = -1;
  long expireAfterAccessNanos = -1L;
  long expireAfterWriteNanos = -1L;
  int initialCapacity = -1;
  Equivalence<Object> keyEquivalence;
  CustomConcurrentHashMap.Strength keyStrength;
  int maximumSize = -1;
  RemovalCause nullRemovalCause;
  Ticker ticker;
  boolean useCustomMap;
  Equivalence<Object> valueEquivalence;
  CustomConcurrentHashMap.Strength valueStrength;

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

  private boolean useNullMap()
  {
    return this.nullRemovalCause == null;
  }

  public MapMaker concurrencyLevel(int paramInt)
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

  @Deprecated
  @Beta
  @GwtIncompatible("To be supported")
  public <K, V> GenericMapMaker<K, V> evictionListener(MapEvictionListener<K, V> paramMapEvictionListener)
  {
    if (this.removalListener == null);
    for (boolean bool = true; ; bool = false)
    {
      Preconditions.checkState(bool);
      this.removalListener = new MapMakerRemovalListener(paramMapEvictionListener);
      this.useCustomMap = true;
      return this;
    }
  }

  @Deprecated
  public MapMaker expiration(long paramLong, TimeUnit paramTimeUnit)
  {
    return expireAfterWrite(paramLong, paramTimeUnit);
  }

  @Deprecated
  @GwtIncompatible("To be supported")
  public MapMaker expireAfterAccess(long paramLong, TimeUnit paramTimeUnit)
  {
    checkExpiration(paramLong, paramTimeUnit);
    this.expireAfterAccessNanos = paramTimeUnit.toNanos(paramLong);
    if ((paramLong == 0L) && (this.nullRemovalCause == null))
      this.nullRemovalCause = RemovalCause.EXPIRED;
    this.useCustomMap = true;
    return this;
  }

  @Deprecated
  public MapMaker expireAfterWrite(long paramLong, TimeUnit paramTimeUnit)
  {
    checkExpiration(paramLong, paramTimeUnit);
    this.expireAfterWriteNanos = paramTimeUnit.toNanos(paramLong);
    if ((paramLong == 0L) && (this.nullRemovalCause == null))
      this.nullRemovalCause = RemovalCause.EXPIRED;
    this.useCustomMap = true;
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

  public MapMaker initialCapacity(int paramInt)
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

  @GwtIncompatible("To be supported")
  MapMaker keyEquivalence(Equivalence<Object> paramEquivalence)
  {
    if (this.keyEquivalence == null);
    for (boolean bool = true; ; bool = false)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.keyEquivalence;
      Preconditions.checkState(bool, "key equivalence was already set to %s", arrayOfObject);
      this.keyEquivalence = ((Equivalence)Preconditions.checkNotNull(paramEquivalence));
      this.useCustomMap = true;
      return this;
    }
  }

  @Deprecated
  public <K, V> ConcurrentMap<K, V> makeComputingMap(Function<? super K, ? extends V> paramFunction)
  {
    if (useNullMap())
      return new ComputingConcurrentHashMap.ComputingMapAdapter(this, paramFunction);
    return new NullComputingConcurrentMap(this, paramFunction);
  }

  @GwtIncompatible("CustomConcurrentHashMap")
  <K, V> CustomConcurrentHashMap<K, V> makeCustomMap()
  {
    return new CustomConcurrentHashMap(this);
  }

  public <K, V> ConcurrentMap<K, V> makeMap()
  {
    if (!this.useCustomMap)
      return new ConcurrentHashMap(getInitialCapacity(), 0.75F, getConcurrencyLevel());
    if (this.nullRemovalCause == null)
      return new CustomConcurrentHashMap(this);
    return new NullConcurrentMap(this);
  }

  @Deprecated
  @Beta
  public MapMaker maximumSize(int paramInt)
  {
    if (this.maximumSize == -1);
    for (boolean bool1 = true; ; bool1 = false)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(this.maximumSize);
      Preconditions.checkState(bool1, "maximum size was already set to %s", arrayOfObject);
      boolean bool2 = false;
      if (paramInt >= 0)
        bool2 = true;
      Preconditions.checkArgument(bool2, "maximum size must not be negative");
      this.maximumSize = paramInt;
      this.useCustomMap = true;
      if (this.maximumSize == 0)
        this.nullRemovalCause = RemovalCause.SIZE;
      return this;
    }
  }

  @GwtIncompatible("To be supported")
  <K, V> GenericMapMaker<K, V> removalListener(RemovalListener<K, V> paramRemovalListener)
  {
    if (this.removalListener == null);
    for (boolean bool = true; ; bool = false)
    {
      Preconditions.checkState(bool);
      this.removalListener = ((RemovalListener)Preconditions.checkNotNull(paramRemovalListener));
      this.useCustomMap = true;
      return this;
    }
  }

  MapMaker setKeyStrength(CustomConcurrentHashMap.Strength paramStrength)
  {
    if (this.keyStrength == null);
    for (boolean bool = true; ; bool = false)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.keyStrength;
      Preconditions.checkState(bool, "Key strength was already set to %s", arrayOfObject);
      this.keyStrength = ((CustomConcurrentHashMap.Strength)Preconditions.checkNotNull(paramStrength));
      if (paramStrength != CustomConcurrentHashMap.Strength.STRONG)
        this.useCustomMap = true;
      return this;
    }
  }

  MapMaker setValueStrength(CustomConcurrentHashMap.Strength paramStrength)
  {
    if (this.valueStrength == null);
    for (boolean bool = true; ; bool = false)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.valueStrength;
      Preconditions.checkState(bool, "Value strength was already set to %s", arrayOfObject);
      this.valueStrength = ((CustomConcurrentHashMap.Strength)Preconditions.checkNotNull(paramStrength));
      if (paramStrength != CustomConcurrentHashMap.Strength.STRONG)
        this.useCustomMap = true;
      return this;
    }
  }

  @Deprecated
  @GwtIncompatible("java.lang.ref.SoftReference")
  public MapMaker softKeys()
  {
    return setKeyStrength(CustomConcurrentHashMap.Strength.SOFT);
  }

  @GwtIncompatible("java.lang.ref.SoftReference")
  public MapMaker softValues()
  {
    return setValueStrength(CustomConcurrentHashMap.Strength.SOFT);
  }

  MapMaker strongKeys()
  {
    return setKeyStrength(CustomConcurrentHashMap.Strength.STRONG);
  }

  MapMaker strongValues()
  {
    return setValueStrength(CustomConcurrentHashMap.Strength.STRONG);
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

  @GwtIncompatible("To be supported")
  MapMaker valueEquivalence(Equivalence<Object> paramEquivalence)
  {
    if (this.valueEquivalence == null);
    for (boolean bool = true; ; bool = false)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.valueEquivalence;
      Preconditions.checkState(bool, "value equivalence was already set to %s", arrayOfObject);
      this.valueEquivalence = ((Equivalence)Preconditions.checkNotNull(paramEquivalence));
      this.useCustomMap = true;
      return this;
    }
  }

  @GwtIncompatible("java.lang.ref.WeakReference")
  public MapMaker weakKeys()
  {
    return setKeyStrength(CustomConcurrentHashMap.Strength.WEAK);
  }

  @GwtIncompatible("java.lang.ref.WeakReference")
  public MapMaker weakValues()
  {
    return setValueStrength(CustomConcurrentHashMap.Strength.WEAK);
  }

  static final class MapMakerRemovalListener<K, V>
    implements MapMaker.RemovalListener<K, V>, Serializable
  {
    private static final long serialVersionUID;
    private final MapEvictionListener<K, V> listener;

    public MapMakerRemovalListener(MapEvictionListener<K, V> paramMapEvictionListener)
    {
      this.listener = ((MapEvictionListener)Preconditions.checkNotNull(paramMapEvictionListener));
    }

    public void onRemoval(MapMaker.RemovalNotification<K, V> paramRemovalNotification)
    {
      if (paramRemovalNotification.wasEvicted())
        this.listener.onEviction(paramRemovalNotification.getKey(), paramRemovalNotification.getValue());
    }
  }

  static final class NullComputingConcurrentMap<K, V> extends MapMaker.NullConcurrentMap<K, V>
  {
    private static final long serialVersionUID;
    final Function<? super K, ? extends V> computingFunction;

    NullComputingConcurrentMap(MapMaker paramMapMaker, Function<? super K, ? extends V> paramFunction)
    {
      super();
      this.computingFunction = ((Function)Preconditions.checkNotNull(paramFunction));
    }

    private V compute(K paramK)
    {
      Preconditions.checkNotNull(paramK);
      try
      {
        Object localObject = this.computingFunction.apply(paramK);
        return localObject;
      }
      catch (ComputationException localComputationException)
      {
        throw localComputationException;
      }
      catch (Throwable localThrowable)
      {
      }
      throw new ComputationException(localThrowable);
    }

    public V get(Object paramObject)
    {
      Object localObject = compute(paramObject);
      Preconditions.checkNotNull(localObject, this.computingFunction + " returned null for key " + paramObject + ".");
      notifyRemoval(paramObject, localObject);
      return localObject;
    }
  }

  static class NullConcurrentMap<K, V> extends AbstractMap<K, V>
    implements ConcurrentMap<K, V>, Serializable
  {
    private static final long serialVersionUID;
    private final MapMaker.RemovalCause removalCause;
    private final MapMaker.RemovalListener<K, V> removalListener;

    NullConcurrentMap(MapMaker paramMapMaker)
    {
      this.removalListener = paramMapMaker.getRemovalListener();
      this.removalCause = paramMapMaker.nullRemovalCause;
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
      MapMaker.RemovalNotification localRemovalNotification = new MapMaker.RemovalNotification(paramK, paramV, this.removalCause);
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

  static abstract enum RemovalCause
  {
    static
    {
      COLLECTED = new RemovalCause("COLLECTED", 2)
      {
        boolean wasEvicted()
        {
          return true;
        }
      };
      EXPIRED = new RemovalCause("EXPIRED", 3)
      {
        boolean wasEvicted()
        {
          return true;
        }
      };
      SIZE = new RemovalCause("SIZE", 4)
      {
        boolean wasEvicted()
        {
          return true;
        }
      };
      RemovalCause[] arrayOfRemovalCause = new RemovalCause[5];
      arrayOfRemovalCause[0] = EXPLICIT;
      arrayOfRemovalCause[1] = REPLACED;
      arrayOfRemovalCause[2] = COLLECTED;
      arrayOfRemovalCause[3] = EXPIRED;
      arrayOfRemovalCause[4] = SIZE;
      $VALUES = arrayOfRemovalCause;
    }

    abstract boolean wasEvicted();
  }

  static abstract interface RemovalListener<K, V>
  {
    public abstract void onRemoval(MapMaker.RemovalNotification<K, V> paramRemovalNotification);
  }

  static final class RemovalNotification<K, V> extends ImmutableEntry<K, V>
  {
    private static final long serialVersionUID;
    private final MapMaker.RemovalCause cause;

    RemovalNotification(@Nullable K paramK, @Nullable V paramV, MapMaker.RemovalCause paramRemovalCause)
    {
      super(paramV);
      this.cause = paramRemovalCause;
    }

    public MapMaker.RemovalCause getCause()
    {
      return this.cause;
    }

    public boolean wasEvicted()
    {
      return this.cause.wasEvicted();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.MapMaker
 * JD-Core Version:    0.6.0
 */