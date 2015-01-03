package org.apache.sanselan.common;

import java.io.IOException;
import java.io.InputStream;
import org.apache.sanselan.ImageReadException;

public class BinaryFileParser extends BinaryFileFunctions
{
  private int byteOrder = 77;

  public BinaryFileParser()
  {
  }

  public BinaryFileParser(int paramInt)
  {
    this.byteOrder = paramInt;
  }

  public static boolean byteArrayHasPrefix(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    if ((paramArrayOfByte1 == null) || (paramArrayOfByte1.length < paramArrayOfByte2.length))
      return false;
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfByte2.length)
        return true;
      if (paramArrayOfByte1[i] != paramArrayOfByte2[i])
        break;
    }
  }

  protected final int convertByteArrayToInt(String paramString, int paramInt, byte[] paramArrayOfByte)
  {
    return convertByteArrayToInt(paramString, paramArrayOfByte, paramInt, this.byteOrder);
  }

  protected final int convertByteArrayToInt(String paramString, byte[] paramArrayOfByte)
  {
    return convertByteArrayToInt(paramString, paramArrayOfByte, this.byteOrder);
  }

  public final int convertByteArrayToShort(String paramString, int paramInt, byte[] paramArrayOfByte)
    throws ImageReadException
  {
    return convertByteArrayToShort(paramString, paramInt, paramArrayOfByte, this.byteOrder);
  }

  public final int convertByteArrayToShort(String paramString, byte[] paramArrayOfByte)
    throws ImageReadException
  {
    return convertByteArrayToShort(paramString, paramArrayOfByte, this.byteOrder);
  }

  protected int getByteOrder()
  {
    return this.byteOrder;
  }

  protected final byte[] int2ToByteArray(int paramInt)
  {
    return int2ToByteArray(paramInt, this.byteOrder);
  }

  public final int read2Bytes(String paramString1, InputStream paramInputStream, String paramString2)
    throws ImageReadException, IOException
  {
    return read2Bytes(paramString1, paramInputStream, paramString2, this.byteOrder);
  }

  public final int read3Bytes(String paramString1, InputStream paramInputStream, String paramString2)
    throws ImageReadException, IOException
  {
    return read3Bytes(paramString1, paramInputStream, paramString2, this.byteOrder);
  }

  public final int read4Bytes(String paramString1, InputStream paramInputStream, String paramString2)
    throws ImageReadException, IOException
  {
    return read4Bytes(paramString1, paramInputStream, paramString2, this.byteOrder);
  }

  protected void setByteOrder(int paramInt)
  {
    this.byteOrder = paramInt;
  }

  protected void setByteOrder(int paramInt1, int paramInt2)
    throws ImageReadException, IOException
  {
    if (paramInt1 != paramInt2)
      throw new ImageReadException("Byte Order bytes don't match (" + paramInt1 + ", " + paramInt2 + ").");
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
    throw new ImageReadException("Unknown Byte Order hint: " + paramInt1);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.BinaryFileParser
 * JD-Core Version:    0.6.0
 */