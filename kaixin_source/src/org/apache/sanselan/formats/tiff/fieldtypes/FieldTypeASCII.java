package org.apache.sanselan.formats.tiff.fieldtypes;

import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.formats.tiff.TiffField;

public class FieldTypeASCII extends FieldType
{
  public FieldTypeASCII(int paramInt, String paramString)
  {
    super(paramInt, 1, paramString);
  }

  public Object getSimpleValue(TiffField paramTiffField)
  {
    byte[] arrayOfByte = getRawBytes(paramTiffField);
    return new String(arrayOfByte, 0, -1 + arrayOfByte.length);
  }

  public byte[] writeData(Object paramObject, int paramInt)
    throws ImageWriteException
  {
    if ((paramObject instanceof byte[]));
    for (byte[] arrayOfByte1 = (byte[])paramObject; ; arrayOfByte1 = ((String)paramObject).getBytes())
    {
      byte[] arrayOfByte2 = new byte[1 + arrayOfByte1.length];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, arrayOfByte1.length);
      return arrayOfByte2;
      if (!(paramObject instanceof String))
        break;
    }
    throw new ImageWriteException("Unknown data type: " + paramObject);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.fieldtypes.FieldTypeASCII
 * JD-Core Version:    0.6.0
 */