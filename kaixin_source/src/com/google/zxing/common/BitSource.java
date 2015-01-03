package com.google.zxing.common;

public final class BitSource
{
  private int bitOffset;
  private int byteOffset;
  private final byte[] bytes;

  public BitSource(byte[] paramArrayOfByte)
  {
    this.bytes = paramArrayOfByte;
  }

  public int available()
  {
    return 8 * (this.bytes.length - this.byteOffset) - this.bitOffset;
  }

  public int readBits(int paramInt)
  {
    if ((paramInt < 1) || (paramInt > 32))
      throw new IllegalArgumentException();
    int i = this.bitOffset;
    int j = 0;
    int n;
    int i1;
    if (i > 0)
    {
      n = 8 - this.bitOffset;
      if (paramInt >= n)
        break label184;
      i1 = paramInt;
      int i2 = n - i1;
      j = (255 >> 8 - i1 << i2 & this.bytes[this.byteOffset]) >> i2;
      paramInt -= i1;
      this.bitOffset = (i1 + this.bitOffset);
      if (this.bitOffset == 8)
      {
        this.bitOffset = 0;
        this.byteOffset = (1 + this.byteOffset);
      }
    }
    if (paramInt > 0);
    while (true)
    {
      if (paramInt < 8)
      {
        if (paramInt > 0)
        {
          int k = 8 - paramInt;
          int m = 255 >> k << k;
          j = j << paramInt | (m & this.bytes[this.byteOffset]) >> k;
          this.bitOffset = (paramInt + this.bitOffset);
        }
        return j;
        label184: i1 = n;
        break;
      }
      j = j << 8 | 0xFF & this.bytes[this.byteOffset];
      this.byteOffset = (1 + this.byteOffset);
      paramInt -= 8;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.common.BitSource
 * JD-Core Version:    0.6.0
 */