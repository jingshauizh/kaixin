package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

public final class MonochromeRectangleDetector
{
  private static final int MAX_MODULES = 32;
  private final BitMatrix image;

  public MonochromeRectangleDetector(BitMatrix paramBitMatrix)
  {
    this.image = paramBitMatrix;
  }

  private int[] blackWhiteRange(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    int i = paramInt3 + paramInt4 >> 1;
    int j = i;
    label18: int i1;
    if (j < paramInt3)
    {
      int n = j + 1;
      i1 = i;
      if (i1 >= paramInt4)
      {
        label35: int i4 = i1 - 1;
        if (i4 <= n)
          break label284;
        return new int[] { n, i4 };
      }
    }
    else
    {
      if (paramBoolean)
      {
        if (!this.image.get(j, paramInt1));
      }
      else
        do
        {
          j--;
          break;
        }
        while (this.image.get(paramInt1, j));
      int k = j;
      label103: 
      do
      {
        j--;
        if (j < paramInt3)
          break;
        if (!paramBoolean)
          break label156;
      }
      while (!this.image.get(j, paramInt1));
      while (true)
      {
        int m = k - j;
        if ((j >= paramInt3) && (m <= paramInt2))
          break;
        j = k;
        break label18;
        label156: if (!this.image.get(paramInt1, j))
          break label103;
      }
    }
    if (paramBoolean)
    {
      if (!this.image.get(i1, paramInt1));
    }
    else
      do
      {
        i1++;
        break;
      }
      while (this.image.get(paramInt1, i1));
    int i2 = i1;
    label213: 
    do
    {
      i1++;
      if (i1 >= paramInt4)
        break;
      if (!paramBoolean)
        break label268;
    }
    while (!this.image.get(i1, paramInt1));
    while (true)
    {
      int i3 = i1 - i2;
      if ((i1 < paramInt4) && (i3 <= paramInt2))
        break;
      i1 = i2;
      break label35;
      label268: if (!this.image.get(paramInt1, i1))
        break label213;
    }
    label284: return null;
  }

  private ResultPoint findCornerFromCenter(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9)
    throws NotFoundException
  {
    Object localObject = (int[])null;
    int i = paramInt5;
    int j = paramInt1;
    while (true)
    {
      if ((i >= paramInt8) || (i < paramInt7) || (j >= paramInt4) || (j < paramInt3))
        throw NotFoundException.getNotFoundInstance();
      int[] arrayOfInt;
      if (paramInt2 == 0)
        arrayOfInt = blackWhiteRange(i, paramInt9, paramInt3, paramInt4, true);
      while (arrayOfInt == null)
      {
        if (localObject == null)
        {
          throw NotFoundException.getNotFoundInstance();
          arrayOfInt = blackWhiteRange(j, paramInt9, paramInt7, paramInt8, false);
          continue;
        }
        if (paramInt2 == 0)
        {
          int i1 = i - paramInt6;
          if (localObject[0] < paramInt1)
          {
            if (localObject[1] > paramInt1)
            {
              if (paramInt6 > 0);
              int i3;
              for (int i2 = localObject[0]; ; i3 = localObject[1])
                return new ResultPoint(i2, i1);
            }
            return new ResultPoint(localObject[0], i1);
          }
          return new ResultPoint(localObject[1], i1);
        }
        int k = j - paramInt2;
        if (localObject[0] < paramInt5)
        {
          if (localObject[1] > paramInt5)
          {
            float f = k;
            if (paramInt2 < 0);
            int n;
            for (int m = localObject[0]; ; n = localObject[1])
              return new ResultPoint(f, m);
          }
          return new ResultPoint(k, localObject[0]);
        }
        return new ResultPoint(k, localObject[1]);
      }
      localObject = arrayOfInt;
      i += paramInt6;
      j += paramInt2;
    }
  }

  public ResultPoint[] detect()
    throws NotFoundException
  {
    int i = this.image.getHeight();
    int j = this.image.getWidth();
    int k = i >> 1;
    int m = j >> 1;
    int n = Math.max(1, i / 256);
    int i1 = Math.max(1, j / 256);
    int i2 = -1 + (int)findCornerFromCenter(m, 0, 0, j, k, -n, 0, i, m >> 1).getY();
    ResultPoint localResultPoint1 = findCornerFromCenter(m, -i1, 0, j, k, 0, i2, i, k >> 1);
    int i3 = -1 + (int)localResultPoint1.getX();
    ResultPoint localResultPoint2 = findCornerFromCenter(m, i1, i3, j, k, 0, i2, i, k >> 1);
    int i4 = 1 + (int)localResultPoint2.getX();
    ResultPoint localResultPoint3 = findCornerFromCenter(m, 0, i3, i4, k, n, i2, i, m >> 1);
    int i5 = 1 + (int)localResultPoint3.getY();
    return new ResultPoint[] { findCornerFromCenter(m, 0, i3, i4, k, -n, i2, i5, m >> 2), localResultPoint1, localResultPoint2, localResultPoint3 };
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.common.detector.MonochromeRectangleDetector
 * JD-Core Version:    0.6.0
 */