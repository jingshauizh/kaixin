package org.apache.sanselan.formats.jpeg.segments;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import org.apache.sanselan.ImageReadException;

public abstract class GenericSegment extends Segment
{
  public final byte[] bytes;

  public GenericSegment(int paramInt1, int paramInt2, InputStream paramInputStream)
    throws ImageReadException, IOException
  {
    super(paramInt1, paramInt2);
    this.bytes = readByteArray("Segment Data", paramInt2, paramInputStream, "Invalid Segment: insufficient data");
  }

  public GenericSegment(int paramInt, byte[] paramArrayOfByte)
    throws ImageReadException, IOException
  {
    super(paramInt, paramArrayOfByte.length);
    this.bytes = paramArrayOfByte;
  }

  public void dump(PrintWriter paramPrintWriter)
  {
    dump(paramPrintWriter, 0);
  }

  public void dump(PrintWriter paramPrintWriter, int paramInt)
  {
    for (int i = 0; ; i++)
    {
      if ((i >= 50) || (i + paramInt >= this.bytes.length))
        return;
      debugNumber(paramPrintWriter, "\t" + (i + paramInt), this.bytes[(i + paramInt)]);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.jpeg.segments.GenericSegment
 * JD-Core Version:    0.6.0
 */