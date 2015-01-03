package com.kaixin001.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.TypedValue;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class ImageCache
{
  public static final int GINGERBREAD_MR1 = 10;
  public static final int MAX_PIC_FILE_SIZE = 512000;
  public static final int MAX_PIC_WIDTH = 600;
  public static final int PIC_COMPRESS_QUALITY = 75;
  private static final String SUB_FOLDER = ".data/";
  private static final String TAG = "ImageCache";
  private static Context mContext;
  private static ImageCache sInstance = null;
  private int hardCachedSize = 8388608;
  private int mFileCount = 400;
  private String mFileExName = ".kxbmp";
  private final HashMap<String, SoftReference<Bitmap>> mMapImageCache = new HashMap();
  private String mPicCachePath;
  private LruCache<String, Bitmap> sHardBitmapCache = null;

  static
  {
    mContext = null;
  }

  public static int calculateInSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
  {
    int i = 1;
    if ((paramInt1 == 0) && (paramInt2 == 0))
      return i;
    int j = paramOptions.outHeight;
    int k = paramOptions.outWidth;
    if ((j > paramInt2) || (k > paramInt1))
      if (k <= j)
        break label55;
    label55: for (i = Math.round(j / paramInt2); ; i = Math.round(k / paramInt1))
      return i;
  }

  private void clearRecycledItem()
  {
    monitorenter;
    while (true)
    {
      Iterator localIterator;
      Map.Entry localEntry;
      try
      {
        localIterator = this.mMapImageCache.entrySet().iterator();
        boolean bool = localIterator.hasNext();
        if (!bool)
          return;
        localEntry = (Map.Entry)localIterator.next();
        if (localEntry == null)
        {
          localIterator.remove();
          continue;
        }
      }
      finally
      {
        monitorexit;
      }
      SoftReference localSoftReference = (SoftReference)localEntry.getValue();
      if ((localSoftReference != null) && ((localSoftReference == null) || (localSoftReference.get() != null)) && ((localSoftReference == null) || (localSoftReference.get() == null) || (!((Bitmap)localSoftReference.get()).isRecycled())))
        continue;
      localIterator.remove();
    }
  }

  private void createHardBitmapCache()
  {
    if (this.sHardBitmapCache == null)
    {
      this.hardCachedSize = getCacheSize(mContext);
      this.sHardBitmapCache = new LruCache(this.hardCachedSize)
      {
        protected void entryRemoved(boolean paramBoolean, String paramString, Bitmap paramBitmap1, Bitmap paramBitmap2)
        {
          KXLog.d("tag", "hard cache is full , push to soft cache");
          synchronized (ImageCache.this.mMapImageCache)
          {
            ImageCache.this.mMapImageCache.put(paramString, new SoftReference(paramBitmap1));
            return;
          }
        }

        public int sizeOf(String paramString, Bitmap paramBitmap)
        {
          return paramBitmap.getRowBytes() * paramBitmap.getHeight();
        }
      };
    }
  }

  public static Bitmap createStringBitmap(String paramString, Paint paramPaint)
  {
    if ((TextUtils.isEmpty(paramString)) || (paramPaint == null))
      return null;
    float f1 = paramPaint.measureText(paramString);
    float f2 = paramPaint.ascent();
    float f3 = paramPaint.descent() + -f2;
    Bitmap localBitmap = Bitmap.createBitmap((int)f1, (int)f3, Bitmap.Config.ARGB_8888);
    new Canvas(localBitmap).drawText(paramString, 0.0F, -f2, paramPaint);
    localBitmap.setDensity(0);
    return localBitmap;
  }

  public static Bitmap createStringBitmap(String paramString, Paint paramPaint, int paramInt)
  {
    if ((TextUtils.isEmpty(paramString)) || (paramPaint == null))
      return null;
    float f1 = paramPaint.measureText(paramString);
    float f2 = paramPaint.ascent();
    float f3 = paramPaint.descent() + -f2;
    (f3 + paramInt);
    Bitmap localBitmap = Bitmap.createBitmap((int)f1, (int)f3, Bitmap.Config.ARGB_8888);
    new Canvas(localBitmap).drawText(paramString, 0.0F, -f2, paramPaint);
    localBitmap.setDensity(0);
    return localBitmap;
  }

  // ERROR //
  private Bitmap getCacheBmp(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 179	com/kaixin001/util/ImageCache:mPicCachePath	Ljava/lang/String;
    //   4: invokestatic 134	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   7: ifne +10 -> 17
    //   10: aload_1
    //   11: invokestatic 134	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   14: ifeq +5 -> 19
    //   17: aconst_null
    //   18: areturn
    //   19: aload_0
    //   20: aload_1
    //   21: invokevirtual 183	com/kaixin001/util/ImageCache:getFileByUrl	(Ljava/lang/String;)Ljava/lang/String;
    //   24: astore_2
    //   25: invokestatic 189	com/kaixin001/util/LruFileCache:getInstance	()Lcom/kaixin001/util/LruFileCache;
    //   28: aload_2
    //   29: invokevirtual 193	com/kaixin001/util/LruFileCache:getFile	(Ljava/lang/String;)Ljava/io/File;
    //   32: astore_3
    //   33: aload_3
    //   34: invokevirtual 198	java/io/File:exists	()Z
    //   37: ifne +5 -> 42
    //   40: aconst_null
    //   41: areturn
    //   42: aload_3
    //   43: invokevirtual 202	java/io/File:length	()J
    //   46: lstore 4
    //   48: lload 4
    //   50: lconst_0
    //   51: lcmp
    //   52: ifne +10 -> 62
    //   55: aload_0
    //   56: aload_1
    //   57: invokevirtual 206	com/kaixin001/util/ImageCache:deleteFileAndCacheImage	(Ljava/lang/String;)V
    //   60: aconst_null
    //   61: areturn
    //   62: aconst_null
    //   63: astore 6
    //   65: aconst_null
    //   66: astore 7
    //   68: new 65	android/graphics/BitmapFactory$Options
    //   71: dup
    //   72: invokespecial 207	android/graphics/BitmapFactory$Options:<init>	()V
    //   75: astore 8
    //   77: lload 4
    //   79: ldc 208
    //   81: i2l
    //   82: lcmp
    //   83: ifgt +68 -> 151
    //   86: aload 8
    //   88: iconst_1
    //   89: putfield 211	android/graphics/BitmapFactory$Options:inSampleSize	I
    //   92: new 213	java/io/FileInputStream
    //   95: dup
    //   96: aload_3
    //   97: invokespecial 216	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   100: astore 12
    //   102: aload 12
    //   104: aconst_null
    //   105: aload 8
    //   107: invokestatic 222	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   110: astore 6
    //   112: aload_3
    //   113: invokestatic 227	java/lang/System:currentTimeMillis	()J
    //   116: ldc2_w 228
    //   119: ldiv
    //   120: invokevirtual 233	java/io/File:setLastModified	(J)Z
    //   123: ifne +10 -> 133
    //   126: ldc 20
    //   128: ldc 235
    //   130: invokestatic 241	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   133: aload 6
    //   135: ifnonnull +8 -> 143
    //   138: aload_0
    //   139: aload_1
    //   140: invokevirtual 206	com/kaixin001/util/ImageCache:deleteFileAndCacheImage	(Ljava/lang/String;)V
    //   143: aload 12
    //   145: invokestatic 247	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   148: aload 6
    //   150: areturn
    //   151: lload 4
    //   153: ldc2_w 248
    //   156: ldc 208
    //   158: i2l
    //   159: lmul
    //   160: lcmp
    //   161: ifgt +40 -> 201
    //   164: aload 8
    //   166: iconst_2
    //   167: putfield 211	android/graphics/BitmapFactory$Options:inSampleSize	I
    //   170: goto -78 -> 92
    //   173: astore 11
    //   175: ldc 20
    //   177: ldc 250
    //   179: aload 11
    //   181: invokestatic 254	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   184: aload 6
    //   186: ifnonnull +8 -> 194
    //   189: aload_0
    //   190: aload_1
    //   191: invokevirtual 206	com/kaixin001/util/ImageCache:deleteFileAndCacheImage	(Ljava/lang/String;)V
    //   194: aload 7
    //   196: invokestatic 247	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   199: aconst_null
    //   200: areturn
    //   201: ldc 208
    //   203: i2l
    //   204: lstore 13
    //   206: aload 8
    //   208: iconst_1
    //   209: lload 4
    //   211: lload 13
    //   213: ldiv
    //   214: l2d
    //   215: invokestatic 258	java/lang/Math:log	(D)D
    //   218: ldc2_w 259
    //   221: invokestatic 258	java/lang/Math:log	(D)D
    //   224: ddiv
    //   225: d2i
    //   226: iadd
    //   227: putfield 211	android/graphics/BitmapFactory$Options:inSampleSize	I
    //   230: goto -138 -> 92
    //   233: astore 10
    //   235: ldc 20
    //   237: ldc 250
    //   239: aload 10
    //   241: invokestatic 254	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   244: aload 6
    //   246: ifnonnull +8 -> 254
    //   249: aload_0
    //   250: aload_1
    //   251: invokevirtual 206	com/kaixin001/util/ImageCache:deleteFileAndCacheImage	(Ljava/lang/String;)V
    //   254: aload 7
    //   256: invokestatic 247	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   259: aconst_null
    //   260: areturn
    //   261: astore 9
    //   263: aload 6
    //   265: ifnonnull +8 -> 273
    //   268: aload_0
    //   269: aload_1
    //   270: invokevirtual 206	com/kaixin001/util/ImageCache:deleteFileAndCacheImage	(Ljava/lang/String;)V
    //   273: aload 7
    //   275: invokestatic 247	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   278: aload 9
    //   280: athrow
    //   281: astore 9
    //   283: aload 12
    //   285: astore 7
    //   287: goto -24 -> 263
    //   290: astore 10
    //   292: aload 12
    //   294: astore 7
    //   296: goto -61 -> 235
    //   299: astore 11
    //   301: aload 12
    //   303: astore 7
    //   305: goto -130 -> 175
    //
    // Exception table:
    //   from	to	target	type
    //   68	77	173	java/lang/Exception
    //   86	92	173	java/lang/Exception
    //   92	102	173	java/lang/Exception
    //   164	170	173	java/lang/Exception
    //   206	230	173	java/lang/Exception
    //   68	77	233	java/lang/OutOfMemoryError
    //   86	92	233	java/lang/OutOfMemoryError
    //   92	102	233	java/lang/OutOfMemoryError
    //   164	170	233	java/lang/OutOfMemoryError
    //   206	230	233	java/lang/OutOfMemoryError
    //   68	77	261	finally
    //   86	92	261	finally
    //   92	102	261	finally
    //   164	170	261	finally
    //   175	184	261	finally
    //   206	230	261	finally
    //   235	244	261	finally
    //   102	133	281	finally
    //   102	133	290	java/lang/OutOfMemoryError
    //   102	133	299	java/lang/Exception
  }

  private int getCacheSize(Context paramContext)
  {
    int i = ((ActivityManager)paramContext.getSystemService("activity")).getMemoryClass();
    if (i <= 24)
      return (i << 20) / 24;
    if (i <= 36)
      return (i << 20) / 18;
    if (i <= 48)
      return (i << 20) / 12;
    return i << 20 >> 3;
  }

  public static KXExifInterface getExifInfo(String paramString)
  {
    try
    {
      KXExifInterface localKXExifInterface = new KXExifInterface(paramString);
      return localKXExifInterface;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static ImageCache getInstance()
  {
    monitorenter;
    try
    {
      if (sInstance == null)
        sInstance = new ImageCache();
      ImageCache localImageCache = sInstance;
      return localImageCache;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  // ERROR //
  public static Bitmap loadBitmapFromFile(String paramString, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 134	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: ifeq +5 -> 9
    //   7: aconst_null
    //   8: areturn
    //   9: new 195	java/io/File
    //   12: dup
    //   13: aload_0
    //   14: invokespecial 288	java/io/File:<init>	(Ljava/lang/String;)V
    //   17: invokevirtual 198	java/io/File:exists	()Z
    //   20: ifeq -13 -> 7
    //   23: aconst_null
    //   24: astore_2
    //   25: iload_1
    //   26: ifeq +75 -> 101
    //   29: new 65	android/graphics/BitmapFactory$Options
    //   32: dup
    //   33: invokespecial 207	android/graphics/BitmapFactory$Options:<init>	()V
    //   36: astore 6
    //   38: aload 6
    //   40: iconst_1
    //   41: putfield 292	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
    //   44: aload_0
    //   45: aload 6
    //   47: invokestatic 296	android/graphics/BitmapFactory:decodeFile	(Ljava/lang/String;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   50: astore 7
    //   52: aload 7
    //   54: ifnull +19 -> 73
    //   57: aload 7
    //   59: invokevirtual 116	android/graphics/Bitmap:isRecycled	()Z
    //   62: ifne +11 -> 73
    //   65: aload 7
    //   67: invokevirtual 299	android/graphics/Bitmap:recycle	()V
    //   70: invokestatic 302	java/lang/System:gc	()V
    //   73: aload 6
    //   75: getfield 68	android/graphics/BitmapFactory$Options:outHeight	I
    //   78: sipush 600
    //   81: if_icmpge +30 -> 111
    //   84: aload 6
    //   86: getfield 71	android/graphics/BitmapFactory$Options:outWidth	I
    //   89: istore 8
    //   91: iload 8
    //   93: sipush 600
    //   96: if_icmpge +15 -> 111
    //   99: aconst_null
    //   100: astore_2
    //   101: aload_0
    //   102: aload_2
    //   103: invokestatic 296	android/graphics/BitmapFactory:decodeFile	(Ljava/lang/String;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   106: astore 5
    //   108: aload 5
    //   110: areturn
    //   111: aload 6
    //   113: iconst_0
    //   114: putfield 292	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
    //   117: aload 6
    //   119: aload 6
    //   121: getfield 71	android/graphics/BitmapFactory$Options:outWidth	I
    //   124: aload 6
    //   126: getfield 68	android/graphics/BitmapFactory$Options:outHeight	I
    //   129: invokestatic 306	java/lang/Math:max	(II)I
    //   132: sipush 600
    //   135: idiv
    //   136: putfield 211	android/graphics/BitmapFactory$Options:inSampleSize	I
    //   139: aload 6
    //   141: astore_2
    //   142: goto -41 -> 101
    //   145: astore 4
    //   147: ldc 20
    //   149: ldc_w 307
    //   152: aload 4
    //   154: invokestatic 254	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   157: aconst_null
    //   158: areturn
    //   159: astore_3
    //   160: ldc 20
    //   162: ldc_w 307
    //   165: aload_3
    //   166: invokestatic 254	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   169: aconst_null
    //   170: areturn
    //   171: astore_3
    //   172: goto -12 -> 160
    //   175: astore 4
    //   177: goto -30 -> 147
    //
    // Exception table:
    //   from	to	target	type
    //   29	38	145	java/lang/OutOfMemoryError
    //   101	108	145	java/lang/OutOfMemoryError
    //   29	38	159	java/lang/Exception
    //   101	108	159	java/lang/Exception
    //   38	52	171	java/lang/Exception
    //   57	73	171	java/lang/Exception
    //   73	91	171	java/lang/Exception
    //   111	139	171	java/lang/Exception
    //   38	52	175	java/lang/OutOfMemoryError
    //   57	73	175	java/lang/OutOfMemoryError
    //   73	91	175	java/lang/OutOfMemoryError
    //   111	139	175	java/lang/OutOfMemoryError
  }

  public static int readPictureDegree(String paramString)
  {
    try
    {
      int i = new ExifInterface(paramString).getAttributeInt("Orientation", 1);
      switch (i)
      {
      case 4:
      case 5:
      case 7:
      default:
        return 0;
      case 6:
        return 90;
      case 3:
        return 180;
      case 8:
      }
      return 270;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return 0;
  }

  public static String saveBitmapToFile(Context paramContext, Bitmap paramBitmap, KXExifInterface paramKXExifInterface, String paramString)
  {
    if (paramBitmap == null)
    {
      KXLog.d("saveBitmapToFile", "null == bmp");
      return "";
    }
    return saveBitmapToFileWithAbsolutePath(paramContext, paramBitmap, paramKXExifInterface, paramContext.getFileStreamPath(paramString).getAbsolutePath());
  }

  // ERROR //
  public static String saveBitmapToFileWithAbsolutePath(Context paramContext, Bitmap paramBitmap, KXExifInterface paramKXExifInterface, String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +20 -> 21
    //   4: ldc_w 324
    //   7: ldc_w 326
    //   10: invokestatic 241	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   13: ldc_w 328
    //   16: astore 7
    //   18: aload 7
    //   20: areturn
    //   21: new 195	java/io/File
    //   24: dup
    //   25: aload_3
    //   26: invokespecial 288	java/io/File:<init>	(Ljava/lang/String;)V
    //   29: astore 4
    //   31: aload 4
    //   33: invokevirtual 198	java/io/File:exists	()Z
    //   36: ifeq +9 -> 45
    //   39: aload 4
    //   41: invokevirtual 341	java/io/File:delete	()Z
    //   44: pop
    //   45: aconst_null
    //   46: astore 5
    //   48: new 343	java/io/FileOutputStream
    //   51: dup
    //   52: aload 4
    //   54: invokespecial 344	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   57: astore 6
    //   59: aload 6
    //   61: ifnull +20 -> 81
    //   64: aload_1
    //   65: getstatic 350	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   68: bipush 75
    //   70: aload 6
    //   72: invokevirtual 354	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   75: pop
    //   76: aload 6
    //   78: invokevirtual 357	java/io/FileOutputStream:flush	()V
    //   81: aload 6
    //   83: invokestatic 247	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   86: aload 4
    //   88: invokevirtual 198	java/io/File:exists	()Z
    //   91: ifeq +13 -> 104
    //   94: aload 4
    //   96: invokevirtual 202	java/io/File:length	()J
    //   99: lconst_0
    //   100: lcmp
    //   101: ifne +51 -> 152
    //   104: ldc_w 324
    //   107: ldc_w 359
    //   110: invokestatic 241	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   113: ldc_w 328
    //   116: areturn
    //   117: astore 12
    //   119: aload 12
    //   121: invokevirtual 283	java/lang/Exception:printStackTrace	()V
    //   124: goto -79 -> 45
    //   127: astore 9
    //   129: aload 9
    //   131: invokevirtual 283	java/lang/Exception:printStackTrace	()V
    //   134: aload 5
    //   136: invokestatic 247	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   139: goto -53 -> 86
    //   142: astore 10
    //   144: aload 5
    //   146: invokestatic 247	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   149: aload 10
    //   151: athrow
    //   152: aload 4
    //   154: invokevirtual 335	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   157: astore 7
    //   159: aload_2
    //   160: ifnull -142 -> 18
    //   163: aload 7
    //   165: aload_2
    //   166: invokestatic 363	com/kaixin001/util/ImageCache:saveExifToFile	(Ljava/lang/String;Lcom/kaixin001/util/KXExifInterface;)Z
    //   169: pop
    //   170: aload 7
    //   172: areturn
    //   173: astore 10
    //   175: aload 6
    //   177: astore 5
    //   179: goto -35 -> 144
    //   182: astore 9
    //   184: aload 6
    //   186: astore 5
    //   188: goto -59 -> 129
    //
    // Exception table:
    //   from	to	target	type
    //   39	45	117	java/lang/Exception
    //   48	59	127	java/lang/Exception
    //   48	59	142	finally
    //   129	134	142	finally
    //   64	81	173	finally
    //   64	81	182	java/lang/Exception
  }

  public static String saveBitmapToFileWithAbsolutePath(Context paramContext, Bitmap paramBitmap, String paramString, Bitmap.CompressFormat paramCompressFormat)
  {
    return saveBitmapToFileWithAbsolutePath(paramContext, paramBitmap, paramString, paramCompressFormat, 100);
  }

  // ERROR //
  public static String saveBitmapToFileWithAbsolutePath(Context paramContext, Bitmap paramBitmap, String paramString, Bitmap.CompressFormat paramCompressFormat, int paramInt)
  {
    // Byte code:
    //   0: ldc 20
    //   2: new 369	java/lang/StringBuilder
    //   5: dup
    //   6: ldc_w 371
    //   9: invokespecial 372	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   12: aload_2
    //   13: invokevirtual 376	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   16: ldc_w 378
    //   19: invokevirtual 376	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   22: iload 4
    //   24: invokevirtual 381	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   27: invokevirtual 384	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   30: invokestatic 241	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   33: aload_1
    //   34: ifnonnull +16 -> 50
    //   37: ldc_w 324
    //   40: ldc_w 326
    //   43: invokestatic 241	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   46: ldc_w 328
    //   49: areturn
    //   50: new 195	java/io/File
    //   53: dup
    //   54: aload_2
    //   55: invokespecial 288	java/io/File:<init>	(Ljava/lang/String;)V
    //   58: astore 5
    //   60: aload 5
    //   62: invokevirtual 198	java/io/File:exists	()Z
    //   65: ifeq +9 -> 74
    //   68: aload 5
    //   70: invokevirtual 341	java/io/File:delete	()Z
    //   73: pop
    //   74: aconst_null
    //   75: astore 6
    //   77: new 343	java/io/FileOutputStream
    //   80: dup
    //   81: aload 5
    //   83: invokespecial 344	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   86: astore 7
    //   88: aload 7
    //   90: ifnull +27 -> 117
    //   93: aload_3
    //   94: getstatic 350	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   97: if_acmpne +65 -> 162
    //   100: aload_1
    //   101: getstatic 350	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   104: iload 4
    //   106: aload 7
    //   108: invokevirtual 354	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   111: pop
    //   112: aload 7
    //   114: invokevirtual 357	java/io/FileOutputStream:flush	()V
    //   117: aload 7
    //   119: invokestatic 247	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   122: aload 5
    //   124: invokevirtual 198	java/io/File:exists	()Z
    //   127: ifeq +13 -> 140
    //   130: aload 5
    //   132: invokevirtual 202	java/io/File:length	()J
    //   135: lconst_0
    //   136: lcmp
    //   137: ifne +69 -> 206
    //   140: ldc 20
    //   142: ldc_w 359
    //   145: invokestatic 241	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   148: ldc_w 328
    //   151: areturn
    //   152: astore 12
    //   154: aload 12
    //   156: invokevirtual 283	java/lang/Exception:printStackTrace	()V
    //   159: goto -85 -> 74
    //   162: aload_1
    //   163: getstatic 387	android/graphics/Bitmap$CompressFormat:PNG	Landroid/graphics/Bitmap$CompressFormat;
    //   166: bipush 100
    //   168: aload 7
    //   170: invokevirtual 354	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   173: pop
    //   174: goto -62 -> 112
    //   177: astore 9
    //   179: aload 7
    //   181: astore 6
    //   183: aload 9
    //   185: invokevirtual 283	java/lang/Exception:printStackTrace	()V
    //   188: aload 6
    //   190: invokestatic 247	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   193: goto -71 -> 122
    //   196: astore 8
    //   198: aload 6
    //   200: invokestatic 247	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   203: aload 8
    //   205: athrow
    //   206: ldc 20
    //   208: new 369	java/lang/StringBuilder
    //   211: dup
    //   212: ldc_w 389
    //   215: invokespecial 372	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   218: aload 5
    //   220: invokevirtual 335	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   223: invokevirtual 376	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   226: ldc_w 378
    //   229: invokevirtual 376	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   232: aload 5
    //   234: invokevirtual 202	java/io/File:length	()J
    //   237: invokevirtual 392	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   240: invokevirtual 384	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   243: invokestatic 241	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   246: aload 5
    //   248: invokevirtual 335	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   251: areturn
    //   252: astore 8
    //   254: aload 7
    //   256: astore 6
    //   258: goto -60 -> 198
    //   261: astore 9
    //   263: aconst_null
    //   264: astore 6
    //   266: goto -83 -> 183
    //
    // Exception table:
    //   from	to	target	type
    //   68	74	152	java/lang/Exception
    //   93	112	177	java/lang/Exception
    //   112	117	177	java/lang/Exception
    //   162	174	177	java/lang/Exception
    //   77	88	196	finally
    //   183	188	196	finally
    //   93	112	252	finally
    //   112	117	252	finally
    //   162	174	252	finally
    //   77	88	261	java/lang/Exception
  }

  public static boolean saveExifToFile(String paramString, KXExifInterface paramKXExifInterface)
  {
    if ((paramKXExifInterface == null) || (TextUtils.isEmpty(paramString)))
      return true;
    try
    {
      KXExifInterface localKXExifInterface = new KXExifInterface(paramString);
      String str1 = paramKXExifInterface.getAttribute(KXExifInterface.TAG_ORIENTATION);
      if (str1 != null)
        localKXExifInterface.setAttribute(KXExifInterface.TAG_ORIENTATION, str1);
      String str2 = paramKXExifInterface.getAttribute(KXExifInterface.TAG_DATETIME);
      if (str2 != null)
        localKXExifInterface.setAttribute(KXExifInterface.TAG_DATETIME, str2);
      String str3 = paramKXExifInterface.getAttribute(KXExifInterface.TAG_MAKE);
      if (str3 != null)
        localKXExifInterface.setAttribute(KXExifInterface.TAG_MAKE, str3);
      String str4 = paramKXExifInterface.getAttribute(KXExifInterface.TAG_MODEL);
      if (str4 != null)
        localKXExifInterface.setAttribute(KXExifInterface.TAG_MODEL, str4);
      String str5 = paramKXExifInterface.getAttribute(KXExifInterface.TAG_FLASH);
      if (str5 != null)
        localKXExifInterface.setAttribute(KXExifInterface.TAG_FLASH, str5);
      String str6 = paramKXExifInterface.getAttribute(KXExifInterface.TAG_IMAGE_WIDTH);
      if (str6 != null)
        localKXExifInterface.setAttribute(KXExifInterface.TAG_IMAGE_WIDTH, str6);
      String str7 = paramKXExifInterface.getAttribute(KXExifInterface.TAG_IMAGE_LENGTH);
      if (str7 != null)
        localKXExifInterface.setAttribute(KXExifInterface.TAG_IMAGE_LENGTH, str7);
      String str8 = paramKXExifInterface.getAttribute(KXExifInterface.TAG_GPS_LATITUDE);
      if (str8 != null)
        localKXExifInterface.setAttribute(KXExifInterface.TAG_GPS_LATITUDE, str8);
      String str9 = paramKXExifInterface.getAttribute(KXExifInterface.TAG_GPS_LONGITUDE);
      if (str9 != null)
        localKXExifInterface.setAttribute(KXExifInterface.TAG_GPS_LONGITUDE, str9);
      String str10 = paramKXExifInterface.getAttribute(KXExifInterface.TAG_GPS_LATITUDE_REF);
      if (str10 != null)
        localKXExifInterface.setAttribute(KXExifInterface.TAG_GPS_LATITUDE_REF, str10);
      String str11 = paramKXExifInterface.getAttribute(KXExifInterface.TAG_GPS_LONGITUDE_REF);
      if (str11 != null)
        localKXExifInterface.setAttribute(KXExifInterface.TAG_GPS_LONGITUDE_REF, str11);
      String str12 = paramKXExifInterface.getAttribute(KXExifInterface.TAG_WHITE_BALANCE);
      if (str12 != null)
        localKXExifInterface.setAttribute(KXExifInterface.TAG_WHITE_BALANCE, str12);
      localKXExifInterface.saveAttributes();
      return true;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return false;
  }

  public static String saveOtherShapBitmapToFile(Context paramContext, Bitmap paramBitmap, String paramString, Bitmap.CompressFormat paramCompressFormat, int paramInt)
  {
    if (paramBitmap == null)
    {
      KXLog.d("saveBitmapToFile", "null == bmp");
      return "";
    }
    FileUtil.ensureEmptyFile(new File(paramString));
    return saveBitmapToFileWithAbsolutePath(paramContext, paramBitmap, paramString, paramCompressFormat, paramInt);
  }

  private void setCachePath(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    this.mPicCachePath = paramString;
    FileUtil.makeDirExist(paramString);
    LruFileCache.getInstance().getCacheDir(this.mPicCachePath, -1);
  }

  public void RemoveBitmapToHardCache(String paramString)
  {
    LruCache localLruCache1 = this.sHardBitmapCache;
    Bitmap localBitmap = null;
    if (localLruCache1 != null);
    synchronized (this.sHardBitmapCache)
    {
      localBitmap = (Bitmap)this.sHardBitmapCache.remove(paramString);
      this.mMapImageCache.remove(paramString);
      if ((localBitmap == null) || (localBitmap.isRecycled()));
    }
    synchronized (this.mMapImageCache)
    {
      this.mMapImageCache.put(paramString, new SoftReference(localBitmap));
      this.mMapImageCache.remove(paramString);
      return;
      localObject2 = finally;
      monitorexit;
      throw localObject2;
    }
  }

  public void addBitmapToHardCache(String paramString, Bitmap paramBitmap)
  {
    createHardBitmapCache();
    if (paramBitmap != null)
      synchronized (this.sHardBitmapCache)
      {
        this.sHardBitmapCache.put(paramString, paramBitmap);
        return;
      }
  }

  public void clear()
  {
    clearMemoryCache();
  }

  public boolean clearCache()
  {
    monitorenter;
    int j;
    int k;
    int m;
    int n;
    label99: int i1;
    while (true)
    {
      try
      {
        boolean bool = TextUtils.isEmpty(this.mPicCachePath);
        if (!bool)
          continue;
        i = 0;
        return i;
        File[] arrayOfFile = new File(this.mPicCachePath).listFiles();
        if ((arrayOfFile != null) && (arrayOfFile.length >= this.mFileCount))
        {
          j = arrayOfFile.length;
          k = j - this.mFileCount;
          m = 0;
          break;
          i = 0;
          if (n >= k)
            continue;
          FileUtil.deleteFileWithoutCheckReturnValue(arrayOfFile[n]);
          n++;
          continue;
          if (arrayOfFile[m].lastModified() <= arrayOfFile[i1].lastModified())
            continue;
          File localFile = arrayOfFile[m];
          arrayOfFile[m] = arrayOfFile[i1];
          arrayOfFile[i1] = localFile;
          i1++;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("ImageUtil", "clearCache", localException);
        i = 0;
        continue;
      }
      finally
      {
        monitorexit;
      }
      int i = 1;
    }
    while (true)
    {
      if (m >= k)
      {
        n = 0;
        break;
      }
      i1 = m + 1;
      if (i1 < j)
        break label99;
      m++;
    }
  }

  public void clearMemoryCache()
  {
    monitorenter;
    try
    {
      if (this.sHardBitmapCache != null)
      {
        this.sHardBitmapCache.evictAll();
        this.sHardBitmapCache = null;
      }
      this.mMapImageCache.clear();
      return;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public Bitmap createSafeImage(String paramString)
  {
    monitorenter;
    try
    {
      Bitmap localBitmap1 = loadMemoryCacheImage(paramString);
      if (localBitmap1 != null);
      Bitmap localBitmap2;
      for (Object localObject2 = localBitmap1; ; localObject2 = localBitmap2)
      {
        return localObject2;
        localBitmap2 = getCacheBmp(paramString);
        addBitmapToHardCache(paramString, localBitmap2);
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject1;
  }

  public Bitmap decodeBitmapFromRes(int paramInt1, int paramInt2, int paramInt3)
  {
    TypedValue localTypedValue = new TypedValue();
    InputStream localInputStream = mContext.getResources().openRawResource(paramInt1, localTypedValue);
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inJustDecodeBounds = true;
    BitmapFactory.decodeResource(mContext.getResources(), paramInt1, localOptions);
    localOptions.inSampleSize = calculateInSize(localOptions, paramInt2, paramInt3);
    localOptions.inPurgeable = true;
    localOptions.inInputShareable = true;
    localOptions.inJustDecodeBounds = false;
    localOptions.inTargetDensity = localTypedValue.density;
    try
    {
      Bitmap localBitmap = BitmapFactory.decodeStream(localInputStream, null, localOptions);
      return localBitmap;
    }
    catch (Exception localException)
    {
      return null;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
    }
    return null;
  }

  public void deleteFileAndCacheImage(String paramString)
  {
    monitorenter;
    try
    {
      if (!TextUtils.isEmpty(this.mPicCachePath))
      {
        boolean bool = TextUtils.isEmpty(paramString);
        if (!bool)
          break label24;
      }
      while (true)
      {
        return;
        label24: String str = getFileByUrl(paramString);
        FileUtil.deleteFileWithoutCheckReturnValue(str);
        RemoveBitmapToHardCache(paramString);
        LruFileCache.getInstance().removeFile(str);
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public Bitmap getBitmapToHardCache(String paramString)
  {
    if ((paramString == null) || (this.sHardBitmapCache == null))
      return null;
    synchronized (this.sHardBitmapCache)
    {
      Bitmap localBitmap = (Bitmap)this.sHardBitmapCache.get(paramString);
      return localBitmap;
    }
  }

  public String getCacheBmpPath(String paramString)
  {
    if ((TextUtils.isEmpty(this.mPicCachePath)) || (TextUtils.isEmpty(paramString)))
      return "";
    return getFileByUrl(paramString);
  }

  public String getCachePath()
  {
    return this.mPicCachePath;
  }

  public Context getContext()
  {
    return mContext;
  }

  public String getCoverBmpPath(String paramString)
  {
    if ((TextUtils.isEmpty(this.mPicCachePath)) || (TextUtils.isEmpty(paramString)))
      return "";
    return getFileByUrl(paramString);
  }

  public String getFileByUrl(String paramString)
  {
    return this.mPicCachePath + StringUtil.MD5Encode(paramString) + this.mFileExName;
  }

  public Bitmap getLoadingBitmap(int paramInt1, int paramInt2, int paramInt3)
  {
    String str = String.valueOf(paramInt1);
    Object localObject = getBitmapToHardCache(str);
    if (((localObject != null) && (!((Bitmap)localObject).isRecycled())) || (paramInt1 > 0));
    try
    {
      Bitmap localBitmap = decodeBitmapFromRes(paramInt1, paramInt2, paramInt3);
      localObject = localBitmap;
      addBitmapToHardCache(str, (Bitmap)localObject);
      return localObject;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      while (true)
      {
        System.gc();
        localObject = decodeBitmapFromRes(paramInt1, paramInt2 / 2, paramInt3 / 2);
      }
    }
  }

  public Bitmap getOtherShapBitmap(Bitmap paramBitmap, String paramString1, String paramString2)
  {
    if (paramBitmap == null)
      return null;
    String str = getOtherShapFileByUrl(paramString1, paramString2);
    Bitmap localBitmap = ImageUtil.getOtherShapeBitmap(paramBitmap, paramString2);
    saveOtherShapBitmapToFile(mContext, localBitmap, str, Bitmap.CompressFormat.PNG, 100);
    return localBitmap;
  }

  public String getOtherShapFileByUrl(String paramString1, String paramString2)
  {
    return this.mPicCachePath + StringUtil.MD5Encode(new StringBuilder(String.valueOf(paramString1)).append(paramString2).toString()) + this.mFileExName;
  }

  public String getOtherShapUrl(String paramString1, String paramString2)
  {
    return paramString1 + paramString2;
  }

  public boolean hasCacheBmp(String paramString)
  {
    if ((TextUtils.isEmpty(this.mPicCachePath)) || (TextUtils.isEmpty(paramString)));
    File localFile;
    do
    {
      return false;
      String str = getFileByUrl(paramString);
      localFile = LruFileCache.getInstance().getFile(str);
    }
    while ((!localFile.exists()) || (localFile.length() == 0L));
    return true;
  }

  public boolean isCacheFileExists(String paramString)
  {
    if ((TextUtils.isEmpty(this.mPicCachePath)) || (TextUtils.isEmpty(paramString)));
    do
      return false;
    while (!new File(getFileByUrl(paramString)).exists());
    return true;
  }

  public Bitmap loadMemoryCacheImage(String paramString)
  {
    monitorenter;
    try
    {
      boolean bool = TextUtils.isEmpty(paramString);
      Object localObject2;
      if (bool)
        localObject2 = null;
      while (true)
      {
        return localObject2;
        localObject2 = getBitmapToHardCache(paramString);
        if (localObject2 != null)
          continue;
        Bitmap localBitmap = null;
        synchronized (this.mMapImageCache)
        {
          SoftReference localSoftReference = (SoftReference)this.mMapImageCache.get(paramString);
          if ((localSoftReference == null) || ((localSoftReference != null) && (localSoftReference.get() == null)) || ((localSoftReference != null) && (localSoftReference.get() != null) && (((Bitmap)localSoftReference.get()).isRecycled())))
          {
            this.mMapImageCache.remove(paramString);
            localObject2 = localBitmap;
            continue;
          }
          localBitmap = (Bitmap)localSoftReference.get();
          addBitmapToHardCache(paramString, localBitmap);
        }
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject1;
  }

  public void setContext(Context paramContext)
  {
    mContext = paramContext;
    createHardBitmapCache();
    String str2;
    if ("mounted".equals(Environment.getExternalStorageState()))
    {
      str2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/kaixin001/";
      if (!FileUtil.makeDirExist(str2))
        return;
    }
    for (String str1 = str2 + ".data/"; ; str1 = mContext.getCacheDir().getAbsolutePath() + "/pic/")
    {
      setCachePath(str1);
      return;
    }
  }

  public void setMaxFileCount(int paramInt)
  {
    this.mFileCount = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.ImageCache
 * JD-Core Version:    0.6.0
 */