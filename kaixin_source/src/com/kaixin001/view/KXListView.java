package com.kaixin001.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ListView;

public class KXListView extends ListView
{
  private OnResizeListener mOnResizeListener = null;

  public KXListView(Context paramContext)
  {
    super(paramContext);
  }

  public KXListView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public KXListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    int i = getSelectedItemPosition();
    super.onFocusChanged(paramBoolean, paramInt, paramRect);
    if ((paramBoolean) && (i >= 0) && (i < getCount()))
      setSelection(i);
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mOnResizeListener != null)
      this.mOnResizeListener.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void setOnResizeListener(OnResizeListener paramOnResizeListener)
  {
    this.mOnResizeListener = paramOnResizeListener;
  }

  public static abstract interface OnResizeListener
  {
    public abstract void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXListView
 * JD-Core Version:    0.6.0
 */