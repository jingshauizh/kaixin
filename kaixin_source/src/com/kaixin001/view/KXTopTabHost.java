package com.kaixin001.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import java.util.ArrayList;

public class KXTopTabHost extends RelativeLayout
{
  private static final int COLOR_SELECTEDFONG_BLUE = Color.parseColor("#38B2CF");
  private static final int COLOR_TABFONG_DEFATLT = Color.parseColor("#999999");
  private static Paint paint;
  private static Rect rect;
  private OnTabChangeListener changeListener;
  private int curIndex = -1;
  private final int deltaHeight = dipToPx(getContext(), 4.0F);
  private final int deltaWidth = dipToPx(getContext(), 75.0F);
  private ImageView ivBlueBar;
  private ArrayList<KXTopTab> topTabList = new ArrayList();

  public KXTopTabHost(Context paramContext)
  {
    super(paramContext);
    initial(paramContext);
  }

  public KXTopTabHost(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initial(paramContext);
  }

  public KXTopTabHost(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initial(paramContext);
  }

  private static int dipToPx(Context paramContext, float paramFloat)
  {
    return (int)(0.5F + paramFloat * paramContext.getResources().getDisplayMetrics().density);
  }

  private void initial(Context paramContext)
  {
    if (paint == null)
    {
      paint = new Paint();
      paint.setAntiAlias(true);
    }
    if (rect == null)
      rect = new Rect();
    this.ivBlueBar = new ImageView(paramContext);
    this.ivBlueBar.setImageResource(2130839068);
    this.ivBlueBar.setScaleType(ImageView.ScaleType.FIT_XY);
    this.ivBlueBar.setVisibility(4);
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(this.deltaWidth, this.deltaHeight);
    addView(this.ivBlueBar, localLayoutParams);
  }

  private void startAnim(float paramFloat, int paramInt)
  {
    int i = getWidth() / this.topTabList.size();
    int j = -6 + getHeight();
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(paramFloat, i / 2 + i * this.curIndex - this.deltaWidth / 2, j, j);
    localTranslateAnimation.setDuration(paramInt);
    localTranslateAnimation.setFillAfter(true);
    this.ivBlueBar.startAnimation(localTranslateAnimation);
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
    return this.curIndex;
  }

  public int getTabCount()
  {
    return this.topTabList.size();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i = this.topTabList.size();
    if (i <= 0)
    {
      paramCanvas.drawText("no tab buttons", 0.0F, -paint.ascent(), paint);
      return;
    }
    paint.setColor(COLOR_SELECTEDFONG_BLUE);
    paramCanvas.drawRect(0.0F, getHeight() - 2, getWidth(), getHeight(), paint);
    paint.setTextAlign(Paint.Align.CENTER);
    int j = dipToPx(getContext(), 14.7F);
    paint.setTextSize(j);
    int k = getWidth() / i;
    int m = getHeight();
    int n = 0;
    label113: KXTopTab localKXTopTab;
    int i1;
    int i2;
    if (n < i)
    {
      localKXTopTab = (KXTopTab)this.topTabList.get(n);
      i1 = k * n + k / 2;
      i2 = 2 * (m / 3);
      if (n != getCurrentTab())
        break label371;
      paint.setColor(COLOR_SELECTEDFONG_BLUE);
      paramCanvas.drawText(localKXTopTab.getText(), i1, i2, paint);
    }
    while (true)
    {
      if (!TextUtils.isEmpty(localKXTopTab.getRightText()))
      {
        int i3 = (int)(i1 + (paint.measureText(localKXTopTab.getText()) - dipToPx(getContext(), 6 * localKXTopTab.getText().length())));
        int i4 = dipToPx(getContext(), 8.0F);
        int i5 = i4 + (i4 + (int)paint.measureText(localKXTopTab.getRightText()));
        int i6 = (int)(1.5F * j);
        if (localKXTopTab.getRightIcon() != null)
        {
          rect.set(i3, i2 - j, i3 + i5, i6 + (i2 - j));
          localKXTopTab.getRightIcon().draw(paramCanvas, rect, paint);
        }
        paint.setColor(-1);
        paint.setTextAlign(Paint.Align.CENTER);
        paramCanvas.drawText(localKXTopTab.getRightText(), i3 + i5 / 2, i2, paint);
      }
      n++;
      break label113;
      break;
      label371: paint.setColor(COLOR_TABFONG_DEFATLT);
      paramCanvas.drawText(localKXTopTab.getText(), i1, i2, paint);
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
        if (k == this.curIndex)
          continue;
        OnTabChangeListener localOnTabChangeListener = this.changeListener;
        int n = 0;
        if (localOnTabChangeListener != null)
        {
          this.changeListener.beforeTabChanged(this.curIndex);
          n = i / 2 + i * this.curIndex - this.deltaWidth / 2;
        }
        this.curIndex = k;
        startAnim(n, 100);
        invalidate();
        if (this.changeListener == null)
          continue;
        this.changeListener.onTabChanged(k);
      }
    }
  }

  public void resetIndex()
  {
    this.curIndex = -1;
  }

  public void setCurrentTab(int paramInt)
  {
    if (this.curIndex < 0)
    {
      this.curIndex = paramInt;
      postDelayed(new Runnable()
      {
        public void run()
        {
          try
          {
            int i = KXTopTabHost.this.topTabList.size();
            if (i == 0)
              i = 1;
            int j = KXTopTabHost.this.getWidth() / i;
            int k = j / 2 + j * KXTopTabHost.this.curIndex - KXTopTabHost.this.deltaWidth / 2;
            KXTopTabHost.this.startAnim(k, 10);
            KXTopTabHost.this.postInvalidate();
            return;
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
          }
        }
      }
      , 200L);
    }
    do
      return;
    while ((paramInt < 0) || (paramInt >= this.topTabList.size()));
    if ((this.changeListener != null) && (this.curIndex != paramInt) && (this.curIndex >= 0))
    {
      this.changeListener.beforeTabChanged(this.curIndex);
      this.curIndex = paramInt;
      this.changeListener.onTabChanged(this.curIndex);
    }
    while (true)
    {
      invalidate();
      return;
      this.curIndex = paramInt;
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
 * Qualified Name:     com.kaixin001.view.KXTopTabHost
 * JD-Core Version:    0.6.0
 */