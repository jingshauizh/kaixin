package com.amap.mapapi.offlinemap;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class a
{
  RandomAccessFile a;
  long b;

  public a()
    throws IOException
  {
    this("", 0L);
  }

  public a(String paramString, long paramLong)
    throws IOException
  {
    File localFile = new File(paramString);
    if (!localFile.exists())
      if (!localFile.getParentFile().exists())
        localFile.getParentFile().mkdirs();
    try
    {
      if (!localFile.exists())
        localFile.createNewFile();
      this.a = new RandomAccessFile(paramString, "rw");
      this.b = paramLong;
      this.a.seek(paramLong);
      return;
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }

  public int a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    monitorenter;
    try
    {
      this.a.write(paramArrayOfByte, paramInt1, paramInt2);
      return paramInt2;
    }
    catch (IOException localIOException)
    {
      while (true)
      {
        localIOException.printStackTrace();
        paramInt2 = -1;
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.offlinemap.a
 * JD-Core Version:    0.6.0
 */