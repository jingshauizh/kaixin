package org.apache.sanselan.common.mylzw;

import java.io.IOException;
import java.io.InputStream;
import org.apache.sanselan.common.BinaryConstants;

public class MyBitInputStream extends InputStream
  implements BinaryConstants
{
  private int bitCache = 0;
  private int bitsInCache = 0;
  private final int byteOrder;
  private long bytesRead = 0L;
  private final InputStream is;
  private boolean tiffLZWMode = false;

  public MyBitInputStream(InputStream paramInputStream, int paramInt)
  {
    this.byteOrder = paramInt;
    this.is = paramInputStream;
  }

  public void flushCache()
  {
    this.bitsInCache = 0;
    this.bitCache = 0;
  }

  public long getBytesRead()
  {
    return this.bytesRead;
  }

  public int read()
    throws IOException
  {
    return readBits(8);
  }

  public int readBits(int paramInt)
    throws IOException
  {
    int k;
    int m;
    if (this.bitsInCache >= paramInt)
    {
      k = -1 + (1 << paramInt);
      if (this.byteOrder != 77)
        break label205;
      m = k & this.bitCache >> this.bitsInCache - paramInt;
    }
    while (true)
    {
      int n = m;
      this.bitsInCache -= paramInt;
      this.bitCache = (-1 + (1 << this.bitsInCache) & this.bitCache);
      return n;
      int i = this.is.read();
      if (i < 0)
      {
        if (this.tiffLZWMode)
          return 257;
        return -1;
      }
      int j = i & 0xFF;
      if (this.byteOrder == 77);
      for (this.bitCache = (j | this.bitCache << 8); ; this.bitCache = (j << this.bitsInCache | this.bitCache))
      {
        this.bytesRead = (1L + this.bytesRead);
        this.bitsInCache = (8 + this.bitsInCache);
        break;
        if (this.byteOrder != 73)
          break label178;
      }
      label178: throw new IOException("Unknown byte order: " + this.byteOrder);
      label205: if (this.byteOrder != 73)
        break label236;
      m = k & this.bitCache;
      this.bitCache >>= paramInt;
    }
    label236: throw new IOException("Unknown byte order: " + this.byteOrder);
  }

  public void setTiffLZWMode()
  {
    this.tiffLZWMode = true;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.mylzw.MyBitInputStream
 * JD-Core Version:    0.6.0
 */