package com.amap.mapapi.core;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.DisplayMetrics;
import java.lang.reflect.Field;

public class n
{
  private Context a = null;
  private String[] b = { "nomap.png", "beta.png", "satellite_watermaker.png", "poi_1.png", "compass_bg.png", "compass_pointer.png", "loc1.png", "loc2.png", "zoom_in_true_HVGA.9.png", "zoom_out_true_HVGA.9.png", "zoom_in_disabled_HVGA.9.png", "zoom_out_disabled_HVGA.9.png", "zoom_in_selected_HVGA.9.png", "zoom_out_selected_HVGA.9.png" };
  private String[] c = { "nomap.png", "beta.png", "satellite_watermaker.png", "poi_1_WVGA.png", "compass_bg.png", "compass_pointer.png", "loc1.png", "loc2.png", "zoom_in_true_WVGA.9.png", "zoom_out_true_WVGA.9.png", "zoom_in_disabled_WVGA.9.png", "zoom_out_disabled_WVGA.9.png", "zoom_in_selected_WVGA.9.png", "zoom_out_selected_WVGA.9.png" };
  private String[] d = { "nomap.png", "beta.png", "satellite_watermaker.png", "poi_1_QVGA.png", "compass_bg__QVGA.png", "compass_pointer_QVGA.png", "loc1_QVGA.png", "loc2_QVGA.png", "zoom_in_true_QVGA.9.png", "zoom_out_true_QVGA.9.png", "zoom_in_disabled_QVGA.9.png", "zoom_out_disabled_QVGA.9.png", "zoom_in_selected_QVGA.9.png", "zoom_out_selected_QVGA.9.png" };
  private Bitmap[] e = null;

  public n(Context paramContext)
  {
    this.a = paramContext;
  }

  public final Bitmap a(int paramInt)
  {
    if (this.e == null)
      this.e = new Bitmap[this.b.length];
    if ((this.e[paramInt] != null) && (!this.e[paramInt].isRecycled()))
      return this.e[paramInt];
    String str = "";
    if (c.e == 2)
      str = this.c[paramInt];
    while (true)
    {
      Bitmap localBitmap = a(this.a, str);
      if (localBitmap != null)
        this.e[paramInt] = localBitmap;
      return this.e[paramInt];
      if (c.e == 1)
      {
        str = this.d[paramInt];
        continue;
      }
      if (c.e != 3)
        continue;
      str = this.b[paramInt];
    }
  }

  // ERROR //
  public final Bitmap a(Context paramContext, String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 118	android/content/Context:getAssets	()Landroid/content/res/AssetManager;
    //   4: astore_3
    //   5: aload_3
    //   6: aload_2
    //   7: invokevirtual 124	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   10: astore 7
    //   12: aload 7
    //   14: invokestatic 130	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    //   17: astore 8
    //   19: aload 8
    //   21: astore 5
    //   23: aload 7
    //   25: invokevirtual 135	java/io/InputStream:close	()V
    //   28: aload 5
    //   30: areturn
    //   31: astore 4
    //   33: aconst_null
    //   34: astore 5
    //   36: aload 4
    //   38: astore 6
    //   40: aload 6
    //   42: invokevirtual 138	java/lang/Exception:printStackTrace	()V
    //   45: aload 5
    //   47: areturn
    //   48: astore 6
    //   50: goto -10 -> 40
    //
    // Exception table:
    //   from	to	target	type
    //   5	19	31	java/lang/Exception
    //   23	28	48	java/lang/Exception
  }

  public final NinePatchDrawable a(Context paramContext, String paramString, byte[] paramArrayOfByte, Rect paramRect)
  {
    return new NinePatchDrawable(a(paramContext, paramString), paramArrayOfByte, paramRect, null);
  }

  public void a()
  {
    if (this.e == null)
      return;
    int i = this.e.length;
    int j = 0;
    if (j < i)
    {
      if (this.e[j] == null);
      while (true)
      {
        j++;
        break;
        this.e[j].recycle();
        this.e[j] = null;
      }
    }
    this.e = null;
  }

  public final Drawable b(Context paramContext, String paramString)
  {
    BitmapDrawable localBitmapDrawable = new BitmapDrawable(a(paramContext, paramString));
    localBitmapDrawable.setBounds(0, 0, localBitmapDrawable.getIntrinsicWidth(), localBitmapDrawable.getIntrinsicHeight());
    return localBitmapDrawable;
  }

  public void b()
  {
    new DisplayMetrics();
    DisplayMetrics localDisplayMetrics = this.a.getApplicationContext().getResources().getDisplayMetrics();
    try
    {
      Field localField2 = localDisplayMetrics.getClass().getField("densityDpi");
      localField1 = localField2;
      if (localField1 != null)
      {
        long l2 = localDisplayMetrics.widthPixels * localDisplayMetrics.heightPixels;
        try
        {
          int j = localField1.getInt(localDisplayMetrics);
          i = j;
          if (i <= 120)
          {
            c.e = 1;
            return;
          }
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          while (true)
          {
            localIllegalArgumentException.printStackTrace();
            i = 160;
          }
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
          int i;
          while (true)
          {
            localIllegalAccessException.printStackTrace();
            i = 160;
          }
          if (i <= 160)
          {
            c.e = 3;
            return;
          }
          if (i <= 240)
          {
            c.e = 2;
            return;
          }
          if (l2 > 153600L)
          {
            c.e = 2;
            return;
          }
          if (l2 < 153600L)
          {
            c.e = 1;
            return;
          }
          c.e = 3;
          return;
        }
      }
      long l1 = localDisplayMetrics.widthPixels * localDisplayMetrics.heightPixels;
      if (l1 > 153600L)
      {
        c.e = 2;
        return;
      }
      if (l1 < 153600L)
      {
        c.e = 1;
        return;
      }
      c.e = 3;
      return;
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      while (true)
        localField1 = null;
    }
    catch (SecurityException localSecurityException)
    {
      while (true)
        Field localField1 = null;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.core.n
 * JD-Core Version:    0.6.0
 */