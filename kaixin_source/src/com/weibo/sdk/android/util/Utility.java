package com.weibo.sdk.android.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboParameters;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Utility
{
  private static byte[] decodes;
  private static char[] encodes = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

  static
  {
    decodes = new byte[256];
  }

  private static boolean __createNewFile(File paramFile)
  {
    if (paramFile == null)
      return false;
    makesureParentExist(paramFile);
    if (paramFile.exists())
      delete(paramFile);
    try
    {
      boolean bool = paramFile.createNewFile();
      return bool;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return false;
  }

  private static void createNewFile(File paramFile)
  {
    if (paramFile == null);
    do
      return;
    while (__createNewFile(paramFile));
    throw new RuntimeException(paramFile.getAbsolutePath() + " doesn't be created!");
  }

  public static byte[] decodeBase62(String paramString)
  {
    if (paramString == null)
      return null;
    char[] arrayOfChar = paramString.toCharArray();
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(paramString.toCharArray().length);
    int i = 0;
    int j = 0;
    int k = 0;
    if (k >= arrayOfChar.length)
      return localByteArrayOutputStream.toByteArray();
    int m = arrayOfChar[k];
    int n;
    if (m == 105)
    {
      k++;
      n = arrayOfChar[k];
      if (n == 97)
        m = 105;
    }
    else
    {
      label77: j = j << 6 | decodes[m];
      i += 6;
    }
    while (true)
    {
      if (i <= 7)
      {
        k++;
        break;
        if (n == 98)
        {
          m = 43;
          break label77;
        }
        if (n == 99)
        {
          m = 47;
          break label77;
        }
        k--;
        m = arrayOfChar[k];
        break label77;
      }
      i -= 8;
      localByteArrayOutputStream.write(j >> i);
      j &= -1 + (1 << i);
    }
  }

  public static Bundle decodeUrl(String paramString)
  {
    Bundle localBundle = new Bundle();
    String[] arrayOfString1;
    int i;
    if (paramString != null)
    {
      arrayOfString1 = paramString.split("&");
      i = arrayOfString1.length;
    }
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return localBundle;
      String[] arrayOfString2 = arrayOfString1[j].split("=");
      localBundle.putString(URLDecoder.decode(arrayOfString2[0]), URLDecoder.decode(arrayOfString2[1]));
    }
  }

  private static void delete(File paramFile)
  {
    if ((paramFile != null) && (paramFile.exists()) && (!paramFile.delete()))
      throw new RuntimeException(paramFile.getAbsolutePath() + " doesn't be deleted!");
  }

  private static boolean deleteDependon(File paramFile, int paramInt)
  {
    int i = 1;
    if (paramInt < 1)
      paramInt = 5;
    boolean bool = false;
    if (paramFile != null);
    while (true)
    {
      if ((bool) || (i > paramInt) || (!paramFile.isFile()) || (!paramFile.exists()))
        return bool;
      bool = paramFile.delete();
      if (bool)
        continue;
      i++;
    }
  }

  private static boolean deleteDependon(String paramString)
  {
    return deleteDependon(paramString, 0);
  }

  private static boolean deleteDependon(String paramString, int paramInt)
  {
    if (TextUtils.isEmpty(paramString))
      return false;
    return deleteDependon(new File(paramString), paramInt);
  }

  private static boolean doesExisted(File paramFile)
  {
    return (paramFile != null) && (paramFile.exists());
  }

  private static boolean doesExisted(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return false;
    return doesExisted(new File(paramString));
  }

  public static String encodeBase62(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer(2 * paramArrayOfByte.length);
    int i = 0;
    int j = 0;
    int k = 0;
    char c2;
    Object localObject2;
    if (k >= paramArrayOfByte.length)
      if (i > 0)
      {
        c2 = encodes[(j << 6 - i)];
        if (c2 != 'i')
          break label176;
        localObject2 = "ia";
      }
    while (true)
    {
      localStringBuffer.append(localObject2);
      return localStringBuffer.toString();
      j = j << 8 | 0xFF & paramArrayOfByte[k];
      i += 8;
      if (i <= 5)
      {
        k++;
        break;
      }
      char[] arrayOfChar = encodes;
      i -= 6;
      char c1 = arrayOfChar[(j >> i)];
      Object localObject1;
      if (c1 == 'i')
        localObject1 = "ia";
      while (true)
      {
        localStringBuffer.append(localObject1);
        j &= -1 + (1 << i);
        break;
        if (c1 == '+')
        {
          localObject1 = "ib";
          continue;
        }
        if (c1 == '/')
        {
          localObject1 = "ic";
          continue;
        }
        localObject1 = Character.valueOf(c1);
      }
      label176: if (c2 == '+')
      {
        localObject2 = "ib";
        continue;
      }
      if (c2 == '/')
      {
        localObject2 = "ic";
        continue;
      }
      localObject2 = Character.valueOf(c2);
    }
  }

  public static String encodeParameters(WeiboParameters paramWeiboParameters)
  {
    if ((paramWeiboParameters == null) || (isBundleEmpty(paramWeiboParameters)))
      return "";
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 0;
    int j = 0;
    while (true)
    {
      if (j >= paramWeiboParameters.size())
        return localStringBuilder.toString();
      String str = paramWeiboParameters.getKey(j);
      if (i != 0)
        localStringBuilder.append("&");
      try
      {
        localStringBuilder.append(URLEncoder.encode(str, "UTF-8")).append("=").append(URLEncoder.encode(paramWeiboParameters.getValue(str), "UTF-8"));
        label88: i++;
        j++;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        break label88;
      }
    }
  }

  public static String encodeUrl(WeiboParameters paramWeiboParameters)
  {
    if (paramWeiboParameters == null)
      return "";
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 1;
    int j = 0;
    if (j >= paramWeiboParameters.size())
      return localStringBuilder.toString();
    if (i != 0)
    {
      i = 0;
      label38: String str = paramWeiboParameters.getKey(j);
      if (paramWeiboParameters.getValue(str) != null)
        break label98;
      Log.i("encodeUrl", "key:" + str + " 's value is null");
    }
    while (true)
    {
      j++;
      break;
      localStringBuilder.append("&");
      break label38;
      label98: localStringBuilder.append(URLEncoder.encode(paramWeiboParameters.getKey(j)) + "=" + URLEncoder.encode(paramWeiboParameters.getValue(j)));
    }
  }

  private static boolean isBundleEmpty(WeiboParameters paramWeiboParameters)
  {
    return (paramWeiboParameters == null) || (paramWeiboParameters.size() == 0);
  }

  public static boolean isWifi(Context paramContext)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    return (localNetworkInfo != null) && (localNetworkInfo.getType() == 1);
  }

  private static void makesureFileExist(File paramFile)
  {
    if (paramFile == null);
    do
      return;
    while (paramFile.exists());
    makesureParentExist(paramFile);
    createNewFile(paramFile);
  }

  private static void makesureFileExist(String paramString)
  {
    if (paramString == null)
      return;
    makesureFileExist(new File(paramString));
  }

  private static void makesureParentExist(File paramFile)
  {
    if (paramFile == null);
    File localFile;
    do
    {
      return;
      localFile = paramFile.getParentFile();
    }
    while ((localFile == null) || (localFile.exists()));
    mkdirs(localFile);
  }

  private static void mkdirs(File paramFile)
  {
    if (paramFile == null);
    do
      return;
    while ((paramFile.exists()) || (paramFile.mkdirs()));
    throw new RuntimeException("fail to make " + paramFile.getAbsolutePath());
  }

  public static Bundle parseUrl(String paramString)
  {
    try
    {
      URL localURL = new URL(paramString);
      Bundle localBundle = decodeUrl(localURL.getQuery());
      localBundle.putAll(decodeUrl(localURL.getRef()));
      return localBundle;
    }
    catch (MalformedURLException localMalformedURLException)
    {
    }
    return new Bundle();
  }

  public static void showAlert(Context paramContext, String paramString1, String paramString2)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramContext);
    localBuilder.setTitle(paramString1);
    localBuilder.setMessage(paramString2);
    localBuilder.create().show();
  }

  public static final class UploadImageUtils
  {
    private static void revitionImageSize(String paramString, int paramInt1, int paramInt2)
      throws IOException
    {
      if (paramInt1 <= 0)
        throw new IllegalArgumentException("size must be greater than 0!");
      if (!Utility.access$0(paramString))
      {
        if (paramString == null)
          paramString = "null";
        throw new FileNotFoundException(paramString);
      }
      if (!BitmapHelper.verifyBitmap(paramString))
        throw new IOException("");
      FileInputStream localFileInputStream = new FileInputStream(paramString);
      BitmapFactory.Options localOptions = new BitmapFactory.Options();
      localOptions.inJustDecodeBounds = true;
      BitmapFactory.decodeStream(localFileInputStream, null, localOptions);
      Bitmap localBitmap;
      label177: FileOutputStream localFileOutputStream;
      try
      {
        localFileInputStream.close();
        i = 0;
        if ((localOptions.outWidth >> i <= paramInt1) && (localOptions.outHeight >> i <= paramInt1))
        {
          localOptions.inSampleSize = (int)Math.pow(2.0D, i);
          localOptions.inJustDecodeBounds = false;
          localBitmap = safeDecodeBimtapFile(paramString, localOptions);
          if (localBitmap != null)
            break label177;
          throw new IOException("Bitmap decode error!");
        }
      }
      catch (Exception localException1)
      {
        while (true)
        {
          int i;
          localException1.printStackTrace();
          continue;
          i++;
        }
        Utility.access$1(paramString);
        Utility.access$2(paramString);
        localFileOutputStream = new FileOutputStream(paramString);
        if (localOptions == null)
          break label245;
      }
      if ((localOptions.outMimeType != null) && (localOptions.outMimeType.contains("png")))
        localBitmap.compress(Bitmap.CompressFormat.PNG, paramInt2, localFileOutputStream);
      try
      {
        while (true)
        {
          localFileOutputStream.close();
          localBitmap.recycle();
          return;
          label245: localBitmap.compress(Bitmap.CompressFormat.JPEG, paramInt2, localFileOutputStream);
        }
      }
      catch (Exception localException2)
      {
        while (true)
          localException2.printStackTrace();
      }
    }

    // ERROR //
    private static void revitionImageSizeHD(String paramString, int paramInt1, int paramInt2)
      throws IOException
    {
      // Byte code:
      //   0: iload_1
      //   1: ifgt +13 -> 14
      //   4: new 16	java/lang/IllegalArgumentException
      //   7: dup
      //   8: ldc 18
      //   10: invokespecial 21	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
      //   13: athrow
      //   14: aload_0
      //   15: invokestatic 27	com/weibo/sdk/android/util/Utility:access$0	(Ljava/lang/String;)Z
      //   18: ifne +23 -> 41
      //   21: aload_0
      //   22: ifnonnull +6 -> 28
      //   25: ldc 29
      //   27: astore_0
      //   28: new 31	java/io/FileNotFoundException
      //   31: dup
      //   32: aload_0
      //   33: invokespecial 32	java/io/FileNotFoundException:<init>	(Ljava/lang/String;)V
      //   36: astore 21
      //   38: aload 21
      //   40: athrow
      //   41: aload_0
      //   42: invokestatic 37	com/weibo/sdk/android/util/BitmapHelper:verifyBitmap	(Ljava/lang/String;)Z
      //   45: ifne +13 -> 58
      //   48: new 12	java/io/IOException
      //   51: dup
      //   52: ldc 39
      //   54: invokespecial 40	java/io/IOException:<init>	(Ljava/lang/String;)V
      //   57: athrow
      //   58: iload_1
      //   59: iconst_2
      //   60: imul
      //   61: istore_3
      //   62: new 42	java/io/FileInputStream
      //   65: dup
      //   66: aload_0
      //   67: invokespecial 43	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
      //   70: astore 4
      //   72: new 45	android/graphics/BitmapFactory$Options
      //   75: dup
      //   76: invokespecial 46	android/graphics/BitmapFactory$Options:<init>	()V
      //   79: astore 5
      //   81: aload 5
      //   83: iconst_1
      //   84: putfield 50	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
      //   87: aload 4
      //   89: aconst_null
      //   90: aload 5
      //   92: invokestatic 56	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
      //   95: pop
      //   96: aload 4
      //   98: invokevirtual 59	java/io/FileInputStream:close	()V
      //   101: iconst_0
      //   102: istore 8
      //   104: aload 5
      //   106: getfield 63	android/graphics/BitmapFactory$Options:outWidth	I
      //   109: iload 8
      //   111: ishr
      //   112: iload_3
      //   113: if_icmpgt +69 -> 182
      //   116: aload 5
      //   118: getfield 66	android/graphics/BitmapFactory$Options:outHeight	I
      //   121: iload 8
      //   123: ishr
      //   124: iload_3
      //   125: if_icmpgt +57 -> 182
      //   128: aload 5
      //   130: ldc2_w 67
      //   133: iload 8
      //   135: i2d
      //   136: invokestatic 74	java/lang/Math:pow	(DD)D
      //   139: d2i
      //   140: putfield 77	android/graphics/BitmapFactory$Options:inSampleSize	I
      //   143: aload 5
      //   145: iconst_0
      //   146: putfield 50	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
      //   149: aload_0
      //   150: aload 5
      //   152: invokestatic 81	com/weibo/sdk/android/util/Utility$UploadImageUtils:safeDecodeBimtapFile	(Ljava/lang/String;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
      //   155: astore 9
      //   157: aload 9
      //   159: ifnonnull +29 -> 188
      //   162: new 12	java/io/IOException
      //   165: dup
      //   166: ldc 83
      //   168: invokespecial 40	java/io/IOException:<init>	(Ljava/lang/String;)V
      //   171: athrow
      //   172: astore 7
      //   174: aload 7
      //   176: invokevirtual 86	java/lang/Exception:printStackTrace	()V
      //   179: goto -78 -> 101
      //   182: iinc 8 1
      //   185: goto -81 -> 104
      //   188: aload_0
      //   189: invokestatic 89	com/weibo/sdk/android/util/Utility:access$1	(Ljava/lang/String;)Z
      //   192: pop
      //   193: aload_0
      //   194: invokestatic 92	com/weibo/sdk/android/util/Utility:access$2	(Ljava/lang/String;)V
      //   197: aload 9
      //   199: invokevirtual 133	android/graphics/Bitmap:getWidth	()I
      //   202: aload 9
      //   204: invokevirtual 136	android/graphics/Bitmap:getHeight	()I
      //   207: if_icmple +176 -> 383
      //   210: aload 9
      //   212: invokevirtual 133	android/graphics/Bitmap:getWidth	()I
      //   215: istore 11
      //   217: iload_1
      //   218: i2f
      //   219: iload 11
      //   221: i2f
      //   222: fdiv
      //   223: fstore 12
      //   225: fload 12
      //   227: fconst_1
      //   228: fcmpg
      //   229: ifge +95 -> 324
      //   232: fload 12
      //   234: aload 9
      //   236: invokevirtual 133	android/graphics/Bitmap:getWidth	()I
      //   239: i2f
      //   240: fmul
      //   241: f2i
      //   242: fload 12
      //   244: aload 9
      //   246: invokevirtual 136	android/graphics/Bitmap:getHeight	()I
      //   249: i2f
      //   250: fmul
      //   251: f2i
      //   252: getstatic 142	android/graphics/Bitmap$Config:ARGB_8888	Landroid/graphics/Bitmap$Config;
      //   255: invokestatic 146	android/graphics/Bitmap:createBitmap	(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;
      //   258: astore 18
      //   260: aload 18
      //   262: ifnonnull +8 -> 270
      //   265: aload 9
      //   267: invokevirtual 123	android/graphics/Bitmap:recycle	()V
      //   270: new 148	android/graphics/Canvas
      //   273: dup
      //   274: aload 18
      //   276: invokespecial 151	android/graphics/Canvas:<init>	(Landroid/graphics/Bitmap;)V
      //   279: astore 19
      //   281: new 153	android/graphics/Matrix
      //   284: dup
      //   285: invokespecial 154	android/graphics/Matrix:<init>	()V
      //   288: astore 20
      //   290: aload 20
      //   292: fload 12
      //   294: fload 12
      //   296: invokevirtual 158	android/graphics/Matrix:setScale	(FF)V
      //   299: aload 19
      //   301: aload 9
      //   303: aload 20
      //   305: new 160	android/graphics/Paint
      //   308: dup
      //   309: invokespecial 161	android/graphics/Paint:<init>	()V
      //   312: invokevirtual 165	android/graphics/Canvas:drawBitmap	(Landroid/graphics/Bitmap;Landroid/graphics/Matrix;Landroid/graphics/Paint;)V
      //   315: aload 9
      //   317: invokevirtual 123	android/graphics/Bitmap:recycle	()V
      //   320: aload 18
      //   322: astore 9
      //   324: new 94	java/io/FileOutputStream
      //   327: dup
      //   328: aload_0
      //   329: invokespecial 95	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
      //   332: astore 13
      //   334: aload 5
      //   336: ifnull +75 -> 411
      //   339: aload 5
      //   341: getfield 99	android/graphics/BitmapFactory$Options:outMimeType	Ljava/lang/String;
      //   344: ifnull +67 -> 411
      //   347: aload 5
      //   349: getfield 99	android/graphics/BitmapFactory$Options:outMimeType	Ljava/lang/String;
      //   352: ldc 101
      //   354: invokevirtual 107	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
      //   357: ifeq +54 -> 411
      //   360: aload 9
      //   362: getstatic 113	android/graphics/Bitmap$CompressFormat:PNG	Landroid/graphics/Bitmap$CompressFormat;
      //   365: iload_2
      //   366: aload 13
      //   368: invokevirtual 119	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
      //   371: pop
      //   372: aload 13
      //   374: invokevirtual 120	java/io/FileOutputStream:close	()V
      //   377: aload 9
      //   379: invokevirtual 123	android/graphics/Bitmap:recycle	()V
      //   382: return
      //   383: aload 9
      //   385: invokevirtual 136	android/graphics/Bitmap:getHeight	()I
      //   388: istore 11
      //   390: goto -173 -> 217
      //   393: astore 17
      //   395: invokestatic 170	java/lang/System:gc	()V
      //   398: ldc2_w 171
      //   401: fload 12
      //   403: f2d
      //   404: dmul
      //   405: d2f
      //   406: fstore 12
      //   408: goto -176 -> 232
      //   411: aload 9
      //   413: getstatic 126	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
      //   416: iload_2
      //   417: aload 13
      //   419: invokevirtual 119	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
      //   422: pop
      //   423: goto -51 -> 372
      //   426: astore 15
      //   428: aload 15
      //   430: invokevirtual 86	java/lang/Exception:printStackTrace	()V
      //   433: goto -56 -> 377
      //
      // Exception table:
      //   from	to	target	type
      //   96	101	172	java/lang/Exception
      //   232	260	393	java/lang/OutOfMemoryError
      //   372	377	426	java/lang/Exception
    }

    public static boolean revitionPostImageSize(String paramString)
    {
      try
      {
        if (Weibo.isWifi)
          revitionImageSizeHD(paramString, 1600, 75);
        else
          revitionImageSize(paramString, 1024, 75);
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        return false;
      }
      return true;
    }

    private static Bitmap safeDecodeBimtapFile(String paramString, BitmapFactory.Options paramOptions)
    {
      BitmapFactory.Options localOptions = paramOptions;
      if (localOptions == null)
      {
        localOptions = new BitmapFactory.Options();
        localOptions.inSampleSize = 1;
      }
      Object localObject1 = null;
      int i = 0;
      Object localObject2 = null;
      while (true)
      {
        if (i >= 5)
          return localObject1;
        try
        {
          localObject3 = new FileInputStream(paramString);
        }
        catch (FileNotFoundException localFileNotFoundException2)
        {
          try
          {
            Bitmap localBitmap = BitmapFactory.decodeStream((InputStream)localObject3, null, paramOptions);
            localObject1 = localBitmap;
            try
            {
              ((FileInputStream)localObject3).close();
              return localObject1;
            }
            catch (IOException localIOException2)
            {
              localIOException2.printStackTrace();
              return localObject1;
            }
          }
          catch (OutOfMemoryError localOutOfMemoryError1)
          {
            localOutOfMemoryError1.printStackTrace();
            localOptions.inSampleSize = (2 * localOptions.inSampleSize);
            try
            {
              ((FileInputStream)localObject3).close();
              i++;
              localObject2 = localObject3;
            }
            catch (IOException localIOException1)
            {
              while (true)
                localIOException1.printStackTrace();
            }
            localFileNotFoundException2 = localFileNotFoundException2;
            return localObject1;
          }
          catch (FileNotFoundException localFileNotFoundException1)
          {
            return localObject1;
          }
        }
        catch (OutOfMemoryError localOutOfMemoryError2)
        {
          while (true)
            Object localObject3 = localObject2;
        }
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.weibo.sdk.android.util.Utility
 * JD-Core Version:    0.6.0
 */