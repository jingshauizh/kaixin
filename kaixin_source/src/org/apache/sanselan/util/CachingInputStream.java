package org.apache.sanselan.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CachingInputStream extends InputStream
{
  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
  private final InputStream is;

  public CachingInputStream(InputStream paramInputStream)
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

  public byte[] getCache()
  {
    return this.baos.toByteArray();
  }

  public int read()
    throws IOException
  {
    int i = this.is.read();
    this.baos.write(i);
    return i;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.util.CachingInputStream
 * JD-Core Version:    0.6.0
 */