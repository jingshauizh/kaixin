package org.apache.sanselan.formats.tiff.constants;

import org.apache.sanselan.SanselanConstants;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldType;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldTypeASCII;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldTypeByte;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldTypeDouble;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldTypeFloat;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldTypeLong;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldTypeRational;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldTypeShort;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldTypeUnknown;

public abstract interface TiffFieldTypeConstants extends SanselanConstants
{
  public static final FieldType[] FIELD_TYPES;
  public static final FieldType[] FIELD_TYPE_ANY;
  public static final FieldTypeASCII FIELD_TYPE_ASCII;
  public static final FieldTypeByte FIELD_TYPE_BYTE = new FieldTypeByte(1, "Byte");
  public static final FieldType[] FIELD_TYPE_DESCRIPTION_ANY;
  public static final FieldType[] FIELD_TYPE_DESCRIPTION_ASCII;
  public static final FieldType[] FIELD_TYPE_DESCRIPTION_BYTE;
  public static final FieldType[] FIELD_TYPE_DESCRIPTION_BYTE_OR_SHORT;
  public static final FieldType[] FIELD_TYPE_DESCRIPTION_LONG;
  public static final FieldType[] FIELD_TYPE_DESCRIPTION_LONG_OR_SHORT;
  public static final FieldType[] FIELD_TYPE_DESCRIPTION_RATIONAL;
  public static final FieldType[] FIELD_TYPE_DESCRIPTION_SHORT;
  public static final FieldType[] FIELD_TYPE_DESCRIPTION_SHORT_OR_LONG;
  public static final FieldType[] FIELD_TYPE_DESCRIPTION_UNKNOWN;
  public static final FieldType FIELD_TYPE_DOUBLE;
  public static final FieldType FIELD_TYPE_FLOAT;
  public static final FieldTypeLong FIELD_TYPE_LONG;
  public static final FieldTypeRational FIELD_TYPE_RATIONAL;
  public static final FieldType FIELD_TYPE_SBYTE;
  public static final FieldTypeShort FIELD_TYPE_SHORT;
  public static final FieldType FIELD_TYPE_SLONG;
  public static final FieldType FIELD_TYPE_SRATIONAL;
  public static final FieldType FIELD_TYPE_SSHORT;
  public static final FieldType FIELD_TYPE_UNDEFINED;
  public static final FieldType FIELD_TYPE_UNKNOWN;

  static
  {
    FIELD_TYPE_ASCII = new FieldTypeASCII(2, "ASCII");
    FIELD_TYPE_SHORT = new FieldTypeShort(3, "Short");
    FIELD_TYPE_LONG = new FieldTypeLong(4, "Long");
    FIELD_TYPE_RATIONAL = new FieldTypeRational(5, "Rational");
    FIELD_TYPE_SBYTE = new FieldTypeByte(6, "SByte");
    FIELD_TYPE_UNDEFINED = new FieldTypeByte(7, "Undefined");
    FIELD_TYPE_SSHORT = new FieldTypeShort(8, "SShort");
    FIELD_TYPE_SLONG = new FieldTypeLong(9, "SLong");
    FIELD_TYPE_SRATIONAL = new FieldTypeRational(10, "SRational");
    FIELD_TYPE_FLOAT = new FieldTypeFloat();
    FIELD_TYPE_DOUBLE = new FieldTypeDouble();
    FIELD_TYPE_UNKNOWN = new FieldTypeUnknown();
    FieldType[] arrayOfFieldType1 = new FieldType[12];
    arrayOfFieldType1[0] = FIELD_TYPE_BYTE;
    arrayOfFieldType1[1] = FIELD_TYPE_ASCII;
    arrayOfFieldType1[2] = FIELD_TYPE_SHORT;
    arrayOfFieldType1[3] = FIELD_TYPE_LONG;
    arrayOfFieldType1[4] = FIELD_TYPE_RATIONAL;
    arrayOfFieldType1[5] = FIELD_TYPE_SBYTE;
    arrayOfFieldType1[6] = FIELD_TYPE_UNDEFINED;
    arrayOfFieldType1[7] = FIELD_TYPE_SSHORT;
    arrayOfFieldType1[8] = FIELD_TYPE_SLONG;
    arrayOfFieldType1[9] = FIELD_TYPE_SRATIONAL;
    arrayOfFieldType1[10] = FIELD_TYPE_FLOAT;
    arrayOfFieldType1[11] = FIELD_TYPE_DOUBLE;
    FIELD_TYPES = arrayOfFieldType1;
    FIELD_TYPE_ANY = FIELD_TYPES;
    FieldType[] arrayOfFieldType2 = new FieldType[1];
    arrayOfFieldType2[0] = FIELD_TYPE_LONG;
    FIELD_TYPE_DESCRIPTION_LONG = arrayOfFieldType2;
    FieldType[] arrayOfFieldType3 = new FieldType[1];
    arrayOfFieldType3[0] = FIELD_TYPE_SHORT;
    FIELD_TYPE_DESCRIPTION_SHORT = arrayOfFieldType3;
    FieldType[] arrayOfFieldType4 = new FieldType[2];
    arrayOfFieldType4[0] = FIELD_TYPE_SHORT;
    arrayOfFieldType4[1] = FIELD_TYPE_LONG;
    FIELD_TYPE_DESCRIPTION_SHORT_OR_LONG = arrayOfFieldType4;
    FieldType[] arrayOfFieldType5 = new FieldType[1];
    arrayOfFieldType5[0] = FIELD_TYPE_ASCII;
    FIELD_TYPE_DESCRIPTION_ASCII = arrayOfFieldType5;
    FieldType[] arrayOfFieldType6 = new FieldType[2];
    arrayOfFieldType6[0] = FIELD_TYPE_SHORT;
    arrayOfFieldType6[1] = FIELD_TYPE_LONG;
    FIELD_TYPE_DESCRIPTION_LONG_OR_SHORT = arrayOfFieldType6;
    FieldType[] arrayOfFieldType7 = new FieldType[1];
    arrayOfFieldType7[0] = FIELD_TYPE_RATIONAL;
    FIELD_TYPE_DESCRIPTION_RATIONAL = arrayOfFieldType7;
    FieldType[] arrayOfFieldType8 = new FieldType[2];
    arrayOfFieldType8[0] = FIELD_TYPE_SHORT;
    arrayOfFieldType8[1] = FIELD_TYPE_BYTE;
    FIELD_TYPE_DESCRIPTION_BYTE_OR_SHORT = arrayOfFieldType8;
    FieldType[] arrayOfFieldType9 = new FieldType[1];
    arrayOfFieldType9[0] = FIELD_TYPE_BYTE;
    FIELD_TYPE_DESCRIPTION_BYTE = arrayOfFieldType9;
    FIELD_TYPE_DESCRIPTION_ANY = FIELD_TYPE_ANY;
    FIELD_TYPE_DESCRIPTION_UNKNOWN = null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.constants.TiffFieldTypeConstants
 * JD-Core Version:    0.6.0
 */