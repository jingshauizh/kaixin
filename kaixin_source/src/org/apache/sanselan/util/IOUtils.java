package org.apache.sanselan.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import org.apache.sanselan.SanselanConstants;

public class IOUtils
  implements SanselanConstants
{
  public static final boolean copyFileNio(File paramFile1, File paramFile2)
    throws IOException
  {
    FileChannel localFileChannel1 = null;
    FileChannel localFileChannel2 = null;
    try
    {
      localFileChannel1 = new FileInputStream(paramFile1).getChannel();
      localFileChannel2 = new FileOutputStream(paramFile2).getChannel();
      long l1 = localFileChannel1.size();
      long l2 = 0L;
      while (true)
      {
        if (l2 >= l1)
        {
          localFileChannel1.close();
          localFileChannel1 = null;
          localFileChannel2.close();
          if (0 == 0);
        }
        try
        {
          null.close();
          if (0 == 0);
        }
        catch (IOException localIOException4)
        {
          try
          {
            null.close();
            return true;
            long l3 = 16777216;
            long l4 = localFileChannel1.transferTo(l2, l3, localFileChannel2);
            l2 += l4;
            continue;
            localIOException4 = localIOException4;
            Debug.debug(localIOException4);
          }
          catch (IOException localIOException3)
          {
            while (true)
              Debug.debug(localIOException3);
          }
        }
      }
    }
    finally
    {
      if (localFileChannel1 == null);
    }
    try
    {
      localFileChannel1.close();
      if (localFileChannel2 == null);
    }
    catch (IOException localIOException2)
    {
      try
      {
        localFileChannel2.close();
        throw localObject;
        localIOException2 = localIOException2;
        Debug.debug(localIOException2);
      }
      catch (IOException localIOException1)
      {
        while (true)
          Debug.debug(localIOException1);
      }
    }
  }

  public static void copyStreamToStream(InputStream paramInputStream, OutputStream paramOutputStream)
    throws IOException
  {
    copyStreamToStream(paramInputStream, paramOutputStream, true);
  }

  // ERROR //
  public static void copyStreamToStream(InputStream paramInputStream, OutputStream paramOutputStream, boolean paramBoolean)
    throws IOException
  {
    // Byte code:
    //   0: new 54	java/io/BufferedInputStream
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 57	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   8: astore_3
    //   9: new 59	java/io/BufferedOutputStream
    //   12: dup
    //   13: aload_1
    //   14: invokespecial 62	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   17: astore 4
    //   19: sipush 4096
    //   22: newarray byte
    //   24: astore 10
    //   26: aload_3
    //   27: aload 10
    //   29: iconst_0
    //   30: aload 10
    //   32: arraylength
    //   33: invokevirtual 66	java/io/BufferedInputStream:read	([BII)I
    //   36: istore 11
    //   38: iload 11
    //   40: ifgt +31 -> 71
    //   43: aload 4
    //   45: invokevirtual 69	java/io/BufferedOutputStream:flush	()V
    //   48: iload_2
    //   49: ifeq +21 -> 70
    //   52: aload_3
    //   53: ifnull +7 -> 60
    //   56: aload_3
    //   57: invokevirtual 70	java/io/BufferedInputStream:close	()V
    //   60: aload 4
    //   62: ifnull +8 -> 70
    //   65: aload 4
    //   67: invokevirtual 71	java/io/BufferedOutputStream:close	()V
    //   70: return
    //   71: aload_1
    //   72: aload 10
    //   74: iconst_0
    //   75: iload 11
    //   77: invokevirtual 77	java/io/OutputStream:write	([BII)V
    //   80: goto -54 -> 26
    //   83: astore 5
    //   85: aload 4
    //   87: astore 6
    //   89: aload_3
    //   90: astore 7
    //   92: iload_2
    //   93: ifeq +23 -> 116
    //   96: aload 7
    //   98: ifnull +8 -> 106
    //   101: aload 7
    //   103: invokevirtual 70	java/io/BufferedInputStream:close	()V
    //   106: aload 6
    //   108: ifnull +8 -> 116
    //   111: aload 6
    //   113: invokevirtual 71	java/io/BufferedOutputStream:close	()V
    //   116: aload 5
    //   118: athrow
    //   119: astore 9
    //   121: aload 9
    //   123: invokestatic 47	org/apache/sanselan/util/Debug:debug	(Ljava/lang/Throwable;)V
    //   126: goto -20 -> 106
    //   129: astore 8
    //   131: aload 8
    //   133: invokestatic 47	org/apache/sanselan/util/Debug:debug	(Ljava/lang/Throwable;)V
    //   136: goto -20 -> 116
    //   139: astore 13
    //   141: aload 13
    //   143: invokestatic 47	org/apache/sanselan/util/Debug:debug	(Ljava/lang/Throwable;)V
    //   146: goto -86 -> 60
    //   149: astore 12
    //   151: aload 12
    //   153: invokestatic 47	org/apache/sanselan/util/Debug:debug	(Ljava/lang/Throwable;)V
    //   156: return
    //   157: astore 5
    //   159: aconst_null
    //   160: astore 7
    //   162: aconst_null
    //   163: astore 6
    //   165: goto -73 -> 92
    //   168: astore 5
    //   170: aload_3
    //   171: astore 7
    //   173: aconst_null
    //   174: astore 6
    //   176: goto -84 -> 92
    //
    // Exception table:
    //   from	to	target	type
    //   19	26	83	finally
    //   26	38	83	finally
    //   43	48	83	finally
    //   71	80	83	finally
    //   101	106	119	java/io/IOException
    //   111	116	129	java/io/IOException
    //   56	60	139	java/io/IOException
    //   65	70	149	java/io/IOException
    //   0	9	157	finally
    //   9	19	168	finally
  }

  // ERROR //
  public static byte[] getFileBytes(File paramFile)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: new 16	java/io/FileInputStream
    //   5: dup
    //   6: aload_0
    //   7: invokespecial 19	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   10: astore_2
    //   11: aload_2
    //   12: invokestatic 83	org/apache/sanselan/util/IOUtils:getInputStreamBytes	(Ljava/io/InputStream;)[B
    //   15: astore 5
    //   17: aload_2
    //   18: ifnull +7 -> 25
    //   21: aload_2
    //   22: invokevirtual 86	java/io/InputStream:close	()V
    //   25: aload 5
    //   27: areturn
    //   28: astore 6
    //   30: aload 6
    //   32: invokestatic 47	org/apache/sanselan/util/Debug:debug	(Ljava/lang/Throwable;)V
    //   35: aload 5
    //   37: areturn
    //   38: astore_3
    //   39: aload_1
    //   40: ifnull +7 -> 47
    //   43: aload_1
    //   44: invokevirtual 86	java/io/InputStream:close	()V
    //   47: aload_3
    //   48: athrow
    //   49: astore 4
    //   51: aload 4
    //   53: invokestatic 47	org/apache/sanselan/util/Debug:debug	(Ljava/lang/Throwable;)V
    //   56: goto -9 -> 47
    //   59: astore_3
    //   60: aload_2
    //   61: astore_1
    //   62: goto -23 -> 39
    //
    // Exception table:
    //   from	to	target	type
    //   21	25	28	java/io/IOException
    //   2	11	38	finally
    //   43	47	49	java/io/IOException
    //   11	17	59	finally
  }

  // ERROR //
  public static byte[] getInputStreamBytes(InputStream paramInputStream)
    throws IOException
  {
    // Byte code:
    //   0: new 88	java/io/ByteArrayOutputStream
    //   3: dup
    //   4: sipush 4096
    //   7: invokespecial 91	java/io/ByteArrayOutputStream:<init>	(I)V
    //   10: astore_1
    //   11: new 54	java/io/BufferedInputStream
    //   14: dup
    //   15: aload_0
    //   16: invokespecial 57	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   19: astore_2
    //   20: sipush 4096
    //   23: newarray byte
    //   25: astore 6
    //   27: aload_2
    //   28: aload 6
    //   30: iconst_0
    //   31: sipush 4096
    //   34: invokevirtual 92	java/io/InputStream:read	([BII)I
    //   37: istore 7
    //   39: iload 7
    //   41: ifgt +24 -> 65
    //   44: aload_1
    //   45: invokevirtual 93	java/io/ByteArrayOutputStream:flush	()V
    //   48: aload_1
    //   49: invokevirtual 97	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   52: astore 8
    //   54: aload_1
    //   55: ifnull +7 -> 62
    //   58: aload_1
    //   59: invokevirtual 98	java/io/ByteArrayOutputStream:close	()V
    //   62: aload 8
    //   64: areturn
    //   65: aload_1
    //   66: aload 6
    //   68: iconst_0
    //   69: iload 7
    //   71: invokevirtual 99	java/io/ByteArrayOutputStream:write	([BII)V
    //   74: goto -47 -> 27
    //   77: astore_3
    //   78: aload_1
    //   79: astore 4
    //   81: aload 4
    //   83: ifnull +8 -> 91
    //   86: aload 4
    //   88: invokevirtual 98	java/io/ByteArrayOutputStream:close	()V
    //   91: aload_3
    //   92: athrow
    //   93: astore 9
    //   95: aload 9
    //   97: invokestatic 47	org/apache/sanselan/util/Debug:debug	(Ljava/lang/Throwable;)V
    //   100: aload 8
    //   102: areturn
    //   103: astore 5
    //   105: aload 5
    //   107: invokestatic 47	org/apache/sanselan/util/Debug:debug	(Ljava/lang/Throwable;)V
    //   110: goto -19 -> 91
    //   113: astore_3
    //   114: aconst_null
    //   115: astore 4
    //   117: goto -36 -> 81
    //   120: astore_3
    //   121: aload_1
    //   122: astore 4
    //   124: goto -43 -> 81
    //
    // Exception table:
    //   from	to	target	type
    //   20	27	77	finally
    //   27	39	77	finally
    //   44	54	77	finally
    //   65	74	77	finally
    //   58	62	93	java/io/IOException
    //   86	91	103	java/io/IOException
    //   0	11	113	finally
    //   11	20	120	finally
  }

  // ERROR //
  public static void putInputStreamToFile(InputStream paramInputStream, File paramFile)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_1
    //   3: invokevirtual 109	java/io/File:getParentFile	()Ljava/io/File;
    //   6: ifnull +11 -> 17
    //   9: aload_1
    //   10: invokevirtual 109	java/io/File:getParentFile	()Ljava/io/File;
    //   13: invokevirtual 113	java/io/File:mkdirs	()Z
    //   16: pop
    //   17: new 25	java/io/FileOutputStream
    //   20: dup
    //   21: aload_1
    //   22: invokespecial 26	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   25: astore 5
    //   27: aload_0
    //   28: aload 5
    //   30: invokestatic 115	org/apache/sanselan/util/IOUtils:copyStreamToStream	(Ljava/io/InputStream;Ljava/io/OutputStream;)V
    //   33: aload 5
    //   35: ifnull +8 -> 43
    //   38: aload 5
    //   40: invokevirtual 116	java/io/FileOutputStream:close	()V
    //   43: return
    //   44: astore_3
    //   45: aload_2
    //   46: ifnull +7 -> 53
    //   49: aload_2
    //   50: invokevirtual 116	java/io/FileOutputStream:close	()V
    //   53: aload_3
    //   54: athrow
    //   55: astore 4
    //   57: aload 4
    //   59: invokestatic 47	org/apache/sanselan/util/Debug:debug	(Ljava/lang/Throwable;)V
    //   62: goto -9 -> 53
    //   65: astore 6
    //   67: aload 6
    //   69: invokestatic 47	org/apache/sanselan/util/Debug:debug	(Ljava/lang/Throwable;)V
    //   72: return
    //   73: astore_3
    //   74: aload 5
    //   76: astore_2
    //   77: goto -32 -> 45
    //
    // Exception table:
    //   from	to	target	type
    //   2	17	44	finally
    //   17	27	44	finally
    //   49	53	55	java/lang/Exception
    //   38	43	65	java/lang/Exception
    //   27	33	73	finally
  }

  // ERROR //
  public static void writeToFile(byte[] paramArrayOfByte, File paramFile)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 120	java/io/ByteArrayInputStream
    //   5: dup
    //   6: aload_0
    //   7: invokespecial 123	java/io/ByteArrayInputStream:<init>	([B)V
    //   10: astore_3
    //   11: aload_3
    //   12: aload_1
    //   13: invokestatic 125	org/apache/sanselan/util/IOUtils:putInputStreamToFile	(Ljava/io/InputStream;Ljava/io/File;)V
    //   16: aload_3
    //   17: ifnull +7 -> 24
    //   20: aload_3
    //   21: invokevirtual 126	java/io/ByteArrayInputStream:close	()V
    //   24: return
    //   25: astore 4
    //   27: aload_2
    //   28: ifnull +7 -> 35
    //   31: aload_2
    //   32: invokevirtual 126	java/io/ByteArrayInputStream:close	()V
    //   35: aload 4
    //   37: athrow
    //   38: astore 5
    //   40: aload 5
    //   42: invokestatic 47	org/apache/sanselan/util/Debug:debug	(Ljava/lang/Throwable;)V
    //   45: goto -10 -> 35
    //   48: astore 6
    //   50: aload 6
    //   52: invokestatic 47	org/apache/sanselan/util/Debug:debug	(Ljava/lang/Throwable;)V
    //   55: return
    //   56: astore 4
    //   58: aload_3
    //   59: astore_2
    //   60: goto -33 -> 27
    //
    // Exception table:
    //   from	to	target	type
    //   2	11	25	finally
    //   31	35	38	java/lang/Exception
    //   20	24	48	java/lang/Exception
    //   11	16	56	finally
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.util.IOUtils
 * JD-Core Version:    0.6.0
 */