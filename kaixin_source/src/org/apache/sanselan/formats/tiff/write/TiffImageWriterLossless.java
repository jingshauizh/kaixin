package org.apache.sanselan.formats.tiff.write;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.sanselan.FormatCompliance;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.common.BinaryFileFunctions;
import org.apache.sanselan.common.BinaryOutputStream;
import org.apache.sanselan.common.byteSources.ByteSource;
import org.apache.sanselan.common.byteSources.ByteSourceArray;
import org.apache.sanselan.formats.tiff.JpegImageData;
import org.apache.sanselan.formats.tiff.TiffContents;
import org.apache.sanselan.formats.tiff.TiffDirectory;
import org.apache.sanselan.formats.tiff.TiffElement;
import org.apache.sanselan.formats.tiff.TiffElement.Stub;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffReader;
import org.apache.sanselan.util.Debug;

public class TiffImageWriterLossless extends TiffImageWriterBase
{
  private static final Comparator ELEMENT_SIZE_COMPARATOR = new Comparator()
  {
    public int compare(Object paramObject1, Object paramObject2)
    {
      TiffElement localTiffElement1 = (TiffElement)paramObject1;
      TiffElement localTiffElement2 = (TiffElement)paramObject2;
      return localTiffElement1.length - localTiffElement2.length;
    }
  };
  private static final Comparator ITEM_SIZE_COMPARATOR = new Comparator()
  {
    public int compare(Object paramObject1, Object paramObject2)
    {
      TiffOutputItem localTiffOutputItem1 = (TiffOutputItem)paramObject1;
      TiffOutputItem localTiffOutputItem2 = (TiffOutputItem)paramObject2;
      return localTiffOutputItem1.getItemLength() - localTiffOutputItem2.getItemLength();
    }
  };
  private final byte[] exifBytes;

  public TiffImageWriterLossless(int paramInt, byte[] paramArrayOfByte)
  {
    super(paramInt);
    this.exifBytes = paramArrayOfByte;
  }

  public TiffImageWriterLossless(byte[] paramArrayOfByte)
  {
    this.exifBytes = paramArrayOfByte;
  }

  private List analyzeOldTiff()
    throws ImageWriteException, IOException
  {
    while (true)
    {
      int i;
      ArrayList localArrayList4;
      int m;
      int j;
      try
      {
        ByteSourceArray localByteSourceArray = new ByteSourceArray(this.exifBytes);
        FormatCompliance localFormatCompliance = FormatCompliance.getDefault();
        TiffContents localTiffContents = new TiffReader(false).readContents(localByteSourceArray, null, localFormatCompliance);
        ArrayList localArrayList1 = new ArrayList();
        ArrayList localArrayList2 = localTiffContents.directories;
        i = 0;
        if (i < localArrayList2.size())
          continue;
        Collections.sort(localArrayList1, TiffElement.COMPARATOR);
        localArrayList4 = new ArrayList();
        Object localObject = null;
        int k = -1;
        m = 0;
        if (m < localArrayList1.size())
          continue;
        if (localObject != null)
        {
          localArrayList4.add(new TiffElement.Stub(localObject.offset, k - localObject.offset));
          return localArrayList4;
          TiffDirectory localTiffDirectory = (TiffDirectory)localArrayList2.get(i);
          localArrayList1.add(localTiffDirectory);
          ArrayList localArrayList3 = localTiffDirectory.getDirectoryEntrys();
          j = 0;
          if (j < localArrayList3.size())
            continue;
          JpegImageData localJpegImageData = localTiffDirectory.getJpegImageData();
          if (localJpegImageData == null)
            break label355;
          localArrayList1.add(localJpegImageData);
          break label355;
          TiffElement localTiffElement1 = ((TiffField)localArrayList3.get(j)).getOversizeValueElement();
          if (localTiffElement1 == null)
            break label361;
          localArrayList1.add(localTiffElement1);
          break label361;
          TiffElement localTiffElement2 = (TiffElement)localArrayList1.get(m);
          int n = localTiffElement2.offset + localTiffElement2.length;
          if (localObject != null)
            continue;
          localObject = localTiffElement2;
          k = n;
          break label367;
          if (localTiffElement2.offset - k <= 3)
            continue;
          localArrayList4.add(new TiffElement.Stub(localObject.offset, k - localObject.offset));
          localObject = localTiffElement2;
          k = n;
          break label367;
          k = n;
        }
      }
      catch (ImageReadException localImageReadException)
      {
        ImageWriteException localImageWriteException = new ImageWriteException(localImageReadException.getMessage(), localImageReadException);
        throw localImageWriteException;
      }
      return localArrayList4;
      label355: i++;
      continue;
      label361: j++;
      continue;
      label367: m++;
    }
  }

