package org.apache.sanselan.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class BitInputStreamFlexible extends InputStream
  implements BinaryConstants
{
  private long bytesRead = 0L;
  private int cache;
  private int cacheBitsRemaining = 0;
  private final InputStream is;

  public BitInputStreamFlexible(InputStream paramInputStream)
  {
    this.is = paramInputStream;
  }

  public void flushCache()
  {
    this.cacheBitsRemaining = 0;
  }

  public long getBytesRead()
  {
    return this.bytesRead;
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
    if (paramInt <= 32)
    {
      int i = this.cacheBitsRemaining;
      int j = 0;
      if (i > 0)
      {
        if (paramInt < this.cacheBitsRemaining)
          break label89;
        j = -1 + (1 << this.cacheBitsRemaining) & this.cache;
        paramInt -= this.cacheBitsRemaining;
        this.cacheBitsRemaining = 0;
      }
      while (true)
      {
        if (paramInt < 8)
        {
          if (paramInt <= 0)
            break;
          this.cache = this.is.read();
          if (this.cache < 0)
          {
            throw new IOException("couldn't read bits");
            label89: this.cacheBitsRemaining -= paramInt;
            j = -1 + (1 << paramInt) & this.cache >> this.cacheBitsRemaining;
            paramInt = 0;
            continue;
          }
        }
        else
        {
          this.cache = this.is.read();
          if (this.cache < 0)
            throw new IOException("couldn't read bits");
          System.out.println("cache 1: " + this.cache + " (" + Integer.toHexString(this.cache) + ", " + Integer.toBinaryString(this.cache) + ")");
          this.bytesRead = (1L + this.bytesRead);
          j = j << 8 | 0xFF & this.cache;
          paramInt -= 8;
          continue;
        }
        System.out.println("cache 2: " + this.cache + " (" + Integer.toHexString(this.cache) + ", " + Integer.toBinaryString(this.cache) + ")");
        this.bytesRead = (1L + this.bytesRead);
        this.cacheBitsRemaining = (8 - paramInt);
        j = j << paramInt | -1 + (1 << paramInt) & this.cache >> this.cacheBitsRemaining;
      }
      return j;
    }
    throw new IOException("BitInputStream: unknown error");
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.BitInputStreamFlexible
 * JD-Core Version:    0.6.0
 */