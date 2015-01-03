package com.kaixin001.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class KXAddIconView extends TextView
{
  public KXAddIconView(Context paramContext)
  {
    super(paramContext);
  }

  public KXAddIconView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public KXAddIconView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    Paint localPaint = new Paint();
    localPaint.setColor(Color.parseColor("#8dcfdd"));
    paramCanvas.drawRect(0.0F, -3 + getHeight() / 2, getWidth(), 3 + getHeight() / 2, localPaint);
    paramCanvas.drawRect(-3 + getWidth() / 2, 0.0F, 3 + getWidth() / 2, getHeight(), localPaint);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXAddIconView
 * JD-Core Version:    0.6.0
 */