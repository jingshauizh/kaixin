package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Hashtable;

public final class Code39Reader extends OneDReader
{
  private static final char[] ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".toCharArray();
  static final String ALPHABET_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%";
  private static final int ASTERISK_ENCODING;
  static final int[] CHARACTER_ENCODINGS = { 52, 289, 97, 352, 49, 304, 112, 37, 292, 100, 265, 73, 328, 25, 280, 88, 13, 268, 76, 28, 259, 67, 322, 19, 274, 82, 7, 262, 70, 22, 385, 193, 448, 145, 400, 208, 133, 388, 196, 148, 168, 162, 138, 42 };
  private final boolean extendedMode;
  private final boolean usingCheckDigit;

  static
  {
    ASTERISK_ENCODING = CHARACTER_ENCODINGS[39];
  }

  public Code39Reader()
  {
    this.usingCheckDigit = false;
    this.extendedMode = false;
  }

  public Code39Reader(boolean paramBoolean)
  {
    this.usingCheckDigit = paramBoolean;
    this.extendedMode = false;
  }

  public Code39Reader(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.usingCheckDigit = paramBoolean1;
    this.extendedMode = paramBoolean2;
  }

  private static String decodeExtended(StringBuffer paramStringBuffer)
    throws FormatException
  {
    int i = paramStringBuffer.length();
    StringBuffer localStringBuffer = new StringBuffer(i);
    int j = 0;
    if (j >= i)
      return localStringBuffer.toString();
    char c1 = paramStringBuffer.charAt(j);
    int k;
    char c2;
    if ((c1 == '+') || (c1 == '$') || (c1 == '%') || (c1 == '/'))
    {
      k = paramStringBuffer.charAt(j + 1);
      c2 = '\000';
      switch (c1)
      {
      default:
        label116: localStringBuffer.append(c2);
        j++;
      case '+':
      case '$':
      case '%':
      case '/':
      }
    }
    while (true)
    {
      j++;
      break;
      if ((k >= 65) && (k <= 90))
      {
        c2 = (char)(k + 32);
        break label116;
      }
      throw FormatException.getFormatInstance();
      if ((k >= 65) && (k <= 90))
      {
        c2 = (char)(k - 64);
        break label116;
      }
      throw FormatException.getFormatInstance();
      if ((k >= 65) && (k <= 69))
      {
        c2 = (char)(k - 38);
        break label116;
      }
      if ((k >= 70) && (k <= 87))
      {
        c2 = (char)(k - 11);
        break label116;
      }
      throw FormatException.getFormatInstance();
      if ((k >= 65) && (k <= 79))
      {
        c2 = (char)(k - 32);
        break label116;
      }
      if (k == 90)
      {
        c2 = ':';
        break label116;
      }
      throw FormatException.getFormatInstance();
      localStringBuffer.append(c1);
    }
  }

  private static int[] findAsteriskPattern(BitArray paramBitArray)
    throws NotFoundException
  {
    int i = paramBitArray.getSize();
    int j = 0;
    label12: int k;
    int[] arrayOfInt;
    int m;
    int i1;
    if (j >= i)
    {
      k = 0;
      arrayOfInt = new int[9];
      m = j;
      n = 0;
      i1 = arrayOfInt.length;
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
      arrayOfInt[k] = (1 + arrayOfInt[k]);
    }
    label86: int i3;
    if (k == i1 - 1)
    {
      if ((toNarrowWidePattern(arrayOfInt) == ASTERISK_ENCODING) && (paramBitArray.isRange(Math.max(0, m - (i2 - m) / 2), m, false)))
        return new int[] { m, i2 };
      m += arrayOfInt[0] + arrayOfInt[1];
      i3 = 2;
      label160: if (i3 >= i1)
      {
        arrayOfInt[(i1 - 2)] = 0;
        arrayOfInt[(i1 - 1)] = 0;
        k--;
        label186: arrayOfInt[k] = 1;
        if (n == 0)
          break label226;
      }
    }
    label226: for (int n = 0; ; n = 1)
    {
      break;
      arrayOfInt[(i3 - 2)] = arrayOfInt[i3];
      i3++;
      break label160;
      k++;
      break label186;
    }
  }

  private static char patternToChar(int paramInt)
    throws NotFoundException
  {
    for (int i = 0; ; i++)
    {
      if (i >= CHARACTER_ENCODINGS.length)
        throw NotFoundException.getNotFoundInstance();
      if (CHARACTER_ENCODINGS[i] == paramInt)
        return ALPHABET[i];
    }
  }

