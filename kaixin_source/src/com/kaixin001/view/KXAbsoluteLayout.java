package com.kaixin001.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class KXAbsoluteLayout extends ViewGroup
{
  public KXAbsoluteLayout(Context paramContext)
  {
    super(paramContext);
  }

  public KXAbsoluteLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public KXAbsoluteLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }

  protected ViewGroup.LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams(-2, -2, 0, 0);
  }

  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }

  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return new LayoutParams(paramLayoutParams);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getChildCount();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return;
      View localView = getChildAt(j);
      if (localView.getVisibility() == 8)
        continue;
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      int k = getPaddingLeft() + localLayoutParams.x;
      int m = getPaddingTop() + localLayoutParams.y;
      localView.layout(k, m, k + localView.getMeasuredWidth(), m + localView.getMeasuredHeight());
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = getChildCount();
    int j = 0;
    int k = 0;
    measureChildren(paramInt1, paramInt2);
    for (int m = 0; ; m++)
    {
      if (m >= i)
      {
        int i2 = k + (getPaddingLeft() + getPaddingRight());
        int i3 = Math.max(j + (getPaddingTop() + getPaddingBottom()), getSuggestedMinimumHeight());
        setMeasuredDimension(resolveSize(Math.max(i2, getSuggestedMinimumWidth()), paramInt1), resolveSize(i3, paramInt2));
        return;
      }
      View localView = getChildAt(m);
      if (localView.getVisibility() == 8)
        continue;
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      int n = localLayoutParams.x + localView.getMeasuredWidth();
      int i1 = localLayoutParams.y + localView.getMeasuredHeight();
      k = Math.max(k, n);
      j = Math.max(j, i1);
    }
  }

  public static class LayoutParams extends ViewGroup.LayoutParams
  {
    public int x;
    public int y;

    public LayoutParams(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      super(paramInt2);
      this.x = paramInt3;
      this.y = paramInt4;
    }

    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      this.x = 0;
      this.y = 0;
    }

    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }

    public String debug(String paramString)
    {
      return paramString + "Absolute.LayoutParams={width=" + this.width + ", height=" + this.height + " x=" + this.x + " y=" + this.y + "}";
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXAbsoluteLayout
 * JD-Core Version:    0.6.0
 */