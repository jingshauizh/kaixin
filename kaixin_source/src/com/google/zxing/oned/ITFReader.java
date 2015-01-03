package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Hashtable;

public final class ITFReader extends OneDReader
{
  private static final int[] DEFAULT_ALLOWED_LENGTHS = { 6, 8, 10, 12, 14, 16, 20, 24, 44 };
  private static final int[] END_PATTERN_REVERSED;
  private static final int MAX_AVG_VARIANCE = 107;
  private static final int MAX_INDIVIDUAL_VARIANCE = 204;
  private static final int N = 1;
  static final int[][] PATTERNS;
  private static final int[] START_PATTERN = { 1, 1, 1, 1 };
  private static final int W = 3;
  private int narrowLineWidth = -1;

  static
  {
    END_PATTERN_REVERSED = new int[] { 1, 1, 3 };
    PATTERNS = new int[][] { { 1, 1, 3, 3, 1 }, { 3, 1, 1, 1, 3 }, { 1, 3, 1, 1, 3 }, { 3, 3, 1, 1, 1 }, { 1, 1, 3, 1, 3 }, { 3, 1, 3, 1, 1 }, { 1, 3, 3, 1, 1 }, { 1, 1, 1, 3, 3 }, { 3, 1, 1, 3, 1 }, { 1, 3, 1, 3, 1 } };
  }

  private static int decodeDigit(int[] paramArrayOfInt)
    throws NotFoundException
  {
    int i = 107;
    int j = -1;
    int k = PATTERNS.length;
    for (int m = 0; ; m++)
    {
      if (m >= k)
      {
        if (j < 0)
          break;
        return j;
      }
      int n = patternMatchVariance(paramArrayOfInt, PATTERNS[m], 204);
      if (n >= i)
        continue;
      i = n;
      j = m;
    }
    throw NotFoundException.getNotFoundInstance();
  }

  private static void decodeMiddle(BitArray paramBitArray, int paramInt1, int paramInt2, StringBuffer paramStringBuffer)
    throws NotFoundException
  {
    int[] arrayOfInt1 = new int[10];
    int[] arrayOfInt2 = new int[5];
    int[] arrayOfInt3 = new int[5];
    if (paramInt1 >= paramInt2)
      return;
    recordPattern(paramBitArray, paramInt1, arrayOfInt1);
    for (int i = 0; ; i++)
    {
      if (i >= 5)
      {
        paramStringBuffer.append((char)(48 + decodeDigit(arrayOfInt2)));
        paramStringBuffer.append((char)(48 + decodeDigit(arrayOfInt3)));
        for (int k = 0; k < arrayOfInt1.length; k++)
          paramInt1 += arrayOfInt1[k];
        break;
      }
      int j = i << 1;
      arrayOfInt2[i] = arrayOfInt1[j];
      arrayOfInt3[i] = arrayOfInt1[(j + 1)];
    }
  }

  private static int[] findGuardPattern(BitArray paramBitArray, int paramInt, int[] paramArrayOfInt)
    throws NotFoundException
  {
    int i = paramArrayOfInt.length;
    int[] arrayOfInt = new int[i];
    int j = paramBitArray.getSize();
    int k = 0;
    int m = 0;
    int n = paramInt;
    for (int i1 = paramInt; ; i1++)
    {
      if (i1 >= j)
        throw NotFoundException.getNotFoundInstance();
      if ((k ^ paramBitArray.get(i1)) == 0)
        break;
      arrayOfInt[m] = (1 + arrayOfInt[m]);
    }
    int i2;
    label143: if (m == i - 1)
    {
      if (patternMatchVariance(arrayOfInt, paramArrayOfInt, 204) < 107)
        return new int[] { n, i1 };
      n += arrayOfInt[0] + arrayOfInt[1];
      i2 = 2;
      label120: if (i2 >= i)
      {
        arrayOfInt[(i - 2)] = 0;
        arrayOfInt[(i - 1)] = 0;
        m--;
        arrayOfInt[m] = 1;
        if (k == 0)
          break label184;
      }
    }
    label184: for (k = 0; ; k = 1)
    {
      break;
      arrayOfInt[(i2 - 2)] = arrayOfInt[i2];
      i2++;
      break label120;
      m++;
      break label143;
    }
  }

