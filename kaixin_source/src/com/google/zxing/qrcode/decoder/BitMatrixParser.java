package com.google.zxing.qrcode.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;

final class BitMatrixParser
{
  private final BitMatrix bitMatrix;
  private FormatInformation parsedFormatInfo;
  private Version parsedVersion;

  BitMatrixParser(BitMatrix paramBitMatrix)
    throws FormatException
  {
    int i = paramBitMatrix.getHeight();
    if ((i < 21) || ((i & 0x3) != 1))
      throw FormatException.getFormatInstance();
    this.bitMatrix = paramBitMatrix;
  }

  private int copyBit(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.bitMatrix.get(paramInt1, paramInt2))
      return 0x1 | paramInt3 << 1;
    return paramInt3 << 1;
  }

  byte[] readCodewords()
    throws FormatException
  {
    FormatInformation localFormatInformation = readFormatInformation();
    Version localVersion = readVersion();
    DataMask localDataMask = DataMask.forReference(localFormatInformation.getDataMask());
    int i = this.bitMatrix.getHeight();
    localDataMask.unmaskBitMatrix(this.bitMatrix, i);
    BitMatrix localBitMatrix = localVersion.buildFunctionPattern();
    int j = 1;
    byte[] arrayOfByte = new byte[localVersion.getTotalCodewords()];
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = i - 1;
    int i4;
    int i5;
    label163: int i6;
    while (true)
      if (i1 <= 0)
      {
        if (k == localVersion.getTotalCodewords())
          break;
        throw FormatException.getFormatInstance();
      }
      else
      {
        if (i1 == 6)
          i1--;
        int i2 = 0;
        if (i2 >= i)
        {
          j ^= 1;
          i1 -= 2;
          continue;
        }
        if (j != 0);
        for (int i3 = i - 1 - i2; ; i3 = i2)
        {
          i4 = 0;
          i5 = k;
          if (i4 < 2)
            break label163;
          i2++;
          k = i5;
          break;
        }
        if (localBitMatrix.get(i1 - i4, i3))
          break label250;
        n++;
        m <<= 1;
        if (this.bitMatrix.get(i1 - i4, i3))
          m |= 1;
        if (n != 8)
          break label250;
        i6 = i5 + 1;
        arrayOfByte[i5] = (byte)m;
        n = 0;
        m = 0;
      }
    while (true)
    {
      i4++;
      i5 = i6;
      break;
      return arrayOfByte;
      label250: i6 = i5;
    }
  }

  FormatInformation readFormatInformation()
    throws FormatException
  {
    if (this.parsedFormatInfo != null)
      return this.parsedFormatInfo;
    int i = 0;
    int j = 0;
    int k;
    int m;
    label51: int n;
    int i1;
    int i3;
    if (j >= 6)
    {
      k = copyBit(8, 7, copyBit(8, 8, copyBit(7, 8, i)));
      m = 5;
      if (m >= 0)
        break label139;
      n = this.bitMatrix.getHeight();
      i1 = 0;
      int i2 = n - 7;
      i3 = n - 1;
      label81: if (i3 >= i2)
        break label155;
    }
    for (int i4 = n - 8; ; i4++)
    {
      if (i4 >= n)
      {
        this.parsedFormatInfo = FormatInformation.decodeFormatInformation(k, i1);
        if (this.parsedFormatInfo == null)
          break label191;
        return this.parsedFormatInfo;
        i = copyBit(j, 8, i);
        j++;
        break;
        label139: k = copyBit(8, m, k);
        m--;
        break label51;
        label155: i1 = copyBit(8, i3, i1);
        i3--;
        break label81;
      }
      i1 = copyBit(i4, 8, i1);
    }
    label191: throw FormatException.getFormatInstance();
  }

  Version readVersion()
    throws FormatException
  {
    if (this.parsedVersion != null)
      return this.parsedVersion;
    int i = this.bitMatrix.getHeight();
    int j = i - 17 >> 2;
    if (j <= 6)
      return Version.getVersionForNumber(j);
    int k = 0;
    int m = i - 11;
    int n = 5;
    if (n < 0)
    {
      this.parsedVersion = Version.decodeVersionInformation(k);
      if ((this.parsedVersion != null) && (this.parsedVersion.getDimensionForVersion() == i))
        return this.parsedVersion;
    }
    else
    {
      for (int i1 = i - 9; ; i1--)
      {
        if (i1 < m)
        {
          n--;
          break;
        }
        k = copyBit(i1, n, k);
      }
    }
    int i2 = 0;
    int i3 = 5;
    if (i3 < 0)
    {
      this.parsedVersion = Version.decodeVersionInformation(i2);
      if ((this.parsedVersion != null) && (this.parsedVersion.getDimensionForVersion() == i))
        return this.parsedVersion;
    }
    else
    {
      for (int i4 = i - 9; ; i4--)
      {
        if (i4 < m)
        {
          i3--;
          break;
        }
        i2 = copyBit(i3, i4, i2);
      }
    }
    throw FormatException.getFormatInstance();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.qrcode.decoder.BitMatrixParser
 * JD-Core Version:    0.6.0
 */