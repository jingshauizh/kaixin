package com.google.zxing.qrcode.detector;

import com.google.zxing.ResultPoint;

public final class AlignmentPattern extends ResultPoint
{
  private final float estimatedModuleSize;

  AlignmentPattern(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    super(paramFloat1, paramFloat2);
    this.estimatedModuleSize = paramFloat3;
  }

  boolean aboutEquals(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f;
    if ((Math.abs(paramFloat2 - getY()) <= paramFloat1) && (Math.abs(paramFloat3 - getX()) <= paramFloat1))
      f = Math.abs(paramFloat1 - this.estimatedModuleSize);
    return (f <= 1.0F) || (f / this.estimatedModuleSize <= 1.0F);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.qrcode.detector.AlignmentPattern
 * JD-Core Version:    0.6.0
 */