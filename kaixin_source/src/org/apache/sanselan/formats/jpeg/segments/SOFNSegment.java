package org.apache.sanselan.formats.jpeg.segments;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import org.apache.sanselan.ImageReadException;

public class SOFNSegment extends Segment
{
  public final int height;
  public final int numberOfComponents;
  public final int precision;
  public final int width;

  public SOFNSegment(int paramInt1, int paramInt2, InputStream paramInputStream)
    throws ImageReadException, IOException
  {
    super(paramInt1, paramInt2);
    if (getDebug())
      System.out.println("SOF0Segment marker_length: " + paramInt2);
    this.precision = readByte("Data_precision", paramInputStream, "Not a Valid JPEG File");
    this.height = read2Bytes("Image_height", paramInputStream, "Not a Valid JPEG File");
    this.width = read2Bytes("Image_Width", paramInputStream, "Not a Valid JPEG File");
    this.numberOfComponents = readByte("Number_of_components", paramInputStream, "Not a Valid JPEG File");
    skipBytes(paramInputStream, paramInt2 - 6, "Not a Valid JPEG File: SOF0 Segment");
    if (getDebug())
      System.out.println("");
  }

  public SOFNSegment(int paramInt, byte[] paramArrayOfByte)
    throws ImageReadException, IOException
  {
    this(paramInt, paramArrayOfByte.length, new ByteArrayInputStream(paramArrayOfByte));
  }

  public String getDescription()
  {
    return "SOFN (SOF" + (this.marker - 65472) + ") (" + getSegmentType() + ")";
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.jpeg.segments.SOFNSegment
 * JD-Core Version:    0.6.0
 */