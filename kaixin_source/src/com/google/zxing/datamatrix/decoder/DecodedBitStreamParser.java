package com.google.zxing.datamatrix.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import com.google.zxing.common.DecoderResult;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

final class DecodedBitStreamParser
{
  private static final int ANSIX12_ENCODE = 4;
  private static final int ASCII_ENCODE = 1;
  private static final int BASE256_ENCODE = 6;
  private static final char[] C40_BASIC_SET_CHARS = { 42, 42, 42, 32, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90 };
  private static final int C40_ENCODE = 2;
  private static final char[] C40_SHIFT2_SET_CHARS = { 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 58, 59, 60, 61, 62, 63, 64, 91, 92, 93, 94, 95 };
  private static final int EDIFACT_ENCODE = 5;
  private static final int PAD_ENCODE = 0;
  private static final char[] TEXT_BASIC_SET_CHARS = { 42, 42, 42, 32, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122 };
  private static final int TEXT_ENCODE = 3;
  private static final char[] TEXT_SHIFT3_SET_CHARS = { 39, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 123, 124, 125, 126, 127 };

  static DecoderResult decode(byte[] paramArrayOfByte)
    throws FormatException
  {
    BitSource localBitSource = new BitSource(paramArrayOfByte);
    StringBuffer localStringBuffer1 = new StringBuffer(100);
    StringBuffer localStringBuffer2 = new StringBuffer(0);
    Vector localVector = new Vector(1);
    int i = 1;
    while (i == 1)
    {
      i = decodeAsciiSegment(localBitSource, localStringBuffer1, localStringBuffer2);
      if ((i != 0) && (localBitSource.available() > 0))
        continue;
      if (localStringBuffer2.length() > 0)
        localStringBuffer1.append(localStringBuffer2.toString());
      String str = localStringBuffer1.toString();
      if (localVector.isEmpty())
        localVector = null;
      return new DecoderResult(paramArrayOfByte, str, localVector, null);
    }
    switch (i)
    {
    default:
      throw FormatException.getFormatInstance();
    case 2:
      decodeC40Segment(localBitSource, localStringBuffer1);
    case 3:
    case 4:
    case 5:
    case 6:
    }
    while (true)
    {
      i = 1;
      break;
      decodeTextSegment(localBitSource, localStringBuffer1);
      continue;
      decodeAnsiX12Segment(localBitSource, localStringBuffer1);
      continue;
      decodeEdifactSegment(localBitSource, localStringBuffer1);
      continue;
      decodeBase256Segment(localBitSource, localStringBuffer1, localVector);
    }
  }

  private static void decodeAnsiX12Segment(BitSource paramBitSource, StringBuffer paramStringBuffer)
    throws FormatException
  {
    int[] arrayOfInt = new int[3];
    int j;
    while (true)
    {
      if (paramBitSource.available() == 8);
      int i;
      do
      {
        return;
        i = paramBitSource.readBits(8);
      }
      while (i == 254);
      parseTwoBytes(i, paramBitSource.readBits(8), arrayOfInt);
      j = 0;
      if (j < 3)
        break;
      if (paramBitSource.available() <= 0)
        return;
    }
    int k = arrayOfInt[j];
    if (k == 0)
      paramStringBuffer.append('\r');
    while (true)
    {
      j++;
      break;
      if (k == 1)
      {
        paramStringBuffer.append('*');
        continue;
      }
      if (k == 2)
      {
        paramStringBuffer.append('>');
        continue;
      }
      if (k == 3)
      {
        paramStringBuffer.append(' ');
        continue;
      }
      if (k < 14)
      {
        paramStringBuffer.append((char)(k + 44));
        continue;
      }
      if (k >= 40)
        break label170;
      paramStringBuffer.append((char)(k + 51));
    }
    label170: throw FormatException.getFormatInstance();
  }

