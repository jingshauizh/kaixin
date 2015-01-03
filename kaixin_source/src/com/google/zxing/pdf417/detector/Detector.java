package com.google.zxing.pdf417.detector;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import java.util.Hashtable;

public final class Detector
{
  private static final int MAX_AVG_VARIANCE = 107;
  private static final int MAX_INDIVIDUAL_VARIANCE = 204;
  private static final int SKEW_THRESHOLD = 2;
  private static final int[] START_PATTERN = { 8, 1, 1, 1, 1, 1, 1, 3 };
  private static final int[] START_PATTERN_REVERSE = { 3, 1, 1, 1, 1, 1, 1, 8 };
  private static final int[] STOP_PATTERN = { 7, 1, 1, 3, 1, 1, 1, 2, 1 };
  private static final int[] STOP_PATTERN_REVERSE = { 1, 2, 1, 1, 1, 3, 1, 1, 7 };
  private final BinaryBitmap image;

  public Detector(BinaryBitmap paramBinaryBitmap)
  {
    this.image = paramBinaryBitmap;
  }

  private static int computeDimension(ResultPoint paramResultPoint1, ResultPoint paramResultPoint2, ResultPoint paramResultPoint3, ResultPoint paramResultPoint4, float paramFloat)
  {
    return 17 * ((8 + (round(ResultPoint.distance(paramResultPoint1, paramResultPoint2) / paramFloat) + round(ResultPoint.distance(paramResultPoint3, paramResultPoint4) / paramFloat) >> 1)) / 17);
  }

  private static float computeModuleWidth(ResultPoint[] paramArrayOfResultPoint)
  {
    return ((ResultPoint.distance(paramArrayOfResultPoint[0], paramArrayOfResultPoint[4]) + ResultPoint.distance(paramArrayOfResultPoint[1], paramArrayOfResultPoint[5])) / 34.0F + (ResultPoint.distance(paramArrayOfResultPoint[6], paramArrayOfResultPoint[2]) + ResultPoint.distance(paramArrayOfResultPoint[7], paramArrayOfResultPoint[3])) / 36.0F) / 2.0F;
  }

  private static void correctCodeWordVertices(ResultPoint[] paramArrayOfResultPoint, boolean paramBoolean)
  {
    float f1 = paramArrayOfResultPoint[4].getY() - paramArrayOfResultPoint[6].getY();
    if (paramBoolean)
      f1 = -f1;
    float f14;
    if (f1 > 2.0F)
    {
      float f12 = paramArrayOfResultPoint[4].getX() - paramArrayOfResultPoint[0].getX();
      float f13 = paramArrayOfResultPoint[6].getX() - paramArrayOfResultPoint[0].getX();
      f14 = f12 * (paramArrayOfResultPoint[6].getY() - paramArrayOfResultPoint[0].getY()) / f13;
    }
    float f5;
    do
    {
      paramArrayOfResultPoint[4] = new ResultPoint(paramArrayOfResultPoint[4].getX(), f14 + paramArrayOfResultPoint[4].getY());
      while (true)
      {
        f5 = paramArrayOfResultPoint[7].getY() - paramArrayOfResultPoint[5].getY();
        if (paramBoolean)
          f5 = -f5;
        if (f5 <= 2.0F)
          break;
        float f9 = paramArrayOfResultPoint[5].getX() - paramArrayOfResultPoint[1].getX();
        float f10 = paramArrayOfResultPoint[7].getX() - paramArrayOfResultPoint[1].getX();
        float f11 = f9 * (paramArrayOfResultPoint[7].getY() - paramArrayOfResultPoint[1].getY()) / f10;
        paramArrayOfResultPoint[5] = new ResultPoint(paramArrayOfResultPoint[5].getX(), f11 + paramArrayOfResultPoint[5].getY());
        return;
        if (-f1 <= 2.0F)
          continue;
        float f2 = paramArrayOfResultPoint[2].getX() - paramArrayOfResultPoint[6].getX();
        float f3 = paramArrayOfResultPoint[2].getX() - paramArrayOfResultPoint[4].getX();
        float f4 = f2 * (paramArrayOfResultPoint[2].getY() - paramArrayOfResultPoint[4].getY()) / f3;
        paramArrayOfResultPoint[6] = new ResultPoint(paramArrayOfResultPoint[6].getX(), paramArrayOfResultPoint[6].getY() - f4);
      }
    }
    while (-f5 <= 2.0F);
    float f6 = paramArrayOfResultPoint[3].getX() - paramArrayOfResultPoint[7].getX();
    float f7 = paramArrayOfResultPoint[3].getX() - paramArrayOfResultPoint[5].getX();
    float f8 = f6 * (paramArrayOfResultPoint[3].getY() - paramArrayOfResultPoint[5].getY()) / f7;
    paramArrayOfResultPoint[7] = new ResultPoint(paramArrayOfResultPoint[7].getX(), paramArrayOfResultPoint[7].getY() - f8);
  }

