package com.google.zxing.pdf417.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.DecoderResult;

final class DecodedBitStreamParser
{
  private static final int AL = 28;
  private static final int ALPHA = 0;
  private static final int ALPHA_SHIFT = 4;
  private static final int AS = 27;
  private static final int BEGIN_MACRO_PDF417_CONTROL_BLOCK = 928;
  private static final int BEGIN_MACRO_PDF417_OPTIONAL_FIELD = 923;
  private static final int BYTE_COMPACTION_MODE_LATCH = 901;
  private static final int BYTE_COMPACTION_MODE_LATCH_6 = 924;
  private static final String[] EXP900;
  private static final int LL = 27;
  private static final int LOWER = 1;
  private static final int MACRO_PDF417_TERMINATOR = 922;
  private static final int MAX_NUMERIC_CODEWORDS = 15;
  private static final int MIXED = 2;
  private static final char[] MIXED_CHARS;
  private static final int ML = 28;
  private static final int MODE_SHIFT_TO_BYTE_COMPACTION_MODE = 913;
  private static final int NUMERIC_COMPACTION_MODE_LATCH = 902;
  private static final int PAL = 29;
  private static final int PL = 25;
  private static final int PS = 29;
  private static final int PUNCT = 3;
  private static final char[] PUNCT_CHARS = { 59, 60, 62, 64, 91, 92, 125, 95, 96, 126, 33, 13, 9, 44, 58, 10, 45, 46, 36, 47, 34, 124, 42, 40, 41, 63, 123, 125, 39 };
  private static final int PUNCT_SHIFT = 5;
  private static final int TEXT_COMPACTION_MODE_LATCH = 900;

  static
  {
    MIXED_CHARS = new char[] { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 38, 13, 9, 44, 58, 35, 45, 46, 36, 47, 43, 37, 42, 61, 94 };
    EXP900 = new String[] { "000000000000000000000000000000000000000000001", "000000000000000000000000000000000000000000900", "000000000000000000000000000000000000000810000", "000000000000000000000000000000000000729000000", "000000000000000000000000000000000656100000000", "000000000000000000000000000000590490000000000", "000000000000000000000000000531441000000000000", "000000000000000000000000478296900000000000000", "000000000000000000000430467210000000000000000", "000000000000000000387420489000000000000000000", "000000000000000348678440100000000000000000000", "000000000000313810596090000000000000000000000", "000000000282429536481000000000000000000000000", "000000254186582832900000000000000000000000000", "000228767924549610000000000000000000000000000", "205891132094649000000000000000000000000000000" };
  }

  private static StringBuffer add(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer1 = new StringBuffer(5);
    StringBuffer localStringBuffer2 = new StringBuffer(5);
    StringBuffer localStringBuffer3 = new StringBuffer(paramString1.length());
    int i = 0;
    int j;
    if (i >= paramString1.length())
      j = 0;
    for (int k = -3 + paramString1.length(); ; k -= 3)
    {
      if (k <= -1)
      {
        return localStringBuffer3;
        localStringBuffer3.append('0');
        i++;
        break;
      }
      localStringBuffer1.setLength(0);
      localStringBuffer1.append(paramString1.charAt(k));
      localStringBuffer1.append(paramString1.charAt(k + 1));
      localStringBuffer1.append(paramString1.charAt(k + 2));
      localStringBuffer2.setLength(0);
      localStringBuffer2.append(paramString2.charAt(k));
      localStringBuffer2.append(paramString2.charAt(k + 1));
      localStringBuffer2.append(paramString2.charAt(k + 2));
      int m = Integer.parseInt(localStringBuffer1.toString());
      int n = Integer.parseInt(localStringBuffer2.toString());
      int i1 = (j + (m + n)) % 1000;
      j = (j + (m + n)) / 1000;
      localStringBuffer3.setCharAt(k + 2, (char)(48 + i1 % 10));
      localStringBuffer3.setCharAt(k + 1, (char)(48 + i1 / 10 % 10));
      localStringBuffer3.setCharAt(k, (char)(48 + i1 / 100));
    }
  }

