package com.kaixin001.pengpeng.http;

public abstract interface HttpRequestCallback
{
  public abstract void asyncRequestFail(HttpRequest paramHttpRequest, HttpException paramHttpException);

  public abstract void asyncRequestSuccess(HttpRequest paramHttpRequest, String paramString);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.pengpeng.http.HttpRequestCallback
 * JD-Core Version:    0.6.0
 */