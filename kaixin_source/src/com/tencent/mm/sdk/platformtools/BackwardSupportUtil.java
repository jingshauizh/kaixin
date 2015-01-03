package com.tencent.mm.sdk.platformtools;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.ExifInterface;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ListView;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BackwardSupportUtil
{
  public static final int ANDROID_API_LEVEL_16 = 16;

  public static class AnimationHelper
  {
    public static void cancelAnimation(View paramView, Animation paramAnimation)
    {
      if (Build.VERSION.SDK_INT >= 8)
      {
        new AnimationHelperImpl22().cancelAnimation(paramView, paramAnimation);
        return;
      }
      new AnimationHelperImpl21below().cancelAnimation(paramView, paramAnimation);
    }

    public static void overridePendingTransition(Activity paramActivity, int paramInt1, int paramInt2)
    {
      paramActivity.overridePendingTransition(paramInt1, paramInt2);
    }

    public static abstract interface IHelper
    {
      public abstract void cancelAnimation(View paramView, Animation paramAnimation);
    }
  }

  public static class BitmapFactory
  {
    public static Bitmap decodeFile(String paramString, float paramFloat)
    {
      BitmapFactory.Options localOptions = new BitmapFactory.Options();
      float f = 160.0F * paramFloat;
      localOptions.inDensity = (int)f;
      Bitmap localBitmap = BitmapFactory.decodeFile(paramString, localOptions);
      if (localBitmap != null)
        localBitmap.setDensity((int)f);
      return localBitmap;
    }

    public static Bitmap decodeStream(InputStream paramInputStream)
    {
      BitmapFactory.Options localOptions = new BitmapFactory.Options();
      localOptions.inDensity = 160;
      localOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
      return BitmapFactory.decodeStream(paramInputStream, null, localOptions);
    }

    public static Bitmap decodeStream(InputStream paramInputStream, float paramFloat)
    {
      BitmapFactory.Options localOptions = new BitmapFactory.Options();
      localOptions.inDensity = (int)(160.0F * paramFloat);
      localOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
      return BitmapFactory.decodeStream(paramInputStream, null, localOptions);
    }

    public static int fromDPToPix(Context paramContext, float paramFloat)
    {
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
      return Math.round(paramFloat * localDisplayMetrics.densityDpi / 160.0F);
    }

    public static Bitmap getBitmapFromURL(String paramString)
    {
      try
      {
        Log.d("MicroMsg.SDK.BackwardSupportUtil", "get bitmap from url:" + paramString);
        HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
        localHttpURLConnection.setDoInput(true);
        localHttpURLConnection.connect();
        InputStream localInputStream = localHttpURLConnection.getInputStream();
        Bitmap localBitmap = decodeStream(localInputStream);
        localInputStream.close();
        return localBitmap;
      }
      catch (IOException localIOException)
      {
        Log.e("MicroMsg.SDK.BackwardSupportUtil", "get bitmap from url failed");
        localIOException.printStackTrace();
        return null;
      }
      finally
      {
      }
      throw localObject;
    }

    public static String getDisplayDensityType(Context paramContext)
    {
      DisplayMetrics localDisplayMetrics = paramContext.getResources().getDisplayMetrics();
      Configuration localConfiguration = paramContext.getResources().getConfiguration();
      String str1;
      StringBuilder localStringBuilder;
      if (localDisplayMetrics.density < 1.0F)
      {
        str1 = "" + "LDPI";
        localStringBuilder = new StringBuilder().append(str1);
        if (localConfiguration.orientation != 2)
          break label140;
      }
      label140: for (String str2 = "_L"; ; str2 = "_P")
      {
        return str2;
        if (localDisplayMetrics.density >= 1.5F)
        {
          str1 = "" + "HDPI";
          break;
        }
        str1 = "" + "MDPI";
        break;
      }
    }
  }

  public static class ExifHelper
  {
    public static int getExifOrientation(String paramString)
    {
      try
      {
        localExifInterface = new ExifInterface(paramString);
        int i;
        if (localExifInterface != null)
        {
          i = localExifInterface.getAttributeInt("Orientation", -1);
          if (i == -1);
        }
        switch (i)
        {
        case 4:
        case 5:
        case 7:
        default:
          return 0;
        case 6:
        case 3:
        case 8:
        }
      }
      catch (IOException localIOException)
      {
        while (true)
        {
          Log.e("MicroMsg.SDK.BackwardSupportUtil", "cannot read exif" + localIOException);
          ExifInterface localExifInterface = null;
        }
        return 90;
      }
      return 180;
      return 270;
    }
  }

  public static class SmoothScrollFactory
  {
    public static void scrollTo(ListView paramListView, int paramInt)
    {
      if (Build.VERSION.SDK_INT >= 8)
      {
        new SmoothScrollToPosition22().doScroll(paramListView, paramInt);
        return;
      }
      new SmoothScrollToPosition21below().doScroll(paramListView, paramInt);
    }

    public static void scrollToTop(ListView paramListView)
    {
      if (Build.VERSION.SDK_INT >= 8)
      {
        new SmoothScrollToPosition22().doScroll(paramListView);
        return;
      }
      new SmoothScrollToPosition21below().doScroll(paramListView);
    }

    public static abstract interface IScroll
    {
      public abstract void doScroll(ListView paramListView);

      public abstract void doScroll(ListView paramListView, int paramInt);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.BackwardSupportUtil
 * JD-Core Version:    0.6.0
 */