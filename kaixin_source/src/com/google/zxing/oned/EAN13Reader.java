package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

public final class EAN13Reader extends UPCEANReader
{
  static final int[] FIRST_DIGIT_ENCODINGS;
  private final int[] decodeMiddleCounters = new int[4];

  static
  {
    int[] arrayOfInt = new int[10];
    arrayOfInt[1] = 11;
    arrayOfInt[2] = 13;
    arrayOfInt[3] = 14;
    arrayOfInt[4] = 19;
    arrayOfInt[5] = 25;
    arrayOfInt[6] = 28;
    arrayOfInt[7] = 21;
    arrayOfInt[8] = 22;
    arrayOfInt[9] = 26;
    FIRST_DIGIT_ENCODINGS = arrayOfInt;
  }

  private static void determineFirstDigit(StringBuffer paramStringBuffer, int paramInt)
    throws NotFoundException
  {
    for (int i = 0; ; i++)
    {
      if (i >= 10)
        throw NotFoundException.getNotFoundInstance();
      if (paramInt != FIRST_DIGIT_ENCODINGS[i])
        continue;
      paramStringBuffer.insert(0, (char)(i + 48));
      return;
    }
  }

  protected int decodeMiddle(BitArray paramBitArray, int[] paramArrayOfInt, StringBuffer paramStringBuffer)
    throws NotFoundException
  {
    int[] arrayOfInt = this.decodeMiddleCounters;
    arrayOfInt[0] = 0;
    arrayOfInt[1] = 0;
    arrayOfInt[2] = 0;
    arrayOfInt[3] = 0;
    int i = paramBitArray.getSize();
    int j = paramArrayOfInt[1];
    int k = 0;
    int m = 0;
    int n;
    int i1;
    if ((m >= 6) || (j >= i))
    {
      determineFirstDigit(paramStringBuffer, k);
      n = findGuardPattern(paramBitArray, j, true, MIDDLE_PATTERN)[1];
      i1 = 0;
      if ((i1 >= 6) || (n >= i))
        return n;
    }
    else
    {
      int i3 = decodeDigit(paramBitArray, arrayOfInt, j, L_AND_G_PATTERNS);
      paramStringBuffer.append((char)(48 + i3 % 10));
      for (int i4 = 0; ; i4++)
      {
        if (i4 >= arrayOfInt.length)
        {
          if (i3 >= 10)
            k |= 1 << 5 - m;
          m++;
          break;
        }
        j += arrayOfInt[i4];
      }
    }
    paramStringBuffer.append((char)(48 + decodeDigit(paramBitArray, arrayOfInt, n, L_PATTERNS)));
    for (int i2 = 0; ; i2++)
    {
      if (i2 >= arrayOfInt.length)
      {
        i1++;
        break;
      }
      n += arrayOfInt[i2];
    }
  }

  BarcodeFormat getBarcodeFormat()
  {
    return BarcodeFormat.EAN_13;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.EAN13Reader
 * JD-Core Version:    0.6.0
 */