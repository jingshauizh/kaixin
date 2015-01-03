package com.kaixin001.view;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import com.kaixin001.util.KXLog;

public abstract class KXHighlightView
{
  public static final int GROW_BOTTOM_EDGE = 16;
  public static final int GROW_LEFT_EDGE = 2;
  public static final int GROW_NONE = 1;
  public static final int GROW_RIGHT_EDGE = 4;
  public static final int GROW_TOP_EDGE = 8;
  public static final int MOVE = 32;
  private static final String TAG = "HighlightView";
  private boolean mCircle = false;
  View mContext;
  RectF mCropRect;
  Rect mDrawRect;
  protected final Paint mFocusPaint = new Paint();
  boolean mHidden;
  private RectF mImageRect;
  private float mInitialAspectRatio;
  boolean mIsFocused;
  private boolean mMaintainAspectRatio = false;
  Matrix mMatrix;
  private ModifyMode mMode = ModifyMode.None;
  protected final Paint mNoFocusPaint = new Paint();
  protected final Paint mOutlinePaint = new Paint();

  public KXHighlightView(View paramView)
  {
    this.mContext = paramView;
  }

  private Rect computeLayout()
  {
    RectF localRectF = new RectF(this.mCropRect.left, this.mCropRect.top, this.mCropRect.right, this.mCropRect.bottom);
    this.mMatrix.mapRect(localRectF);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mCropRect;
    KXLog.w("HighlightView", "******  image crop rectF %s  ******", arrayOfObject);
    KXLog.w("HighlightView", "******  screen rectF %s  ******", new Object[] { localRectF });
    localRectF.offset(this.mContext.getPaddingLeft(), this.mContext.getPaddingTop());
    return new Rect(Math.round(localRectF.left), Math.round(localRectF.top), Math.round(localRectF.right), Math.round(localRectF.bottom));
  }

  protected abstract void draw(Canvas paramCanvas);

  public Rect getCropRect()
  {
    return new Rect((int)this.mCropRect.left, (int)this.mCropRect.top, (int)this.mCropRect.right, (int)this.mCropRect.bottom);
  }

