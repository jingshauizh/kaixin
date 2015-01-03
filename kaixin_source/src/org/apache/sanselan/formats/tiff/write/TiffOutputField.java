package org.apache.sanselan.formats.tiff.write;

import java.io.IOException;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.common.BinaryOutputStream;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldType;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldTypeASCII;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldTypeLong;

public class TiffOutputField
  implements TiffConstants
{
  private static final String newline = System.getProperty("line.separator");
  private byte[] bytes;
  public final int count;
  public final FieldType fieldType;
  private final TiffOutputItem.Value separateValueItem;
  private int sortHint = -1;
  public final int tag;
  public final TagInfo tagInfo;

  public TiffOutputField(int paramInt1, TagInfo paramTagInfo, FieldType paramFieldType, int paramInt2, byte[] paramArrayOfByte)
  {
    this.tag = paramInt1;
    this.tagInfo = paramTagInfo;
    this.fieldType = paramFieldType;
    this.count = paramInt2;
    this.bytes = paramArrayOfByte;
    if (isLocalValue())
    {
      this.separateValueItem = null;
      return;
    }
    this.separateValueItem = new TiffOutputItem.Value("Field Seperate value (" + paramTagInfo.getDescription() + ")", paramArrayOfByte);
  }

  public TiffOutputField(TagInfo paramTagInfo, FieldType paramFieldType, int paramInt, byte[] paramArrayOfByte)
  {
    this(paramTagInfo.tag, paramTagInfo, paramFieldType, paramInt, paramArrayOfByte);
  }

  public static TiffOutputField create(TagInfo paramTagInfo, int paramInt, Number paramNumber)
    throws ImageWriteException
  {
    if ((paramTagInfo.dataTypes == null) || (paramTagInfo.dataTypes.length < 1))
      throw new ImageWriteException("Tag has no default data type.");
    FieldType localFieldType = paramTagInfo.dataTypes[0];
    if (paramTagInfo.length != 1)
      throw new ImageWriteException("Tag does not expect a single value.");
    byte[] arrayOfByte = localFieldType.writeData(paramNumber, paramInt);
    return new TiffOutputField(paramTagInfo.tag, paramTagInfo, localFieldType, 1, arrayOfByte);
  }

  public static TiffOutputField create(TagInfo paramTagInfo, int paramInt, String paramString)
    throws ImageWriteException
  {
    FieldTypeASCII localFieldTypeASCII;
    if (paramTagInfo.dataTypes == null)
      localFieldTypeASCII = FIELD_TYPE_ASCII;
    while (true)
    {
      byte[] arrayOfByte = localFieldTypeASCII.writeData(paramString, paramInt);
      return new TiffOutputField(paramTagInfo.tag, paramTagInfo, localFieldTypeASCII, arrayOfByte.length, arrayOfByte);
      if (paramTagInfo.dataTypes == FIELD_TYPE_DESCRIPTION_ASCII)
      {
        localFieldTypeASCII = FIELD_TYPE_ASCII;
        continue;
      }
      if (paramTagInfo.dataTypes[0] != FIELD_TYPE_ASCII)
        break;
      localFieldTypeASCII = FIELD_TYPE_ASCII;
    }
    throw new ImageWriteException("Tag has unexpected data type.");
  }

  public static TiffOutputField create(TagInfo paramTagInfo, int paramInt, Number[] paramArrayOfNumber)
    throws ImageWriteException
  {
    if ((paramTagInfo.dataTypes == null) || (paramTagInfo.dataTypes.length < 1))
      throw new ImageWriteException("Tag has no default data type.");
    FieldType localFieldType = paramTagInfo.dataTypes[0];
    if (paramTagInfo.length != paramArrayOfNumber.length)
      throw new ImageWriteException("Tag does not expect a single value.");
    byte[] arrayOfByte = localFieldType.writeData(paramArrayOfNumber, paramInt);
    return new TiffOutputField(paramTagInfo.tag, paramTagInfo, localFieldType, paramArrayOfNumber.length, arrayOfByte);
  }

  protected static final TiffOutputField createOffsetField(TagInfo paramTagInfo, int paramInt)
    throws ImageWriteException
  {
    return new TiffOutputField(paramTagInfo, FIELD_TYPE_LONG, 1, FIELD_TYPE_LONG.writeData(new int[1], paramInt));
  }

  protected TiffOutputItem getSeperateValue()
  {
    return this.separateValueItem;
  }

  public int getSortHint()
  {
    return this.sortHint;
  }

  protected boolean isLocalValue()
  {
    return this.bytes.length <= 4;
  }

  protected void setData(byte[] paramArrayOfByte)
    throws ImageWriteException
  {
    if (this.bytes.length != paramArrayOfByte.length)
      throw new ImageWriteException("Cannot change size of value.");
    this.bytes = paramArrayOfByte;
    if (this.separateValueItem != null)
      this.separateValueItem.updateValue(paramArrayOfByte);
  }

  public void setSortHint(int paramInt)
  {
    this.sortHint = paramInt;
  }

  public String toString()
  {
    return toString(null);
  }

  public String toString(String paramString)
  {
    if (paramString == null)
      paramString = "";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramString);
    localStringBuffer.append(this.tagInfo);
    localStringBuffer.append(newline);
    localStringBuffer.append(paramString);
    localStringBuffer.append("count: " + this.count);
    localStringBuffer.append(newline);
    localStringBuffer.append(paramString);
    localStringBuffer.append(this.fieldType);
    localStringBuffer.append(newline);
    return localStringBuffer.toString();
  }

  protected void writeField(BinaryOutputStream paramBinaryOutputStream)
    throws IOException, ImageWriteException
  {
    paramBinaryOutputStream.write2Bytes(this.tag);
    paramBinaryOutputStream.write2Bytes(this.fieldType.type);
    paramBinaryOutputStream.write4Bytes(this.count);
    if (isLocalValue())
    {
      if (this.separateValueItem != null)
        throw new ImageWriteException("Unexpected separate value item.");
      if (this.bytes.length > 4)
        throw new ImageWriteException("Local value has invalid length: " + this.bytes.length);
      paramBinaryOutputStream.writeByteArray(this.bytes);
      int i = 4 - this.bytes.length;
      for (int j = 0; ; j++)
      {
        if (j >= i)
          return;
        paramBinaryOutputStream.write(0);
      }
    }
    if (this.separateValueItem == null)
      throw new ImageWriteException("Missing separate value item.");
    paramBinaryOutputStream.write4Bytes(this.separateValueItem.getOffset());
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.write.TiffOutputField
 * JD-Core Version:    0.6.0
 */