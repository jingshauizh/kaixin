package com.tencent.mm.algorithm;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public final class MD5
{
  public static String getMD5(File paramFile)
  {
    return getMD5(paramFile, 102400);
  }

  // ERROR //
  public static String getMD5(File paramFile, int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnull +14 -> 15
    //   4: iload_1
    //   5: ifle +10 -> 15
    //   8: aload_0
    //   9: invokevirtual 24	java/io/File:exists	()Z
    //   12: ifne +5 -> 17
    //   15: aconst_null
    //   16: areturn
    //   17: new 26	java/io/FileInputStream
    //   20: dup
    //   21: aload_0
    //   22: invokespecial 29	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   25: astore_2
    //   26: iload_1
    //   27: i2l
    //   28: lstore_3
    //   29: lload_3
    //   30: aload_0
    //   31: invokevirtual 33	java/io/File:length	()J
    //   34: lcmp
    //   35: ifgt +32 -> 67
    //   38: iload_1
    //   39: i2l
    //   40: lstore 10
    //   42: aload_2
    //   43: lload 10
    //   45: l2i
    //   46: invokestatic 36	com/tencent/mm/algorithm/MD5:getMD5	(Ljava/io/FileInputStream;I)Ljava/lang/String;
    //   49: astore 12
    //   51: aload_2
    //   52: invokevirtual 39	java/io/FileInputStream:close	()V
    //   55: aload_2
    //   56: invokevirtual 39	java/io/FileInputStream:close	()V
    //   59: aload 12
    //   61: areturn
    //   62: astore 13
    //   64: aload 12
    //   66: areturn
    //   67: aload_0
    //   68: invokevirtual 33	java/io/File:length	()J
    //   71: lstore 14
    //   73: lload 14
    //   75: lstore 10
    //   77: goto -35 -> 42
    //   80: astore 16
    //   82: aconst_null
    //   83: astore 6
    //   85: aload 6
    //   87: ifnull +8 -> 95
    //   90: aload 6
    //   92: invokevirtual 39	java/io/FileInputStream:close	()V
    //   95: aconst_null
    //   96: areturn
    //   97: astore 8
    //   99: aconst_null
    //   100: astore_2
    //   101: aload_2
    //   102: ifnull +7 -> 109
    //   105: aload_2
    //   106: invokevirtual 39	java/io/FileInputStream:close	()V
    //   109: aload 8
    //   111: athrow
    //   112: astore 7
    //   114: goto -19 -> 95
    //   117: astore 9
    //   119: goto -10 -> 109
    //   122: astore 8
    //   124: goto -23 -> 101
    //   127: astore 5
    //   129: aload_2
    //   130: astore 6
    //   132: goto -47 -> 85
    //
    // Exception table:
    //   from	to	target	type
    //   55	59	62	java/io/IOException
    //   17	26	80	java/lang/Exception
    //   17	26	97	finally
    //   90	95	112	java/io/IOException
    //   105	109	117	java/io/IOException
    //   29	38	122	finally
    //   42	55	122	finally
    //   67	73	122	finally
    //   29	38	127	java/lang/Exception
    //   42	55	127	java/lang/Exception
    //   67	73	127	java/lang/Exception
  }

  // ERROR //
  public static String getMD5(File paramFile, int paramInt1, int paramInt2)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnull +18 -> 19
    //   4: aload_0
    //   5: invokevirtual 24	java/io/File:exists	()Z
    //   8: ifeq +11 -> 19
    //   11: iload_1
    //   12: iflt +7 -> 19
    //   15: iload_2
    //   16: ifgt +5 -> 21
    //   19: aconst_null
    //   20: areturn
    //   21: new 26	java/io/FileInputStream
    //   24: dup
    //   25: aload_0
    //   26: invokespecial 29	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   29: astore_3
    //   30: aload_3
    //   31: ldc 11
    //   33: iload_1
    //   34: iload_2
    //   35: invokestatic 43	com/tencent/mm/algorithm/MD5:getMD5	(Ljava/io/FileInputStream;III)Ljava/lang/String;
    //   38: astore 9
    //   40: aload_3
    //   41: invokevirtual 39	java/io/FileInputStream:close	()V
    //   44: aload_3
    //   45: invokevirtual 39	java/io/FileInputStream:close	()V
    //   48: aload 9
    //   50: areturn
    //   51: astore 10
    //   53: aload 9
    //   55: areturn
    //   56: astore 11
    //   58: aconst_null
    //   59: astore 5
    //   61: aload 5
    //   63: ifnull +8 -> 71
    //   66: aload 5
    //   68: invokevirtual 39	java/io/FileInputStream:close	()V
    //   71: aconst_null
    //   72: areturn
    //   73: astore 7
    //   75: aconst_null
    //   76: astore_3
    //   77: aload_3
    //   78: ifnull +7 -> 85
    //   81: aload_3
    //   82: invokevirtual 39	java/io/FileInputStream:close	()V
    //   85: aload 7
    //   87: athrow
    //   88: astore 6
    //   90: goto -19 -> 71
    //   93: astore 8
    //   95: goto -10 -> 85
    //   98: astore 7
    //   100: goto -23 -> 77
    //   103: astore 4
    //   105: aload_3
    //   106: astore 5
    //   108: goto -47 -> 61
    //
    // Exception table:
    //   from	to	target	type
    //   44	48	51	java/io/IOException
    //   21	30	56	java/lang/Exception
    //   21	30	73	finally
    //   66	71	88	java/io/IOException
    //   81	85	93	java/io/IOException
    //   30	44	98	finally
    //   30	44	103	java/lang/Exception
  }

  public static final String getMD5(FileInputStream paramFileInputStream, int paramInt)
  {
    int i = 0;
    if ((paramFileInputStream == null) || (paramInt <= 0))
      return null;
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      StringBuilder localStringBuilder = new StringBuilder(32);
      byte[] arrayOfByte1 = new byte[paramInt];
      while (true)
      {
        int j = paramFileInputStream.read(arrayOfByte1);
        if (j == -1)
          break;
        localMessageDigest.update(arrayOfByte1, 0, j);
      }
      byte[] arrayOfByte2 = localMessageDigest.digest();
      while (i < arrayOfByte2.length)
      {
        localStringBuilder.append(Integer.toString(256 + (0xFF & arrayOfByte2[i]), 16).substring(1));
        i++;
      }
      String str = localStringBuilder.toString();
      return str;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static final String getMD5(FileInputStream paramFileInputStream, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    if ((paramFileInputStream == null) || (paramInt1 <= 0) || (paramInt2 < 0) || (paramInt3 <= 0));
    while (true)
    {
      return null;
      long l = paramInt2;
      try
      {
        if (paramFileInputStream.skip(l) < paramInt2)
          continue;
        MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
        StringBuilder localStringBuilder = new StringBuilder(32);
        byte[] arrayOfByte1 = new byte[paramInt1];
        int j = 0;
        while (true)
        {
          int k = paramFileInputStream.read(arrayOfByte1);
          if ((k == -1) || (j >= paramInt3))
            break;
          if (j + k <= paramInt3)
          {
            localMessageDigest.update(arrayOfByte1, 0, k);
            j += k;
            continue;
          }
          localMessageDigest.update(arrayOfByte1, 0, paramInt3 - j);
          j = paramInt3;
        }
        byte[] arrayOfByte2 = localMessageDigest.digest();
        while (i < arrayOfByte2.length)
        {
          localStringBuilder.append(Integer.toString(256 + (0xFF & arrayOfByte2[i]), 16).substring(1));
          i++;
        }
        String str = localStringBuilder.toString();
        return str;
      }
      catch (Exception localException)
      {
      }
    }
    return null;
  }

  public static String getMD5(String paramString)
  {
    if (paramString == null);
    File localFile;
    do
    {
      return null;
      localFile = new File(paramString);
    }
    while (!localFile.exists());
    return getMD5(localFile, 102400);
  }

  public static String getMD5(String paramString, int paramInt1, int paramInt2)
  {
    if (paramString == null);
    File localFile;
    do
    {
      return null;
      localFile = new File(paramString);
    }
    while (!localFile.exists());
    return getMD5(localFile, paramInt1, paramInt2);
  }

  public static final String getMessageDigest(byte[] paramArrayOfByte)
  {
    int i = 0;
    char[] arrayOfChar1 = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramArrayOfByte);
      byte[] arrayOfByte = localMessageDigest.digest();
      int j = arrayOfByte.length;
      char[] arrayOfChar2 = new char[j * 2];
      int k = 0;
      while (i < j)
      {
        int m = arrayOfByte[i];
        int n = k + 1;
        arrayOfChar2[k] = arrayOfChar1[(0xF & m >>> 4)];
        k = n + 1;
        arrayOfChar2[n] = arrayOfChar1[(m & 0xF)];
        i++;
      }
      String str = new String(arrayOfChar2);
      return str;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static final byte[] getRawDigest(byte[] paramArrayOfByte)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramArrayOfByte);
      byte[] arrayOfByte = localMessageDigest.digest();
      return arrayOfByte;
    }
    catch (Exception localException)
    {
    }
    return null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.algorithm.MD5
 * JD-Core Version:    0.6.0
 */