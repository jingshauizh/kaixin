package org.apache.sanselan.util;

import java.io.IOException;
import java.io.InputStream;

public class DebugInputStream extends InputStream
{
  private long bytes_read = 0L;
  private final InputStream is;

  public DebugInputStream(InputStream paramInputStream)
  {
    this.is = paramInputStream;
  }

  public int available()
    throws IOException
  {
    return this.is.available();
  }

  public void close()
    throws IOException
  {
    this.is.close();
  }

  public long getBytesRead()
  {
    return this.bytes_read;
  }

  public int read()
    throws IOException
  {
    int i = this.is.read();
    this.bytes_read = (1L + this.bytes_read);
    return i;
  }

  public long skip(long paramLong)
    throws IOException
  {
    long l = this.is.skip(paramLong);
    this.bytes_read = (paramLong + this.bytes_read);
    return l;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.util.DebugInputStream
 * JD-Core Version:    0.6.0
 */