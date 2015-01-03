package com.kaixin001.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import com.kaixin001.util.ImageUtil;

public class KXMergeFrameImageView extends KXDecorateImageView
{
  public static final int CHIP_FILE_DOWN = 6;
  public static final int CHIP_FILE_LEFT = 0;
  public static final int CHIP_FILE_LEFTDOWN = 7;
  public static final int CHIP_FILE_LEFTUP = 1;
  public static final int CHIP_FILE_RIGHT = 4;
  public static final int CHIP_FILE_RIGHTDOWN = 5;
  public static final int CHIP_FILE_RIGHT_UP = 3;
  public static final int CHIP_FILE_UP = 2;
  private BitmapDrawable[] frameChips;
  private int hei;
  private Bitmap mBitmap;
  private float mOriginalScaleX = 0.0F;
  private float mOriginalScaleY = 0.0F;
  private float mOriginalX;
  private float mOriginalY;
  private int posX;
  private int posY;
  private int wid;

  public KXMergeFrameImageView(Context paramContext)
  {
    super(paramContext);
  }

  public KXMergeFrameImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  private void changeMatrixScale(float paramFloat1, float paramFloat2)
  {
    if (this.mBitmap != null)
    {
      Matrix localMatrix = getImageMatrix();
      float[] arrayOfFloat = new float[9];
      localMatrix.getValues(arrayOfFloat);
      float f1 = arrayOfFloat[0];
      float f2 = arrayOfFloat[4];
      localMatrix.postScale(paramFloat1 / f1, paramFloat2 / f2);
    }
  }

  private void changeMatrixTrans(float paramFloat1, float paramFloat2)
  {
    if (this.mBitmap != null)
    {
      Matrix localMatrix = getImageMatrix();
      float[] arrayOfFloat = new float[9];
      localMatrix.getValues(arrayOfFloat);
      float f1 = arrayOfFloat[2];
      float f2 = arrayOfFloat[5];
      localMatrix.postTranslate(paramFloat1 - f1, paramFloat2 - f2);
    }
  }

