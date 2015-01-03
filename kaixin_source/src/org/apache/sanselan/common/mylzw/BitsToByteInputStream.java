package org.apache.sanselan.common.mylzw;

import java.io.IOException;
import java.io.InputStream;

public class BitsToByteInputStream extends InputStream
{
  private final int desiredDepth;
  private final MyBitInputStream is;

  public BitsToByteInputStream(MyBitInputStream paramMyBitInputStream, int paramInt)
  {
    this.is = paramMyBitInputStream;
    this.desiredDepth = paramInt;
  }

  public int read()
    throws IOException
  {
    return readBits(8);
  }

  public int readBits(int paramInt)
    throws IOException
  {
    int i = this.is.readBits(paramInt);
    if (paramInt < this.desiredDepth)
      i <<= this.desiredDepth - paramInt;
    do
      return i;
    while (paramInt <= this.desiredDepth);
    return i >> paramInt - this.desiredDepth;
  }

  public int[] readBitsArray(int paramInt1, int paramInt2)
    throws IOException
  {
    int[] arrayOfInt = new int[paramInt2];
    for (int i = 0; ; i++)
    {
      if (i >= paramInt2)
        return arrayOfInt;
      arrayOfInt[i] = readBits(paramInt1);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.mylzw.BitsToByteInputStream
 * JD-Core Version:    0.6.0
 */