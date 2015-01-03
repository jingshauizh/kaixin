package com.kaixin001.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.kaixin001.util.KXLog;

public class KXTrimImageView extends ImageView
{
  private static final int MODE_DRAG = 1;
  private static final int MODE_NONE = 0;
  private static final int MODE_ZOOM = 2;
  private float ZOOM_MAX;
  private float ZOOM_MIN;
  private Bitmap mBitmap;
  private float mBottomMin;
  private float mFingleDistance;
  private float mFrameCenterX;
  private float mFrameCenterY;
  private float mFrameHeight = 0.0F;
  private float mFrameOffsetX = dipToPx(50.0F);
  private float mFrameOffsetY = dipToPx(50.0F);
  private float mFramePaddingX = 0.0F;
  private float mFramePaddingY = dipToPx(10.0F);
  private float mFrameWidth = 0.0F;
  private boolean mInit = false;
  private float mLeftMax;
  private float[] mMatrixValues = new float[9];
  private int mMode;
  private Paint mPaint = new Paint();
  private PointF mPtLastMove = new PointF();
  private float mRightMin;
  private float mTopMax;

  public KXTrimImageView(Context paramContext)
  {
    super(paramContext);
  }

  public KXTrimImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  private float dipToPx(float paramFloat)
  {
    return 0.5F + paramFloat * getResources().getDisplayMetrics().density;
  }

  private static float getFingleDistance(MotionEvent paramMotionEvent)
  {
    float f1 = paramMotionEvent.getX(0) - paramMotionEvent.getX(1);
    float f2 = paramMotionEvent.getY(0) - paramMotionEvent.getY(1);
    return FloatMath.sqrt(f1 * f1 + f2 * f2);
  }

  private void regulatePos()
  {
    MotionEvent localMotionEvent = MotionEvent.obtain(0L, 0L, 0, this.mPtLastMove.x, this.mPtLastMove.y, 0);
    translate(this.mPtLastMove, localMotionEvent);
  }

  private void translate(PointF paramPointF, MotionEvent paramMotionEvent)
  {
    float f1 = getValue(getImageMatrix(), 2);
    float f2 = getValue(getImageMatrix(), 5);
    float f3 = getValue(getImageMatrix(), 0);
    float f4 = getValue(getImageMatrix(), 4);
    float f5 = f3 * this.mBitmap.getWidth();
    float f6 = f4 * this.mBitmap.getHeight();
    float f7 = paramMotionEvent.getX() - paramPointF.x;
    float f8 = paramMotionEvent.getY() - paramPointF.y;
    KXLog.d("TESTAPP", "translate1 deltaX:" + f7 + ", deltaY:" + f8);
    if (f1 + f7 > this.mLeftMax)
    {
      f7 = this.mLeftMax - f1;
      if (f2 + f8 <= this.mTopMax)
        break label237;
      f8 = this.mTopMax - f2;
    }
    while (true)
    {
      paramPointF.set(paramMotionEvent.getX(), paramMotionEvent.getY());
      if ((f7 != 0.0F) || (f8 != 0.0F))
      {
        getImageMatrix().postTranslate(f7, f8);
        postInvalidate();
      }
      return;
      if (f5 + (f1 + f7) >= this.mRightMin)
        break;
      f7 = this.mRightMin - f1 - f5;
      break;
      label237: if (f6 + (f2 + f8) >= this.mBottomMin)
        continue;
      f8 = this.mBottomMin - f2 - f6;
    }
  }

  private void zoomTo(float paramFloat)
  {
    zoomTo(paramFloat, this.mFrameCenterX, this.mFrameCenterY);
  }

  private void zoomTo(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f1 = getValue(getImageMatrix(), 0);
    float f2 = paramFloat1 * f1;
    if (f2 > this.ZOOM_MAX)
      f2 = this.ZOOM_MAX;
    while (true)
    {
      float f3 = f2 / f1;
      getImageMatrix().postScale(f3, f3, paramFloat2, paramFloat3);
      postInvalidate();
      return;
      if (f2 >= this.ZOOM_MIN)
        continue;
      f2 = this.ZOOM_MIN;
    }
  }

  public float getFrameHeight()
  {
    return this.mFrameHeight;
  }

  public float getFramePaddingX()
  {
    return this.mFramePaddingX;
  }

