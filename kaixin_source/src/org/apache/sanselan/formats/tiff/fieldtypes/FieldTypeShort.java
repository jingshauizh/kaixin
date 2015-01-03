package org.apache.sanselan.formats.tiff.fieldtypes;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.util.Debug;

public class FieldTypeShort extends FieldType
{
  public FieldTypeShort(int paramInt, String paramString)
  {
    super(paramInt, 2, paramString);
  }

  public Object getSimpleValue(TiffField paramTiffField)
    throws ImageReadException
  {
    if (paramTiffField.length == 1)
      return new Integer(convertByteArrayToShort(this.name + " (" + paramTiffField.tagInfo.name + ")", paramTiffField.valueOffsetBytes, paramTiffField.byteOrder));
    return convertByteArrayToShortArray(this.name + " (" + paramTiffField.tagInfo.name + ")", getRawBytes(paramTiffField), 0, paramTiffField.length, paramTiffField.byteOrder);
  }

  public byte[] writeData(Object paramObject, int paramInt)
    throws ImageWriteException
  {
    if ((paramObject instanceof Integer))
    {
      int[] arrayOfInt2 = new int[1];
      arrayOfInt2[0] = ((Integer)paramObject).intValue();
      return convertShortArrayToByteArray(arrayOfInt2, paramInt);
    }
    if ((paramObject instanceof int[]))
      return convertShortArrayToByteArray((int[])paramObject, paramInt);
    if ((paramObject instanceof Integer[]))
    {
      Integer[] arrayOfInteger = (Integer[])paramObject;
      int[] arrayOfInt1 = new int[arrayOfInteger.length];
      for (int i = 0; ; i++)
      {
        if (i >= arrayOfInt1.length)
          return convertShortArrayToByteArray(arrayOfInt1, paramInt);
        arrayOfInt1[i] = arrayOfInteger[i].intValue();
      }
    }
    throw new ImageWriteException("Invalid data: " + paramObject + " (" + Debug.getType(paramObject) + ")");
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.fieldtypes.FieldTypeShort
 * JD-Core Version:    0.6.0
 */