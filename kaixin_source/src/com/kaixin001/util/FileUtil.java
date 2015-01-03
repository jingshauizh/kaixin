package com.kaixin001.util;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;
import com.kaixin001.item.CloudAlbumPicItem;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Date;

public class FileUtil
{
  private static final String TAG = "FileUtil";

  private static boolean checkDataDirs(String paramString1, String paramString2)
  {
    String str = paramString1 + "/data";
    File localFile1 = new File(str);
    if ((!localFile1.exists()) && (!localFile1.mkdirs()))
    {
      KXLog.d("checkDataDirs", "!dataCache.mkdir()");
      return false;
    }
    File[] arrayOfFile = localFile1.listFiles(new FileFilter()
    {
      public boolean accept(File paramFile)
      {
        return paramFile.isDirectory();
      }
    });
    if (arrayOfFile.length == 10)
      delOldestFile(arrayOfFile);
    File localFile2 = new File(str + "/" + paramString2);
    if ((!localFile2.exists()) && (!localFile2.mkdirs()))
    {
      KXLog.d("checkDataDirs", "!userCache.mkdir()");
      return false;
    }
    return true;
  }

  // ERROR //
  public static boolean copyFile(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 79	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: ifne +24 -> 28
    //   7: aload_1
    //   8: invokestatic 79	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   11: ifne +17 -> 28
    //   14: aload_2
    //   15: invokestatic 79	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   18: ifne +10 -> 28
    //   21: aload_3
    //   22: invokestatic 79	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   25: ifeq +5 -> 30
    //   28: iconst_0
    //   29: ireturn
    //   30: aconst_null
    //   31: astore 4
    //   33: aconst_null
    //   34: astore 5
    //   36: aload_0
    //   37: aload_1
    //   38: invokestatic 81	com/kaixin001/util/FileUtil:checkDataDirs	(Ljava/lang/String;Ljava/lang/String;)Z
    //   41: istore 8
    //   43: iload 8
    //   45: ifne +13 -> 58
    //   48: aconst_null
    //   49: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   52: aconst_null
    //   53: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   56: iconst_0
    //   57: ireturn
    //   58: new 37	java/io/File
    //   61: dup
    //   62: new 16	java/lang/StringBuilder
    //   65: dup
    //   66: aload_0
    //   67: invokestatic 22	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   70: invokespecial 25	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   73: ldc 89
    //   75: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   78: aload_1
    //   79: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   82: ldc 67
    //   84: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: aload_2
    //   88: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   91: invokevirtual 35	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   94: invokespecial 38	java/io/File:<init>	(Ljava/lang/String;)V
    //   97: astore 9
    //   99: aload 9
    //   101: invokestatic 93	com/kaixin001/util/FileUtil:makeEmptyFile	(Ljava/io/File;)Z
    //   104: istore 10
    //   106: iload 10
    //   108: ifne +13 -> 121
    //   111: aconst_null
    //   112: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   115: aconst_null
    //   116: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   119: iconst_0
    //   120: ireturn
    //   121: new 95	java/io/FileOutputStream
    //   124: dup
    //   125: aload 9
    //   127: invokespecial 98	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   130: astore 11
    //   132: new 100	java/io/FileInputStream
    //   135: dup
    //   136: aload_3
    //   137: invokespecial 101	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   140: astore 12
    //   142: sipush 4096
    //   145: newarray byte
    //   147: astore 13
    //   149: aload 12
    //   151: aload 13
    //   153: invokevirtual 105	java/io/FileInputStream:read	([B)I
    //   156: istore 14
    //   158: iload 14
    //   160: iconst_m1
    //   161: if_icmpne +20 -> 181
    //   164: aload 11
    //   166: invokevirtual 108	java/io/FileOutputStream:flush	()V
    //   169: aload 11
    //   171: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   174: aload 12
    //   176: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   179: iconst_1
    //   180: ireturn
    //   181: aload 11
    //   183: aload 13
    //   185: iconst_0
    //   186: iload 14
    //   188: invokevirtual 112	java/io/FileOutputStream:write	([BII)V
    //   191: goto -42 -> 149
    //   194: astore 6
    //   196: aload 11
    //   198: astore 5
    //   200: aload 12
    //   202: astore 4
    //   204: ldc 8
    //   206: ldc 114
    //   208: aload 6
    //   210: invokestatic 118	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   213: aload 5
    //   215: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   218: aload 4
    //   220: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   223: iconst_0
    //   224: ireturn
    //   225: astore 7
    //   227: aload 5
    //   229: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   232: aload 4
    //   234: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   237: aload 7
    //   239: athrow
    //   240: astore 7
    //   242: aload 11
    //   244: astore 5
    //   246: aconst_null
    //   247: astore 4
    //   249: goto -22 -> 227
    //   252: astore 7
    //   254: aload 11
    //   256: astore 5
    //   258: aload 12
    //   260: astore 4
    //   262: goto -35 -> 227
    //   265: astore 6
    //   267: aconst_null
    //   268: astore 4
    //   270: aconst_null
    //   271: astore 5
    //   273: goto -69 -> 204
    //   276: astore 6
    //   278: aload 11
    //   280: astore 5
    //   282: aconst_null
    //   283: astore 4
    //   285: goto -81 -> 204
    //
    // Exception table:
    //   from	to	target	type
    //   142	149	194	java/lang/Exception
    //   149	158	194	java/lang/Exception
    //   164	169	194	java/lang/Exception
    //   181	191	194	java/lang/Exception
    //   36	43	225	finally
    //   58	106	225	finally
    //   121	132	225	finally
    //   204	213	225	finally
    //   132	142	240	finally
    //   142	149	252	finally
    //   149	158	252	finally
    //   164	169	252	finally
    //   181	191	252	finally
    //   36	43	265	java/lang/Exception
    //   58	106	265	java/lang/Exception
    //   121	132	265	java/lang/Exception
    //   132	142	276	java/lang/Exception
  }

  public static void delOldestFile(File[] paramArrayOfFile)
  {
    int i = paramArrayOfFile.length;
    long l1 = 0L;
    int j = 0;
    for (int k = 0; ; k++)
    {
      if (k >= i)
      {
        if (!deleteDirectory(paramArrayOfFile[j]))
          KXLog.d("delOldestFile", "deleteDirectory failed");
        return;
      }
      long l2 = paramArrayOfFile[k].lastModified();
      if (k == 0)
        l1 = l2;
      if (l1 <= l2)
        continue;
      l1 = l2;
      j = k;
    }
  }

  public static boolean deleteCacheFile(String paramString)
  {
    File localFile = new File(paramString);
    if (localFile.exists())
      return localFile.delete();
    return true;
  }

  public static boolean deleteCacheFile(String paramString1, String paramString2)
  {
    File localFile = new File(paramString1, paramString2);
    if (localFile.exists())
      return localFile.delete();
    return true;
  }

  public static boolean deleteCacheFile(String paramString1, String paramString2, String paramString3)
  {
    File localFile = new File(paramString1 + "/data/" + paramString2 + "/" + paramString3);
    if (localFile.exists())
      return localFile.delete();
    return true;
  }

