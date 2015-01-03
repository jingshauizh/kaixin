package org.apache.sanselan.common.mylzw;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.sanselan.common.BinaryConstants;

public class MyBitOutputStream extends OutputStream
  implements BinaryConstants
{
  private int bitCache = 0;
  private int bitsInCache = 0;
  private final int byteOrder;
  private int bytesWritten = 0;
  private final OutputStream os;

  public MyBitOutputStream(OutputStream paramOutputStream, int paramInt)
  {
    this.byteOrder = paramInt;
    this.os = paramOutputStream;
  }

  private void actualWrite(int paramInt)
    throws IOException
  {
    this.os.write(paramInt);
    this.bytesWritten = (1 + this.bytesWritten);
  }

  public void flushCache()
    throws IOException
  {
    int i;
    if (this.bitsInCache > 0)
    {
      i = -1 + (1 << this.bitsInCache) & this.bitCache;
      if (this.byteOrder != 77)
        break label59;
      int j = i << 8 - this.bitsInCache;
      this.os.write(j);
    }
    while (true)
    {
      this.bitsInCache = 0;
      this.bitCache = 0;
      return;
      label59: if (this.byteOrder != 73)
        continue;
      this.os.write(i);
    }
  }

  public int getBytesWritten()
  {
    int i = this.bytesWritten;
    if (this.bitsInCache > 0);
    for (int j = 1; ; j = 0)
      return j + i;
  }

  public void write(int paramInt)
    throws IOException
  {
    writeBits(paramInt, 8);
  }

  public void writeBits(int paramInt1, int paramInt2)
    throws IOException
  {
    int i = paramInt1 & -1 + (1 << paramInt2);
    if (this.byteOrder == 77)
      this.bitCache = (i | this.bitCache << paramInt2);
    while (true)
    {
      this.bitsInCache = (paramInt2 + this.bitsInCache);
      if (this.bitsInCache >= 8)
        break;
      return;
      if (this.byteOrder == 73)
      {
        this.bitCache |= i << this.bitsInCache;
        continue;
      }
      throw new IOException("Unknown byte order: " + this.byteOrder);
    }
    if (this.byteOrder == 77)
    {
      actualWrite(0xFF & this.bitCache >> -8 + this.bitsInCache);
      this.bitsInCache = (-8 + this.bitsInCache);
    }
    while (true)
    {
      this.bitCache = (-1 + (1 << this.bitsInCache) & this.bitCache);
      break;
      if (this.byteOrder != 73)
        continue;
      actualWrite(0xFF & this.bitCache);
      this.bitCache >>= 8;
      this.bitsInCache = (-8 + this.bitsInCache);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.mylzw.MyBitOutputStream
 * JD-Core Version:    0.6.0
 */