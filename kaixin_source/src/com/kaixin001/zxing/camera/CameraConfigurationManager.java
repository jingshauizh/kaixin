package com.kaixin001.zxing.camera;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import com.kaixin001.util.KXLog;
import java.util.regex.Pattern;

final class CameraConfigurationManager
{
  private static final Pattern COMMA_PATTERN;
  private static final String TAG = CameraConfigurationManager.class.getSimpleName();
  private static final int TEN_DESIRED_ZOOM = 27;
  private Point cameraResolution;
  private final Context context;
  private int previewFormat;
  private String previewFormatString;
  private Point screenResolution;

  static
  {
    COMMA_PATTERN = Pattern.compile(",");
  }

  CameraConfigurationManager(Context paramContext)
  {
    this.context = paramContext;
  }

  private static int findBestMotZoomValue(CharSequence paramCharSequence, int paramInt)
  {
    int i = 0;
    String[] arrayOfString = COMMA_PATTERN.split(paramCharSequence);
    int j = arrayOfString.length;
    int k = 0;
    while (true)
    {
      if (k >= j)
        return i;
      String str = arrayOfString[k].trim();
      try
      {
        double d = Double.parseDouble(str);
        int m = (int)(10.0D * d);
        if (Math.abs(paramInt - d) < Math.abs(paramInt - i))
          i = m;
        k++;
      }
      catch (NumberFormatException localNumberFormatException)
      {
      }
    }
    return paramInt;
  }

  private static Point findBestPreviewSizeValue(CharSequence paramCharSequence, Point paramPoint)
  {
    int i = 0;
    int j = 0;
    int k = 2147483647;
    String[] arrayOfString = COMMA_PATTERN.split(paramCharSequence);
    int m = arrayOfString.length;
    int n = 0;
    if (n >= m)
    {
      if ((i > 0) && (j > 0))
        return new Point(i, j);
    }
    else
    {
      String str = arrayOfString[n].trim();
      int i1 = str.indexOf('x');
      if (i1 < 0)
        KXLog.d(TAG, "Bad preview-size: " + str);
      while (true)
      {
        int i2;
        int i3;
        int i4;
        while (true)
        {
          n++;
          break;
          try
          {
            i2 = Integer.parseInt(str.substring(0, i1));
            i3 = Integer.parseInt(str.substring(i1 + 1));
            i4 = Math.abs(i2 - paramPoint.x) + Math.abs(i3 - paramPoint.y);
            if (i4 != 0)
              break label195;
            i = i2;
            j = i3;
          }
          catch (NumberFormatException localNumberFormatException)
          {
            KXLog.e(TAG, "Bad preview-size: " + str);
          }
        }
        continue;
        label195: if (i4 >= k)
          continue;
        i = i2;
        j = i3;
        k = i4;
      }
    }
    return null;
  }

  private static Point getCameraResolution(Camera.Parameters paramParameters, Point paramPoint)
  {
    String str = paramParameters.get("preview-size-values");
    if (str == null)
      str = paramParameters.get("preview-size-value");
    Point localPoint = null;
    if (str != null)
    {
      KXLog.d(TAG, "preview-size-values parameter: " + str);
      localPoint = findBestPreviewSizeValue(str, paramPoint);
    }
    if (localPoint == null)
      localPoint = new Point(paramPoint.x >> 3 << 3, paramPoint.y >> 3 << 3);
    return localPoint;
  }

  private void setFlash(Camera.Parameters paramParameters)
  {
    if ((Build.MODEL.contains("Behold II")) && (CameraManager.SDK_INT == 3))
      paramParameters.set("flash-value", 1);
    while (true)
    {
      paramParameters.set("flash-mode", "off");
      return;
      paramParameters.set("flash-value", 2);
    }
  }

  private void setZoom(Camera.Parameters paramParameters)
  {
    String str1 = paramParameters.get("zoom-supported");
    if ((str1 != null) && (!Boolean.parseBoolean(str1)));
    while (true)
    {
      return;
      int i = 27;
      String str2 = paramParameters.get("max-zoom");
      if (str2 != null);
      try
      {
        double d = Double.parseDouble(str2);
        int n = (int)(10.0D * d);
        if (i > n)
          i = n;
        str3 = paramParameters.get("taking-picture-zoom-max");
        if (str3 == null);
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        try
        {
          int m = Integer.parseInt(str3);
          if (i > m)
            i = m;
          str4 = paramParameters.get("mot-zoom-values");
          if (str4 != null)
            i = findBestMotZoomValue(str4, i);
          str5 = paramParameters.get("mot-zoom-step");
          if (str5 == null);
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          try
          {
            String str3;
            while (true)
            {
              String str4;
              String str5;
              int j = (int)(10.0D * Double.parseDouble(str5.trim()));
              if (j > 1)
              {
                int k = i % j;
                i -= k;
              }
              label154: if ((str2 != null) || (str4 != null))
                paramParameters.set("zoom", String.valueOf(i / 10.0D));
              if (str3 == null)
                break;
              paramParameters.set("taking-picture-zoom", i);
              return;
              localNumberFormatException3 = localNumberFormatException3;
              KXLog.e(TAG, "Bad max-zoom: " + str2);
            }
            localNumberFormatException2 = localNumberFormatException2;
            KXLog.e(TAG, "Bad taking-picture-zoom-max: " + str3);
          }
          catch (NumberFormatException localNumberFormatException1)
          {
            break label154;
          }
        }
      }
    }
  }

  Point getCameraResolution()
  {
    return this.cameraResolution;
  }

  int getPreviewFormat()
  {
    return this.previewFormat;
  }

  String getPreviewFormatString()
  {
    return this.previewFormatString;
  }

  Point getScreenResolution()
  {
    return this.screenResolution;
  }

  void initFromCameraParameters(Camera paramCamera)
  {
    Camera.Parameters localParameters = paramCamera.getParameters();
    this.previewFormat = localParameters.getPreviewFormat();
    this.previewFormatString = localParameters.get("preview-format");
    Display localDisplay = ((WindowManager)this.context.getSystemService("window")).getDefaultDisplay();
    int i = localDisplay.getWidth();
    int j = localDisplay.getHeight();
    this.screenResolution = new Point(i, j);
    if (i < j)
    {
      this.cameraResolution = getCameraResolution(localParameters, new Point(j, i));
      return;
    }
    this.cameraResolution = getCameraResolution(localParameters, this.screenResolution);
  }

  void setDesiredCameraParameters(Camera paramCamera)
  {
    paramCamera.setDisplayOrientation(90);
    Camera.Parameters localParameters = paramCamera.getParameters();
    KXLog.d(TAG, "Setting preview size: " + this.cameraResolution);
    localParameters.setPreviewSize(this.cameraResolution.x, this.cameraResolution.y);
    setFlash(localParameters);
    setZoom(localParameters);
    paramCamera.setParameters(localParameters);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.zxing.camera.CameraConfigurationManager
 * JD-Core Version:    0.6.0
 */