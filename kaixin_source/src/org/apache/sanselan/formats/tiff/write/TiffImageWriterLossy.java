package org.apache.sanselan.formats.tiff.write;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.common.BinaryOutputStream;

public class TiffImageWriterLossy extends TiffImageWriterBase
{
  public TiffImageWriterLossy()
  {
  }

  public TiffImageWriterLossy(int paramInt)
  {
    super(paramInt);
  }

  private void updateOffsetsStep(List paramList)
    throws IOException, ImageWriteException
  {
    int i = 8;
    for (int j = 0; ; j++)
    {
      if (j >= paramList.size())
        return;
      TiffOutputItem localTiffOutputItem = (TiffOutputItem)paramList.get(j);
      localTiffOutputItem.setOffset(i);
      int k = localTiffOutputItem.getItemLength();
      i = i + k + imageDataPaddingLength(k);
    }
  }

  private void writeStep(BinaryOutputStream paramBinaryOutputStream, List paramList)
    throws IOException, ImageWriteException
  {
    writeImageFileHeader(paramBinaryOutputStream);
    int i = 0;
    if (i >= paramList.size())
      return;
    TiffOutputItem localTiffOutputItem = (TiffOutputItem)paramList.get(i);
    localTiffOutputItem.writeItem(paramBinaryOutputStream);
    int j = imageDataPaddingLength(localTiffOutputItem.getItemLength());
    for (int k = 0; ; k++)
    {
      if (k >= j)
      {
        i++;
        break;
      }
      paramBinaryOutputStream.write(0);
    }
  }

  public void write(OutputStream paramOutputStream, TiffOutputSet paramTiffOutputSet)
    throws IOException, ImageWriteException
  {
    TiffOutputSummary localTiffOutputSummary = validateDirectories(paramTiffOutputSet);
    List localList = paramTiffOutputSet.getOutputItems(localTiffOutputSummary);
    updateOffsetsStep(localList);
    localTiffOutputSummary.updateOffsets(this.byteOrder);
    writeStep(new BinaryOutputStream(paramOutputStream, this.byteOrder), localList);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.write.TiffImageWriterLossy
 * JD-Core Version:    0.6.0
 */