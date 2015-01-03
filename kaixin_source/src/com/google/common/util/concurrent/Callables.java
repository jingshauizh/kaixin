package com.google.common.util.concurrent;

import java.util.concurrent.Callable;
import javax.annotation.Nullable;

public final class Callables
{
  public static <T> Callable<T> returning(@Nullable T paramT)
  {
    return new Callable(paramT)
    {
      public T call()
      {
        return this.val$value;
      }
    };
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.util.concurrent.Callables
 * JD-Core Version:    0.6.0
 */