  private void dumpElements(List paramList)
    throws IOException
  {
    dumpElements(new ByteSourceArray(this.exifBytes), paramList);
  }

  private void dumpElements(ByteSource paramByteSource, List paramList)
    throws IOException
  {
    int i = 8;
    int j = 0;
    if (j >= paramList.size())
    {
      Debug.debug();
      return;
    }
    TiffElement localTiffElement = (TiffElement)paramList.get(j);
    byte[] arrayOfByte;
    if (localTiffElement.offset > i)
    {
      int k = localTiffElement.offset - i;
      Debug.debug("gap of " + k + " bytes.");
      arrayOfByte = paramByteSource.getBlock(i, k);
      if (arrayOfByte.length <= 64)
        break label257;
      Debug.debug("\thead", BinaryFileFunctions.head(arrayOfByte, 32));
      Debug.debug("\ttail", BinaryFileFunctions.tail(arrayOfByte, 32));
    }
    while (true)
    {
      Debug.debug("element[" + j + "]:" + localTiffElement.getElementDescription() + " (" + localTiffElement.offset + " + " + localTiffElement.length + " = " + (localTiffElement.offset + localTiffElement.length) + ")");
      if ((localTiffElement instanceof TiffDirectory))
      {
        TiffDirectory localTiffDirectory = (TiffDirectory)localTiffElement;
        Debug.debug("\tnext Directory Offset: " + localTiffDirectory.nextDirectoryOffset);
      }
      i = localTiffElement.offset + localTiffElement.length;
      j++;
      break;
      label257: Debug.debug("\tbytes", arrayOfByte);
    }
  }

  private int updateOffsetsStep(List paramList1, List paramList2)
    throws IOException, ImageWriteException
  {
    int i = this.exifBytes.length;
    ArrayList localArrayList1 = new ArrayList(paramList1);
    Collections.sort(localArrayList1, TiffElement.COMPARATOR);
    Collections.reverse(localArrayList1);
    label39: ArrayList localArrayList2;
    if (localArrayList1.size() <= 0)
    {
      Collections.sort(localArrayList1, ELEMENT_SIZE_COMPARATOR);
      Collections.reverse(localArrayList1);
      localArrayList2 = new ArrayList(paramList2);
      Collections.sort(localArrayList2, ITEM_SIZE_COMPARATOR);
      Collections.reverse(localArrayList2);
    }
    while (true)
    {
      if (localArrayList2.size() <= 0)
      {
        return i;
        TiffElement localTiffElement1 = (TiffElement)localArrayList1.get(0);
        if (localTiffElement1.offset + localTiffElement1.length != i)
          break label39;
        i -= localTiffElement1.length;
        localArrayList1.remove(0);
        break;
      }
      TiffOutputItem localTiffOutputItem = (TiffOutputItem)localArrayList2.remove(0);
      int j = localTiffOutputItem.getItemLength();
      Object localObject = null;
      for (int k = 0; ; k++)
      {
        if (k >= localArrayList1.size());
        TiffElement localTiffElement2;
        do
        {
          if (localObject != null)
            break label226;
          localTiffOutputItem.setOffset(i);
          i += j;
          break;
          localTiffElement2 = (TiffElement)localArrayList1.get(k);
        }
        while (localTiffElement2.length < j);
        localObject = localTiffElement2;
      }
      label226: localTiffOutputItem.setOffset(localObject.offset);
      localArrayList1.remove(localObject);
      if (localObject.length <= j)
        continue;
      localArrayList1.add(new TiffElement.Stub(j + localObject.offset, localObject.length - j));
      Collections.sort(localArrayList1, ELEMENT_SIZE_COMPARATOR);
      Collections.reverse(localArrayList1);
    }
  }

