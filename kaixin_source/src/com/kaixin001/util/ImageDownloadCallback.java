package com.kaixin001.util;

public abstract interface ImageDownloadCallback
{
  public abstract void onImageDownloadFailed();

  public abstract void onImageDownloadSuccess();

  public abstract void onImageDownloading();
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.ImageDownloadCallback
 * JD-Core Version:    0.6.0
 */