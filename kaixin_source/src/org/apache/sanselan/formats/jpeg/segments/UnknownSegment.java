package org.apache.sanselan.formats.jpeg.segments;

import java.io.IOException;
import java.io.InputStream;
import org.apache.sanselan.ImageReadException;

public class UnknownSegment extends GenericSegment
{
  public UnknownSegment(int paramInt1, int paramInt2, InputStream paramInputStream)
    throws ImageReadException, IOException
  {
    super(paramInt1, paramInt2, paramInputStream);
  }

  public UnknownSegment(int paramInt, byte[] paramArrayOfByte)
    throws ImageReadException, IOException
  {
    super(paramInt, paramArrayOfByte);
  }

  public String getDescription()
  {
    return "Unknown (" + getSegmentType() + ")";
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.jpeg.segments.UnknownSegment
 * JD-Core Version:    0.6.0
 */