package org.apache.sanselan;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

public class ImageInfo
{
  public static final int COLOR_TYPE_BW = 0;
  public static final int COLOR_TYPE_CMYK = 3;
  public static final int COLOR_TYPE_GRAYSCALE = 1;
  public static final int COLOR_TYPE_OTHER = -1;
  public static final int COLOR_TYPE_RGB = 2;
  public static final int COLOR_TYPE_UNKNOWN = -2;
  public static final String COMPRESSION_ALGORITHM_CCITT_1D = "CCITT 1D";
  public static final String COMPRESSION_ALGORITHM_CCITT_GROUP_3 = "CCITT Group 3 1-Dimensional Modified Huffman run-length encoding.";
  public static final String COMPRESSION_ALGORITHM_CCITT_GROUP_4 = "CCITT Group 4";
  public static final String COMPRESSION_ALGORITHM_JPEG = "JPEG";
  public static final String COMPRESSION_ALGORITHM_LZW = "LZW";
  public static final String COMPRESSION_ALGORITHM_NONE = "None";
  public static final String COMPRESSION_ALGORITHM_PACKBITS = "PackBits";
  public static final String COMPRESSION_ALGORITHM_PNG_FILTER = "PNG Filter";
  public static final String COMPRESSION_ALGORITHM_PSD = "Photoshop";
  public static final String COMPRESSION_ALGORITHM_RLE = "RLE: Run-Length Encoding";
  public static final String COMPRESSION_ALGORITHM_UNKNOWN = "Unknown";
  private final int bitsPerPixel;
  private final int colorType;
  private final ArrayList comments;
  private final String compressionAlgorithm;
  private final ImageFormat format;
  private final String formatDetails;
  private final String formatName;
  private final int height;
  private final boolean isProgressive;
  private final boolean isTransparent;
  private final String mimeType;
  private final int numberOfImages;
  private final int physicalHeightDpi;
  private final float physicalHeightInch;
  private final int physicalWidthDpi;
  private final float physicalWidthInch;
  private final boolean usesPalette;
  private final int width;

  public ImageInfo(String paramString1, int paramInt1, ArrayList paramArrayList, ImageFormat paramImageFormat, String paramString2, int paramInt2, String paramString3, int paramInt3, int paramInt4, float paramFloat1, int paramInt5, float paramFloat2, int paramInt6, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt7, String paramString4)
  {
    this.formatDetails = paramString1;
    this.bitsPerPixel = paramInt1;
    this.comments = paramArrayList;
    this.format = paramImageFormat;
    this.formatName = paramString2;
    this.height = paramInt2;
    this.mimeType = paramString3;
    this.numberOfImages = paramInt3;
    this.physicalHeightDpi = paramInt4;
    this.physicalHeightInch = paramFloat1;
    this.physicalWidthDpi = paramInt5;
    this.physicalWidthInch = paramFloat2;
    this.width = paramInt6;
    this.isProgressive = paramBoolean1;
    this.isTransparent = paramBoolean2;
    this.usesPalette = paramBoolean3;
    this.colorType = paramInt7;
    this.compressionAlgorithm = paramString4;
  }

  public void dump()
  {
    System.out.print(toString());
  }

  public int getBitsPerPixel()
  {
    return this.bitsPerPixel;
  }

  public int getColorType()
  {
    return this.colorType;
  }

  public String getColorTypeDescription()
  {
    switch (this.colorType)
    {
    default:
      return "Unknown";
    case 0:
      return "Black and White";
    case 1:
      return "Grayscale";
    case 2:
      return "RGB";
    case 3:
      return "CMYK";
    case -1:
      return "Other";
    case -2:
    }
    return "Unknown";
  }

  public ArrayList getComments()
  {
    return new ArrayList(this.comments);
  }

  public ImageFormat getFormat()
  {
    return this.format;
  }

  public String getFormatName()
  {
    return this.formatName;
  }

  public int getHeight()
  {
    return this.height;
  }

  public boolean getIsProgressive()
  {
    return this.isProgressive;
  }

  public String getMimeType()
  {
    return this.mimeType;
  }

  public int getNumberOfImages()
  {
    return this.numberOfImages;
  }

  public int getPhysicalHeightDpi()
  {
    return this.physicalHeightDpi;
  }

  public float getPhysicalHeightInch()
  {
    return this.physicalHeightInch;
  }

  public int getPhysicalWidthDpi()
  {
    return this.physicalWidthDpi;
  }

  public float getPhysicalWidthInch()
  {
    return this.physicalWidthInch;
  }

  public int getWidth()
  {
    return this.width;
  }

  public String toString()
  {
    try
    {
      StringWriter localStringWriter = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
      toString(localPrintWriter, "");
      localPrintWriter.flush();
      String str = localStringWriter.toString();
      return str;
    }
    catch (Exception localException)
    {
    }
    return "Image Data: Error";
  }

  public void toString(PrintWriter paramPrintWriter, String paramString)
    throws ImageReadException, IOException
  {
    paramPrintWriter.println("Format Details: " + this.formatDetails);
    paramPrintWriter.println("Bits Per Pixel: " + this.bitsPerPixel);
    paramPrintWriter.println("Comments: " + this.comments.size());
    for (int i = 0; ; i++)
    {
      if (i >= this.comments.size())
      {
        paramPrintWriter.println("Format: " + this.format.name);
        paramPrintWriter.println("Format Name: " + this.formatName);
        paramPrintWriter.println("Compression Algorithm: " + this.compressionAlgorithm);
        paramPrintWriter.println("Height: " + this.height);
        paramPrintWriter.println("MimeType: " + this.mimeType);
        paramPrintWriter.println("Number Of Images: " + this.numberOfImages);
        paramPrintWriter.println("Physical Height Dpi: " + this.physicalHeightDpi);
        paramPrintWriter.println("Physical Height Inch: " + this.physicalHeightInch);
        paramPrintWriter.println("Physical Width Dpi: " + this.physicalWidthDpi);
        paramPrintWriter.println("Physical Width Inch: " + this.physicalWidthInch);
        paramPrintWriter.println("Width: " + this.width);
        paramPrintWriter.println("Is Progressive: " + this.isProgressive);
        paramPrintWriter.println("Is Transparent: " + this.isTransparent);
        paramPrintWriter.println("Color Type: " + getColorTypeDescription());
        paramPrintWriter.println("Uses Palette: " + this.usesPalette);
        paramPrintWriter.flush();
        return;
      }
      String str = (String)this.comments.get(i);
      paramPrintWriter.println("\t" + i + ": '" + str + "'");
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.ImageInfo
 * JD-Core Version:    0.6.0
 */