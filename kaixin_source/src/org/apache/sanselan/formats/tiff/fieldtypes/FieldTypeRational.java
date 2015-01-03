package org.apache.sanselan.formats.tiff.fieldtypes;

import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.common.RationalNumber;
import org.apache.sanselan.common.RationalNumberUtilities;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.util.Debug;

public class FieldTypeRational extends FieldType
{
  public FieldTypeRational(int paramInt, String paramString)
  {
    super(paramInt, 8, paramString);
  }

  public Object getSimpleValue(TiffField paramTiffField)
  {
    if (paramTiffField.length == 1)
      return convertByteArrayToRational(this.name + " (" + paramTiffField.tagInfo.name + ")", paramTiffField.oversizeValue, paramTiffField.byteOrder);
    return convertByteArrayToRationalArray(this.name + " (" + paramTiffField.tagInfo.name + ")", getRawBytes(paramTiffField), 0, paramTiffField.length, paramTiffField.byteOrder);
  }

  public byte[] writeData(int paramInt1, int paramInt2, int paramInt3)
    throws ImageWriteException
  {
    return writeData(new int[] { paramInt1 }, new int[] { paramInt2 }, paramInt3);
  }

  public byte[] writeData(Object paramObject, int paramInt)
    throws ImageWriteException
  {
    if ((paramObject instanceof RationalNumber))
      return convertRationalToByteArray((RationalNumber)paramObject, paramInt);
    if ((paramObject instanceof RationalNumber[]))
      return convertRationalArrayToByteArray((RationalNumber[])paramObject, paramInt);
    if ((paramObject instanceof Number))
      return convertRationalToByteArray(RationalNumberUtilities.getRationalNumber(((Number)paramObject).doubleValue()), paramInt);
    if ((paramObject instanceof Number[]))
    {
      Number[] arrayOfNumber = (Number[])paramObject;
      RationalNumber[] arrayOfRationalNumber2 = new RationalNumber[arrayOfNumber.length];
      for (int j = 0; ; j++)
      {
        if (j >= arrayOfNumber.length)
          return convertRationalArrayToByteArray(arrayOfRationalNumber2, paramInt);
        arrayOfRationalNumber2[j] = RationalNumberUtilities.getRationalNumber(arrayOfNumber[j].doubleValue());
      }
    }
    if ((paramObject instanceof double[]))
    {
      double[] arrayOfDouble = (double[])paramObject;
      RationalNumber[] arrayOfRationalNumber1 = new RationalNumber[arrayOfDouble.length];
      for (int i = 0; ; i++)
      {
        if (i >= arrayOfDouble.length)
          return convertRationalArrayToByteArray(arrayOfRationalNumber1, paramInt);
        arrayOfRationalNumber1[i] = RationalNumberUtilities.getRationalNumber(arrayOfDouble[i]);
      }
    }
    throw new ImageWriteException("Invalid data: " + paramObject + " (" + Debug.getType(paramObject) + ")");
  }

  public byte[] writeData(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt)
    throws ImageWriteException
  {
    return convertIntArrayToRationalArray(paramArrayOfInt1, paramArrayOfInt2, paramInt);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.fieldtypes.FieldTypeRational
 * JD-Core Version:    0.6.0
 */