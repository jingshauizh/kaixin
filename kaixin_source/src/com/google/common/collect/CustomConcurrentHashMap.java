package com.google.common.collect;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Equivalence;
import com.google.common.base.Equivalences;
import com.google.common.base.Preconditions;
import com.google.common.base.Ticker;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractQueue;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

class CustomConcurrentHashMap<K, V> extends AbstractMap<K, V>
  implements ConcurrentMap<K, V>, Serializable
{
  static final long CLEANUP_EXECUTOR_DELAY_SECS = 60L;
  static final int CONTAINS_VALUE_RETRIES = 3;
  static final Queue<? extends Object> DISCARDING_QUEUE;
  static final int DRAIN_MAX = 16;
  static final int DRAIN_THRESHOLD = 63;
  static final int MAXIMUM_CAPACITY = 1073741824;
  static final int MAX_SEGMENTS = 65536;
  static final ValueReference<Object, Object> UNSET;
  private static final Logger logger = Logger.getLogger(CustomConcurrentHashMap.class.getName());
  private static final long serialVersionUID = 5L;
  final int concurrencyLevel;
  final transient EntryFactory entryFactory;
  Set<Map.Entry<K, V>> entrySet;
  final long expireAfterAccessNanos;
  final long expireAfterWriteNanos;
  final Equivalence<Object> keyEquivalence;
  Set<K> keySet;
  final Strength keyStrength;
  final int maximumSize;
  final MapMaker.RemovalListener<K, V> removalListener;
  final Queue<MapMaker.RemovalNotification<K, V>> removalNotificationQueue;
  final transient int segmentMask;
  final transient int segmentShift;
  final transient Segment<K, V>[] segments;
  final Ticker ticker;
  final Equivalence<Object> valueEquivalence;
  final Strength valueStrength;
  Collection<V> values;

  static
  {
    UNSET = new ValueReference()
    {
      public void clear(CustomConcurrentHashMap.ValueReference<Object, Object> paramValueReference)
      {
      }

      public CustomConcurrentHashMap.ValueReference<Object, Object> copyFor(ReferenceQueue<Object> paramReferenceQueue, CustomConcurrentHashMap.ReferenceEntry<Object, Object> paramReferenceEntry)
      {
        return this;
      }

      public Object get()
      {
        return null;
      }

      public CustomConcurrentHashMap.ReferenceEntry<Object, Object> getEntry()
      {
        return null;
      }

      public boolean isComputingReference()
      {
        return false;
      }

      public Object waitForValue()
      {
        return null;
      }
    };
    DISCARDING_QUEUE = new AbstractQueue()
    {
      public Iterator<Object> iterator()
      {
        return Iterators.emptyIterator();
      }

      public boolean offer(Object paramObject)
      {
        return true;
      }

      public Object peek()
      {
        return null;
      }

      public Object poll()
      {
        return null;
      }

      public int size()
      {
        return 0;
      }
    };
  }

  CustomConcurrentHashMap(MapMaker paramMapMaker)
  {
    this.concurrencyLevel = Math.min(paramMapMaker.getConcurrencyLevel(), 65536);
    this.keyStrength = paramMapMaker.getKeyStrength();
    this.valueStrength = paramMapMaker.getValueStrength();
    this.keyEquivalence = paramMapMaker.getKeyEquivalence();
    this.valueEquivalence = paramMapMaker.getValueEquivalence();
    this.maximumSize = paramMapMaker.maximumSize;
    this.expireAfterAccessNanos = paramMapMaker.getExpireAfterAccessNanos();
    this.expireAfterWriteNanos = paramMapMaker.getExpireAfterWriteNanos();
    this.entryFactory = EntryFactory.getFactory(this.keyStrength, expires(), evictsBySize());
    this.ticker = paramMapMaker.getTicker();
    this.removalListener = paramMapMaker.getRemovalListener();
    if (this.removalListener == GenericMapMaker.NullListener.INSTANCE);
    int i;
    int j;
    int k;
    for (Object localObject = discardingQueue(); ; localObject = new ConcurrentLinkedQueue())
    {
      this.removalNotificationQueue = ((Queue)localObject);
      i = Math.min(paramMapMaker.getInitialCapacity(), 1073741824);
      if (evictsBySize())
        i = Math.min(i, this.maximumSize);
      j = 0;
      k = 1;
      while ((k < this.concurrencyLevel) && ((!evictsBySize()) || (k * 2 <= this.maximumSize)))
      {
        j++;
        k <<= 1;
      }
    }
    this.segmentShift = (32 - j);
    this.segmentMask = (k - 1);
    this.segments = newSegmentArray(k);
    int m = i / k;
    if (m * k < i)
      m++;
    int n = 1;
    while (n < m)
      n <<= 1;
    if (evictsBySize())
    {
      int i2 = 1 + this.maximumSize / k;
      int i3 = this.maximumSize % k;
      for (int i4 = 0; i4 < this.segments.length; i4++)
      {
        if (i4 == i3)
          i2--;
        this.segments[i4] = createSegment(n, i2);
      }
    }
    for (int i1 = 0; i1 < this.segments.length; i1++)
      this.segments[i1] = createSegment(n, -1);
  }

  @GuardedBy("Segment.this")
  static <K, V> void connectEvictables(ReferenceEntry<K, V> paramReferenceEntry1, ReferenceEntry<K, V> paramReferenceEntry2)
  {
    paramReferenceEntry1.setNextEvictable(paramReferenceEntry2);
    paramReferenceEntry2.setPreviousEvictable(paramReferenceEntry1);
  }

  @GuardedBy("Segment.this")
  static <K, V> void connectExpirables(ReferenceEntry<K, V> paramReferenceEntry1, ReferenceEntry<K, V> paramReferenceEntry2)
  {
    paramReferenceEntry1.setNextExpirable(paramReferenceEntry2);
    paramReferenceEntry2.setPreviousExpirable(paramReferenceEntry1);
  }

  static <E> Queue<E> discardingQueue()
  {
    return DISCARDING_QUEUE;
  }

  static <K, V> ReferenceEntry<K, V> nullEntry()
  {
    return NullEntry.INSTANCE;
  }

  @GuardedBy("Segment.this")
  static <K, V> void nullifyEvictable(ReferenceEntry<K, V> paramReferenceEntry)
  {
    ReferenceEntry localReferenceEntry = nullEntry();
    paramReferenceEntry.setNextEvictable(localReferenceEntry);
    paramReferenceEntry.setPreviousEvictable(localReferenceEntry);
  }

  @GuardedBy("Segment.this")
  static <K, V> void nullifyExpirable(ReferenceEntry<K, V> paramReferenceEntry)
  {
    ReferenceEntry localReferenceEntry = nullEntry();
    paramReferenceEntry.setNextExpirable(localReferenceEntry);
    paramReferenceEntry.setPreviousExpirable(localReferenceEntry);
  }

  static int rehash(int paramInt)
  {
    int i = paramInt + (0xFFFFCD7D ^ paramInt << 15);
    int j = i ^ i >>> 10;
    int k = j + (j << 3);
    int m = k ^ k >>> 6;
    int n = m + ((m << 2) + (m << 14));
    return n ^ n >>> 16;
  }

  static <K, V> ValueReference<K, V> unset()
  {
    return UNSET;
  }

  public void clear()
  {
    Segment[] arrayOfSegment = this.segments;
    int i = arrayOfSegment.length;
    for (int j = 0; j < i; j++)
      arrayOfSegment[j].clear();
  }

  public boolean containsKey(@Nullable Object paramObject)
  {
    if (paramObject == null)
      return false;
    int i = hash(paramObject);
    return segmentFor(i).containsKey(paramObject, i);
  }

  public boolean containsValue(@Nullable Object paramObject)
  {
    if (paramObject == null)
      return false;
    Segment[] arrayOfSegment = this.segments;
    long l1 = -1L;
    for (int i = 0; ; i++)
    {
      long l2;
      if (i < 3)
      {
        l2 = 0L;
        int j = arrayOfSegment.length;
        for (int k = 0; k < j; k++)
        {
          Segment localSegment = arrayOfSegment[k];
          AtomicReferenceArray localAtomicReferenceArray = localSegment.table;
          for (int m = 0; m < localAtomicReferenceArray.length(); m++)
            for (ReferenceEntry localReferenceEntry = (ReferenceEntry)localAtomicReferenceArray.get(m); localReferenceEntry != null; localReferenceEntry = localReferenceEntry.getNext())
            {
              Object localObject = localSegment.getLiveValue(localReferenceEntry);
              if ((localObject != null) && (this.valueEquivalence.equivalent(paramObject, localObject)))
                return true;
            }
          l2 += localSegment.modCount;
        }
        if (l2 != l1);
      }
      else
      {
        return false;
      }
      l1 = l2;
    }
  }

  @VisibleForTesting
  @GuardedBy("Segment.this")
  ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> paramReferenceEntry1, ReferenceEntry<K, V> paramReferenceEntry2)
  {
    return segmentFor(paramReferenceEntry1.getHash()).copyEntry(paramReferenceEntry1, paramReferenceEntry2);
  }

  Segment<K, V> createSegment(int paramInt1, int paramInt2)
  {
    return new Segment(this, paramInt1, paramInt2);
  }

  public Set<Map.Entry<K, V>> entrySet()
  {
    Set localSet = this.entrySet;
    if (localSet != null)
      return localSet;
    EntrySet localEntrySet = new EntrySet();
    this.entrySet = localEntrySet;
    return localEntrySet;
  }

  boolean evictsBySize()
  {
    return this.maximumSize != -1;
  }

  boolean expires()
  {
    return (expiresAfterWrite()) || (expiresAfterAccess());
  }

  boolean expiresAfterAccess()
  {
    return this.expireAfterAccessNanos > 0L;
  }

  boolean expiresAfterWrite()
  {
    return this.expireAfterWriteNanos > 0L;
  }

  public V get(@Nullable Object paramObject)
  {
    if (paramObject == null)
      return null;
    int i = hash(paramObject);
    return segmentFor(i).get(paramObject, i);
  }

  ReferenceEntry<K, V> getEntry(@Nullable Object paramObject)
  {
    if (paramObject == null)
      return null;
    int i = hash(paramObject);
    return segmentFor(i).getEntry(paramObject, i);
  }

  ReferenceEntry<K, V> getLiveEntry(@Nullable Object paramObject)
  {
    if (paramObject == null)
      return null;
    int i = hash(paramObject);
    return segmentFor(i).getLiveEntry(paramObject, i);
  }

  V getLiveValue(ReferenceEntry<K, V> paramReferenceEntry)
  {
    Object localObject;
    if (paramReferenceEntry.getKey() == null)
      localObject = null;
    do
    {
      return localObject;
      localObject = paramReferenceEntry.getValueReference().get();
      if (localObject == null)
        return null;
    }
    while ((!expires()) || (!isExpired(paramReferenceEntry)));
    return null;
  }

  int hash(Object paramObject)
  {
    return rehash(this.keyEquivalence.hash(paramObject));
  }

  public boolean isEmpty()
  {
    long l = 0L;
    Segment[] arrayOfSegment = this.segments;
    int i = 0;
    if (i < arrayOfSegment.length)
      if (arrayOfSegment[i].count == 0);
    label89: 
    do
    {
      return false;
      l += arrayOfSegment[i].modCount;
      i++;
      break;
      if (l == 0L)
        break label95;
      for (int j = 0; ; j++)
      {
        if (j >= arrayOfSegment.length)
          break label89;
        if (arrayOfSegment[j].count != 0)
          break;
        l -= arrayOfSegment[j].modCount;
      }
    }
    while (l != 0L);
    label95: return true;
  }

  boolean isExpired(ReferenceEntry<K, V> paramReferenceEntry)
  {
    return isExpired(paramReferenceEntry, this.ticker.read());
  }

  boolean isExpired(ReferenceEntry<K, V> paramReferenceEntry, long paramLong)
  {
    return paramLong - paramReferenceEntry.getExpirationTime() > 0L;
  }

  @VisibleForTesting
  boolean isLive(ReferenceEntry<K, V> paramReferenceEntry)
  {
    return segmentFor(paramReferenceEntry.getHash()).getLiveValue(paramReferenceEntry) != null;
  }

  public Set<K> keySet()
  {
    Set localSet = this.keySet;
    if (localSet != null)
      return localSet;
    KeySet localKeySet = new KeySet();
    this.keySet = localKeySet;
    return localKeySet;
  }

  @VisibleForTesting
  @GuardedBy("Segment.this")
  ReferenceEntry<K, V> newEntry(K paramK, int paramInt, @Nullable ReferenceEntry<K, V> paramReferenceEntry)
  {
    return segmentFor(paramInt).newEntry(paramK, paramInt, paramReferenceEntry);
  }

  final Segment<K, V>[] newSegmentArray(int paramInt)
  {
    return new Segment[paramInt];
  }

  @VisibleForTesting
  @GuardedBy("Segment.this")
  ValueReference<K, V> newValueReference(ReferenceEntry<K, V> paramReferenceEntry, V paramV)
  {
    int i = paramReferenceEntry.getHash();
    return this.valueStrength.referenceValue(segmentFor(i), paramReferenceEntry, paramV);
  }

  void processPendingNotifications()
  {
    while (true)
    {
      MapMaker.RemovalNotification localRemovalNotification = (MapMaker.RemovalNotification)this.removalNotificationQueue.poll();
      if (localRemovalNotification == null)
        break;
      try
      {
        this.removalListener.onRemoval(localRemovalNotification);
      }
      catch (Exception localException)
      {
        logger.log(Level.WARNING, "Exception thrown by removal listener", localException);
      }
    }
  }

  public V put(K paramK, V paramV)
  {
    Preconditions.checkNotNull(paramK);
    Preconditions.checkNotNull(paramV);
    int i = hash(paramK);
    return segmentFor(i).put(paramK, i, paramV, false);
  }

  public void putAll(Map<? extends K, ? extends V> paramMap)
  {
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      put(localEntry.getKey(), localEntry.getValue());
    }
  }

  public V putIfAbsent(K paramK, V paramV)
  {
    Preconditions.checkNotNull(paramK);
    Preconditions.checkNotNull(paramV);
    int i = hash(paramK);
    return segmentFor(i).put(paramK, i, paramV, true);
  }

  void reclaimKey(ReferenceEntry<K, V> paramReferenceEntry)
  {
    int i = paramReferenceEntry.getHash();
    segmentFor(i).reclaimKey(paramReferenceEntry, i);
  }

  void reclaimValue(ValueReference<K, V> paramValueReference)
  {
    ReferenceEntry localReferenceEntry = paramValueReference.getEntry();
    int i = localReferenceEntry.getHash();
    segmentFor(i).reclaimValue(localReferenceEntry.getKey(), i, paramValueReference);
  }

  public V remove(@Nullable Object paramObject)
  {
    if (paramObject == null)
      return null;
    int i = hash(paramObject);
    return segmentFor(i).remove(paramObject, i);
  }

  public boolean remove(@Nullable Object paramObject1, @Nullable Object paramObject2)
  {
    if ((paramObject1 == null) || (paramObject2 == null))
      return false;
    int i = hash(paramObject1);
    return segmentFor(i).remove(paramObject1, i, paramObject2);
  }

  public V replace(K paramK, V paramV)
  {
    Preconditions.checkNotNull(paramK);
    Preconditions.checkNotNull(paramV);
    int i = hash(paramK);
    return segmentFor(i).replace(paramK, i, paramV);
  }

  public boolean replace(K paramK, @Nullable V paramV1, V paramV2)
  {
    Preconditions.checkNotNull(paramK);
    Preconditions.checkNotNull(paramV2);
    if (paramV1 == null)
      return false;
    int i = hash(paramK);
    return segmentFor(i).replace(paramK, i, paramV1, paramV2);
  }

  Segment<K, V> segmentFor(int paramInt)
  {
    return this.segments[(paramInt >>> this.segmentShift & this.segmentMask)];
  }

  public int size()
  {
    Segment[] arrayOfSegment = this.segments;
    long l = 0L;
    for (int i = 0; i < arrayOfSegment.length; i++)
      l += arrayOfSegment[i].count;
    return Ints.saturatedCast(l);
  }

  boolean usesKeyReferences()
  {
    return this.keyStrength != Strength.STRONG;
  }

  boolean usesValueReferences()
  {
    return this.valueStrength != Strength.STRONG;
  }

  public Collection<V> values()
  {
    Collection localCollection = this.values;
    if (localCollection != null)
      return localCollection;
    Values localValues = new Values();
    this.values = localValues;
    return localValues;
  }

  Object writeReplace()
  {
    return new SerializationProxy(this.keyStrength, this.valueStrength, this.keyEquivalence, this.valueEquivalence, this.expireAfterWriteNanos, this.expireAfterAccessNanos, this.maximumSize, this.concurrencyLevel, this.removalListener, this);
  }

  static abstract class AbstractReferenceEntry<K, V>
    implements CustomConcurrentHashMap.ReferenceEntry<K, V>
  {
    public long getExpirationTime()
    {
      throw new UnsupportedOperationException();
    }

    public int getHash()
    {
      throw new UnsupportedOperationException();
    }

    public K getKey()
    {
      throw new UnsupportedOperationException();
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNext()
    {
      throw new UnsupportedOperationException();
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable()
    {
      throw new UnsupportedOperationException();
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable()
    {
      throw new UnsupportedOperationException();
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable()
    {
      throw new UnsupportedOperationException();
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable()
    {
      throw new UnsupportedOperationException();
    }

    public CustomConcurrentHashMap.ValueReference<K, V> getValueReference()
    {
      throw new UnsupportedOperationException();
    }

    public void setExpirationTime(long paramLong)
    {
      throw new UnsupportedOperationException();
    }

    public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      throw new UnsupportedOperationException();
    }

    public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      throw new UnsupportedOperationException();
    }

    public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      throw new UnsupportedOperationException();
    }

    public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      throw new UnsupportedOperationException();
    }

    public void setValueReference(CustomConcurrentHashMap.ValueReference<K, V> paramValueReference)
    {
      throw new UnsupportedOperationException();
    }
  }

  static abstract class AbstractSerializationProxy<K, V> extends ForwardingConcurrentMap<K, V>
    implements Serializable
  {
    private static final long serialVersionUID = 3L;
    final int concurrencyLevel;
    transient ConcurrentMap<K, V> delegate;
    final long expireAfterAccessNanos;
    final long expireAfterWriteNanos;
    final Equivalence<Object> keyEquivalence;
    final CustomConcurrentHashMap.Strength keyStrength;
    final int maximumSize;
    final MapMaker.RemovalListener<? super K, ? super V> removalListener;
    final Equivalence<Object> valueEquivalence;
    final CustomConcurrentHashMap.Strength valueStrength;

    AbstractSerializationProxy(CustomConcurrentHashMap.Strength paramStrength1, CustomConcurrentHashMap.Strength paramStrength2, Equivalence<Object> paramEquivalence1, Equivalence<Object> paramEquivalence2, long paramLong1, long paramLong2, int paramInt1, int paramInt2, MapMaker.RemovalListener<? super K, ? super V> paramRemovalListener, ConcurrentMap<K, V> paramConcurrentMap)
    {
      this.keyStrength = paramStrength1;
      this.valueStrength = paramStrength2;
      this.keyEquivalence = paramEquivalence1;
      this.valueEquivalence = paramEquivalence2;
      this.expireAfterWriteNanos = paramLong1;
      this.expireAfterAccessNanos = paramLong2;
      this.maximumSize = paramInt1;
      this.concurrencyLevel = paramInt2;
      this.removalListener = paramRemovalListener;
      this.delegate = paramConcurrentMap;
    }

    protected ConcurrentMap<K, V> delegate()
    {
      return this.delegate;
    }

    void readEntries(ObjectInputStream paramObjectInputStream)
      throws IOException, ClassNotFoundException
    {
      while (true)
      {
        Object localObject1 = paramObjectInputStream.readObject();
        if (localObject1 == null)
          return;
        Object localObject2 = paramObjectInputStream.readObject();
        this.delegate.put(localObject1, localObject2);
      }
    }

    MapMaker readMapMaker(ObjectInputStream paramObjectInputStream)
      throws IOException
    {
      int i = paramObjectInputStream.readInt();
      MapMaker localMapMaker = new MapMaker().initialCapacity(i).setKeyStrength(this.keyStrength).setValueStrength(this.valueStrength).keyEquivalence(this.keyEquivalence).valueEquivalence(this.valueEquivalence).concurrencyLevel(this.concurrencyLevel);
      localMapMaker.removalListener(this.removalListener);
      if (this.expireAfterWriteNanos > 0L)
        localMapMaker.expireAfterWrite(this.expireAfterWriteNanos, TimeUnit.NANOSECONDS);
      if (this.expireAfterAccessNanos > 0L)
        localMapMaker.expireAfterAccess(this.expireAfterAccessNanos, TimeUnit.NANOSECONDS);
      if (this.maximumSize != -1)
        localMapMaker.maximumSize(this.maximumSize);
      return localMapMaker;
    }

    void writeMapTo(ObjectOutputStream paramObjectOutputStream)
      throws IOException
    {
      paramObjectOutputStream.writeInt(this.delegate.size());
      Iterator localIterator = this.delegate.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        paramObjectOutputStream.writeObject(localEntry.getKey());
        paramObjectOutputStream.writeObject(localEntry.getValue());
      }
      paramObjectOutputStream.writeObject(null);
    }
  }

  static final class CleanupMapTask
    implements Runnable
  {
    final WeakReference<CustomConcurrentHashMap<?, ?>> mapReference;

    public CleanupMapTask(CustomConcurrentHashMap<?, ?> paramCustomConcurrentHashMap)
    {
      this.mapReference = new WeakReference(paramCustomConcurrentHashMap);
    }

    public void run()
    {
      CustomConcurrentHashMap localCustomConcurrentHashMap = (CustomConcurrentHashMap)this.mapReference.get();
      if (localCustomConcurrentHashMap == null)
        throw new CancellationException();
      CustomConcurrentHashMap.Segment[] arrayOfSegment = localCustomConcurrentHashMap.segments;
      int i = arrayOfSegment.length;
      for (int j = 0; j < i; j++)
        arrayOfSegment[j].runCleanup();
    }
  }

  static abstract enum EntryFactory
  {
    static final int EVICTABLE_MASK = 2;
    static final int EXPIRABLE_MASK = 1;
    static final EntryFactory[][] factories;

    static
    {
      STRONG_EVICTABLE = new EntryFactory("STRONG_EVICTABLE", 2)
      {
        <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> paramSegment, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry1, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry2)
        {
          CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = super.copyEntry(paramSegment, paramReferenceEntry1, paramReferenceEntry2);
          copyEvictableEntry(paramReferenceEntry1, localReferenceEntry);
          return localReferenceEntry;
        }

        <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> paramSegment, K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
        {
          return new CustomConcurrentHashMap.StrongEvictableEntry(paramK, paramInt, paramReferenceEntry);
        }
      };
      STRONG_EXPIRABLE_EVICTABLE = new EntryFactory("STRONG_EXPIRABLE_EVICTABLE", 3)
      {
        <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> paramSegment, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry1, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry2)
        {
          CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = super.copyEntry(paramSegment, paramReferenceEntry1, paramReferenceEntry2);
          copyExpirableEntry(paramReferenceEntry1, localReferenceEntry);
          copyEvictableEntry(paramReferenceEntry1, localReferenceEntry);
          return localReferenceEntry;
        }

        <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> paramSegment, K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
        {
          return new CustomConcurrentHashMap.StrongExpirableEvictableEntry(paramK, paramInt, paramReferenceEntry);
        }
      };
      SOFT = new EntryFactory("SOFT", 4)
      {
        <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> paramSegment, K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
        {
          return new CustomConcurrentHashMap.SoftEntry(paramSegment.keyReferenceQueue, paramK, paramInt, paramReferenceEntry);
        }
      };
      SOFT_EXPIRABLE = new EntryFactory("SOFT_EXPIRABLE", 5)
      {
        <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> paramSegment, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry1, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry2)
        {
          CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = super.copyEntry(paramSegment, paramReferenceEntry1, paramReferenceEntry2);
          copyExpirableEntry(paramReferenceEntry1, localReferenceEntry);
          return localReferenceEntry;
        }

        <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> paramSegment, K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
        {
          return new CustomConcurrentHashMap.SoftExpirableEntry(paramSegment.keyReferenceQueue, paramK, paramInt, paramReferenceEntry);
        }
      };
      SOFT_EVICTABLE = new EntryFactory("SOFT_EVICTABLE", 6)
      {
        <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> paramSegment, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry1, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry2)
        {
          CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = super.copyEntry(paramSegment, paramReferenceEntry1, paramReferenceEntry2);
          copyEvictableEntry(paramReferenceEntry1, localReferenceEntry);
          return localReferenceEntry;
        }

        <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> paramSegment, K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
        {
          return new CustomConcurrentHashMap.SoftEvictableEntry(paramSegment.keyReferenceQueue, paramK, paramInt, paramReferenceEntry);
        }
      };
      SOFT_EXPIRABLE_EVICTABLE = new EntryFactory("SOFT_EXPIRABLE_EVICTABLE", 7)
      {
        <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> paramSegment, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry1, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry2)
        {
          CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = super.copyEntry(paramSegment, paramReferenceEntry1, paramReferenceEntry2);
          copyExpirableEntry(paramReferenceEntry1, localReferenceEntry);
          copyEvictableEntry(paramReferenceEntry1, localReferenceEntry);
          return localReferenceEntry;
        }

        <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> paramSegment, K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
        {
          return new CustomConcurrentHashMap.SoftExpirableEvictableEntry(paramSegment.keyReferenceQueue, paramK, paramInt, paramReferenceEntry);
        }
      };
      WEAK = new EntryFactory("WEAK", 8)
      {
        <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> paramSegment, K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
        {
          return new CustomConcurrentHashMap.WeakEntry(paramSegment.keyReferenceQueue, paramK, paramInt, paramReferenceEntry);
        }
      };
      WEAK_EXPIRABLE = new EntryFactory("WEAK_EXPIRABLE", 9)
      {
        <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> paramSegment, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry1, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry2)
        {
          CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = super.copyEntry(paramSegment, paramReferenceEntry1, paramReferenceEntry2);
          copyExpirableEntry(paramReferenceEntry1, localReferenceEntry);
          return localReferenceEntry;
        }

        <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> paramSegment, K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
        {
          return new CustomConcurrentHashMap.WeakExpirableEntry(paramSegment.keyReferenceQueue, paramK, paramInt, paramReferenceEntry);
        }
      };
      WEAK_EVICTABLE = new EntryFactory("WEAK_EVICTABLE", 10)
      {
        <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> paramSegment, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry1, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry2)
        {
          CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = super.copyEntry(paramSegment, paramReferenceEntry1, paramReferenceEntry2);
          copyEvictableEntry(paramReferenceEntry1, localReferenceEntry);
          return localReferenceEntry;
        }

        <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> paramSegment, K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
        {
          return new CustomConcurrentHashMap.WeakEvictableEntry(paramSegment.keyReferenceQueue, paramK, paramInt, paramReferenceEntry);
        }
      };
      WEAK_EXPIRABLE_EVICTABLE = new EntryFactory("WEAK_EXPIRABLE_EVICTABLE", 11)
      {
        <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> paramSegment, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry1, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry2)
        {
          CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = super.copyEntry(paramSegment, paramReferenceEntry1, paramReferenceEntry2);
          copyExpirableEntry(paramReferenceEntry1, localReferenceEntry);
          copyEvictableEntry(paramReferenceEntry1, localReferenceEntry);
          return localReferenceEntry;
        }

        <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> paramSegment, K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
        {
          return new CustomConcurrentHashMap.WeakExpirableEvictableEntry(paramSegment.keyReferenceQueue, paramK, paramInt, paramReferenceEntry);
        }
      };
      EntryFactory[] arrayOfEntryFactory1 = new EntryFactory[12];
      arrayOfEntryFactory1[0] = STRONG;
      arrayOfEntryFactory1[1] = STRONG_EXPIRABLE;
      arrayOfEntryFactory1[2] = STRONG_EVICTABLE;
      arrayOfEntryFactory1[3] = STRONG_EXPIRABLE_EVICTABLE;
      arrayOfEntryFactory1[4] = SOFT;
      arrayOfEntryFactory1[5] = SOFT_EXPIRABLE;
      arrayOfEntryFactory1[6] = SOFT_EVICTABLE;
      arrayOfEntryFactory1[7] = SOFT_EXPIRABLE_EVICTABLE;
      arrayOfEntryFactory1[8] = WEAK;
      arrayOfEntryFactory1[9] = WEAK_EXPIRABLE;
      arrayOfEntryFactory1[10] = WEAK_EVICTABLE;
      arrayOfEntryFactory1[11] = WEAK_EXPIRABLE_EVICTABLE;
      $VALUES = arrayOfEntryFactory1;
      EntryFactory[][] arrayOfEntryFactory; = new EntryFactory[3][];
      EntryFactory[] arrayOfEntryFactory2 = new EntryFactory[4];
      arrayOfEntryFactory2[0] = STRONG;
      arrayOfEntryFactory2[1] = STRONG_EXPIRABLE;
      arrayOfEntryFactory2[2] = STRONG_EVICTABLE;
      arrayOfEntryFactory2[3] = STRONG_EXPIRABLE_EVICTABLE;
      arrayOfEntryFactory;[0] = arrayOfEntryFactory2;
      EntryFactory[] arrayOfEntryFactory3 = new EntryFactory[4];
      arrayOfEntryFactory3[0] = SOFT;
      arrayOfEntryFactory3[1] = SOFT_EXPIRABLE;
      arrayOfEntryFactory3[2] = SOFT_EVICTABLE;
      arrayOfEntryFactory3[3] = SOFT_EXPIRABLE_EVICTABLE;
      arrayOfEntryFactory;[1] = arrayOfEntryFactory3;
      EntryFactory[] arrayOfEntryFactory4 = new EntryFactory[4];
      arrayOfEntryFactory4[0] = WEAK;
      arrayOfEntryFactory4[1] = WEAK_EXPIRABLE;
      arrayOfEntryFactory4[2] = WEAK_EVICTABLE;
      arrayOfEntryFactory4[3] = WEAK_EXPIRABLE_EVICTABLE;
      arrayOfEntryFactory;[2] = arrayOfEntryFactory4;
      factories = arrayOfEntryFactory;;
    }

    static EntryFactory getFactory(CustomConcurrentHashMap.Strength paramStrength, boolean paramBoolean1, boolean paramBoolean2)
    {
      if (paramBoolean1);
      for (int i = 1; ; i = 0)
      {
        int j = 0;
        if (paramBoolean2)
          j = 2;
        int k = i | j;
        return factories[paramStrength.ordinal()][k];
      }
    }

    @GuardedBy("Segment.this")
    <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> paramSegment, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry1, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry2)
    {
      return newEntry(paramSegment, paramReferenceEntry1.getKey(), paramReferenceEntry1.getHash(), paramReferenceEntry2);
    }

    @GuardedBy("Segment.this")
    <K, V> void copyEvictableEntry(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry1, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry2)
    {
      CustomConcurrentHashMap.connectEvictables(paramReferenceEntry1.getPreviousEvictable(), paramReferenceEntry2);
      CustomConcurrentHashMap.connectEvictables(paramReferenceEntry2, paramReferenceEntry1.getNextEvictable());
      CustomConcurrentHashMap.nullifyEvictable(paramReferenceEntry1);
    }

    @GuardedBy("Segment.this")
    <K, V> void copyExpirableEntry(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry1, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry2)
    {
      paramReferenceEntry2.setExpirationTime(paramReferenceEntry1.getExpirationTime());
      CustomConcurrentHashMap.connectExpirables(paramReferenceEntry1.getPreviousExpirable(), paramReferenceEntry2);
      CustomConcurrentHashMap.connectExpirables(paramReferenceEntry2, paramReferenceEntry1.getNextExpirable());
      CustomConcurrentHashMap.nullifyExpirable(paramReferenceEntry1);
    }

    abstract <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> paramSegment, K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry);
  }

  final class EntryIterator extends CustomConcurrentHashMap<K, V>.HashIterator
    implements Iterator<Map.Entry<K, V>>
  {
    EntryIterator()
    {
      super();
    }

    public Map.Entry<K, V> next()
    {
      return nextEntry();
    }
  }

  final class EntrySet extends AbstractSet<Map.Entry<K, V>>
  {
    EntrySet()
    {
    }

    public void clear()
    {
      CustomConcurrentHashMap.this.clear();
    }

    public boolean contains(Object paramObject)
    {
      if (!(paramObject instanceof Map.Entry));
      Map.Entry localEntry;
      Object localObject2;
      do
      {
        Object localObject1;
        do
        {
          return false;
          localEntry = (Map.Entry)paramObject;
          localObject1 = localEntry.getKey();
        }
        while (localObject1 == null);
        localObject2 = CustomConcurrentHashMap.this.get(localObject1);
      }
      while ((localObject2 == null) || (!CustomConcurrentHashMap.this.valueEquivalence.equivalent(localEntry.getValue(), localObject2)));
      return true;
    }

    public boolean isEmpty()
    {
      return CustomConcurrentHashMap.this.isEmpty();
    }

    public Iterator<Map.Entry<K, V>> iterator()
    {
      return new CustomConcurrentHashMap.EntryIterator(CustomConcurrentHashMap.this);
    }

    public boolean remove(Object paramObject)
    {
      if (!(paramObject instanceof Map.Entry));
      Map.Entry localEntry;
      Object localObject;
      do
      {
        return false;
        localEntry = (Map.Entry)paramObject;
        localObject = localEntry.getKey();
      }
      while ((localObject == null) || (!CustomConcurrentHashMap.this.remove(localObject, localEntry.getValue())));
      return true;
    }

    public int size()
    {
      return CustomConcurrentHashMap.this.size();
    }
  }

  static final class EvictionQueue<K, V> extends AbstractQueue<CustomConcurrentHashMap.ReferenceEntry<K, V>>
  {
    final CustomConcurrentHashMap.ReferenceEntry<K, V> head = new CustomConcurrentHashMap.AbstractReferenceEntry()
    {
      CustomConcurrentHashMap.ReferenceEntry<K, V> nextEvictable = this;
      CustomConcurrentHashMap.ReferenceEntry<K, V> previousEvictable = this;

      public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable()
      {
        return this.nextEvictable;
      }

      public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable()
      {
        return this.previousEvictable;
      }

      public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
      {
        this.nextEvictable = paramReferenceEntry;
      }

      public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
      {
        this.previousEvictable = paramReferenceEntry;
      }
    };

    public void clear()
    {
      CustomConcurrentHashMap.ReferenceEntry localReferenceEntry;
      for (Object localObject = this.head.getNextEvictable(); localObject != this.head; localObject = localReferenceEntry)
      {
        localReferenceEntry = ((CustomConcurrentHashMap.ReferenceEntry)localObject).getNextEvictable();
        CustomConcurrentHashMap.nullifyEvictable((CustomConcurrentHashMap.ReferenceEntry)localObject);
      }
      this.head.setNextEvictable(this.head);
      this.head.setPreviousEvictable(this.head);
    }

    public boolean contains(Object paramObject)
    {
      return ((CustomConcurrentHashMap.ReferenceEntry)paramObject).getNextEvictable() != CustomConcurrentHashMap.NullEntry.INSTANCE;
    }

    public boolean isEmpty()
    {
      return this.head.getNextEvictable() == this.head;
    }

    public Iterator<CustomConcurrentHashMap.ReferenceEntry<K, V>> iterator()
    {
      return new AbstractLinkedIterator(peek())
      {
        protected CustomConcurrentHashMap.ReferenceEntry<K, V> computeNext(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
        {
          CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = paramReferenceEntry.getNextEvictable();
          if (localReferenceEntry == CustomConcurrentHashMap.EvictionQueue.this.head)
            localReferenceEntry = null;
          return localReferenceEntry;
        }
      };
    }

    public boolean offer(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      CustomConcurrentHashMap.connectEvictables(paramReferenceEntry.getPreviousEvictable(), paramReferenceEntry.getNextEvictable());
      CustomConcurrentHashMap.connectEvictables(this.head.getPreviousEvictable(), paramReferenceEntry);
      CustomConcurrentHashMap.connectEvictables(paramReferenceEntry, this.head);
      return true;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> peek()
    {
      CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = this.head.getNextEvictable();
      if (localReferenceEntry == this.head)
        localReferenceEntry = null;
      return localReferenceEntry;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> poll()
    {
      CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = this.head.getNextEvictable();
      if (localReferenceEntry == this.head)
        return null;
      remove(localReferenceEntry);
      return localReferenceEntry;
    }

    public boolean remove(Object paramObject)
    {
      CustomConcurrentHashMap.ReferenceEntry localReferenceEntry1 = (CustomConcurrentHashMap.ReferenceEntry)paramObject;
      CustomConcurrentHashMap.ReferenceEntry localReferenceEntry2 = localReferenceEntry1.getPreviousEvictable();
      CustomConcurrentHashMap.ReferenceEntry localReferenceEntry3 = localReferenceEntry1.getNextEvictable();
      CustomConcurrentHashMap.connectEvictables(localReferenceEntry2, localReferenceEntry3);
      CustomConcurrentHashMap.nullifyEvictable(localReferenceEntry1);
      return localReferenceEntry3 != CustomConcurrentHashMap.NullEntry.INSTANCE;
    }

    public int size()
    {
      int i = 0;
      for (CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = this.head.getNextEvictable(); localReferenceEntry != this.head; localReferenceEntry = localReferenceEntry.getNextEvictable())
        i++;
      return i;
    }
  }

  static final class ExpirationQueue<K, V> extends AbstractQueue<CustomConcurrentHashMap.ReferenceEntry<K, V>>
  {
    final CustomConcurrentHashMap.ReferenceEntry<K, V> head = new CustomConcurrentHashMap.AbstractReferenceEntry()
    {
      CustomConcurrentHashMap.ReferenceEntry<K, V> nextExpirable = this;
      CustomConcurrentHashMap.ReferenceEntry<K, V> previousExpirable = this;

      public long getExpirationTime()
      {
        return 9223372036854775807L;
      }

      public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable()
      {
        return this.nextExpirable;
      }

      public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable()
      {
        return this.previousExpirable;
      }

      public void setExpirationTime(long paramLong)
      {
      }

      public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
      {
        this.nextExpirable = paramReferenceEntry;
      }

      public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
      {
        this.previousExpirable = paramReferenceEntry;
      }
    };

    public void clear()
    {
      CustomConcurrentHashMap.ReferenceEntry localReferenceEntry;
      for (Object localObject = this.head.getNextExpirable(); localObject != this.head; localObject = localReferenceEntry)
      {
        localReferenceEntry = ((CustomConcurrentHashMap.ReferenceEntry)localObject).getNextExpirable();
        CustomConcurrentHashMap.nullifyExpirable((CustomConcurrentHashMap.ReferenceEntry)localObject);
      }
      this.head.setNextExpirable(this.head);
      this.head.setPreviousExpirable(this.head);
    }

    public boolean contains(Object paramObject)
    {
      return ((CustomConcurrentHashMap.ReferenceEntry)paramObject).getNextExpirable() != CustomConcurrentHashMap.NullEntry.INSTANCE;
    }

    public boolean isEmpty()
    {
      return this.head.getNextExpirable() == this.head;
    }

    public Iterator<CustomConcurrentHashMap.ReferenceEntry<K, V>> iterator()
    {
      return new AbstractLinkedIterator(peek())
      {
        protected CustomConcurrentHashMap.ReferenceEntry<K, V> computeNext(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
        {
          CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = paramReferenceEntry.getNextExpirable();
          if (localReferenceEntry == CustomConcurrentHashMap.ExpirationQueue.this.head)
            localReferenceEntry = null;
          return localReferenceEntry;
        }
      };
    }

    public boolean offer(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      CustomConcurrentHashMap.connectExpirables(paramReferenceEntry.getPreviousExpirable(), paramReferenceEntry.getNextExpirable());
      CustomConcurrentHashMap.connectExpirables(this.head.getPreviousExpirable(), paramReferenceEntry);
      CustomConcurrentHashMap.connectExpirables(paramReferenceEntry, this.head);
      return true;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> peek()
    {
      CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = this.head.getNextExpirable();
      if (localReferenceEntry == this.head)
        localReferenceEntry = null;
      return localReferenceEntry;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> poll()
    {
      CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = this.head.getNextExpirable();
      if (localReferenceEntry == this.head)
        return null;
      remove(localReferenceEntry);
      return localReferenceEntry;
    }

    public boolean remove(Object paramObject)
    {
      CustomConcurrentHashMap.ReferenceEntry localReferenceEntry1 = (CustomConcurrentHashMap.ReferenceEntry)paramObject;
      CustomConcurrentHashMap.ReferenceEntry localReferenceEntry2 = localReferenceEntry1.getPreviousExpirable();
      CustomConcurrentHashMap.ReferenceEntry localReferenceEntry3 = localReferenceEntry1.getNextExpirable();
      CustomConcurrentHashMap.connectExpirables(localReferenceEntry2, localReferenceEntry3);
      CustomConcurrentHashMap.nullifyExpirable(localReferenceEntry1);
      return localReferenceEntry3 != CustomConcurrentHashMap.NullEntry.INSTANCE;
    }

    public int size()
    {
      int i = 0;
      for (CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = this.head.getNextExpirable(); localReferenceEntry != this.head; localReferenceEntry = localReferenceEntry.getNextExpirable())
        i++;
      return i;
    }
  }

  abstract class HashIterator
  {
    CustomConcurrentHashMap.Segment<K, V> currentSegment;
    AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> currentTable;
    CustomConcurrentHashMap<K, V>.WriteThroughEntry lastReturned;
    CustomConcurrentHashMap.ReferenceEntry<K, V> nextEntry;
    CustomConcurrentHashMap<K, V>.WriteThroughEntry nextExternal;
    int nextSegmentIndex = -1 + CustomConcurrentHashMap.this.segments.length;
    int nextTableIndex = -1;

    HashIterator()
    {
      advance();
    }

    final void advance()
    {
      this.nextExternal = null;
      if (nextInChain());
      label12: 
      do
      {
        do
        {
          do
          {
            return;
            continue;
            break label12;
            continue;
            while (nextInTable());
          }
          while (this.nextSegmentIndex < 0);
          CustomConcurrentHashMap.Segment[] arrayOfSegment = CustomConcurrentHashMap.this.segments;
          int i = this.nextSegmentIndex;
          this.nextSegmentIndex = (i - 1);
          this.currentSegment = arrayOfSegment[i];
        }
        while (this.currentSegment.count == 0);
        this.currentTable = this.currentSegment.table;
        this.nextTableIndex = (-1 + this.currentTable.length());
      }
      while (!nextInTable());
    }

    boolean advanceTo(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      try
      {
        Object localObject2 = paramReferenceEntry.getKey();
        Object localObject3 = CustomConcurrentHashMap.this.getLiveValue(paramReferenceEntry);
        if (localObject3 != null)
        {
          this.nextExternal = new CustomConcurrentHashMap.WriteThroughEntry(CustomConcurrentHashMap.this, localObject2, localObject3);
          return true;
        }
        return false;
      }
      finally
      {
        this.currentSegment.postReadCleanup();
      }
      throw localObject1;
    }

    public boolean hasNext()
    {
      return this.nextExternal != null;
    }

    CustomConcurrentHashMap<K, V>.WriteThroughEntry nextEntry()
    {
      if (this.nextExternal == null)
        throw new NoSuchElementException();
      this.lastReturned = this.nextExternal;
      advance();
      return this.lastReturned;
    }

    boolean nextInChain()
    {
      if (this.nextEntry != null)
        for (this.nextEntry = this.nextEntry.getNext(); this.nextEntry != null; this.nextEntry = this.nextEntry.getNext())
          if (advanceTo(this.nextEntry))
            return true;
      return false;
    }

    boolean nextInTable()
    {
      while (this.nextTableIndex >= 0)
      {
        AtomicReferenceArray localAtomicReferenceArray = this.currentTable;
        int i = this.nextTableIndex;
        this.nextTableIndex = (i - 1);
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = (CustomConcurrentHashMap.ReferenceEntry)localAtomicReferenceArray.get(i);
        this.nextEntry = localReferenceEntry;
        if ((localReferenceEntry != null) && ((advanceTo(this.nextEntry)) || (nextInChain())))
          return true;
      }
      return false;
    }

    public void remove()
    {
      if (this.lastReturned != null);
      for (boolean bool = true; ; bool = false)
      {
        Preconditions.checkState(bool);
        CustomConcurrentHashMap.this.remove(this.lastReturned.getKey());
        this.lastReturned = null;
        return;
      }
    }
  }

  final class KeyIterator extends CustomConcurrentHashMap<K, V>.HashIterator
    implements Iterator<K>
  {
    KeyIterator()
    {
      super();
    }

    public K next()
    {
      return nextEntry().getKey();
    }
  }

  final class KeySet extends AbstractSet<K>
  {
    KeySet()
    {
    }

    public void clear()
    {
      CustomConcurrentHashMap.this.clear();
    }

    public boolean contains(Object paramObject)
    {
      return CustomConcurrentHashMap.this.containsKey(paramObject);
    }

    public boolean isEmpty()
    {
      return CustomConcurrentHashMap.this.isEmpty();
    }

    public Iterator<K> iterator()
    {
      return new CustomConcurrentHashMap.KeyIterator(CustomConcurrentHashMap.this);
    }

    public boolean remove(Object paramObject)
    {
      return CustomConcurrentHashMap.this.remove(paramObject) != null;
    }

    public int size()
    {
      return CustomConcurrentHashMap.this.size();
    }
  }

  private static enum NullEntry
    implements CustomConcurrentHashMap.ReferenceEntry<Object, Object>
  {
    static
    {
      NullEntry[] arrayOfNullEntry = new NullEntry[1];
      arrayOfNullEntry[0] = INSTANCE;
      $VALUES = arrayOfNullEntry;
    }

    public long getExpirationTime()
    {
      return 0L;
    }

    public int getHash()
    {
      return 0;
    }

    public Object getKey()
    {
      return null;
    }

    public CustomConcurrentHashMap.ReferenceEntry<Object, Object> getNext()
    {
      return null;
    }

    public CustomConcurrentHashMap.ReferenceEntry<Object, Object> getNextEvictable()
    {
      return this;
    }

    public CustomConcurrentHashMap.ReferenceEntry<Object, Object> getNextExpirable()
    {
      return this;
    }

    public CustomConcurrentHashMap.ReferenceEntry<Object, Object> getPreviousEvictable()
    {
      return this;
    }

    public CustomConcurrentHashMap.ReferenceEntry<Object, Object> getPreviousExpirable()
    {
      return this;
    }

    public CustomConcurrentHashMap.ValueReference<Object, Object> getValueReference()
    {
      return null;
    }

    public void setExpirationTime(long paramLong)
    {
    }

    public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<Object, Object> paramReferenceEntry)
    {
    }

    public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<Object, Object> paramReferenceEntry)
    {
    }

    public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<Object, Object> paramReferenceEntry)
    {
    }

    public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<Object, Object> paramReferenceEntry)
    {
    }

    public void setValueReference(CustomConcurrentHashMap.ValueReference<Object, Object> paramValueReference)
    {
    }
  }

  static abstract interface ReferenceEntry<K, V>
  {
    public abstract long getExpirationTime();

    public abstract int getHash();

    public abstract K getKey();

    public abstract ReferenceEntry<K, V> getNext();

    public abstract ReferenceEntry<K, V> getNextEvictable();

    public abstract ReferenceEntry<K, V> getNextExpirable();

    public abstract ReferenceEntry<K, V> getPreviousEvictable();

    public abstract ReferenceEntry<K, V> getPreviousExpirable();

    public abstract CustomConcurrentHashMap.ValueReference<K, V> getValueReference();

    public abstract void setExpirationTime(long paramLong);

    public abstract void setNextEvictable(ReferenceEntry<K, V> paramReferenceEntry);

    public abstract void setNextExpirable(ReferenceEntry<K, V> paramReferenceEntry);

    public abstract void setPreviousEvictable(ReferenceEntry<K, V> paramReferenceEntry);

    public abstract void setPreviousExpirable(ReferenceEntry<K, V> paramReferenceEntry);

    public abstract void setValueReference(CustomConcurrentHashMap.ValueReference<K, V> paramValueReference);
  }

  static class Segment<K, V> extends ReentrantLock
  {
    volatile int count;

    @GuardedBy("Segment.this")
    final Queue<CustomConcurrentHashMap.ReferenceEntry<K, V>> evictionQueue;

    @GuardedBy("Segment.this")
    final Queue<CustomConcurrentHashMap.ReferenceEntry<K, V>> expirationQueue;
    final ReferenceQueue<K> keyReferenceQueue;
    final CustomConcurrentHashMap<K, V> map;
    final int maxSegmentSize;
    int modCount;
    final AtomicInteger readCount = new AtomicInteger();
    final Queue<CustomConcurrentHashMap.ReferenceEntry<K, V>> recencyQueue;
    volatile AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table;
    int threshold;
    final ReferenceQueue<V> valueReferenceQueue;

    Segment(CustomConcurrentHashMap<K, V> paramCustomConcurrentHashMap, int paramInt1, int paramInt2)
    {
      this.map = paramCustomConcurrentHashMap;
      this.maxSegmentSize = paramInt2;
      initTable(newEntryArray(paramInt1));
      ReferenceQueue localReferenceQueue1;
      Object localObject1;
      label108: Object localObject2;
      if (paramCustomConcurrentHashMap.usesKeyReferences())
      {
        localReferenceQueue1 = new ReferenceQueue();
        this.keyReferenceQueue = localReferenceQueue1;
        boolean bool = paramCustomConcurrentHashMap.usesValueReferences();
        ReferenceQueue localReferenceQueue2 = null;
        if (bool)
          localReferenceQueue2 = new ReferenceQueue();
        this.valueReferenceQueue = localReferenceQueue2;
        if ((!paramCustomConcurrentHashMap.evictsBySize()) && (!paramCustomConcurrentHashMap.expiresAfterAccess()))
          break label165;
        localObject1 = new ConcurrentLinkedQueue();
        this.recencyQueue = ((Queue)localObject1);
        if (!paramCustomConcurrentHashMap.evictsBySize())
          break label173;
        localObject2 = new CustomConcurrentHashMap.EvictionQueue();
        label130: this.evictionQueue = ((Queue)localObject2);
        if (!paramCustomConcurrentHashMap.expires())
          break label181;
      }
      label165: label173: label181: for (Object localObject3 = new CustomConcurrentHashMap.ExpirationQueue(); ; localObject3 = CustomConcurrentHashMap.discardingQueue())
      {
        this.expirationQueue = ((Queue)localObject3);
        return;
        localReferenceQueue1 = null;
        break;
        localObject1 = CustomConcurrentHashMap.discardingQueue();
        break label108;
        localObject2 = CustomConcurrentHashMap.discardingQueue();
        break label130;
      }
    }

    void clear()
    {
      if (this.count != 0)
        lock();
      while (true)
      {
        int i;
        try
        {
          AtomicReferenceArray localAtomicReferenceArray = this.table;
          if (this.map.removalNotificationQueue == CustomConcurrentHashMap.DISCARDING_QUEUE)
            break label183;
          i = 0;
          if (i >= localAtomicReferenceArray.length())
            break label183;
          CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = (CustomConcurrentHashMap.ReferenceEntry)localAtomicReferenceArray.get(i);
          if (localReferenceEntry != null)
          {
            if (localReferenceEntry.getValueReference().isComputingReference())
              continue;
            enqueueNotification(localReferenceEntry, MapMaker.RemovalCause.EXPLICIT);
            localReferenceEntry = localReferenceEntry.getNext();
            continue;
            if (j >= localAtomicReferenceArray.length())
              continue;
            localAtomicReferenceArray.set(j, null);
            j++;
            continue;
            clearReferenceQueues();
            this.evictionQueue.clear();
            this.expirationQueue.clear();
            this.readCount.set(0);
            this.modCount = (1 + this.modCount);
            this.count = 0;
            return;
          }
        }
        finally
        {
          unlock();
          postWriteCleanup();
        }
        i++;
        continue;
        label183: int j = 0;
      }
    }

    void clearKeyReferenceQueue()
    {
      while (this.keyReferenceQueue.poll() != null);
    }

    void clearReferenceQueues()
    {
      if (this.map.usesKeyReferences())
        clearKeyReferenceQueue();
      if (this.map.usesValueReferences())
        clearValueReferenceQueue();
    }

    boolean clearValue(K paramK, int paramInt, CustomConcurrentHashMap.ValueReference<K, V> paramValueReference)
    {
      lock();
      try
      {
        AtomicReferenceArray localAtomicReferenceArray = this.table;
        int i = paramInt & -1 + localAtomicReferenceArray.length();
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry1 = (CustomConcurrentHashMap.ReferenceEntry)localAtomicReferenceArray.get(i);
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry2;
        for (Object localObject2 = localReferenceEntry1; localObject2 != null; localObject2 = localReferenceEntry2)
        {
          Object localObject3 = ((CustomConcurrentHashMap.ReferenceEntry)localObject2).getKey();
          if ((((CustomConcurrentHashMap.ReferenceEntry)localObject2).getHash() == paramInt) && (localObject3 != null) && (this.map.keyEquivalence.equivalent(paramK, localObject3)))
          {
            if (((CustomConcurrentHashMap.ReferenceEntry)localObject2).getValueReference() == paramValueReference)
            {
              localAtomicReferenceArray.set(i, removeFromChain(localReferenceEntry1, (CustomConcurrentHashMap.ReferenceEntry)localObject2));
              return true;
            }
            return false;
          }
          localReferenceEntry2 = ((CustomConcurrentHashMap.ReferenceEntry)localObject2).getNext();
        }
        return false;
      }
      finally
      {
        unlock();
        postWriteCleanup();
      }
      throw localObject1;
    }

    void clearValueReferenceQueue()
    {
      while (this.valueReferenceQueue.poll() != null);
    }

    boolean containsKey(Object paramObject, int paramInt)
    {
      try
      {
        if (this.count != 0)
        {
          CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = getLiveEntry(paramObject, paramInt);
          if (localReferenceEntry == null)
            return false;
          Object localObject2 = localReferenceEntry.getValueReference().get();
          int i = 0;
          if (localObject2 != null)
            i = 1;
          return i;
        }
        return false;
      }
      finally
      {
        postReadCleanup();
      }
      throw localObject1;
    }

    @VisibleForTesting
    boolean containsValue(Object paramObject)
    {
      try
      {
        if (this.count != 0)
        {
          AtomicReferenceArray localAtomicReferenceArray = this.table;
          int i = localAtomicReferenceArray.length();
          for (int j = 0; j < i; j++)
          {
            CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = (CustomConcurrentHashMap.ReferenceEntry)localAtomicReferenceArray.get(j);
            if (localReferenceEntry == null)
              continue;
            Object localObject2 = getLiveValue(localReferenceEntry);
            if (localObject2 == null);
            boolean bool;
            do
            {
              localReferenceEntry = localReferenceEntry.getNext();
              break;
              bool = this.map.valueEquivalence.equivalent(paramObject, localObject2);
            }
            while (!bool);
            return true;
          }
        }
        return false;
      }
      finally
      {
        postReadCleanup();
      }
      throw localObject1;
    }

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry1, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry2)
    {
      CustomConcurrentHashMap.ValueReference localValueReference = paramReferenceEntry1.getValueReference();
      CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = this.map.entryFactory.copyEntry(this, paramReferenceEntry1, paramReferenceEntry2);
      localReferenceEntry.setValueReference(localValueReference.copyFor(this.valueReferenceQueue, localReferenceEntry));
      return localReferenceEntry;
    }

    @GuardedBy("Segment.this")
    void drainKeyReferenceQueue()
    {
      int i = 0;
      do
      {
        Reference localReference = this.keyReferenceQueue.poll();
        if (localReference == null)
          break;
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = (CustomConcurrentHashMap.ReferenceEntry)localReference;
        this.map.reclaimKey(localReferenceEntry);
        i++;
      }
      while (i != 16);
    }

    @GuardedBy("Segment.this")
    void drainRecencyQueue()
    {
      while (true)
      {
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = (CustomConcurrentHashMap.ReferenceEntry)this.recencyQueue.poll();
        if (localReferenceEntry == null)
          break;
        if (this.evictionQueue.contains(localReferenceEntry))
          this.evictionQueue.add(localReferenceEntry);
        if ((!this.map.expiresAfterAccess()) || (!this.expirationQueue.contains(localReferenceEntry)))
          continue;
        this.expirationQueue.add(localReferenceEntry);
      }
    }

    @GuardedBy("Segment.this")
    void drainReferenceQueues()
    {
      if (this.map.usesKeyReferences())
        drainKeyReferenceQueue();
      if (this.map.usesValueReferences())
        drainValueReferenceQueue();
    }

    @GuardedBy("Segment.this")
    void drainValueReferenceQueue()
    {
      int i = 0;
      do
      {
        Reference localReference = this.valueReferenceQueue.poll();
        if (localReference == null)
          break;
        CustomConcurrentHashMap.ValueReference localValueReference = (CustomConcurrentHashMap.ValueReference)localReference;
        this.map.reclaimValue(localValueReference);
        i++;
      }
      while (i != 16);
    }

    void enqueueNotification(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry, MapMaker.RemovalCause paramRemovalCause)
    {
      enqueueNotification(paramReferenceEntry.getKey(), paramReferenceEntry.getHash(), paramReferenceEntry.getValueReference().get(), paramRemovalCause);
    }

    void enqueueNotification(@Nullable K paramK, int paramInt, @Nullable V paramV, MapMaker.RemovalCause paramRemovalCause)
    {
      if (this.map.removalNotificationQueue != CustomConcurrentHashMap.DISCARDING_QUEUE)
      {
        MapMaker.RemovalNotification localRemovalNotification = new MapMaker.RemovalNotification(paramK, paramV, paramRemovalCause);
        this.map.removalNotificationQueue.offer(localRemovalNotification);
      }
    }

    @GuardedBy("Segment.this")
    boolean evictEntries()
    {
      if ((this.map.evictsBySize()) && (this.count >= this.maxSegmentSize))
      {
        drainRecencyQueue();
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = (CustomConcurrentHashMap.ReferenceEntry)this.evictionQueue.remove();
        if (!removeEntry(localReferenceEntry, localReferenceEntry.getHash(), MapMaker.RemovalCause.SIZE))
          throw new AssertionError();
        return true;
      }
      return false;
    }

    @GuardedBy("Segment.this")
    void expand()
    {
      AtomicReferenceArray localAtomicReferenceArray1 = this.table;
      int i = localAtomicReferenceArray1.length();
      if (i >= 1073741824)
        return;
      int j = this.count;
      AtomicReferenceArray localAtomicReferenceArray2 = newEntryArray(i << 1);
      this.threshold = (3 * localAtomicReferenceArray2.length() / 4);
      int k = -1 + localAtomicReferenceArray2.length();
      int m = 0;
      while (m < i)
      {
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry1 = (CustomConcurrentHashMap.ReferenceEntry)localAtomicReferenceArray1.get(m);
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry2;
        int n;
        if (localReferenceEntry1 != null)
        {
          localReferenceEntry2 = localReferenceEntry1.getNext();
          n = k & localReferenceEntry1.getHash();
          if (localReferenceEntry2 == null)
            localAtomicReferenceArray2.set(n, localReferenceEntry1);
        }
        else
        {
          m++;
          continue;
        }
        Object localObject = localReferenceEntry1;
        int i1 = n;
        for (CustomConcurrentHashMap.ReferenceEntry localReferenceEntry3 = localReferenceEntry2; localReferenceEntry3 != null; localReferenceEntry3 = localReferenceEntry3.getNext())
        {
          int i3 = k & localReferenceEntry3.getHash();
          if (i3 == i1)
            continue;
          i1 = i3;
          localObject = localReferenceEntry3;
        }
        localAtomicReferenceArray2.set(i1, localObject);
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry4 = localReferenceEntry1;
        label189: if (localReferenceEntry4 != localObject)
        {
          if (!isCollected(localReferenceEntry4))
            break label226;
          removeCollectedEntry(localReferenceEntry4);
          j--;
        }
        while (true)
        {
          localReferenceEntry4 = localReferenceEntry4.getNext();
          break label189;
          break;
          label226: int i2 = k & localReferenceEntry4.getHash();
          localAtomicReferenceArray2.set(i2, copyEntry(localReferenceEntry4, (CustomConcurrentHashMap.ReferenceEntry)localAtomicReferenceArray2.get(i2)));
        }
      }
      this.table = localAtomicReferenceArray2;
      this.count = j;
    }

    @GuardedBy("Segment.this")
    void expireEntries()
    {
      drainRecencyQueue();
      if (this.expirationQueue.isEmpty())
        return;
      long l = this.map.ticker.read();
      CustomConcurrentHashMap.ReferenceEntry localReferenceEntry;
      do
      {
        localReferenceEntry = (CustomConcurrentHashMap.ReferenceEntry)this.expirationQueue.peek();
        if ((localReferenceEntry == null) || (!this.map.isExpired(localReferenceEntry, l)))
          break;
      }
      while (removeEntry(localReferenceEntry, localReferenceEntry.getHash(), MapMaker.RemovalCause.EXPIRED));
      throw new AssertionError();
    }

    V get(Object paramObject, int paramInt)
    {
      try
      {
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = getLiveEntry(paramObject, paramInt);
        if (localReferenceEntry == null)
          return null;
        Object localObject2 = localReferenceEntry.getValueReference().get();
        if (localObject2 != null)
          recordRead(localReferenceEntry);
        while (true)
        {
          return localObject2;
          tryDrainReferenceQueues();
        }
      }
      finally
      {
        postReadCleanup();
      }
      throw localObject1;
    }

    CustomConcurrentHashMap.ReferenceEntry<K, V> getEntry(Object paramObject, int paramInt)
    {
      if (this.count != 0)
      {
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = getFirst(paramInt);
        if (localReferenceEntry != null)
        {
          if (localReferenceEntry.getHash() != paramInt);
          Object localObject;
          label57: 
          do
            while (true)
            {
              localReferenceEntry = localReferenceEntry.getNext();
              break;
              localObject = localReferenceEntry.getKey();
              if (localObject != null)
                break label57;
              tryDrainReferenceQueues();
            }
          while (!this.map.keyEquivalence.equivalent(paramObject, localObject));
          return localReferenceEntry;
        }
      }
      return null;
    }

    CustomConcurrentHashMap.ReferenceEntry<K, V> getFirst(int paramInt)
    {
      AtomicReferenceArray localAtomicReferenceArray = this.table;
      return (CustomConcurrentHashMap.ReferenceEntry)localAtomicReferenceArray.get(paramInt & -1 + localAtomicReferenceArray.length());
    }

    CustomConcurrentHashMap.ReferenceEntry<K, V> getLiveEntry(Object paramObject, int paramInt)
    {
      CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = getEntry(paramObject, paramInt);
      if (localReferenceEntry == null)
        localReferenceEntry = null;
      do
        return localReferenceEntry;
      while ((!this.map.expires()) || (!this.map.isExpired(localReferenceEntry)));
      tryExpireEntries();
      return null;
    }

    V getLiveValue(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      Object localObject;
      if (paramReferenceEntry.getKey() == null)
      {
        tryDrainReferenceQueues();
        localObject = null;
      }
      do
      {
        return localObject;
        localObject = paramReferenceEntry.getValueReference().get();
        if (localObject != null)
          continue;
        tryDrainReferenceQueues();
        return null;
      }
      while ((!this.map.expires()) || (!this.map.isExpired(paramReferenceEntry)));
      tryExpireEntries();
      return null;
    }

    void initTable(AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> paramAtomicReferenceArray)
    {
      this.threshold = (3 * paramAtomicReferenceArray.length() / 4);
      if (this.threshold == this.maxSegmentSize)
        this.threshold = (1 + this.threshold);
      this.table = paramAtomicReferenceArray;
    }

    boolean isCollected(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      if (paramReferenceEntry.getKey() == null)
        return true;
      return isCollected(paramReferenceEntry.getValueReference());
    }

    boolean isCollected(CustomConcurrentHashMap.ValueReference<K, V> paramValueReference)
    {
      if (paramValueReference.isComputingReference());
      do
        return false;
      while (paramValueReference.get() != null);
      return true;
    }

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      return this.map.entryFactory.newEntry(this, paramK, paramInt, paramReferenceEntry);
    }

    AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> newEntryArray(int paramInt)
    {
      return new AtomicReferenceArray(paramInt);
    }

    void postReadCleanup()
    {
      if ((0x3F & this.readCount.incrementAndGet()) == 0)
        runCleanup();
    }

    void postWriteCleanup()
    {
      runUnlockedCleanup();
    }

    @GuardedBy("Segment.this")
    void preWriteCleanup()
    {
      runLockedCleanup();
    }

    V put(K paramK, int paramInt, V paramV, boolean paramBoolean)
    {
      lock();
      try
      {
        preWriteCleanup();
        int i = 1 + this.count;
        if (i > this.threshold)
        {
          expand();
          i = 1 + this.count;
        }
        AtomicReferenceArray localAtomicReferenceArray = this.table;
        int j = paramInt & -1 + localAtomicReferenceArray.length();
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry1 = (CustomConcurrentHashMap.ReferenceEntry)localAtomicReferenceArray.get(j);
        for (CustomConcurrentHashMap.ReferenceEntry localReferenceEntry2 = localReferenceEntry1; localReferenceEntry2 != null; localReferenceEntry2 = localReferenceEntry2.getNext())
        {
          Object localObject2 = localReferenceEntry2.getKey();
          if ((localReferenceEntry2.getHash() != paramInt) || (localObject2 == null) || (!this.map.keyEquivalence.equivalent(paramK, localObject2)))
            continue;
          CustomConcurrentHashMap.ValueReference localValueReference = localReferenceEntry2.getValueReference();
          Object localObject3 = localValueReference.get();
          if (localObject3 == null)
          {
            this.modCount = (1 + this.modCount);
            setValue(localReferenceEntry2, paramV);
            if (!localValueReference.isComputingReference())
            {
              enqueueNotification(paramK, paramInt, localObject3, MapMaker.RemovalCause.COLLECTED);
              i = this.count;
            }
            while (true)
            {
              this.count = i;
              return null;
              if (!evictEntries())
                continue;
              i = 1 + this.count;
            }
          }
          if (paramBoolean)
          {
            recordLockedRead(localReferenceEntry2);
            return localObject3;
          }
          this.modCount = (1 + this.modCount);
          enqueueNotification(paramK, paramInt, localObject3, MapMaker.RemovalCause.REPLACED);
          setValue(localReferenceEntry2, paramV);
          return localObject3;
        }
        this.modCount = (1 + this.modCount);
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry3 = newEntry(paramK, paramInt, localReferenceEntry1);
        setValue(localReferenceEntry3, paramV);
        localAtomicReferenceArray.set(j, localReferenceEntry3);
        if (evictEntries())
          i = 1 + this.count;
        this.count = i;
        return null;
      }
      finally
      {
        unlock();
        postWriteCleanup();
      }
      throw localObject1;
    }

    boolean reclaimKey(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry, int paramInt)
    {
      lock();
      try
      {
        (-1 + this.count);
        AtomicReferenceArray localAtomicReferenceArray = this.table;
        int i = paramInt & -1 + localAtomicReferenceArray.length();
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry1 = (CustomConcurrentHashMap.ReferenceEntry)localAtomicReferenceArray.get(i);
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry3;
        for (Object localObject2 = localReferenceEntry1; localObject2 != null; localObject2 = localReferenceEntry3)
        {
          if (localObject2 == paramReferenceEntry)
          {
            this.modCount = (1 + this.modCount);
            enqueueNotification(((CustomConcurrentHashMap.ReferenceEntry)localObject2).getKey(), paramInt, ((CustomConcurrentHashMap.ReferenceEntry)localObject2).getValueReference().get(), MapMaker.RemovalCause.COLLECTED);
            CustomConcurrentHashMap.ReferenceEntry localReferenceEntry2 = removeFromChain(localReferenceEntry1, (CustomConcurrentHashMap.ReferenceEntry)localObject2);
            int j = -1 + this.count;
            localAtomicReferenceArray.set(i, localReferenceEntry2);
            this.count = j;
            return true;
          }
          localReferenceEntry3 = ((CustomConcurrentHashMap.ReferenceEntry)localObject2).getNext();
        }
        return false;
      }
      finally
      {
        unlock();
        postWriteCleanup();
      }
      throw localObject1;
    }

    boolean reclaimValue(K paramK, int paramInt, CustomConcurrentHashMap.ValueReference<K, V> paramValueReference)
    {
      lock();
      try
      {
        (-1 + this.count);
        AtomicReferenceArray localAtomicReferenceArray = this.table;
        int i = paramInt & -1 + localAtomicReferenceArray.length();
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry1 = (CustomConcurrentHashMap.ReferenceEntry)localAtomicReferenceArray.get(i);
        Object localObject2 = localReferenceEntry1;
        int j;
        if (localObject2 != null)
        {
          Object localObject3 = ((CustomConcurrentHashMap.ReferenceEntry)localObject2).getKey();
          if ((((CustomConcurrentHashMap.ReferenceEntry)localObject2).getHash() == paramInt) && (localObject3 != null) && (this.map.keyEquivalence.equivalent(paramK, localObject3)))
            if (((CustomConcurrentHashMap.ReferenceEntry)localObject2).getValueReference() == paramValueReference)
            {
              this.modCount = (1 + this.modCount);
              enqueueNotification(paramK, paramInt, paramValueReference.get(), MapMaker.RemovalCause.COLLECTED);
              CustomConcurrentHashMap.ReferenceEntry localReferenceEntry3 = removeFromChain(localReferenceEntry1, (CustomConcurrentHashMap.ReferenceEntry)localObject2);
              int k = -1 + this.count;
              localAtomicReferenceArray.set(i, localReferenceEntry3);
              this.count = k;
              j = 1;
            }
        }
        boolean bool2;
        do
        {
          boolean bool1;
          do
          {
            return j;
            unlock();
            bool1 = isHeldByCurrentThread();
            j = 0;
          }
          while (bool1);
          postWriteCleanup();
          return false;
          CustomConcurrentHashMap.ReferenceEntry localReferenceEntry2 = ((CustomConcurrentHashMap.ReferenceEntry)localObject2).getNext();
          localObject2 = localReferenceEntry2;
          break;
          unlock();
          bool2 = isHeldByCurrentThread();
          j = 0;
        }
        while (bool2);
        postWriteCleanup();
        return false;
      }
      finally
      {
        unlock();
        if (!isHeldByCurrentThread())
          postWriteCleanup();
      }
      throw localObject1;
    }

    void recordExpirationTime(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry, long paramLong)
    {
      paramReferenceEntry.setExpirationTime(paramLong + this.map.ticker.read());
    }

    @GuardedBy("Segment.this")
    void recordLockedRead(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.evictionQueue.add(paramReferenceEntry);
      if (this.map.expiresAfterAccess())
      {
        recordExpirationTime(paramReferenceEntry, this.map.expireAfterAccessNanos);
        this.expirationQueue.add(paramReferenceEntry);
      }
    }

    void recordRead(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      if (this.map.expiresAfterAccess())
        recordExpirationTime(paramReferenceEntry, this.map.expireAfterAccessNanos);
      this.recencyQueue.add(paramReferenceEntry);
    }

    @GuardedBy("Segment.this")
    void recordWrite(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      drainRecencyQueue();
      this.evictionQueue.add(paramReferenceEntry);
      long l;
      if (this.map.expires())
      {
        if (!this.map.expiresAfterAccess())
          break label61;
        l = this.map.expireAfterAccessNanos;
      }
      while (true)
      {
        recordExpirationTime(paramReferenceEntry, l);
        this.expirationQueue.add(paramReferenceEntry);
        return;
        label61: l = this.map.expireAfterWriteNanos;
      }
    }

    V remove(Object paramObject, int paramInt)
    {
      lock();
      try
      {
        preWriteCleanup();
        (-1 + this.count);
        AtomicReferenceArray localAtomicReferenceArray = this.table;
        int i = paramInt & -1 + localAtomicReferenceArray.length();
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry1 = (CustomConcurrentHashMap.ReferenceEntry)localAtomicReferenceArray.get(i);
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry2;
        for (Object localObject2 = localReferenceEntry1; localObject2 != null; localObject2 = localReferenceEntry2)
        {
          Object localObject3 = ((CustomConcurrentHashMap.ReferenceEntry)localObject2).getKey();
          if ((((CustomConcurrentHashMap.ReferenceEntry)localObject2).getHash() == paramInt) && (localObject3 != null) && (this.map.keyEquivalence.equivalent(paramObject, localObject3)))
          {
            CustomConcurrentHashMap.ValueReference localValueReference = ((CustomConcurrentHashMap.ReferenceEntry)localObject2).getValueReference();
            Object localObject4 = localValueReference.get();
            if (localObject4 != null);
            for (MapMaker.RemovalCause localRemovalCause = MapMaker.RemovalCause.EXPLICIT; ; localRemovalCause = MapMaker.RemovalCause.COLLECTED)
            {
              this.modCount = (1 + this.modCount);
              enqueueNotification(localObject3, paramInt, localObject4, localRemovalCause);
              CustomConcurrentHashMap.ReferenceEntry localReferenceEntry3 = removeFromChain(localReferenceEntry1, (CustomConcurrentHashMap.ReferenceEntry)localObject2);
              int j = -1 + this.count;
              localAtomicReferenceArray.set(i, localReferenceEntry3);
              this.count = j;
              return localObject4;
              if (!isCollected(localValueReference))
                break;
            }
            return null;
          }
          localReferenceEntry2 = ((CustomConcurrentHashMap.ReferenceEntry)localObject2).getNext();
        }
        return null;
      }
      finally
      {
        unlock();
        postWriteCleanup();
      }
      throw localObject1;
    }

    boolean remove(Object paramObject1, int paramInt, Object paramObject2)
    {
      lock();
      try
      {
        preWriteCleanup();
        (-1 + this.count);
        AtomicReferenceArray localAtomicReferenceArray = this.table;
        int i = paramInt & -1 + localAtomicReferenceArray.length();
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry1 = (CustomConcurrentHashMap.ReferenceEntry)localAtomicReferenceArray.get(i);
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry2;
        for (Object localObject2 = localReferenceEntry1; localObject2 != null; localObject2 = localReferenceEntry2)
        {
          Object localObject3 = ((CustomConcurrentHashMap.ReferenceEntry)localObject2).getKey();
          if ((((CustomConcurrentHashMap.ReferenceEntry)localObject2).getHash() == paramInt) && (localObject3 != null) && (this.map.keyEquivalence.equivalent(paramObject1, localObject3)))
          {
            CustomConcurrentHashMap.ValueReference localValueReference = ((CustomConcurrentHashMap.ReferenceEntry)localObject2).getValueReference();
            Object localObject4 = localValueReference.get();
            if (this.map.valueEquivalence.equivalent(paramObject2, localObject4));
            for (MapMaker.RemovalCause localRemovalCause1 = MapMaker.RemovalCause.EXPLICIT; ; localRemovalCause1 = MapMaker.RemovalCause.COLLECTED)
            {
              this.modCount = (1 + this.modCount);
              enqueueNotification(localObject3, paramInt, localObject4, localRemovalCause1);
              CustomConcurrentHashMap.ReferenceEntry localReferenceEntry3 = removeFromChain(localReferenceEntry1, (CustomConcurrentHashMap.ReferenceEntry)localObject2);
              int j = -1 + this.count;
              localAtomicReferenceArray.set(i, localReferenceEntry3);
              this.count = j;
              MapMaker.RemovalCause localRemovalCause2 = MapMaker.RemovalCause.EXPLICIT;
              int k = 0;
              if (localRemovalCause1 == localRemovalCause2)
                k = 1;
              return k;
              if (!isCollected(localValueReference))
                break;
            }
            return false;
          }
          localReferenceEntry2 = ((CustomConcurrentHashMap.ReferenceEntry)localObject2).getNext();
        }
        return false;
      }
      finally
      {
        unlock();
        postWriteCleanup();
      }
      throw localObject1;
    }

    void removeCollectedEntry(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      enqueueNotification(paramReferenceEntry, MapMaker.RemovalCause.COLLECTED);
      this.evictionQueue.remove(paramReferenceEntry);
      this.expirationQueue.remove(paramReferenceEntry);
    }

    @GuardedBy("Segment.this")
    boolean removeEntry(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry, int paramInt, MapMaker.RemovalCause paramRemovalCause)
    {
      (-1 + this.count);
      AtomicReferenceArray localAtomicReferenceArray = this.table;
      int i = paramInt & -1 + localAtomicReferenceArray.length();
      CustomConcurrentHashMap.ReferenceEntry localReferenceEntry1 = (CustomConcurrentHashMap.ReferenceEntry)localAtomicReferenceArray.get(i);
      for (CustomConcurrentHashMap.ReferenceEntry localReferenceEntry2 = localReferenceEntry1; localReferenceEntry2 != null; localReferenceEntry2 = localReferenceEntry2.getNext())
      {
        if (localReferenceEntry2 != paramReferenceEntry)
          continue;
        this.modCount = (1 + this.modCount);
        enqueueNotification(localReferenceEntry2.getKey(), paramInt, localReferenceEntry2.getValueReference().get(), paramRemovalCause);
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry3 = removeFromChain(localReferenceEntry1, localReferenceEntry2);
        int j = -1 + this.count;
        localAtomicReferenceArray.set(i, localReferenceEntry3);
        this.count = j;
        return true;
      }
      return false;
    }

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> removeFromChain(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry1, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry2)
    {
      this.evictionQueue.remove(paramReferenceEntry2);
      this.expirationQueue.remove(paramReferenceEntry2);
      int i = this.count;
      CustomConcurrentHashMap.ReferenceEntry localReferenceEntry = paramReferenceEntry2.getNext();
      Object localObject = paramReferenceEntry1;
      if (localObject != paramReferenceEntry2)
      {
        if (isCollected((CustomConcurrentHashMap.ReferenceEntry)localObject))
        {
          removeCollectedEntry((CustomConcurrentHashMap.ReferenceEntry)localObject);
          i--;
        }
        while (true)
        {
          localObject = ((CustomConcurrentHashMap.ReferenceEntry)localObject).getNext();
          break;
          localReferenceEntry = copyEntry((CustomConcurrentHashMap.ReferenceEntry)localObject, localReferenceEntry);
        }
      }
      this.count = i;
      return (CustomConcurrentHashMap.ReferenceEntry<K, V>)localReferenceEntry;
    }

    V replace(K paramK, int paramInt, V paramV)
    {
      lock();
      try
      {
        preWriteCleanup();
        AtomicReferenceArray localAtomicReferenceArray = this.table;
        int i = paramInt & -1 + localAtomicReferenceArray.length();
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry1 = (CustomConcurrentHashMap.ReferenceEntry)localAtomicReferenceArray.get(i);
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry2;
        for (Object localObject2 = localReferenceEntry1; localObject2 != null; localObject2 = localReferenceEntry2)
        {
          Object localObject3 = ((CustomConcurrentHashMap.ReferenceEntry)localObject2).getKey();
          if ((((CustomConcurrentHashMap.ReferenceEntry)localObject2).getHash() == paramInt) && (localObject3 != null) && (this.map.keyEquivalence.equivalent(paramK, localObject3)))
          {
            CustomConcurrentHashMap.ValueReference localValueReference = ((CustomConcurrentHashMap.ReferenceEntry)localObject2).getValueReference();
            Object localObject4 = localValueReference.get();
            if (localObject4 == null)
            {
              if (isCollected(localValueReference))
              {
                (-1 + this.count);
                this.modCount = (1 + this.modCount);
                enqueueNotification(localObject3, paramInt, localObject4, MapMaker.RemovalCause.COLLECTED);
                CustomConcurrentHashMap.ReferenceEntry localReferenceEntry3 = removeFromChain(localReferenceEntry1, (CustomConcurrentHashMap.ReferenceEntry)localObject2);
                int j = -1 + this.count;
                localAtomicReferenceArray.set(i, localReferenceEntry3);
                this.count = j;
              }
              return null;
            }
            this.modCount = (1 + this.modCount);
            enqueueNotification(paramK, paramInt, localObject4, MapMaker.RemovalCause.REPLACED);
            setValue((CustomConcurrentHashMap.ReferenceEntry)localObject2, paramV);
            return localObject4;
          }
          localReferenceEntry2 = ((CustomConcurrentHashMap.ReferenceEntry)localObject2).getNext();
        }
        return null;
      }
      finally
      {
        unlock();
        postWriteCleanup();
      }
      throw localObject1;
    }

    boolean replace(K paramK, int paramInt, V paramV1, V paramV2)
    {
      lock();
      try
      {
        preWriteCleanup();
        AtomicReferenceArray localAtomicReferenceArray = this.table;
        int i = paramInt & -1 + localAtomicReferenceArray.length();
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry1 = (CustomConcurrentHashMap.ReferenceEntry)localAtomicReferenceArray.get(i);
        CustomConcurrentHashMap.ReferenceEntry localReferenceEntry2;
        for (Object localObject2 = localReferenceEntry1; localObject2 != null; localObject2 = localReferenceEntry2)
        {
          Object localObject3 = ((CustomConcurrentHashMap.ReferenceEntry)localObject2).getKey();
          if ((((CustomConcurrentHashMap.ReferenceEntry)localObject2).getHash() == paramInt) && (localObject3 != null) && (this.map.keyEquivalence.equivalent(paramK, localObject3)))
          {
            CustomConcurrentHashMap.ValueReference localValueReference = ((CustomConcurrentHashMap.ReferenceEntry)localObject2).getValueReference();
            Object localObject4 = localValueReference.get();
            if (localObject4 == null)
            {
              if (isCollected(localValueReference))
              {
                (-1 + this.count);
                this.modCount = (1 + this.modCount);
                enqueueNotification(localObject3, paramInt, localObject4, MapMaker.RemovalCause.COLLECTED);
                CustomConcurrentHashMap.ReferenceEntry localReferenceEntry3 = removeFromChain(localReferenceEntry1, (CustomConcurrentHashMap.ReferenceEntry)localObject2);
                int j = -1 + this.count;
                localAtomicReferenceArray.set(i, localReferenceEntry3);
                this.count = j;
              }
              return false;
            }
            if (this.map.valueEquivalence.equivalent(paramV1, localObject4))
            {
              this.modCount = (1 + this.modCount);
              enqueueNotification(paramK, paramInt, localObject4, MapMaker.RemovalCause.REPLACED);
              setValue((CustomConcurrentHashMap.ReferenceEntry)localObject2, paramV2);
              return true;
            }
            recordLockedRead((CustomConcurrentHashMap.ReferenceEntry)localObject2);
            return false;
          }
          localReferenceEntry2 = ((CustomConcurrentHashMap.ReferenceEntry)localObject2).getNext();
        }
        return false;
      }
      finally
      {
        unlock();
        postWriteCleanup();
      }
      throw localObject1;
    }

    void runCleanup()
    {
      runLockedCleanup();
      runUnlockedCleanup();
    }

    void runLockedCleanup()
    {
      if (tryLock());
      try
      {
        drainReferenceQueues();
        expireEntries();
        this.readCount.set(0);
        return;
      }
      finally
      {
        unlock();
      }
      throw localObject;
    }

    void runUnlockedCleanup()
    {
      if (!isHeldByCurrentThread())
        this.map.processPendingNotifications();
    }

    @GuardedBy("Segment.this")
    void setValue(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry, V paramV)
    {
      paramReferenceEntry.setValueReference(this.map.valueStrength.referenceValue(this, paramReferenceEntry, paramV));
      recordWrite(paramReferenceEntry);
    }

    void tryDrainReferenceQueues()
    {
      if (tryLock());
      try
      {
        drainReferenceQueues();
        return;
      }
      finally
      {
        unlock();
      }
      throw localObject;
    }

    void tryExpireEntries()
    {
      if (tryLock());
      try
      {
        expireEntries();
        return;
      }
      finally
      {
        unlock();
      }
      throw localObject;
    }
  }

  private static final class SerializationProxy<K, V> extends CustomConcurrentHashMap.AbstractSerializationProxy<K, V>
  {
    private static final long serialVersionUID = 3L;

    SerializationProxy(CustomConcurrentHashMap.Strength paramStrength1, CustomConcurrentHashMap.Strength paramStrength2, Equivalence<Object> paramEquivalence1, Equivalence<Object> paramEquivalence2, long paramLong1, long paramLong2, int paramInt1, int paramInt2, MapMaker.RemovalListener<? super K, ? super V> paramRemovalListener, ConcurrentMap<K, V> paramConcurrentMap)
    {
      super(paramStrength2, paramEquivalence1, paramEquivalence2, paramLong1, paramLong2, paramInt1, paramInt2, paramRemovalListener, paramConcurrentMap);
    }

    private void readObject(ObjectInputStream paramObjectInputStream)
      throws IOException, ClassNotFoundException
    {
      paramObjectInputStream.defaultReadObject();
      this.delegate = readMapMaker(paramObjectInputStream).makeMap();
      readEntries(paramObjectInputStream);
    }

    private Object readResolve()
    {
      return this.delegate;
    }

    private void writeObject(ObjectOutputStream paramObjectOutputStream)
      throws IOException
    {
      paramObjectOutputStream.defaultWriteObject();
      writeMapTo(paramObjectOutputStream);
    }
  }

  static class SoftEntry<K, V> extends SoftReference<K>
    implements CustomConcurrentHashMap.ReferenceEntry<K, V>
  {
    final int hash;
    final CustomConcurrentHashMap.ReferenceEntry<K, V> next;
    volatile CustomConcurrentHashMap.ValueReference<K, V> valueReference = CustomConcurrentHashMap.unset();

    SoftEntry(ReferenceQueue<K> paramReferenceQueue, K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      super(paramReferenceQueue);
      this.hash = paramInt;
      this.next = paramReferenceEntry;
    }

    public long getExpirationTime()
    {
      throw new UnsupportedOperationException();
    }

    public int getHash()
    {
      return this.hash;
    }

    public K getKey()
    {
      return get();
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNext()
    {
      return this.next;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable()
    {
      throw new UnsupportedOperationException();
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable()
    {
      throw new UnsupportedOperationException();
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable()
    {
      throw new UnsupportedOperationException();
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable()
    {
      throw new UnsupportedOperationException();
    }

    public CustomConcurrentHashMap.ValueReference<K, V> getValueReference()
    {
      return this.valueReference;
    }

    public void setExpirationTime(long paramLong)
    {
      throw new UnsupportedOperationException();
    }

    public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      throw new UnsupportedOperationException();
    }

    public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      throw new UnsupportedOperationException();
    }

    public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      throw new UnsupportedOperationException();
    }

    public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      throw new UnsupportedOperationException();
    }

    public void setValueReference(CustomConcurrentHashMap.ValueReference<K, V> paramValueReference)
    {
      CustomConcurrentHashMap.ValueReference localValueReference = this.valueReference;
      this.valueReference = paramValueReference;
      localValueReference.clear(paramValueReference);
    }
  }

  static final class SoftEvictableEntry<K, V> extends CustomConcurrentHashMap.SoftEntry<K, V>
    implements CustomConcurrentHashMap.ReferenceEntry<K, V>
  {

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> nextEvictable = CustomConcurrentHashMap.nullEntry();

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> previousEvictable = CustomConcurrentHashMap.nullEntry();

    SoftEvictableEntry(ReferenceQueue<K> paramReferenceQueue, K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      super(paramK, paramInt, paramReferenceEntry);
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable()
    {
      return this.nextEvictable;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable()
    {
      return this.previousEvictable;
    }

    public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.nextEvictable = paramReferenceEntry;
    }

    public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.previousEvictable = paramReferenceEntry;
    }
  }

  static final class SoftExpirableEntry<K, V> extends CustomConcurrentHashMap.SoftEntry<K, V>
    implements CustomConcurrentHashMap.ReferenceEntry<K, V>
  {

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> nextExpirable = CustomConcurrentHashMap.nullEntry();

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> previousExpirable = CustomConcurrentHashMap.nullEntry();
    volatile long time = 9223372036854775807L;

    SoftExpirableEntry(ReferenceQueue<K> paramReferenceQueue, K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      super(paramK, paramInt, paramReferenceEntry);
    }

    public long getExpirationTime()
    {
      return this.time;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable()
    {
      return this.nextExpirable;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable()
    {
      return this.previousExpirable;
    }

    public void setExpirationTime(long paramLong)
    {
      this.time = paramLong;
    }

    public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.nextExpirable = paramReferenceEntry;
    }

    public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.previousExpirable = paramReferenceEntry;
    }
  }

  static final class SoftExpirableEvictableEntry<K, V> extends CustomConcurrentHashMap.SoftEntry<K, V>
    implements CustomConcurrentHashMap.ReferenceEntry<K, V>
  {

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> nextEvictable = CustomConcurrentHashMap.nullEntry();

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> nextExpirable = CustomConcurrentHashMap.nullEntry();

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> previousEvictable = CustomConcurrentHashMap.nullEntry();

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> previousExpirable = CustomConcurrentHashMap.nullEntry();
    volatile long time = 9223372036854775807L;

    SoftExpirableEvictableEntry(ReferenceQueue<K> paramReferenceQueue, K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      super(paramK, paramInt, paramReferenceEntry);
    }

    public long getExpirationTime()
    {
      return this.time;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable()
    {
      return this.nextEvictable;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable()
    {
      return this.nextExpirable;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable()
    {
      return this.previousEvictable;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable()
    {
      return this.previousExpirable;
    }

    public void setExpirationTime(long paramLong)
    {
      this.time = paramLong;
    }

    public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.nextEvictable = paramReferenceEntry;
    }

    public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.nextExpirable = paramReferenceEntry;
    }

    public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.previousEvictable = paramReferenceEntry;
    }

    public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.previousExpirable = paramReferenceEntry;
    }
  }

  static final class SoftValueReference<K, V> extends SoftReference<V>
    implements CustomConcurrentHashMap.ValueReference<K, V>
  {
    final CustomConcurrentHashMap.ReferenceEntry<K, V> entry;

    SoftValueReference(ReferenceQueue<V> paramReferenceQueue, V paramV, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      super(paramReferenceQueue);
      this.entry = paramReferenceEntry;
    }

    public void clear(CustomConcurrentHashMap.ValueReference<K, V> paramValueReference)
    {
      clear();
    }

    public CustomConcurrentHashMap.ValueReference<K, V> copyFor(ReferenceQueue<V> paramReferenceQueue, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      return new SoftValueReference(paramReferenceQueue, get(), paramReferenceEntry);
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getEntry()
    {
      return this.entry;
    }

    public boolean isComputingReference()
    {
      return false;
    }

    public V waitForValue()
    {
      return get();
    }
  }

  static abstract enum Strength
  {
    static
    {
      SOFT = new Strength("SOFT", 1)
      {
        Equivalence<Object> defaultEquivalence()
        {
          return Equivalences.identity();
        }

        <K, V> CustomConcurrentHashMap.ValueReference<K, V> referenceValue(CustomConcurrentHashMap.Segment<K, V> paramSegment, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry, V paramV)
        {
          return new CustomConcurrentHashMap.SoftValueReference(paramSegment.valueReferenceQueue, paramV, paramReferenceEntry);
        }
      };
      WEAK = new Strength("WEAK", 2)
      {
        Equivalence<Object> defaultEquivalence()
        {
          return Equivalences.identity();
        }

        <K, V> CustomConcurrentHashMap.ValueReference<K, V> referenceValue(CustomConcurrentHashMap.Segment<K, V> paramSegment, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry, V paramV)
        {
          return new CustomConcurrentHashMap.WeakValueReference(paramSegment.valueReferenceQueue, paramV, paramReferenceEntry);
        }
      };
      Strength[] arrayOfStrength = new Strength[3];
      arrayOfStrength[0] = STRONG;
      arrayOfStrength[1] = SOFT;
      arrayOfStrength[2] = WEAK;
      $VALUES = arrayOfStrength;
    }

    abstract Equivalence<Object> defaultEquivalence();

    abstract <K, V> CustomConcurrentHashMap.ValueReference<K, V> referenceValue(CustomConcurrentHashMap.Segment<K, V> paramSegment, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry, V paramV);
  }

  static class StrongEntry<K, V>
    implements CustomConcurrentHashMap.ReferenceEntry<K, V>
  {
    final int hash;
    final K key;
    final CustomConcurrentHashMap.ReferenceEntry<K, V> next;
    volatile CustomConcurrentHashMap.ValueReference<K, V> valueReference = CustomConcurrentHashMap.unset();

    StrongEntry(K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.key = paramK;
      this.hash = paramInt;
      this.next = paramReferenceEntry;
    }

    public long getExpirationTime()
    {
      throw new UnsupportedOperationException();
    }

    public int getHash()
    {
      return this.hash;
    }

    public K getKey()
    {
      return this.key;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNext()
    {
      return this.next;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable()
    {
      throw new UnsupportedOperationException();
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable()
    {
      throw new UnsupportedOperationException();
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable()
    {
      throw new UnsupportedOperationException();
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable()
    {
      throw new UnsupportedOperationException();
    }

    public CustomConcurrentHashMap.ValueReference<K, V> getValueReference()
    {
      return this.valueReference;
    }

    public void setExpirationTime(long paramLong)
    {
      throw new UnsupportedOperationException();
    }

    public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      throw new UnsupportedOperationException();
    }

    public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      throw new UnsupportedOperationException();
    }

    public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      throw new UnsupportedOperationException();
    }

    public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      throw new UnsupportedOperationException();
    }

    public void setValueReference(CustomConcurrentHashMap.ValueReference<K, V> paramValueReference)
    {
      CustomConcurrentHashMap.ValueReference localValueReference = this.valueReference;
      this.valueReference = paramValueReference;
      localValueReference.clear(paramValueReference);
    }
  }

  static final class StrongEvictableEntry<K, V> extends CustomConcurrentHashMap.StrongEntry<K, V>
    implements CustomConcurrentHashMap.ReferenceEntry<K, V>
  {

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> nextEvictable = CustomConcurrentHashMap.nullEntry();

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> previousEvictable = CustomConcurrentHashMap.nullEntry();

    StrongEvictableEntry(K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      super(paramInt, paramReferenceEntry);
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable()
    {
      return this.nextEvictable;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable()
    {
      return this.previousEvictable;
    }

    public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.nextEvictable = paramReferenceEntry;
    }

    public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.previousEvictable = paramReferenceEntry;
    }
  }

  static final class StrongExpirableEntry<K, V> extends CustomConcurrentHashMap.StrongEntry<K, V>
    implements CustomConcurrentHashMap.ReferenceEntry<K, V>
  {

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> nextExpirable = CustomConcurrentHashMap.nullEntry();

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> previousExpirable = CustomConcurrentHashMap.nullEntry();
    volatile long time = 9223372036854775807L;

    StrongExpirableEntry(K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      super(paramInt, paramReferenceEntry);
    }

    public long getExpirationTime()
    {
      return this.time;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable()
    {
      return this.nextExpirable;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable()
    {
      return this.previousExpirable;
    }

    public void setExpirationTime(long paramLong)
    {
      this.time = paramLong;
    }

    public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.nextExpirable = paramReferenceEntry;
    }

    public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.previousExpirable = paramReferenceEntry;
    }
  }

  static final class StrongExpirableEvictableEntry<K, V> extends CustomConcurrentHashMap.StrongEntry<K, V>
    implements CustomConcurrentHashMap.ReferenceEntry<K, V>
  {

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> nextEvictable = CustomConcurrentHashMap.nullEntry();

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> nextExpirable = CustomConcurrentHashMap.nullEntry();

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> previousEvictable = CustomConcurrentHashMap.nullEntry();

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> previousExpirable = CustomConcurrentHashMap.nullEntry();
    volatile long time = 9223372036854775807L;

    StrongExpirableEvictableEntry(K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      super(paramInt, paramReferenceEntry);
    }

    public long getExpirationTime()
    {
      return this.time;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable()
    {
      return this.nextEvictable;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable()
    {
      return this.nextExpirable;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable()
    {
      return this.previousEvictable;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable()
    {
      return this.previousExpirable;
    }

    public void setExpirationTime(long paramLong)
    {
      this.time = paramLong;
    }

    public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.nextEvictable = paramReferenceEntry;
    }

    public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.nextExpirable = paramReferenceEntry;
    }

    public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.previousEvictable = paramReferenceEntry;
    }

    public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.previousExpirable = paramReferenceEntry;
    }
  }

  static final class StrongValueReference<K, V>
    implements CustomConcurrentHashMap.ValueReference<K, V>
  {
    final V referent;

    StrongValueReference(V paramV)
    {
      this.referent = paramV;
    }

    public void clear(CustomConcurrentHashMap.ValueReference<K, V> paramValueReference)
    {
    }

    public CustomConcurrentHashMap.ValueReference<K, V> copyFor(ReferenceQueue<V> paramReferenceQueue, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      return this;
    }

    public V get()
    {
      return this.referent;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getEntry()
    {
      return null;
    }

    public boolean isComputingReference()
    {
      return false;
    }

    public V waitForValue()
    {
      return get();
    }
  }

  final class ValueIterator extends CustomConcurrentHashMap<K, V>.HashIterator
    implements Iterator<V>
  {
    ValueIterator()
    {
      super();
    }

    public V next()
    {
      return nextEntry().getValue();
    }
  }

  static abstract interface ValueReference<K, V>
  {
    public abstract void clear(@Nullable ValueReference<K, V> paramValueReference);

    public abstract ValueReference<K, V> copyFor(ReferenceQueue<V> paramReferenceQueue, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry);

    public abstract V get();

    public abstract CustomConcurrentHashMap.ReferenceEntry<K, V> getEntry();

    public abstract boolean isComputingReference();

    public abstract V waitForValue()
      throws ExecutionException;
  }

  final class Values extends AbstractCollection<V>
  {
    Values()
    {
    }

    public void clear()
    {
      CustomConcurrentHashMap.this.clear();
    }

    public boolean contains(Object paramObject)
    {
      return CustomConcurrentHashMap.this.containsValue(paramObject);
    }

    public boolean isEmpty()
    {
      return CustomConcurrentHashMap.this.isEmpty();
    }

    public Iterator<V> iterator()
    {
      return new CustomConcurrentHashMap.ValueIterator(CustomConcurrentHashMap.this);
    }

    public int size()
    {
      return CustomConcurrentHashMap.this.size();
    }
  }

  static class WeakEntry<K, V> extends WeakReference<K>
    implements CustomConcurrentHashMap.ReferenceEntry<K, V>
  {
    final int hash;
    final CustomConcurrentHashMap.ReferenceEntry<K, V> next;
    volatile CustomConcurrentHashMap.ValueReference<K, V> valueReference = CustomConcurrentHashMap.unset();

    WeakEntry(ReferenceQueue<K> paramReferenceQueue, K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      super(paramReferenceQueue);
      this.hash = paramInt;
      this.next = paramReferenceEntry;
    }

    public long getExpirationTime()
    {
      throw new UnsupportedOperationException();
    }

    public int getHash()
    {
      return this.hash;
    }

    public K getKey()
    {
      return get();
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNext()
    {
      return this.next;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable()
    {
      throw new UnsupportedOperationException();
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable()
    {
      throw new UnsupportedOperationException();
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable()
    {
      throw new UnsupportedOperationException();
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable()
    {
      throw new UnsupportedOperationException();
    }

    public CustomConcurrentHashMap.ValueReference<K, V> getValueReference()
    {
      return this.valueReference;
    }

    public void setExpirationTime(long paramLong)
    {
      throw new UnsupportedOperationException();
    }

    public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      throw new UnsupportedOperationException();
    }

    public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      throw new UnsupportedOperationException();
    }

    public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      throw new UnsupportedOperationException();
    }

    public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      throw new UnsupportedOperationException();
    }

    public void setValueReference(CustomConcurrentHashMap.ValueReference<K, V> paramValueReference)
    {
      CustomConcurrentHashMap.ValueReference localValueReference = this.valueReference;
      this.valueReference = paramValueReference;
      localValueReference.clear(paramValueReference);
    }
  }

  static final class WeakEvictableEntry<K, V> extends CustomConcurrentHashMap.WeakEntry<K, V>
    implements CustomConcurrentHashMap.ReferenceEntry<K, V>
  {

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> nextEvictable = CustomConcurrentHashMap.nullEntry();

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> previousEvictable = CustomConcurrentHashMap.nullEntry();

    WeakEvictableEntry(ReferenceQueue<K> paramReferenceQueue, K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      super(paramK, paramInt, paramReferenceEntry);
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable()
    {
      return this.nextEvictable;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable()
    {
      return this.previousEvictable;
    }

    public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.nextEvictable = paramReferenceEntry;
    }

    public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.previousEvictable = paramReferenceEntry;
    }
  }

  static final class WeakExpirableEntry<K, V> extends CustomConcurrentHashMap.WeakEntry<K, V>
    implements CustomConcurrentHashMap.ReferenceEntry<K, V>
  {

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> nextExpirable = CustomConcurrentHashMap.nullEntry();

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> previousExpirable = CustomConcurrentHashMap.nullEntry();
    volatile long time = 9223372036854775807L;

    WeakExpirableEntry(ReferenceQueue<K> paramReferenceQueue, K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      super(paramK, paramInt, paramReferenceEntry);
    }

    public long getExpirationTime()
    {
      return this.time;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable()
    {
      return this.nextExpirable;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable()
    {
      return this.previousExpirable;
    }

    public void setExpirationTime(long paramLong)
    {
      this.time = paramLong;
    }

    public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.nextExpirable = paramReferenceEntry;
    }

    public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.previousExpirable = paramReferenceEntry;
    }
  }

  static final class WeakExpirableEvictableEntry<K, V> extends CustomConcurrentHashMap.WeakEntry<K, V>
    implements CustomConcurrentHashMap.ReferenceEntry<K, V>
  {

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> nextEvictable = CustomConcurrentHashMap.nullEntry();

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> nextExpirable = CustomConcurrentHashMap.nullEntry();

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> previousEvictable = CustomConcurrentHashMap.nullEntry();

    @GuardedBy("Segment.this")
    CustomConcurrentHashMap.ReferenceEntry<K, V> previousExpirable = CustomConcurrentHashMap.nullEntry();
    volatile long time = 9223372036854775807L;

    WeakExpirableEvictableEntry(ReferenceQueue<K> paramReferenceQueue, K paramK, int paramInt, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      super(paramK, paramInt, paramReferenceEntry);
    }

    public long getExpirationTime()
    {
      return this.time;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable()
    {
      return this.nextEvictable;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable()
    {
      return this.nextExpirable;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable()
    {
      return this.previousEvictable;
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable()
    {
      return this.previousExpirable;
    }

    public void setExpirationTime(long paramLong)
    {
      this.time = paramLong;
    }

    public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.nextEvictable = paramReferenceEntry;
    }

    public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.nextExpirable = paramReferenceEntry;
    }

    public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.previousEvictable = paramReferenceEntry;
    }

    public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      this.previousExpirable = paramReferenceEntry;
    }
  }

  static final class WeakValueReference<K, V> extends WeakReference<V>
    implements CustomConcurrentHashMap.ValueReference<K, V>
  {
    final CustomConcurrentHashMap.ReferenceEntry<K, V> entry;

    WeakValueReference(ReferenceQueue<V> paramReferenceQueue, V paramV, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      super(paramReferenceQueue);
      this.entry = paramReferenceEntry;
    }

    public void clear(CustomConcurrentHashMap.ValueReference<K, V> paramValueReference)
    {
      clear();
    }

    public CustomConcurrentHashMap.ValueReference<K, V> copyFor(ReferenceQueue<V> paramReferenceQueue, CustomConcurrentHashMap.ReferenceEntry<K, V> paramReferenceEntry)
    {
      return new WeakValueReference(paramReferenceQueue, get(), paramReferenceEntry);
    }

    public CustomConcurrentHashMap.ReferenceEntry<K, V> getEntry()
    {
      return this.entry;
    }

    public boolean isComputingReference()
    {
      return false;
    }

    public V waitForValue()
    {
      return get();
    }
  }

  final class WriteThroughEntry extends AbstractMapEntry<K, V>
  {
    final K key;
    V value;

    WriteThroughEntry(V arg2)
    {
      Object localObject1;
      this.key = localObject1;
      Object localObject2;
      this.value = localObject2;
    }

    public boolean equals(@Nullable Object paramObject)
    {
      boolean bool1 = paramObject instanceof Map.Entry;
      int i = 0;
      if (bool1)
      {
        Map.Entry localEntry = (Map.Entry)paramObject;
        boolean bool2 = this.key.equals(localEntry.getKey());
        i = 0;
        if (bool2)
        {
          boolean bool3 = this.value.equals(localEntry.getValue());
          i = 0;
          if (bool3)
            i = 1;
        }
      }
      return i;
    }

    public K getKey()
    {
      return this.key;
    }

    public V getValue()
    {
      return this.value;
    }

    public int hashCode()
    {
      return this.key.hashCode() ^ this.value.hashCode();
    }

    public V setValue(V paramV)
    {
      Object localObject = CustomConcurrentHashMap.this.put(this.key, paramV);
      this.value = paramV;
      return localObject;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.CustomConcurrentHashMap
 * JD-Core Version:    0.6.0
 */