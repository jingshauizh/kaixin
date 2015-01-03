package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Deprecated
@Beta
public abstract interface UninterruptibleFuture<V> extends Future<V>
{
  public abstract V get()
    throws ExecutionException;

  public abstract V get(long paramLong, TimeUnit paramTimeUnit)
    throws ExecutionException, TimeoutException;
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.util.concurrent.UninterruptibleFuture
 * JD-Core Version:    0.6.0
 */