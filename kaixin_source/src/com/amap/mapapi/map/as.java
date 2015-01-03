package com.amap.mapapi.map;

import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

class as
  implements g
{
  private f a = new f(Bitmap.Config.ARGB_4444);
  private Drawable b = null;

  public static void a(Drawable paramDrawable1, Drawable paramDrawable2)
  {
    Rect localRect = paramDrawable2.getBounds();
    int i = (int)(0.5F * localRect.height());
    int j = (int)(0.5D * (0.9F * localRect.width()));
    paramDrawable1.setBounds(j + localRect.left, i + localRect.top, j + localRect.right, i + localRect.bottom);
  }

  public Drawable a(Drawable paramDrawable)
  {
    this.b = paramDrawable;
    this.a.a(this.b.getIntrinsicWidth(), this.b.getIntrinsicHeight());
    this.a.a(this);
    this.b = null;
    return new BitmapDrawable(this.a.b());
  }

  public void a(Canvas paramCanvas)
  {
    this.b.setColorFilter(2130706432, PorterDuff.Mode.SRC_IN);
    paramCanvas.skew(-0.9F, 0.0F);
    paramCanvas.scale(1.0F, 0.5F);
    this.b.draw(paramCanvas);
    this.b.clearColorFilter();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.as
 * JD-Core Version:    0.6.0
 */