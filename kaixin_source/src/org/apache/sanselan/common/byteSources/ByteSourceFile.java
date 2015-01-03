package org.apache.sanselan.common.byteSources;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteSourceFile extends ByteSource
{
  private final File file;

  public ByteSourceFile(File paramFile)
  {
    super(paramFile.getName());
    this.file = paramFile;
  }

  // ERROR //
  public byte[] getAll()
    throws IOException
  {
    // Byte code:
    //   0: new 25	java/io/ByteArrayOutputStream
    //   3: dup
    //   4: invokespecial 28	java/io/ByteArrayOutputStream:<init>	()V
    //   7: astore_1
    //   8: aconst_null
    //   9: astore_2
    //   10: new 30	java/io/FileInputStream
    //   13: dup
    //   14: aload_0
    //   15: getfield 19	org/apache/sanselan/common/byteSources/ByteSourceFile:file	Ljava/io/File;
    //   18: invokespecial 32	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   21: astore_3
    //   22: new 34	java/io/BufferedInputStream
    //   25: dup
    //   26: aload_3
    //   27: invokespecial 37	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   30: astore_2
    //   31: sipush 1024
    //   34: newarray byte
    //   36: astore 6
    //   38: aload_2
    //   39: aload 6
    //   41: invokevirtual 43	java/io/InputStream:read	([B)I
    //   44: istore 7
    //   46: iload 7
    //   48: ifgt +20 -> 68
    //   51: aload_1
    //   52: invokevirtual 46	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   55: astore 8
    //   57: aload_2
    //   58: ifnull +7 -> 65
    //   61: aload_2
    //   62: invokevirtual 49	java/io/InputStream:close	()V
    //   65: aload 8
    //   67: areturn
    //   68: aload_1
    //   69: aload 6
    //   71: iconst_0
    //   72: iload 7
    //   74: invokevirtual 53	java/io/ByteArrayOutputStream:write	([BII)V
    //   77: goto -39 -> 38
    //   80: astore 4
    //   82: aload_2
    //   83: ifnull +7 -> 90
    //   86: aload_2
    //   87: invokevirtual 49	java/io/InputStream:close	()V
    //   90: aload 4
    //   92: athrow
    //   93: astore 9
    //   95: aload 8
    //   97: areturn
    //   98: astore 5
    //   100: goto -10 -> 90
    //   103: astore 4
    //   105: aload_3
    //   106: astore_2
    //   107: goto -25 -> 82
    //
    // Exception table:
    //   from	to	target	type
    //   10	22	80	finally
    //   31	38	80	finally
    //   38	46	80	finally
    //   51	57	80	finally
    //   68	77	80	finally
    //   61	65	93	java/io/IOException
    //   86	90	98	java/io/IOException
    //   22	31	103	finally
  }

  // ERROR //
  public byte[] getBlock(int paramInt1, int paramInt2)
    throws IOException
  {
    // Byte code:
    //   0: new 59	java/io/RandomAccessFile
    //   3: dup
    //   4: aload_0
    //   5: getfield 19	org/apache/sanselan/common/byteSources/ByteSourceFile:file	Ljava/io/File;
    //   8: ldc 61
    //   10: invokespecial 64	java/io/RandomAccessFile:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   13: astore_3
    //   14: iload_1
    //   15: i2l
    //   16: lstore 4
    //   18: aload_0
    //   19: aload_3
    //   20: lload 4
    //   22: iload_2
    //   23: ldc 66
    //   25: invokevirtual 70	org/apache/sanselan/common/byteSources/ByteSourceFile:getRAFBytes	(Ljava/io/RandomAccessFile;JILjava/lang/String;)[B
    //   28: astore 8
    //   30: aload_3
    //   31: invokevirtual 71	java/io/RandomAccessFile:close	()V
    //   34: aload 8
    //   36: areturn
    //   37: astore 9
    //   39: aload 9
    //   41: invokestatic 77	org/apache/sanselan/util/Debug:debug	(Ljava/lang/Throwable;)V
    //   44: aload 8
    //   46: areturn
    //   47: astore 6
    //   49: aconst_null
    //   50: astore_3
    //   51: aload_3
    //   52: invokevirtual 71	java/io/RandomAccessFile:close	()V
    //   55: aload 6
    //   57: athrow
    //   58: astore 7
    //   60: aload 7
    //   62: invokestatic 77	org/apache/sanselan/util/Debug:debug	(Ljava/lang/Throwable;)V
    //   65: goto -10 -> 55
    //   68: astore 6
    //   70: goto -19 -> 51
    //
    // Exception table:
    //   from	to	target	type
    //   30	34	37	java/lang/Exception
    //   0	14	47	finally
    //   51	55	58	java/lang/Exception
    //   18	30	68	finally
  }

  public String getDescription()
  {
    return "File: '" + this.file.getAbsolutePath() + "'";
  }

  public InputStream getInputStream()
    throws IOException
  {
    return new BufferedInputStream(new FileInputStream(this.file));
  }

  public long getLength()
  {
    return this.file.length();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.byteSources.ByteSourceFile
 * JD-Core Version:    0.6.0
 */