  private static int byteCompaction(int paramInt1, int[] paramArrayOfInt, int paramInt2, StringBuffer paramStringBuffer)
  {
    int i2;
    long l2;
    char[] arrayOfChar2;
    int[] arrayOfInt;
    int i3;
    if (paramInt1 == 901)
    {
      i2 = 0;
      l2 = 0L;
      arrayOfChar2 = new char[6];
      arrayOfInt = new int[6];
      i3 = 0;
    }
    label52: label59: label319: label452: label456: label458: label462: 
    while (true)
    {
      int i4 = paramArrayOfInt[0];
      int i5;
      int i6;
      int i7;
      if ((paramInt2 >= i4) || (i3 != 0))
      {
        i5 = 5 * (i2 / 5);
        if (i5 >= i2)
          return paramInt2;
      }
      else
      {
        i6 = paramInt2 + 1;
        i7 = paramArrayOfInt[paramInt2];
        if (i7 < 900)
        {
          arrayOfInt[i2] = i7;
          i2++;
          l2 = 900L * l2 + i7;
          paramInt2 = i6;
        }
      }
      while (true)
      {
        label104: if ((i2 % 5 != 0) || (i2 <= 0))
          break label462;
        for (int i8 = 0; ; i8++)
        {
          if (i8 >= 6)
          {
            paramStringBuffer.append(arrayOfChar2);
            i2 = 0;
            break;
            if ((i7 != 900) && (i7 != 901) && (i7 != 902) && (i7 != 924) && (i7 != 928) && (i7 != 923) && (i7 != 922))
              break label458;
            paramInt2 = i6 - 1;
            i3 = 1;
            break label104;
          }
          arrayOfChar2[(5 - i8)] = (char)(int)(l2 % 256L);
          l2 >>= 8;
        }
        paramStringBuffer.append((char)arrayOfInt[i5]);
        i5++;
        break label52;
        if (paramInt1 != 924)
          break label59;
        int i = 0;
        long l1 = 0L;
        int j = 0;
        while (true)
        {
          int k = paramArrayOfInt[0];
          if ((paramInt2 >= k) || (j != 0))
            break;
          int m = paramInt2 + 1;
          int n = paramArrayOfInt[paramInt2];
          if (n < 900)
          {
            i++;
            l1 = 900L * l1 + n;
            paramInt2 = m;
          }
          while (true)
          {
            if ((i % 5 != 0) || (i <= 0))
              break label456;
            char[] arrayOfChar1 = new char[6];
            for (int i1 = 0; ; i1++)
            {
              if (i1 >= 6)
              {
                paramStringBuffer.append(arrayOfChar1);
                break;
                if ((n != 900) && (n != 901) && (n != 902) && (n != 924) && (n != 928) && (n != 923) && (n != 922))
                  break label452;
                paramInt2 = m - 1;
                j = 1;
                break label319;
              }
              arrayOfChar1[(5 - i1)] = (char)(int)(0xFF & l1);
              l1 >>= 8;
            }
            paramInt2 = m;
          }
        }
        paramInt2 = i6;
      }
    }
  }

  static DecoderResult decode(int[] paramArrayOfInt)
    throws FormatException
  {
    StringBuffer localStringBuffer = new StringBuffer(100);
    int i = 1 + 1;
    int j = paramArrayOfInt[1];
    int k = i;
    if (k >= paramArrayOfInt[0])
      return new DecoderResult(null, localStringBuffer.toString(), null, null);
    int m;
    switch (j)
    {
    default:
      m = textCompaction(paramArrayOfInt, k - 1, localStringBuffer);
    case 900:
    case 901:
    case 902:
    case 913:
    case 924:
    }
    while (true)
    {
      if (m >= paramArrayOfInt.length)
        break label195;
      int n = m + 1;
      j = paramArrayOfInt[m];
      k = n;
      break;
      m = textCompaction(paramArrayOfInt, k, localStringBuffer);
      continue;
      m = byteCompaction(j, paramArrayOfInt, k, localStringBuffer);
      continue;
      m = numericCompaction(paramArrayOfInt, k, localStringBuffer);
      continue;
      m = byteCompaction(j, paramArrayOfInt, k, localStringBuffer);
      continue;
      m = byteCompaction(j, paramArrayOfInt, k, localStringBuffer);
    }
    label195: throw FormatException.getFormatInstance();
  }

