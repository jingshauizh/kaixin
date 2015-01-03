package com.google.zxing.common;

public final class BitArray
{
  public int[] bits;
  public int size;

  public BitArray()
  {
    this.size = 0;
    this.bits = new int[1];
  }

  public BitArray(int paramInt)
  {
    this.size = paramInt;
    this.bits = makeArray(paramInt);
  }

  private void ensureCapacity(int paramInt)
  {
    if (paramInt > this.bits.length << 5)
    {
      int[] arrayOfInt = makeArray(paramInt);
      System.arraycopy(this.bits, 0, arrayOfInt, 0, this.bits.length);
      this.bits = arrayOfInt;
    }
  }

  private static int[] makeArray(int paramInt)
  {
    return new int[paramInt + 31 >> 5];
  }

  public void appendBit(boolean paramBoolean)
  {
    ensureCapacity(1 + this.size);
    if (paramBoolean)
    {
      int[] arrayOfInt = this.bits;
      int i = this.size >> 5;
      arrayOfInt[i] |= 1 << (0x1F & this.size);
    }
    this.size = (1 + this.size);
  }

  public void appendBitArray(BitArray paramBitArray)
  {
    int i = paramBitArray.getSize();
    ensureCapacity(i + this.size);
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return;
      appendBit(paramBitArray.get(j));
    }
  }

  public void appendBits(int paramInt1, int paramInt2)
  {
    if ((paramInt2 < 0) || (paramInt2 > 32))
      throw new IllegalArgumentException("Num bits must be between 0 and 32");
    ensureCapacity(paramInt2 + this.size);
    int i = paramInt2;
    if (i <= 0)
      return;
    if ((0x1 & paramInt1 >> i - 1) == 1);
    for (boolean bool = true; ; bool = false)
    {
      appendBit(bool);
      i--;
      break;
    }
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

  public void flip(int paramInt)
  {
    int[] arrayOfInt = this.bits;
    int i = paramInt >> 5;
    arrayOfInt[i] ^= 1 << (paramInt & 0x1F);
  }

  public boolean get(int paramInt)
  {
    return (this.bits[(paramInt >> 5)] & 1 << (paramInt & 0x1F)) != 0;
  }

  public int[] getBitArray()
  {
    return this.bits;
  }

  public int getSize()
  {
    return this.size;
  }

  public int getSizeInBytes()
  {
    return 7 + this.size >> 3;
  }

  public boolean isRange(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramInt2 < paramInt1)
      throw new IllegalArgumentException();
    if (paramInt2 == paramInt1);
    while (true)
    {
      return true;
      int i = paramInt2 - 1;
      int j = paramInt1 >> 5;
      int k = i >> 5;
      label156: label162: for (int m = j; m <= k; m++)
      {
        int n;
        int i1;
        label68: int i2;
        label83: int i4;
        if (m > j)
        {
          n = 0;
          if (m >= k)
            break label117;
          i1 = 31;
          if ((n != 0) || (i1 != 31))
            break label127;
          i2 = -1;
          i4 = i2 & this.bits[m];
          if (!paramBoolean)
            break label156;
        }
        while (true)
        {
          if (i4 == i2)
            break label162;
          return false;
          n = paramInt1 & 0x1F;
          break;
          label117: i1 = i & 0x1F;
          break label68;
          label127: i2 = 0;
          for (int i3 = n; i3 <= i1; i3++)
            i2 |= 1 << i3;
          break label83;
          i2 = 0;
        }
      }
    }
  }

  public void reverse()
  {
    int[] arrayOfInt = new int[this.bits.length];
    int i = this.size;
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        this.bits = arrayOfInt;
        return;
      }
      if (!get(-1 + (i - j)))
        continue;
      int k = j >> 5;
      arrayOfInt[k] |= 1 << (j & 0x1F);
    }
  }

  public void set(int paramInt)
  {
    int[] arrayOfInt = this.bits;
    int i = paramInt >> 5;
    arrayOfInt[i] |= 1 << (paramInt & 0x1F);
  }

  public void setBulk(int paramInt1, int paramInt2)
  {
    this.bits[(paramInt1 >> 5)] = paramInt2;
  }

  public void toBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    int i = 0;
    if (i >= paramInt3)
      return;
    int j = 0;
    for (int k = 0; ; k++)
    {
      if (k >= 8)
      {
        paramArrayOfByte[(paramInt2 + i)] = (byte)j;
        i++;
        break;
      }
      if (get(paramInt1))
        j |= 1 << 7 - k;
      paramInt1++;
    }
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer(this.size);
    int i = 0;
    if (i >= this.size)
      return localStringBuffer.toString();
    if ((i & 0x7) == 0)
      localStringBuffer.append(' ');
    if (get(i));
    for (char c = 'X'; ; c = '.')
    {
      localStringBuffer.append(c);
      i++;
      break;
    }
  }

  public void xor(BitArray paramBitArray)
  {
    if (this.bits.length != paramBitArray.bits.length)
      throw new IllegalArgumentException("Sizes don't match");
    for (int i = 0; ; i++)
    {
      if (i >= this.bits.length)
        return;
      int[] arrayOfInt = this.bits;
      arrayOfInt[i] ^= paramBitArray.bits[i];
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.common.BitArray
 * JD-Core Version:    0.6.0
 */