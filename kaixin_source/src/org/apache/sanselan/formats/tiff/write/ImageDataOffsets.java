package org.apache.sanselan.formats.tiff.write;

import org.apache.sanselan.formats.tiff.TiffElement.DataElement;

class ImageDataOffsets
{
  public final int[] imageDataOffsets;
  public final TiffOutputField imageDataOffsetsField;
  public final TiffOutputItem[] outputItems;

  public ImageDataOffsets(TiffElement.DataElement[] paramArrayOfDataElement, int[] paramArrayOfInt, TiffOutputField paramTiffOutputField)
  {
    this.imageDataOffsets = paramArrayOfInt;
    this.imageDataOffsetsField = paramTiffOutputField;
    this.outputItems = new TiffOutputItem[paramArrayOfDataElement.length];
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfDataElement.length)
        return;
      TiffOutputItem.Value localValue = new TiffOutputItem.Value("TIFF image data", paramArrayOfDataElement[i].data);
      this.outputItems[i] = localValue;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.write.ImageDataOffsets
 * JD-Core Version:    0.6.0
 */