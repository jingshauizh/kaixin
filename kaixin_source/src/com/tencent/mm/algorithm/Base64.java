package com.tencent.mm.algorithm;

import java.util.Arrays;

public class Base64
{
  private static final char[] a = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
  private static final int[] b;

  static
  {
    int[] arrayOfInt = new int[256];
    b = arrayOfInt;
    Arrays.fill(arrayOfInt, -1);
    int i = a.length;
    for (int j = 0; j < i; j++)
      b[a[j]] = j;
    b[61] = 0;
  }

  public static final byte[] decode(String paramString)
  {
    if (paramString != null);
    for (int i = paramString.length(); i == 0; i = 0)
      return new byte[0];
    int j = 0;
    int k = 0;
    while (j < i)
    {
      if (b[paramString.charAt(j)] < 0)
        k++;
      j++;
    }
    if ((i - k) % 4 != 0)
      return null;
    int m = i;
    int n = 0;
    while (m > 1)
    {
      int[] arrayOfInt2 = b;
      m--;
      if (arrayOfInt2[paramString.charAt(m)] > 0)
        break;
      if (paramString.charAt(m) != '=')
        continue;
      n++;
    }
    int i1 = (6 * (i - k) >> 3) - n;
    byte[] arrayOfByte = new byte[i1];
    int i2 = 0;
    int i3 = 0;
    int i5;
    int i7;
    int i8;
    if (i2 < i1)
    {
      int i4 = 0;
      i5 = i3;
      int i6 = 0;
      if (i6 < 4)
      {
        int[] arrayOfInt1 = b;
        int i9 = i5 + 1;
        int i10 = arrayOfInt1[paramString.charAt(i5)];
        if (i10 >= 0)
          i4 |= i10 << 18 - i6 * 6;
        while (true)
        {
          i6++;
          i5 = i9;
          break;
          i6--;
        }
      }
      i7 = i2 + 1;
      arrayOfByte[i2] = (byte)(i4 >> 16);
      if (i7 < i1)
      {
        i8 = i7 + 1;
        arrayOfByte[i7] = (byte)(i4 >> 8);
        if (i8 >= i1)
          break label294;
        i7 = i8 + 1;
        arrayOfByte[i8] = (byte)i4;
      }
    }
    while (true)
    {
      i2 = i7;
      i3 = i5;
      break;
      return arrayOfByte;
      label294: i7 = i8;
    }
  }

  public static final byte[] decode(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length;
    int j = 0;
    int k = 0;
    while (j < i)
    {
      if (b[(0xFF & paramArrayOfByte[j])] < 0)
        k++;
      j++;
    }
    if ((i - k) % 4 != 0)
      return null;
    int m = i;
    int n = 0;
    while (m > 1)
    {
      int[] arrayOfInt2 = b;
      m--;
      if (arrayOfInt2[(0xFF & paramArrayOfByte[m])] > 0)
        break;
      if (paramArrayOfByte[m] != 61)
        continue;
      n++;
    }
    int i1 = (6 * (i - k) >> 3) - n;
    byte[] arrayOfByte = new byte[i1];
    int i2 = 0;
    int i3 = 0;
    int i5;
    int i7;
    int i8;
    if (i2 < i1)
    {
      int i4 = 0;
      i5 = i3;
      int i6 = 0;
      if (i6 < 4)
      {
        int[] arrayOfInt1 = b;
        int i9 = i5 + 1;
        int i10 = arrayOfInt1[(0xFF & paramArrayOfByte[i5])];
        if (i10 >= 0)
          i4 |= i10 << 18 - i6 * 6;
        while (true)
        {
          i6++;
          i5 = i9;
          break;
          i6--;
        }
      }
      i7 = i2 + 1;
      arrayOfByte[i2] = (byte)(i4 >> 16);
      if (i7 < i1)
      {
        i8 = i7 + 1;
        arrayOfByte[i7] = (byte)(i4 >> 8);
        if (i8 >= i1)
          break label279;
        i7 = i8 + 1;
        arrayOfByte[i8] = (byte)i4;
      }
    }
    while (true)
    {
      i2 = i7;
      i3 = i5;
      break;
      return arrayOfByte;
      label279: i7 = i8;
    }
  }

