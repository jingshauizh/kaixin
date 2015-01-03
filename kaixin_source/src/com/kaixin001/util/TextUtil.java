package com.kaixin001.util;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class TextUtil
{
  public static void setTextViewShadow(TextView paramTextView, int paramInt1, int paramInt2)
  {
    paramTextView.setOnTouchListener(new View.OnTouchListener(paramTextView, paramInt2, paramInt1)
    {
      public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
      {
        if (paramMotionEvent.getAction() == 0)
          TextUtil.this.setShadowLayer(1.0F, 0.0F, 2.0F, this.val$shadowPress);
        while (true)
        {
          return false;
          if ((paramMotionEvent.getAction() != 3) && (paramMotionEvent.getAction() != 1))
            continue;
          TextUtil.this.setShadowLayer(1.0F, 0.0F, 2.0F, this.val$shadowNormal);
        }
      }
    });
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.TextUtil
 * JD-Core Version:    0.6.0
 */