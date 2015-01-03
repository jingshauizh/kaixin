package org.apache.sanselan.formats.tiff;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.common.byteSources.ByteSource;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldType;

public class TiffDirectory extends TiffElement
  implements TiffConstants
{
  public final ArrayList entries;
  private JpegImageData jpegImageData = null;
  public final int nextDirectoryOffset;
  public final int type;

  public TiffDirectory(int paramInt1, ArrayList paramArrayList, int paramInt2, int paramInt3)
  {
    super(paramInt2, 4 + (2 + 12 * paramArrayList.size()));
    this.type = paramInt1;
    this.entries = paramArrayList;
    this.nextDirectoryOffset = paramInt3;
  }

  public static final String description(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return "Bad Type";
    case -1:
      return "Unknown";
    case 0:
      return "Root";
    case 1:
      return "Sub";
    case 2:
      return "Thumbnail";
    case -2:
      return "Exif";
    case -3:
      return "Gps";
    case -4:
    }
    return "Interoperability";
  }

  private ArrayList getRawImageDataElements(TiffField paramTiffField1, TiffField paramTiffField2)
    throws ImageReadException
  {
    int[] arrayOfInt1 = paramTiffField1.getIntArrayValue();
    int[] arrayOfInt2 = paramTiffField2.getIntArrayValue();
    if (arrayOfInt1.length != arrayOfInt2.length)
      throw new ImageReadException("offsets.length(" + arrayOfInt1.length + ") != byteCounts.length(" + arrayOfInt2.length + ")");
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfInt1.length)
        return localArrayList;
      localArrayList.add(new ImageDataElement(arrayOfInt1[i], arrayOfInt2[i]));
    }
  }

  public String description()
  {
    return description(this.type);
  }

  public void dump()
  {
    for (int i = 0; ; i++)
    {
      if (i >= this.entries.size())
        return;
      ((TiffField)this.entries.get(i)).dump();
    }
  }

  protected void fillInValues(ByteSource paramByteSource)
    throws ImageReadException, IOException
  {
    for (int i = 0; ; i++)
    {
      if (i >= this.entries.size())
        return;
      ((TiffField)this.entries.get(i)).fillInValue(paramByteSource);
    }
  }

  public TiffField findField(TagInfo paramTagInfo)
    throws ImageReadException
  {
    return findField(paramTagInfo, false);
  }

  public TiffField findField(TagInfo paramTagInfo, boolean paramBoolean)
    throws ImageReadException
  {
    TiffField localTiffField;
    if (this.entries == null)
    {
      localTiffField = null;
      return localTiffField;
    }
    for (int i = 0; ; i++)
    {
      if (i >= this.entries.size())
      {
        if (!paramBoolean)
          break label88;
        throw new ImageReadException("Missing expected field: " + paramTagInfo.getDescription());
      }
      localTiffField = (TiffField)this.entries.get(i);
      if (localTiffField.tag == paramTagInfo.tag)
        break;
    }
    label88: return null;
  }

  public ArrayList getDirectoryEntrys()
  {
    return new ArrayList(this.entries);
  }

  public String getElementDescription(boolean paramBoolean)
  {
    if (!paramBoolean)
      return "TIFF Directory (" + description() + ")";
    int i = 2 + this.offset;
    StringBuffer localStringBuffer = new StringBuffer();
    for (int j = 0; ; j++)
    {
      if (j >= this.entries.size())
        return localStringBuffer.toString();
      TiffField localTiffField = (TiffField)this.entries.get(j);
      localStringBuffer.append("\t");
      localStringBuffer.append("[" + i + "]: ");
      localStringBuffer.append(localTiffField.tagInfo.name);
      localStringBuffer.append(" (" + localTiffField.tag + ", 0x" + Integer.toHexString(localTiffField.tag) + ")");
      localStringBuffer.append(", " + localTiffField.fieldType.name);
      localStringBuffer.append(", " + localTiffField.fieldType.getRawBytes(localTiffField).length);
      localStringBuffer.append(": " + localTiffField.getValueDescription());
      localStringBuffer.append("\n");
      i += 12;
    }
  }

  public JpegImageData getJpegImageData()
  {
    return this.jpegImageData;
  }

  public ImageDataElement getJpegRawImageDataElement()
    throws ImageReadException
  {
    TiffField localTiffField1 = findField(TIFF_TAG_JPEG_INTERCHANGE_FORMAT);
    TiffField localTiffField2 = findField(TIFF_TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
    if ((localTiffField1 != null) && (localTiffField2 != null))
      return new ImageDataElement(localTiffField1.getIntArrayValue()[0], localTiffField2.getIntArrayValue()[0]);
    throw new ImageReadException("Couldn't find image data.");
  }

  public ArrayList getTiffRawImageDataElements()
    throws ImageReadException
  {
    TiffField localTiffField1 = findField(TIFF_TAG_TILE_OFFSETS);
    TiffField localTiffField2 = findField(TIFF_TAG_TILE_BYTE_COUNTS);
    TiffField localTiffField3 = findField(TIFF_TAG_STRIP_OFFSETS);
    TiffField localTiffField4 = findField(TIFF_TAG_STRIP_BYTE_COUNTS);
    if ((localTiffField1 != null) && (localTiffField2 != null))
      return getRawImageDataElements(localTiffField1, localTiffField2);
    if ((localTiffField3 != null) && (localTiffField4 != null))
      return getRawImageDataElements(localTiffField3, localTiffField4);
    throw new ImageReadException("Couldn't find image data.");
  }

  public boolean hasJpegImageData()
    throws ImageReadException
  {
    return findField(TIFF_TAG_JPEG_INTERCHANGE_FORMAT) != null;
  }

  public boolean hasTiffImageData()
    throws ImageReadException
  {
    if (findField(TIFF_TAG_TILE_OFFSETS) != null);
    do
      return true;
    while (findField(TIFF_TAG_STRIP_OFFSETS) != null);
    return false;
  }

  public boolean imageDataInStrips()
    throws ImageReadException
  {
    int i = 1;
    TiffField localTiffField1 = findField(TIFF_TAG_TILE_OFFSETS);
    TiffField localTiffField2 = findField(TIFF_TAG_TILE_BYTE_COUNTS);
    TiffField localTiffField3 = findField(TIFF_TAG_STRIP_OFFSETS);
    TiffField localTiffField4 = findField(TIFF_TAG_STRIP_BYTE_COUNTS);
    if ((localTiffField1 != null) && (localTiffField2 != null))
      i = 0;
    do
      return i;
    while (((localTiffField3 != null) && (localTiffField4 != null)) || ((localTiffField3 != null) && (localTiffField4 != null)));
    throw new ImageReadException("Couldn't find image data.");
  }

  public void setJpegImageData(JpegImageData paramJpegImageData)
  {
    this.jpegImageData = paramJpegImageData;
  }

  public final class ImageDataElement extends TiffElement
  {
    public ImageDataElement(int paramInt1, int arg3)
    {
      super(i);
    }

    public String getElementDescription(boolean paramBoolean)
    {
      if (paramBoolean)
        return null;
      return "ImageDataElement";
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.TiffDirectory
 * JD-Core Version:    0.6.0
 */