  public int getHit(float paramFloat1, float paramFloat2)
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Float.valueOf(paramFloat1);
    arrayOfObject[1] = Float.valueOf(paramFloat2);
    KXLog.w("HighlightView", "........ getHit x = %f, y = %f ........", arrayOfObject);
    Rect localRect = computeLayout();
    int i = 1;
    if (this.mCircle)
    {
      float f1 = paramFloat1 - localRect.centerX();
      float f2 = paramFloat2 - localRect.centerY();
      int m = (int)Math.sqrt(f1 * f1 + f2 * f2);
      int n = this.mDrawRect.width() / 2;
      if (Math.abs(m - n) <= 20.0F)
      {
        if (Math.abs(f2) > Math.abs(f1))
        {
          if (f2 < 0.0F)
          {
            i = 8;
            return i;
          }
          return 16;
        }
        if (f1 < 0.0F)
          return 2;
        return 4;
      }
      if (m < n)
        return 32;
      return 1;
    }
    int j;
    if ((paramFloat2 >= localRect.top - 20.0F) && (paramFloat2 < 20.0F + localRect.bottom))
    {
      j = 1;
      label192: if ((paramFloat1 < localRect.left - 20.0F) || (paramFloat1 >= 20.0F + localRect.right))
        break label364;
    }
    label364: for (int k = 1; ; k = 0)
    {
      if ((Math.abs(localRect.left - paramFloat1) < 20.0F) && (j != 0))
        i |= 2;
      if ((Math.abs(localRect.right - paramFloat1) < 20.0F) && (j != 0))
        i |= 4;
      if ((Math.abs(localRect.top - paramFloat2) < 20.0F) && (k != 0))
        i |= 8;
      if ((Math.abs(localRect.bottom - paramFloat2) < 20.0F) && (k != 0))
        i |= 16;
      if ((i != 1) || (!localRect.contains((int)paramFloat1, (int)paramFloat2)))
        break;
      return 32;
      j = 0;
      break label192;
    }
  }

  void growBy(int paramInt, float paramFloat1, float paramFloat2)
  {
    float f = 100.0F;
    RectF localRectF;
    if (this.mMaintainAspectRatio)
    {
      if (paramFloat1 != 0.0F)
        paramFloat2 = paramFloat1 / this.mInitialAspectRatio;
    }
    else
    {
      localRectF = new RectF(this.mCropRect);
      if ((paramInt & 0x2) != 0)
      {
        localRectF.left = (paramFloat1 + localRectF.left);
        if (localRectF.left < this.mImageRect.left)
          localRectF.left = this.mImageRect.left;
      }
      if ((paramInt & 0x4) != 0)
      {
        localRectF.right = (paramFloat1 + localRectF.right);
        if (localRectF.right > this.mImageRect.right)
          localRectF.right = this.mImageRect.right;
      }
      if ((paramInt & 0x8) != 0)
      {
        localRectF.top = (paramFloat2 + localRectF.top);
        if (localRectF.top < this.mImageRect.top)
          localRectF.top = this.mImageRect.top;
      }
      if ((paramInt & 0x10) != 0)
      {
        localRectF.bottom = (paramFloat2 + localRectF.bottom);
        if (localRectF.bottom > this.mImageRect.bottom)
          localRectF.bottom = this.mImageRect.bottom;
      }
      if (localRectF.width() < f)
        localRectF.inset(-(f - localRectF.width()) / 2.0F, 0.0F);
      if (this.mMaintainAspectRatio)
        f /= this.mInitialAspectRatio;
      if (localRectF.height() < f)
        localRectF.inset(0.0F, -(f - localRectF.height()) / 2.0F);
      if (localRectF.left >= this.mImageRect.left)
        break label406;
      localRectF.offset(this.mImageRect.left - localRectF.left, 0.0F);
      label330: if (localRectF.top >= this.mImageRect.top)
        break label445;
      localRectF.offset(0.0F, this.mImageRect.top - localRectF.top);
    }
    while (true)
    {
      this.mCropRect.set(localRectF);
      this.mDrawRect = computeLayout();
      this.mContext.invalidate();
      return;
      if (paramFloat2 == 0.0F)
        break;
      paramFloat1 = paramFloat2 * this.mInitialAspectRatio;
      break;
      label406: if (localRectF.right <= this.mImageRect.right)
        break label330;
      localRectF.offset(-(localRectF.right - this.mImageRect.right), 0.0F);
      break label330;
      label445: if (localRectF.bottom <= this.mImageRect.bottom)
        continue;
      localRectF.offset(0.0F, -(localRectF.bottom - this.mImageRect.bottom));
    }
  }

  void handleMotion(int paramInt, float paramFloat1, float paramFloat2)
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Float.valueOf(paramFloat1);
    arrayOfObject[1] = Float.valueOf(paramFloat2);
    KXLog.w("HighlightView", "......... handleMotion dx = %f   dy = %f .......", arrayOfObject);
    Rect localRect = computeLayout();
    if (paramInt == 1)
      return;
    if (paramInt == 32)
    {
      moveBy(paramFloat1 * (this.mCropRect.width() / localRect.width()), paramFloat2 * (this.mCropRect.height() / localRect.height()));
      return;
    }
    if ((paramInt & 0x6) == 0)
      paramFloat1 = 0.0F;
    if ((paramInt & 0x18) == 0)
      paramFloat2 = 0.0F;
    growBy(paramInt, paramFloat1 * (this.mCropRect.width() / localRect.width()), paramFloat2 * (this.mCropRect.height() / localRect.height()));
  }

  public boolean hasFocus()
  {
    return this.mIsFocused;
  }

  protected void init()
  {
  }

  public void invalidate()
  {
    this.mDrawRect = computeLayout();
  }

  void moveBy(float paramFloat1, float paramFloat2)
  {
    Rect localRect = new Rect(this.mDrawRect);
    this.mCropRect.offset(paramFloat1, paramFloat2);
    this.mCropRect.offset(Math.max(0.0F, this.mImageRect.left - this.mCropRect.left), Math.max(0.0F, this.mImageRect.top - this.mCropRect.top));
    this.mCropRect.offset(Math.min(0.0F, this.mImageRect.right - this.mCropRect.right), Math.min(0.0F, this.mImageRect.bottom - this.mCropRect.bottom));
    this.mDrawRect = computeLayout();
    localRect.union(this.mDrawRect);
    localRect.inset(-15, -15);
    this.mContext.invalidate(localRect);
  }

  public void setFocus(boolean paramBoolean)
  {
    this.mIsFocused = paramBoolean;
  }

  public void setHidden(boolean paramBoolean)
  {
    this.mHidden = paramBoolean;
  }

  public void setMode(ModifyMode paramModifyMode)
  {
    if (paramModifyMode != this.mMode)
    {
      this.mMode = paramModifyMode;
      this.mContext.invalidate();
    }
  }

  public void setup(Matrix paramMatrix, Rect paramRect, RectF paramRectF, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean1)
      paramBoolean2 = true;
    float[] arrayOfFloat = new float[9];
    paramMatrix.getValues(arrayOfFloat);
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = Float.valueOf(arrayOfFloat[0]);
    KXLog.w("HighlightView", "------- scale level >> %f", arrayOfObject1);
    KXLog.w("HighlightView", "--------------- setup() input matrix>>%s ----------", new Object[] { paramMatrix });
    this.mMatrix = new Matrix(paramMatrix);
    Object[] arrayOfObject2 = new Object[1];
    arrayOfObject2[0] = this.mMatrix;
    KXLog.w("HighlightView", "--------------- setup() local matrix>>%s ----------", arrayOfObject2);
    KXLog.w("HighlightView", "--------------- setup() image rect>>%s ----------", new Object[] { paramRect });
    KXLog.w("HighlightView", "--------------- setup() corp rect>>%s ----------", new Object[] { paramRectF });
    this.mCropRect = paramRectF;
    this.mImageRect = new RectF(paramRect);
    this.mMaintainAspectRatio = paramBoolean2;
    this.mCircle = paramBoolean1;
    this.mInitialAspectRatio = (this.mCropRect.width() / this.mCropRect.height());
    this.mDrawRect = computeLayout();
    Object[] arrayOfObject3 = new Object[1];
    arrayOfObject3[0] = this.mDrawRect;
    KXLog.w("HighlightView", "--------------- setup() mDrawRect >>%s ----------", arrayOfObject3);
    this.mFocusPaint.setARGB(125, 50, 50, 50);
    this.mNoFocusPaint.setARGB(125, 50, 50, 50);
    this.mOutlinePaint.setStrokeWidth(3.0F);
    this.mOutlinePaint.setStyle(Paint.Style.STROKE);
    this.mOutlinePaint.setAntiAlias(true);
    this.mMode = ModifyMode.None;
    init();
  }

  static enum ModifyMode
  {
    static
    {
      Move = new ModifyMode("Move", 1);
      Grow = new ModifyMode("Grow", 2);
      ModifyMode[] arrayOfModifyMode = new ModifyMode[3];
      arrayOfModifyMode[0] = None;
      arrayOfModifyMode[1] = Move;
      arrayOfModifyMode[2] = Grow;
      ENUM$VALUES = arrayOfModifyMode;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXHighlightView
 * JD-Core Version:    0.6.0
 */