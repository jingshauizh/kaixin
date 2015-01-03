package com.amap.mapapi.core;

public class d
{
  public static int a(byte[] paramArrayOfByte, int paramInt)
  {
    int i = 0xFF & paramArrayOfByte[(paramInt + 3)];
    int j = 0xFF & paramArrayOfByte[(paramInt + 2)];
    int k = 0xFF & paramArrayOfByte[(paramInt + 1)];
    int m = 0xFF & paramArrayOfByte[(paramInt + 0)];
    return (i << 24) + (j << 16) + (k << 8) + (m << 0);
  }

  public static void a(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3)
  {
    byte[] arrayOfByte = new byte[paramInt3];
    System.arraycopy(paramArrayOfByte1, paramInt1, arrayOfByte, 0, paramInt3);
    System.arraycopy(arrayOfByte, 0, paramArrayOfByte2, paramInt2, paramInt3);
  }

  public static short b(byte[] paramArrayOfByte, int paramInt)
  {
    int i = 0xFF & paramArrayOfByte[(paramInt + 1)];
    int j = 0xFF & paramArrayOfByte[(paramInt + 0)];
    return (short)((i << 8) + (j << 0));
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.core.d
 * JD-Core Version:    0.6.0
 */