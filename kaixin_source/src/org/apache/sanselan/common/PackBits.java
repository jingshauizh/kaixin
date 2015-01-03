package org.apache.sanselan.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.sanselan.ImageReadException;

public class PackBits
{
  private int findNextDuplicate(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramInt >= paramArrayOfByte.length);
    while (true)
    {
      return -1;
      int i = paramArrayOfByte[paramInt];
      for (int j = paramInt + 1; j < paramArrayOfByte.length; j++)
      {
        int k = paramArrayOfByte[j];
        if (k == i)
          return j - 1;
        i = k;
      }
    }
  }

  private int findRunLength(byte[] paramArrayOfByte, int paramInt)
  {
    int i = paramArrayOfByte[paramInt];
    for (int j = paramInt + 1; ; j++)
      if ((j >= paramArrayOfByte.length) || (paramArrayOfByte[j] != i))
        return j - paramInt;
  }

  public byte[] compress(byte[] paramArrayOfByte)
    throws IOException
  {
    MyByteArrayOutputStream localMyByteArrayOutputStream = new MyByteArrayOutputStream(2 * paramArrayOfByte.length);
    int i = 0;
    int j = 0;
    while (true)
    {
      if (i >= paramArrayOfByte.length)
        return localMyByteArrayOutputStream.toByteArray();
      j++;
      int k = findNextDuplicate(paramArrayOfByte, i);
      if (k == i)
      {
        int i5 = Math.min(findRunLength(paramArrayOfByte, k), 128);
        localMyByteArrayOutputStream.write(-(i5 - 1));
        localMyByteArrayOutputStream.write(paramArrayOfByte[i]);
        i += i5;
        continue;
      }
      int m = k - i;
      if (k > 0)
      {
        int i2 = findRunLength(paramArrayOfByte, k);
        if (i2 < 3)
        {
          int i3 = i2 + (i + m);
          int i4 = findNextDuplicate(paramArrayOfByte, i3);
          if (i4 != i3)
          {
            k = i4;
            m = k - i;
          }
        }
      }
      if (k < 0)
        m = paramArrayOfByte.length - i;
      int n = Math.min(m, 128);
      localMyByteArrayOutputStream.write(n - 1);
      for (int i1 = 0; i1 < n; i1++)
      {
        localMyByteArrayOutputStream.write(paramArrayOfByte[i]);
        i++;
      }
    }
  }

  public byte[] decompress(byte[] paramArrayOfByte, int paramInt)
    throws ImageReadException, IOException
  {
    int i = 0;
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    int j = 0;
    while (true)
    {
      if (i >= paramInt)
        return localByteArrayOutputStream.toByteArray();
      if (j >= paramArrayOfByte.length)
        throw new ImageReadException("Tiff: Unpack bits source exhausted: " + j + ", done + " + i + ", expected + " + paramInt);
      int k = j + 1;
      int m = paramArrayOfByte[j];
      if ((m >= 0) && (m <= 127))
      {
        int i3 = m + 1;
        i += i3;
        int i4 = 0;
        int i5;
        for (j = k; i4 < i3; j = i5)
        {
          i5 = j + 1;
          localByteArrayOutputStream.write(paramArrayOfByte[j]);
          i4++;
        }
        continue;
      }
      if ((m >= -127) && (m <= -1))
      {
        j = k + 1;
        int n = paramArrayOfByte[k];
        int i1 = 1 + -m;
        i += i1;
        for (int i2 = 0; i2 < i1; i2++)
          localByteArrayOutputStream.write(n);
        continue;
      }
      if (m == -128)
        throw new ImageReadException("Packbits: " + m);
      j = k;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.PackBits
 * JD-Core Version:    0.6.0
 */