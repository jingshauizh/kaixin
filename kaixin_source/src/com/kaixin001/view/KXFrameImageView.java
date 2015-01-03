package com.kaixin001.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView.ScaleType;
import com.kaixin001.util.ImageCache;

public class KXFrameImageView extends KXSaveFlowImageView
{
  public static final int TYPE_FIT_START_X = 3;
  public static final int TYPE_FIT_START_Y = 5;
  public static final int TYPE_FIT_X = 2;
  public static final int TYPE_FIT_Y = 4;
  public static final int TYPE_NORMAL = 0;
  public static final int TYPE_START_CROP = 1;
  private Context mContext;
  private Drawable mFrameDrawable;
  private Drawable mIndicateBackground;
  private Drawable mIndicateIcon;
  private onKCFrameImageListener mKCFrameImageListener;
  private int mMaxShowHei;
  private NinePatch mNinePatch;
  private Paint mPaint = new Paint();
  private int mType = 0;

  public KXFrameImageView(Context paramContext)
  {
    super(paramContext);
    init(paramContext);
  }

  public KXFrameImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext);
    readAttr(paramAttributeSet);
  }

  private void changeMatrixScale(Matrix paramMatrix, float paramFloat1, float paramFloat2)
  {
    if (paramMatrix == null)
      return;
    float[] arrayOfFloat = new float[9];
    paramMatrix.getValues(arrayOfFloat);
    float f1 = arrayOfFloat[0];
    float f2 = arrayOfFloat[4];
    paramMatrix.postScale(paramFloat1 / f1, paramFloat2 / f2);
  }

  private void changeMatrixTrans(Matrix paramMatrix, float paramFloat1, float paramFloat2)
  {
    if (paramMatrix == null)
      return;
    float[] arrayOfFloat = new float[9];
    paramMatrix.getValues(arrayOfFloat);
    float f1 = arrayOfFloat[2];
    float f2 = arrayOfFloat[5];
    paramMatrix.postTranslate(paramFloat1 - f1, paramFloat2 - f2);
  }

  private void init(Context paramContext)
  {
    this.mContext = paramContext;
    setFrameNinePatchResId(2130838778);
  }

  private void readAttr(AttributeSet paramAttributeSet)
  {
    this.mMaxShowHei = paramAttributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "maxHeight", -1);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.mNinePatch != null)
    {
      Rect localRect = new Rect(0, 0, getWidth(), getHeight());
      this.mNinePatch.draw(paramCanvas, localRect, this.mPaint);
    }
    if (this.mFrameDrawable != null)
    {
      this.mFrameDrawable.setBounds(0, 0, getWidth(), getHeight());
      this.mFrameDrawable.draw(paramCanvas);
    }
    if (this.mIndicateBackground != null)
    {
      this.mIndicateBackground.setBounds(0, 0, getWidth(), getHeight());
      this.mIndicateBackground.draw(paramCanvas);
    }
    if (this.mIndicateIcon != null)
    {
      int i = this.mIndicateIcon.getIntrinsicWidth();
      int j = this.mIndicateIcon.getIntrinsicHeight();
      if ((this.mIndicateIcon instanceof BitmapDrawable))
      {
        BitmapDrawable localBitmapDrawable = (BitmapDrawable)this.mIndicateIcon;
        if (localBitmapDrawable.getBitmap() != null)
        {
          float f = this.mContext.getResources().getDisplayMetrics().density;
          i = (int)(f * localBitmapDrawable.getBitmap().getWidth() / 1.5F);
          j = (int)(f * localBitmapDrawable.getBitmap().getHeight() / 1.5F);
        }
      }
      int k = (getWidth() - i) / 2;
      int m = (getHeight() - j) / 2;
      this.mIndicateIcon.setBounds(k, m, k + i, m + j);
      this.mIndicateIcon.draw(paramCanvas);
    }
  }

  public void setCenterIndicateBackground(Drawable paramDrawable)
  {
    this.mIndicateBackground = paramDrawable;
    invalidate();
  }

  public void setCenterIndicateIcon(int paramInt)
  {
    this.mIndicateIcon = null;
    if (paramInt > 0)
      this.mIndicateIcon = new BitmapDrawable(ImageCache.getInstance().getLoadingBitmap(paramInt, 0, 0));
    invalidate();
  }

  public void setFrameNinePatchResId(int paramInt)
  {
    if (paramInt > 0)
      try
      {
        Bitmap localBitmap = ImageCache.getInstance().getLoadingBitmap(paramInt, 0, 0);
        this.mNinePatch = new NinePatch(localBitmap, localBitmap.getNinePatchChunk(), null);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
    this.mNinePatch = null;
  }

  public void setFrameResId(int paramInt)
  {
    if (paramInt > 0)
      this.mFrameDrawable = this.mContext.getResources().getDrawable(paramInt);
  }

  public void setImageBitmap(Bitmap paramBitmap)
  {
    int i = 1;
    if (paramBitmap == null)
    {
      super.setImageBitmap(paramBitmap);
      return;
    }
    Matrix localMatrix = new Matrix();
    ViewGroup.LayoutParams localLayoutParams = getLayoutParams();
    int k = getWidth() - getPaddingLeft() - getPaddingRight();
    int m = getHeight() - getPaddingTop() - getPaddingBottom();
    if (((k == 0) || (m == 0)) && (localLayoutParams != null))
    {
      k = localLayoutParams.width;
      m = localLayoutParams.height;
    }
    int n = paramBitmap.getWidth();
    int i1 = paramBitmap.getHeight();
    float f9;
    if (this.mType == i)
      if (this.mMaxShowHei > 0)
      {
        f9 = k / n;
        if (localLayoutParams != null)
        {
          int i2 = (int)(f9 * i1);
          if (i2 <= this.mMaxShowHei)
            break label212;
          if (i != 0)
            i2 = this.mMaxShowHei;
          localLayoutParams.height = i2;
          if (this.mKCFrameImageListener != null)
            this.mKCFrameImageListener.onImageCreate(i);
        }
        setLayoutParams(localLayoutParams);
        label186: localMatrix.setScale(f9, f9);
        setScaleType(ImageView.ScaleType.MATRIX);
        setImageMatrix(localMatrix);
      }
    while (true)
    {
      super.setImageBitmap(paramBitmap);
      return;
      label212: int j = 0;
      break;
      if (n * m > k * i1)
      {
        f9 = m / i1;
        break label186;
      }
      f9 = k / n;
      break label186;
      if (this.mType == 2)
      {
        float f6 = k / n;
        float f7 = 0.5F * (k - f6 * n);
        float f8 = 0.5F * (m - f6 * i1);
        localMatrix.setScale(f6, f6);
        localMatrix.postTranslate(f7, f8);
        setScaleType(ImageView.ScaleType.MATRIX);
        setImageMatrix(localMatrix);
        continue;
      }
      if (this.mType == 3)
      {
        float f5 = k / n;
        localMatrix.setScale(f5, f5);
        localMatrix.postTranslate(0.0F, 0.0F);
        setScaleType(ImageView.ScaleType.MATRIX);
        setImageMatrix(localMatrix);
        continue;
      }
      if (this.mType == 4)
      {
        float f2 = m / i1;
        float f3 = 0.5F * (k - f2 * n);
        float f4 = 0.5F * (m - f2 * i1);
        localMatrix.setScale(f2, f2);
        localMatrix.postTranslate(f3, f4);
        setScaleType(ImageView.ScaleType.MATRIX);
        setImageMatrix(localMatrix);
        continue;
      }
      if (this.mType != 5)
        continue;
      float f1 = m / i1;
      changeMatrixScale(localMatrix, f1, f1);
      changeMatrixTrans(localMatrix, 0.0F, 0.0F);
      setScaleType(ImageView.ScaleType.MATRIX);
      setImageMatrix(localMatrix);
    }
  }

  public void setMaxShowHei(int paramInt)
  {
    this.mMaxShowHei = paramInt;
  }

  public void setStartCrop(boolean paramBoolean)
  {
    this.mType = 1;
  }

  public void setType(int paramInt)
  {
    this.mType = paramInt;
  }

  public void setonKCFrameImageListener(onKCFrameImageListener paramonKCFrameImageListener)
  {
    this.mKCFrameImageListener = paramonKCFrameImageListener;
  }

  public static abstract interface onKCFrameImageListener
  {
    public abstract void onImageCreate(boolean paramBoolean);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXFrameImageView
 * JD-Core Version:    0.6.0
 */