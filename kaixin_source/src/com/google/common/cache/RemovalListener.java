package com.google.common.cache;

import com.google.common.annotations.Beta;

@Beta
public abstract interface RemovalListener<K, V>
{
  public abstract void onRemoval(RemovalNotification<K, V> paramRemovalNotification);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.cache.RemovalListener
 * JD-Core Version:    0.6.0
 */