  private static int[] findGuardPattern(BitMatrix paramBitMatrix, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;
    int[] arrayOfInt = new int[i];
    boolean bool = paramBoolean;
    int j = 0;
    int k = paramInt1;
    for (int m = paramInt1; ; m++)
    {
      if (m >= paramInt1 + paramInt3)
        return null;
      if (!(bool ^ paramBitMatrix.get(m, paramInt2)))
        break;
      arrayOfInt[j] = (1 + arrayOfInt[j]);
    }
    int n;
    label146: if (j == i - 1)
    {
      if (patternMatchVariance(arrayOfInt, paramArrayOfInt, 204) < 107)
        return new int[] { k, m };
      k += arrayOfInt[0] + arrayOfInt[1];
      n = 2;
      label120: if (n >= i)
      {
        arrayOfInt[(i - 2)] = 0;
        arrayOfInt[(i - 1)] = 0;
        j--;
        arrayOfInt[j] = 1;
        if (!bool)
          break label187;
      }
    }
    label187: for (bool = false; ; bool = true)
    {
      break;
      arrayOfInt[(n - 2)] = arrayOfInt[n];
      n++;
      break label120;
      j++;
      break label146;
    }
  }

  private static ResultPoint[] findVertices(BitMatrix paramBitMatrix)
  {
    int i = paramBitMatrix.getHeight();
    int j = paramBitMatrix.getWidth();
    ResultPoint[] arrayOfResultPoint = new ResultPoint[8];
    int k = 0;
    int m = 0;
    label28: int i2;
    label38: label46: int i1;
    if (k >= i)
    {
      if (m != 0)
      {
        i2 = i - 1;
        m = 0;
        if (i2 > 0)
          break label155;
      }
      if (m != 0)
      {
        i1 = 0;
        label54: m = 0;
        if (i1 < i)
          break label222;
      }
      label63: if (m == 0);
    }
    label155: label222: label352: for (int n = i - 1; ; n--)
    {
      m = 0;
      if (n <= 0);
      while (true)
      {
        if (m == 0)
          break label358;
        return arrayOfResultPoint;
        int[] arrayOfInt1 = findGuardPattern(paramBitMatrix, 0, k, j, false, START_PATTERN);
        if (arrayOfInt1 != null)
        {
          arrayOfResultPoint[0] = new ResultPoint(arrayOfInt1[0], k);
          arrayOfResultPoint[4] = new ResultPoint(arrayOfInt1[1], k);
          m = 1;
          break label28;
        }
        k++;
        break;
        int[] arrayOfInt4 = findGuardPattern(paramBitMatrix, 0, i2, j, false, START_PATTERN);
        if (arrayOfInt4 != null)
        {
          arrayOfResultPoint[1] = new ResultPoint(arrayOfInt4[0], i2);
          arrayOfResultPoint[5] = new ResultPoint(arrayOfInt4[1], i2);
          m = 1;
          break label46;
        }
        i2--;
        break label38;
        int[] arrayOfInt3 = findGuardPattern(paramBitMatrix, 0, i1, j, false, STOP_PATTERN);
        if (arrayOfInt3 != null)
        {
          arrayOfResultPoint[2] = new ResultPoint(arrayOfInt3[1], i1);
          arrayOfResultPoint[6] = new ResultPoint(arrayOfInt3[0], i1);
          m = 1;
          break label63;
        }
        i1++;
        break label54;
        int[] arrayOfInt2 = findGuardPattern(paramBitMatrix, 0, n, j, false, STOP_PATTERN);
        if (arrayOfInt2 == null)
          break label352;
        arrayOfResultPoint[3] = new ResultPoint(arrayOfInt2[1], n);
        arrayOfResultPoint[7] = new ResultPoint(arrayOfInt2[0], n);
        m = 1;
      }
    }
    label358: return null;
  }

  private static ResultPoint[] findVertices180(BitMatrix paramBitMatrix)
  {
    int i = paramBitMatrix.getHeight();
    int j = paramBitMatrix.getWidth() >> 1;
    ResultPoint[] arrayOfResultPoint = new ResultPoint[8];
    int k = i - 1;
    int m = 0;
    label31: int i2;
    label39: label48: int i1;
    if (k <= 0)
    {
      if (m != 0)
      {
        i2 = 0;
        m = 0;
        if (i2 < i)
          break label157;
      }
      if (m != 0)
      {
        i1 = i - 1;
        label58: m = 0;
        if (i1 > 0)
          break label224;
      }
      label66: if (m == 0);
    }
    label157: label224: label362: for (int n = 0; ; n++)
    {
      m = 0;
      if (n >= i);
      while (true)
      {
        if (m == 0)
          break label368;
        return arrayOfResultPoint;
        int[] arrayOfInt1 = findGuardPattern(paramBitMatrix, j, k, j, true, START_PATTERN_REVERSE);
        if (arrayOfInt1 != null)
        {
          arrayOfResultPoint[0] = new ResultPoint(arrayOfInt1[1], k);
          arrayOfResultPoint[4] = new ResultPoint(arrayOfInt1[0], k);
          m = 1;
          break label31;
        }
        k--;
        break;
        int[] arrayOfInt6 = findGuardPattern(paramBitMatrix, j, i2, j, true, START_PATTERN_REVERSE);
        if (arrayOfInt6 != null)
        {
          arrayOfResultPoint[1] = new ResultPoint(arrayOfInt6[1], i2);
          arrayOfResultPoint[5] = new ResultPoint(arrayOfInt6[0], i2);
          m = 1;
          break label48;
        }
        i2++;
        break label39;
        int[] arrayOfInt4 = STOP_PATTERN_REVERSE;
        int[] arrayOfInt5 = findGuardPattern(paramBitMatrix, 0, i1, j, false, arrayOfInt4);
        if (arrayOfInt5 != null)
        {
          arrayOfResultPoint[2] = new ResultPoint(arrayOfInt5[0], i1);
          arrayOfResultPoint[6] = new ResultPoint(arrayOfInt5[1], i1);
          m = 1;
          break label66;
        }
        i1--;
        break label58;
        int[] arrayOfInt2 = STOP_PATTERN_REVERSE;
        int[] arrayOfInt3 = findGuardPattern(paramBitMatrix, 0, n, j, false, arrayOfInt2);
        if (arrayOfInt3 == null)
          break label362;
        arrayOfResultPoint[3] = new ResultPoint(arrayOfInt3[0], n);
        arrayOfResultPoint[7] = new ResultPoint(arrayOfInt3[1], n);
        m = 1;
      }
    }
    label368: return null;
  }