  public static final byte[] decode(char[] paramArrayOfChar)
  {
    if (paramArrayOfChar != null);
    for (int i = paramArrayOfChar.length; i == 0; i = 0)
      return new byte[0];
    int j = 0;
    int k = 0;
    while (j < i)
    {
      if (b[paramArrayOfChar[j]] < 0)
        k++;
      j++;
    }
    if ((i - k) % 4 != 0)
      return null;
    int m = i;
    int n = 0;
    while (m > 1)
    {
      int[] arrayOfInt2 = b;
      m--;
      if (arrayOfInt2[paramArrayOfChar[m]] > 0)
        break;
      if (paramArrayOfChar[m] != '=')
        continue;
      n++;
    }
    int i1 = (6 * (i - k) >> 3) - n;
    byte[] arrayOfByte = new byte[i1];
    int i2 = 0;
    int i3 = 0;
    int i5;
    int i7;
    int i8;
    if (i2 < i1)
    {
      int i4 = 0;
      i5 = i3;
      int i6 = 0;
      if (i6 < 4)
      {
        int[] arrayOfInt1 = b;
        int i9 = i5 + 1;
        int i10 = arrayOfInt1[paramArrayOfChar[i5]];
        if (i10 >= 0)
          i4 |= i10 << 18 - i6 * 6;
        while (true)
        {
          i6++;
          i5 = i9;
          break;
          i6--;
        }
      }
      i7 = i2 + 1;
      arrayOfByte[i2] = (byte)(i4 >> 16);
      if (i7 < i1)
      {
        i8 = i7 + 1;
        arrayOfByte[i7] = (byte)(i4 >> 8);
        if (i8 >= i1)
          break label284;
        i7 = i8 + 1;
        arrayOfByte[i8] = (byte)i4;
      }
    }
    while (true)
    {
      i2 = i7;
      i3 = i5;
      break;
      return arrayOfByte;
      label284: i7 = i8;
    }
  }

  public static final byte[] decodeFast(String paramString)
  {
    int i = 0;
    int j = paramString.length();
    if (j == 0)
      return new byte[0];
    int k = j - 1;
    for (int m = 0; (m < k) && (b[(0xFF & paramString.charAt(m))] < 0); m++);
    while (true)
    {
      if ((n > 0) && (b[(0xFF & paramString.charAt(n))] < 0))
      {
        n--;
        continue;
      }
      int i1;
      int i2;
      int i24;
      if (paramString.charAt(n) == '=')
        if (paramString.charAt(n - 1) == '=')
        {
          i1 = 2;
          i2 = 1 + (n - m);
          if (j <= 76)
            break label383;
          if (paramString.charAt(76) != '\r')
            break label377;
          i24 = i2 / 78;
        }
      label139: int i4;
      byte[] arrayOfByte;
      int i7;
      label377: label383: for (int i3 = i24 << 1; ; i3 = 0)
      {
        i4 = (6 * (i2 - i3) >> 3) - i1;
        arrayOfByte = new byte[i4];
        int i5 = 3 * (i4 / 3);
        int i6 = 0;
        i7 = 0;
        while (i7 < i5)
        {
          int[] arrayOfInt2 = b;
          int i15 = m + 1;
          int i16 = arrayOfInt2[paramString.charAt(m)] << 18;
          int[] arrayOfInt3 = b;
          int i17 = i15 + 1;
          int i18 = i16 | arrayOfInt3[paramString.charAt(i15)] << 12;
          int[] arrayOfInt4 = b;
          int i19 = i17 + 1;
          int i20 = i18 | arrayOfInt4[paramString.charAt(i17)] << 6;
          int[] arrayOfInt5 = b;
          m = i19 + 1;
          int i21 = i20 | arrayOfInt5[paramString.charAt(i19)];
          int i22 = i7 + 1;
          arrayOfByte[i7] = (byte)(i21 >> 16);
          int i23 = i22 + 1;
          arrayOfByte[i22] = (byte)(i21 >> 8);
          i7 = i23 + 1;
          arrayOfByte[i23] = (byte)i21;
          if (i3 <= 0)
            continue;
          i6++;
          if (i6 != 19)
            continue;
          m += 2;
          i6 = 0;
        }
        i1 = 1;
        break;
        i1 = 0;
        break;
        i24 = 0;
        break label139;
      }
      if (i7 < i4)
      {
        int i8 = m;
        int i9 = 0;
        while (i8 <= n - i1)
        {
          int[] arrayOfInt1 = b;
          int i13 = i8 + 1;
          int i14 = i9 | arrayOfInt1[paramString.charAt(i8)] << 18 - i * 6;
          i++;
          i9 = i14;
          i8 = i13;
        }
        int i10 = 16;
        int i12;
        for (int i11 = i7; i11 < i4; i11 = i12)
        {
          i12 = i11 + 1;
          arrayOfByte[i11] = (byte)(i9 >> i10);
          i10 -= 8;
        }
      }
      return arrayOfByte;
      int n = k;
    }
  }

