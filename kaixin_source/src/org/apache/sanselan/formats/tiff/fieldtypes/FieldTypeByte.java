package org.apache.sanselan.formats.tiff.fieldtypes;

import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.util.Debug;

public class FieldTypeByte extends FieldType
{
  public FieldTypeByte(int paramInt, String paramString)
  {
    super(paramInt, 1, paramString);
  }

  public Object getSimpleValue(TiffField paramTiffField)
  {
    if (paramTiffField.length == 1)
      return new Byte(paramTiffField.valueOffsetBytes[0]);
    return getRawBytes(paramTiffField);
  }

  public byte[] writeData(Object paramObject, int paramInt)
    throws ImageWriteException
  {
    if ((paramObject instanceof Byte))
    {
      byte[] arrayOfByte = new byte[1];
      arrayOfByte[0] = ((Byte)paramObject).byteValue();
      return arrayOfByte;
    }
    if ((paramObject instanceof byte[]))
      return (byte[])paramObject;
    throw new ImageWriteException("Invalid data: " + paramObject + " (" + Debug.getType(paramObject) + ")");
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.fieldtypes.FieldTypeByte
 * JD-Core Version:    0.6.0
 */