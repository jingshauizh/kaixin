package org.apache.sanselan.common.byteSources;

import java.io.IOException;
import java.io.InputStream;
import org.apache.sanselan.common.BinaryFileFunctions;

public abstract class ByteSource extends BinaryFileFunctions
{
  protected final String filename;

  public ByteSource(String paramString)
  {
    this.filename = paramString;
  }

  public abstract byte[] getAll()
    throws IOException;

  public abstract byte[] getBlock(int paramInt1, int paramInt2)
    throws IOException;

  public abstract String getDescription();

  public final String getFilename()
  {
    return this.filename;
  }

  public abstract InputStream getInputStream()
    throws IOException;

  public final InputStream getInputStream(int paramInt)
    throws IOException
  {
    InputStream localInputStream = getInputStream();
    skipBytes(localInputStream, paramInt);
    return localInputStream;
  }

  public abstract long getLength()
    throws IOException;
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.byteSources.ByteSource
 * JD-Core Version:    0.6.0
 */