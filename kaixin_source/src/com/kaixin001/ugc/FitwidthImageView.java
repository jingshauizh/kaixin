package com.kaixin001.ugc;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class FitwidthImageView extends ImageView
{
  private ScaleFactorChangeListener mListener;
  float scaleFactor = 1.0F;

  public FitwidthImageView(Context paramContext)
  {
    super(paramContext);
  }

  public FitwidthImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setScaleType(ImageView.ScaleType.MATRIX);
  }

  public FitwidthImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  private void setFactor(float paramFloat)
  {
    if ((paramFloat != this.scaleFactor) && (this.mListener != null))
      this.mListener.onScaled(paramFloat);
    this.scaleFactor = paramFloat;
  }

  public float getScaleFactor()
  {
    return this.scaleFactor;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (getDrawable() == null)
    {
      super.onMeasure(paramInt1, paramInt2);
      return;
    }
    int i = getDrawable().getIntrinsicWidth();
    int j = getDrawable().getIntrinsicHeight();
    int k = View.MeasureSpec.getSize(paramInt1);
    setMeasuredDimension(k, k * j / i);
  }

  protected boolean setFrame(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (getDrawable() != null)
    {
      Matrix localMatrix = getImageMatrix();
      float f = getMeasuredWidth() / getDrawable().getIntrinsicWidth();
      setFactor(f);
      localMatrix.setScale(f, f, 0.0F, 0.0F);
      localMatrix.postTranslate(0.0F, (getMeasuredHeight() - this.scaleFactor * getDrawable().getIntrinsicHeight()) / 2.0F);
      setImageMatrix(localMatrix);
    }
    return super.setFrame(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void setScaleChangeListener(ScaleFactorChangeListener paramScaleFactorChangeListener)
  {
    this.mListener = paramScaleFactorChangeListener;
  }

  public static abstract interface ScaleFactorChangeListener
  {
    public abstract void onScaled(float paramFloat);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.ugc.FitwidthImageView
 * JD-Core Version:    0.6.0
 */