  private void draw8Frame(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    BitmapDrawable localBitmapDrawable1 = this.frameChips[0];
    BitmapDrawable localBitmapDrawable2 = this.frameChips[1];
    BitmapDrawable localBitmapDrawable3 = this.frameChips[2];
    BitmapDrawable localBitmapDrawable4 = this.frameChips[3];
    BitmapDrawable localBitmapDrawable5 = this.frameChips[4];
    BitmapDrawable localBitmapDrawable6 = this.frameChips[5];
    BitmapDrawable localBitmapDrawable7 = this.frameChips[6];
    BitmapDrawable localBitmapDrawable8 = this.frameChips[7];
    paramCanvas.translate(paramInt1, paramInt2);
    int i = localBitmapDrawable1.getBitmap().getWidth();
    int j = localBitmapDrawable1.getBitmap().getHeight();
    int k = localBitmapDrawable2.getBitmap().getWidth();
    int m = localBitmapDrawable2.getBitmap().getHeight();
    int n = localBitmapDrawable3.getBitmap().getWidth();
    int i1 = localBitmapDrawable3.getBitmap().getHeight();
    int i2 = localBitmapDrawable4.getBitmap().getWidth();
    int i3 = localBitmapDrawable4.getBitmap().getHeight();
    int i4 = localBitmapDrawable5.getBitmap().getWidth();
    localBitmapDrawable5.getBitmap().getHeight();
    int i5 = localBitmapDrawable6.getBitmap().getWidth();
    int i6 = localBitmapDrawable6.getBitmap().getHeight();
    int i7 = localBitmapDrawable7.getBitmap().getWidth();
    int i8 = localBitmapDrawable7.getBitmap().getHeight();
    int i9 = localBitmapDrawable8.getBitmap().getWidth();
    int i10 = localBitmapDrawable8.getBitmap().getHeight();
    int i11 = paramInt3 - 2 * (i - 2);
    int i12 = paramInt4 - 2 * (i1 - 2);
    int i13 = i2 + (k + n);
    int i14 = i10 + (m + j);
    if ((i11 < i13) || (i12 < i14))
    {
      new Rect(i - 2, i1 - 2, 2 + (i13 - i4), 2 + (i14 - i8));
      Rect localRect1 = new Rect();
      localRect1.set(0, 0, k, m);
      localBitmapDrawable2.setBounds(localRect1);
      localBitmapDrawable2.draw(paramCanvas);
      localRect1.set(i13 - i2, 0, i13, i3);
      localBitmapDrawable4.setBounds(localRect1);
      localBitmapDrawable4.draw(paramCanvas);
      localRect1.set(0, i14 - i10, i9, i14);
      localBitmapDrawable8.setBounds(localRect1);
      localBitmapDrawable8.draw(paramCanvas);
      localRect1.set(i13 - i5, i14 - i6, i13, i14);
      localBitmapDrawable6.setBounds(localRect1);
      localBitmapDrawable6.draw(paramCanvas);
      localRect1.set(0, 0, n, i1);
      paramCanvas.save();
      paramCanvas.translate(k, 0.0F);
      localBitmapDrawable3.setBounds(localRect1);
      localBitmapDrawable3.draw(paramCanvas);
      localRect1.set(0, 0, i7, i8);
      paramCanvas.restore();
      paramCanvas.save();
      paramCanvas.translate(i9, i14 - i8);
      localBitmapDrawable7.setBounds(localRect1);
      localBitmapDrawable7.draw(paramCanvas);
      paramCanvas.restore();
      paramCanvas.save();
      localRect1.set(0, 0, i, j);
      paramCanvas.translate(0.0F, m);
      localBitmapDrawable1.setBounds(localRect1);
      localBitmapDrawable1.draw(paramCanvas);
      paramCanvas.restore();
      paramCanvas.translate(i13 - i4, i3);
      localBitmapDrawable5.setBounds(localRect1);
      localBitmapDrawable5.draw(paramCanvas);
      return;
    }
    int i15 = i - 2;
    int i16 = i1 - 2;
    int i17 = i15 + (i11 + i15);
    int i18 = i16 + (i12 + i16);
    ((i17 - k - i2) / n);
    ((i18 - m - i10) / j);
    int i19 = i17 - k - i2;
    int i20 = i18 - m - i10;
    int i21 = i19 + (k + i2);
    int i22 = i20 + (i10 + m);
    Rect localRect2 = new Rect();
    localRect2.set(0, 0, k, m);
    localBitmapDrawable2.setBounds(localRect2);
    localBitmapDrawable2.draw(paramCanvas);
    localRect2.set(i21 - i2, 0, i21, i3);
    localBitmapDrawable4.setBounds(localRect2);
    localBitmapDrawable4.draw(paramCanvas);
    localRect2.set(0, i22 - i10, i9, i22);
    localBitmapDrawable8.setBounds(localRect2);
    localBitmapDrawable8.draw(paramCanvas);
    localRect2.set(i21 - i5, i22 - i6, i21, i22);
    localBitmapDrawable6.setBounds(localRect2);
    localBitmapDrawable6.draw(paramCanvas);
    localRect2.set(0, 0, i19, i1);
    paramCanvas.save();
    paramCanvas.translate(k, 0.0F);
    localBitmapDrawable3.setBounds(localRect2);
    localBitmapDrawable3.draw(paramCanvas);
    paramCanvas.restore();
    localRect2.set(0, 0, i19, i8);
    paramCanvas.save();
    paramCanvas.translate(i9, i22 - i8);
    localBitmapDrawable7.setBounds(localRect2);
    localBitmapDrawable7.draw(paramCanvas);
    paramCanvas.restore();
    paramCanvas.save();
    localRect2.set(0, 0, i, i20);
    paramCanvas.translate(0.0F, m);
    localBitmapDrawable1.setBounds(localRect2);
    localBitmapDrawable1.draw(paramCanvas);
    paramCanvas.restore();
    paramCanvas.translate(i21 - i4, i3);
    localBitmapDrawable5.setBounds(localRect2);
    localBitmapDrawable5.draw(paramCanvas);
  }

