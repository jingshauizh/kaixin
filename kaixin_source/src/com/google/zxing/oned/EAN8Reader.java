package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

public final class EAN8Reader extends UPCEANReader
{
  private final int[] decodeMiddleCounters = new int[4];

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
    int m;
    int n;
    if ((k >= 4) || (j >= i))
    {
      m = findGuardPattern(paramBitArray, j, true, MIDDLE_PATTERN)[1];
      n = 0;
      if ((n >= 4) || (m >= i))
        return m;
    }
    else
    {
      paramStringBuffer.append((char)(48 + decodeDigit(paramBitArray, arrayOfInt, j, L_PATTERNS)));
      for (int i2 = 0; ; i2++)
      {
        if (i2 >= arrayOfInt.length)
        {
          k++;
          break;
        }
        j += arrayOfInt[i2];
      }
    }
    paramStringBuffer.append((char)(48 + decodeDigit(paramBitArray, arrayOfInt, m, L_PATTERNS)));
    for (int i1 = 0; ; i1++)
    {
      if (i1 >= arrayOfInt.length)
      {
        n++;
        break;
      }
      m += arrayOfInt[i1];
    }
  }

  BarcodeFormat getBarcodeFormat()
  {
    return BarcodeFormat.EAN_8;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.EAN8Reader
 * JD-Core Version:    0.6.0
 */