package com.tencent.connect.a;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class a
{
  public static String a(InputStream paramInputStream)
  {
    Object localObject;
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
      String str2;
      for (localObject = ""; ; localObject = str2)
      {
        String str1 = localBufferedReader.readLine();
        if (str1 == null)
          break;
        str2 = (String)localObject + str1;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      localObject = null;
    }
    return (String)localObject;
  }

  public static boolean a(File paramFile1, File paramFile2)
  {
    try
    {
      boolean bool = b(new FileInputStream(paramFile1), paramFile2);
      return bool;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
    }
    return false;
  }

  public static boolean a(InputStream paramInputStream, File paramFile)
  {
    try
    {
      if (!paramFile.exists())
        paramFile.createNewFile();
      localFileOutputStream = new FileOutputStream(paramFile);
      byte[] arrayOfByte = new byte[1024];
      while (true)
      {
        int i = paramInputStream.read(arrayOfByte);
        if (i == -1)
          break;
        localFileOutputStream.write(arrayOfByte, 0, i);
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      FileOutputStream localFileOutputStream;
      localFileNotFoundException.printStackTrace();
      return false;
      localFileOutputStream.flush();
      paramInputStream.close();
      localFileOutputStream.close();
      return true;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return false;
  }

  public static boolean a(InputStream paramInputStream, String paramString1, String paramString2)
  {
    File localFile1 = new File(paramString1);
    if (!localFile1.exists())
      localFile1.mkdirs();
    File localFile2 = new File(paramString1 + File.separator + paramString2);
    if (localFile2.exists())
      localFile2.delete();
    FileOutputStream localFileOutputStream;
    try
    {
      localFileOutputStream = new FileOutputStream(localFile2);
      byte[] arrayOfByte = new byte[1024];
      while (true)
      {
        int i = paramInputStream.read(arrayOfByte);
        if (i <= 0)
          break;
        localFileOutputStream.write(arrayOfByte, 0, i);
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      return false;
    }
    localFileOutputStream.close();
    paramInputStream.close();
    return true;
  }

  public static boolean a(String paramString)
  {
    File localFile = new File(paramString);
    if (localFile.isDirectory())
    {
      File[] arrayOfFile = localFile.listFiles();
      int i = arrayOfFile.length;
      for (int j = 0; j < i; j++)
        if (!arrayOfFile[j].delete())
          return false;
    }
    return true;
  }

  public static boolean a(String paramString1, String paramString2, String paramString3)
  {
    try
    {
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(paramString1).openConnection();
      localHttpURLConnection.setConnectTimeout(5000);
      localHttpURLConnection.setRequestMethod("GET");
      localHttpURLConnection.setRequestProperty("Chaset", "UTF-8");
      boolean bool = a(localHttpURLConnection.getInputStream(), paramString2, paramString3);
      return bool;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localMalformedURLException.printStackTrace();
      return false;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return false;
  }

  public static boolean b(InputStream paramInputStream, File paramFile)
  {
    try
    {
      localZipInputStream = new ZipInputStream(paramInputStream);
      while (true)
      {
        ZipEntry localZipEntry = localZipInputStream.getNextEntry();
        if (localZipEntry == null)
          break;
        if (localZipEntry.isDirectory())
          continue;
        String str = localZipEntry.getName();
        File localFile = new File(paramFile.getPath() + File.separator + str);
        if (localFile.exists())
          localFile.delete();
        if (!localFile.getParentFile().exists())
          localFile.getParentFile().mkdirs();
        byte[] arrayOfByte = new byte[1024];
        FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
        for (int i = localZipInputStream.read(arrayOfByte); i > 0; i = localZipInputStream.read(arrayOfByte))
          localFileOutputStream.write(arrayOfByte, 0, i);
        localZipInputStream.closeEntry();
        localFileOutputStream.close();
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      ZipInputStream localZipInputStream;
      localFileNotFoundException.printStackTrace();
      return false;
      localZipInputStream.close();
      return true;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.connect.a.a
 * JD-Core Version:    0.6.0
 */