  private void drawSingleFrame(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Rect localRect = new Rect();
    localRect.set(paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
    this.frameChips[0].setBounds(localRect);
    this.frameChips[0].draw(paramCanvas);
  }

  public static BitmapDrawable newBitmapDrawable(Bitmap paramBitmap, float paramFloat, boolean paramBoolean)
  {
    BitmapDrawable localBitmapDrawable = new BitmapDrawable(ImageUtil.resizeBitmap(paramBitmap, paramFloat, paramFloat));
    if (paramBoolean)
      localBitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
    return localBitmapDrawable;
  }

  protected void drawFrame(Canvas paramCanvas)
  {
    if (this.mBitmap != null)
    {
      int i = this.mBitmap.getWidth();
      int j = this.mBitmap.getHeight();
      float f = Math.min(getWidth() / i, getHeight() / j);
      this.wid = (int)(f * i);
      this.hei = (int)(f * j);
      this.posX = ((getWidth() - this.wid) / 2);
      this.posY = ((getHeight() - this.hei) / 2);
      if ((this.frameChips == null) || (this.frameChips.length != 1))
        break label132;
      drawSingleFrame(paramCanvas, this.posX, this.posY, this.wid, this.hei);
    }
    label132: 
    do
      return;
    while ((this.frameChips == null) || (this.frameChips.length != 8));
    draw8Frame(paramCanvas, this.posX, this.posY, this.wid, this.hei);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    drawFrame(paramCanvas);
  }

  public void setImageBitmap(Bitmap paramBitmap)
  {
    super.setImageBitmap(paramBitmap);
    this.mBitmap = paramBitmap;
    this.mOriginalScaleX = 0.0F;
  }

  public void setframeChips(Bitmap[] paramArrayOfBitmap)
  {
    float f1 = 1.0F;
    int k;
    label84: label235: float f2;
    float f3;
    float f4;
    float f5;
    if ((this.mBitmap != null) && (this.mOriginalScaleX <= 0.0F))
    {
      Matrix localMatrix = getImageMatrix();
      float[] arrayOfFloat = new float[9];
      localMatrix.getValues(arrayOfFloat);
      this.mOriginalScaleX = arrayOfFloat[0];
      this.mOriginalScaleY = arrayOfFloat[4];
      this.mOriginalX = arrayOfFloat[2];
      this.mOriginalY = arrayOfFloat[5];
      f1 = this.mOriginalScaleX;
      if (this.frameChips != null)
      {
        k = 0;
        if (k < this.frameChips.length)
          break label426;
        this.frameChips = null;
      }
      if ((paramArrayOfBitmap == null) || (paramArrayOfBitmap.length != 8))
        break label440;
      this.frameChips = new BitmapDrawable[8];
      this.frameChips[0] = newBitmapDrawable(paramArrayOfBitmap[0], f1, true);
      this.frameChips[1] = newBitmapDrawable(paramArrayOfBitmap[1], f1, false);
      this.frameChips[2] = newBitmapDrawable(paramArrayOfBitmap[2], f1, true);
      this.frameChips[3] = newBitmapDrawable(paramArrayOfBitmap[3], f1, false);
      this.frameChips[4] = newBitmapDrawable(paramArrayOfBitmap[4], f1, true);
      this.frameChips[5] = newBitmapDrawable(paramArrayOfBitmap[5], f1, false);
      this.frameChips[6] = newBitmapDrawable(paramArrayOfBitmap[6], f1, true);
      this.frameChips[7] = newBitmapDrawable(paramArrayOfBitmap[7], f1, false);
      f2 = this.mOriginalX;
      f3 = this.mOriginalY;
      f4 = this.mOriginalScaleX;
      f5 = this.mOriginalScaleY;
      if ((this.mBitmap == null) || (this.frameChips == null) || (this.frameChips.length != 8))
        break label475;
      int i = -2 + this.frameChips[0].getBitmap().getWidth();
      int j = -2 + this.frameChips[2].getBitmap().getHeight();
      float f6 = this.mOriginalX + i;
      float f7 = this.mOriginalY + j;
      changeMatrixScale((this.mBitmap.getWidth() * this.mOriginalScaleX - i * 2) / this.mBitmap.getWidth(), (this.mBitmap.getHeight() * this.mOriginalScaleY - j * 2) / this.mBitmap.getHeight());
      changeMatrixTrans(f6, f7);
    }
    while (true)
    {
      postInvalidate();
      return;
      if (this.mOriginalScaleX <= 0.0F)
        break;
      f1 = this.mOriginalScaleX;
      break;
      label426: this.frameChips[k] = null;
      k++;
      break label84;
      label440: if ((paramArrayOfBitmap == null) || (paramArrayOfBitmap.length != 1))
        break label235;
      this.frameChips = new BitmapDrawable[1];
      this.frameChips[0] = newBitmapDrawable(paramArrayOfBitmap[0], f1, false);
      break label235;
      label475: if ((this.mBitmap == null) || (this.frameChips == null) || (this.frameChips.length != 1))
        continue;
      changeMatrixScale(f4, f5);
      changeMatrixTrans(f2, f3);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXMergeFrameImageView
 * JD-Core Version:    0.6.0
 */