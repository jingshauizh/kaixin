package com.tencent.tauth;

public abstract interface IDownloadFileFromWeiyunStatus
{
  public abstract void onDownloadProgress(int paramInt);

  public abstract void onDownloadStart();

  public abstract void onDownloadSuccess(String paramString);

  public abstract void onError(String paramString);

  public abstract void onPrepareStart();
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.IDownloadFileFromWeiyunStatus
 * JD-Core Version:    0.6.0
 */