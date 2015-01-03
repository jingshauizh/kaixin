package com.kaixin001.util;

import com.kaixin001.model.User;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KXUBLog
{
  private static final String FILE_NAME = "/sdcard/kaixin001/kxub.kxlog";
  public static final String HOMEPAGE_ACT = "homepage_act";
  public static final String HOMEPAGE_ADDFRIEND = "homepage_addfriend";
  public static final String HOMEPAGE_APP = "homepage_app";
  public static final String HOMEPAGE_CANCEL = "homepage_cancel";
  public static final String HOMEPAGE_CHAT = "homepage_chat";
  public static final String HOMEPAGE_CIRCLE = "homepage_circle";
  public static final String HOMEPAGE_DIARY = "homepage_diary";
  public static final String HOMEPAGE_DYNAMIC = "homepage_dynamic";
  public static final String HOMEPAGE_FRIEND = "homepage_friend";
  public static final String HOMEPAGE_LOCAL = "homepage_local";
  public static final String HOMEPAGE_MESSAGE = "homepage_message";
  public static final String HOMEPAGE_MYHOME = "homepage_myhome";
  public static final String HOMEPAGE_OTHER = "homepage_other";
  public static final String HOMEPAGE_PIC = "homepage_pic";
  public static final String HOMEPAGE_RECORDS = "homepage_records";
  public static final String HOMEPAGE_REPRINT = "homepage_reprint";
  public static final String HOMEPAGE_SIGN = "homepage_sign";
  public static final String HOMEPAGE_VIEWPIC = "homepage_viewpic";
  public static final String OPEN_DROID = "open_Droid";
  public static final String QUIT_DROID = "quit_Droid";
  private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS\t");
  private static FileWriter writer;

  public static void clearLog()
  {
    File localFile = new File("/sdcard/kaixin001/kxub.kxlog");
    try
    {
      if (localFile.exists())
        localFile.delete();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  // ERROR //
  public static String getAllLog()
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: aconst_null
    //   4: astore_0
    //   5: aconst_null
    //   6: astore_1
    //   7: new 91	java/io/File
    //   10: dup
    //   11: ldc 8
    //   13: invokespecial 92	java/io/File:<init>	(Ljava/lang/String;)V
    //   16: astore_2
    //   17: aload_2
    //   18: invokevirtual 96	java/io/File:exists	()Z
    //   21: istore 7
    //   23: iload 7
    //   25: ifne +20 -> 45
    //   28: aconst_null
    //   29: invokestatic 110	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   32: aconst_null
    //   33: invokestatic 110	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   36: aconst_null
    //   37: astore 6
    //   39: ldc 2
    //   41: monitorexit
    //   42: aload 6
    //   44: areturn
    //   45: new 112	java/lang/StringBuffer
    //   48: dup
    //   49: invokespecial 113	java/lang/StringBuffer:<init>	()V
    //   52: astore 8
    //   54: new 115	java/io/FileInputStream
    //   57: dup
    //   58: aload_2
    //   59: invokespecial 118	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   62: astore 9
    //   64: new 120	java/io/InputStreamReader
    //   67: dup
    //   68: aload 9
    //   70: invokespecial 123	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   73: astore 10
    //   75: sipush 256
    //   78: newarray char
    //   80: astore 11
    //   82: aload 10
    //   84: aload 11
    //   86: iconst_0
    //   87: sipush 256
    //   90: invokevirtual 127	java/io/InputStreamReader:read	([CII)I
    //   93: istore 12
    //   95: iload 12
    //   97: iconst_m1
    //   98: if_icmpne +27 -> 125
    //   101: aload 8
    //   103: invokevirtual 130	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   106: astore 13
    //   108: aload 13
    //   110: astore 6
    //   112: aload 10
    //   114: invokestatic 110	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   117: aload 9
    //   119: invokestatic 110	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   122: goto -83 -> 39
    //   125: aload 8
    //   127: aload 11
    //   129: iconst_0
    //   130: iload 12
    //   132: invokevirtual 134	java/lang/StringBuffer:append	([CII)Ljava/lang/StringBuffer;
    //   135: pop
    //   136: goto -54 -> 82
    //   139: astore_3
    //   140: aload 10
    //   142: astore_1
    //   143: aload 9
    //   145: astore_0
    //   146: aload_3
    //   147: invokevirtual 102	java/lang/Exception:printStackTrace	()V
    //   150: aload_1
    //   151: invokestatic 110	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   154: aload_0
    //   155: invokestatic 110	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   158: aconst_null
    //   159: astore 6
    //   161: goto -122 -> 39
    //   164: astore 5
    //   166: ldc 2
    //   168: monitorexit
    //   169: aload 5
    //   171: athrow
    //   172: astore 4
    //   174: aload_1
    //   175: invokestatic 110	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   178: aload_0
    //   179: invokestatic 110	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   182: aload 4
    //   184: athrow
    //   185: astore 4
    //   187: aload 9
    //   189: astore_0
    //   190: aconst_null
    //   191: astore_1
    //   192: goto -18 -> 174
    //   195: astore 4
    //   197: aload 10
    //   199: astore_1
    //   200: aload 9
    //   202: astore_0
    //   203: goto -29 -> 174
    //   206: astore_3
    //   207: aconst_null
    //   208: astore_1
    //   209: aconst_null
    //   210: astore_0
    //   211: goto -65 -> 146
    //   214: astore_3
    //   215: aload 9
    //   217: astore_0
    //   218: aconst_null
    //   219: astore_1
    //   220: goto -74 -> 146
    //   223: astore 5
    //   225: goto -59 -> 166
    //
    // Exception table:
    //   from	to	target	type
    //   75	82	139	java/lang/Exception
    //   82	95	139	java/lang/Exception
    //   101	108	139	java/lang/Exception
    //   125	136	139	java/lang/Exception
    //   28	36	164	finally
    //   150	158	164	finally
    //   174	185	164	finally
    //   7	23	172	finally
    //   45	64	172	finally
    //   146	150	172	finally
    //   64	75	185	finally
    //   75	82	195	finally
    //   82	95	195	finally
    //   101	108	195	finally
    //   125	136	195	finally
    //   7	23	206	java/lang/Exception
    //   45	64	206	java/lang/Exception
    //   64	75	214	java/lang/Exception
    //   112	122	223	finally
  }

  public static void log(String paramString)
  {
    monitorenter;
    try
    {
      writer = new FileWriter(new File("/sdcard/kaixin001/kxub.kxlog"), true);
      writer.write(sdf.format(new Date()));
      writer.write(User.getInstance().getUID() + "\t" + paramString + "\n");
    }
    catch (Exception localException)
    {
    }
    finally
    {
      CloseUtil.close(writer);
    }
    throw localObject1;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.KXUBLog
 * JD-Core Version:    0.6.0
 */