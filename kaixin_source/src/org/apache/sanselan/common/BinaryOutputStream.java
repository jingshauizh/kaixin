package org.apache.sanselan.common;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.sanselan.ImageWriteException;

public class BinaryOutputStream extends OutputStream
  implements BinaryConstants
{
  private int byteOrder = 77;
  private int count = 0;
  protected boolean debug = false;
  private final OutputStream os;

  public BinaryOutputStream(OutputStream paramOutputStream)
  {
    this.os = paramOutputStream;
  }

  public BinaryOutputStream(OutputStream paramOutputStream, int paramInt)
  {
    this.byteOrder = paramInt;
    this.os = paramOutputStream;
  }

  private byte[] convertValueToByteArray(int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte = new byte[paramInt2];
    int j;
    if (this.byteOrder == 77)
    {
      j = 0;
      if (j < paramInt2);
    }
    while (true)
    {
      return arrayOfByte;
      arrayOfByte[j] = (byte)(0xFF & paramInt1 >> 8 * (-1 + (paramInt2 - j)));
      j++;
      break;
      for (int i = 0; i < paramInt2; i++)
        arrayOfByte[i] = (byte)(0xFF & paramInt1 >> i * 8);
    }
  }

  private final void writeNBytes(int paramInt1, int paramInt2)
    throws ImageWriteException, IOException
  {
    write(convertValueToByteArray(paramInt1, paramInt2));
  }

  public int getByteCount()
  {
    return this.count;
  }

  public int getByteOrder()
  {
    return this.byteOrder;
  }

  public final boolean getDebug()
  {
    return this.debug;
  }

  protected void setByteOrder(int paramInt)
  {
    this.byteOrder = paramInt;
  }

  protected void setByteOrder(int paramInt1, int paramInt2)
    throws ImageWriteException, IOException
  {
    if (paramInt1 != paramInt2)
      throw new ImageWriteException("Byte Order bytes don't match (" + paramInt1 + ", " + paramInt2 + ").");
    if (paramInt1 == 77)
    {
      this.byteOrder = paramInt1;
      return;
    }
    if (paramInt1 == 73)
    {
      this.byteOrder = paramInt1;
      return;
    }
    throw new ImageWriteException("Unknown Byte Order hint: " + paramInt1);
  }

  public final void setDebug(boolean paramBoolean)
  {
    this.debug = paramBoolean;
  }

  public void write(int paramInt)
    throws IOException
  {
    this.os.write(paramInt);
    this.count = (1 + this.count);
  }

  public final void write2ByteInteger(int paramInt)
    throws ImageWriteException, IOException
  {
    if (this.byteOrder == 77)
    {
      write(0xFF & paramInt >> 8);
      write(paramInt & 0xFF);
      return;
    }
    write(paramInt & 0xFF);
    write(0xFF & paramInt >> 8);
  }

  public final void write2Bytes(int paramInt)
    throws ImageWriteException, IOException
  {
    writeNBytes(paramInt, 2);
  }

  public final void write3Bytes(int paramInt)
    throws ImageWriteException, IOException
  {
    writeNBytes(paramInt, 3);
  }

  public final void write4ByteInteger(int paramInt)
    throws ImageWriteException, IOException
  {
    if (this.byteOrder == 77)
    {
      write(0xFF & paramInt >> 24);
      write(0xFF & paramInt >> 16);
      write(0xFF & paramInt >> 8);
      write(paramInt & 0xFF);
      return;
    }
    write(paramInt & 0xFF);
    write(0xFF & paramInt >> 8);
    write(0xFF & paramInt >> 16);
    write(0xFF & paramInt >> 24);
  }

  public final void write4Bytes(int paramInt)
    throws ImageWriteException, IOException
  {
    writeNBytes(paramInt, 4);
  }

  public final void writeByteArray(byte[] paramArrayOfByte)
    throws IOException
  {
    this.os.write(paramArrayOfByte, 0, paramArrayOfByte.length);
    this.count += paramArrayOfByte.length;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.BinaryOutputStream
 * JD-Core Version:    0.6.0
 */