package com.tencent.mm.algorithm;

public final class TypeTransform
{
  private static byte[] a(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length;
    byte[] arrayOfByte = new byte[i];
    for (int j = 0; j < i; j++)
      arrayOfByte[j] = paramArrayOfByte[(i - 1 - j)];
    return arrayOfByte;
  }

  public static int byteArrayHLToInt(byte[] paramArrayOfByte)
  {
    return byteArrayHLToInt(paramArrayOfByte, 0);
  }

  public static int byteArrayHLToInt(byte[] paramArrayOfByte, int paramInt)
  {
    return (0xFF & paramArrayOfByte[paramInt]) << 24 | (0xFF & paramArrayOfByte[(paramInt + 1)]) << 16 | (0xFF & paramArrayOfByte[(paramInt + 2)]) << 8 | (0xFF & paramArrayOfByte[(paramInt + 3)]) << 0;
  }

  public static long byteArrayHLToLong(byte[] paramArrayOfByte)
  {
    return byteArrayHLToLong(paramArrayOfByte, 0);
  }

  public static long byteArrayHLToLong(byte[] paramArrayOfByte, int paramInt)
  {
    return (0xFF & paramArrayOfByte[paramInt]) << 56 | (0xFF & paramArrayOfByte[(paramInt + 1)]) << 48 | (0xFF & paramArrayOfByte[(paramInt + 2)]) << 40 | (0xFF & paramArrayOfByte[(paramInt + 3)]) << 32 | (0xFF & paramArrayOfByte[(paramInt + 4)]) << 24 | (0xFF & paramArrayOfByte[(paramInt + 5)]) << 16 | (0xFF & paramArrayOfByte[(paramInt + 6)]) << 8 | (0xFF & paramArrayOfByte[(paramInt + 7)]) << 0;
  }

  public static int byteArrayLHToInt(byte[] paramArrayOfByte)
  {
    return byteArrayLHToInt(paramArrayOfByte, 0);
  }

  public static int byteArrayLHToInt(byte[] paramArrayOfByte, int paramInt)
  {
    return (0xFF & paramArrayOfByte[(paramInt + 3)]) << 24 | (0xFF & paramArrayOfByte[(paramInt + 2)]) << 16 | (0xFF & paramArrayOfByte[(paramInt + 1)]) << 8 | (0xFF & paramArrayOfByte[paramInt]) << 0;
  }

  public static long byteArrayLHToLong(byte[] paramArrayOfByte)
  {
    return byteArrayLHToLong(paramArrayOfByte, 0);
  }

  public static long byteArrayLHToLong(byte[] paramArrayOfByte, int paramInt)
  {
    return (0xFF & paramArrayOfByte[(paramInt + 7)]) << 56 | (0xFF & paramArrayOfByte[(paramInt + 6)]) << 48 | (0xFF & paramArrayOfByte[5]) << 40 | (0xFF & paramArrayOfByte[(paramInt + 4)]) << 32 | (0xFF & paramArrayOfByte[(paramInt + 3)]) << 24 | (0xFF & paramArrayOfByte[(paramInt + 2)]) << 16 | (0xFF & paramArrayOfByte[(paramInt + 1)]) << 8 | (0xFF & paramArrayOfByte[paramInt]) << 0;
  }

  public static byte[] intToByteArrayHL(int paramInt)
  {
    return a(intToByteArrayLH(paramInt));
  }

  public static byte[] intToByteArrayLH(int paramInt)
  {
    byte[] arrayOfByte = new byte[4];
    for (int i = 0; i < 4; i++)
      arrayOfByte[i] = (byte)(0xFF & paramInt >> i * 8);
    return arrayOfByte;
  }

  public static byte[] longToByteArrayHL(long paramLong)
  {
    return a(longToByteArrayLH(paramLong));
  }

  public static byte[] longToByteArrayLH(long paramLong)
  {
    byte[] arrayOfByte = new byte[8];
    for (int i = 0; i < 8; i++)
      arrayOfByte[i] = (byte)(int)(paramLong >> i * 8);
    return arrayOfByte;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.algorithm.TypeTransform
 * JD-Core Version:    0.6.0
 */