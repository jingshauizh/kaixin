package org.apache.sanselan.util;

import java.io.IOException;
import java.io.OutputStream;

public class DebugOutputStream extends OutputStream
{
  private long count = 0L;
  private final OutputStream os;

  public DebugOutputStream(OutputStream paramOutputStream)
  {
    this.os = paramOutputStream;
  }

  public void close()
    throws IOException
  {
    this.os.close();
  }

  public long count()
  {
    return this.count;
  }

  public void flush()
    throws IOException
  {
    this.os.flush();
  }

  public void write(int paramInt)
    throws IOException
  {
    this.os.write(paramInt);
    this.count = (1L + this.count);
  }

  public void write(byte[] paramArrayOfByte)
    throws IOException
  {
    this.os.write(paramArrayOfByte);
    this.count += paramArrayOfByte.length;
  }

  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    this.os.write(paramArrayOfByte, paramInt1, paramInt2);
    this.count += paramInt2;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.util.DebugOutputStream
 * JD-Core Version:    0.6.0
 */