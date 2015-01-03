package com.tencent.mm.sdk.platformtools;

import com.tencent.mm.algorithm.MD5;
import java.io.File;
import java.io.IOException;

public class FilePathGenerator
{
  public static final String ANDROID_DIR_SEP = "/";
  public static final int HASH_TYPE_ALL_MD5 = 2;
  public static final int HASH_TYPE_HEAD_2_BYTE = 1;
  public static final String NO_MEDIA_FILENAME = ".nomedia";

  private static String b(String paramString)
  {
    if (Util.isNullOrNil(paramString));
    do
      return null;
    while (paramString.length() <= 4);
    return paramString.substring(0, 2) + "/" + paramString.substring(2, 4) + "/";
  }

  private static boolean c(String paramString)
  {
    try
    {
      File localFile1 = new File(paramString);
      File localFile2;
      if (!localFile1.exists())
      {
        localFile1.mkdirs();
        localFile2 = new File(paramString + ".nomedia");
        boolean bool = localFile2.exists();
        if (bool);
      }
      try
      {
        localFile2.createNewFile();
        return true;
      }
      catch (IOException localIOException)
      {
        while (true)
          localIOException.printStackTrace();
      }
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public static String defGenPathWithOld(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt)
  {
    String str1 = paramString1 + paramString3 + paramString4 + paramString5;
    String str2 = genPath(paramString2, paramString3, paramString4, paramString5, paramInt);
    if ((Util.isNullOrNil(str1)) || (Util.isNullOrNil(str2)))
      str2 = null;
    File localFile1;
    File localFile2;
    do
    {
      return str2;
      localFile1 = new File(str2);
      localFile2 = new File(str1);
    }
    while ((localFile1.exists()) || (!localFile2.exists()));
    FilesCopy.copy(str1, str2, false);
    return str2;
  }

  public static String genPath(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt)
  {
    if (Util.isNullOrNil(paramString1));
    label137: 
    while (true)
    {
      return null;
      if (!paramString1.endsWith("/"))
        continue;
      String str1 = "";
      if (paramInt == 1)
        str1 = b(paramString3);
      while (true)
      {
        if (Util.isNullOrNil(str1))
          break label137;
        String str2 = paramString1 + str1;
        if (!c(str2))
          break;
        return str2 + Util.nullAsNil(paramString2) + paramString3 + Util.nullAsNil(paramString4);
        if (paramInt != 2)
          continue;
        if (Util.isNullOrNil(paramString3))
        {
          str1 = null;
          continue;
        }
        str1 = b(MD5.getMessageDigest(paramString3.getBytes()));
      }
    }
  }

  public static enum DIR_HASH_TYPE
  {
    static
    {
      ALL_MD5 = new DIR_HASH_TYPE("ALL_MD5", 1);
      DIR_HASH_TYPE[] arrayOfDIR_HASH_TYPE = new DIR_HASH_TYPE[2];
      arrayOfDIR_HASH_TYPE[0] = HEAD_2_BYTE;
      arrayOfDIR_HASH_TYPE[1] = ALL_MD5;
      x = arrayOfDIR_HASH_TYPE;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.FilePathGenerator
 * JD-Core Version:    0.6.0
 */