  public static final byte[] decodeFast(byte[] paramArrayOfByte)
  {
    int i = 0;
    int j = paramArrayOfByte.length;
    if (j == 0)
      return new byte[0];
    int k = j - 1;
    for (int m = 0; (m < k) && (b[(0xFF & paramArrayOfByte[m])] < 0); m++);
    while (true)
    {
      if ((n > 0) && (b[(0xFF & paramArrayOfByte[n])] < 0))
      {
        n--;
        continue;
      }
      int i1;
      int i2;
      int i24;
      if (paramArrayOfByte[n] == 61)
        if (paramArrayOfByte[(n - 1)] == 61)
        {
          i1 = 2;
          i2 = 1 + (n - m);
          if (j <= 76)
            break label363;
          if (paramArrayOfByte[76] != 13)
            break label357;
          i24 = i2 / 78;
        }
      label127: int i4;
      byte[] arrayOfByte;
      int i7;
      label357: label363: for (int i3 = i24 << 1; ; i3 = 0)
      {
        i4 = (6 * (i2 - i3) >> 3) - i1;
        arrayOfByte = new byte[i4];
        int i5 = 3 * (i4 / 3);
        int i6 = 0;
        i7 = 0;
        while (i7 < i5)
        {
          int[] arrayOfInt2 = b;
          int i15 = m + 1;
          int i16 = arrayOfInt2[paramArrayOfByte[m]] << 18;
          int[] arrayOfInt3 = b;
          int i17 = i15 + 1;
          int i18 = i16 | arrayOfInt3[paramArrayOfByte[i15]] << 12;
          int[] arrayOfInt4 = b;
          int i19 = i17 + 1;
          int i20 = i18 | arrayOfInt4[paramArrayOfByte[i17]] << 6;
          int[] arrayOfInt5 = b;
          m = i19 + 1;
          int i21 = i20 | arrayOfInt5[paramArrayOfByte[i19]];
          int i22 = i7 + 1;
          arrayOfByte[i7] = (byte)(i21 >> 16);
          int i23 = i22 + 1;
          arrayOfByte[i22] = (byte)(i21 >> 8);
          i7 = i23 + 1;
          arrayOfByte[i23] = (byte)i21;
          if (i3 <= 0)
            continue;
          i6++;
          if (i6 != 19)
            continue;
          m += 2;
          i6 = 0;
        }
        i1 = 1;
        break;
        i1 = 0;
        break;
        i24 = 0;
        break label127;
      }
      if (i7 < i4)
      {
        int i8 = m;
        int i9 = 0;
        while (i8 <= n - i1)
        {
          int[] arrayOfInt1 = b;
          int i13 = i8 + 1;
          int i14 = i9 | arrayOfInt1[paramArrayOfByte[i8]] << 18 - i * 6;
          i++;
          i9 = i14;
          i8 = i13;
        }
        int i10 = 16;
        int i12;
        for (int i11 = i7; i11 < i4; i11 = i12)
        {
          i12 = i11 + 1;
          arrayOfByte[i11] = (byte)(i9 >> i10);
          i10 -= 8;
        }
      }
      return arrayOfByte;
      int n = k;
    }
  }

