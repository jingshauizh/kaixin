package com.kaixin001.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class KXImageView extends ImageView
{
  private static final int DRAG = 1;
  private static final int NONE = 0;
  private static final float PAN_RATE = 20.0F;
  static final float SCALE_RATE = 1.25F;
  private static final int ZOOM = 2;
  protected Matrix mBaseMatrix = new Matrix();
  protected final RotateBitmap mBitmapDisplayed = new RotateBitmap(null);
  private final Matrix mDisplayMatrix = new Matrix();
  private boolean mDragedOrZoomed = false;
  protected Handler mHandler = new Handler();
  private final float[] mMatrixValues = new float[9];
  float mMaxZoom = 3.0F;
  private long mNextChangePositionTime;
  private Runnable mOnLayoutRunnable = null;
  private PointF mPtLastMove = new PointF();
  private PointF mPtMouseDown = new PointF();
  private Recycler mRecycler;
  protected Matrix mSuppMatrix = new Matrix();
  int mThisHeight = -1;
  int mThisWidth = -1;
  private int mode = 0;
  private float oldDist;
  private float ori_ratio = 1.0F;

  public KXImageView(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public KXImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  private void getProperBaseMatrix(RotateBitmap paramRotateBitmap, Matrix paramMatrix)
  {
    float f1 = getWidth();
    float f2 = getHeight();
    float f3 = paramRotateBitmap.getWidth();
    float f4 = paramRotateBitmap.getHeight();
    paramMatrix.reset();
    float f5 = Math.min(Math.min(f1 / f3, 3.0F), Math.min(f2 / f4, 3.0F));
    paramMatrix.postConcat(paramRotateBitmap.getRotateMatrix());
    paramMatrix.postScale(f5, f5);
    paramMatrix.postTranslate((f1 - f3 * f5) / 2.0F, (f2 - f4 * f5) / 2.0F);
  }

  private void init()
  {
    setScaleType(ImageView.ScaleType.MATRIX);
  }

  private boolean isXBound(float paramFloat)
  {
    Matrix localMatrix = getImageViewMatrix();
    float f = getValue(getImageMatrix(), 2);
    RectF localRectF = new RectF(0.0F, 0.0F, this.mBitmapDisplayed.getBitmap().getWidth(), this.mBitmapDisplayed.getBitmap().getHeight());
    localMatrix.mapRect(localRectF);
    return localRectF.width() - (paramFloat + (getWidth() - f)) <= 0.0F;
  }

  private boolean isYBound(float paramFloat)
  {
    Matrix localMatrix = getImageViewMatrix();
    float f = getValue(getImageMatrix(), 5);
    RectF localRectF = new RectF(0.0F, 0.0F, this.mBitmapDisplayed.getBitmap().getWidth(), this.mBitmapDisplayed.getBitmap().getHeight());
    localMatrix.mapRect(localRectF);
    return localRectF.width() - (paramFloat + (getWidth() - f)) <= 0.0F;
  }

  private float mesaureDistanceX(float paramFloat)
  {
    Matrix localMatrix = getImageViewMatrix();
    float f1 = getValue(getImageMatrix(), 2);
    RectF localRectF = new RectF(0.0F, 0.0F, this.mBitmapDisplayed.getBitmap().getWidth(), this.mBitmapDisplayed.getBitmap().getHeight());
    localMatrix.mapRect(localRectF);
    float f2 = localRectF.width();
    int i = getWidth();
    if (paramFloat < 0.0F)
    {
      if (f1 - paramFloat < 0.0F)
        return paramFloat;
      return f1;
    }
    if (paramFloat + (i - f1) < f2)
      return paramFloat;
    return f1 + (f2 - i);
  }

  private float mesaureDistanceY(float paramFloat)
  {
    Matrix localMatrix = getImageViewMatrix();
    float f1 = getValue(getImageMatrix(), 5);
    RectF localRectF = new RectF(0.0F, 0.0F, this.mBitmapDisplayed.getBitmap().getWidth(), this.mBitmapDisplayed.getBitmap().getHeight());
    localMatrix.mapRect(localRectF);
    float f2 = localRectF.height();
    int i = getHeight();
    if (paramFloat < 0.0F)
    {
      if (f1 - paramFloat < 0.0F)
        return paramFloat;
      return f1;
    }
    if (paramFloat + (i - f1) < f2)
      return paramFloat;
    return f1 + (f2 - i);
  }

  private void midPoint(PointF paramPointF, MotionEvent paramMotionEvent)
  {
    float f1 = paramMotionEvent.getX(0) + paramMotionEvent.getX(1);
    float f2 = paramMotionEvent.getY(0) + paramMotionEvent.getY(1);
    paramPointF.set(f1 / 2.0F, f2 / 2.0F);
  }

  private void setImageBitmap(Bitmap paramBitmap, int paramInt)
  {
    super.setImageBitmap(paramBitmap);
    Drawable localDrawable = getDrawable();
    if (localDrawable != null)
      localDrawable.setDither(true);
    Bitmap localBitmap = this.mBitmapDisplayed.getBitmap();
    this.mBitmapDisplayed.setBitmap(paramBitmap);
    this.mBitmapDisplayed.setRotation(paramInt);
    if ((localBitmap != null) && (localBitmap != paramBitmap) && (this.mRecycler != null))
      this.mRecycler.recycle(localBitmap);
  }

  private float spacing(MotionEvent paramMotionEvent)
  {
    float f1 = paramMotionEvent.getX(0) - paramMotionEvent.getX(1);
    float f2 = paramMotionEvent.getY(0) - paramMotionEvent.getY(1);
    return FloatMath.sqrt(f1 * f1 + f2 * f2);
  }

  public boolean canZoomIn()
  {
    return getScale() < this.mMaxZoom;
  }

  public boolean canZoomOut()
  {
    return getScale() > 1.0F;
  }

  protected void center(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.mBitmapDisplayed.getBitmap() == null)
      return;
    Matrix localMatrix = getImageViewMatrix();
    RectF localRectF = new RectF(0.0F, 0.0F, this.mBitmapDisplayed.getBitmap().getWidth(), this.mBitmapDisplayed.getBitmap().getHeight());
    localMatrix.mapRect(localRectF);
    float f1 = localRectF.height();
    float f2 = localRectF.width();
    float f3 = 0.0F;
    int j;
    float f4;
    int i;
    if (paramBoolean2)
    {
      j = getHeight();
      if (f1 < j)
        f3 = (j - f1) / 2.0F - localRectF.top;
    }
    else
    {
      f4 = 0.0F;
      if (paramBoolean1)
      {
        i = getWidth();
        if (f2 >= i)
          break label219;
        f4 = (i - f2) / 2.0F - localRectF.left;
      }
    }
    while (true)
    {
      postTranslate(f4, f3);
      setImageMatrix(getImageViewMatrix());
      return;
      if (localRectF.top > 0.0F)
      {
        f3 = -localRectF.top;
        break;
      }
      boolean bool2 = localRectF.bottom < j;
      f3 = 0.0F;
      if (!bool2)
        break;
      f3 = getHeight() - localRectF.bottom;
      break;
      label219: if (localRectF.left > 0.0F)
      {
        f4 = -localRectF.left;
        continue;
      }
      boolean bool1 = localRectF.right < i;
      f4 = 0.0F;
      if (!bool1)
        continue;
      f4 = i - localRectF.right;
    }
  }

  public void clear()
  {
    setImageBitmapResetBase(null, true);
  }

  public float getImageHeight()
  {
    Matrix localMatrix = getImageViewMatrix();
    RectF localRectF = new RectF(0.0F, 0.0F, this.mBitmapDisplayed.getBitmap().getWidth(), this.mBitmapDisplayed.getBitmap().getHeight());
    localMatrix.mapRect(localRectF);
    return localRectF.height();
  }

  protected Matrix getImageViewMatrix()
  {
    this.mDisplayMatrix.set(this.mBaseMatrix);
    this.mDisplayMatrix.postConcat(this.mSuppMatrix);
    return this.mDisplayMatrix;
  }

  public float getImageWidth()
  {
    Matrix localMatrix = getImageViewMatrix();
    RectF localRectF = new RectF(0.0F, 0.0F, this.mBitmapDisplayed.getBitmap().getWidth(), this.mBitmapDisplayed.getBitmap().getHeight());
    localMatrix.mapRect(localRectF);
    return localRectF.width();
  }

  protected float getScale()
  {
    return getScale(this.mSuppMatrix);
  }

  protected float getScale(Matrix paramMatrix)
  {
    return getValue(paramMatrix, 0);
  }

  public float getTransX()
  {
    return getValue(getImageMatrix(), 2);
  }

  public float getTransY()
  {
    return getValue(getImageMatrix(), 5);
  }

  protected float getValue(Matrix paramMatrix, int paramInt)
  {
    paramMatrix.getValues(this.mMatrixValues);
    return this.mMatrixValues[paramInt];
  }

  public boolean isBottomBound()
  {
    Matrix localMatrix = getImageViewMatrix();
    float f1 = getValue(getImageMatrix(), 5);
    RectF localRectF = new RectF(0.0F, 0.0F, this.mBitmapDisplayed.getBitmap().getWidth(), this.mBitmapDisplayed.getBitmap().getHeight());
    localMatrix.mapRect(localRectF);
    float f2 = localRectF.height();
    int i = getHeight();
    int j;
    if (f2 <= i)
      j = 1;
    boolean bool;
    do
    {
      return j;
      bool = f1 < i - f2;
      j = 0;
    }
    while (bool);
    return true;
  }

  public boolean isLeftBound()
  {
    if ((this.mBitmapDisplayed == null) || (this.mBitmapDisplayed.getBitmap() == null))
      return false;
    Matrix localMatrix = getImageViewMatrix();
    float f = getValue(getImageMatrix(), 2);
    RectF localRectF = new RectF(0.0F, 0.0F, this.mBitmapDisplayed.getBitmap().getWidth(), this.mBitmapDisplayed.getBitmap().getHeight());
    localMatrix.mapRect(localRectF);
    int i;
    if (localRectF.width() <= getWidth() - getPaddingLeft() - getPaddingRight())
      i = 1;
    while (true)
    {
      return i;
      boolean bool = f < 0.0F;
      i = 0;
      if (bool)
        continue;
      i = 1;
    }
  }

  public boolean isNeedDrag()
  {
    Matrix localMatrix = getImageViewMatrix();
    RectF localRectF = new RectF(0.0F, 0.0F, this.mBitmapDisplayed.getBitmap().getWidth(), this.mBitmapDisplayed.getBitmap().getHeight());
    localMatrix.mapRect(localRectF);
    boolean bool = localRectF.height() < getHeight();
    int i = 0;
    if (bool)
      i = 1;
    return i;
  }

  public boolean isRightBound()
  {
    if ((this.mBitmapDisplayed == null) || (this.mBitmapDisplayed.getBitmap() == null))
      return false;
    Matrix localMatrix = getImageViewMatrix();
    float f1 = getValue(getImageMatrix(), 2);
    RectF localRectF = new RectF(0.0F, 0.0F, this.mBitmapDisplayed.getBitmap().getWidth(), this.mBitmapDisplayed.getBitmap().getHeight());
    localMatrix.mapRect(localRectF);
    float f2 = localRectF.width();
    int i = getWidth();
    int j;
    if (f2 <= i)
      j = 1;
    while (true)
    {
      return j;
      boolean bool = f1 < i - f2;
      j = 0;
      if (bool)
        continue;
      j = 1;
    }
  }

  public boolean isTopBound()
  {
    Matrix localMatrix = getImageViewMatrix();
    float f = getValue(getImageMatrix(), 5);
    RectF localRectF = new RectF(0.0F, 0.0F, this.mBitmapDisplayed.getBitmap().getWidth(), this.mBitmapDisplayed.getBitmap().getHeight());
    localMatrix.mapRect(localRectF);
    int i;
    if (localRectF.height() <= getWidth())
      i = 1;
    boolean bool;
    do
    {
      return i;
      bool = f < 0.0F;
      i = 0;
    }
    while (bool);
    return true;
  }

  protected float maxZoom()
  {
    if (this.mBitmapDisplayed.getBitmap() == null)
      return 1.0F;
    return 4.0F * Math.max(this.mBitmapDisplayed.getWidth() / this.mThisWidth, this.mBitmapDisplayed.getHeight() / this.mThisHeight);
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    switch (paramInt)
    {
    default:
      center(true, true);
      return super.onKeyDown(paramInt, paramKeyEvent);
    case 21:
    case 22:
    case 19:
    case 20:
    }
    while (true)
    {
      try
      {
        if ((getScale() > 1.0F) || (paramKeyEvent.getEventTime() < this.mNextChangePositionTime))
          continue;
        this.mNextChangePositionTime = (500L + paramKeyEvent.getEventTime());
        return true;
        panBy(20.0F, 0.0F);
        center(true, false);
        continue;
      }
      finally
      {
        center(true, true);
      }
      if ((getScale() <= 1.0F) && (paramKeyEvent.getEventTime() >= this.mNextChangePositionTime))
      {
        this.mNextChangePositionTime = (500L + paramKeyEvent.getEventTime());
        continue;
      }
      panBy(-20.0F, 0.0F);
      center(true, false);
      continue;
      panBy(0.0F, 20.0F);
      center(false, true);
      continue;
      panBy(0.0F, -20.0F);
      center(false, true);
    }
  }

  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    return super.onKeyUp(paramInt, paramKeyEvent);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mThisWidth = (paramInt3 - paramInt1);
    this.mThisHeight = (paramInt4 - paramInt2);
    Runnable localRunnable = this.mOnLayoutRunnable;
    if (localRunnable != null)
    {
      this.mOnLayoutRunnable = null;
      localRunnable.run();
    }
    if (this.mBitmapDisplayed.getBitmap() != null)
    {
      getProperBaseMatrix(this.mBitmapDisplayed, this.mBaseMatrix);
      setImageMatrix(getImageViewMatrix());
    }
  }

  public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    int i = 1;
    float f1;
    if ((paramFloat1 <= 0.0F) || (isRightBound()))
    {
      boolean bool1 = paramFloat1 < 0.0F;
      f1 = 0.0F;
      if (bool1)
      {
        boolean bool4 = isLeftBound();
        f1 = 0.0F;
        if (bool4);
      }
    }
    else
    {
      f1 = mesaureDistanceX(paramFloat1);
    }
    float f2;
    if ((paramFloat2 <= 0.0F) || (isBottomBound()))
    {
      boolean bool2 = paramFloat2 < 0.0F;
      f2 = 0.0F;
      if (bool2)
      {
        boolean bool3 = isTopBound();
        f2 = 0.0F;
        if (bool3);
      }
    }
    else
    {
      f2 = mesaureDistanceY(paramFloat2);
    }
    if ((f1 != 0.0F) || (f2 != 0.0F))
    {
      panBy(-f1, -f2);
      i = 1;
    }
    return i;
  }

  protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = 1;
    int j = 0xFF & paramMotionEvent.getAction();
    int k = paramMotionEvent.getPointerCount();
    switch (j)
    {
    case 3:
    case 4:
    default:
      i = super.onTouchEvent(paramMotionEvent);
    case 0:
    case 1:
    case 6:
    case 5:
    case 2:
    }
    label266: label362: float f2;
    do
    {
      while (true)
      {
        return i;
        this.mPtMouseDown.set(paramMotionEvent.getX(), paramMotionEvent.getY());
        this.mPtLastMove.set(paramMotionEvent.getX(), paramMotionEvent.getY());
        this.mDragedOrZoomed = false;
        this.mode = i;
        break;
        this.mode = 0;
        if ((Math.abs(paramMotionEvent.getX() - this.mPtMouseDown.x) > 10.0F) || (Math.abs(paramMotionEvent.getY() - this.mPtMouseDown.y) > 10.0F))
          continue;
        if (!this.mDragedOrZoomed)
          break;
        return i;
        this.ori_ratio = getScale();
        this.oldDist = spacing(paramMotionEvent);
        if (this.oldDist <= 10.0F)
          break;
        this.mode = 2;
        break;
        if (this.mode != i)
          break label362;
        if (getScale() > 1.0F)
        {
          if (paramMotionEvent.getEventTime() < this.mNextChangePositionTime)
            break label266;
          this.mNextChangePositionTime = (500L + paramMotionEvent.getEventTime());
        }
        while ((isLeftBound()) || (isRightBound()))
        {
          return super.onTouchEvent(paramMotionEvent);
          this.mDragedOrZoomed = i;
          if (isNeedDrag())
          {
            panImage(this.mPtLastMove.x - paramMotionEvent.getX(), this.mPtLastMove.y - paramMotionEvent.getY());
            this.mPtLastMove.set(paramMotionEvent.getX(), paramMotionEvent.getY());
            continue;
          }
          panImage(this.mPtLastMove.x - paramMotionEvent.getX(), 0.0F);
          this.mPtLastMove.set(paramMotionEvent.getX(), this.mPtLastMove.y);
        }
      }
      if (this.mode != 2)
        break;
      float f1 = spacing(paramMotionEvent);
      if (f1 <= 10.0F)
        break;
      this.mDragedOrZoomed = i;
      f2 = f1 / this.oldDist * this.ori_ratio;
      if (k != 2)
        break;
    }
    while (f2 < 1.0F);
    zoomTo(f2);
    return i;
  }

  protected void panBy(float paramFloat1, float paramFloat2)
  {
    postTranslate(paramFloat1, paramFloat2);
    setImageMatrix(getImageViewMatrix());
  }

  public void panImage(float paramFloat1, float paramFloat2)
  {
    float f1;
    if ((paramFloat1 <= 0.0F) || (isRightBound()))
    {
      boolean bool1 = paramFloat1 < 0.0F;
      f1 = 0.0F;
      if (bool1)
      {
        boolean bool4 = isLeftBound();
        f1 = 0.0F;
        if (bool4);
      }
    }
    else
    {
      f1 = mesaureDistanceX(paramFloat1);
    }
    float f2;
    if ((paramFloat2 <= 0.0F) || (isBottomBound()))
    {
      boolean bool2 = paramFloat2 < 0.0F;
      f2 = 0.0F;
      if (bool2)
      {
        boolean bool3 = isTopBound();
        f2 = 0.0F;
        if (bool3);
      }
    }
    else
    {
      f2 = mesaureDistanceY(paramFloat2);
    }
    if ((f1 != 0.0F) || (f2 != 0.0F))
      panBy(-f1, -f2);
  }

  protected void postTranslate(float paramFloat1, float paramFloat2)
  {
    this.mSuppMatrix.postTranslate(paramFloat1, paramFloat2);
  }

  public void postTranslateCenter(float paramFloat1, float paramFloat2)
  {
    postTranslate(paramFloat1, paramFloat2);
    center(true, true);
  }

  public void rotate(int paramInt)
  {
  }

  public void setImageBitmap(Bitmap paramBitmap)
  {
    setImageBitmap(paramBitmap, 0);
  }

  public void setImageBitmapResetBase(Bitmap paramBitmap, boolean paramBoolean)
  {
    setImageRotateBitmapResetBase(new RotateBitmap(paramBitmap), paramBoolean);
  }

  public void setImageResource(int paramInt)
  {
    setImageBitmap(BitmapFactory.decodeResource(getResources(), paramInt));
  }

  public void setImageRotateBitmapResetBase(RotateBitmap paramRotateBitmap, boolean paramBoolean)
  {
    if (getWidth() <= 0)
    {
      this.mOnLayoutRunnable = new Runnable(paramRotateBitmap, paramBoolean)
      {
        public void run()
        {
          KXImageView.this.setImageRotateBitmapResetBase(this.val$bitmap, this.val$resetSupp);
        }
      };
      return;
    }
    if (paramRotateBitmap.getBitmap() != null)
    {
      getProperBaseMatrix(paramRotateBitmap, this.mBaseMatrix);
      setImageBitmap(paramRotateBitmap.getBitmap(), paramRotateBitmap.getRotation());
    }
    while (true)
    {
      if (paramBoolean)
        this.mSuppMatrix.reset();
      setImageMatrix(getImageViewMatrix());
      this.mMaxZoom = maxZoom();
      return;
      this.mBaseMatrix.reset();
      setImageBitmap(null);
    }
  }

  public void setRecycler(Recycler paramRecycler)
  {
    this.mRecycler = paramRecycler;
  }

  public void zoomIn()
  {
    zoomIn(1.25F);
  }

  protected void zoomIn(float paramFloat)
  {
    if (getScale() >= this.mMaxZoom);
    do
      return;
    while (this.mBitmapDisplayed.getBitmap() == null);
    float f1 = getWidth() / 2.0F;
    float f2 = getHeight() / 2.0F;
    this.mSuppMatrix.postScale(paramFloat, paramFloat, f1, f2);
    setScaleType(ImageView.ScaleType.MATRIX);
    setImageMatrix(getImageViewMatrix());
    center(true, true);
  }

  public void zoomOut()
  {
    zoomOut(1.25F);
  }

  protected void zoomOut(float paramFloat)
  {
    if (this.mBitmapDisplayed.getBitmap() == null)
      return;
    float f1 = getWidth() / 2.0F;
    float f2 = getHeight() / 2.0F;
    Matrix localMatrix = new Matrix(this.mSuppMatrix);
    localMatrix.postScale(1.0F / paramFloat, 1.0F / paramFloat, f1, f2);
    if (getScale(localMatrix) < 1.0F)
      this.mSuppMatrix.setScale(1.0F, 1.0F, f1, f2);
    while (true)
    {
      setScaleType(ImageView.ScaleType.MATRIX);
      setImageMatrix(getImageViewMatrix());
      center(true, true);
      return;
      this.mSuppMatrix.postScale(1.0F / paramFloat, 1.0F / paramFloat, f1, f2);
    }
  }

  public void zoomTo(float paramFloat)
  {
    zoomTo(paramFloat, getWidth() / 2.0F, getHeight() / 2.0F);
  }

  protected void zoomTo(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramFloat1 > this.mMaxZoom)
      paramFloat1 = this.mMaxZoom;
    float f = paramFloat1 / getScale();
    this.mSuppMatrix.postScale(f, f, paramFloat2, paramFloat3);
    setImageMatrix(getImageViewMatrix());
    center(true, true);
  }

  protected void zoomTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f1 = (paramFloat1 - getScale()) / paramFloat4;
    float f2 = getScale();
    long l = System.currentTimeMillis();
    this.mHandler.post(new Runnable(paramFloat4, l, f2, f1, paramFloat2, paramFloat3)
    {
      public void run()
      {
        long l = System.currentTimeMillis();
        float f1 = Math.min(this.val$durationMs, (float)(l - this.val$startTime));
        float f2 = this.val$oldScale + f1 * this.val$incrementPerMs;
        KXImageView.this.zoomTo(f2, this.val$centerX, this.val$centerY);
        if (f1 < this.val$durationMs)
          KXImageView.this.mHandler.post(this);
      }
    });
  }

  protected void zoomToPoint(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f1 = getWidth() / 2.0F;
    float f2 = getHeight() / 2.0F;
    panBy(f1 - paramFloat2, f2 - paramFloat3);
    zoomTo(paramFloat1, f1, f2);
  }

  public static abstract interface Recycler
  {
    public abstract void recycle(Bitmap paramBitmap);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXImageView
 * JD-Core Version:    0.6.0
 */