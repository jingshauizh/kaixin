package com.google.zxing.common;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import java.lang.reflect.Array;

public final class HybridBinarizer extends GlobalHistogramBinarizer
{
  private static final int MINIMUM_DIMENSION = 40;
  private BitMatrix matrix = null;

  public HybridBinarizer(LuminanceSource paramLuminanceSource)
  {
    super(paramLuminanceSource);
  }

  private void binarizeEntireImage()
    throws NotFoundException
  {
    if (this.matrix == null)
    {
      LuminanceSource localLuminanceSource = getLuminanceSource();
      if ((localLuminanceSource.getWidth() >= 40) && (localLuminanceSource.getHeight() >= 40))
      {
        byte[] arrayOfByte = localLuminanceSource.getMatrix();
        int i = localLuminanceSource.getWidth();
        int j = localLuminanceSource.getHeight();
        int k = i >> 3;
        if ((i & 0x7) != 0)
          k++;
        int m = j >> 3;
        if ((j & 0x7) != 0)
          m++;
        int[][] arrayOfInt = calculateBlackPoints(arrayOfByte, k, m, i, j);
        this.matrix = new BitMatrix(i, j);
        calculateThresholdForBlock(arrayOfByte, k, m, i, j, arrayOfInt, this.matrix);
      }
    }
    else
    {
      return;
    }
    this.matrix = super.getBlackMatrix();
  }

  private static int[][] calculateBlackPoints(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int[] arrayOfInt = { paramInt2, paramInt1 };
    int[][] arrayOfInt1 = (int[][])Array.newInstance(Integer.TYPE, arrayOfInt);
    int i = 0;
    if (i >= paramInt2)
      return arrayOfInt1;
    int j = i << 3;
    if (j + 8 >= paramInt4)
      j = paramInt4 - 8;
    int m;
    int n;
    int i1;
    int i2;
    int i3;
    for (int k = 0; ; k++)
    {
      if (k >= paramInt1)
      {
        i++;
        break;
      }
      m = k << 3;
      if (m + 8 >= paramInt3)
        m = paramInt3 - 8;
      n = 0;
      i1 = 255;
      i2 = 0;
      i3 = 0;
      if (i3 < 8)
        break label151;
      if (i2 - i1 <= 24)
        break label227;
      i7 = n >> 6;
      arrayOfInt1[i][k] = i7;
    }
    label151: int i4 = m + paramInt3 * (j + i3);
    for (int i5 = 0; ; i5++)
    {
      if (i5 >= 8)
      {
        i3++;
        break;
      }
      int i6 = 0xFF & paramArrayOfByte[(i4 + i5)];
      n += i6;
      if (i6 < i1)
        i1 = i6;
      if (i6 <= i2)
        continue;
      i2 = i6;
    }
    label227: if (i2 == 0);
    for (int i7 = 1; ; i7 = i1 >> 1)
      break;
  }

  private static void calculateThresholdForBlock(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[][] paramArrayOfInt, BitMatrix paramBitMatrix)
  {
    int j;
    int k;
    for (int i = 0; ; i++)
    {
      if (i >= paramInt2)
        return;
      j = i << 3;
      if (j + 8 >= paramInt4)
        j = paramInt4 - 8;
      k = 0;
      if (k < paramInt1)
        break;
    }
    int m = k << 3;
    if (m + 8 >= paramInt3)
      m = paramInt3 - 8;
    int n;
    label79: label87: int i1;
    label97: label105: int i2;
    if (k > 1)
    {
      n = k;
      if (n >= paramInt1 - 2)
        break label146;
      if (i <= 1)
        break label154;
      i1 = i;
      if (i1 >= paramInt2 - 2)
        break label160;
      i2 = 0;
    }
    for (int i3 = -2; ; i3++)
    {
      if (i3 > 2)
      {
        threshold8x8Block(paramArrayOfByte, m, j, i2 / 25, paramInt3, paramBitMatrix);
        k++;
        break;
        n = 2;
        break label79;
        label146: n = paramInt1 - 3;
        break label87;
        label154: i1 = 2;
        break label97;
        label160: i1 = paramInt2 - 3;
        break label105;
      }
      int[] arrayOfInt = paramArrayOfInt[(i1 + i3)];
      i2 = i2 + arrayOfInt[(n - 2)] + arrayOfInt[(n - 1)] + arrayOfInt[n] + arrayOfInt[(n + 1)] + arrayOfInt[(n + 2)];
    }
  }

  private static void threshold8x8Block(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, BitMatrix paramBitMatrix)
  {
    int i = 0;
    if (i >= 8)
      return;
    int j = paramInt1 + paramInt4 * (paramInt2 + i);
    for (int k = 0; ; k++)
    {
      if (k >= 8)
      {
        i++;
        break;
      }
      if ((0xFF & paramArrayOfByte[(j + k)]) >= paramInt3)
        continue;
      paramBitMatrix.set(paramInt1 + k, paramInt2 + i);
    }
  }

  public Binarizer createBinarizer(LuminanceSource paramLuminanceSource)
  {
    return new HybridBinarizer(paramLuminanceSource);
  }

  public BitMatrix getBlackMatrix()
    throws NotFoundException
  {
    binarizeEntireImage();
    return this.matrix;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.common.HybridBinarizer
 * JD-Core Version:    0.6.0
 */