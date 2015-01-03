package com.tencent.sdkutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class AsynScaleCompressImage
{
  private static final String TAG = "AsynScaleCompressImage";
  private static final int UNCONSTRAINED = -1;
  private static final int UOLOAD_QZONE_IMAGE_MAX_SHORT_SIDE_LENGTH = 640;
  private static final int UPLOAD_IMAGE_MAX_LENGTH = 140;
  private static final int UPLOAD_IMAGE_MSG_ERROR = 102;
  private static final int UPLOAD_IMAGE_MSG_OK = 101;
  private static final int UPLOAD_QZONE_IMAGE_MAX_LONG_SIDE_LENGTH = 10000;

  public static final void batchScaleCompressImage(Context paramContext, ArrayList<String> paramArrayList, AsynLoadImg.AsynLoadImgBack paramAsynLoadImgBack)
  {
    if (paramArrayList == null)
    {
      paramAsynLoadImgBack.saved(1, null);
      return;
    }
    if (!Util.checkSd())
    {
      paramAsynLoadImgBack.saved(2, null);
      return;
    }
    new Thread(new AsynScaleCompressImage.4(paramArrayList, new AsynScaleCompressImage.3(paramContext.getMainLooper(), paramAsynLoadImgBack))).start();
  }

  protected static final String compressBitmap(Bitmap paramBitmap, String paramString1, String paramString2)
  {
    File localFile1 = new File(paramString1);
    if (!localFile1.exists())
      localFile1.mkdirs();
    String str = paramString1 + paramString2;
    File localFile2 = new File(str);
    if (localFile2.exists())
      localFile2.delete();
    if (paramBitmap != null);
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(localFile2);
      paramBitmap.compress(Bitmap.CompressFormat.JPEG, 80, localFileOutputStream);
      localFileOutputStream.flush();
      localFileOutputStream.close();
      paramBitmap.recycle();
      return str;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
      return null;
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }

  private static int computeInitialSampleSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
  {
    double d1 = paramOptions.outWidth;
    double d2 = paramOptions.outHeight;
    int i;
    int j;
    if (paramInt2 == -1)
    {
      i = 1;
      if (paramInt1 != -1)
        break label60;
      j = 128;
      label31: if (j >= i)
        break label84;
    }
    label60: label84: 
    do
    {
      return i;
      i = (int)Math.ceil(Math.sqrt(d1 * d2 / paramInt2));
      break;
      j = (int)Math.min(Math.floor(d1 / paramInt1), Math.floor(d2 / paramInt1));
      break label31;
      if ((paramInt2 == -1) && (paramInt1 == -1))
        return 1;
    }
    while (paramInt1 == -1);
    return j;
  }

  public static final int computeSampleSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
  {
    int i = computeInitialSampleSize(paramOptions, paramInt1, paramInt2);
    if (i <= 8)
    {
      j = 1;
      while (j < i)
        j <<= 1;
    }
    int j = 8 * ((i + 7) / 8);
    return j;
  }

  private static final boolean isBitMapNeedToCompress(String paramString, int paramInt1, int paramInt2)
  {
    if (TextUtils.isEmpty(paramString))
      return false;
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(paramString, localOptions);
    int i = localOptions.outWidth;
    int j = localOptions.outHeight;
    if ((localOptions.mCancel) || (localOptions.outWidth == -1) || (localOptions.outHeight == -1))
      return false;
    int k;
    if (i > j)
    {
      k = i;
      if (i >= j)
        break label147;
    }
    while (true)
    {
      Log.d("AsynScaleCompressImage", "longSide=" + k + "shortSide=" + i);
      localOptions.inPreferredConfig = Bitmap.Config.RGB_565;
      if ((k <= paramInt2) && (i <= paramInt1))
        break label154;
      return true;
      k = j;
      break;
      label147: i = j;
    }
    label154: return false;
  }

  private static Bitmap scaleBitmap(Bitmap paramBitmap, int paramInt)
  {
    Matrix localMatrix = new Matrix();
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    if (i > j);
    while (true)
    {
      float f = paramInt / i;
      localMatrix.postScale(f, f);
      return Bitmap.createBitmap(paramBitmap, 0, 0, paramBitmap.getWidth(), paramBitmap.getHeight(), localMatrix, true);
      i = j;
    }
  }

  public static final Bitmap scaleBitmap(String paramString, int paramInt)
  {
    Bitmap localBitmap;
    if (TextUtils.isEmpty(paramString))
      localBitmap = null;
    while (true)
    {
      return localBitmap;
      BitmapFactory.Options localOptions = new BitmapFactory.Options();
      localOptions.inJustDecodeBounds = true;
      BitmapFactory.decodeFile(paramString, localOptions);
      int i = localOptions.outWidth;
      int j = localOptions.outHeight;
      if ((localOptions.mCancel) || (localOptions.outWidth == -1) || (localOptions.outHeight == -1))
        return null;
      if (i > j);
      while (true)
      {
        localOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        if (i > paramInt)
          localOptions.inSampleSize = computeSampleSize(localOptions, -1, paramInt * paramInt);
        localOptions.inJustDecodeBounds = false;
        localBitmap = BitmapFactory.decodeFile(paramString, localOptions);
        if (localBitmap != null)
          break;
        return null;
        i = j;
      }
      int k = localOptions.outWidth;
      int m = localOptions.outHeight;
      if (k > m);
      while (k > paramInt)
      {
        return scaleBitmap(localBitmap, paramInt);
        k = m;
      }
    }
  }

  public static final void scaleCompressImage(Context paramContext, String paramString, AsynLoadImg.AsynLoadImgBack paramAsynLoadImgBack)
  {
    if (TextUtils.isEmpty(paramString))
    {
      paramAsynLoadImgBack.saved(1, null);
      return;
    }
    if (!Util.checkSd())
    {
      paramAsynLoadImgBack.saved(2, null);
      return;
    }
    new Thread(new AsynScaleCompressImage.2(paramString, new AsynScaleCompressImage.1(paramContext.getMainLooper(), paramAsynLoadImgBack))).start();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.AsynScaleCompressImage
 * JD-Core Version:    0.6.0
 */