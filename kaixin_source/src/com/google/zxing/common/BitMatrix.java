package com.google.zxing.common;

public final class BitMatrix
{
  public final int[] bits;
  public final int height;
  public final int rowSize;
  public final int width;

  public BitMatrix(int paramInt)
  {
    this(paramInt, paramInt);
  }

  public BitMatrix(int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 1) || (paramInt2 < 1))
      throw new IllegalArgumentException("Both dimensions must be greater than 0");
    this.width = paramInt1;
    this.height = paramInt2;
    this.rowSize = (paramInt1 + 31 >> 5);
    this.bits = new int[paramInt2 * this.rowSize];
  }

  public void clear()
  {
    int i = this.bits.length;
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return;
      this.bits[j] = 0;
    }
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof BitMatrix));
    BitMatrix localBitMatrix;
    do
    {
      return false;
      localBitMatrix = (BitMatrix)paramObject;
    }
    while ((this.width != localBitMatrix.width) || (this.height != localBitMatrix.height) || (this.rowSize != localBitMatrix.rowSize) || (this.bits.length != localBitMatrix.bits.length));
    for (int i = 0; ; i++)
    {
      if (i >= this.bits.length)
        return true;
      if (this.bits[i] != localBitMatrix.bits[i])
        break;
    }
  }

  public void flip(int paramInt1, int paramInt2)
  {
    int i = paramInt2 * this.rowSize + (paramInt1 >> 5);
    int[] arrayOfInt = this.bits;
    arrayOfInt[i] ^= 1 << (paramInt1 & 0x1F);
  }

  public boolean get(int paramInt1, int paramInt2)
  {
    int i = paramInt2 * this.rowSize + (paramInt1 >> 5);
    return (0x1 & this.bits[i] >>> (paramInt1 & 0x1F)) != 0;
  }

  public int[] getBottomRightOnBit()
  {
    for (int i = -1 + this.bits.length; ; i--)
    {
      if ((i >= 0) && (this.bits[i] == 0))
        continue;
      if (i >= 0)
        break;
      return null;
    }
    int j = i / this.rowSize;
    int k = i % this.rowSize << 5;
    int m = this.bits[i];
    for (int n = 31; ; n--)
      if (m >>> n != 0)
        return new int[] { k + n, j };
  }

  public int getHeight()
  {
    return this.height;
  }

  public BitArray getRow(int paramInt, BitArray paramBitArray)
  {
    if ((paramBitArray == null) || (paramBitArray.getSize() < this.width))
      paramBitArray = new BitArray(this.width);
    int i = paramInt * this.rowSize;
    for (int j = 0; ; j++)
    {
      if (j >= this.rowSize)
        return paramBitArray;
      paramBitArray.setBulk(j << 5, this.bits[(i + j)]);
    }
  }

  public int[] getTopLeftOnBit()
  {
    for (int i = 0; ; i++)
    {
      if ((i < this.bits.length) && (this.bits[i] == 0))
        continue;
      if (i != this.bits.length)
        break;
      return null;
    }
    int j = i / this.rowSize;
    int k = i % this.rowSize << 5;
    int m = this.bits[i];
    for (int n = 0; ; n++)
      if (m << 31 - n != 0)
        return new int[] { k + n, j };
  }

  public int getWidth()
  {
    return this.width;
  }

  public int hashCode()
  {
    int i = 31 * (31 * (31 * this.width + this.width) + this.height) + this.rowSize;
    for (int j = 0; ; j++)
    {
      if (j >= this.bits.length)
        return i;
      i = i * 31 + this.bits[j];
    }
  }

  public void set(int paramInt1, int paramInt2)
  {
    int i = paramInt2 * this.rowSize + (paramInt1 >> 5);
    int[] arrayOfInt = this.bits;
    arrayOfInt[i] |= 1 << (paramInt1 & 0x1F);
  }

  public void setRegion(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt2 < 0) || (paramInt1 < 0))
      throw new IllegalArgumentException("Left and top must be nonnegative");
    if ((paramInt4 < 1) || (paramInt3 < 1))
      throw new IllegalArgumentException("Height and width must be at least 1");
    int i = paramInt1 + paramInt3;
    int j = paramInt2 + paramInt4;
    if ((j > this.height) || (i > this.width))
      throw new IllegalArgumentException("The region must fit inside the matrix");
    int k = paramInt2;
    if (k >= j)
      return;
    int m = k * this.rowSize;
    for (int n = paramInt1; ; n++)
    {
      if (n >= i)
      {
        k++;
        break;
      }
      int[] arrayOfInt = this.bits;
      int i1 = m + (n >> 5);
      arrayOfInt[i1] |= 1 << (n & 0x1F);
    }
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer(this.height * (1 + this.width));
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
    if (get(j, i));
    for (String str = "X "; ; str = "  ")
    {
      localStringBuffer.append(str);
      j++;
      break;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.common.BitMatrix
 * JD-Core Version:    0.6.0
 */