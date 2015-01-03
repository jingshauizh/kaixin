package com.google.zxing.qrcode.encoder;

import java.lang.reflect.Array;

public final class ByteMatrix
{
  private final byte[][] bytes;
  private final int height;
  private final int width;

  public ByteMatrix(int paramInt1, int paramInt2)
  {
    int[] arrayOfInt = { paramInt2, paramInt1 };
    this.bytes = ((byte[][])Array.newInstance(Byte.TYPE, arrayOfInt));
    this.width = paramInt1;
    this.height = paramInt2;
  }

  public void clear(byte paramByte)
  {
    int i = 0;
    if (i >= this.height)
      return;
    for (int j = 0; ; j++)
    {
      if (j >= this.width)
      {
        i++;
        break;
      }
      this.bytes[i][j] = paramByte;
    }
  }

  public byte get(int paramInt1, int paramInt2)
  {
    return this.bytes[paramInt2][paramInt1];
  }

  public byte[][] getArray()
  {
    return this.bytes;
  }

  public int getHeight()
  {
    return this.height;
  }

  public int getWidth()
  {
    return this.width;
  }

  public void set(int paramInt1, int paramInt2, byte paramByte)
  {
    this.bytes[paramInt2][paramInt1] = paramByte;
  }

  public void set(int paramInt1, int paramInt2, int paramInt3)
  {
    this.bytes[paramInt2][paramInt1] = (byte)paramInt3;
  }

  public void set(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    byte[] arrayOfByte = this.bytes[paramInt2];
    if (paramBoolean);
    for (int i = 1; ; i = 0)
    {
      arrayOfByte[paramInt1] = (byte)i;
      return;
    }
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer(2 + 2 * this.width * this.height);
    int j;
    for (int i = 0; ; i++)
    {
      if (i >= this.height)
        return localStringBuffer.toString();
      j = 0;
      if (j < this.width)
        break;
      localStringBuffer.append('\n');
    }
    switch (this.bytes[i][j])
    {
    default:
      localStringBuffer.append("  ");
    case 0:
    case 1:
    }
    while (true)
    {
      j++;
      break;
      localStringBuffer.append(" 0");
      continue;
      localStringBuffer.append(" 1");
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.qrcode.encoder.ByteMatrix
 * JD-Core Version:    0.6.0
 */