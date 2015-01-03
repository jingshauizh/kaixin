package com.kaixin001.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.kaixin001.util.ImageCache;

public class KXSaveFlowImageView extends ImageView
{
  public static final int STATE_LOADING = 2;
  public static final int STATE_NORMAL = 0;
  public static final int STATE_SAVE_FLOW = 1;
  public static final int STATE_SHOW_IMAGE = 3;
  private static Drawable mDefaultDrawable;
  private Drawable mInfoDrawable;
  private int mState = 0;

  public KXSaveFlowImageView(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public KXSaveFlowImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  public KXSaveFlowImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }

  private void init()
  {
    if (mDefaultDrawable == null)
      mDefaultDrawable = new BitmapDrawable(ImageCache.getInstance().getLoadingBitmap(2130838229, 0, 0));
    this.mInfoDrawable = mDefaultDrawable;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if ((this.mState == 1) && (this.mInfoDrawable != null))
    {
      this.mInfoDrawable.setBounds(0, 0, getWidth(), getHeight());
      this.mInfoDrawable.draw(paramCanvas);
    }
  }

  public void resetState(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mState = 1;
      this.mInfoDrawable = mDefaultDrawable;
    }
    while (true)
    {
      postInvalidate();
      return;
      this.mState = 0;
    }
  }

  public boolean saveFlowState()
  {
    return this.mState == 1;
  }

  public void setInfoDrawable(int paramInt)
  {
    if (paramInt > 0);
    for (this.mInfoDrawable = new BitmapDrawable(ImageCache.getInstance().getLoadingBitmap(paramInt, 0, 0)); ; this.mInfoDrawable = null)
    {
      postInvalidate();
      return;
    }
  }

  public void setState(int paramInt)
  {
    this.mState = paramInt;
    postInvalidate();
  }

  public void startDownloading()
  {
    this.mState = 2;
    postInvalidate();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXSaveFlowImageView
 * JD-Core Version:    0.6.0
 */