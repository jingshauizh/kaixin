package com.google.zxing;

import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;

public abstract class Binarizer
{
  private final LuminanceSource source;

  protected Binarizer(LuminanceSource paramLuminanceSource)
  {
    if (paramLuminanceSource == null)
      throw new IllegalArgumentException("Source must be non-null.");
    this.source = paramLuminanceSource;
  }

  public abstract Binarizer createBinarizer(LuminanceSource paramLuminanceSource);

  public abstract BitMatrix getBlackMatrix()
    throws NotFoundException;

  public abstract BitArray getBlackRow(int paramInt, BitArray paramBitArray)
    throws NotFoundException;

  public LuminanceSource getLuminanceSource()
  {
    return this.source;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.Binarizer
 * JD-Core Version:    0.6.0
 */