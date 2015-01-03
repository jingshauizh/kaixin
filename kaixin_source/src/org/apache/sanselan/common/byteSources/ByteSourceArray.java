package org.apache.sanselan.common.byteSources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteSourceArray extends ByteSource
{
  private final byte[] bytes;

  public ByteSourceArray(String paramString, byte[] paramArrayOfByte)
  {
    super(paramString);
    this.bytes = paramArrayOfByte;
  }

  public ByteSourceArray(byte[] paramArrayOfByte)
  {
    super(null);
    this.bytes = paramArrayOfByte;
  }

  public byte[] getAll()
    throws IOException
  {
    return this.bytes;
  }

  public byte[] getBlock(int paramInt1, int paramInt2)
    throws IOException
  {
    if (paramInt1 + paramInt2 > this.bytes.length)
      throw new IOException("Could not read block (block start: " + paramInt1 + ", block length: " + paramInt2 + ", data length: " + this.bytes.length + ").");
    byte[] arrayOfByte = new byte[paramInt2];
    System.arraycopy(this.bytes, paramInt1, arrayOfByte, 0, paramInt2);
    return arrayOfByte;
  }

  public String getDescription()
  {
    return this.bytes.length + " byte array";
  }

  public InputStream getInputStream()
  {
    return new ByteArrayInputStream(this.bytes);
  }

  public long getLength()
  {
    return this.bytes.length;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.byteSources.ByteSourceArray
 * JD-Core Version:    0.6.0
 */