package com.tencent.sdkutil;

import java.util.ArrayList;

public abstract interface AsynLoadImgBack
{
  public static final int LOAD_IMAGE_COMPLETED = 0;
  public static final int LOAD_IMAGE_IMAGE_FORMAT_ERROR = 3;
  public static final int LOAD_IMAGE_NO_SDCARD = 2;
  public static final int LOAD_IMAGE_PATH_NULL = 1;

  public abstract void batchSaved(int paramInt, ArrayList<String> paramArrayList);

  public abstract void saved(int paramInt, String paramString);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.AsynLoadImgBack
 * JD-Core Version:    0.6.0
 */