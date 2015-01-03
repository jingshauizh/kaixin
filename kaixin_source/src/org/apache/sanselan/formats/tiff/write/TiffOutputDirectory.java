package org.apache.sanselan.formats.tiff.write;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.common.BinaryOutputStream;
import org.apache.sanselan.formats.tiff.JpegImageData;
import org.apache.sanselan.formats.tiff.TiffDirectory;
import org.apache.sanselan.formats.tiff.constants.TagConstantsUtils;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;
import org.apache.sanselan.formats.tiff.constants.TiffDirectoryConstants.ExifDirectoryType;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldType;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldTypeLong;

public final class TiffOutputDirectory extends TiffOutputItem
  implements TiffConstants
{
  private final ArrayList fields = new ArrayList();
  private JpegImageData jpegImageData = null;
  private TiffOutputDirectory nextDirectory = null;
  public final int type;

  public TiffOutputDirectory(int paramInt)
  {
    this.type = paramInt;
  }

  private void removeFieldIfPresent(TagInfo paramTagInfo)
  {
    TiffOutputField localTiffOutputField = findField(paramTagInfo);
    if (localTiffOutputField != null)
      this.fields.remove(localTiffOutputField);
  }

  public void add(TiffOutputField paramTiffOutputField)
  {
    this.fields.add(paramTiffOutputField);
  }

  public String description()
  {
    return TiffDirectory.description(this.type);
  }

  public TiffOutputField findField(int paramInt)
  {
    for (int i = 0; ; i++)
    {
      TiffOutputField localTiffOutputField;
      if (i >= this.fields.size())
        localTiffOutputField = null;
      do
      {
        return localTiffOutputField;
        localTiffOutputField = (TiffOutputField)this.fields.get(i);
      }
      while (localTiffOutputField.tag == paramInt);
    }
  }

  public TiffOutputField findField(TagInfo paramTagInfo)
  {
    return findField(paramTagInfo.tag);
  }

  public ArrayList getFields()
  {
    return new ArrayList(this.fields);
  }

  public String getItemDescription()
  {
    TiffDirectoryConstants.ExifDirectoryType localExifDirectoryType = TagConstantsUtils.getExifDirectoryType(this.type);
    return "Directory: " + localExifDirectoryType.name + " (" + this.type + ")";
  }

  public int getItemLength()
  {
    return 4 + (2 + 12 * this.fields.size());
  }

  protected List getOutputItems(TiffOutputSummary paramTiffOutputSummary)
    throws ImageWriteException
  {
    removeFieldIfPresent(TIFF_TAG_JPEG_INTERCHANGE_FORMAT);
    removeFieldIfPresent(TIFF_TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
    JpegImageData localJpegImageData = this.jpegImageData;
    TiffOutputField localTiffOutputField1 = null;
    if (localJpegImageData != null)
    {
      localTiffOutputField1 = new TiffOutputField(TIFF_TAG_JPEG_INTERCHANGE_FORMAT, FIELD_TYPE_LONG, 1, FieldType.getStubLocalValue());
      add(localTiffOutputField1);
      FieldTypeLong localFieldTypeLong = FIELD_TYPE_LONG;
      int[] arrayOfInt = new int[1];
      arrayOfInt[0] = this.jpegImageData.length;
      byte[] arrayOfByte = localFieldTypeLong.writeData(arrayOfInt, paramTiffOutputSummary.byteOrder);
      add(new TiffOutputField(TIFF_TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, FIELD_TYPE_LONG, 1, arrayOfByte));
    }
    removeFieldIfPresent(TIFF_TAG_STRIP_OFFSETS);
    removeFieldIfPresent(TIFF_TAG_STRIP_BYTE_COUNTS);
    removeFieldIfPresent(TIFF_TAG_TILE_OFFSETS);
    removeFieldIfPresent(TIFF_TAG_TILE_BYTE_COUNTS);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(this);
    sortFields();
    int i = 0;
    if (i >= this.fields.size())
    {
      if ((0 == 0) || (this.jpegImageData != null))
      {
        TiffOutputItem.Value localValue = new TiffOutputItem.Value("JPEG image data", this.jpegImageData.data);
        localArrayList.add(localValue);
        paramTiffOutputSummary.add(localValue, localTiffOutputField1);
      }
      return localArrayList;
    }
    TiffOutputField localTiffOutputField2 = (TiffOutputField)this.fields.get(i);
    if (localTiffOutputField2.isLocalValue());
    while (true)
    {
      i++;
      break;
      localArrayList.add(localTiffOutputField2.getSeperateValue());
    }
  }

  public JpegImageData getRawJpegImageData()
  {
    return this.jpegImageData;
  }

  public void removeField(int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; ; i++)
    {
      if (i >= this.fields.size())
      {
        this.fields.removeAll(localArrayList);
        return;
      }
      TiffOutputField localTiffOutputField = (TiffOutputField)this.fields.get(i);
      if (localTiffOutputField.tag != paramInt)
        continue;
      localArrayList.add(localTiffOutputField);
    }
  }

  public void removeField(TagInfo paramTagInfo)
  {
    removeField(paramTagInfo.tag);
  }

  public void setJpegImageData(JpegImageData paramJpegImageData)
  {
    this.jpegImageData = paramJpegImageData;
  }

  public void setNextDirectory(TiffOutputDirectory paramTiffOutputDirectory)
  {
    this.nextDirectory = paramTiffOutputDirectory;
  }

  public void sortFields()
  {
    1 local1 = new Comparator()
    {
      public int compare(Object paramObject1, Object paramObject2)
      {
        TiffOutputField localTiffOutputField1 = (TiffOutputField)paramObject1;
        TiffOutputField localTiffOutputField2 = (TiffOutputField)paramObject2;
        if (localTiffOutputField1.tag != localTiffOutputField2.tag)
          return localTiffOutputField1.tag - localTiffOutputField2.tag;
        return localTiffOutputField1.getSortHint() - localTiffOutputField2.getSortHint();
      }
    };
    Collections.sort(this.fields, local1);
  }

  public void writeItem(BinaryOutputStream paramBinaryOutputStream)
    throws IOException, ImageWriteException
  {
    paramBinaryOutputStream.write2Bytes(this.fields.size());
    int j;
    for (int i = 0; ; i++)
    {
      if (i >= this.fields.size())
      {
        TiffOutputDirectory localTiffOutputDirectory = this.nextDirectory;
        j = 0;
        if (localTiffOutputDirectory != null)
          j = this.nextDirectory.getOffset();
        if (j != -1)
          break;
        paramBinaryOutputStream.write4Bytes(0);
        return;
      }
      ((TiffOutputField)this.fields.get(i)).writeField(paramBinaryOutputStream);
    }
    paramBinaryOutputStream.write4Bytes(j);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.write.TiffOutputDirectory
 * JD-Core Version:    0.6.0
 */