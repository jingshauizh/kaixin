package com.kaixin001.view;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class RotateBitmap
{
  public static final String TAG = "RotateBitmap";
  private Bitmap mBitmap;
  private int mRotation;

  public RotateBitmap(Bitmap paramBitmap)
  {
    this.mBitmap = paramBitmap;
    this.mRotation = 0;
  }

  public RotateBitmap(Bitmap paramBitmap, int paramInt)
  {
    this.mBitmap = paramBitmap;
    this.mRotation = (paramInt % 360);
  }

  public Bitmap getBitmap()
  {
    return this.mBitmap;
  }

  public int getHeight()
  {
    if (isOrientationChanged())
      return this.mBitmap.getWidth();
    return this.mBitmap.getHeight();
  }

  public Matrix getRotateMatrix()
  {
    Matrix localMatrix = new Matrix();
    if (this.mRotation != 0)
    {
      int i = this.mBitmap.getWidth() / 2;
      int j = this.mBitmap.getHeight() / 2;
      localMatrix.preTranslate(-i, -j);
      localMatrix.postRotate(this.mRotation);
      localMatrix.postTranslate(getWidth() / 2.0F, getHeight() / 2.0F);
    }
    return localMatrix;
  }

  public int getRotation()
  {
    return this.mRotation;
  }

  public int getWidth()
  {
    if (isOrientationChanged())
      return this.mBitmap.getHeight();
    return this.mBitmap.getWidth();
  }

  public boolean isOrientationChanged()
  {
    return this.mRotation / 90 % 2 != 0;
  }

  public void recycle()
  {
    if (this.mBitmap != null)
    {
      this.mBitmap.recycle();
      this.mBitmap = null;
    }
  }

  public void setBitmap(Bitmap paramBitmap)
  {
    this.mBitmap = paramBitmap;
  }

  public void setRotation(int paramInt)
  {
    this.mRotation = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.RotateBitmap
 * JD-Core Version:    0.6.0
 */