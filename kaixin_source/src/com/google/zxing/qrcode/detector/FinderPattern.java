package com.google.zxing.qrcode.detector;

import com.google.zxing.ResultPoint;

public final class FinderPattern extends ResultPoint
{
  private int count;
  private final float estimatedModuleSize;

  FinderPattern(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    super(paramFloat1, paramFloat2);
    this.estimatedModuleSize = paramFloat3;
    this.count = 1;
  }

  boolean aboutEquals(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f;
    if ((Math.abs(paramFloat2 - getY()) <= paramFloat1) && (Math.abs(paramFloat3 - getX()) <= paramFloat1))
      f = Math.abs(paramFloat1 - this.estimatedModuleSize);
    return (f <= 1.0F) || (f / this.estimatedModuleSize <= 1.0F);
  }

  int getCount()
  {
    return this.count;
  }

  public float getEstimatedModuleSize()
  {
    return this.estimatedModuleSize;
  }

  void incrementCount()
  {
    this.count = (1 + this.count);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.qrcode.detector.FinderPattern
 * JD-Core Version:    0.6.0
 */