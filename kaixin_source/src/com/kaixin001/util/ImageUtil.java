package com.kaixin001.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class ImageUtil
{
  static final String CIRCLE = "circle";
  static final String MIRROR = "mirror";
  static final String NOMOL = "";
  static final String ROUND_CORNER = "round";

  public static Bitmap getCircleBitmap(Bitmap paramBitmap)
  {
    int i;
    Bitmap localBitmap;
    Canvas localCanvas;
    Paint localPaint;
    int k;
    label97: int m;
    label115: Rect localRect;
    int n;
    if (paramBitmap.getWidth() < paramBitmap.getHeight())
    {
      i = paramBitmap.getWidth();
      localBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
      localCanvas = new Canvas(localBitmap);
      localPaint = new Paint(1);
      localCanvas.drawOval(new RectF(0.0F, 0.0F, i, i), localPaint);
      localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
      if (i >= paramBitmap.getWidth())
        break label180;
      k = (paramBitmap.getWidth() - i) / 2;
      if (i >= paramBitmap.getHeight())
        break label186;
      m = (paramBitmap.getHeight() - i) / 2;
      localRect = new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
      if (k != 0)
        break label192;
      n = i;
      label142: if (m != 0)
        break label204;
    }
    while (true)
    {
      localCanvas.drawBitmap(paramBitmap, localRect, new Rect(k, m, n, i), localPaint);
      return localBitmap;
      int j = paramBitmap.getHeight();
      break;
      label180: k = 0;
      break label97;
      label186: m = 0;
      break label115;
      label192: int i1 = k + paramBitmap.getWidth();
      break label142;
      label204: j = m + paramBitmap.getHeight();
    }
  }

  public static Bitmap getMirrorBitmap(Bitmap paramBitmap)
  {
    try
    {
      Matrix localMatrix = new Matrix();
      localMatrix.setScale(1.0F, -1.0F, 0.0F, 0.0F);
      int i = paramBitmap.getWidth();
      int j = paramBitmap.getHeight();
      KXLog.d("Mirror", i + ":" + j);
      Bitmap localBitmap = Bitmap.createBitmap(paramBitmap, 0, 0, i, j, localMatrix, false);
      KXLog.d("Mirror", localBitmap.getWidth() + ":" + localBitmap.getHeight());
      return localBitmap;
    }
    catch (Exception localException)
    {
      KXLog.d("Mirror", "", localException);
      localException.printStackTrace();
    }
    return null;
  }

  public static Bitmap getOtherShapeBitmap(Bitmap paramBitmap, String paramString)
  {
    if ("round".equals(paramString))
      return getRoundedCornerBitmap(paramBitmap, ImageDownloader.RoundCornerType.hdpi_small);
    if ("circle".equals(paramString))
      return getCircleBitmap(paramBitmap);
    if ("mirror".equals(paramString))
      return getMirrorBitmap(paramBitmap);
    return null;
  }

  public static Bitmap getRoundedCornerBitmap(Bitmap paramBitmap, ImageDownloader.RoundCornerType paramRoundCornerType)
  {
    int i;
    float f;
    label65: Bitmap localBitmap;
    Canvas localCanvas;
    Paint localPaint;
    int k;
    label152: int m;
    label170: Rect localRect;
    int n;
    if (paramBitmap.getWidth() < paramBitmap.getHeight())
    {
      i = paramBitmap.getWidth();
      switch ($SWITCH_TABLE$com$kaixin001$util$ImageDownloader$RoundCornerType()[paramRoundCornerType.ordinal()])
      {
      default:
        f = 3.75F * (i / 35.0F);
        localBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        localCanvas = new Canvas(localBitmap);
        localPaint = new Paint(1);
        localCanvas.drawRoundRect(new RectF(0.0F, 0.0F, i, i), f, f, localPaint);
        localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        if (i >= paramBitmap.getWidth())
          break;
        k = (paramBitmap.getWidth() - i) / 2;
        if (i < paramBitmap.getHeight())
        {
          m = (paramBitmap.getHeight() - i) / 2;
          localRect = new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
          if (k != 0)
            break label297;
          n = i;
          label197: if (m != 0)
            break label309;
        }
      case 2:
      case 1:
      case 4:
      case 3:
      }
    }
    while (true)
    {
      localCanvas.drawBitmap(paramBitmap, localRect, new Rect(k, m, n, i), localPaint);
      return localBitmap;
      int j = paramBitmap.getHeight();
      break;
      f = 5.0F * (j / 38.0F);
      break label65;
      f = 7.5F * (j / 52.0F);
      break label65;
      f = 2.5F * (j / 25.0F);
      break label65;
      f = 3.75F * (j / 35.0F);
      break label65;
      k = 0;
      break label152;
      m = 0;
      break label170;
      label297: int i1 = k + paramBitmap.getWidth();
      break label197;
      label309: j = m + paramBitmap.getHeight();
    }
  }

  public static Bitmap resizeBitmap(Bitmap paramBitmap, float paramFloat1, float paramFloat2)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    Matrix localMatrix = new Matrix();
    localMatrix.postScale(paramFloat1, paramFloat2);
    return Bitmap.createBitmap(paramBitmap, 0, 0, i, j, localMatrix, true);
  }

  public static Bitmap resizeBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    float f1 = paramInt1 / i;
    float f2 = paramInt2 / j;
    Matrix localMatrix = new Matrix();
    localMatrix.postScale(f1, f2);
    return Bitmap.createBitmap(paramBitmap, 0, 0, i, j, localMatrix, true);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.ImageUtil
 * JD-Core Version:    0.6.0
 */