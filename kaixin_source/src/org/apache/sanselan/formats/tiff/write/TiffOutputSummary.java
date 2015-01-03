package org.apache.sanselan.formats.tiff.write;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldTypeLong;

class TiffOutputSummary
  implements TiffConstants
{
  public final int byteOrder;
  public final Map directoryTypeMap;
  private List imageDataItems = new ArrayList();
  private List offsetItems = new ArrayList();
  public final TiffOutputDirectory rootDirectory;

  public TiffOutputSummary(int paramInt, TiffOutputDirectory paramTiffOutputDirectory, Map paramMap)
  {
    this.byteOrder = paramInt;
    this.rootDirectory = paramTiffOutputDirectory;
    this.directoryTypeMap = paramMap;
  }

  public void add(TiffOutputItem paramTiffOutputItem, TiffOutputField paramTiffOutputField)
  {
    this.offsetItems.add(new OffsetItem(paramTiffOutputItem, paramTiffOutputField));
  }

  public void addTiffImageData(ImageDataOffsets paramImageDataOffsets)
  {
    this.imageDataItems.add(paramImageDataOffsets);
  }

  public void updateOffsets(int paramInt)
    throws ImageWriteException
  {
    int j;
    for (int i = 0; ; i++)
    {
      if (i >= this.offsetItems.size())
      {
        j = 0;
        if (j < this.imageDataItems.size())
          break;
        return;
      }
      OffsetItem localOffsetItem = (OffsetItem)this.offsetItems.get(i);
      FieldTypeLong localFieldTypeLong = FIELD_TYPE_LONG;
      int[] arrayOfInt = new int[1];
      arrayOfInt[0] = localOffsetItem.item.getOffset();
      byte[] arrayOfByte = localFieldTypeLong.writeData(arrayOfInt, paramInt);
      localOffsetItem.itemOffsetField.setData(arrayOfByte);
    }
    ImageDataOffsets localImageDataOffsets = (ImageDataOffsets)this.imageDataItems.get(j);
    for (int k = 0; ; k++)
    {
      if (k >= localImageDataOffsets.outputItems.length)
      {
        localImageDataOffsets.imageDataOffsetsField.setData(FIELD_TYPE_LONG.writeData(localImageDataOffsets.imageDataOffsets, paramInt));
        j++;
        break;
      }
      TiffOutputItem localTiffOutputItem = localImageDataOffsets.outputItems[k];
      localImageDataOffsets.imageDataOffsets[k] = localTiffOutputItem.getOffset();
    }
  }

  private static class OffsetItem
  {
    public final TiffOutputItem item;
    public final TiffOutputField itemOffsetField;

    public OffsetItem(TiffOutputItem paramTiffOutputItem, TiffOutputField paramTiffOutputField)
    {
      this.itemOffsetField = paramTiffOutputField;
      this.item = paramTiffOutputItem;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.write.TiffOutputSummary
 * JD-Core Version:    0.6.0
 */