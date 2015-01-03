package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
 enum BstSide
{
  static
  {
    BstSide[] arrayOfBstSide = new BstSide[2];
    arrayOfBstSide[0] = LEFT;
    arrayOfBstSide[1] = RIGHT;
    $VALUES = arrayOfBstSide;
  }

  abstract BstSide other();
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.BstSide
 * JD-Core Version:    0.6.0
 */