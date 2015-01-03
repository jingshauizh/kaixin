package org.apache.sanselan.common;

import java.io.IOException;
import java.io.InputStream;

public class BitInputStream extends InputStream
  implements BinaryConstants
{
  private long bytes_read = 0L;
  private int cache;
  private int cacheBitsRemaining = 0;
  private final InputStream is;

  public BitInputStream(InputStream paramInputStream)
  {
    this.is = paramInputStream;
  }

  public void flushCache()
  {
    this.cacheBitsRemaining = 0;
  }

  public long getBytesRead()
  {
    return this.bytes_read;
  }

  public int read()
    throws IOException
  {
    if (this.cacheBitsRemaining > 0)
      throw new IOException("BitInputStream: incomplete bit read");
    return this.is.read();
  }

  public final int readBits(int paramInt)
    throws IOException
  {
    int i;
    if (paramInt < 8)
    {
      if (this.cacheBitsRemaining == 0)
      {
        this.cache = this.is.read();
        this.cacheBitsRemaining = 8;
        this.bytes_read = (1L + this.bytes_read);
      }
      if (paramInt > this.cacheBitsRemaining)
        throw new IOException("BitInputStream: can't read bit fields across bytes");
      this.cacheBitsRemaining -= paramInt;
      i = this.cache >> this.cacheBitsRemaining;
    }
    switch (paramInt)
    {
    default:
      if (this.cacheBitsRemaining <= 0)
        break;
      throw new IOException("BitInputStream: incomplete bit read");
    case 1:
      return i & 0x1;
    case 2:
      return i & 0x3;
    case 3:
      return i & 0x7;
    case 4:
      return i & 0xF;
    case 5:
      return i & 0x1F;
    case 6:
      return i & 0x3F;
    case 7:
      return i & 0x7F;
    }
    if (paramInt == 8)
    {
      this.bytes_read = (1L + this.bytes_read);
      return this.is.read();
    }
    if (paramInt == 16)
    {
      this.bytes_read = (2L + this.bytes_read);
      return this.is.read() << 8 | this.is.read() << 0;
    }
    if (paramInt == 24)
    {
      this.bytes_read = (3L + this.bytes_read);
      return this.is.read() << 16 | this.is.read() << 8 | this.is.read() << 0;
    }
    if (paramInt == 32)
    {
      this.bytes_read = (4L + this.bytes_read);
      return this.is.read() << 24 | this.is.read() << 16 | this.is.read() << 8 | this.is.read() << 0;
    }
    throw new IOException("BitInputStream: unknown error");
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.BitInputStream
 * JD-Core Version:    0.6.0
 */