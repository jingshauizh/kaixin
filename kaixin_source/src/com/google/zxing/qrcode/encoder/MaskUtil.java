package com.google.zxing.qrcode.encoder;

public final class MaskUtil
{
  public static int applyMaskPenaltyRule1(ByteMatrix paramByteMatrix)
  {
    return applyMaskPenaltyRule1Internal(paramByteMatrix, true) + applyMaskPenaltyRule1Internal(paramByteMatrix, false);
  }

  private static int applyMaskPenaltyRule1Internal(ByteMatrix paramByteMatrix, boolean paramBoolean)
  {
    int i = 0;
    int j = 0;
    int k = -1;
    int m;
    int n;
    label27: byte[][] arrayOfByte;
    if (paramBoolean)
    {
      m = paramByteMatrix.getHeight();
      if (!paramBoolean)
        break label54;
      n = paramByteMatrix.getWidth();
      arrayOfByte = paramByteMatrix.getArray();
    }
    label54: int i2;
    for (int i1 = 0; ; i1++)
    {
      if (i1 >= m)
      {
        return i;
        m = paramByteMatrix.getWidth();
        break;
        n = paramByteMatrix.getHeight();
        break label27;
      }
      i2 = 0;
      if (i2 < n)
        break label81;
      j = 0;
    }
    label81: int i3;
    if (paramBoolean)
    {
      i3 = arrayOfByte[i1][i2];
      label95: if (i3 != k)
        break label143;
      j++;
      if (j != 5)
        break label132;
      i += 3;
    }
    while (true)
    {
      i2++;
      break;
      i3 = arrayOfByte[i2][i1];
      break label95;
      label132: if (j <= 5)
        continue;
      i++;
      continue;
      label143: j = 1;
      k = i3;
    }
  }

  public static int applyMaskPenaltyRule2(ByteMatrix paramByteMatrix)
  {
    int i = 0;
    byte[][] arrayOfByte = paramByteMatrix.getArray();
    int j = paramByteMatrix.getWidth();
    int k = paramByteMatrix.getHeight();
    int m = 0;
    if (m >= k - 1)
      return i;
    for (int n = 0; ; n++)
    {
      if (n >= j - 1)
      {
        m++;
        break;
      }
      int i1 = arrayOfByte[m][n];
      if ((i1 != arrayOfByte[m][(n + 1)]) || (i1 != arrayOfByte[(m + 1)][n]) || (i1 != arrayOfByte[(m + 1)][(n + 1)]))
        continue;
      i += 3;
    }
  }

  public static int applyMaskPenaltyRule3(ByteMatrix paramByteMatrix)
  {
    int i = 0;
    byte[][] arrayOfByte = paramByteMatrix.getArray();
    int j = paramByteMatrix.getWidth();
    int k = paramByteMatrix.getHeight();
    int m = 0;
    if (m >= k)
      return i;
    for (int n = 0; ; n++)
    {
      if (n >= j)
      {
        m++;
        break;
      }
      if ((n + 6 < j) && (arrayOfByte[m][n] == 1) && (arrayOfByte[m][(n + 1)] == 0) && (arrayOfByte[m][(n + 2)] == 1) && (arrayOfByte[m][(n + 3)] == 1) && (arrayOfByte[m][(n + 4)] == 1) && (arrayOfByte[m][(n + 5)] == 0) && (arrayOfByte[m][(n + 6)] == 1) && (((n + 10 < j) && (arrayOfByte[m][(n + 7)] == 0) && (arrayOfByte[m][(n + 8)] == 0) && (arrayOfByte[m][(n + 9)] == 0) && (arrayOfByte[m][(n + 10)] == 0)) || ((n - 4 >= 0) && (arrayOfByte[m][(n - 1)] == 0) && (arrayOfByte[m][(n - 2)] == 0) && (arrayOfByte[m][(n - 3)] == 0) && (arrayOfByte[m][(n - 4)] == 0))))
        i += 40;
      if ((m + 6 >= k) || (arrayOfByte[m][n] != 1) || (arrayOfByte[(m + 1)][n] != 0) || (arrayOfByte[(m + 2)][n] != 1) || (arrayOfByte[(m + 3)][n] != 1) || (arrayOfByte[(m + 4)][n] != 1) || (arrayOfByte[(m + 5)][n] != 0) || (arrayOfByte[(m + 6)][n] != 1) || (((m + 10 >= k) || (arrayOfByte[(m + 7)][n] != 0) || (arrayOfByte[(m + 8)][n] != 0) || (arrayOfByte[(m + 9)][n] != 0) || (arrayOfByte[(m + 10)][n] != 0)) && ((m - 4 < 0) || (arrayOfByte[(m - 1)][n] != 0) || (arrayOfByte[(m - 2)][n] != 0) || (arrayOfByte[(m - 3)][n] != 0) || (arrayOfByte[(m - 4)][n] != 0))))
        continue;
      i += 40;
    }
  }

  public static int applyMaskPenaltyRule4(ByteMatrix paramByteMatrix)
  {
    int i = 0;
    byte[][] arrayOfByte = paramByteMatrix.getArray();
    int j = paramByteMatrix.getWidth();
    int k = paramByteMatrix.getHeight();
    int m = 0;
    if (m >= k)
    {
      int i1 = paramByteMatrix.getHeight() * paramByteMatrix.getWidth();
      return 10 * (Math.abs((int)(100.0D * (i / i1) - 50.0D)) / 5);
    }
    for (int n = 0; ; n++)
    {
      if (n >= j)
      {
        m++;
        break;
      }
      if (arrayOfByte[m][n] != 1)
        continue;
      i++;
    }
  }

  public static boolean getDataMaskBit(int paramInt1, int paramInt2, int paramInt3)
  {
    if (!QRCode.isValidMaskPattern(paramInt1))
      throw new IllegalArgumentException("Invalid mask pattern");
    int i;
    switch (paramInt1)
    {
    default:
      throw new IllegalArgumentException("Invalid mask pattern: " + paramInt1);
    case 0:
      i = 0x1 & paramInt3 + paramInt2;
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    }
    while (i == 0)
    {
      return true;
      i = paramInt3 & 0x1;
      continue;
      i = paramInt2 % 3;
      continue;
      i = (paramInt3 + paramInt2) % 3;
      continue;
      i = 0x1 & (paramInt3 >>> 1) + paramInt2 / 3;
      continue;
      int k = paramInt3 * paramInt2;
      i = (k & 0x1) + k % 3;
      continue;
      int j = paramInt3 * paramInt2;
      i = 0x1 & (j & 0x1) + j % 3;
      continue;
      i = 0x1 & paramInt3 * paramInt2 % 3 + (0x1 & paramInt3 + paramInt2);
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.qrcode.encoder.MaskUtil
 * JD-Core Version:    0.6.0
 */