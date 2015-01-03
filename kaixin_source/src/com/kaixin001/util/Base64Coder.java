package com.kaixin001.util;

public class Base64Coder
{
  private static final char[] map1;
  private static final byte[] map2;
  private static final String systemLineSeparator = System.getProperty("line.separator");

  static
  {
    map1 = new char[64];
    int i = 65;
    int j = 0;
    int m;
    label30: int i1;
    label41: int i4;
    if (i > 90)
    {
      m = 97;
      if (m <= 122)
        break label134;
      i1 = 48;
      if (i1 <= 57)
        break label163;
      char[] arrayOfChar4 = map1;
      int i3 = j + 1;
      arrayOfChar4[j] = '+';
      char[] arrayOfChar5 = map1;
      (i3 + 1);
      arrayOfChar5[i3] = '/';
      map2 = new byte[''];
      i4 = 0;
      label92: if (i4 < map2.length)
        break label192;
    }
    for (int i5 = 0; ; i5++)
    {
      if (i5 >= 64)
      {
        return;
        char[] arrayOfChar1 = map1;
        int k = j + 1;
        arrayOfChar1[j] = i;
        i = (char)(i + 1);
        j = k;
        break;
        label134: char[] arrayOfChar2 = map1;
        int n = j + 1;
        arrayOfChar2[j] = m;
        m = (char)(m + 1);
        j = n;
        break label30;
        label163: char[] arrayOfChar3 = map1;
        int i2 = j + 1;
        arrayOfChar3[j] = i1;
        i1 = (char)(i1 + 1);
        j = i2;
        break label41;
        label192: map2[i4] = -1;
        i4++;
        break label92;
      }
      map2[map1[i5]] = (byte)i5;
    }
  }

  public static byte[] decode(String paramString)
  {
    return decode(paramString.toCharArray());
  }

  public static byte[] decode(char[] paramArrayOfChar)
  {
    return decode(paramArrayOfChar, 0, paramArrayOfChar.length);
  }

  public static byte[] decode(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    if (paramInt2 % 4 != 0)
      throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
    do
      paramInt2--;
    while ((paramInt2 > 0) && (paramArrayOfChar[(-1 + (paramInt1 + paramInt2))] == '='));
    int i = paramInt2 * 3 / 4;
    byte[] arrayOfByte = new byte[i];
    int j = paramInt1 + paramInt2;
    int k = 0;
    int m = paramInt1;
    if (m >= j)
      return arrayOfByte;
    int n = m + 1;
    int i1 = paramArrayOfChar[m];
    int i2 = n + 1;
    int i3 = paramArrayOfChar[n];
    int i4;
    int i6;
    int i5;
    if (i2 < j)
    {
      int i16 = i2 + 1;
      i4 = paramArrayOfChar[i2];
      i2 = i16;
      if (i2 >= j)
        break label178;
      i6 = i2 + 1;
      i5 = paramArrayOfChar[i2];
    }
    while (true)
    {
      if ((i1 <= 127) && (i3 <= 127) && (i4 <= 127) && (i5 <= 127))
        break label189;
      throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
      i4 = 65;
      break;
      label178: i5 = 65;
      i6 = i2;
    }
    label189: int i7 = map2[i1];
    int i8 = map2[i3];
    int i9 = map2[i4];
    int i10 = map2[i5];
    if ((i7 < 0) || (i8 < 0) || (i9 < 0) || (i10 < 0))
      throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
    int i11 = i7 << 2 | i8 >>> 4;
    int i12 = (i8 & 0xF) << 4 | i9 >>> 2;
    int i13 = i10 | (i9 & 0x3) << 6;
    int i14 = k + 1;
    arrayOfByte[k] = (byte)i11;
    if (i14 < i)
    {
      k = i14 + 1;
      arrayOfByte[i14] = (byte)i12;
    }
    while (true)
    {
      if (k < i)
      {
        int i15 = k + 1;
        arrayOfByte[k] = (byte)i13;
        k = i15;
        m = i6;
        break;
      }
      m = i6;
      break;
      k = i14;
    }
  }

  public static byte[] decodeLines(String paramString)
  {
    char[] arrayOfChar = new char[paramString.length()];
    int i = 0;
    for (int j = 0; ; j++)
    {
      if (j >= paramString.length())
        return decode(arrayOfChar, 0, i);
      int k = paramString.charAt(j);
      if ((k == 32) || (k == 13) || (k == 10) || (k == 9))
        continue;
      int m = i + 1;
      arrayOfChar[i] = k;
      i = m;
    }
  }

  public static String decodeString(String paramString)
  {
    return new String(decode(paramString));
  }

  public static char[] encode(byte[] paramArrayOfByte)
  {
    return encode(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public static char[] encode(byte[] paramArrayOfByte, int paramInt)
  {
    return encode(paramArrayOfByte, 0, paramInt);
  }

  public static char[] encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = (2 + paramInt2 * 4) / 3;
    char[] arrayOfChar = new char[4 * ((paramInt2 + 2) / 3)];
    int j = paramInt1 + paramInt2;
    int k = 0;
    int m = paramInt1;
    if (m >= j)
      return arrayOfChar;
    int n = m + 1;
    int i1 = 0xFF & paramArrayOfByte[m];
    int i2;
    int i3;
    label79: int i4;
    int i5;
    label102: int i9;
    int i12;
    label191: int i13;
    if (n < j)
    {
      i2 = n + 1;
      i3 = 0xFF & paramArrayOfByte[n];
      if (i2 >= j)
        break label248;
      i4 = i2 + 1;
      i5 = 0xFF & paramArrayOfByte[i2];
      int i6 = i1 >>> 2;
      int i7 = (i1 & 0x3) << 4 | i3 >>> 4;
      int i8 = (i3 & 0xF) << 2 | i5 >>> 6;
      i9 = i5 & 0x3F;
      int i10 = k + 1;
      arrayOfChar[k] = map1[i6];
      int i11 = i10 + 1;
      arrayOfChar[i10] = map1[i7];
      if (i11 >= i)
        break label258;
      i12 = map1[i8];
      arrayOfChar[i11] = i12;
      i13 = i11 + 1;
      if (i13 >= i)
        break label265;
    }
    label258: label265: for (int i14 = map1[i9]; ; i14 = 61)
    {
      arrayOfChar[i13] = i14;
      k = i13 + 1;
      m = i4;
      break;
      i2 = n;
      i3 = 0;
      break label79;
      label248: i4 = i2;
      i5 = 0;
      break label102;
      i12 = 61;
      break label191;
    }
  }

  public static String encodeLines(byte[] paramArrayOfByte)
  {
    return encodeLines(paramArrayOfByte, 0, paramArrayOfByte.length, 76, systemLineSeparator);
  }

  public static String encodeLines(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, String paramString)
  {
    int i = paramInt3 * 3 / 4;
    if (i <= 0)
      throw new IllegalArgumentException();
    int j = (-1 + (paramInt2 + i)) / i;
    StringBuilder localStringBuilder = new StringBuilder(4 * ((paramInt2 + 2) / 3) + j * paramString.length());
    int k = 0;
    while (true)
    {
      if (k >= paramInt2)
        return localStringBuilder.toString();
      int m = Math.min(paramInt2 - k, i);
      localStringBuilder.append(encode(paramArrayOfByte, paramInt1 + k, m));
      localStringBuilder.append(paramString);
      k += m;
    }
  }

  public static String encodeString(String paramString)
  {
    return new String(encode(paramString.getBytes()));
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.Base64Coder
 * JD-Core Version:    0.6.0
 */