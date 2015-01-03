package com.kaixin001.util;

import android.content.Context;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppDownloadManager
{
  private static final int POOL_SIZE = 5;
  private static final String TAG = "AppDownloadManager";
  public static Context mContext;
  private static final Executor thearpool = Executors.newScheduledThreadPool(5);

  public AppDownloadManager(Context paramContext)
  {
    mContext = paramContext;
  }

  // ERROR //
  public boolean downloadApk(String paramString)
  {
    // Byte code:
    //   0: new 39	com/kaixin001/network/HttpConnection
    //   3: dup
    //   4: getstatic 31	com/kaixin001/util/AppDownloadManager:mContext	Landroid/content/Context;
    //   7: invokespecial 41	com/kaixin001/network/HttpConnection:<init>	(Landroid/content/Context;)V
    //   10: astore_2
    //   11: new 43	java/lang/StringBuilder
    //   14: dup
    //   15: invokestatic 49	android/os/Environment:getExternalStorageDirectory	()Ljava/io/File;
    //   18: invokevirtual 55	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   21: invokestatic 61	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   24: invokespecial 64	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   27: ldc 66
    //   29: invokevirtual 70	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   32: aload_1
    //   33: iconst_1
    //   34: aload_1
    //   35: ldc 72
    //   37: invokevirtual 76	java/lang/String:lastIndexOf	(Ljava/lang/String;)I
    //   40: iadd
    //   41: invokevirtual 80	java/lang/String:substring	(I)Ljava/lang/String;
    //   44: invokevirtual 70	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   47: invokevirtual 83	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   50: astore_3
    //   51: aload_2
    //   52: aload_1
    //   53: invokevirtual 87	com/kaixin001/network/HttpConnection:getRemoteFileSize	(Ljava/lang/String;)J
    //   56: lstore 10
    //   58: new 51	java/io/File
    //   61: dup
    //   62: aload_3
    //   63: invokespecial 88	java/io/File:<init>	(Ljava/lang/String;)V
    //   66: astore 12
    //   68: aload 12
    //   70: invokevirtual 92	java/io/File:exists	()Z
    //   73: ifeq +18 -> 91
    //   76: aload 12
    //   78: invokevirtual 96	java/io/File:length	()J
    //   81: lstore 13
    //   83: lload 13
    //   85: lload 10
    //   87: lcmp
    //   88: ifle +40 -> 128
    //   91: getstatic 31	com/kaixin001/util/AppDownloadManager:mContext	Landroid/content/Context;
    //   94: ifnull +85 -> 179
    //   97: new 98	com/kaixin001/network/HttpProxy
    //   100: dup
    //   101: getstatic 31	com/kaixin001/util/AppDownloadManager:mContext	Landroid/content/Context;
    //   104: invokespecial 99	com/kaixin001/network/HttpProxy:<init>	(Landroid/content/Context;)V
    //   107: astore 5
    //   109: aload 5
    //   111: aload_1
    //   112: aload_3
    //   113: iconst_0
    //   114: aconst_null
    //   115: aconst_null
    //   116: invokevirtual 103	com/kaixin001/network/HttpProxy:httpDownload	(Ljava/lang/String;Ljava/lang/String;ZLcom/kaixin001/network/HttpRequestState;Lcom/kaixin001/network/HttpProgressListener;)Z
    //   119: istore 8
    //   121: iload 8
    //   123: istore 7
    //   125: iload 7
    //   127: ireturn
    //   128: lload 13
    //   130: lload 10
    //   132: lcmp
    //   133: ifge +6 -> 139
    //   136: goto -45 -> 91
    //   139: goto -48 -> 91
    //   142: astore 9
    //   144: aload 9
    //   146: invokevirtual 106	com/kaixin001/network/HttpException:printStackTrace	()V
    //   149: goto -58 -> 91
    //   152: astore 4
    //   154: aload 4
    //   156: invokevirtual 107	java/lang/Exception:printStackTrace	()V
    //   159: goto -68 -> 91
    //   162: astore 6
    //   164: ldc 11
    //   166: ldc 109
    //   168: aload 6
    //   170: invokestatic 115	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   173: iconst_0
    //   174: istore 7
    //   176: goto -51 -> 125
    //   179: ldc 11
    //   181: ldc 117
    //   183: invokestatic 120	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   186: iconst_0
    //   187: ireturn
    //
    // Exception table:
    //   from	to	target	type
    //   51	83	142	com/kaixin001/network/HttpException
    //   51	83	152	java/lang/Exception
    //   109	121	162	java/lang/Exception
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.AppDownloadManager
 * JD-Core Version:    0.6.0
 */