  private static int decodeAsciiSegment(BitSource paramBitSource, StringBuffer paramStringBuffer1, StringBuffer paramStringBuffer2)
    throws FormatException
  {
    int j;
    label215: 
    do
    {
      int i = 0;
      while (true)
      {
        j = paramBitSource.readBits(8);
        if (j == 0)
          throw FormatException.getFormatInstance();
        if (j <= 128)
        {
          if (i != 0)
            j += 128;
          paramStringBuffer1.append((char)(j - 1));
          return 1;
        }
        if (j == 129)
          return 0;
        if (j <= 229)
        {
          int k = j - 130;
          if (k < 10)
            paramStringBuffer1.append('0');
          paramStringBuffer1.append(k);
        }
        while (paramBitSource.available() <= 0)
        {
          return 1;
          if (j == 230)
            return 2;
          if (j == 231)
            return 6;
          if ((j == 232) || (j == 233) || (j == 234))
            continue;
          if (j == 235)
          {
            i = 1;
            continue;
          }
          if (j == 236)
          {
            paramStringBuffer1.append("[)>\03605\035");
            paramStringBuffer2.insert(0, "\036\004");
            continue;
          }
          if (j != 237)
            break label215;
          paramStringBuffer1.append("[)>\03606\035");
          paramStringBuffer2.insert(0, "\036\004");
        }
      }
      if (j == 238)
        return 4;
      if (j == 239)
        return 3;
      if (j == 240)
        return 5;
    }
    while ((j == 241) || (j < 242) || ((j == 254) && (paramBitSource.available() == 0)));
    throw FormatException.getFormatInstance();
  }

  private static void decodeBase256Segment(BitSource paramBitSource, StringBuffer paramStringBuffer, Vector paramVector)
    throws FormatException
  {
    int i = paramBitSource.readBits(8);
    int j = 2 + 1;
    int k = unrandomize255State(i, 2);
    int i2;
    int i1;
    if (k == 0)
    {
      i2 = paramBitSource.available() / 8;
      i1 = j;
    }
    while (i2 < 0)
    {
      throw FormatException.getFormatInstance();
      if (k < 250)
      {
        i2 = k;
        i1 = j;
        continue;
      }
      int m = 250 * (k - 249);
      int n = paramBitSource.readBits(8);
      i1 = j + 1;
      i2 = m + unrandomize255State(n, j);
    }
    byte[] arrayOfByte = new byte[i2];
    int i3 = 0;
    int i4 = i1;
    while (true)
    {
      if (i3 >= i2)
        paramVector.addElement(arrayOfByte);
      try
      {
        paramStringBuffer.append(new String(arrayOfByte, "ISO8859_1"));
        return;
        if (paramBitSource.available() < 8)
          throw FormatException.getFormatInstance();
        int i5 = paramBitSource.readBits(8);
        int i6 = i4 + 1;
        arrayOfByte[i3] = unrandomize255State(i5, i4);
        i3++;
        i4 = i6;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
      }
    }
    throw new RuntimeException("Platform does not support required encoding: " + localUnsupportedEncodingException);
  }

  private static void decodeC40Segment(BitSource paramBitSource, StringBuffer paramStringBuffer)
    throws FormatException
  {
    int i = 0;
    int[] arrayOfInt = new int[3];
    int k;
    int m;
    while (true)
    {
      if (paramBitSource.available() == 8);
      int j;
      do
      {
        return;
        j = paramBitSource.readBits(8);
      }
      while (j == 254);
      parseTwoBytes(j, paramBitSource.readBits(8), arrayOfInt);
      k = 0;
      m = 0;
      if (m < 3)
        break;
      if (paramBitSource.available() <= 0)
        return;
    }
    int n = arrayOfInt[m];
    switch (k)
    {
    default:
      throw FormatException.getFormatInstance();
    case 0:
      if (n < 3)
        k = n + 1;
      while (true)
      {
        m++;
        break;
        if (n >= C40_BASIC_SET_CHARS.length)
          break label174;
        char c2 = C40_BASIC_SET_CHARS[n];
        if (i != 0)
        {
          paramStringBuffer.append((char)(c2 + ''));
          i = 0;
          continue;
        }
        paramStringBuffer.append(c2);
      }
      throw FormatException.getFormatInstance();
    case 1:
      if (i != 0)
      {
        paramStringBuffer.append((char)(n + 128));
        i = 0;
      }
      while (true)
      {
        k = 0;
        break;
        paramStringBuffer.append(n);
      }
    case 2:
      label174: char c1;
      if (n < C40_SHIFT2_SET_CHARS.length)
      {
        c1 = C40_SHIFT2_SET_CHARS[n];
        if (i != 0)
        {
          paramStringBuffer.append((char)(c1 + ''));
          i = 0;
        }
      }
      while (true)
      {
        k = 0;
        break;
        paramStringBuffer.append(c1);
        continue;
        if (n == 27)
          throw FormatException.getFormatInstance();
        if (n != 30)
          break label286;
        i = 1;
      }
      label286: throw FormatException.getFormatInstance();
    case 3:
    }
    if (i != 0)
    {
      paramStringBuffer.append((char)(n + 224));
      i = 0;
    }
    while (true)
    {
      k = 0;
      break;
      paramStringBuffer.append((char)(n + 96));
    }
  }

