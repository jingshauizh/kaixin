package com.kaixin001.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

public class KXCommentView extends ImageView
{
  private float textSize = 12.0F;

  public KXCommentView(Context paramContext)
  {
    super(paramContext);
  }

  public KXCommentView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public KXCommentView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public void setNum(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      paramString = "0";
    Bitmap localBitmap1 = BitmapFactory.decodeResource(getResources(), 2130838654);
    int i = localBitmap1.getWidth();
    int j = localBitmap1.getHeight();
    Canvas localCanvas = new Canvas();
    Bitmap localBitmap2 = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
    localCanvas.setBitmap(localBitmap2);
    Paint localPaint = new Paint();
    localPaint.setAntiAlias(true);
    localCanvas.drawBitmap(localBitmap1, 0.0F, 0.0F, localPaint);
    localPaint.setColor(-1);
    localPaint.setTextSize(this.textSize);
    Paint.FontMetrics localFontMetrics = localPaint.getFontMetrics();
    float f = localPaint.measureText(paramString);
    localCanvas.drawText(paramString, (i - f) / 2.0F, (j - localFontMetrics.ascent) / 2.0F - 4.0F, localPaint);
    setImageBitmap(localBitmap2);
  }

  public void setTextSize(float paramFloat)
  {
    this.textSize = paramFloat;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXCommentView
 * JD-Core Version:    0.6.0
 */