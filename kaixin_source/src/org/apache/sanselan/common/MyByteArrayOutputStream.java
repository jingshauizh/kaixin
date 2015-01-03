package org.apache.sanselan.common;

import java.io.IOException;
import java.io.OutputStream;

public class MyByteArrayOutputStream extends OutputStream
{
  private final byte[] bytes;
  private int count = 0;

  public MyByteArrayOutputStream(int paramInt)
  {
    this.bytes = new byte[paramInt];
  }

  public int getBytesWritten()
  {
    return this.count;
  }

  public byte[] toByteArray()
  {
    if (this.count < this.bytes.length)
    {
      byte[] arrayOfByte = new byte[this.count];
      System.arraycopy(this.bytes, 0, arrayOfByte, 0, this.count);
      return arrayOfByte;
    }
    return this.bytes;
  }

  public void write(int paramInt)
    throws IOException
  {
    if (this.count >= this.bytes.length)
      throw new IOException("Write exceeded expected length (" + this.count + ", " + this.bytes.length + ")");
    this.bytes[this.count] = (byte)paramInt;
    this.count = (1 + this.count);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.MyByteArrayOutputStream
 * JD-Core Version:    0.6.0
 */