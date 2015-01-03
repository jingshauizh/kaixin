package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Hashtable;

public final class CodaBarReader extends OneDReader
{
  private static final char[] ALPHABET = "0123456789-$:/.+ABCDTN".toCharArray();
  private static final String ALPHABET_STRING = "0123456789-$:/.+ABCDTN";
  private static final int[] CHARACTER_ENCODINGS = { 3, 6, 9, 96, 18, 66, 33, 36, 48, 72, 12, 24, 69, 81, 84, 21, 26, 41, 11, 14, 26, 41 };
  private static final char[] STARTEND_ENCODING = { 69, 42, 65, 66, 67, 68, 84, 78 };
  private static final int minCharacterLength = 6;

  private static boolean arrayContains(char[] paramArrayOfChar, char paramChar)
  {
    if (paramArrayOfChar != null);
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfChar.length)
        return false;
      if (paramArrayOfChar[i] == paramChar)
        return true;
    }
  }

  private static int[] findAsteriskPattern(BitArray paramBitArray)
    throws NotFoundException
  {
    int i = paramBitArray.getSize();
    int j = 0;
    label12: int k;
    int[] arrayOfInt1;
    int m;
    int n;
    int i1;
    if (j >= i)
    {
      k = 0;
      arrayOfInt1 = new int[7];
      m = j;
      n = 0;
      i1 = arrayOfInt1.length;
    }
    for (int i2 = j; ; i2++)
    {
      if (i2 >= i)
      {
        throw NotFoundException.getNotFoundInstance();
        if (paramBitArray.get(j))
          break label12;
        j++;
        break;
      }
      if ((n ^ paramBitArray.get(i2)) == 0)
        break label86;
      arrayOfInt1[k] = (1 + arrayOfInt1[k]);
    }
    label86: int i3;
    if (k == i1 - 1)
    {
      try
      {
        if ((arrayContains(STARTEND_ENCODING, toNarrowWidePattern(arrayOfInt1))) && (paramBitArray.isRange(Math.max(0, m - (i2 - m) / 2), m, false)))
        {
          int[] arrayOfInt2 = { m, i2 };
          return arrayOfInt2;
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        m += arrayOfInt1[0] + arrayOfInt1[1];
        i3 = 2;
      }
      label169: if (i3 >= i1)
      {
        arrayOfInt1[(i1 - 2)] = 0;
        arrayOfInt1[(i1 - 1)] = 0;
        k--;
      }
    }
    while (true)
    {
      arrayOfInt1[k] = 1;
      n ^= 1;
      break;
      arrayOfInt1[(i3 - 2)] = arrayOfInt1[i3];
      i3++;
      break label169;
      k++;
    }
  }

  private static char toNarrowWidePattern(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;
    int j = 0;
    int k = 2147483647;
    int m = 0;
    label17: int n;
    int i1;
    int i2;
    if (m >= i)
    {
      n = 0;
      i1 = 0;
      i2 = 0;
      label26: if (i2 < i)
        break label99;
      if ((n != 2) && (n != 3));
    }
    for (int i3 = 0; ; i3++)
    {
      if (i3 >= CHARACTER_ENCODINGS.length)
      {
        j--;
        if (j > k)
          break label17;
        return '!';
        if (paramArrayOfInt[m] < k)
          k = paramArrayOfInt[m];
        if (paramArrayOfInt[m] > j)
          j = paramArrayOfInt[m];
        m++;
        break;
        label99: if (paramArrayOfInt[i2] > j)
        {
          i1 |= 1 << i - 1 - i2;
          n++;
        }
        i2++;
        break label26;
      }
      if (CHARACTER_ENCODINGS[i3] == i1)
        return ALPHABET[i3];
    }
  }

  public Result decodeRow(int paramInt, BitArray paramBitArray, Hashtable paramHashtable)
    throws NotFoundException
  {
    int[] arrayOfInt1 = findAsteriskPattern(paramBitArray);
    arrayOfInt1[1] = 0;
    int i = arrayOfInt1[1];
    int j = paramBitArray.getSize();
    StringBuffer localStringBuffer;
    if ((i >= j) || (paramBitArray.get(i)))
      localStringBuffer = new StringBuffer();
    int[] arrayOfInt2;
    int k;
    int m;
    label109: 
    do
    {
      arrayOfInt2 = new int[7];
      recordPattern(paramBitArray, i, arrayOfInt2);
      char c1 = toNarrowWidePattern(arrayOfInt2);
      if (c1 == '!')
      {
        throw NotFoundException.getNotFoundInstance();
        i++;
        break;
      }
      localStringBuffer.append(c1);
      k = i;
      m = 0;
      if (m < arrayOfInt2.length)
        break label176;
      if ((i < j) && (!paramBitArray.get(i)))
        break label192;
    }
    while (i < j);
    int n = 0;
    for (int i1 = 0; ; i1++)
    {
      if (i1 >= arrayOfInt2.length)
      {
        int i2 = i - k - n;
        if ((i == j) || (i2 / 2 >= n))
          break label214;
        throw NotFoundException.getNotFoundInstance();
        label176: i += arrayOfInt2[m];
        m++;
        break;
        label192: i++;
        break label109;
      }
      n += arrayOfInt2[i1];
    }
    label214: if (localStringBuffer.length() < 2)
      throw NotFoundException.getNotFoundInstance();
    char c2 = localStringBuffer.charAt(0);
    if (!arrayContains(STARTEND_ENCODING, c2))
      throw NotFoundException.getNotFoundInstance();
    for (int i3 = 1; ; i3++)
    {
      if (i3 >= localStringBuffer.length())
      {
        if (localStringBuffer.length() <= 6)
          break;
        localStringBuffer.deleteCharAt(-1 + localStringBuffer.length());
        localStringBuffer.deleteCharAt(0);
        float f1 = (arrayOfInt1[1] + arrayOfInt1[0]) / 2.0F;
        float f2 = (i + k) / 2.0F;
        String str = localStringBuffer.toString();
        ResultPoint[] arrayOfResultPoint = new ResultPoint[2];
        ResultPoint localResultPoint1 = new ResultPoint(f1, paramInt);
        arrayOfResultPoint[0] = localResultPoint1;
        ResultPoint localResultPoint2 = new ResultPoint(f2, paramInt);
        arrayOfResultPoint[1] = localResultPoint2;
        return new Result(str, null, arrayOfResultPoint, BarcodeFormat.CODABAR);
      }
      if ((localStringBuffer.charAt(i3) != c2) || (i3 + 1 == localStringBuffer.length()))
        continue;
      localStringBuffer.delete(i3 + 1, -1 + localStringBuffer.length());
      i3 = localStringBuffer.length();
    }
    throw NotFoundException.getNotFoundInstance();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.CodaBarReader
 * JD-Core Version:    0.6.0
 */