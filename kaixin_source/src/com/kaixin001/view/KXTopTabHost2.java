package com.kaixin001.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import java.util.ArrayList;

public class KXTopTabHost2 extends RelativeLayout
{
  private static final int COLOR_SELECTEDFONG_BLUE = Color.parseColor("#38B2CF");
  private static final int COLOR_TABFONG_DEFATLT = Color.parseColor("#999999");
  private OnTabChangeListener changeListener;
  private int deltaHeight = dipToPx(getContext(), 4.0F);
  private int deltaWidth = dipToPx(getContext(), 75.0F);
  private Bitmap mBackImg;
  private int mBlueBarPosX = 0;
  private int mSelectedIndex = -1;
  private ArrayList<KXTopTab> topTabList = new ArrayList();

  public KXTopTabHost2(Context paramContext)
  {
    super(paramContext);
    initialImageView(paramContext);
  }

  public KXTopTabHost2(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initialImageView(paramContext);
  }

  public KXTopTabHost2(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initialImageView(paramContext);
  }

  private static int dipToPx(Context paramContext, float paramFloat)
  {
    return (int)(0.5F + paramFloat * paramContext.getResources().getDisplayMetrics().density);
  }

  private void initialImageView(Context paramContext)
  {
    this.deltaWidth = (paramContext.getResources().getDisplayMetrics().widthPixels / 2);
  }

  private void startAnim()
  {
    this.mBlueBarPosX = (getWidth() / this.topTabList.size() * this.mSelectedIndex);
  }

  public void addTab(KXTopTab paramKXTopTab)
  {
    this.topTabList.add(paramKXTopTab);
  }

  public void clean()
  {
    this.topTabList.clear();
  }

  public int getCurrentTab()
  {
    return this.mSelectedIndex;
  }

  public int getTabCount()
  {
    return this.topTabList.size();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    Paint localPaint = new Paint();
    localPaint.setAntiAlias(true);
    if (this.mBackImg != null)
    {
      Rect localRect1 = new Rect(0, 0, this.mBackImg.getWidth(), getHeight());
      Rect localRect2 = new Rect(0, 0, getWidth(), getHeight());
      paramCanvas.drawBitmap(this.mBackImg, localRect1, localRect2, localPaint);
    }
    int i = this.topTabList.size();
    int j = getWidth() / i;
    int k = getHeight();
    localPaint.setColor(COLOR_SELECTEDFONG_BLUE);
    paramCanvas.drawRect(0.0F, getHeight() - 2, getWidth(), getHeight(), localPaint);
    localPaint.setTextAlign(Paint.Align.CENTER);
    int m = dipToPx(getContext(), 15.0F);
    localPaint.setTextSize(m);
    if (i <= 0)
    {
      paramCanvas.drawText("no tab buttons", 0.0F, -localPaint.ascent(), localPaint);
      return;
    }
    int n = 0;
    if (n >= i)
    {
      localPaint.setColor(COLOR_SELECTEDFONG_BLUE);
      paramCanvas.drawRect(this.mBlueBarPosX, getHeight() - 6, j + this.mBlueBarPosX, getHeight(), localPaint);
      return;
    }
    KXTopTab localKXTopTab = (KXTopTab)this.topTabList.get(n);
    int i1 = j * n + j / 2;
    int i2 = 2 * (k / 3);
    if (n < i - 1)
    {
      localPaint.setColor(436207615);
      int i7 = dipToPx(getContext(), 18.0F);
      int i8 = (k - i7) / 2;
      paramCanvas.drawRect(-1 + (j + j * n), i8, 1 + (j + j * n), i8 + i7, localPaint);
    }
    if (n == getCurrentTab())
    {
      localPaint.setColor(COLOR_SELECTEDFONG_BLUE);
      paramCanvas.drawText(localKXTopTab.getText(), i1, i2, localPaint);
    }
    while (true)
    {
      if (!TextUtils.isEmpty(localKXTopTab.getRightText()))
      {
        int i3 = (int)(i1 + (localPaint.measureText(localKXTopTab.getText()) - dipToPx(getContext(), 6 * localKXTopTab.getText().length())));
        int i4 = dipToPx(getContext(), 8.0F);
        int i5 = i4 + (i4 + (int)localPaint.measureText(localKXTopTab.getRightText()));
        int i6 = (int)(1.5F * m);
        if (localKXTopTab.getRightIcon() != null)
        {
          Rect localRect3 = new Rect(i3, i2 - m, i3 + i5, i6 + (i2 - m));
          localKXTopTab.getRightIcon().draw(paramCanvas, localRect3, localPaint);
        }
        localPaint.setColor(-1);
        paramCanvas.drawText(localKXTopTab.getRightText(), i3 + i4 + dipToPx(getContext(), 3.0F), i2, localPaint);
      }
      n++;
      break;
      localPaint.setColor(COLOR_TABFONG_DEFATLT);
      paramCanvas.drawText(localKXTopTab.getText(), i1, i2, localPaint);
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
    if (!isEnabled())
      return false;
    int i;
    int j;
    if (paramMotionEvent.getAction() == 0)
    {
      i = getWidth();
      j = this.topTabList.size();
      if (j > 0)
        i = getWidth() / j;
    }
    for (int k = 0; ; k++)
    {
      if (k >= j);
      while (true)
      {
        return true;
        int m = k * i;
        if (!new RectF(m, 0.0F, m + i, getHeight()).contains(paramMotionEvent.getX(), paramMotionEvent.getY()))
          break;
        if (k == this.mSelectedIndex)
          continue;
        setCurrentTab(k);
      }
    }
  }

  public void setBackImg(int paramInt)
  {
    if (paramInt > 0)
      this.mBackImg = ((BitmapDrawable)getResources().getDrawable(paramInt)).getBitmap();
  }

  public void setCurrentTab(int paramInt)
  {
    setCurrentTab(paramInt, true);
  }

  public void setCurrentTab(int paramInt, boolean paramBoolean)
  {
    if ((paramInt >= 0) && (paramInt < this.topTabList.size()))
    {
      if ((!paramBoolean) || (this.changeListener == null) || (this.mSelectedIndex == paramInt) || (this.mSelectedIndex < 0))
        break label81;
      this.changeListener.beforeTabChanged(this.mSelectedIndex);
      this.mSelectedIndex = paramInt;
      this.changeListener.onTabChanged(this.mSelectedIndex);
    }
    while (true)
    {
      startAnim();
      invalidate();
      return;
      label81: this.mSelectedIndex = paramInt;
    }
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
 * Qualified Name:     com.kaixin001.view.KXTopTabHost2
 * JD-Core Version:    0.6.0
 */