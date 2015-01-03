package org.apache.sanselan.formats.jpeg.segments;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.formats.jpeg.JpegImageParser;

public class App2Segment extends APPNSegment
  implements Comparable
{
  public final int cur_marker;
  public final byte[] icc_bytes;
  public final int num_markers;

  public App2Segment(int paramInt1, int paramInt2, InputStream paramInputStream)
    throws ImageReadException, IOException
  {
    super(paramInt1, paramInt2, paramInputStream);
    if (startsWith(this.bytes, JpegImageParser.icc_profile_label))
    {
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(this.bytes);
      readAndVerifyBytes(localByteArrayInputStream, JpegImageParser.icc_profile_label, "Not a Valid App2 Segment: missing ICC Profile label");
      this.cur_marker = readByte("cur_marker", localByteArrayInputStream, "Not a valid App2 Marker");
      this.num_markers = readByte("num_markers", localByteArrayInputStream, "Not a valid App2 Marker");
      this.icc_bytes = readByteArray("App2 Data", -2 + (paramInt2 - JpegImageParser.icc_profile_label.length), localByteArrayInputStream, "Invalid App2 Segment: insufficient data");
      return;
    }
    this.cur_marker = -1;
    this.num_markers = -1;
    this.icc_bytes = null;
  }

  public App2Segment(int paramInt, byte[] paramArrayOfByte)
    throws ImageReadException, IOException
  {
    this(paramInt, paramArrayOfByte.length, new ByteArrayInputStream(paramArrayOfByte));
  }

  public int compareTo(Object paramObject)
  {
    App2Segment localApp2Segment = (App2Segment)paramObject;
    return this.cur_marker - localApp2Segment.cur_marker;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.jpeg.segments.App2Segment
 * JD-Core Version:    0.6.0
 */