package com.autonavi.aps.api;

import android.os.Environment;
import android.os.StatFs;
import java.io.File;

public class Storage
{
  private static Storage a;

  public static long availaleSize()
  {
    StatFs localStatFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
    return localStatFs.getBlockSize() * localStatFs.getAvailableBlocks() / 1024L / 1024L;
  }

  public static Storage getInstance()
  {
    if (a == null)
      a = new Storage();
    return a;
  }

  public static boolean sdcardAvaliable()
  {
    Utils.writeLogCat("sdcard in " + Environment.getExternalStorageState() + " status ");
    return Environment.getExternalStorageState().equalsIgnoreCase("mounted");
  }

  // ERROR //
  public void writeLog(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic 80	com/autonavi/aps/api/Storage:sdcardAvaliable	()Z
    //   5: ifeq +250 -> 255
    //   8: invokestatic 82	com/autonavi/aps/api/Storage:availaleSize	()J
    //   11: ldc2_w 83
    //   14: lcmp
    //   15: ifle +232 -> 247
    //   18: new 22	java/io/File
    //   21: dup
    //   22: new 47	java/lang/StringBuilder
    //   25: dup
    //   26: invokespecial 85	java/lang/StringBuilder:<init>	()V
    //   29: invokestatic 20	android/os/Environment:getExternalStorageDirectory	()Ljava/io/File;
    //   32: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   35: ldc 90
    //   37: ldc 92
    //   39: getstatic 96	java/io/File:separator	Ljava/lang/String;
    //   42: invokevirtual 100	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   45: invokevirtual 57	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   48: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   51: invokespecial 101	java/io/File:<init>	(Ljava/lang/String;)V
    //   54: astore_3
    //   55: aload_3
    //   56: invokevirtual 104	java/io/File:getParentFile	()Ljava/io/File;
    //   59: astore 4
    //   61: aload 4
    //   63: invokevirtual 107	java/io/File:exists	()Z
    //   66: ifne +42 -> 108
    //   69: aload 4
    //   71: invokevirtual 110	java/io/File:mkdirs	()Z
    //   74: ifne +31 -> 105
    //   77: new 47	java/lang/StringBuilder
    //   80: dup
    //   81: ldc 112
    //   83: invokespecial 50	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   86: aload 4
    //   88: invokevirtual 115	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   91: invokevirtual 57	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   94: ldc 117
    //   96: invokevirtual 57	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   99: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   102: invokestatic 67	com/autonavi/aps/api/Utils:writeLogCat	(Ljava/lang/String;)V
    //   105: aload_0
    //   106: monitorexit
    //   107: return
    //   108: aconst_null
    //   109: astore 5
    //   111: aload_3
    //   112: invokevirtual 107	java/io/File:exists	()Z
    //   115: ifne +8 -> 123
    //   118: aload_3
    //   119: invokevirtual 120	java/io/File:createNewFile	()Z
    //   122: pop
    //   123: new 122	java/io/FileOutputStream
    //   126: dup
    //   127: aload_3
    //   128: iconst_1
    //   129: invokespecial 125	java/io/FileOutputStream:<init>	(Ljava/io/File;Z)V
    //   132: astore 7
    //   134: aload 7
    //   136: new 47	java/lang/StringBuilder
    //   139: dup
    //   140: aload_1
    //   141: invokestatic 129	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   144: invokespecial 50	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   147: ldc 131
    //   149: invokevirtual 57	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   152: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   155: invokevirtual 135	java/lang/String:getBytes	()[B
    //   158: invokevirtual 139	java/io/FileOutputStream:write	([B)V
    //   161: ldc 141
    //   163: invokestatic 67	com/autonavi/aps/api/Utils:writeLogCat	(Ljava/lang/String;)V
    //   166: aload 7
    //   168: invokevirtual 144	java/io/FileOutputStream:close	()V
    //   171: goto -66 -> 105
    //   174: astore 11
    //   176: aload 11
    //   178: invokestatic 148	com/autonavi/aps/api/Utils:printException	(Ljava/lang/Exception;)V
    //   181: goto -76 -> 105
    //   184: astore_2
    //   185: aload_0
    //   186: monitorexit
    //   187: aload_2
    //   188: athrow
    //   189: astore 9
    //   191: aload 9
    //   193: invokestatic 148	com/autonavi/aps/api/Utils:printException	(Ljava/lang/Exception;)V
    //   196: aload 5
    //   198: ifnull -93 -> 105
    //   201: aload 5
    //   203: invokevirtual 144	java/io/FileOutputStream:close	()V
    //   206: goto -101 -> 105
    //   209: astore 10
    //   211: aload 10
    //   213: invokestatic 148	com/autonavi/aps/api/Utils:printException	(Ljava/lang/Exception;)V
    //   216: goto -111 -> 105
    //   219: astore 6
    //   221: aconst_null
    //   222: astore 7
    //   224: aload 7
    //   226: ifnull +8 -> 234
    //   229: aload 7
    //   231: invokevirtual 144	java/io/FileOutputStream:close	()V
    //   234: aload 6
    //   236: athrow
    //   237: astore 8
    //   239: aload 8
    //   241: invokestatic 148	com/autonavi/aps/api/Utils:printException	(Ljava/lang/Exception;)V
    //   244: goto -10 -> 234
    //   247: ldc 150
    //   249: invokestatic 67	com/autonavi/aps/api/Utils:writeLogCat	(Ljava/lang/String;)V
    //   252: goto -147 -> 105
    //   255: ldc 152
    //   257: invokestatic 67	com/autonavi/aps/api/Utils:writeLogCat	(Ljava/lang/String;)V
    //   260: goto -155 -> 105
    //   263: astore 6
    //   265: goto -41 -> 224
    //   268: astore 6
    //   270: aload 5
    //   272: astore 7
    //   274: goto -50 -> 224
    //   277: astore 9
    //   279: aload 7
    //   281: astore 5
    //   283: goto -92 -> 191
    //
    // Exception table:
    //   from	to	target	type
    //   166	171	174	java/lang/Exception
    //   2	105	184	finally
    //   166	171	184	finally
    //   176	181	184	finally
    //   201	206	184	finally
    //   211	216	184	finally
    //   229	234	184	finally
    //   234	237	184	finally
    //   239	244	184	finally
    //   247	252	184	finally
    //   255	260	184	finally
    //   111	123	189	java/lang/Exception
    //   123	134	189	java/lang/Exception
    //   201	206	209	java/lang/Exception
    //   111	123	219	finally
    //   123	134	219	finally
    //   229	234	237	java/lang/Exception
    //   134	166	263	finally
    //   191	196	268	finally
    //   134	166	277	java/lang/Exception
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.autonavi.aps.api.Storage
 * JD-Core Version:    0.6.0
 */