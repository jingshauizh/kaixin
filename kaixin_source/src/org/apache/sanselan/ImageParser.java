package org.apache.sanselan;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import org.apache.sanselan.common.BinaryFileParser;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.common.byteSources.ByteSource;
import org.apache.sanselan.common.byteSources.ByteSourceArray;
import org.apache.sanselan.common.byteSources.ByteSourceFile;
import org.apache.sanselan.formats.jpeg.JpegImageParser;
import org.apache.sanselan.formats.tiff.TiffImageParser;

public abstract class ImageParser extends BinaryFileParser
  implements SanselanConstants
{
  public static final ImageParser[] getAllImageParsers()
  {
    ImageParser[] arrayOfImageParser = new ImageParser[2];
    arrayOfImageParser[0] = new JpegImageParser();
    arrayOfImageParser[1] = new TiffImageParser();
    return arrayOfImageParser;
  }

  public static final boolean isStrict(Map paramMap)
  {
    if ((paramMap == null) || (!paramMap.containsKey("STRICT")))
      return false;
    return ((Boolean)paramMap.get("STRICT")).booleanValue();
  }

  protected final boolean canAcceptExtension(File paramFile)
  {
    return canAcceptExtension(paramFile.getName());
  }

  protected final boolean canAcceptExtension(String paramString)
  {
    String[] arrayOfString = getAcceptedExtensions();
    if (arrayOfString == null)
      return true;
    int i = paramString.lastIndexOf('.');
    String str;
    if (i >= 0)
      str = paramString.substring(i).toLowerCase();
    for (int j = 0; ; j++)
    {
      if (j >= arrayOfString.length)
        return false;
      if (arrayOfString[j].toLowerCase().equals(str))
        break;
    }
  }

  public boolean canAcceptType(ImageFormat paramImageFormat)
  {
    ImageFormat[] arrayOfImageFormat = getAcceptedTypes();
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfImageFormat.length)
        return false;
      if (arrayOfImageFormat[i].equals(paramImageFormat))
        return true;
    }
  }

  public final String dumpImageFile(File paramFile)
    throws ImageReadException, IOException
  {
    if (!canAcceptExtension(paramFile))
      return null;
    if (this.debug)
      System.out.println(getName() + ": " + paramFile.getName());
    return dumpImageFile(new ByteSourceFile(paramFile));
  }

  public final String dumpImageFile(ByteSource paramByteSource)
    throws ImageReadException, IOException
  {
    StringWriter localStringWriter = new StringWriter();
    PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
    dumpImageFile(localPrintWriter, paramByteSource);
    localPrintWriter.flush();
    return localStringWriter.toString();
  }

  public final String dumpImageFile(byte[] paramArrayOfByte)
    throws ImageReadException, IOException
  {
    return dumpImageFile(new ByteSourceArray(paramArrayOfByte));
  }

  public boolean dumpImageFile(PrintWriter paramPrintWriter, ByteSource paramByteSource)
    throws ImageReadException, IOException
  {
    return false;
  }

  public abstract boolean embedICCProfile(File paramFile1, File paramFile2, byte[] paramArrayOfByte);

  protected abstract String[] getAcceptedExtensions();

  protected abstract ImageFormat[] getAcceptedTypes();

  public abstract String getDefaultExtension();

  public final FormatCompliance getFormatCompliance(File paramFile)
    throws ImageReadException, IOException
  {
    if (!canAcceptExtension(paramFile))
      return null;
    return getFormatCompliance(new ByteSourceFile(paramFile));
  }

  public FormatCompliance getFormatCompliance(ByteSource paramByteSource)
    throws ImageReadException, IOException
  {
    return null;
  }

  public final FormatCompliance getFormatCompliance(byte[] paramArrayOfByte)
    throws ImageReadException, IOException
  {
    return getFormatCompliance(new ByteSourceArray(paramArrayOfByte));
  }

  public final byte[] getICCProfileBytes(File paramFile)
    throws ImageReadException, IOException
  {
    return getICCProfileBytes(paramFile, null);
  }

  public final byte[] getICCProfileBytes(File paramFile, Map paramMap)
    throws ImageReadException, IOException
  {
    if (!canAcceptExtension(paramFile))
      return null;
    if (this.debug)
      System.out.println(getName() + ": " + paramFile.getName());
    return getICCProfileBytes(new ByteSourceFile(paramFile), paramMap);
  }

  public abstract byte[] getICCProfileBytes(ByteSource paramByteSource, Map paramMap)
    throws ImageReadException, IOException;

  public final byte[] getICCProfileBytes(byte[] paramArrayOfByte)
    throws ImageReadException, IOException
  {
    return getICCProfileBytes(paramArrayOfByte, null);
  }

  public final byte[] getICCProfileBytes(byte[] paramArrayOfByte, Map paramMap)
    throws ImageReadException, IOException
  {
    return getICCProfileBytes(new ByteSourceArray(paramArrayOfByte), paramMap);
  }

  public final ImageInfo getImageInfo(File paramFile, Map paramMap)
    throws ImageReadException, IOException
  {
    if (!canAcceptExtension(paramFile))
      return null;
    return getImageInfo(new ByteSourceFile(paramFile), paramMap);
  }

  public final ImageInfo getImageInfo(ByteSource paramByteSource)
    throws ImageReadException, IOException
  {
    return getImageInfo(paramByteSource, null);
  }

  public abstract ImageInfo getImageInfo(ByteSource paramByteSource, Map paramMap)
    throws ImageReadException, IOException;

  public final ImageInfo getImageInfo(byte[] paramArrayOfByte, Map paramMap)
    throws ImageReadException, IOException
  {
    return getImageInfo(new ByteSourceArray(paramArrayOfByte), paramMap);
  }

  public final IImageMetadata getMetadata(File paramFile)
    throws ImageReadException, IOException
  {
    return getMetadata(paramFile, null);
  }

  public final IImageMetadata getMetadata(File paramFile, Map paramMap)
    throws ImageReadException, IOException
  {
    if (this.debug)
      System.out.println(getName() + ".getMetadata" + ": " + paramFile.getName());
    if (!canAcceptExtension(paramFile))
      return null;
    return getMetadata(new ByteSourceFile(paramFile), paramMap);
  }

  public final IImageMetadata getMetadata(ByteSource paramByteSource)
    throws ImageReadException, IOException
  {
    return getMetadata(paramByteSource, null);
  }

  public abstract IImageMetadata getMetadata(ByteSource paramByteSource, Map paramMap)
    throws ImageReadException, IOException;

  public final IImageMetadata getMetadata(byte[] paramArrayOfByte)
    throws ImageReadException, IOException
  {
    return getMetadata(paramArrayOfByte);
  }

  public final IImageMetadata getMetadata(byte[] paramArrayOfByte, Map paramMap)
    throws ImageReadException, IOException
  {
    return getMetadata(new ByteSourceArray(paramArrayOfByte), paramMap);
  }

  public abstract String getName();

  public abstract String getXmpXml(ByteSource paramByteSource, Map paramMap)
    throws ImageReadException, IOException;
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.ImageParser
 * JD-Core Version:    0.6.0
 */