package org.apache.sanselan;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.common.byteSources.ByteSource;
import org.apache.sanselan.common.byteSources.ByteSourceArray;
import org.apache.sanselan.common.byteSources.ByteSourceFile;
import org.apache.sanselan.common.byteSources.ByteSourceInputStream;
import org.apache.sanselan.util.Debug;

public abstract class Sanselan
  implements SanselanConstants
{
  private static final ImageParser getImageParser(ByteSource paramByteSource)
    throws ImageReadException, IOException
  {
    ImageFormat localImageFormat = guessFormat(paramByteSource);
    ImageParser[] arrayOfImageParser2;
    int j;
    String str;
    ImageParser[] arrayOfImageParser1;
    if (!localImageFormat.equals(ImageFormat.IMAGE_FORMAT_UNKNOWN))
    {
      arrayOfImageParser2 = ImageParser.getAllImageParsers();
      j = 0;
      if (j < arrayOfImageParser2.length);
    }
    else
    {
      str = paramByteSource.getFilename();
      if (str != null)
        arrayOfImageParser1 = ImageParser.getAllImageParsers();
    }
    for (int i = 0; ; i++)
    {
      ImageParser localImageParser;
      if (i >= arrayOfImageParser1.length)
      {
        throw new ImageReadException("Can't parse this format.");
        localImageParser = arrayOfImageParser2[j];
        if (!localImageParser.canAcceptType(localImageFormat));
      }
      do
      {
        return localImageParser;
        j++;
        break;
        localImageParser = arrayOfImageParser1[i];
      }
      while (localImageParser.canAcceptExtension(str));
    }
  }

  public static IImageMetadata getMetadata(File paramFile)
    throws ImageReadException, IOException
  {
    return getMetadata(paramFile, null);
  }

  public static IImageMetadata getMetadata(File paramFile, Map paramMap)
    throws ImageReadException, IOException
  {
    return getMetadata(new ByteSourceFile(paramFile), paramMap);
  }

  public static IImageMetadata getMetadata(InputStream paramInputStream, String paramString)
    throws ImageReadException, IOException
  {
    return getMetadata(paramInputStream, paramString, null);
  }

  public static IImageMetadata getMetadata(InputStream paramInputStream, String paramString, Map paramMap)
    throws ImageReadException, IOException
  {
    return getMetadata(new ByteSourceInputStream(paramInputStream, paramString), paramMap);
  }

  private static IImageMetadata getMetadata(ByteSource paramByteSource, Map paramMap)
    throws ImageReadException, IOException
  {
    return getImageParser(paramByteSource).getMetadata(paramByteSource, paramMap);
  }

  public static IImageMetadata getMetadata(byte[] paramArrayOfByte)
    throws ImageReadException, IOException
  {
    return getMetadata(paramArrayOfByte, null);
  }

  public static IImageMetadata getMetadata(byte[] paramArrayOfByte, Map paramMap)
    throws ImageReadException, IOException
  {
    return getMetadata(new ByteSourceArray(paramArrayOfByte), paramMap);
  }

  public static ImageFormat guessFormat(ByteSource paramByteSource)
    throws ImageReadException, IOException
  {
    InputStream localInputStream = null;
    int i;
    int j;
    try
    {
      localInputStream = paramByteSource.getInputStream();
      i = localInputStream.read();
      j = localInputStream.read();
      if ((i < 0) || (j < 0))
        throw new ImageReadException("Couldn't read magic numbers to guess format.");
    }
    finally
    {
      if (localInputStream == null);
    }
    try
    {
      localInputStream.close();
      throw localObject;
      int k = i & 0xFF;
      int m = j & 0xFF;
      ImageFormat localImageFormat;
      if ((k == 71) && (m == 73))
      {
        localImageFormat = ImageFormat.IMAGE_FORMAT_GIF;
        if (localInputStream == null);
      }
      do
      {
        while (true)
        {
          try
          {
            localInputStream.close();
            return localImageFormat;
          }
          catch (IOException localIOException16)
          {
            Debug.debug(localIOException16);
            return localImageFormat;
          }
          if ((k == 137) && (m == 80))
          {
            localImageFormat = ImageFormat.IMAGE_FORMAT_PNG;
            if (localInputStream == null)
              continue;
            try
            {
              localInputStream.close();
              return localImageFormat;
            }
            catch (IOException localIOException15)
            {
              Debug.debug(localIOException15);
              return localImageFormat;
            }
          }
          if ((k == 255) && (m == 216))
          {
            localImageFormat = ImageFormat.IMAGE_FORMAT_JPEG;
            if (localInputStream == null)
              continue;
            try
            {
              localInputStream.close();
              return localImageFormat;
            }
            catch (IOException localIOException14)
            {
              Debug.debug(localIOException14);
              return localImageFormat;
            }
          }
          if ((k == 66) && (m == 77))
          {
            localImageFormat = ImageFormat.IMAGE_FORMAT_BMP;
            if (localInputStream == null)
              continue;
            try
            {
              localInputStream.close();
              return localImageFormat;
            }
            catch (IOException localIOException13)
            {
              Debug.debug(localIOException13);
              return localImageFormat;
            }
          }
          if ((k == 77) && (m == 77))
          {
            localImageFormat = ImageFormat.IMAGE_FORMAT_TIFF;
            if (localInputStream == null)
              continue;
            try
            {
              localInputStream.close();
              return localImageFormat;
            }
            catch (IOException localIOException12)
            {
              Debug.debug(localIOException12);
              return localImageFormat;
            }
          }
          if ((k == 73) && (m == 73))
          {
            localImageFormat = ImageFormat.IMAGE_FORMAT_TIFF;
            if (localInputStream == null)
              continue;
            try
            {
              localInputStream.close();
              return localImageFormat;
            }
            catch (IOException localIOException11)
            {
              Debug.debug(localIOException11);
              return localImageFormat;
            }
          }
          if ((k == 56) && (m == 66))
          {
            localImageFormat = ImageFormat.IMAGE_FORMAT_PSD;
            if (localInputStream == null)
              continue;
            try
            {
              localInputStream.close();
              return localImageFormat;
            }
            catch (IOException localIOException10)
            {
              Debug.debug(localIOException10);
              return localImageFormat;
            }
          }
          if ((k == 80) && (m == 49))
          {
            localImageFormat = ImageFormat.IMAGE_FORMAT_PBM;
            if (localInputStream == null)
              continue;
            try
            {
              localInputStream.close();
              return localImageFormat;
            }
            catch (IOException localIOException9)
            {
              Debug.debug(localIOException9);
              return localImageFormat;
            }
          }
          if ((k == 80) && (m == 52))
          {
            localImageFormat = ImageFormat.IMAGE_FORMAT_PBM;
            if (localInputStream == null)
              continue;
            try
            {
              localInputStream.close();
              return localImageFormat;
            }
            catch (IOException localIOException8)
            {
              Debug.debug(localIOException8);
              return localImageFormat;
            }
          }
          if ((k == 80) && (m == 50))
          {
            localImageFormat = ImageFormat.IMAGE_FORMAT_PGM;
            if (localInputStream == null)
              continue;
            try
            {
              localInputStream.close();
              return localImageFormat;
            }
            catch (IOException localIOException7)
            {
              Debug.debug(localIOException7);
              return localImageFormat;
            }
          }
          if ((k == 80) && (m == 53))
          {
            localImageFormat = ImageFormat.IMAGE_FORMAT_PGM;
            if (localInputStream == null)
              continue;
            try
            {
              localInputStream.close();
              return localImageFormat;
            }
            catch (IOException localIOException6)
            {
              Debug.debug(localIOException6);
              return localImageFormat;
            }
          }
          if ((k == 80) && (m == 51))
          {
            localImageFormat = ImageFormat.IMAGE_FORMAT_PPM;
            if (localInputStream == null)
              continue;
            try
            {
              localInputStream.close();
              return localImageFormat;
            }
            catch (IOException localIOException5)
            {
              Debug.debug(localIOException5);
              return localImageFormat;
            }
          }
          if ((k == 80) && (m == 54))
          {
            localImageFormat = ImageFormat.IMAGE_FORMAT_PPM;
            if (localInputStream == null)
              continue;
            try
            {
              localInputStream.close();
              return localImageFormat;
            }
            catch (IOException localIOException4)
            {
              Debug.debug(localIOException4);
              return localImageFormat;
            }
          }
          if ((k != 151) || (m != 74))
            break;
          int n = localInputStream.read();
          int i1 = localInputStream.read();
          if ((n < 0) || (i1 < 0))
            throw new ImageReadException("Couldn't read magic numbers to guess format.");
          int i2 = n & 0xFF;
          int i3 = i1 & 0xFF;
          if ((i2 != 66) || (i3 != 50))
            break;
          localImageFormat = ImageFormat.IMAGE_FORMAT_JBIG2;
          if (localInputStream == null)
            continue;
          try
          {
            localInputStream.close();
            return localImageFormat;
          }
          catch (IOException localIOException3)
          {
            Debug.debug(localIOException3);
            return localImageFormat;
          }
        }
        localImageFormat = ImageFormat.IMAGE_FORMAT_UNKNOWN;
      }
      while (localInputStream == null);
      try
      {
        localInputStream.close();
        return localImageFormat;
      }
      catch (IOException localIOException2)
      {
        Debug.debug(localIOException2);
        return localImageFormat;
      }
    }
    catch (IOException localIOException1)
    {
      while (true)
        Debug.debug(localIOException1);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.Sanselan
 * JD-Core Version:    0.6.0
 */