  private static int skipWhiteSpace(BitArray paramBitArray)
    throws NotFoundException
  {
    int i = paramBitArray.getSize();
    for (int j = 0; ; j++)
    {
      if (j >= i);
      do
      {
        if (j != i)
          break;
        throw NotFoundException.getNotFoundInstance();
      }
      while (paramBitArray.get(j));
    }
    return j;
  }

  private void validateQuietZone(BitArray paramBitArray, int paramInt)
    throws NotFoundException
  {
    int i = 10 * this.narrowLineWidth;
    for (int j = paramInt - 1; ; j--)
    {
      if ((i <= 0) || (j < 0));
      do
      {
        if (i == 0)
          break;
        throw NotFoundException.getNotFoundInstance();
      }
      while (paramBitArray.get(j));
      i--;
    }
  }

  int[] decodeEnd(BitArray paramBitArray)
    throws NotFoundException
  {
    paramBitArray.reverse();
    try
    {
      int[] arrayOfInt = findGuardPattern(paramBitArray, skipWhiteSpace(paramBitArray), END_PATTERN_REVERSED);
      validateQuietZone(paramBitArray, arrayOfInt[0]);
      int i = arrayOfInt[0];
      arrayOfInt[0] = (paramBitArray.getSize() - arrayOfInt[1]);
      arrayOfInt[1] = (paramBitArray.getSize() - i);
      return arrayOfInt;
    }
    finally
    {
      paramBitArray.reverse();
    }
    throw localObject;
  }

  public Result decodeRow(int paramInt, BitArray paramBitArray, Hashtable paramHashtable)
    throws FormatException, NotFoundException
  {
    int[] arrayOfInt1 = decodeStart(paramBitArray);
    int[] arrayOfInt2 = decodeEnd(paramBitArray);
    StringBuffer localStringBuffer = new StringBuffer(20);
    decodeMiddle(paramBitArray, arrayOfInt1[1], arrayOfInt2[0], localStringBuffer);
    String str = localStringBuffer.toString();
    int[] arrayOfInt3 = (int[])null;
    if (paramHashtable != null)
      arrayOfInt3 = (int[])paramHashtable.get(DecodeHintType.ALLOWED_LENGTHS);
    if (arrayOfInt3 == null)
      arrayOfInt3 = DEFAULT_ALLOWED_LENGTHS;
    int i = str.length();
    for (int j = 0; ; j++)
    {
      int k = arrayOfInt3.length;
      int m = 0;
      if (j >= k);
      while (true)
      {
        if (m != 0)
          break label134;
        throw FormatException.getFormatInstance();
        if (i != arrayOfInt3[j])
          break;
        m = 1;
      }
    }
    label134: ResultPoint[] arrayOfResultPoint = new ResultPoint[2];
    arrayOfResultPoint[0] = new ResultPoint(arrayOfInt1[1], paramInt);
    arrayOfResultPoint[1] = new ResultPoint(arrayOfInt2[0], paramInt);
    return new Result(str, null, arrayOfResultPoint, BarcodeFormat.ITF);
  }

  int[] decodeStart(BitArray paramBitArray)
    throws NotFoundException
  {
    int[] arrayOfInt = findGuardPattern(paramBitArray, skipWhiteSpace(paramBitArray), START_PATTERN);
    this.narrowLineWidth = (arrayOfInt[1] - arrayOfInt[0] >> 2);
    validateQuietZone(paramBitArray, arrayOfInt[0]);
    return arrayOfInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.ITFReader
 * JD-Core Version:    0.6.0
 */