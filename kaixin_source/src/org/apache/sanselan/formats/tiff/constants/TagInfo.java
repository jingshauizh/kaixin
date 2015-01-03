package org.apache.sanselan.formats.tiff.constants;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.common.BinaryFileFunctions;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldType;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldTypeASCII;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldTypeByte;
import org.apache.sanselan.util.Debug;

public class TagInfo
  implements TiffDirectoryConstants, TiffFieldTypeConstants
{
  protected static final int LENGTH_UNKNOWN = -1;
  public final FieldType[] dataTypes;
  public final TiffDirectoryConstants.ExifDirectoryType directoryType;
  public final int length;
  public final String name;
  public final int tag;

  public TagInfo(String paramString, int paramInt, FieldType paramFieldType)
  {
    this(paramString, paramInt, paramFieldType, -1, EXIF_DIRECTORY_UNKNOWN);
  }

  public TagInfo(String paramString, int paramInt1, FieldType paramFieldType, int paramInt2)
  {
    this(paramString, paramInt1, new FieldType[] { paramFieldType }, paramInt2, EXIF_DIRECTORY_UNKNOWN);
  }

  public TagInfo(String paramString, int paramInt1, FieldType paramFieldType, int paramInt2, TiffDirectoryConstants.ExifDirectoryType paramExifDirectoryType)
  {
    this(paramString, paramInt1, new FieldType[] { paramFieldType }, paramInt2, paramExifDirectoryType);
  }

  public TagInfo(String paramString1, int paramInt, FieldType paramFieldType, String paramString2)
  {
    this(paramString1, paramInt, new FieldType[] { paramFieldType }, -1, EXIF_DIRECTORY_UNKNOWN);
  }

  public TagInfo(String paramString1, int paramInt1, FieldType[] paramArrayOfFieldType, int paramInt2, String paramString2)
  {
    this(paramString1, paramInt1, paramArrayOfFieldType, paramInt2, EXIF_DIRECTORY_UNKNOWN);
  }

  public TagInfo(String paramString, int paramInt1, FieldType[] paramArrayOfFieldType, int paramInt2, TiffDirectoryConstants.ExifDirectoryType paramExifDirectoryType)
  {
    this.name = paramString;
    this.tag = paramInt1;
    this.dataTypes = paramArrayOfFieldType;
    this.length = paramInt2;
    this.directoryType = paramExifDirectoryType;
  }

  public TagInfo(String paramString1, int paramInt, FieldType[] paramArrayOfFieldType, String paramString2)
  {
    this(paramString1, paramInt, paramArrayOfFieldType, -1, EXIF_DIRECTORY_UNKNOWN);
  }

  public byte[] encodeValue(FieldType paramFieldType, Object paramObject, int paramInt)
    throws ImageWriteException
  {
    return paramFieldType.writeData(paramObject, paramInt);
  }

  public String getDescription()
  {
    return this.tag + " (0x" + Integer.toHexString(this.tag) + ": " + this.name + "): ";
  }

  public Object getValue(TiffField paramTiffField)
    throws ImageReadException
  {
    return paramTiffField.fieldType.getSimpleValue(paramTiffField);
  }

  public boolean isDate()
  {
    return false;
  }

  public boolean isOffset()
  {
    return false;
  }

  public boolean isText()
  {
    return false;
  }

  public boolean isUnknown()
  {
    return false;
  }

  public String toString()
  {
    return "[TagInfo. tag: " + this.tag + " (0x" + Integer.toHexString(this.tag) + ", name: " + this.name + "]";
  }

  public static class Date extends TagInfo
  {
    private static final DateFormat DATE_FORMAT_1 = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
    private static final DateFormat DATE_FORMAT_2 = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");

    public Date(String paramString, int paramInt1, FieldType paramFieldType, int paramInt2)
    {
      super(paramInt1, paramFieldType, paramInt2);
    }

    public byte[] encodeValue(FieldType paramFieldType, Object paramObject, int paramInt)
      throws ImageWriteException
    {
      throw new ImageWriteException("date encode value: " + paramObject + " (" + Debug.getType(paramObject) + ")");
    }

    public Object getValue(TiffField paramTiffField)
      throws ImageReadException
    {
      Object localObject = paramTiffField.fieldType.getSimpleValue(paramTiffField);
      String str = (String)localObject;
      try
      {
        Date localDate2 = DATE_FORMAT_1.parse(str);
        return localDate2;
      }
      catch (Exception localException1)
      {
        try
        {
          Date localDate1 = DATE_FORMAT_2.parse(str);
          return localDate1;
        }
        catch (Exception localException2)
        {
          Debug.debug(localException2);
        }
      }
      return localObject;
    }

    public boolean isDate()
    {
      return true;
    }

    public String toString()
    {
      return "[TagInfo. tag: " + this.tag + ", name: " + this.name + " (data)" + "]";
    }
  }

  public static class Offset extends TagInfo
  {
    public Offset(String paramString, int paramInt1, FieldType paramFieldType, int paramInt2)
    {
      super(paramInt1, paramFieldType, paramInt2);
    }

    public Offset(String paramString, int paramInt1, FieldType paramFieldType, int paramInt2, TiffDirectoryConstants.ExifDirectoryType paramExifDirectoryType)
    {
      super(paramInt1, paramFieldType, paramInt2, paramExifDirectoryType);
    }

    public Offset(String paramString, int paramInt1, FieldType[] paramArrayOfFieldType, int paramInt2, TiffDirectoryConstants.ExifDirectoryType paramExifDirectoryType)
    {
      super(paramInt1, paramArrayOfFieldType, paramInt2, paramExifDirectoryType);
    }

    public boolean isOffset()
    {
      return true;
    }
  }

  public static final class Text extends TagInfo
  {
    private static final TextEncoding[] TEXT_ENCODINGS;
    private static final TextEncoding TEXT_ENCODING_ASCII;
    private static final TextEncoding TEXT_ENCODING_JIS;
    private static final TextEncoding TEXT_ENCODING_UNDEFINED;
    private static final TextEncoding TEXT_ENCODING_UNICODE;

    static
    {
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = 65;
      arrayOfByte1[1] = 83;
      arrayOfByte1[2] = 67;
      arrayOfByte1[3] = 73;
      arrayOfByte1[4] = 73;
      TEXT_ENCODING_ASCII = new TextEncoding(arrayOfByte1, "US-ASCII");
      byte[] arrayOfByte2 = new byte[8];
      arrayOfByte2[0] = 74;
      arrayOfByte2[1] = 73;
      arrayOfByte2[2] = 83;
      TEXT_ENCODING_JIS = new TextEncoding(arrayOfByte2, "JIS");
      byte[] arrayOfByte3 = new byte[8];
      arrayOfByte3[0] = 85;
      arrayOfByte3[1] = 78;
      arrayOfByte3[2] = 73;
      arrayOfByte3[3] = 67;
      arrayOfByte3[4] = 79;
      arrayOfByte3[5] = 68;
      arrayOfByte3[6] = 69;
      TEXT_ENCODING_UNICODE = new TextEncoding(arrayOfByte3, "UTF-8");
      TEXT_ENCODING_UNDEFINED = new TextEncoding(new byte[8], "ISO-8859-1");
      TextEncoding[] arrayOfTextEncoding = new TextEncoding[4];
      arrayOfTextEncoding[0] = TEXT_ENCODING_ASCII;
      arrayOfTextEncoding[1] = TEXT_ENCODING_JIS;
      arrayOfTextEncoding[2] = TEXT_ENCODING_UNICODE;
      arrayOfTextEncoding[3] = TEXT_ENCODING_UNDEFINED;
      TEXT_ENCODINGS = arrayOfTextEncoding;
    }

    public Text(String paramString, int paramInt1, FieldType paramFieldType, int paramInt2, TiffDirectoryConstants.ExifDirectoryType paramExifDirectoryType)
    {
      super(paramInt1, paramFieldType, paramInt2, paramExifDirectoryType);
    }

    public Text(String paramString, int paramInt1, FieldType[] paramArrayOfFieldType, int paramInt2, TiffDirectoryConstants.ExifDirectoryType paramExifDirectoryType)
    {
      super(paramInt1, paramArrayOfFieldType, paramInt2, paramExifDirectoryType);
    }

    public byte[] encodeValue(FieldType paramFieldType, Object paramObject, int paramInt)
      throws ImageWriteException
    {
      if (!(paramObject instanceof String))
        throw new ImageWriteException("Text value not String: " + paramObject + " (" + Debug.getType(paramObject) + ")");
      String str = (String)paramObject;
      try
      {
        byte[] arrayOfByte1 = str.getBytes(TEXT_ENCODING_ASCII.encodingName);
        if (new String(arrayOfByte1, TEXT_ENCODING_ASCII.encodingName).equals(str))
        {
          byte[] arrayOfByte4 = new byte[arrayOfByte1.length + TEXT_ENCODING_ASCII.prefix.length];
          System.arraycopy(TEXT_ENCODING_ASCII.prefix, 0, arrayOfByte4, 0, TEXT_ENCODING_ASCII.prefix.length);
          System.arraycopy(arrayOfByte1, 0, arrayOfByte4, TEXT_ENCODING_ASCII.prefix.length, arrayOfByte1.length);
          return arrayOfByte4;
        }
        byte[] arrayOfByte2 = str.getBytes(TEXT_ENCODING_UNICODE.encodingName);
        byte[] arrayOfByte3 = new byte[arrayOfByte2.length + TEXT_ENCODING_UNICODE.prefix.length];
        System.arraycopy(TEXT_ENCODING_UNICODE.prefix, 0, arrayOfByte3, 0, TEXT_ENCODING_UNICODE.prefix.length);
        System.arraycopy(arrayOfByte2, 0, arrayOfByte3, TEXT_ENCODING_UNICODE.prefix.length, arrayOfByte2.length);
        return arrayOfByte3;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
      }
      throw new ImageWriteException(localUnsupportedEncodingException.getMessage(), localUnsupportedEncodingException);
    }

    public Object getValue(TiffField paramTiffField)
      throws ImageReadException
    {
      if (paramTiffField.type == FIELD_TYPE_ASCII.type)
        return FIELD_TYPE_ASCII.getSimpleValue(paramTiffField);
      if (paramTiffField.type == FIELD_TYPE_UNDEFINED.type);
      byte[] arrayOfByte;
      while (true)
      {
        arrayOfByte = paramTiffField.fieldType.getRawBytes(paramTiffField);
        if (arrayOfByte.length >= 8)
          break;
        try
        {
          String str3 = new String(arrayOfByte, "US-ASCII");
          return str3;
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException3)
        {
          throw new ImageReadException("Text field missing encoding prefix.");
        }
        if (paramTiffField.type == FIELD_TYPE_BYTE.type)
          continue;
        Debug.debug("entry.type", paramTiffField.type);
        Debug.debug("entry.directoryType", paramTiffField.directoryType);
        Debug.debug("entry.type", paramTiffField.getDescriptionWithoutValue());
        Debug.debug("entry.type", paramTiffField.fieldType);
        throw new ImageReadException("Text field not encoded as bytes.");
      }
      for (int i = 0; ; i++)
      {
        if (i >= TEXT_ENCODINGS.length)
          try
          {
            String str2 = new String(arrayOfByte, "US-ASCII");
            return str2;
          }
          catch (UnsupportedEncodingException localUnsupportedEncodingException2)
          {
            throw new ImageReadException("Unknown text encoding prefix.");
          }
        TextEncoding localTextEncoding = TEXT_ENCODINGS[i];
        if (!BinaryFileFunctions.compareBytes(arrayOfByte, 0, localTextEncoding.prefix, 0, localTextEncoding.prefix.length))
          continue;
        try
        {
          String str1 = new String(arrayOfByte, localTextEncoding.prefix.length, arrayOfByte.length - localTextEncoding.prefix.length, localTextEncoding.encodingName);
          return str1;
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException1)
        {
          throw new ImageReadException(localUnsupportedEncodingException1.getMessage(), localUnsupportedEncodingException1);
        }
      }
    }

    public boolean isText()
    {
      return true;
    }

    private static final class TextEncoding
    {
      public final String encodingName;
      public final byte[] prefix;

      public TextEncoding(byte[] paramArrayOfByte, String paramString)
      {
        this.prefix = paramArrayOfByte;
        this.encodingName = paramString;
      }
    }
  }

  public static final class Unknown extends TagInfo
  {
    public Unknown(String paramString, int paramInt1, FieldType[] paramArrayOfFieldType, int paramInt2, TiffDirectoryConstants.ExifDirectoryType paramExifDirectoryType)
    {
      super(paramInt1, paramArrayOfFieldType, paramInt2, paramExifDirectoryType);
    }

    public byte[] encodeValue(FieldType paramFieldType, Object paramObject, int paramInt)
      throws ImageWriteException
    {
      return super.encodeValue(paramFieldType, paramObject, paramInt);
    }

    public Object getValue(TiffField paramTiffField)
      throws ImageReadException
    {
      return super.getValue(paramTiffField);
    }

    public boolean isUnknown()
    {
      return true;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.constants.TagInfo
 * JD-Core Version:    0.6.0
 */