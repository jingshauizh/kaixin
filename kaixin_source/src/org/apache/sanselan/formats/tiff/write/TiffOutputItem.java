package org.apache.sanselan.formats.tiff.write;

import java.io.IOException;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.common.BinaryOutputStream;
import org.apache.sanselan.formats.tiff.constants.AllTagConstants;

abstract class TiffOutputItem
  implements AllTagConstants
{
  public static final int UNDEFINED_VALUE = -1;
  private int offset = -1;

  public abstract String getItemDescription();

  public abstract int getItemLength();

  protected int getOffset()
  {
    return this.offset;
  }

  protected void setOffset(int paramInt)
  {
    this.offset = paramInt;
  }

  public abstract void writeItem(BinaryOutputStream paramBinaryOutputStream)
    throws IOException, ImageWriteException;

  public static class Value extends TiffOutputItem
  {
    private final byte[] bytes;
    private final String name;

    public Value(String paramString, byte[] paramArrayOfByte)
    {
      this.name = paramString;
      this.bytes = paramArrayOfByte;
    }

    public String getItemDescription()
    {
      return this.name;
    }

    public int getItemLength()
    {
      return this.bytes.length;
    }

    public void updateValue(byte[] paramArrayOfByte)
      throws ImageWriteException
    {
      if (this.bytes.length != paramArrayOfByte.length)
        throw new ImageWriteException("Updated data size mismatch: " + this.bytes.length + " vs. " + paramArrayOfByte.length);
      System.arraycopy(paramArrayOfByte, 0, this.bytes, 0, paramArrayOfByte.length);
    }

    public void writeItem(BinaryOutputStream paramBinaryOutputStream)
      throws IOException, ImageWriteException
    {
      paramBinaryOutputStream.write(this.bytes);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.write.TiffOutputItem
 * JD-Core Version:    0.6.0
 */