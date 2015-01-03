package com.google.zxing.oned.rss.expanded.decoders;

abstract class DecodedObject
{
  protected final int newPosition;

  DecodedObject(int paramInt)
  {
    this.newPosition = paramInt;
  }

  int getNewPosition()
  {
    return this.newPosition;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.rss.expanded.decoders.DecodedObject
 * JD-Core Version:    0.6.0
 */