package com.google.common.cache;

import com.google.common.annotations.Beta;

@Beta
public enum RemovalCause
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

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.cache.RemovalCause
 * JD-Core Version:    0.6.0
 */