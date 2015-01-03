package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

public final class WhiteRectangleDetector
{
  private static final int CORR = 1;
  private static final int INIT_SIZE = 30;
  private final int downInit;
  private final int height;
  private final BitMatrix image;
  private final int leftInit;
  private final int rightInit;
  private final int upInit;
  private final int width;

  public WhiteRectangleDetector(BitMatrix paramBitMatrix)
    throws NotFoundException
  {
    this.image = paramBitMatrix;
    this.height = paramBitMatrix.getHeight();
    this.width = paramBitMatrix.getWidth();
    this.leftInit = (-30 + this.width >> 1);
    this.rightInit = (30 + this.width >> 1);
    this.upInit = (-30 + this.height >> 1);
    this.downInit = (30 + this.height >> 1);
    if ((this.upInit < 0) || (this.leftInit < 0) || (this.downInit >= this.height) || (this.rightInit >= this.width))
      throw NotFoundException.getNotFoundInstance();
  }

  public WhiteRectangleDetector(BitMatrix paramBitMatrix, int paramInt1, int paramInt2, int paramInt3)
    throws NotFoundException
  {
    this.image = paramBitMatrix;
    this.height = paramBitMatrix.getHeight();
    this.width = paramBitMatrix.getWidth();
    int i = paramInt1 >> 1;
    this.leftInit = (paramInt2 - i);
    this.rightInit = (paramInt2 + i);
    this.upInit = (paramInt3 - i);
    this.downInit = (paramInt3 + i);
    if ((this.upInit < 0) || (this.leftInit < 0) || (this.downInit >= this.height) || (this.rightInit >= this.width))
      throw NotFoundException.getNotFoundInstance();
  }

  private ResultPoint[] centerEdges(ResultPoint paramResultPoint1, ResultPoint paramResultPoint2, ResultPoint paramResultPoint3, ResultPoint paramResultPoint4)
  {
    float f1 = paramResultPoint1.getX();
    float f2 = paramResultPoint1.getY();
    float f3 = paramResultPoint2.getX();
    float f4 = paramResultPoint2.getY();
    float f5 = paramResultPoint3.getX();
    float f6 = paramResultPoint3.getY();
    float f7 = paramResultPoint4.getX();
    float f8 = paramResultPoint4.getY();
    if (f1 < this.width / 2)
    {
      ResultPoint[] arrayOfResultPoint2 = new ResultPoint[4];
      arrayOfResultPoint2[0] = new ResultPoint(f7 - 1.0F, 1.0F + f8);
      arrayOfResultPoint2[1] = new ResultPoint(1.0F + f3, 1.0F + f4);
      arrayOfResultPoint2[2] = new ResultPoint(f5 - 1.0F, f6 - 1.0F);
      arrayOfResultPoint2[3] = new ResultPoint(1.0F + f1, f2 - 1.0F);
      return arrayOfResultPoint2;
    }
    ResultPoint[] arrayOfResultPoint1 = new ResultPoint[4];
    arrayOfResultPoint1[0] = new ResultPoint(1.0F + f7, 1.0F + f8);
    arrayOfResultPoint1[1] = new ResultPoint(1.0F + f3, f4 - 1.0F);
    arrayOfResultPoint1[2] = new ResultPoint(f5 - 1.0F, 1.0F + f6);
    arrayOfResultPoint1[3] = new ResultPoint(f1 - 1.0F, f2 - 1.0F);
    return arrayOfResultPoint1;
  }

  private boolean containsBlackPoint(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    int i = 1;
    int k;
    if (paramBoolean)
    {
      k = paramInt1;
      if (k <= paramInt2);
    }
    label68: 
    while (true)
    {
      i = 0;
      do
        return i;
      while (this.image.get(k, paramInt3));
      k++;
      break;
      for (int j = paramInt1; ; j++)
      {
        if (j > paramInt2)
          break label68;
        if (this.image.get(paramInt3, j))
          break;
      }
    }
  }

  private static int distanceL2(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f1 = paramFloat1 - paramFloat3;
    float f2 = paramFloat2 - paramFloat4;
    return round((float)Math.sqrt(f1 * f1 + f2 * f2));
  }

