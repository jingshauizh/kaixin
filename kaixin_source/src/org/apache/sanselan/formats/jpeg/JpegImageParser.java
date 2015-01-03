package org.apache.sanselan.formats.jpeg;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.sanselan.ImageFormat;
import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageParser;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.common.byteSources.ByteSource;
import org.apache.sanselan.formats.jpeg.segments.App2Segment;
import org.apache.sanselan.formats.jpeg.segments.GenericSegment;
import org.apache.sanselan.formats.jpeg.segments.JFIFSegment;
import org.apache.sanselan.formats.jpeg.segments.SOFNSegment;
import org.apache.sanselan.formats.jpeg.segments.Segment;
import org.apache.sanselan.formats.jpeg.segments.UnknownSegment;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.TiffImageParser;
import org.apache.sanselan.formats.tiff.constants.TiffTagConstants;
import org.apache.sanselan.util.Debug;

public class JpegImageParser extends ImageParser
  implements JpegConstants, TiffTagConstants
{
  public static final String[] AcceptedExtensions = { ".jpg", ".jpeg" };
  private static final String DEFAULT_EXTENSION = ".jpg";
  public static final boolean permissive = true;

  public JpegImageParser()
  {
    setByteOrder(77);
  }

  private byte[] assembleSegments(ArrayList paramArrayList)
    throws ImageReadException, IOException
  {
    try
    {
      byte[] arrayOfByte = assembleSegments(paramArrayList, false);
      return arrayOfByte;
    }
    catch (ImageReadException localImageReadException)
    {
    }
    return assembleSegments(paramArrayList, true);
  }

  private byte[] assembleSegments(ArrayList paramArrayList, boolean paramBoolean)
    throws ImageReadException, IOException
  {
    int i = 1;
    if (paramArrayList.size() < i)
      throw new ImageReadException("No App2 Segments Found.");
    int j = ((App2Segment)paramArrayList.get(0)).num_markers;
    if (paramArrayList.size() != j)
      throw new ImageReadException("App2 Segments Missing.  Found: " + paramArrayList.size() + ", Expected: " + j + ".");
    Collections.sort(paramArrayList);
    if (paramBoolean)
      i = 0;
    int k = 0;
    int m = 0;
    byte[] arrayOfByte;
    int n;
    if (m >= paramArrayList.size())
    {
      arrayOfByte = new byte[k];
      n = 0;
    }
    for (int i1 = 0; ; i1++)
    {
      if (i1 >= paramArrayList.size())
      {
        return arrayOfByte;
        App2Segment localApp2Segment1 = (App2Segment)paramArrayList.get(m);
        if (m + i != localApp2Segment1.cur_marker)
        {
          dumpSegments(paramArrayList);
          throw new ImageReadException("Incoherent App2 Segment Ordering.  i: " + m + ", segment[" + m + "].cur_marker: " + localApp2Segment1.cur_marker + ".");
        }
        if (j != localApp2Segment1.num_markers)
        {
          dumpSegments(paramArrayList);
          throw new ImageReadException("Inconsistent App2 Segment Count info.  markerCount: " + j + ", segment[" + m + "].num_markers: " + localApp2Segment1.num_markers + ".");
        }
        k += localApp2Segment1.icc_bytes.length;
        m++;
        break;
      }
      App2Segment localApp2Segment2 = (App2Segment)paramArrayList.get(i1);
      System.arraycopy(localApp2Segment2.icc_bytes, 0, arrayOfByte, n, localApp2Segment2.icc_bytes.length);
      n += localApp2Segment2.icc_bytes.length;
    }
  }

  private void dumpSegments(ArrayList paramArrayList)
  {
    Debug.debug();
    Debug.debug("dumpSegments", paramArrayList.size());
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayList.size())
      {
        Debug.debug();
        return;
      }
      App2Segment localApp2Segment = (App2Segment)paramArrayList.get(i);
      Debug.debug(i + ": " + localApp2Segment.cur_marker + " / " + localApp2Segment.num_markers);
    }
  }

  private ArrayList filterAPP1Segments(ArrayList paramArrayList)
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayList.size())
        return localArrayList;
      GenericSegment localGenericSegment = (GenericSegment)paramArrayList.get(i);
      if (!isExifAPP1Segment(localGenericSegment))
        continue;
      localArrayList.add(localGenericSegment);
    }
  }

  private ArrayList filterSegments(ArrayList paramArrayList, List paramList)
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayList.size())
        return localArrayList;
      Segment localSegment = (Segment)paramArrayList.get(i);
      if (!paramList.contains(new Integer(localSegment.marker)))
        continue;
      localArrayList.add(localSegment);
    }
  }

  public static boolean isExifAPP1Segment(GenericSegment paramGenericSegment)
  {
    return byteArrayHasPrefix(paramGenericSegment.bytes, EXIF_IDENTIFIER_CODE);
  }

  private boolean keepMarker(int paramInt, int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null)
      return true;
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfInt.length)
        return false;
      if (paramArrayOfInt[i] == paramInt)
        break;
    }
  }

  public boolean dumpImageFile(PrintWriter paramPrintWriter, ByteSource paramByteSource)
    throws ImageReadException, IOException
  {
    paramPrintWriter.println("tiff.dumpImageFile");
    ImageInfo localImageInfo = getImageInfo(paramByteSource);
    if (localImageInfo == null)
      return false;
    localImageInfo.toString(paramPrintWriter, "");
    paramPrintWriter.println("");
    ArrayList localArrayList = readSegments(paramByteSource, null, false);
    if (localArrayList == null)
      throw new ImageReadException("No Segments Found.");
    for (int i = 0; ; i++)
    {
      if (i >= localArrayList.size())
      {
        paramPrintWriter.println("");
        return true;
      }
      Segment localSegment = (Segment)localArrayList.get(i);
      NumberFormat localNumberFormat = NumberFormat.getIntegerInstance();
      paramPrintWriter.println(i + ": marker: " + Integer.toHexString(localSegment.marker) + ", " + localSegment.getDescription() + " (length: " + localNumberFormat.format(localSegment.length) + ")");
      localSegment.dump(paramPrintWriter);
    }
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
    return AcceptedExtensions;
  }

  protected ImageFormat[] getAcceptedTypes()
  {
    ImageFormat[] arrayOfImageFormat = new ImageFormat[1];
    arrayOfImageFormat[0] = ImageFormat.IMAGE_FORMAT_JPEG;
    return arrayOfImageFormat;
  }

  public String getDefaultExtension()
  {
    return ".jpg";
  }

  public TiffImageMetadata getExifMetadata(ByteSource paramByteSource, Map paramMap)
    throws ImageReadException, IOException
  {
    byte[] arrayOfByte = getExifRawData(paramByteSource);
    if (arrayOfByte == null)
      return null;
    if (paramMap == null)
      paramMap = new HashMap();
    if (!paramMap.containsKey("READ_THUMBNAILS"))
      paramMap.put("READ_THUMBNAILS", Boolean.TRUE);
    return (TiffImageMetadata)new TiffImageParser().getMetadata(arrayOfByte, paramMap);
  }

  public byte[] getExifRawData(ByteSource paramByteSource)
    throws ImageReadException, IOException
  {
    ArrayList localArrayList1 = readSegments(paramByteSource, new int[] { 65505 }, false);
    if ((localArrayList1 == null) || (localArrayList1.size() < 1));
    ArrayList localArrayList2;
    do
    {
      return null;
      localArrayList2 = filterAPP1Segments(localArrayList1);
      if (!this.debug)
        continue;
      System.out.println("exif_segments.size: " + localArrayList2.size());
    }
    while (localArrayList2.size() < 1);
    if (localArrayList2.size() > 1)
      throw new ImageReadException("Sanselan currently can't parse EXIF metadata split across multiple APP1 segments.  Please send this image to the Sanselan project.");
    return getByteArrayTail("trimmed exif bytes", ((GenericSegment)localArrayList2.get(0)).bytes, 6);
  }

  public byte[] getICCProfileBytes(ByteSource paramByteSource, Map paramMap)
    throws ImageReadException, IOException
  {
    Object localObject = readSegments(paramByteSource, new int[] { 65506 }, false);
    ArrayList localArrayList;
    if (localObject != null)
      localArrayList = new ArrayList();
    for (int i = 0; ; i++)
    {
      if (i >= ((ArrayList)localObject).size())
      {
        localObject = localArrayList;
        if ((localObject != null) && (((ArrayList)localObject).size() >= 1))
          break;
        arrayOfByte = null;
        return arrayOfByte;
      }
      App2Segment localApp2Segment = (App2Segment)((ArrayList)localObject).get(i);
      if (localApp2Segment.icc_bytes == null)
        continue;
      localArrayList.add(localApp2Segment);
    }
    byte[] arrayOfByte = assembleSegments((ArrayList)localObject);
    PrintStream localPrintStream;
    StringBuilder localStringBuilder;
    String str;
    if (this.debug)
    {
      localPrintStream = System.out;
      localStringBuilder = new StringBuilder("bytes: ");
      str = null;
      if (arrayOfByte != null)
        break label167;
    }
    while (true)
    {
      localPrintStream.println(str);
      if (!this.debug)
        break;
      System.out.println("");
      return arrayOfByte;
      label167: str = arrayOfByte.length;
    }
  }

  public ImageInfo getImageInfo(ByteSource paramByteSource, Map paramMap)
    throws ImageReadException, IOException
  {
    ArrayList localArrayList1 = readSegments(paramByteSource, new int[] { 65472, 65473, 65474, 65475, 65477, 65478, 65479, 65481, 65482, 65483, 65485, 65486, 65487 }, false);
    if (localArrayList1 == null)
      throw new ImageReadException("No SOFN Data Found.");
    ArrayList localArrayList2 = readSegments(paramByteSource, new int[] { 65504 }, true);
    SOFNSegment localSOFNSegment = (SOFNSegment)localArrayList1.get(0);
    if (localSOFNSegment == null)
      throw new ImageReadException("No SOFN Data Found.");
    int i = localSOFNSegment.width;
    int j = localSOFNSegment.height;
    JFIFSegment localJFIFSegment = null;
    if (localArrayList2 != null)
    {
      int i4 = localArrayList2.size();
      localJFIFSegment = null;
      if (i4 > 0)
        localJFIFSegment = (JFIFSegment)localArrayList2.get(0);
    }
    double d1 = -1.0D;
    double d2 = -1.0D;
    double d3 = -1.0D;
    String str;
    int k;
    float f1;
    int m;
    float f2;
    ArrayList localArrayList3;
    int n;
    int i1;
    ImageFormat localImageFormat;
    boolean bool;
    label422: int i2;
    if (localJFIFSegment != null)
    {
      d1 = localJFIFSegment.xDensity;
      d2 = localJFIFSegment.yDensity;
      int i3 = localJFIFSegment.densityUnits;
      str = "Jpeg/JFIF v." + localJFIFSegment.jfifMajorVersion + "." + localJFIFSegment.jfifMinorVersion;
      switch (i3)
      {
      case 0:
      default:
        k = -1;
        f1 = -1.0F;
        m = -1;
        f2 = -1.0F;
        if (d3 > 0.0D)
        {
          m = (int)Math.round(d1 / d3);
          f2 = (float)(i / (d1 * d3));
          k = (int)Math.round(d2 * d3);
          f1 = (float)(j / (d2 * d3));
        }
        localArrayList3 = new ArrayList();
        n = localSOFNSegment.numberOfComponents;
        i1 = n * localSOFNSegment.precision;
        localImageFormat = ImageFormat.IMAGE_FORMAT_JPEG;
        if (localSOFNSegment.marker != 65474)
          break;
        bool = true;
        if (n == 1)
          i2 = 0;
      case 1:
      case 2:
      }
    }
    while (true)
    {
      return new ImageInfo(str, i1, localArrayList3, localImageFormat, "JPEG (Joint Photographic Experts Group) Format", j, "image/jpeg", 1, k, f1, m, f2, i, bool, false, false, i2, "JPEG");
      d3 = 1.0D;
      break;
      d3 = 2.54D;
      break;
      JpegImageMetadata localJpegImageMetadata = (JpegImageMetadata)getMetadata(paramByteSource, paramMap);
      if (localJpegImageMetadata != null)
      {
        TiffField localTiffField1 = localJpegImageMetadata.findEXIFValue(TIFF_TAG_XRESOLUTION);
        if (localTiffField1 != null)
          d1 = ((Number)localTiffField1.getValue()).doubleValue();
        TiffField localTiffField2 = localJpegImageMetadata.findEXIFValue(TIFF_TAG_YRESOLUTION);
        if (localTiffField2 != null)
          d2 = ((Number)localTiffField2.getValue()).doubleValue();
        TiffField localTiffField3 = localJpegImageMetadata.findEXIFValue(TIFF_TAG_RESOLUTION_UNIT);
        if (localTiffField3 != null)
          switch (((Number)localTiffField3.getValue()).intValue())
          {
          case 1:
          default:
          case 2:
          case 3:
          }
      }
      while (true)
      {
        str = "Jpeg/DCM";
        break;
        d3 = 1.0D;
        continue;
        d3 = 2.54D;
      }
      bool = false;
      break label422;
      if (n == 3)
      {
        i2 = 2;
        continue;
      }
      if (n == 4)
      {
        i2 = 3;
        continue;
      }
      i2 = -2;
    }
  }

  public int[] getImageSize(ByteSource paramByteSource, Map paramMap)
    throws ImageReadException, IOException
  {
    ArrayList localArrayList = readSegments(paramByteSource, new int[] { 65472, 65473, 65474, 65475, 65477, 65478, 65479, 65481, 65482, 65483, 65485, 65486, 65487 }, true);
    if ((localArrayList == null) || (localArrayList.size() < 1))
      throw new ImageReadException("No JFIF Data Found.");
    if (localArrayList.size() > 1)
      throw new ImageReadException("Redundant JFIF Data Found.");
    SOFNSegment localSOFNSegment = (SOFNSegment)localArrayList.get(0);
    int[] arrayOfInt = new int[2];
    arrayOfInt[0] = localSOFNSegment.width;
    arrayOfInt[1] = localSOFNSegment.height;
    return arrayOfInt;
  }

  public IImageMetadata getMetadata(ByteSource paramByteSource, Map paramMap)
    throws ImageReadException, IOException
  {
    TiffImageMetadata localTiffImageMetadata = getExifMetadata(paramByteSource, paramMap);
    if ((localTiffImageMetadata == null) && (0 == 0))
      return null;
    return new JpegImageMetadata(null, localTiffImageMetadata);
  }

  public String getName()
  {
    return "Jpeg-Custom";
  }

  public Object getPhotoshopMetadata(ByteSource paramByteSource, Map paramMap)
    throws ImageReadException, IOException
  {
    return null;
  }

  public String getXmpXml(ByteSource paramByteSource, Map paramMap)
    throws ImageReadException, IOException
  {
    return null;
  }

  public boolean hasExifSegment(ByteSource paramByteSource)
    throws ImageReadException, IOException
  {
    boolean[] arrayOfBoolean = new boolean[1];
    2 local2 = new JpegUtils.Visitor(arrayOfBoolean)
    {
      public boolean beginSOS()
      {
        return false;
      }

      public void visitSOS(int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
      {
      }

      public boolean visitSOS(int paramInt, byte[] paramArrayOfByte, InputStream paramInputStream)
      {
        return false;
      }

      public boolean visitSegment(int paramInt1, byte[] paramArrayOfByte1, int paramInt2, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
        throws ImageReadException, IOException
      {
        if (paramInt1 == 65497)
          return false;
        if ((paramInt1 == 65505) && (JpegImageParser.byteArrayHasPrefix(paramArrayOfByte3, JpegImageParser.EXIF_IDENTIFIER_CODE)))
        {
          this.val$result[0] = true;
          return false;
        }
        return true;
      }
    };
    new JpegUtils().traverseJFIF(paramByteSource, local2);
    return arrayOfBoolean[0];
  }

  public boolean hasIptcSegment(ByteSource paramByteSource)
    throws ImageReadException, IOException
  {
    return new boolean[1][0];
  }

  public boolean hasXmpSegment(ByteSource paramByteSource)
    throws ImageReadException, IOException
  {
    return new boolean[1][0];
  }

  public ArrayList readSegments(ByteSource paramByteSource, int[] paramArrayOfInt, boolean paramBoolean)
    throws ImageReadException, IOException
  {
    return readSegments(paramByteSource, paramArrayOfInt, paramBoolean, false);
  }

  public ArrayList readSegments(ByteSource paramByteSource, int[] paramArrayOfInt, boolean paramBoolean1, boolean paramBoolean2)
    throws ImageReadException, IOException
  {
    ArrayList localArrayList = new ArrayList();
    1 local1 = new JpegUtils.Visitor(paramArrayOfInt, localArrayList, paramBoolean1)
    {
      public boolean beginSOS()
      {
        return false;
      }

      public void visitSOS(int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
      {
      }

      public boolean visitSOS(int paramInt, byte[] paramArrayOfByte, InputStream paramInputStream)
      {
        return false;
      }

      public boolean visitSegment(int paramInt1, byte[] paramArrayOfByte1, int paramInt2, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
        throws ImageReadException, IOException
      {
        if (paramInt1 == 65497);
        while (true)
        {
          return false;
          if (!JpegImageParser.this.keepMarker(paramInt1, this.val$markers))
            return true;
          if (paramInt1 != 65517)
          {
            if (paramInt1 != 65506)
              break label64;
            this.val$result.add(new App2Segment(paramInt1, paramArrayOfByte3));
          }
          while (!this.val$returnAfterFirst)
          {
            return true;
            label64: if (paramInt1 == 65504)
            {
              this.val$result.add(new JFIFSegment(paramInt1, paramArrayOfByte3));
              continue;
            }
            if ((paramInt1 >= 65472) && (paramInt1 <= 65487))
            {
              this.val$result.add(new SOFNSegment(paramInt1, paramArrayOfByte3));
              continue;
            }
            if ((paramInt1 < 65505) || (paramInt1 > 65519))
              continue;
            this.val$result.add(new UnknownSegment(paramInt1, paramArrayOfByte3));
          }
        }
      }
    };
    new JpegUtils().traverseJFIF(paramByteSource, local1);
    return localArrayList;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.jpeg.JpegImageParser
 * JD-Core Version:    0.6.0
 */