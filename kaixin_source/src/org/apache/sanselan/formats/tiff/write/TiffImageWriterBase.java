package org.apache.sanselan.formats.tiff.write;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.common.BinaryConstants;
import org.apache.sanselan.common.BinaryOutputStream;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;

public abstract class TiffImageWriterBase
  implements TiffConstants, BinaryConstants
{
  protected final int byteOrder;

  public TiffImageWriterBase()
  {
    this.byteOrder = 73;
  }

  public TiffImageWriterBase(int paramInt)
  {
    this.byteOrder = paramInt;
  }

  protected static final int imageDataPaddingLength(int paramInt)
  {
    return (4 - paramInt % 4) % 4;
  }

  protected TiffOutputSummary validateDirectories(TiffOutputSet paramTiffOutputSet)
    throws ImageWriteException
  {
    List localList = paramTiffOutputSet.getDirectories();
    if (1 > localList.size())
      throw new ImageWriteException("No directories.");
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    Object localObject4 = null;
    Object localObject5 = null;
    Object localObject6 = null;
    ArrayList localArrayList1 = new ArrayList();
    HashMap localHashMap = new HashMap();
    int i = 0;
    int j = localList.size();
    if (i >= j)
    {
      if (localArrayList1.size() < 1)
        throw new ImageWriteException("Missing root directory.");
    }
    else
    {
      TiffOutputDirectory localTiffOutputDirectory1 = (TiffOutputDirectory)localList.get(i);
      int k = localTiffOutputDirectory1.type;
      Integer localInteger1 = new Integer(k);
      localHashMap.put(localInteger1, localTiffOutputDirectory1);
      if (k < 0)
        switch (k)
        {
        default:
          throw new ImageWriteException("Unknown directory: " + k);
        case -2:
          if (localObject1 != null)
            throw new ImageWriteException("More than one EXIF directory.");
          localObject1 = localTiffOutputDirectory1;
        case -3:
        case -4:
        }
      HashSet localHashSet;
      ArrayList localArrayList2;
      int m;
      while (true)
      {
        localHashSet = new HashSet();
        localArrayList2 = localTiffOutputDirectory1.getFields();
        m = 0;
        int n = localArrayList2.size();
        if (m < n)
          break label359;
        i++;
        break;
        if (localObject2 != null)
          throw new ImageWriteException("More than one GPS directory.");
        localObject2 = localTiffOutputDirectory1;
        continue;
        if (localObject3 != null)
          throw new ImageWriteException("More than one Interoperability directory.");
        localObject3 = localTiffOutputDirectory1;
        continue;
        if (localArrayList1.contains(localInteger1))
          throw new ImageWriteException("More than one directory with index: " + k + ".");
        Integer localInteger2 = new Integer(k);
        localArrayList1.add(localInteger2);
      }
      label359: TiffOutputField localTiffOutputField = (TiffOutputField)localArrayList2.get(m);
      Integer localInteger3 = new Integer(localTiffOutputField.tag);
      if (localHashSet.contains(localInteger3))
        throw new ImageWriteException("Tag (" + localTiffOutputField.tagInfo.getDescription() + ") appears twice in directory.");
      localHashSet.add(localInteger3);
      if (localTiffOutputField.tag == EXIF_TAG_EXIF_OFFSET.tag)
      {
        if (localObject4 != null)
          throw new ImageWriteException("More than one Exif directory offset field.");
        localObject4 = localTiffOutputField;
      }
      while (true)
      {
        m++;
        break;
        if (localTiffOutputField.tag == EXIF_TAG_INTEROP_OFFSET.tag)
        {
          if (localObject6 != null)
            throw new ImageWriteException("More than one Interoperability directory offset field.");
          localObject6 = localTiffOutputField;
          continue;
        }
        if (localTiffOutputField.tag != EXIF_TAG_GPSINFO.tag)
          continue;
        if (localObject5 != null)
          throw new ImageWriteException("More than one GPS directory offset field.");
        localObject5 = localTiffOutputField;
      }
    }
    Collections.sort(localArrayList1);
    Object localObject7 = null;
    TiffOutputDirectory localTiffOutputDirectory3;
    TiffOutputSummary localTiffOutputSummary;
    for (int i1 = 0; ; i1++)
    {
      int i2 = localArrayList1.size();
      if (i1 >= i2)
      {
        localTiffOutputDirectory3 = (TiffOutputDirectory)localHashMap.get(new Integer(0));
        localTiffOutputSummary = new TiffOutputSummary(this.byteOrder, localTiffOutputDirectory3, localHashMap);
        if ((localObject3 != null) || (localObject6 == null))
          break;
        throw new ImageWriteException("Output set has Interoperability Directory Offset field, but no Interoperability Directory");
      }
      Integer localInteger4 = (Integer)localArrayList1.get(i1);
      if (localInteger4.intValue() != i1)
        throw new ImageWriteException("Missing directory: " + i1 + ".");
      TiffOutputDirectory localTiffOutputDirectory2 = (TiffOutputDirectory)localHashMap.get(localInteger4);
      if (localObject7 != null)
        localObject7.setNextDirectory(localTiffOutputDirectory2);
      localObject7 = localTiffOutputDirectory2;
    }
    if (localObject3 != null)
    {
      if (localObject1 == null)
        localObject1 = paramTiffOutputSet.addExifDirectory();
      if (localObject6 == null)
      {
        localObject6 = TiffOutputField.createOffsetField(EXIF_TAG_INTEROP_OFFSET, this.byteOrder);
        ((TiffOutputDirectory)localObject1).add((TiffOutputField)localObject6);
      }
      localTiffOutputSummary.add(localObject3, (TiffOutputField)localObject6);
    }
    if ((localObject1 == null) && (localObject4 != null))
      throw new ImageWriteException("Output set has Exif Directory Offset field, but no Exif Directory");
    if (localObject1 != null)
    {
      if (localObject4 == null)
      {
        localObject4 = TiffOutputField.createOffsetField(EXIF_TAG_EXIF_OFFSET, this.byteOrder);
        localTiffOutputDirectory3.add((TiffOutputField)localObject4);
      }
      localTiffOutputSummary.add((TiffOutputItem)localObject1, (TiffOutputField)localObject4);
    }
    if ((localObject2 == null) && (localObject5 != null))
      throw new ImageWriteException("Output set has GPS Directory Offset field, but no GPS Directory");
    if (localObject2 != null)
    {
      if (localObject5 == null)
      {
        localObject5 = TiffOutputField.createOffsetField(EXIF_TAG_GPSINFO, this.byteOrder);
        localTiffOutputDirectory3.add((TiffOutputField)localObject5);
      }
      localTiffOutputSummary.add(localObject2, (TiffOutputField)localObject5);
    }
    return (TiffOutputSummary)(TiffOutputSummary)(TiffOutputSummary)(TiffOutputSummary)localTiffOutputSummary;
  }

  public abstract void write(OutputStream paramOutputStream, TiffOutputSet paramTiffOutputSet)
    throws IOException, ImageWriteException;

  protected void writeImageFileHeader(BinaryOutputStream paramBinaryOutputStream)
    throws IOException, ImageWriteException
  {
    writeImageFileHeader(paramBinaryOutputStream, 8);
  }

  protected void writeImageFileHeader(BinaryOutputStream paramBinaryOutputStream, int paramInt)
    throws IOException, ImageWriteException
  {
    paramBinaryOutputStream.write(this.byteOrder);
    paramBinaryOutputStream.write(this.byteOrder);
    paramBinaryOutputStream.write2Bytes(42);
    paramBinaryOutputStream.write4Bytes(paramInt);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.write.TiffImageWriterBase
 * JD-Core Version:    0.6.0
 */