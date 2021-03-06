package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

@Beta
@GwtCompatible
public enum BoundType
{
  static
  {
    CLOSED = new BoundType("CLOSED", 1);
    BoundType[] arrayOfBoundType = new BoundType[2];
    arrayOfBoundType[0] = OPEN;
    arrayOfBoundType[1] = CLOSED;
    $VALUES = arrayOfBoundType;
  }

  static BoundType forBoolean(boolean paramBoolean)
  {
    if (paramBoolean)
      return CLOSED;
    return OPEN;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.BoundType
 * JD-Core Version:    0.6.0
 */