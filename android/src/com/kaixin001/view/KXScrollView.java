package com.kaixin001.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class KXScrollView extends ScrollView
{
  private static final String TAG = "KXScrollView";
  private SizeChangeListener mSizeChangeListener;

  public KXScrollView(Context paramContext)
  {
    super(paramContext);
  }

  public KXScrollView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public KXScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramInt2 >= paramInt4){
    	return;
    }
    SizeChangeListener localSizeChangeListener  = this.mSizeChangeListener;
    if (this.mSizeChangeListener != null)
    {
    	localSizeChangeListener.onSizeChanged(paramInt4);
    }   
  }

  public void setSizeChangeListener(SizeChangeListener paramSizeChangeListener)
  {
    this.mSizeChangeListener = paramSizeChangeListener;
  }

  public static abstract interface SizeChangeListener
  {
    public static final int SIZE_NORMAL = 1;
    public static final int SIZE_SMALL = 2;

    public abstract void onSizeChanged(int paramInt);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXScrollView
 * JD-Core Version:    0.6.0
 */