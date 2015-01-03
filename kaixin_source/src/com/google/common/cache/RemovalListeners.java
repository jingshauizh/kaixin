package com.google.common.cache;

import com.google.common.annotations.Beta;
import java.util.concurrent.Executor;

@Beta
public final class RemovalListeners
{
  public static <K, V> RemovalListener<K, V> asynchronous(RemovalListener<K, V> paramRemovalListener, Executor paramExecutor)
  {
    return new RemovalListener(paramExecutor, paramRemovalListener)
    {
      public void onRemoval(RemovalNotification<K, V> paramRemovalNotification)
      {
        this.val$executor.execute(new Runnable(paramRemovalNotification)
        {
          public void run()
          {
            RemovalListeners.1.this.val$listener.onRemoval(this.val$notification);
          }
        });
      }
    };
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.cache.RemovalListeners
 * JD-Core Version:    0.6.0
 */