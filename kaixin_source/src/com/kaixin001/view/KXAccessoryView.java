package com.kaixin001.view;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import com.kaixin001.util.KXLog;

public class KXAccessoryView extends KXHighlightView
  implements ScaleGestureDetector.OnScaleGestureListener
{
  private static int BASE_ID = 0;
  private static final int CORNER_COLOR = 860638284;
  private static final int CORNER_DIMENS = 6;
  private static final int EREA_BG_COLOR = 866822826;
  private static final int INVALID_POINTER_ID = -1;
  private static final int MIN_HEIGHT = 40;
  private static final int MIN_WIDTH = 40;
  public static final int MOVE = 2;
  public static final int NONE = 1;
  public static final int ROTATE = 8;
  public static final int SCALE = 4;
  private static final String TAG = "KXAccessoryView";
  private int boundOffset;
  private int id = 0;
  private RectF mAccessoryRect;
  private int mActivePointerId = -1;
  private Matrix mBaseMatrix = new Matrix();
  private Paint mBgPaint = new Paint(1);
  private Bitmap mBitmap;
  private BitmapDrawable mClose;
  private Rect mCloseBounds;
  private int mCloseSemiHeight;
  private int mCloseSemiWidth;
  private Rect mCornerRect;
  private float mDensity = 1.0F;
  private final Matrix mDispMatrix = new Matrix();
  private RectF mImageRect;
  private float mLastX;
  private float mLastY;
  private Paint mPaint = new Paint();
  private ScaleGestureDetector mScaleGestureDetector;
  private Matrix mSuppMatrix = new Matrix();
  private int mTouchState = 1;

  public KXAccessoryView(View paramView, Bitmap paramBitmap, float paramFloat)
  {
    super(paramView);
    this.mBitmap = paramBitmap;
    this.mDensity = paramFloat;
    this.mClose = ((BitmapDrawable)paramView.getResources().getDrawable(2130837704));
    this.mCloseSemiWidth = (this.mClose.getBitmap().getWidth() >> 1);
    this.mCloseSemiHeight = (this.mClose.getBitmap().getHeight() >> 1);
    this.mFocusPaint.setARGB(255, 0, 255, 0);
    this.mBgPaint.setColor(866822826);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(paramBitmap.getWidth());
    arrayOfObject[1] = Integer.valueOf(paramBitmap.getHeight());
    KXLog.w("KXAccessoryView", "---------- bm width = %d , height = %d ---------", arrayOfObject);
    int i = BASE_ID;
    BASE_ID = i + 1;
    this.id = i;
    this.mScaleGestureDetector = new ScaleGestureDetector(paramView.getContext(), this);
    int j = (int)(6.0F * paramFloat);
    this.mCornerRect = new Rect(0, 0, j, j);
    this.boundOffset = (int)(-10.0F * paramFloat);
  }

  private void drawBackground(Canvas paramCanvas, Rect paramRect)
  {
    this.mBgPaint.setColor(866822826);
    paramCanvas.drawRect(paramRect, this.mBgPaint);
    this.mBgPaint.setColor(860638284);
    Rect localRect = new Rect(this.mCornerRect);
    localRect.offset(paramRect.left, paramRect.top);
    paramCanvas.drawRect(localRect, this.mBgPaint);
    localRect.offset(0, paramRect.height() - localRect.height());
    paramCanvas.drawRect(localRect, this.mBgPaint);
    localRect.offset(paramRect.width() - localRect.width(), 0);
    paramCanvas.drawRect(localRect, this.mBgPaint);
    localRect.offset(0, localRect.height() - paramRect.height());
    paramCanvas.drawRect(localRect, this.mBgPaint);
  }

  protected boolean clickedClose(float paramFloat1, float paramFloat2)
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this.mCloseBounds;
    arrayOfObject[1] = Float.valueOf(paramFloat1);
    arrayOfObject[2] = Float.valueOf(paramFloat2);
    KXLog.w("KXAccessoryView", "CLOSE BOUNDS >>%s << clicked x=%s, y=%s", arrayOfObject);
    Rect localRect = this.mCloseBounds;
    boolean bool = false;
    if (localRect != null)
      bool = this.mCloseBounds.contains((int)paramFloat1, (int)paramFloat2);
    return bool;
  }

  protected boolean computeHit(float paramFloat1, float paramFloat2)
  {
    return getValidateBounds().contains((int)paramFloat1, (int)paramFloat2);
  }

  Rect computeLayout()
  {
    RectF localRectF = new RectF(this.mAccessoryRect);
    this.mMatrix.mapRect(localRectF);
    localRectF.offset(this.mContext.getPaddingLeft(), this.mContext.getPaddingTop());
    return new Rect(Math.round(localRectF.left), Math.round(localRectF.top), Math.round(localRectF.right), Math.round(localRectF.bottom));
  }

  Matrix computeMatrix()
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mBaseMatrix;
    KXLog.w("KXAccessoryView", "---------- computeMatrix Base matrix = %s ---------", arrayOfObject);
    Matrix localMatrix = new Matrix(this.mBaseMatrix);
    KXLog.w("KXAccessoryView", "---------- computeMatrix  m=%s -----------", new Object[] { localMatrix });
    localMatrix.preConcat(this.mMatrix);
    return localMatrix;
  }

  protected void draw(Canvas paramCanvas)
  {
    Rect localRect1 = computeLayout();
    Rect localRect2;
    if (this.mIsFocused)
    {
      localRect2 = new Rect(localRect1);
      localRect2.inset(this.boundOffset, this.boundOffset);
      if (((0x2 & this.mTouchState) == 0) && ((0x4 & this.mTouchState) == 0))
        break label84;
      drawBackground(paramCanvas, localRect2);
    }
    while (true)
    {
      Bitmap localBitmap = this.mBitmap;
      this.mPaint.setDither(true);
      paramCanvas.drawBitmap(localBitmap, null, localRect1, this.mPaint);
      return;
      label84: int i = localRect2.left;
      int j = localRect2.top;
      this.mCloseBounds = new Rect(i - this.mCloseSemiWidth, j - this.mCloseSemiHeight, i + this.mCloseSemiWidth, j + this.mCloseSemiHeight);
      this.mClose.setBounds(this.mCloseBounds);
      this.mClose.draw(paramCanvas);
    }
  }

  public Bitmap getBitmap()
  {
    return this.mBitmap;
  }

  public RectF getBounds()
  {
    return this.mAccessoryRect;
  }

  protected Rect getValidateBounds()
  {
    Rect localRect = computeLayout();
    localRect.inset(-5, -5);
    return localRect;
  }

  protected void init()
  {
    super.init();
  }

  public void invalidate()
  {
    this.mDrawRect = computeLayout();
    this.mContext.invalidate();
  }

  void moveBy(float paramFloat1, float paramFloat2)
  {
    this.mAccessoryRect.offset(paramFloat1, paramFloat2);
    invalidate();
  }

  public boolean onScale(ScaleGestureDetector paramScaleGestureDetector)
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Float.valueOf(paramScaleGestureDetector.getScaleFactor());
    KXLog.w("KXAccessoryView", "# # # onScale >> factor:%f", arrayOfObject);
    this.mTouchState = (0x4 | this.mTouchState);
    scaleBy(paramScaleGestureDetector.getScaleFactor());
    return true;
  }

  public boolean onScaleBegin(ScaleGestureDetector paramScaleGestureDetector)
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Float.valueOf(paramScaleGestureDetector.getScaleFactor());
    KXLog.w("KXAccessoryView", "# # # # # # # onScaleBegin >> factor:%f", arrayOfObject);
    return true;
  }

  public void onScaleEnd(ScaleGestureDetector paramScaleGestureDetector, boolean paramBoolean)
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Float.valueOf(paramScaleGestureDetector.getScaleFactor());
    KXLog.w("KXAccessoryView", "# # # onScaleEnd >> factor:%f", arrayOfObject);
    this.mTouchState = (0xFFFFFFFB & this.mTouchState);
  }

  void scaleBy(float paramFloat)
  {
    float f1 = this.mAccessoryRect.width();
    float f2 = this.mAccessoryRect.height();
    if ((paramFloat < 1.0F) && ((f1 <= 40.0F) || (f2 <= 40.0F)))
      return;
    float f3 = Math.max(40.0F, f1 * paramFloat);
    float f4 = Math.max(40.0F, f2 * paramFloat);
    float f5 = (f3 - f1) / 2.0F;
    float f6 = (f4 - f2) / 2.0F;
    this.mAccessoryRect.inset(-f5, -f6);
    invalidate();
  }

  public void setup(Matrix paramMatrix, RectF paramRectF1, RectF paramRectF2)
  {
    this.mMatrix = paramMatrix;
    this.mImageRect = paramRectF1;
    this.mAccessoryRect = paramRectF2;
    KXLog.w("KXAccessoryView", "---------- setup() image rect>> %s", new Object[] { paramRectF1 });
    KXLog.w("KXAccessoryView", "---------- setup() accessory rect>> %s", new Object[] { paramRectF2 });
    this.mOutlinePaint.setColor(-16711936);
    this.mOutlinePaint.setStrokeWidth(3.0F);
    this.mOutlinePaint.setStyle(Paint.Style.STROKE);
    this.mOutlinePaint.setAntiAlias(true);
    init();
  }

  public String toString()
  {
    return "[ KXAccessory # " + this.id + "]";
  }

  protected boolean touchEvent(MotionEvent paramMotionEvent)
  {
    this.mScaleGestureDetector.onTouchEvent(paramMotionEvent);
    Rect localRect = computeLayout();
    switch (0xFF & paramMotionEvent.getAction())
    {
    case 4:
    case 5:
    default:
    case 0:
    case 2:
    case 3:
    case 1:
    case 6:
    }
    int i;
    do
    {
      while (true)
      {
        return true;
        KXLog.w("KXAccessoryView", "^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ACTION_DOWN ^ ^ ^ ^ ^ ^ ^ ^");
        float f5 = paramMotionEvent.getX();
        float f6 = paramMotionEvent.getY();
        if (computeHit(f5, f6))
          this.mIsFocused = true;
        this.mLastX = f5;
        this.mLastY = f6;
        this.mActivePointerId = paramMotionEvent.getPointerId(0);
        continue;
        KXLog.w("KXAccessoryView", "^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ACTION_MOVE ^ ^ ^ ^ ^ ^ ^ ^");
        int k = paramMotionEvent.findPointerIndex(this.mActivePointerId);
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = this;
        arrayOfObject[1] = Integer.valueOf(this.mActivePointerId);
        arrayOfObject[2] = Integer.valueOf(k);
        KXLog.w("KXAccessoryView", "# # # %s ACTION_MOVE use mActivePointerId:%d , pointerIndex : %d", arrayOfObject);
        if (k < 0)
        {
          KXLog.d("KXAccessoryView", "ArrayIndexOutOfBoundsException, pointerIndex < 0");
          continue;
        }
        float f1 = paramMotionEvent.getX(k);
        float f2 = paramMotionEvent.getY(k);
        if (!this.mScaleGestureDetector.isInProgress())
        {
          this.mTouchState = (0x2 | this.mTouchState);
          float f3 = f1 - this.mLastX;
          float f4 = f2 - this.mLastY;
          moveBy(f3 * (this.mAccessoryRect.width() / localRect.width()), f4 * (this.mAccessoryRect.height() / localRect.height()));
        }
        this.mLastX = f1;
        this.mLastY = f2;
        continue;
        KXLog.w("KXAccessoryView", "^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ACTION_CANCEL ^ ^ ^ ^ ^ ^ ^ ^");
        this.mActivePointerId = -1;
        continue;
        KXLog.w("KXAccessoryView", "^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ACTION_UP ^ ^ ^ ^ ^ ^ ^ ^");
        this.mActivePointerId = -1;
        if (paramMotionEvent.getEventTime() - paramMotionEvent.getDownTime() >= 200L)
          continue;
        this.mTouchState = 1;
        invalidate();
      }
      KXLog.w("KXAccessoryView", "^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ACTION_POINTER_UP ^ ^ ^ ^ ^ ^ ^ ^");
      i = (0xFF00 & paramMotionEvent.getAction()) >> 8;
    }
    while (paramMotionEvent.getPointerId(i) != this.mActivePointerId);
    if (i == 0);
    for (int j = 1; ; j = 0)
    {
      this.mLastX = paramMotionEvent.getX(j);
      this.mLastY = paramMotionEvent.getY(j);
      this.mActivePointerId = paramMotionEvent.getPointerId(j);
      break;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXAccessoryView
 * JD-Core Version:    0.6.0
 */