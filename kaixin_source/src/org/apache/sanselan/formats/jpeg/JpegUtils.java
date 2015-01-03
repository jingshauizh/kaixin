package org.apache.sanselan.formats.jpeg;

import java.io.IOException;
import java.io.InputStream;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.common.BinaryFileParser;
import org.apache.sanselan.common.byteSources.ByteSource;
import org.apache.sanselan.util.Debug;

public class JpegUtils extends BinaryFileParser
  implements JpegConstants
{
  public JpegUtils()
  {
    setByteOrder(77);
  }

  public static String getMarkerName(int paramInt)
  {
    switch (paramInt)
    {
    case 65488:
    case 65489:
    case 65490:
    case 65491:
    case 65492:
    case 65493:
    case 65494:
    case 65495:
    case 65496:
    case 65497:
    case 65499:
    case 65500:
    case 65501:
    case 65502:
    case 65503:
    case 65507:
    case 65508:
    case 65509:
    case 65510:
    case 65511:
    case 65512:
    case 65513:
    case 65514:
    case 65515:
    case 65516:
    default:
      return "Unknown";
    case 65498:
      return "SOS_Marker";
    case 65505:
      return "JPEG_APP1_Marker";
    case 65506:
      return "JPEG_APP2_Marker";
    case 65517:
      return "JPEG_APP13_Marker";
    case 65518:
      return "JPEG_APP14_Marker";
    case 65519:
      return "JPEG_APP15_Marker";
    case 65504:
      return "JFIFMarker";
    case 65472:
      return "SOF0Marker";
    case 65473:
      return "SOF1Marker";
    case 65474:
      return "SOF2Marker";
    case 65475:
      return "SOF3Marker";
    case 65476:
      return "SOF4Marker";
    case 65477:
      return "SOF5Marker";
    case 65478:
      return "SOF6Marker";
    case 65479:
      return "SOF7Marker";
    case 65480:
      return "SOF8Marker";
    case 65481:
      return "SOF9Marker";
    case 65482:
      return "SOF10Marker";
    case 65483:
      return "SOF11Marker";
    case 65484:
      return "SOF12Marker";
    case 65485:
      return "SOF13Marker";
    case 65486:
      return "SOF14Marker";
    case 65487:
    }
    return "SOF15Marker";
  }

  public void dumpJFIF(ByteSource paramByteSource)
    throws ImageReadException, IOException, ImageWriteException
  {
    traverseJFIF(paramByteSource, new Visitor()
    {
      public boolean beginSOS()
      {
        return true;
      }

      public void visitSOS(int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
      {
        Debug.debug("SOS marker.  " + paramArrayOfByte2.length + " bytes of image data.");
        Debug.debug("");
      }

      public boolean visitSOS(int paramInt, byte[] paramArrayOfByte, InputStream paramInputStream)
      {
        return false;
      }

      public boolean visitSegment(int paramInt1, byte[] paramArrayOfByte1, int paramInt2, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
      {
        Debug.debug("Segment marker: " + Integer.toHexString(paramInt1) + " (" + JpegUtils.getMarkerName(paramInt1) + "), " + paramArrayOfByte3.length + " bytes of segment data.");
        return true;
      }
    });
  }

  public void traverseJFIF(ByteSource paramByteSource, Visitor paramVisitor)
    throws ImageReadException, IOException
  {
    InputStream localInputStream = null;
    try
    {
      localInputStream = paramByteSource.getInputStream();
      readAndVerifyBytes(localInputStream, SOI, "Not a Valid JPEG File: doesn't begin with 0xffd8");
      int i = getByteOrder();
      for (int j = 0; ; j++)
      {
        byte[] arrayOfByte1 = readByteArray("markerBytes", 2, localInputStream, "markerBytes");
        int k = convertByteArrayToShort("marker", arrayOfByte1, i);
        if ((k == 65497) || (k == 65498))
        {
          boolean bool1 = paramVisitor.beginSOS();
          if (!bool1)
            if ((localInputStream == null) || (1 == 0));
        }
        while (true)
        {
          try
          {
            localInputStream.close();
            return;
          }
          catch (Exception localException3)
          {
            Debug.debug(localException3);
            return;
          }
          boolean bool2 = paramVisitor.visitSOS(k, arrayOfByte1, localInputStream);
          if (bool2);
          for (int m = 0; (localInputStream != null) && (m != 0); m = 1)
            try
            {
              localInputStream.close();
              return;
            }
            catch (Exception localException2)
            {
              Debug.debug(localException2);
              return;
            }
          continue;
          byte[] arrayOfByte2 = readByteArray("segmentLengthBytes", 2, localInputStream, "segmentLengthBytes");
          int n = convertByteArrayToShort("segmentLength", arrayOfByte2, i);
          boolean bool3 = paramVisitor.visitSegment(k, arrayOfByte1, n, arrayOfByte2, readByteArray("Segment Data", n - 2, localInputStream, "Invalid Segment: insufficient data"));
          if (bool3)
            break;
          if ((localInputStream == null) || (1 == 0))
            continue;
          try
          {
            localInputStream.close();
            return;
          }
          catch (Exception localException4)
          {
            Debug.debug(localException4);
            return;
          }
        }
      }
    }
    finally
    {
      if ((localInputStream == null) || (1 == 0));
    }
    try
    {
      localInputStream.close();
      throw localObject;
    }
    catch (Exception localException1)
    {
      while (true)
        Debug.debug(localException1);
    }
  }

  public static abstract interface Visitor
  {
    public abstract boolean beginSOS();

    public abstract void visitSOS(int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);

    public abstract boolean visitSOS(int paramInt, byte[] paramArrayOfByte, InputStream paramInputStream);

    public abstract boolean visitSegment(int paramInt1, byte[] paramArrayOfByte1, int paramInt2, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
      throws ImageReadException, IOException;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.jpeg.JpegUtils
 * JD-Core Version:    0.6.0
 */