package com.kaixin001.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;

public class BitmapFrameUtils
{
  private static final String TAG = "BitmapFrameUtils";
  private static Bitmap resBm = null;

  public static Bitmap drawFrame(Bitmap paramBitmap, BitmapDrawable paramBitmapDrawable)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    Rect localRect1 = new Rect(0, 0, i, j);
    resBm = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(resBm);
    localCanvas.drawBitmap(paramBitmap, null, localRect1, null);
    Rect localRect2 = new Rect();
    localRect2.set(0, 0, i, j);
    paramBitmapDrawable.setBounds(localRect2);
    paramBitmapDrawable.draw(localCanvas);
    return resBm;
  }

  public static Bitmap mergeFrame(Bitmap paramBitmap, Bitmap[] paramArrayOfBitmap)
  {
    return paste8block(paramBitmap, newBitmapDrawable(paramArrayOfBitmap[0], true), newBitmapDrawable(paramArrayOfBitmap[1], false), newBitmapDrawable(paramArrayOfBitmap[2], true), newBitmapDrawable(paramArrayOfBitmap[3], false), newBitmapDrawable(paramArrayOfBitmap[4], true), newBitmapDrawable(paramArrayOfBitmap[5], false), newBitmapDrawable(paramArrayOfBitmap[6], true), newBitmapDrawable(paramArrayOfBitmap[7], false));
  }

  public static Bitmap mergeSingleFrame(Bitmap paramBitmap, Bitmap[] paramArrayOfBitmap)
  {
    return drawFrame(paramBitmap, newBitmapDrawable(paramArrayOfBitmap[0], false));
  }

  public static BitmapDrawable newBitmapDrawable(Bitmap paramBitmap, boolean paramBoolean)
  {
    BitmapDrawable localBitmapDrawable = new BitmapDrawable(paramBitmap);
    if (paramBoolean)
      localBitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
    return localBitmapDrawable;
  }

  public static Bitmap paste8block(Bitmap paramBitmap, BitmapDrawable paramBitmapDrawable1, BitmapDrawable paramBitmapDrawable2, BitmapDrawable paramBitmapDrawable3, BitmapDrawable paramBitmapDrawable4, BitmapDrawable paramBitmapDrawable5, BitmapDrawable paramBitmapDrawable6, BitmapDrawable paramBitmapDrawable7, BitmapDrawable paramBitmapDrawable8)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    int k = paramBitmapDrawable1.getBitmap().getWidth();
    int m = paramBitmapDrawable1.getBitmap().getHeight();
    int n = paramBitmapDrawable2.getBitmap().getWidth();
    int i1 = paramBitmapDrawable2.getBitmap().getHeight();
    int i2 = paramBitmapDrawable3.getBitmap().getWidth();
    int i3 = paramBitmapDrawable3.getBitmap().getHeight();
    int i4 = paramBitmapDrawable4.getBitmap().getWidth();
    int i5 = paramBitmapDrawable4.getBitmap().getHeight();
    int i6 = paramBitmapDrawable5.getBitmap().getWidth();
    paramBitmapDrawable5.getBitmap().getHeight();
    int i7 = paramBitmapDrawable6.getBitmap().getWidth();
    int i8 = paramBitmapDrawable6.getBitmap().getHeight();
    int i9 = paramBitmapDrawable7.getBitmap().getWidth();
    int i10 = paramBitmapDrawable7.getBitmap().getHeight();
    int i11 = paramBitmapDrawable8.getBitmap().getWidth();
    int i12 = paramBitmapDrawable8.getBitmap().getHeight();
    int i13 = i4 + (n + i2);
    int i14 = i12 + (i1 + m);
    if ((i < i13) || (j < i14))
    {
      Rect localRect1 = new Rect(k - 2, i3 - 2, 2 + (i13 - i6), 2 + (i14 - i10));
      resBm = Bitmap.createBitmap(i13, i14, Bitmap.Config.ARGB_8888);
      Canvas localCanvas1 = new Canvas(resBm);
      localCanvas1.drawBitmap(paramBitmap, null, localRect1, null);
      Rect localRect2 = new Rect();
      localRect2.set(0, 0, n, i1);
      paramBitmapDrawable2.setBounds(localRect2);
      paramBitmapDrawable2.draw(localCanvas1);
      localRect2.set(i13 - i4, 0, i13, i5);
      paramBitmapDrawable4.setBounds(localRect2);
      paramBitmapDrawable4.draw(localCanvas1);
      localRect2.set(0, i14 - i12, i11, i14);
      paramBitmapDrawable8.setBounds(localRect2);
      paramBitmapDrawable8.draw(localCanvas1);
      localRect2.set(i13 - i7, i14 - i8, i13, i14);
      paramBitmapDrawable6.setBounds(localRect2);
      paramBitmapDrawable6.draw(localCanvas1);
      localRect2.set(0, 0, i2, i3);
      localCanvas1.save();
      localCanvas1.translate(n, 0.0F);
      paramBitmapDrawable3.setBounds(localRect2);
      paramBitmapDrawable3.draw(localCanvas1);
      localRect2.set(0, 0, i9, i10);
      localCanvas1.restore();
      localCanvas1.save();
      localCanvas1.translate(i11, i14 - i10);
      paramBitmapDrawable7.setBounds(localRect2);
      paramBitmapDrawable7.draw(localCanvas1);
      localCanvas1.restore();
      localCanvas1.save();
      localRect2.set(0, 0, k, m);
      localCanvas1.translate(0.0F, i1);
      paramBitmapDrawable1.setBounds(localRect2);
      paramBitmapDrawable1.draw(localCanvas1);
      localCanvas1.restore();
      localCanvas1.translate(i13 - i6, i5);
      paramBitmapDrawable5.setBounds(localRect2);
      paramBitmapDrawable5.draw(localCanvas1);
      return resBm;
    }
    int i15 = k - 2;
    int i16 = i3 - 2;
    int i17 = i15 + (i + i15);
    int i18 = i16 + (j + i16);
    int i19 = (i17 - n - i4) / i2;
    int i20 = (i18 - i1 - i12) / m;
    int i21 = n + i4 + i19 * i2;
    int i22 = i12 + i1 + i20 * m;
    Rect localRect3 = new Rect(i15, i16, i21 - i15, i22 - i16);
    resBm = Bitmap.createBitmap(i21, i22, Bitmap.Config.ARGB_8888);
    Canvas localCanvas2 = new Canvas(resBm);
    localCanvas2.drawBitmap(paramBitmap, null, localRect3, null);
    Rect localRect4 = new Rect();
    localRect4.set(0, 0, n, i1);
    paramBitmapDrawable2.setBounds(localRect4);
    paramBitmapDrawable2.draw(localCanvas2);
    localRect4.set(i21 - i4, 0, i21, i5);
    paramBitmapDrawable4.setBounds(localRect4);
    paramBitmapDrawable4.draw(localCanvas2);
    localRect4.set(0, i22 - i12, i11, i22);
    paramBitmapDrawable8.setBounds(localRect4);
    paramBitmapDrawable8.draw(localCanvas2);
    localRect4.set(i21 - i7, i22 - i8, i21, i22);
    paramBitmapDrawable6.setBounds(localRect4);
    paramBitmapDrawable6.draw(localCanvas2);
    localRect4.set(0, 0, i2 * i19, i3);
    localCanvas2.save();
    localCanvas2.translate(n, 0.0F);
    paramBitmapDrawable3.setBounds(localRect4);
    paramBitmapDrawable3.draw(localCanvas2);
    localCanvas2.restore();
    localRect4.set(0, 0, i9 * i19, i10);
    localCanvas2.save();
    localCanvas2.translate(i11, i22 - i10);
    paramBitmapDrawable7.setBounds(localRect4);
    paramBitmapDrawable7.draw(localCanvas2);
    localCanvas2.restore();
    localCanvas2.save();
    localRect4.set(0, 0, k, m * i20);
    localCanvas2.translate(0.0F, i1);
    paramBitmapDrawable1.setBounds(localRect4);
    paramBitmapDrawable1.draw(localCanvas2);
    localCanvas2.restore();
    localCanvas2.translate(i21 - i6, i5);
    paramBitmapDrawable5.setBounds(localRect4);
    paramBitmapDrawable5.draw(localCanvas2);
    return resBm;
  }

  public static Bitmap trimBitmap(Bitmap paramBitmap, Matrix paramMatrix, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramBitmap == null) || (paramMatrix == null))
      return null;
    Bitmap localBitmap = Bitmap.createBitmap(paramInt3 - paramInt1, paramInt4 - paramInt2, Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    localCanvas.translate(0 - paramInt1, 0 - paramInt2);
    localCanvas.drawBitmap(paramBitmap, paramMatrix, new Paint());
    return localBitmap;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.BitmapFrameUtils
 * JD-Core Version:    0.6.0
 */