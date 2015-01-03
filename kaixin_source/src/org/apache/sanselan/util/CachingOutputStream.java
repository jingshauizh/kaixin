package org.apache.sanselan.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CachingOutputStream extends OutputStream
{
  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
  private final OutputStream os;

  public CachingOutputStream(OutputStream paramOutputStream)
  {
    this.os = paramOutputStream;
  }

  public void close()
    throws IOException
  {
    this.os.close();
  }

  public void flush()
    throws IOException
  {
    this.os.flush();
  }

  public byte[] getCache()
  {
    return this.baos.toByteArray();
  }

  public void write(int paramInt)
    throws IOException
  {
    this.os.write(paramInt);
    this.baos.write(paramInt);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.util.CachingOutputStream
 * JD-Core Version:    0.6.0
 */