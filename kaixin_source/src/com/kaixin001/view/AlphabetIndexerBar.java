package com.kaixin001.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class AlphabetIndexerBar extends View
{
  private static final String ALPHABET_LANDSCAPE = "A●D●G●I●L●O●R●T●W●Z";
  private static final String ALPHABET_PORTRAIT = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  public static final String FIRST_INDEXER = " ";
  private static final int INVALID_INDEX = -1;
  private static final String LAST_INDEXER = "Z";
  private static final char SIGN_CHAR = '●';
  private static final int mBkgPaddingBottom = 6;
  private static final int mBkgPaddingTop = 6;
  private static final int mFontColor = 1996488704;
  private static final int mPaddingBottom = 13;
  private static final int mPaddingRight = 4;
  private static final int mPaddingTop = 13;
  private String alphaBet = null;
  private Bitmap mBmpBkgBottom = null;
  private Bitmap mBmpBkgMiddle = null;
  private Bitmap mBmpBkgTop = null;
  private Bitmap mBmpSearchIcon = null;
  private float mFontSize = 12.0F;
  private boolean mHasMouseDown = false;
  private OnIndexerChangedListener mIndexerChangeListener = null;
  private int mIndexerWidth = 28;
  private float mItemHeight = -1.0F;
  private String mLastSection = null;
  private boolean mShowSearchIcon = true;

  public AlphabetIndexerBar(Context paramContext)
  {
    super(paramContext);
    loadBitmaps();
  }

  public AlphabetIndexerBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mFontSize = (paramContext.getResources().getDisplayMetrics().density * this.mFontSize);
    loadBitmaps();
  }

  public AlphabetIndexerBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mFontSize = (paramContext.getResources().getDisplayMetrics().density * this.mFontSize);
    loadBitmaps();
  }

  private String getAlphabet()
  {
    if (this.alphaBet != null)
      return this.alphaBet;
    if (isLandscape())
      return "A●D●G●I●L●O●R●T●W●Z";
    return "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  }

  private Rect getDrawingRect()
  {
    int i = getWidth();
    int j = getHeight();
    int k = i - 4;
    return new Rect(k - this.mIndexerWidth, 0, k, j);
  }

  private int getIndexerByPosition(int paramInt1, int paramInt2)
  {
    int i;
    if (!getDrawingRect().contains(paramInt1, paramInt2))
      i = -1;
    float f;
    int j;
    do
    {
      return i;
      i = getAlphabet().length();
      if (this.mShowSearchIcon)
        i++;
      f = (-13 + (-13 + getHeight())) / i;
      j = paramInt2 - 13;
      if (j < 0)
        return 0;
    }
    while (j >= f * i);
    return (int)(j / f);
  }

  private String getIndexerNameByIndex(int paramInt)
  {
    String str = getAlphabet();
    if (paramInt <= 0)
      return " ";
    if (paramInt >= str.length())
      return "Z";
    int i = str.charAt(paramInt - 1);
    if (i != 9679)
      return String.valueOf(i);
    return String.valueOf((char)('\001' + str.charAt(paramInt - 2)));
  }

  private boolean isLandscape()
  {
    WindowManager localWindowManager = (WindowManager)getContext().getSystemService("window");
    if (localWindowManager == null);
    Display localDisplay;
    do
    {
      return true;
      localDisplay = localWindowManager.getDefaultDisplay();
    }
    while (localDisplay.getWidth() > localDisplay.getHeight());
    return false;
  }

  private void loadBitmaps()
  {
    if (this.mBmpSearchIcon == null)
      this.mBmpSearchIcon = BitmapFactory.decodeResource(getResources(), 2130837737);
    if (this.mBmpBkgTop == null)
      this.mBmpBkgTop = BitmapFactory.decodeResource(getResources(), 2130837740);
    if (this.mBmpBkgMiddle == null)
      this.mBmpBkgMiddle = BitmapFactory.decodeResource(getResources(), 2130837739);
    if (this.mBmpBkgBottom == null)
      this.mBmpBkgBottom = BitmapFactory.decodeResource(getResources(), 2130837738);
    if (this.mBmpBkgMiddle != null)
      this.mIndexerWidth = this.mBmpBkgMiddle.getWidth();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    Paint localPaint = new Paint();
    localPaint.setColor(0);
    Rect localRect1 = getDrawingRect();
    paramCanvas.drawRect(localRect1, localPaint);
    String str1 = getAlphabet();
    float f1 = -13 + (-13 + getHeight());
    float f2;
    int i;
    int j;
    label170: float f3;
    int m;
    if (this.mShowSearchIcon)
    {
      f2 = f1 / (1 + str1.length());
      i = localRect1.left;
      if (this.mHasMouseDown)
      {
        Rect localRect2 = new Rect(0, 0, this.mBmpBkgTop.getWidth(), this.mBmpBkgTop.getHeight());
        Rect localRect3 = new Rect(i, 6, localRect1.right, 6 + this.mBmpBkgTop.getHeight());
        paramCanvas.drawBitmap(this.mBmpBkgTop, localRect2, localRect3, null);
        j = 6 + this.mBmpBkgTop.getHeight();
        int k = -6 + localRect1.bottom - this.mBmpBkgBottom.getHeight();
        if (j < k)
          break label471;
        Bitmap localBitmap2 = this.mBmpBkgBottom;
        Rect localRect6 = new Rect(0, 0, this.mBmpBkgBottom.getWidth(), this.mBmpBkgBottom.getHeight());
        Rect localRect7 = new Rect(i, j, localRect1.right, j + this.mBmpBkgBottom.getHeight());
        paramCanvas.drawBitmap(localBitmap2, localRect6, localRect7, null);
      }
      f3 = 13.0F;
      if ((this.mBmpSearchIcon != null) && (this.mShowSearchIcon))
      {
        int i1 = (this.mIndexerWidth - this.mBmpSearchIcon.getWidth()) / 2;
        int i2 = ((int)f2 - this.mBmpSearchIcon.getHeight()) / 2;
        localRect1.left = (i + i1);
        localRect1.top = (i2 + (int)f3);
        localRect1.right = (localRect1.left + this.mBmpSearchIcon.getWidth());
        localRect1.bottom = (localRect1.top + this.mBmpSearchIcon.getHeight());
        paramCanvas.drawBitmap(this.mBmpSearchIcon, new Rect(0, 0, this.mBmpSearchIcon.getWidth(), this.mBmpSearchIcon.getHeight()), localRect1, null);
        f3 += f2;
      }
      localPaint.setColor(1996488704);
      localPaint.setTextSize(this.mFontSize);
      localPaint.setFakeBoldText(true);
      localPaint.setAntiAlias(true);
      localPaint.setFlags(1);
      localPaint.setStyle(Paint.Style.FILL_AND_STROKE);
      localPaint.setFakeBoldText(true);
      m = ((int)f2 - (int)localPaint.getFontMetrics().ascent) / 2;
    }
    for (int n = 0; ; n++)
    {
      if (n >= str1.length())
      {
        return;
        f2 = f1 / str1.length();
        break;
        label471: Bitmap localBitmap1 = this.mBmpBkgMiddle;
        Rect localRect4 = new Rect(0, 0, this.mBmpBkgMiddle.getWidth(), 1);
        Rect localRect5 = new Rect(i, j, localRect1.right, j + 1);
        paramCanvas.drawBitmap(localBitmap1, localRect4, localRect5, null);
        j++;
        break label170;
      }
      String str2 = Character.valueOf(str1.charAt(n)).toString();
      float f4 = localPaint.measureText(str2);
      paramCanvas.drawText(str2, i + (this.mIndexerWidth - f4) / 2.0F, f3 + m, localPaint);
      f3 += f2;
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    Rect localRect = getDrawingRect();
    int i = (int)paramMotionEvent.getX();
    int j = (int)paramMotionEvent.getY();
    if (!localRect.contains(i, j))
    {
      if (this.mHasMouseDown)
      {
        this.mHasMouseDown = false;
        invalidate();
      }
      return false;
    }
    int n;
    int m;
    if (paramMotionEvent.getAction() == 0)
    {
      this.mHasMouseDown = true;
      n = 1;
      m = 1;
      invalidate();
    }
    while (true)
    {
      if (m != 0)
      {
        int i1 = getIndexerByPosition(i, j);
        if (i1 != -1)
        {
          String str = getIndexerNameByIndex(i1);
          if ((str != null) && (!str.equals(this.mLastSection)))
          {
            this.mLastSection = str;
            if (this.mIndexerChangeListener != null)
              this.mIndexerChangeListener.onIndexerChanged(str);
          }
        }
      }
      return n;
      if ((2 == paramMotionEvent.getAction()) && (this.mHasMouseDown))
      {
        m = 1;
        n = 1;
        continue;
      }
      int k = paramMotionEvent.getAction();
      m = 0;
      n = 0;
      if (1 != k)
        continue;
      boolean bool = this.mHasMouseDown;
      m = 0;
      n = 0;
      if (!bool)
        continue;
      this.mHasMouseDown = false;
      n = 1;
      this.mLastSection = null;
      invalidate();
      m = 0;
    }
  }

  public void setAlphabet(String paramString)
  {
    this.alphaBet = paramString;
    invalidate();
  }

  public void setOnIndexerChangedListener(OnIndexerChangedListener paramOnIndexerChangedListener)
  {
    this.mIndexerChangeListener = paramOnIndexerChangedListener;
  }

  public void showSearchIcon(boolean paramBoolean)
  {
    this.mShowSearchIcon = paramBoolean;
  }

  public static abstract interface OnIndexerChangedListener
  {
    public abstract void onIndexerChanged(String paramString);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.AlphabetIndexerBar
 * JD-Core Version:    0.6.0
 */