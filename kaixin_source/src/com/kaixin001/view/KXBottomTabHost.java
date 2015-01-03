package com.kaixin001.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;
import com.kaixin001.activity.R.styleable;
import java.util.ArrayList;

public class KXBottomTabHost extends TextView
{
  private static final int MARGIN_LEFT_ICON = 2;
  private OnTabChangeListener changeListener;
  private Drawable mBkgBubble;
  private Drawable mBkgLeft;
  private Drawable mBkgMid;
  private Drawable mBkgRight;
  private Drawable mBkgSelectedLeft;
  private Drawable mBkgSelectedMid;
  private Drawable mBkgSelectedRight;
  private boolean mHeightChangeEnable = false;
  private int mSelectedIndex = -1;
  private Drawable mSeperator;
  private final ArrayList<KXBottomTabButton> mTabBtnList = new ArrayList();
  private int tabHeight = 45;
  private boolean useRedTab = true;

  public KXBottomTabHost(Context paramContext)
  {
    super(paramContext, null);
    initial(paramContext, null);
  }

  public KXBottomTabHost(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initial(paramContext, paramAttributeSet);
  }

  public KXBottomTabHost(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initial(paramContext, paramAttributeSet);
  }

  private void initial(Context paramContext, AttributeSet paramAttributeSet)
  {
    Resources localResources;
    if (paramAttributeSet == null)
    {
      this.useRedTab = true;
      localResources = getResources();
      if (!this.useRedTab)
        break label159;
      this.mBkgSelectedLeft = localResources.getDrawable(2130837569);
      this.mBkgSelectedMid = localResources.getDrawable(2130837571);
      this.mBkgSelectedRight = localResources.getDrawable(2130837572);
      this.mBkgLeft = localResources.getDrawable(2130837570);
      this.mBkgMid = localResources.getDrawable(2130837575);
      this.mBkgRight = localResources.getDrawable(2130837573);
    }
    for (this.mSeperator = localResources.getDrawable(2130837574); ; this.mSeperator = localResources.getDrawable(2130839084))
    {
      while (true)
      {
        this.mBkgBubble = localResources.getDrawable(2130839000);
        this.tabHeight = this.mBkgMid.getIntrinsicHeight();
        return;
        try
        {
          TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.KXBottomTabHost);
          this.useRedTab = localTypedArray.getBoolean(0, true);
          localTypedArray.recycle();
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
      break;
      label159: this.mBkgSelectedLeft = localResources.getDrawable(2130839078);
      this.mBkgSelectedMid = localResources.getDrawable(2130839079);
      this.mBkgSelectedRight = localResources.getDrawable(2130839080);
      this.mBkgLeft = localResources.getDrawable(2130839081);
      this.mBkgMid = localResources.getDrawable(2130839082);
      this.mBkgRight = localResources.getDrawable(2130839083);
    }
  }

  public void addTab(KXBottomTabButton paramKXBottomTabButton)
  {
    this.mTabBtnList.add(paramKXBottomTabButton);
  }

  public void clean()
  {
    this.mTabBtnList.clear();
  }

  public KXBottomTabButton getButton(int paramInt)
  {
    return (KXBottomTabButton)this.mTabBtnList.get(paramInt);
  }

  public int getCurrentTab()
  {
    return this.mSelectedIndex;
  }

  public int getTabCount()
  {
    return this.mTabBtnList.size();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    Paint localPaint = new Paint();
    localPaint.setAntiAlias(true);
    localPaint.setTextSize(getTextSize());
    Paint.FontMetrics localFontMetrics = localPaint.getFontMetrics();
    float f1 = localFontMetrics.descent - localFontMetrics.ascent;
    int i = this.mTabBtnList.size();
    if (i <= 0)
    {
      paramCanvas.drawText("no tab buttons", 0.0F, -localPaint.ascent(), localPaint);
      return;
    }
    int j = getWidth() / i;
    int k = getHeight();
    int m = Math.max(0, (int)(k - localFontMetrics.bottom + localFontMetrics.top));
    Rect localRect1 = new Rect(0, 0, j, k);
    Rect localRect2 = new Rect();
    Rect localRect3 = new Rect();
    Rect localRect4 = new Rect();
    int n = 0;
    label145: Drawable localDrawable;
    label186: float f2;
    float f11;
    float f12;
    label396: float f4;
    label448: Rect localRect7;
    float f7;
    float f8;
    label717: float f9;
    float f10;
    if (n < i)
    {
      KXBottomTabButton localKXBottomTabButton = (KXBottomTabButton)this.mTabBtnList.get(n);
      if (n != 0)
        break label870;
      if (n != this.mSelectedIndex)
        break label861;
      localDrawable = this.mBkgSelectedLeft;
      localDrawable.getPadding(localRect2);
      int i1 = (m - localRect2.top - localRect2.bottom) / 2;
      localRect1.top = i1;
      localRect1.bottom = (k - i1);
      localDrawable.setBounds(localRect1);
      localRect3.set(localRect1.left + localRect2.left, localRect1.top + localRect2.top, localRect1.right - localRect2.right, localRect1.bottom - localRect2.bottom);
      if (this.mHeightChangeEnable)
      {
        Rect localRect5 = new Rect(localRect1.left, 0, localRect1.right, k);
        localDrawable.setBounds(localRect5);
      }
      localDrawable.draw(paramCanvas);
      String str1 = localKXBottomTabButton.getText();
      f2 = localPaint.measureText(str1);
      Bitmap localBitmap = localKXBottomTabButton.getLeftIcon();
      if (localBitmap == null)
        break label947;
      f11 = (localRect3.right - localRect3.left - localBitmap.getWidth() - f2 - 2.0F) / 2.0F;
      if (f11 >= 0.0F)
        break label933;
      f12 = localRect3.left;
      float f13 = localRect3.top + (localRect3.bottom - localRect3.top - localBitmap.getHeight()) / 2;
      paramCanvas.drawBitmap(localBitmap, f12, f13, localPaint);
      f4 = f12 + (2 + localBitmap.getWidth());
      localPaint.setColor(-10066330);
      localPaint.setShadowLayer(1.0F, 0.0F, 1.0F, -1);
      float f5 = localRect3.top + (localRect3.bottom - localRect3.top - f1) / 2.0F - localFontMetrics.ascent;
      paramCanvas.drawText(str1, f4, f5, localPaint);
      String str2 = localKXBottomTabButton.getRightText();
      if (!TextUtils.isEmpty(str2))
      {
        this.mBkgBubble.getPadding(localRect2);
        float f6 = localPaint.measureText(str2);
        localRect7 = new Rect(localRect1.right - this.mBkgBubble.getIntrinsicWidth(), 0, localRect1.right, this.mBkgBubble.getIntrinsicHeight());
        if (this.mBkgBubble.getIntrinsicWidth() - localRect2.left - localRect2.right < f6)
        {
          localRect7.left = (localRect7.right - localRect2.left - localRect2.right - (int)f6);
          if (localRect7.left < localRect1.left)
            localRect7.left = localRect1.left;
        }
        this.mBkgBubble.setBounds(localRect7);
        this.mBkgBubble.draw(paramCanvas);
        f7 = (localRect7.right - localRect7.left - localRect2.left - localRect2.right - f6) / 2.0F;
        if (f7 >= 0.0F)
          break label998;
        f8 = localRect7.left + localRect2.left;
        f9 = (localRect7.bottom - localRect7.top - localRect2.top - localRect2.bottom - f1) / 2.0F;
        if (f9 >= 0.0F)
          break label1018;
        f10 = localRect7.top + localRect2.top - localFontMetrics.ascent;
        label774: localPaint.setColor(-1);
        paramCanvas.drawText(str2, f8, f10, localPaint);
      }
      if (n + 1 < i)
      {
        if (!this.mHeightChangeEnable)
          break label1043;
        Rect localRect6 = new Rect(-1 + localRect1.right, 0, localRect1.right, k);
        this.mSeperator.setBounds(localRect6);
      }
    }
    while (true)
    {
      this.mSeperator.draw(paramCanvas);
      localRect1.offset(j, 0);
      n++;
      break label145;
      break;
      label861: localDrawable = this.mBkgLeft;
      break label186;
      label870: if (n + 1 == i)
      {
        if (n == this.mSelectedIndex);
        for (localDrawable = this.mBkgSelectedRight; ; localDrawable = this.mBkgRight)
          break;
      }
      if (n == this.mSelectedIndex);
      for (localDrawable = this.mBkgSelectedMid; ; localDrawable = this.mBkgMid)
        break;
      label933: f12 = f11 + localRect3.left;
      break label396;
      label947: float f3 = (localRect3.right - localRect3.left - f2) / 2.0F;
      if (f3 < 0.0F)
      {
        f4 = localRect3.left;
        break label448;
      }
      f4 = f3 + localRect3.left;
      break label448;
      label998: f8 = f7 + (localRect7.left + localRect2.left);
      break label717;
      label1018: f10 = f9 + (localRect7.top + localRect2.top - localFontMetrics.ascent);
      break label774;
      label1043: localRect4.set(-1 + localRect1.right, localRect1.top, localRect1.right, localRect1.bottom);
      this.mSeperator.setBounds(localRect4);
    }
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    int i = paramKeyEvent.getAction();
    int j = 0;
    int k;
    if (i == 0)
    {
      k = getCurrentTab();
      j = 0;
      switch (paramInt)
      {
      default:
      case 21:
      case 22:
      }
    }
    while (true)
    {
      boolean bool = super.onKeyDown(paramInt, paramKeyEvent);
      if ((j != 0) || (bool))
        break;
      return false;
      j = 0;
      if (k <= 0)
        continue;
      setCurrentTab(k - 1);
      j = 1;
      continue;
      int m = -1 + getTabCount();
      j = 0;
      if (k >= m)
        continue;
      setCurrentTab(k + 1);
      j = 1;
    }
    return true;
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    super.onTouchEvent(paramMotionEvent);
    if (!isEnabled());
    label201: 
    while (true)
    {
      return false;
      if (paramMotionEvent.getAction() != 0)
        continue;
      int i = getWidth();
      int j = getHeight();
      int k = i;
      int m = this.mTabBtnList.size();
      if (m > 0)
        k = i / m;
      for (int n = 0; ; n++)
      {
        if (n >= m)
          break label201;
        int i1 = n * k;
        RectF localRectF = new RectF(i1, 0.0F, i1 + k, j);
        if (n == 0)
          localRectF.left = 0.0F;
        if (n == m - 1)
          localRectF.right = i;
        if (!localRectF.contains(paramMotionEvent.getX(), paramMotionEvent.getY()))
          continue;
        if (n == this.mSelectedIndex)
          break;
        if (this.changeListener != null)
          this.changeListener.beforeTabChanged(this.mSelectedIndex);
        this.mSelectedIndex = n;
        invalidate();
        if (this.changeListener == null)
          break;
        this.changeListener.onTabChanged(n);
        return false;
      }
    }
  }

  public void setCurrentTab(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < this.mTabBtnList.size()))
    {
      if ((this.changeListener == null) || (this.mSelectedIndex == paramInt) || (this.mSelectedIndex < 0))
        break label73;
      this.changeListener.beforeTabChanged(this.mSelectedIndex);
      this.mSelectedIndex = paramInt;
      this.changeListener.onTabChanged(this.mSelectedIndex);
    }
    while (true)
    {
      invalidate();
      return;
      label73: this.mSelectedIndex = paramInt;
    }
  }

  public void setHeightChangeEnable(boolean paramBoolean)
  {
    this.mHeightChangeEnable = paramBoolean;
  }

  public void setOnTabChangeListener(OnTabChangeListener paramOnTabChangeListener)
  {
    this.changeListener = paramOnTabChangeListener;
  }

  public static abstract interface OnTabChangeListener
  {
    public abstract void beforeTabChanged(int paramInt);

    public abstract void onTabChanged(int paramInt);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXBottomTabHost
 * JD-Core Version:    0.6.0
 */