  private static void decodeEdifactSegment(BitSource paramBitSource, StringBuffer paramStringBuffer)
  {
    int i = 0;
    if (paramBitSource.available() <= 16)
      label11: return;
    for (int j = 0; ; j++)
    {
      if (j >= 4)
      {
        if (i != 0)
          break label11;
        if (paramBitSource.available() > 0)
          break;
        return;
      }
      int k = paramBitSource.readBits(6);
      if (k == 31)
        i = 1;
      if (i != 0)
        continue;
      if ((k & 0x20) == 0)
        k |= 64;
      paramStringBuffer.append(k);
    }
  }

  private static void decodeTextSegment(BitSource paramBitSource, StringBuffer paramStringBuffer)
    throws FormatException
  {
    int i = 0;
    int[] arrayOfInt = new int[3];
    int j = 0;
    int m;
    while (true)
    {
      if (paramBitSource.available() == 8);
      int k;
      do
      {
        return;
        k = paramBitSource.readBits(8);
      }
      while (k == 254);
      parseTwoBytes(k, paramBitSource.readBits(8), arrayOfInt);
      m = 0;
      if (m < 3)
        break;
      if (paramBitSource.available() <= 0)
        return;
    }
    int n = arrayOfInt[m];
    switch (j)
    {
    default:
      throw FormatException.getFormatInstance();
    case 0:
      if (n < 3)
        j = n + 1;
      while (true)
      {
        m++;
        break;
        if (n >= TEXT_BASIC_SET_CHARS.length)
          break label174;
        char c3 = TEXT_BASIC_SET_CHARS[n];
        if (i != 0)
        {
          paramStringBuffer.append((char)(c3 + ''));
          i = 0;
          continue;
        }
        paramStringBuffer.append(c3);
      }
      throw FormatException.getFormatInstance();
    case 1:
      if (i != 0)
      {
        paramStringBuffer.append((char)(n + 128));
        i = 0;
      }
      while (true)
      {
        j = 0;
        break;
        paramStringBuffer.append(n);
      }
    case 2:
      label174: char c2;
      if (n < C40_SHIFT2_SET_CHARS.length)
      {
        c2 = C40_SHIFT2_SET_CHARS[n];
        if (i != 0)
        {
          paramStringBuffer.append((char)(c2 + ''));
          i = 0;
        }
      }
      while (true)
      {
        j = 0;
        break;
        paramStringBuffer.append(c2);
        continue;
        if (n == 27)
          throw FormatException.getFormatInstance();
        if (n != 30)
          break label286;
        i = 1;
      }
      label286: throw FormatException.getFormatInstance();
    case 3:
    }
    if (n < TEXT_SHIFT3_SET_CHARS.length)
    {
      char c1 = TEXT_SHIFT3_SET_CHARS[n];
      if (i != 0)
      {
        paramStringBuffer.append((char)(c1 + ''));
        i = 0;
      }
      while (true)
      {
        j = 0;
        break;
        paramStringBuffer.append(c1);
      }
    }
    throw FormatException.getFormatInstance();
  }

  private static void parseTwoBytes(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    int i = -1 + (paramInt2 + (paramInt1 << 8));
    int j = i / 1600;
    paramArrayOfInt[0] = j;
    int k = i - j * 1600;
    int m = k / 40;
    paramArrayOfInt[1] = m;
    paramArrayOfInt[2] = (k - m * 40);
  }

  private static byte unrandomize255State(int paramInt1, int paramInt2)
  {
    int i = paramInt1 - (1 + paramInt2 * 149 % 255);
    if (i >= 0);
    while (true)
    {
      return (byte)i;
      i += 256;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.datamatrix.decoder.DecodedBitStreamParser
 * JD-Core Version:    0.6.0
 */