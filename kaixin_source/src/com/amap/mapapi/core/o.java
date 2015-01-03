package com.amap.mapapi.core;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class o
{
  public static byte[] a(Context paramContext, String paramString)
  {
    AssetManager localAssetManager = paramContext.getAssets();
    try
    {
      InputStream localInputStream = localAssetManager.open(paramString);
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      byte[] arrayOfByte1 = new byte[1024];
      while (true)
      {
        int i = localInputStream.read(arrayOfByte1);
        if (i <= -1)
          break;
        localByteArrayOutputStream.write(arrayOfByte1, 0, i);
      }
      byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
      localByteArrayOutputStream.close();
      localInputStream.close();
      return arrayOfByte2;
    }
    catch (IOException localIOException)
    {
    }
    return null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.core.o
 * JD-Core Version:    0.6.0
 */