  private void writeStep(OutputStream paramOutputStream, TiffOutputSet paramTiffOutputSet, List paramList1, List paramList2, int paramInt)
    throws IOException, ImageWriteException
  {
    TiffOutputDirectory localTiffOutputDirectory = paramTiffOutputSet.getRootDirectory();
    byte[] arrayOfByte = new byte[paramInt];
    System.arraycopy(this.exifBytes, 0, arrayOfByte, 0, Math.min(this.exifBytes.length, arrayOfByte.length));
    writeImageFileHeader(new BinaryOutputStream(new BufferOutputStream(arrayOfByte, 0), this.byteOrder), localTiffOutputDirectory.getOffset());
    int i = 0;
    if (i >= paramList1.size());
    for (int m = 0; ; m++)
    {
      if (m >= paramList2.size())
      {
        paramOutputStream.write(arrayOfByte);
        return;
        TiffElement localTiffElement = (TiffElement)paramList1.get(i);
        for (int j = 0; ; j++)
        {
          if (j >= localTiffElement.length)
          {
            i++;
            break;
          }
          int k = j + localTiffElement.offset;
          if (k >= arrayOfByte.length)
            continue;
          arrayOfByte[k] = 0;
        }
      }
      TiffOutputItem localTiffOutputItem = (TiffOutputItem)paramList2.get(m);
      localTiffOutputItem.writeItem(new BinaryOutputStream(new BufferOutputStream(arrayOfByte, localTiffOutputItem.getOffset()), this.byteOrder));
    }
  }

  public void write(OutputStream paramOutputStream, TiffOutputSet paramTiffOutputSet)
    throws IOException, ImageWriteException
  {
    List localList1 = analyzeOldTiff();
    int i = this.exifBytes.length;
    if (localList1.size() < 1)
      throw new ImageWriteException("Couldn't analyze old tiff data.");
    if (localList1.size() == 1)
    {
      TiffElement localTiffElement = (TiffElement)localList1.get(0);
      if ((localTiffElement.offset == 8) && (8 + (localTiffElement.offset + localTiffElement.length) == i))
      {
        new TiffImageWriterLossy(this.byteOrder).write(paramOutputStream, paramTiffOutputSet);
        return;
      }
    }
    TiffOutputSummary localTiffOutputSummary = validateDirectories(paramTiffOutputSet);
    List localList2 = paramTiffOutputSet.getOutputItems(localTiffOutputSummary);
    int j = updateOffsetsStep(localList1, localList2);
    localTiffOutputSummary.updateOffsets(this.byteOrder);
    writeStep(paramOutputStream, paramTiffOutputSet, localList1, localList2, j);
  }

  private static class BufferOutputStream extends OutputStream
  {
    private final byte[] buffer;
    private int index;

    public BufferOutputStream(byte[] paramArrayOfByte, int paramInt)
    {
      this.buffer = paramArrayOfByte;
      this.index = paramInt;
    }

    public void write(int paramInt)
      throws IOException
    {
      if (this.index >= this.buffer.length)
        throw new IOException("Buffer overflow.");
      byte[] arrayOfByte = this.buffer;
      int i = this.index;
      this.index = (i + 1);
      arrayOfByte[i] = (byte)paramInt;
    }

    public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      if (paramInt2 + this.index > this.buffer.length)
        throw new IOException("Buffer overflow.");
      System.arraycopy(paramArrayOfByte, paramInt1, this.buffer, this.index, paramInt2);
      this.index = (paramInt2 + this.index);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.write.TiffImageWriterLossless
 * JD-Core Version:    0.6.0
 */