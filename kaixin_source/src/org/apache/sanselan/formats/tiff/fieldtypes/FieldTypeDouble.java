package org.apache.sanselan.formats.tiff.fieldtypes;

import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.util.Debug;

public class FieldTypeDouble extends FieldType
{
  public FieldTypeDouble()
  {
    super(12, 8, "Double");
  }

  public Object getSimpleValue(TiffField paramTiffField)
  {
    return "?";
  }

  public byte[] writeData(Object paramObject, int paramInt)
    throws ImageWriteException
  {
    if ((paramObject instanceof Double))
      return convertDoubleToByteArray(((Double)paramObject).doubleValue(), paramInt);
    if ((paramObject instanceof double[]))
      return convertDoubleArrayToByteArray((double[])paramObject, paramInt);
    if ((paramObject instanceof Double[]))
    {
      Double[] arrayOfDouble = (Double[])paramObject;
      double[] arrayOfDouble1 = new double[arrayOfDouble.length];
      for (int i = 0; ; i++)
      {
        if (i >= arrayOfDouble1.length)
          return convertDoubleArrayToByteArray(arrayOfDouble1, paramInt);
        arrayOfDouble1[i] = arrayOfDouble[i].doubleValue();
      }
    }
    throw new ImageWriteException("Invalid data: " + paramObject + " (" + Debug.getType(paramObject) + ")");
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.fieldtypes.FieldTypeDouble
 * JD-Core Version:    0.6.0
 */