  public static final byte[] decodeFast(char[] paramArrayOfChar)
  {
    int i = 0;
    int j = paramArrayOfChar.length;
    if (j == 0)
      return new byte[0];
    int k = j - 1;
    for (int m = 0; (m < k) && (b[paramArrayOfChar[m]] < 0); m++);
    while (true)
    {
      if ((n > 0) && (b[paramArrayOfChar[n]] < 0))
      {
        n--;
        continue;
      }
      int i1;
      int i2;
      int i24;
      if (paramArrayOfChar[n] == '=')
        if (paramArrayOfChar[(n - 1)] == '=')
        {
          i1 = 2;
          i2 = 1 + (n - m);
          if (j <= 76)
            break label355;
          if (paramArrayOfChar[76] != '\r')
            break label349;
          i24 = i2 / 78;
        }
      label119: int i4;
      byte[] arrayOfByte;
      int i7;
      label349: label355: for (int i3 = i24 << 1; ; i3 = 0)
      {
        i4 = (6 * (i2 - i3) >> 3) - i1;
        arrayOfByte = new byte[i4];
        int i5 = 3 * (i4 / 3);
        int i6 = 0;
        i7 = 0;
        while (i7 < i5)
        {
          int[] arrayOfInt2 = b;
          int i15 = m + 1;
          int i16 = arrayOfInt2[paramArrayOfChar[m]] << 18;
          int[] arrayOfInt3 = b;
          int i17 = i15 + 1;
          int i18 = i16 | arrayOfInt3[paramArrayOfChar[i15]] << 12;
          int[] arrayOfInt4 = b;
          int i19 = i17 + 1;
          int i20 = i18 | arrayOfInt4[paramArrayOfChar[i17]] << 6;
          int[] arrayOfInt5 = b;
          m = i19 + 1;
          int i21 = i20 | arrayOfInt5[paramArrayOfChar[i19]];
          int i22 = i7 + 1;
          arrayOfByte[i7] = (byte)(i21 >> 16);
          int i23 = i22 + 1;
          arrayOfByte[i22] = (byte)(i21 >> 8);
          i7 = i23 + 1;
          arrayOfByte[i23] = (byte)i21;
          if (i3 <= 0)
            continue;
          i6++;
          if (i6 != 19)
            continue;
          m += 2;
          i6 = 0;
        }
        i1 = 1;
        break;
        i1 = 0;
        break;
        i24 = 0;
        break label119;
      }
      if (i7 < i4)
      {
        int i8 = m;
        int i9 = 0;
        while (i8 <= n - i1)
        {
          int[] arrayOfInt1 = b;
          int i13 = i8 + 1;
          int i14 = i9 | arrayOfInt1[paramArrayOfChar[i8]] << 18 - i * 6;
          i++;
          i9 = i14;
          i8 = i13;
        }
        int i10 = 16;
        int i12;
        for (int i11 = i7; i11 < i4; i11 = i12)
        {
          i12 = i11 + 1;
          arrayOfByte[i11] = (byte)(i9 >> i10);
          i10 -= 8;
        }
      }
      return arrayOfByte;
      int n = k;
    }
  }

  public static final byte[] encodeToByte(byte[] paramArrayOfByte, boolean paramBoolean)
  {
    if (paramArrayOfByte != null);
    for (int i = paramArrayOfByte.length; i == 0; i = 0)
      return new byte[0];
    int j = 3 * (i / 3);
    int k = 1 + (i - 1) / 3 << 2;
    if (paramBoolean);
    int n;
    byte[] arrayOfByte;
    for (int m = (k - 1) / 76 << 1; ; m = 0)
    {
      n = k + m;
      arrayOfByte = new byte[n];
      int i1 = 0;
      int i2 = 0;
      int i3 = 0;
      while (i3 < j)
      {
        int i10 = i3 + 1;
        int i11 = (0xFF & paramArrayOfByte[i3]) << 16;
        int i12 = i10 + 1;
        int i13 = i11 | (0xFF & paramArrayOfByte[i10]) << 8;
        i3 = i12 + 1;
        int i14 = i13 | 0xFF & paramArrayOfByte[i12];
        int i15 = i2 + 1;
        arrayOfByte[i2] = (byte)a[(0x3F & i14 >>> 18)];
        int i16 = i15 + 1;
        arrayOfByte[i15] = (byte)a[(0x3F & i14 >>> 12)];
        int i17 = i16 + 1;
        arrayOfByte[i16] = (byte)a[(0x3F & i14 >>> 6)];
        i2 = i17 + 1;
        arrayOfByte[i17] = (byte)a[(i14 & 0x3F)];
        if (!paramBoolean)
          continue;
        i1++;
        if ((i1 != 19) || (i2 >= n - 2))
          continue;
        int i18 = i2 + 1;
        arrayOfByte[i2] = 13;
        int i19 = i18 + 1;
        arrayOfByte[i18] = 10;
        i2 = i19;
        i1 = 0;
      }
    }
    int i4 = i - j;
    int i7;
    int i8;
    if (i4 > 0)
    {
      int i5 = (0xFF & paramArrayOfByte[j]) << 10;
      int i6 = 0;
      if (i4 == 2)
        i6 = (0xFF & paramArrayOfByte[(i - 1)]) << 2;
      i7 = i6 | i5;
      arrayOfByte[(n - 4)] = (byte)a[(i7 >> 12)];
      arrayOfByte[(n - 3)] = (byte)a[(0x3F & i7 >>> 6)];
      i8 = n - 2;
      if (i4 != 2)
        break label429;
    }
    label429: for (int i9 = (byte)a[(i7 & 0x3F)]; ; i9 = 61)
    {
      arrayOfByte[i8] = i9;
      arrayOfByte[(n - 1)] = 61;
      return arrayOfByte;
    }
  }