  private ResultPoint getBlackPointOnSegment(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    int i = distanceL2(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    float f1 = (paramFloat3 - paramFloat1) / i;
    float f2 = (paramFloat4 - paramFloat2) / i;
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return null;
      int k = round(paramFloat1 + f1 * j);
      int m = round(paramFloat2 + f2 * j);
      if (this.image.get(k, m))
        return new ResultPoint(k, m);
    }
  }

  private static int round(float paramFloat)
  {
    return (int)(0.5F + paramFloat);
  }

  public ResultPoint[] detect()
    throws NotFoundException
  {
    int i = this.leftInit;
    int j = this.rightInit;
    int k = this.upInit;
    int m = this.downInit;
    int n = 1;
    int i1 = 0;
    int i2 = 0;
    int i3;
    ResultPoint localResultPoint1;
    if (n == 0)
    {
      if ((i2 == 0) && (i1 != 0))
      {
        i3 = j - i;
        localResultPoint1 = null;
      }
    }
    else
    {
      label130: label280: for (int i4 = 1; ; i4++)
      {
        if (i4 >= i3);
        label187: label234: 
        do
        {
          if (localResultPoint1 != null)
            break label324;
          throw NotFoundException.getNotFoundInstance();
          n = 0;
          boolean bool1 = true;
          while (true)
          {
            if ((!bool1) || (j >= this.width))
            {
              if (j < this.width)
                break label130;
              i2 = 1;
              break;
            }
            bool1 = containsBlackPoint(k, m, j, false);
            if (!bool1)
              continue;
            j++;
            n = 1;
          }
          boolean bool2 = true;
          while (true)
          {
            if ((!bool2) || (m >= this.height))
            {
              if (m < this.height)
                break label187;
              i2 = 1;
              break;
            }
            bool2 = containsBlackPoint(i, j, m, true);
            if (!bool2)
              continue;
            m++;
            n = 1;
          }
          boolean bool3 = true;
          while (true)
          {
            if ((!bool3) || (i < 0))
            {
              if (i >= 0)
                break label234;
              i2 = 1;
              break;
            }
            bool3 = containsBlackPoint(k, m, i, false);
            if (!bool3)
              continue;
            i--;
            n = 1;
          }
          boolean bool4 = true;
          while (true)
          {
            if ((!bool4) || (k < 0))
            {
              if (k >= 0)
                break label280;
              i2 = 1;
              break;
            }
            bool4 = containsBlackPoint(i, j, k, true);
            if (!bool4)
              continue;
            k--;
            n = 1;
          }
          if (n == 0)
            break;
          i1 = 1;
          break;
          localResultPoint1 = getBlackPointOnSegment(i, m - i4, i + i4, m);
        }
        while (localResultPoint1 != null);
      }
      label324: ResultPoint localResultPoint2 = null;
      for (int i5 = 1; ; i5++)
      {
        if (i5 >= i3);
        do
        {
          if (localResultPoint2 != null)
            break;
          throw NotFoundException.getNotFoundInstance();
          localResultPoint2 = getBlackPointOnSegment(i, k + i5, i + i5, k);
        }
        while (localResultPoint2 != null);
      }
      ResultPoint localResultPoint3 = null;
      for (int i6 = 1; ; i6++)
      {
        if (i6 >= i3);
        do
        {
          if (localResultPoint3 != null)
            break;
          throw NotFoundException.getNotFoundInstance();
          localResultPoint3 = getBlackPointOnSegment(j, k + i6, j - i6, k);
        }
        while (localResultPoint3 != null);
      }
      ResultPoint localResultPoint4 = null;
      for (int i7 = 1; ; i7++)
      {
        if (i7 >= i3);
        do
        {
          if (localResultPoint4 != null)
            break;
          throw NotFoundException.getNotFoundInstance();
          localResultPoint4 = getBlackPointOnSegment(j, m - i7, j - i7, m);
        }
        while (localResultPoint4 != null);
      }
      return centerEdges(localResultPoint4, localResultPoint1, localResultPoint3, localResultPoint2);
    }
    throw NotFoundException.getNotFoundInstance();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.common.detector.WhiteRectangleDetector
 * JD-Core Version:    0.6.0
 */