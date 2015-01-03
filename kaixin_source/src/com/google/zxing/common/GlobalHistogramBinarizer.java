package com.google.zxing.common;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;

public class GlobalHistogramBinarizer extends Binarizer
{
  private static final int LUMINANCE_BITS = 5;
  private static final int LUMINANCE_BUCKETS = 32;
  private static final int LUMINANCE_SHIFT = 3;
  private int[] buckets = null;
  private byte[] luminances = null;

  public GlobalHistogramBinarizer(LuminanceSource paramLuminanceSource)
  {
    super(paramLuminanceSource);
  }

  private static int estimateBlackPoint(int[] paramArrayOfInt)
    throws NotFoundException
  {
    int i = paramArrayOfInt.length;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1;
    int i2;
    if (n >= i)
    {
      i1 = 0;
      i2 = 0;
    }
    for (int i3 = 0; ; i3++)
    {
      if (i3 >= i)
      {
        if (k > i1)
        {
          int i11 = k;
          k = i1;
          i1 = i11;
        }
        if (i1 - k > i >> 4)
          break label140;
        throw NotFoundException.getNotFoundInstance();
        if (paramArrayOfInt[n] > m)
        {
          k = n;
          m = paramArrayOfInt[n];
        }
        if (paramArrayOfInt[n] > j)
          j = paramArrayOfInt[n];
        n++;
        break;
      }
      int i4 = i3 - k;
      int i5 = i4 * (i4 * paramArrayOfInt[i3]);
      if (i5 <= i2)
        continue;
      i1 = i3;
      i2 = i5;
    }
    label140: int i6 = i1 - 1;
    int i7 = -1;
    for (int i8 = i1 - 1; ; i8--)
    {
      if (i8 <= k)
        return i6 << 3;
      int i9 = i8 - k;
      int i10 = i9 * i9 * (i1 - i8) * (j - paramArrayOfInt[i8]);
      if (i10 <= i7)
        continue;
      i6 = i8;
      i7 = i10;
    }
  }

  private void initArrays(int paramInt)
  {
    if ((this.luminances == null) || (this.luminances.length < paramInt))
      this.luminances = new byte[paramInt];
    if (this.buckets == null)
      this.buckets = new int[32];
    while (true)
    {
      return;
      for (int i = 0; i < 32; i++)
        this.buckets[i] = 0;
    }
  }

  public Binarizer createBinarizer(LuminanceSource paramLuminanceSource)
  {
    return new GlobalHistogramBinarizer(paramLuminanceSource);
  }

  public BitMatrix getBlackMatrix()
    throws NotFoundException
  {
    LuminanceSource localLuminanceSource = getLuminanceSource();
    int i = localLuminanceSource.getWidth();
    int j = localLuminanceSource.getHeight();
    BitMatrix localBitMatrix = new BitMatrix(i, j);
    initArrays(i);
    int[] arrayOfInt = this.buckets;
    int k = 1;
    int i2;
    byte[] arrayOfByte2;
    int i3;
    if (k >= 5)
    {
      i2 = estimateBlackPoint(arrayOfInt);
      arrayOfByte2 = localLuminanceSource.getMatrix();
      i3 = 0;
      if (i3 >= j)
        return localBitMatrix;
    }
    else
    {
      byte[] arrayOfByte1 = localLuminanceSource.getRow(j * k / 5, this.luminances);
      int m = (i << 2) / 5;
      for (int n = i / 5; ; n++)
      {
        if (n >= m)
        {
          k++;
          break;
        }
        int i1 = (0xFF & arrayOfByte1[n]) >> 3;
        arrayOfInt[i1] = (1 + arrayOfInt[i1]);
      }
    }
    int i4 = i3 * i;
    for (int i5 = 0; ; i5++)
    {
      if (i5 >= i)
      {
        i3++;
        break;
      }
      if ((0xFF & arrayOfByte2[(i4 + i5)]) >= i2)
        continue;
      localBitMatrix.set(i5, i3);
    }
  }

  public BitArray getBlackRow(int paramInt, BitArray paramBitArray)
    throws NotFoundException
  {
    LuminanceSource localLuminanceSource = getLuminanceSource();
    int i = localLuminanceSource.getWidth();
    byte[] arrayOfByte;
    int[] arrayOfInt;
    int j;
    label60: int m;
    int n;
    int i1;
    if ((paramBitArray == null) || (paramBitArray.getSize() < i))
    {
      paramBitArray = new BitArray(i);
      initArrays(i);
      arrayOfByte = localLuminanceSource.getRow(paramInt, this.luminances);
      arrayOfInt = this.buckets;
      j = 0;
      if (j < i)
        break label115;
      m = estimateBlackPoint(arrayOfInt);
      n = 0xFF & arrayOfByte[0];
      i1 = 0xFF & arrayOfByte[1];
    }
    for (int i2 = 1; ; i2++)
    {
      if (i2 >= i - 1)
      {
        return paramBitArray;
        paramBitArray.clear();
        break;
        label115: int k = (0xFF & arrayOfByte[j]) >> 3;
        arrayOfInt[k] = (1 + arrayOfInt[k]);
        j++;
        break label60;
      }
      int i3 = 0xFF & arrayOfByte[(i2 + 1)];
      if ((i1 << 2) - n - i3 >> 1 < m)
        paramBitArray.set(i2);
      n = i1;
      i1 = i3;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.common.GlobalHistogramBinarizer
 * JD-Core Version:    0.6.0
 */