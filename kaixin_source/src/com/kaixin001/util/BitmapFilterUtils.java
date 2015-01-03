package com.kaixin001.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;

public class BitmapFilterUtils
{
  private static final int EFFECT_BLACK = 9;
  private static final int EFFECT_CLAASIC = 4;
  private static final int EFFECT_COLD = 5;
  private static final int EFFECT_DREAM = 8;
  private static final int EFFECT_FOREST = 11;
  private static final int EFFECT_JAPAN = 6;
  private static final int EFFECT_LOMO = 7;
  private static final int EFFECT_OLD = 2;
  private static final int EFFECT_STRONG = 3;
  private static final int EFFECT_SUN = 1;
  private static final int EFFECT_WATER = 10;
  public static final String PAT_BLACK = "patblack.jpg";
  public static final String PAT_DREAM = "patdream.png";
  public static final String PAT_FOREST = "patforest.png";
  public static final String PAT_JAPAN = "patjapan.png";
  public static final String PAT_LOMO = "patlomo.png";
  public static final String PAT_WATER = "patwater.png";

  public static byte[] bitmap2Bytes(Bitmap paramBitmap)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
    return localByteArrayOutputStream.toByteArray();
  }

  public static Bitmap blackWhite(Bitmap paramBitmap1, Bitmap paramBitmap2)
  {
    return doMemFilter(9, paramBitmap1, paramBitmap2);
  }

  public static boolean blackWhite(String paramString1, String paramString2, String paramString3)
  {
    return doFilter(9, paramString1, paramString2, paramString3);
  }

  public static Bitmap bytes2Bitmap(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte.length != 0)
      return BitmapFactory.decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length);
    return null;
  }

  public static Bitmap classic(Bitmap paramBitmap)
  {
    return doMemFilter(4, paramBitmap, null);
  }

  public static boolean classic(String paramString1, String paramString2)
  {
    return doFilter(4, paramString1, paramString2, null);
  }

  public static Bitmap cold(Bitmap paramBitmap)
  {
    return doMemFilter(5, paramBitmap, null);
  }

  public static boolean cold(String paramString1, String paramString2)
  {
    return doFilter(5, paramString1, paramString2, null);
  }

  private static native boolean doFilter(int paramInt, String paramString1, String paramString2, String paramString3);

  private static Bitmap doMemFilter(int paramInt, Bitmap paramBitmap1, Bitmap paramBitmap2)
  {
    if ((paramBitmap1 == null) || (paramBitmap1.isRecycled()))
      return null;
    int i = paramBitmap1.getWidth();
    int j = paramBitmap1.getHeight();
    int[] arrayOfInt1 = new int[i * j];
    paramBitmap1.getPixels(arrayOfInt1, 0, i, 0, 0, i, j);
    int[] arrayOfInt2 = null;
    int k = 0;
    int m = 0;
    if (paramBitmap2 != null)
    {
      k = paramBitmap2.getWidth();
      m = paramBitmap2.getHeight();
      arrayOfInt2 = new int[k * m];
      paramBitmap2.getPixels(arrayOfInt2, 0, k, 0, 0, k, m);
    }
    System.gc();
    doMemFilter(paramInt, arrayOfInt1, i, j, arrayOfInt2, k, m);
    System.gc();
    Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
    localBitmap.setPixels(arrayOfInt1, 0, i, 0, 0, i, j);
    return localBitmap;
  }

  private static native boolean doMemFilter(int paramInt1, int[] paramArrayOfInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt2, int paramInt4, int paramInt5);

  public static Bitmap dream(Bitmap paramBitmap1, Bitmap paramBitmap2)
  {
    return doMemFilter(8, paramBitmap1, paramBitmap2);
  }

  public static boolean dream(String paramString1, String paramString2, String paramString3)
  {
    return doFilter(8, paramString1, paramString2, paramString3);
  }

  public static Bitmap forest(Bitmap paramBitmap1, Bitmap paramBitmap2)
  {
    return doMemFilter(11, paramBitmap1, paramBitmap2);
  }

  public static boolean forest(String paramString1, String paramString2, String paramString3)
  {
    return doFilter(11, paramString1, paramString2, paramString3);
  }

  public static Bitmap japan(Bitmap paramBitmap1, Bitmap paramBitmap2)
  {
    return doMemFilter(6, paramBitmap1, paramBitmap2);
  }

  public static boolean japan(String paramString1, String paramString2, String paramString3)
  {
    return doFilter(6, paramString1, paramString2, paramString3);
  }

  public static Bitmap lomo(Bitmap paramBitmap1, Bitmap paramBitmap2)
  {
    return doMemFilter(7, paramBitmap1, paramBitmap2);
  }

  public static boolean lomo(String paramString1, String paramString2, String paramString3)
  {
    return doFilter(7, paramString1, paramString2, paramString3);
  }

  public static Bitmap old(Bitmap paramBitmap)
  {
    return doMemFilter(2, paramBitmap, null);
  }

  public static boolean old(String paramString1, String paramString2)
  {
    return doFilter(2, paramString1, paramString2, null);
  }

  public static native String stringFromJNI();

  public static Bitmap strongPro(Bitmap paramBitmap)
  {
    return doMemFilter(3, paramBitmap, null);
  }

  public static boolean strongPro(String paramString1, String paramString2)
  {
    return doFilter(3, paramString1, paramString2, null);
  }

  public static Bitmap sun(Bitmap paramBitmap)
  {
    return doMemFilter(1, paramBitmap, null);
  }

  public static boolean sun(String paramString1, String paramString2)
  {
    return doFilter(1, paramString1, paramString2, null);
  }

  public static Bitmap waterPro(Bitmap paramBitmap1, Bitmap paramBitmap2)
  {
    return doMemFilter(10, paramBitmap1, paramBitmap2);
  }

  public static boolean waterPro(String paramString1, String paramString2, String paramString3)
  {
    return doFilter(10, paramString1, paramString2, paramString3);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.BitmapFilterUtils
 * JD-Core Version:    0.6.0
 */