  private static String decodeBase900toBase10(int[] paramArrayOfInt, int paramInt)
  {
    Object localObject = null;
    int i = 0;
    if (i >= paramInt);
    label115: for (int j = 0; ; j++)
    {
      int k = ((StringBuffer)localObject).length();
      String str = null;
      if (j >= k);
      while (true)
      {
        if (str == null)
          str = ((StringBuffer)localObject).toString();
        return str;
        StringBuffer localStringBuffer = multiply(EXP900[(-1 + (paramInt - i))], paramArrayOfInt[i]);
        if (localObject == null);
        for (localObject = localStringBuffer; ; localObject = add(((StringBuffer)localObject).toString(), localStringBuffer.toString()))
        {
          i++;
          break;
        }
        if (((StringBuffer)localObject).charAt(j) != '1')
          break label115;
        str = ((StringBuffer)localObject).toString().substring(j + 1);
      }
    }
  }

  private static void decodeTextCompaction(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt, StringBuffer paramStringBuffer)
  {
    int i = 0;
    int j = 0;
    int k = 0;
    if (k >= paramInt)
      return;
    int m = paramArrayOfInt1[k];
    char c = '\000';
    switch (i)
    {
    default:
    case 0:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    }
    while (true)
    {
      if (c != 0)
        paramStringBuffer.append(c);
      k++;
      break;
      if (m < 26)
      {
        c = (char)(m + 65);
        continue;
      }
      if (m == 26)
      {
        c = ' ';
        continue;
      }
      if (m == 27)
      {
        i = 1;
        c = '\000';
        continue;
      }
      if (m == 28)
      {
        i = 2;
        c = '\000';
        continue;
      }
      if (m == 29)
      {
        j = i;
        i = 5;
        c = '\000';
        continue;
      }
      c = '\000';
      if (m != 913)
        continue;
      paramStringBuffer.append((char)paramArrayOfInt2[k]);
      c = '\000';
      continue;
      if (m < 26)
      {
        c = (char)(m + 97);
        continue;
      }
      if (m == 26)
      {
        c = ' ';
        continue;
      }
      if (m == 27)
      {
        j = i;
        i = 4;
        c = '\000';
        continue;
      }
      if (m == 28)
      {
        i = 2;
        c = '\000';
        continue;
      }
      if (m == 29)
      {
        j = i;
        i = 5;
        c = '\000';
        continue;
      }
      c = '\000';
      if (m != 913)
        continue;
      paramStringBuffer.append((char)paramArrayOfInt2[k]);
      c = '\000';
      continue;
      if (m < 25)
      {
        c = MIXED_CHARS[m];
        continue;
      }
      if (m == 25)
      {
        i = 3;
        c = '\000';
        continue;
      }
      if (m == 26)
      {
        c = ' ';
        continue;
      }
      if (m == 27)
      {
        i = 1;
        c = '\000';
        continue;
      }
      if (m == 28)
      {
        c = '\000';
        i = 0;
        continue;
      }
      if (m == 29)
      {
        j = i;
        i = 5;
        c = '\000';
        continue;
      }
      c = '\000';
      if (m != 913)
        continue;
      paramStringBuffer.append((char)paramArrayOfInt2[k]);
      c = '\000';
      continue;
      if (m < 29)
      {
        c = PUNCT_CHARS[m];
        continue;
      }
      if (m == 29)
      {
        c = '\000';
        i = 0;
        continue;
      }
      c = '\000';
      if (m != 913)
        continue;
      paramStringBuffer.append((char)paramArrayOfInt2[k]);
      c = '\000';
      continue;
      i = j;
      if (m < 26)
      {
        c = (char)(m + 65);
        continue;
      }
      c = '\000';
      if (m != 26)
        continue;
      c = ' ';
      continue;
      i = j;
      if (m < 29)
      {
        c = PUNCT_CHARS[m];
        continue;
      }
      c = '\000';
      if (m != 29)
        continue;
      c = '\000';
      i = 0;
    }
  }

