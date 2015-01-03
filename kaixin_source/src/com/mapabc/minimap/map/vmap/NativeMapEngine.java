package com.mapabc.minimap.map.vmap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;
import android.util.DisplayMetrics;
import com.amap.mapapi.core.o;
import java.io.File;

public class NativeMapEngine
{
  public static final int ICON_HEIGHT = 12;
  public static final int ICON_WIDTH = 12;
  public static final int MAX_ICON_SIZE = 128;
  public static final int MAX_LABELAINE = 7;
  Bitmap[] a = new Bitmap[''];
  int b = 0;

  static
  {
    try
    {
      System.loadLibrary("minimapv320");
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public NativeMapEngine(Context paramContext)
  {
    if (!Environment.getExternalStorageState().equals("mounted"));
    File localFile2;
    for (String str = paramContext.getCacheDir().toString() + "/"; ; str = localFile2.toString() + "/")
    {
      this.b = nativeCreate(str);
      setBitmapCacheMaxSize(256);
      setVecotormapCacheMaxSize(200);
      return;
      File localFile1 = new File(Environment.getExternalStorageDirectory(), "Amap");
      if (!localFile1.exists())
        localFile1.mkdir();
      localFile2 = new File(localFile1, "mini_mapv2");
      if (localFile2.exists())
        continue;
      localFile2.mkdir();
    }
  }

  private static native void nativeClearBackground(int paramInt);

  private static native int nativeCreate(String paramString);

  private static native void nativeFillBitmapBufferData(int paramInt, String paramString, byte[] paramArrayOfByte);

  private static native void nativeFinalizer(int paramInt);

  private static native int nativeGetBKColor(int paramInt1, int paramInt2);

  private static native int nativeGetBitmapCacheSize(int paramInt);

  private static native byte[] nativeGetGridData(int paramInt, String paramString);

  private static native boolean nativeHasBitMapData(int paramInt, String paramString);

  private static native boolean nativeHasGridData(int paramInt, String paramString);

  private static native void nativePutBitmapData(int paramInt1, String paramString, byte[] paramArrayOfByte, int paramInt2, int paramInt3);

  private static native void nativePutGridData(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3);

  private static native void nativeRemoveBitmapData(int paramInt1, String paramString, int paramInt2);

  private static native void nativeSetBackgroundImageData(int paramInt, byte[] paramArrayOfByte);

  private static native void nativeSetBitmapCacheMaxSize(int paramInt1, int paramInt2);

  private static native void nativeSetIconData(int paramInt1, int paramInt2, byte[] paramArrayOfByte);

  private static native void nativeSetStyleData(int paramInt, byte[] paramArrayOfByte);

  private static native void nativeSetVectormapCacheMaxSize(int paramInt1, int paramInt2);

  public void clearBackground()
  {
    nativeClearBackground(this.b);
  }

  public void destory()
  {
    if (this.b == 0);
    while (true)
    {
      return;
      nativeFinalizer(this.b);
      this.b = 0;
      for (int i = 1; i < 53; i++)
      {
        if (this.a[i] == null)
          continue;
        this.a[i].recycle();
        this.a[i] = null;
      }
    }
  }

  public void fillBitmapBufferData(String paramString, byte[] paramArrayOfByte)
  {
    nativeFillBitmapBufferData(this.b, paramString, paramArrayOfByte);
  }

  protected void finalize()
    throws Throwable
  {
    destory();
  }

  public int getBKColor(int paramInt)
  {
    return nativeGetBKColor(this.b, paramInt);
  }

  public int getBitmapCacheSize()
  {
    return nativeGetBitmapCacheSize(this.b);
  }

  public byte[] getGridData(String paramString)
  {
    return nativeGetGridData(this.b, paramString);
  }

  public Bitmap getIconBitmap(int paramInt)
  {
    return this.a[paramInt];
  }

  public boolean hasBitMapData(String paramString)
  {
    return nativeHasBitMapData(this.b, paramString);
  }

  public boolean hasGridData(String paramString)
  {
    return nativeHasGridData(this.b, paramString);
  }

  public void initIconData(Context paramContext)
  {
    int i = 1;
    setBackgroudImageData(o.a(paramContext, "bk.data"));
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inSampleSize = i;
    localOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
    while (i < 53)
    {
      byte[] arrayOfByte = o.a(paramContext, i + ".png");
      if (arrayOfByte != null)
        this.a[i] = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length, localOptions);
      i++;
    }
  }

  public void initStyleData(Context paramContext)
  {
    String str = "style_l.data";
    if ((paramContext.getResources().getDisplayMetrics().densityDpi == 120) || (paramContext.getResources().getDisplayMetrics().densityDpi == 160))
      str = "style_s.data";
    setStyleData(o.a(paramContext, str));
  }

  public void putBitmapData(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    nativePutBitmapData(this.b, paramString, paramArrayOfByte, paramInt1, paramInt2);
  }

  public void putGridData(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    nativePutGridData(this.b, paramArrayOfByte, paramInt1, paramInt2);
  }

  public void removeBitmapData(String paramString, int paramInt)
  {
    nativeRemoveBitmapData(this.b, paramString, paramInt);
  }

  public void setBackgroudImageData(byte[] paramArrayOfByte)
  {
    nativeSetBackgroundImageData(this.b, paramArrayOfByte);
  }

  public void setBitmapCacheMaxSize(int paramInt)
  {
    nativeSetBitmapCacheMaxSize(this.b, paramInt);
  }

  public void setIconData(int paramInt, byte[] paramArrayOfByte)
  {
    nativeSetIconData(this.b, paramInt, paramArrayOfByte);
  }

  public void setStyleData(byte[] paramArrayOfByte)
  {
    nativeSetStyleData(this.b, paramArrayOfByte);
  }

  public void setVecotormapCacheMaxSize(int paramInt)
  {
    nativeSetVectormapCacheMaxSize(this.b, paramInt);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.mapabc.minimap.map.vmap.NativeMapEngine
 * JD-Core Version:    0.6.0
 */