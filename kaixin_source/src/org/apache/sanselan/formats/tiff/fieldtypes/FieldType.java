package org.apache.sanselan.formats.tiff.fieldtypes;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.common.BinaryFileFunctions;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;

public abstract class FieldType extends BinaryFileFunctions
  implements TiffConstants
{
  public final int length;
  public final String name;
  public final int type;

  public FieldType(int paramInt1, int paramInt2, String paramString)
  {
    this.type = paramInt1;
    this.length = paramInt2;
    this.name = paramString;
  }

  public static final byte[] getStubLocalValue()
  {
    return new byte[4];
  }

  public int getBytesLength(TiffField paramTiffField)
    throws ImageReadException
  {
    if (this.length < 1)
      throw new ImageReadException("Unknown field type");
    return this.length * paramTiffField.length;
  }

  public String getDisplayValue(TiffField paramTiffField)
    throws ImageReadException
  {
    Object localObject = getSimpleValue(paramTiffField);
    if (localObject == null)
      return "NULL";
    return localObject.toString();
  }

  public final byte[] getRawBytes(TiffField paramTiffField)
  {
    if (isLocalValue(paramTiffField))
    {
      int i = this.length * paramTiffField.length;
      byte[] arrayOfByte = new byte[i];
      System.arraycopy(paramTiffField.valueOffsetBytes, 0, arrayOfByte, 0, i);
      return arrayOfByte;
    }
    return paramTiffField.oversizeValue;
  }

  public abstract Object getSimpleValue(TiffField paramTiffField)
    throws ImageReadException;

  public final byte[] getStubValue(int paramInt)
  {
    return new byte[paramInt * this.length];
  }

  public boolean isLocalValue(TiffField paramTiffField)
  {
    return (this.length > 0) && (this.length * paramTiffField.length <= 4);
  }

  public String toString()
  {
    return "[" + getClass().getName() + ". type: " + this.type + ", name: " + this.name + ", length: " + this.length + "]";
  }

  public abstract byte[] writeData(Object paramObject, int paramInt)
    throws ImageWriteException;
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.fieldtypes.FieldType
 * JD-Core Version:    0.6.0
 */