  public static final char[] encodeToChar(byte[] paramArrayOfByte, boolean paramBoolean)
  {
    if (paramArrayOfByte != null);
    for (int i = paramArrayOfByte.length; i == 0; i = 0)
      return new char[0];
    int j = 3 * (i / 3);
    int k = 1 + (i - 1) / 3 << 2;
    if (paramBoolean);
    int n;
    char[] arrayOfChar;
    for (int m = (k - 1) / 76 << 1; ; m = 0)
    {
      n = k + m;
      arrayOfChar = new char[n];
      int i1 = 0;
      int i2 = 0;
      int i3 = 0;
      while (i3 < j)
      {
        int i10 = i3 + 1;
        int i11 = (0xFF & paramArrayOfByte[i3]) << 16;
        int i12 = i10 + 1;
        int i13 = i11 | (0xFF & paramArrayOfByte[i10]) << 8;
        i3 = i12 + 1;
        int i14 = i13 | 0xFF & paramArrayOfByte[i12];
        int i15 = i2 + 1;
        arrayOfChar[i2] = a[(0x3F & i14 >>> 18)];
        int i16 = i15 + 1;
        arrayOfChar[i15] = a[(0x3F & i14 >>> 12)];
        int i17 = i16 + 1;
        arrayOfChar[i16] = a[(0x3F & i14 >>> 6)];
        i2 = i17 + 1;
        arrayOfChar[i17] = a[(i14 & 0x3F)];
        if (!paramBoolean)
          continue;
        i1++;
        if ((i1 != 19) || (i2 >= n - 2))
          continue;
        int i18 = i2 + 1;
        arrayOfChar[i2] = '\r';
        int i19 = i18 + 1;
        arrayOfChar[i18] = '\n';
        i2 = i19;
        i1 = 0;
      }
    }
    int i4 = i - j;
    int i7;
    int i8;
    if (i4 > 0)
    {
      int i5 = (0xFF & paramArrayOfByte[j]) << 10;
      int i6 = 0;
      if (i4 == 2)
        i6 = (0xFF & paramArrayOfByte[(i - 1)]) << 2;
      i7 = i6 | i5;
      arrayOfChar[(n - 4)] = a[(i7 >> 12)];
      arrayOfChar[(n - 3)] = a[(0x3F & i7 >>> 6)];
      i8 = n - 2;
      if (i4 != 2)
        break label422;
    }
    label422: for (int i9 = a[(i7 & 0x3F)]; ; i9 = 61)
    {
      arrayOfChar[i8] = i9;
      arrayOfChar[(n - 1)] = '=';
      return arrayOfChar;
    }
  }

  public static final String encodeToString(byte[] paramArrayOfByte, boolean paramBoolean)
  {
    return new String(encodeToChar(paramArrayOfByte, paramBoolean));
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.algorithm.Base64
 * JD-Core Version:    0.6.0
 */