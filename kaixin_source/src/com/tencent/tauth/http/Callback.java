package com.tencent.tauth.http;

public abstract interface Callback
{
  public abstract void onCancel(int paramInt);

  public abstract void onFail(int paramInt, String paramString);

  public abstract void onSuccess(Object paramObject);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.http.Callback
 * JD-Core Version:    0.6.0
 */