  private static StringBuffer multiply(String paramString, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramString.length());
    int i = 0;
    int j;
    int n;
    label46: int i1;
    if (i >= paramString.length())
    {
      j = paramInt / 100;
      int k = paramInt / 10 % 10;
      int m = paramInt % 10;
      n = 0;
      if (n < m)
        break label88;
      i1 = 0;
      label56: if (i1 < k)
        break label103;
    }
    for (int i2 = 0; ; i2++)
    {
      if (i2 >= j)
      {
        return localStringBuffer;
        localStringBuffer.append('0');
        i++;
        break;
        label88: localStringBuffer = add(localStringBuffer.toString(), paramString);
        n++;
        break label46;
        label103: localStringBuffer = add(localStringBuffer.toString(), (paramString + '0').substring(1));
        i1++;
        break label56;
      }
      localStringBuffer = add(localStringBuffer.toString(), (paramString + "00").substring(2));
    }
  }

  private static int numericCompaction(int[] paramArrayOfInt, int paramInt, StringBuffer paramStringBuffer)
  {
    int i = 0;
    int j = 0;
    int[] arrayOfInt = new int[15];
    label165: 
    while (true)
    {
      if ((paramInt >= paramArrayOfInt[0]) || (j != 0))
        return paramInt;
      int k = paramInt + 1;
      int m = paramArrayOfInt[paramInt];
      if (k == paramArrayOfInt[0])
        j = 1;
      if (m < 900)
      {
        arrayOfInt[i] = m;
        i++;
        paramInt = k;
      }
      while (true)
      {
        if ((i % 15 != 0) && (m != 902) && (j == 0))
          break label165;
        paramStringBuffer.append(decodeBase900toBase10(arrayOfInt, i));
        i = 0;
        break;
        if ((m == 900) || (m == 901) || (m == 924) || (m == 928) || (m == 923) || (m == 922))
        {
          paramInt = k - 1;
          j = 1;
          continue;
        }
        paramInt = k;
      }
    }
  }

  private static int textCompaction(int[] paramArrayOfInt, int paramInt, StringBuffer paramStringBuffer)
  {
    int[] arrayOfInt1 = new int[paramArrayOfInt[0] << 1];
    int[] arrayOfInt2 = new int[paramArrayOfInt[0] << 1];
    int i = 0;
    int j = 0;
    while (true)
    {
      if ((paramInt >= paramArrayOfInt[0]) || (j != 0))
      {
        decodeTextCompaction(arrayOfInt1, arrayOfInt2, i, paramStringBuffer);
        return paramInt;
      }
      int k = paramInt + 1;
      int m = paramArrayOfInt[paramInt];
      if (m < 900)
      {
        arrayOfInt1[i] = (m / 30);
        arrayOfInt1[(i + 1)] = (m % 30);
        i += 2;
        paramInt = k;
        continue;
      }
      switch (m)
      {
      default:
        paramInt = k;
        break;
      case 900:
        paramInt = k - 1;
        j = 1;
        break;
      case 901:
        paramInt = k - 1;
        j = 1;
        break;
      case 902:
        paramInt = k - 1;
        j = 1;
        break;
      case 913:
        arrayOfInt1[i] = 913;
        paramInt = k + 1;
        arrayOfInt2[i] = paramArrayOfInt[k];
        i++;
        break;
      case 924:
        paramInt = k - 1;
        j = 1;
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.pdf417.decoder.DecodedBitStreamParser
 * JD-Core Version:    0.6.0
 */