package org.apache.sanselan.formats.jpeg.segments;

import java.io.IOException;
import java.io.InputStream;
import org.apache.sanselan.ImageReadException;

public class APPNSegment extends GenericSegment
{
  public APPNSegment(int paramInt1, int paramInt2, InputStream paramInputStream)
    throws ImageReadException, IOException
  {
    super(paramInt1, paramInt2, paramInputStream);
  }

  public String getDescription()
  {
    return "APPN (APP" + (this.marker - 65504) + ") (" + getSegmentType() + ")";
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.jpeg.segments.APPNSegment
 * JD-Core Version:    0.6.0
 */