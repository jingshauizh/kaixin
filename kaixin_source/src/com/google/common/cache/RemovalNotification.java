package com.google.common.cache;

import com.google.common.annotations.Beta;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@Beta
public final class RemovalNotification<K, V>
  implements Map.Entry<K, V>
{
  private static final long serialVersionUID;
  private final RemovalCause cause;

  @Nullable
  private final K key;

  @Nullable
  private final V value;

  RemovalNotification(@Nullable K paramK, @Nullable V paramV, RemovalCause paramRemovalCause)
  {
    this.key = paramK;
    this.value = paramV;
    this.cause = ((RemovalCause)Preconditions.checkNotNull(paramRemovalCause));
  }

  public boolean equals(@Nullable Object paramObject)
  {
    boolean bool1 = paramObject instanceof Map.Entry;
    int i = 0;
    if (bool1)
    {
      Map.Entry localEntry = (Map.Entry)paramObject;
      boolean bool2 = Objects.equal(getKey(), localEntry.getKey());
      i = 0;
      if (bool2)
      {
        boolean bool3 = Objects.equal(getValue(), localEntry.getValue());
        i = 0;
        if (bool3)
          i = 1;
      }
    }
    return i;
  }

  public RemovalCause getCause()
  {
    return this.cause;
  }

  @Nullable
  public K getKey()
  {
    return this.key;
  }

  @Nullable
  public V getValue()
  {
    return this.value;
  }

  public int hashCode()
  {
    Object localObject1 = getKey();
    Object localObject2 = getValue();
    int i;
    int j;
    if (localObject1 == null)
    {
      i = 0;
      j = 0;
      if (localObject2 != null)
        break label36;
    }
    while (true)
    {
      return j ^ i;
      i = localObject1.hashCode();
      break;
      label36: j = localObject2.hashCode();
    }
  }

  public final V setValue(V paramV)
  {
    throw new UnsupportedOperationException();
  }

  public String toString()
  {
    return getKey() + "=" + getValue();
  }

  public boolean wasEvicted()
  {
    return this.cause.wasEvicted();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.cache.RemovalNotification
 * JD-Core Version:    0.6.0
 */