  public float getFramePaddingY()
  {
    return this.mFramePaddingY;
  }

  public float getFrameWidth()
  {
    return this.mFrameWidth;
  }

  protected float getValue(Matrix paramMatrix, int paramInt)
  {
    paramMatrix.getValues(this.mMatrixValues);
    return this.mMatrixValues[paramInt];
  }

  public void initDefaultParams()
  {
    if (this.mFrameWidth <= 0.0F)
    {
      this.mFrameWidth = getWidth();
      this.mFrameHeight = (4 * getWidth() / 3.0F);
    }
    this.mFrameCenterX = (this.mFramePaddingX + this.mFrameWidth / 2.0F);
    this.mFrameCenterY = (this.mFramePaddingY + this.mFrameHeight / 2.0F);
    float f1;
    float f2;
    float f3;
    Matrix localMatrix;
    if (this.mBitmap != null)
    {
      int i = this.mBitmap.getWidth();
      int j = this.mBitmap.getHeight();
      f1 = Math.max(this.mFrameWidth / i, this.mFrameHeight / j);
      f2 = this.mFramePaddingX + (this.mFrameWidth - f1 * i) / 2.0F;
      f3 = this.mFramePaddingY + (this.mFrameHeight - f1 * j) / 2.0F;
      KXLog.d("TESTAPP", "posY:" + f3);
      localMatrix = getImageMatrix();
      if (localMatrix != null)
        break label252;
      localMatrix = new Matrix();
      setImageMatrix(localMatrix);
    }
    while (true)
    {
      localMatrix.postScale(f1, f1);
      float f4 = getValue(getImageMatrix(), 2);
      float f5 = getValue(getImageMatrix(), 5);
      localMatrix.postTranslate(f2 - f4, f3 - f5);
      this.ZOOM_MAX = (f1 * 3.0F);
      this.ZOOM_MIN = f1;
      this.mInit = true;
      return;
      label252: localMatrix.reset();
    }
  }

  protected void onDraw(Canvas paramCanvas)
  {
    Matrix localMatrix = getImageMatrix();
    if (!this.mInit)
      initDefaultParams();
    if ((localMatrix != null) && (this.mBitmap != null))
    {
      float f = getValue(getImageMatrix(), 5);
      KXLog.d("TESTAPP", "transY:" + f);
      paramCanvas.drawBitmap(this.mBitmap, localMatrix, this.mPaint);
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    switch (0xFF & paramMotionEvent.getAction())
    {
    case 1:
    case 3:
    case 4:
    default:
    case 0:
    case 5:
    case 2:
      do
      {
        do
        {
          return true;
          this.mMode = 1;
          this.mPtLastMove.set(paramMotionEvent.getX(), paramMotionEvent.getY());
          return true;
          this.mFingleDistance = getFingleDistance(paramMotionEvent);
        }
        while (this.mFingleDistance <= 10.0F);
        this.mMode = 2;
        return true;
        if (this.mMode != 1)
          continue;
        translate(this.mPtLastMove, paramMotionEvent);
        return true;
      }
      while (this.mMode != 2);
      float f = getFingleDistance(paramMotionEvent);
      zoomTo(f / this.mFingleDistance);
      regulatePos();
      this.mFingleDistance = f;
      return true;
    case 6:
    }
    this.mMode = 0;
    return true;
  }

  public void setFrame(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    this.mFramePaddingX = paramFloat1;
    this.mFramePaddingY = paramFloat2;
    this.mFrameWidth = paramFloat3;
    this.mFrameHeight = paramFloat4;
    this.mFrameOffsetX = paramFloat5;
    this.mFrameOffsetY = paramFloat6;
    this.mRightMin = (this.mFramePaddingX + this.mFrameOffsetX);
    this.mLeftMax = (this.mFramePaddingX + this.mFrameWidth - this.mFrameOffsetX);
    this.mBottomMin = (this.mFramePaddingY + this.mFrameOffsetY);
    this.mTopMax = (this.mFramePaddingY + this.mFrameHeight - this.mFrameOffsetY);
  }

  public void setImageBitmap(Bitmap paramBitmap)
  {
    this.mBitmap = paramBitmap;
    super.setImageBitmap(paramBitmap);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXTrimImageView
 * JD-Core Version:    0.6.0
 */