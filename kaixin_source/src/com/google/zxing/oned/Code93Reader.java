package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Hashtable;

public final class Code93Reader extends OneDReader
{
  private static final char[] ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".toCharArray();
  private static final String ALPHABET_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*";
  private static final int ASTERISK_ENCODING;
  private static final int[] CHARACTER_ENCODINGS = { 276, 328, 324, 322, 296, 292, 290, 336, 274, 266, 424, 420, 418, 404, 402, 394, 360, 356, 354, 308, 282, 344, 332, 326, 300, 278, 436, 434, 428, 422, 406, 410, 364, 358, 310, 314, 302, 468, 466, 458, 366, 374, 430, 294, 474, 470, 306, 350 };

  static
  {
    ASTERISK_ENCODING = CHARACTER_ENCODINGS[47];
  }

  private static void checkChecksums(StringBuffer paramStringBuffer)
    throws ChecksumException
  {
    int i = paramStringBuffer.length();
    checkOneChecksum(paramStringBuffer, i - 2, 20);
    checkOneChecksum(paramStringBuffer, i - 1, 15);
  }

  private static void checkOneChecksum(StringBuffer paramStringBuffer, int paramInt1, int paramInt2)
    throws ChecksumException
  {
    int i = 1;
    int j = 0;
    for (int k = paramInt1 - 1; ; k--)
    {
      if (k < 0)
      {
        if (paramStringBuffer.charAt(paramInt1) == ALPHABET[(j % 47)])
          break;
        throw ChecksumException.getChecksumInstance();
      }
      j += i * "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".indexOf(paramStringBuffer.charAt(k));
      i++;
      if (i <= paramInt2)
        continue;
      i = 1;
    }
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
    if ((c1 >= 'a') && (c1 <= 'd'))
    {
      k = paramStringBuffer.charAt(j + 1);
      c2 = '\000';
      switch (c1)
      {
      default:
        label92: localStringBuffer.append(c2);
        j++;
      case 'd':
      case 'a':
      case 'b':
      case 'c':
      }
    }
    while (true)
    {
      j++;
      break;
      if ((k >= 65) && (k <= 90))
      {
        c2 = (char)(k + 32);
        break label92;
      }
      throw FormatException.getFormatInstance();
      if ((k >= 65) && (k <= 90))
      {
        c2 = (char)(k - 64);
        break label92;
      }
      throw FormatException.getFormatInstance();
      if ((k >= 65) && (k <= 69))
      {
        c2 = (char)(k - 38);
        break label92;
      }
      if ((k >= 70) && (k <= 87))
      {
        c2 = (char)(k - 11);
        break label92;
      }
      throw FormatException.getFormatInstance();
      if ((k >= 65) && (k <= 79))
      {
        c2 = (char)(k - 32);
        break label92;
      }
      if (k == 90)
      {
        c2 = ':';
        break label92;
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
      arrayOfInt = new int[6];
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
      if (toPattern(arrayOfInt) == ASTERISK_ENCODING)
        return new int[] { m, i2 };
      m += arrayOfInt[0] + arrayOfInt[1];
      i3 = 2;
      label136: if (i3 >= i1)
      {
        arrayOfInt[(i1 - 2)] = 0;
        arrayOfInt[(i1 - 1)] = 0;
        k--;
        label162: arrayOfInt[k] = 1;
        if (n == 0)
          break label202;
      }
    }
    label202: for (int n = 0; ; n = 1)
    {
      break;
      arrayOfInt[(i3 - 2)] = arrayOfInt[i3];
      i3++;
      break label136;
      k++;
      break label162;
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

  private static int toPattern(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;
    int j = 0;
    int m;
    int n;
    for (int k = 0; ; k++)
    {
      if (k >= i)
      {
        m = 0;
        n = 0;
        if (n < i)
          break;
        return m;
      }
      j += paramArrayOfInt[k];
    }
    int i1 = 9 * (paramArrayOfInt[n] << 8) / j;
    int i2 = i1 >> 8;
    if ((i1 & 0xFF) > 127)
      i2++;
    if ((i2 < 1) || (i2 > 4))
      return -1;
    int i3;
    if ((n & 0x1) == 0)
    {
      i3 = 0;
      label98: if (i3 < i2);
    }
    while (true)
    {
      n++;
      break;
      m = 0x1 | m << 1;
      i3++;
      break label98;
      m <<= i2;
    }
  }

  public Result decodeRow(int paramInt, BitArray paramBitArray, Hashtable paramHashtable)
    throws NotFoundException, ChecksumException, FormatException
  {
    int[] arrayOfInt1 = findAsteriskPattern(paramBitArray);
    int i = arrayOfInt1[1];
    int j = paramBitArray.getSize();
    StringBuffer localStringBuffer;
    int[] arrayOfInt2;
    int k;
    while (true)
    {
      if ((i >= j) || (paramBitArray.get(i)))
      {
        localStringBuffer = new StringBuffer(20);
        arrayOfInt2 = new int[6];
        recordPattern(paramBitArray, i, arrayOfInt2);
        k = toPattern(arrayOfInt2);
        if (k >= 0)
          break;
        throw NotFoundException.getNotFoundInstance();
      }
      i++;
    }
    char c = patternToChar(k);
    localStringBuffer.append(c);
    int m = i;
    int n = 0;
    label103: if (n >= arrayOfInt2.length);
    while (true)
    {
      if ((i >= j) || (paramBitArray.get(i)))
      {
        if (c != '*')
          break;
        localStringBuffer.deleteCharAt(-1 + localStringBuffer.length());
        if ((i != j) && (paramBitArray.get(i)))
          break label189;
        throw NotFoundException.getNotFoundInstance();
        i += arrayOfInt2[n];
        n++;
        break label103;
      }
      i++;
    }
    label189: if (localStringBuffer.length() < 2)
      throw NotFoundException.getNotFoundInstance();
    checkChecksums(localStringBuffer);
    localStringBuffer.setLength(-2 + localStringBuffer.length());
    String str = decodeExtended(localStringBuffer);
    float f1 = (arrayOfInt1[1] + arrayOfInt1[0]) / 2.0F;
    float f2 = (i + m) / 2.0F;
    ResultPoint[] arrayOfResultPoint = new ResultPoint[2];
    ResultPoint localResultPoint1 = new ResultPoint(f1, paramInt);
    arrayOfResultPoint[0] = localResultPoint1;
    ResultPoint localResultPoint2 = new ResultPoint(f2, paramInt);
    arrayOfResultPoint[1] = localResultPoint2;
    return new Result(str, null, arrayOfResultPoint, BarcodeFormat.CODE_93);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.Code93Reader
 * JD-Core Version:    0.6.0
 */