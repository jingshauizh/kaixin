package org.apache.sanselan.formats.jpeg.segments;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.formats.jpeg.JpegConstants;

public class JFIFSegment extends Segment
  implements JpegConstants
{
  public final int densityUnits;
  public final int jfifMajorVersion;
  public final int jfifMinorVersion;
  public final int thumbnailSize;
  public final int xDensity;
  public final int xThumbnail;
  public final int yDensity;
  public final int yThumbnail;

  public JFIFSegment(int paramInt1, int paramInt2, InputStream paramInputStream)
    throws ImageReadException, IOException
  {
    super(paramInt1, paramInt2);
    byte[] arrayOfByte = readBytes(paramInputStream, JFIF0_SIGNATURE.length);
    if ((!compareByteArrays(arrayOfByte, JFIF0_SIGNATURE)) && (!compareByteArrays(arrayOfByte, JFIF0_SIGNATURE_ALTERNATIVE)))
      throw new ImageReadException("Not a Valid JPEG File: missing JFIF string");
    this.jfifMajorVersion = readByte("JFIF_major_version", paramInputStream, "Not a Valid JPEG File");
    this.jfifMinorVersion = readByte("JFIF_minor_version", paramInputStream, "Not a Valid JPEG File");
    this.densityUnits = readByte("density_units", paramInputStream, "Not a Valid JPEG File");
    this.xDensity = read2Bytes("x_density", paramInputStream, "Not a Valid JPEG File");
    this.yDensity = read2Bytes("y_density", paramInputStream, "Not a Valid JPEG File");
    this.xThumbnail = readByte("x_thumbnail", paramInputStream, "Not a Valid JPEG File");
    this.yThumbnail = readByte("y_thumbnail", paramInputStream, "Not a Valid JPEG File");
    this.thumbnailSize = (this.xThumbnail * this.yThumbnail);
    if (this.thumbnailSize > 0)
      skipBytes(paramInputStream, this.thumbnailSize, "Not a Valid JPEG File: missing thumbnail");
    if (getDebug())
      System.out.println("");
  }

  public JFIFSegment(int paramInt, byte[] paramArrayOfByte)
    throws ImageReadException, IOException
  {
    this(paramInt, paramArrayOfByte.length, new ByteArrayInputStream(paramArrayOfByte));
  }

  public String getDescription()
  {
    return "JFIF (" + getSegmentType() + ")";
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.jpeg.segments.JFIFSegment
 * JD-Core Version:    0.6.0
 */