  private static int patternMatchVariance(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt)
  {
    int i = paramArrayOfInt1.length;
    int j = 0;
    int k = 0;
    int m = 0;
    if (m >= i)
      if (j >= k)
        break label52;
    label149: 
    while (true)
    {
      return 2147483647;
      j += paramArrayOfInt1[m];
      k += paramArrayOfInt2[m];
      m++;
      break;
      label52: int n = (j << 8) / k;
      int i1 = paramInt * n >> 8;
      int i2 = 0;
      int i3 = 0;
      if (i3 >= i)
        return i2 / j;
      int i4 = paramArrayOfInt1[i3] << 8;
      int i5 = n * paramArrayOfInt2[i3];
      if (i4 > i5);
      for (int i6 = i4 - i5; ; i6 = i5 - i4)
      {
        if (i6 > i1)
          break label149;
        i2 += i6;
        i3++;
        break;
      }
    }
  }

  private static int round(float paramFloat)
  {
    return (int)(0.5F + paramFloat);
  }

  private static BitMatrix sampleGrid(BitMatrix paramBitMatrix, ResultPoint paramResultPoint1, ResultPoint paramResultPoint2, ResultPoint paramResultPoint3, ResultPoint paramResultPoint4, int paramInt)
    throws NotFoundException
  {
    return GridSampler.getInstance().sampleGrid(paramBitMatrix, paramInt, paramInt, 0.0F, 0.0F, paramInt, 0.0F, paramInt, paramInt, 0.0F, paramInt, paramResultPoint1.getX(), paramResultPoint1.getY(), paramResultPoint3.getX(), paramResultPoint3.getY(), paramResultPoint4.getX(), paramResultPoint4.getY(), paramResultPoint2.getX(), paramResultPoint2.getY());
  }

  public DetectorResult detect()
    throws NotFoundException
  {
    return detect(null);
  }

  public DetectorResult detect(Hashtable paramHashtable)
    throws NotFoundException
  {
    BitMatrix localBitMatrix1 = this.image.getBlackMatrix();
    ResultPoint[] arrayOfResultPoint1 = findVertices(localBitMatrix1);
    if (arrayOfResultPoint1 == null)
    {
      arrayOfResultPoint1 = findVertices180(localBitMatrix1);
      if (arrayOfResultPoint1 != null)
        correctCodeWordVertices(arrayOfResultPoint1, true);
    }
    while (arrayOfResultPoint1 == null)
    {
      throw NotFoundException.getNotFoundInstance();
      correctCodeWordVertices(arrayOfResultPoint1, false);
    }
    float f = computeModuleWidth(arrayOfResultPoint1);
    if (f < 1.0F)
      throw NotFoundException.getNotFoundInstance();
    int i = computeDimension(arrayOfResultPoint1[4], arrayOfResultPoint1[6], arrayOfResultPoint1[5], arrayOfResultPoint1[7], f);
    if (i < 1)
      throw NotFoundException.getNotFoundInstance();
    BitMatrix localBitMatrix2 = sampleGrid(localBitMatrix1, arrayOfResultPoint1[4], arrayOfResultPoint1[5], arrayOfResultPoint1[6], arrayOfResultPoint1[7], i);
    ResultPoint[] arrayOfResultPoint2 = new ResultPoint[4];
    arrayOfResultPoint2[0] = arrayOfResultPoint1[4];
    arrayOfResultPoint2[1] = arrayOfResultPoint1[5];
    arrayOfResultPoint2[2] = arrayOfResultPoint1[6];
    arrayOfResultPoint2[3] = arrayOfResultPoint1[7];
    return new DetectorResult(localBitMatrix2, arrayOfResultPoint2);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.pdf417.detector.Detector
 * JD-Core Version:    0.6.0
 */