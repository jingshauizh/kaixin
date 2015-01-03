package com.google.common.collect;

import com.google.common.annotations.Beta;
import javax.annotation.Nullable;

@Deprecated
@Beta
public abstract interface MapEvictionListener<K, V>
{
  public abstract void onEviction(@Nullable K paramK, @Nullable V paramV);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.MapEvictionListener
 * JD-Core Version:    0.6.0
 */