  private static int toNarrowWidePattern(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;
    int j = 0;
    int i1;
    label31: label87: 
    do
    {
      int k = 2147483647;
      int m = 0;
      int i2;
      int i3;
      int i4;
      if (m >= i)
      {
        j = k;
        i1 = 0;
        i2 = 0;
        i3 = 0;
        i4 = 0;
        if (i4 < i)
          break label87;
        if (i1 != 3)
          continue;
      }
      for (int i6 = 0; ; i6++)
      {
        if ((i6 >= i) || (i1 <= 0))
        {
          return i3;
          int n = paramArrayOfInt[m];
          if ((n < k) && (n > j))
            k = n;
          m++;
          break;
          int i5 = paramArrayOfInt[i4];
          if (paramArrayOfInt[i4] > j)
          {
            i3 |= 1 << i - 1 - i4;
            i1++;
            i2 += i5;
          }
          i4++;
          break label31;
        }
        int i7 = paramArrayOfInt[i6];
        if (paramArrayOfInt[i6] <= j)
          continue;
        i1--;
        if (i7 << 1 >= i2)
          return -1;
      }
    }
    while (i1 > 3);
    return -1;
  }

  public Result decodeRow(int paramInt, BitArray paramBitArray, Hashtable paramHashtable)
    throws NotFoundException, ChecksumException, FormatException
  {
    int[] arrayOfInt1 = findAsteriskPattern(paramBitArray);
    int i = arrayOfInt1[1];
    int j = paramBitArray.getSize();
    StringBuffer localStringBuffer;
    int[] arrayOfInt2;
    if ((i >= j) || (paramBitArray.get(i)))
    {
      localStringBuffer = new StringBuffer(20);
      arrayOfInt2 = new int[9];
    }
    char c;
    int m;
    int n;
    label111: 
    do
    {
      recordPattern(paramBitArray, i, arrayOfInt2);
      int k = toNarrowWidePattern(arrayOfInt2);
      if (k < 0)
      {
        throw NotFoundException.getNotFoundInstance();
        i++;
        break;
      }
      c = patternToChar(k);
      localStringBuffer.append(c);
      m = i;
      n = 0;
      if (n < arrayOfInt2.length)
        break label191;
      if ((i < j) && (!paramBitArray.get(i)))
        break label207;
    }
    while (c != '*');
    localStringBuffer.deleteCharAt(-1 + localStringBuffer.length());
    int i1 = 0;
    for (int i2 = 0; ; i2++)
    {
      if (i2 >= arrayOfInt2.length)
      {
        int i3 = i - m - i1;
        if ((i == j) || (i3 / 2 >= i1))
          break label229;
        throw NotFoundException.getNotFoundInstance();
        label191: i += arrayOfInt2[n];
        n++;
        break;
        label207: i++;
        break label111;
      }
      i1 += arrayOfInt2[i2];
    }
    label229: if (this.usingCheckDigit)
    {
      int i4 = -1 + localStringBuffer.length();
      int i5 = 0;
      for (int i6 = 0; ; i6++)
      {
        if (i6 >= i4)
        {
          if (localStringBuffer.charAt(i4) == ALPHABET[(i5 % 43)])
            break;
          throw ChecksumException.getChecksumInstance();
        }
        i5 += "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(localStringBuffer.charAt(i6));
      }
      localStringBuffer.deleteCharAt(i4);
    }
    if (localStringBuffer.length() == 0)
      throw NotFoundException.getNotFoundInstance();
    if (this.extendedMode);
    for (String str = decodeExtended(localStringBuffer); ; str = localStringBuffer.toString())
    {
      float f1 = (arrayOfInt1[1] + arrayOfInt1[0]) / 2.0F;
      float f2 = (i + m) / 2.0F;
      ResultPoint[] arrayOfResultPoint = new ResultPoint[2];
      ResultPoint localResultPoint1 = new ResultPoint(f1, paramInt);
      arrayOfResultPoint[0] = localResultPoint1;
      ResultPoint localResultPoint2 = new ResultPoint(f2, paramInt);
      arrayOfResultPoint[1] = localResultPoint2;
      Result localResult = new Result(str, null, arrayOfResultPoint, BarcodeFormat.CODE_39);
      return localResult;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.Code39Reader
 * JD-Core Version:    0.6.0
 */