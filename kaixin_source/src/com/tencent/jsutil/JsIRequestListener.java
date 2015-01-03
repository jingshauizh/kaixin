package com.tencent.jsutil;

public abstract interface JsIRequestListener
{
  public abstract void onComplete(String paramString1, String paramString2);

  public abstract void onConnectTimeoutException(String paramString1, String paramString2);

  public abstract void onHttpStatusException(String paramString1, String paramString2);

  public abstract void onIOException(String paramString1, String paramString2);

  public abstract void onJSONException(String paramString1, String paramString2);

  public abstract void onMalformedURLException(String paramString1, String paramString2);

  public abstract void onNetworkUnavailableException(String paramString1, String paramString2);

  public abstract void onSocketTimeoutException(String paramString1, String paramString2);

  public abstract void onUnknowException(String paramString1, String paramString2);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.jsutil.JsIRequestListener
 * JD-Core Version:    0.6.0
 */