package com.tencent.tauth;

public abstract interface IUploadFileToWeiyunStatus
{
  public abstract void onError(String paramString);

  public abstract void onPrepareStart();

  public abstract void onUploadProgress(int paramInt);

  public abstract void onUploadStart();

  public abstract void onUploadSuccess();
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.IUploadFileToWeiyunStatus
 * JD-Core Version:    0.6.0
 */