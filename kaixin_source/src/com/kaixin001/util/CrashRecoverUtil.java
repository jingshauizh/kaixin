package com.kaixin001.util;

import android.content.Context;
import com.kaixin001.model.FaceModel;

public class CrashRecoverUtil
{
  public static void crashRecover(Context paramContext)
  {
    ImageCache.getInstance().setContext(paramContext.getApplicationContext());
    FaceModel.getInstance();
  }

  public static boolean isCrashBefore()
  {
    return ImageCache.getInstance().getContext() == null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.CrashRecoverUtil
 * JD-Core Version:    0.6.0
 */