package org.apache.sanselan.formats.tiff.fieldtypes;

import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.util.Debug;

public class FieldTypeFloat extends FieldType
{
  public FieldTypeFloat()
  {
    super(11, 4, "Float");
  }

  public Object getSimpleValue(TiffField paramTiffField)
  {
    if (paramTiffField.length == 1)
      return new Float(convertByteArrayToFloat(this.name + " (" + paramTiffField.tagInfo.name + ")", paramTiffField.valueOffsetBytes, paramTiffField.byteOrder));
    return convertByteArrayToFloatArray(this.name + " (" + paramTiffField.tagInfo.name + ")", getRawBytes(paramTiffField), 0, paramTiffField.length, paramTiffField.byteOrder);
  }

  public byte[] writeData(Object paramObject, int paramInt)
    throws ImageWriteException
  {
    if ((paramObject instanceof Float))
      return convertFloatToByteArray(((Float)paramObject).floatValue(), paramInt);
    if ((paramObject instanceof float[]))
      return convertFloatArrayToByteArray((float[])paramObject, paramInt);
    if ((paramObject instanceof Float[]))
    {
      Float[] arrayOfFloat = (Float[])paramObject;
      float[] arrayOfFloat1 = new float[arrayOfFloat.length];
      for (int i = 0; ; i++)
      {
        if (i >= arrayOfFloat1.length)
          return convertFloatArrayToByteArray(arrayOfFloat1, paramInt);
        arrayOfFloat1[i] = arrayOfFloat[i].floatValue();
      }
    }
    throw new ImageWriteException("Invalid data: " + paramObject + " (" + Debug.getType(paramObject) + ")");
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.fieldtypes.FieldTypeFloat
 * JD-Core Version:    0.6.0
 */