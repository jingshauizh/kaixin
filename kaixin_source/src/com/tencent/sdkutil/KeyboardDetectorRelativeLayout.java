package com.tencent.sdkutil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class KeyboardDetectorRelativeLayout extends RelativeLayout
{
  private KeyboardDetectorRelativeLayout.IKeyboardChanged mKeyboardListener = null;
  private Rect rect = null;

  public KeyboardDetectorRelativeLayout(Context paramContext)
  {
    super(paramContext);
    if (this.rect == null)
      this.rect = new Rect();
  }

  public KeyboardDetectorRelativeLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (this.rect == null)
      this.rect = new Rect();
  }

  public void addKeyboardStateChangedListener(KeyboardDetectorRelativeLayout.IKeyboardChanged paramIKeyboardChanged)
  {
    this.mKeyboardListener = paramIKeyboardChanged;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt2);
    Activity localActivity = (Activity)getContext();
    localActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(this.rect);
    int j = this.rect.top;
    int k = localActivity.getWindowManager().getDefaultDisplay().getHeight() - j - i;
    if ((this.mKeyboardListener != null) && (i != 0))
    {
      if (k <= 100)
        break label112;
      this.mKeyboardListener.onKeyboardShown(Math.abs(this.rect.height()) - getPaddingBottom() - getPaddingTop());
    }
    while (true)
    {
      super.onMeasure(paramInt1, paramInt2);
      return;
      label112: this.mKeyboardListener.onKeyboardHidden();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.KeyboardDetectorRelativeLayout
 * JD-Core Version:    0.6.0
 */