  public static boolean deleteDirectory(File paramFile)
  {
    if (paramFile == null)
      return false;
    if (paramFile.isFile())
      return paramFile.delete();
    File[] arrayOfFile = paramFile.listFiles();
    if (arrayOfFile != null);
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfFile.length)
        return paramFile.delete();
      deleteDirectory(arrayOfFile[i]);
    }
  }

  public static void deleteFileWithoutCheckReturnValue(File paramFile)
  {
    deleteDirectory(paramFile);
  }

  public static void deleteFileWithoutCheckReturnValue(String paramString)
  {
    deleteDirectory(new File(paramString));
  }

  public static boolean ensureEmptyFile(File paramFile)
  {
    File localFile = paramFile.getParentFile();
    if ((localFile != null) && (localFile.exists()))
    {
      if (paramFile.exists())
        return paramFile.delete();
    }
    else if ((localFile != null) && (!localFile.mkdirs()))
      return false;
    return true;
  }

  public static boolean existCacheFile(String paramString)
  {
    return new File(paramString).exists();
  }

  public static boolean existCacheFile(String paramString1, String paramString2)
  {
    return new File(paramString1, paramString2).exists();
  }

  // ERROR //
  public static Bitmap getBmpFromFile(Context paramContext, String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 79	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: ifeq +5 -> 9
    //   7: aconst_null
    //   8: areturn
    //   9: new 37	java/io/File
    //   12: dup
    //   13: aload_1
    //   14: invokespecial 38	java/io/File:<init>	(Ljava/lang/String;)V
    //   17: astore_2
    //   18: aload_2
    //   19: invokevirtual 42	java/io/File:exists	()Z
    //   22: ifne +5 -> 27
    //   25: aconst_null
    //   26: areturn
    //   27: aconst_null
    //   28: astore_3
    //   29: new 155	android/graphics/BitmapFactory$Options
    //   32: dup
    //   33: invokespecial 156	android/graphics/BitmapFactory$Options:<init>	()V
    //   36: astore 4
    //   38: aload_2
    //   39: invokevirtual 159	java/io/File:length	()J
    //   42: lstore 8
    //   44: aload_0
    //   45: aload_1
    //   46: invokestatic 163	com/kaixin001/util/FileUtil:isScreenShot	(Landroid/content/Context;Ljava/lang/String;)Z
    //   49: ifeq +37 -> 86
    //   52: aload 4
    //   54: iconst_1
    //   55: putfield 167	android/graphics/BitmapFactory$Options:inSampleSize	I
    //   58: new 100	java/io/FileInputStream
    //   61: dup
    //   62: aload_2
    //   63: invokespecial 168	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   66: astore 12
    //   68: aload 12
    //   70: aconst_null
    //   71: aload 4
    //   73: invokestatic 174	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   76: astore 13
    //   78: aload 12
    //   80: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   83: aload 13
    //   85: areturn
    //   86: lload 8
    //   88: ldc 175
    //   90: i2l
    //   91: lcmp
    //   92: ifgt +25 -> 117
    //   95: aload 4
    //   97: iconst_1
    //   98: putfield 167	android/graphics/BitmapFactory$Options:inSampleSize	I
    //   101: goto -43 -> 58
    //   104: astore 7
    //   106: aload 7
    //   108: invokevirtual 178	java/lang/Exception:printStackTrace	()V
    //   111: aload_3
    //   112: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   115: aconst_null
    //   116: areturn
    //   117: lload 8
    //   119: ldc2_w 179
    //   122: ldc 175
    //   124: i2l
    //   125: lmul
    //   126: lcmp
    //   127: ifgt +25 -> 152
    //   130: aload 4
    //   132: iconst_2
    //   133: putfield 167	android/graphics/BitmapFactory$Options:inSampleSize	I
    //   136: goto -78 -> 58
    //   139: astore 6
    //   141: aload 6
    //   143: invokevirtual 181	java/lang/OutOfMemoryError:printStackTrace	()V
    //   146: aload_3
    //   147: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   150: aconst_null
    //   151: areturn
    //   152: ldc 175
    //   154: i2l
    //   155: lstore 10
    //   157: aload 4
    //   159: iconst_1
    //   160: lload 8
    //   162: lload 10
    //   164: ldiv
    //   165: l2d
    //   166: invokestatic 187	java/lang/Math:log	(D)D
    //   169: ldc2_w 188
    //   172: invokestatic 187	java/lang/Math:log	(D)D
    //   175: ddiv
    //   176: d2i
    //   177: iadd
    //   178: putfield 167	android/graphics/BitmapFactory$Options:inSampleSize	I
    //   181: goto -123 -> 58
    //   184: astore 5
    //   186: aload_3
    //   187: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   190: aload 5
    //   192: athrow
    //   193: astore 5
    //   195: aload 12
    //   197: astore_3
    //   198: goto -12 -> 186
    //   201: astore 6
    //   203: aload 12
    //   205: astore_3
    //   206: goto -65 -> 141
    //   209: astore 7
    //   211: aload 12
    //   213: astore_3
    //   214: goto -108 -> 106
    //
    // Exception table:
    //   from	to	target	type
    //   29	58	104	java/lang/Exception
    //   58	68	104	java/lang/Exception
    //   95	101	104	java/lang/Exception
    //   130	136	104	java/lang/Exception
    //   157	181	104	java/lang/Exception
    //   29	58	139	java/lang/OutOfMemoryError
    //   58	68	139	java/lang/OutOfMemoryError
    //   95	101	139	java/lang/OutOfMemoryError
    //   130	136	139	java/lang/OutOfMemoryError
    //   157	181	139	java/lang/OutOfMemoryError
    //   29	58	184	finally
    //   58	68	184	finally
    //   95	101	184	finally
    //   106	111	184	finally
    //   130	136	184	finally
    //   141	146	184	finally
    //   157	181	184	finally
    //   68	78	193	finally
    //   68	78	201	java/lang/OutOfMemoryError
    //   68	78	209	java/lang/Exception
  }

  // ERROR //
  public static String getCacheData(String paramString1, String paramString2, String paramString3)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 79	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: ifne +17 -> 21
    //   7: aload_1
    //   8: invokestatic 79	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   11: ifne +10 -> 21
    //   14: aload_2
    //   15: invokestatic 79	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   18: ifeq +5 -> 23
    //   21: aconst_null
    //   22: areturn
    //   23: aconst_null
    //   24: astore_3
    //   25: aconst_null
    //   26: astore 4
    //   28: new 37	java/io/File
    //   31: dup
    //   32: new 16	java/lang/StringBuilder
    //   35: dup
    //   36: aload_0
    //   37: invokestatic 22	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   40: invokespecial 25	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   43: ldc 89
    //   45: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   48: aload_1
    //   49: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: ldc 67
    //   54: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   57: aload_2
    //   58: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   61: invokevirtual 35	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   64: invokespecial 38	java/io/File:<init>	(Ljava/lang/String;)V
    //   67: astore 5
    //   69: aload 5
    //   71: invokevirtual 42	java/io/File:exists	()Z
    //   74: istore 10
    //   76: iload 10
    //   78: ifne +31 -> 109
    //   81: iconst_0
    //   82: ifeq +7 -> 89
    //   85: aconst_null
    //   86: invokevirtual 193	java/io/FileInputStream:close	()V
    //   89: iconst_0
    //   90: ifeq +7 -> 97
    //   93: aconst_null
    //   94: invokevirtual 196	java/io/InputStreamReader:close	()V
    //   97: aconst_null
    //   98: areturn
    //   99: astore 19
    //   101: aload 19
    //   103: invokevirtual 178	java/lang/Exception:printStackTrace	()V
    //   106: goto -9 -> 97
    //   109: new 198	java/lang/StringBuffer
    //   112: dup
    //   113: invokespecial 199	java/lang/StringBuffer:<init>	()V
    //   116: astore 11
    //   118: new 100	java/io/FileInputStream
    //   121: dup
    //   122: aload 5
    //   124: invokespecial 168	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   127: astore 12
    //   129: new 195	java/io/InputStreamReader
    //   132: dup
    //   133: aload 12
    //   135: invokespecial 202	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   138: astore 13
    //   140: sipush 256
    //   143: newarray char
    //   145: astore 14
    //   147: aload 13
    //   149: aload 14
    //   151: iconst_0
    //   152: sipush 256
    //   155: invokevirtual 205	java/io/InputStreamReader:read	([CII)I
    //   158: istore 15
    //   160: iload 15
    //   162: iconst_m1
    //   163: if_icmpne +33 -> 196
    //   166: aload 11
    //   168: invokevirtual 206	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   171: astore 16
    //   173: aload 12
    //   175: ifnull +8 -> 183
    //   178: aload 12
    //   180: invokevirtual 193	java/io/FileInputStream:close	()V
    //   183: aload 13
    //   185: ifnull +112 -> 297
    //   188: aload 13
    //   190: invokevirtual 196	java/io/InputStreamReader:close	()V
    //   193: aload 16
    //   195: areturn
    //   196: aload 11
    //   198: aload 14
    //   200: iconst_0
    //   201: iload 15
    //   203: invokevirtual 209	java/lang/StringBuffer:append	([CII)Ljava/lang/StringBuffer;
    //   206: pop
    //   207: goto -60 -> 147
    //   210: astore 6
    //   212: aload 13
    //   214: astore 4
    //   216: aload 12
    //   218: astore_3
    //   219: ldc 8
    //   221: ldc 210
    //   223: aload 6
    //   225: invokestatic 118	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   228: aload_3
    //   229: ifnull +7 -> 236
    //   232: aload_3
    //   233: invokevirtual 193	java/io/FileInputStream:close	()V
    //   236: aload 4
    //   238: ifnull -217 -> 21
    //   241: aload 4
    //   243: invokevirtual 196	java/io/InputStreamReader:close	()V
    //   246: aconst_null
    //   247: areturn
    //   248: astore 9
    //   250: aload 9
    //   252: invokevirtual 178	java/lang/Exception:printStackTrace	()V
    //   255: aconst_null
    //   256: areturn
    //   257: astore 7
    //   259: aload_3
    //   260: ifnull +7 -> 267
    //   263: aload_3
    //   264: invokevirtual 193	java/io/FileInputStream:close	()V
    //   267: aload 4
    //   269: ifnull +8 -> 277
    //   272: aload 4
    //   274: invokevirtual 196	java/io/InputStreamReader:close	()V
    //   277: aload 7
    //   279: athrow
    //   280: astore 8
    //   282: aload 8
    //   284: invokevirtual 178	java/lang/Exception:printStackTrace	()V
    //   287: goto -10 -> 277
    //   290: astore 17
    //   292: aload 17
    //   294: invokevirtual 178	java/lang/Exception:printStackTrace	()V
    //   297: aload 16
    //   299: areturn
    //   300: astore 7
    //   302: aload 12
    //   304: astore_3
    //   305: aconst_null
    //   306: astore 4
    //   308: goto -49 -> 259
    //   311: astore 7
    //   313: aload 13
    //   315: astore 4
    //   317: aload 12
    //   319: astore_3
    //   320: goto -61 -> 259
    //   323: astore 6
    //   325: aconst_null
    //   326: astore 4
    //   328: aconst_null
    //   329: astore_3
    //   330: goto -111 -> 219
    //   333: astore 6
    //   335: aload 12
    //   337: astore_3
    //   338: aconst_null
    //   339: astore 4
    //   341: goto -122 -> 219
    //
    // Exception table:
    //   from	to	target	type
    //   85	89	99	java/lang/Exception
    //   93	97	99	java/lang/Exception
    //   140	147	210	java/lang/Exception
    //   147	160	210	java/lang/Exception
    //   166	173	210	java/lang/Exception
    //   196	207	210	java/lang/Exception
    //   232	236	248	java/lang/Exception
    //   241	246	248	java/lang/Exception
    //   28	76	257	finally
    //   109	129	257	finally
    //   219	228	257	finally
    //   263	267	280	java/lang/Exception
    //   272	277	280	java/lang/Exception
    //   178	183	290	java/lang/Exception
    //   188	193	290	java/lang/Exception
    //   129	140	300	finally
    //   140	147	311	finally
    //   147	160	311	finally
    //   166	173	311	finally
    //   196	207	311	finally
    //   28	76	323	java/lang/Exception
    //   109	129	323	java/lang/Exception
    //   129	140	333	java/lang/Exception
  }

  public static String getCacheDir(String paramString1, String paramString2)
  {
    return paramString1 + "/data/" + paramString2;
  }

  public static String getDynamicFileName(String paramString1, String paramString2)
  {
    String str1 = getCacheDir(paramString1, User.getInstance().getUID());
    long l = new Date().getTime();
    int i = paramString2.indexOf(".jpg", -5 + paramString2.length());
    String str2;
    if (i != -1)
      str2 = paramString2.substring(0, i) + Long.toString(l);
    int k;
    for (int j = 0; ; j = k)
    {
      if (!existCacheFile(str1, str2 + ".jpg"))
      {
        return str2 + ".jpg";
        str2 = paramString2 + Long.toString(l);
        break;
      }
      StringBuilder localStringBuilder = new StringBuilder(String.valueOf(str2));
      k = j + 1;
      str2 = Integer.toString(j);
    }
  }

  public static String getDynamicFilePath(String paramString)
  {
    int i = 0;
    KXLog.d("sOriFilePath", "sOriFilePath=" + paramString);
    long l = new Date().getTime();
    int j = paramString.indexOf(".jpg");
    String str;
    if (j != -1)
      str = paramString.substring(0, j) + Long.toString(l);
    while (true)
    {
      if (!existCacheFile(str + ".jpg"))
      {
        return str + ".jpg";
        str = paramString + Long.toString(l);
        i = 0;
        continue;
      }
      StringBuilder localStringBuilder = new StringBuilder(String.valueOf(str));
      int k = i + 1;
      str = Integer.toString(i);
      i = k;
    }
  }

  public static String getKXCacheDir(Context paramContext)
  {
    if (paramContext == null)
      return null;
    if (Environment.getExternalStorageState().equals("mounted"))
      return Environment.getExternalStorageDirectory().getAbsolutePath() + "/kaixin001/cache";
    return paramContext.getCacheDir().getAbsolutePath();
  }

  public static int getUploadPicMaxWid(Context paramContext)
  {
    int i = (int)Math.sqrt((((ActivityManager)paramContext.getSystemService("activity")).getMemoryClass() << 20) / 4 / 4);
    if (i >= 1000)
      i = 1000;
    do
      return i;
    while (i >= 500);
    return 500;
  }

  public static boolean isPathExist(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      File localFile = new File(paramString);
      if ((localFile != null) && (localFile.exists()))
        return true;
    }
    return false;
  }

  public static boolean isPicExist(Context paramContext, String paramString)
  {
    Cursor localCursor = null;
    while (true)
    {
      try
      {
        String[] arrayOfString1 = { "_data", "date_added", "_id", "mini_thumb_magic", "_size" };
        String[] arrayOfString2 = { paramString };
        localCursor = paramContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOfString1, "_id = ?", arrayOfString2, "date_added");
        if (localCursor.moveToFirst())
        {
          String str = localCursor.getString(0);
          if (!TextUtils.isEmpty(str))
          {
            File localFile = new File(str);
            if ((localFile != null) && (localFile.exists()))
            {
              long l = localFile.length();
              if (l > 100L)
                return true;
            }
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return false;
      }
      finally
      {
        if (localCursor == null)
          continue;
        localCursor.close();
      }
      if (localCursor == null)
        continue;
      localCursor.close();
    }
  }

  public static boolean isScreenShot(Context paramContext, String paramString)
  {
    WindowManager localWindowManager = (WindowManager)paramContext.getSystemService("window");
    int i = localWindowManager.getDefaultDisplay().getWidth();
    int j = localWindowManager.getDefaultDisplay().getHeight();
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    try
    {
      localOptions.inJustDecodeBounds = true;
      Bitmap localBitmap = BitmapFactory.decodeFile(paramString, localOptions);
      if ((localBitmap != null) && (!localBitmap.isRecycled()))
      {
        localBitmap.recycle();
        System.gc();
      }
      int k = localOptions.outWidth;
      int m = localOptions.outHeight;
      int n = 0;
      if (k == i)
      {
        n = 0;
        if (m == j)
          n = 1;
      }
      return n;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  // ERROR //
  public static Bitmap loadBitmapFromFile(String paramString, int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 79	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: ifeq +5 -> 9
    //   7: aconst_null
    //   8: areturn
    //   9: new 37	java/io/File
    //   12: dup
    //   13: aload_0
    //   14: invokespecial 38	java/io/File:<init>	(Ljava/lang/String;)V
    //   17: invokevirtual 42	java/io/File:exists	()Z
    //   20: ifeq -13 -> 7
    //   23: new 155	android/graphics/BitmapFactory$Options
    //   26: dup
    //   27: invokespecial 156	android/graphics/BitmapFactory$Options:<init>	()V
    //   30: astore_2
    //   31: aload_2
    //   32: iconst_1
    //   33: putfield 368	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
    //   36: aload_0
    //   37: aload_2
    //   38: invokestatic 372	android/graphics/BitmapFactory:decodeFile	(Ljava/lang/String;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   41: astore 5
    //   43: aload 5
    //   45: ifnull +19 -> 64
    //   48: aload 5
    //   50: invokevirtual 377	android/graphics/Bitmap:isRecycled	()Z
    //   53: ifne +11 -> 64
    //   56: aload 5
    //   58: invokevirtual 380	android/graphics/Bitmap:recycle	()V
    //   61: invokestatic 385	java/lang/System:gc	()V
    //   64: aload_2
    //   65: getfield 391	android/graphics/BitmapFactory$Options:outHeight	I
    //   68: iload_1
    //   69: if_icmpge +29 -> 98
    //   72: aload_2
    //   73: getfield 388	android/graphics/BitmapFactory$Options:outWidth	I
    //   76: istore 8
    //   78: iload 8
    //   80: iload_1
    //   81: if_icmpge +17 -> 98
    //   84: aconst_null
    //   85: astore 6
    //   87: aload_0
    //   88: aload 6
    //   90: invokestatic 372	android/graphics/BitmapFactory:decodeFile	(Ljava/lang/String;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   93: astore 7
    //   95: aload 7
    //   97: areturn
    //   98: aload_2
    //   99: iconst_0
    //   100: putfield 368	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
    //   103: aload_2
    //   104: aload_2
    //   105: getfield 388	android/graphics/BitmapFactory$Options:outWidth	I
    //   108: aload_2
    //   109: getfield 391	android/graphics/BitmapFactory$Options:outHeight	I
    //   112: invokestatic 397	java/lang/Math:max	(II)I
    //   115: iload_1
    //   116: idiv
    //   117: putfield 167	android/graphics/BitmapFactory$Options:inSampleSize	I
    //   120: aload_2
    //   121: astore 6
    //   123: goto -36 -> 87
    //   126: astore_3
    //   127: ldc 8
    //   129: ldc_w 398
    //   132: aload_3
    //   133: invokestatic 118	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   136: aconst_null
    //   137: areturn
    //   138: astore 4
    //   140: ldc 8
    //   142: ldc_w 398
    //   145: aload 4
    //   147: invokestatic 118	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   150: aconst_null
    //   151: areturn
    //   152: astore 4
    //   154: goto -14 -> 140
    //   157: astore_3
    //   158: goto -31 -> 127
    //
    // Exception table:
    //   from	to	target	type
    //   23	31	126	java/lang/OutOfMemoryError
    //   87	95	126	java/lang/OutOfMemoryError
    //   23	31	138	java/lang/Exception
    //   87	95	138	java/lang/Exception
    //   31	43	152	java/lang/Exception
    //   48	64	152	java/lang/Exception
    //   64	78	152	java/lang/Exception
    //   98	120	152	java/lang/Exception
    //   31	43	157	java/lang/OutOfMemoryError
    //   48	64	157	java/lang/OutOfMemoryError
    //   64	78	157	java/lang/OutOfMemoryError
    //   98	120	157	java/lang/OutOfMemoryError
  }

  // ERROR //
  public static Bitmap loadBitmapFromFile(String paramString, int paramInt1, int paramInt2)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 79	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: ifeq +5 -> 9
    //   7: aconst_null
    //   8: areturn
    //   9: new 37	java/io/File
    //   12: dup
    //   13: aload_0
    //   14: invokespecial 38	java/io/File:<init>	(Ljava/lang/String;)V
    //   17: invokevirtual 42	java/io/File:exists	()Z
    //   20: ifeq -13 -> 7
    //   23: aconst_null
    //   24: astore_3
    //   25: new 155	android/graphics/BitmapFactory$Options
    //   28: dup
    //   29: invokespecial 156	android/graphics/BitmapFactory$Options:<init>	()V
    //   32: astore 4
    //   34: aload 4
    //   36: iconst_1
    //   37: putfield 368	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
    //   40: aload_0
    //   41: aload 4
    //   43: invokestatic 372	android/graphics/BitmapFactory:decodeFile	(Ljava/lang/String;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   46: astore 10
    //   48: aload 10
    //   50: ifnull +19 -> 69
    //   53: aload 10
    //   55: invokevirtual 377	android/graphics/Bitmap:isRecycled	()Z
    //   58: ifne +11 -> 69
    //   61: aload 10
    //   63: invokevirtual 380	android/graphics/Bitmap:recycle	()V
    //   66: invokestatic 385	java/lang/System:gc	()V
    //   69: aload 4
    //   71: getfield 391	android/graphics/BitmapFactory$Options:outHeight	I
    //   74: iload_1
    //   75: if_icmpge +28 -> 103
    //   78: aload 4
    //   80: getfield 388	android/graphics/BitmapFactory$Options:outWidth	I
    //   83: istore 14
    //   85: iload 14
    //   87: iload_2
    //   88: if_icmpge +15 -> 103
    //   91: aconst_null
    //   92: astore_3
    //   93: aload_0
    //   94: aload_3
    //   95: invokestatic 372	android/graphics/BitmapFactory:decodeFile	(Ljava/lang/String;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   98: astore 13
    //   100: aload 13
    //   102: areturn
    //   103: aload 4
    //   105: iconst_0
    //   106: putfield 368	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
    //   109: aload 4
    //   111: getfield 388	android/graphics/BitmapFactory$Options:outWidth	I
    //   114: iload_1
    //   115: idiv
    //   116: aload 4
    //   118: getfield 391	android/graphics/BitmapFactory$Options:outHeight	I
    //   121: iload_2
    //   122: idiv
    //   123: invokestatic 397	java/lang/Math:max	(II)I
    //   126: istore 11
    //   128: iconst_1
    //   129: istore 12
    //   131: goto +92 -> 223
    //   134: aload 4
    //   136: iload 12
    //   138: putfield 167	android/graphics/BitmapFactory$Options:inSampleSize	I
    //   141: aload 4
    //   143: astore_3
    //   144: goto -51 -> 93
    //   147: iload 12
    //   149: iconst_2
    //   150: imul
    //   151: istore 12
    //   153: iload 11
    //   155: iload 12
    //   157: if_icmpgt +66 -> 223
    //   160: goto -26 -> 134
    //   163: astore 5
    //   165: ldc 8
    //   167: ldc_w 398
    //   170: aload 5
    //   172: invokestatic 118	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   175: invokestatic 385	java/lang/System:gc	()V
    //   178: aload_0
    //   179: aload_3
    //   180: invokestatic 372	android/graphics/BitmapFactory:decodeFile	(Ljava/lang/String;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   183: astore 8
    //   185: aload 8
    //   187: areturn
    //   188: astore 9
    //   190: ldc 8
    //   192: ldc_w 398
    //   195: aload 9
    //   197: invokestatic 118	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   200: aconst_null
    //   201: areturn
    //   202: astore 7
    //   204: aconst_null
    //   205: areturn
    //   206: astore 6
    //   208: aconst_null
    //   209: areturn
    //   210: astore 9
    //   212: goto -22 -> 190
    //   215: astore 5
    //   217: aload 4
    //   219: astore_3
    //   220: goto -55 -> 165
    //   223: iload 11
    //   225: iload 12
    //   227: if_icmpgt -80 -> 147
    //   230: goto -96 -> 134
    //
    // Exception table:
    //   from	to	target	type
    //   25	34	163	java/lang/OutOfMemoryError
    //   93	100	163	java/lang/OutOfMemoryError
    //   25	34	188	java/lang/Exception
    //   93	100	188	java/lang/Exception
    //   178	185	202	java/lang/Exception
    //   178	185	206	java/lang/Error
    //   34	48	210	java/lang/Exception
    //   53	69	210	java/lang/Exception
    //   69	85	210	java/lang/Exception
    //   103	128	210	java/lang/Exception
    //   134	141	210	java/lang/Exception
    //   34	48	215	java/lang/OutOfMemoryError
    //   53	69	215	java/lang/OutOfMemoryError
    //   69	85	215	java/lang/OutOfMemoryError
    //   103	128	215	java/lang/OutOfMemoryError
    //   134	141	215	java/lang/OutOfMemoryError
  }

  public static String loadLatestPic(Context paramContext)
  {
    Cursor localCursor = null;
    while (true)
    {
      try
      {
        String[] arrayOfString = { "_data", "date_added", "_id" };
        localCursor = paramContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOfString, null, null, "date_added");
        if (localCursor.moveToLast())
        {
          String str = localCursor.getString(0);
          return str;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      finally
      {
        if (localCursor == null)
          continue;
        localCursor.close();
      }
      if (localCursor == null)
        continue;
      localCursor.close();
    }
  }

  public static ArrayList<CloudAlbumPicItem> loadLocalPictures(Context paramContext, String paramString, int paramInt, boolean paramBoolean)
  {
    KXLog.d("CloudAlbumManager", " + loadLocalPictures(" + paramString + ")");
    ArrayList localArrayList = new ArrayList();
    Cursor localCursor = null;
    while (true)
    {
      boolean bool2;
      try
      {
        String[] arrayOfString1 = { "_data", "date_added", "_id" };
        boolean bool1 = TextUtils.isEmpty(paramString);
        String str1 = null;
        String[] arrayOfString2 = null;
        if (bool1)
          continue;
        if (!paramBoolean)
          continue;
        str1 = "date_added < ?";
        arrayOfString2 = new String[] { paramString };
        localCursor = paramContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOfString1, str1, arrayOfString2, "date_added");
        KXLog.d("CloudAlbumManager", "cursor.getCount():" + localCursor.getCount());
        bool2 = localCursor.moveToLast();
        break label428;
        KXLog.d("CloudAlbumManager", "load availabe pic num:" + localArrayList.size());
        return localArrayList;
        str1 = "date_added > ?";
        continue;
        String str2 = localCursor.getString(0);
        long l = Long.parseLong(localCursor.getString(1));
        String str3 = localCursor.getString(2);
        if (skipPicture(str2))
          continue;
        CloudAlbumPicItem localCloudAlbumPicItem = new CloudAlbumPicItem();
        localCloudAlbumPicItem.mUrl = str2;
        localCloudAlbumPicItem.mMD5 = null;
        localCloudAlbumPicItem.mThumbUrl = str3;
        localCloudAlbumPicItem.mLastModfiedTime = l;
        localArrayList.add(localCloudAlbumPicItem);
        KXLog.d("CloudAlbumManager", "Load IMG:" + localCloudAlbumPicItem.mUrl + ", " + KXTextUtil.formatTimestamp(1000L * localCloudAlbumPicItem.mLastModfiedTime) + ", " + localCloudAlbumPicItem.mLastModfiedTime);
        if ((paramInt > 0) && (localArrayList.size() >= paramInt))
          continue;
        boolean bool3 = localCursor.moveToPrevious();
        bool2 = bool3;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return localArrayList;
      }
      finally
      {
        if (localCursor == null)
          continue;
        localCursor.close();
      }
      label428: if (bool2)
        continue;
    }
  }

  public static boolean makeDirExist(File paramFile)
  {
    if (paramFile.exists());
    do
      return true;
    while (paramFile.mkdirs());
    return false;
  }

  public static boolean makeDirExist(String paramString)
  {
    return makeDirExist(new File(paramString));
  }

  public static boolean makeEmptyFile(File paramFile)
  {
    File localFile = paramFile.getParentFile();
    if ((localFile != null) && (localFile.exists()))
    {
      if (paramFile.exists())
        return paramFile.delete();
    }
    else if ((localFile != null) && (!localFile.mkdirs()))
      return false;
    return true;
  }

  public static boolean makeEmptyFile(String paramString)
  {
    return makeEmptyFile(new File(paramString));
  }

  public static boolean makeEmptyFile(String paramString1, String paramString2)
  {
    return makeEmptyFile(new File(paramString1, paramString2));
  }

  // ERROR //
  public static byte[] md5(File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: ldc_w 488
    //   5: invokestatic 493	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
    //   8: astore 10
    //   10: new 100	java/io/FileInputStream
    //   13: dup
    //   14: aload_0
    //   15: invokespecial 168	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   18: astore 11
    //   20: sipush 1024
    //   23: newarray byte
    //   25: astore 12
    //   27: iconst_0
    //   28: istore 13
    //   30: aload 11
    //   32: aload 12
    //   34: invokevirtual 105	java/io/FileInputStream:read	([B)I
    //   37: istore 14
    //   39: iload 14
    //   41: iconst_m1
    //   42: if_icmpgt +23 -> 65
    //   45: aload 10
    //   47: invokevirtual 497	java/security/MessageDigest:digest	()[B
    //   50: astore 15
    //   52: aload 11
    //   54: ifnull +8 -> 62
    //   57: aload 11
    //   59: invokevirtual 193	java/io/FileInputStream:close	()V
    //   62: aload 15
    //   64: areturn
    //   65: aload 10
    //   67: aload 12
    //   69: iconst_0
    //   70: iload 14
    //   72: invokevirtual 500	java/security/MessageDigest:update	([BII)V
    //   75: iload 13
    //   77: iload 14
    //   79: iadd
    //   80: istore 13
    //   82: sipush 1024
    //   85: ifle -55 -> 30
    //   88: iload 13
    //   90: sipush 1024
    //   93: if_icmplt -63 -> 30
    //   96: goto -51 -> 45
    //   99: astore 16
    //   101: aload 16
    //   103: invokevirtual 501	java/io/IOException:printStackTrace	()V
    //   106: goto -44 -> 62
    //   109: astore 8
    //   111: aload 8
    //   113: invokevirtual 502	java/security/NoSuchAlgorithmException:printStackTrace	()V
    //   116: aload_1
    //   117: ifnull +7 -> 124
    //   120: aload_1
    //   121: invokevirtual 193	java/io/FileInputStream:close	()V
    //   124: aconst_null
    //   125: areturn
    //   126: astore 9
    //   128: aload 9
    //   130: invokevirtual 501	java/io/IOException:printStackTrace	()V
    //   133: goto -9 -> 124
    //   136: astore 6
    //   138: aload 6
    //   140: invokevirtual 503	java/io/FileNotFoundException:printStackTrace	()V
    //   143: aload_1
    //   144: ifnull -20 -> 124
    //   147: aload_1
    //   148: invokevirtual 193	java/io/FileInputStream:close	()V
    //   151: goto -27 -> 124
    //   154: astore 7
    //   156: aload 7
    //   158: invokevirtual 501	java/io/IOException:printStackTrace	()V
    //   161: goto -37 -> 124
    //   164: astore 4
    //   166: aload 4
    //   168: invokevirtual 501	java/io/IOException:printStackTrace	()V
    //   171: aload_1
    //   172: ifnull -48 -> 124
    //   175: aload_1
    //   176: invokevirtual 193	java/io/FileInputStream:close	()V
    //   179: goto -55 -> 124
    //   182: astore 5
    //   184: aload 5
    //   186: invokevirtual 501	java/io/IOException:printStackTrace	()V
    //   189: goto -65 -> 124
    //   192: astore_2
    //   193: aload_1
    //   194: ifnull +7 -> 201
    //   197: aload_1
    //   198: invokevirtual 193	java/io/FileInputStream:close	()V
    //   201: aload_2
    //   202: athrow
    //   203: astore_3
    //   204: aload_3
    //   205: invokevirtual 501	java/io/IOException:printStackTrace	()V
    //   208: goto -7 -> 201
    //   211: astore_2
    //   212: aload 11
    //   214: astore_1
    //   215: goto -22 -> 193
    //   218: astore 4
    //   220: aload 11
    //   222: astore_1
    //   223: goto -57 -> 166
    //   226: astore 6
    //   228: aload 11
    //   230: astore_1
    //   231: goto -93 -> 138
    //   234: astore 8
    //   236: aload 11
    //   238: astore_1
    //   239: goto -128 -> 111
    //
    // Exception table:
    //   from	to	target	type
    //   57	62	99	java/io/IOException
    //   2	20	109	java/security/NoSuchAlgorithmException
    //   120	124	126	java/io/IOException
    //   2	20	136	java/io/FileNotFoundException
    //   147	151	154	java/io/IOException
    //   2	20	164	java/io/IOException
    //   175	179	182	java/io/IOException
    //   2	20	192	finally
    //   111	116	192	finally
    //   138	143	192	finally
    //   166	171	192	finally
    //   197	201	203	java/io/IOException
    //   20	27	211	finally
    //   30	39	211	finally
    //   45	52	211	finally
    //   65	75	211	finally
    //   20	27	218	java/io/IOException
    //   30	39	218	java/io/IOException
    //   45	52	218	java/io/IOException
    //   65	75	218	java/io/IOException
    //   20	27	226	java/io/FileNotFoundException
    //   30	39	226	java/io/FileNotFoundException
    //   45	52	226	java/io/FileNotFoundException
    //   65	75	226	java/io/FileNotFoundException
    //   20	27	234	java/security/NoSuchAlgorithmException
    //   30	39	234	java/security/NoSuchAlgorithmException
    //   45	52	234	java/security/NoSuchAlgorithmException
    //   65	75	234	java/security/NoSuchAlgorithmException
  }

  public static boolean renameCacheFile(String paramString1, String paramString2, String paramString3)
  {
    String str = getCacheDir(paramString1, User.getInstance().getUID());
    File localFile = new File(str, paramString3);
    if (!makeEmptyFile(localFile))
      return false;
    return new File(str, paramString2).renameTo(localFile);
  }

  public static boolean renameCachePath(String paramString1, String paramString2)
  {
    File localFile = new File(paramString2);
    if (!makeEmptyFile(localFile))
      return false;
    return new File(paramString1).renameTo(localFile);
  }

  // ERROR //
  public static String savePicture(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aconst_null
    //   3: astore_2
    //   4: new 16	java/lang/StringBuilder
    //   7: dup
    //   8: aload_0
    //   9: invokestatic 514	com/kaixin001/util/StringUtil:MD5Encode	(Ljava/lang/String;)Ljava/lang/String;
    //   12: invokestatic 22	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   15: invokespecial 25	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   18: ldc 232
    //   20: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: invokevirtual 35	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   26: astore 9
    //   28: invokestatic 273	android/os/Environment:getExternalStorageState	()Ljava/lang/String;
    //   31: ldc_w 275
    //   34: invokevirtual 279	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   37: istore 10
    //   39: aconst_null
    //   40: astore_2
    //   41: aconst_null
    //   42: astore_1
    //   43: iload 10
    //   45: ifeq +87 -> 132
    //   48: new 16	java/lang/StringBuilder
    //   51: dup
    //   52: invokestatic 282	android/os/Environment:getExternalStorageDirectory	()Ljava/io/File;
    //   55: invokevirtual 285	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   58: invokestatic 22	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   61: invokespecial 25	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   64: ldc_w 516
    //   67: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   70: invokevirtual 35	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   73: astore 11
    //   75: new 37	java/io/File
    //   78: dup
    //   79: aload 11
    //   81: invokespecial 38	java/io/File:<init>	(Ljava/lang/String;)V
    //   84: astore 12
    //   86: aload 12
    //   88: invokevirtual 42	java/io/File:exists	()Z
    //   91: istore 13
    //   93: aconst_null
    //   94: astore_2
    //   95: aconst_null
    //   96: astore_1
    //   97: iload 13
    //   99: ifne +79 -> 178
    //   102: aload 12
    //   104: invokevirtual 519	java/io/File:mkdir	()Z
    //   107: istore 14
    //   109: iload 14
    //   111: ifne +67 -> 178
    //   114: iconst_0
    //   115: ifeq +7 -> 122
    //   118: aconst_null
    //   119: invokevirtual 520	java/io/FileOutputStream:close	()V
    //   122: iconst_0
    //   123: ifeq +7 -> 130
    //   126: aconst_null
    //   127: invokevirtual 523	java/io/BufferedOutputStream:close	()V
    //   130: aconst_null
    //   131: areturn
    //   132: iconst_0
    //   133: ifeq +7 -> 140
    //   136: aconst_null
    //   137: invokevirtual 520	java/io/FileOutputStream:close	()V
    //   140: iconst_0
    //   141: ifeq -11 -> 130
    //   144: aconst_null
    //   145: invokevirtual 523	java/io/BufferedOutputStream:close	()V
    //   148: aconst_null
    //   149: areturn
    //   150: astore 24
    //   152: ldc 8
    //   154: ldc_w 525
    //   157: aload 24
    //   159: invokestatic 118	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   162: aconst_null
    //   163: areturn
    //   164: astore 23
    //   166: ldc 8
    //   168: ldc_w 525
    //   171: aload 23
    //   173: invokestatic 118	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   176: aconst_null
    //   177: areturn
    //   178: invokestatic 530	com/kaixin001/util/ImageCache:getInstance	()Lcom/kaixin001/util/ImageCache;
    //   181: aload_0
    //   182: invokevirtual 534	com/kaixin001/util/ImageCache:createSafeImage	(Ljava/lang/String;)Landroid/graphics/Bitmap;
    //   185: astore 15
    //   187: new 37	java/io/File
    //   190: dup
    //   191: new 16	java/lang/StringBuilder
    //   194: dup
    //   195: aload 11
    //   197: invokestatic 22	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   200: invokespecial 25	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   203: aload 9
    //   205: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   208: invokevirtual 35	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   211: invokespecial 38	java/io/File:<init>	(Ljava/lang/String;)V
    //   214: astore 16
    //   216: new 95	java/io/FileOutputStream
    //   219: dup
    //   220: aload 16
    //   222: invokespecial 98	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   225: astore 17
    //   227: new 522	java/io/BufferedOutputStream
    //   230: dup
    //   231: aload 17
    //   233: invokespecial 537	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   236: astore 18
    //   238: aload 15
    //   240: getstatic 543	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   243: bipush 100
    //   245: aload 18
    //   247: invokevirtual 547	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   250: istore 19
    //   252: aload 18
    //   254: invokevirtual 548	java/io/BufferedOutputStream:flush	()V
    //   257: iload 19
    //   259: ifeq +48 -> 307
    //   262: aload 16
    //   264: invokevirtual 285	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   267: astore 20
    //   269: aload 17
    //   271: ifnull +8 -> 279
    //   274: aload 17
    //   276: invokevirtual 520	java/io/FileOutputStream:close	()V
    //   279: aload 18
    //   281: ifnull +8 -> 289
    //   284: aload 18
    //   286: invokevirtual 523	java/io/BufferedOutputStream:close	()V
    //   289: aload 20
    //   291: areturn
    //   292: astore 21
    //   294: ldc 8
    //   296: ldc_w 525
    //   299: aload 21
    //   301: invokestatic 118	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   304: goto -15 -> 289
    //   307: aload 17
    //   309: ifnull +8 -> 317
    //   312: aload 17
    //   314: invokevirtual 520	java/io/FileOutputStream:close	()V
    //   317: aload 18
    //   319: ifnull +8 -> 327
    //   322: aload 18
    //   324: invokevirtual 523	java/io/BufferedOutputStream:close	()V
    //   327: aconst_null
    //   328: areturn
    //   329: astore 22
    //   331: ldc 8
    //   333: ldc_w 525
    //   336: aload 22
    //   338: invokestatic 118	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   341: goto -14 -> 327
    //   344: astore 7
    //   346: ldc 8
    //   348: ldc_w 550
    //   351: aload 7
    //   353: invokestatic 118	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   356: aload_1
    //   357: ifnull +7 -> 364
    //   360: aload_1
    //   361: invokevirtual 520	java/io/FileOutputStream:close	()V
    //   364: aload_2
    //   365: ifnull -235 -> 130
    //   368: aload_2
    //   369: invokevirtual 523	java/io/BufferedOutputStream:close	()V
    //   372: aconst_null
    //   373: areturn
    //   374: astore 8
    //   376: ldc 8
    //   378: ldc_w 525
    //   381: aload 8
    //   383: invokestatic 118	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   386: aconst_null
    //   387: areturn
    //   388: astore 5
    //   390: aload 5
    //   392: invokevirtual 181	java/lang/OutOfMemoryError:printStackTrace	()V
    //   395: aload_1
    //   396: ifnull +7 -> 403
    //   399: aload_1
    //   400: invokevirtual 520	java/io/FileOutputStream:close	()V
    //   403: aload_2
    //   404: ifnull -274 -> 130
    //   407: aload_2
    //   408: invokevirtual 523	java/io/BufferedOutputStream:close	()V
    //   411: aconst_null
    //   412: areturn
    //   413: astore 6
    //   415: ldc 8
    //   417: ldc_w 525
    //   420: aload 6
    //   422: invokestatic 118	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   425: aconst_null
    //   426: areturn
    //   427: astore_3
    //   428: aload_1
    //   429: ifnull +7 -> 436
    //   432: aload_1
    //   433: invokevirtual 520	java/io/FileOutputStream:close	()V
    //   436: aload_2
    //   437: ifnull +7 -> 444
    //   440: aload_2
    //   441: invokevirtual 523	java/io/BufferedOutputStream:close	()V
    //   444: aload_3
    //   445: athrow
    //   446: astore 4
    //   448: ldc 8
    //   450: ldc_w 525
    //   453: aload 4
    //   455: invokestatic 118	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   458: goto -14 -> 444
    //   461: astore_3
    //   462: aload 17
    //   464: astore_1
    //   465: aconst_null
    //   466: astore_2
    //   467: goto -39 -> 428
    //   470: astore_3
    //   471: aload 18
    //   473: astore_2
    //   474: aload 17
    //   476: astore_1
    //   477: goto -49 -> 428
    //   480: astore 5
    //   482: aload 17
    //   484: astore_1
    //   485: aconst_null
    //   486: astore_2
    //   487: goto -97 -> 390
    //   490: astore 5
    //   492: aload 18
    //   494: astore_2
    //   495: aload 17
    //   497: astore_1
    //   498: goto -108 -> 390
    //   501: astore 7
    //   503: aload 17
    //   505: astore_1
    //   506: aconst_null
    //   507: astore_2
    //   508: goto -162 -> 346
    //   511: astore 7
    //   513: aload 18
    //   515: astore_2
    //   516: aload 17
    //   518: astore_1
    //   519: goto -173 -> 346
    //
    // Exception table:
    //   from	to	target	type
    //   136	140	150	java/lang/Exception
    //   144	148	150	java/lang/Exception
    //   118	122	164	java/lang/Exception
    //   126	130	164	java/lang/Exception
    //   274	279	292	java/lang/Exception
    //   284	289	292	java/lang/Exception
    //   312	317	329	java/lang/Exception
    //   322	327	329	java/lang/Exception
    //   4	39	344	java/lang/Exception
    //   48	93	344	java/lang/Exception
    //   102	109	344	java/lang/Exception
    //   178	227	344	java/lang/Exception
    //   360	364	374	java/lang/Exception
    //   368	372	374	java/lang/Exception
    //   4	39	388	java/lang/OutOfMemoryError
    //   48	93	388	java/lang/OutOfMemoryError
    //   102	109	388	java/lang/OutOfMemoryError
    //   178	227	388	java/lang/OutOfMemoryError
    //   399	403	413	java/lang/Exception
    //   407	411	413	java/lang/Exception
    //   4	39	427	finally
    //   48	93	427	finally
    //   102	109	427	finally
    //   178	227	427	finally
    //   346	356	427	finally
    //   390	395	427	finally
    //   432	436	446	java/lang/Exception
    //   440	444	446	java/lang/Exception
    //   227	238	461	finally
    //   238	257	470	finally
    //   262	269	470	finally
    //   227	238	480	java/lang/OutOfMemoryError
    //   238	257	490	java/lang/OutOfMemoryError
    //   262	269	490	java/lang/OutOfMemoryError
    //   227	238	501	java/lang/Exception
    //   238	257	511	java/lang/Exception
    //   262	269	511	java/lang/Exception
  }

  // ERROR //
  public static boolean setCacheData(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 79	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: ifne +24 -> 28
    //   7: aload_1
    //   8: invokestatic 79	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   11: ifne +17 -> 28
    //   14: aload_2
    //   15: invokestatic 79	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   18: ifne +10 -> 28
    //   21: aload_3
    //   22: invokestatic 79	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   25: ifeq +5 -> 30
    //   28: iconst_0
    //   29: ireturn
    //   30: aconst_null
    //   31: astore 4
    //   33: aconst_null
    //   34: astore 5
    //   36: aload_0
    //   37: aload_1
    //   38: invokestatic 81	com/kaixin001/util/FileUtil:checkDataDirs	(Ljava/lang/String;Ljava/lang/String;)Z
    //   41: istore 9
    //   43: iload 9
    //   45: ifne +13 -> 58
    //   48: aconst_null
    //   49: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   52: aconst_null
    //   53: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   56: iconst_0
    //   57: ireturn
    //   58: new 37	java/io/File
    //   61: dup
    //   62: new 16	java/lang/StringBuilder
    //   65: dup
    //   66: aload_0
    //   67: invokestatic 22	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   70: invokespecial 25	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   73: ldc 89
    //   75: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   78: aload_1
    //   79: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   82: ldc 67
    //   84: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: aload_2
    //   88: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   91: invokevirtual 35	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   94: invokespecial 38	java/io/File:<init>	(Ljava/lang/String;)V
    //   97: astore 10
    //   99: aload 10
    //   101: invokestatic 93	com/kaixin001/util/FileUtil:makeEmptyFile	(Ljava/io/File;)Z
    //   104: istore 11
    //   106: iload 11
    //   108: ifne +13 -> 121
    //   111: aconst_null
    //   112: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   115: aconst_null
    //   116: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   119: iconst_0
    //   120: ireturn
    //   121: new 95	java/io/FileOutputStream
    //   124: dup
    //   125: aload 10
    //   127: invokespecial 98	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   130: astore 12
    //   132: new 553	java/io/OutputStreamWriter
    //   135: dup
    //   136: aload 12
    //   138: invokespecial 554	java/io/OutputStreamWriter:<init>	(Ljava/io/OutputStream;)V
    //   141: astore 13
    //   143: aload 13
    //   145: aload_3
    //   146: invokevirtual 556	java/io/OutputStreamWriter:write	(Ljava/lang/String;)V
    //   149: aload 13
    //   151: invokevirtual 557	java/io/OutputStreamWriter:flush	()V
    //   154: aload 12
    //   156: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   159: aload 13
    //   161: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   164: iconst_1
    //   165: ireturn
    //   166: astore 8
    //   168: ldc 8
    //   170: ldc_w 558
    //   173: aload 8
    //   175: invokestatic 118	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   178: aload 4
    //   180: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   183: aload 5
    //   185: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   188: iconst_0
    //   189: ireturn
    //   190: astore 7
    //   192: ldc 8
    //   194: ldc_w 560
    //   197: invokestatic 562	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   200: aload 4
    //   202: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   205: aload 5
    //   207: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   210: iconst_1
    //   211: ireturn
    //   212: astore 6
    //   214: aload 4
    //   216: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   219: aload 5
    //   221: invokestatic 87	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   224: aload 6
    //   226: athrow
    //   227: astore 6
    //   229: aload 12
    //   231: astore 4
    //   233: aconst_null
    //   234: astore 5
    //   236: goto -22 -> 214
    //   239: astore 6
    //   241: aload 13
    //   243: astore 5
    //   245: aload 12
    //   247: astore 4
    //   249: goto -35 -> 214
    //   252: astore 15
    //   254: aload 12
    //   256: astore 4
    //   258: aconst_null
    //   259: astore 5
    //   261: goto -69 -> 192
    //   264: astore 14
    //   266: aload 13
    //   268: astore 5
    //   270: aload 12
    //   272: astore 4
    //   274: goto -82 -> 192
    //   277: astore 8
    //   279: aload 12
    //   281: astore 4
    //   283: aconst_null
    //   284: astore 5
    //   286: goto -118 -> 168
    //   289: astore 8
    //   291: aload 13
    //   293: astore 5
    //   295: aload 12
    //   297: astore 4
    //   299: goto -131 -> 168
    //
    // Exception table:
    //   from	to	target	type
    //   36	43	166	java/lang/Exception
    //   58	106	166	java/lang/Exception
    //   121	132	166	java/lang/Exception
    //   36	43	190	java/lang/OutOfMemoryError
    //   58	106	190	java/lang/OutOfMemoryError
    //   121	132	190	java/lang/OutOfMemoryError
    //   36	43	212	finally
    //   58	106	212	finally
    //   121	132	212	finally
    //   168	178	212	finally
    //   192	200	212	finally
    //   132	143	227	finally
    //   143	154	239	finally
    //   132	143	252	java/lang/OutOfMemoryError
    //   143	154	264	java/lang/OutOfMemoryError
    //   132	143	277	java/lang/Exception
    //   143	154	289	java/lang/Exception
  }

  public static boolean skipPicture(String paramString)
  {
    String str1 = Setting.getInstance().getDeviceName();
    String str2 = Setting.getInstance().getManufacturerName();
    try
    {
      KXExifInterface localKXExifInterface = ImageCache.getExifInfo(paramString);
      String str3 = localKXExifInterface.getAttribute(KXExifInterface.TAG_MAKE);
      String str4 = localKXExifInterface.getAttribute(KXExifInterface.TAG_MODEL);
      if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str3)) && (str1.contains(str3)))
        return false;
      if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str4)) && (str1.contains(str4)))
        return false;
      if ((!TextUtils.isEmpty(str2)) && (!TextUtils.isEmpty(str3)) && (str2.contains(str3)))
        return false;
      if ((!TextUtils.isEmpty(str2)) && (!TextUtils.isEmpty(str4)))
      {
        boolean bool = str2.contains(str4);
        if (bool)
          return false;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return true;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.FileUtil
 * JD-Core Version:    0.6.0
 */