package org.apache.sanselan.formats.tiff;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.sanselan.FormatCompliance;
import org.apache.sanselan.ImageFormat;
import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageParser;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.common.byteSources.ByteSource;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;

public class TiffImageParser extends ImageParser
  implements TiffConstants
{
  private static final String[] ACCEPTED_EXTENSIONS = { ".tif", ".tiff" };
  private static final String DEFAULT_EXTENSION = ".tif";

  public List collectRawImageData(ByteSource paramByteSource, Map paramMap)
    throws ImageReadException, IOException
  {
    FormatCompliance localFormatCompliance = FormatCompliance.getDefault();
    TiffContents localTiffContents = new TiffReader(isStrict(paramMap)).readDirectories(paramByteSource, true, localFormatCompliance);
    ArrayList localArrayList1 = new ArrayList();
    int i = 0;
    if (i >= localTiffContents.directories.size())
      return localArrayList1;
    ArrayList localArrayList2 = ((TiffDirectory)localTiffContents.directories.get(i)).getTiffRawImageDataElements();
    for (int j = 0; ; j++)
    {
      if (j >= localArrayList2.size())
      {
        i++;
        break;
      }
      TiffDirectory.ImageDataElement localImageDataElement = (TiffDirectory.ImageDataElement)localArrayList2.get(j);
      localArrayList1.add(paramByteSource.getBlock(localImageDataElement.offset, localImageDataElement.length));
    }
  }

  public boolean dumpImageFile(PrintWriter paramPrintWriter, ByteSource paramByteSource)
    throws ImageReadException, IOException
  {
    try
    {
      paramPrintWriter.println("tiff.dumpImageFile");
      ImageInfo localImageInfo = getImageInfo(paramByteSource);
      if (localImageInfo == null)
        return false;
      localImageInfo.toString(paramPrintWriter, "");
      paramPrintWriter.println("");
      FormatCompliance localFormatCompliance = FormatCompliance.getDefault();
      ArrayList localArrayList1 = new TiffReader(true).readContents(paramByteSource, null, localFormatCompliance).directories;
      if (localArrayList1 == null)
        return false;
      int i = 0;
      if (i >= localArrayList1.size())
      {
        paramPrintWriter.println("");
        return true;
      }
      ArrayList localArrayList2 = ((TiffDirectory)localArrayList1.get(i)).entries;
      if (localArrayList2 == null)
        return false;
      for (int j = 0; ; j++)
      {
        if (j >= localArrayList2.size())
        {
          i++;
          break;
        }
        ((TiffField)localArrayList2.get(j)).dump(paramPrintWriter, i);
      }
    }
    finally
    {
      paramPrintWriter.println("");
    }
    throw localObject;
  }

  public boolean embedICCProfile(File paramFile1, File paramFile2, byte[] paramArrayOfByte)
  {
    return false;
  }

  public byte[] embedICCProfile(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    return null;
  }

  protected String[] getAcceptedExtensions()
  {
    return ACCEPTED_EXTENSIONS;
  }

  protected ImageFormat[] getAcceptedTypes()
  {
    ImageFormat[] arrayOfImageFormat = new ImageFormat[1];
    arrayOfImageFormat[0] = ImageFormat.IMAGE_FORMAT_TIFF;
    return arrayOfImageFormat;
  }

  public String getDefaultExtension()
  {
    return ".tif";
  }

  public FormatCompliance getFormatCompliance(ByteSource paramByteSource)
    throws ImageReadException, IOException
  {
    FormatCompliance localFormatCompliance = FormatCompliance.getDefault();
    new TiffReader(isStrict(null)).readContents(paramByteSource, null, localFormatCompliance);
    return localFormatCompliance;
  }

  public byte[] getICCProfileBytes(ByteSource paramByteSource, Map paramMap)
    throws ImageReadException, IOException
  {
    FormatCompliance localFormatCompliance = FormatCompliance.getDefault();
    TiffField localTiffField = ((TiffDirectory)new TiffReader(isStrict(paramMap)).readFirstDirectory(paramByteSource, paramMap, false, localFormatCompliance).directories.get(0)).findField(EXIF_TAG_ICC_PROFILE);
    if (localTiffField == null)
      return null;
    return localTiffField.oversizeValue;
  }

  public ImageInfo getImageInfo(ByteSource paramByteSource, Map paramMap)
    throws ImageReadException, IOException
  {
    FormatCompliance localFormatCompliance = FormatCompliance.getDefault();
    TiffContents localTiffContents = new TiffReader(isStrict(paramMap)).readDirectories(paramByteSource, false, localFormatCompliance);
    TiffDirectory localTiffDirectory = (TiffDirectory)localTiffContents.directories.get(0);
    TiffField localTiffField1 = localTiffDirectory.findField(TIFF_TAG_IMAGE_WIDTH, true);
    TiffField localTiffField2 = localTiffDirectory.findField(TIFF_TAG_IMAGE_LENGTH, true);
    if ((localTiffField1 == null) || (localTiffField2 == null))
      throw new ImageReadException("TIFF image missing size info.");
    int i = localTiffField2.getIntValue();
    int j = localTiffField1.getIntValue();
    TiffField localTiffField3 = localTiffDirectory.findField(TIFF_TAG_RESOLUTION_UNIT);
    int k = 2;
    if ((localTiffField3 != null) && (localTiffField3.getValue() != null))
      k = localTiffField3.getIntValue();
    double d1 = -1.0D;
    int m;
    float f1;
    int n;
    float f2;
    int i2;
    ArrayList localArrayList1;
    ArrayList localArrayList2;
    int i3;
    label337: ImageFormat localImageFormat;
    int i5;
    String str1;
    boolean bool;
    String str2;
    switch (k)
    {
    case 1:
    default:
      TiffField localTiffField4 = localTiffDirectory.findField(TIFF_TAG_XRESOLUTION);
      TiffField localTiffField5 = localTiffDirectory.findField(TIFF_TAG_YRESOLUTION);
      m = -1;
      f1 = -1.0F;
      n = -1;
      f2 = -1.0F;
      if (d1 > 0.0D)
      {
        if ((localTiffField4 != null) && (localTiffField4.getValue() != null))
        {
          double d3 = localTiffField4.getDoubleValue();
          m = (int)(d3 / d1);
          f1 = (float)(j / (d3 * d1));
        }
        if ((localTiffField5 != null) && (localTiffField5.getValue() != null))
        {
          double d2 = localTiffField5.getDoubleValue();
          n = (int)(d2 / d1);
          f2 = (float)(i / (d2 * d1));
        }
      }
      TiffField localTiffField6 = localTiffDirectory.findField(TIFF_TAG_BITS_PER_SAMPLE);
      int i1 = -1;
      if ((localTiffField6 != null) && (localTiffField6.getValue() != null))
        i1 = localTiffField6.getIntValueOrArraySum();
      i2 = i1;
      localArrayList1 = new ArrayList();
      localArrayList2 = localTiffDirectory.entries;
      i3 = 0;
      int i4 = localArrayList2.size();
      if (i3 < i4)
        break;
      localImageFormat = ImageFormat.IMAGE_FORMAT_TIFF;
      i5 = localTiffContents.directories.size();
      str1 = "Tiff v." + localTiffContents.header.tiffVersion;
      TiffField localTiffField7 = localTiffDirectory.findField(TIFF_TAG_COLOR_MAP);
      bool = false;
      if (localTiffField7 != null)
        bool = true;
      switch (localTiffDirectory.findField(TIFF_TAG_COMPRESSION).getIntValue())
      {
      default:
        str2 = "Unknown";
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 32771:
      case 32773:
      }
    case 2:
    case 3:
    }
    while (true)
    {
      return new ImageInfo(str1, i2, localArrayList1, localImageFormat, "TIFF Tag-based Image File Format", i, "image/tiff", i5, n, f2, m, f1, j, false, false, bool, 2, str2);
      d1 = 1.0D;
      break;
      d1 = 0.0254D;
      break;
      localArrayList1.add(((TiffField)localArrayList2.get(i3)).toString());
      i3++;
      break label337;
      str2 = "None";
      continue;
      str2 = "CCITT 1D";
      continue;
      str2 = "CCITT Group 3 1-Dimensional Modified Huffman run-length encoding.";
      continue;
      str2 = "CCITT Group 4";
      continue;
      str2 = "LZW";
      continue;
      str2 = "JPEG";
      continue;
      str2 = "None";
      continue;
      str2 = "PackBits";
    }
  }

  public int[] getImageSize(ByteSource paramByteSource, Map paramMap)
    throws ImageReadException, IOException
  {
    FormatCompliance localFormatCompliance = FormatCompliance.getDefault();
    TiffDirectory localTiffDirectory = (TiffDirectory)new TiffReader(isStrict(paramMap)).readFirstDirectory(paramByteSource, paramMap, false, localFormatCompliance).directories.get(0);
    return new int[] { localTiffDirectory.findField(TIFF_TAG_IMAGE_WIDTH).getIntValue(), localTiffDirectory.findField(TIFF_TAG_IMAGE_LENGTH).getIntValue() };
  }

  public IImageMetadata getMetadata(ByteSource paramByteSource, Map paramMap)
    throws ImageReadException, IOException
  {
    FormatCompliance localFormatCompliance = FormatCompliance.getDefault();
    TiffContents localTiffContents = new TiffReader(isStrict(paramMap)).readContents(paramByteSource, paramMap, localFormatCompliance);
    ArrayList localArrayList1 = localTiffContents.directories;
    TiffImageMetadata localTiffImageMetadata = new TiffImageMetadata(localTiffContents);
    int i = 0;
    if (i >= localArrayList1.size())
      return localTiffImageMetadata;
    TiffDirectory localTiffDirectory = (TiffDirectory)localArrayList1.get(i);
    TiffImageMetadata.Directory localDirectory = new TiffImageMetadata.Directory(localTiffDirectory);
    ArrayList localArrayList2 = localTiffDirectory.getDirectoryEntrys();
    for (int j = 0; ; j++)
    {
      if (j >= localArrayList2.size())
      {
        localTiffImageMetadata.add(localDirectory);
        i++;
        break;
      }
      localDirectory.add((TiffField)localArrayList2.get(j));
    }
  }

  public String getName()
  {
    return "Tiff-Custom";
  }

  public String getXmpXml(ByteSource paramByteSource, Map paramMap)
    throws ImageReadException, IOException
  {
    FormatCompliance localFormatCompliance = FormatCompliance.getDefault();
    TiffField localTiffField = ((TiffDirectory)new TiffReader(isStrict(paramMap)).readDirectories(paramByteSource, false, localFormatCompliance).directories.get(0)).findField(TIFF_TAG_XMP, false);
    if (localTiffField == null)
      return null;
    byte[] arrayOfByte = localTiffField.getByteArrayValue();
    try
    {
      String str = new String(arrayOfByte, "utf-8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    throw new ImageReadException("Invalid JPEG XMP Segment.");
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.TiffImageParser
 * JD-Core Version:    0.6.0
 */