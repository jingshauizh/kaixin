package com.amap.mapapi.map;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class i
{
  public static int a = 2048;

  public static byte[] a(InputStream paramInputStream)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    byte[] arrayOfByte = new byte[a];
    while (true)
    {
      int i = paramInputStream.read(arrayOfByte, 0, a);
      if (i == -1)
        break;
      localByteArrayOutputStream.write(arrayOfByte, 0, i);
    }
    return localByteArrayOutputStream.toByteArray();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.i
 * JD-Core Version:    0.6.0
 */